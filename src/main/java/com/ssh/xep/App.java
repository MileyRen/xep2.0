package com.ssh.xep;

import com.ssh.xep.util.process.Process;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws Exception {
		Process p = new Process();
		p.create("java", new String[] {"-jar", "D:\\Users\\qi_l\\Desktop\\app.jar"});
	}
}
