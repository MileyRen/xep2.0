$(document).ready(function () {
	var queryString = window.location.search.substring(1).split('&');
	var title = "Node Information"
		, id;
	for (var i = 0; i < queryString.length; i++) {
		queryString[i] = queryString[i].split('=');
		queryString[i][1] = decodeURIComponent(queryString[i][1]);

		if (queryString[i][0] == 'id') {
			id = queryString[i][1];
		} else if (queryString[i][0] == 'name') {
			title = queryString[i][1];
		}
	}

	if (!id || !window.opener) {
		alert('Information fragmentary，window closed.');
		window.close();
	}

	// $('.nodeName').attr('value', title);

	if (title != 'Node Information') {
		title += ' Detail';
	}
	document.title = title;

	$(window).on('beforeunload', closeAction);

	var tools = window.opener.tools;		// xmlPath对应的是文本文档
	var toolTypes = window.opener.toolTypes;
	var toolInfos = {};						// 文档转化后的结果，使用ID作为索引，储存全部工具

	var $max = 0;		// 全部隐藏ID等需要序列化的东西的取值地方
	var prefix = 'modify-hidden-id-';		// ID的前缀
	var selectInfos = {};					// 存储select对应的dom信息，键值对id：{}，在改变工具后，这个应当被彻底清空

	var attrIdentity = 'option-value';		// 标识储存选项的节点
	var containerIdentity = 'container-identity';		// 标识容器的类

	var pSelectedToolIndex;			// 当前选定的工具的索引

	(function (id) {
		// 装填工具
		for (var i = 0; i < tools.length; i++) {
//			var option = document.createElement('option');
//			option.value = tools[i].id;
//			option.innerHTML = tools[i].toolName;
//			option.dataset.color = tools[i].color || 'white';
//			option.dataset.toolTypeId = tools[i].toolTypeId;
//			$('#form > p > .tool').append(option);

			// 转化xml
			toolInfos[tools[i].id] = {
				name: tools[i].toolName,
				savedResults: tools[i].savedResults,
				xml: $(new DOMParser().parseFromString(tools[i].xmlPath, 'text/xml')).children('tool')[0]
			};
		}

		for (var i = 0; i < toolTypes.length; i++) {
			var option = document.createElement('option');
			option.value = toolTypes[i].id;
			option.innerHTML = toolTypes[i].typeName;
			$('#tools-select .tool-type').append(option);
		}

		// 已经有的节点信息
		// toolId: toolId,
		// toolName: toolName,
		// xml: toolDom
		var nodeInfor = window.opener.instance.getNodeInformation(id) 
			|| {toolId: tools[0].id							// 空
				, toolName: tools[0].toolName
				, xml: toolInfos[tools[0].id].xml};

		for(var i=0; i<tools.length; i++) {
			if(tools[i].id == nodeInfor.toolId) {
				$('#tools-select .tool-type')[0].value = tools[i].toolTypeId;
				break;
			}
		}
		var toolTypeId = $('#tools-select .tool-type')[0].value;
		for(var i=0; i<tools.length; i++) {
			if(toolTypeId == tools[i].toolTypeId) {
				var option = document.createElement('option');
				option.value = tools[i].id;
				option.innerHTML = tools[i].toolName;
				option.dataset.color = tools[i].color || 'white';
				option.dataset.toolTypeId = tools[i].toolTypeId;
				$('#tools-select .tool').append(option);
			}
		}
		$('#tools-select .tool')[0].value = nodeInfor.toolId;

		// 装填infos数组以及fieldset
		if(!nodeInfor.xml) {
			return;
		}
		// var params = dom.getElementsByTagName('params')[0].children;

		// 加载节点名字
		$('.node-name').prop('value', $($('.tool').children('option')[$('.tool')[0].selectedIndex]).html());

		var fieldset = $('#form > .fieldset')[0];
		// 此处修改为tbody
		// TODO
		loadTool($(nodeInfor.xml).children('params')[0], $(fieldset).children('table').children('tbody')[0]);		// 目前直接使用fieldset作为最外部的container

		pSelectedToolIndex = $('#tools-select .tool')[0].value;
	})(id);

	// 工具改变
	$('.tool').on('change', function (e) {
		$('#form > .fieldset').children('table').children('tbody').empty();
		selectInfos = {};

		$('.node-name').prop('value', $($('.tool').children('option')[$('.tool')[0].selectedIndex]).html());
		loadTool($(toolInfos[e.target.value].xml).children('params')[0], $('#form > .fieldset').children('table').children('tbody')[0]);
	});

	$('.tool-type').on('change', function (e) {
		// 重置新的tool的select，设置初始值
		$('#tools-select .tool').children().remove();
		for(var i=0; i<tools.length; i++) {
			if(e.target.value == tools[i].toolTypeId) {
				var option = document.createElement('option');
				option.value = tools[i].id;
				option.innerHTML = tools[i].toolName;
				option.dataset.color = tools[i].color || 'white';
				option.dataset.toolTypeId = tools[i].toolTypeId;
				$('#tools-select .tool').append(option);
			}
		}
		$('#tools-select .tool')[0].selectedIndex = 0;
		// 执行tool的select的数据
		$('#form > .fieldset').children('table').children('tbody').empty();
		selectInfos = {};

		if($('#tools-select .tool')[0].value) {
			$('.node-name').prop('value', $($('.tool').children('option')[$('.tool')[0].selectedIndex]).html());
			loadTool($(toolInfos[$('#tools-select .tool')[0].value].xml).children('params')[0], $('#form > .fieldset').children('table').children('tbody')[0]);
		}
	});

	$('#confirm').on('click', function () {
		// 获取生成的xml文件
		// 将xml文件添加进流程之中

		var form = document.forms['form'];
		if (form.checkValidity() == false) {
			$(form).children('p').children('.submit').click();
			return;
		}

		var selectedToolIndex = $('#tools-select .tool')[0].value;
		var toolId = $("#tools-select .tool")[0].value;
		var flowToolDoc = toolInfos[selectedToolIndex].xml;
		var flowTool = $(flowToolDoc)[0];
		var name = $(flowTool).attr('name');
		$(flowTool).attr('id', id);
		$(flowTool).attr('tool-id', toolId);
		// TODO 此处的获取数据是有问题的
		saveTool(getMostInsideContainer($(form).children('.fieldset')[0]), $(flowTool).children('params')[0]);
		if(toolInfos[selectedToolIndex].savedResults && toolInfos[selectedToolIndex].savedResults != 0) {
			$(flowTool).find('output').attr('value', '-1');
		}

		var pWin = window.opener;
		pWin.instance.setNodeInformation(id, toolId, name, flowToolDoc);
		if(pSelectedToolIndex != selectedToolIndex) {
			// 删除掉所有的连线
			var inst = pWin.instance.getInstance();
			var s = [];
			for(var i in inst.$lineData) {
				if(inst.$lineData[i].from == id || inst.$lineData[i].to == id) {
					s.push(i);
				}
			}
			for(var i=0; i<s.length; i++) {
				inst.delLine(s[i]);
			}
		}

		var nodeName = $('.node-name').prop('value');
		resetPWindow(nodeName, $("#tools-select .tool")[0].selectedOptions[0].dataset.color);
		window.close();
	});

	$('#cancel').on('click', function () {
		window.close();
	});

	/**
	 * 获取储存属性的容器的最里面一层
	 * @param {Element} container 容器
	 */
	function getMostInsideContainer(container) {
		if(!container) {
			return;
		}
		if(container.tagName == 'TR') {
			container = $(container).children('td')[0];
		}
		return $(container).children('table').children('tbody')[0];
	}

	function getRealSelect(container) {
		return $(container).find('.'+attrIdentity)[0];
	}

	/**
	 * 根据获得的选项填充文档数据
	 * 选项每次递归填充一层
	 *
	 * @param {Element} container	获得的是一个div或者filedset，其下必然是直接储存的属性数据
	 * @param {Element} option	即option
	 */
	function saveTool(container, option) {
		// TODO 此处的p应该被修改
		var values = $(container).children('tr').children('td').children('.'+attrIdentity);
		var options = $(option).children(':not(value)');
		for(var i=0; i<values.length; i++) {
			var tag = options[i].tagName;
			if(tag == 'input' || tag == 'output') {
				continue;
			}
			var type = $(options[i]).attr('type');
			if(type == 'fixed') {
				continue;
			}
			var value = $(values[i])[0].value;
			$(options[i]).attr('value', value);
			if(type == 'select') {
				var ops = $(options[i]).children();
				for(var j=0; j<ops.length; j++) {
					var v = $(ops[j]).children('value').html();
					if(v == value) {
						// TODO 此处的数据有问题
						saveTool(getMostInsideContainer($('#'+values[i].dataset.divId)[0]), ops[j]);
						break;
					}
				}
			}
		}
	}

	/**
	 * 递归获取全部的值：
	 * 获取一个入口节点。
	 * 转化入口节点的全部子节点。
	 * 如果是select，则
	 * 	对应生成一个关联div，用递归填充内部信息。
	 * 	在记录集中记录下其和dom的关联。
	 * 返回生成好的节点。
	 */
	// 最终确定，将div的创建放在函数外部执行，这样，函数外部将会负责生成最外部div的信息。
	// 由于最外部的div本身相对于页面是固定的，因此不需要关心。
	// 内部div存在的意义是跟select相关联，最外部没有div，因此不需要进行关联。
	// 将工具内的信息加入给定的工具节点
	function loadTool(toolDom, container) {
		var params = $(toolDom).children();
		for(var i=0; i<params.length; i++) {
			if(params[i].tagName == 'input') {
				$(container).append(createInput(params[i]));
			} else if(params[i].tagName == 'output') {
				$(container).append(createOutput(params[i]));
			} else if(params[i].tagName == 'param') {
				var type = $(params[i]).attr('type');
				if(type == 'fixed') {
					$(container).append(createFixed(params[i]));
				} else if(['integer', 'number', 'double', 'float', 'short'].includes(type)) {
					$(container).append(createNumber(params[i]));
				} else if(type == 'select') {
					var select = createSelect(params[i]);
					// TODO 此处
					var selectDom = getRealSelect(select);
					$(container).append(select);
					var options = $(params[i]).children();
					if(options.length == 0) {
						continue;
					}
					var value = $(options[0]).children('value').html();
					if($(params[i]).attr('value')) {
						value = $(params[i]).attr('value');
					}
					for(var j=0; j<options.length; j++) {
						var v = $(options[j]).children('value').html();
						if(v == value) {
							// 这里是用来包含select内部的数据的地方
							var sContainer = createDiv();
							combineSelect(selectDom, sContainer, params[i]);
							$(container).append(sContainer);
							loadTool(options[j], getMostInsideContainer(sContainer));
							break;
						}
					}
				} else if(type == 'text') {
					$(container).append(createTextArea(params[i]));
				}
			} else {		// VALUE
				continue;
			}
		}
	}

	// 创建div，并且赋予div样式
	function createDiv() {
		var tr = document.createElement('tr');
		var td = document.createElement('td');
		$(td).prop('colspan', '4');
		var table = document.createElement('table');
		$(table).prop('border', '1');
		$(table).css('border-collapse', 'collapse');
		$(table).css('border-width', '0px');
		$(table).css('border-style', 'hidden');
		$(table).css('border-color', 'gainsboro');
		$(table).css('width', '100%');
		$(table).append('<tbody></tbody>');
		$(td).append(table);
		$(tr).append(td);
		return tr;
	}

	// 将select和div以及selectInfos关联起来
	function combineSelect(select, div, selectDom) {
		select.dataset.divId = genID();
		div.id = currentID();
		$(div).addClass(containerIdentity);
		selectInfos[currentID()] = {xml: selectDom};
	}

	function createInput(element) {
		var tr = document.createElement('tr');
		$(tr).addClass('form-group');
		var td = document.createElement('td');
		$(td).css('width', '30%');
		var label = document.createElement('label');
		$(label).addClass('control-label');
		$(label).addClass('label');
		$(label).html($(element).attr('label')+':');
		$(td).append(label);
		$(tr).append(td);
		td = document.createElement('td');
		$(td).css('width', '70%');
		$(td).css('padding', '10px');
		var input = document.createElement('input');
		$(input).attr('value', 'Input File');
		$(input).css('cursor', 'url(icon/cancel.ico), auto');
		$(input).addClass('detail-area');
		$(input).addClass('form-control');
		$(input).addClass(attrIdentity);
		$(input).attr('disabled', true);
		$(input).attr('placeholder', 'Input File');
		$(td).append(input);
		$(tr).append(td);
		return tr;
	}

	function createOutput(element) {
		var tr = document.createElement('tr');
		$(tr).addClass('form-group');
		var td = document.createElement('td');
		$(td).css('width', '30%');

		var label = document.createElement('label');
		$(label).addClass('control-label');
		$(label).addClass('label');
		$(label).html($(element).attr('label')+':');

		$(td).append(label);
		$(tr).append(td);
		td = document.createElement('td');
		$(td).css('width', '70%');
		$(td).css('padding', '10px');

		var output = document.createElement('input');
		$(output).attr('value', 'Output File');
		$(output).css('cursor', 'url(icon/cancel.ico), auto');
		$(output).addClass('detail-area');
		$(output).addClass('form-control');
		$(output).addClass(attrIdentity);
		$(output).attr('disabled', true);

		$(td).append(output);
		$(tr).append(td);

		return tr;
	}

	function createFixed(element) {
		var tr = document.createElement('tr');
		$(tr).addClass('form-group');
		var td = document.createElement('td');
		$(td).css('width', '30%');

		var label = document.createElement('label');
		$(label).addClass('control-label');
		$(label).addClass('label');
		$(label).html('Fixed Parameter:');

		$(td).append(label);
		$(tr).append(td);
		td = document.createElement('td');
		$(td).css('width', '70%');
		$(td).css('padding', '10px');

		var value = document.createElement('label');
		$(value).html($(element).attr('value'));
		$(value).css('cursor', 'url(icon/cancel.ico), auto');
		$(value).css('background', '#eee');
		$(value).addClass('detail-area');
		$(value).addClass('value');
		$(value).addClass(attrIdentity);

		$(td).append(value);
		$(tr).append(td);
		return tr;
	}

	function createNumber(element) {
		var tr = document.createElement('tr');
		$(tr).addClass('form-group');
		var td = document.createElement('td');
		$(td).css('width', '30%');

		var label = document.createElement('label');
		$(label).addClass('control-label');
		$(label).addClass('label');
		$(label).html($(element).attr('label')+':');

		$(td).append(label);
		$(tr).append(td);
		td = document.createElement('td');
		$(td).css('width', '70%');
		$(td).css('padding', '10px');

		var input = document.createElement('input');
		$(input).attr('maxLength', $(element).attr('size'));
		$(input).attr('type', 'text');
		$(input).attr('required', 'required');
		$(input).addClass('detail-area');
		$(input).addClass('form-control');
		$(input).addClass('value');
		$(input).addClass(attrIdentity);
		$(input).attr('placeholder', $(element).attr('type'));
		var value = $(element).attr('value');
		if (value) {
			input.value = value;
		}

		$(td).append(input);
		$(tr).append(td);
		return tr;
	}

	function createSelect(element) {
		var tr = document.createElement('tr');
		$(tr).addClass('form-group');
		var td = document.createElement('td');
		$(td).css('width', '30%');

		var label = document.createElement('label');
		$(label).addClass('control-label');
		$(label).addClass('label');
		$(label).html($(element).attr('label')+':');

		$(td).append(label);
		$(tr).append(td);
		td = document.createElement('td');
		$(td).css('width', '70%');
		$(td).css('padding', '10px');

		var select = document.createElement('select');
		var options = $(element).children();
		for(var i=0; i<options.length; i++) {
			var option = document.createElement('option');
			option.value = $(options[i]).children('value').html();
			$(option).html($(options[i]).attr('label'));
			$(select).append(option);
		}
		var value = $(options[0]).children('value').html();
		if($(element).attr('value')) {
			value = $(element).attr('value');
		}
		select.value = value;
		$(select).attr('required', 'required');
		$(select).addClass('detail-area');
		$(select).addClass('select');
		$(select).addClass('value');
		$(select).addClass(attrIdentity);

		$(td).append(select);
		$(tr).append(td);

		$(select).on('change', function(e) {
			var value = e.target.value;
			var divId = e.target.dataset.divId;
			var xml = selectInfos[divId].xml;
			var options = $(xml).children();
			var container = getMostInsideContainer($('#'+divId)[0]);

			///////////////////////////////////////
			var cChildren = $(container).find('.'+containerIdentity);
			for(var i=0; i<cChildren.length; i++) {
				var id = $(cChildren[i]).attr('id');
				delete selectInfos[id];
			}

			$(container).empty();
			for(var i=0; i<options.length; i++) {
				var v = $(options[i]).children('value').html();
				if(v == value) {
					loadTool(options[i], container);
					break;
				}
			}
		});

		return tr;
	}

	function createTextArea(element) {
		var tr = document.createElement('tr');
		$(tr).addClass('form-group');
		var td = document.createElement('td');
		$(td).css('width', '30%');

		var label = document.createElement('label');
		$(label).addClass('control-label');
		$(label).addClass('label');
		$(label).html($(element).attr('label')+':');

		$(td).append(label);
		$(tr).append(td);
		td = document.createElement('td');
		$(td).css('width', '70%');
		$(td).css('padding', '10px');

		var textarea = document.createElement('textarea');
		var value = $(element).attr('value');
		if (value) {
			textarea.value = value;
		}
		$(textarea).attr('required', 'required');
		$(textarea).addClass('detail-area');
		$(textarea).addClass('form-control');
		$(textarea).addClass('value');
		$(textarea).addClass(attrIdentity);
		$(textarea).prop('rows', '3');
		$(textarea).attr('placeholder', $(element).attr('type'));

		$(td).append(textarea);
		$(tr).append(td);
		return tr;
	}

	/**
	 * 生成ID
	 */
	function genID() {
		$max ++;
		var id = prefix + $max;
		return id;
	}
	/**
	 * 获取当前ID
	 */
	function currentID() {
		return prefix + $max;
	}

	function closeAction() {
		resetPWindow();
	}

	function resetPWindow(name, color) {
		var pWindow = window.opener;
		if(color)
			pWindow.instance.setName(name, 'node', {color:color});
		else
			pWindow.instance.setName(name);
	}
});
