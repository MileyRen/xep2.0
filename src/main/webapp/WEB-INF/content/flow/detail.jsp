<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" %>
<!-- 弃用 -->
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DocType html>
<html>
<head>
	<base href="<%=basePath%>"/>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

	<link rel="stylesheet" type="text/css" href="css/default.css">
	<link rel="stylesheet" type="text/css" href="css/my.css">

	<script type="text/javascript" src="js/jquery-latest.js"></script>
	<script type="text/javascript" src="js/GooFunc.js"></script>
	<script type="text/javascript" src="js/my.js"></script>
	<script type="text/javascript" src="js/flow.js"></script>

	<style>
		#flowTip {
			display: none;
			width: 200px;
			height: 200px;
			border-radius: 4px;
			box-shadow: 0 0 1px 0;
		}
	</style>
	<title>详细信息</title>
</head>
<body>
<div id="flowTip">
	<p>名字：${info.name }</p>
	<p>用户ID：${info.userId }</p>
	<p>节点数量：${info.flowNum }</p>
	<p>详细信息：</p>
</div>
<div id='flowInfo' style="display: none;">${info.flow }</div>

<div id="flow"></div>
<script>
	var flow = document.getElementById('flowInfo').innerHTML.replace(/\+/g, '%20');
	flow = decodeURIComponent(flow);
	var instance = new Flow('flow', flow, false, '${info.name }');
	instance.setToolTipInfo('flowTip');
</script>
</body>
</html>
