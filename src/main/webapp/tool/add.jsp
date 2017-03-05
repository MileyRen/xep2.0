<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>CreateTool</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<%@include file="/WEB-INF/headinclude.jsp"%>

<!-- <script type="text/javascript" src="js/jquery-latest.js"></script> -->
    <style type="text/css">
    .main{
    margin-top: 50px;
    margin-left: 100px;
    margin-right: 100px;
    
    }
    
fieldset{padding:.35em .625em .75em;margin:0 2px;border:1px solid silver}

legend{padding:.5em;border:0;width:auto;margin:0;font-size:initial;}
label{font-weight:initial;}

    </style>
</head>

<body>
<div> <%@include file="/WEB-INF/content/navigator.jsp"%></div>
<div class="main">
	<form id="createtool" name="createtool" action="${pageContext.request.contextPath}/tool/add.action"
		onsubmit="return on_submit()" method="post" enctype="multipart/form-data">
		<table class="table table-bordered">
			<tr>
				<td>Tool File：</td>
				<td><input type="file" name="file" id="file" /><span id="chkfilename"></span>
				</td>
			</tr>
			<tr>
				<td>Toolname:</td>
				<td><input type="text" name="toolName" id="toolName" />&nbsp;&nbsp;&nbsp;<span id="chkname"></span>
				</td>
			</tr>
			<tr>
				<td>Save results:</td>
				<td><input type="radio" name="savedResults" value="1" />Yes 
				<input type="radio" name="savedResults" value="0" checked="checked" />No</td>
			</tr>
			<!-- <tr>
				<td>Tooltype:</td>
				<td><select name="tooltype" id="tooltype">
						<option value="0" selected="">first</option>
						<option value="1">hello</option>
				</select>
				</td>
			</tr>
 -->
 
 <tr>
				<td>Tooltype:</td>
				<td>
					<select name="webtooltype">
					<c:forEach items="${sessionScope.ctypes}"  var="s">
						<option value="${s.id}">${s.typeName}</option>
					</c:forEach>
					<lect>
				</td>
			</tr>
 


			<tr>
				<td>Interpreter:</td>
				<td><select name="interpreter" id="interpreter">
						<option value="python" selected="">python</option>
						<option value="perl">perl</option>
						<option value="other">other</option>
				</select>
				</td>
			</tr>

			<tr>
				<td>Share:</td>
				<td><select name="isShared" id="share">
				
						<option value="0" <c:if test="${sessionScope.ctool.isShared==0}">selected</c:if>  >private</option>
	     			<c:if test="${sessionScope.user.roleId==0}">
	     					<option value="1" <c:if test="${sessionScope.ctool.isShared==1}">selected</c:if>  >share</option>
	     			</c:if>
	     			<c:if test="${sessionScope.user.roleId==1}">
						<option value="2" <c:if test="${sessionScope.ctool.isShared==2}">selected</c:if>  >system</option>
					</c:if>
				</select></td>
			</tr>

			<tr>
				<td>Describtion:</td>
				<td><textarea rows="2" cols="100" id="describtion" name="describtion"></textarea>
				</td>
			</tr>

		
			
			<input type="hidden" name="xmlsection" id="xmlsection" value="xml">



			<tr>
				<td>Parameter:</td>
				<td><select name="item" id="item">
						<option value="0" selected>file</option>
						<!-- <option value="1">fileo</option> -->
						<option value="2">text</option>
						<option value="3">integer</option>
						<option value="4">select</option>
						<option value="5">fixed</option>
				</select> &nbsp;
				<!-- <input class="btn btn-info btn-xs" type="button" value="add" id="add"> -->
				<a class="btn btn-info btn-xs" type="button"  id="add">
				   <span class="glyphicon glyphicon-plus"></span> add
				</a>
				</td>
			</tr>


			


			<tr>
				<td></td>
				<td>
					<div id="one">

						<!-- insert label -->
					</div>
				</td>
			</tr>

			
		<!-- 	<tr>
				<td></td>
				<td><textarea rows="7" cols="100" id="area">我是一个文本框。 </textarea>
				</td>
			</tr> -->

			<!-- <tr>
				<td></td>
				<td><input class="btn btn-info btn-xs" style="width:100px;" type="button" onclick="createx()" value="CreateXml" id="cx">
				</td>
			</tr> -->




			<tr>
				<td colspan="2"><input class="btn btn-info " type="submit" value="Create" />
				</td>
			</tr>
		</table>
	</form>
	</div>
