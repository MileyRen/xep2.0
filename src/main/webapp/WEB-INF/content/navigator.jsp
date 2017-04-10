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
				tool<b class="caret"></b></a>
				<ul class="dropdown-menu">
					<li><a href="${pageContext.request.contextPath}/tooltype/get_all_tooltype.action">type manage</a></li>
					<li><a href="${pageContext.request.contextPath}/tool/getall.action">tool manage</a></li>
			</ul></li>
			<li><a href="flow/view.action">Work Flow</a></li>
			<li class="dropdown">
				<a href="#" class="dropdown-toggle" data-toggle="dropdown">
					job
					<b class="caret"></b>
				</a>
				<ul class="dropdown-menu">
					<li><a href="job/create.action">create</a></li>
					<li><a href="job/view.action">waiting</a></li>
					<li><a href="jobsList.action?jobstate=run">Running job</a></li>
					<li><a href="jobsList.action?jobstate=stop">Stopped job</a></li>
				</ul>
			</li>
			<li><a href="pageList.action?pageTag=1">file manager</a></li>
			<li><a a href="#" class="dropdown-toggle" data-toggle="dropdown">
					user<b class="caret"></b></a>
				<ul class="dropdown-menu">
					<li><a href="${pageContext.request.contextPath}/user/userinfo.jsp">user information</a></li>
					<li><a href="${pageContext.request.contextPath}/user/modify.jsp">modify password</a></li>
					<c:if test="${sessionScope.user.roleId==1}">
						<li><a href="${pageContext.request.contextPath}/user/usermanage?op=check">audit</a></li>
					</c:if>
				</ul>
			</li>
		</ul>
	</div>
	<div style="float:right; display: inline-block;">
		<div><ul class="nav navbar-nav"><li><a href="javascript:;">退出</a></li></ul></div>
	</div>
	</div>
</nav>
