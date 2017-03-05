package com.xeq.file.dao.impl;

import java.io.File;

public class PathFormat {
	public static String strEnd(String str) {
		if (!str.endsWith(File.separator)) {
			str += File.separator;
		}
		return str;
	}
}
