package com.ssh.xep.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by qi_l on 2016/11/16.
 * 读取user.properties配置
 */
public class UserProperties {
	public static final String HOME;
	public static final String TMP;
	public static final String JBPM_PATH;
	public static final String LOAD_NAME_PREFIX;
	;

	static {
		String home = System.getProperty("user.dir");
		String tmp = System.getProperty("java.io.tmpdir");
		String jbpm_path = tmp + "jbpm.jar";
		String loadNamePrefix = "com.ssh.xep.bpmn.";

		String path = Flow2JobImpl.class.getClassLoader().getResource("user.properties").getPath();
		Properties p = new Properties();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(path);
			p.load(fis);
			home = p.getProperty("home");
			tmp = p.getProperty("tmp");
			jbpm_path = p.getProperty("jbpm_path");
			loadNamePrefix = p.getProperty("load_name_prefix");
		} catch (IOException e) {
		} finally {
			if (fis != null) {
				try { fis.close(); } catch (IOException e) {}
			}
		}

		HOME = home;
		TMP = tmp;
		JBPM_PATH = jbpm_path;
		LOAD_NAME_PREFIX = loadNamePrefix;
	}
}
