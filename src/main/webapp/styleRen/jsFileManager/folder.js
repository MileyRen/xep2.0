/**
 * 
 */

function delbulk(arr) {
	var str = "delbulk.action?";
	var i = 0;
	$("input[class='delbulk']:checked").each(
			function() {
				var temp = $(this).val();
				var strArray = new Array();
				strArray = temp.split("[arr]");
				str += "id[" + i + "]=" + strArray[1] + "&type[" + i + "]="
						+ strArray[2] + "&name[" + i + "]=" + (strArray[3])
						+ "&";
				i++;
			});
	str += "delsize=" + i + "&parentFolderId=" + arr;
	var temp = document.createElement("form");
	temp.action = encodeURI(str);
	temp.method = "post";
	temp.style.display = "none";
	document.body.appendChild(temp);
	temp.submit();
}
function MoveList(arr1, arr2) {
	var str = "BulkMove.action?";
	var i = 0;
	$("input[class='delbulk']:checked").each(function() {
		var temp = $(this).val();
		var strArray = new Array();
		strArray = temp.split("[arr]");
		str += "fromId[" + i + "]=" + strArray[1] + "&";
		i++;
	});
	str += "movesize=" + i;
	var temp = document.getElementById("moveBulk");
	temp.action = str;
	temp.method = "post";
	temp.style.display = "none";
	document.body.appendChild(temp);
	temp.submit();
}
function promList_old(arr1, arr2) {
	var str = "BulkMove.action?";
	var i = 0;
	$("input[class='delbulk']:checked").each(function() {
		var temp = $(this).val();
		var strArray = new Array();
		strArray = temp.split("[arr]");
		str += "fromId[" + i + "]=" + strArray[1] + "&";
		i++;
	});
	str += "movesize=" + i;
	var toIdBulk = document.getElementById("BulktoPathId").value;
	str += "&toIdBulk=" + toIdBulk + "&pagesource.currentPage=" + arr1
			+ "&parentFolderId=" + arr2;
	var temp = document.createElement("form");
	temp.action = str;
	temp.method = "post";
	temp.style.display = "none";
	document.body.appendChild(temp);
	temp.submit();
}

// 全选和全不选（第一个参数为复选框名称，第二个参数为是全选还是全不选）
function allCheck(name, boolValue) {
	var allvalue = document.getElementsByClassName(name);
	for (var i = 0; i < allvalue.length; i++) {
		if (allvalue[i].type == "checkbox")
			allvalue[i].checked = boolValue;
	}
}

// 反选 参数为复选框名称
function reserveCheck(name) {
	var revalue = document.getElementsByClassName(name);
	for (i = 0; i < revalue.length; i++) {
		if (revalue[i].checked == true)
			revalue[i].checked = false;
		else
			revalue[i].checked = true;
	}
}

function prom(value) {
	document.getElementById("fromId").value = value;
}
function open() {
	$('#win_move').window('open');
}

function getValue(value) {
	document.getElementById("toPathId").value = value;
}

function getValueTest(value) {
	alert(value);
	document.getElementById("test").value = value;

}

$(document).ready(function() {

	/* 弹出创建文件夹窗口 */
	$("#btncreate").click(function() {
		$("#win_add").window("open");
	});
	/* 弹出上传文件窗口 */
	$("#btnupload").click(function() {
		$("#win_upload").window("open");
	});
});

function post(id) {
	var temp = document.createElement("form");
	temp.action = "folderlist.action?parentFolderId=" + id;
	temp.method = "post";
	temp.style.display = "none";
	document.body.appendChild(temp);
	temp.submit();
	return temp;
}

function into(id) {
	var temp = document.createElement("form");
	temp.action = "pageList.action?pageTag=1&parentFolderId=" + id;
	temp.method = "post";
	temp.style.display = "none";
	document.body.appendChild(temp);
	temp.submit();
	return temp;
}
$('input[id=lefile]').change(function() {
	$('#photoCover').val($(this).val());
});
/** 动态上传文件 */
/*
function addMore() {
	var div = document.getElementById("more");
	var br = document.createElement("br");
	var input = document.createElement("input");
	var button = document.createElement("button");
	var span = document.createElement("span");
	span.className = "glyphicon glyphicon-trash";

	input.type = "file";
	input.name = "uploadFiles";
	input.className = "file";

	button.type = "button";
	button.className = "btn btn-xs";
	button.appendChild(span);
	button.onclick = function() {
		div.removeChild(br);
		div.removeChild(button);
		div.removeChild(input);
		div.removeChild(br);
	}

	div.appendChild(br);
	div.appendChild(button);
	div.appendChild(input);
	div.appendChild(br);

}
*/