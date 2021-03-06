<%@page import="com.gene.utils.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@page import="com.xeq.file.domain.FileAndFolder"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<head>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Folder And File List</title>
<!-- 新 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="http://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>

<link rel="stylesheet" type="text/css" href="styleRen/jquery-easyui-1.3.6/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="styleRen/jquery-easyui-1.3.6/themes/icon.css">
<link rel="stylesheet" type="text/css" href="styleRen/jquery-easyui-1.3.6/themes/fgr.css">
<script type="text/javascript" src="styleRen/jquery-easyui-1.3.6/jquery.easyui.min.js"></script>
<script type="text/javascript" src="styleRen/jsFileManager/folder.js"></script>
<script type="text/javascript" src="styleRen/jsFileManager/Popup.js"></script>
<style type="text/css">
input[type=file] {
	display: inline;
}
/*--------文件上传开始----------*/
.fileup {
    position: relative;
    display: inline-block;
    background: #D0EEFF;
    border: 1px solid #99D3F5;
    border-radius: 4px;
    padding: 4px 12px;
    overflow: hidden;
    color: #1E88C7;
    text-decoration: none;
    text-indent: 0;
    line-height: 20px;
}
.fileup input {
    position: absolute;
    font-size: 100px;
    right: 0;
    top: 0;
    opacity: 0;
}
.fileup:hover {
    background: #AADFFD;
    border-color: #78C3F3;
    color: #004974;
    text-decoration: none;
}
.file_text {
	margin-left:3px;
	border: 0px;
	font-size: 14px;
}
/*--------文件上传结束----------*/
</style>
</head>
<body>
	<%@include file="/WEB-INF/content/navigator.jsp"%>
	<div class="container">
		 <div class="row clearfix"> 
			<div class="col-md-12 column"> 
				<!-- 初始化页面列表结束 -->
				<!-- 导航按钮开始-->
				<nav class="navbar navbar-default" role="navigation">
				<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
					<ul class="nav navbar-nav">
						<ol class="breadcrumb">
							<li>
								<a onclick="return into(-1)">
									<span class="glyphicon glyphicon-folder-open"></span>
									${userName}
								</a>
							</li>
							<s:iterator value="#session.breadcrumb" status="FileAndFolder">
								<li>
									<a onclick="return into(${id })">${name}</a>
								</li>
							</s:iterator>
						</ol>
					</ul>
					<ul class="nav navbar-nav navbar-right">
						<li>
							<a href="#" onclick="javascript:window.location.href='syncFile.action'" class="btn" data-toggle="tooltip" data-placement="top" title="Sync files and folders"> 
 								<span class="glyphicon glyphicon-refresh"></span>
 								 Sync
 							</a>
						</li>
						<li>
							<a href="#modal-container-create" class="btn" data-toggle="modal">
								<span class="glyphicon glyphicon-plus-sign"></span>
								Create
							</a>
						</li>
						<li>
							<a href="#modal-container-upload" class="btn" data-toggle="modal">
								<span class="glyphicon glyphicon-cloud-upload"></span>
								Upload
							</a>
						</li>
						<li>
							<a href="#" class="btn"
								onclick="confirm(' ARE YOU SURE DELETE THE FOLDER AND FILES IN THOST FOLDER?',
								function(flag){if(flag){delbulk(${parentId});}
								else{return false;}
										})">
								<span class="glyphicon glyphicon-trash"></span>
								Delete
							</a>
						</li>
						<li>
							<a class="btn" data-toggle="modal" data-target="#BulkMoveModal">
								<span class="glyphicon glyphicon-move"></span>
								Move
							</a>
						</li>

						<li>
							<a href="#" onclick="javascript:window.location.href='backStack.action'" class="btn">
								<span class="glyphicon glyphicon-circle-arrow-up"></span>
 								Up
							</a>
						</li>

					</ul>
				</div>
				</nav>
				<!-- 导航按钮结束 -->
				<table class="table table-hover">
					<tr>
						<th><input type="checkbox"
								onclick="if(this.checked==true){allCheck('delbulk',true);}else{allCheck('delbulk',false);}"></th>
						<th>Icon</th>
						<th>Name</th>
						<th>Create Time</th>
						<th>Type</th>
						<th>Size</th>
						<!-- <th>Folder Path</th> -->
						<th>Action</th>
					</tr>
					<s:iterator value="#session.fileList" status="FileAndFolder">
						<s:if test="type=='folder' || type=='mapping_copy'">
							<tr>
								<td>
 									<s:if test="type=='folder'">
 										<input class="delbulk" type="checkbox" value="[arr]${id}[arr]${type}[arr]${name}">
 									</s:if>
 									<s:if test="type=='mapping_copy'">
 										<input type="checkbox" disabled >
 									</s:if>	
 								</td>
 								<td onclick="return into(${id})"><span class="icon tree-folder"></span></td>
							
								<td onclick="return into(${id})">${name}</td>
								<td onclick="return into(${id})"><s:date name="time" format="yyyy-MM-dd" /></td>
								<td onclick="return into(${id})">${type}</td>
								<td onclick="return into(${id})">${size}</td>
								<%-- <td onclick="return into(${id})">${folderPath}</td> --%>
								<td>
									<div class="btn-group">
										<a class="btn btn-primary dropdown-toggle btn-xs" data-toggle="dropdown"
 										<s:if test="type=='mapping_copy'">disabled="disabled"</s:if>>
											<span class="glyphicon  glyphicon-pencil"></span>
											edit
											<span class="caret"></span>
										</a>
										<ul class="dropdown-menu" role="menu" style="min-width: 100%;">
											<li>
												<form id="del${id}" action="deleteDir.action" method="post" style="display: none">
													<input type="hidden" name="id" value="${id}">
													<input type="hidden" name="folderPath" value="${folderPath}">
													<input type="hidden" name="parentFolderId" value="${parentFolderId}">
													<input type="hidden" name="pagesource.currentPage" value="${pagesource.currentPage}">
												</form>
												<a
													onclick="confirm(' ARE YOU SURE DELETE THE FOLDER AND FILES IN THE FOLDER?',
													function(flag){if(flag){$('form#del${id}').submit();}
													else{return false;
													}
													})">
													<span class="glyphicon glyphicon-trash"></span>
													delete
												</a>
											</li>
											<li>
												<a href="#modal-container-move" data-toggle="modal" onclick="prom(${id})">
													<span class="glyphicon glyphicon-move"></span>
													move
												</a>
											</li>
										</ul>
									</div>
								</td>
							</tr>
						</s:if>
					</s:iterator>
					<s:iterator value="#session.fileList" status="FileAndFolder">
						<s:if test="type!='folder' && type!='mapping' && type!='mapping_copy'">
							<tr>
								<td><input class="delbulk" type="checkbox" value="[arr]${id}[arr]${type}[arr]${name}"></td>
								<td><span class="icon tree-file"></span></td>
								<td>${name}</td>
								<td><s:date name="time" format="yyyy-MM-dd" /></td>
								<td>${type}</td>
								<td>${size}</td>
								<%-- <td>${folderPath}</td> --%>
								<td>
									<!-- 按钮组开始 -->
									<div class="btn-group">
										<a class="btn btn-primary dropdown-toggle btn-xs" data-toggle="dropdown">
											<span class="glyphicon glyphicon-pencil"></span>
											edit
											<span class="caret"></span>
										</a>
										<ul class="dropdown-menu" role="menu" style="min-width: 100%;">
											<li>
												<form id="del${id}" action="delete.action" method="post" style="display: none">
													<input type="hidden" name="id" value="${id}">
													<input type="hidden" name="folderPath" value="${folderPath}">
													<input type="hidden" name="name" value="${name }">
													<input type="hidden" name="type" value="${type }">
													<input type="hidden" name="parentFolderId" value="${parentFolderId}">
													<input type="hidden" name="pagesource.currentPage" value="${pagesource.currentPage}">
												</form>
												<a
													onclick="confirm(' ARE YOU SURE DELETE THE FILE?',
														function(flag){if(flag){$('form#del${id}').submit();}
														else{return false;}
													})">
													<span class="glyphicon glyphicon-trash"></span>
													delete
												</a>
											</li>
											<li>
												<form id="down${id}" action="download.action" method="post">
													<input type="hidden" name="folderPath" value="${folderPath}">
													<input type="hidden" name="name" value="${name}">
													<input type="hidden" name="type" value="${type}">
													<input type="hidden" name="downfileName" value="${name}${type}">
												</form>
												<a onclick="javascript:$('form#down${id}').submit()">
													<span class="glyphicon  glyphicon-save"></span>
													download
												</a>
											</li>
											<li>
												<a href="#modal-container-move" data-toggle="modal" onclick="prom(${id})">
													<span class="glyphicon glyphicon-move"></span>
													move
												</a>
											</li>
										</ul>
									</div> <!-- 按钮组结束 -->
								</td>
							</tr>
						</s:if>
					</s:iterator>
				</table>
				<!-- 文件列表结束 -->
				<!-- 分页效果开始 -->
			<div style="margin: 20px 0;"></div>
			<div>
				<ul class="pagination">
					<li>
						<a onclick="javascript:window.location.href='pageList.action?parentFolderId=${parentFolderId}&pagesource.currentPage=1'">&laquo;</a>
					</li>
					<li>
						<a onclick="javascript:window.location.href='pageList.action?parentFolderId=${parentFolderId}&pagesource.currentPage=${pagesource.currentPage-1 }'">previous</a>
					</li>
					<li>
						<a>[ ${pagesource.currentPage} of ${pagesource.totalPages }]
						</a>
					</li>
					<li>
						<a onclick="javascript:window.location.href='pageList.action?parentFolderId=${parentFolderId}&pagesource.currentPage=${pagesource.currentPage+1 }'">next</a>
					</li>
					<li>
						<a onclick="javascript:window.location.href='pageList.action?parentFolderId=${parentFolderId}&pagesource.currentPage=${pagesource.totalPages }'">&raquo;</a>
					</li>
				</ul>
			</div>
		</div>
	</div>
	</div>
	<!-- 弹出框开始 -->

	<div class="modal fade" id="BulkMoveModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">
						<a href="#" class="btn">
							<span class="glyphicon glyphicon-plus"> </span>
							Move Folders and Files To A Folder...
						</a>
					</h4>
				</div>
				<div class="modal-body" style="height: 250px">
					<div class="form-group" style="padding: 10px 20px 10px 80px;">
						<form action="BulkMove.action" id="moveBulk" method="post" role="form"
							style="padding: 10px 20px 10px 30px;">
							<s:token />
							<select id="bulkTree" style="width: 300px" class="form-control"></select>
							<br>

							<input type="hidden" name="pagesource.currentPage" value="${pagesource.currentPage}" />
							<input type="hidden" name="parentFolderId" value="${session.parentId }" />
							<input type="hidden" name="toIdBulk" id="BulktoPathId" value="">
						</form>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
					<button type="button" class="btn btn-primary"
						onclick="MoveList(${pagesource.currentPage},${session.parentId })">Move</button>
				</div>
			</div>
		</div>
	</div>

	<!-- 批量移动文件夹结束 -->

	<!-- 移动文件开始 ,文件可移动到任意文件夹-->
	<div class="modal fade" id="modal-container-move" role="dialog" aria-labelledby="myModalLabel"
		aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
					<h4 class="modal-title" id="myModalLabel">
						<a href="#" class="btn">
							<span class="glyphicon glyphicon-plus"> </span>
							Move To A Folder...
						</a>
					</h4>
				</div>
				<div class="modal-body" style="height: 250px">
					<div class="form-group">
						<form action="moveAction.action" id="movef" method="post" role="form"
							style="padding: 10px 20px 10px 80px;">
							<select id="testTree" style="width: 300px" class="form-control"></select>
							<input type="hidden" name="fromId" id="fromId" value="" />
							<input type="hidden" name="pagesource.currentPage" value="${pagesource.currentPage}" />
							<input type="hidden" name="parentFolderId" value="${session.parentId }" />
							<input type="hidden" name="toId" id="toPathId" value="">
							<s:token />
						</form>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">cancel</button>
					<button type="button" class="btn btn-primary" onclick="javascript:$('form#movef').submit()">submit</button>
				</div>
			</div>
		</div>
	</div>
	<!-- 移动文件结束 -->
	<!-- 上传文件窗口 -->
	<div class="modal fade" id="modal-container-upload" role="dialog" aria-labelledby="myModalLabel"
		aria-hidden="true" >
		<div class="modal-dialog"  style="width:500px;max-height: 800px">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
					<h5 class="modal-title" id="myModalLabel">Please Add Some Files...</h5>
				</div>
				<div class="modal-body">
				<!-- 新添加部分开始 -->
					<form action="fileUpload.action" id="addFiles" method="post" style="padding: 10px 20px 10px 0px;" enctype="multipart/form-data">
        				<a id="more" href="javascript:void(0)" class="fileup" ><strong style="margin-left: 5px; margin-right: 8px;" class="icon-plus-circle" >select file</strong>
    						<input id="fileUp" name="uploadFiles" type="file" onchange="change()">
						</a>
						<input type="hidden" id="parentFolderId" name="parentFolderId" value=${session.parentId }>
						<input type="hidden" id="folderPath" name="folderPath" value=${session.parentPath }>
						<input type="hidden" id="currentPage" name="pagesource.currentPage" value="${pagesource.currentPage}">
					</form>
					<div id="showFileList">
						
					</div>
