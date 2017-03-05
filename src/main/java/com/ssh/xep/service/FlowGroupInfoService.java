package com.ssh.xep.service;

import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.dom4j.DocumentException;

import com.ssh.xep.entity.FlowGroupInfo;

public interface FlowGroupInfoService {
	FlowGroupInfo load(Integer id);

	FlowGroupInfo get(Integer id) throws DocumentException;

	List<FlowGroupInfo> findAll() throws DocumentException;

	List<FlowGroupInfo> findAll(String page) throws DocumentException;

	void pessist(FlowGroupInfo entity);

	Integer save(FlowGroupInfo entity) throws ParserConfigurationException;

	void saveOrUpdate(FlowGroupInfo entity) throws ParserConfigurationException;

	void delete(Integer id);

	void delete(List<Integer> idl);

	void flush();

	int count();
}
