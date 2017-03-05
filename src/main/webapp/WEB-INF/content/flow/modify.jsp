<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.ssh.xep.entity.*" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

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
	<link rel="stylesheet" type="text/css" href="css/flow/modify.css">

	<script type="text/javascript" src="js/jquery-latest.js"></script>
	<script type="text/javascript" src="js/GooFunc.js"></script>
	<script type="text/javascript" src="js/my.js"></script>
	<script type="text/javascript" src="js/flow.js"></script>
	<title>${create }流程</title>
</head>
<body>

<div id="flowTip">
	<p>id is ${info.id }</p>
	<p>流程名字：${info.name }</p>
	<p>可用工具数量：${fn:length(tools) }</p>
	<p>工具种类：${fn:length(toolTypes) }</p>
	<p>组别种类：${fn:length(groups) }</p>
</div>

<div id='flow'>
</div>

<div id='flowInfo' style="display: none;">${info.flow }</div>
<script type="text/javascript">
	var id = "${info.id}";
	try { parseInt(id); } catch (e) { id=0; }
	var tools = [];
	var tool;
	<%
		List<Tools> tools = (List<Tools>) request.getAttribute("tools");
		for(Tools t : tools) {
			%>tool = {}<%
			%>;tool.id = '<% out.print(t.getId());
			%>';tool.toolName = '<% out.print(t.getToolName());
			%>';tool.toolId = '<% out.print(t.getId());
			%>';tool.toolTypeId = '<% out.print(t.getToolTypeId());
			%>';tool.userId = '<% out.print(t.getUserId());
			%>';tool.xmlPath = decodeURIComponent('<% out.print(t.getXmlPath());
			%>'.replace(/\+/g, ' '));tool.description = '<% out.print(t.getDescription());
			%>';tool.addedTime = '<% out.print(t.getAddedTime());
			%>';tool.isCreatedByUser = '<% out.print(t.getIsShared());
			%>';tool.color = '<% out.print(t.getToolTypeId());
			%>';tools.push(tool);<%
		}
	%>

	var toolTypes = [];
	var toolType;
	<%
	List<ToolTypes> toolTypes = (List<ToolTypes>) request.getAttribute("toolTypes");
	for(ToolTypes t : toolTypes) {
			%>toolType = {}<%
			%>;toolType.id = '<% out.print(t.getId());
			%>';toolType.addedUserId = '<% out.print(t.getAddedUserId());
			%>';toolType.typeName = '<% out.print(t.getTypeName());
			%>';toolType.showColor = '<% out.print(t.getShowColor());
			%>';toolType.description = '<% out.print(t.getDescription());
			%>';toolTypes.push(toolType);<%
		}
	%>

	var groups = [];
	var group;
	<%
	List<FlowGroupInfo> groups = (List<FlowGroupInfo>) request.getAttribute("groups");
	for(FlowGroupInfo g : groups) {
			%>group = {}<%
			%>;group.id = '<% out.print(g.getId());
			%>';group.addedUserId = '<% out.print(g.getName());
			%>';group.typeName = '<% out.print(g.getIntro());
			%>';groups.push(group);<%
		}
	%>
	for(var i=0; i<tools.length; i++) {
		for(var j=0; j<toolTypes.length; j++) {
			if(tools[i].color == toolTypes[j].id) {
				tools[i].color = toolTypes[j].showColor;
				break;
			}
		}
	}

	var flow = document.getElementById('flowInfo').innerHTML.replace(/\+/g, '%20');
	flow = decodeURIComponent(flow);
	var instance = new Flow('flow', flow, true, '${info.name }');
	instance.setToolTipInfo('flowTip');
	instance.onSave = function() {
		// 获取数据
		var xmlStr = encodeURIComponent(instance.asStr()).replace(/%20/g, '+');

		window.removeEventListener('beforeunload', closeAction);

		// 更新父窗口值
		window.opener.save(instance.getNodeCount(), xmlStr);

		// 关闭本窗口
		window.close();
	};

	// 至少保证将按钮恢复
	window.addEventListener('beforeunload', closeAction);

	function closeAction() {
		resetPWindow();
	}
	function resetPWindow() {
		var pWindow = window.opener;
		pWindow.$get('work-area').style.display = 'none';

		pWindow.$get('add').disabled = false;
		pWindow.$get('delete').disabled = false;
		pWindow.linkDisabled = false;
	}
</script>
</body>
</html>
