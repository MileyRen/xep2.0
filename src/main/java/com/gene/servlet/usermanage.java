package com.gene.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import com.gene.utils.HibernateSessionFactory;
import com.gene.utils.User;
import com.ssh.xep.SpringContextHolder;

import developtool.*;

public class usermanage extends HttpServlet {

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String op = request.getParameter("op");

		if ("adduser".equals(op)) {
			addUser(request, response);
		} else if ("login".equals(op)) {
			login(request, response);
		} else if ("check".equals(op)) {
			check(request, response);
		} else if ("activeuser".equals(op)) {
			activeuser(request, response);
		} else if ("deluser".equals(op)) {
			deluser(request, response);
		} else if ("modify".equals(op)) {
			modify(request, response);
		} else if ("pauseuser".equals(op)) {
			pauseuser(request, response);
		} else if ("logout".equals(op)) {
			logout(request, response);
		}
	}

	private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession wsession = request.getSession();
		wsession.invalidate();
		response.sendRedirect(request.getContextPath() + "/user/login.jsp");
	}

	private void pauseuser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String id = request.getParameter("id");
//		Configuration configuration = new Configuration();
//		configuration.configure();
		SessionFactory sessionfactory = (SessionFactory) SpringContextHolder.getApplicationContext().getBean("sessionFactory");//configuration.buildSessionFactory();
		Session session = sessionfactory.openSession();
		Transaction transaction = session.beginTransaction();
		User user = (User) session.get(User.class, Integer.parseInt(id));
		user.setIsAvailable(1);
		session.update(user);

		transaction.commit();
		session.close();
		response.sendRedirect(request.getContextPath() + "/user/usermanage?op=check");
	}

	private void modify(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession wsession = request.getSession();
		User t = (User) wsession.getAttribute("user");
		String oldpwd = request.getParameter("oldpwd");
		String newpwd = request.getParameter("newpwd");
		if (t.getPassWord().equals(oldpwd)) {
//			Configuration configuration = new Configuration();
//			configuration.configure();
			SessionFactory sessionfactory = (SessionFactory) SpringContextHolder.getApplicationContext().getBean("sessionFactory");//configuration.buildSessionFactory();
			Session session = sessionfactory.openSession();
			Transaction transaction = session.beginTransaction();
			t.setPassWord(newpwd);
			session.update(t);
			transaction.commit();
			session.close();
			wsession.setAttribute("user", t);
			if (t.getIsAvailable() == 2)
				response.sendRedirect(request.getContextPath() + "/user/userinfo.jsp?msg=Your password has changed!");
			else
				response.sendRedirect(
						request.getContextPath() + "/user/userinfotemp.jsp?msg=Your password has changed!");

		} else {
			response.sendRedirect(request.getContextPath() + "/user/modify.jsp?msg=Old password not correct!");
		}

	}

	private void check(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Configuration configuration =new Configuration();
		// ?Session sc =
		// configuration.configure().buildSessionFactory().openSession();
		// ?Session sc=HibernateSessionFactory.getSession();

//		Configuration configuration = new Configuration().configure();
//		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties())
//				.buildServiceRegistry();
		Session sc = ((SessionFactory) SpringContextHolder.getApplicationContext().getBean("sessionFactory")).openSession();//configuration.buildSessionFactory(serviceRegistry).openSession();

		// configuration.configure();
		// SessionFactory sessionfactory=configuration.buildSessionFactory();
		// Session sc=sessionfactory.openSession();

		Criteria crit = sc.createCriteria(User.class);
		List<User> users = crit.list();
		// for(User u : users) {
		// System.out.print(u.getIsAvailable());
		// System.out.println(" "+u.getUserName());
		// }
		sc.close();
		request.setAttribute("cusers", users);
		request.getRequestDispatcher("/user/check.jsp").forward(request, response);
		// sc.flush();

	}

	private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String pwd = request.getParameter("pwd");

