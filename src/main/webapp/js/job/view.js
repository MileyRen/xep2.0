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

$(document).ready(function () {
	$('.pagination').jqPagination({
		link_string: 'job/view.action?page={page_number}',
		current_page: page, //设置当前页 默认为1
		max_page: maxPage, //设置最大页 默认为1
		page_string: '{current_page} th page of {max_page}',
		paged: function (p) {
			if (p == page) {
				return;
			}
			window.open(format.call('job/view.action?page={0}', p - 1), '_self');
		}
	});

	$('#delete').on('click', function() {
		var selected = $('#flowList').children('tr').children('.delete').children('input[type=checkbox]:checked');
		var a = [];
		for(var i=0; i<selected.length; i++) {
			a.push(selected[i].value);
		}
		a = a.join(',');
		a = encodeURIComponent(a);
		a = a.replace(/%20/g, '+');
		window.open('job/delete.action?deleted='+a, '_self');
	});
});
