<%@page import="java.io.File"%>
<%@ page import="com.gene.utils.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录</title>
</head>
<body>
	<%
		User user = new User();
		String path = "d" + File.separator + "xeptest";
		user.setFolder(path);
		user.setId(1);
		user.setUserName("root");
		user.setRoleId(1);
		session.setAttribute("user", user);
	%>
</body>
</html>
