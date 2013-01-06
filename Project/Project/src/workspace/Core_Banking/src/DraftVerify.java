

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
 * Servlet implementation class for Servlet: DraftVerify
 *
 */
 public class DraftVerify extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public DraftVerify() {
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
				
			
			
			
			String bank = request.getParameter("InFavourOf");
			String dbank = request.getParameter("Branch");
			String Amount = request.getParameter("Amnt");
			String Operation = request.getParameter("medium");
			String acnum = request.getParameter("AccNo");
			String Chequeno = request.getParameter("IdNum");
			
			ctx.setAttribute("InFavourOf",bank);
			ctx.setAttribute("Branch",dbank);
			ctx.setAttribute("Amnt",Amount);
			ctx.setAttribute("medium",Operation);					
			ctx.setAttribute("AccNo",acnum);
			ctx.setAttribute("IdNum",Chequeno);
		
			
			if(Operation.equals("By Account"))
			{
				query="select * from account where account.accno="+acnum;
				rset=stmt.executeQuery(query);
				boolean enter=false;
				while(rset.next())
				{
					enter=true;
					
					String userid=rset.getString(1);
					
					
				int holds=rset.getShort(5);
				
				pw.println("<td valign=\"top\" width=580><font size=3><B>Verify Signatures of Account Holders<br></B></font><br><table>");
				for(int i=1;i<=holds;i++)
				{
					String html="<td valign=\"top\"><img src=\"signatures/"+i+userid+".jpg\" width=180 height=180></td>";
					pw.println(html);
					/*query="select * from holder where holder.user='"+userid+"' and holder.holdnum="+holds;
					rset=stmt.executeQuery(query);
					rset.next();*/
					//pw.println(" height=180 width=180></td>");
				}
				pw.println("</table><br><br><form action=\"DraftIssued\" method=\"POST\"><input type=\"submit\" value=\"Verify\" name=verify></form></td>");
				break;
					}
				
			if(!enter)
			{
				pw.println("<td valign=\"top\" width=580><font size=3><B>Account Not Found!</B></FONT></td>");
			}
		
			}
					else
					pw.println("<td valign=\"top\" width=580><form action=\"DraftIssued\" method=\"POST\"><input type=\"submit\" value=\"Continue\" name=verify></form></td>");
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
				
			
			
			
			String bank = request.getParameter("InFavourOf");
			String dbank = request.getParameter("Branch");
			String Amount = request.getParameter("Amnt");
			String Operation = request.getParameter("medium");
			String acnum = request.getParameter("AccNo");
			String Chequeno = request.getParameter("IdNum");
			
			ctx.setAttribute("InFavourOf",bank);
			ctx.setAttribute("Branch",dbank);
			ctx.setAttribute("Amnt",Amount);
			ctx.setAttribute("medium",Operation);					
			ctx.setAttribute("AccNo",acnum);
			ctx.setAttribute("IdNum",Chequeno);
		
			
			if(Operation.equals("By Account"))
			{
				query="select * from account where account.accno="+acnum;
				rset=stmt.executeQuery(query);
				boolean enter=false;
				while(rset.next())
				{
					enter=true;
					
					String userid=rset.getString(1);
					
					
				int holds=rset.getShort(5);
				
				pw.println("<td valign=\"top\" width=580><font size=3><B>Verify Signatures of Account Holders<br></B></font><br><table>");
				for(int i=1;i<=holds;i++)
				{
					String html="<td valign=\"top\"><img src=\"signatures/"+i+userid+".jpg\" width=180 height=180></td>";
					pw.println(html);
					/*query="select * from holder where holder.user='"+userid+"' and holder.holdnum="+holds;
					rset=stmt.executeQuery(query);
					rset.next();*/
					//pw.println(" height=180 width=180></td>");
				}
				pw.println("</table><br><br><form action=\"DraftIssued\" method=\"POST\"><input type=\"submit\" value=\"Verify\" name=verify></form></td>");
				break;
					}
				
			if(!enter)
			{
				pw.println("<td valign=\"top\" width=580><font size=3><B>Account Not Found!</B></FONT></td>");
			}
		
			}
					else
					pw.println("<td valign=\"top\" width=580><form action=\"DraftIssued\" method=\"POST\"><input type=\"submit\" value=\"Continue\" name=verify></form></td>");
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