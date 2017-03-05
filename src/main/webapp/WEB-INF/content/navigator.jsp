<%@ page language="java" pageEncoding="UTF-8" %>
<nav class="navbar navbar-default" role="navigation">
	<div style="margin: 0 150px;" class="container-fluid">
	<div class="navbar-header">
		<a class="navbar-brand" href="login.action">生科院2.0</a>
	</div>
	<div>
		<ul class="nav navbar-nav">
<!-- 			<li class="active"><a href="#">iOS</a></li> -->
			<li><a a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown">
				工具<b class="caret"></b></a>
				<ul class="dropdown-menu">
					<li><a href="${pageContext.request.contextPath}/tooltype/get_all_tooltype.action">工具类型管理</a></li>
					<li><a href="${pageContext.request.contextPath}/tool/getall.action">工具管理</a></li>
			</ul></li>
			<li><a href="flow/view.action">流程</a></li>
			<li class="dropdown">
				<a href="#" class="dropdown-toggle" data-toggle="dropdown">
					作业
					<b class="caret"></b>
				</a>
				<ul class="dropdown-menu">
					<li><a href="job/create.action">新建作业</a></li>
					<li><a href="job/view.action">查看未开始作业</a></li>
					<li><a href="jobsList.action">作业状态查询</a></li>
				</ul>
			</li>
			<li><a href="pageList.action?pageTag=1">文件管理页面</a></li>
			<li><a a href="#" class="dropdown-toggle" data-toggle="dropdown">
					用户<b class="caret"></b></a>
				<ul class="dropdown-menu">
					<li><a href="${pageContext.request.contextPath}/user/userinfo.jsp">查看用户信息</a></li>
					<li><a href="${pageContext.request.contextPath}/user/modify.jsp">修改密码</a></li>
					<c:if test="${sessionScope.user.roleId==1}">
					<li><a href="${pageContext.request.contextPath}/user/usermanage?op=check">用户审核</a></li>
					</c:if>
			</ul></li>
		</ul>
	</div>
	<div style="float:right; display: inline-block;">
		<div><ul class="nav navbar-nav"><li><a href="javascript:;">退出</a></li></ul></div>
	</div>
	</div>
</nav>
