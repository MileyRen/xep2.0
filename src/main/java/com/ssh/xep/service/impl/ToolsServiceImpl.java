package com.ssh.xep.service.impl;

import com.ssh.xep.dao.ToolsDao;
import com.ssh.xep.entity.Tools;
import com.ssh.xep.service.ToolsService;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;

@Service("toolsService")
public class ToolsServiceImpl implements ToolsService {
	@Autowired
	private ToolsDao dao;

	@Deprecated
	public Tools load(Integer id) {
		return dao.load(id);
	}

	public Tools get(Integer id) throws DocumentException, UnsupportedEncodingException {
		Tools tool = dao.get(id);
		tool.setXmlPath(this.getXML(tool.getXmlPath()));
//		tool.setXmlPath(this.parseXML2JSON(tool.getXmlPath()));
		return tool;
	}

	public List<Tools> findAll() throws DocumentException, UnsupportedEncodingException {
		List<Tools> tools = dao.findAll();
		if(tools == null) {
			return tools;
		}
		for (Tools t : tools) {
			t.setXmlPath(this.getXML(t.getXmlPath()));
//			t.setXmlPath(this.parseXML2JSON(t.getXmlPath()));
		}
		return tools;
	}

	public List<Tools> findAll(Integer userId) throws DocumentException, UnsupportedEncodingException {
		List<Tools> tools = dao.findAll(userId);
		if(tools == null) {
			return tools;
		}
		for (Tools t : tools) {
			t.setXmlPath(this.getXML(t.getXmlPath()));
//			t.setXmlPath(this.parseXML2JSON(t.getXmlPath()));
		}
		return tools;
	}

	public void pessist(Tools entity) {
		dao.persist(entity);
	}

	public Integer save(Tools entity) throws ParserConfigurationException {
		return dao.save(entity);
	}

	public void saveOrUpdate(Tools entity) throws ParserConfigurationException {
		dao.saveOrUpdate(entity);
	}

	public void delete(Integer id) {
		dao.delete(id);
	}

	public void flush() {
		dao.flush();
	}

	public String getXML(String xmlOrPath) throws UnsupportedEncodingException {
		if (xmlOrPath.startsWith("<?")) {
			return xmlOrPath;
		}
		StringBuilder sb = new StringBuilder();
		String line;
		FileInputStream fis;
		try {
			fis = new FileInputStream(xmlOrPath);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
			while ((line = br.readLine()) != null) {
				sb.append(line);
				sb.append("\n"); // linux下，换行符
			}
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return URLEncoder.encode(sb.toString(), "UTF-8");
	}

	/**
	 * 将本tool的XML文件解析成JSON
	 * 放弃
	 * @param xml
	 * @return
	 * @throws DocumentException
	 */
//	private String parseXML2JSON(String xml) throws DocumentException {
//		System.out.println(xml);
//		JSONObject json = new JSONObject();
//		Element root = DocumentHelper.parseText(xml).getRootElement();
//		Element commandE = root.element("command");
//		Element inputs = root.element("inputs");
//		Element outputs = root.element("outputs");
//		json.put("tool-type", root.attributeValue("interpreter"));
//		JSONArray toolInfos = new JSONArray();
//		String[] commands = commandE.getText().split(" ");
//
//		JSONObject temp = new JSONObject();
//		List<Element> templ = inputs.elements("param");
//		for (Element e : templ) {
//			JSONObject t = new JSONObject();
//			String name = e.attributeValue("name");
//			if (name.startsWith("input")) {
//				t.put("type", "inputFile");
//			} else {
//				String type = e.attributeValue("type");
//				t.put("type", type);
//				if (type.equals("integer") || type.equals("float") || type.equals("boolean")) {
//					t.put("size", e.attributeValue("size"));
//				} else if (type.equals("text")) {
//					t.put("size", e.attributeValue("size"));
//				} else if (type.equals("select")) {
//					List<Element> el = e.elements("option");
//					JSONArray ta = new JSONArray();
//					for (Element ee : el) {
//						ta.add("{\"value:\"\"" + ee.attributeValue("value") + "\",\"label\":\"" + ee.getText() + "\"}");
//					}
//					t.put("options", ta);
//				}
//			}
//			temp.put(name, t);
//		}
//		templ = outputs.elements("data");
//		for (Element e : templ) {
//			JSONObject t = new JSONObject();
//			String name = e.attributeValue("name");
//			t.put("type", "outputFile");
//			temp.put(name, t);
//		}
//
//		for (int i = 1; i < commands.length; i++) {
//			String c = commands[i];
//			if (c.startsWith("$") == false) {
//				JSONObject o = new JSONObject();
//				o.put("type", "fixed");
//				o.put("value", c);
//				toolInfos.add(o);
//				continue;
//			} else {
//				c = c.substring(1);
//				JSONObject t = temp.getJSONObject(c);
//				toolInfos.add(t);
//			}
//		}
//		json.put("tool-info", toolInfos);
//
//		return json.toString();
//	}
}
