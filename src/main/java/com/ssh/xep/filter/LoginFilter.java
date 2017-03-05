package com.ssh.xep.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginFilter implements Filter {

	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String path = ((HttpServletRequest)request).getServletPath();
		if(path.equals("/") || path.equals("/login.action")) {
			chain.doFilter(request, response);
			return;
		} else if(((HttpServletRequest)request).getSession().getAttribute("user") == null) {
			String root = ((HttpServletRequest)request).getContextPath();
			((HttpServletResponse)response).sendRedirect(root+"/login.action");
			return;
		}
		chain.doFilter(request, response);
	}

	public void destroy() {
	}

}
