

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
 * Servlet implementation class for Servlet: View
 *
 */
 public class View extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public View() {
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
			String date="",time="";
			String query="";
			String msg="";
			int mnum=0;
			try
			 {
			 InitialContext context = new InitialContext ();
			 dsource = (DataSource) context.lookup("java:comp/env/jdbc/MyDataSource");
			 conn = dsource.getConnection();
			 stmt=conn.createStatement();
			 per=ctx.getAttribute("UserID").toString();
			 ctx.setAttribute("loginsession", "T");
			 ctx.setAttribute("UserID",per);
			 
			 
			 
			char[] perarray = new char[per.length()];
			perarray=per.toCharArray();
			if(perarray[0]=='c')
			{
				//customer
				query="select * from holder where holder.user='"+per+"'";
				rset=stmt.executeQuery(query);
				if(rset.next())
				{
				pw.println(myIndex.section1+myIndex.customer);
				pw.println("<td valign=\"top\"><font size=2><b><u>General Information</u></b></font>    <br><br><table>	<td align='right' width=200>		User-ID:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Account No.:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Account Nature:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Account Type:&nbsp;&nbsp;&nbsp;&nbsp;<br>      		No.of Holders:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Available Balance:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Guarantee 1:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Guarantee 2:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Account Opening Date:&nbsp;&nbsp;&nbsp;&nbsp;<br>		</td><td valign=\"top\"><b>");
				query="select * from account where account.user='"+per+"'";
				rset=stmt.executeQuery(query);
				while(rset.next())
				{
					pw.println(rset.getString(1)+"<br>"+rset.getLong(2)+"<br>"+rset.getString(3)+"<br>"+rset.getString(4)+"<br>"+rset.getShort(5)+"<br>"+rset.getFloat(6)+"<br>"+rset.getString(7)+"<br>"+rset.getString(8)+"<br>"+rset.getDate(9)+"</td></table><br><br>");
					break;
				}
				String part1="<font size=\"2\"><b><u>Holder Information</u></b></font>         <br><br><table>	<td valign=\"top\">		Name:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Date of Birth:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Occupation:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Annual Income:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Street:&nbsp;&nbsp;&nbsp;&nbsp;<br>		City:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Country:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Zip:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Phone:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Email:&nbsp;&nbsp;&nbsp;&nbsp;<br>				Father's Name:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Father's Occupation:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Cheque Book Number:&nbsp;&nbsp;&nbsp;&nbsp;<br>Sex:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Marital Status:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Qualifications:&nbsp;&nbsp;&nbsp;&nbsp;<br>						</td>";
				pw.println(part1);
				query="select * from holder where holder.user='"+per+"'";
				rset=stmt.executeQuery(query);
				while(rset.next())
				{
					pw.println("<td valign=\"top\"><B>"+rset.getString(3)+"<br>"+rset.getDate(4)+"<br>"+rset.getString(5)+"<br>"+rset.getFloat(6)+"<br>"+rset.getString(7)+"<br>"+rset.getString(8)+"<br>"+rset.getString(20)+"<br>"+rset.getInt(9)+"<br>"+rset.getString(10)+"<br>"+rset.getString(11)+"<br>"+rset.getString(14)+"<br>"+rset.getString(15)+"<br>"+rset.getString(16)+"<br>"+rset.getString(17)+"<br>"+rset.getString(18)+"<br>"+rset.getString(19)+"</B></td>");
					
				}
				pw.println("</table></td>");
				}
				else
				{
					//NRI
					pw.println(myIndex.section1+myIndex.customer);
					pw.println("<td valign=\"top\"><font size=2><b><u>General Information</u></b></font>    <br><br><table>	<td align='right' width=200>		User-ID:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Account No.:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Account Nature:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Account Type:&nbsp;&nbsp;&nbsp;&nbsp;<br>      		No.of Holders:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Available Balance:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Guarantee 1:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Guarantee 2:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Account Opening Date:&nbsp;&nbsp;&nbsp;&nbsp;<br>		</td><td valign=\"top\"><b>");
					query="select * from account where account.user='"+per+"'";
					rset=stmt.executeQuery(query);
					while(rset.next())
					{
						pw.println(rset.getString(1)+"<br>"+rset.getLong(2)+"<br>"+rset.getString(3)+"<br>"+rset.getString(4)+"<br>"+rset.getShort(5)+"<br>"+rset.getFloat(6)+"<br>"+rset.getString(7)+"<br>"+rset.getString(8)+"<br>"+rset.getDate(9)+"</td></table><br><br>");
						break;
					}
					String part1="<font size=\"2\"><b><u>Holder Information</u></b></font>         <br><br><table>	<td valign=\"top\">		Name:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Date of Birth:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Occupation:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Monthly Income:&nbsp;&nbsp;&nbsp;&nbsp;<br>     Annual Income:&nbsp;&nbsp;&nbsp;&nbsp;<br>		<br><u>Temporary Address:</u><br>Street:&nbsp;&nbsp;&nbsp;&nbsp;<br>		City:&nbsp;&nbsp;&nbsp;&nbsp;<br>			Zip:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Phone:&nbsp;&nbsp;&nbsp;&nbsp;<br>		<br><u>Permanent Address:</u><br>Street:&nbsp;&nbsp;&nbsp;&nbsp;<br>		City:&nbsp;&nbsp;&nbsp;&nbsp;<br>	Country:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Zip:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Phone:&nbsp;&nbsp;&nbsp;&nbsp;<br> Email:&nbsp;&nbsp;&nbsp;&nbsp;<br>				Sex:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Marital Status:&nbsp;&nbsp;&nbsp;&nbsp;<br>		</td>";
					pw.println(part1);
					query="select * from nri where nri.user='"+per+"'";
					rset=stmt.executeQuery(query);
					while(rset.next())
					{
						pw.println("<td valign=\"top\"><B>"+rset.getString(3)+"<br>"+rset.getDate(4)+"<br>"+rset.getString(14)+"<br>"+rset.getFloat(15)+"<br>"+rset.getFloat(16)+"<br><br><br>"+rset.getString(5)+"<br>"+rset.getString(6)+"<br>"+rset.getInt(7)+"<br>"+rset.getString(8)+"<br><br><br>"+rset.getString(9)+"<br>"+rset.getString(10)+"<br>"+rset.getString(22)+"<br>"+rset.getString(11)+"<br>"+rset.getString(12)+"<br>"+rset.getString(13)+"<br>"+rset.getString(19)+"<br>"+rset.getString(20)+"</B></td>");
						
					}
					pw.println("</table></td>");
				}
				
			}
			else if(perarray[0]=='e')
			{
				//employee
				query="select * from holder where holder.user='"+per+"'";
				rset=stmt.executeQuery(query);
				if(rset.next())
				{
				pw.println(myIndex.section1+myIndex.employee);
				pw.println("<td valign=\"top\"><font size=2><b><u>General Information</u></b></font>    <br><br><table>	<td align='right' width=200>		User-ID:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Account No.:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Account Nature:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Account Type:&nbsp;&nbsp;&nbsp;&nbsp;<br>      		No.of Holders:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Available Balance:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Guarantee 1:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Guarantee 2:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Account Opening Date:&nbsp;&nbsp;&nbsp;&nbsp;<br>		</td><td valign=\"top\"><b>");
				query="select * from account where account.user='"+per+"'";
				rset=stmt.executeQuery(query);
				while(rset.next())
				{
					pw.println(rset.getString(1)+"<br>"+rset.getLong(2)+"<br>"+rset.getString(3)+"<br>"+rset.getString(4)+"<br>"+rset.getShort(5)+"<br>"+rset.getFloat(6)+"<br>"+rset.getString(7)+"<br>"+rset.getString(8)+"<br>"+rset.getDate(9)+"</td></table><br><br>");
					break;
				}
				String part1="<font size=\"2\"><b><u>Holder Information</u></b></font>         <br><br><table>	<td valign=\"top\">		Name:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Date of Birth:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Occupation:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Annual Income:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Street:&nbsp;&nbsp;&nbsp;&nbsp;<br>		City:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Country:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Zip:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Phone:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Email:&nbsp;&nbsp;&nbsp;&nbsp;<br>				Father's Name:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Father's Occupation:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Cheque Book Number:&nbsp;&nbsp;&nbsp;&nbsp;<br>Sex:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Marital Status:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Qualifications:&nbsp;&nbsp;&nbsp;&nbsp;<br>						</td>";
				pw.println(part1);
				query="select * from holder where holder.user='"+per+"'";
				rset=stmt.executeQuery(query);
				while(rset.next())
				{
					pw.println("<td valign=\"top\"><B>"+rset.getString(3)+"<br>"+rset.getDate(4)+"<br>"+rset.getString(5)+"<br>"+rset.getFloat(6)+"<br>"+rset.getString(7)+"<br>"+rset.getString(8)+"<br>"+rset.getString(20)+"<br>"+rset.getInt(9)+"<br>"+rset.getString(10)+"<br>"+rset.getString(11)+"<br>"+rset.getString(14)+"<br>"+rset.getString(15)+"<br>"+rset.getString(16)+"<br>"+rset.getString(17)+"<br>"+rset.getString(18)+"<br>"+rset.getString(19)+"</B></td>");
					
				}
				pw.println("</table></td>");
				}
				
			}
			else if(perarray[0]=='a')
			{
				//admin
				query="select * from holder where holder.user='"+per+"'";
				rset=stmt.executeQuery(query);
				if(rset.next())
				{
				pw.println(myIndex.section1+myIndex.admin);
				pw.println("<td valign=\"top\"><font size=2><b><u>General Information</u></b></font>    <br><br><table>	<td align='right' width=200>		User-ID:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Account No.:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Account Nature:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Account Type:&nbsp;&nbsp;&nbsp;&nbsp;<br>      		No.of Holders:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Available Balance:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Guarantee 1:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Guarantee 2:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Account Opening Date:&nbsp;&nbsp;&nbsp;&nbsp;<br>		</td><td valign=\"top\"><b>");
				query="select * from account where account.user='"+per+"'";
				rset=stmt.executeQuery(query);
				while(rset.next())
				{
					pw.println(rset.getString(1)+"<br>"+rset.getLong(2)+"<br>"+rset.getString(3)+"<br>"+rset.getString(4)+"<br>"+rset.getShort(5)+"<br>"+rset.getFloat(6)+"<br>"+rset.getString(7)+"<br>"+rset.getString(8)+"<br>"+rset.getDate(9)+"</td></table><br><br>");
					break;
				}
				String part1="<font size=\"2\"><b><u>Holder Information</u></b></font>         <br><br><table>	<td valign=\"top\">		Name:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Date of Birth:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Occupation:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Annual Income:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Street:&nbsp;&nbsp;&nbsp;&nbsp;<br>		City:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Country:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Zip:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Phone:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Email:&nbsp;&nbsp;&nbsp;&nbsp;<br>				Father's Name:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Father's Occupation:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Cheque Book Number:&nbsp;&nbsp;&nbsp;&nbsp;<br>Sex:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Marital Status:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Qualifications:&nbsp;&nbsp;&nbsp;&nbsp;<br>						</td>";
				pw.println(part1);
				query="select * from holder where holder.user='"+per+"'";
				rset=stmt.executeQuery(query);
				while(rset.next())
				{
					pw.println("<td valign=\"top\"><B>"+rset.getString(3)+"<br>"+rset.getDate(4)+"<br>"+rset.getString(5)+"<br>"+rset.getFloat(6)+"<br>"+rset.getString(7)+"<br>"+rset.getString(8)+"<br>"+rset.getString(20)+"<br>"+rset.getInt(9)+"<br>"+rset.getString(10)+"<br>"+rset.getString(11)+"<br>"+rset.getString(14)+"<br>"+rset.getString(15)+"<br>"+rset.getString(16)+"<br>"+rset.getString(17)+"<br>"+rset.getString(18)+"<br>"+rset.getString(19)+"</B></td>");
					
				}
				pw.println("</table></td>");
				}
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
			String date="",time="";
			String query="";
			String msg="";
			int mnum=0;
			try
			 {
			 InitialContext context = new InitialContext ();
			 dsource = (DataSource) context.lookup("java:comp/env/jdbc/MyDataSource");
			 conn = dsource.getConnection();
			 stmt=conn.createStatement();
			 per=ctx.getAttribute("UserID").toString();
			 ctx.setAttribute("loginsession", "T");
			 ctx.setAttribute("UserID",per);
			 
			 
			 
			char[] perarray = new char[per.length()];
			perarray=per.toCharArray();
			if(perarray[0]=='c')
			{
				//customer
				query="select * from holder where holder.user='"+per+"'";
				rset=stmt.executeQuery(query);
				if(rset.next())
				{
				pw.println(myIndex.section1+myIndex.customer);
				pw.println("<td valign=\"top\"><font size=2><b><u>General Information</u></b></font>    <br><br><table>	<td align='right' width=200>		User-ID:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Account No.:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Account Nature:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Account Type:&nbsp;&nbsp;&nbsp;&nbsp;<br>      		No.of Holders:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Available Balance:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Guarantee 1:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Guarantee 2:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Account Opening Date:&nbsp;&nbsp;&nbsp;&nbsp;<br>		</td><td valign=\"top\"><b>");
				query="select * from account where account.user='"+per+"'";
				rset=stmt.executeQuery(query);
				while(rset.next())
				{
					pw.println(rset.getString(1)+"<br>"+rset.getLong(2)+"<br>"+rset.getString(3)+"<br>"+rset.getString(4)+"<br>"+rset.getShort(5)+"<br>"+rset.getFloat(6)+"<br>"+rset.getString(7)+"<br>"+rset.getString(8)+"<br>"+rset.getDate(9)+"</td></table><br><br>");
					break;
				}
				String part1="<font size=\"2\"><b><u>Holder Information</u></b></font>         <br><br><table>	<td valign=\"top\">		Name:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Date of Birth:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Occupation:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Annual Income:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Street:&nbsp;&nbsp;&nbsp;&nbsp;<br>		City:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Country:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Zip:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Phone:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Email:&nbsp;&nbsp;&nbsp;&nbsp;<br>				Father's Name:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Father's Occupation:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Cheque Book Number:&nbsp;&nbsp;&nbsp;&nbsp;<br>Sex:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Marital Status:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Qualifications:&nbsp;&nbsp;&nbsp;&nbsp;<br>						</td>";
				pw.println(part1);
				query="select * from holder where holder.user='"+per+"'";
				rset=stmt.executeQuery(query);
				while(rset.next())
				{
					pw.println("<td valign=\"top\"><B>"+rset.getString(3)+"<br>"+rset.getDate(4)+"<br>"+rset.getString(5)+"<br>"+rset.getFloat(6)+"<br>"+rset.getString(7)+"<br>"+rset.getString(8)+"<br>"+rset.getString(20)+"<br>"+rset.getInt(9)+"<br>"+rset.getString(10)+"<br>"+rset.getString(11)+"<br>"+rset.getString(14)+"<br>"+rset.getString(15)+"<br>"+rset.getString(16)+"<br>"+rset.getString(17)+"<br>"+rset.getString(18)+"<br>"+rset.getString(19)+"</B></td>");
					
				}
				pw.println("</table></td>");
				}
				else
				{
					//NRI
					pw.println(myIndex.section1+myIndex.customer);
					pw.println("<td valign=\"top\"><font size=2><b><u>General Information</u></b></font>    <br><br><table>	<td align='right' width=200>		User-ID:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Account No.:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Account Nature:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Account Type:&nbsp;&nbsp;&nbsp;&nbsp;<br>      		No.of Holders:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Available Balance:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Guarantee 1:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Guarantee 2:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Account Opening Date:&nbsp;&nbsp;&nbsp;&nbsp;<br>		</td><td valign=\"top\"><b>");
					query="select * from account where account.user='"+per+"'";
					rset=stmt.executeQuery(query);
					while(rset.next())
					{
						pw.println(rset.getString(1)+"<br>"+rset.getLong(2)+"<br>"+rset.getString(3)+"<br>"+rset.getString(4)+"<br>"+rset.getShort(5)+"<br>"+rset.getFloat(6)+"<br>"+rset.getString(7)+"<br>"+rset.getString(8)+"<br>"+rset.getDate(9)+"</td></table><br><br>");
						break;
					}
					String part1="<font size=\"2\"><b><u>Holder Information</u></b></font>         <br><br><table>	<td valign=\"top\">		Name:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Date of Birth:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Occupation:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Monthly Income:&nbsp;&nbsp;&nbsp;&nbsp;<br>     Annual Income:&nbsp;&nbsp;&nbsp;&nbsp;<br>		<br><u>Temporary Address:</u><br>Street:&nbsp;&nbsp;&nbsp;&nbsp;<br>		City:&nbsp;&nbsp;&nbsp;&nbsp;<br>			Zip:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Phone:&nbsp;&nbsp;&nbsp;&nbsp;<br>		<br><u>Permanent Address:</u><br>Street:&nbsp;&nbsp;&nbsp;&nbsp;<br>		City:&nbsp;&nbsp;&nbsp;&nbsp;<br>	Country:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Zip:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Phone:&nbsp;&nbsp;&nbsp;&nbsp;<br> Email:&nbsp;&nbsp;&nbsp;&nbsp;<br>				Sex:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Marital Status:&nbsp;&nbsp;&nbsp;&nbsp;<br>		</td>";
					pw.println(part1);
					query="select * from nri where nri.user='"+per+"'";
					rset=stmt.executeQuery(query);
					while(rset.next())
					{
						pw.println("<td valign=\"top\"><B>"+rset.getString(3)+"<br>"+rset.getDate(4)+"<br>"+rset.getString(14)+"<br>"+rset.getFloat(15)+"<br>"+rset.getFloat(16)+"<br><br><br>"+rset.getString(5)+"<br>"+rset.getString(6)+"<br>"+rset.getInt(7)+"<br>"+rset.getString(8)+"<br><br><br>"+rset.getString(9)+"<br>"+rset.getString(10)+"<br>"+rset.getString(22)+"<br>"+rset.getString(11)+"<br>"+rset.getString(12)+"<br>"+rset.getString(13)+"<br>"+rset.getString(19)+"<br>"+rset.getString(20)+"</B></td>");
						
					}
					pw.println("</table></td>");
				}
				
			}
			else if(perarray[0]=='e')
			{
				//employee
				query="select * from holder where holder.user='"+per+"'";
				rset=stmt.executeQuery(query);
				if(rset.next())
				{
				pw.println(myIndex.section1+myIndex.employee);
				pw.println("<td valign=\"top\"><font size=2><b><u>General Information</u></b></font>    <br><br><table>	<td align='right' width=200>		User-ID:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Account No.:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Account Nature:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Account Type:&nbsp;&nbsp;&nbsp;&nbsp;<br>      		No.of Holders:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Available Balance:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Guarantee 1:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Guarantee 2:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Account Opening Date:&nbsp;&nbsp;&nbsp;&nbsp;<br>		</td><td valign=\"top\"><b>");
				query="select * from account where account.user='"+per+"'";
				rset=stmt.executeQuery(query);
				while(rset.next())
				{
					pw.println(rset.getString(1)+"<br>"+rset.getLong(2)+"<br>"+rset.getString(3)+"<br>"+rset.getString(4)+"<br>"+rset.getShort(5)+"<br>"+rset.getFloat(6)+"<br>"+rset.getString(7)+"<br>"+rset.getString(8)+"<br>"+rset.getDate(9)+"</td></table><br><br>");
					break;
				}
				String part1="<font size=\"2\"><b><u>Holder Information</u></b></font>         <br><br><table>	<td valign=\"top\">		Name:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Date of Birth:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Occupation:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Annual Income:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Street:&nbsp;&nbsp;&nbsp;&nbsp;<br>		City:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Country:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Zip:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Phone:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Email:&nbsp;&nbsp;&nbsp;&nbsp;<br>				Father's Name:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Father's Occupation:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Cheque Book Number:&nbsp;&nbsp;&nbsp;&nbsp;<br>Sex:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Marital Status:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Qualifications:&nbsp;&nbsp;&nbsp;&nbsp;<br>						</td>";
				pw.println(part1);
				query="select * from holder where holder.user='"+per+"'";
				rset=stmt.executeQuery(query);
				while(rset.next())
				{
					pw.println("<td valign=\"top\"><B>"+rset.getString(3)+"<br>"+rset.getDate(4)+"<br>"+rset.getString(5)+"<br>"+rset.getFloat(6)+"<br>"+rset.getString(7)+"<br>"+rset.getString(8)+"<br>"+rset.getString(20)+"<br>"+rset.getInt(9)+"<br>"+rset.getString(10)+"<br>"+rset.getString(11)+"<br>"+rset.getString(14)+"<br>"+rset.getString(15)+"<br>"+rset.getString(16)+"<br>"+rset.getString(17)+"<br>"+rset.getString(18)+"<br>"+rset.getString(19)+"</B></td>");
					
				}
				pw.println("</table></td>");
				}
				
			}
			else if(perarray[0]=='a')
			{
				//admin
				query="select * from holder where holder.user='"+per+"'";
				rset=stmt.executeQuery(query);
				if(rset.next())
				{
				pw.println(myIndex.section1+myIndex.admin);
				pw.println("<td valign=\"top\"><font size=2><b><u>General Information</u></b></font>    <br><br><table>	<td align='right' width=200>		User-ID:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Account No.:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Account Nature:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Account Type:&nbsp;&nbsp;&nbsp;&nbsp;<br>      		No.of Holders:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Available Balance:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Guarantee 1:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Guarantee 2:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Account Opening Date:&nbsp;&nbsp;&nbsp;&nbsp;<br>		</td><td valign=\"top\"><b>");
				query="select * from account where account.user='"+per+"'";
				rset=stmt.executeQuery(query);
				while(rset.next())
				{
					pw.println(rset.getString(1)+"<br>"+rset.getLong(2)+"<br>"+rset.getString(3)+"<br>"+rset.getString(4)+"<br>"+rset.getShort(5)+"<br>"+rset.getFloat(6)+"<br>"+rset.getString(7)+"<br>"+rset.getString(8)+"<br>"+rset.getDate(9)+"</td></table><br><br>");
					break;
				}
				String part1="<font size=\"2\"><b><u>Holder Information</u></b></font>         <br><br><table>	<td valign=\"top\">		Name:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Date of Birth:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Occupation:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Annual Income:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Street:&nbsp;&nbsp;&nbsp;&nbsp;<br>		City:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Country:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Zip:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Phone:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Email:&nbsp;&nbsp;&nbsp;&nbsp;<br>				Father's Name:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Father's Occupation:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Cheque Book Number:&nbsp;&nbsp;&nbsp;&nbsp;<br>Sex:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Marital Status:&nbsp;&nbsp;&nbsp;&nbsp;<br>		Qualifications:&nbsp;&nbsp;&nbsp;&nbsp;<br>						</td>";
				pw.println(part1);
				query="select * from holder where holder.user='"+per+"'";
				rset=stmt.executeQuery(query);
				while(rset.next())
				{
					pw.println("<td valign=\"top\"><B>"+rset.getString(3)+"<br>"+rset.getDate(4)+"<br>"+rset.getString(5)+"<br>"+rset.getFloat(6)+"<br>"+rset.getString(7)+"<br>"+rset.getString(8)+"<br>"+rset.getString(20)+"<br>"+rset.getInt(9)+"<br>"+rset.getString(10)+"<br>"+rset.getString(11)+"<br>"+rset.getString(14)+"<br>"+rset.getString(15)+"<br>"+rset.getString(16)+"<br>"+rset.getString(17)+"<br>"+rset.getString(18)+"<br>"+rset.getString(19)+"</B></td>");
					
				}
				pw.println("</table></td>");
				}
			}
			 }
			catch(Exception ex)
			{
				
			}
		}
	}   	  	    
}