

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
 * Servlet implementation class for Servlet: Upload
 *
 */
 public class Upload extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public Upload() {
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
				String EmpUid=per;
				try
				{
				String AccType = request.getParameter("AccountType");
				ctx.setAttribute("AccType",AccType);
				String AccNature = request.getParameter("AccountNature");
				ctx.setAttribute("AccNature",AccNature);
				String Bal = request.getParameter("StartBal");
				ctx.setAttribute("Bal",Bal);
				String branch = request.getParameter("Branch");
				ctx.setAttribute("Branch",branch);
						
				String Guid1 = request.getParameter("GuaranteeUID1");
				ctx.setAttribute("Guid1",Guid1);
				String Guid2 = request.getParameter("GuaranteeUID2");
				ctx.setAttribute("Guid2",Guid2 );
				String ChequeBook = request.getParameter("ChequeBook");
				ctx.setAttribute("ChequeBook",ChequeBook);
				String ATM = request.getParameter("ATM");
				ctx.setAttribute("ATM",ATM);
				
				
				Date dt = new Date();
		                 String date1="",month="",year="",hrs="",mins="",sec="";
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
		                 if(dt.getHours()<10)
		                     hrs+="0"+dt.getHours();
		                 else 
		                     hrs+=dt.getHours();
		                 if(dt.getMinutes()<10)
		                     mins+="0"+dt.getMinutes();
		                 else
		                     mins+=dt.getMinutes(); 
		                 if(dt.getSeconds()<10)
		                     sec+="0"+dt.getSeconds();
		                 else
		                     sec+=dt.getSeconds();
					String CurrDate=date1+"."+month+"."+year;
					String CurrTime=hrs+":"+mins+":"+sec;
					
					
								int num = 1;
									for(int i=1 ; i<= num;i++)
									{
										String HolderName =request.getParameter("Holder"+i);
											ctx.setAttribute("HolderName"+i,HolderName );
										String HolderFatherName=request.getParameter("FatherHolder"+i);
											ctx.setAttribute("HolderFatherName"+i,HolderFatherName );
										String Bday=request.getParameter("Bday"+i);
											ctx.setAttribute("Bday"+i,Bday );
										String Bmonth=request.getParameter("Bmonth"+i);
											ctx.setAttribute("Bmonth"+i,Bmonth );
										String Byear=request.getParameter("Byear"+i);
											ctx.setAttribute("Byear"+i,Byear );
										String HolderAdd=request.getParameter("HolderAdd"+i);
											ctx.setAttribute("HolderAdd"+i,HolderAdd );
										String HolderCity=request.getParameter("HolderCity"+i);
											ctx.setAttribute("HolderCity"+i,HolderCity );
										String HolderZip=request.getParameter("HolderZip"+i);
											ctx.setAttribute("HolderZip"+i,HolderZip );
										String HolderPhone=request.getParameter("HolderPhone"+i);
											ctx.setAttribute("HolderPhone"+i,HolderPhone );
										String HolderMail=request.getParameter("HolderMail"+i);
											ctx.setAttribute("HolderMail"+i,HolderMail );
										String Occupation=request.getParameter("Occupation"+i);
											ctx.setAttribute("Occupation"+i,Occupation );
											String salary =request.getParameter("sal"+i);
											ctx.setAttribute("sal"+i,salary );
										
										String Incom=request.getParameter("Incom"+i);
											ctx.setAttribute("Incom"+i,Incom );
										String FatherOccupation=request.getParameter("FatherOccupation"+i);
											ctx.setAttribute("FatherOccupation"+i,FatherOccupation );
										String sex=request.getParameter("sex"+i);
											ctx.setAttribute("sex"+i,sex );
										String maritalstatus=request.getParameter("maritalstatus"+i);
											ctx.setAttribute("maritalstatus"+i,maritalstatus );
										String qualification=request.getParameter("qualification"+i);
											ctx.setAttribute("qualification"+i,qualification );
										String ID=request.getParameter("ID"+i);
											ctx.setAttribute("ID"+i,ID );
										String Country=request.getParameter("Country"+i);
											ctx.setAttribute("Country"+i,Country );
										String IDNumber=request.getParameter("IDNumber"+i);
											ctx.setAttribute("IDNumber"+i,IDNumber );
										
										
										
									}
									
				}
				catch(Exception ex)
				{
				pw.println(ex.getMessage());
				}				
						pw.println("<td valign=\"top\" width=580><form name=\"mainForm\" method=\"post\" enctype=\"multipart/form-data\" action=\"EmpSubmit\">	<input type=\"hidden\" name=\"hiddenParameter\" value=\"123\">	<p>File(only *.jpg case sensitive): <input type=\"file\" name=\"file\"></p>		<p><input type=\"submit\" value=\"Upload\"></p>	</form></td>  ");
						if(perarray[0]=='e')
							pw.println(myIndex.section6+"Employee"+myIndex.section7+date+myIndex.section8+time+myIndex.section9);
							else if(perarray[0]=='a')
								pw.println(myIndex.section6+"Administrator"+myIndex.section7+date+myIndex.section8+time+myIndex.section9);
								
							
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
				String EmpUid=per;
				try
				{
				String AccType = request.getParameter("AccountType");
				ctx.setAttribute("AccType",AccType);
				String AccNature = request.getParameter("AccountNature");
				ctx.setAttribute("AccNature",AccNature);
				String Bal = request.getParameter("StartBal");
				ctx.setAttribute("Bal",Bal);
				String branch = request.getParameter("Branch");
				ctx.setAttribute("Branch",branch);
						
				String Guid1 = request.getParameter("GuaranteeUID1");
				ctx.setAttribute("Guid1",Guid1);
				String Guid2 = request.getParameter("GuaranteeUID2");
				ctx.setAttribute("Guid2",Guid2 );
				String ChequeBook = request.getParameter("ChequeBook");
				ctx.setAttribute("ChequeBook",ChequeBook);
				String ATM = request.getParameter("ATM");
				ctx.setAttribute("ATM",ATM);
				
				
				Date dt = new Date();
		                 String date1="",month="",year="",hrs="",mins="",sec="";
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
		                 if(dt.getHours()<10)
		                     hrs+="0"+dt.getHours();
		                 else 
		                     hrs+=dt.getHours();
		                 if(dt.getMinutes()<10)
		                     mins+="0"+dt.getMinutes();
		                 else
		                     mins+=dt.getMinutes(); 
		                 if(dt.getSeconds()<10)
		                     sec+="0"+dt.getSeconds();
		                 else
		                     sec+=dt.getSeconds();
					String CurrDate=date1+"."+month+"."+year;
					String CurrTime=hrs+":"+mins+":"+sec;
					
					
								int num = 1;
									for(int i=1 ; i<= num;i++)
									{
										String HolderName =request.getParameter("Holder"+i);
											ctx.setAttribute("HolderName"+i,HolderName );
										String HolderFatherName=request.getParameter("FatherHolder"+i);
											ctx.setAttribute("HolderFatherName"+i,HolderFatherName );
										String Bday=request.getParameter("Bday"+i);
											ctx.setAttribute("Bday"+i,Bday );
										String Bmonth=request.getParameter("Bmonth"+i);
											ctx.setAttribute("Bmonth"+i,Bmonth );
										String Byear=request.getParameter("Byear"+i);
											ctx.setAttribute("Byear"+i,Byear );
										String HolderAdd=request.getParameter("HolderAdd"+i);
											ctx.setAttribute("HolderAdd"+i,HolderAdd );
										String HolderCity=request.getParameter("HolderCity"+i);
											ctx.setAttribute("HolderCity"+i,HolderCity );
										String HolderZip=request.getParameter("HolderZip"+i);
											ctx.setAttribute("HolderZip"+i,HolderZip );
										String HolderPhone=request.getParameter("HolderPhone"+i);
											ctx.setAttribute("HolderPhone"+i,HolderPhone );
										String HolderMail=request.getParameter("HolderMail"+i);
											ctx.setAttribute("HolderMail"+i,HolderMail );
										String Occupation=request.getParameter("Occupation"+i);
											ctx.setAttribute("Occupation"+i,Occupation );
											String salary =request.getParameter("sal"+i);
											ctx.setAttribute("sal"+i,salary );
										
										String Incom=request.getParameter("Incom"+i);
											ctx.setAttribute("Incom"+i,Incom );
										String FatherOccupation=request.getParameter("FatherOccupation"+i);
											ctx.setAttribute("FatherOccupation"+i,FatherOccupation );
										String sex=request.getParameter("sex"+i);
											ctx.setAttribute("sex"+i,sex );
										String maritalstatus=request.getParameter("maritalstatus"+i);
											ctx.setAttribute("maritalstatus"+i,maritalstatus );
										String qualification=request.getParameter("qualification"+i);
											ctx.setAttribute("qualification"+i,qualification );
										String ID=request.getParameter("ID"+i);
											ctx.setAttribute("ID"+i,ID );
										String Country=request.getParameter("Country"+i);
											ctx.setAttribute("Country"+i,Country );
										String IDNumber=request.getParameter("IDNumber"+i);
											ctx.setAttribute("IDNumber"+i,IDNumber );
										
										
										
									}
									
				}
				catch(Exception ex)
				{
				pw.println(ex.getMessage());
				}				
						pw.println("<td valign=\"top\" width=580><form name=\"mainForm\" method=\"post\" enctype=\"multipart/form-data\" action=\"EmpSubmit\">	<input type=\"hidden\" name=\"hiddenParameter\" value=\"123\">	<p>File(only *.jpg case sensitive): <input type=\"file\" name=\"file\"></p>		<p><input type=\"submit\" value=\"Upload\"></p>	</form></td>  ");
						if(perarray[0]=='e')
							pw.println(myIndex.section6+"Employee"+myIndex.section7+date+myIndex.section8+time+myIndex.section9);
							else if(perarray[0]=='a')
								pw.println(myIndex.section6+"Administrator"+myIndex.section7+date+myIndex.section8+time+myIndex.section9);
								
							
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