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
	<link rel="stylesheet" href="css/flow/modifyInputOutput.css">

	<title>修改输入输出映射</title>
	<script type="text/javascript" src="js/jquery-latest.js"></script>
	<script type="text/javascript" src="js/GooFunc.js"></script>
	<script src="https://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<h2>修改输入输出映射</h2>
<p>现在可以指定输入到输出之间的关系，左边是前一个节点的输出信息，右边是后一个节点的输入信息</p>
<div id='output' style='float: left; border: 1px red solid; width: 280px; height: 500px; border-radius: 5px; box-shadow: 0 0 3px grey;'>
	<!-- <p class='item'>This is a Output Line</p>
	<p class='item'>This is a Output Line</p>
	<p class='item'>This is a Output Line</p>
	<p class='item'>This is a Output Line</p>-->
</div>
<svg id='paint-area' style='float: left;' width="400px" height="500px" version="1.1" xmlns="http://www.w3.org/2000/svg">

	<!--<circle class="radio" cx="25" cy="20" r="10" stroke="grey" stroke-width="5" fill="red"/>
	<circle class="radio" cx="25" cy="60" r="10" stroke="grey" stroke-width="5" fill="red"/>
	<circle class="radio" cx="25" cy="100" r="10" stroke="grey" stroke-width="5" fill="red"/>
	<circle class="radio" cx="25" cy="140" r="10" stroke="grey" stroke-width="5" fill="red"/>
	<circle class="radio" cx="366" cy="20" r="10" stroke="grey" stroke-width="5" fill="red"/>
	<circle class="radio" cx="366" cy="60" r="10" stroke="grey" stroke-width="5" fill="red"/>
	<circle class="radio" cx="366" cy="100" r="10" stroke="grey" stroke-width="5" fill="red"/>
	<circle class="radio" cx="366" cy="140" r="10" stroke="grey" stroke-width="5" fill="red"/>
	<line id="liner" class='line' x1="0" y1="0" x2="300" y2="300" style="stroke:rgb(99,99,99);stroke-width:4"/>-->

</svg>
<div id='input' style='float: left; border: 1px red solid; width: 280px; height: 500px; border-radius: 5px; box-shadow: 0 0 3px grey;'>
	<!--<p class='item'>This is a Input Line</p>
	<p class='item'>This is a Input Line</p>
	<p class='item'>This is a Input Line</p>
	<p class='item'>This is a Input Line</p>-->
</div>
<div style='position: absolute; bottom: 10px;'>
	<input type='button' value='确定' class='btn btn-primary' id='confirm'><input type='button' class='btn btn-primary' value='取消' id='cancel'>
</div>
<script type='text/javascript'>
var instance = undefined;
var from = undefined;
var to = undefined;
var fromId = undefined;
var toId = undefined;
</script>
<script type='text/javascript' src='js/flow/modifyInputOutput.js'></script>
</body>
</html>
