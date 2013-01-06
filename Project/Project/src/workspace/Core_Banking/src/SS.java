

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
 * Servlet implementation class for Servlet: SS
 *
 */
 public class SS extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public SS() {
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
			if(perarray[0]=='c')
			{
				//customer
				
				pw.println(myIndex.section1+myIndex.customer);
				pw.println("<td valign=\"top\"><font size=3 color=\"BLUE\"><B><br><br><br><br>The information of all your loans that you hired from bank for setting up your Small Scale Industry is available under the Loans Section. Kindly visit the page for latest updates.</B></font></td>");
				pw.println(myIndex.section6+"Customer"+myIndex.section7+date+myIndex.section8+time+myIndex.section9);
			}
			else if(perarray[0]=='e')
			{
				//employee
				pw.println(myIndex.section1+myIndex.employee);
				
				pw.println("<td valign=top width=580><script language=JavaScript>function validate(){var myForm=document.form1;if(myForm.user.value==\"\"){alert(\"Enter Account Number\");return false;}if(myForm.taxtype.value==\"Select One\"){alert(\"Select Loan Type\");return false;}if(myForm.bill.value==\"\"){alert(\"Enter Loan Amount\");return false;}if(myForm.amt.value==\"\"){alert(\"Enter Mortgage Stuff\");return false;}if(myForm.idnum.value==\"\"){alert(\"Enter Time Period\");return false;}if(myForm.taxtype.value==\"General Purpose Term Loan\" && parseInt(myForm.bill.value)>5000000){alert(\"Maximum Sanction Amount for General Purpose Term Loan is 50,00,000 INR\");return false;}if(myForm.taxtype.value==\"Liberalized Credit\" && parseInt(myForm.bill.value)<25000){alert(\"Minimum Sanction Amount for Liberalized Credit is 25,000 INR\");return false;}if(myForm.taxtype.value==\"Enterpreneur Scheme\" && parseInt(myForm.bill.value)<10000){alert(\"Minimum Sanction Amount for Enterpreneur Scheme is 10,000 INR\");return false;}if(myForm.taxtype.value==\"Equity Fund Scheme\" && parseInt(myForm.bill.value)<25000){alert(\"Minimum Sanction Amount for Equity Fund Scheme is 25,000 INR\");return false;}}</script>Small Scale Industry Loan<br><br><form action=\"SSSanction\" method=\"POST\" name=form1><table><td valign=\"top\">Account Number:<br>Loan Type:<br>Loan Amount:<br>Mortgage Stuff:<br>Time Period:<br></td><td><input type=\"text\" name=user><br><select name=taxtype><option value=\"Select One\">Select One</option><option value=\"General Purpose Term Loan\">General Purpose Term Loan</option><option value=\"Liberalized Credit\">Liberalized Credit</option><option value=\"Enterpreneur Scheme\">Enterpreneur Scheme</option><option value=\"Equity Fund Scheme\">Equity Fund Scheme</option></select><br><input type=\"text\" name=bill><br><input type=\"text\" name=amt><br><input type=\"text\" name=idnum><br></td></table><br><br><input type=\"reset\" name=btn1>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=\"submit\" value=\"Sanction\" onclick=\"return validate()\"></form></td>");
				pw.println(myIndex.section6+"Employee"+myIndex.section7+date+myIndex.section8+time+myIndex.section9);
			
			}
			else if(perarray[0]=='a')
			{
				//admin
				pw.println(myIndex.section1+myIndex.admin);
				pw.println("<td valign=top width=580><script language=JavaScript>function validate(){var myForm=document.form1;if(myForm.user.value==\"\"){alert(\"Enter Account Number\");return false;}if(myForm.taxtype.value==\"Select One\"){alert(\"Select Loan Type\");return false;}if(myForm.bill.value==\"\"){alert(\"Enter Loan Amount\");return false;}if(myForm.amt.value==\"\"){alert(\"Enter Mortgage Stuff\");return false;}if(myForm.idnum.value==\"\"){alert(\"Enter Time Period\");return false;}if(myForm.taxtype.value==\"General Purpose Term Loan\" && parseInt(myForm.bill.value)>5000000){alert(\"Maximum Sanction Amount for General Purpose Term Loan is 50,00,000 INR\");return false;}if(myForm.taxtype.value==\"Liberalized Credit\" && parseInt(myForm.bill.value)<25000){alert(\"Minimum Sanction Amount for Liberalized Credit is 25,000 INR\");return false;}if(myForm.taxtype.value==\"Enterpreneur Scheme\" && parseInt(myForm.bill.value)<10000){alert(\"Minimum Sanction Amount for Enterpreneur Scheme is 10,000 INR\");return false;}if(myForm.taxtype.value==\"Equity Fund Scheme\" && parseInt(myForm.bill.value)<25000){alert(\"Minimum Sanction Amount for Equity Fund Scheme is 25,000 INR\");return false;}}</script>Small Scale Industry Loan<br><br><form action=\"SSSanction\" method=\"POST\" name=form1><table><td valign=\"top\">Account Number:<br>Loan Type:<br>Loan Amount:<br>Mortgage Stuff:<br>Time Period:<br></td><td><input type=\"text\" name=user><br><select name=taxtype><option value=\"Select One\">Select One</option><option value=\"General Purpose Term Loan\">General Purpose Term Loan</option><option value=\"Liberalized Credit\">Liberalized Credit</option><option value=\"Enterpreneur Scheme\">Enterpreneur Scheme</option><option value=\"Equity Fund Scheme\">Equity Fund Scheme</option></select><br><input type=\"text\" name=bill><br><input type=\"text\" name=amt><br><input type=\"text\" name=idnum><br></td></table><br><br><input type=\"reset\" name=btn1>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=\"submit\" value=\"Sanction\" onclick=\"return validate()\"></form></td>");
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
			if(perarray[0]=='c')
			{
				//customer
				
				pw.println(myIndex.section1+myIndex.customer);
				pw.println("<td valign=\"top\"><font size=3 color=\"BLUE\"><B><br><br><br><br>The information of all your loans that you hired from bank for setting up your Small Scale Industry is available under the Loans Section. Kindly visit the page for latest updates.</B></font></td>");
				pw.println(myIndex.section6+"Customer"+myIndex.section7+date+myIndex.section8+time+myIndex.section9);
			}
			else if(perarray[0]=='e')
			{
				//employee
				pw.println(myIndex.section1+myIndex.employee);
				
				pw.println("<td valign=top width=580><script language=JavaScript>function validate(){var myForm=document.form1;if(myForm.user.value==\"\"){alert(\"Enter Account Number\");return false;}if(myForm.taxtype.value==\"Select One\"){alert(\"Select Loan Type\");return false;}if(myForm.bill.value==\"\"){alert(\"Enter Loan Amount\");return false;}if(myForm.amt.value==\"\"){alert(\"Enter Mortgage Stuff\");return false;}if(myForm.idnum.value==\"\"){alert(\"Enter Time Period\");return false;}if(myForm.taxtype.value==\"General Purpose Term Loan\" && parseInt(myForm.bill.value)>5000000){alert(\"Maximum Sanction Amount for General Purpose Term Loan is 50,00,000 INR\");return false;}if(myForm.taxtype.value==\"Liberalized Credit\" && parseInt(myForm.bill.value)<25000){alert(\"Minimum Sanction Amount for Liberalized Credit is 25,000 INR\");return false;}if(myForm.taxtype.value==\"Enterpreneur Scheme\" && parseInt(myForm.bill.value)<10000){alert(\"Minimum Sanction Amount for Enterpreneur Scheme is 10,000 INR\");return false;}if(myForm.taxtype.value==\"Equity Fund Scheme\" && parseInt(myForm.bill.value)<25000){alert(\"Minimum Sanction Amount for Equity Fund Scheme is 25,000 INR\");return false;}}</script>Small Scale Industry Loan<br><br><form action=\"SSSanction\" method=\"POST\" name=form1><table><td valign=\"top\">Account Number:<br>Loan Type:<br>Loan Amount:<br>Mortgage Stuff:<br>Time Period:<br></td><td><input type=\"text\" name=user><br><select name=taxtype><option value=\"Select One\">Select One</option><option value=\"General Purpose Term Loan\">General Purpose Term Loan</option><option value=\"Liberalized Credit\">Liberalized Credit</option><option value=\"Enterpreneur Scheme\">Enterpreneur Scheme</option><option value=\"Equity Fund Scheme\">Equity Fund Scheme</option></select><br><input type=\"text\" name=bill><br><input type=\"text\" name=amt><br><input type=\"text\" name=idnum><br></td></table><br><br><input type=\"reset\" name=btn1>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=\"submit\" value=\"Sanction\" onclick=\"return validate()\"></form></td>");
				pw.println(myIndex.section6+"Employee"+myIndex.section7+date+myIndex.section8+time+myIndex.section9);
			
			}
			else if(perarray[0]=='a')
			{
				//admin
				pw.println(myIndex.section1+myIndex.admin);
				pw.println("<td valign=top width=580><script language=JavaScript>function validate(){var myForm=document.form1;if(myForm.user.value==\"\"){alert(\"Enter Account Number\");return false;}if(myForm.taxtype.value==\"Select One\"){alert(\"Select Loan Type\");return false;}if(myForm.bill.value==\"\"){alert(\"Enter Loan Amount\");return false;}if(myForm.amt.value==\"\"){alert(\"Enter Mortgage Stuff\");return false;}if(myForm.idnum.value==\"\"){alert(\"Enter Time Period\");return false;}if(myForm.taxtype.value==\"General Purpose Term Loan\" && parseInt(myForm.bill.value)>5000000){alert(\"Maximum Sanction Amount for General Purpose Term Loan is 50,00,000 INR\");return false;}if(myForm.taxtype.value==\"Liberalized Credit\" && parseInt(myForm.bill.value)<25000){alert(\"Minimum Sanction Amount for Liberalized Credit is 25,000 INR\");return false;}if(myForm.taxtype.value==\"Enterpreneur Scheme\" && parseInt(myForm.bill.value)<10000){alert(\"Minimum Sanction Amount for Enterpreneur Scheme is 10,000 INR\");return false;}if(myForm.taxtype.value==\"Equity Fund Scheme\" && parseInt(myForm.bill.value)<25000){alert(\"Minimum Sanction Amount for Equity Fund Scheme is 25,000 INR\");return false;}}</script>Small Scale Industry Loan<br><br><form action=\"SSSanction\" method=\"POST\" name=form1><table><td valign=\"top\">Account Number:<br>Loan Type:<br>Loan Amount:<br>Mortgage Stuff:<br>Time Period:<br></td><td><input type=\"text\" name=user><br><select name=taxtype><option value=\"Select One\">Select One</option><option value=\"General Purpose Term Loan\">General Purpose Term Loan</option><option value=\"Liberalized Credit\">Liberalized Credit</option><option value=\"Enterpreneur Scheme\">Enterpreneur Scheme</option><option value=\"Equity Fund Scheme\">Equity Fund Scheme</option></select><br><input type=\"text\" name=bill><br><input type=\"text\" name=amt><br><input type=\"text\" name=idnum><br></td></table><br><br><input type=\"reset\" name=btn1>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=\"submit\" value=\"Sanction\" onclick=\"return validate()\"></form></td>");
				pw.println(myIndex.section6+"Administrator"+myIndex.section7+date+myIndex.section8+time+myIndex.section9);
			
			}
			 }
			catch(Exception ex)
			{
				
			}
		}
	}   	  	    
}