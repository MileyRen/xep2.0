package com.ssh.xep.dao;

import java.util.List;

import com.ssh.xep.entity.JobFlowUser;
import com.ssh.xep.entity.JobInfo;

public interface JobInfoDao extends GenericDao<JobInfo, Integer> {
	List<JobInfo> findAll(Integer userId);
	List<JobInfo> findAll(Integer userId, Integer flowBasicInfoId);
	List<JobFlowUser> findAllJoin(Integer userId, int lStart, int lEnd);
	List<JobFlowUser> findAllJoin(Integer userId, Integer flowBasicInfoId, int lStart, int lEnd);

	void delete(List<Integer> ida);

	int count();
}
