package com.ssh.xep.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.ssh.xep.entity.FlowBasicInfo;
import com.ssh.xep.entity.JobFlowUser;
import com.ssh.xep.entity.JobInfo;
import com.ssh.xep.entity.TreeList;
import com.ssh.xep.service.FileService;
import com.ssh.xep.service.FlowBasicInfoService;
import com.ssh.xep.service.JobInfoService;
import com.gene.utils.User;
import com.ssh.xep.util.Flow2Job;
import com.ssh.xep.util.Flow2JobImpl;
import com.ssh.xep.util.LoadBpmn;
import com.ssh.xep.util.UserProperties;
import com.ssh.xep.util.process.Process;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Namespace("/job")
@Result(name = "error", location = "/WEB-INF/error.jsp")
public class JobInfoAction extends ActionSupport implements ModelDriven<JobInfo>, Preparable {

	private static final long serialVersionUID = -7988746546869953899L;

	private static final Logger LOGGER = Logger.getLogger(JobInfoAction.class);

	private List<FlowBasicInfo> flowBasicInfos;
	private FlowBasicInfo flowBasicInfo;
	private JobInfo info;
	private List<JobInfo> infos;
	private int flowId;
	private int jobId;
	private String page;
	private List<JobFlowUser> jfuInfos;
	private int maxPage;
	private List<TreeList> treeList;

	@Autowired
	private JobInfoService service;
	@Autowired
	private FlowBasicInfoService flowService;
	@Autowired
	private FileService fileService;

	public List<FlowBasicInfo> getFlowBasicInfos() {
		return flowBasicInfos;
	}

	public void setFlowBasicInfos(List<FlowBasicInfo> flowBasicInfos) {
		this.flowBasicInfos = flowBasicInfos;
	}

	public List<TreeList> getTreeList() {
		return treeList;
	}

	public void setTreeList(List<TreeList> treeList) {
		this.treeList = treeList;
	}

	public FlowBasicInfo getFlowBasicInfo() {
		return flowBasicInfo;
	}

	public void setFlowBasicInfo(FlowBasicInfo flowBasicInfo) {
		this.flowBasicInfo = flowBasicInfo;
	}

	public int getMaxPage() {
		return maxPage;
	}

	public void setMaxPage(int maxPage) {
		this.maxPage = maxPage;
	}

	public List<JobFlowUser> getJfuInfos() {
		return jfuInfos;
	}

