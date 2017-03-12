package com.xeq.file.action;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.gene.utils.User;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.xeq.file.dao.impl.PathFormat;
import com.xeq.file.domain.FileAndFolder;
import com.xeq.file.service.FolderService;

/** 文件同步 */
@Namespace("/")
public class syncFileAction extends ActionSupport implements SessionAware, ModelDriven<FileAndFolder>, Preparable {
	private static final long serialVersionUID = 5818005862725532536L;
	private static Logger logger = Logger.getLogger(FileMgrAction.class);
	private Map<String, Object> session;

	@Autowired
	private FolderService fService;

	@Autowired

	

	@Override
	public void prepare() throws Exception {
	}

	private FileAndFolder model;

	@Override
	public FileAndFolder getModel() {
		return model;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

}
