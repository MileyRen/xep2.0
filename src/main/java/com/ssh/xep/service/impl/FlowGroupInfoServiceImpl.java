package com.ssh.xep.service.impl;

import com.ssh.xep.dao.FlowGroupInfoDao;
import com.ssh.xep.entity.FlowGroupInfo;
import com.ssh.xep.service.FlowGroupInfoService;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.parsers.ParserConfigurationException;
import java.util.List;

@Service("flowGroupInfoService")
public class FlowGroupInfoServiceImpl implements FlowGroupInfoService {
	@Autowired
	private FlowGroupInfoDao dao;

	public FlowGroupInfo load(Integer id) {
		return dao.load(id);
	}

	public FlowGroupInfo get(Integer id) throws DocumentException {
		FlowGroupInfo info = dao.get(id);
		return info;
	}

	private int[] getLSE(String page) {
		int lStart, lEnd;
		if (page.equals("start")) {
			lStart = 0;
			lEnd = 10;
		} else if (page.equals("end")) {
			int c = count();
			c /= 10;
			c *= 10;
			lStart = c;
			lEnd = c + 10;
		} else {
			int p = Integer.parseInt(page);
			lStart = p * 10;
			lEnd = lStart + 10;
		}
		return new int[]{lStart, lEnd};
	}

	public List<FlowGroupInfo> findAll() throws DocumentException {
		return dao.findAll();
	}

	public List<FlowGroupInfo> findAll(String page) throws DocumentException {
		int[] lse = getLSE(page);
		List<FlowGroupInfo> infos = dao.findAll(lse[0], lse[1]);
		return infos;
	}

	public void pessist(FlowGroupInfo entity) {
		dao.persist(entity);
	}

	public Integer save(FlowGroupInfo entity) throws ParserConfigurationException {
		return dao.save(entity);
	}

	public void saveOrUpdate(FlowGroupInfo entity) throws ParserConfigurationException {
		dao.saveOrUpdate(entity);
	}

	public void delete(Integer id) {
		dao.delete(id);
	}

	public void delete(List<Integer> idl) {
		dao.delete(idl);
	}

	public void flush() {
		dao.flush();
	}

	@Override
	public int count() {
		return (int) dao.count();
	}

}
