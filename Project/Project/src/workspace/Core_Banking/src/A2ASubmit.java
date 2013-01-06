

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
import java.sql.SQLException;
/**
 * Servlet implementation class for Servlet: A2ASubmit
 *
 */
 public class A2ASubmit extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public A2ASubmit() {
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
		ResultSet rset2 = null;
		
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
					pw.println("<html><head><meta http-equiv=\"content-type\" content=\"text/html;charset=utf-8\"><TITLE>Core Banking Solutions 1.0</TITLE><SCRIPT LANGUAGE=JavaScript>function butCheckForm_onclick(){   var myForm = document.form1;   if (myForm.txtAge.value == \"\" || myForm.txtName.value == \"\")   {      alert(\"Please complete all the form\");  }}</SCRIPT><script type=\"text/javascript\">var dmWorkPath=\"deluxe-tabs.files/\";</script><script type=\"text/javascript\" src=\"deluxe-tabs.files/dtabs.js\"></script></head><body bgcolor=\"#FFFFFF\"><img border=0 src=\"images/logo.jpg\" width=980 height=97><script type=\"text/javascript\" src=\"javascripts/data-deluxe-tabs.js\"></script><br>"+myIndex.customer);
					String DestaccNum = request.getParameter("userid");
					String IDnum  = request.getParameter("proof");
					String PayAmount= request.getParameter("amount");
					String destUID = "";
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
					 String CompDate = date1+"."+month+"."+year ;
					 query = "select * from holder where holder.user='"+per+"' and holder.idnum='"+IDnum+"'";
					 rset = stmt.executeQuery(query); 
					 boolean enter=false;
					 while(rset.next() )
					 {
						 enter=true;
						 break;
				     }
					 if(!enter)
					 {
						 pw.println("<td valign=\"top\" align=\"center\" width=580><font color=\"RED\" size=3>Invalid Information!</font>	</td>");
					 }
					 else
					 {
					 query = "select * from account where account.accno="+DestaccNum;
						 
					 rset=stmt.executeQuery(query);
					 enter=false;
					 while(rset.next())
					 {
						 enter=true;
						 destUID+=rset.getString(1);
						 break;
					 }
					 if(!enter)
					 {
						 pw.println("<td valign=\"top\" align=\"center\" width=580><font color=\"RED\" size=3>Both the users must be from this bank and non-NRIs !</font>	</td>");
					 }
					 else
					 {
					 	query = "select * from cnt where cnt.name='TransID'";
						rset = stmt.executeQuery(query);
						rset.next();
						int TID = rset.getInt(2) +1;
						query = "update cnt set cnt.cnt1="+TID+" where cnt.name='TransID'";
						stmt.executeUpdate(query);	
						
				
				 		query="select * from account where account.user='"+per+"'";
				     	rset = stmt.executeQuery(query);
				     	rset.next();
				     	
						    if( (int)rset.getFloat(6) >= Integer.parseInt(PayAmount)+(int)(0.02*Integer.parseInt(PayAmount)))
				          	{
						    //allow transaction 
				          	// increasing balance of dest id user varchar(15),amount float,dot date,initials varchar(20)
				          	
						    query = "insert into credit values('"+destUID+"',"+PayAmount+",'"+CompDate+"','By Transfer'"+",'"+TID+"','NA'"+")";
							stmt.executeUpdate(query);
							
							query = "update account set account.balance=account.balance+"+PayAmount+" where account.user="+ "'"+destUID+"'";
							stmt.executeUpdate(query);
							
							int fine=Integer.parseInt(PayAmount)+(int)(0.02*Integer.parseInt(PayAmount));
							//decresing balance from source id debit(user varchar(15),damount float,dod date,idtpe varchar(20),idno varchar(20))
							query = "insert into debit values('"+per+"',"+fine+".00,'"+CompDate+"','Trans to "+DestaccNum+"','"+IDnum+"','"+TID+"','NA'"+")";
							stmt.executeUpdate(query);
							
							query = "update account set account.balance=account.balance-"+PayAmount+" where account.user="+ "'"+per+"'";
							stmt.executeUpdate(query);
							
							pw.println("<td valign=\"top\" align=\"center\" width=580><font color=\"RED\" size=3>Money Transferred Successfully!</font>	</td>");
				          	}
				          	else
				          	{
				          		pw.println("<td valign=\"top\" align=\"center\" width=580><font color=\"RED\" size=3>Insufficient Balance in your Account!</font>	</td>");
				          	}
						       		
					 }
						 }
				pw.println(myIndex.section6+"Customer"+myIndex.section7+date+myIndex.section8+time+myIndex.section9);
				
			}
			else if(perarray[0]=='e')
			{
				//employee
				pw.println("<html><head><meta http-equiv=\"content-type\" content=\"text/html;charset=utf-8\"><TITLE>Core Banking Solutions 1.0</TITLE><SCRIPT LANGUAGE=JavaScript>function butCheckForm_onclick(){   var myForm = document.form1;   if (myForm.txtAge.value == \"\" || myForm.txtName.value == \"\")   {      alert(\"Please complete all the form\");  }}</SCRIPT><script type=\"text/javascript\">var dmWorkPath=\"deluxe-tabs.files/\";</script><script type=\"text/javascript\" src=\"deluxe-tabs.files/dtabs.js\"></script></head><body bgcolor=\"#FFFFFF\"><img border=0 src=\"images/logo.jpg\" width=980 height=97><script type=\"text/javascript\" src=\"javascripts/data-deluxe-tabs.js\"></script><br>"+myIndex.customer);
				String DestaccNum = request.getParameter("userid");
				String IDnum  = request.getParameter("proof");
				String PayAmount= request.getParameter("amount");
				String destUID = "";
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
				 String CompDate = date1+"."+month+"."+year ;
				 query = "select * from holder where holder.user='"+per+"' and holder.idnum='"+IDnum+"'";
				 rset = stmt.executeQuery(query); 
				 boolean enter=false;
				 while(rset.next() )
				 {
					 enter=true;
					 break;
			     }
				 if(!enter)
				 {
					 pw.println("<td valign=\"top\" align=\"center\" width=580><font color=\"RED\" size=3>Invalid Information!</font>	</td>");
				 }
				 else
				 {
				 query = "select * from account where account.accno="+DestaccNum;
					 
				 rset=stmt.executeQuery(query);
				 enter=false;
				 while(rset.next())
				 {
					 enter=true;
					 destUID+=rset.getString(1);
					 break;
				 }
				 if(!enter)
				 {
					 pw.println("<td valign=\"top\" align=\"center\" width=580><font color=\"RED\" size=3>Both the users must be from this bank and non-NRIs !</font>	</td>");
				 }
				 else
				 {
				 	query = "select * from cnt where cnt.name='TransID'";
					rset = stmt.executeQuery(query);
					rset.next();
					int TID = rset.getInt(2) +1;
					query = "update cnt set cnt.cnt1="+TID+" where cnt.name='TransID'";
					stmt.executeUpdate(query);	
					
			
			 		query="select * from account where account.user='"+per+"'";
			     	rset = stmt.executeQuery(query);
			     	rset.next();
			     	
					    if( (int)rset.getFloat(6) >= Integer.parseInt(PayAmount)+(int)(0.02*Integer.parseInt(PayAmount)))
			          	{
					    //allow transaction 
			          	// increasing balance of dest id user varchar(15),amount float,dot date,initials varchar(20)
			          	
					    query = "insert into credit values('"+destUID+"',"+PayAmount+",'"+CompDate+"','By Transfer'"+",'"+TID+"','NA'"+")";
						stmt.executeUpdate(query);
						
						query = "update account set account.balance=account.balance+"+PayAmount+" where account.user="+ "'"+destUID+"'";
						stmt.executeUpdate(query);
						
						int fine=Integer.parseInt(PayAmount)+(int)(0.02*Integer.parseInt(PayAmount));
						//decresing balance from source id debit(user varchar(15),damount float,dod date,idtpe varchar(20),idno varchar(20))
						query = "insert into debit values('"+per+"',"+fine+".00,'"+CompDate+"','Trans to "+DestaccNum+"','"+IDnum+"','"+TID+"','NA'"+")";
						stmt.executeUpdate(query);
						
						query = "update account set account.balance=account.balance-"+PayAmount+" where account.user="+ "'"+per+"'";
						stmt.executeUpdate(query);
						
						pw.println("<td valign=\"top\" align=\"center\" width=580><font color=\"RED\" size=3>Money Transferred Successfully!</font>	</td>");
			          	}
			          	else
			          	{
			          		pw.println("<td valign=\"top\" align=\"center\" width=580><font color=\"RED\" size=3>Insufficient Balance in your Account!</font></td>");
			          	}
					       		
				 }
					 }
				pw.println(myIndex.section6+"Employee"+myIndex.section7+date+myIndex.section8+time+myIndex.section9);
				
			}
			else if(perarray[0]=='a')
			{
				//admin
				
				pw.println("<html><head><meta http-equiv=\"content-type\" content=\"text/html;charset=utf-8\"><TITLE>Core Banking Solutions 1.0</TITLE><SCRIPT LANGUAGE=JavaScript>function butCheckForm_onclick(){   var myForm = document.form1;   if (myForm.txtAge.value == \"\" || myForm.txtName.value == \"\")   {      alert(\"Please complete all the form\");  }}</SCRIPT><script type=\"text/javascript\">var dmWorkPath=\"deluxe-tabs.files/\";</script><script type=\"text/javascript\" src=\"deluxe-tabs.files/dtabs.js\"></script></head><body bgcolor=\"#FFFFFF\"><img border=0 src=\"images/logo.jpg\" width=980 height=97><script type=\"text/javascript\" src=\"javascripts/data-deluxe-tabs.js\"></script><br>"+myIndex.customer);
				String DestaccNum = request.getParameter("userid");
				String IDnum  = request.getParameter("proof");
				String PayAmount= request.getParameter("amount");
				String destUID = "";
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
				 String CompDate = date1+"."+month+"."+year ;
				 query = "select * from holder where holder.user='"+per+"' and holder.idnum='"+IDnum+"'";
				 rset = stmt.executeQuery(query); 
				 boolean enter=false;
				 while(rset.next() )
				 {
					 enter=true;
					 break;
			     }
				 if(!enter)
				 {
					 pw.println("<td valign=\"top\" align=\"center\" width=580><font color=\"RED\" size=3>Invalid Information!</font>	</td>");
				 }
				 else
				 {
				 query = "select * from account where account.accno="+DestaccNum;
					 
				 rset=stmt.executeQuery(query);
				 enter=false;
				 while(rset.next())
				 {
					 enter=true;
					 destUID+=rset.getString(1);
					 break;
				 }
				 if(!enter)
				 {
					 pw.println("<td valign=\"top\" align=\"center\" width=580><font color=\"RED\" size=3>Both the users must be from this bank and non-NRIs !</font></td>");
				 }
				 else
				 {
				 	query = "select * from cnt where cnt.name='TransID'";
					rset = stmt.executeQuery(query);
					rset.next();
					int TID = rset.getInt(2) +1;
					query = "update cnt set cnt.cnt1="+TID+" where cnt.name='TransID'";
					stmt.executeUpdate(query);	
					
			
			 		query="select * from account where account.user='"+per+"'";
			     	rset = stmt.executeQuery(query);
			     	rset.next();
			     	
					    if( (int)rset.getFloat(6) >= Integer.parseInt(PayAmount)+(int)(0.02*Integer.parseInt(PayAmount)))
			          	{
					    //allow transaction 
			          	// increasing balance of dest id user varchar(15),amount float,dot date,initials varchar(20)
			          	
					    query = "insert into credit values('"+destUID+"',"+PayAmount+",'"+CompDate+"','By Transfer'"+",'"+TID+"','NA'"+")";
						stmt.executeUpdate(query);
						
						query = "update account set account.balance=account.balance+"+PayAmount+" where account.user="+ "'"+destUID+"'";
						stmt.executeUpdate(query);
						
						int fine=Integer.parseInt(PayAmount)+(int)(0.02*Integer.parseInt(PayAmount));
						//decresing balance from source id debit(user varchar(15),damount float,dod date,idtpe varchar(20),idno varchar(20))
						query = "insert into debit values('"+per+"',"+fine+".00,'"+CompDate+"','Trans to "+DestaccNum+"','"+IDnum+"','"+TID+"','NA'"+")";
						stmt.executeUpdate(query);
						
						query = "update account set account.balance=account.balance-"+PayAmount+" where account.user="+ "'"+per+"'";
						stmt.executeUpdate(query);
						
						pw.println("<td valign=\"top\" align=\"center\" width=580><font color=\"RED\" size=3>Money Transferred Successfully!</font>	</td>");
			          	}
			          	else
			          	{
			          		pw.println("<td valign=\"top\" align=\"center\" width=580><font color=\"RED\" size=3>Insufficient Balance in your Account!</font>	</td>");
			          	}
					       		
				 }
					 }
			}
				
				pw.println(myIndex.section6+"Administrator"+myIndex.section7+date+myIndex.section8+time+myIndex.section9);
				
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
		ResultSet rset2 = null;
		
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
					pw.println("<html><head><meta http-equiv=\"content-type\" content=\"text/html;charset=utf-8\"><TITLE>Core Banking Solutions 1.0</TITLE><SCRIPT LANGUAGE=JavaScript>function butCheckForm_onclick(){   var myForm = document.form1;   if (myForm.txtAge.value == \"\" || myForm.txtName.value == \"\")   {      alert(\"Please complete all the form\");  }}</SCRIPT><script type=\"text/javascript\">var dmWorkPath=\"deluxe-tabs.files/\";</script><script type=\"text/javascript\" src=\"deluxe-tabs.files/dtabs.js\"></script></head><body bgcolor=\"#FFFFFF\"><img border=0 src=\"images/logo.jpg\" width=980 height=97><script type=\"text/javascript\" src=\"javascripts/data-deluxe-tabs.js\"></script><br>"+myIndex.customer);
					String DestaccNum = request.getParameter("userid");
					String IDnum  = request.getParameter("proof");
					String PayAmount= request.getParameter("amount");
					String destUID = "";
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
					 String CompDate = date1+"."+month+"."+year ;
					 query = "select * from holder where holder.user='"+per+"' and holder.idnum='"+IDnum+"'";
					 rset = stmt.executeQuery(query); 
					 boolean enter=false;
					 while(rset.next() )
					 {
						 enter=true;
						 break;
				     }
					 if(!enter)
					 {
						 pw.println("<td valign=\"top\" align=\"center\" width=580><font color=\"RED\" size=3>Invalid Information!</font>	</td>");
					 }
					 else
					 {
					 query = "select * from account where account.accno="+DestaccNum;
						 
					 rset=stmt.executeQuery(query);
					 enter=false;
					 while(rset.next())
					 {
						 enter=true;
						 destUID+=rset.getString(1);
						 break;
					 }
					 if(!enter)
					 {
						 pw.println("<td valign=\"top\" align=\"center\" width=580><font color=\"RED\" size=3>Both the users must be from this bank and non-NRIs !</font>	</td>");
					 }
					 else
					 {
					 	query = "select * from cnt where cnt.name='TransID'";
						rset = stmt.executeQuery(query);
						rset.next();
						int TID = rset.getInt(2) +1;
						query = "update cnt set cnt.cnt1="+TID+" where cnt.name='TransID'";
						stmt.executeUpdate(query);	
						
				
				 		query="select * from account where account.user='"+per+"'";
				     	rset = stmt.executeQuery(query);
				     	rset.next();
				     	
						    if( (int)rset.getFloat(6) >= Integer.parseInt(PayAmount)+(int)(0.02*Integer.parseInt(PayAmount)))
				          	{
						    //allow transaction 
				          	// increasing balance of dest id user varchar(15),amount float,dot date,initials varchar(20)
				          	
						    query = "insert into credit values('"+destUID+"',"+PayAmount+",'"+CompDate+"','By Transfer'"+",'"+TID+"','NA'"+")";
							stmt.executeUpdate(query);
							
							query = "update account set account.balance=account.balance+"+PayAmount+" where account.user="+ "'"+destUID+"'";
							stmt.executeUpdate(query);
							
							int fine=Integer.parseInt(PayAmount)+(int)(0.02*Integer.parseInt(PayAmount));
							//decresing balance from source id debit(user varchar(15),damount float,dod date,idtpe varchar(20),idno varchar(20))
							query = "insert into debit values('"+per+"',"+fine+".00,'"+CompDate+"','Trans to "+DestaccNum+"','"+IDnum+"','"+TID+"','NA'"+")";
							stmt.executeUpdate(query);
							
							query = "update account set account.balance=account.balance-"+PayAmount+" where account.user="+ "'"+per+"'";
							stmt.executeUpdate(query);
							
							pw.println("<td valign=\"top\" align=\"center\" width=580><font color=\"RED\" size=3>Money Transferred Successfully!</font>	</td>");
				          	}
				          	else
				          	{
				          		pw.println("<td valign=\"top\" align=\"center\" width=580><font color=\"RED\" size=3>Insufficient Balance in your Account!</font>	</td>");
				          	}
						       		
					 }
						 }
				pw.println(myIndex.section6+"Customer"+myIndex.section7+date+myIndex.section8+time+myIndex.section9);
				
			}
			else if(perarray[0]=='e')
			{
				//employee
				pw.println("<html><head><meta http-equiv=\"content-type\" content=\"text/html;charset=utf-8\"><TITLE>Core Banking Solutions 1.0</TITLE><SCRIPT LANGUAGE=JavaScript>function butCheckForm_onclick(){   var myForm = document.form1;   if (myForm.txtAge.value == \"\" || myForm.txtName.value == \"\")   {      alert(\"Please complete all the form\");  }}</SCRIPT><script type=\"text/javascript\">var dmWorkPath=\"deluxe-tabs.files/\";</script><script type=\"text/javascript\" src=\"deluxe-tabs.files/dtabs.js\"></script></head><body bgcolor=\"#FFFFFF\"><img border=0 src=\"images/logo.jpg\" width=980 height=97><script type=\"text/javascript\" src=\"javascripts/data-deluxe-tabs.js\"></script><br>"+myIndex.customer);
				String DestaccNum = request.getParameter("userid");
				String IDnum  = request.getParameter("proof");
				String PayAmount= request.getParameter("amount");
				String destUID = "";
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
				 String CompDate = date1+"."+month+"."+year ;
				 query = "select * from holder where holder.user='"+per+"' and holder.idnum='"+IDnum+"'";
				 rset = stmt.executeQuery(query); 
				 boolean enter=false;
				 while(rset.next() )
				 {
					 enter=true;
					 break;
			     }
				 if(!enter)
				 {
					 pw.println("<td valign=\"top\" align=\"center\" width=580><font color=\"RED\" size=3>Invalid Information!</font>	</td>");
				 }
				 else
				 {
				 query = "select * from account where account.accno="+DestaccNum;
					 
				 rset=stmt.executeQuery(query);
				 enter=false;
				 while(rset.next())
				 {
					 enter=true;
					 destUID+=rset.getString(1);
					 break;
				 }
				 if(!enter)
				 {
					 pw.println("<td valign=\"top\" align=\"center\" width=580><font color=\"RED\" size=3>Both the users must be from this bank and non-NRIs !</font>	</td>");
				 }
				 else
				 {
				 	query = "select * from cnt where cnt.name='TransID'";
					rset = stmt.executeQuery(query);
					rset.next();
					int TID = rset.getInt(2) +1;
					query = "update cnt set cnt.cnt1="+TID+" where cnt.name='TransID'";
					stmt.executeUpdate(query);	
					
			
			 		query="select * from account where account.user='"+per+"'";
			     	rset = stmt.executeQuery(query);
			     	rset.next();
			     	
					    if( (int)rset.getFloat(6) >= Integer.parseInt(PayAmount)+(int)(0.02*Integer.parseInt(PayAmount)))
			          	{
					    //allow transaction 
			          	// increasing balance of dest id user varchar(15),amount float,dot date,initials varchar(20)
			          	
					    query = "insert into credit values('"+destUID+"',"+PayAmount+",'"+CompDate+"','By Transfer'"+",'"+TID+"','NA'"+")";
						stmt.executeUpdate(query);
						
						query = "update account set account.balance=account.balance+"+PayAmount+" where account.user="+ "'"+destUID+"'";
						stmt.executeUpdate(query);
						
						int fine=Integer.parseInt(PayAmount)+(int)(0.02*Integer.parseInt(PayAmount));
						//decresing balance from source id debit(user varchar(15),damount float,dod date,idtpe varchar(20),idno varchar(20))
						query = "insert into debit values('"+per+"',"+fine+".00,'"+CompDate+"','Trans to "+DestaccNum+"','"+IDnum+"','"+TID+"','NA'"+")";
						stmt.executeUpdate(query);
						
						query = "update account set account.balance=account.balance-"+PayAmount+" where account.user="+ "'"+per+"'";
						stmt.executeUpdate(query);
						
						pw.println("<td valign=\"top\" align=\"center\" width=580><font color=\"RED\" size=3>Money Transferred Successfully!</font>	</td>");
			          	}
			          	else
			          	{
			          		pw.println("<td valign=\"top\" align=\"center\" width=580><font color=\"RED\" size=3>Insufficient Balance in your Account!</font></td>");
			          	}
					       		
				 }
					 }
				pw.println(myIndex.section6+"Employee"+myIndex.section7+date+myIndex.section8+time+myIndex.section9);
				
			}
			else if(perarray[0]=='a')
			{
				//admin
				
				pw.println("<html><head><meta http-equiv=\"content-type\" content=\"text/html;charset=utf-8\"><TITLE>Core Banking Solutions 1.0</TITLE><SCRIPT LANGUAGE=JavaScript>function butCheckForm_onclick(){   var myForm = document.form1;   if (myForm.txtAge.value == \"\" || myForm.txtName.value == \"\")   {      alert(\"Please complete all the form\");  }}</SCRIPT><script type=\"text/javascript\">var dmWorkPath=\"deluxe-tabs.files/\";</script><script type=\"text/javascript\" src=\"deluxe-tabs.files/dtabs.js\"></script></head><body bgcolor=\"#FFFFFF\"><img border=0 src=\"images/logo.jpg\" width=980 height=97><script type=\"text/javascript\" src=\"javascripts/data-deluxe-tabs.js\"></script><br>"+myIndex.customer);
				String DestaccNum = request.getParameter("userid");
				String IDnum  = request.getParameter("proof");
				String PayAmount= request.getParameter("amount");
				String destUID = "";
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
				 String CompDate = date1+"."+month+"."+year ;
				 query = "select * from holder where holder.user='"+per+"' and holder.idnum='"+IDnum+"'";
				 rset = stmt.executeQuery(query); 
				 boolean enter=false;
				 while(rset.next() )
				 {
					 enter=true;
					 break;
			     }
				 if(!enter)
				 {
					 pw.println("<td valign=\"top\" align=\"center\" width=580><font color=\"RED\" size=3>Invalid Information!</font>	</td>");
				 }
				 else
				 {
				 query = "select * from account where account.accno="+DestaccNum;
					 
				 rset=stmt.executeQuery(query);
				 enter=false;
				 while(rset.next())
				 {
					 enter=true;
					 destUID+=rset.getString(1);
					 break;
				 }
				 if(!enter)
				 {
					 pw.println("<td valign=\"top\" align=\"center\" width=580><font color=\"RED\" size=3>Both the users must be from this bank and non-NRIs !</font></td>");
				 }
				 else
				 {
				 	query = "select * from cnt where cnt.name='TransID'";
					rset = stmt.executeQuery(query);
					rset.next();
					int TID = rset.getInt(2) +1;
					query = "update cnt set cnt.cnt1="+TID+" where cnt.name='TransID'";
					stmt.executeUpdate(query);	
					
			
			 		query="select * from account where account.user='"+per+"'";
			     	rset = stmt.executeQuery(query);
			     	rset.next();
			     	
					    if( (int)rset.getFloat(6) >= Integer.parseInt(PayAmount)+(int)(0.02*Integer.parseInt(PayAmount)))
			          	{
					    //allow transaction 
			          	// increasing balance of dest id user varchar(15),amount float,dot date,initials varchar(20)
			          	
					    query = "insert into credit values('"+destUID+"',"+PayAmount+",'"+CompDate+"','By Transfer'"+",'"+TID+"','NA'"+")";
						stmt.executeUpdate(query);
						
						query = "update account set account.balance=account.balance+"+PayAmount+" where account.user="+ "'"+destUID+"'";
						stmt.executeUpdate(query);
						
						int fine=Integer.parseInt(PayAmount)+(int)(0.02*Integer.parseInt(PayAmount));
						//decresing balance from source id debit(user varchar(15),damount float,dod date,idtpe varchar(20),idno varchar(20))
						query = "insert into debit values('"+per+"',"+fine+".00,'"+CompDate+"','Trans to "+DestaccNum+"','"+IDnum+"','"+TID+"','NA'"+")";
						stmt.executeUpdate(query);
						
						query = "update account set account.balance=account.balance-"+PayAmount+" where account.user="+ "'"+per+"'";
						stmt.executeUpdate(query);
						
						pw.println("<td valign=\"top\" align=\"center\" width=580><font color=\"RED\" size=3>Money Transferred Successfully!</font>	</td>");
			          	}
			          	else
			          	{
			          		pw.println("<td valign=\"top\" align=\"center\" width=580><font color=\"RED\" size=3>Insufficient Balance in your Account!</font>	</td>");
			          	}
					       		
				 }
					 }
			}
				
				pw.println(myIndex.section6+"Administrator"+myIndex.section7+date+myIndex.section8+time+myIndex.section9);
				
			 }
			catch(Exception ex)
			{
				
			}
		}
	}   	  	    
}