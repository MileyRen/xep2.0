<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.* , com.xeq.file.domain.ScriptState"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Insert title here</title>
</head>

<%! 
	String jsonStr = new String();
%>
<script type="text/javascript">
	function getJsonStrFromRequest(){
		var str = '<%=request.getAttribute("jsonStr")%>';
		document.getElementById("mySavedModel").value = str;
	}
	function show(){
		getJsonStrFromRequest(); 
		makeSVG();
	}
</script>
<body>
	<a onclick="show()" class="btn btn-primary ">show flow chart</a>
	<br>
	<!-- <h1>测试request对象</h1> -->
	<% jsonStr = (String)request.getAttribute("jsonStr");%>
	<%-- <%=jsonStr %>	
 --%>
	<br>
	<div id="sample">
	    <div style="display:none">
		<div style="width: 100%; white-space: nowrap;">
			<div id="myDiagramDiv" style="border: solid 1px black; height: 720px"></div>
		</div>
		<button id="SaveButton" onclick="save()">Save</button>
		<button onclick="load()">Load</button>
		Diagram Model saved in JSON format:
		<textarea id="mySavedModel" style="width: 100%; height: 300px;margin:auto;">
       </textarea>
		<button onclick="makeSVG()" >Render as SVG</button>
		</div>
		<div id="SVGArea" ></div>

	</div>
	
	<!-- 引入js库 -->
	<script src="js/go.js"></script>
	<!-- <script src="js/goSamples.js"></script> -->
	<script src="js/new.js"></script>
</body>
</html>