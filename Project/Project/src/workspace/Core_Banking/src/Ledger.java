

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
 * Servlet implementation class for Servlet: Ledger
 *
 */
 public class Ledger extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public Ledger() {
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
				
					pw.println("<html><title>Error Page!</title><body><font color=\"red\" size=3><B>Error! This page is not meant for you</font></B></body></html>");

				
			}
			else if(perarray[0]=='e')
			{
				//employee
				pw.println(myIndex.section1+myIndex.employee);
				String abc="<td valign=top width=580><SCRIPT LANGUAGE=JavaScript>function validate(){   var myForm = document.form1;   if (myForm.acnumber.value ==\"\")   {      alert(\"Enter your Account Number\");    	return false;     }if (myForm.ddfrom.value ==\"\")   {      alert(\"Enter Start Date\");    	return false;     }if (myForm.mmfrom.value ==\"\")   {      alert(\"Enter Start Month\");    	return false;     }if (myForm.yyfrom.value ==\"\")   {      alert(\"Enter Start Year\");    	return false;     }if (myForm.ddto.value ==\"\")   {      alert(\"Enter End Date\");    	return false;     }if (myForm.mmto.value ==\"\")   {      alert(\"Enter End Month\");    	return false;     }if (myForm.yyto.value ==\"\")   {      alert(\"Enter End Year\");    	return false;     }}</SCRIPT>";
				pw.println(abc+"<br><br><form name=form1 action=\"LedgerView\" method=\"POST\"><table >	<td valign=\"top\" >	<b>Account No:<br><br>	Date From :<br><br>	Date To :<br></b>	</td>	<td valign=\"top\" >	<input type=\"text\" maxlength=\"20\" name=acnumber><br><br>	<input type=\"text\" size=3 name=ddfrom \">/<input type=\"text\" size=3 name=mmfrom \">/<input type=\"text\" size=4 name=yyfrom \"><br><br>	<input type=\"text\" size=3 name=ddto \">/<input type=\"text\" size=3 name=mmto \">/<input type=\"text\" size=4 name=yyto \"></td></table><br><input type=\"submit\" value=\"View\"  onclick=\"return validate()\">&nbsp&nbsp&nbsp&nbsp&nbsp<input type=\"reset\" value=Reset>	</form></td>");
				pw.println(myIndex.section6+"Employee"+myIndex.section7+date+myIndex.section8+time+myIndex.section9);
			}
			else if(perarray[0]=='a')
			{
				//admin
				pw.println(myIndex.section1+myIndex.admin);
				String abc="<td valign=top width=580><SCRIPT LANGUAGE=JavaScript>function validate(){   var myForm = document.form1;   if (myForm.acnumber.value ==\"\")   {      alert(\"Enter your Account Number\");    	return false;     }if (myForm.ddfrom.value ==\"\")   {      alert(\"Enter Start Date\");    	return false;     }if (myForm.mmfrom.value ==\"\")   {      alert(\"Enter Start Month\");    	return false;     }if (myForm.yyfrom.value ==\"\")   {      alert(\"Enter Start Year\");    	return false;     }if (myForm.ddto.value ==\"\")   {      alert(\"Enter End Date\");    	return false;     }if (myForm.mmto.value ==\"\")   {      alert(\"Enter End Month\");    	return false;     }if (myForm.yyto.value ==\"\")   {      alert(\"Enter End Year\");    	return false;     }}</SCRIPT>";
				pw.println(abc+"<br><br><form name=form1 action=\"LedgerView\" method=\"POST\"><table >	<td valign=\"top\" >	<b>Account No:<br><br>	Date From :<br><br>	Date To :<br></b>	</td>	<td valign=\"top\" >	<input type=\"text\" maxlength=\"20\" name=acnumber><br><br>	<input type=\"text\" size=3 name=ddfrom \">/<input type=\"text\" size=3 name=mmfrom \">/<input type=\"text\" size=4 name=yyfrom \"><br><br>	<input type=\"text\" size=3 name=ddto \">/<input type=\"text\" size=3 name=mmto \">/<input type=\"text\" size=4 name=yyto \"></td></table><br><input type=\"submit\" value=\"View\"  onclick=\"return validate()\">&nbsp&nbsp&nbsp&nbsp&nbsp<input type=\"reset\" value=Reset>	</form></td>");
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
				
					pw.println("<html><title>Error Page!</title><body><font color=\"red\" size=3><B>Error! This page is not meant for you</font></B></body></html>");

				
			}
			else if(perarray[0]=='e')
			{
				//employee
				pw.println(myIndex.section1+myIndex.employee);
				String abc="<td valign=top width=580><SCRIPT LANGUAGE=JavaScript>function validate(){   var myForm = document.form1;   if (myForm.acnumber.value ==\"\")   {      alert(\"Enter your Account Number\");    	return false;     }if (myForm.ddfrom.value ==\"\")   {      alert(\"Enter Start Date\");    	return false;     }if (myForm.mmfrom.value ==\"\")   {      alert(\"Enter Start Month\");    	return false;     }if (myForm.yyfrom.value ==\"\")   {      alert(\"Enter Start Year\");    	return false;     }if (myForm.ddto.value ==\"\")   {      alert(\"Enter End Date\");    	return false;     }if (myForm.mmto.value ==\"\")   {      alert(\"Enter End Month\");    	return false;     }if (myForm.yyto.value ==\"\")   {      alert(\"Enter End Year\");    	return false;     }}</SCRIPT>";
				pw.println(abc+"<br><br><form name=form1 action=\"LedgerView\" method=\"POST\"><table >	<td valign=\"top\" >	<b>Account No:<br><br>	Date From :<br><br>	Date To :<br></b>	</td>	<td valign=\"top\" >	<input type=\"text\" maxlength=\"20\" name=acnumber><br><br>	<input type=\"text\" size=3 name=ddfrom \">/<input type=\"text\" size=3 name=mmfrom \">/<input type=\"text\" size=4 name=yyfrom \"><br><br>	<input type=\"text\" size=3 name=ddto \">/<input type=\"text\" size=3 name=mmto \">/<input type=\"text\" size=4 name=yyto \"></td></table><br><input type=\"submit\" value=\"View\"  onclick=\"return validate()\">&nbsp&nbsp&nbsp&nbsp&nbsp<input type=\"reset\" value=Reset>	</form></td>");
				pw.println(myIndex.section6+"Employee"+myIndex.section7+date+myIndex.section8+time+myIndex.section9);
			}
			else if(perarray[0]=='a')
			{
				//admin
				pw.println(myIndex.section1+myIndex.admin);
				String abc="<td valign=top width=580><SCRIPT LANGUAGE=JavaScript>function validate(){   var myForm = document.form1;   if (myForm.acnumber.value ==\"\")   {      alert(\"Enter your Account Number\");    	return false;     }if (myForm.ddfrom.value ==\"\")   {      alert(\"Enter Start Date\");    	return false;     }if (myForm.mmfrom.value ==\"\")   {      alert(\"Enter Start Month\");    	return false;     }if (myForm.yyfrom.value ==\"\")   {      alert(\"Enter Start Year\");    	return false;     }if (myForm.ddto.value ==\"\")   {      alert(\"Enter End Date\");    	return false;     }if (myForm.mmto.value ==\"\")   {      alert(\"Enter End Month\");    	return false;     }if (myForm.yyto.value ==\"\")   {      alert(\"Enter End Year\");    	return false;     }}</SCRIPT>";
				pw.println(abc+"<br><br><form name=form1 action=\"LedgerView\" method=\"POST\"><table >	<td valign=\"top\" >	<b>Account No:<br><br>	Date From :<br><br>	Date To :<br></b>	</td>	<td valign=\"top\" >	<input type=\"text\" maxlength=\"20\" name=acnumber><br><br>	<input type=\"text\" size=3 name=ddfrom \">/<input type=\"text\" size=3 name=mmfrom \">/<input type=\"text\" size=4 name=yyfrom \"><br><br>	<input type=\"text\" size=3 name=ddto \">/<input type=\"text\" size=3 name=mmto \">/<input type=\"text\" size=4 name=yyto \"></td></table><br><input type=\"submit\" value=\"View\"  onclick=\"return validate()\">&nbsp&nbsp&nbsp&nbsp&nbsp<input type=\"reset\" value=Reset>	</form></td>");
				pw.println(myIndex.section6+"Administrator"+myIndex.section7+date+myIndex.section8+time+myIndex.section9);
			}
			 }
			catch(Exception ex)
			{
				
			}
		}
	}   	  	    
}