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

	<title>IO Mapping</title>
	<script type="text/javascript" src="js/jquery-latest.js"></script>
	<script type="text/javascript" src="js/GooFunc.js"></script>
	<script src="https://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<h2 title='You can now specify the relationship between the input and output, the left is the output of the previous node, while the right is the input of the latter node'>Modify the mapping between Output And Input</h2>
<table style='margin-bottom:20px;'><tbody><tr><td id='left-node-name' style=" text-align: center;
    width: 280px; font-size: 30px;
"></td><td style=" text-align: right;
    width: 400px; font-size: 30px; background: url(images/arrow-modify-input-and-output.png) 50% 0 no-repeat;
    background-size: 40% 100%;
"></td><td id='right-node-name' style="
    width: 280px; font-size: 30px; text-align: center;
"></td></tr></tbody></table>
<div id='output' style='float: left; border: 1px red solid; width: 280px; height: 500px; border-radius: 5px; box-shadow: 0 0 3px grey;'>
	<!-- <p class='item'>This is a Output Line</p>
	<p class='item'>This is a Output Line</p>
	<p class='item'>This is a Output Line</p>
	<p class='item'>This is a Output Line</p>-->
</div>
<svg id='paint-area' style='float: left;' width="400px" height="500px" version="1.1" xmlns="http://www.w3.org/2000/svg">
	<defs>
	<marker id="arrow"
	        markerUnits="strokeWidth"
	        markerWidth="5"
	        markerHeight="5"
	        viewBox="0 0 12 12"
	        refX="6"
	        refY="6"
	        orient="auto">
		<path d="M2,2 L10,6 L2,10 L6,6 L2,2" style="fill: #000000;" />
	</marker>
	</defs>

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
	<input type='button' value='Confirm' class='btn btn-info' id='confirm'><input type='button' class='btn btn-info' value='Cancel' id='cancel'>
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
