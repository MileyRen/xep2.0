package com.ssh.xep.service.impl;

import com.ssh.xep.dao.FlowBasicInfoDao;
import com.ssh.xep.entity.FlowBasicInfo;
import com.ssh.xep.entity.FlowGroupUser;
import com.ssh.xep.service.FlowBasicInfoService;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.xml.parsers.ParserConfigurationException;
import java.util.List;

@Service("flowBasicInfoService")
public class FlowBasicInfoServiceImpl implements FlowBasicInfoService {
	@Autowired
	private FlowBasicInfoDao dao;

	public FlowBasicInfo load(Integer id) {
		return dao.load(id);
	}

	public FlowBasicInfo get(Integer id) throws DocumentException {
		FlowBasicInfo info = dao.get(id);
		return info;
	}

	public List<FlowBasicInfo> findAll() throws DocumentException {
		List<FlowBasicInfo> infos = dao.findAll();
		return infos;
	}

	public List<FlowBasicInfo> findAll(Integer userId, String startDate, String endDate) throws DocumentException {
		List<FlowBasicInfo> infos = dao.findAll(userId, startDate, endDate);
		return infos;
	}

	public List<FlowBasicInfo> findAll(Integer userId, int[] auths, String startDate, String endDate) throws DocumentException {
		List<FlowBasicInfo> infos = dao.findAll(userId, auths, startDate, endDate);
		return infos;
	}

	private int[] getLSE(String page) {
		int lStart=0, lEnd=10;
		if(page.equals("start")) {
			lStart = 0;
			lEnd = 10;
		} else if(page.equals("latest")) {
			int c = (int) count();
			c /= 10;
			c *= 10;
			lStart = c;
			lEnd = c+10;
		} else {
			int p = Integer.parseInt(page);
			lStart = p*10;
			lEnd = lStart+10;
		}
		return new int[]{lStart, lEnd};
	}

	public List<FlowGroupUser> findAllJoin(Integer userId, int[] auths, String startDate, String endDate, String page) throws DocumentException {
		int[] l = getLSE(page);
		List<FlowGroupUser> infos = dao.findAllJoin(userId, auths, startDate, endDate, l[0], l[1]);
		return infos;
	}

	public List<FlowGroupUser> findAllJoin(Integer userId, int[] auths, String startDate, String endDate, String page, int auth) throws DocumentException {
		int[] l = getLSE(page);
		List<FlowGroupUser> infos = dao.findAllJoin(userId, auths, startDate, endDate, l[0], l[1], auth);
		return infos;
	}

	public long count() {
		return dao.count();
	}

	public void pessist(FlowBasicInfo entity) {
		dao.persist(entity);
	}

	public Integer save(FlowBasicInfo entity) throws ParserConfigurationException {
		return dao.save(entity);
	}

	public void saveOrUpdate(FlowBasicInfo entity) throws ParserConfigurationException {
		dao.saveOrUpdate(entity);
	}

	public void delete(Integer id) {
		dao.delete(id);
	}

	public void delete(List<Integer> ids) {
		dao.delete(ids);
	}

	public void flush() {
		dao.flush();
	}

}
