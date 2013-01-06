
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.util.*;

/**
 * Servlet implementation class for Servlet: DraftIssued
 *
 */
 public class DraftIssued extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public DraftIssued() {
		super();
	}   	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter pw = response.getWriter();
		Index myIndex = new Index();
		ServletContext ctx=this.getServletContext();
		String login =ctx.getAttribute("loginsession").toString();
		DataSource dsource = null;
		Statement stmt = null;
		Statement stmt1 = null;
		Connection conn = null;
		ResultSet rset = null;
		ResultSet rset1 = null;
		
		if(!login.equals("T"))
			pw.println(myIndex.section1+myIndex.section2+myIndex.section3+myIndex.section4);
		else
		{
			String per="";
			String date1="",time1="";
			String query="";
			try
			 {
			 InitialContext context = new InitialContext ();
			 dsource = (DataSource) context.lookup("java:comp/env/jdbc/MyDataSource");
			 conn = dsource.getConnection();
			 stmt=conn.createStatement();
			 stmt1=conn.createStatement();
			 per=ctx.getAttribute("UserID").toString();
			 ctx.setAttribute("loginsession", "T");
			 ctx.setAttribute("UserID",per);
			 query="select * from logininfo where logininfo.user='"+per+"'";
			 rset1=stmt.executeQuery(query);
			 
			 while(rset1.next())
			 {
				date1=rset1.getString(2);
				time1=rset1.getString(3);
				 break;
			 }
			 
			 float balance=0;
			char[] perarray = new char[per.length()];
			perarray=per.toCharArray();
			if(perarray[0]!='c')
			{
			if(perarray[0]=='e')
			
				//employee
				pw.println(myIndex.section1+myIndex.employee);
			else if(perarray[0]=='a')
				pw.println(myIndex.section1+myIndex.admin);
			String EmpUid=per;
			
			String InFavourOf = ctx.getAttribute("InFavourOf").toString();
			String Branch = ctx.getAttribute("Branch").toString();
			String Amount = ctx.getAttribute("Amnt").toString();
			
			String medium =ctx.getAttribute("medium").toString();
			
			String AccNo = ctx.getAttribute("AccNo").toString();
			
			String IdNum = ctx.getAttribute("IdNum").toString();
			
			int Amnt = Integer.parseInt(Amount);
			
			Date dt = new Date();
					String day="",month="",year="",hrs="",mins="",sec="";
					 if(dt.getDate()<10)
						 day+="0"+dt.getDate();
					 else
						 day+=dt.getDate();
					 int m=dt.getMonth()+1;
				 
					 if(m<10)
						 month+="0"+m;
					 else
						 month+=m;
					 int y=dt.getYear()+1900;
					 year+=y;
				
					String CompDate = day+"."+month+"."+year ;
					
			
			query = "select * from cnt where cnt.name='Draft'";
						rset=stmt.executeQuery(query);
						rset.next();
			
			int draftnum=rset.getInt(2)+1 ;
						
			query = "update cnt set cnt.cnt1=cnt.cnt1+1 where cnt.name='Draft'"; 
			stmt.executeUpdate(query);
			
			
			query = "select * from branch where branch.branchname='"+Branch+"'";
			rset=stmt.executeQuery(query);
			
			rset.next();
			int Bcode = rset.getInt(1);
			
			
			
			//draft(draftnum int,favour varchar(30),bcode int,amt float,acno bigint,stat varchar(10))
								
			if(medium.equals("By Cash"))
			{
				float fee=Amnt/1000;
				fee=2*fee;
				fee=(float)(1.5*fee);
				float ded=Amnt+fee;
				query = "insert into draft values("+draftnum+",'"+InFavourOf+"',"+Bcode+","+Amnt+",0,'"+"Issued"+"')";
				stmt.executeUpdate(query);
				
				pw.println("<td valign=\"top\" width=580><font size=3>Amount Payable = "+ded+"<br><B>Draft can be Issued<br>Draft Number="+draftnum+"</B></FONT></td>");
			}
			else
			{
				query = "select * from cnt where cnt.name='TransID'";
				rset=stmt.executeQuery(query);
		
				rset.next();
				int TransID;
				TransID=rset.getInt(2)+1 ;
		query = "update cnt set cnt.cnt1=cnt.cnt1+1 where cnt.name='TransID'"; 
		stmt.executeUpdate(query);	
				query = "select * from account where account.accno="+AccNo+"";
			
				rset=stmt.executeQuery(query);
				
				rset.next();
				float fee=Amnt/1000;
				fee=(float)(1.5*fee);
				float ded=Amnt+fee;
				String UserID = rset.getString(1) ;
					if(rset.getFloat(6)>(Amnt+fee+500))
					{//debit debit(user varchar(15),damount float,dod date,idtpe varchar(20),idno varchar(20),transid1 varchar(20),empid varchar(15)) 
						query = "insert into debit values('"+UserID+"',"+Amnt+".00,'"+CompDate+"','To draft "+draftnum+"','"+IdNum+"','"+TransID+"','"+per+"')";
				
						stmt.executeUpdate(query);
				
						query = "insert into debit values('"+UserID+"',"+fee+",'"+CompDate+"','To draft Commission"+"','"+"Bank"+"','"+TransID+"','"+per+"')";
				
						stmt.executeUpdate(query);
				
						query = "update account set account.balance=account.balance-"+ded+" where account.user='"+UserID+"'";
				
						stmt.executeUpdate(query);
				
						query = "insert into draft values("+draftnum+",'"+InFavourOf+"',"+Bcode+","+Amnt+","+AccNo+",'"+"Issued"+"')";
				
						stmt.executeUpdate(query); 
						
						pw.println("<td valign=\"top\" width=580><font size=3>Amount Payable = "+ded+"<br><B>Draft can be Issued<br>Transaction ID:"+TransID+"<br>Draft Number: "+draftnum+"</B></FONT></td>");
					
					}
					else
					{//cancel transaction due to less account balance
						pw.println("<td valign=\"top\" width=580><font size=3><B>Draft cannot be issued due to insufficient balance!</B></FONT></td>");
					}
					
			}
			
			
				
				
				
				
				if(perarray[0]=='e')
				pw.println(myIndex.section6+"Employee"+myIndex.section7+date1+myIndex.section8+time1+myIndex.section9);
				else if(perarray[0]=='a')
					pw.println(myIndex.section6+"Administrator"+myIndex.section7+date1+myIndex.section8+time1+myIndex.section9);
			}
			else
				pw.println("<html><title>Error Page!</title><body><font color=\"red\" size=3><B>Error! This page is not meant for you</font></B></body></html>");

			 }
			
			 
			catch(Exception ex)
			{
				
			}
		}
	}  	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter pw = response.getWriter();
		Index myIndex = new Index();
		ServletContext ctx=this.getServletContext();
		String login =ctx.getAttribute("loginsession").toString();
		DataSource dsource = null;
		Statement stmt = null;
		Statement stmt1 = null;
		Connection conn = null;
		ResultSet rset = null;
		ResultSet rset1 = null;
		
		if(!login.equals("T"))
			pw.println(myIndex.section1+myIndex.section2+myIndex.section3+myIndex.section4);
		else
		{
			String per="";
			String date1="",time1="";
			String query="";
			try
			 {
			 InitialContext context = new InitialContext ();
			 dsource = (DataSource) context.lookup("java:comp/env/jdbc/MyDataSource");
			 conn = dsource.getConnection();
			 stmt=conn.createStatement();
			 stmt1=conn.createStatement();
			 per=ctx.getAttribute("UserID").toString();
			 ctx.setAttribute("loginsession", "T");
			 ctx.setAttribute("UserID",per);
			 query="select * from logininfo where logininfo.user='"+per+"'";
			 rset1=stmt.executeQuery(query);
			 
			 while(rset1.next())
			 {
				date1=rset1.getString(2);
				time1=rset1.getString(3);
				 break;
			 }
			 
			 float balance=0;
			char[] perarray = new char[per.length()];
			perarray=per.toCharArray();
			if(perarray[0]!='c')
			{
			if(perarray[0]=='e')
			
				//employee
				pw.println(myIndex.section1+myIndex.employee);
			else if(perarray[0]=='a')
				pw.println(myIndex.section1+myIndex.admin);
			String EmpUid=per;
			
			String InFavourOf = ctx.getAttribute("InFavourOf").toString();
			String Branch = ctx.getAttribute("Branch").toString();
			String Amount = ctx.getAttribute("Amnt").toString();
			
			String medium =ctx.getAttribute("medium").toString();
			
			String AccNo = ctx.getAttribute("AccNo").toString();
			
			String IdNum = ctx.getAttribute("IdNum").toString();
			
			int Amnt = Integer.parseInt(Amount);
			
			Date dt = new Date();
					String day="",month="",year="",hrs="",mins="",sec="";
					 if(dt.getDate()<10)
						 day+="0"+dt.getDate();
					 else
						 day+=dt.getDate();
					 int m=dt.getMonth()+1;
				 
					 if(m<10)
						 month+="0"+m;
					 else
						 month+=m;
					 int y=dt.getYear()+1900;
					 year+=y;
				
					String CompDate = day+"."+month+"."+year ;
					
			
			query = "select * from cnt where cnt.name='Draft'";
						rset=stmt.executeQuery(query);
						rset.next();
			
			int draftnum=rset.getInt(2)+1 ;
						
			query = "update cnt set cnt.cnt1=cnt.cnt1+1 where cnt.name='Draft'"; 
			stmt.executeUpdate(query);
			
			
			query = "select * from branch where branch.branchname='"+Branch+"'";
			rset=stmt.executeQuery(query);
			
			rset.next();
			int Bcode = rset.getInt(1);
			
			
			
			//draft(draftnum int,favour varchar(30),bcode int,amt float,acno bigint,stat varchar(10))
								
			if(medium.equals("By Cash"))
			{
				float fee=Amnt/1000;
				fee=2*fee;
				fee=(float)(1.5*fee);
				float ded=Amnt+fee;
				query = "insert into draft values("+draftnum+",'"+InFavourOf+"',"+Bcode+","+Amnt+",0,'"+"Issued"+"')";
				stmt.executeUpdate(query);
				
				pw.println("<td valign=\"top\" width=580><font size=3>Amount Payable = "+ded+"<br><B>Draft can be Issued<br>Draft Number="+draftnum+"</B></FONT></td>");
			}
			else
			{
				query = "select * from cnt where cnt.name='TransID'";
				rset=stmt.executeQuery(query);
		
				rset.next();
				int TransID;
				TransID=rset.getInt(2)+1 ;
		query = "update cnt set cnt.cnt1=cnt.cnt1+1 where cnt.name='TransID'"; 
		stmt.executeUpdate(query);	
				query = "select * from account where account.accno="+AccNo+"";
			
				rset=stmt.executeQuery(query);
				
				rset.next();
				float fee=Amnt/1000;
				fee=(float)(1.5*fee);
				float ded=Amnt+fee;
				String UserID = rset.getString(1) ;
					if(rset.getFloat(6)>(Amnt+fee+500))
					{//debit debit(user varchar(15),damount float,dod date,idtpe varchar(20),idno varchar(20),transid1 varchar(20),empid varchar(15)) 
						query = "insert into debit values('"+UserID+"',"+Amnt+".00,'"+CompDate+"','To draft "+draftnum+"','"+IdNum+"','"+TransID+"','"+per+"')";
				
						stmt.executeUpdate(query);
				
						query = "insert into debit values('"+UserID+"',"+fee+",'"+CompDate+"','To draft Commission"+"','"+"Bank"+"','"+TransID+"','"+per+"')";
				
						stmt.executeUpdate(query);
				
						query = "update account set account.balance=account.balance-"+ded+" where account.user='"+UserID+"'";
				
						stmt.executeUpdate(query);
				
						query = "insert into draft values("+draftnum+",'"+InFavourOf+"',"+Bcode+","+Amnt+","+AccNo+",'"+"Issued"+"')";
				
						stmt.executeUpdate(query); 
						
						pw.println("<td valign=\"top\" width=580><font size=3>Amount Payable = "+ded+"<br><B>Draft can be Issued<br>Transaction ID:"+TransID+"<br>Draft Number: "+draftnum+"</B></FONT></td>");
					
					}
					else
					{//cancel transaction due to less account balance
						pw.println("<td valign=\"top\" width=580><font size=3><B>Draft cannot be issued due to insufficient balance!</B></FONT></td>");
					}
					
			}
			
			
				
				
				
				
				if(perarray[0]=='e')
				pw.println(myIndex.section6+"Employee"+myIndex.section7+date1+myIndex.section8+time1+myIndex.section9);
				else if(perarray[0]=='a')
					pw.println(myIndex.section6+"Administrator"+myIndex.section7+date1+myIndex.section8+time1+myIndex.section9);
			}
			else
				pw.println("<html><title>Error Page!</title><body><font color=\"red\" size=3><B>Error! This page is not meant for you</font></B></body></html>");

			 }
			
			 
			catch(Exception ex)
			{
				
			}
		}
	}   	  	    
}