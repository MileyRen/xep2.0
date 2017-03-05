<%@ page import="com.gene.utils.User" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>未开始作业基本信息</title>
	<link rel="stylesheet" href="css/job/common.css" type="text/css">
	<link rel="stylesheet" href="css/job/view.css" type="text/css">
	<link rel="stylesheet" href="css/jqpagination.css" type="text/css">
	<link rel="stylesheet" href="https://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">

	<script type="text/javascript" src="js/GooFunc.js"></script>
	<script type="text/javascript" src="js/jquery-latest.js"></script>
	<script type="text/javascript" src="js/jquery.jqpagination.min.js"></script>
	<script src="https://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<%@include file="/WEB-INF/content/navigator.jsp"%>
<div style="margin: 0 150px; position: relative;">
<h2>用户全部未开始作业 <input type="button" id="delete" class="btn btn-primary" value="删除"></h2>
<table id="show-area" class="table table-hover view-table">
	<thead>
	<tr>
		<th class="delete"></th>
		<th class="name">名字</th>
		<th class="user">用户</th>
		<th class="flow-name">流程名字</th>
		<th class="detail">修改</th>
		<th class="start">启动</th>
	</tr>
	</thead>
	<tbody id="flowList">
	<c:forEach items="${jfuInfos }" var="info">
		<tr>
			<td class="delete"><input type="checkbox" value="${info.id}"></td>
			<td class="name">${info.id }</td>
			<td class="user">${info.userName }</td>
			<td class="flow-name">${info.flowBasicInfoName }</td>
			<td class="detail"><a target="_blank" href="job/modify.action?id=${info.id}">修改</a>
			<td class="detail"><a target="_blank" href="job/start.action?jobId=${info.id}">启动</a>
			</td>
		</tr>
	</c:forEach>
	</tbody>
</table>

<div id="page">
	<div class="pagination">
		<a href="#" class="first" data-action="first">&laquo;</a>
		<a href="#" class="previous" data-action="previous">&lsaquo;</a>
		<input type="text" readonly="readonly" data-max-page="40"/>
		<a href="#" class="next" data-action="next">&rsaquo;</a>
		<a href="#" class="last" data-action="last">&raquo;</a>
	</div>
</div>
</div>

<script type="text/javascript">
	var page = window.location.search.substring(1).split('&');
	var maxPage = ${maxPage };
</script>
<script type="text/javascript" src="js/job/view.js"></script>
</body>
</html>
