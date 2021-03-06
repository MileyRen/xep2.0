<%--
  Created by IntelliJ IDEA.
  User: qi_l
  Date: 2016/11/16
  Time: 15:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html>
<head>
	<base href="<%=basePath%>"/>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="https://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">
	<link rel="stylesheet" href="css/flow/modify2.css" type="text/css">

	<!-- XXX点信息 -->
	<title>Node Detail</title>
	<script type="text/javascript" src="js/jquery-latest.js"></script>
	<script type="text/javascript" src="js/GooFunc.js"></script>
	<script src="https://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<!-- 节点名字 -->
<form id='form' action="javascript:;" class="form-horizontal" role="form">
<!-- 	<p class="form-group"><label class="col-sm-2 control-label">节点名字</label><input class='nodeName detail-area form-control' required="required"></p> -->
	<table id='tools-select' border="1" style="border-color: gainsboro; border-collapse: collapse;width: 85%;margin: 0 0 0 7.5%;">
	<tr><td style="width: 30%; text-align: left;" class="col-sm-2 control-label">Type:</td><td style="width: 70%; padding: 10px;"><select class="tool-type detail-area select" required="required"></select></td></tr>
	<tr><td style="width: 30%; text-align: left;" class="col-sm-2 control-label">Tool:</td><td style="width: 70%; padding: 10px; "><select class="tool detail-area select" required="required"></select></td></tr>
	<tr><td style="width: 30%; text-align: left;" class="col-sm-2 control-label">Name:</td><td style="width: 70%; padding: 10px; "><input class="node-name detail-area" required="required"></td></tr>
	</table>
	<fieldset class='fieldset'>
		<legend data-count='0'>Parameter</legend>
		<table id='table' border="1" style="border-color: gainsboro; border-collapse: collapse;width: 85%;margin: 0 0 0 7.5%;"><tbody id='tbody'></tbody></table>
		<%--<p><label class='tool-id-count'>参数1</label><input><span><a href="javascript:void(0)">删除选项</a></span></p>--%>
	</fieldset>
	<p class="form-group btn-container"><input class="btn btn-info" type="button" id="confirm" value="Confirm"><input class="btn btn-info" type="button" id="cancel" value="Cancel"><input type="submit" class="submit" style="display: none;"></p>
</form>
<!-- 工具 -->

<script type="text/javascript" src="js/flow/modify2.js"></script>
</body>
</html>
