

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
 * Servlet implementation class for Servlet: SR
 *
 */
 public class SR extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public SR() {
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
			
			   query="select * from account full outer join loan on account.user=loan.user where lissue>='01."+month+"."+year+"' and lissue<='31."+month+"."+year+"'";
			   rset=stmt.executeQuery(query);
			
			pw.println("<td valign=\"top\" width=580><font size=3><U><B>Loans EMI Status Report for the month: "+month+"/"+year+"</U></B></font><br><br><table><tr><td style=\"border:1px #000000 solid\" bgcolor=#b5b5b5>S.No.</td><td style=\"border:1px #000000 solid\" bgcolor=#b5b5b5>Account Number</td><td style=\"border:1px #000000 solid\" bgcolor=#b5b5b5>Loan No.</td><td style=\"border:1px #000000 solid\" bgcolor=#b5b5b5>Amount Sanctioned</td><td style=\"border:1px #000000 solid\" bgcolor=#b5b5b5>EMI</td><td style=\"border:1px #000000 solid\" bgcolor=#b5b5b5>Overdue</td></tr>");
			int i=1;
				while(rset.next())
				{
					String overdue="";
					int temp=(int)(rset.getFloat(22));
					if( temp == -1)
						overdue+="Not Started";
					else 
						overdue +=rset.getFloat(10);
					pw.println("<tr><td style=\"border:1px #000000 solid\">"+i+"</td><td style=\"border:1px #000000 solid\">"+rset.getLong(2)+"</td><td style=\"border:1px #000000 solid\">"+rset.getString(14)+"</td><td style=\"border:1px #000000 solid\">"+rset.getFloat(16)+"</td><td style=\"border:1px #000000 solid\">"+rset.getFloat(23)+"</td><td style=\"border:1px #000000 solid\">"+overdue+"</td></tr>");
					i++;
				}
				
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
			
			   query="select * from account full outer join loan on account.user=loan.user where lissue>='01."+month+"."+year+"' and lissue<='31."+month+"."+year+"'";
			   rset=stmt.executeQuery(query);
			
			pw.println("<td valign=\"top\" width=580><font size=3><U><B>Loans EMI Status Report for the month: "+month+"/"+year+"</U></B></font><br><br><table><tr><td style=\"border:1px #000000 solid\" bgcolor=#b5b5b5>S.No.</td><td style=\"border:1px #000000 solid\" bgcolor=#b5b5b5>Account Number</td><td style=\"border:1px #000000 solid\" bgcolor=#b5b5b5>Loan No.</td><td style=\"border:1px #000000 solid\" bgcolor=#b5b5b5>Amount Sanctioned</td><td style=\"border:1px #000000 solid\" bgcolor=#b5b5b5>EMI</td><td style=\"border:1px #000000 solid\" bgcolor=#b5b5b5>Overdue</td></tr>");
			int i=1;
				while(rset.next())
				{
					String overdue="";
					int temp=(int)(rset.getFloat(22));
					if( temp == -1)
						overdue+="Not Started";
					else 
						overdue +=rset.getFloat(10);
					pw.println("<tr><td style=\"border:1px #000000 solid\">"+i+"</td><td style=\"border:1px #000000 solid\">"+rset.getLong(2)+"</td><td style=\"border:1px #000000 solid\">"+rset.getString(14)+"</td><td style=\"border:1px #000000 solid\">"+rset.getFloat(16)+"</td><td style=\"border:1px #000000 solid\">"+rset.getFloat(23)+"</td><td style=\"border:1px #000000 solid\">"+overdue+"</td></tr>");
					i++;
				}
				
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