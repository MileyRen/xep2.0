package com.ssh.xep.util;

import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import java.io.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 生成BPMN文件
 *
 * @author qilin
 */
public class MakeBpmn {
	private Document xml;
	private Set<String> exts;
	private static final String loadNamePrefix = UserProperties.LOAD_NAME_PREFIX;

	public MakeBpmn(String loadName) throws ParserConfigurationException {
		this(loadName, loadName + " name");
	}

	/**
	 * 初始化，创建bpmn文件的雏形
	 *
	 * @param loadName 执行bpmn文件时需要的名字，会被冠以前缀，作为id存在 id是loadNamePrefix【loadName】
	 * @param name     bpmn文件中process的name
	 * @throws ParserConfigurationException
	 */
	public MakeBpmn(String loadName, String name) throws ParserConfigurationException {
		this.exts = new HashSet<String>();

		xml = DocumentFactory.getInstance().createDocument();
		Element e = xml.addElement("definitions", "http://www.omg.org/spec/BPMN/20100524/MODEL");

		e.addAttribute("id", "Definition");
		e.addAttribute("targetNamespace", "http://www.jboss.org/drools");
		e.addAttribute("typeLanguage", "http://www.java.com/javaTypes");
		e.addAttribute("expressionLanguage", "http://www.mvel.org/2.0");
		e.addAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
		e.addAttribute("xsi:schemaLocation", "http://www.omg.org/spec/BPMN/20100524/MODEL BPMN20.xsd");
		e.addAttribute("xmlns:g", "http://www.jboss.org/drools/flow/gpd");
		e.addAttribute("xmlns:bpmndi", "http://www.omg.org/spec/BPMN/20100524/DI");
		e.addAttribute("xmlns:dc", "http://www.omg.org/spec/DD/20100524/DC");
		e.addAttribute("xmlns:di", "http://www.omg.org/spec/DD/20100524/DI");
		e.addAttribute("xmlns:tns", "http://www.jboss.org/drools");

		Element e1 = e.addElement("process");
		Element e2 = e.addElement("bpmndi:BPMNDiagram", "http://www.omg.org/spec/BPMN/20100524/DI");

		e1.addAttribute("processType", "Private");
		e1.addAttribute("isExecutable", "true");
		e1.addAttribute("id", loadNamePrefix + loadName);
		e1.addAttribute("name", name);

		Element imp = e1.addElement("extensionElements");
		imp.addElement("tns:import", "http://www.jboss.org/drools").addAttribute("name", "com.jbpm.jbpm.App");
		imp.addElement("tns:import", "http://www.jboss.org/drools").addAttribute("name",
				"com.ssh.xep.util.process.Process");
		imp.addElement("tns:import", "http://www.jboss.org/drools").addAttribute("name", "java.util.Iterator");
		imp.addElement("tns:import", "http://www.jboss.org/drools").addAttribute("name", "java.util.Date");
		imp.addElement("tns:import", "http://www.jboss.org/drools").addAttribute("name", "java.sql.Connection");
		imp.addElement("tns:import", "http://www.jboss.org/drools").addAttribute("name", "java.sql.PreparedStatement");
		imp.addElement("tns:import", "http://www.jboss.org/drools").addAttribute("name", "java.sql.ResultSet");
		imp.addElement("tns:import", "http://www.jboss.org/drools").addAttribute("name", "net.sf.json.JSONArray");
		imp.addElement("tns:import", "http://www.jboss.org/drools").addAttribute("name", "net.sf.json.JSONObject");
		Element eStart = e1.addElement("startEvent");
		eStart.addAttribute("id", "_1");
		eStart.addAttribute("isInterrupting", "true");

		Element eEnd = e1.addElement("endEvent");
		eEnd.addAttribute("id", "_3");
		eEnd.addElement("terminateEventDefinition");

		Element plane = e2.addElement("bpmndi:BPMNPlane", "http://www.omg.org/spec/BPMN/20100524/DI");
		plane.addAttribute("bpmnElement", loadNamePrefix + loadName);
		plane.addElement("bpmndi:BPMNShape", "http://www.omg.org/spec/BPMN/20100524/DI").addAttribute("bpmnElement", "_1")
				.addElement("dc:Bounds", "http://www.omg.org/spec/DD/20100524/DC").addAttribute("x", "50")
				.addAttribute("y", "50").addAttribute("width", "24").addAttribute("height", "24");
		plane.addElement("bpmndi:BPMNShape", "http://www.omg.org/spec/BPMN/20100524/DI").addAttribute("bpmnElement", "_3")
				.addElement("dc:Bounds", "http://www.omg.org/spec/DD/20100524/DC").addAttribute("x", "500")
				.addAttribute("y", "50").addAttribute("width", "24").addAttribute("height", "24");
	}

	public void addTask(String taskId, String name, int toolId, String toolName, String toolType, String toolPath,
						File scriptFile, String addOn) throws IOException {
		addTask(taskId, name, toolId, toolName, toolType, toolPath, scriptFile, null, addOn);
	}

