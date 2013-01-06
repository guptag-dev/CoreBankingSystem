

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
import java.io.*;

/**
 * Servlet implementation class for Servlet: CloseConfirm
 *
 */
 public class CloseConfirm extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public CloseConfirm() {
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
			
			String acnum=ctx.getAttribute("acnum").toString();
				//String acnum = request.getParameter("acnum");
				query="select * from account where account.accno="+acnum;
				rset=stmt.executeQuery(query);
				rset.next();
				String userid=rset.getString(1);
				int totalHolders=rset.getShort(5);
				
				query = "select * from loan where loan.user='"+userid+"'";
				
				rset=stmt.executeQuery(query);
				
				boolean enter=false;
				while(rset.next())
				{
					enter=true;
					pw.println("<td valign=\"top\" width=580><B>Cannot Close your account since some loans are due on this account</B><br>");
					break;
				}
				if(!enter)
				{
				
					query = "select * from treasury where treasury.user='"+userid+"'";
					rset=stmt.executeQuery(query);
					
					enter=false;
					while(rset.next())
					{
						enter=true;
						pw.println("<td valign=\"top\" width=580><B>Cannot Close your account since some loans are due on this account</B><br>");
						break;
					}
					if(!enter)
					{					
					
				
				query = "delete from login where login.user='"+userid+"'";
				stmt.executeUpdate(query);					
				
				query = "delete from account where account.user='"+userid+"'";
				stmt.executeUpdate(query);			//deleting entries from account table
				
				query = "delete from holder where holder.user='"+userid+"'";
				stmt.executeUpdate(query);			// deleting entries from holder table
				
				query = "delete from credit where credit.user='"+userid+"'";
				stmt.executeUpdate(query);			// deleting entries from credit table
				
				query = "delete from debit where debit.user='"+userid+"'";
				stmt.executeUpdate(query);			// deleting entries from debit table
				
				query = "delete from bills where bills.user='"+userid+"'";
				stmt.executeUpdate(query);			// deleting entries from bills table
				
				query = "delete from interbank where interbank.user='"+userid+"'";
				stmt.executeUpdate(query);			
				
				query = "delete from sharesell where sharesell.user='"+userid+"'";
				stmt.executeUpdate(query);
				
				query = "delete from sharepur where sharepur.user='"+userid+"'";
				stmt.executeUpdate(query);
				
				query = "delete from taxes where taxes.user='"+userid+"'";
				stmt.executeUpdate(query);
				
				query = "delete from logininfo where logininfo.user='"+userid+"'";
				stmt.executeUpdate(query);
				
				String dir=ctx.getRealPath(this.toString());
				char[] dirarr = new char[dir.length()];
				dirarr=dir.toCharArray();
				int k;
				for(k=dir.length()-1;k>=0;k--)
				{
					if(dirarr[k]=='/')
						break;
				}
				
				String dirName = "";
				for(int o=0;o<=k;o++)
					dirName+=dirarr[o];
				dirName+="signatures/";
				for(int i=1;i<=totalHolders;i++)
				{
					String fname=dirName+i+userid+".jpg";
					 File f = new File(fname);
					 f.delete();
				}
				
				
				
				pw.println("<td valign=\"top\" width=580><font size=3><B>Account Closed Successfully.<br></B></font>");
			
				if(perarray[0]=='e')
				pw.println(myIndex.section6+"Employee"+myIndex.section7+date+myIndex.section8+time+myIndex.section9);
				else if(perarray[0]=='a')
					pw.println(myIndex.section6+"Employee"+myIndex.section7+date+myIndex.section8+time+myIndex.section9);
			}
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
			
			String acnum=ctx.getAttribute("acnum").toString();
				//String acnum = request.getParameter("acnum");
				query="select * from account where account.accno="+acnum;
				rset=stmt.executeQuery(query);
				rset.next();
				String userid=rset.getString(1);
				int totalHolders=rset.getShort(5);
				
				query = "select * from loan where loan.user='"+userid+"'";
				
				rset=stmt.executeQuery(query);
				
				boolean enter=false;
				while(rset.next())
				{
					enter=true;
					pw.println("<td valign=\"top\" width=580><B>Cannot Close your account since some loans are due on this account</B><br>");
					break;
				}
				if(!enter)
				{
				
					query = "select * from treasury where treasury.user='"+userid+"'";
					rset=stmt.executeQuery(query);
					
					enter=false;
					while(rset.next())
					{
						enter=true;
						pw.println("<td valign=\"top\" width=580><B>Cannot Close your account since some loans are due on this account</B><br>");
						break;
					}
					if(!enter)
					{					
					
				
				query = "delete from login where login.user='"+userid+"'";
				stmt.executeUpdate(query);					
				
				query = "delete from account where account.user='"+userid+"'";
				stmt.executeUpdate(query);			//deleting entries from account table
				
				query = "delete from holder where holder.user='"+userid+"'";
				stmt.executeUpdate(query);			// deleting entries from holder table
				
				query = "delete from credit where credit.user='"+userid+"'";
				stmt.executeUpdate(query);			// deleting entries from credit table
				
				query = "delete from debit where debit.user='"+userid+"'";
				stmt.executeUpdate(query);			// deleting entries from debit table
				
				query = "delete from bills where bills.user='"+userid+"'";
				stmt.executeUpdate(query);			// deleting entries from bills table
				
				query = "delete from interbank where interbank.user='"+userid+"'";
				stmt.executeUpdate(query);			
				
				query = "delete from sharesell where sharesell.user='"+userid+"'";
				stmt.executeUpdate(query);
				
				query = "delete from sharepur where sharepur.user='"+userid+"'";
				stmt.executeUpdate(query);
				
				query = "delete from taxes where taxes.user='"+userid+"'";
				stmt.executeUpdate(query);
				
				query = "delete from logininfo where logininfo.user='"+userid+"'";
				stmt.executeUpdate(query);
				
				String dir=ctx.getRealPath(this.toString());
				char[] dirarr = new char[dir.length()];
				dirarr=dir.toCharArray();
				int k;
				for(k=dir.length()-1;k>=0;k--)
				{
					if(dirarr[k]=='/')
						break;
				}
				
				String dirName = "";
				for(int o=0;o<=k;o++)
					dirName+=dirarr[o];
				dirName+="signatures/";
				for(int i=1;i<=totalHolders;i++)
				{
					String fname=dirName+i+userid+".jpg";
					 File f = new File(fname);
					 f.delete();
				}
				
				
				
				pw.println("<td valign=\"top\" width=580><font size=3><B>Account Closed Successfully.<br></B></font>");
			
				if(perarray[0]=='e')
				pw.println(myIndex.section6+"Employee"+myIndex.section7+date+myIndex.section8+time+myIndex.section9);
				else if(perarray[0]=='a')
					pw.println(myIndex.section6+"Employee"+myIndex.section7+date+myIndex.section8+time+myIndex.section9);
			}
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