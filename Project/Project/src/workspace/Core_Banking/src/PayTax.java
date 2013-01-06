

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class for Servlet: PayTax
 *
 */
 public class PayTax extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public PayTax() {
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
			}
			else if(perarray[0]=='e')
			{
				//employee
				
				pw.println(myIndex.section1+myIndex.employee);
			}
			else if(perarray[0]=='a')
			{
				//admin
				
				pw.println(myIndex.section1+myIndex.admin);
			}
				 String billtype=request.getParameter("taxtype");
			        String billnum=request.getParameter("bill");
			        String billamount=request.getParameter("amt");
			        String id=request.getParameter("idnum");
			        query = "select * from holder where holder.user='"+per+"' and holder.idnum='"+id+"'";
					 rset = stmt.executeQuery(query); 
					 boolean enter=false;
					 while(rset.next() )
					 {
						 enter=true;
						 break;
				     }
					 if(!enter)
					 {
						 String abc="<td valign=top width=580><SCRIPT LANGUAGE=JavaScript>function validate(){   var myForm = document.form1;   if (myForm.taxtype.value ==\"Select One\")   {      alert(\"Select Type of Tax\");    	return false;     }if (myForm.bill.value ==\"\")   {      alert(\"Enter your Tax Number\");    	return false;     }if (myForm.amt.value ==\"\")   {      alert(\"Enter Tax Amount\");    	return false;     }if (myForm.idnum.value ==\"\")   {      alert(\"Enter Your ID Proof\");    	return false;     }}</SCRIPT>";
							pw.println(abc+"<font color=\"BLUE\" size=3><B>Invalid Information</b></font><br><br><form action=\"PayTax\" method=\"POST\" name=form1><font size=\"3\">Taxes</font>          <br><br><table>	<td width=200>	<b>Tax Type:</b><br><br>	<b>Tax No.:</b><br><br>	<b>Amount:</b><br><br><b>ID Proof:</b><br><br>	</td>	<td>	<select name=taxtype>	<option value=\"Select One\">Select One</option>	<option value=\"Income Tax\">Income Tax</option>	<option value=\"Corporate Tax\">Corporate Tax</option>	<option value=\"Sales Tax\">Sales Tax</option>	<option value=\"Excise Duty\">Excise Duty</option><option value=\"Custom Duty\">Custom Duty</option><option value=\"Value Added Tax\">Value Added Tax</option><option value=\"Capital Gains Tax\">Capital Gains Tax</option></select><br><br><input type=\"text\" name=bill maxlength=20><br><br><input type=\"text\" name=amt maxlength=20><br><br>	<input type=\"text\" name=idnum maxlength=20><br><br><input type=reset value=\"Reset\" name=btnd2>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type=\"Submit\" value=\"Pay\" name=btnd1 onclick=\"return validate()\">	</td></table> </form></td>");
								
					 }
					 else
						 {
			        query = "select * from account where account.user='"+per+"'";
			        rset=stmt.executeQuery(query);
			        rset.next();
			        if((int)(rset.getFloat(6)) > Integer.parseInt(billamount) + 500)
					{
					   Date dt = new Date();
					   String date1="",month="",year="";
					
					   if(dt.getDate()<10)
						date1+="0"+dt.getDate();
					   else
						date1+=dt.getDate();
					   int m=dt.getMonth()+1;
					
					   if(m<10)
						month+="0"+m;
					   else
						month+=m;
					   int y=dt.getYear()+1900;
					   year+=y;
					   int fee=Integer.parseInt(billamount);
					   query= "insert into taxes values('"+per+"','"+billtype+"','"+billnum+"')";
					   stmt.executeUpdate(query);
					   query = "update account set account.balance=account.balance-"+fee+" where account.user='"+per+"'";
					   stmt.executeUpdate(query);
					   query = "select * from cnt where cnt.name='TransID'";
					   rset=stmt.executeQuery(query);
					   rset.next();
					   int TID=rset.getInt(2) +1;
					   query = "update cnt set cnt.cnt1=cnt.cnt1+1 where cnt.name='TransID'";
					   stmt.executeUpdate(query);
					   //debit(user varchar(15),damount float,dod date,idtpe varchar(20),idno varchar(20),transid1 varchar(20),empid varchar(15))
					   query="insert into debit values('"+per+"',"+fee+".00,'"+date1+"."+month+"."+year+"','To Tax "+billnum+"','"+id+"','"+TID+"','Self')";
					   stmt.executeUpdate(query);  
					   pw.println("<td valign=\"top\"><b><font size=3><u>Tax Receipt</b></u></font> <br><br><br><br><table> <tr>	<td style=\"border:1px #000000 solid\">Paid By :"+per+" (with reference to bank) </td></tr> <tr>	<td style=\"border:1px #000000 solid\">Type Of Tax:"+billtype+" </td>	</tr>	<tr><td style=\"border:1px #000000 solid\">	Tax No:"+billnum+" </td>	</tr>	<tr><td style=\"border:1px #000000 solid\">	Tax Amount Paid:"+billamount+" </td>	</tr>	<tr >	<td style=\"border:1px #000000 solid\">	Date of Payment:"+date1+"/"+month+"/"+year+"	</td></tr>	<tr><td style=\"border:1px #000000 solid\">	Comments: Paid to & Approved By Core Banking Solutions Bank	</td></tr>	  </table> </td>");
					 
					}
			        else
			        {
			        	String abc="<td valign=top width=580><SCRIPT LANGUAGE=JavaScript>function validate(){   var myForm = document.form1;   if (myForm.taxtype.value ==\"Select One\")   {      alert(\"Select Type of Tax\");    	return false;     }if (myForm.bill.value ==\"\")   {      alert(\"Enter your Tax Number\");    	return false;     }if (myForm.amt.value ==\"\")   {      alert(\"Enter Tax Amount\");    	return false;     }if (myForm.idnum.value ==\"\")   {      alert(\"Enter Your ID Proof\");    	return false;     }}</SCRIPT>";
						pw.println(abc+"<font color=\"BLUE\" size=3><B>Insufficient Balance</b></font><br><br><form action=\"PayTax\" method=\"POST\" name=form1><font size=\"3\">Taxes</font>          <br><br><table>	<td width=200>	<b>Tax Type:</b><br><br>	<b>Tax No.:</b><br><br>	<b>Amount:</b><br><br><b>ID Proof:</b><br><br>	</td>	<td>	<select name=taxtype>	<option value=\"Select One\">Select One</option>	<option value=\"Income Tax\">Income Tax</option>	<option value=\"Corporate Tax\">Corporate Tax</option>	<option value=\"Sales Tax\">Sales Tax</option>	<option value=\"Excise Duty\">Excise Duty</option><option value=\"Custom Duty\">Custom Duty</option><option value=\"Value Added Tax\">Value Added Tax</option><option value=\"Capital Gains Tax\">Capital Gains Tax</option></select><br><br><input type=\"text\" name=bill maxlength=20><br><br><input type=\"text\" name=amt maxlength=20><br><br>	<input type=\"text\" name=idnum maxlength=20><br><br><input type=reset value=\"Reset\" name=btnd2>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type=\"Submit\" value=\"Pay\" name=btnd1 onclick=\"return validate()\">	</td></table> </form></td>");
					
			        }
						 }
			        
			//	String abc="<td valign=top width=580><SCRIPT LANGUAGE=JavaScript>function validate(){   var myForm = document.form1;   if (myForm.taxtype.value ==\"Select One\")   {      alert(\"Select Type of Bill\");    	return false;     }if (myForm.bill.value ==\"\")   {      alert(\"Enter your Bill Number\");    	return false;     }if (myForm.amt.value ==\"\")   {      alert(\"Enter Bill Amount\");    	return false;     }}</SCRIPT>";
				//pw.println(abc+"<br><br><form action=\"PayBill\" method=\"GET\" name=form1><font size=\"3\">Bills</font>          <br><br><table>	<td width=200>	<b>Bill Type:</b><br><br>	<b>Bill No.:</b><br><br>	<b>Bill Amount:</b><br><br>	</td>	<td>	<select name=taxtype>	<option value=\"Select One\">Select One</option>	<option value=\"electricity\">Electricity</option>	<option value=\"phone\">Phone Bill</option>	<option value=\"water\">Water Bill</option>	</select><br><br><input type=\"text\" name=bill maxlength=20><br><br>	<input type=\"text\" name=amt maxlength=20><br><br><input type=reset value=\"Reset\" name=btnd2>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type=\"Submit\" value=\"Pay Bill\" name=btnd1 onclick=\"return validate()\">	</td></table> </form></td>");
					 if(perarray[0]=='c')
					 pw.println(myIndex.section6+"Customer"+myIndex.section7+date+myIndex.section8+time+myIndex.section9);
					 else if(perarray[0]=='e')
						 pw.println(myIndex.section6+"Employee"+myIndex.section7+date+myIndex.section8+time+myIndex.section9);
					 else if(perarray[0]=='a')
						 pw.println(myIndex.section6+"Administrator"+myIndex.section7+date+myIndex.section8+time+myIndex.section9);
			
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
			}
			else if(perarray[0]=='e')
			{
				//employee
				
				pw.println(myIndex.section1+myIndex.employee);
			}
			else if(perarray[0]=='c')
			{
				//admin
				
				pw.println(myIndex.section1+myIndex.admin);
			}
				 String billtype=request.getParameter("taxtype");
			        String billnum=request.getParameter("bill");
			        String billamount=request.getParameter("amt");
			        String id=request.getParameter("idnum");
			        query = "select * from holder where holder.user='"+per+"' and holder.idnum='"+id+"'";
					 rset = stmt.executeQuery(query); 
					 boolean enter=false;
					 while(rset.next() )
					 {
						 enter=true;
						 break;
				     }
					 if(!enter)
					 {
						 String abc="<td valign=top width=580><SCRIPT LANGUAGE=JavaScript>function validate(){   var myForm = document.form1;   if (myForm.taxtype.value ==\"Select One\")   {      alert(\"Select Type of Tax\");    	return false;     }if (myForm.bill.value ==\"\")   {      alert(\"Enter your Tax Number\");    	return false;     }if (myForm.amt.value ==\"\")   {      alert(\"Enter Tax Amount\");    	return false;     }if (myForm.idnum.value ==\"\")   {      alert(\"Enter Your ID Proof\");    	return false;     }}</SCRIPT>";
							pw.println(abc+"<font color=\"BLUE\" size=3><B>Invalid Information</b></font><br><br><form action=\"PayTax\" method=\"POST\" name=form1><font size=\"3\">Taxes</font>          <br><br><table>	<td width=200>	<b>Tax Type:</b><br><br>	<b>Tax No.:</b><br><br>	<b>Amount:</b><br><br><b>ID Proof:</b><br><br>	</td>	<td>	<select name=taxtype>	<option value=\"Select One\">Select One</option>	<option value=\"Income Tax\">Income Tax</option>	<option value=\"Corporate Tax\">Corporate Tax</option>	<option value=\"Sales Tax\">Sales Tax</option>	<option value=\"Excise Duty\">Excise Duty</option><option value=\"Custom Duty\">Custom Duty</option><option value=\"Value Added Tax\">Value Added Tax</option><option value=\"Capital Gains Tax\">Capital Gains Tax</option></select><br><br><input type=\"text\" name=bill maxlength=20><br><br><input type=\"text\" name=amt maxlength=20><br><br>	<input type=\"text\" name=idnum maxlength=20><br><br><input type=reset value=\"Reset\" name=btnd2>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type=\"Submit\" value=\"Pay\" name=btnd1 onclick=\"return validate()\">	</td></table> </form></td>");
								
					 }
					 else
						 {
			        query = "select * from account where account.user='"+per+"'";
			        rset=stmt.executeQuery(query);
			        rset.next();
			        if((int)(rset.getFloat(6)) > Integer.parseInt(billamount) + 500)
					{
					   Date dt = new Date();
					   String date1="",month="",year="";
					
					   if(dt.getDate()<10)
						date1+="0"+dt.getDate();
					   else
						date1+=dt.getDate();
					   int m=dt.getMonth()+1;
					
					   if(m<10)
						month+="0"+m;
					   else
						month+=m;
					   int y=dt.getYear()+1900;
					   year+=y;
					   int fee=Integer.parseInt(billamount);
					   query= "insert into taxes values('"+per+"','"+billtype+"','"+billnum+"')";
					   stmt.executeUpdate(query);
					   query = "update account set account.balance=account.balance-"+fee+" where account.user='"+per+"'";
					   stmt.executeUpdate(query);
					   query = "select * from cnt where cnt.name='TransID'";
					   rset=stmt.executeQuery(query);
					   rset.next();
					   int TID=rset.getInt(2) +1;
					   query = "update cnt set cnt.cnt1=cnt.cnt1+1 where cnt.name='TransID'";
					   stmt.executeUpdate(query);
					   //debit(user varchar(15),damount float,dod date,idtpe varchar(20),idno varchar(20),transid1 varchar(20),empid varchar(15))
					   query="insert into debit values('"+per+"',"+fee+".00,'"+date1+"."+month+"."+year+"','To Tax "+billnum+"','"+id+"','"+TID+"','Self')";
					   stmt.executeUpdate(query);  
					   pw.println("<td valign=\"top\"><b><font size=3><u>Tax Receipt</b></u></font> <br><br><br><br><table> <tr>	<td style=\"border:1px #000000 solid\">Paid By :"+per+" (with reference to bank) </td></tr> <tr>	<td style=\"border:1px #000000 solid\">Type Of Tax:"+billtype+" </td>	</tr>	<tr><td style=\"border:1px #000000 solid\">	Tax No:"+billnum+" </td>	</tr>	<tr><td style=\"border:1px #000000 solid\">	Tax Amount Paid:"+billamount+" </td>	</tr>	<tr >	<td style=\"border:1px #000000 solid\">	Date of Payment:"+date1+"/"+month+"/"+year+"	</td></tr>	<tr><td style=\"border:1px #000000 solid\">	Comments: Paid to & Approved By Core Banking Solutions Bank	</td></tr>	  </table> </td>");
					 
					}
			        else
			        {
			        	String abc="<td valign=top width=580><SCRIPT LANGUAGE=JavaScript>function validate(){   var myForm = document.form1;   if (myForm.taxtype.value ==\"Select One\")   {      alert(\"Select Type of Tax\");    	return false;     }if (myForm.bill.value ==\"\")   {      alert(\"Enter your Tax Number\");    	return false;     }if (myForm.amt.value ==\"\")   {      alert(\"Enter Tax Amount\");    	return false;     }if (myForm.idnum.value ==\"\")   {      alert(\"Enter Your ID Proof\");    	return false;     }}</SCRIPT>";
						pw.println(abc+"<font color=\"BLUE\" size=3><B>Insufficient Balance</b></font><br><br><form action=\"PayTax\" method=\"POST\" name=form1><font size=\"3\">Taxes</font>          <br><br><table>	<td width=200>	<b>Tax Type:</b><br><br>	<b>Tax No.:</b><br><br>	<b>Amount:</b><br><br><b>ID Proof:</b><br><br>	</td>	<td>	<select name=taxtype>	<option value=\"Select One\">Select One</option>	<option value=\"Income Tax\">Income Tax</option>	<option value=\"Corporate Tax\">Corporate Tax</option>	<option value=\"Sales Tax\">Sales Tax</option>	<option value=\"Excise Duty\">Excise Duty</option><option value=\"Custom Duty\">Custom Duty</option><option value=\"Value Added Tax\">Value Added Tax</option><option value=\"Capital Gains Tax\">Capital Gains Tax</option></select><br><br><input type=\"text\" name=bill maxlength=20><br><br><input type=\"text\" name=amt maxlength=20><br><br>	<input type=\"text\" name=idnum maxlength=20><br><br><input type=reset value=\"Reset\" name=btnd2>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type=\"Submit\" value=\"Pay\" name=btnd1 onclick=\"return validate()\">	</td></table> </form></td>");
					
			        }
						 }
			        
			//	String abc="<td valign=top width=580><SCRIPT LANGUAGE=JavaScript>function validate(){   var myForm = document.form1;   if (myForm.taxtype.value ==\"Select One\")   {      alert(\"Select Type of Bill\");    	return false;     }if (myForm.bill.value ==\"\")   {      alert(\"Enter your Bill Number\");    	return false;     }if (myForm.amt.value ==\"\")   {      alert(\"Enter Bill Amount\");    	return false;     }}</SCRIPT>";
				//pw.println(abc+"<br><br><form action=\"PayBill\" method=\"GET\" name=form1><font size=\"3\">Bills</font>          <br><br><table>	<td width=200>	<b>Bill Type:</b><br><br>	<b>Bill No.:</b><br><br>	<b>Bill Amount:</b><br><br>	</td>	<td>	<select name=taxtype>	<option value=\"Select One\">Select One</option>	<option value=\"electricity\">Electricity</option>	<option value=\"phone\">Phone Bill</option>	<option value=\"water\">Water Bill</option>	</select><br><br><input type=\"text\" name=bill maxlength=20><br><br>	<input type=\"text\" name=amt maxlength=20><br><br><input type=reset value=\"Reset\" name=btnd2>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type=\"Submit\" value=\"Pay Bill\" name=btnd1 onclick=\"return validate()\">	</td></table> </form></td>");
					 if(perarray[0]=='c')
					 pw.println(myIndex.section6+"Customer"+myIndex.section7+date+myIndex.section8+time+myIndex.section9);
					 else if(perarray[0]=='e')
						 pw.println(myIndex.section6+"Employee"+myIndex.section7+date+myIndex.section8+time+myIndex.section9);
					 else if(perarray[0]=='a')
						 pw.println(myIndex.section6+"Administrator"+myIndex.section7+date+myIndex.section8+time+myIndex.section9);
			
			 }
			catch(Exception ex)
			{
				
			}
		}
	}   	  	    
}