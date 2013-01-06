
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class for Servlet: LOGissued2
 *
 */
 public class LOGissued2 extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public LOGissued2() {
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
			String date1="",time1="";
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
			
			String Guarantee = ctx.getAttribute("Guarantee").toString();
			
			String Guaranteetype = ctx.getAttribute("Guaranteetype").toString();
			
			String bank  = ctx.getAttribute("bank").toString();
			
			String maxlimit = ctx.getAttribute("maxlimit").toString();
			
			String idno  = ctx.getAttribute("idno").toString();
			
			String ACno = ctx.getAttribute("ACno").toString();
			
			int accno = Integer.parseInt(ACno);
			String to = ctx.getAttribute("to").toString();
			
			String day1 = ctx.getAttribute("day1").toString();
			
			String month1 = ctx.getAttribute("month1").toString();
			
			String year1 = ctx.getAttribute("year1").toString();
			
			String Dov = day1+"."+month1+"."+year1;

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
			  
			
					
			query = "select * from account where account.accno="+accno+"";	
				rset=stmt.executeQuery(query);
				
				rset.next();
				String UserID = rset.getString(1) ;
				query = "select * from holder where holder.idnum='"+idno+"'";	
					rset=stmt.executeQuery(query);
					
				rset.next();
					int holder = rset.getInt(2);
					
					query = "select * from cnt where cnt.name='TransID'";
					rset=stmt.executeQuery(query);
					
					rset.next();
					int TransID;
					TransID=rset.getInt(2)+1 ;
					query = "update cnt set cnt.cnt1=cnt.cnt1+1 where cnt.name='TransID'"; 
					stmt.executeUpdate(query);
					
					query = "select * from cnt where cnt.name='LOG'";
					rset=stmt.executeQuery(query);
								
					rset.next();
					int gid;
					gid=rset.getInt(2)+1 ;
					query = "update cnt set cnt.cnt1=cnt.cnt1+1 where cnt.name='LOG'"; 
					stmt.executeUpdate(query);
					
					query="insert into log1 values('"+Guarantee+"','"+Guaranteetype+"','"+bank+"',"+accno+",'"+idno+"',"+maxlimit+",'"+to+"','"+Doi+"','"+Dov+"','"+TransID+"','Issued',"+gid+")";
				  	stmt.executeUpdate(query);
				  	
				  	query="insert into pay values("+gid+",0)";
				  	stmt.executeUpdate(query);
					pw.println("<td valign=\"top\" width=580>Your letter of guarantee has been issued<br>Guarantee Letter No. is: "+gid+"</td>");		 				
					
			
			
			}
			else
				pw.println("<html><title>Error Page!</title><body><font color=\"red\" size=3><B>Error! This page is not meant for you</font></B></body></html>");

			
			
				
				
				
				
				if(perarray[0]=='e')
				pw.println(myIndex.section6+"Employee"+myIndex.section7+date1+myIndex.section8+time1+myIndex.section9);
				else if(perarray[0]=='a')
					pw.println(myIndex.section6+"Administrator"+myIndex.section7+date1+myIndex.section8+time1+myIndex.section9);
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
			String date1="",time1="";
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
			
			String Guarantee = ctx.getAttribute("Guarantee").toString();
			
			String Guaranteetype = ctx.getAttribute("Guaranteetype").toString();
			
			String bank  = ctx.getAttribute("bank").toString();
			
			String maxlimit = ctx.getAttribute("maxlimit").toString();
			
			String idno  = ctx.getAttribute("idno").toString();
			
			String ACno = ctx.getAttribute("ACno").toString();
			
			int accno = Integer.parseInt(ACno);
			String to = ctx.getAttribute("to").toString();
			
			String day1 = ctx.getAttribute("day1").toString();
			
			String month1 = ctx.getAttribute("month1").toString();
			
			String year1 = ctx.getAttribute("year1").toString();
			
			String Dov = day1+"."+month1+"."+year1;

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
			  
			
					
			query = "select * from account where account.accno="+accno+"";	
				rset=stmt.executeQuery(query);
				
				rset.next();
				String UserID = rset.getString(1) ;
				query = "select * from holder where holder.idnum='"+idno+"'";	
					rset=stmt.executeQuery(query);
					
				rset.next();
					int holder = rset.getInt(2);
					
					query = "select * from cnt where cnt.name='TransID'";
					rset=stmt.executeQuery(query);
					
					rset.next();
					int TransID;
					TransID=rset.getInt(2)+1 ;
					query = "update cnt set cnt.cnt1=cnt.cnt1+1 where cnt.name='TransID'"; 
					stmt.executeUpdate(query);
					
					query = "select * from cnt where cnt.name='LOG'";
					rset=stmt.executeQuery(query);
								
					rset.next();
					int gid;
					gid=rset.getInt(2)+1 ;
					query = "update cnt set cnt.cnt1=cnt.cnt1+1 where cnt.name='LOG'"; 
					stmt.executeUpdate(query);
					
					query="insert into log1 values('"+Guarantee+"','"+Guaranteetype+"','"+bank+"',"+accno+",'"+idno+"',"+maxlimit+",'"+to+"','"+Doi+"','"+Dov+"','"+TransID+"','Issued',"+gid+")";
				  	stmt.executeUpdate(query);
				  	
				  	query="insert into pay values("+gid+",0)";
				  	stmt.executeUpdate(query);
					pw.println("<td valign=\"top\" width=580>Your letter of guarantee has been issued<br>Guarantee Letter No. is: "+gid+"</td>");		 				
					
			
			
			}
			else
				pw.println("<html><title>Error Page!</title><body><font color=\"red\" size=3><B>Error! This page is not meant for you</font></B></body></html>");

			
			
				
				
				
				
				if(perarray[0]=='e')
				pw.println(myIndex.section6+"Employee"+myIndex.section7+date1+myIndex.section8+time1+myIndex.section9);
				else if(perarray[0]=='a')
					pw.println(myIndex.section6+"Administrator"+myIndex.section7+date1+myIndex.section8+time1+myIndex.section9);
			}
			 
			
			 
			catch(Exception ex)
			{
				
			}
		}
	}   	  	    
}