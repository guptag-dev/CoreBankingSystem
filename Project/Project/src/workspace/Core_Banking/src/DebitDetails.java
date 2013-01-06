

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
 * Servlet implementation class for Servlet: DebitDetails
 *
 */
 public class DebitDetails extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public DebitDetails() {
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
			if(perarray[0]=='c')
			{
				
				pw.println(myIndex.section1+myIndex.customer);
				pw.println("<td valign=\"top\" width=580><b> <u>View Debit Transactions </u></b> <br> <br><br><br>");
				query =" select * from debit where debit.user='"+per+"' order by debit.dod" ;
				rset = stmt.executeQuery(query);
				pw.println("<table><tr><td width=120  style=\"border:1px #000000 solid\" bgcolor=#b5b5b5><b>Date</b></td><td width=120 style=\"border:1px #000000 solid\" bgcolor=#b5b5b5><b>Debit Amount</b></td><td width=200 style=\"border:1px #000000 solid\" bgcolor=#b5b5b5  ><b>Initials</b></td></tr>");
				while(rset.next())
				{
					
					pw.println("<tr> <td width=120 style=\"border:1px #000000 solid\" >"+rset.getDate(3)+"</td><td width=120 style=\"border:1px #000000 solid\" >"+rset.getFloat(2)+"</td><td width=200 style=\"border:1px #000000 solid\"  >"+rset.getString(4)+" by "+rset.getString(5)+"</td></tr>");
				}
				pw.println("</table><br></td>");
				pw.println(myIndex.section6+"Customer"+myIndex.section7+date+myIndex.section8+time+myIndex.section9);
				
			}
			else if(perarray[0]=='e')
			{
				//pw.println(myIndex.acpagescript+myIndex.employee+myIndex.section11+myIndex.section6+"Employee"+myIndex.section7+date+myIndex.section8+time+myIndex.section9);
				//employee
				pw.println(myIndex.section1+myIndex.employee);
				pw.println("<td valign=\"top\" width=580><b> <u>View Debit Transactions </u></b> <br> <br><br><br>");
				query =" select * from debit where debit.user='"+per+"' order by debit.dod" ;
				rset = stmt.executeQuery(query);
				pw.println("<table><tr><td width=120  style=\"border:1px #000000 solid\" bgcolor=#b5b5b5><b>Date</b></td><td width=120 style=\"border:1px #000000 solid\" bgcolor=#b5b5b5><b>Debit Amount</b></td><td width=200 style=\"border:1px #000000 solid\" bgcolor=#b5b5b5  ><b>Initials</b></td></tr>");
				while(rset.next())
				{
					
					pw.println("<tr> <td width=120 style=\"border:1px #000000 solid\" >"+rset.getDate(3)+"</td><td width=120 style=\"border:1px #000000 solid\" >"+rset.getFloat(2)+"</td><td width=200 style=\"border:1px #000000 solid\"  >"+rset.getString(4)+" by "+rset.getString(5)+"</td></tr>");
				}
				pw.println("</table><br></td>");
				pw.println(myIndex.section6+"Employee"+myIndex.section7+date+myIndex.section8+time+myIndex.section9);
				
			}
			else if(perarray[0]=='a')
			{
				//Administrator
				pw.println(myIndex.section1+myIndex.admin);
				pw.println("<td valign=\"top\" width=580><b> <u>View Debit Transactions </u></b> <br> <br><br><br>");
				query =" select * from debit where debit.user='"+per+"' order by debit.dod" ;
				rset = stmt.executeQuery(query);
				pw.println("<table><tr><td width=120  style=\"border:1px #000000 solid\" bgcolor=#b5b5b5><b>Date</b></td><td width=120 style=\"border:1px #000000 solid\" bgcolor=#b5b5b5><b>Debit Amount</b></td><td width=200 style=\"border:1px #000000 solid\" bgcolor=#b5b5b5  ><b>Initials</b></td></tr>");
				while(rset.next())
				{
					
					pw.println("<tr> <td width=120 style=\"border:1px #000000 solid\" >"+rset.getDate(3)+"</td><td width=120 style=\"border:1px #000000 solid\" >"+rset.getFloat(2)+"</td><td width=200 style=\"border:1px #000000 solid\"  >"+rset.getString(4)+" by "+rset.getString(5)+"</td></tr>");
				}
				pw.println("</table><br></td>");
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
			if(perarray[0]=='c')
			{
				
				pw.println(myIndex.section1+myIndex.customer);
				pw.println("<td valign=\"top\" width=580><b> <u>View Debit Transactions </u></b> <br> <br><br><br>");
				query =" select * from debit where debit.user='"+per+"' order by debit.dod" ;
				rset = stmt.executeQuery(query);
				pw.println("<table><tr><td width=120  style=\"border:1px #000000 solid\" bgcolor=#b5b5b5><b>Date</b></td><td width=120 style=\"border:1px #000000 solid\" bgcolor=#b5b5b5><b>Debit Amount</b></td><td width=200 style=\"border:1px #000000 solid\" bgcolor=#b5b5b5  ><b>Initials</b></td></tr>");
				while(rset.next())
				{
					
					pw.println("<tr> <td width=120 style=\"border:1px #000000 solid\" >"+rset.getDate(3)+"</td><td width=120 style=\"border:1px #000000 solid\" >"+rset.getFloat(2)+"</td><td width=200 style=\"border:1px #000000 solid\"  >"+rset.getString(4)+" by "+rset.getString(5)+"</td></tr>");
				}
				pw.println("</table><br></td>");
				pw.println(myIndex.section6+"Customer"+myIndex.section7+date+myIndex.section8+time+myIndex.section9);
				
			}
			else if(perarray[0]=='e')
			{
				//pw.println(myIndex.acpagescript+myIndex.employee+myIndex.section11+myIndex.section6+"Employee"+myIndex.section7+date+myIndex.section8+time+myIndex.section9);
				//employee
				pw.println(myIndex.section1+myIndex.employee);
				pw.println("<td valign=\"top\" width=580><b> <u>View Debit Transactions </u></b> <br> <br><br><br>");
				query =" select * from debit where debit.user='"+per+"' order by debit.dod" ;
				rset = stmt.executeQuery(query);
				pw.println("<table><tr><td width=120  style=\"border:1px #000000 solid\" bgcolor=#b5b5b5><b>Date</b></td><td width=120 style=\"border:1px #000000 solid\" bgcolor=#b5b5b5><b>Debit Amount</b></td><td width=200 style=\"border:1px #000000 solid\" bgcolor=#b5b5b5  ><b>Initials</b></td></tr>");
				while(rset.next())
				{
					
					pw.println("<tr> <td width=120 style=\"border:1px #000000 solid\" >"+rset.getDate(3)+"</td><td width=120 style=\"border:1px #000000 solid\" >"+rset.getFloat(2)+"</td><td width=200 style=\"border:1px #000000 solid\"  >"+rset.getString(4)+" by "+rset.getString(5)+"</td></tr>");
				}
				pw.println("</table><br></td>");
				pw.println(myIndex.section6+"Employee"+myIndex.section7+date+myIndex.section8+time+myIndex.section9);
				
			}
			else if(perarray[0]=='a')
			{
				//Administrator
				pw.println(myIndex.section1+myIndex.admin);
				pw.println("<td valign=\"top\" width=580><b> <u>View Debit Transactions </u></b> <br> <br><br><br>");
				query =" select * from debit where debit.user='"+per+"' order by debit.dod" ;
				rset = stmt.executeQuery(query);
				pw.println("<table><tr><td width=120  style=\"border:1px #000000 solid\" bgcolor=#b5b5b5><b>Date</b></td><td width=120 style=\"border:1px #000000 solid\" bgcolor=#b5b5b5><b>Debit Amount</b></td><td width=200 style=\"border:1px #000000 solid\" bgcolor=#b5b5b5  ><b>Initials</b></td></tr>");
				while(rset.next())
				{
					
					pw.println("<tr> <td width=120 style=\"border:1px #000000 solid\" >"+rset.getDate(3)+"</td><td width=120 style=\"border:1px #000000 solid\" >"+rset.getFloat(2)+"</td><td width=200 style=\"border:1px #000000 solid\"  >"+rset.getString(4)+" by "+rset.getString(5)+"</td></tr>");
				}
				pw.println("</table><br></td>");
				pw.println(myIndex.section6+"Administrator"+myIndex.section7+date+myIndex.section8+time+myIndex.section9);
				
			}
			 }
			catch(Exception ex)
			{
				
			}
		}
	}   	  	    
}