<!-- 新添加部分结束 -->
				</div>
				<div class="modal-footer">
					<button type="reset" class="btn btn-default" data-dismiss="modal">cancel</button>
					<button type="button" class="btn btn-primary" onclick="$('form#addFiles').submit()">Upload</button> 
				</div>
			</div>
		</div>
	</div>
	<!-- 上传文件窗口结束 -->
	<!-- 创建文件夹窗口 -->
	<div class="modal fade" id="modal-container-create" role="dialog" aria-labelledby="myModalLabel"
		aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
					<h4 class="modal-title" id="myModalLabel">
						<a href="#" class="btn">
							<span class="glyphicon glyphicon-plus"> </span>
							Create A Folder...
						</a>
					</h4>
				</div>
				<div class="modal-body">
					<form action="addFolder.action" id="addF" target="_parent"
						style="padding: 10px 20px 10px 40px;" method="post">
						<s:token />
						<%-- <s:fielderror /> --%>
						<input type="hidden" name="parentFolderId" value=${session.parentId }>
						<input type="hidden" name="folderPath" value=${session.parentPath }>
						<input type="hidden" name="pagesource.currentPage" value="${pagesource.currentPage}">
						<%-- <s:fielderror name="name" /> --%>
						<div class="input-group">
							<span class="input-group-addon">
								<span class="glyphicon glyphicon-folder-close"></span>
							</span>
							<input name="name" id="name" type="text" class="form-control" id="firstname"
								placeholder="Please enter a folder name..." required>
						</div>
						<br>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">cancel</button>
					<button type="button" class="btn btn-primary" onclick="$('form#addF').submit()">create</button>
				</div>
			</div>
		</div>
	</div>
	<!-- 创建文件夹窗口结束 -->
	<script type="text/javascript">
	$(document).ready(function() {
      /**批量删除结果*/		
		var deleteBulkMgs="${deleteBulkMgs}";
		if(deleteBulkMgs!=null&&deleteBulkMgs!=""){
			alert(deleteBulkMgs,function(){
				<%session.setAttribute("deleteBulkMgs", null);%>
				location.reload();
			});
		}
		/**批量移动结果*/
		var moveBulkMgs="${moveBulkMgs}";
		if(moveBulkMgs!=null&&moveBulkMgs!=""){
			alert(moveBulkMgs,function(){
				<%session.setAttribute("moveBulkMgs", null);%>
			 	location.reload();
			});
		}
		/**删除文件或文件夹结束信息*/
		var del="${del}";
		if(del!=null&&del!=""){
			alert(del,function(){
				<%session.setAttribute("del", null);%>
				location.reload();
			});
		}
		
		/**移动文件或文件夹结束信息*/
		var mfs ="${moveFileOrFolder}";
		if(mfs!=null&&mfs!=""){
			alert(mfs,function(){
				<%session.setAttribute("moveFileOrFolder", null);%>
				location.reload();
			});
		}
		
		/**创建文件夹结束提示信息*/
		var create ="${createFolder}";
		if(create!=null&&create!=""){
			alert(create,function(){
				<%session.setAttribute("createFolder", null);%>
				location.reload();
			});
			} 
		/**上传文件结束提示信息*/
		var failedSize="${failedSize}";
		if(failedSize!=null&&failedSize!=""){
		var sucSize="${sucSize}";
		alert("Upload Success:"+sucSize+" ; Upload Failed: "+failedSize,function(){
			<%session.setAttribute("failedSize", null);%>
			location.reload();
		});
		} 
	});
   
   var data1=<%=session.getAttribute("folderJson")%>;
   $("#testTree").combotree({
	 valueField: 'id',
     textField: 'text',                                  
     data : data1,
     required:true,
     onSelect:function(node){
    	 document.getElementById("toPathId").value = node.id;
    }
 });
   
   $("#bulkTree").combotree({
		 valueField: 'id',
	     textField: 'text',                                  
	     data : data1,
	     required:true,
	     onSelect:function(node){
	    	 document.getElementById("BulktoPathId").value = node.id;
	    }
	 });
