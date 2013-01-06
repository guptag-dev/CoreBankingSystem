

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Servlet implementation class for Servlet: Forgot
 *
 */
 public class Forgot extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public Forgot() {
		super();
	}   	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ServletContext ctx=this.getServletContext();
		 ctx.setAttribute("loginsession", "F");
		Index myIndex = new Index();
		String addition="<B><font color=\"red\">Forgot Password...</font></B><form action=\"PassSent\" method=\"POST\">Enter Account Number:<br><input type=\"text\" name=\"acnum\"><br>Enter ID Proof:<br><input type=\"text\" name=\"idp\"><br><br><input type=\"submit\" name=\"btn1\"><input type=\"hidden\" name=\"loginsession\" value=\"F\"></form></TD></TR></table>";
		PrintWriter pw=response.getWriter();
		pw.println(myIndex.section1+myIndex.section2+addition+myIndex.section4);
	}  	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ServletContext ctx=this.getServletContext();
		 ctx.setAttribute("loginsession", "F");
		Index myIndex = new Index();
		String addition="<B><font color=\"red\">Forgot Password...</font></B><form action=\"PassSent\" method=\"POST\">Enter Account Number:<br><input type=\"text\" name=\"acnum\"><br>Enter ID Proof:<br><input type=\"text\" name=\"idp\"><br><br><input type=\"submit\" name=\"btn1\"><input type=\"hidden\" name=\"loginsession\" value=\"F\"></form></TD></TR></table>";
		PrintWriter pw=response.getWriter();
		pw.println(myIndex.section1+myIndex.section2+addition+myIndex.section4);
	}   	  	    
}