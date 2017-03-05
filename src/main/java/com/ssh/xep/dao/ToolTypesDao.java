package com.ssh.xep.dao;

import java.util.List;

import com.ssh.xep.entity.ToolTypes;

public interface ToolTypesDao extends GenericDao<ToolTypes, Integer> {
	List<ToolTypes> findAll(Integer userId);
}
