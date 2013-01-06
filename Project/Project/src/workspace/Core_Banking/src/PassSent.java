

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.*;


import javax.sql.*;
import javax.naming.InitialContext;
/**
 * Servlet implementation class for Servlet: PassSent
 *
 */
 public class PassSent extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public PassSent() {
		super();
	}   	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String acno=request.getParameter("acnum");
		String userid="",pass="";
		String idproof = request.getParameter("idp");
		DataSource dsource = null;
		Statement stmt = null;
		Connection conn = null;
		ResultSet rset = null;
		ResultSet rset1 = null;
		PrintWriter pw=response.getWriter();
		Index myIndex = new Index();
		try
		 {
		 InitialContext context = new InitialContext ();
		 dsource = (DataSource) context.lookup("java:comp/env/jdbc/MyDataSource");
		 conn = dsource.getConnection();
		 stmt=conn.createStatement();
		 String query="select * from account full outer join holder on account.user=holder.user where accno="+acno+" and idnum='"+idproof+"'";
		 rset = stmt.executeQuery(query);		
		 boolean enter=false;
		 while(rset.next())
		 {
			 enter=true;
			 userid=rset.getString(1);
			 query="select * from login where login.user='"+userid+"'";
			 rset1 = stmt.executeQuery(query);
			 while(rset1.next())
			 {
				 pass=rset1.getString(2);
				 break;
			 }
			 ServletContext ctx=this.getServletContext();
			 ctx.setAttribute("loginsession", "F");
			 String add="<B><font color=\"red\">Forgot Password!</font></B><br>Your User-ID is:"+userid+"<br>and password is:"+pass+".<br>Kindly log in and change your password.</TD></TR></table>";
			 pw.println(myIndex.section1+myIndex.section2+add+myIndex.section4);
			 break;
		 }
		 if(!enter)
		 {
			 pw.println(myIndex.section1+myIndex.section2+"<B><font color=\"red\">Invalid Information!</font></B>"+myIndex.section3+myIndex.section4);
		 }
		 }
		catch(Exception ex)
		{
			
		}
		
	}  	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String acno=request.getParameter("acnum");
		String userid="",pass="";
		String idproof = request.getParameter("idp");
		DataSource dsource = null;
		Statement stmt = null;
		Connection conn = null;
		ResultSet rset = null;
		ResultSet rset1 = null;
		PrintWriter pw=response.getWriter();
		Index myIndex = new Index();
		try
		 {
		 InitialContext context = new InitialContext ();
		 dsource = (DataSource) context.lookup("java:comp/env/jdbc/MyDataSource");
		 conn = dsource.getConnection();
		 stmt=conn.createStatement();
		 String query="select * from account full outer join holder on account.user=holder.user where accno="+acno+" and idnum='"+idproof+"'";
		 rset = stmt.executeQuery(query);		
		 boolean enter=false;
		 while(rset.next())
		 {
			 enter=true;
			 userid=rset.getString(1);
			 query="select * from login where login.user='"+userid+"'";
			 rset1 = stmt.executeQuery(query);
			 while(rset1.next())
			 {
				 pass=rset1.getString(2);
				 break;
			 }
			 ServletContext ctx=this.getServletContext();
			 ctx.setAttribute("loginsession", "F");
			 String add="<B><font color=\"red\">Forgot Password!</font></B><br>Your User-ID is:"+userid+"<br>and password is:"+pass+".<br>Kindly log in and change your password.</TD></TR></table>";
			 pw.println(myIndex.section1+myIndex.section2+add+myIndex.section4);
			 break;
		 }
		 if(!enter)
		 {
			 pw.println(myIndex.section1+myIndex.section2+"<B><font color=\"red\">Invalid Information!</font></B>"+myIndex.section3+myIndex.section4);
		 }
		 }
		catch(Exception ex)
		{
			
		}
	}   	  	    
}