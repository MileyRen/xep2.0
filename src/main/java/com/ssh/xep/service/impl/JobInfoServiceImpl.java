package com.ssh.xep.service.impl;

import com.ssh.xep.dao.JobInfoDao;
import com.ssh.xep.entity.JobFlowUser;
import com.ssh.xep.entity.JobInfo;
import com.ssh.xep.service.JobInfoService;
import org.dom4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.parsers.ParserConfigurationException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

@Service("jobInfoService")
public class JobInfoServiceImpl implements JobInfoService {
	@Autowired
	private JobInfoDao dao;

	@Deprecated
	public JobInfo load(Integer id) {
		return dao.load(id);
	}

	public JobInfo get(Integer id) throws DocumentException {
		JobInfo info = dao.get(id);
		return info;
	}

	public List<JobInfo> findAll() throws DocumentException {
		List<JobInfo> infos = dao.findAll();
		return infos;
	}

	public List<JobInfo> findAll(Integer userId) throws DocumentException {
		List<JobInfo> infos = dao.findAll(userId);
		return infos;
	}

	public void pessist(JobInfo entity) {
		dao.persist(entity);
	}

	public Integer save(JobInfo entity) throws ParserConfigurationException {
		return dao.save(entity);
	}

	public void saveOrUpdate(JobInfo entity) throws ParserConfigurationException {
		dao.saveOrUpdate(entity);
	}

	public void delete(Integer id) {
		dao.delete(id);
	}

	public void delete(List<Integer> ida) {
		dao.delete(ida);
	}

	public void flush() {
		dao.flush();
	}

	public String getProcessInfo(String bpmn) throws UnsupportedEncodingException, DocumentException {
		String dStr = URLDecoder.decode(bpmn, "UTF-8");
		Document d = DocumentHelper.parseText(dStr);
		Document ret = DocumentFactory.getInstance().createDocument();
		Element retRoot = ret.addElement("states");
		List<Element> el = d.getRootElement().element("process").elements("scriptTask");
		for (Element e : el) {
			retRoot.addElement("script").addAttribute("id", e.attributeValue("id")).addAttribute("name", e.attributeValue("name")).addAttribute("state", "pending");
		}
		return URLEncoder.encode(ret.asXML(), "UTF-8");
	}

	private int[] getLSE(String page) {
		if (page == null) {
			return new int[]{0, 10};
		} else if (page.equals("start")) {
			return new int[]{0, 10};
		} else if (page.equals("latest")) {
			int max = dao.count();
			int bg = max / 10 * 10;
			return new int[]{bg, bg + 10};
		} else {
			int p = Integer.parseInt(page);
			return new int[]{p * 10, p * 10 + 10};
		}
	}

	public List<JobFlowUser> findAllJoin(Integer userId, String page) {
		int[] lse = getLSE(page);
		return dao.findAllJoin(userId, lse[0], lse[1]);
	}

	public List<JobFlowUser> findAllJoin(Integer userId, Integer flowBasicInfoId, String page) {
		int[] lse = getLSE(page);
		return dao.findAllJoin(userId, flowBasicInfoId, lse[0], lse[1]);
	}

	public int count() {
		return dao.count();
	}
}
