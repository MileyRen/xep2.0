/**
 * Created by qi_l on 2016/11/24.
 */

$(document).ready(function () {
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

	$('#add').on('click', function() {
		openWindow();
	});

	$('#delete').on('click', function () {
		var checkedBoxes = $('#show-area')[0].getElementsByTagName('tbody')[0].querySelectorAll('tr > .delete > input[type=checkbox]:checked');
		if (checkedBoxes.length == 0) {
			return;
		}
		var deletedValues = [];
		for (var i = 0; i < checkedBoxes.length; i++) {
			deletedValues.push(checkedBoxes[i].value);
		}
		deletedValues = encodeURIComponent(deletedValues.join());
		window.open(format.call('flowGroup/delete.action?page={0}&deleted={1}', page-1, deletedValues), '_self');
	});

	$('.pagination').jqPagination({
		link_string: 'flowGroup/view.action?page={page_number}',
		current_page: page, //设置当前页 默认为1
		max_page: maxPage, //设置最大页 默认为1
		page_string: '第{current_page}页，共{max_page}页',
		paged: function (p) {
			if (p == page) {
				return;
			}
			window.open(format.call('flowGroup/view.action?page={0}', p - 1), '_self');
		}
	});
});
