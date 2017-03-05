package com.ssh.xep.dao;

import java.util.List;

import com.ssh.xep.entity.FlowBasicInfo;
import com.ssh.xep.entity.FlowGroupUser;

public interface FlowBasicInfoDao extends GenericDao<FlowBasicInfo, Integer> {
	List<FlowBasicInfo> findAll(Integer userId, int[] auths, String startDate, String endDate);
	List<FlowBasicInfo> findAll(Integer userId, String startDate, String endDate);

	List<FlowGroupUser> findAllJoin(Integer userId, int[] auths, String startDate, String endDate, int lStart, int lEnd, int auth);
	List<FlowGroupUser> findAllJoin(Integer userId, int[] auths, String startDate, String endDate, int lStart, int lEnd);

	void delete(List<Integer> ids);

	long count();
}
