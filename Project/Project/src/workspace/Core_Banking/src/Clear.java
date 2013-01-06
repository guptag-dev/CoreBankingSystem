

import java.io.File;
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
 * Servlet implementation class for Servlet: Clear
 *
 */
 public class Clear extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public Clear() {
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
			String date="",time="";
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
				date=rset1.getString(2);
				time=rset1.getString(3);
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
			
			String acnum=ctx.getAttribute("acnum").toString();
				//String acnum = request.getParameter("acnum");
				query="select * from account where account.accno="+acnum;
				rset=stmt.executeQuery(query);
				rset.next();
				String userid=rset.getString(1);
				int totalHolders=rset.getShort(5);
				
				String EmpID=per;
				String bank = ctx.getAttribute("bank").toString();
				String Chequeno = ctx.getAttribute("Chequeno").toString();
				String ACno = ctx.getAttribute("acnum").toString();
				String Operation = ctx.getAttribute("Operation").toString();
				String Amount = ctx.getAttribute("Amount").toString();
				String UserID =ctx.getAttribute("User").toString();
				
				
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
					
					
					query = "select * from cnt where cnt.name='TransID'";
					rset=stmt.executeQuery(query);
					rset.next();
					int TransID;
					TransID=rset.getInt(2)+1 ;
				query = "select * from status where status.chqnum='"+Chequeno+"'";
				rset1=stmt.executeQuery(query);	
				boolean chk=false;
				if(rset1.next())
				{
			if(rset1.getString(2).equals("Cancelled"))
			{//cheque has been cancelled
			 // print message
				chk=true;
				pw.println("<td valign=\"top\" width=580><font size=3><B>This Cheque has been Cancelled!</B></FONT></td>");
			}
				}
			if(!chk)
			{
				if(Operation.equals("Credit"))
				{//credit operation selected
					//account update
					query="select * from status where status.chqnum='"+Chequeno+"'";
					rset=stmt.executeQuery(query);
					rset.next();
					if(rset.getString(2).equals("Cleared"))
					{
					query = "update account set account.balance=account.balance+"+Amount+" where account.user='"+UserID+"'";
					stmt.executeUpdate(query);
					//credit(user varchar(15),amount float,dot date,initials varchar(20),transid varchar(20),empid varchar(15))
					
						
					query = "update cnt set cnt.cnt1=cnt.cnt1+1 where cnt.name='TransID'"; 
					stmt.executeUpdate(query);
					query = "insert into credit values('"+UserID+"',"+Amount+",'"+CompDate+"','By Clearing"+Chequeno+bank+"','"+TransID+"','"+EmpID+"')";
					stmt.executeUpdate(query);
					query = "update status set status.stat='Paid' where status.chqnum='"+Chequeno+"'";
					stmt.executeUpdate(query);
					pw.println("<td valign=\"top\" width=580><font size=3><B>This Cheque can be Paid!<br>Transaction ID: "+TransID+"</B></FONT></td>");
					}
					else
					{
						pw.println("<td valign=\"top\" width=580><font size=3><B>This Cheque cannot be Paid!</B></FONT></td>");
					}
							
				}
				else
				{
					
					query = "select * from account where account.user='"+UserID+"'";
					rset=stmt.executeQuery(query);
					rset.next();
					if((int)(rset.getFloat(6))>(Integer.parseInt(Amount)+500))
					{//debit debit(user varchar(15),damount float,dod date,idtpe varchar(20),idno varchar(20),transid1 varchar(20),empid varchar(15)) 
					
			query = "update cnt set cnt.cnt1=cnt.cnt1+1 where cnt.name='TransID'"; 
					stmt.executeUpdate(query);
						query = "insert into debit values('"+UserID+"',"+Amount+",'"+CompDate+"','To cheque"+Chequeno+bank+"','"+Chequeno+"','"+TransID+"','"+EmpID+"')";
						stmt.executeUpdate(query);
						query = "update account set account.balance=account.balance-"+Amount+",account.chqused=account.chqused+1 where account.user='"+UserID+"'";
						stmt.executeUpdate(query);
						query = "insert into status values('"+Chequeno+"','Cleared')";
						stmt.executeUpdate(query);
					
						pw.println("<td valign=\"top\" width=580><font size=3><B>This Cheque has been Cleared!<br>Transaction ID: "+TransID+"</B></FONT></td>");
					}
					else
					{//cheque bounce
						query = "insert into status values('"+Chequeno+"','Bounced')";
						stmt.executeUpdate(query);
						//check bounce due to less account balance
						//warning
						pw.println("<td valign=\"top\" width=580><font size=3><B>This Cheque has been Bounced!</B></FONT></td>");
					}
				}
			}
				
				
				
				
				if(perarray[0]=='e')
				pw.println(myIndex.section6+"Employee"+myIndex.section7+date+myIndex.section8+time+myIndex.section9);
				else if(perarray[0]=='a')
					pw.println(myIndex.section6+"Employee"+myIndex.section7+date+myIndex.section8+time+myIndex.section9);
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
			String date="",time="";
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
				date=rset1.getString(2);
				time=rset1.getString(3);
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
			
			String acnum=ctx.getAttribute("acnum").toString();
				//String acnum = request.getParameter("acnum");
				query="select * from account where account.accno="+acnum;
				rset=stmt.executeQuery(query);
				rset.next();
				String userid=rset.getString(1);
				int totalHolders=rset.getShort(5);
				
				String EmpID=per;
				String bank = ctx.getAttribute("bank").toString();
				String Chequeno = ctx.getAttribute("Chequeno").toString();
				String ACno = ctx.getAttribute("acnum").toString();
				String Operation = ctx.getAttribute("Operation").toString();
				String Amount = ctx.getAttribute("Amount").toString();
				String UserID =ctx.getAttribute("User").toString();
				
				
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
					
					
					query = "select * from cnt where cnt.name='TransID'";
					rset=stmt.executeQuery(query);
					rset.next();
					int TransID;
					TransID=rset.getInt(2)+1 ;
				query = "select * from status where status.chqnum='"+Chequeno+"'";
				rset1=stmt.executeQuery(query);	
				boolean chk=false;
				if(rset1.next())
				{
			if(rset1.getString(2).equals("Cancelled"))
			{//cheque has been cancelled
			 // print message
				chk=true;
				pw.println("<td valign=\"top\" width=580><font size=3><B>This Cheque has been Cancelled!</B></FONT></td>");
			}
				}
			if(!chk)
			{
				if(Operation.equals("Credit"))
				{//credit operation selected
					//account update
					query="select * from status where status.chqnum='"+Chequeno+"'";
					rset=stmt.executeQuery(query);
					rset.next();
					if(rset.getString(2).equals("Cleared"))
					{
					query = "update account set account.balance=account.balance+"+Amount+" where account.user='"+UserID+"'";
					stmt.executeUpdate(query);
					//credit(user varchar(15),amount float,dot date,initials varchar(20),transid varchar(20),empid varchar(15))
					
						
					query = "update cnt set cnt.cnt1=cnt.cnt1+1 where cnt.name='TransID'"; 
					stmt.executeUpdate(query);
					query = "insert into credit values('"+UserID+"',"+Amount+",'"+CompDate+"','By Clearing"+Chequeno+bank+"','"+TransID+"','"+EmpID+"')";
					stmt.executeUpdate(query);
					query = "update status set status.stat='Paid' where status.chqnum='"+Chequeno+"'";
					stmt.executeUpdate(query);
					pw.println("<td valign=\"top\" width=580><font size=3><B>This Cheque can be Paid!<br>Transaction ID: "+TransID+"</B></FONT></td>");
					}
					else
					{
						pw.println("<td valign=\"top\" width=580><font size=3><B>This Cheque cannot be Paid!</B></FONT></td>");
					}
							
				}
				else
				{
					
					query = "select * from account where account.user='"+UserID+"'";
					rset=stmt.executeQuery(query);
					rset.next();
					if((int)(rset.getFloat(6))>(Integer.parseInt(Amount)+500))
					{//debit debit(user varchar(15),damount float,dod date,idtpe varchar(20),idno varchar(20),transid1 varchar(20),empid varchar(15)) 
					
			query = "update cnt set cnt.cnt1=cnt.cnt1+1 where cnt.name='TransID'"; 
					stmt.executeUpdate(query);
						query = "insert into debit values('"+UserID+"',"+Amount+",'"+CompDate+"','To cheque"+Chequeno+bank+"','"+Chequeno+"','"+TransID+"','"+EmpID+"')";
						stmt.executeUpdate(query);
						query = "update account set account.balance=account.balance-"+Amount+",account.chqused=account.chqused+1 where account.user='"+UserID+"'";
						stmt.executeUpdate(query);
						query = "insert into status values('"+Chequeno+"','Cleared')";
						stmt.executeUpdate(query);
					
						pw.println("<td valign=\"top\" width=580><font size=3><B>This Cheque has been Cleared!<br>Transaction ID: "+TransID+"</B></FONT></td>");
					}
					else
					{//cheque bounce
						query = "insert into status values('"+Chequeno+"','Bounced')";
						stmt.executeUpdate(query);
						//check bounce due to less account balance
						//warning
						pw.println("<td valign=\"top\" width=580><font size=3><B>This Cheque has been Bounced!</B></FONT></td>");
					}
				}
			}
				
				
				
				
				if(perarray[0]=='e')
				pw.println(myIndex.section6+"Employee"+myIndex.section7+date+myIndex.section8+time+myIndex.section9);
				else if(perarray[0]=='a')
					pw.println(myIndex.section6+"Employee"+myIndex.section7+date+myIndex.section8+time+myIndex.section9);
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