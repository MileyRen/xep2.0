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

<title>Edit</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

<script type="text/javascript" src="js/jquery-latest.js"></script>
<script type="text/javascript" src="js/jquerysession.js"></script>
<%@include file="/WEB-INF/headinclude.jsp"%>


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
	<form id="edittool" name="edittool" action="${pageContext.request.contextPath}/tool/edittool.action" onsubmit="return on_submit()"
		method="post" enctype="multipart/form-data">
		<table class="table table-bordered">
			<tr>
				<td>Toolname：</td>
				<td><input type="text" name="toolName" id="toolName"
					value="${sessionScope.ctool.toolName}" /></td>
			</tr>
			<tr>
				<td>Type：</td>
				<td>
				<select name="type">
					<c:forEach items="${sessionScope.ctypes}" var="c">
					<option value="${c.id}"
					<c:if test="${sessionScope.ctool.tooltype.id==c.id}">selected</c:if>
					>${c.typeName}</option>	
								
					</c:forEach>
				</select>
				</td>
			</tr>
			<tr>
				<td>Save results:</td>
				<td><input type="radio" name="savedResults" value="1"
					<c:if test="${sessionScope.ctool.savedResults==1}">checked="checked"</c:if> />Yes
					<input type="radio" name="savedResults" value="0"
					<c:if test="${sessionScope.ctool.savedResults==0}">checked="checked"</c:if> />No
				</td>
			</tr>

			<tr>
				<td>Interpreter:</td>
				<td><select name="interpreter" id="interpreter">
						<option value="python" selected>python</option>
						<option value="perl">perl</option>
						<option value="other">other</option>
				</select></td>
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
				<td><textarea rows="2" cols="100" name="describtion">${sessionScope.ctool.describtion}</textarea>
				</td>
			</tr>

			
			
			<input type="hidden" name="xmlsection" id="xmlsection" value="xml">


			<%-- <tr>
				<td>xml:</td>
				<td><textarea id="xml" rows="3" cols="100" name="xml"
						style="display: none;">${sessionScope.xmlstring}</textarea></td>
			</tr> --%>

			<tr>
				<td>Parameter:</td>
				<td><select name="item" id="item">
						<option value="0" selected>file</option>
						
						<option value="2">text</option>
						<option value="3">integer</option>
						<option value="4">select</option>
						<option value="5">fixed</option>
				</select>
				 <!-- <input class="btn btn-info btn-xs" style="width:100px;" type="button" value="add" id="add"> -->
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
					</div></td>

			</tr>

			

			<!-- <tr>
				<td></td>
				<td><input class="btn btn-info btn-xs" style="width:100px;" type="button" onclick="createx()" value="cx" id="cx">
				</td>
			</tr>
 -->
			<tr>
				<td colspan="2"><input class="btn btn-info" type="submit" value="Edit" />
				</td>
			</tr>
			
			
			
		</table>
	</form>
	</div>
</body>



<script type="text/javascript">

