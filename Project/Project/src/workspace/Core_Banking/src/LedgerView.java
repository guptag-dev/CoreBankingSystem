

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
 * Servlet implementation class for Servlet: LedgerView
 *
 */
 public class LedgerView extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public LedgerView() {
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
				String acnum = request.getParameter("acnumber");
				String ddfrom = request.getParameter("ddfrom");
				String mmfrom = request.getParameter("mmfrom");
				String yyfrom = request.getParameter("yyfrom");
				String ddto = request.getParameter("ddto");
				String mmto = request.getParameter("mmto");
				String yyto = request.getParameter("yyto");
				String per1="";
			 if(perarray[0]=='e')
			{
				pw.println(myIndex.section1+myIndex.employee);
				query="select * from holder where holder.user='"+per+"'";
				rset=stmt.executeQuery(query);
				if(rset.next())
				{
				
				pw.println("<td valign=\"top\"><font size=2><b><u>General Information</u></b></font>    <br><br><table>	<td align='right' width=200>		User-ID:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Account No.:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Account Nature:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Account Type:&nbsp;&nbsp;&nbsp;&nbsp;<br>      		No.of Holders:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Available Balance:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Guarantee 1:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Guarantee 2:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Account Opening Date:&nbsp;&nbsp;&nbsp;&nbsp;<br>		</td><td valign=\"top\"><b>");
				query="select * from account where account.accno="+acnum;
				rset=stmt.executeQuery(query);
				while(rset.next())
				{
					per1=rset.getString(1);
					pw.println(rset.getString(1)+"<br>"+rset.getLong(2)+"<br>"+rset.getString(3)+"<br>"+rset.getString(4)+"<br>"+rset.getShort(5)+"<br>"+rset.getFloat(6)+"<br>"+rset.getString(7)+"<br>"+rset.getString(8)+"<br>"+rset.getDate(9)+"</td></table><br><br>");
					break;
				}
				String part1="<font size=\"2\"><b><u>Holder Information</u></b></font>         <br><br><table>	<td valign=\"top\">		Name:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Date of Birth:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Occupation:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Annual Income:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Street:&nbsp;&nbsp;&nbsp;&nbsp;<br>		City:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Country:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Zip:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Phone:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Email:&nbsp;&nbsp;&nbsp;&nbsp;<br>				Father's Name:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Father's Occupation:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Cheque Book Number:&nbsp;&nbsp;&nbsp;&nbsp;<br>Sex:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Marital Status:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Qualifications:&nbsp;&nbsp;&nbsp;&nbsp;<br>						</td>";
				pw.println(part1);
				query="select * from holder where holder.user='"+per1+"'";
				rset=stmt.executeQuery(query);
				while(rset.next())
				{
					pw.println("<td valign=\"top\"><B>"+rset.getString(3)+"<br>"+rset.getDate(4)+"<br>"+rset.getString(5)+"<br>"+rset.getFloat(6)+"<br>"+rset.getString(7)+"<br>"+rset.getString(8)+"<br>"+rset.getString(20)+"<br>"+rset.getInt(9)+"<br>"+rset.getString(10)+"<br>"+rset.getString(11)+"<br>"+rset.getString(14)+"<br>"+rset.getString(15)+"<br>"+rset.getString(16)+"<br>"+rset.getString(17)+"<br>"+rset.getString(18)+"<br>"+rset.getString(19)+"</B></td>");
					
				}
				pw.println("</table>");
				}
				
			}
			else if(perarray[0]=='a')
			{
				pw.println(myIndex.section1+myIndex.admin);
				query="select * from holder where holder.user='"+per+"'";
				rset=stmt.executeQuery(query);
				if(rset.next())
				{
				
				pw.println("<td valign=\"top\"><font size=2><b><u>General Information</u></b></font>    <br><br><table>	<td align='right' width=200>		User-ID:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Account No.:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Account Nature:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Account Type:&nbsp;&nbsp;&nbsp;&nbsp;<br>      		No.of Holders:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Available Balance:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Guarantee 1:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Guarantee 2:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Account Opening Date:&nbsp;&nbsp;&nbsp;&nbsp;<br>		</td><td valign=\"top\"><b>");
				query="select * from account where account.accno="+acnum;
				rset=stmt.executeQuery(query);
				while(rset.next())
				{
					per1=rset.getString(1);
					pw.println(rset.getString(1)+"<br>"+rset.getLong(2)+"<br>"+rset.getString(3)+"<br>"+rset.getString(4)+"<br>"+rset.getShort(5)+"<br>"+rset.getFloat(6)+"<br>"+rset.getString(7)+"<br>"+rset.getString(8)+"<br>"+rset.getDate(9)+"</td></table><br><br>");
					break;
				}
				String part1="<font size=\"2\"><b><u>Holder Information</u></b></font>         <br><br><table>	<td valign=\"top\">		Name:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Date of Birth:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Occupation:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Annual Income:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Street:&nbsp;&nbsp;&nbsp;&nbsp;<br>		City:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Country:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Zip:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Phone:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Email:&nbsp;&nbsp;&nbsp;&nbsp;<br>				Father's Name:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Father's Occupation:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Cheque Book Number:&nbsp;&nbsp;&nbsp;&nbsp;<br>Sex:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Marital Status:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Qualifications:&nbsp;&nbsp;&nbsp;&nbsp;<br>						</td>";
				pw.println(part1);
				query="select * from holder where holder.user='"+per1+"'";
				rset=stmt.executeQuery(query);
				while(rset.next())
				{
					pw.println("<td valign=\"top\"><B>"+rset.getString(3)+"<br>"+rset.getDate(4)+"<br>"+rset.getString(5)+"<br>"+rset.getFloat(6)+"<br>"+rset.getString(7)+"<br>"+rset.getString(8)+"<br>"+rset.getString(20)+"<br>"+rset.getInt(9)+"<br>"+rset.getString(10)+"<br>"+rset.getString(11)+"<br>"+rset.getString(14)+"<br>"+rset.getString(15)+"<br>"+rset.getString(16)+"<br>"+rset.getString(17)+"<br>"+rset.getString(18)+"<br>"+rset.getString(19)+"</B></td>");
					
				}
				pw.println("</table>");
			}
			}
			
			
			
			
			
				pw.println("<br>");
				{
					pw.println("<table ><tr><td width=120  style=\"border:1px #000000 solid\" bgcolor=#b5b5b5><b>Date</b></td><td width=120 style=\"border:1px #000000 solid\" bgcolor=#b5b5b5><b>Particulars</b></td><td width=120 style=\"border:1px #000000 solid\" bgcolor=#b5b5b5><b>Debits</b></td><td width=200 style=\"border:1px #000000 solid\" bgcolor=#b5b5b5  ><b>Credits</b></td><td width=120 style=\"border:1px #000000 solid\" bgcolor=#b5b5b5><b>Balance</b></td><td width=120 style=\"border:1px #000000 solid\" bgcolor=#b5b5b5><b>Transaction ID</b></td></tr>");
				}
				String query1 =" select * from credit where credit.user='"+per1+"'" ;
				String query2 =" select * from debit where debit.user='"+per1+"'"  ;
		        ResultSet rstcredit = stmt.executeQuery(query1);
		        
		        query="create table temp(datetemp Date,credittemp float,debittemp float,init varchar(40),tid3 varchar(20))";
		        stmt1.executeUpdate(query);
		        
		        while(rstcredit.next())
		        {
		        	//pw.println("<B>Entered");
		        	query="insert into temp values('"+rstcredit.getDate(3)+"',"+rstcredit.getFloat(2)+",0.00,'"+rstcredit.getString(4)+"','"+rstcredit.getString(5)+"')";
		        	stmt1.executeUpdate(query);
		        }
		        ResultSet rstdebit = stmt.executeQuery(query2);
		        while(rstdebit.next())
		        {
		        	query="insert into temp values('"+rstdebit.getDate(3)+"',0.00,"+rstdebit.getFloat(2)+",'"+rstdebit.getString(4)+" by "+rstdebit.getString(5)+"','"+rstdebit.getString(6)+"')";
		        	stmt1.executeUpdate(query);
		        }
		        query="select * from temp where temp.datetemp>='"+ddfrom+"."+mmfrom+"."+yyfrom+"' and temp.datetemp<='"+ddto+"."+mmto+"."+yyto+"' order by temp.datetemp";
		        rset=stmt.executeQuery(query);
		       
		        
		        while(rset.next())
		        {
		        	if(rset.getFloat(3)==0)
		        		balance=balance+rset.getFloat(2);
		        	else if(rset.getFloat(2)==0)
		        		balance=balance-rset.getFloat(3);
		        	pw.println("<tr> <td width=120 style=\"border:1px #000000 solid\" >"+rset.getDate(1)+"</td><td width=120 style=\"border:1px #000000 solid\" >"+rset.getString(4)+"</td><td width=120 style=\"border:1px #000000 solid\" >"+rset.getFloat(3)+"</td><td width=240 style=\"border:1px #000000 solid\"  >"+rset.getFloat(2)+"</td><td width=240 style=\"border:1px #000000 solid\"  >"+balance+"</td><td width=120 style=\"border:1px #000000 solid\" >"+rset.getString(5)+"</td> </tr>");
		        }
		        
		        query="drop table temp";
		        stmt.executeUpdate(query);
		        pw.println("</table><br><font size=4><br><br>Current Balance :<b>"+balance+"</b></td>");
		        if(perarray[0]=='c')
				pw.println(myIndex.section6+"Customer"+myIndex.section7+date+myIndex.section8+time+myIndex.section9);
		        else if(perarray[0]=='e')
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
				String acnum = request.getParameter("acnumber");
				String ddfrom = request.getParameter("ddfrom");
				String mmfrom = request.getParameter("mmfrom");
				String yyfrom = request.getParameter("yyfrom");
				String ddto = request.getParameter("ddto");
				String mmto = request.getParameter("mmto");
				String yyto = request.getParameter("yyto");
				String per1="";
			 if(perarray[0]=='e')
			{
				pw.println(myIndex.section1+myIndex.employee);
				query="select * from holder where holder.user='"+per+"'";
				rset=stmt.executeQuery(query);
				if(rset.next())
				{
				
				pw.println("<td valign=\"top\"><font size=2><b><u>General Information</u></b></font>    <br><br><table>	<td align='right' width=200>		User-ID:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Account No.:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Account Nature:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Account Type:&nbsp;&nbsp;&nbsp;&nbsp;<br>      		No.of Holders:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Available Balance:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Guarantee 1:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Guarantee 2:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Account Opening Date:&nbsp;&nbsp;&nbsp;&nbsp;<br>		</td><td valign=\"top\"><b>");
				query="select * from account where account.accno="+acnum;
				rset=stmt.executeQuery(query);
				while(rset.next())
				{
					per1=rset.getString(1);
					pw.println(rset.getString(1)+"<br>"+rset.getLong(2)+"<br>"+rset.getString(3)+"<br>"+rset.getString(4)+"<br>"+rset.getShort(5)+"<br>"+rset.getFloat(6)+"<br>"+rset.getString(7)+"<br>"+rset.getString(8)+"<br>"+rset.getDate(9)+"</td></table><br><br>");
					break;
				}
				String part1="<font size=\"2\"><b><u>Holder Information</u></b></font>         <br><br><table>	<td valign=\"top\">		Name:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Date of Birth:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Occupation:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Annual Income:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Street:&nbsp;&nbsp;&nbsp;&nbsp;<br>		City:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Country:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Zip:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Phone:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Email:&nbsp;&nbsp;&nbsp;&nbsp;<br>				Father's Name:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Father's Occupation:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Cheque Book Number:&nbsp;&nbsp;&nbsp;&nbsp;<br>Sex:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Marital Status:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Qualifications:&nbsp;&nbsp;&nbsp;&nbsp;<br>						</td>";
				pw.println(part1);
				query="select * from holder where holder.user='"+per1+"'";
				rset=stmt.executeQuery(query);
				while(rset.next())
				{
					pw.println("<td valign=\"top\"><B>"+rset.getString(3)+"<br>"+rset.getDate(4)+"<br>"+rset.getString(5)+"<br>"+rset.getFloat(6)+"<br>"+rset.getString(7)+"<br>"+rset.getString(8)+"<br>"+rset.getString(20)+"<br>"+rset.getInt(9)+"<br>"+rset.getString(10)+"<br>"+rset.getString(11)+"<br>"+rset.getString(14)+"<br>"+rset.getString(15)+"<br>"+rset.getString(16)+"<br>"+rset.getString(17)+"<br>"+rset.getString(18)+"<br>"+rset.getString(19)+"</B></td>");
					
				}
				pw.println("</table>");
				}
				
			}
			else if(perarray[0]=='a')
			{
				pw.println(myIndex.section1+myIndex.admin);
				query="select * from holder where holder.user='"+per+"'";
				rset=stmt.executeQuery(query);
				if(rset.next())
				{
				
				pw.println("<td valign=\"top\"><font size=2><b><u>General Information</u></b></font>    <br><br><table>	<td align='right' width=200>		User-ID:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Account No.:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Account Nature:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Account Type:&nbsp;&nbsp;&nbsp;&nbsp;<br>      		No.of Holders:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Available Balance:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Guarantee 1:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Guarantee 2:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Account Opening Date:&nbsp;&nbsp;&nbsp;&nbsp;<br>		</td><td valign=\"top\"><b>");
				query="select * from account where account.accno="+acnum;
				rset=stmt.executeQuery(query);
				while(rset.next())
				{
					per1=rset.getString(1);
					pw.println(rset.getString(1)+"<br>"+rset.getLong(2)+"<br>"+rset.getString(3)+"<br>"+rset.getString(4)+"<br>"+rset.getShort(5)+"<br>"+rset.getFloat(6)+"<br>"+rset.getString(7)+"<br>"+rset.getString(8)+"<br>"+rset.getDate(9)+"</td></table><br><br>");
					break;
				}
				String part1="<font size=\"2\"><b><u>Holder Information</u></b></font>         <br><br><table>	<td valign=\"top\">		Name:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Date of Birth:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Occupation:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Annual Income:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Street:&nbsp;&nbsp;&nbsp;&nbsp;<br>		City:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Country:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Zip:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Phone:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Email:&nbsp;&nbsp;&nbsp;&nbsp;<br>				Father's Name:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Father's Occupation:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Cheque Book Number:&nbsp;&nbsp;&nbsp;&nbsp;<br>Sex:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Marital Status:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Qualifications:&nbsp;&nbsp;&nbsp;&nbsp;<br>						</td>";
				pw.println(part1);
				query="select * from holder where holder.user='"+per1+"'";
				rset=stmt.executeQuery(query);
				while(rset.next())
				{
					pw.println("<td valign=\"top\"><B>"+rset.getString(3)+"<br>"+rset.getDate(4)+"<br>"+rset.getString(5)+"<br>"+rset.getFloat(6)+"<br>"+rset.getString(7)+"<br>"+rset.getString(8)+"<br>"+rset.getString(20)+"<br>"+rset.getInt(9)+"<br>"+rset.getString(10)+"<br>"+rset.getString(11)+"<br>"+rset.getString(14)+"<br>"+rset.getString(15)+"<br>"+rset.getString(16)+"<br>"+rset.getString(17)+"<br>"+rset.getString(18)+"<br>"+rset.getString(19)+"</B></td>");
					
				}
				pw.println("</table>");
			}
			}
			
			
			
			
			
				pw.println("<br>");
				{
					pw.println("<table ><tr><td width=120  style=\"border:1px #000000 solid\" bgcolor=#b5b5b5><b>Date</b></td><td width=120 style=\"border:1px #000000 solid\" bgcolor=#b5b5b5><b>Particulars</b></td><td width=120 style=\"border:1px #000000 solid\" bgcolor=#b5b5b5><b>Debits</b></td><td width=200 style=\"border:1px #000000 solid\" bgcolor=#b5b5b5  ><b>Credits</b></td><td width=120 style=\"border:1px #000000 solid\" bgcolor=#b5b5b5><b>Balance</b></td><td width=120 style=\"border:1px #000000 solid\" bgcolor=#b5b5b5><b>Transaction ID</b></td></tr>");
				}
				String query1 =" select * from credit where credit.user='"+per1+"'" ;
				String query2 =" select * from debit where debit.user='"+per1+"'"  ;
		        ResultSet rstcredit = stmt.executeQuery(query1);
		        
		        query="create table temp(datetemp Date,credittemp float,debittemp float,init varchar(40),tid3 varchar(20))";
		        stmt1.executeUpdate(query);
		        
		        while(rstcredit.next())
		        {
		        	//pw.println("<B>Entered");
		        	query="insert into temp values('"+rstcredit.getDate(3)+"',"+rstcredit.getFloat(2)+",0.00,'"+rstcredit.getString(4)+"','"+rstcredit.getString(5)+"')";
		        	stmt1.executeUpdate(query);
		        }
		        ResultSet rstdebit = stmt.executeQuery(query2);
		        while(rstdebit.next())
		        {
		        	query="insert into temp values('"+rstdebit.getDate(3)+"',0.00,"+rstdebit.getFloat(2)+",'"+rstdebit.getString(4)+" by "+rstdebit.getString(5)+"','"+rstdebit.getString(6)+"')";
		        	stmt1.executeUpdate(query);
		        }
		        query="select * from temp where temp.datetemp>='"+ddfrom+"."+mmfrom+"."+yyfrom+"' and temp.datetemp<='"+ddto+"."+mmto+"."+yyto+"' order by temp.datetemp";
		        rset=stmt.executeQuery(query);
		       
		        
		        while(rset.next())
		        {
		        	if(rset.getFloat(3)==0)
		        		balance=balance+rset.getFloat(2);
		        	else if(rset.getFloat(2)==0)
		        		balance=balance-rset.getFloat(3);
		        	pw.println("<tr> <td width=120 style=\"border:1px #000000 solid\" >"+rset.getDate(1)+"</td><td width=120 style=\"border:1px #000000 solid\" >"+rset.getString(4)+"</td><td width=120 style=\"border:1px #000000 solid\" >"+rset.getFloat(3)+"</td><td width=240 style=\"border:1px #000000 solid\"  >"+rset.getFloat(2)+"</td><td width=240 style=\"border:1px #000000 solid\"  >"+balance+"</td><td width=120 style=\"border:1px #000000 solid\" >"+rset.getString(5)+"</td> </tr>");
		        }
		        
		        query="drop table temp";
		        stmt.executeUpdate(query);
		        pw.println("</table><br><font size=4><br><br>Current Balance :<b>"+balance+"</b></td>");
		        if(perarray[0]=='c')
				pw.println(myIndex.section6+"Customer"+myIndex.section7+date+myIndex.section8+time+myIndex.section9);
		        else if(perarray[0]=='e')
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