</body>


<script type="text/javascript" src="js/createtool.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		$("#toolName").blur(function(){
		var str=$("#toolName").val();
		if(str.trim()!=""){
		$.post("${pageContext.request.contextPath}/ajax/ajaxcheck?op=NameCheck",
		     {toolname:str.trim()},
			function(data,status){
				$("#chkname").html(data);
			}
			);
		}
		else
		 $("#chkname").html("<font color='red'>Please input toolname</font>");
		});
		
		$("#file").change(function(){
		$("#chkfilename").html("");
		var str=$("#file").val();
		if(str!=""){
			var arr=str.split("\\");
			$.post("${pageContext.request.contextPath}/ajax/ajaxcheck?op=FileNameCheck",
		     {filename:arr.pop()},
			function(data,status){
				$("#chkfilename").html(data);
			}
			);
		}
	
		});
	});




	function on_submit() {
	var file = document.createtool.file.value; 
	  if($("#chkfilename").html()!="") {
	  		alert("please check file!");  
            createtool.file.focus();
            return false;
	  }
	  if($("#chkname").html()!="") {
	  		alert("please check toolname!");  
            createtool.toolName.focus();
            return false;
	  }
	  if (file == null||file == ""){  
           alert("please choose file!");  
           createtool.file.focus();
           return false;  
                }  
		if (createtool.toolName.value == "") {
			alert("please input name!");
			createtool.toolName.focus();
			return false;
		}
		if (createtool.describtion.value == "") {
			alert("please input description!");
			createtool.describtion.focus();
			return false;
		}
		for(var i=0;i<document.createtool.elements.length-1;i++)
			   {
				  if(document.createtool.elements[i].value=="")
				  {
					 alert("please input!");
					 document.createtool.elements[i].focus();
					 return false;
				  }
			   }
			   
		createx();
	   
	}

	function createXMLDoc() {
		try //Internet Explorer
		{
			var xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
		} catch (e) {
			try //Firefox, Mozilla, Opera, etc.
			{
				var xmlDoc = document.implementation.createDocument("", "",
						null);
			} catch (e) {
				alert(e.message);
			}
		}
		return xmlDoc;
	}

	function serializeXMLDoc(doc) {
		var text;
		try {
			text = (new XMLSerializer()).serializeToString(doc);
		} catch (e) {
			text = doc.xml;
		}
		return text;
	}

	function createx() {
		//alert("1"); 
		var XmlDoc = createXMLDoc(); //解决浏览器兼容
		var p=XmlDoc.createProcessingInstruction("xml","version='1.0' encoding='utf-8'");
		XmlDoc.appendChild(p);
		

		var tool = XmlDoc.createElement("tool");

		tool.setAttribute("id", "toolid");
		tool.setAttribute("name", document.getElementById("toolName").value);
		tool.setAttribute("path", "xmlpath");
		tool.setAttribute("interpreter", "interpretertext");
		tool.setAttribute("version", "1.0");

		

		description = XmlDoc.createElement("description");
		exit_code = XmlDoc.createElement("exit_code");
		exit_code.setAttribute("value", "1");
		exit_code.setAttribute("description", "description");
		description.appendChild(exit_code);
		tool.appendChild(description);

		//<params>
		params = XmlDoc.createElement("params");

		var one = document.getElementById("one");
		var alldiv = one.getElementsByTagName("div");
		//alert(alldiv.length);

		for ( var i = 0; i < alldiv.length; i++) {

			if (alldiv[i].parentNode.getElementsByTagName("legend")[0].innerHTML == "Input:") {
				//input
				input = XmlDoc.createElement("input");
				input.setAttribute("label", alldiv[i]
						.getElementsByTagName("input")[0].value);
				params.appendChild(input);

			} else if (alldiv[i].parentNode.getElementsByTagName("legend")[0].innerHTML == "Output:") {
				//output
				output = XmlDoc.createElement("output");
				output.setAttribute("id", alldiv[i].getElementsByTagName("input")[0].value+"_id_"+i);
				output.setAttribute("label", alldiv[i]
						.getElementsByTagName("input")[0].value);
				params.appendChild(output);

			} else if (alldiv[i].parentNode.getElementsByTagName("legend")[0].innerHTML == "Fixed:") {
				//type=fixed
				param = XmlDoc.createElement("param");
				param.setAttribute("type", "fixed");
				param.setAttribute("value", alldiv[i]
						.getElementsByTagName("input")[0].value);
				params.appendChild(param);

			} else if (alldiv[i].parentNode.getElementsByTagName("legend")[0].innerHTML== "Integer:") {
				//type=integer
				param = XmlDoc.createElement("param");
				param.setAttribute("type", "integer");
				param.setAttribute("size", alldiv[i]
						.getElementsByTagName("input")[1].value);
				param.setAttribute("label", alldiv[i]
						.getElementsByTagName("input")[0].value);
				params.appendChild(param);

			} else if (alldiv[i].parentNode.getElementsByTagName("legend")[0].innerHTML == "Select:") {
				//type=select
				param = XmlDoc.createElement("param");
				param.setAttribute("type", "select");
				param.setAttribute("label", alldiv[i]
						.getElementsByTagName("input")[0].value);
				

				value = alldiv[i].getElementsByTagName("input")[1].value;
				text = alldiv[i].getElementsByTagName("input")[2].value;

				if (value.charAt(value.length - 1) == ";") {
					value = value.substring(0, value.length - 1);
				}
				if (text.charAt(text.length - 1) == ";") {
					text = text.substring(0, text.length - 1);
				}
				//alert(value);
				//alert(text);

				var valuearray = value.split(";");
				var textarray = text.split(";");
				var count=textarray.length;
				if(valuearray.length<textarray.length){
					count=valuearray.length;
				}
				for(var j=0;j<count;j++){
					option = XmlDoc.createElement("option");
					option.setAttribute("label",textarray[j]);
					text = XmlDoc.createTextNode(valuearray[j]);
					option.appendChild(text);
					param.appendChild(option);

				}

				
				params.appendChild(param);

			} else if (alldiv[i].parentNode.getElementsByTagName("legend")[0].innerHTML == "Text:") {
				//type=text
				//
				param = XmlDoc.createElement("param");
				param.setAttribute("type", "text");
				param.setAttribute("label", alldiv[i]
						.getElementsByTagName("input")[0].value);
				params.appendChild(param);

			}
		}

		tool.appendChild(params);
		//</params>		

		XmlDoc.appendChild(tool);

		//XmlDoc.save("c:\temp1.xml");
		//alert(XmlDoc);

		//alert(serializeXMLDoc(XmlDoc));
		//document.getElementById("area").value = serializeXMLDoc(XmlDoc);
		document.getElementById("xmlsection").value = serializeXMLDoc(XmlDoc);
		//alert(serializeXMLDoc(XmlDoc));
		alert("create success!");
	}

	function cx1() {
		var value = "1;2;3";
		var text = "a;b;c;";
		if (value.charAt(value.length - 1) == ";") {
			value = value.substring(0, value.length - 1);
		}
		if (text.charAt(text.length - 1) == ";") {
			text = text.substring(0, text.length - 1);
		}
		//alert(value);
		//alert(text);

		var valuearray = value.split(";");
		var textarray = text.split(";");

		alert(valuearray);
		alert(textarray);

		//var array=value.split(";");
		//alert(value.length);
		//(value.charAt(value.length-1))
		//alert(array.length);

		//alert(array);

	}
</script>




</html>
