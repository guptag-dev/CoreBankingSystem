

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
 * Servlet implementation class for Servlet: AInbox
 *
 */
 public class AInbox extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public AInbox() {
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
			String per="";
			String date1="",time1="";
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
				date1=rset1.getString(2);
				time1=rset1.getString(3);
				 break;
			 }
			 
			 float balance=0;
			char[] perarray = new char[per.length()];
			perarray=per.toCharArray();
			if(perarray[0]=='a')
			{
				pw.println(myIndex.section1+myIndex.admin);
								
				query = "select * from ainbox";
				   rset=stmt.executeQuery(query);
				   int msgnum=0;
				   pw.println("<td valign=\"top\" width=580>Administrator's Inbox<table> <tr> <td style=\"border:1px #000000 solid\" bgcolor=#b5b5b5> S.No.</td> <td style=\"border:1px #000000 solid\" bgcolor=#b5b5b5> From</td> <td style=\"border:1px #000000 solid\" bgcolor=#b5b5b5> Message</td> </tr>");

				   while(rset.next())
				   {
				   	msgnum++;
				   	String from=rset.getString(1);
				   	String message=rset.getString(2);
				   	pw.println("<tr><td style=\"border:1px #000000 solid\">"+msgnum+"</td><td style=\"border:1px #000000 solid\">"+from+"</td><td style=\"border:1px #000000 solid\">"+message+"</td></tr>");
				   }
				   pw.println("</table></td>");
			
				
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
			if(perarray[0]=='a')
			{
				pw.println(myIndex.section1+myIndex.admin);
								
				query = "select * from ainbox";
				   rset=stmt.executeQuery(query);
				   int msgnum=0;
				   pw.println("<td valign=\"top\" width=580>Administrator's Inbox<table> <tr> <td style=\"border:1px #000000 solid\" bgcolor=#b5b5b5> S.No.</td> <td style=\"border:1px #000000 solid\" bgcolor=#b5b5b5> From</td> <td style=\"border:1px #000000 solid\" bgcolor=#b5b5b5> Message</td> </tr>");

				   while(rset.next())
				   {
				   	msgnum++;
				   	String from=rset.getString(1);
				   	String message=rset.getString(2);
				   	pw.println("<tr><td style=\"border:1px #000000 solid\">"+msgnum+"</td><td style=\"border:1px #000000 solid\">"+from+"</td><td style=\"border:1px #000000 solid\">"+message+"</td></tr>");
				   }
				   pw.println("</table></td>");
			
				
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