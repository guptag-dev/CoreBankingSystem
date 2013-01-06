

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

/**
 * Servlet implementation class for Servlet: Pass
 *
 */
 public class Pass extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public Pass() {
		super();
	}   	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter pw = response.getWriter();
		Index myIndex = new Index();
		String user = request.getParameter("user");
		String cpass = request.getParameter("cpass");
		String npass = request.getParameter("npass");
		
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
			String per="";
			String date="",time="";
			String query="";
			String msg="";
			int mnum=0;
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
			
				//customer
				String comm="<td valign=\"top\" width=580><font size=2>";
				String comm1="<font color=\"red\">Invalid Information! Please Enter Again<br></font>";
				String comm2="Change Your Password:<br><br><table><td valign=\"top\">User ID:<br>Current Password:<br>New Password:</td><td valign=\"top\"><form action=\"Pass\" method=\"POST\"><input type=\"text\" name=\"user\"><br><input type=\"password\" name=\"cpass\"><br><input type=\"password\" name=\"npass\"><br><input type=\"Reset\" value=\"Reset\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=\"Submit\" value=\"Submit\"></form></td></table></td>";
				String comm3="<font color=\"red\">Password Changed Successfully!<br></font>";
				pw.println(myIndex.section1+myIndex.customer);
				if(user.equals(per))
				{
					query="select * from login where login.user='"+user+"' and login.passwd='"+cpass+"'";
					rset = stmt.executeQuery(query);
					boolean enter=false;
					 while(rset.next())
					 {
						 enter=true;
						 query="update login set login.passwd='"+npass+"' where login.user='"+user+"'";
						 stmt.executeUpdate(query);
						 pw.println(comm+comm3+comm2);
						 break;
					 }
					 if(!enter)
					 {
						 pw.println(comm+comm1+comm2);
					 }
				}
				else
					pw.println(comm+comm1+comm2);
				if(perarray[0]=='c')
				{
				pw.println(myIndex.section6+"Customer"+myIndex.section7+date+myIndex.section8+time+myIndex.section9);
			}
			else if(perarray[0]=='e')
			{
				//employee
				pw.println(myIndex.section6+"Employee"+myIndex.section7+date+myIndex.section8+time+myIndex.section9);
			}
			else if(perarray[0]=='a')
			{
				//admin
				pw.println(myIndex.section6+"Administrator"+myIndex.section7+date+myIndex.section8+time+myIndex.section9);
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
		String user = request.getParameter("user");
		String cpass = request.getParameter("cpass");
		String npass = request.getParameter("npass");
		
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
			String per="";
			String date="",time="";
			String query="";
			String msg="";
			int mnum=0;
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
			
				//customer
				String comm="<td valign=\"top\" width=580><font size=2>";
				String comm1="<font color=\"red\">Invalid Information! Please Enter Again<br></font>";
				String comm2="Change Your Password:<br><br><table><td valign=\"top\">User ID:<br>Current Password:<br>New Password:</td><td valign=\"top\"><form action=\"Pass\" method=\"POST\"><input type=\"text\" name=\"user\"><br><input type=\"password\" name=\"cpass\"><br><input type=\"password\" name=\"npass\"><br><input type=\"Reset\" value=\"Reset\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=\"Submit\" value=\"Submit\"></form></td></table></td>";
				String comm3="<font color=\"red\">Password Changed Successfully!<br></font>";
				pw.println(myIndex.section1+myIndex.customer);
				if(user.equals(per))
				{
					query="select * from login where login.user='"+user+"' and login.passwd='"+cpass+"'";
					rset = stmt.executeQuery(query);
					boolean enter=false;
					 while(rset.next())
					 {
						 enter=true;
						 query="update login set login.passwd='"+npass+"' where login.user='"+user+"'";
						 stmt.executeUpdate(query);
						 pw.println(comm+comm3+comm2);
						 break;
					 }
					 if(!enter)
					 {
						 pw.println(comm+comm1+comm2);
					 }
				}
				else
					pw.println(comm+comm1+comm2);
				if(perarray[0]=='c')
				{
				pw.println(myIndex.section6+"Customer"+myIndex.section7+date+myIndex.section8+time+myIndex.section9);
			}
			else if(perarray[0]=='e')
			{
				//employee
				pw.println(myIndex.section6+"Employee"+myIndex.section7+date+myIndex.section8+time+myIndex.section9);
			}
			else if(perarray[0]=='a')
			{
				//admin
				pw.println(myIndex.section6+"Administrator"+myIndex.section7+date+myIndex.section8+time+myIndex.section9);
			}
			 }
			catch(Exception ex)
			{
				
			}
		}
	}   	  	    
}