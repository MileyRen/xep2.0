package com.ssh.xep.dao;

import java.util.List;

import com.ssh.xep.entity.Tools;

public interface ToolsDao extends GenericDao<Tools, Integer> {
	List<Tools> findAll(Integer userId);
}
