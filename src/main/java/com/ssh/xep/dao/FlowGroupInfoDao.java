package com.ssh.xep.dao;

import java.util.List;

import com.ssh.xep.entity.FlowGroupInfo;
import com.ssh.xep.entity.FlowGroupUser;

public interface FlowGroupInfoDao extends GenericDao<FlowGroupInfo, Integer> {
	void delete(List<Integer> idl);

	long count();

	List<FlowGroupInfo> findAll(int lStart, int lEnd);
}
