package com.xeq.file.dao.impl;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import com.xeq.file.domain.ScriptState;

public class PathFormat {
	public static String strEnd(String str) {
		if (!str.endsWith(File.separator)) {
			str += File.separator;
		}
		return str;
	}

	public static List<ScriptState> getDraw(String str) {
		List<ScriptState> ret = new ArrayList<ScriptState>();
		try {
			str = URLDecoder.decode(str, "utf-8");
			@SuppressWarnings("deprecation")
			SAXBuilder saxBuilder = new SAXBuilder(false);
			StringReader reader = new StringReader(str);
			Document document = null;
			try {
				document = saxBuilder.build(reader);
			} catch (JDOMException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			Element root = document.getRootElement();
			List<Element> elements = root.getChildren();
			for (Element element : elements) {
				ScriptState scriptState = new ScriptState();
				scriptState.setStepName(element.getAttributeValue("name"));
				scriptState.setState(element.getAttributeValue("state"));
				ret.add(scriptState);// 添加到返回列表中
			}
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		return ret;
	}
}
