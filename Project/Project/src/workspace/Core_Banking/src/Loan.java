

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
 public class Loan extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public Loan() {
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
				pw.println(myIndex.section1+myIndex.customer);
			else if(perarray[0]=='e')
				pw.println(myIndex.section1+myIndex.employee);
			else if(perarray[0]=='a')
				pw.println(myIndex.section1+myIndex.admin);
			pw.println("<td valign=\"top\" width=580><b><u> Loan Details </u></b> <br> <br><br><br><table> <tr> <td bgcolor=#b5b5b5 style=\"border:1px #000000 solid\"><b>Loan Number</b></td> <td bgcolor=#b5b5b5 style=\"border:1px #000000 solid\"><b>Loan Type</b></td><td bgcolor=#b5b5b5 style=\"border:1px #000000 solid\"><b>Amount Sanction</b></td> <td bgcolor=#b5b5b5 style=\"border:1px #000000 solid\"><b>EMI</b></td> <td bgcolor=#b5b5b5 style=\"border:1px #000000 solid\"><b>Start Date</b></td> <td bgcolor=#b5b5b5 style=\"border:1px #000000 solid\"><b>End Date</b></td> <td bgcolor=#b5b5b5 style=\"border:1px #000000 solid\"><b>Overdue</b></td> </tr>");
			query= "select * from loan where loan.user ='"+per+"'";
			rset=stmt.executeQuery(query);
			boolean enter=false;
			while(rset.next())
			 {
				enter=true;
				pw.println("<tr>");
				String overdue="";
				int temp=(int)(rset.getFloat(10));
				if( temp == -1)
					overdue+="Not Started";
				else 
					overdue +=rset.getFloat(10);
				int mm = (rset.getDate(3).getMonth()) +1;
				int yr=rset.getDate(3).getYear() +rset.getInt(9)+1900;
				pw.println("<td style=\"border:1px #000000 solid\">"+rset.getString(2)+"<td style=\"border:1px #000000 solid\">"+rset.getString(8)+"</td>"+"<td style=\"border:1px #000000 solid\">"+rset.getFloat(4)+"</td>"+"<td style=\"border:1px #000000 solid\">"+rset.getFloat(11)+"</td>"+"<td style=\"border:1px #000000 solid\">"+rset.getDate(6)+"</td>"+"<td style=\"border:1px #000000 solid\">"+yr+"-"+mm+"-"+(rset.getDate(3).getDate())+"</td>"+"<td style=\"border:1px #000000 solid\">"+overdue+"</td> </tr>");
				
				pw.println("</tr>");
			}
			if(!enter)
			{
				pw.println("<br><br><font size=4 color=\"BLUE\"><B>You have hired no loans from our Bank</font></b>");
			}
			if(perarray[0]=='c')
			{
			pw.println("</table></td>");
			pw.println(myIndex.section6);
			
			pw.println("Customer");
			}
			else if(perarray[0]=='e')
			{
				pw.println("</table><br><br><hr><font size=3><B><U>Job</U></B></font><br><br>1) <a href=\"Sanction\">Sanction Loan</a><br>2) <a href=\"EMI\">EMI Payment</a></td>");
			}
			else if(perarray[0]=='a')
			{
				pw.println("</table><br><br><hr><font size=3><B><U>Job</U></B></font><br><br>1) <a href=\"Sanction\">Sanction Loan</a><br>2) <a href=\"EMI\">EMI Payment</a></td>");
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
				pw.println(myIndex.section1+myIndex.customer);
			else if(perarray[0]=='e')
				pw.println(myIndex.section1+myIndex.employee);
			else if(perarray[0]=='a')
				pw.println(myIndex.section1+myIndex.admin);
			pw.println("<td valign=\"top\" width=580><b><u> Loan Details </u></b> <br> <br><br><br><table> <tr> <td bgcolor=#b5b5b5 style=\"border:1px #000000 solid\"><b>Loan Number</b></td> <td bgcolor=#b5b5b5 style=\"border:1px #000000 solid\"><b>Loan Type</b></td><td bgcolor=#b5b5b5 style=\"border:1px #000000 solid\"><b>Amount Sanction</b></td> <td bgcolor=#b5b5b5 style=\"border:1px #000000 solid\"><b>EMI</b></td> <td bgcolor=#b5b5b5 style=\"border:1px #000000 solid\"><b>Start Date</b></td> <td bgcolor=#b5b5b5 style=\"border:1px #000000 solid\"><b>End Date</b></td> <td bgcolor=#b5b5b5 style=\"border:1px #000000 solid\"><b>Overdue</b></td> </tr>");
			query= "select * from loan where loan.user ='"+per+"'";
			rset=stmt.executeQuery(query);
			boolean enter=false;
			while(rset.next())
			 {
				enter=true;
				pw.println("<tr>");
				String overdue="";
				int temp=(int)(rset.getFloat(10));
				if( temp == -1)
					overdue+="Not Started";
				else 
					overdue +=rset.getFloat(10);
				int mm = (rset.getDate(3).getMonth()) +1;
				int yr=rset.getDate(3).getYear() +rset.getInt(9)+1900;
				pw.println("<td style=\"border:1px #000000 solid\">"+rset.getString(2)+"<td style=\"border:1px #000000 solid\">"+rset.getString(8)+"</td>"+"<td style=\"border:1px #000000 solid\">"+rset.getFloat(4)+"</td>"+"<td style=\"border:1px #000000 solid\">"+rset.getFloat(11)+"</td>"+"<td style=\"border:1px #000000 solid\">"+rset.getDate(6)+"</td>"+"<td style=\"border:1px #000000 solid\">"+yr+"-"+mm+"-"+(rset.getDate(3).getDate())+"</td>"+"<td style=\"border:1px #000000 solid\">"+overdue+"</td> </tr>");
				
				pw.println("</tr>");
			}
			if(!enter)
			{
				pw.println("<br><br><font size=4 color=\"BLUE\"><B>You have hired no loans from our Bank</font></b>");
			}
			if(perarray[0]=='c')
			{
			pw.println("</table></td>");
			pw.println(myIndex.section6);
			
			pw.println("Customer");
			}
			else if(perarray[0]=='e')
			{
				pw.println("</table><br><br><hr><font size=3><B><U>Job</U></B></font><br><br>1) <a href=\"Sanction\">Sanction Loan</a><br>2) <a href=\"EMI\">EMI Payment</a></td>");
			}
			else if(perarray[0]=='a')
			{
				pw.println("</table><br><br><hr><font size=3><B><U>Job</U></B></font><br><br>1) <a href=\"Sanction\">Sanction Loan</a><br>2) <a href=\"EMI\">EMI Payment</a></td>");
			}
			
			 }
			catch(Exception ex)
			{
				
			}
		}
	}   	  	    
}