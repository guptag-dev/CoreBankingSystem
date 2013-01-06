

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
 * Servlet implementation class for Servlet: Shortcut
 *
 */
 public class Shortcut extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public Shortcut() {
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
			String date1="",time1="";
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
				date1=rset1.getString(2);
				time1=rset1.getString(3);
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
			
			String command=request.getParameter("command");
			if(command.equals("ledger"))
				response.sendRedirect("Ledger");
			if(command.equals("close"))
				response.sendRedirect("Close");
			if(command.equals("inbox"))
				response.sendRedirect("Inbox");
			if(command.equals("chpass"))
				response.sendRedirect("PassChange");
			if(command.equals("view"))
				response.sendRedirect("View");
			if(command.equals("passbook"))
				response.sendRedirect("PassBook");
			if(command.equals("msg"))
				response.sendRedirect("Message");
			if(command.equals("exit"))
				response.sendRedirect("Logout");
			if(command.equals("dtr"))
				response.sendRedirect("DTR");
			if(command.equals("lemi"))
				response.sendRedirect("SR");
			if(command.equals("mls"))
				response.sendRedirect("LEMI");
			if(command.equals("stand"))
				response.sendRedirect("Stand");
			if(command.equals("search"))
				response.sendRedirect("Search");
			if(command.equals("credit"))
				response.sendRedirect("Credit");
			if(command.equals("debit"))
				response.sendRedirect("Debit");
			if(command.equals("a2a"))
				response.sendRedirect("A2A");
			if(command.equals("treasury"))
				response.sendRedirect("Treasury");
			if(command.equals("create"))
				response.sendRedirect("CustAccount");
			if(command.equals("empcreate"))
				response.sendRedirect("EmpAccount");
			if(command.equals("loan"))
				response.sendRedirect("Sanction");
			if(command.equals("emi"))
				response.sendRedirect("EMI");
			if(command.equals("ss"))
				response.sendRedirect("SS");
			if(command.equals("agro"))
				response.sendRedirect("Agro");
			if(command.equals("rural"))
				response.sendRedirect("Rural");
			if(command.equals("corporate"))
				response.sendRedirect("Corporate");
			if(command.equals("bill"))
				response.sendRedirect("Bill");
			if(command.equals("tax"))
				response.sendRedirect("Tax");
			if(command.equals("remit"))
				response.sendRedirect("Remit");
			if(command.equals("cheque"))
				response.sendRedirect("ChequeClear");
			if(command.equals("chqstatus"))
				response.sendRedirect("ChequeStatus");
			if(command.equals("chqcancel"))
				response.sendRedirect("ChequeCancel");
			if(command.equals("issue"))
				response.sendRedirect("DraftIssue");
			if(command.equals("pay"))
				response.sendRedirect("DraftPay");
			if(command.equals("draftstatus"))
				response.sendRedirect("DraftStatus");
			if(command.equals("draftcancel"))
				response.sendRedirect("DraftCancel");
			if(command.equals("logi"))
				response.sendRedirect("LOGissue");
			if(command.equals("logp"))
				response.sendRedirect("LOGPay");
					
							
				
				if(perarray[0]=='e')
				pw.println(myIndex.section6+"Employee"+myIndex.section7+date1+myIndex.section8+time1+myIndex.section9);
				else if(perarray[0]=='a')
					pw.println(myIndex.section6+"Administrator"+myIndex.section7+date1+myIndex.section8+time1+myIndex.section9);
			
			 
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
			String date1="",time1="";
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
				date1=rset1.getString(2);
				time1=rset1.getString(3);
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
			
			String command=request.getParameter("command");
			if(command.equals("ledger"))
				response.sendRedirect("Ledger");
			if(command.equals("close"))
				response.sendRedirect("Close");
			if(command.equals("inbox"))
				response.sendRedirect("Inbox");
			if(command.equals("chpass"))
				response.sendRedirect("PassChange");
			if(command.equals("view"))
				response.sendRedirect("View");
			if(command.equals("passbook"))
				response.sendRedirect("PassBook");
			if(command.equals("msg"))
				response.sendRedirect("Message");
			if(command.equals("exit"))
				response.sendRedirect("Logout");
			if(command.equals("dtr"))
				response.sendRedirect("DTR");
			if(command.equals("lemi"))
				response.sendRedirect("SR");
			if(command.equals("mls"))
				response.sendRedirect("LEMI");
			if(command.equals("stand"))
				response.sendRedirect("Stand");
			if(command.equals("search"))
				response.sendRedirect("Search");
			if(command.equals("credit"))
				response.sendRedirect("Credit");
			if(command.equals("debit"))
				response.sendRedirect("Debit");
			if(command.equals("a2a"))
				response.sendRedirect("A2A");
			if(command.equals("treasury"))
				response.sendRedirect("Treasury");
			if(command.equals("create"))
				response.sendRedirect("CustAccount");
			if(command.equals("empcreate"))
				response.sendRedirect("EmpAccount");
			if(command.equals("loan"))
				response.sendRedirect("Sanction");
			if(command.equals("emi"))
				response.sendRedirect("EMI");
			if(command.equals("ss"))
				response.sendRedirect("SS");
			if(command.equals("agro"))
				response.sendRedirect("Agro");
			if(command.equals("rural"))
				response.sendRedirect("Rural");
			if(command.equals("corporate"))
				response.sendRedirect("Corporate");
			if(command.equals("bill"))
				response.sendRedirect("Bill");
			if(command.equals("tax"))
				response.sendRedirect("Tax");
			if(command.equals("remit"))
				response.sendRedirect("Remit");
			if(command.equals("cheque"))
				response.sendRedirect("ChequeClear");
			if(command.equals("chqstatus"))
				response.sendRedirect("ChequeStatus");
			if(command.equals("chqcancel"))
				response.sendRedirect("ChequeCancel");
			if(command.equals("issue"))
				response.sendRedirect("DraftIssue");
			if(command.equals("pay"))
				response.sendRedirect("DraftPay");
			if(command.equals("draftstatus"))
				response.sendRedirect("DraftStatus");
			if(command.equals("draftcancel"))
				response.sendRedirect("DraftCancel");
			if(command.equals("logi"))
				response.sendRedirect("LOGissue");
			if(command.equals("logp"))
				response.sendRedirect("LOGPay");
					
							
				
				if(perarray[0]=='e')
				pw.println(myIndex.section6+"Employee"+myIndex.section7+date1+myIndex.section8+time1+myIndex.section9);
				else if(perarray[0]=='a')
					pw.println(myIndex.section6+"Administrator"+myIndex.section7+date1+myIndex.section8+time1+myIndex.section9);
			
			 
			}
			 }
			 
			catch(Exception ex)
			{
				
			}
		}
	}   	  	    
}