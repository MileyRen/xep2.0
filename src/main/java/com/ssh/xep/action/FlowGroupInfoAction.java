package com.ssh.xep.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.ssh.xep.entity.FlowGroupInfo;
import com.ssh.xep.service.FlowGroupInfoService;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.SystemEnvironmentPropertySource;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

// 还没有增加创建

@Namespace("/flowGroup")
@Result(name = ActionSupport.ERROR, location = "/WEB-INF/error.jsp")
public class FlowGroupInfoAction extends ActionSupport implements ModelDriven<FlowGroupInfo>, Preparable {

	private static final long serialVersionUID = 2242673720623311926L;

	private static final Logger LOGGER = Logger.getLogger(FlowGroupInfoAction.class);

	private List<FlowGroupInfo> groups;
	private FlowGroupInfo group;
	private Integer groupId;
	private String maxPage;
	private String page;

	@Autowired
	private FlowGroupInfoService service;

	public List<FlowGroupInfo> getGroups() {
		return groups;
	}

	public void setGroups(List<FlowGroupInfo> groups) {
		this.groups = groups;
	}

	public FlowGroupInfo getGroup() {
		return group;
	}

	public void setGroup(FlowGroupInfo group) {
		this.group = group;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getMaxPage() {
		return maxPage;
	}

	public void setMaxPage(String maxPage) {
		this.maxPage = maxPage;
	}

	public void prepare() throws Exception {
	}

	@Override
	public FlowGroupInfo getModel() {
		if (group != null) {
			return group;
		}
		group = new FlowGroupInfo();
		return group;
	}

	// 此时和用户无关
	@Action("view")
	public String view() throws Exception {
		if (page == null) {
			page = "0";
		}
		maxPage = String.valueOf(service.count());
		groups = service.findAll(page);
		return SUCCESS;
	}

	@Action("modify")
	public String modify() throws Exception {
		if (groupId == null || groupId == 0) {
			group.setName("新建");
		} else {
			group = service.get(groupId);
		}
		return SUCCESS;
	}

	@Action(value = "modify-commit", results = {@Result(name = SUCCESS, location = "/WEB-INF/success.jsp")})
	public String modifyCommit() throws Exception {
		if(group==null) {
			ServletActionContext.getRequest().setAttribute("errorInformation", "数据缺失");
			return ERROR;
		}
		if(group.getId() == 0) {
			service.save(group);
		} else {
			service.saveOrUpdate(group);
		}
		return SUCCESS;
	}

	@Action(value = "delete", results = {@Result(name = SUCCESS, type = "redirectAction", location = "view.action?page=${page}")})
	public String delete() throws Exception {
		String valueStr = ServletActionContext.getRequest().getParameter("deleted");
		valueStr = URLDecoder.decode(valueStr, "UTF-8");
		String[] values = valueStr.split(",");
		List<Integer> idl = new ArrayList<>(values.length);
		for (String v : values) {
			idl.add(Integer.parseInt(v));
		}
		service.delete(idl);

		if(page == null) {
			page = "start";
		}
		return SUCCESS;
	}
}
