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

	<!-- XXX点信息 -->
	<title>Information</title>
	<script type="text/javascript" src="js/jquery-latest.js"></script>
	<script type="text/javascript" src="js/GooFunc.js"></script>
	<script src="https://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>

	<style>
		.view-table {
			width: 1000px;
		}
		.no {
			width: 25%;
		}
		.node {
			width: 37.5%;
			white-space: nowrap;
		}
		.name {
			width: 37.5%;
			white-space: nowrap;
		}
		
		body {
			padding: 15px;
		}
	</style>
</head>
<body>
<table id='show' class='table table-hover view-table'>
	<thead><tr>
	<th class='no'>#</th>
	<th class='node'>node</th>
	<th class='name'>name</th>
	</tr></thead>
	<tbody id='show-body'>
	</tbody>
</table>

<script>
window.addEventListener('beforeunload', function() {
	var pWin = window.opener;
	pWin.instance.getInstance().$editable = true;
});

$(document).ready(function() {
	var pWin = window.opener;
	var instance = pWin.instance;
	var nodeData = pWin.instance.getInstance().$nodeData;
	var count = 1;
	for(var i in nodeData) {
		if(i == '_1' || i == '_3') {
			continue;
		}
		var outputs = $(instance.getNodeInformation(i).xml).find('output');
		for(var j=0; j<outputs.length; j++) {
			if(!$(outputs[j]).attr('value')) {
				$('#show-body').append('<tr><td class="no">'+(count++)
						+'</td><td class="node">'+instance.getNodeInformation(i).toolName
						+'</td><td class="name">'+$(outputs[j]).attr('label')+'</td></tr>');
			}
		}
	}
});
</script>
</body>
</html>
