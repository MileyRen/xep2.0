/**
 * 本文件的作用是根据流程输出表生成列表
 */

$(document).ready(function () {
	$(window.opener.document).find('input[type=button]').prop('disabled', true);
	var outputFileList = window.opener.outputFileList;
	var $max = 0;

	$('#tree').append(makeHTML());

	$(".st_tree").SimpleTree({
		click: function (a) {
			if ($(a).attr("hasChild") == "false") {
				var selectedFileId = $(a).prop('id');
				var filePath = getFilePath(selectedFileId);
				window.filePath = filePath;
				window.close();
			}
		}
	});

	$(window).on('beforeunload', function() {
		$(window.opener.document).find('input[type=button]').prop('disabled', false);
	});

	// 禁止右键菜单
	$(document).on('contextmenu', function (e) {
		e.preventDefault();
	});

	/**
	 * @param {String} id
	 * @return {Object}
	 */
	function getFilePath(id) {
		var path = "/"+$('#'+id).html();
		var idA = [];
		idA.push($('#'+id)[0].dataset.id);
		var folder = $('#'+id)[0].dataset.folder;
		var p = $('#'+id).parent().parent().parent().children('li');
		for(var i=0; i<p.length; i++) {
			if($(p[i]).children()[0].dataset.id == folder) {
				path = "/"+$(p[i]).children().html()+path;
				idA.push($(p[i]).children()[0].dataset.id);
				break;
			}
		}
		return {path: path, idA:idA};
	}

	function makeHTML() {
		var container = document.createElement('ul');
		for(var i in outputFileList) {
			if(i=='currentID' || i=='setID') {
				continue;
			}
			var li = document.createElement('li');
			var a = document.createElement('a');
			$(a).prop('href', 'javascript:;');
			$(a).prop('id', genID());
			a.dataset.id = i;
			a.dataset.folder = '-1';
			a.dataset.type = 'folder';
			$(a).html(outputFileList[i].name);
			$(li).append(a);
			$(container).append(li);
			var ul = document.createElement('ul');
			for(var j in outputFileList[i].children) {
				li = document.createElement('li');
				a = document.createElement('a');
				$(a).prop('href', 'javascript:;');
				$(a).prop('id', genID());
				a.dataset.id = j;
				a.dataset.folder = i;
				a.dataset.type = 'file';
				$(a).html(outputFileList[i].children[j].label);
				$(li).append(a);
				$(ul).append(li);
			}
			$(container).append(ul);
		}
		return container;
	}

	/**
	 * 生成'unique-'+$max
	 */
	function genID() {
		$max++;
		return 'unique-' + $max;
	}

	/**
	 * 当前的'unique-'+$max
	 */
	function currentID() {
		return 'unique-' + $max;
	}
});
