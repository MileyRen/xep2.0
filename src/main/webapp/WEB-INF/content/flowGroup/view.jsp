<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html>
<html>
<head>
	<base href="<%=basePath%>"/>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>流程组基本信息</title>

	<link rel="stylesheet" href="css/jqpagination.css" type="text/css">
	<link rel="stylesheet" href="css/flow-group/common.css" type="text/css">
	<link rel="stylesheet" href="css/flow-group/view.css" type="text/css">
	<link rel="stylesheet" href="https://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">

	<script type="text/javascript" src="js/GooFunc.js"></script>
	<script type="text/javascript" src="js/jquery-latest.js"></script>
	<script type="text/javascript" src="js/jquery.jqpagination.min.js"></script>
	<script src="https://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<%@include file="/WEB-INF/content/navigator.jsp"%>
<div style="margin: 0 150px; position: relative;">
<h2>全部组 <input type="button" id="add" class="btn btn-primary" value="新增"><input type="button" id="delete" class="btn btn-primary" value="删除"></h2>
<table id="show-area" class="table table-hover view-table">
	<thead>
	<tr>
		<th class="delete"></th>
		<th class="id">ID</th>
		<th class="name">名字</th>
		<th class="intro">介绍</th>
		<th class="intro"></th>
	</tr>
	</thead>
	<tbody id="flowList">
	<c:forEach items="${groups }" var="group">
		<tr>
			<td class="delete"><input type="checkbox" value="${group.id }"></td>
			<td class="id">${group.id }</td>
			<td class="name">${group.name }</td>
			<td class="intro">${group.intro }</td>
			<td class="modify"><a
					href="javascript:openWindow(${group.id })">详细/修改</a>
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
<script>
	var page = window.location.search.substring(1).split('&');
	var maxPage = parseInt((${maxPage }-1) / 10) + 1;

	function openWindow(groupId) {
		document.getElementById('add').disabled = true;
		document.getElementById('delete').disabled = true;
		var w = undefined;
		if (groupId) {
			w = window.open('flowGroup/modify.action?groupId=' + groupId, '修改窗口', 'top=100, left=300,width=500,height=350,resizable=no,scrollbars=yes,status=no');
		} else {
			w = window.open('flowGroup/modify.action', '新建窗口', 'top=100, left=300,width=500,height=350,resizable=no,scrollbars=yes,status=no');
		}
		w.addEventListener('beforeunload', function () {
			setTimeout(function () {
				window.location.reload();
			}, 0);
		});
	}
</script>
<script type="text/javascript" src="js/flowGroup/view.js"></script>
</body>
</html>
