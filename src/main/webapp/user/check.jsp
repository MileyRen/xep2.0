<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'check.jsp' starting page</title>
    
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
    margin-top: 20px;
    margin-left: 60px;
    margin-right: 60px;
    }
    </style>

  </head>
  
  <body>
  <div> <%@include file="/WEB-INF/content/navigator.jsp"%></div>
  <div>
    <div class="main">
    	<table  class="table table-hover">
    		<tr>
<!--     			<th>ID</th>  -->
    			<th>UserName</th>
    			<th>E-mail</th>
    			<th>Role</th>
    			<th>Status</th>
    			<th>Register Time</th>
    			<th>Initial Space</th>
    			<th>Used Space</th>  
    			<th>Action</th>  			   			
    		</tr>
    		
    		<c:forEach items="${cusers}"  var="s">
    		<c:if test="${s.roleId==0}">
    		<tr>
<!--     		    <td>${s.id}</td>  -->
    			<td>${s.userName}</td>
    			<td>${s.email}</td>
    			<td>
    			<c:choose>
    						<c:when test="${s.roleId==0}">
    						User
    						</c:when>
    						<c:when test="${s.roleId==1}">
    						administrator
    						</c:when>
    			</c:choose>
    			</td>
    			<td>
    			<c:choose>
    						<c:when test="${s.isAvailable==0}">
    						nonactivated
    						</c:when>
    						<c:when test="${s.isAvailable==1}">
    						paused
    						</c:when>
    						<c:when test="${s.isAvailable==2}">
    						activated
    						</c:when>
    			</c:choose>
    			</td>
    			<td>${s.registerTime}</td>
    			<td>${s.initStorge}m</td>
    			<td>${s.usedStorage}m</td>
    			<td>
    			<div class="btn-group">
    			<a class="btn btn-info dropdown-toggle btn-xs" data-toggle="dropdown">
						<span class="glyphicon  glyphicon-pencil"></span>edit
						<span class="caret"></span>
				</a>
				<ul class="dropdown-menu" role="menu" style="min-width: 100%;">
					<li>
				    <a href="${pageContext.request.contextPath}/user/usermanage?op=activeuser&id=${s.id}">
				    <span class="glyphicon glyphicon-circle-arrow-up"></span>
						active
				     </a>
				    </li>
				    <li>
				    <a href="${pageContext.request.contextPath}/user/usermanage?op=pauseuser&id=${s.id}">
				    <span class="glyphicon glyphicon-circle-arrow-down"></span>
						pause
				     </a>
				    </li>
					<li>
				    <a href="${pageContext.request.contextPath}/user/usermanage?op=deluser&id=${s.id}" onClick="return confirm('Are you sure?');">
				    <span class="glyphicon glyphicon-trash"></span>
						delete
				     </a>
				    </li>
				</ul>
    			</div>
    			</td>
    			 
    		</tr> 
    		</c:if> 		
    		</c:forEach>
    	</table>
    	</div>
  </div>
  </body>
</html>
