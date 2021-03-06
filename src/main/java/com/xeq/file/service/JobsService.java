package com.xeq.file.service;

import java.util.List;

import org.dom4j.Element;

import com.ssh.xep.entity.JobInfo;
import com.xeq.file.domain.JobCss;
import com.xeq.file.domain.JobStep;
import com.xeq.file.domain.PageSource;

public interface JobsService {
	/** 查询JobInfo信息 */
	List<JobInfo> getJobList(String hql);

	/** 查询该Id对应的job */
	JobInfo getJobInfo(Integer id);

	/** 解析JobStep **/
	List<JobStep> getNodes(Element node, List<JobStep> lists);

	/** 解析JobInfo */
	List<JobCss> getJobCss(List<JobInfo> jlist);

	/** 将String转换为Time */
	long getTime(String date);

	/**
	 * 作业的分页查询
	 * 
	 * @param page分页pojo类
	 * @param hql语句
	 * @return list列表
	 */
	List<JobInfo> pageReview(PageSource page, String hql);

	String getUserName(Integer userId);
}
