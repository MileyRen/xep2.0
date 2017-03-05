package com.ssh.xep.service;

import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import com.ssh.xep.entity.TreeList;
import org.dom4j.DocumentException;

import com.xeq.file.domain.FileAndFolder;

public interface FileService {
	FileAndFolder load(Integer id);

	FileAndFolder get(Integer id) throws DocumentException;

	List<FileAndFolder> findAll() throws DocumentException;

	List<TreeList> findAll(Integer userId) throws DocumentException;

	List<TreeList> findAll(Integer userId, String type) throws DocumentException;

	void pessist(FileAndFolder entity);

	/**
	 * @param entity
	 *            传入的实体
	 * @return 生成的ID
	 * @throws ParserConfigurationException
	 */
	Integer save(FileAndFolder entity) throws ParserConfigurationException;

	void saveOrUpdate(FileAndFolder entity) throws ParserConfigurationException;

	void delete(Integer id);

	void flush();
}
