<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
     <script src="js/login/jquery-1.11.1.min.js"></script>
    <link rel='stylesheet prefetch' href='css/bootstrap.css'>
	<link rel='stylesheet prefetch' href='js/login/animate.min.css'>
	<link rel='stylesheet prefetch' href='js/login/font-awesome.min.css'>
    <title>My JSP 'reg.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
	<style type="text/css">

body {
    padding-top: 100px;      
    }
    
.login-form {
    width: 390px;
}
.login-title {
    font-family: 'Exo', sans-serif;
    text-align: center;
   
}
.login-userinput {
    margin-bottom: 10px;
}
.login-button {
    margin-top: 10px;
}
.login-options {
    margin-bottom: 0px;
}
.login-forgot {
    float: right;
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
    <div class="container login-form">
    <h2 class="login-title">- Create User -</h2>
    <div class="panel panel-default">
        <div class="panel-body">
            <form  action="${pageContext.request.contextPath}/user/usermanage?op=adduser" method="post">
            
            
                <div class="input-group login-userinput">
                    <label>Username</label>            
                    <input id="txtUser" type="text" style="width:330px" class="form-control" name="name">                    
                </div>
                <div class="input-group login-userinput">
                     <label>Password</label>            
                    <input id="txtPwd" type="password" style="width:330px" class="form-control" name="pwd">                    
                </div>
                <div class="input-group login-userinput">
                     <label>Confirm Password</label>            
                    <input id="txtPwdcfm" type="password" style="width:330px" class="form-control" name="pwdcfm">                    
                </div>
                <div class="input-group login-userinput">
                     <label>Email</label>            
                    <input id="txtEmail" type="text" style="width:330px" class="form-control" name="email">                    
                </div>
                 
                <button class="btn btn-primary btn-block login-button" type="submit"><i class="	glyphicon glyphicon-circle-arrow-right"></i> Submit</button>
                
            </form>
        </div>
    </div>
</div>
  </body>
</html>
