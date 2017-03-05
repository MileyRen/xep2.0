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
	<title>${title }</title>
	<link rel="stylesheet" href="css/job/common.css" type="text/css">
	<link rel="stylesheet" href="css/accordion/accordion.css" type="text/css">
	<link rel="stylesheet" href="https://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">
	<link rel="stylesheet" href="css/job/modify.css" type="text/css">

	<script type="text/javascript" src="js/GooFunc.js"></script>
	<script type="text/javascript" src="js/jquery-latest.js"></script>
	<script type="text/javascript" src="js/modernizr.custom.accordion.js"></script>
	<script src="https://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<%@include file="/WEB-INF/content/navigator.jsp"%>
<div style="margin: 0 150px; position: relative;">

<c:choose>
<c:when test="${fn:startsWith(title, '创建') }">
<p><label style='margin: 15px 30px 15px 15px;'>选择流程:</label><select class='select'>
<option selected value=''></option>
<c:forEach items="${flowBasicInfos }" var='item'>
<option value="${item.id }">${item.name }</option>
</c:forEach>
</select></p>
</c:when>
</c:choose>

<form id='form' class="form-horizontal" role="form">
<!-- 
<section id="show-area" class="ac-container">
	<%--<div class="ac-div">--%>
	<%--<input id="ac-1" name="accordion-1" class="ac-input" type="checkbox"/>--%>
	<%--<label for="ac-1" class="ac-label">About us</label>--%>
	<%--<article class="ac-open">--%>
	<%--<p class="ac-p">Well, the way they make shows is, they make one show. That show's called a pilot. Then they show that--%>
	<%--show to the people who make shows, and on the strength of that one show they decide if they're going--%>
	<%--to make more shows.</p>--%>
	<%--</article>--%>
	<%--</div>--%>
</section>
 -->
<section id="show-area">
</section>
<p class="btn-container">
<input id="confirm" type="button" class="btn btn-primary" value="确定"><input id="cancel" type="button" class="btn btn-primary" value="取消">
<input id="run" value="执行" class="btn btn-primary" type="button"><input id='submit' type="submit" style="display:none;">
</p>
</form></div>

<c:choose>
<c:when test="${!empty flowBasicInfo }">
<script>
	<c:choose>
	<c:when test="${fn:startsWith(title, '创建') }">
	var id = 0;
	var flowId = "${flowBasicInfo.id }";
	var flowName = undefined;
	var userId = "${flowBasicInfo.userId }";
	var xmlStr = "${flowBasicInfo.flow }";
	</c:when>
	<c:otherwise>
	var id = "${info.id }";
	var flowId = "${info.flowBasicInfoId }";
	var flowName = undefined;
	var userId = "${info.userId }";
	var xmlStr = "${info.bpmn }";
	</c:otherwise>
	</c:choose>

	xmlStr = xmlStr.replace(/\+/g, '%20');
	xmlStr = decodeURIComponent(xmlStr);
	var sectionId = 'show-area';
</script>
<script src="js/job/modify.js"></script>
</c:when>
</c:choose>
<script type="text/javascript">
	function onChange(e) {
		window.open('job/create.action?id='+e.target.selectedOptions[0].value, '_self');
	}
	$(document).ready(function() {
		$('.select').on('change', onChange);
	});
</script>
</body>
</html>
