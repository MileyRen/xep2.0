package com.xeq.file.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssh.xep.entity.JobInfo;
import com.xeq.file.dao.JobsDao;
import com.xeq.file.domain.JobCss;
import com.xeq.file.domain.JobStep;
import com.xeq.file.domain.PageSource;
import com.xeq.file.service.JobsService;

@Service("jobsService")
public class JobsServiceImpl implements JobsService {
	@Autowired
	private JobsDao jobsDao;

	@Override
	public List<JobInfo> getJobList(String hql) {
		return jobsDao.getJobList(hql);
	}

	public JobsDao getJobsDao() {
		return jobsDao;
	}

	public void setJobsDao(JobsDao jobsDao) {
		this.jobsDao = jobsDao;
	}

	@Override
	public JobInfo getJobInfo(Integer id) {
		return jobsDao.getJobInfo(id);
	}

	@Override
	public List<JobStep> getNodes(Element node, List<JobStep> lists) {
		List<Attribute> listAttr = node.attributes();
		JobStep jStep = new JobStep();
		for (Attribute attr : listAttr) {// 遍历当前节点的所有属性
			String name = attr.getName();
			String value = attr.getValue();
			// System.out.println("属性名称：" + name + "属性值：" + value);
			if (name == "id" || name.equals("id")) {
				jStep.setId(value);
			} else if (name == "name" || name.equals("name")) {
				jStep.setName(value);
			} else if (name == "ret" || name.equals("ret")) {
				jStep.setRet(value);
			} else if (name == "pid" || name.equals("pid")) {
				jStep.setPid(value);
			} else if (name == "bgTime" || name.equals("bgTime")) {
				jStep.setBgTime(value);
			} else if (name == "edTime" || name.equals("edTime")) {
				jStep.setEdTime(value);
			} else if (name == "state" || name.equals("state")) {
				jStep.setState(value);
				if (value == "pending" || value.equals("pending")) {
					jStep.setCss("active");
					jStep.setLabel("default");
				} else if (value == "finish" || value.equals("finish")) {
					jStep.setCss("success");
					jStep.setLabel("success");
				} else if (value == "success" || value.equals("success")) {
					jStep.setCss("success");
					jStep.setLabel("success");
				} else if (value == "error" || value.equals("error")) {
					jStep.setCss("error");
					jStep.setLabel("danger");
				} else {
					jStep.setCss("warning");
					jStep.setLabel("warning");
				}
			}
		}
		if (jStep.getId() != null) {
			lists.add(jStep);
		}
		// 递归遍历当前节点所有的子节点
		List<Element> listElement = node.elements();// 所有一级子节点的list
		for (Element e : listElement) {// 遍历所有一级子节点
			getNodes(e, lists);// 递归
		}
		return lists;
	}

	@Override
	public List<JobCss> getJobCss(List<JobInfo> jlist) {
		List<JobCss> jcList = new ArrayList<JobCss>();
		if (jlist.size() > 0) {
			for (JobInfo jobInfo : jlist) {
				JobCss jCss = new JobCss();
				jCss.setId(jobInfo.getId());
				jCss.setBgTime(Long.toString(jobInfo.getBgTime()));
				jCss.setBpmn(jobInfo.getBpmn());
				jCss.setEdTime(Long.toString(jobInfo.getEdTime()));
				jCss.setFlowBasicInfoId(jCss.getFlowBasicInfoId());
				jCss.setProcessInfo(jobInfo.getProcessInfo());
				jCss.setUserId(jobInfo.getUserId());
				jCss.setState(jobInfo.getState());
				jCss.setName(jobInfo.getName());
				jCss.setAddOn(jobInfo.getAddOn());
				String value = jobInfo.getState();
				if (value == null || value.equals("")) {
					jCss.setCss("");
					jCss.setLabel("");
				} else if (value == "pending" || value.equalsIgnoreCase("pending")) {
					jCss.setCss("active");
					jCss.setLabel("default");
				} else if (value == "stop" || value.equalsIgnoreCase("stop")) {
					jCss.setCss("success");
					jCss.setLabel("primary");
				} else if (value == "running" || value.equalsIgnoreCase("running")) {
					jCss.setCss("info");
					jCss.setLabel("info");
				} else if (value == "error" || value.equalsIgnoreCase("error")) {
					jCss.setCss("error");
					jCss.setLabel("danger");
				} else if (value == "finish" || value.equalsIgnoreCase("finish")) {
					jCss.setCss("success");
					jCss.setLabel("success");
				} else if (value == "success" || value.equalsIgnoreCase("success")) {
					jCss.setCss("success");
					jCss.setLabel("success");
				} else {
					jCss.setCss("warning");
					jCss.setLabel("warning");
				}
				jcList.add(jCss);
			}
		}
		return jcList;
	}

	@Override
	public long getTime(String date) {

		long time = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date1 = sdf.parse(date);
			time = date1.getTime();
		} catch (Exception e) {
			return 0;
		}
		return time;
	}

	@Override
	public List<JobInfo> pageReview(PageSource page, String hql) {
		return jobsDao.jobpage(page, hql);
	}

	@Override
	public String getUserName(Integer userId) {
		return jobsDao.getUserName(userId);
	}

}
