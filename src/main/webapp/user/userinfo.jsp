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
    
    <title>My JSP 'userinfo.jsp' starting page</title>
    
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
    margin-left: 400px;
    margin-right: 400px;
    }
    </style>
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
  </head>
  
  <body>
  <div> <%@include file="/WEB-INF/content/navigator.jsp"%></div>
  <div>
  	   <div class="main">
       <table class="table" width="500px">
    	<tr>
    		
    			<td>ID：</td>
    			<td>${sessionScope.user.id}</td>
       </tr>
       <tr>
    		
    			<td>password：</td>
    			<td>${sessionScope.user.passWord}</td>
       </tr>
       <tr>
    				<td>姓名：</td>
    				<td>${sessionScope.user.userName}</td>
      </tr>	
      <tr>
    				<td>邮箱：</td>
    				<td>${sessionScope.user.email}</td>	
     </tr>
     <tr>
    				<td>角色：</td>
    				<td>
    				<c:choose>
    						<c:when test="${sessionScope.user.roleId==0}">
    						普通用户
    						</c:when>
    						<c:when test="${sessionScope.user.roleId==1}">
    						管理员
    						</c:when>
    				</c:choose>
    				</td>
     </tr>
     <tr>

    				
    				<td>状态：</td>
    				<td>
    				<c:choose>
    						<c:when test="${sessionScope.user.isAvailable==0}">
    						未激活
    						</c:when>
    						<c:when test="${sessionScope.user.isAvailable==1}">
    						暂停
    						</c:when>
    						<c:when test="${sessionScope.user.isAvailable==2}">
    						有效
    						</c:when>
    				</c:choose>
    				</td> 
    </tr> 
     <tr>  				
    				
    				<td>注册时间：</td>
    				<td>${sessionScope.user.registerTime}</td>
    </tr>
    <tr>		 				
    				<td>个人文件夹：</td>
    				<td>${sessionScope.user.folder}</td>
  </tr>
   <tr>
    				<td>大小限制：</td>
    				<td>${sessionScope.user.initStorge} MB</td>
   </tr>
   <tr>
    				<td>已用大小：</td>
    				<td>${sessionScope.user.usedStorage} MB</td>
   </tr>
   
    			
    		
 
    	
 </table>
<!--        <a  href="${pageContext.request.contextPath}/user/modify.jsp">修改密码</a> -->
<!--        <a  href="${pageContext.request.contextPath}/tooltype/get_all_tooltype.action">查看工具类型</a> -->
       </div>
 
 </div>
  </body>
</html>
