package com.gene.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.gene.utils.HibernateSessionFactory;
import com.gene.utils.Tool;

public class ajaxcheck extends HttpServlet {

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the GET method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = request.getParameter("op");
		
		if("NameCheck".equals(op)){
			NameCheck(request,response);	
			}
		else if("FileNameCheck".equals(op)){
			FileNameCheck(request,response);	
			}
		
	}

	private void FileNameCheck(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String filename=request.getParameter("filename");
		
		
		String root = ServletActionContext.getServletContext().getRealPath("/uploadtools");
		root+="\\"+filename;
		File file=new File(root);  
		if(file.exists())
			response.getWriter().write("<font color='red'>This file is existed.Please change file or rename this file</font>");
		
	}

	private void NameCheck(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String toolname=request.getParameter("toolname");
		if(checkexist(toolname))
			response.getWriter().write("<font color='red'>This toolname is existed</font>");
		
		
	}
	
	public boolean checkexist(String toolname) {
		Session session = HibernateSessionFactory.getSessionFactory()
				.getCurrentSession();
		Transaction tx = session.beginTransaction();
		Criteria cri = session.createCriteria(Tool.class);
		cri.setMaxResults(1);
		cri.add(Restrictions.eq("toolName", toolname));
		Tool l = (Tool) cri.uniqueResult();
		tx.commit();
		if (l != null)
			return true;
		else
			return false;

	}

}