function on_submit() {
	
		if (edittool.toolName.value == "") {
			alert("please input name!");
			edittool.toolName.focus();
			return false;
		}
		if (edittool.describtion.value == "") {
			alert("please input description!");
			edittool.describtion.focus();
			return false;
		}
		for(var i=0;i<document.edittool.elements.length-1;i++)
			   {
				  if(document.edittool.elements[i].value=="")
				  {
					 alert("please input!");
					 document.edittool.elements[i].focus();
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
				output.setAttribute("id", alldiv[i]
						.getElementsByTagName("input")[0].value+"_id_"+i);
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
		console.log(XmlDoc);

		//alert(serializeXMLDoc(XmlDoc));
		//document.getElementById("area").value = serializeXMLDoc(XmlDoc);
		document.getElementById("xmlsection").value = serializeXMLDoc(XmlDoc);
		alert("edit success!");
	
	}


	function cc(xmlString) {
		var xmlDoc = null;
		//判断浏览器的类型
		//支持IE浏览器
		if (!window.DOMParser && window.ActiveXObject) { //window.DOMParser 判断是否是非ie浏览器
			var xmlDomVersions = [ 'MSXML.2.DOMDocument.6.0',
					'MSXML.2.DOMDocument.3.0', 'Microsoft.XMLDOM' ];
			for ( var i = 0; i < xmlDomVersions.length; i++) {
				try {
					xmlDoc = new ActiveXObject(xmlDomVersions[i]);
					xmlDoc.async = false;
					xmlDoc.loadXML(xmlString); //loadXML方法载入xml字符串
					break;
				} catch (e) {
				}
			}
		}
		//支持Mozilla浏览器
		else if (window.DOMParser && document.implementation
				&& document.implementation.createDocument) {
			try {
				/* DOMParser 对象解析 XML 文本并返回一个 XML Document 对象。
				 * 要使用 DOMParser，使用不带参数的构造函数来实例化它，然后调用其 parseFromString() 方法
				 * parseFromString(text, contentType) 参数text:要解析的 XML 标记 参数contentType文本的内容类型
				 * 可能是 "text/xml" 、"application/xml" 或 "application/xhtml+xml" 中的一个。注意，不支持 "text/html"。
				 */
				domParser = new DOMParser();
				xmlDoc = domParser.parseFromString(xmlString, 'text/xml');
			} catch (e) {
			}
		} else {
			return null;
		}
		return xmlDoc;
	}
	

	function cx1() {
	
	//createx();
	
	
		//var ss=document.getElementById("xml").innerHTML;
		//var a='<%=session.getAttribute("xmlstring") %>';
		//console.log(a);
		
		//xxmlDoc=cc(a);
		//console.log(xxmlDoc);
		
		//var tool=xmlDoc.getElementsByTagName("tool");
		//var me=tool[0];
		//alert(me.getAttribute("id"));
			
		//alert(s1);
		//console.log(ss);
		//var xmlDoc=cc(ss);
		//alert(xmlDoc);
		//console.log(xmlDoc);

		//alert("1");

		//var tool=xmlDoc.getElementsByTagName("tool");
		//var me=tool[0];
		//alert(me.getAttribute("id"));
	
	}
</script>
<script type="text/javascript">

$(document).ready(function () {
	//alert("aaa");
	//
	var id_index = 0;
	var input_count=0;
	var output_count=0;
	var text_count=0;
	var integer_count=0;
	var select_count=0;
	var fixed_count=0;
	//	
	//alert("huanying");
	
	var a='<%=session.getAttribute("xmlstring")%>';
	console.log(a);
		
	var xxmlDoc=cc(a);
	console.log(xxmlDoc);
	
	//var tool=xxmlDoc.getElementsByTagName("tool");
	//var me=tool[0];
	//alert(me.getAttribute("id"));

	//var a=$.session.get("xmlstring");

	//console.log(xxmlDoc);
	
	
	var $inall=$(xxmlDoc).find("params").children("input");	
	$.each($inall,function(key,element){	
	id_index++;
	//$("#one").append($('<div id="div_'+id_index+'" ></div>'));
	$("#one").append($('<fieldset id="fieldset_'+id_index+'" ><legend>iteam</legend></fieldset>'));
	$("#fieldset_"+id_index).append($('<div id="div_'+id_index+'" ></div>'));
	input_count++;
	$("#fieldset_"+id_index).find("legend")[0].innerHTML="Input:";
	
	$("#div_"+id_index).append($('<label style="display:inline-block;width:50px;">IO:</label>'));
			
			//$("#div_"+id_index).append($('<select name="io" id="io"><option value="0" selected>input</option><option value="1">output</option></select><br />'));
			var pp = $('<select name="io" id="io"><option value="0" selected>input</option><option value="1">output</option></select><br />');
			
			pp.on('change', function(){
				opchange(pp);

			});
			$("#div_"+id_index).append(pp);
	
	$("#div_"+id_index).append($('<label style="display:inline-block;width:50px;">Label:</label>'));
	$("#div_"+id_index).append($('<input type="text" name="input_label_'+input_count+'" id="input_label_'+input_count+'">&nbsp;&nbsp;&nbsp;&nbsp;//the label of io<br />'));
	$("#input_label_"+input_count).val($(element).attr("label"));	
var p = $('<a class="btn btn-info btn-xs" type="button"><span class="glyphicon glyphicon-trash"></span> delete</a><br />');
	p.on('click', function(){
		delFunc(p);
	});
	$("#div_"+id_index).append(p);
	});

	var $outall=$(xxmlDoc).find("params").children("output");	
	$.each($outall,function(key,element){	
	id_index++;
	//$("#one").append($('<div id="div_'+id_index+'" ></div>'));
	$("#one").append($('<fieldset id="fieldset_'+id_index+'" ><legend>iteam</legend></fieldset>'));
	$("#fieldset_"+id_index).append($('<div id="div_'+id_index+'" ></div>'));
	output_count++;
	$("#fieldset_"+id_index).find("legend")[0].innerHTML="Output:";
	
	$("#div_"+id_index).append($('<label style="display:inline-block;width:50px;">IO:</label>'));
			
			//$("#div_"+id_index).append($('<select name="io" id="io"><option value="0" selected>input</option><option value="1">output</option></select><br />'));
			var pp = $('<select name="io" id="io"><option value="0">input</option><option value="1" selected>output</option></select><br />');
			
			pp.on('change', function(){
				opchange(pp);

			});
			$("#div_"+id_index).append(pp);
	
	//$("#div_"+id_index).append($('<label style="display:inline-block;width:50px;">Id:</label>'));
	//$("#div_"+id_index).append($('<input type="text" name="output_id_'+output_count+'" id="output_id_'+output_count+'">&nbsp;&nbsp;&nbsp;&nbsp;//Id of output<br />'));
	$("#div_"+id_index).append($('<label style="display:inline-block;width:50px;">Label:</label>'));
	$("#div_"+id_index).append($('<input type="text" name="output_label_'+output_count+'" id="output_label_'+output_count+'">&nbsp;&nbsp;&nbsp;&nbsp;//label of io<br />'));
	$("#output_label_"+output_count).val($(element).attr("label"));	
	//$("#output_id_"+output_count).val($(element).attr("id"));	
	var p = $('<a class="btn btn-info btn-xs" type="button"><span class="glyphicon glyphicon-trash"></span> delete</a><br />');
	p.on('click', function(){
		delFunc(p);

	});
	$("#div_"+id_index).append(p);
	});


	var $paramall=$(xxmlDoc).find("params").children("param");	
	$.each($paramall,function(key,element){	
	id_index++;
	//$("#one").append($('<div id="div_'+id_index+'" ></div>'));
	$("#one").append($('<fieldset id="fieldset_'+id_index+'" ><legend>iteam</legend></fieldset>'));
	$("#fieldset_"+id_index).append($('<div id="div_'+id_index+'" ></div>'));
	var typesel=$(element).attr("type");
	//console.log(typesel);
	if(typesel=="text"){
			//text
			text_count++;
			$("#fieldset_"+id_index).find("legend")[0].innerHTML="Text:";
			
			$("#div_"+id_index).append($('<label style="display:inline-block;width:50px;">Label:</label>'));
			$("#div_"+id_index).append($('<input type="text" name="text_label_'+text_count+'" id="text_label_'+text_count+'">&nbsp;&nbsp;&nbsp;&nbsp;//label of text<br />'));

			$("#text_label_"+text_count).val($(element).attr("label"));	
			var p = $('<a class="btn btn-info btn-xs" type="button"><span class="glyphicon glyphicon-trash"></span> delete</a><br />');
			p.on('click', function(){
				delFunc(p);

			});
			$("#div_"+id_index).append(p);

		}else if(typesel=="integer"){
			//integer
			integer_count++;
			$("#fieldset_"+id_index).find("legend")[0].innerHTML="Integer:";
			
			$("#div_"+id_index).append($('<label style="display:inline-block;width:50px;">Label:</label>'));
			$("#div_"+id_index).append($('<input type="text" name="integer_label_'+integer_count+'" id="integer_label_'+integer_count+'">&nbsp;&nbsp;&nbsp;&nbsp;//label of integer<br />'));
			$("#div_"+id_index).append($('<label style="display:inline-block;width:50px;">Size:</label>'));
			$("#div_"+id_index).append($('<input type="text" name="integer_size_'+integer_count+'" id="integer_size_'+integer_count+'">&nbsp;&nbsp;&nbsp;&nbsp;//width of integer<br />'));

			$("#integer_size_"+integer_count).val($(element).attr("size"));	
			$("#integer_label_"+integer_count).val($(element).attr("label"));	
			var p = $('<a class="btn btn-info btn-xs" type="button"><span class="glyphicon glyphicon-trash"></span> delete</a><br />');
			p.on('click', function(){
				delFunc(p);

			});
			$("#div_"+id_index).append(p);



		}else if(typesel=="select"){
			//select
			select_count++;
			$("#fieldset_"+id_index).find("legend")[0].innerHTML="Select:";
			
			$("#div_"+id_index).append($('<label style="display:inline-block;width:50px;">Label:</label>'));
			$("#div_"+id_index).append($('<input type="text" name="select_label_'+select_count+'" id="select_label_'+select_count+'">&nbsp;&nbsp;&nbsp;&nbsp;//label of select<br />'));
			$("#div_"+id_index).append($('<label style="display:inline-block;width:50px;">Value:</label>'));
			$("#div_"+id_index).append($('<input type="text" name="select_value_'+select_count+'" id="select_value_'+select_count+'">&nbsp;&nbsp;&nbsp;&nbsp;//values of select,separate multiple values with a semicolon<br />'));
			$("#div_"+id_index).append($('<label style="display:inline-block;width:50px;">Text:</label>'));
			$("#div_"+id_index).append($('<input type="text" name="select_text_'+select_count+'" id="select_text_'+select_count+'">&nbsp;&nbsp;&nbsp;&nbsp;//texts of select,separate multiple texts with a semicolon<br />'));

			var olabel="";
			var otext="";
			var $optionall=$(element).children("option");	
	        $.each($optionall,function(okey,oelement){
	        	olabel+=$(oelement).attr("label")+";";
	        	otext+=$(oelement).text()+";";

	        	});
	        //console.log(olabel);
	        //console.log(otext);


			$("#select_label_"+select_count).val($(element).attr("label"));	
			$("#select_value_"+select_count).val(olabel);	
			$("#select_text_"+select_count).val(otext);	
			var p = $('<a class="btn btn-info btn-xs" type="button"><span class="glyphicon glyphicon-trash"></span> delete</a><br />');
			p.on('click', function(){
				delFunc(p);

			});
			$("#div_"+id_index).append(p);

		}else if(typesel=="fixed"){
			//fixed
			fixed_count++;
			$("#fieldset_"+id_index).find("legend")[0].innerHTML="Fixed:";
			
			$("#div_"+id_index).append($('<label style="display:inline-block;width:50px;">Value:</label>'));
			$("#div_"+id_index).append($('<input type="text" name="fixed_value_'+fixed_count+'" id="fixed_value_'+fixed_count+'">&nbsp;&nbsp;&nbsp;&nbsp;//value of fixed<br />'));

			$("#fixed_value_"+fixed_count).val($(element).attr("value"));	
			var p = $('<a class="btn btn-info btn-xs" type="button"><span class="glyphicon glyphicon-trash"></span> delete</a><br />');
			p.on('click', function(){
				delFunc(p);

			});
			$("#div_"+id_index).append(p);
			
		}

	});
	
	
	//	
	$("#add").on('click',function(){
		id_index++;
		

		var selectitem=$("#item").val();
		$("#one").append($('<fieldset id="fieldset_'+id_index+'" ><legend>iteam</legend></fieldset>'));
		$("#fieldset_"+id_index).append($('<div id="div_'+id_index+'" ></div>'));



		if(selectitem==0){
			//input
			input_count++;
			$("#fieldset_"+id_index).find("legend")[0].innerHTML="Input:";
			
			$("#div_"+id_index).append($('<label style="display:inline-block;width:50px;">IO:</label>'));
			
			//$("#div_"+id_index).append($('<select name="io" id="io"><option value="0" selected>input</option><option value="1">output</option></select><br />'));
			var pp = $('<select name="io" id="io"><option value="0" selected>input</option><option value="1">output</option></select><br />');
			
			pp.on('change', function(){
				opchange(pp);

			});
			$("#div_"+id_index).append(pp);
			
			$("#div_"+id_index).append($('<label style="display:inline-block;width:50px;">Label:</label>'));
			$("#div_"+id_index).append($('<input type="text" name="input_label_'+input_count+'" id="input_label_'+input_count+'">&nbsp;&nbsp;&nbsp;&nbsp;//label of input<br />'));
			
			var p = $('<a class="btn btn-info btn-xs" type="button"><span class="glyphicon glyphicon-trash"></span> delete</a><br />');
			p.on('click', function(){
				delFunc(p);

			});
			$("#div_"+id_index).append(p);

		}
		
		/* else if(selectitem==1){
			//output
			output_count++;
			$("#fieldset_"+id_index).find("legend")[0].innerHTML="Output:";
			
			//$("#div_"+id_index).append($('<label style="display:inline-block;width:50px;">Id:</label>'));
			//$("#div_"+id_index).append($('<input type="text" name="output_id_'+output_count+'" id="output_id_'+output_count+'">&nbsp;&nbsp;&nbsp;&nbsp;//Id of output<br />'));
			$("#div_"+id_index).append($('<label style="display:inline-block;width:50px;">Label:</label>'));
			$("#div_"+id_index).append($('<input type="text" name="output_label_'+output_count+'" id="output_label_'+output_count+'">&nbsp;&nbsp;&nbsp;&nbsp;//label of output<br />'));
			
			var p = $('<a class="btn btn-info btn-xs" type="button"><span class="glyphicon glyphicon-trash"></span> delete</a><br />');
			p.on('click', function(){
				delFunc(p);

			});
			$("#div_"+id_index).append(p);

		} */
		
		else if(selectitem==2){
			//text
			text_count++;
			$("#fieldset_"+id_index).find("legend")[0].innerHTML="Text:";
			
			
			$("#div_"+id_index).append($('<label style="display:inline-block;width:50px;">Label:</label>'));
			$("#div_"+id_index).append($('<input type="text" name="text_label_'+text_count+'" id="text_label_'+text_count+'">&nbsp;&nbsp;&nbsp;&nbsp;//label of text<br />'));
			
			var p = $('<a class="btn btn-info btn-xs" type="button"><span class="glyphicon glyphicon-trash"></span> delete</a><br />');
			p.on('click', function(){
				delFunc(p);

			});
			$("#div_"+id_index).append(p);

		}else if(selectitem==3){
			//integer
			integer_count++;
			$("#fieldset_"+id_index).find("legend")[0].innerHTML="Integer:";
			
			
			$("#div_"+id_index).append($('<label style="display:inline-block;width:50px;">Label:</label>'));
			$("#div_"+id_index).append($('<input type="text" name="integer_label_'+integer_count+'" id="integer_label_'+integer_count+'">&nbsp;&nbsp;&nbsp;&nbsp;//label of integer<br />'));
			$("#div_"+id_index).append($('<label style="display:inline-block;width:50px;">Size: </label>'));
			$("#div_"+id_index).append($('<input type="text" name="integer_size_'+integer_count+'" id="integer_size_'+integer_count+'">&nbsp;&nbsp;&nbsp;&nbsp;//width of integer<br />'));
			
			var p = $('<a class="btn btn-info btn-xs" type="button"><span class="glyphicon glyphicon-trash"></span> delete</a><br />');
			p.on('click', function(){
				delFunc(p);

			});
			$("#div_"+id_index).append(p);


		}else if(selectitem==4){
			//select
			select_count++;
			$("#fieldset_"+id_index).find("legend")[0].innerHTML="Select:";
			
			
			$("#div_"+id_index).append($('<label style="display:inline-block;width:50px;">Label:</label>'));
			$("#div_"+id_index).append($('<input type="text" name="select_label_'+select_count+'" id="select_label_'+select_count+'">&nbsp;&nbsp;&nbsp;&nbsp;//label of select<br />'));
			$("#div_"+id_index).append($('<label style="display:inline-block;width:50px;">Value:</label>'));
			$("#div_"+id_index).append($('<input type="text" name="select_value_'+select_count+'" id="select_value_'+select_count+'">&nbsp;&nbsp;&nbsp;&nbsp;//values of select,separate multiple values with a semicolon<br />'));
			$("#div_"+id_index).append($('<label style="display:inline-block;width:50px;">Text: </label>'));
			$("#div_"+id_index).append($('<input type="text" name="select_text_'+select_count+'" id="select_text_'+select_count+'">&nbsp;&nbsp;&nbsp;&nbsp;//texts of select,separate multiple texts with a semicolon<br />'));
			
			var p = $('<a class="btn btn-info btn-xs" type="button"><span class="glyphicon glyphicon-trash"></span> delete</a><br />');
			p.on('click', function(){
				delFunc(p);

			});
			$("#div_"+id_index).append(p);

		}else if(selectitem==5){
			//fixed
			fixed_count++;
			$("#fieldset_"+id_index).find("legend")[0].innerHTML="Fixed:";
			
			
			$("#div_"+id_index).append($('<label style="display:inline-block;width:50px;">Value: </label>'));
			$("#div_"+id_index).append($('<input type="text" name="fixed_value_'+fixed_count+'" id="fixed_value_'+fixed_count+'">&nbsp;&nbsp;&nbsp;&nbsp;//value of fixed<br />'));
			
			var p = $('<a class="btn btn-info btn-xs" type="button"><span class="glyphicon glyphicon-trash"></span> delete</a><br />');
			p.on('click', function(){
				delFunc(p);

			});
			$("#div_"+id_index).append(p);
			
		}
	
	});

	function delFunc(a) {
		a.parent().parent().remove();
	}
	function opchange(a) {
		var iosle=a.val();
		if(iosle==0){
			a.parent().parent().find("legend")[0].innerHTML="Input:";
		}else{
			a.parent().parent().find("legend")[0].innerHTML="Output:";
		}
		
	}
	
	
});

</script>
</html>
