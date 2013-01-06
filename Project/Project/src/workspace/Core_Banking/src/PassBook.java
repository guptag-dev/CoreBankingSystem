

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
import java.util.*;
import java.text.*;

/**
 * Servlet implementation class for Servlet: PassBook
 *
 */
 public class PassBook extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public PassBook() {
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
				pw.println(myIndex.section1+myIndex.employee);
			}
			else if(perarray[0]=='a')
			{
				pw.println(myIndex.section1+myIndex.admin);
			}
				pw.println("<td valign=\"top\"><font size=2 ><table>	<td>User-Id : <br>Account No. : <br>	Account Type : <br><br></td>	<td valign=\"top\"><b>");
				query="select * from account where account.user='"+per+"'";
				rset=stmt.executeQuery(query);
				while(rset.next())
				{
					pw.println(rset.getString(1)+"<br>"+rset.getLong(2)+"<br>"+rset.getString(4)+"</B></td></table><table ><tr><td width=120  style=\"border:1px #000000 solid\" bgcolor=#b5b5b5><b>Date</b></td><td width=120 style=\"border:1px #000000 solid\" bgcolor=#b5b5b5><b>Credit</b></td><td width=120 style=\"border:1px #000000 solid\" bgcolor=#b5b5b5><b>Debit</b></td><td width=200 style=\"border:1px #000000 solid\" bgcolor=#b5b5b5  ><b>Initials</b></td><td width=120 style=\"border:1px #000000 solid\" bgcolor=#b5b5b5><b>Balance</b></td></tr>");
					break;
				}
				String query1 =" select * from credit where credit.user='"+per+"'" ;
				String query2 =" select * from debit where debit.user='"+per+"'"  ;
		        ResultSet rstcredit = stmt.executeQuery(query1);
		        
		        query="create table temp(datetemp Date,credittemp float,debittemp float,init varchar(40))";
		        stmt1.executeUpdate(query);
		        
		        while(rstcredit.next())
		        {
		        	//pw.println("<B>Entered");
		        	query="insert into temp values('"+rstcredit.getDate(3)+"',"+rstcredit.getFloat(2)+",0.00,'"+rstcredit.getString(4)+"')";
		        	stmt1.executeUpdate(query);
		        }
		        ResultSet rstdebit = stmt.executeQuery(query2);
		        while(rstdebit.next())
		        {
		        	query="insert into temp values('"+rstdebit.getDate(3)+"',0.00,"+rstdebit.getFloat(2)+",'"+rstdebit.getString(4)+" by "+rstdebit.getString(5)+"')";
		        	stmt1.executeUpdate(query);
		        }
		        query="select * from temp order by temp.datetemp";
		        rset=stmt.executeQuery(query);
		       
		        
		        while(rset.next())
		        {
		        	if(rset.getFloat(3)==0)
		        		balance=balance+rset.getFloat(2);
		        	else if(rset.getFloat(2)==0)
		        		balance=balance-rset.getFloat(3);
		        	pw.println("<tr> <td width=120 style=\"border:1px #000000 solid\" >"+rset.getDate(1)+"</td><td width=120 style=\"border:1px #000000 solid\" >"+rset.getFloat(2)+"</td><td width=120 style=\"border:1px #000000 solid\" >"+rset.getFloat(3)+"</td><td width=240 style=\"border:1px #000000 solid\"  >"+rset.getString(4)+"</td><td width=120 style=\"border:1px #000000 solid\" >"+balance+"</td> </tr>");
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
				pw.println(myIndex.section1+myIndex.employee);
			}
			else if(perarray[0]=='a')
			{
				pw.println(myIndex.section1+myIndex.admin);
			}
				pw.println("<td valign=\"top\"><font size=2 ><table>	<td>User-Id : <br>Account No. : <br>	Account Type : <br><br></td>	<td valign=\"top\"><b>");
				query="select * from account where account.user='"+per+"'";
				rset=stmt.executeQuery(query);
				while(rset.next())
				{
					pw.println(rset.getString(1)+"<br>"+rset.getLong(2)+"<br>"+rset.getString(4)+"</B></td></table><table ><tr><td width=120  style=\"border:1px #000000 solid\" bgcolor=#b5b5b5><b>Date</b></td><td width=120 style=\"border:1px #000000 solid\" bgcolor=#b5b5b5><b>Credit</b></td><td width=120 style=\"border:1px #000000 solid\" bgcolor=#b5b5b5><b>Debit</b></td><td width=200 style=\"border:1px #000000 solid\" bgcolor=#b5b5b5  ><b>Initials</b></td><td width=120 style=\"border:1px #000000 solid\" bgcolor=#b5b5b5><b>Balance</b></td></tr>");
					break;
				}
				String query1 =" select * from credit where credit.user='"+per+"'" ;
				String query2 =" select * from debit where debit.user='"+per+"'"  ;
		        ResultSet rstcredit = stmt.executeQuery(query1);
		        
		        query="create table temp(datetemp Date,credittemp float,debittemp float,init varchar(40))";
		        stmt1.executeUpdate(query);
		        
		        while(rstcredit.next())
		        {
		        	//pw.println("<B>Entered");
		        	query="insert into temp values('"+rstcredit.getDate(3)+"',"+rstcredit.getFloat(2)+",0.00,'"+rstcredit.getString(4)+"')";
		        	stmt1.executeUpdate(query);
		        }
		        ResultSet rstdebit = stmt.executeQuery(query2);
		        while(rstdebit.next())
		        {
		        	query="insert into temp values('"+rstdebit.getDate(3)+"',0.00,"+rstdebit.getFloat(2)+",'"+rstdebit.getString(4)+" by "+rstdebit.getString(5)+"')";
		        	stmt1.executeUpdate(query);
		        }
		        query="select * from temp order by temp.datetemp";
		        rset=stmt.executeQuery(query);
		       
		        
		        while(rset.next())
		        {
		        	if(rset.getFloat(3)==0)
		        		balance=balance+rset.getFloat(2);
		        	else if(rset.getFloat(2)==0)
		        		balance=balance-rset.getFloat(3);
		        	pw.println("<tr> <td width=120 style=\"border:1px #000000 solid\" >"+rset.getDate(1)+"</td><td width=120 style=\"border:1px #000000 solid\" >"+rset.getFloat(2)+"</td><td width=120 style=\"border:1px #000000 solid\" >"+rset.getFloat(3)+"</td><td width=240 style=\"border:1px #000000 solid\"  >"+rset.getString(4)+"</td><td width=120 style=\"border:1px #000000 solid\" >"+balance+"</td> </tr>");
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
			catch(Exception ex)
			{
				
			}
		}
	}   	  	    
}