<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@page import="com.ssh.xep.entity.JobInfo"%>
<%@page import="com.xeq.file.domain.JobStep"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- 新 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="http://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<base href="<%=basePath%>" />
<link rel="stylesheet" type="text/css" href="css/default.css">
<link rel="stylesheet" type="text/css" href="styleRen/my.css">

<script type="text/javascript" src="js/jquery-latest.js"></script>
<script type="text/javascript" src="styleRen/GooFunc.js"></script>
<script type="text/javascript" src="styleRen/my.js"></script>
<script type="text/javascript" src="styleRen/flow_sel.js"></script>

<style>
#flowTip {
	display: none;
	width: 200px;
	height: 200px;
	border-radius: 4px;
	box-shadow: 0 0 1px 0;
}
</style>
<title>JobStep</title>
</head>
<body>
	<%@include file="/WEB-INF/content/navigator.jsp"%>
	<div class="container">
		<div class="row clearfix">
			<div class="col-md-12 column">
				<nav class="navbar navbar-default" role="navigation">
				<div class="container-fluid">
					<div class="navbar-header">
						<a class="navbar-brand" href="#">name:${jobStep.name}</a>
					</div>
					<div>
						<ul class="nav navbar-nav">
							<li><a>status:${jobStep.state}</a></li>
						</ul>
						<ul class="nav navbar-nav navbar-right">
							<li><a href="jobsList.action?jobstate=${jobstate}">BACK</a></li>
						</ul>
					</div>
				</div>
				</nav>
				<div class="row clearfix">
					<div class="col-md-12 column">
						<table class="table table-condensed">
							<tr>
								<th>Name</th>
								<th>Begin Time</th>
								<th>End Time</th>
								<th>Status</th>
							</tr>
							<s:iterator value="#session.processInfo" status="JobStep">
								<tr class="${css}">
									<td>${name}</td>
									<td>${bgTime}</td>
									<td>${edTime}</td>
									<td>
										<span class="label label-${label}">${state}</span>
									</td>
								</tr>
							</s:iterator>
						</table>
					</div>
				</div>
			</div>
		</div>
        <%-- <div><%@include file="draw.jsp"%></div> --%>
		<div><a href="jobInfoList/chart.jsp" class="btn btn-primary" target="_blank">show flow chart</a></div>
	</div>
</body>
</html>