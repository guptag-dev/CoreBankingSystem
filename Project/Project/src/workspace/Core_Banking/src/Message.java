

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
 * Servlet implementation class for Servlet: Message
 *
 */
 public class Message extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public Message() {
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
				String abc="<td valign=top width=580><SCRIPT LANGUAGE=JavaScript>function validate(){   var myForm = document.form1;   if (myForm.message.value ==\"\")   {      alert(\"Message cannot be empty\");    	return false;     }}</SCRIPT>";
				pw.println(abc+"<font size=2 ><B>Enter Your Message:<br><br></B><form name=form1 action=\"MessageSent\" method=\"POST\"><table >	<td valign=\"top\" >	<b>To :<br><br>	From :<br><br>	Message :<br></b>	</td>	<td valign=\"top\" >	<input type=\"text\" maxlength=\"20\" name=to value=\"admin@cbsolns.com\" size=\"20\" ><br><br>	<input type=\"text\" maxlength=\"20\" name=from size=\"20\" value=\""+per+"@cbsolns.com"+"\"><br><br>	<textarea rows=\"10\" cols=\"60\" name=message ></textarea></td></table><br><input type=\"submit\" value=\"Send\"  onclick=\"return validate()\">&nbsp&nbsp&nbsp&nbsp&nbsp<input type=\"reset\" value=Reset>	</form></td>");
				pw.println(myIndex.section6+"Customer"+myIndex.section7+date+myIndex.section8+time+myIndex.section9);
			}
			else if(perarray[0]=='e')
			{
				//employee
				pw.println(myIndex.section1+myIndex.employee);
				String abc="<td valign=top width=580><SCRIPT LANGUAGE=JavaScript>function validate(){   var myForm = document.form1;   if (myForm.message.value ==\"\")   {      alert(\"Message cannot be empty\");    	return false;     }}</SCRIPT>";
				pw.println(abc+"<font size=2 ><B>Enter Your Message:<br><br></B><form name=form1 action=\"MessageSent\" method=\"POST\"><table >	<td valign=\"top\" >	<b>To :<br><br>	From :<br><br>	Message :<br></b>	</td>	<td valign=\"top\" >	<input type=\"text\" maxlength=\"20\" name=to value=\"admin@cbsolns.com\" size=\"20\" ><br><br>	<input type=\"text\" maxlength=\"20\" name=from size=\"20\" value=\""+per+"@cbsolns.com"+"\"><br><br>	<textarea rows=\"10\" cols=\"60\" name=message ></textarea></td></table><br><input type=\"submit\" value=\"Send\"  onclick=\"return validate()\">&nbsp&nbsp&nbsp&nbsp&nbsp<input type=\"reset\" value=Reset>	</form></td>");
				pw.println(myIndex.section6+"Employee"+myIndex.section7+date+myIndex.section8+time+myIndex.section9);
			}
			else if(perarray[0]=='a')
			{
				//admin
				pw.println("<html><title>Error Page!</title><body><font color=\"red\" size=3><B>Error! This page is not meant for you</font></B></body></html>");
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
				String abc="<td valign=top width=580><SCRIPT LANGUAGE=JavaScript>function validate(){   var myForm = document.form1;   if (myForm.message.value ==\"\")   {      alert(\"Message cannot be empty\");    	return false;     }}</SCRIPT>";
				pw.println(abc+"<font size=2 ><B>Enter Your Message:<br><br></B><form name=form1 action=\"MessageSent\" method=\"POST\"><table >	<td valign=\"top\" >	<b>To :<br><br>	From :<br><br>	Message :<br></b>	</td>	<td valign=\"top\" >	<input type=\"text\" maxlength=\"20\" name=to value=\"admin@cbsolns.com\" size=\"20\" ><br><br>	<input type=\"text\" maxlength=\"20\" name=from size=\"20\" value=\""+per+"@cbsolns.com"+"\"><br><br>	<textarea rows=\"10\" cols=\"60\" name=message ></textarea></td></table><br><input type=\"submit\" value=\"Send\"  onclick=\"return validate()\">&nbsp&nbsp&nbsp&nbsp&nbsp<input type=\"reset\" value=Reset>	</form></td>");
				pw.println(myIndex.section6+"Customer"+myIndex.section7+date+myIndex.section8+time+myIndex.section9);
			}
			else if(perarray[0]=='e')
			{
				//employee
				pw.println(myIndex.section1+myIndex.employee);
				String abc="<td valign=top width=580><SCRIPT LANGUAGE=JavaScript>function validate(){   var myForm = document.form1;   if (myForm.message.value ==\"\")   {      alert(\"Message cannot be empty\");    	return false;     }}</SCRIPT>";
				pw.println(abc+"<font size=2 ><B>Enter Your Message:<br><br></B><form name=form1 action=\"MessageSent\" method=\"POST\"><table >	<td valign=\"top\" >	<b>To :<br><br>	From :<br><br>	Message :<br></b>	</td>	<td valign=\"top\" >	<input type=\"text\" maxlength=\"20\" name=to value=\"admin@cbsolns.com\" size=\"20\" ><br><br>	<input type=\"text\" maxlength=\"20\" name=from size=\"20\" value=\""+per+"@cbsolns.com"+"\"><br><br>	<textarea rows=\"10\" cols=\"60\" name=message ></textarea></td></table><br><input type=\"submit\" value=\"Send\"  onclick=\"return validate()\">&nbsp&nbsp&nbsp&nbsp&nbsp<input type=\"reset\" value=Reset>	</form></td>");
				pw.println(myIndex.section6+"Employee"+myIndex.section7+date+myIndex.section8+time+myIndex.section9);
			}
			else if(perarray[0]=='a')
			{
				//admin
				pw.println("<html><title>Error Page!</title><body><font color=\"red\" size=3><B>Error! This page is not meant for you</font></B></body></html>");
			}
			 }
			catch(Exception ex)
			{
				
			}
		}
	}   	  	    
}