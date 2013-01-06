

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
 * Servlet implementation class for Servlet: FinanceForm
 *
 */
 public class FinanceForm extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public FinanceForm() {
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
			if(perarray[0]=='e')
			
				//employee
				pw.println(myIndex.section1+myIndex.employee);
			else if(perarray[0]=='a')
				pw.println(myIndex.section1+myIndex.admin);
				String user = request.getParameter("user");
				String loanType = request.getParameter("taxtype");
				String mortgage = request.getParameter("amt");
				String yearReturn = request.getParameter("idnum");
				String ammount = request.getParameter("bill");
				query = "select * from cnt where cnt.name='Loan'";
				rset=stmt.executeQuery(query);
				rset.next();
				int temp=rset.getInt(2);
				temp++;
				String loanNum = "L"+temp;
									//getting last loan number count
				query = "update cnt set cnt.cnt1="+temp+" where cnt.name='Loan'";
				stmt.executeUpdate(query);			//updating loan count

				query = "select * from interest where interest.event='"+loanType+"'";
				rset=stmt.executeQuery(query);
				rset.next();
				float interest = rset.getFloat(2);		//fetching loan interest rate
				
				float AmmountReturn = Integer.parseInt(ammount)*(1+(interest*(Integer.parseInt(yearReturn))/100));	//calculating ammount to be return
				
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
				int yr=y+Integer.parseInt(yearReturn);		// 
						// Calculating loan return year
				
				query = "insert into loan values('"+user+"','"+loanNum+"','"+date1+"."+month+"."+year+"',"+ammount+","+AmmountReturn+",'"+date1+"."+month+"."+yr+"','"+mortgage+"','"+loanType+"')";

				stmt.executeUpdate(query);		//inserting values into loan table
				
				
				pw.println("<td valign=\"top\" width=580><font size=3><B>Finance Issued Successfully.<br></B></font>Your Loan Number is: "+loanNum);
			
				if(perarray[0]=='e')
				pw.println(myIndex.section6+"Employee"+myIndex.section7+date+myIndex.section8+time+myIndex.section9);
				else if(perarray[0]=='a')
					pw.println(myIndex.section6+"Employee"+myIndex.section7+date+myIndex.section8+time+myIndex.section9);
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
			if(perarray[0]=='e')
			
				//employee
				pw.println(myIndex.section1+myIndex.employee);
			else if(perarray[0]=='a')
				pw.println(myIndex.section1+myIndex.admin);
				String user = request.getParameter("user");
				String loanType = request.getParameter("taxtype");
				String mortgage = request.getParameter("amt");
				String yearReturn = request.getParameter("idnum");
				String ammount = request.getParameter("bill");
				query = "select * from cnt where cnt.name='Loan'";
				rset=stmt.executeQuery(query);
				rset.next();
				int temp=rset.getInt(2);
				temp++;
				String loanNum = "L"+temp;
									//getting last loan number count
				query = "update cnt set cnt.cnt1="+temp+" where cnt.name='Loan'";
				stmt.executeUpdate(query);			//updating loan count

				query = "select * from interest where interest.event='"+loanType+"'";
				rset=stmt.executeQuery(query);
				rset.next();
				float interest = rset.getFloat(2);		//fetching loan interest rate
				
				float AmmountReturn = Integer.parseInt(ammount)*(1+(interest*(Integer.parseInt(yearReturn))/100));	//calculating ammount to be return
				
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
				int yr=y+Integer.parseInt(yearReturn);		// 
						// Calculating loan return year
				
				query = "insert into loan values('"+user+"','"+loanNum+"','"+date1+"."+month+"."+year+"',"+ammount+","+AmmountReturn+",'"+date1+"."+month+"."+yr+"','"+mortgage+"','"+loanType+"')";

				stmt.executeUpdate(query);		//inserting values into loan table
				
				
				pw.println("<td valign=\"top\" width=580><font size=3><B>Finance Issued Successfully.<br></B></font>Your Loan Number is: "+loanNum);
			
				if(perarray[0]=='e')
				pw.println(myIndex.section6+"Employee"+myIndex.section7+date+myIndex.section8+time+myIndex.section9);
				else if(perarray[0]=='a')
					pw.println(myIndex.section6+"Employee"+myIndex.section7+date+myIndex.section8+time+myIndex.section9);
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