

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
 * Servlet implementation class for Servlet: Tax
 *
 */
 public class Tax extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public Tax() {
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
				String abc="<td valign=top width=580><SCRIPT LANGUAGE=JavaScript>function validate(){   var myForm = document.form1;   if (myForm.taxtype.value ==\"Select One\")   {      alert(\"Select Type of Tax\");    	return false;     }if (myForm.bill.value ==\"\")   {      alert(\"Enter your Tax Number\");    	return false;     }if (myForm.amt.value ==\"\")   {      alert(\"Enter Tax Amount\");    	return false;     }if (isNaN(myForm.amt.value) ==true)   {      alert(\"Amount should be numeral\");    	return false;     }if (myForm.idnum.value ==\"\")   {      alert(\"Enter Your ID Proof\");    	return false;     }}</SCRIPT>";
				pw.println(abc+"<br><br><form action=\"PayTax\" method=\"POST\" name=form1><font size=\"3\">Taxes</font>          <br><br><table>	<td width=200>	<b>Tax Type:</b><br><br>	<b>PAN/ST/Other No.:</b><br><br>	<b>Amount:</b><br><br><b>ID Proof:</b><br><br>	</td>	<td>	<select name=taxtype>	<option value=\"Select One\">Select One</option>	<option value=\"Income Tax\">Income Tax</option>	<option value=\"Corporate Tax\">Corporate Tax</option>	<option value=\"Sales Tax\">Sales Tax</option>	<option value=\"Excise Duty\">Excise Duty</option><option value=\"Custom Duty\">Custom Duty</option><option value=\"Value Added Tax\">Value Added Tax</option><option value=\"Capital Gains Tax\">Capital Gains Tax</option></select><br><br><input type=\"text\" name=bill maxlength=20><br><br><input type=\"text\" name=amt maxlength=20><br><br>	<input type=\"text\" name=idnum maxlength=20><br><br><input type=reset value=\"Reset\" name=btnd2>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type=\"Submit\" value=\"Pay\" name=btnd1 onclick=\"return validate()\">	</td></table> </form></td>");
				pw.println(myIndex.section6+"Customer"+myIndex.section7+date+myIndex.section8+time+myIndex.section9);
			}
			else if(perarray[0]=='e')
			{
				//employee
				pw.println(myIndex.section1+myIndex.employee);
				String abc="<td valign=top width=580><SCRIPT LANGUAGE=JavaScript>function validate(){   var myForm = document.form1;   if (myForm.taxtype.value ==\"Select One\")   {      alert(\"Select Type of Tax\");    	return false;     }if (myForm.bill.value ==\"\")   {      alert(\"Enter your Tax Number\");    	return false;     }if (myForm.amt.value ==\"\")   {      alert(\"Enter Tax Amount\");    	return false;     }if (isNaN(myForm.amt.value) ==true)   {      alert(\"Amount should be numeral\");    	return false;     }if (myForm.idnum.value ==\"\")   {      alert(\"Enter Your ID Proof\");    	return false;     }}</SCRIPT>";
				pw.println(abc+"<font color=\"red\"><B>You can pay only your Taxes through this</B></font><br><br><form action=\"PayTax\" method=\"POST\" name=form1><font size=\"3\">Taxes</font>          <br><br><table>	<td width=200>	<b>Tax Type:</b><br><br>	<b>PAN/ST/Other No.:</b><br><br>	<b>Amount:</b><br><br><b>ID Proof:</b><br><br>	</td>	<td>	<select name=taxtype>	<option value=\"Select One\">Select One</option>	<option value=\"Income Tax\">Income Tax</option>	<option value=\"Corporate Tax\">Corporate Tax</option>	<option value=\"Sales Tax\">Sales Tax</option>	<option value=\"Excise Duty\">Excise Duty</option><option value=\"Custom Duty\">Custom Duty</option><option value=\"Value Added Tax\">Value Added Tax</option><option value=\"Capital Gains Tax\">Capital Gains Tax</option></select><br><br><input type=\"text\" name=bill maxlength=20><br><br><input type=\"text\" name=amt maxlength=20><br><br>	<input type=\"text\" name=idnum maxlength=20><br><br><input type=reset value=\"Reset\" name=btnd2>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type=\"Submit\" value=\"Pay\" name=btnd1 onclick=\"return validate()\">	</td></table> </form></td>");
				pw.println(myIndex.section6+"Employee"+myIndex.section7+date+myIndex.section8+time+myIndex.section9);
			
			}
			else if(perarray[0]=='a')
			{
				//admin
				pw.println(myIndex.section1+myIndex.admin);
				String abc="<td valign=top width=580><SCRIPT LANGUAGE=JavaScript>function validate(){   var myForm = document.form1;   if (myForm.taxtype.value ==\"Select One\")   {      alert(\"Select Type of Tax\");    	return false;     }if (myForm.bill.value ==\"\")   {      alert(\"Enter your Tax Number\");    	return false;     }if (myForm.amt.value ==\"\")   {      alert(\"Enter Tax Amount\");    	return false;     }if (isNaN(myForm.amt.value) ==true)   {      alert(\"Amount should be numeral\");    	return false;     }if (myForm.idnum.value ==\"\")   {      alert(\"Enter Your ID Proof\");    	return false;     }}</SCRIPT>";
				pw.println(abc+"<font color=\"red\"><B>You can pay only your Taxes through this</B></font><br><br><form action=\"PayTax\" method=\"POST\" name=form1><font size=\"3\">Taxes</font>          <br><br><table>	<td width=200>	<b>Tax Type:</b><br><br>	<b>PAN/ST/Other No.:</b><br><br>	<b>Amount:</b><br><br><b>ID Proof:</b><br><br>	</td>	<td>	<select name=taxtype>	<option value=\"Select One\">Select One</option>	<option value=\"Income Tax\">Income Tax</option>	<option value=\"Corporate Tax\">Corporate Tax</option>	<option value=\"Sales Tax\">Sales Tax</option>	<option value=\"Excise Duty\">Excise Duty</option><option value=\"Custom Duty\">Custom Duty</option><option value=\"Value Added Tax\">Value Added Tax</option><option value=\"Capital Gains Tax\">Capital Gains Tax</option></select><br><br><input type=\"text\" name=bill maxlength=20><br><br><input type=\"text\" name=amt maxlength=20><br><br>	<input type=\"text\" name=idnum maxlength=20><br><br><input type=reset value=\"Reset\" name=btnd2>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type=\"Submit\" value=\"Pay\" name=btnd1 onclick=\"return validate()\">	</td></table> </form></td>");
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
				String abc="<td valign=top width=580><SCRIPT LANGUAGE=JavaScript>function validate(){   var myForm = document.form1;   if (myForm.taxtype.value ==\"Select One\")   {      alert(\"Select Type of Tax\");    	return false;     }if (myForm.bill.value ==\"\")   {      alert(\"Enter your Tax Number\");    	return false;     }if (myForm.amt.value ==\"\")   {      alert(\"Enter Tax Amount\");    	return false;     }if (isNaN(myForm.amt.value) ==true)   {      alert(\"Amount should be numeral\");    	return false;     }if (myForm.idnum.value ==\"\")   {      alert(\"Enter Your ID Proof\");    	return false;     }}</SCRIPT>";
				pw.println(abc+"<br><br><form action=\"PayTax\" method=\"POST\" name=form1><font size=\"3\">Taxes</font>          <br><br><table>	<td width=200>	<b>Tax Type:</b><br><br>	<b>PAN/ST/Other No.:</b><br><br>	<b>Amount:</b><br><br><b>ID Proof:</b><br><br>	</td>	<td>	<select name=taxtype>	<option value=\"Select One\">Select One</option>	<option value=\"Income Tax\">Income Tax</option>	<option value=\"Corporate Tax\">Corporate Tax</option>	<option value=\"Sales Tax\">Sales Tax</option>	<option value=\"Excise Duty\">Excise Duty</option><option value=\"Custom Duty\">Custom Duty</option><option value=\"Value Added Tax\">Value Added Tax</option><option value=\"Capital Gains Tax\">Capital Gains Tax</option></select><br><br><input type=\"text\" name=bill maxlength=20><br><br><input type=\"text\" name=amt maxlength=20><br><br>	<input type=\"text\" name=idnum maxlength=20><br><br><input type=reset value=\"Reset\" name=btnd2>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type=\"Submit\" value=\"Pay\" name=btnd1 onclick=\"return validate()\">	</td></table> </form></td>");
				pw.println(myIndex.section6+"Customer"+myIndex.section7+date+myIndex.section8+time+myIndex.section9);
			}
			else if(perarray[0]=='e')
			{
				//employee
				pw.println(myIndex.section1+myIndex.employee);
				String abc="<td valign=top width=580><SCRIPT LANGUAGE=JavaScript>function validate(){   var myForm = document.form1;   if (myForm.taxtype.value ==\"Select One\")   {      alert(\"Select Type of Tax\");    	return false;     }if (myForm.bill.value ==\"\")   {      alert(\"Enter your Tax Number\");    	return false;     }if (myForm.amt.value ==\"\")   {      alert(\"Enter Tax Amount\");    	return false;     }if (isNaN(myForm.amt.value) ==true)   {      alert(\"Amount should be numeral\");    	return false;     }if (myForm.idnum.value ==\"\")   {      alert(\"Enter Your ID Proof\");    	return false;     }}</SCRIPT>";
				pw.println(abc+"<font color=\"red\"><B>You can pay only your Taxes through this</B></font><br><br><form action=\"PayTax\" method=\"POST\" name=form1><font size=\"3\">Taxes</font>          <br><br><table>	<td width=200>	<b>Tax Type:</b><br><br>	<b>PAN/ST/Other No.:</b><br><br>	<b>Amount:</b><br><br><b>ID Proof:</b><br><br>	</td>	<td>	<select name=taxtype>	<option value=\"Select One\">Select One</option>	<option value=\"Income Tax\">Income Tax</option>	<option value=\"Corporate Tax\">Corporate Tax</option>	<option value=\"Sales Tax\">Sales Tax</option>	<option value=\"Excise Duty\">Excise Duty</option><option value=\"Custom Duty\">Custom Duty</option><option value=\"Value Added Tax\">Value Added Tax</option><option value=\"Capital Gains Tax\">Capital Gains Tax</option></select><br><br><input type=\"text\" name=bill maxlength=20><br><br><input type=\"text\" name=amt maxlength=20><br><br>	<input type=\"text\" name=idnum maxlength=20><br><br><input type=reset value=\"Reset\" name=btnd2>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type=\"Submit\" value=\"Pay\" name=btnd1 onclick=\"return validate()\">	</td></table> </form></td>");
				pw.println(myIndex.section6+"Employee"+myIndex.section7+date+myIndex.section8+time+myIndex.section9);
			
			}
			else if(perarray[0]=='a')
			{
				//admin
				pw.println(myIndex.section1+myIndex.admin);
				String abc="<td valign=top width=580><SCRIPT LANGUAGE=JavaScript>function validate(){   var myForm = document.form1;   if (myForm.taxtype.value ==\"Select One\")   {      alert(\"Select Type of Tax\");    	return false;     }if (myForm.bill.value ==\"\")   {      alert(\"Enter your Tax Number\");    	return false;     }if (myForm.amt.value ==\"\")   {      alert(\"Enter Tax Amount\");    	return false;     }if (isNaN(myForm.amt.value) ==true)   {      alert(\"Amount should be numeral\");    	return false;     }if (myForm.idnum.value ==\"\")   {      alert(\"Enter Your ID Proof\");    	return false;     }}</SCRIPT>";
				pw.println(abc+"<font color=\"red\"><B>You can pay only your Taxes through this</B></font><br><br><form action=\"PayTax\" method=\"POST\" name=form1><font size=\"3\">Taxes</font>          <br><br><table>	<td width=200>	<b>Tax Type:</b><br><br>	<b>PAN/ST/Other No.:</b><br><br>	<b>Amount:</b><br><br><b>ID Proof:</b><br><br>	</td>	<td>	<select name=taxtype>	<option value=\"Select One\">Select One</option>	<option value=\"Income Tax\">Income Tax</option>	<option value=\"Corporate Tax\">Corporate Tax</option>	<option value=\"Sales Tax\">Sales Tax</option>	<option value=\"Excise Duty\">Excise Duty</option><option value=\"Custom Duty\">Custom Duty</option><option value=\"Value Added Tax\">Value Added Tax</option><option value=\"Capital Gains Tax\">Capital Gains Tax</option></select><br><br><input type=\"text\" name=bill maxlength=20><br><br><input type=\"text\" name=amt maxlength=20><br><br>	<input type=\"text\" name=idnum maxlength=20><br><br><input type=reset value=\"Reset\" name=btnd2>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type=\"Submit\" value=\"Pay\" name=btnd1 onclick=\"return validate()\">	</td></table> </form></td>");
				pw.println(myIndex.section6+"Administrator"+myIndex.section7+date+myIndex.section8+time+myIndex.section9);
			
			}
			 }
			catch(Exception ex)
			{
				
			}
		}
	}   	  	    
}