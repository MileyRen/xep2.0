$(document).ready(function () {
	var xml = new DOMParser().parseFromString(xmlStr, 'text/xml');
	var process = $(xml).children('definitions').children('process')[0];
	var section = $('#' + sectionId)[0];
	var nodeInfor = {};				// 记录每个输入输出文件的节点，直接对xml进行修改
	var outputFileList = {
		currentID: undefined,
		setID: function(id, name) {
			this[id] = this[id] || {children:{}, name:name};
			this.currentID = id;
		}
	};		// 记录每个节点的outputFile
	/** @Depressed */
	window.outputFileList = outputFileList;		// 这里是用在选择来自流程的节点的地方的，现在应该用不到了
	var $max = 0;                   // id: unique--$max
	var attrIdentity = 'option-value';		// 标识储存选项的节点

// var containerIdentity = 'container-identity';		// 标识容器的类

// 解析xml文件，获取task
// 解析task，获取tool
// 获取outputFile
// 生成表格

	(function () {
		var scriptTask = $(process).children('scriptTask');
		for (var i = 0; i < scriptTask.length; i++) {
			addNode($(scriptTask[i]).children('tool')[0], $(scriptTask[i]).attr('name'));
		}
		fixInput();
	})();

// 表格中内容：
// 如果表格中有input则允许用户选择来自文件还是其他节点的某个输出
//  以下做在一个页面中
//  如果是来自文件则打开文件选择页面
//  如果是来自其他节点的某个输出则出现二级下拉列表
// 如果是输出文件则允许用户指定是否需要保留
//  如果需要保留则打开文件选择页面
// 其他则加入disable限制，只做显示，如果是select则只加入一条数据，作为显示使用

// 提交：
// 判断表格是否填写完整
// 获取每个表格中的输入和输出节点
// 如果是输入节点：
//  如果来自文件，则保存文件路径
//  如果来自其他节点则保存节点数据
// 如果是输出节点：
//  如果选择保留则保存文件路径
//  如果不保留则标明不保留
// 根据获取的信息更新xml文件

	$('#cancel').on('click', function() {
		window.close();
	});

	$('#confirm').on('click', function() {
		if(checkValidity() == false) {
			return;
		}
		var output = genOutput();
		output = encodeURIComponent(output);
		output = output.replace(/%20/g, '+');

		var form = document.createElement('form');
		$(form).prop('action', 'job/confirmCreate.action');
		$(form).prop('method', 'POST');
		$(form).css('display', 'none');
		var flowBasicInfoId = document.createElement('input');
		flowBasicInfoId.name = 'flowBasicInfoId';
		flowBasicInfoId.value = flowId;
		var bpmn = document.createElement('input');
		bpmn.name = 'bpmn';
		bpmn.value = output;
		$(form).append(flowBasicInfoId);
		$(form).append(bpmn);
		$(document.body).append(form);
		form.submit();
	});

	$('#run').on('click' ,function() {
		if(checkValidity() == false) {
			return;
		}
		var output = genOutput();
		output = encodeURIComponent(output);
		output = output.replace(/%20/g, '+');

		var form = document.createElement('form');
		$(form).prop('action', 'job/confirmCreateAndStart.action');
		$(form).prop('method', 'POST');
		$(form).css('display', 'none');
		var flowBasicInfoId = document.createElement('input');
		flowBasicInfoId.name = 'flowBasicInfoId';
		flowBasicInfoId.value = flowId;
		var bpmn = document.createElement('input');
		bpmn.name = 'bpmn';
		bpmn.value = output;
		$(form).append(flowBasicInfoId);
		$(form).append(bpmn);
		$(document.body).append(form);
		form.submit();
	});

	function checkValidity() {
		var form = $('#form')[0];
		if(form.checkValidity() == false) {
			$('.ac-input').prop('checked', 'checked');
			$('#submit')[0].click();
			return false;
		}
		return true;
	}

	/**
	 * 根据节点生成输出文件配置
	 */
	function genOutput() {
		var form = $('#form')[0];
		// var inputs = $(form).find('.'+attrIdentity);
		for(var i in nodeInfor) {
			var input = $('#'+i)[0];
			var xml = nodeInfor[i].xml;
			$(xml).attr('ref', input.dataset.ref);
			if(input.dataset.ref == 'fromFlow') {
				$(xml).attr('value', input.dataset.value);
			} else if(input.dataset.ref == 'fromDatabase') {
				if(input.dataset.fileNameID) {
					$(xml).attr('value', $(input).prop('value')+'/'+$('#'+input.dataset.fileNameID).attr('value'));
				} else {
					$(xml).attr('value', $(input).prop('value'));
				}
			} else {
				$(xml).attr('value', '0');
			}
		}

		return asStr();
	}

	/**
	 * 向section中添加一个条目
	 * var section = $('#'+sectionId)[0];
	 *
	 * @param {HTMLElement} toolElement 工具，完整的工具节点
	 * @returns
	 */
	function addNode(toolElement, name) {
		/*
		var id = $(toolElement).attr('id');
		var input = document.createElement('input');
		input.type = 'checkbox';
		input.name = 'accordion-1';
		$(input).addClass('ac-input');
		$(input).attr('id', genID());

		var label = document.createElement('label');
		$(label).attr('for', currentID());
		$(label).addClass('show-title');
		$(label).html(name);
		$(label).addClass('ac-label');

		var article = document.createElement('article');
		$(article).addClass('ac-open');

		var div = document.createElement('div');
		$(div).attr('id', id);
		$(div).addClass('ac-div');

		$(div).append(input);
		$(div).append(label);
		$(div).append(article);
		$(section).append(div);
		*/

		outputFileList.setID($(toolElement).attr('id'), name);
		loadTool($(toolElement).children('params')[0], section);

	}

	/**
	 * 向列表中加载工具
	 * @param {HTMLElement} toolDom
	 * @param {HTMLElement} container
	 */
	function loadTool(toolDom, container) {
		var params = $(toolDom).children();
		for (var i = 0; i < params.length; i++) {
			if (params[i].tagName == 'input') {
				var res = createInput(params[i]);
				if(res) $(container).append(res);
			} else if (params[i].tagName == 'output') {
				var res = createOutput(params[i]);
				if(res) $(container).append(res);
			} else if (params[i].tagName == 'param') {
				continue;		// 下面的都不要
				var type = $(params[i]).attr('type');
				if (type == 'fixed') {
					$(container).append(createFixed(params[i]));
				} else if (['integer', 'number', 'double', 'float', 'short'].includes(type)) {
					$(container).append(createNumber(params[i]));
				} else if (type == 'select') {
					var select = createSelect(params[i]);
					$(container).append(select);
					var options = $(params[i]).children();
					if (options.length == 0) {
						continue;
					}
					var value = $(options[0]).children('value').html();
					if ($(params[i]).attr('value')) {
						value = $(params[i]).attr('value');
					}
					for (var j = 0; j < options.length; j++) {
						var v = $(options[j]).children('value').html();
						if (v == value) {
							loadTool(options[j], container);
							break;
						}
					}
				} else if (type == 'text') {
					$(container).append(createTextArea(params[i]));
				}
			} else {		// VALUE
				continue;
			}
		}
	}

	/**
	 * 获取真正的select
	 * 层次遍历
	 * @param {HTMLElement} select
	 */
	function getRealSelect(select) {
		if (select.tagName == 'select') {
			return select;
		}
		var children = $(select).children('select');
		if (children.length != 0) {
			return children[0];
		}
		children = $(select).children();
		for (var i = 0; i < children.length; i++) {
			select = getRealSelect(children[i]);
			if (select) {
				return select;
			}
		}
		return undefined;
	}

// 创建div，并且赋予div样式
	function createDiv() {
		var div = document.createElement('div');
		return div;
	}

	/**
	 * 填充输入空格
	 */
	function fixInput() {
		var inputs = $('.'+attrIdentity);
		for(var i=0; i<inputs.length; i++) {
			var id = $(inputs[i]).prop('id');
			var xml = nodeInfor[id].xml;
			if($(xml).attr('value')) {
				var input = inputs[i];
				input.dataset.ref = $(xml).attr('ref');
				if(input.dataset.ref == 'fromFlow') {
					input.dataset.value = $(xml).attr('value');
					var spIndex = input.dataset.value.lastIndexOf('-');
					var toolId = input.dataset.value.substring(0, spIndex);
					var outputFileId = input.dataset.value.substring(spIndex+1);
					var outputFile = outputFileList[toolId];
					$(input).prop('value', '/'+outputFile.name+'/'+outputFile.children[outputFileId].label);
				} else if(input.dataset.ref == 'fromDatabase') {
					$(input).prop('value', $(xml).attr('value'));
				} else {
					$(input).prop('value', '不保存');
				}
			}
		}
	}

	/**
	 * 本表单不再和原本的xml文档形成一一对应关系
	 * 而是利用唯一的ID将需要更新的元素和xml文档关联起来
	 */
	function createInput(element) {
		if($(element).attr('ref') == 'fromFlow') {
			return undefined;
		}
		var p = document.createElement('p');
		$(p).addClass('modify-p');
		$(p).addClass('form-group');
		var label = document.createElement('label');
		$(label).addClass('col-sm-2');
		$(label).addClass('control-label');
		$(label).addClass('label');
		$(label).html($(element).attr('label'));
		var input = document.createElement('input');
		$(input).prop('id', genID());
		$(input).prop('required', 'required');
		$(input).css('cursor', 'default');
		$(input).addClass('detail-area-with-button-influence');
		$(input).addClass('form-control');
		$(input).addClass(attrIdentity);
		$(input).on('keydown', function() {
			return false;
		});
		nodeInfor[currentID()] = {xml: element};
		/*var inputFromFlow = document.createElement('input');
		$(inputFromFlow).prop('type', 'button');
		$(inputFromFlow).prop('value', '从流程中选择');
		$(inputFromFlow).prop('id', genID());
		$(inputFromFlow).addClass('btn');
		$(inputFromFlow).addClass('btn-primary');
		$(inputFromFlow).addClass('button');
		$(inputFromFlow).on('click', function() {
			var child = window.open('job/selectFlow.action', '文件列表', 'height=500, width=380, top=50, left=500, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');
			$(child).on('beforeunload', function() {
				if(child.filePath) {
					$(input).prop('value', child.filePath.path);
					input.dataset.value = child.filePath.idA[1]+'-'+child.filePath.idA[0];
					input.dataset.ref = "fromFlow";
				}
			});
		});*/
		var inputFromDB = document.createElement('input');
		$(inputFromDB).prop('type', 'button');
		$(inputFromDB).prop('value', '从数据库中选择');
		$(inputFromDB).prop('id', genID());
		$(inputFromDB).addClass('btn');
		$(inputFromDB).addClass('btn-primary');
		$(inputFromDB).addClass('button');
		$(inputFromDB).on('click', function() {
			var child = window.open('job/selectFile.action', '文件列表', 'height=500, width=380, top=50, left=500, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');
			$(child).on('beforeunload', function() {
				if(child.filePath) {
					$(input).prop('value', child.filePath);
					$(input)[0].dataset.ref = "fromDatabase";
				}
			});
		});
		$(p).append(label);
		$(p).append(input);
//		$(p).append(inputFromFlow);
		$(p).append(inputFromDB);
		return p;
	}

	function createOutput(element) {
		if($(element).attr('value') == '0') {
			return undefined;
		}
		var p = document.createElement('p');
		$(p).addClass('modify-p');
		$(p).addClass('form-group');
		var label = document.createElement('label');
		$(label).addClass('col-sm-2');
		$(label).addClass('control-label');
		$(label).addClass('label');
		$(label).html($(element).attr('label'));
		var output = document.createElement('input');
		$(output).prop('id', genID());
		$(output).prop('placeholder', '输出到数据库的位置');
		$(output).prop('required', 'required');
		$(output).css('cursor', 'default');
		$(output).addClass('detail-area-with-button-influence-output');
		$(output).addClass('form-control');
		$(output).addClass(attrIdentity);
		$(output).on('keydown', function() {
			return false;
		});
		nodeInfor[currentID()] = {xml: element};
		outputFileList[outputFileList.currentID].children[$(element).attr('id')] = {
			value: $(element).attr('value'),
			path: $(element).attr('path'),
			label: $(element).attr('label')
		}
		/*var outputNotSaved = document.createElement('input');
		$(outputNotSaved).prop('type', 'button');
		$(outputNotSaved).prop('value', '不保存');
		$(outputNotSaved).prop('id', genID());
		$(outputNotSaved).addClass('btn');
		$(outputNotSaved).addClass('btn-primary');
		$(outputNotSaved).addClass('button');
		$(outputNotSaved).on('click', function() {
			$(output).prop('value', '不保存');
			$(output)[0].dataset.ref = "notSaved";
		});*/
		var outputToDB = document.createElement('input');
		$(outputToDB).prop('type', 'button');
		$(outputToDB).prop('value', '输出到数据库');
		$(outputToDB).prop('id', genID());
		$(outputToDB).addClass('btn');
		$(outputToDB).addClass('btn-primary');
		$(outputToDB).addClass('button');
		$(outputToDB).on('click', function() {
			var child = window.open('job/selectFile.action?selectFolder=1', '文件列表', 'height=500, width=380, top=50, left=500, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');
			$(child).on('beforeunload', function() {
				if(child.filePath) {
					$(output).prop('value', child.filePath);
					$(output)[0].dataset.ref = "fromDatabase";
				}
			});
		});
		$(p).append(label);
		genID();
		output.dataset.fileNameID = currentID();
		$(p).append(output);
//		$(p).append(outputNotSaved);
		$(p).append('<label style="width: 10px; text-align: center;">/</label>');
		$(p).append('<input type="text" class="detail-area-with-button-influence-output form-control" id="'+currentID()+'" placeholder="文件名" required="required">');
		$(p).append(outputToDB);
		return p;
	}

	function createFixed(element) {
		var p = document.createElement('p');
		$(p).addClass('modify-p');
		$(p).addClass('form-group');
		var label = document.createElement('label');
		$(label).addClass('col-sm-2');
		$(label).addClass('control-label');
		$(label).addClass('label');
		$(label).html('固定参数');
		var value = document.createElement('label');
		$(value).html($(element).attr('value'));
		$(value).addClass('col-sm-2');
		$(value).addClass('detail-area');
		$(value).addClass('value');
		$(p).append(label);
		$(p).append(value);
		return p;
	}

	function createNumber(element) {
		var p = document.createElement('p');
		$(p).addClass('modify-p');
		$(p).addClass('form-group');
		var label = document.createElement('label');
		$(label).addClass('col-sm-2');
		$(label).addClass('control-label');
		$(label).addClass('label');
		$(label).html($(element).attr('label'));
		var input = document.createElement('input');
		$(input).attr('maxLength', $(element).attr('size'));
		$(input).attr('type', 'text');
		$(input).attr('disabled', 'disabled');
		$(input).addClass('detail-area');
		$(input).addClass('form-control');
		$(input).addClass('value');
		input.value = $(element).attr('value');
		$(p).append(label);
		$(p).append(input);
		return p;
	}

	function createSelect(element) {
		var p = document.createElement('p');
		$(p).addClass('modify-p');
		$(p).addClass('form-group');
		var label = document.createElement('label');
		$(label).addClass('col-sm-2');
		$(label).addClass('control-label');
		$(label).addClass('label');
		$(label).html($(element).attr('label'));

		var select = document.createElement('select');
		var options = $(element).children();
		for (var i = 0; i < options.length; i++) {
			var option = document.createElement('option');
			$(option).attr('value', $(options[i]).children('value').html());
			$(option).html($(options[i]).attr('label'));
			$(select).append(option);
		}
		var value = $(options[0]).children('value').html();
		if ($(element).attr('value')) {
			value = $(element).attr('value');
		}
		select.value = value;

		$(select).attr('disabled', 'disabled');
		$(select).addClass('detail-area');
		$(select).addClass('select');
		$(select).addClass('value');
		$(p).append(label);
		$(p).append(select);

		return p;
	}

	function createTextArea(element) {
		var p = document.createElement('p');
		$(p).addClass('modify-p');
		$(p).addClass('form-group');
		var label = document.createElement('label');
		$(label).addClass('col-sm-2');
		$(label).addClass('control-label');
		$(label).addClass('label');
		$(label).html($(element).attr('label'));
		var textarea = document.createElement('textarea');
		var value = $(element).attr('value');
		if (value) {
			textarea.value = value;
		}
		$(textarea).attr('disabled', 'disabled');
		$(textarea).prop('rows', '3');
		$(textarea).addClass('detail-area');
		$(textarea).addClass('form-control');
		$(textarea).addClass('value');
		$(p).append(label);
		$(p).append(textarea);
		return p;
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

	function asStr() {
		return new XMLSerializer().serializeToString(xml);
	}
});
