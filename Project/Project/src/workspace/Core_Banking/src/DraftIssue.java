
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
 * Servlet implementation class for Servlet: DraftIssue
 *
 */
 public class DraftIssue extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public DraftIssue() {
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
				
					pw.println("<html><title>Error Page!</title><body><font color=\"red\" size=3><B>Error! This page is not meant for you</font></B></body></html>");

				
			}
			else if(perarray[0]=='e')
			{
				//employee
				pw.println(myIndex.section1+myIndex.employee);
				String abc="<td valign=top width=580><SCRIPT LANGUAGE=\"JavaScript\">function validate(){var myForm = document.form1;if(myForm.Branch.value==\"Select One\"){       alert(\"Destination bank is a required field\");   return false;   }if(myForm.InFavourOf.value==\"\"){       alert(\"Please fill for whom you are issuing the draft\");   return false;   }if(myForm.Amnt.value==\"\"){       alert(\"Emter correct ammount\");   return false;   }if(myForm.medium.value==\"Select One\"){       alert(\"Please select medium of payment\");   return false;   }if(myForm.AccNo.value==\"\"){       alert(\"Enter correct account number or 0 otherwise\");   return false;   }if(myForm.IdNum.value==\"\"){       alert(\"Enter correct ID number or 0 otherwise\");   return false;   }}</script>";
				pw.println(abc+"<br><br><form action=\"DraftVerify\" method=\"POST\" name=form1><table><td valign=\"top\" width=150>	In the Favour of :<br>	Payable at :<br>	Amount : <br>	Medium : <br>	Account No.:<br> Id Proof:</td><td><b>	<input type=text name=InFavourOf size=15	><br>	<select name=Branch >	<option value=\"Select One\">Select One</option>");
				query="select * from branch";
				rset=stmt.executeQuery(query);
				while(rset.next())
				{
					pw.println("<option value=\""+rset.getString(3)+"\">"+rset.getString(3)+"</option>");
				}
				pw.println("</select><br>	<input type=text name=Amnt size=15><br>	<select name=medium>	<option value=\"Select One\">Select One</option>	<option value=\"By Cash\">By Cash</option>	<option value=\"By Account\">By Account</option>	</select><br>	<input type=text name=AccNo size=15><br><input type=text name=IdNum size=15><br></b>	</td></table>		<br><input type=submit value=\"Issue\" onclick=\"return validate()\">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp		<input type=reset value=\"Reset\" ></form></td>");
				pw.println(myIndex.section6+"Employee"+myIndex.section7+date+myIndex.section8+time+myIndex.section9);
			
			}
			else if(perarray[0]=='a')
			{
				//admin
				pw.println(myIndex.section1+myIndex.employee);
				String abc="<td valign=top width=580><SCRIPT LANGUAGE=\"JavaScript\">function validate(){var myForm = document.form1;if(myForm.Branch.value==\"Select One\"){       alert(\"Destination bank is a required field\");   return false;   }if(myForm.InFavourOf.value==\"\"){       alert(\"Please fill for whom you are issuing the draft\");   return false;   }if(myForm.Amnt.value==\"\"){       alert(\"Emter correct ammount\");   return false;   }if(myForm.medium.value==\"Select One\"){       alert(\"Please select medium of payment\");   return false;   }if(myForm.AccNo.value==\"\"){       alert(\"Enter correct account number or 0 otherwise\");   return false;   }if(myForm.IdNum.value==\"\"){       alert(\"Enter correct ID number or 0 otherwise\");   return false;   }}</script>";
				pw.println(abc+"<br><br><form action=\"DraftVerify\" method=\"POST\" name=form1><table><td valign=\"top\" width=150>	In the Favour of :<br>	Payable at :<br>	Amount : <br>	Medium : <br>	Account No.:<br> Id Proof:</td><td><b>	<input type=text name=InFavourOf size=15	><br>	<select name=Branch >	<option value=\"Select One\">Select One</option>");
				query="select * from branch";
				rset=stmt.executeQuery(query);
				while(rset.next())
				{
					pw.println("<option value=\""+rset.getString(3)+"\">"+rset.getString(3)+"</option>");
				}
				pw.println("</select><br>	<input type=text name=Amnt size=15><br>	<select name=medium>	<option value=\"Select One\">Select One</option>	<option value=\"By Cash\">By Cash</option>	<option value=\"By Account\">By Account</option>	</select><br>	<input type=text name=AccNo size=15><br><input type=text name=IdNum size=15><br></b>	</td></table>		<br><input type=submit value=\"Issue\" onclick=\"return validate()\">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp		<input type=reset value=\"Reset\" ></form></td>");
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
				
					pw.println("<html><title>Error Page!</title><body><font color=\"red\" size=3><B>Error! This page is not meant for you</font></B></body></html>");

				
			}
			else if(perarray[0]=='e')
			{
				//employee
				pw.println(myIndex.section1+myIndex.employee);
				String abc="<td valign=top width=580><SCRIPT LANGUAGE=\"JavaScript\">function validate(){var myForm = document.form1;if(myForm.Branch.value==\"Select One\"){       alert(\"Destination bank is a required field\");   return false;   }if(myForm.InFavourOf.value==\"\"){       alert(\"Please fill for whom you are issuing the draft\");   return false;   }if(myForm.Amnt.value==\"\"){       alert(\"Emter correct ammount\");   return false;   }if(myForm.medium.value==\"Select One\"){       alert(\"Please select medium of payment\");   return false;   }if(myForm.AccNo.value==\"\"){       alert(\"Enter correct account number or 0 otherwise\");   return false;   }if(myForm.IdNum.value==\"\"){       alert(\"Enter correct ID number or 0 otherwise\");   return false;   }}</script>";
				pw.println(abc+"<br><br><form action=\"DraftVerify\" method=\"POST\" name=form1><table><td valign=\"top\" width=150>	In the Favour of :<br>	Payable at :<br>	Amount : <br>	Medium : <br>	Account No.:<br> Id Proof:</td><td><b>	<input type=text name=InFavourOf size=15	><br>	<select name=Branch >	<option value=\"Select One\">Select One</option>");
				query="select * from branch";
				rset=stmt.executeQuery(query);
				while(rset.next())
				{
					pw.println("<option value=\""+rset.getString(3)+"\">"+rset.getString(3)+"</option>");
				}
				pw.println("</select><br>	<input type=text name=Amnt size=15><br>	<select name=medium>	<option value=\"Select One\">Select One</option>	<option value=\"By Cash\">By Cash</option>	<option value=\"By Account\">By Account</option>	</select><br>	<input type=text name=AccNo size=15><br><input type=text name=IdNum size=15><br></b>	</td></table>		<br><input type=submit value=\"Issue\" onclick=\"return validate()\">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp		<input type=reset value=\"Reset\" ></form></td>");
				pw.println(myIndex.section6+"Employee"+myIndex.section7+date+myIndex.section8+time+myIndex.section9);
			
			}
			else if(perarray[0]=='a')
			{
				//admin
				pw.println(myIndex.section1+myIndex.employee);
				String abc="<td valign=top width=580><SCRIPT LANGUAGE=\"JavaScript\">function validate(){var myForm = document.form1;if(myForm.Branch.value==\"Select One\"){       alert(\"Destination bank is a required field\");   return false;   }if(myForm.InFavourOf.value==\"\"){       alert(\"Please fill for whom you are issuing the draft\");   return false;   }if(myForm.Amnt.value==\"\"){       alert(\"Emter correct ammount\");   return false;   }if(myForm.medium.value==\"Select One\"){       alert(\"Please select medium of payment\");   return false;   }if(myForm.AccNo.value==\"\"){       alert(\"Enter correct account number or 0 otherwise\");   return false;   }if(myForm.IdNum.value==\"\"){       alert(\"Enter correct ID number or 0 otherwise\");   return false;   }}</script>";
				pw.println(abc+"<br><br><form action=\"DraftVerify\" method=\"POST\" name=form1><table><td valign=\"top\" width=150>	In the Favour of :<br>	Payable at :<br>	Amount : <br>	Medium : <br>	Account No.:<br> Id Proof:</td><td><b>	<input type=text name=InFavourOf size=15	><br>	<select name=Branch >	<option value=\"Select One\">Select One</option>");
				query="select * from branch";
				rset=stmt.executeQuery(query);
				while(rset.next())
				{
					pw.println("<option value=\""+rset.getString(3)+"\">"+rset.getString(3)+"</option>");
				}
				pw.println("</select><br>	<input type=text name=Amnt size=15><br>	<select name=medium>	<option value=\"Select One\">Select One</option>	<option value=\"By Cash\">By Cash</option>	<option value=\"By Account\">By Account</option>	</select><br>	<input type=text name=AccNo size=15><br><input type=text name=IdNum size=15><br></b>	</td></table>		<br><input type=submit value=\"Issue\" onclick=\"return validate()\">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp		<input type=reset value=\"Reset\" ></form></td>");
				pw.println(myIndex.section6+"Employee"+myIndex.section7+date+myIndex.section8+time+myIndex.section9);
			
			}
			 }
			catch(Exception ex)
			{
				
			}
		}
	}   	  	    
}