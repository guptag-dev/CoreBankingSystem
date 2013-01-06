

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
 * Servlet implementation class for Servlet: LOGpaid
 *
 */
 public class LOGpaid extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public LOGpaid() {
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
			String EmpUid=per;
			
			String gid = request.getParameter("gid");
			ctx.setAttribute("gid", gid);
			String medium = request.getParameter("medium");
			ctx.setAttribute("medium", medium);
			String ACno = request.getParameter("acnum");
			ctx.setAttribute("acnum",ACno);
			int accno = Integer.parseInt(ACno);
			
			String amt = request.getParameter("amt");
			ctx.setAttribute("amt",ACno);
			int amount = Integer.parseInt(amt);
			
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
				
			String Doi = day+"."+month+"."+year ;
			int mn=Integer.parseInt(month)+1;
			String Doi1=day+"."+mn+"."+year;
			{
				query="select * from log1 where log1.gid="+gid;
				rset=stmt.executeQuery(query);
				rset.next();
				int acc =(int) rset.getLong(4);
				int ml=(int)rset.getLong(6);
				query="select * from pay where pay.gid="+gid;
				rset=stmt.executeQuery(query);
				
				rset.next();
				float paidamt=rset.getFloat(2);
				if(paidamt>ml)
				{
					pw.println("<td valign=\"top\" width=580>Paid Amount has exceeded the maximum limit of this letter. Can't process any futhur</td>");
				}
				else
				{
					if(paidamt+amount>ml)
					{
						pw.println("<td valign=\"top\" width=580>Requested amount cannot be granted as it leads to surpassing the maximum limit</td>");
					}
					else
					{
						query="select * from account where account.accno="+acc;
						rset=stmt.executeQuery(query);
						
						rset.next();
						float bal=rset.getFloat(6);
						String user=rset.getString(1);
						
						{
							
							
							query="select * from cnt where cnt.name='TransID'";
							rset=stmt.executeQuery(query);
							rset.next();
							
							int tid=rset.getInt(2)+1;
							query="update cnt set cnt.cnt1=cnt.cnt1+1 where cnt.name='TransID'";
							
							stmt.executeUpdate(query);
							
							//debit(user varchar(15),damount float,dod date,idtpe varchar(30),idno varchar(20),transid1 varchar(20),empid varchar(15))
							if(bal-amount>=500)
							{
							query="insert into debit values('"+user+"',"+amount+",'"+Doi+"','To Guarantee Letter "+gid+"','NA','"+tid+"','"+per+"')";
							stmt.executeUpdate(query);
							
							query="update account set account.balance=account.balance-"+amount+" where account.accno="+acc;
							stmt.executeUpdate(query);
							}
							else
							{
								query="select * from cnt where cnt.name='Loan'";
								rset=stmt.executeQuery(query);
								rset.next();
								int ln=rset.getInt(2)+1;
								query="update cnt set cnt.cnt1=cnt.cnt1+1 where cnt.name='Loan'";
								stmt.executeUpdate(query);
								float emi=amount/12;
								//loan(user varchar(15),loannum varchar(10),lissue date,amtissue float,amtpaid float,startdate date,mstuff varchar(50),loantype varchar(30),repay int,overdue float,emi float)
								query="insert into loan values('"+user+"','L"+ln+"','"+Doi+"',"+amount+",0,'"+Doi1+"','Letter "+gid+"','Demand Loan',1,0.00,"+emi+")";
								stmt.executeUpdate(query);
								
							}
				
							query="update pay set pay.paidamt=pay.paidamt+"+amount+" where pay.gid="+gid;
							stmt.executeUpdate(query);
							
							query="update log1 set log1.stat='Paid' where log1.gid="+gid;
							stmt.executeUpdate(query);
							
							if(medium.equals("Account"))
							{
								query="select * from account where account.accno="+accno;
								rset=stmt.executeQuery(query);
								
								rset.next();
								
								String usr=rset.getString(1);
								//credit(user varchar(15),amount float,dot date,initials varchar(30),transid varchar(20),empid varchar(15))
								query="insert into credit values('"+usr+"',"+amount+",'"+Doi+"','By Guarantee Letter "+gid+"','"+tid+"','"+per+"')";
								stmt.executeUpdate(query);
								pw.println("<td valign=\"top\" width=580>Amount Credit to your Account</td>");
								
							}
							
						}
						
					}
				}
			}
					
							
				
				if(perarray[0]=='e')
				pw.println(myIndex.section6+"Employee"+myIndex.section7+date1+myIndex.section8+time1+myIndex.section9);
				else if(perarray[0]=='a')
					pw.println(myIndex.section6+"Administrator"+myIndex.section7+date1+myIndex.section8+time1+myIndex.section9);
			
			 
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
			String EmpUid=per;
			
			String gid = request.getParameter("gid");
			ctx.setAttribute("gid", gid);
			String medium = request.getParameter("medium");
			ctx.setAttribute("medium", medium);
			String ACno = request.getParameter("acnum");
			ctx.setAttribute("acnum",ACno);
			int accno = Integer.parseInt(ACno);
			
			String amt = request.getParameter("amt");
			ctx.setAttribute("amt",ACno);
			int amount = Integer.parseInt(amt);
			
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
				
			String Doi = day+"."+month+"."+year ;
			int mn=Integer.parseInt(month)+1;
			String Doi1=day+"."+mn+"."+year;
			{
				query="select * from log1 where log1.gid="+gid;
				rset=stmt.executeQuery(query);
				rset.next();
				int acc =(int) rset.getLong(4);
				int ml=(int)rset.getLong(6);
				query="select * from pay where pay.gid="+gid;
				rset=stmt.executeQuery(query);
				
				rset.next();
				float paidamt=rset.getFloat(2);
				if(paidamt>ml)
				{
					pw.println("<td valign=\"top\" width=580>Paid Amount has exceeded the maximum limit of this letter. Can't process any futhur</td>");
				}
				else
				{
					if(paidamt+amount>ml)
					{
						pw.println("<td valign=\"top\" width=580>Requested amount cannot be granted as it leads to surpassing the maximum limit</td>");
					}
					else
					{
						query="select * from account where account.accno="+acc;
						rset=stmt.executeQuery(query);
						
						rset.next();
						float bal=rset.getFloat(6);
						String user=rset.getString(1);
						
						{
							
							
							query="select * from cnt where cnt.name='TransID'";
							rset=stmt.executeQuery(query);
							rset.next();
							
							int tid=rset.getInt(2)+1;
							query="update cnt set cnt.cnt1=cnt.cnt1+1 where cnt.name='TransID'";
							
							stmt.executeUpdate(query);
							
							//debit(user varchar(15),damount float,dod date,idtpe varchar(30),idno varchar(20),transid1 varchar(20),empid varchar(15))
							if(bal-amount>=500)
							{
							query="insert into debit values('"+user+"',"+amount+",'"+Doi+"','To Guarantee Letter "+gid+"','NA','"+tid+"','"+per+"')";
							stmt.executeUpdate(query);
							
							query="update account set account.balance=account.balance-"+amount+" where account.accno="+acc;
							stmt.executeUpdate(query);
							}
							else
							{
								query="select * from cnt where cnt.name='Loan'";
								rset=stmt.executeQuery(query);
								rset.next();
								int ln=rset.getInt(2)+1;
								query="update cnt set cnt.cnt1=cnt.cnt1+1 where cnt.name='Loan'";
								stmt.executeUpdate(query);
								float emi=amount/12;
								//loan(user varchar(15),loannum varchar(10),lissue date,amtissue float,amtpaid float,startdate date,mstuff varchar(50),loantype varchar(30),repay int,overdue float,emi float)
								query="insert into loan values('"+user+"','L"+ln+"','"+Doi+"',"+amount+",0,'"+Doi1+"','Letter "+gid+"','Demand Loan',1,0.00,"+emi+")";
								stmt.executeUpdate(query);
								
							}
				
							query="update pay set pay.paidamt=pay.paidamt+"+amount+" where pay.gid="+gid;
							stmt.executeUpdate(query);
							
							query="update log1 set log1.stat='Paid' where log1.gid="+gid;
							stmt.executeUpdate(query);
							
							if(medium.equals("Account"))
							{
								query="select * from account where account.accno="+accno;
								rset=stmt.executeQuery(query);
								
								rset.next();
								
								String usr=rset.getString(1);
								//credit(user varchar(15),amount float,dot date,initials varchar(30),transid varchar(20),empid varchar(15))
								query="insert into credit values('"+usr+"',"+amount+",'"+Doi+"','By Guarantee Letter "+gid+"','"+tid+"','"+per+"')";
								stmt.executeUpdate(query);
								pw.println("<td valign=\"top\" width=580>Amount Credit to your Account</td>");
								
							}
							
						}
						
					}
				}
			}
					
							
				
				if(perarray[0]=='e')
				pw.println(myIndex.section6+"Employee"+myIndex.section7+date1+myIndex.section8+time1+myIndex.section9);
				else if(perarray[0]=='a')
					pw.println(myIndex.section6+"Administrator"+myIndex.section7+date1+myIndex.section8+time1+myIndex.section9);
			
			 
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