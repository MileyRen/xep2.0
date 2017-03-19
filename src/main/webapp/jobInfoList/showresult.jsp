<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
 <meta http-equiv="expires" content="0">   
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<script type="text/javascript">
	function getFromRequest(){
		var str = '<%=request.getAttribute("msg")%>' ;
		document.getElementById("myArea").value = str;
		alert(str);
	}
</script>
<body>
	<%
		String str = (String)session.getAttribute("message");
	%>
	<%=str %>
	${msg }
	<br>
	<button onclick="getFromRequest()">测试在js中获取request中的值</button><br>
	<textarea id="myArea"rows="10" cols="10"></textarea>
</body>
</html>