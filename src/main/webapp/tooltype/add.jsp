<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'add.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<%@include file="/WEB-INF/headinclude.jsp"%>

  <s:head/>
  <style type="text/css">
    .main{
    margin-top: 50px;
    margin-left: 100px;
    margin-right: 100px;
    }
    </style>
  </head>
  
  <body>
  <div> <%@include file="/WEB-INF/content/navigator.jsp"%></div>
  <div class="main">
    <form action="${pageContext.request.contextPath}/tooltype/pack.action" method="post">
    	<table class="table table-hover" >
    		<tr>
    			<td>typename：</td>
    			<td>
    				<input type="text" name="typeName"/>
    			</td>
    		</tr>
    		<tr>
    			<td>color:</td>
    			<td>
    				<input type="color" name="showColor"/>
    			</td>
    		</tr>
    		
    		<tr>
    			<td>describtion:</td>
    			<td>
    				<textarea rows="3" cols="100" name="describtion"></textarea>
    			</td>
    		</tr>
    		
    		
    		<tr>
    			<td colspan="2">
    				<input type="submit" value="提交"/>
    			</td>
    		</tr>
    	</table>
    </form>
    </div>
  </body>
</html>
