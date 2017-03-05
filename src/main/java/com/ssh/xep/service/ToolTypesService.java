package com.ssh.xep.service;

import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.dom4j.DocumentException;

import com.ssh.xep.entity.ToolTypes;

public interface ToolTypesService {
	ToolTypes load(Integer id);

	ToolTypes get(Integer id) throws DocumentException;

	List<ToolTypes> findAll() throws DocumentException;

	List<ToolTypes> findAll(Integer userId) throws DocumentException;

	void pessist(ToolTypes entity);

	/**
	 * @param entity
	 *            传入的实体
	 * @return 生成的ID
	 * @throws ParserConfigurationException
	 */
	Integer save(ToolTypes entity) throws ParserConfigurationException;

	void saveOrUpdate(ToolTypes entity) throws ParserConfigurationException;

	void delete(Integer id);

	void flush();
}
