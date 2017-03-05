/**
 * Created by qi_l on 2016/11/24.
 */
var viewJSP = {
	clear: function () {
		for (var i in this) {
			if (i == 'clear') {
				continue;
			}
			if (this.hasOwnProperty(i)) {
				delete this[i];
			}
		}
	}
};		// view.jsp 专门的变量空间
for (var i = 0; i < page.length; i++) {
	if (page[i].startsWith('page=')) {
		page = page[i].substring(5);
		break;
	}
}
if (page instanceof Array) {
	page = '0';
}
if (page == 'start') {
	page = '0';
} else if (page == 'latest') {
	page = maxPage - 1;
}
page = parseInt(page) + 1;

var linkDisabled = false;

$get('add').addEventListener('click', function () {
	var worker = $get('work-area').querySelector('.worker');
	worker.getElementsByClassName('name')[0].value = "";
	worker.getElementsByClassName('privilege')[0].selectedIndex = 0;
	var offsetTop = getElCoordinate($get('show-area')).top-76;
	var offsetLeft = getElCoordinate($get('show-area')).left-150;
	offsetTop += $get('show-area').offsetHeight+3;
	$get('work-area').style.left = offsetLeft + 'px';
	$get('work-area').style.top = offsetTop + 'px';
	$get('work-area').style.display = 'block';
	$('#work-area > .table').css('display', 'table');

	$get('add').disabled = true;
	$get('delete').disabled = true;
	linkDisabled = true;

	viewJSP.clear();

	$get('confirm').disabled = false;
	$get('cancel').disabled = false;
});

$get('delete').addEventListener('click', function () {
	var checkedBoxes = $get('show-area').getElementsByTagName('tbody')[0].querySelectorAll('tr > .delete > input[type=checkbox]:checked');
	if (checkedBoxes.length == 0) {
		return;
	}
	var deletedValues = [];
	for (var i = 0; i < checkedBoxes.length; i++) {
		deletedValues.push(checkedBoxes[i].value);
	}
	deletedValues = encodeURIComponent(deletedValues.join());
	window.open(format.call('flow/delete.action?page={0}&deleted={1}', page-1, deletedValues), '_self');
});

$get('confirm').addEventListener('click', function () {
	// $('work-area').style.display = 'none';
	// 弹出窗口
	// 指明是要创建还是修改
	var worker = $get('work-area').querySelector('.worker');
	var flowName = worker.getElementsByClassName('name')[0].value;
//	var groupId = worker.getElementsByClassName('group')[0].value;
	var auth = worker.getElementsByClassName('privilege')[0].value;
	if (!flowName) {
		return;
	}
	viewJSP.flowName = flowName;
//	viewJSP.groupId = groupId;
	viewJSP.auth = auth;
	var url = format.call('flow/modify.action?flowName={0}&auth={1}', flowName, auth);

	$get('confirm').disabled = true;
	$get('cancel').disabled = true;

	if (viewJSP.id) {
		window.open(url + '&id=' + viewJSP.id, '_blank');
	} else {
		window.open(url, '_blank');
	}

	// 完成修改的部分在子窗口中完成
});

$get('cancel').addEventListener('click', function () {
	$get('work-area').style.display = 'none';

	$get('add').disabled = false;
	$get('delete').disabled = false;
	linkDisabled = false;
});

$get('filterDate').addEventListener('click', function() {
	var startDate = $get('startDate').value;
	var endDate = $get('endDate').value;
	var groupId = $('#filterDateForm .group-filter')[0].value;
	var url = format.call("flow/view.action?page={0}", page - 1);
	if(startDate) {
		url = url+format.call("&startDate={0}", startDate);
	}
	if(endDate) {
		url = url+format.call("&endDate={0}", endDate);
	}
	if(groupId) {
		url = url+format.call("&auth={0}", groupId);
	}
//	console.log(url);
	window.open(url, "_self");
});

function deleteOneColumn(id) {
	window.open(format.call('flow/delete.action?page={0}&deleted={1}', page-1, id), '_self');
}

function setViewJSP(id, name, auth, groupId) {
	if(linkDisabled) {
		return;
	}

	viewJSP.id = id;
	viewJSP.flowName = name;
	viewJSP.auth = auth;
//	viewJSP.groupId = groupId;
	window.open(format.call("flow/detail.action?flowName={0}&auth={1}&id={2}", name, auth, id), "_blank");
}

function modifyColumn(item, id, name, auth, groupId) {
	if(linkDisabled) {
		return;
	}

	console.log(item);
	var worker = $get('work-area').querySelector('.worker');
	worker.getElementsByClassName('name')[0].value = name;
//	worker.getElementsByClassName('group')[0].value = groupId;
	worker.getElementsByClassName('privilege')[0].value = auth;
	var offsetTop = getElCoordinate(item).top-78;
	var offsetLeft = getElCoordinate($get('show-area')).left-150;
	offsetTop += 4;
	$get('work-area').style.left = offsetLeft + 'px';
	$get('work-area').style.top = offsetTop + 'px';
	$get('work-area').style.display = 'block';
	$('#work-area > .table').css('display', 'none');

	$get('add').disabled = true;
	$get('delete').disabled = true;
	linkDisabled = true;

	viewJSP.clear();
	viewJSP.id = id;

	$get('confirm').disabled = false;
	$get('cancel').disabled = false;
}

// 子窗口调用，完成其他信息
function save(flowNum, xmlStr) {
	// 进行后台通信与写入数据库
	var form = new FormData();
	form.append('id', viewJSP.hasOwnProperty('id') ? viewJSP.id : 0);
	form.append('flow', xmlStr);
	form.append('flowNum', flowNum);
	form.append('name', viewJSP.flowName);
	form.append('auth', viewJSP.auth);
//	form.append('groupId', viewJSP.groupId);
	form.append('createDate', $get('work-area').querySelector('.table > tbody > tr > .create-date').innerHTML);
	console.log(form);

	fetch('flow/modify-commit.action', {
		method: 'POST',
		credentials: 'same-origin',
		body: form
	}).then(function (response) {
		if (response.status == 200) {
			// 更新窗口值
			if (!viewJSP.id) {
				window.location.replace('flow/view.action?page=latest');
			} else {			// 现在已经在正确的页面上了
				window.location.reload();
			}
		}
	});
}

$(document).ready(function () {
	$('.pagination').jqPagination({
		link_string: 'flow/view.action?page={page_number}',
		current_page: page, //设置当前页 默认为1
		max_page: maxPage, //设置最大页 默认为1
		page_string: '第{current_page}页，共{max_page}页',
		paged: function (p) {
			if (p == page) {
				return;
			}
			window.open(format.call('flow/view.action?page={0}', p - 1), '_self');
		}
	});
});

function $get(id) {
	return document.getElementById(id);
}
