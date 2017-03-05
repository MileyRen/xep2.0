package com.ssh.xep.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

/**
 * makeFlow现在不需要服务器实现了。。。
 * Sat 17:22 10/29/2016
 */
@Deprecated
public class MakeFlow {
	private MakeBpmn bpmn;

	public MakeFlow(String loadName, String name) throws ParserConfigurationException {
		bpmn = new MakeBpmn(loadName, name);
	}

	public MakeBpmn getBpmn() {
		return bpmn;
	}

	// 添加工具
	// 工具是获取工具ID的，执行程序的时候，会根据工具ID获取工具位置，然后执行工具
	// 获取工具这个功能是在JBPM执行时实现的
	/*
	 * String path =
	 * conn.executeSql("select toolPath from tool where toolId = [toolId]");
	 * Process p = Process.execute("path");
	 */
	public void addTask(String id, String name, String toolId, String toolName, String toolType, String toolPath,
						JSONArray toolInfo, String addOn) throws IOException {
		FileInputStream fis = new FileInputStream(MakeFlow.class.getClassLoader().getResource("bpmn_script_template.dat").getPath());
		StringBuilder sb = new StringBuilder();
		String line;
		BufferedReader br = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
		while ((line = br.readLine()) != null) {
			sb.append(line);
			sb.append("\n");
		}
		String template = sb.toString();
		fis.close();
		sb = new StringBuilder();
		if (toolType.equals("java")) {
			sb.append("java -jar ");
		} else if (toolType.equals("python")) {
			sb.append("python ");
		} else if (toolType.equals("shell")) {
			sb.append("/bin/bash ");
		} else if (toolType.equals("perl")) {
			sb.append("perl ");
		}
		sb.append(toolPath);
		Iterator<JSONObject> it = toolInfo.iterator();
		while (it.hasNext()) {
			JSONObject obj = it.next();
			String type = obj.getString("type");
			if (type.startsWith("db")) {
				sb.append(obj.getString("path"));
				sb.append(" ");
			} else if (type.startsWith("id")) {
				sb.append("$");
				sb.append(obj.getString("value"));
				sb.append(" ");
			} else {
				sb.append(obj.getString("value"));
				sb.append(" ");
			}
		}
		String command = sb.toString();
		template = String.format(template, command, id);
		bpmn.addTask(id, name, Integer.parseInt(toolId), toolName, toolType, toolPath, template, addOn);
	}

	// 给不同ID之间添加连接
	public void addConnection(String fromId, String toId) {
		bpmn.addConnection(fromId, toId);
	}

	// 添加分开节点
	public void addDiverging(String gatewayId) {
		bpmn.addDiverging(gatewayId);
	}

	// 添加合并节点
	public void addConverging(String gatewayId) {
		bpmn.addConverging(gatewayId);
	}
}
