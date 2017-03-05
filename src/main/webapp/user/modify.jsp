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
    
    <title>My JSP 'modify.jsp' starting page</title>
    
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
  	<div> <%@include file="/WEB-INF/content/navigator.jsp"%></div>
  <form action="${pageContext.request.contextPath}/user/usermanage?op=modify" method="post">
    <table  width="200" align="center" >
    	<tr>
    		
    			<td>Input current password</td>
    			
       </tr>
       <tr>
    		
    			<td><input type="password" name="oldpwd"></td>
    			
       </tr>
       <tr>
    		
    			<td>Input new password</td>
    			
       </tr>
       <tr>
    		
    			<td><input type="password" name="newpwd"></td>
    			
       </tr>
       <tr>
    		
    			<td><input class="btn btn-info " type="submit" value="submit"/></td>
    			
       </tr>
             

   
    			
    		
 
    	
 </table>
 </form>
  </body>
</html>
