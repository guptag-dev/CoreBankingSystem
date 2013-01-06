

import java.io.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class for Servlet: Logout
 *
 */
 public class Logout extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public Logout() {
		super();
	}   	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ServletContext ctx=this.getServletContext();
		Index myIndex = new Index();
		ctx.setAttribute("loginsession", "F");
		PrintWriter pw=response.getWriter();
		pw.println(myIndex.section1+"<br><font color=\"red\" align=\"center\"><b>You have been successfully logged out!</B></FONT>"+myIndex.section2+myIndex.section3+myIndex.section4);
		response.sendRedirect("Index");
	}  	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ServletContext ctx=this.getServletContext();
		Index myIndex = new Index();
		ctx.setAttribute("loginsession", "F");
		PrintWriter pw=response.getWriter();
		pw.println(myIndex.section1+"<br><font color=\"red\" align=\"center\"><b>You have been successfully logged out!</B></FONT>"+myIndex.section2+myIndex.section3+myIndex.section4);
		response.sendRedirect("Index");
	}   	  	    
}