package com.ssh.xep.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.ssh.xep.entity.*;
import com.ssh.xep.service.FlowBasicInfoService;
import com.ssh.xep.service.FlowGroupInfoService;
import com.ssh.xep.service.ToolTypesService;
import com.ssh.xep.service.ToolsService;
import com.ssh.xep.util.MakeBpmn;
import com.gene.utils.User;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Namespace("/flow")
@Result(name = ActionSupport.ERROR, location = "/WEB-INF/error.jsp")
public class FlowBasicInfoAction extends ActionSupport implements ModelDriven<FlowBasicInfo>, Preparable {

	private static final long serialVersionUID = -7988746546869953899L;

	private static final Logger LOGGER = Logger.getLogger(FlowBasicInfoAction.class);

	private FlowBasicInfo info;
	private List<FlowBasicInfo> infos;
	private List<FlowGroupInfo> groups;
	private List<FlowGroupUser> infoJoins;
	private List<Tools> tools;
	private List<ToolTypes> toolTypes;
	private String startDate, endDate;
	private String page;
	private String maxPage;

	@Autowired
	private FlowBasicInfoService service;

	@Autowired
	private ToolsService toolService;

	@Autowired
	private ToolTypesService toolTypesService;

	@Autowired
	private FlowGroupInfoService flowGroupInfoService;

	public List<FlowGroupUser> getInfoJoins() {
		return infoJoins;
	}

	public void setInfoJoins(List<FlowGroupUser> infoJoins) {
		this.infoJoins = infoJoins;
	}

	public List<FlowGroupInfo> getGroups() {
		return groups;
	}

	public void setGroups(List<FlowGroupInfo> groups) {
		this.groups = groups;
	}

	public List<ToolTypes> getToolTypes() {
		return toolTypes;
	}

	public void setToolTypes(List<ToolTypes> toolTypes) {
		this.toolTypes = toolTypes;
	}

	public FlowBasicInfo getInfo() {
		return info;
	}

	public void setInfo(FlowBasicInfo info) {
		this.info = info;
	}

	public List<FlowBasicInfo> getInfos() {
		return infos;
	}

	public void setInfos(List<FlowBasicInfo> infos) {
		this.infos = infos;
	}

	public List<Tools> getTools() {
		return tools;
	}