	public void setJfuInfos(List<JobFlowUser> jfuInfos) {
		this.jfuInfos = jfuInfos;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public int getJobId() {
		return jobId;
	}

	public void setJobId(int jobId) {
		this.jobId = jobId;
	}

	public int getFlowId() {
		return flowId;
	}

	public void setFlowId(int flowId) {
		this.flowId = flowId;
	}

	public JobInfo getInfo() {
		return info;
	}

	public void setInfo(JobInfo info) {
		this.info = info;
	}

	public List<JobInfo> getInfos() {
		return infos;
	}

	public void setInfos(List<JobInfo> infos) {
		this.infos = infos;
	}

	public void prepare() throws Exception {
	}

	public JobInfo getModel() {
		if (info != null) {
			return info;
		}
		info = new JobInfo();
		return info;
	}

	@Action(value = "confirmCreate", results = {@Result(name = SUCCESS, type = "redirectAction", location = "view.action?page=latest")})
	public String confirmCreate() throws Exception {
		LOGGER.info("创建指定任务");
		if (info == null || info.getFlowBasicInfoId() == 0) {
			ServletActionContext.getRequest().setAttribute("errorInformation", "流程ID缺失");
			return ERROR;
		}
		User userInfo = (User) ServletActionContext.getRequest().getSession().getAttribute("user");
		Integer userId = userInfo.getId();
		info.setBgTime(0);
		info.setEdTime(0);
		info.setProcessInfo("");
		info.setUserId(userId);
		info.setId(service.save(info));

		return SUCCESS;
	}

	@Override
	@Action(value = "start", results = {@Result(name = SUCCESS, location = "/WEB-INF/success.jsp")})
	public String execute() throws Exception {
		LOGGER.info("启动指定任务");
		if (jobId == 0) {
			ServletActionContext.getRequest().setAttribute("errorInformation", "任务ID缺失");
			return ERROR;
		}try{
		info = service.get(jobId);

		User userInfo = (User) ServletActionContext.getRequest().getSession().getAttribute("user");

		Integer userId = userInfo.getId();
		info.setProcessInfo(service.getProcessInfo(info.getBpmn()));

		info.setBpmn(URLDecoder.decode(info.getBpmn(), "UTF-8"));
		Flow2Job flow2Job = new Flow2JobImpl(info.getId(), userInfo.getFolder());
		info.setBpmn(flow2Job.flow2Job(String.valueOf(userId), String.valueOf(info.getId()), info.getBpmn()));
		String filePath = LoadBpmn.loadBpmn(info.getBpmn(), String.valueOf(userInfo.getId()), String.valueOf(jobId), userInfo.getFolder());
		info.setBpmn(URLEncoder.encode(info.getBpmn(), "UTF-8"));

		Process p = new Process();
		int pId = p.create("java", new String[]{"java", "-jar", UserProperties.JBPM_PATH, filePath, String.format("%s.%s", userId, jobId)});

		info.setpId(pId);
		info.setState("pending");
		info.setBgTime(new Date().getTime());
		service.saveOrUpdate(info);}catch(Exception e){e.printStackTrace();}
		return SUCCESS;
	}

	@Action(value = "confirmCreateAndStart", results = {@Result(name = SUCCESS, location = "/WEB-INF/success.jsp")})
	public String confirmCreateAndStart() throws Exception {
		LOGGER.info("创建并启动指定任务");
		if (info == null || info.getFlowBasicInfoId() == 0) {
			ServletActionContext.getRequest().setAttribute("errorInformation", "流程ID缺失");
			return ERROR;
		}

		User userInfo = (User) ServletActionContext.getRequest().getSession().getAttribute("user");

		Integer userId = userInfo.getId();
		info.setBgTime(0);
		info.setEdTime(0);
		info.setUserId(userId);
		info.setId(service.save(info));

		info.setBpmn(URLDecoder.decode(info.getBpmn(), "UTF-8"));
		Flow2Job flow2Job = new Flow2JobImpl(info.getId(), userInfo.getFolder());
		info.setBpmn(flow2Job.flow2Job(String.valueOf(userId), String.valueOf(info.getId()), info.getBpmn()));
		String filePath = LoadBpmn.loadBpmn(info.getBpmn(), String.valueOf(userInfo.getId()), String.valueOf(info.getId()), userInfo.getFolder());
		info.setBpmn(URLEncoder.encode(info.getBpmn(), "UTF-8"));

		info.setProcessInfo(service.getProcessInfo(info.getBpmn()));

		Process p = new Process();
		int pId = p.create("java", new String[]{"java", "-jar", UserProperties.JBPM_PATH, filePath, String.format("%s.%s", userId, info.getId())});

		info.setpId(pId);
		info.setState("pending");
		info.setBgTime(new Date().getTime());
		service.saveOrUpdate(info);

		return SUCCESS;
	}

	@Action(value = "view", results = {@Result(name = SUCCESS, location = "/WEB-INF/content/job/view.jsp")})
	public String view() throws Exception {
		LOGGER.info("job + view");
		try {
		if (page == null) {
			page = "start";
		}

		User userInfo = (User) ServletActionContext.getRequest().getSession().getAttribute("user");
		Integer userId = userInfo.getId();
		jfuInfos = service.findAllJoin(userId, page);
		maxPage = service.count() / 10 + 1;
		}catch(Exception e) {e.printStackTrace();}
		return SUCCESS;
	}

	@Action(value = "create", results = {@Result(name = SUCCESS, location = "/WEB-INF/content/job/modify.jsp")})
	public String create() throws Exception {
		LOGGER.info("create");
		ServletActionContext.getRequest().getSession().setAttribute("title", "创建");
		String id = ServletActionContext.getRequest().getParameter("id");
		if(id != null) {
			flowBasicInfo = flowService.get(Integer.parseInt(id));
		} else {
			flowBasicInfo = null;
		}

		Integer userId = ((User) (ServletActionContext.getRequest().getSession().getAttribute("user"))).getId();
		flowBasicInfos = flowService.findAll(userId, "1900-01-01", "2100-10-10");
//		treeList = fileService.findAll(userId);
		return SUCCESS;
	}

	@Action(value = "modify", results = {@Result(name = SUCCESS, location = "/WEB-INF/content/job/modify.jsp")})
	public String modify() throws Exception {
		LOGGER.info("modify");
		ServletActionContext.getRequest().getSession().setAttribute("title", "修改");
		String id = ServletActionContext.getRequest().getParameter("id");
		info = service.get(Integer.parseInt(id));
		flowBasicInfo = new FlowBasicInfo();

//		Integer userId = (Integer) (ServletActionContext.getRequest().getSession().getAttribute("userId"));
//		treeList = fileService.findAll(userId);
		return SUCCESS;
	}

	@Action(value = "selectFile", results = {@Result(name = SUCCESS, location = "/WEB-INF/content/job/selectFile.jsp")})
	public String selectFile() throws Exception {
		LOGGER.info("selectFile");try{

		String selectFolder = ServletActionContext.getRequest().getParameter("selectFolder");

		User userInfo = (User) ServletActionContext.getRequest().getSession().getAttribute("user");
		Integer userId = userInfo.getId();
		if(selectFolder == null)
			treeList = fileService.findAll(userId);
		else
			treeList = fileService.findAll(userId, "folder");}catch(Exception e){e.printStackTrace();}
		return SUCCESS;
	}

	@Action(value = "delete", results = {@Result(name = SUCCESS, type = "redirectAction", location = "view.action?page=${page}")})
	public String delete() throws UnsupportedEncodingException {
		String valueStr = ServletActionContext.getRequest().getParameter("deleted");
		valueStr = URLDecoder.decode(valueStr, "UTF-8");
		String[] values = valueStr.split(",");
		List<Integer> ids = new ArrayList<>(values.length);
		for (String v : values) {
			ids.add(Integer.parseInt(v));
		}
		service.delete(ids);

		if (page == null) {
			page = "start";
		}
		return SUCCESS;
	}
}
