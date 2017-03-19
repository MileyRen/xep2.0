package com.xeq.file.action;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.dom4j.Element;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.springframework.beans.factory.annotation.Autowired;

import com.gene.utils.User;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.ssh.xep.entity.JobInfo;
import com.xeq.file.dao.impl.BaseDao;
import com.xeq.file.dao.impl.PathFormat;
import com.xeq.file.domain.JobCss;
import com.xeq.file.domain.JobStep;
import com.xeq.file.domain.PageSource;
import com.xeq.file.domain.ScriptState;
import com.xeq.file.service.JobsService;

@Namespace("/")
public class JobListAction extends ActionSupport
		implements SessionAware, ServletRequestAware, ModelDriven<JobInfo>, Preparable {
	private static final long serialVersionUID = -8335181902779435263L;
	private static Logger logger = Logger.getLogger(JobListAction.class);
	private Map<String, Object> session;
	private HttpServletRequest request;
	@Autowired
	private JobsService jobsService;
	@Autowired
	private PageSource jobpageSource;
	private Integer id;
	private JobInfo jobInfo;

	// 分页
	private String jobPage;

	@Action(value = "jobsList", results = { @Result(name = "success", location = "/jobInfoList/JobInfoList.jsp"),
			@Result(name = "input", location = "login.jsp") })
	public String getJobs() {
		logger.info("-----------------------查询用户的所有Job------------------------");
		try {
			User user = (User) session.get("user");
			if (user == null) {
				return INPUT;
			}
			if (user.getRoleId() == null) {
				user.setRoleId(0);
			}
			int userId = user.getId();
			String hql = "FROM JobInfo ";
			if (user.getRoleId() == 1) {
				hql += " where userId >0 ";
			} else {
				hql += " where userId=" + userId;
			}
			long fTime = jobsService.getTime(request.getParameter("fTime"));
			long tTime = jobsService.getTime(request.getParameter("tTime"));
			String sortByTime = request.getParameter("sortByTime");
			String sortDA = request.getParameter("sortDA");
			String jobType = request.getParameter("jobstate");
			String jobstate = " and state = '" + jobType + "'";
			StringBuffer sf = new StringBuffer();
			sf.append(hql);

			if (jobType != null && !jobType.equals("ALL")) {
				sf.append(jobstate);
			}

			if (fTime != 0 && tTime == 0) {
				sf.append(" and bgTime > " + fTime + " ");
			} else if (fTime == 0 && tTime != 0) {
				sf.append(" and bgTime < " + tTime + " ");
			} else if (fTime != 0 && tTime != 0) {
				sf.append(" and bgTime between " + fTime + " and " + tTime + " ");
			}

			if (StringUtils.isNotBlank(sortDA)) {
				sf.append(sortByTime + sortDA);
			}
			String HQL = sf.toString();

			logger.info("hql==" + HQL);
			List<JobInfo> jList = jobsService.getJobList(HQL);

			List<JobInfo> jobPageList = new ArrayList<JobInfo>();

			jobpageSource.setTotalRows(jList.size());
			if (jobPage != null) {
				jobpageSource.init(jList.size(), (new BaseDao()).pageSize());// 初始化，用以获取总页数
				jobpageSource.setCurrentPage(1);
				jobpageSource.setPageSize((new BaseDao()).pageSize());

				logger.info(jobpageSource.toString());
				jobPageList = jobsService.pageReview(jobpageSource, HQL);
				logger.info("hql=" + HQL + ";size=" + jobPageList.size());
				logger.info("没有执行分页的代码");
			} else {
				// /执行分页操纵
				logger.info("执行了分页的代码");
				jobpageSource.init(jList.size(), jobpageSource.getPageSize());// 初始化，用以获取总页数
				jobpageSource.setPageSize(jobpageSource.getPageSize());

				if (jobpageSource.getCurrentPage() >= jobpageSource.getTotalPages()) {
					jobpageSource.setCurrentPage(jobpageSource.getTotalPages());
				} else if (jobpageSource.getCurrentPage() < 1) {
					jobpageSource.setCurrentPage(1);
				}
				logger.info(jobpageSource.toString());
				jobPageList = jobsService.pageReview(jobpageSource, HQL);
			}

			List<JobCss> jcList = jobsService.getJobCss(jobPageList);
			for (JobCss jobCss : jcList) {
				jobCss.setUserName(jobsService.getUserName(jobCss.getUserId()));
			}
			session.put("jobpagesource", jobpageSource);
			session.put("jcList", jcList);
			session.put("jList", jList);
			session.put("jobstate", jobType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@Action(value = "jobInfo", results = { @Result(name = "success", location = "/jobInfoList/jobStep.jsp"),
			@Result(name = "input", location = "login.jsp"),
			@Result(name = "noStart", location = "/jobInfoList/jobStep.jsp") })
	public String getJobsInfo() {
		logger.info("-----------------------查询JOB的详细信息------------------------");
		User user = (User) session.get("user");
		if (user == null) {
			return INPUT;
		}
		logger.info("id=" + id);
		JobInfo jf = jobsService.getJobInfo(id);
		/** 获取processInfo内容 */
		String processInfo = jf.getProcessInfo();

		session.put("jobStep", jf);

		/***** 解析processInfo *****/
		Document document;
		try {
			document = (Document) DocumentHelper.parseText(URLDecoder.decode(processInfo, "utf-8"));
			Element rootElement = (org.dom4j.Element) document.getRootElement();
			List<JobStep> processLists = new ArrayList<JobStep>();
			processLists = jobsService.getNodes(rootElement, processLists);
			for (JobStep jobStep : processLists) {
				System.out.println(jobStep.toString());
			}
			session.put("processInfo", processLists);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			logger.info("processInfo为空。。。。。。。");
			session.put("processInfo", null);
			return "noStart";
		}
		/***** 解析processInfo结束 *****/

		/** 绘图开始 */
		List<ScriptState> list = new ArrayList<ScriptState>();
		list = PathFormat.getDraw(processInfo);
		String jsonStr = ConvertToJSON.convert(list);
		System.out.println(jsonStr);
		request.setAttribute("jsonStr", jsonStr);
		/** 绘图结束 */
		return SUCCESS;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public JobsService getJobsService() {
		return jobsService;
	}

	public void setJobsService(JobsService jobsService) {
		this.jobsService = jobsService;
	}

	public JobInfo getJobInfo() {
		return jobInfo;
	}

	public void setJobInfo(JobInfo jobInfo) {
		this.jobInfo = jobInfo;
	}

	public String getJobPage() {
		return jobPage;
	}

	public void setJobPage(String jobPage) {
		this.jobPage = jobPage;
	}

	public PageSource getJobpageSource() {
		return jobpageSource;
	}

	public void setJobpageSource(PageSource jobpageSource) {
		this.jobpageSource = jobpageSource;
	}

	@Override
	public void prepare() throws Exception {
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	private JobInfo model;

	@Override
	public JobInfo getModel() {
		return model;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

}
