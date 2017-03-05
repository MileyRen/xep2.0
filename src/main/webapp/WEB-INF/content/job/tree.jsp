<%--
  Created by IntelliJ IDEA.
  User: qi_l
  Date: 2016/11/29
  Time: 13:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<ul>
	<c:forEach var="item" items="${treeList}">
		<li><a href="javascript:;" id="${item.id}" data-type="${item.type}" data-folder="${item.folderId}">${item.name}<c:if test="${item.type != 'folder'}">${item.type}</c:if></a></li>
		<c:if test="${item.type == 'folder'}">
			<c:set var="treeList" value="${item.children}" scope="request"/>
			<c:import url="tree.jsp"/>
		</c:if>
	</c:forEach>
</ul>
