package com.ssh.xep.action;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Namespace;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

@Namespace("/login")
public class Login extends ActionSupport implements Preparable {

	private static final long serialVersionUID = -7988746546869953899L;

	private static final Logger LOGGER = Logger.getLogger(Login.class);

	public void prepare() throws Exception {
	}

	public String execute() throws Exception {
		LOGGER.info("登录");
		return SUCCESS;
	}
}
