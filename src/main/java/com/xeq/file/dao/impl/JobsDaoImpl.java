package com.xeq.file.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.gene.utils.User;
import com.ssh.xep.entity.JobInfo;
import com.xeq.file.dao.JobsDao;
import com.xeq.file.domain.PageSource;

@Repository("jobsDaoImpl")
public class JobsDaoImpl extends BaseDao implements JobsDao {

	@Override
	public List<JobInfo> getJobList(String hql) {
		Query query = getSession().createQuery(hql);
		List<JobInfo> list = query.list();
		return list;
	}

	@Override
	public JobInfo getJobInfo(Integer id) {
		String hql = "FROM JobInfo WHERE id=" + id;
		Query query = getSession().createQuery(hql);
		JobInfo jobInfo = (JobInfo) query.uniqueResult();
		return jobInfo;
	}

	@Override
	public List<JobInfo> jobpage(PageSource page, String hql) {
		Query query = getSession().createQuery(hql);
		query.setFirstResult((page.getCurrentPage() - 1) * page.getPageSize());
		query.setMaxResults(page.getPageSize());
		List<JobInfo> list = query.list();
		return list;
	}

	@Override
	public String getUserName(Integer userId) {
		String hql = "FROM User WHERE id=" + userId;
		Query query = getSession().createQuery(hql);
		User user = (User) query.uniqueResult();
		return user.getUserName();
	}

}
