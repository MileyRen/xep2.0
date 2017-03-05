package com.ssh.xep.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.List;

public class ProcessInfo {
	/**
	 * @param flowInfo 流程或者作业的字符串
	 * @return
	 * @throws DocumentException
	 */
	public static String generateProcessInfo(String flowInfo) throws DocumentException {
		Document ret = DocumentHelper.parseText("<states></states>");
		Element retRoot = ret.getRootElement();
		Document document = DocumentHelper.parseText(flowInfo);
		Element process = document.getRootElement().element("process");
		List<Element> el = process.elements("scriptTask");
		for (Element e : el) {
			retRoot.addElement("script").addAttribute("id", e.attributeValue(""))
					.addAttribute("name", e.attributeValue("data-name")).addAttribute("state", "pending");
		}
		return ret.asXML();
	}
}
