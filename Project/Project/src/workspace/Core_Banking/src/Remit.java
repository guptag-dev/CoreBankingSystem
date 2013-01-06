

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
 * Servlet implementation class for Servlet: Remit
 *
 */
 public class Remit extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public Remit() {
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
				pw.println("<td valign=\"top\" width=580><br><br><br><br><br><font size=3 color=\"BLUE\"><B>Dear Customer, due to certain security reasons we have not enabled this option for customers. Kindly bear with us and contact your nearby center to avail this facility and related details.</B></font>");
				pw.println(myIndex.section6+"Customer"+myIndex.section7+date+myIndex.section8+time+myIndex.section9);
			}
			else if(perarray[0]=='e')
			{
				//employee
				pw.println(myIndex.section1+myIndex.employee);
				String abc="<td valign=top width=580><SCRIPT LANGUAGE=JavaScript>function validate(){   var myForm = document.form1;  if (myForm.user.value ==\"\")   {      alert(\"Enter your Account Number\");    	return false;     } if (myForm.bill.value ==\"\")   {      alert(\"Select Destination Account Number\");    	return false;     }if (myForm.amt.value ==\"\")   {      alert(\"Enter Amount\");    	return false;     }if (myForm.idnum.value ==\"\")   {      alert(\"Enter ID Proof\");    	return false;     }}</SCRIPT>";
				pw.println(abc+"<br><br><form action=\"RemitVerify\" method=\"POST\" name=form1><font size=\"3\">Remittance Form (Available Only for Limited Countries. Check Help section for more details)</font>          <br><br><table>	<td width=200 valign=\"top\">	<b>User A/C No.:</b><br><br><b>Destination A/C No.:</b><br><br>	<b>Amount:</b><br><br>	<b>ID Proof:</b></td>	<td valign=\"top\"><input type=\"text\" name=user><br><br><input type=\"text\" name=bill maxlength=20><br><br>	<input type=\"text\" name=amt maxlength=20><br><br><input type=\"text\" name=idnum><br><br><input type=reset value=\"Reset\" name=btnd2>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type=\"Submit\" value=\"Remit\" name=btnd1 onclick=\"return validate()\">	</td></table> </form></td>");
				pw.println(myIndex.section6+"Employee"+myIndex.section7+date+myIndex.section8+time+myIndex.section9);
			
			}
			else if(perarray[0]=='a')
			{
				//admin
				pw.println(myIndex.section1+myIndex.admin);
				String abc="<td valign=top width=580><SCRIPT LANGUAGE=JavaScript>function validate(){   var myForm = document.form1;  if (myForm.user.value ==\"\")   {      alert(\"Enter your Account Number\");    	return false;     } if (myForm.bill.value ==\"\")   {      alert(\"Select Destination Number\");    	return false;     }if (myForm.amt.value ==\"\")   {      alert(\"Enter Amount\");    	return false;     }if (myForm.idnum.value ==\"\")   {      alert(\"Enter ID Proof\");    	return false;     }}</SCRIPT>";
				pw.println(abc+"<br><br><form action=\"RemitVerify\" method=\"POST\" name=form1><font size=\"3\">Remittance Form (Available Only for Limited Countries. Check help section for more details)</font>          <br><br><table>	<td width=200 valign=\"top\">	<b>User A/C No.:</b><br><br><b>Destination A/C No.:</b><br><br>	<b>Amount:</b><br><br>	<b>ID Proof:</b></td>	<td valign=\"top\"><input type=\"text\" name=user><br><br><input type=\"text\" name=bill maxlength=20><br><br>	<input type=\"text\" name=amt maxlength=20><br><br><input type=\"text\" name=idnum><br><br><input type=reset value=\"Reset\" name=btnd2>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type=\"Submit\" value=\"Remit\" name=btnd1 onclick=\"return validate()\">	</td></table> </form></td>");
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
				pw.println("<td valign=\"top\" width=580><br><br><br><br><br><font size=3 color=\"BLUE\"><B>Dear Customer, due to certain security reasons we have not enabled this option for customers. Kindly bear with us and contact your nearby center to avail this facility and related details.</B></font>");
				pw.println(myIndex.section6+"Customer"+myIndex.section7+date+myIndex.section8+time+myIndex.section9);
			}
			else if(perarray[0]=='e')
			{
				//employee
				pw.println(myIndex.section1+myIndex.employee);
				String abc="<td valign=top width=580><SCRIPT LANGUAGE=JavaScript>function validate(){   var myForm = document.form1;  if (myForm.user.value ==\"\")   {      alert(\"Enter your Account Number\");    	return false;     } if (myForm.bill.value ==\"\")   {      alert(\"Select Destination Account Number\");    	return false;     }if (myForm.amt.value ==\"\")   {      alert(\"Enter Amount\");    	return false;     }if (myForm.idnum.value ==\"\")   {      alert(\"Enter ID Proof\");    	return false;     }}</SCRIPT>";
				pw.println(abc+"<br><br><form action=\"RemitVerify\" method=\"POST\" name=form1><font size=\"3\">Remittance Form (Available Only for Limited Countries. Check help section for more details)</font>          <br><br><table>	<td width=200 valign=\"top\">	<b>User A/C No.:</b><br><br><b>Destination A/C No.:</b><br><br>	<b>Amount:</b><br><br>	<b>ID Proof:</b></td>	<td valign=\"top\"><input type=\"text\" name=user><br><br><input type=\"text\" name=bill maxlength=20><br><br>	<input type=\"text\" name=amt maxlength=20><br><br><input type=\"text\" name=idnum><br><br><input type=reset value=\"Reset\" name=btnd2>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type=\"Submit\" value=\"Remit\" name=btnd1 onclick=\"return validate()\">	</td></table> </form></td>");
				pw.println(myIndex.section6+"Employee"+myIndex.section7+date+myIndex.section8+time+myIndex.section9);
			
			}
			else if(perarray[0]=='a')
			{
				//admin
				pw.println(myIndex.section1+myIndex.admin);
				String abc="<td valign=top width=580><SCRIPT LANGUAGE=JavaScript>function validate(){   var myForm = document.form1;  if (myForm.user.value ==\"\")   {      alert(\"Enter your Account Number\");    	return false;     } if (myForm.bill.value ==\"\")   {      alert(\"Select Destination Number\");    	return false;     }if (myForm.amt.value ==\"\")   {      alert(\"Enter Amount\");    	return false;     }if (myForm.idnum.value ==\"\")   {      alert(\"Enter ID Proof\");    	return false;     }}</SCRIPT>";
				pw.println(abc+"<br><br><form action=\"RemitVerify\" method=\"POST\" name=form1><font size=\"3\">Remittance Form (Available Only for Limited Countries. Check help section for more details)</font>          <br><br><table>	<td width=200 valign=\"top\">	<b>User A/C No.:</b><br><br><b>Destination A/C No.:</b><br><br>	<b>Amount:</b><br><br>	<b>ID Proof:</b></td>	<td valign=\"top\"><input type=\"text\" name=user><br><br><input type=\"text\" name=bill maxlength=20><br><br>	<input type=\"text\" name=amt maxlength=20><br><br><input type=\"text\" name=idnum><br><br><input type=reset value=\"Reset\" name=btnd2>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type=\"Submit\" value=\"Remit\" name=btnd1 onclick=\"return validate()\">	</td></table> </form></td>");
				pw.println(myIndex.section6+"Administrator"+myIndex.section7+date+myIndex.section8+time+myIndex.section9);
			
			}
			 }
			catch(Exception ex)
			{
				
			}
		}
	}   	  	    
}