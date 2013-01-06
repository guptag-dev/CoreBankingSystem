

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
 * Servlet implementation class for Servlet: EMIPay
 *
 */
 public class EMIPay extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public EMIPay() {
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
				String user;
				String LoanNum = request.getParameter("lnum");
				String EMI = request.getParameter("emi");
				
				//query = "select * from account where account.accno="+LoanNum;
				//rset=stmt.executeQuery(query);
				//rset.next();				
				//user = rset.getString(1);
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
				
				boolean enter = false;
				query = "select * from loan where loan.loannum='"+LoanNum+"' and loan.startdate<='"+CompDate+"'";
				rset = stmt.executeQuery(query);
				while(rset.next())
				{
					float myemi = rset.getFloat(11);
					int roundoff = (int) (myemi+0.5);
					if(roundoff != Integer.parseInt(EMI))
						break;
					else
						enter=true;
					
					float overdue = rset.getFloat(10);
					if((int)(overdue) == -1)
						overdue = 0;
					query = "update loan set loan.amtpaid=amtpaid+"+Integer.parseInt(EMI)+",loan.overdue="+overdue+" where loan.loannum='"+LoanNum+"'";
					stmt.executeUpdate(query);
					
					pw.println("<td valign=\"top\" width=580><font size=3><B> EMI payment successful </B></font>");
				}
				if(!enter)
				{
					pw.println("<td valign=\"top\" width=580><font size=3><B> EMI Payment Failed due to one of the following reasons :<br> 1) Loan Number is invalid <br> 2) Repayment period has not started <br> 3) Amount paid by you does not match your loan's EMI </B></font>");
				}
				

				
				
				
			
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
				String user;
				String LoanNum = request.getParameter("lnum");
				String EMI = request.getParameter("emi");
				
				//query = "select * from account where account.accno="+LoanNum;
				//rset=stmt.executeQuery(query);
				//rset.next();				
				//user = rset.getString(1);
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
				
				boolean enter = false;
				query = "select * from loan where loan.loannum='"+LoanNum+"' and loan.startdate<='"+CompDate+"'";
				rset = stmt.executeQuery(query);
				while(rset.next())
				{
					float myemi = rset.getFloat(11);
					int roundoff = (int) (myemi+0.5);
					if(roundoff != Integer.parseInt(EMI))
						break;
					else
						enter=true;
					
					float overdue = rset.getFloat(10);
					if((int)(overdue) == -1)
						overdue = 0;
					query = "update loan set loan.amtpaid=amtpaid+"+Integer.parseInt(EMI)+",loan.overdue="+overdue+" where loan.loannum='"+LoanNum+"'";
					stmt.executeUpdate(query);
					
					pw.println("<td valign=\"top\" width=580><font size=3><B> EMI payment successful </B></font>");
				}
				if(!enter)
				{
					pw.println("<td valign=\"top\" width=580><font size=3><B> EMI Payment Failed due to one of the following reasons :<br> 1) Loan Number is invalid <br> 2) Repayment period has not started <br> 3) Amount paid by you does not match your loan's EMI </B></font>");
				}
				

				
				
				
			
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