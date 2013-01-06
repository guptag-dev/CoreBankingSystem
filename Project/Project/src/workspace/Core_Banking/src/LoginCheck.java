

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.*;
import javax.sql.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Date;
import java.text.*;
/**
 * Servlet implementation class for Servlet: LoginCheck
 *
 */
 public class LoginCheck extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public LoginCheck() {
		super();
	}   	
	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		String userid = request.getParameter("UserID");
		String pass = request.getParameter("Pass");
		String loginstatus = request.getParameter("loginsession");
		DataSource dsource = null;
		Statement stmt = null;
		Connection conn = null;
		ResultSet rset = null;
		ResultSet rset1 = null;
		Index myIndex = new Index();
		try
		 {
		 InitialContext context = new InitialContext ();
		 dsource = (DataSource) context.lookup("java:comp/env/jdbc/MyDataSource");
		 conn = dsource.getConnection();
		 stmt=conn.createStatement();
		 String query="select * from login where login.user='"+userid+"' and login.passwd='"+pass+"'";
		 rset = stmt.executeQuery(query);		
		 boolean enter=false;
		 while(rset.next())
		 {
			 enter=true;
			 if(rset.getString(1).equals(userid))
			 {
				 ServletContext ctx=this.getServletContext();
				 ctx.setAttribute("loginsession", "T");
				 ctx.setAttribute("UserID",userid);
				 pw.println(myIndex.section1);
				 char[] userarray = new char[userid.length()];
				 userarray = userid.toCharArray();
				 String person="";
				 if(userarray[0]=='c')
				 {
					 pw.println(myIndex.customer);
					 person="Customer";
				 }
				 else if(userarray[0]=='e')
				 {
					 pw.println(myIndex.employee);
					 person="Employee";
				 }
				 else if(userarray[0]=='a')
				 {
					 pw.println(myIndex.admin);
					 person="Administrator";
				 }
				 query="select * from account where account.user='"+userid+"'";
				 rset1 = stmt.executeQuery(query);
				 int holders=1;
				 while(rset1.next())
				 {
					 holders=rset1.getInt(5);
					 break;
				 }
				 pw.println(myIndex.section5);
				 for(int i=1;i<=holders;i++)
				 {
					 query="select * from holder where holder.user='"+userid+"' and holder.holdnum="+i;
					 rset1 = stmt.executeQuery(query);
					 while(rset1.next())
					 {
						 pw.println(", "+rset1.getString(3));
					 }
				 }
				 pw.println(myIndex.section10+myIndex.section6+person+myIndex.section7);
				 query="select * from logininfo where logininfo.user='"+userid+"'";
				 rset1=stmt.executeQuery(query);
				 while(rset1.next())
				 {
					 pw.println(rset1.getString(2)+myIndex.section8+rset1.getString(3)+myIndex.section9);
					 break;
				 }
				 
				 Date dt = new Date();
				 String date="",month="",year="",hrs="",mins="",sec="";
				 if(dt.getDate()<10)
					 date+="0"+dt.getDate();
				 else
					 date+=dt.getDate();
				 int m=dt.getMonth()+1;
				 
				 if(m<10)
					 month+="0"+m;
				 else
					 month+=m;
				 int y=dt.getYear()+1900;
				 year+=y;
				 if(dt.getHours()<10)
					 hrs+="0"+dt.getHours();
				 else
					 hrs+=dt.getHours();
				 if(dt.getMinutes()<10)
					 mins+="0"+dt.getMinutes();
				 else
					 mins+=dt.getMinutes();
				 if(dt.getSeconds()<10)
					 sec+="0"+dt.getSeconds();
				 else
					 sec+=dt.getSeconds();
				 query="update logininfo set logininfo.llogindate='"+date+"."+month+"."+year+"',logininfo.llogintime='"+hrs+":"+mins+":"+sec+"' where logininfo.user='"+userid+"'";
				 
				 stmt.executeUpdate(query);
				 
			 }
			 
			 break;
		 }
		 if(!enter)
		 {
			 ServletContext ctx=this.getServletContext();
			 ctx.setAttribute("loginsession", "F");
			 pw.println(myIndex.section1+myIndex.section2+"<B><font color=\"red\">Invalid Login Attempt!</font></B>"+myIndex.section3+myIndex.section4);
		 }
		 }
		catch(Exception ex)
		{
			pw.println("<B>"+ex.getMessage());
		}
		
		pw.close();
	}   	  	    
		
	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		String userid = request.getParameter("UserID");
		String pass = request.getParameter("Pass");
		String loginstatus = request.getParameter("loginsession");
		DataSource dsource = null;
		Statement stmt = null;
		Connection conn = null;
		ResultSet rset = null;
		ResultSet rset1 = null;
		Index myIndex = new Index();
		try
		 {
		 InitialContext context = new InitialContext ();
		 dsource = (DataSource) context.lookup("java:comp/env/jdbc/MyDataSource");
		 conn = dsource.getConnection();
		 stmt=conn.createStatement();
		 String query="select * from login where login.user='"+userid+"' and login.passwd='"+pass+"'";
		 rset = stmt.executeQuery(query);		
		 boolean enter=false;
		 while(rset.next())
		 {
			 enter=true;
			 if(rset.getString(1).equals(userid))
			 {
				 ServletContext ctx=this.getServletContext();
				 ctx.setAttribute("loginsession", "T");
				 ctx.setAttribute("UserID",userid);
				 pw.println(myIndex.section1);
				 char[] userarray = new char[userid.length()];
				 userarray = userid.toCharArray();
				 String person="";
				 if(userarray[0]=='c')
				 {
					 pw.println(myIndex.customer);
					 person="Customer";
				 }
				 else if(userarray[0]=='e')
				 {
					 pw.println(myIndex.employee);
					 person="Employee";
				 }
				 else if(userarray[0]=='a')
				 {
					 pw.println(myIndex.admin);
					 person="Administrator";
				 }
				 query="select * from account where account.user='"+userid+"'";
				 rset1 = stmt.executeQuery(query);
				 int holders=1;
				 while(rset1.next())
				 {
					 holders=rset1.getInt(5);
					 break;
				 }
				 pw.println(myIndex.section5);
				 for(int i=1;i<=holders;i++)
				 {
					 query="select * from holder where holder.user='"+userid+"' and holder.holdnum="+i;
					 rset1 = stmt.executeQuery(query);
					 while(rset1.next())
					 {
						 pw.println(", "+rset1.getString(3));
					 }
				 }
				 pw.println(myIndex.section10+myIndex.section6+person+myIndex.section7);
				 query="select * from logininfo where logininfo.user='"+userid+"'";
				 rset1=stmt.executeQuery(query);
				 while(rset1.next())
				 {
					 pw.println(rset1.getString(2)+myIndex.section8+rset1.getString(3)+myIndex.section9);
					 break;
				 }
				 
				 Date dt = new Date();
				 String date="",month="",year="",hrs="",mins="",sec="";
				 if(dt.getDate()<10)
					 date+="0"+dt.getDate();
				 else
					 date+=dt.getDate();
				 int m=dt.getMonth()+1;
				 
				 if(m<10)
					 month+="0"+m;
				 else
					 month+=m;
				 int y=dt.getYear()+1900;
				 year+=y;
				 if(dt.getHours()<10)
					 hrs+="0"+dt.getHours();
				 else
					 hrs+=dt.getHours();
				 if(dt.getMinutes()<10)
					 mins+="0"+dt.getMinutes();
				 else
					 mins+=dt.getMinutes();
				 if(dt.getSeconds()<10)
					 sec+="0"+dt.getSeconds();
				 else
					 sec+=dt.getSeconds();
				 query="update logininfo set logininfo.llogindate='"+date+"."+month+"."+year+"',logininfo.llogintime='"+hrs+":"+mins+":"+sec+"' where logininfo.user='"+userid+"'";
				 
				 stmt.executeUpdate(query);
				 
			 }
			 
			 break;
		 }
		 if(!enter)
		 {
			 ServletContext ctx=this.getServletContext();
			 ctx.setAttribute("loginsession", "F");
			 pw.println(myIndex.section1+myIndex.section2+"<B><font color=\"red\">Invalid Login Attempt!</font></B>"+myIndex.section3+myIndex.section4);
		 }
		 }
		catch(Exception ex)
		{
			pw.println("<B>"+ex.getMessage());
		}
		
		pw.close();
	}   	  	    
}