	public void setTools(List<Tools> tools) {
		this.tools = tools;
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

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public void prepare() throws Exception {
	}

	public FlowBasicInfo getModel() {
		if (info != null) {
			return info;
		}
		info = new FlowBasicInfo();
		return info;
	}

	@Override
	@Action("view")
	public String execute() throws Exception {
		LOGGER.info("查询所有流程"+page);try{
		User userInfo = (User) ServletActionContext.getRequest().getSession().getAttribute("user");
		Integer userId = userInfo.getId();
		boolean isAdmin = userInfo.getRoleId() == 1;
		if (startDate == null) {
			startDate = "1900-01-01";
		}
		if (endDate == null) {
			endDate = "2100-10-10";
		}
		System.out.println(startDate);
		System.out.println(endDate);
		int[] auths;
		if (isAdmin) {
			auths = new int[]{1, 2};
		} else {
			auths = new int[]{2};
		}
		if(page == null) {
			page = "0";
		}
		String auth = ServletActionContext.getRequest().getParameter("auth");
		if (auth != null) {
			infoJoins = service.findAllJoin(userId, auths, startDate, endDate, page, Integer.parseInt(auth));
		} else {
			infoJoins = service.findAllJoin(userId, auths, startDate, endDate, page);
		}
		groups = flowGroupInfoService.findAll();
		maxPage = String.valueOf(service.count());}catch(Exception e) {e.printStackTrace();}

		return SUCCESS;
	}

	@Action(value = "detail")
	public String detail() throws DocumentException, ParserConfigurationException {
		Integer id = info.getId();
		LOGGER.info("查看某个流程详细信息： " + id);
		info = service.get(Integer.valueOf(id));
		if (info == null) {
			ServletActionContext.getRequest().setAttribute("errorInformation", "找不到对象呀。");
			return ERROR;
		}

		return SUCCESS;
	}

	@Action(value = "modify", results = {@Result(name = SUCCESS, location = "/WEB-INF/content/flow/modify.jsp")})
	public String modify() throws DocumentException, ParserConfigurationException, TransformerFactoryConfigurationError,
			TransformerException {
		String id = ServletActionContext.getRequest().getParameter("id");
		LOGGER.info("修改或者创建某个流程： " + id);
		User userInfo = (User) ServletActionContext.getRequest().getSession().getAttribute("user");
		Integer userId = userInfo.getId();
		try {
			tools = toolService.findAll(userId);
			toolTypes = toolTypesService.findAll();
			groups = flowGroupInfoService.findAll();
		} catch (Exception e) {e.printStackTrace();}
		String flowName = ServletActionContext.getRequest().getParameter("flowName");
		String authStr = ServletActionContext.getRequest().getParameter("auth");
		String groupId = ServletActionContext.getRequest().getParameter("groupId");
		if (id == null) {
			if (groupId == null) {
				groupId = "1";
			}
			if (flowName == null || authStr == null) {
				ServletActionContext.getRequest().setAttribute("errorInformation", "缺少参数。");
				return ERROR;
			}
			try {
				Integer.parseInt(authStr);
				Integer.parseInt(groupId);
			} catch (Exception e) {
				ServletActionContext.getRequest().setAttribute("errorInformation", "参数错误。");
				return ERROR;
			}

			ServletActionContext.getRequest().setAttribute("create", "创建");
			info = new FlowBasicInfo();
			try {
				info.setFlow(URLEncoder.encode(new MakeBpmn(String.valueOf(userId)).get(), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			info.setFlowNum(0);
			info.setUserId(String.valueOf(userId));
			info.setName(flowName);
			info.setAuth((short) Integer.parseInt(authStr));
			info.setGroupId(groupId);
			id = "0";
			info.setId(Integer.parseInt(id));
		} else {
			ServletActionContext.getRequest().setAttribute("create", "修改");
			info = service.get(Integer.parseInt(id));
			if (info == null) {
				ServletActionContext.getRequest().setAttribute("errorInformation", "找不到对象呀。");
				return ERROR;
			}
			if(flowName != null) {
				info.setName(flowName);
			}
			if(authStr != null) {
				try {
					Integer.parseInt(authStr);
				} catch (Exception e) {
					ServletActionContext.getRequest().setAttribute("errorInformation", "参数错误。");
					return ERROR;
				}
				info.setAuth((short) Integer.parseInt(authStr));
			}
			if(groupId != null) {
				try {
					Integer.parseInt(groupId);
				} catch (Exception e) {
					ServletActionContext.getRequest().setAttribute("errorInformation", "参数错误。");
					return ERROR;
				}
				info.setGroupId(groupId);
			}
		}
		return SUCCESS;
	}

	@Action(value = "modify-commit", results = {@Result(name = SUCCESS, location = "/WEB-INF/success.jsp")})
	public String modifyCommit() throws ParserConfigurationException {
		LOGGER.info("最终，提交了"+info.getCreateDate());
		if (info.getFlow() == null || info.getFlow().equals("")) {
			ServletActionContext.getRequest().setAttribute("errorInformation", "数据缺失");
			return ERROR;
		}
		User userInfo = (User) ServletActionContext.getRequest().getSession().getAttribute("user");
		if (info.getUserId() == null) {
			Integer userId = userInfo.getId();
			info.setUserId(String.valueOf(userId));
		}
		if(info.getId() != 0) {
			service.saveOrUpdate(info);
		} else {
			service.save(info);
		}
		return SUCCESS;
	}

	@Action(value = "delete", results = {@Result(name = SUCCESS, type = "redirectAction", location = "view.action?page=${page}")})
	public String delete() throws UnsupportedEncodingException {
		String valueStr = ServletActionContext.getRequest().getParameter("deleted");
		valueStr = URLDecoder.decode(valueStr, "UTF-8");
		String[] values = valueStr.split(",");
		List<Integer> ids = new ArrayList<>(values.length);
		for(String v:values) {
			ids.add(Integer.parseInt(v));
		}
		service.delete(ids);

		if(page == null) {
			page = "start";
		}
		return SUCCESS;
	}

}
