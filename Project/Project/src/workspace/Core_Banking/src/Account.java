

import java.io.*;

import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;

import javax.sql.*;

/**
 * Servlet implementation class for Servlet: Account
 *
 */
 public class Account extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public Account() {
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
		Connection conn = null;
		ResultSet rset = null;
		ResultSet rset1 = null;
		
		if(!login.equals("T"))
			pw.println(myIndex.section1+myIndex.section2+myIndex.section3+myIndex.section4);
		else
		{
			//Logged In
			String per="";
			String date="",time="";
			String query="";
			try
			 {
			 InitialContext context = new InitialContext ();
			 dsource = (DataSource) context.lookup("java:comp/env/jdbc/MyDataSource");
			 conn = dsource.getConnection();
			 stmt=conn.createStatement();
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
			 
			 
			char[] perarray = new char[per.length()];
			perarray=per.toCharArray();
			query="select * from account where account.user='"+per+"'";
			rset1=stmt.executeQuery(query);
			
				
				while(rset1.next())
				 {
					if(perarray[0]=='c')
						pw.println(myIndex.section1+myIndex.customer+"<td valign=\"top\" width=580><font size=2 >"+myIndex.section12+"<table><td valign=\"top\">Account Number:<br>Account Holders:<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 1.<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 2.<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 3.<br><br>User ID:<br>Account Type:<br>Account Opening Date:<br>Balance:<br></td><td valign=\"top\">");
					else if(perarray[0]=='e')
						pw.println(myIndex.section1+myIndex.employee+"<td valign=\"top\" width=580><font size=2 >"+myIndex.section12+"<table><td valign=\"top\">Account Number:<br>Account Holders:<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 1.<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 2.<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 3.<br><br>User ID:<br>Account Type:<br>Account Opening Date:<br>Balance:<br></td><td valign=\"top\">");
					else if(perarray[0]=='a')
						pw.println(myIndex.section1+myIndex.admin+"<td valign=\"top\" width=580><font size=2 >"+myIndex.section12+"<table><td valign=\"top\">Account Number:<br>Account Holders:<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 1.<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 2.<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 3.<br><br>User ID:<br>Account Type:<br>Account Opening Date:<br>Balance:<br></td><td valign=\"top\">");
					
					pw.println("<B>"+rset1.getLong(2)+"<br>");
					int i;
					String i1=rset1.getString(9);
					String actype = rset1.getString(4);
					float acbal=rset1.getFloat(6);
					int x=rset1.getInt(5);
					for(i=1;i<=x;i++)
					{
						query="select * from holder where holder.user='"+per+"' and holder.holdnum="+i;
						rset = stmt.executeQuery(query);
						while(rset.next())
						{
							
							pw.println("<br>"+rset.getString(3));
							break;
						}
					}
					while(i<=3)
					{
						pw.println("<br>-----");
						i++;
					}
					pw.println("<br><br>"+per+"<br>"+actype+"<br>"+i1+"<br>"+acbal+"</B></td></table>");
					if(perarray[0]!='c')
						pw.println("<hr><br><font size=3><B><U>Job</B></U><br>1. <a href=\"CustAccount\">Create Customer Account</a><br>2. <a href=\"EmpAccount\">Create Employee Account</a><br>3. <a href=\"Ledger\">View General Ledger Entry</a><br>4. <a href=\"Close\">Close Account</a></font>");
					
					pw.println("</td>"+myIndex.section6);
					if(perarray[0]=='c')
						pw.println("Customer");
					else if(perarray[0]=='e')
						pw.println("Employee");
					else if(perarray[0]=='a')
						pw.println("Administrator");
					pw.println(myIndex.section7+date+myIndex.section8+time+myIndex.section9);
							//+""+"</td></table>");
					 break;
				 }
				
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
		Connection conn = null;
		ResultSet rset = null;
		ResultSet rset1 = null;
		
		if(!login.equals("T"))
			pw.println(myIndex.section1+myIndex.section2+myIndex.section3+myIndex.section4);
		else
		{
			//Logged In
			String per="";
			String date="",time="";
			String query="";
			try
			 {
			 InitialContext context = new InitialContext ();
			 dsource = (DataSource) context.lookup("java:comp/env/jdbc/MyDataSource");
			 conn = dsource.getConnection();
			 stmt=conn.createStatement();
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
			 
			 
			char[] perarray = new char[per.length()];
			perarray=per.toCharArray();
			query="select * from account where account.user='"+per+"'";
			rset1=stmt.executeQuery(query);
			
				
				while(rset1.next())
				 {
					if(perarray[0]=='c')
						pw.println(myIndex.section1+myIndex.customer+"<td valign=\"top\" width=580><font size=2 >"+myIndex.section12+"<table><td valign=\"top\">Account Number:<br>Account Holders:<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 1.<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 2.<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 3.<br><br>User ID:<br>Account Type:<br>Account Opening Date:<br>Balance:<br></td><td valign=\"top\">");
					else if(perarray[0]=='e')
						pw.println(myIndex.section1+myIndex.employee+"<td valign=\"top\" width=580><font size=2 >"+myIndex.section12+"<table><td valign=\"top\">Account Number:<br>Account Holders:<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 1.<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 2.<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 3.<br><br>User ID:<br>Account Type:<br>Account Opening Date:<br>Balance:<br></td><td valign=\"top\">");
					else if(perarray[0]=='a')
						pw.println(myIndex.section1+myIndex.admin+"<td valign=\"top\" width=580><font size=2 >"+myIndex.section12+"<table><td valign=\"top\">Account Number:<br>Account Holders:<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 1.<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 2.<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 3.<br><br>User ID:<br>Account Type:<br>Account Opening Date:<br>Balance:<br></td><td valign=\"top\">");
					
					pw.println("<B>"+rset1.getLong(2)+"<br>");
					int i;
					String i1=rset1.getString(9);
					String actype = rset1.getString(4);
					float acbal=rset1.getFloat(6);
					int x=rset1.getInt(5);
					for(i=1;i<=x;i++)
					{
						query="select * from holder where holder.user='"+per+"' and holder.holdnum="+i;
						rset = stmt.executeQuery(query);
						while(rset.next())
						{
							
							pw.println("<br>"+rset.getString(3));
							break;
						}
					}
					while(i<=3)
					{
						pw.println("<br>-----");
						i++;
					}
					pw.println("<br><br>"+per+"<br>"+actype+"<br>"+i1+"<br>"+acbal+"</B></td></table>");
					if(perarray[0]!='c')
						pw.println("<hr><br><font size=3><B><U>Job</B></U><br>1. <a href=\"CustAccount\">Create Customer Account</a><br>2. <a href=\"EmpAccount\">Create Employee Account</a><br>3. <a href=\"Ledger\">View General Ledger Entry</a><br>4. <a href=\"Close\">Close Account</a></font>");
					
					pw.println("</td>"+myIndex.section6);
					if(perarray[0]=='c')
						pw.println("Customer");
					else if(perarray[0]=='e')
						pw.println("Employee");
					else if(perarray[0]=='a')
						pw.println("Administrator");
					pw.println(myIndex.section7+date+myIndex.section8+time+myIndex.section9);
							//+""+"</td></table>");
					 break;
				 }
				
				 }
			catch(Exception ex)
			{
				
			}
		}
	}   	  	    
}