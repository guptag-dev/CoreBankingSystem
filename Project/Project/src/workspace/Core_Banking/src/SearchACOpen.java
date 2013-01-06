

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
 * Servlet implementation class for Servlet: SearchACOpen
 *
 */
 public class SearchACOpen extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public SearchACOpen() {
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
			String dd=request.getParameter("dd");
			String mm=request.getParameter("mm");
			String yy=request.getParameter("yy");
			String ddf=request.getParameter("ddf");
			String mmf=request.getParameter("mmf");
			String yyf=request.getParameter("yyf");
				String start = dd+"."+mm+"."+yy;
				String end = ddf+"."+mmf+"."+yyf;
				query = "select * from account where account.open>='"+start+"' and account.open<='"+end+"'";
				rset=stmt.executeQuery(query);
				pw.println("<td valign=\"top\" width=580><font size=3><U><B>Search Results</U></B></font><br><br><table><tr><td style=\"border:1px #000000 solid\" bgcolor=#b5b5b5>User ID</td><td style=\"border:1px #000000 solid\" bgcolor=#b5b5b5>Account Number</td><td style=\"border:1px #000000 solid\" bgcolor=#b5b5b5>Balance</td><td style=\"border:1px #000000 solid\" bgcolor=#b5b5b5>Account Opening Date</td></tr>");
				while(rset.next())
				{
					pw.println("<tr><td style=\"border:1px #000000 solid\">"+rset.getString(1)+"</td><td style=\"border:1px #000000 solid\">"+rset.getLong(2)+"</td><td style=\"border:1px #000000 solid\">"+rset.getFloat(6)+"</td><td style=\"border:1px #000000 solid\">"+rset.getDate(9)+"</td></tr>");
				}
				
				pw.println("</table></td>");
				if(perarray[0]=='e')
				pw.println(myIndex.section6+"Employee"+myIndex.section7+date+myIndex.section8+time+myIndex.section9);
				else if(perarray[0]=='a')
					pw.println(myIndex.section6+"Administrator"+myIndex.section7+date+myIndex.section8+time+myIndex.section9);
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
			String dd=request.getParameter("dd");
			String mm=request.getParameter("mm");
			String yy=request.getParameter("yy");
			String ddf=request.getParameter("ddf");
			String mmf=request.getParameter("mmf");
			String yyf=request.getParameter("yyf");
				String start = dd+"."+mm+"."+yy;
				String end = ddf+"."+mmf+"."+yyf;
				query = "select * from account where account.open>='"+start+"' and account.open<='"+end+"'";
				rset=stmt.executeQuery(query);
				pw.println("<td valign=\"top\" width=580><font size=3><U><B>Search Results</U></B></font><br><br><table><tr><td style=\"border:1px #000000 solid\" bgcolor=#b5b5b5>User ID</td><td style=\"border:1px #000000 solid\" bgcolor=#b5b5b5>Account Number</td><td style=\"border:1px #000000 solid\" bgcolor=#b5b5b5>Balance</td><td style=\"border:1px #000000 solid\" bgcolor=#b5b5b5>Account Opening Date</td></tr>");
				while(rset.next())
				{
					pw.println("<tr><td style=\"border:1px #000000 solid\">"+rset.getString(1)+"</td><td style=\"border:1px #000000 solid\">"+rset.getLong(2)+"</td><td style=\"border:1px #000000 solid\">"+rset.getFloat(6)+"</td><td style=\"border:1px #000000 solid\">"+rset.getDate(9)+"</td></tr>");
				}
				
				pw.println("</table></td>");
				if(perarray[0]=='e')
				pw.println(myIndex.section6+"Employee"+myIndex.section7+date+myIndex.section8+time+myIndex.section9);
				else if(perarray[0]=='a')
					pw.println(myIndex.section6+"Administrator"+myIndex.section7+date+myIndex.section8+time+myIndex.section9);
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