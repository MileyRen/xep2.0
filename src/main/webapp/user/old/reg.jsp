<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'reg.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
  	<script>
  		var q = window.location.search;
  		q = q || "?";
  		q = q.substring(1);
  		q = q.split('&');
  		for(var i in q) {
  			if(q[i].startsWith('msg=')) {
  				alert(decodeURIComponent(q[i].substring(4)));
  				break;
  			}
  		}
  	</script>
    <form action="${pageContext.request.contextPath}/user/usermanage?op=adduser" method="post">
    	<table border="1" >
    		<tr>
    			<td>姓名：</td>
    			<td>
    				<input type="text" name="name"/>
    			</td>
    		</tr>
    		<tr>
    			<td>密码:</td>
    			<td>
    				<input type="password" name="pwd"/>
    			</td>
    		</tr>
    		<tr>
    			<td>确认密码:</td>
    			<td>
    				<input type="password" name="pwdcfm"/>
    			</td>
    		</tr>
    		<tr>
    			<td>电子邮件:</td>
    			<td>
    				<input type="text" name="email"/>
    			</td>
    		</tr>
    		
    		
    		<tr>
    			<td colspan="2">
    				<input type="submit" value="提交"/>
    			</td>
    		</tr>
    	</table>
    </form>
  </body>
</html>
