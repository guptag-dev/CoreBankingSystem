


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
/**
 * Servlet implementation class for Servlet: printLOG
 *
 */
 public class printLOG extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public printLOG() {
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
			
			 String Guarantee = ctx.getAttribute("Guarantee").toString();
				ctx.setAttribute("Guarantee",Guarantee);
				String Guaranteetype = ctx.getAttribute("Guaranteetype").toString();
				ctx.setAttribute("Guaranteetype",Guaranteetype);
				String bank  = ctx.getAttribute("bank").toString();
				ctx.setAttribute("bank",bank);
				String maxlimit = ctx.getAttribute("maxlimit").toString();
				ctx.setAttribute("maxlimit",maxlimit);
				String idno  = ctx.getAttribute("idno").toString();
				ctx.setAttribute("idno",idno);
				String ACno = ctx.getAttribute("ACno").toString();
				ctx.setAttribute("ACno",ACno);
				int accno = Integer.parseInt(ACno);
				String to = ctx.getAttribute("to").toString();
				ctx.setAttribute("to",to);
				String day1 = ctx.getAttribute("day1").toString();
				ctx.setAttribute("day1",day1);
				String month1 = ctx.getAttribute("month1").toString();
				ctx.setAttribute("month1",month1);
				String year1 = ctx.getAttribute("year1").toString();
				ctx.setAttribute("year1",year1);
				
				String ValidDate=day1+"."+month1+"."+year1;
				Date dt = new Date();
						String day="",month="",year="",hrs="",mins="",sec="";
						 if(dt.getDate()<10)
							 day+="0"+dt.getDate();
						 else
							 day+=dt.getDate();
						 int m=dt.getMonth()+1;
					 
						 if(m<10)
							 month+="0"+m;
						 else
							 month+=m;
						 int y=dt.getYear()+1900;
						 year+=y;
					
						String CompDate = day+"."+month+"."+year ;
			query = "select * from account where account.accno="+accno+"";	
			rset=stmt.executeQuery(query);
			rset.next();
		String UserID = rset.getString(1) ;
		query = "select * from holder where holder.user='"+UserID+"' and holder.idnum='"+idno+"'";	
			rset=stmt.executeQuery(query);
			rset.next();
		String Name=rset.getString(3);	
			
			if(perarray[0]=='c')
			{
				
					pw.println("<html><title>Error Page!</title><body><font color=\"red\" size=3><B>Error! This page is not meant for you</font></B></body></html>");

				
			}
			else if(perarray[0]=='e')
			{
				pw.println(myIndex.section1+myIndex.employee);
					pw.println("<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp<font size=\"6\">Bank Letter of Guarantee</font>          <br><br><form action=\"LOGissued2\" method=\"POST\" name=form1><table>	<td width=800>	<br><br>	To:<b>"+to+"</b><br><br>	<p>			Dear Sir:<br><br>This letter will serve as your notification that <b>"+Name+"</b> will irrevocably guarantee for payment  at bank <b>"+bank+"</b> written by <b>"+to+"</b> up to the amount of <b>"+maxlimit+"</b> and drawn on account number <b>"+ACno+"</b>.  This guarantee is for <b>"+Guaranteetype+"</b> on <b>"+CompDate+"</b> which will be valid upto <b>"+ValidDate+"</b>.If further information is required, please feel free to contact this office.<br><br><br><br><br>Sincerely,<br>Bank officer's signature & title<br><b><br><br><br>"+per+"</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </p></td>	</table><br><br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type=Submit value=\"Sanction\"> </form></td>");
				pw.println(myIndex.section6+"Employee"+myIndex.section7+date+myIndex.section8+time+myIndex.section9);
					
			}
			else if(perarray[0]=='a')
			{
				pw.println(myIndex.section1+myIndex.employee);
				pw.println("<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp<font size=\"6\">Bank Letter of Guarantee</font>          <br><br><form action=\"LOGissued2\" method=\"POST\" name=form1><table>	<td width=800>	<br><br>	To:<b>"+to+"</b><br><br>	<p>			Dear Sir:<br><br>This letter will serve as your notification that <b>"+Name+"</b> will irrevocably guarantee for payment  at bank <b>"+bank+"</b> written by <b>"+to+"</b> up to the amount of <b>"+maxlimit+"</b> and drawn on account number <b>"+ACno+"</b>.  This guarantee is for <b>"+Guaranteetype+"</b> on <b>"+CompDate+"</b> which will be valid upto <b>"+ValidDate+"</b>.If further information is required, please feel free to contact this office.<br><br><br><br><br>Sincerely,<br>Bank officer's signature & title<br><b><br><br><br>"+per+"</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </p></td>	</table><br><br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type=Submit value=\"Sanction\"> </form></td>");
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
			
			 String Guarantee = ctx.getAttribute("Guarantee").toString();
				ctx.setAttribute("Guarantee",Guarantee);
				String Guaranteetype = ctx.getAttribute("Guaranteetype").toString();
				ctx.setAttribute("Guaranteetype",Guaranteetype);
				String bank  = ctx.getAttribute("bank").toString();
				ctx.setAttribute("bank",bank);
				String maxlimit = ctx.getAttribute("maxlimit").toString();
				ctx.setAttribute("maxlimit",maxlimit);
				String idno  = ctx.getAttribute("idno").toString();
				ctx.setAttribute("idno",idno);
				String ACno = ctx.getAttribute("ACno").toString();
				ctx.setAttribute("ACno",ACno);
				int accno = Integer.parseInt(ACno);
				String to = ctx.getAttribute("to").toString();
				ctx.setAttribute("to",to);
				String day1 = ctx.getAttribute("day1").toString();
				ctx.setAttribute("day1",day1);
				String month1 = ctx.getAttribute("month1").toString();
				ctx.setAttribute("month1",month1);
				String year1 = ctx.getAttribute("year1").toString();
				ctx.setAttribute("year1",year1);
				
				String ValidDate=day1+"."+month1+"."+year1;
				Date dt = new Date();
						String day="",month="",year="",hrs="",mins="",sec="";
						 if(dt.getDate()<10)
							 day+="0"+dt.getDate();
						 else
							 day+=dt.getDate();
						 int m=dt.getMonth()+1;
					 
						 if(m<10)
							 month+="0"+m;
						 else
							 month+=m;
						 int y=dt.getYear()+1900;
						 year+=y;
					
						String CompDate = day+"."+month+"."+year ;
			query = "select * from account where account.accno="+accno+"";	
			rset=stmt.executeQuery(query);
			rset.next();
		String UserID = rset.getString(1) ;
		query = "select * from holder where holder.user='"+UserID+"' and holder.idnum='"+idno+"'";	
			rset=stmt.executeQuery(query);
			rset.next();
		String Name=rset.getString(3);	
			
			if(perarray[0]=='c')
			{
				
					pw.println("<html><title>Error Page!</title><body><font color=\"red\" size=3><B>Error! This page is not meant for you</font></B></body></html>");

				
			}
			else if(perarray[0]=='e')
			{
				pw.println(myIndex.section1+myIndex.employee);
					pw.println("<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp<font size=\"6\">Bank Letter of Guarantee</font>          <br><br><form action=\"LOGissued2\" method=\"POST\" name=form1><table>	<td width=800>	<br><br>	To:<b>"+to+"</b><br><br>	<p>			Dear Sir:<br><br>This letter will serve as your notification that <b>"+Name+"</b> will irrevocably guarantee for payment  at bank <b>"+bank+"</b> written by <b>"+to+"</b> up to the amount of <b>"+maxlimit+"</b> and drawn on account number <b>"+ACno+"</b>.  This guarantee is for <b>"+Guaranteetype+"</b> on <b>"+CompDate+"</b> which will be valid upto <b>"+ValidDate+"</b>.If further information is required, please feel free to contact this office.<br><br><br><br><br>Sincerely,<br>Bank officer's signature & title<br><b><br><br><br>"+per+"</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </p></td>	</table><br><br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type=Submit value=\"Sanction\"> </form></td>");
				pw.println(myIndex.section6+"Employee"+myIndex.section7+date+myIndex.section8+time+myIndex.section9);
					
			}
			else if(perarray[0]=='a')
			{
				pw.println(myIndex.section1+myIndex.employee);
				pw.println("<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp<font size=\"6\">Bank Letter of Guarantee</font>          <br><br><form action=\"LOGissued2\" method=\"POST\" name=form1><table>	<td width=800>	<br><br>	To:<b>"+to+"</b><br><br>	<p>			Dear Sir:<br><br>This letter will serve as your notification that <b>"+Name+"</b> will irrevocably guarantee for payment  at bank <b>"+bank+"</b> written by <b>"+to+"</b> up to the amount of <b>"+maxlimit+"</b> and drawn on account number <b>"+ACno+"</b>.  This guarantee is for <b>"+Guaranteetype+"</b> on <b>"+CompDate+"</b> which will be valid upto <b>"+ValidDate+"</b>.If further information is required, please feel free to contact this office.<br><br><br><br><br>Sincerely,<br>Bank officer's signature & title<br><b><br><br><br>"+per+"</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </p></td>	</table><br><br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type=Submit value=\"Sanction\"> </form></td>");
				pw.println(myIndex.section6+"Employee"+myIndex.section7+date+myIndex.section8+time+myIndex.section9);
		
			}
			 }
			catch(Exception ex)
			{
				
			}
		}	
	}   	  	    
}