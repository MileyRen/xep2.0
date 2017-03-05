package com.ssh.xep.service;

import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import com.ssh.xep.entity.FlowGroupUser;
import org.dom4j.DocumentException;

import com.ssh.xep.entity.FlowBasicInfo;

public interface FlowBasicInfoService {
	FlowBasicInfo load(Integer id);

	FlowBasicInfo get(Integer id) throws DocumentException;

	List<FlowBasicInfo> findAll() throws DocumentException;

	List<FlowBasicInfo> findAll(Integer userId, int[] auths, String startDate, String endDate) throws DocumentException;

	List<FlowBasicInfo> findAll(Integer userId, String startDate, String endDate) throws DocumentException;

	List<FlowGroupUser> findAllJoin(Integer userId, int[] auths, String startDate, String endDate, String page, int auth) throws DocumentException;

	List<FlowGroupUser> findAllJoin(Integer userId, int[] auths, String startDate, String endDate, String page) throws DocumentException;

	long count();

	void pessist(FlowBasicInfo entity);

	/**
	 * @param entity
	 *            传入的实体
	 * @return 生成的ID
	 * @throws ParserConfigurationException
	 */
	Integer save(FlowBasicInfo entity) throws ParserConfigurationException;

	void saveOrUpdate(FlowBasicInfo entity) throws ParserConfigurationException;

	void delete(Integer id);

	void delete(List<Integer> ids);

	void flush();
}