//		Configuration configuration = new Configuration();
//		configuration.configure();
		SessionFactory sessionfactory = (SessionFactory) SpringContextHolder.getApplicationContext().getBean("sessionFactory");//configuration.buildSessionFactory();
		Session sc = sessionfactory.openSession();
		Criteria crit = sc.createCriteria(User.class);
		crit.setMaxResults(1);
		crit.add(Restrictions.eq("userName", name));
		User user = (User) crit.uniqueResult();
		sc.close();
		if (user != null) {
			if (user.getPassWord().equals(pwd)) {
				// request.setAttribute("cuser", user);
				HttpSession wsession = request.getSession();
				wsession.setAttribute("user", user);
				if (user.getIsAvailable() == 2)
					response.sendRedirect(request.getContextPath() + "/user/userinfo.jsp");
				else
					response.sendRedirect(request.getContextPath() + "/user/userinfotemp.jsp");
				// if(user.getRoleId()==0)
				// request.getRequestDispatcher("/user/userinfo.jsp").forward(request,
				// response);
				// else
				// request.getRequestDispatcher("/user/admin/userinfo.jsp").forward(request,
				// response);

			} else
				response.sendRedirect(request.getContextPath() + "/user/loginfail.jsp");

		} else {
			response.sendRedirect(request.getContextPath() + "/user/loginfail.jsp");

		}

	}

	private void addUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String name = request.getParameter("name");
		String pwd = request.getParameter("pwd");
		String pwdcfm = request.getParameter("pwdcfm");
		String email = request.getParameter("email");

		int check = Reg.RegCheck(name.trim(), pwd.trim(), pwdcfm.trim(), email.trim());
		String msg = "";
		switch (check) {
		case 1:
			msg = "You must fill all information";
			break;
		case 2:
			msg = "'password' and 'confirm password' not same";
			break;
		}

//		Configuration configuration = new Configuration().configure();
//		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties())
//				.buildServiceRegistry();
		SessionFactory sessionfactory = (SessionFactory) SpringContextHolder.getApplicationContext().getBean("sessionFactory");//configuration.buildSessionFactory(serviceRegistry);
		Session session = sessionfactory.openSession();

		Criteria crit = session.createCriteria(User.class);
		crit.setMaxResults(1);
		crit.add(Restrictions.eq("userName", name));
		User t = (User) crit.uniqueResult();
		session.close();
		if (t != null && msg == "")
			msg = "This username has existed,please change username";

		if (msg != "") {
			response.getWriter().print("<script language='javascript'>alert('" + msg + "');history.go(-1)</script>");
			// response.sendRedirect(request.getContextPath()+"/user/reg.jsp?msg="+msg);
			return;
		}

		User user = new User(name, pwd, email);
		user.setRoleId(0);
		user.setIsAvailable(0);
		Date date = new Date();
		Timestamp nowdate = new Timestamp(date.getTime());
		user.setRegisterTime(nowdate);
		user.setInitStorge(Reg.INITSTROGE);
		user.setUsedStorage(0f);
		String path = "/userfolder/" + user.getUserName();
		user.setFolder(path);

		// Configuration configuration =new Configuration();
		// configuration.configure();
		// SessionFactory sessionfactory=configuration.buildSessionFactory();
		// Session session=sessionfactory.openSession();
		session = sessionfactory.openSession();
		Transaction transaction = session.beginTransaction();
		session.save(user);

		transaction.commit();
		session.close();
		// String
		// path=request.getRealPath("/")+"userfolder\\"+user.getId()+"\\tools";
		// String path="/userfolder/"+user.getId()+"/tools";
		// path+="/tools";
		String real = request.getRealPath(path);

		File folder = new File(real);

		boolean c = false;
		c = folder.mkdirs();

		request.setAttribute("cuser", user);
		HttpSession wsession = request.getSession();
		wsession.setAttribute("user", user);
		request.getRequestDispatcher("/user/userinfotemp.jsp").forward(request, response);

	}

	private void deluser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String id = request.getParameter("id");
		// Configuration configuration =new Configuration();
		// configuration.configure();
		Session session = HibernateSessionFactory.getSession();
		Transaction tx = session.beginTransaction();
		User user = (User) session.get(User.class, Integer.parseInt(id));
		session.delete(user);
		// String hql="delete from user where ID="+id;
		// Query query=session.createQuery(hql);
		// query.executeUpdate();
		tx.commit();
		session.close();
		response.sendRedirect(request.getContextPath() + "/user/usermanage?op=check");

	}

	private void activeuser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String id = request.getParameter("id");
//		Configuration configuration = new Configuration();
//		configuration.configure();
		SessionFactory sessionfactory = (SessionFactory) SpringContextHolder.getApplicationContext().getBean("sessionFactory");//configuration.buildSessionFactory();
		Session session = sessionfactory.openSession();
		Transaction transaction = session.beginTransaction();
		User user = (User) session.get(User.class, Integer.parseInt(id));
		user.setIsAvailable(2);
		session.update(user);

		transaction.commit();
		session.flush();
		session.close();
		response.sendRedirect(request.getContextPath() + "/user/usermanage?op=check");

	}

}
