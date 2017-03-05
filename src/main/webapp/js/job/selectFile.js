/**
 * 本文件的作用是选择文件并且拼接出文件路径的字符串来。
 */

$(document).ready(function () {
	var queryString = window.location.search.substring(1).split('&');
	var selectFolder = false;
	for(var i=0; i<queryString.length; i++) {
		if(queryString[i].startsWith('selectFolder')) {
			selectFolder = queryString[i].split('=')[1] != '0';
		}
	}
	if(selectFolder) {
		$('.st_tree').find('li').addClass('leaf-folder');
	}

	$(window.opener.document).find('input[type=button]').prop('disabled', true);

	$(".st_tree").SimpleTree({
		click: function (a) {
			if ($(a).attr("hasChild") == "false" || (a.dataset.href=="true"&&selectFolder)) {
				var selectedFileId = $(a).prop('id');
				var filePath = getFilePath(selectedFileId);
				window.filePath = filePath;
				window.close();
			}
			a.dataset.href = false;
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
	 * @return {String}
	 */
	function getFilePath(id) {
		var path = "/"+$('#'+id).html();
		var id = $('#'+id)[0].dataset.folder;
		while(id > 0) {
			path = "/"+$('#'+id).html()+path;
			id = $('#'+id)[0].dataset.folder;
		}
		return path;
	}
});
