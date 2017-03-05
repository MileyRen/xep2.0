<%@ page import="com.gene.utils.User" %><%--
  Created by IntelliJ IDEA.
  User: qi_l
  Date: 2016/11/29
  Time: 13:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";

	User user = (User) session.getAttribute("user");
	Boolean isAdmin = user.getRoleId() == 1;
	Integer userId = user.getId();
	String userName = user.getUserName();
%>
<!DocType html>
<html>
<head>
	<base href="<%=basePath%>"/>
	<title>选择文件</title>
	<link rel="stylesheet" href="css/job/common.css" type="text/css">
	<link rel="stylesheet" href="css/job/modify2.css" type="text/css">
	<link rel="stylesheet" type="text/css" href="css/SimpleTree/SimpleTree.css"/>

	<script type="text/javascript" src="js/GooFunc.js"></script>
	<script type="text/javascript" src="js/jquery-latest.js"></script>
	<script type="text/javascript" src="js/SimpleTree.js"></script>
</head>
<body>
<div class="st_tree" id="tree">
</div>
<!--<input id="confirm" value="确定" type="button">
<input id="cancel" value="取消" type="button">-->
<script src="js/job/selectFlow.js"></script>
</body>
</html>
