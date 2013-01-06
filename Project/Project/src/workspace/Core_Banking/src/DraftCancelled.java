

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
/**
 * Servlet implementation class for Servlet: DraftCancelled
 *
 */
 public class DraftCancelled extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public DraftCancelled() {
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
			String Draftnum = request.getParameter("Chequeno");
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
			
			query = "select * from draft where draft.draftnum="+Draftnum ;
				rset=stmt.executeQuery(query);
			rset.next();
			
			if(rset.getString(6).equals("Paid"))
			{
			pw.println("<td valign=\"top\" width=580>"+"Draft has been paid so it can't be cancelled"+"</td>");	
			}
			else
			{
				if(rset.getString(6).equals("Issued"))
				{
						int acno=(int)rset.getLong(5);
						float amt=rset.getFloat(4);
						if(acno==0)
						{
							query = "update draft set draft.stat='Cancelled' where draft.draftnum="+Draftnum; 
							stmt.executeUpdate(query);
							
							pw.println("<td valign=\"top\" width=580>"+"Draft was issued by paying Cash. So, kindly match the user signature with that on the issue application form of this Draft.<br>After verification, Amount Payable="+amt+"</td>");
							
						}
						else
						{
							query="select * from account where account.accno="+acno;
							rset=stmt.executeQuery(query);
							rset.next();
							String user=rset.getString(1);
							query="update account set account.balance=account.balance+"+amt+" where account.accno="+acno;
							stmt.executeUpdate(query);
							query="select * from cnt where cnt.name='TransID'";
							rset=stmt.executeQuery(query);
							rset.next();
							int tid=rset.getInt(2)+1;
							query="update cnt set cnt.cnt1=cnt.cnt1+1 where cnt.name='TransID'";
							stmt.executeUpdate(query);
							//credit(user varchar(15),amount float,dot date,initials varchar(30),transid varchar(20),empid varchar(15))
							query="insert into credit values('"+user+"',"+amt+",'"+CompDate+"','By Draft Cancellation "+Draftnum+"','"+tid+"','"+per+"')";
							stmt.executeUpdate(query);
							query = "update draft set draft.stat='Cancelled' where draft.draftnum="+Draftnum; 
							stmt.executeUpdate(query);
							pw.println("<td valign=\"top\" width=580>"+"Draft has been cancelled!"+"</td>");
						}
				}
				else
				{
				query = "update draft set draft.stat='Cancelled' where draft.draftnum="+Draftnum; 
				stmt.executeUpdate(query);
				pw.println("<td valign=\"top\" width=580>"+"Draft has been cancelled!"+"</td>");
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
			String Draftnum = request.getParameter("Chequeno");
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
			
			query = "select * from draft where draft.draftnum="+Draftnum ;
				rset=stmt.executeQuery(query);
			rset.next();
			
			if(rset.getString(6).equals("Paid"))
			{
			pw.println("<td valign=\"top\" width=580>"+"Draft has been paid so it can't be cancelled"+"</td>");	
			}
			else
			{
				if(rset.getString(6).equals("Issued"))
				{
						int acno=(int)rset.getLong(5);
						float amt=rset.getFloat(4);
						if(acno==0)
						{
							query = "update draft set draft.stat='Cancelled' where draft.draftnum="+Draftnum; 
							stmt.executeUpdate(query);
							
							pw.println("<td valign=\"top\" width=580>"+"Draft was issued by paying Cash. So, kindly match the user signature with that on the issue application form of this Draft.<br>After verification, Amount Payable="+amt+"</td>");
							
						}
						else
						{
							query="select * from account where account.accno="+acno;
							rset=stmt.executeQuery(query);
							rset.next();
							String user=rset.getString(1);
							query="update account set account.balance=account.balance+"+amt+" where account.accno="+acno;
							stmt.executeUpdate(query);
							query="select * from cnt where cnt.name='TransID'";
							rset=stmt.executeQuery(query);
							rset.next();
							int tid=rset.getInt(2)+1;
							query="update cnt set cnt.cnt1=cnt.cnt1+1 where cnt.name='TransID'";
							stmt.executeUpdate(query);
							//credit(user varchar(15),amount float,dot date,initials varchar(30),transid varchar(20),empid varchar(15))
							query="insert into credit values('"+user+"',"+amt+",'"+CompDate+"','By Draft Cancellation "+Draftnum+"','"+tid+"','"+per+"')";
							stmt.executeUpdate(query);
							query = "update draft set draft.stat='Cancelled' where draft.draftnum="+Draftnum; 
							stmt.executeUpdate(query);
							pw.println("<td valign=\"top\" width=580>"+"Draft has been cancelled!"+"</td>");
						}
				}
				else
				{
				query = "update draft set draft.stat='Cancelled' where draft.draftnum="+Draftnum; 
				stmt.executeUpdate(query);
				pw.println("<td valign=\"top\" width=580>"+"Draft has been cancelled!"+"</td>");
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