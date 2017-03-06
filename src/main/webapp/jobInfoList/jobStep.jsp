<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@page import="com.ssh.xep.entity.JobInfo"%>
<%@page import="com.xeq.file.domain.JobStep"%>
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
<script type="text/javascript">
	$(function() {
		$("[data-toggle='tooltip']").tooltip();
	});
</script>
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
							<%-- <li>
								<a>user:${jobStep.userId}</a>
							</li> --%>
							<li>
								<a>status:${jobStep.state}</a>
							</li>
						</ul>
						<ul class="nav navbar-nav navbar-right">
							<li>
								<a href="jobsList.action">BACK</a>
							</li>
						</ul>
					</div>
				</div>
				</nav>
				<div class="row clearfix">
					<div class="col-md-12 column">
						<table class="table table-condensed">
							<tr>
								<!-- <th>Id</th> -->
								<th>Name</th>
								<th>Begin Time</th>
								<th>End Time</th>
								<th>Status</th>
							</tr>
							<s:iterator value="#session.processInfo" status="JobStep">
								<tr class="${css}">
									<%-- <td>${id}</td> --%>
									<td>${name}</td>
									<td>${bgTime}</td>
									<td>${edTime}</td>
									<td><span class="label label-${label}">${state}</span></td>
								</tr>
							</s:iterator>
						</table>

						<%-- <div class="row">
							<div class="col-md-1" style="width:60px;height:60px" >
								<canvas id="start" width="60" height="60"> </canvas>
							</div>
							<s:iterator value="#session.processInfo" status="JobStep">
								<div class="col-md-1" >
									<img src="styleRen/arr.png" />
								</div>
								<div class="col-md-1" style="width:12%">
									<table class="table" border="1" >
										<tr>
											<td class="${css}" style="height: 68px">
											<a class="btn"
													data-toggle="tooltip" data-placement="bottom" title="${state}"> ${name}
											</a>
											</td>
										</tr>
									</table>
								</div>
							</s:iterator>
						</div> --%>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		var c = document.getElementById("start");
		var cxt = c.getContext("2d");
		cxt.fillStyle = "#444";
		cxt.beginPath();
		cxt.arc(35, 35, 15, 0, Math.PI * 2, true);
		cxt.closePath();
		cxt.fill();

		function arrow2(canId, ox, oy, x1, y1, x2, y2) {
			//参数说明 canvas的 id ，原点坐标  第一个端点的坐标，第二个端点的坐标  
			var sta = new Array(x1, y1);
			var end = new Array(x2, y2);

			var canvas = document.getElementById(canId);

			if (canvas == null)
				return false;
			var ctx = canvas.getContext('2d');
			//画线   
			ctx.beginPath();
			ctx.translate(ox, oy, 0); //坐标源点   
			ctx.moveTo(sta[0], sta[1]);
			ctx.lineTo(end[0], end[1]);
			ctx.fill();
			ctx.stroke();
			ctx.save();
			//画箭头   
			ctx.translate(end[0], end[1]);
			//我的箭头本垂直向下，算出直线偏离Y的角，然后旋转 ,rotate是顺时针旋转的，所以加个负号  
			var ang = (end[0] - sta[0]) / (end[1] - sta[1]);
			ang = Math.atan(ang);
			if (end[1] - sta[1] >= 0) {
				ctx.rotate(-ang);
			} else {
				ctx.rotate(Math.PI - ang);//加个180度，反过来  
			}
			ctx.lineTo(-5, -10);
			ctx.lineTo(0, -5);
			ctx.lineTo(5, -10);
			ctx.lineTo(0, 0);
			ctx.fill(); //箭头是个封闭图形  
			ctx.restore(); //恢复到堆的上一个状态，其实这里没什么用。  
			ctx.closePath();
		}
		window.onload = function() {
			arrow2("arr", 0, 0, 0, 40, 80, 40)
		}
	</script>

</body>
</html>