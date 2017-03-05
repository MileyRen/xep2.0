package com.gene.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.gene.utils.*;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import developtool.Reg;

public  class TooltypeAction extends ActionSupport implements ModelDriven<Tooltype>{
	private Tooltype tooltype=new Tooltype();
	private HttpServletRequest request=ServletActionContext.getRequest();
	private HttpServletResponse response=ServletActionContext.getResponse();
	

	public Tooltype getTooltype() {
		return tooltype;
	}


	public void setTooltype(Tooltype tooltype) {
		this.tooltype = tooltype;
	}
	
	public String pack() throws IOException{
		tooltype.setTypeName(tooltype.getTypeName().trim());
		if(Reg.IsNullOrEmpty(tooltype.getTypeName()))
		{
			response.getWriter().print("<script languge='javascript'>alert('please input tooltypename :)');history.go(-1)</script>");
			return null;
		}
		
		
		HttpSession wsession=request.getSession();
		User user=(User) wsession.getAttribute("user");
		tooltype.setUser(user);
		tooltype.setToolNum(0);
		return SUCCESS;
	}
	public String add(){
		int id=0;
		Session session=HibernateSessionFactory.getSessionFactory().getCurrentSession();
		Transaction tx=session.beginTransaction();
		id=(Integer) session.save(tooltype);
		tx.commit();
		session.close();
		if(id!=0)
		   return SUCCESS;
		else
			return ERROR;
				
	}
	public void getalltooltype() throws ServletException, IOException{try{
		Session session=HibernateSessionFactory.getSessionFactory().getCurrentSession();
		Transaction tx=session.beginTransaction();
		Criteria crit=session.createCriteria(Tooltype.class);
		List<Tooltype> tooltypes=crit.list();
		tx.commit();
		request.setAttribute("ctooltype", tooltypes);
		HttpSession sc=request.getSession();
		sc.setAttribute("tooltypelist", tooltypes);
		request.getRequestDispatcher("/tooltype/index.jsp").forward(request, response);}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public String edit(){
		String id=request.getParameter("id");
		Session session=HibernateSessionFactory.getSessionFactory().getCurrentSession();
		Transaction tx=session.beginTransaction();
		Criteria crit=session.createCriteria(Tooltype.class);
		crit.setMaxResults(1);
		crit.add(Restrictions.eq("id", Integer.parseInt(id)));
		Tooltype c=(Tooltype) crit.uniqueResult();
		tx.commit();
		//request.setAttribute("edittooltype", c);
		HttpSession wsession=request.getSession();
		wsession.setAttribute("edittooltype", c);
		//session.close();
		return SUCCESS;
	
	}
	
	
	
	public String edittool(){
		HttpSession wsession=request.getSession();
		Tooltype c=(Tooltype) wsession.getAttribute("edittooltype");
		c.setTypeName(tooltype.getTypeName());
		c.setShowColor(tooltype.getShowColor());
		c.setDescribtion(tooltype.getDescribtion());
		Session session=HibernateSessionFactory.getSessionFactory().getCurrentSession();
		Transaction tx=session.beginTransaction();
		session.update(c);
		tx.commit();
		wsession.removeAttribute("edittooltype");
		return SUCCESS;
		
	}
	
    public String del(){
    	String id=request.getParameter("id");
		Session session=HibernateSessionFactory.getSessionFactory().getCurrentSession();
		Transaction tx=session.beginTransaction();
		Criteria crit=session.createCriteria(Tooltype.class);
		crit.setMaxResults(1);
		crit.add(Restrictions.eq("id", Integer.parseInt(id)));
		Tooltype c=(Tooltype) crit.uniqueResult();
		session.delete(c);
		tx.commit();
		return SUCCESS;
		
	}
	

	@Override
	public Tooltype getModel() {
		
		return tooltype;
	}
	

}
