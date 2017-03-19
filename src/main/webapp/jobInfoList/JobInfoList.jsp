<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@page import="com.ssh.xep.entity.JobInfo"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.xeq.file.domain.JobCss"%>
<%@page import="com.gene.utils.User"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<head>
<base href="<%=basePath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- 新 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="http://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<title>JobList</title>
</head>
<body>
	<%@include file="/WEB-INF/content/navigator.jsp"%>
	<div class="container">
		<div class="page-header">
			<div class="row clearfix">
				<div class="col-md-12 column">
					<%@include file="jobSearch.jsp"%>
				</div>
			</div>
		</div>
		<div id="show">
			<table class="table table-hover" id="jobList">
				<tr>
					<TH>Name</TH>
					<th>Begin Time</th>
					<th>End Time</th>
					<th>UserName</th>
					<th>Status</th>
					<th>Action</th>
				</tr>
				<s:iterator value="#session.jcList" status="JobCss">
					<tr>
						<td>${name}</td>
						<td>${bgTime}</td>
						<td>${edTime}</td>
						<td>${userName}</td>
						<td>
							<span class="label label-${label}">${state}</span>
						</td>
						<td>
							<a type="button" class="btn btn-primary btn-xs" onclick="javascript:window.location.href='jobInfo.action?id=${id}'"> <span class="glyphicon glyphicon-search"></span> Info
							</a>
						</td>
					</tr>
				</s:iterator>
			</table>
		</div>
		<!-- 分页效果开始 -->

		<div>
			<ul class="pagination">
				<li><a href="jobsList.action?jobstate=${jobstate}&jobpageSource.currentPage=1">&laquo;</a></li>
				<li><a href="jobsList.action?jobstate=${jobstate}&jobpageSource.currentPage=${jobpagesource.currentPage-1 }">previous</a></li>
				<li><a>[${jobpagesource.currentPage} of ${jobpagesource.totalPages }] [total:${jobpagesource.totalRows}]</a></li>
				<li><a href="jobsList.action?jobstate=${jobstate}&jobpageSource.currentPage=${jobpagesource.currentPage+1 }">next</a></li>
				<li><a href="jobsList.action?jobstate=${jobstate}&jobpageSource.currentPage=${jobpagesource.totalPages}">&raquo;</a></li>
			</ul>
		</div>
		<!--分页效果结束 -->
	</div>
</body>
</html>