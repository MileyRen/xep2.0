package com.ssh.xep.util;

import org.dom4j.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * 加载BPMN数据并生成对应文件。
 *
 * @author qilin
 */
public class LoadBpmn {
	private static final String loadNamePrefix = UserProperties.LOAD_NAME_PREFIX;

	public static String loadBpmn(String bpmnStr, String userId, String jobId, String userRoot)
			throws DocumentException, IOException {
		Document xml = DocumentHelper.parseText(bpmnStr);
		return loadBpmn(xml, userId, jobId, userRoot);
	}

	/**
	 * 在用户特定路径下生成bpmn文件，文件名是【userId】_【jobId】.bpmn，调用id是loadNamePrefix【userId】.【jobId】
	 *
	 * @param xml      xml文件
	 * @param userId   用户ID
	 * @param jobId    数据库中的job的ID
	 * @param userRoot 用户根目录，目前是tmp文件夹下的xep_【userId】文件夹
	 * @throws DocumentException
	 * @throws IOException
	 */
	public static String loadBpmn(Document xml, String userId, String jobId, String userRoot)
			throws DocumentException, IOException {
		Element process = xml.getRootElement().element("process");
		xml.getRootElement().element(new QName("BPMNDiagram", xml.getRootElement().getNamespaceForPrefix("bpmndi")))
				.element(new QName("BPMNPlane", xml.getRootElement().getNamespaceForPrefix("bpmndi")))
				.addAttribute("bpmnElement", loadNamePrefix + userId + "." + jobId);
		process.remove(process.attribute("id"));
		process.addAttribute("id", loadNamePrefix + userId + "." + jobId);

		if (userRoot.endsWith("/") == false) {
			userRoot += "/";
		}
		File bpmnDir = new File(userRoot + "bpmn/");
		if (bpmnDir.isDirectory() == false) {
			bpmnDir.delete();
		}
		if (bpmnDir.exists() == false) {
			bpmnDir.mkdirs();
		}
		File bpmnFile = new File(userRoot + "bpmn/" + userId + "_" + jobId + ".bpmn");
		if (bpmnFile.exists()) {
			bpmnFile.delete();
		}
		bpmnFile.createNewFile();
		FileOutputStream fos = new FileOutputStream(bpmnFile);
		OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
		osw.write(xml.asXML());
		osw.flush();
		osw.close();
		fos.close();

		return bpmnFile.getAbsolutePath();
	}
}
