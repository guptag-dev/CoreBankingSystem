

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
 * Servlet implementation class for Servlet: LOGissue
 *
 */
 public class LOGissue extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public LOGissue() {
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
				String abc="<td valign=top width=580><script language=Javascript>function validate(){	var myform = document.form1;		if(myform.bank.value==\"Select One\"){		alert(\"Bank is the required Field\");		return false;	}	if(myform.idno.value==\"\")	{		alert(\"ID Number is the required Field\");		return false;	}	if(myform.ACno.value==\"\")	{		alert(\"Account Number is the required Field\");		return false;	}	if(myform.Guarantee.value==\"Select One\")	{		alert(\"Select any one Guarantee\");		return false;	}	if(myform.Guaranteetype.value==\"Select One\")	{		alert(\"Select any one Guarantee Letter Type\");		return false;	}		if(myform.Amount.value==\"\")	{		alert(\"Amount is the required Field\");		return false;	}	if(myform.to.value==\"\")	{		alert(\"To is the required Field\");		return false;	}	if(myform.maxlimit.value==\"\")	{		alert(\"Maximum Limit is the required Field\");		return false;	}		if(myform.day1.value==\"dd\")	{		alert(\"Select any Day\");		return false;	}	if(myform.month1.value==\"mm\")	{		alert(\"Select any Month\");		return false;	}	if(myform.year1.value==\"yyyy\")	{		alert(\"Select any Year\");		return false;	}	}</script>";
				pw.println(abc+"<br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font size=\"6\">Letter of Guarantee</font>          <br><br><form action=\"LOGissued\" method=\"POST\" name=form1><table>	<td width=200>	<b>Guarantee:</b><br><br>	<b>Type of Guarantee Letter:</b><br><br>	<b>Bank:</b><br><br>	<b>Maximum Limit:</b><br><br>	<b>ID Number:</b><br><br>	<b>Account Number:</b><br><br>	<br><br>	<b>To:</b><br><br>	<b>Valid Till:</b><br><br>		</td width=200>	<td>	<select name=Guarantee>	<option value=\"Select One\">Select One</option>	<option value=\"deposit\">Guaranteed Deposit</option>	<option value=\"mortgage\">Mortgage</option>	</select><br><br>		<select name=Guaranteetype>	<option value=\"Select One\">Select One</option>	<option value=\"tender\">Tender Guarantee</option>	<option value=\"performance\">Performance Guarantee</option>	<option value=\"advance\">Advance Payment Guarantee</option>	<option value=\"maintenance\">Maintenance Guarantee</option>	<option value=\"Retention\">Retention Guarantee</option>	</select><br><br>		<select name=bank><option value=\"Select One\">Select One</option>");
				query="select * from branch";
				rset=stmt.executeQuery(query);
				while(rset.next())
				{
					pw.println("<option value=\""+rset.getString(3)+"\">"+rset.getString(3)+"</option>");
				}
				pw.println("</select><br><br>	<input type=\"text\" name=maxlimit maxlength=20><br><br>	<input type=\"text\" name=idno maxlength=20><br><br>	<input type=\"text\" name=ACno maxlength=20><br><br>		<br>	<input type=\"text\" name=to maxlength=20><br><br>		<select name=\"day1\">		<option value=\"dd\">dd</option>		<option value=\"1\">01</option>		<option value=\"2\">02</option>		<option value=\"3\">03</option>		<option value=\"4\">04</option>		<option value=\"5\">05</option>		<option value=\"6\">06</option>		<option value=\"7\">07</option>		<option value=\"8\">08</option>		<option value=\"9\">09</option>		<option value=\"10\">10</option>		<option value=\"11\">11</option>		<option value=\"12\">12</option>		<option value=\"13\">13</option>		<option value=\"14\">14</option>		<option value=\"15\">15</option>		<option value=\"16\">16</option>		<option value=\"17\">17</option>		<option value=\"18\">18</option>		<option value=\"19\">19</option>		<option value=\"20\">20</option>		<option value=\"21\">21</option>		<option value=\"22\">22</option>		<option value=\"23\">23</option>		<option value=\"24\">24</option>		<option value=\"25\">25</option>		<option value=\"26\">26</option>		<option value=\"27\">27</option>		<option value=\"28\">28</option>		<option value=\"29\">29</option>		<option value=\"30\">30</option>		<option value=\"31\">31</option>		</select>		<select name=\"month1\" >				<option value=\"mm\">mm</option>				<option value=\"1\">01</option>				<option value=\"2\">02</option>				<option value=\"3\">03</option>				<option value=\"4\">04</option>				<option value=\"5\">05</option>				<option value=\"6\">06</option>				<option value=\"7\">07</option>				<option value=\"8\">08</option>				<option value=\"9\">09</option>				<option value=\"10\">10</option>				<option value=\"11\">11</option>				<option value=\"12\">12</option>		</select>				<select name =\"year1\" >				<option value=\"yyyy\">yyyy</option>				<option value=\"2000\">2000</option>				<option value=\"2001\">2001</option>				<option value=\"2002\">2002</option>				<option value=\"2003\">2003</option>				<option value=\"2004\">2004</option>				<option value=\"2005\">2005</option>				<option value=\"2006\">2006</option>				<option value=\"2007\">2007</option>				<option value=\"2008\">2008</option>				<option value=\"2009\">2009</option>				<option value=\"2010\">2010</option>				<option value=\"2011\">2011</option>				<option value=\"2012\">2012</option>				<option value=\"2013\">2013</option>				<option value=\"2014\">2014</option>		</select><br><br>		</td></table><br><br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type=Submit value=\"Submit\" onclick=\"return validate()\"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type=reset value=\"Reset\"> </form></td>");	
				pw.println(myIndex.section6+"Employee"+myIndex.section7+date+myIndex.section8+time+myIndex.section9);
			
			}
			else if(perarray[0]=='a')
			{
				//admin
				pw.println(myIndex.section1+myIndex.employee);
				String abc="<td valign=top width=580><script language=Javascript>function validate(){	var myform = document.form1;		if(myform.bank.value==\"Select One\"){		alert(\"Bank is the required Field\");		return false;	}	if(myform.idno.value==\"\")	{		alert(\"ID Number is the required Field\");		return false;	}	if(myform.ACno.value==\"\")	{		alert(\"Account Number is the required Field\");		return false;	}	if(myform.Guarantee.value==\"Select One\")	{		alert(\"Select any one Guarantee\");		return false;	}	if(myform.Guaranteetype.value==\"Select One\")	{		alert(\"Select any one Guarantee Letter Type\");		return false;	}		if(myform.Amount.value==\"\")	{		alert(\"Amount is the required Field\");		return false;	}	if(myform.to.value==\"\")	{		alert(\"To is the required Field\");		return false;	}	if(myform.maxlimit.value==\"\")	{		alert(\"Maximum Limit is the required Field\");		return false;	}		if(myform.day1.value==\"dd\")	{		alert(\"Select any Day\");		return false;	}	if(myform.month1.value==\"mm\")	{		alert(\"Select any Month\");		return false;	}	if(myform.year1.value==\"yyyy\")	{		alert(\"Select any Year\");		return false;	}	}</script>";
				pw.println(abc+"<br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font size=\"6\">Letter of Guarantee</font>          <br><br><form action=\"LOGissued\" method=\"POST\" name=form1><table>	<td width=200>	<b>Guarantee:</b><br><br>	<b>Type of Guarantee Letter:</b><br><br>	<b>Bank:</b><br><br>	<b>Maximum Limit:</b><br><br>	<b>ID Number:</b><br><br>	<b>Account Number:</b><br><br>	<br><br>	<b>To:</b><br><br>	<b>Valid Till:</b><br><br>		</td width=200>	<td>	<select name=Guarantee>	<option value=\"Select One\">Select One</option>	<option value=\"deposit\">Guaranteed Deposit</option>	<option value=\"mortgage\">Mortgage</option>	</select><br><br>		<select name=Guaranteetype>	<option value=\"Select One\">Select One</option>	<option value=\"tender\">Tender Guarantee</option>	<option value=\"performance\">Performance Guarantee</option>	<option value=\"advance\">Advance Payment Guarantee</option>	<option value=\"maintenance\">Maintenance Guarantee</option>	<option value=\"Retention\">Retention Guarantee</option>	</select><br><br>		<select name=bank><option value=\"Select One\">Select One</option>");
				query="select * from branch";
				rset=stmt.executeQuery(query);
				while(rset.next())
				{
					pw.println("<option value=\""+rset.getString(3)+"\">"+rset.getString(3)+"</option>");
				}
				pw.println("</select><br><br>	<input type=\"text\" name=maxlimit maxlength=20><br><br>	<input type=\"text\" name=idno maxlength=20><br><br>	<input type=\"text\" name=ACno maxlength=20><br><br>		<br>	<input type=\"text\" name=to maxlength=20><br><br>		<select name=\"day1\">		<option value=\"dd\">dd</option>		<option value=\"1\">01</option>		<option value=\"2\">02</option>		<option value=\"3\">03</option>		<option value=\"4\">04</option>		<option value=\"5\">05</option>		<option value=\"6\">06</option>		<option value=\"7\">07</option>		<option value=\"8\">08</option>		<option value=\"9\">09</option>		<option value=\"10\">10</option>		<option value=\"11\">11</option>		<option value=\"12\">12</option>		<option value=\"13\">13</option>		<option value=\"14\">14</option>		<option value=\"15\">15</option>		<option value=\"16\">16</option>		<option value=\"17\">17</option>		<option value=\"18\">18</option>		<option value=\"19\">19</option>		<option value=\"20\">20</option>		<option value=\"21\">21</option>		<option value=\"22\">22</option>		<option value=\"23\">23</option>		<option value=\"24\">24</option>		<option value=\"25\">25</option>		<option value=\"26\">26</option>		<option value=\"27\">27</option>		<option value=\"28\">28</option>		<option value=\"29\">29</option>		<option value=\"30\">30</option>		<option value=\"31\">31</option>		</select>		<select name=\"month1\" >				<option value=\"mm\">mm</option>				<option value=\"1\">01</option>				<option value=\"2\">02</option>				<option value=\"3\">03</option>				<option value=\"4\">04</option>				<option value=\"5\">05</option>				<option value=\"6\">06</option>				<option value=\"7\">07</option>				<option value=\"8\">08</option>				<option value=\"9\">09</option>				<option value=\"10\">10</option>				<option value=\"11\">11</option>				<option value=\"12\">12</option>		</select>				<select name =\"year1\" >				<option value=\"yyyy\">yyyy</option>				<option value=\"2000\">2000</option>				<option value=\"2001\">2001</option>				<option value=\"2002\">2002</option>				<option value=\"2003\">2003</option>				<option value=\"2004\">2004</option>				<option value=\"2005\">2005</option>				<option value=\"2006\">2006</option>				<option value=\"2007\">2007</option>				<option value=\"2008\">2008</option>				<option value=\"2009\">2009</option>				<option value=\"2010\">2010</option>				<option value=\"2011\">2011</option>				<option value=\"2012\">2012</option>				<option value=\"2013\">2013</option>				<option value=\"2014\">2014</option>		</select><br><br>		</td></table><br><br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type=Submit value=\"Submit\" onclick=\"return validate()\"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type=reset value=\"Reset\"> </form></td>");	
				pw.println(myIndex.section6+"Employee"+myIndex.section7+date+myIndex.section8+time+myIndex.section9);
			
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
				String abc="<td valign=top width=580><script language=Javascript>function validate(){	var myform = document.form1;		if(myform.bank.value==\"Select One\"){		alert(\"Bank is the required Field\");		return false;	}	if(myform.idno.value==\"\")	{		alert(\"ID Number is the required Field\");		return false;	}	if(myform.ACno.value==\"\")	{		alert(\"Account Number is the required Field\");		return false;	}	if(myform.Guarantee.value==\"Select One\")	{		alert(\"Select any one Guarantee\");		return false;	}	if(myform.Guaranteetype.value==\"Select One\")	{		alert(\"Select any one Guarantee Letter Type\");		return false;	}		if(myform.Amount.value==\"\")	{		alert(\"Amount is the required Field\");		return false;	}	if(myform.to.value==\"\")	{		alert(\"To is the required Field\");		return false;	}	if(myform.maxlimit.value==\"\")	{		alert(\"Maximum Limit is the required Field\");		return false;	}		if(myform.day1.value==\"dd\")	{		alert(\"Select any Day\");		return false;	}	if(myform.month1.value==\"mm\")	{		alert(\"Select any Month\");		return false;	}	if(myform.year1.value==\"yyyy\")	{		alert(\"Select any Year\");		return false;	}	}</script>";
				pw.println(abc+"<br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font size=\"6\">Letter of Guarantee</font>          <br><br><form action=\"LOGissued\" method=\"POST\" name=form1><table>	<td width=200>	<b>Guarantee:</b><br><br>	<b>Type of Guarantee Letter:</b><br><br>	<b>Bank:</b><br><br>	<b>Maximum Limit:</b><br><br>	<b>ID Number:</b><br><br>	<b>Account Number:</b><br><br>	<br><br>	<b>To:</b><br><br>	<b>Valid Till:</b><br><br>		</td width=200>	<td>	<select name=Guarantee>	<option value=\"Select One\">Select One</option>	<option value=\"deposit\">Guaranteed Deposit</option>	<option value=\"mortgage\">Mortgage</option>	</select><br><br>		<select name=Guaranteetype>	<option value=\"Select One\">Select One</option>	<option value=\"tender\">Tender Guarantee</option>	<option value=\"performance\">Performance Guarantee</option>	<option value=\"advance\">Advance Payment Guarantee</option>	<option value=\"maintenance\">Maintenance Guarantee</option>	<option value=\"Retention\">Retention Guarantee</option>	</select><br><br>		<select name=bank><option value=\"Select One\">Select One</option>");
				query="select * from branch";
				rset=stmt.executeQuery(query);
				while(rset.next())
				{
					pw.println("<option value=\""+rset.getString(3)+"\">"+rset.getString(3)+"</option>");
				}
				pw.println("</select><br><br>	<input type=\"text\" name=maxlimit maxlength=20><br><br>	<input type=\"text\" name=idno maxlength=20><br><br>	<input type=\"text\" name=ACno maxlength=20><br><br>		<br>	<input type=\"text\" name=to maxlength=20><br><br>		<select name=\"day1\">		<option value=\"dd\">dd</option>		<option value=\"1\">01</option>		<option value=\"2\">02</option>		<option value=\"3\">03</option>		<option value=\"4\">04</option>		<option value=\"5\">05</option>		<option value=\"6\">06</option>		<option value=\"7\">07</option>		<option value=\"8\">08</option>		<option value=\"9\">09</option>		<option value=\"10\">10</option>		<option value=\"11\">11</option>		<option value=\"12\">12</option>		<option value=\"13\">13</option>		<option value=\"14\">14</option>		<option value=\"15\">15</option>		<option value=\"16\">16</option>		<option value=\"17\">17</option>		<option value=\"18\">18</option>		<option value=\"19\">19</option>		<option value=\"20\">20</option>		<option value=\"21\">21</option>		<option value=\"22\">22</option>		<option value=\"23\">23</option>		<option value=\"24\">24</option>		<option value=\"25\">25</option>		<option value=\"26\">26</option>		<option value=\"27\">27</option>		<option value=\"28\">28</option>		<option value=\"29\">29</option>		<option value=\"30\">30</option>		<option value=\"31\">31</option>		</select>		<select name=\"month1\" >				<option value=\"mm\">mm</option>				<option value=\"1\">01</option>				<option value=\"2\">02</option>				<option value=\"3\">03</option>				<option value=\"4\">04</option>				<option value=\"5\">05</option>				<option value=\"6\">06</option>				<option value=\"7\">07</option>				<option value=\"8\">08</option>				<option value=\"9\">09</option>				<option value=\"10\">10</option>				<option value=\"11\">11</option>				<option value=\"12\">12</option>		</select>				<select name =\"year1\" >				<option value=\"yyyy\">yyyy</option>				<option value=\"2000\">2000</option>				<option value=\"2001\">2001</option>				<option value=\"2002\">2002</option>				<option value=\"2003\">2003</option>				<option value=\"2004\">2004</option>				<option value=\"2005\">2005</option>				<option value=\"2006\">2006</option>				<option value=\"2007\">2007</option>				<option value=\"2008\">2008</option>				<option value=\"2009\">2009</option>				<option value=\"2010\">2010</option>				<option value=\"2011\">2011</option>				<option value=\"2012\">2012</option>				<option value=\"2013\">2013</option>				<option value=\"2014\">2014</option>		</select><br><br>		</td></table><br><br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type=Submit value=\"Submit\" onclick=\"return validate()\"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type=reset value=\"Reset\"> </form></td>");	
				pw.println(myIndex.section6+"Employee"+myIndex.section7+date+myIndex.section8+time+myIndex.section9);
			
			}
			else if(perarray[0]=='a')
			{
				//admin
				pw.println(myIndex.section1+myIndex.employee);
				String abc="<td valign=top width=580><script language=Javascript>function validate(){	var myform = document.form1;		if(myform.bank.value==\"Select One\"){		alert(\"Bank is the required Field\");		return false;	}	if(myform.idno.value==\"\")	{		alert(\"ID Number is the required Field\");		return false;	}	if(myform.ACno.value==\"\")	{		alert(\"Account Number is the required Field\");		return false;	}	if(myform.Guarantee.value==\"Select One\")	{		alert(\"Select any one Guarantee\");		return false;	}	if(myform.Guaranteetype.value==\"Select One\")	{		alert(\"Select any one Guarantee Letter Type\");		return false;	}		if(myform.Amount.value==\"\")	{		alert(\"Amount is the required Field\");		return false;	}	if(myform.to.value==\"\")	{		alert(\"To is the required Field\");		return false;	}	if(myform.maxlimit.value==\"\")	{		alert(\"Maximum Limit is the required Field\");		return false;	}		if(myform.day1.value==\"dd\")	{		alert(\"Select any Day\");		return false;	}	if(myform.month1.value==\"mm\")	{		alert(\"Select any Month\");		return false;	}	if(myform.year1.value==\"yyyy\")	{		alert(\"Select any Year\");		return false;	}	}</script>";
				pw.println(abc+"<br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font size=\"6\">Letter of Guarantee</font>          <br><br><form action=\"LOGissued\" method=\"POST\" name=form1><table>	<td width=200>	<b>Guarantee:</b><br><br>	<b>Type of Guarantee Letter:</b><br><br>	<b>Bank:</b><br><br>	<b>Maximum Limit:</b><br><br>	<b>ID Number:</b><br><br>	<b>Account Number:</b><br><br>	<br><br>	<b>To:</b><br><br>	<b>Valid Till:</b><br><br>		</td width=200>	<td>	<select name=Guarantee>	<option value=\"Select One\">Select One</option>	<option value=\"deposit\">Guaranteed Deposit</option>	<option value=\"mortgage\">Mortgage</option>	</select><br><br>		<select name=Guaranteetype>	<option value=\"Select One\">Select One</option>	<option value=\"tender\">Tender Guarantee</option>	<option value=\"performance\">Performance Guarantee</option>	<option value=\"advance\">Advance Payment Guarantee</option>	<option value=\"maintenance\">Maintenance Guarantee</option>	<option value=\"Retention\">Retention Guarantee</option>	</select><br><br>		<select name=bank><option value=\"Select One\">Select One</option>");
				query="select * from branch";
				rset=stmt.executeQuery(query);
				while(rset.next())
				{
					pw.println("<option value=\""+rset.getString(3)+"\">"+rset.getString(3)+"</option>");
				}
				pw.println("</select><br><br>	<input type=\"text\" name=maxlimit maxlength=20><br><br>	<input type=\"text\" name=idno maxlength=20><br><br>	<input type=\"text\" name=ACno maxlength=20><br><br>		<br>	<input type=\"text\" name=to maxlength=20><br><br>		<select name=\"day1\">		<option value=\"dd\">dd</option>		<option value=\"1\">01</option>		<option value=\"2\">02</option>		<option value=\"3\">03</option>		<option value=\"4\">04</option>		<option value=\"5\">05</option>		<option value=\"6\">06</option>		<option value=\"7\">07</option>		<option value=\"8\">08</option>		<option value=\"9\">09</option>		<option value=\"10\">10</option>		<option value=\"11\">11</option>		<option value=\"12\">12</option>		<option value=\"13\">13</option>		<option value=\"14\">14</option>		<option value=\"15\">15</option>		<option value=\"16\">16</option>		<option value=\"17\">17</option>		<option value=\"18\">18</option>		<option value=\"19\">19</option>		<option value=\"20\">20</option>		<option value=\"21\">21</option>		<option value=\"22\">22</option>		<option value=\"23\">23</option>		<option value=\"24\">24</option>		<option value=\"25\">25</option>		<option value=\"26\">26</option>		<option value=\"27\">27</option>		<option value=\"28\">28</option>		<option value=\"29\">29</option>		<option value=\"30\">30</option>		<option value=\"31\">31</option>		</select>		<select name=\"month1\" >				<option value=\"mm\">mm</option>				<option value=\"1\">01</option>				<option value=\"2\">02</option>				<option value=\"3\">03</option>				<option value=\"4\">04</option>				<option value=\"5\">05</option>				<option value=\"6\">06</option>				<option value=\"7\">07</option>				<option value=\"8\">08</option>				<option value=\"9\">09</option>				<option value=\"10\">10</option>				<option value=\"11\">11</option>				<option value=\"12\">12</option>		</select>				<select name =\"year1\" >				<option value=\"yyyy\">yyyy</option>				<option value=\"2000\">2000</option>				<option value=\"2001\">2001</option>				<option value=\"2002\">2002</option>				<option value=\"2003\">2003</option>				<option value=\"2004\">2004</option>				<option value=\"2005\">2005</option>				<option value=\"2006\">2006</option>				<option value=\"2007\">2007</option>				<option value=\"2008\">2008</option>				<option value=\"2009\">2009</option>				<option value=\"2010\">2010</option>				<option value=\"2011\">2011</option>				<option value=\"2012\">2012</option>				<option value=\"2013\">2013</option>				<option value=\"2014\">2014</option>		</select><br><br>		</td></table><br><br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type=Submit value=\"Submit\" onclick=\"return validate()\"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type=reset value=\"Reset\"> </form></td>");	
				pw.println(myIndex.section6+"Employee"+myIndex.section7+date+myIndex.section8+time+myIndex.section9);
			
			}
			 }
			catch(Exception ex)
			{
				
			}
		}
	}   	  	    
}