<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'listall.jsp' starting page</title>
    
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
    margin-left: 100px;
    margin-right: 100px;
    }
    </style>
	<script type="text/javascript">
	$(document).ready(function(){
		function showall(){
			$("table tr:gt(0)").each(function(){
				$(this).show();
			});			
		}
	    
	    $("#selected").click(function(){
			var adder=$("#adder option:selected").text();
	    	var type=$("#type option:selected").text();
	    	var share=$("#share option:selected").text();
	    	var desc=$("#desc").val().trim();
			showall();
			
			$("table tr:gt(0)").each(function(){
				var ctype=$(this).children("td:eq(4)").text();
				var cadder=$(this).children("td:eq(1)").text();
				var cshare=$(this).children("td:eq(2)").text().trim();
				var cdesc=$(this).children("td:eq(6)").text().trim();
				if(ctype!=type&&type!="All") $(this).hide();				
				if(cadder!=adder&&adder!="All") $(this).hide();
				if(cshare!=share&&share!="All") $(this).hide();
				if(cdesc.match(desc)==null&&desc!="") $(this).hide();
				
			});
		});
	    
	
	});
	
	</script>

  </head>
  
  <body>
    <div> <%@include file="/WEB-INF/content/navigator.jsp"%></div>
    <div class="main"><span>
    <label>Adder</label>
    <select name="adder" id="adder">
    <option value="0">All</option>
    <c:forEach items="${adders}" var="s">
    <option value="${s.id}">${s.userName}</option>
    </c:forEach>
    </select>
    <label>Type</label>
    <select name="type" id="type">
    <option value="0">All</option>
    <c:forEach items="${types}" var="s">
    <option value="${s.id}">${s.typeName}</option>
    </c:forEach>
    </select>
    <label>Share</label>
    <select id="share">
    <option>All</option>
    <c:if test="${sessionScope.user.roleId==1}">   
    <option>Shared</option>
    </c:if>
    <option>System</option>   
	</select>
	<label>Description</label>
	<input type="text" id="desc">
	<a id="selected" class="btn btn-info btn-xs"><span class="glyphicon glyphicon-search"></span> search</a>
	
    </span>
    </div>
    
  <div class="main">
  
   
    	<table class="table table-hover">
    		<tr>
<!--     			<th>ID</th>  -->
    			<th>ToolName</th>
    			<th>Adder</th>
    			<th>Share</th>
    			<th>Save Results</th>
    			<th>Tool Type</th>
    			<th>Add Time</th>
    			<th>Describtion</th>
<!--     			<th>Parameters</th>    			 -->
    			<th>action</th>    				   			
    		</tr> 
    			
    		<c:forEach items="${ctools}"  var="s" >
    		<tr>
    		<c:if test="${s.user.id==sessionScope.user.id||(s.isShared==1&&sessionScope.user.roleId==1)||s.isShared==2}">
<!--     		    <td>${s.id}</td>  -->
    			<td>${s.toolName}</td>
    			<td>${s.user.userName}</td>
				
    			<td>
				<c:choose>
					<c:when test="${s.isShared==0}">
					Private
					</c:when>
					<c:when test="${s.isShared==1}">
					Shared
					</c:when>
					<c:when test="${s.isShared==2}">
					System
					</c:when>
				
				</c:choose>
				
				</td>
    			<td>
    			<c:choose>
					<c:when test="${s.savedResults==0}">
					No
					</c:when>
					<c:when test="${s.savedResults==1}">
					Yes
					</c:when>					
				
				</c:choose>
    			
    			</td>
				
    			<td>${s.tooltype.typeName}</td>
    			<td>
    			<fmt:formatDate pattern="yyyy-MM-dd" value="${s.addedTime}"/>    			
    			</td>
    			<td>${s.describtion}</td>
<!--     			<td>${s.parameters}</td> -->
    			<td>
    			<!-- <a class="btn btn-info btn-xs" href="${pageContext.request.contextPath}/tool/edit.action?id=${s.id}">修改</a>
    			 <a class="btn btn-info btn-xs" href="${pageContext.request.contextPath}/tool/del.action?id=${s.id}">删除</a>    			 
    			-->
    			<!-- 按钮组 -->
    			<c:if test="${s.user.id==sessionScope.user.id||(s.isShared==1&&sessionScope.user.roleId==1)}">
    			<div class="btn-group">
    			<a class="btn btn-info dropdown-toggle btn-xs" data-toggle="dropdown">
						<span class="glyphicon  glyphicon-pencil"></span>edit
						<span class="caret"></span>
				</a>
				<ul class="dropdown-menu" role="menu" style="min-width: 100%;">
				<li>
				    <a href="${pageContext.request.contextPath}/tool/edit.action?id=${s.id}">
				    <span class="glyphicon glyphicon-adjust"></span>
						modify
				     </a>
				    </li>
					<li>
				    <a href="${pageContext.request.contextPath}/tool/del.action?id=${s.id}" onClick="return confirm('Are you sure?');">
				    <span class="glyphicon glyphicon-trash"></span>
						delete
				     </a>
				    </li>
				</ul>
    			</div>
    			</c:if>
    			<!-- 按钮组结束 -->
    			</td>
    			</c:if>
    		</tr> 
    		</c:forEach>
    		
    		</table>
    		 <a class="btn btn-info " href="${pageContext.request.contextPath}/tool/add.jsp">add</a>
    		
    </div>
  </body>
  </body>
</html>
