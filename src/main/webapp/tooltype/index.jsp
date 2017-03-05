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
    <table  class="table table-hover">
    		<tr>
<!--     			<th>ID</th>  -->
    			<th>typename</th>
    			<th>adduser</th>
    			<th>color</th>
    			<th>describtion</th>
    			<th>toolnum</th>
    			<th>action</th>
    				   			
    		</tr>
    		
    		<c:forEach items="${ctooltype}"  var="s">
    		<tr>
<!--     		    <td>${s.id}</td>  -->
				<td>${s.typeName}</td>
    			<td>${s.user.userName}</td>    			
    			<td bgcolor="${s.showColor}"></td>
    			<td>${s.describtion}</td>    			
    			<td>${s.toolNum}</td>
    			<td>
    			<!-- 按钮组 -->
    			<c:if test="${sessionScope.user.roleId==1 }">
    			<div class="btn-group">
    			<a class="btn btn-info dropdown-toggle btn-xs" data-toggle="dropdown">
						<span class="glyphicon  glyphicon-pencil"></span>edit
						<span class="caret"></span>
				</a>
				<ul class="dropdown-menu" role="menu" style="min-width: 100%;">
				<li>
				    <a href="${pageContext.request.contextPath}/tooltype/edit.action?id=${s.id}">
				    <span class="glyphicon glyphicon-adjust"></span>
						modify
				     </a>
				    </li>
				    <c:if test="${s.toolNum==0}">
					<li>
				    <a href="${pageContext.request.contextPath}/tooltype/del.action?id=${s.id}" onClick="return confirm('Are you sure?');">
				    <span class="glyphicon glyphicon-trash"></span>
						delete
				     </a>
				    </li>
				    </c:if>
				</ul>
    			</div>
    			</c:if>
    			<!-- 按钮组结束 -->
    			
    			
    			
<!--     			 <a class="btn btn-info btn-xs" href="${pageContext.request.contextPath}/tooltype/edit.action?id=${s.id}">修改</a> -->
<!--     			 <a class="btn btn-info btn-xs" href="${pageContext.request.contextPath}/tooltype/del.action?id=${s.id}">删除</a> -->

    			</td>
    			 
    		</tr>  		
    		</c:forEach>
    		</table>
    		<c:if test="${sessionScope.user.roleId==1 }">
    	<a class="btn btn-info" href="${pageContext.request.contextPath}/tooltype/add.jsp">add</a>
    	</c:if>
   </div>
  </body>
</html>