/*新添加文件上传部分*/
 	const UPLOAD_FILE_SIZE = 5;//上传文件的最大数量
	var filePos = 0;
	var posArry = new Array(UPLOAD_FILE_SIZE);//指定一次最多上传的文件数
	function change() {
		if( (filePos = getPos()) == -1 )
		{
			alert("最多只允许上传" + UPLOAD_FILE_SIZE + "文件");
			return;
		}
		
		var input_fileUp; //接收文件输入的input
		var showFileList;
				
		var input_file = document.createElement("input");//存储上传的input文件输入框
		input_file.type = "file";
		input_file.name = "uploadFiles";
		input_file.id = "fileUp";
		input_file.onchange = change;

		input_fileUp = document.getElementById("fileUp");//获取现有的fileUp
		input_fileUp.after(input_file);
		input_fileUp.id = "fileUp_"+filePos;
		showFileList = document.getElementById("showFileList");
		
		var input_text = document.createElement("input");//显示信息的input文件输入框
		input_text.type = "text";
		input_text.name = getFileName(input_fileUp.value);
		input_text.id = input_fileUp.id;
		input_text.value = input_text.name;
		input_text.className = "file_text";

		createElems(showFileList, input_text);
	}
	function createElems(showFileList, input_text)
	{
		var br = document.createElement("br");
		var span = document.createElement("span");
		span.className = "glyphicon glyphicon-trash";
		var button = document.createElement("button");
		button.type = "button";
		button.className = "btn btn-xs";
		button.appendChild(span);
		button.onclick = function() {
			br.remove();
			button.remove();
			freePos(input_text.id);
			document.getElementById(input_text.id).remove();
			input_text.remove();
		}
		showFileList.appendChild(br);
		showFileList.appendChild(button);
		showFileList.appendChild(input_text);
		showFileList.appendChild(br);
	}
	function getPos()
	{
		for(var i=0; i<UPLOAD_FILE_SIZE; i++)
		{
			if(posArry[i] == UPLOAD_FILE_SIZE)
			{
				posArry[i] = i;
				return i;
			}
		}
		return -1;
	}
	function freePos(pos)
	{
		var temp_pos = pos.lastIndexOf("_");
		var pos_num = pos.substring(temp_pos+1);
		posArry[pos_num] = UPLOAD_FILE_SIZE;
	}
	function getFileName(file){
	    var pos = file.lastIndexOf("\\");
	    return file.substring(pos+1);  
	}

	window.onload = function(){
		for(var i=0; i<UPLOAD_FILE_SIZE; i++)
		{
			posArry[i] = UPLOAD_FILE_SIZE;
		}
	}
   </script>
</body>
</html>