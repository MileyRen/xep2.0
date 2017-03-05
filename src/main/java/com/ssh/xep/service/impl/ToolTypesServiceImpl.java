package com.ssh.xep.service.impl;

import com.ssh.xep.dao.ToolTypesDao;
import com.ssh.xep.entity.ToolTypes;
import com.ssh.xep.service.ToolTypesService;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.parsers.ParserConfigurationException;
import java.util.List;

@Service("toolTypesService")
public class ToolTypesServiceImpl implements ToolTypesService {
	@Autowired
	private ToolTypesDao dao;

	@Deprecated
	public ToolTypes load(Integer id) {
		return dao.load(id);
	}

	public ToolTypes get(Integer id) throws DocumentException {
		return dao.get(id);
	}

	public List<ToolTypes> findAll() throws DocumentException {
		return dao.findAll();
	}

	public List<ToolTypes> findAll(Integer userId) throws DocumentException {
		return dao.findAll(userId);
	}

	public void pessist(ToolTypes entity) {
		dao.persist(entity);
	}

	public Integer save(ToolTypes entity) throws ParserConfigurationException {
		return dao.save(entity);
	}

	public void saveOrUpdate(ToolTypes entity) throws ParserConfigurationException {
		dao.saveOrUpdate(entity);
	}

	public void delete(Integer id) {
		dao.delete(id);
	}

	public void flush() {
		dao.flush();
	}

}
