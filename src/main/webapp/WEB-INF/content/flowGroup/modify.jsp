<%--
  Created by IntelliJ IDEA.
  User: qi_l
  Date: 2016/11/24
  Time: 17:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
	<base href="<%=basePath%>"/>
	<title>${group.name } 信息</title>
	<link rel="stylesheet" href="https://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">

	<script type="text/javascript" src="js/jquery-latest.js"></script>
	<script type="text/javascript" src="js/jquery.jqpagination.min.js"></script>
	<script src="https://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>

	<style>
		form {
			margin: 20px;
		}
		.btn-container {
			text-align: right;
		}
	</style>
</head>
<body>
<form class="form-horizontal" role="form" action="flowGroup/modify-commit.action">
	<div class="form-group"><label class="col-sm-2 control-label">id</label><label class="col-sm-2 control-label"><c:choose><c:when test="${group.id==0}">无</c:when><c:otherwise>${group.id }</c:otherwise></c:choose></label>
	</div><hr><input style="display: none;" name="id"
			   value="${group.id }">
	<div class="form-group"><label class="col-sm-2 control-label">分组</label><div class="col-sm-10"><input required value="${group.name }" class="form-control" name="name"></div></div>
	<div class="form-group"><label class="col-sm-2 control-label">介绍</label><div class="col-sm-10"><textarea required name="intro" class="form-control" rows="3">${group.intro }</textarea></div></div>
	<div class="form-group"><div class="btn-container col-sm-offset-2 col-sm-10"><input type="submit" class="btn btn-primary" value="确定"><input type="button" class="btn btn-primary" value="取消" id="cancel" onclick="javascript:window.close()"></div></div>
</form>
</body>
</html>
