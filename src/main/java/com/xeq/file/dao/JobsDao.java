package com.xeq.file.dao;

import java.util.List;

import com.ssh.xep.entity.JobInfo;
import com.xeq.file.domain.PageSource;

/** 作业查询Dao */
public interface JobsDao {

	/** 查询JobInfo信息 */
	List<JobInfo> getJobList(String hql);

	/** 查询该Id对应的job */
	JobInfo getJobInfo(Integer id);

	/**
	 * Job分页查询
	 * 
	 * @param page分页pojo类
	 * @param hql语句
	 * @return list列表
	 */
	List<JobInfo> jobpage(PageSource page, String hql);
}
