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
    
    <title>My JSP 'index.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<%@include file="/WEB-INF/headinclude.jsp"%>
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
   
    	<table class="table table-hover">
    		<tr>
    			<th>ID</th> 
    			<th>ToolName</th>
    			<th>adduser</th>
    			<th>IsShared</th>
    			<th>SaveResults</th>
    			<th>tooltype</th>
    			<th>AddedTime</th>
    			<th>Describtion</th>
    			<th>Parameters</th>    			
    			<th>action</th>    				   			
    		</tr>    		
    		<c:forEach items="${ctools}"  var="s">
    		<tr>
    		    <td>${s.id}</td> 
    			<td>${s.toolName}</td>
    			<td>${s.user.userName}</td>
    			<td>${s.isShared}</td>
    			<td>${s.savedResults}</td>
    			<td>${s.tooltype.typeName}</td>
    			<td>${s.addedTime}</td>
    			<td>${s.describtion}</td>
    			<td>${s.parameters}</td>    			
    			
    			<td>
    			 <a href="${pageContext.request.contextPath}/tool/edit.action?id=${s.id}">修改</a>
    			 <a href="${pageContext.request.contextPath}/tool/del.action?id=${s.id}">删除</a>    			 
    			</td>
    			 
    		</tr>  		
    		</c:forEach>
    		</table>
    		 <a href="${pageContext.request.contextPath}/tool/add.jsp">添加</a>
    		
    </div>
  </body>
</html>
