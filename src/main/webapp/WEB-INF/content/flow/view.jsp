<!DocType html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.TimeZone" %>
<%@ page import="com.gene.utils.User" %>
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

<html>
<head>
	<base href="<%=basePath%>"/>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>流程基本信息</title>
	<link rel="stylesheet" href="css/flow/common.css" type="text/css">
	<link rel="stylesheet" href="css/flow/view.css" type="text/css">
	<link rel="stylesheet" href="css/jqpagination.css" type="text/css">
	<link rel="stylesheet" href="https://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">
	<link href="http://runningls.com/demos/2016/daily/datepicker/bootstrap-datepicker3.css" rel="stylesheet">

	<script type="text/javascript" src="js/GooFunc.js"></script>
	<script type="text/javascript" src="js/jquery-latest.js"></script>
	<script type="text/javascript" src="js/jquery.jqpagination.min.js"></script>
	<script src="https://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script src="http://runningls.com/demos/2016/daily/datepicker/bootstrap-datepicker.min.js"></script>  
	<script src="http://runningls.com/demos/2016/daily/datepicker/bootstrap-datepicker.zh-CN.min.js"></script>
</head>
<body>
<%@include file="/WEB-INF/content/navigator.jsp"%>
<div style="margin: 0 150px; position: relative;">
<h2>&nbsp;</h2>
<form id="filterDateForm"><p>Date:&nbsp;<input readonly type="text" class="form-control" name="startDate" id="startDate" required>to:&nbsp;<input readonly type="text" class="form-control" name="endDate" id="endDate" required>Privilege:&nbsp;
<select class="group-filter">
<option selected value=""></option>
<option value="0">私有</option>
<option value="1">共享</option>
<option value="2">系统</option>
</select>
<input type="button" id="filterDate" class="btn btn-primary" value="筛选"></p><input type="submit" style="display:none;" class="submit"></form>
<table id="show-area" class="table table-hover view-table">
	<thead>
	<tr>
		<th class="delete"></th>
		<th class="name">名字</th>
		<th class="user-id">用户</th>
		<th class="privilege">权限</th>
		<th class="node-num">节点数量</th>
		<th class="create-date">修改日期</th>
		<th class="detail">修改</th>
		<th class="create-job">删除</th>
	</tr>
	</thead>
	<tbody id="flowList">
	<c:forEach items="${infoJoins }" var="info">
		<tr><!-- <a href="javascript:;" onclick="javascript:modifyColumn(this, ${info.id}, '${info.name}', ${info.auth}, ${info.groupId});"> -->
			<td class="delete"><input type="checkbox" value="${info.id}"></td>
			<td class="name">${info.name }</td>
			<td class="user-id">${info.userName }</td>
			<td class="privilege"><c:choose><c:when
					test="${info.auth==0 }">私有</c:when><c:when
					test="${info.auth==1 }">共享</c:when><c:otherwise>系统</c:otherwise></c:choose></td>
			<td class="node-num">${info.flowNum }</td>
			<td class="create-date">${info.createDate }</td>
			<td class="detail">
					<input type="button" value="修改" class="btn btn-primary view-btn-in-table" onclick="modifyColumn(this, ${info.id}, '${info.name}', ${info.auth})">
			</td>
			<td class="create-job"><input type="button" value="删除" class="btn btn-primary view-btn-in-table" onclick="deleteOneColumn('${info.id}')"></td>
		</tr>
	</c:forEach>
	</tbody>
</table>
<div id="page">
	<input type="button" id="add" class="btn btn-primary" value="新增"><input type="button" id="delete" class="btn btn-primary" value="删除">
	<div class="pagination">
		<a href="#" class="first" data-action="first">&laquo;</a>
		<a href="#" class="previous" data-action="previous">&lsaquo;</a>
		<input type="text" readonly="readonly" data-max-page="40"/>
		<a href="#" class="next" data-action="next">&rsaquo;</a>
		<a href="#" class="last" data-action="last">&raquo;</a>
	</div>
</div>

<div id="work-area" style="display: none;">
	<table class='table active-table view-table'>
		<tr>
			<td class="delete"><input type="checkbox"></td>
			<td class="name">no name</td>
			<td class="user-id"><%=userName %>
			</td>
			<td class="privilege">私有</td>
			<td class="node-num">0</td>
			<td class="create-date"><%
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
				out.print(sdf.format(new Date()));
			%></td>
			<td class="detail"><a
					target="_self"
					href="javascript:void(0)"></a></td>
			<td class="create-job"><a
					target="_self"
					href="javascript:void(0)"></a></td>
		</tr>
	</table>
	<div class="worker">
		<input type="text" class="name form-control" required>
		<select class="privilege">
			<option selected value="0">私有</option>
			<option value="1">共享</option>
			<% if (isAdmin) {
				out.println("<option value='2'>系统</option>");
			} %>
		</select>
		<input type="button" value="确定" class="btn btn-primary" id="confirm"><input type="button" value="取消" class="btn btn-primary" id="cancel">
	</div>
</div>
</div>
<script type="text/javascript">
var page = window.location.search.substring(1).split('&');
var maxPage = parseInt((${maxPage }-1) / 10) + 1;

function DatePicker(beginSelector,endSelector){
    // 仅选择日期
    $(beginSelector).datepicker(
    {
    	language:  "zh-CN",
    	autoclose: true,
    	startView: 0,
    	format: "yyyy-mm-dd",
    	clearBtn:true,
    	todayBtn:false,
    	endDate:new Date()
    }).on('changeDate', function(ev){
    	if(ev.date){
    		$(endSelector).datepicker('setStartDate', new Date(ev.date.valueOf()))
    	}else{
    		$(endSelector).datepicker('setStartDate',null);
    	}
    });

    $(endSelector).datepicker(
    {
    	language:  "zh-CN",
    	autoclose: true,
    	startView:0,
    	format: "yyyy-mm-dd",
    	clearBtn:true,
    	todayBtn:false,
    	endDate:new Date()
    }).on('changeDate', function(ev){  
    	if(ev.date){
    		$(beginSelector).datepicker('setEndDate', new Date(ev.date.valueOf()))
    	}else{
    		$(beginSelector).datepicker('setEndDate',new Date());
    	} 
    });
}

DatePicker("#startDate","#endDate");
</script>
<script src="js/flow/view.js">
</script>
</body>
</html>
