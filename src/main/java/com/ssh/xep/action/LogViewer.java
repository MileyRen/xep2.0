package com.ssh.xep.action;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

@Deprecated
@Namespace("/")
@Results({ @Result(name = ActionSupport.ERROR, location = "/WEB-INF/error.jsp") })
public class LogViewer extends ActionSupport implements Preparable {
	private static final long serialVersionUID = -1310951828876142446L;

	public void prepare() throws Exception {
	}

	private String bgDate;
	private String edDate;
	private int limit;
	private List<Map<String, String>> res;

	public String getBgDate() {
		return bgDate;
	}

	public void setBgDate(String bgDate) {
		this.bgDate = bgDate;
	}

	public String getEdDate() {
		return edDate;
	}

	public void setEdDate(String edDate) {
		this.edDate = edDate;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public List<Map<String, String>> getRes() {
		return res;
	}

	public void setRes(List<Map<String, String>> res) {
		this.res = res;
	}

	// 不再影响其它类，就在这里完成全部功能
	@Action(value = "ll", results = {
			@Result(name = ActionSupport.SUCCESS, location = "/WEB-INF/content/logviewer.jsp") })
	public String execute() throws ClassNotFoundException, SQLException {
		if (limit < 1) {
			limit = 10;
		}
		if (bgDate == null) {
			bgDate = "2016-01-01 00:00:00";
		}
		if (edDate == null) {
			edDate = "2017-01-01 00:00:00";
		}
		Class.forName("com.mysql.jdbc.Driver");
		String url = "jdbc:mysql://localhost:3306/xep?user=root&password=123456&useUnicode=true&characterEncoding=utf-8";
		Connection conn = DriverManager.getConnection(url);
		PreparedStatement ps = conn
				.prepareStatement("SELECT * FROM devLog WHERE time BETWEEN ? AND ? ORDER BY id DESC limit " + limit);
		ps.setString(1, bgDate);
		ps.setString(2, edDate);
		ResultSet rs = ps.executeQuery();
		res = new ArrayList<Map<String, String>>();
		while (rs.next()) {
			Map<String, String> m = new HashMap<String, String>();
			m.put("id", rs.getString(1));
			m.put("log", rs.getString("log"));
			m.put("time", rs.getString("time"));
			res.add(m);
		}
		rs.close();
		ps.close();
		conn.close();

		return SUCCESS;
	}
}