	/**
	 * @param taskId     任务ID
	 * @param name       任务名称
	 * @param scriptFile 脚本文件，里面包含了完整的脚本
	 * @param exts       引用包
	 * @throws IOException
	 */
	public void addTask(String taskId, String name, int toolId, String toolName, String toolType, String toolPath,
						File scriptFile, String exts, String addOn) throws IOException {
		FileInputStream fis = new FileInputStream(scriptFile);
		InputStreamReader isr = new InputStreamReader(fis);
		BufferedReader br = new BufferedReader(isr);
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = br.readLine()) != null) {
			sb.append(line);
			sb.append("\n");
		}
		fis.close();

		addTask(taskId, name, toolId, toolName, toolType, toolPath, sb.toString(), exts, addOn);
	}

	public void addTask(String taskId, String name, int toolId, String toolName, String toolType, String toolPath,
						String script, String addOn) {
		addTask(taskId, name, toolId, toolName, toolType, toolPath, script, (String) null, addOn);
	}

	public void addTask(String taskId, String name, int toolId, String toolName, String toolType, String toolPath,
						String script, String exts, String addOn) {
		if (exts != null) {
			addTask(taskId, name, toolId, toolName, toolType, toolPath, script, exts.split(";"), addOn);
		} else {
			addTask(taskId, name, toolId, toolName, toolType, toolPath, script, (String[]) null, addOn);
		}
	}

	/**
	 * @param taskId   任务ID
	 * @param dataName 任务名称
	 * @param script   执行脚本，完整脚本
	 * @param exts     引用包，完整形式，包含import
	 */
	public void addTask(String taskId, String dataName, int toolId, String toolName, String toolType, String toolPath,
						String script, String[] exts, String addOn) {
		if (exts != null) {
			Element e = xml.getRootElement();
			e = e.element("process").element("extensionElements");
			for (String s : exts) {
				char[] cs = s.toCharArray();
				int bg = 0;
				while (cs[bg] == ' ' || cs[bg] == '\r' || cs[bg] == '\n' || cs[bg] == '\t') {
					bg++;
				}
				int ed = cs.length - 1;
				while (cs[ed] == ' ' || cs[ed] == '\r' || cs[ed] == '\n' || cs[ed] == '\t') {
					ed--;
				}
				// 去除import
				if (bg + 7 >= ed) {
					continue;
				}
				// 去除import
				s = s.substring(bg + 7, ed + 1);
				if (this.exts.contains(s) == false) {
					this.exts.add(s);
					e.addElement("tns:import", "http://www.jboss.org/drools").addAttribute("name", s);
				}
			}
		}

		Element e = xml.getRootElement().element("process");
		String id = "_jbpm-unique-" + taskId;
		String name = taskId;
		String scriptFormat = "http://www.java.com/java";
		e = e.addElement("scriptTask");
		e.addAttribute("id", id);
		e.addAttribute("name", name);
		e.addAttribute("scriptFormat", scriptFormat);
		e.addAttribute("data-name", dataName);
		e.addAttribute("tool-id", String.valueOf(toolId));
		e.addAttribute("tool-name", String.valueOf(toolName));
		e.addAttribute("tool-type", String.valueOf(toolType));
		e.addAttribute("tool-path", String.valueOf(toolPath));
		e.addElement("script").addText(script);
		if (addOn == null) {
			addOn = "";
		}
		e.addElement("addOn").addText(addOn);
	}

	/**
	 * 将不同id的script联系在一起，会判断_1和_3
	 *
	 * @param fromId 源id，会被加上前缀
	 * @param toId   目的id，会被加上前缀
	 */
	public void addConnection(String fromId, String toId) {
		String sourceRef = fromId;
		if (sourceRef.equals("_1") == false) {
			sourceRef = "_jbpm-unique-" + sourceRef;
		}
		String targetRef = toId;
		if (targetRef.equals("_3") == false) {
			targetRef = "_jbpm-unique-" + targetRef;
		}
		String id = sourceRef + "-" + targetRef;
		Element e = xml.getRootElement().element("process").addElement("sequenceFlow");
		e.addAttribute("id", id).addAttribute("sourceRef", sourceRef).addAttribute("targetRef", targetRef);
	}

	/**
	 * 添加分开节点
	 *
	 * @param gatewayId 会被加上前缀
	 */
	public void addDiverging(String gatewayId) {
		String id = "_jbpm-unique-" + gatewayId;
		xml.getRootElement().element("process").addElement("parallelGateway").addAttribute("id", id)
				.addAttribute("name", "Gateway").addAttribute("gatewayDirection", "Diverging");
	}

	/**
	 * 添加合并节点
	 *
	 * @param gatewayId 会被加上前缀
	 */
	public void addConverging(String gatewayId) {
		String id = "_jbpm-unique-" + gatewayId;
		xml.getRootElement().element("process").addElement("parallelGateway").addAttribute("id", id)
				.addAttribute("name", "Gateway").addAttribute("gatewayDirection", "Converging");
	}

	public String get() throws TransformerFactoryConfigurationError, TransformerException {
		return xml.asXML();
	}

	public Document getXML() {
		return xml;
	}
}
