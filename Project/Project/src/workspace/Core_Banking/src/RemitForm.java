

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
 * Servlet implementation class for Servlet: RemitForm
 *
 */
 public class RemitForm extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public RemitForm() {
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
			String dacnum=ctx.getAttribute("dacnum").toString();
			String user=ctx.getAttribute("user").toString();
			String duser=ctx.getAttribute("duser").toString();
			String amt=ctx.getAttribute("amt").toString();
			String id=ctx.getAttribute("idnum").toString();
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
				 query = "select * from holder where holder.user='"+user+"' and holder.idnum='"+id+"'";
				   rset=stmt.executeQuery(query);
				  
				   String snation="";
				   boolean enter=false;
				   while(rset.next())
				   {
					   enter=true;
				   snation = rset.getString(20);
				   break;
				   }
				   if(!enter)
				   {
					   pw.println("<td valign=\"top\" width=580><b>Invalid Information</td>");
				   }
				   else
				   {
				   query = "select * from currency where currency.nation='"+snation+"'";
				   rset=stmt.executeQuery(query);
				  
				   rset.next();
				   float scrValue = rset.getFloat(4);	//getting source account country
				   query = "select * from holder where holder.user='"+duser+"'";
				   
				   rset=stmt.executeQuery(query);
				  
				   rset.next();
				   String dnation = rset.getString(20);	//getting destination account country
				   query = "select * from currency where currency.nation='"+dnation+"'";
				   rset=stmt.executeQuery(query);
				   
				   rset.next();
				   float dcrValue = rset.getFloat(4);
				   if(Integer.parseInt(amt)*scrValue < 50)
				   {
					pw.println("<td valign=\"top\" width=580><b>Minimum tranferrable amount should be equivalent to 50 US dollars</td>");
					
				   }
				   else
				   {
					   float fee=(float)(Integer.parseInt(amt)*0.02);		//  2% processing fee for money transfer
					   float substract = Integer.parseInt(amt)+fee;
					   
					   query="select * from account where account.user='"+user+"'";
					   rset=stmt.executeQuery(query);
					   rset.next();
					   float bal=rset.getFloat(6);
					   if(bal-substract>=500)
					   {
					   
					   query = "update account set account.balance=account.balance-"+substract+" where account.user='"+user+"'";
					   stmt.executeUpdate(query);	//updating source account
					   
					   query="select * from cnt where cnt.name='TransID'";
					   rset=stmt.executeQuery(query);
					   
					   rset.next();
					   int TID=rset.getInt(2)+1;
					   query="update cnt set cnt.cnt1=cnt.cnt1+1 where cnt.name='TransID'";
					   stmt.executeUpdate(query);
					  
			//debit(user varchar(15),damount float,dod date,idtpe varchar(20),idno varchar(20),transid1 varchar(20),empid varchar(15))
					   query = "insert into debit values('"+user+"',"+Integer.parseInt(amt)+",'"+date1+"."+month+"."+year+"','To "+dacnum+"','"+id+"','"+TID+"','"+per+"')";
					   stmt.executeUpdate(query);	//inserting transaction values into debit table
					  
					   query = "insert into debit values('"+user+"',"+fee+",'"+date1+"."+month+"."+year+"','To Remit Commission"+"','"+"Bank"+"','"+TID+"','"+per+"')";
					   stmt.executeUpdate(query);	
					   
					   
					   float damount = (Integer.parseInt(amt)*scrValue)/dcrValue;
					   query = "update account set account.balance=account.balance+"+damount+" where account.user='"+duser+"'";
					   stmt.executeUpdate(query);	//updating destination account
					   
					   //credit(user varchar(15),amount float,dot date,initials varchar(20),transid varchar(20),empid varchar(15))
					   query = "insert into credit values('"+duser+"',"+damount+",'"+date1+"."+month+"."+year+"','By Transfer:"+acnum+"','"+TID+"','"+per+"')";
					   
					   stmt.executeUpdate(query);	//inserting transaction values into credit table
					   pw.println("<td valign=\"top\" width=580><b>Amount Remitted Successfully<br>Transaction ID:"+TID+"</td>");
					   }
					   else
					   {
						   pw.println("<td valign=\"top\" width=580><b>Insufficient Balance</td>");
					   }
					  
					   
				   }
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
				
			
			String acnum=ctx.getAttribute("acnum").toString();
			String dacnum=ctx.getAttribute("dacnum").toString();
			String user=ctx.getAttribute("user").toString();
			String duser=ctx.getAttribute("duser").toString();
			String amt=ctx.getAttribute("amt").toString();
			String id=ctx.getAttribute("idnum").toString();
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
				 query = "select * from holder where holder.user='"+user+"' and holder.idnum='"+id+"'";
				   rset=stmt.executeQuery(query);
				  
				   String snation="";
				   boolean enter=false;
				   while(rset.next())
				   {
					   enter=true;
				   snation = rset.getString(20);
				   break;
				   }
				   if(!enter)
				   {
					   pw.println("<td valign=\"top\" width=580><b>Invalid Information</td>");
				   }
				   else
				   {
				   query = "select * from currency where currency.nation='"+snation+"'";
				   rset=stmt.executeQuery(query);
				  
				   rset.next();
				   float scrValue = rset.getFloat(4);	//getting source account country
				   query = "select * from holder where holder.user='"+duser+"'";
				   
				   rset=stmt.executeQuery(query);
				  
				   rset.next();
				   String dnation = rset.getString(20);	//getting destination account country
				   query = "select * from currency where currency.nation='"+dnation+"'";
				   rset=stmt.executeQuery(query);
				   
				   rset.next();
				   float dcrValue = rset.getFloat(4);
				   if(Integer.parseInt(amt)*scrValue < 50)
				   {
					pw.println("<td valign=\"top\" width=580><b>Minimum tranferrable amount should be equivalent to 50 US dollars</td>");
					
				   }
				   else
				   {
					   float fee=(float)(Integer.parseInt(amt)*0.02);		//  2% processing fee for money transfer
					   float substract = Integer.parseInt(amt)+fee;
					   
					   query="select * from account where account.user='"+user+"'";
					   rset=stmt.executeQuery(query);
					   rset.next();
					   float bal=rset.getFloat(6);
					   if(bal-substract>=500)
					   {
					   
					   query = "update account set account.balance=account.balance-"+substract+" where account.user='"+user+"'";
					   stmt.executeUpdate(query);	//updating source account
					   
					   query="select * from cnt where cnt.name='TransID'";
					   rset=stmt.executeQuery(query);
					   
					   rset.next();
					   int TID=rset.getInt(2)+1;
					   query="update cnt set cnt.cnt1=cnt.cnt1+1 where cnt.name='TransID'";
					   stmt.executeUpdate(query);
					  
			//debit(user varchar(15),damount float,dod date,idtpe varchar(20),idno varchar(20),transid1 varchar(20),empid varchar(15))
					   query = "insert into debit values('"+user+"',"+Integer.parseInt(amt)+",'"+date1+"."+month+"."+year+"','To "+dacnum+"','"+id+"','"+TID+"','"+per+"')";
					   stmt.executeUpdate(query);	//inserting transaction values into debit table
					  
					   query = "insert into debit values('"+user+"',"+fee+",'"+date1+"."+month+"."+year+"','To Remit Commission"+"','"+"Bank"+"','"+TID+"','"+per+"')";
					   stmt.executeUpdate(query);	
					   
					   
					   float damount = (Integer.parseInt(amt)*scrValue)/dcrValue;
					   query = "update account set account.balance=account.balance+"+damount+" where account.user='"+duser+"'";
					   stmt.executeUpdate(query);	//updating destination account
					   
					   //credit(user varchar(15),amount float,dot date,initials varchar(20),transid varchar(20),empid varchar(15))
					   query = "insert into credit values('"+duser+"',"+damount+",'"+date1+"."+month+"."+year+"','By Transfer:"+acnum+"','"+TID+"','"+per+"')";
					   
					   stmt.executeUpdate(query);	//inserting transaction values into credit table
					   pw.println("<td valign=\"top\" width=580><b>Amount Remitted Successfully<br>Transaction ID:"+TID+"</td>");
					   }
					   else
					   {
						   pw.println("<td valign=\"top\" width=580><b>Insufficient Balance</td>");
					   }
					  
					   
				   }
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