/**
 * Created by qi_l on 2016/11/4.
 * 使用my.js
 */

/**
 * 流程类
 * @param {String} bindID 节点ID
 */
function Flow(bindID, xmlStr, editable, title) {
	/*jsondata = {
	 title: "流程设计",
	 nodes: {
	 node_1: {name: "node_1", left: 67, top: 69, type: "start", width: 24, height: 24},
	 node_2: {name: "node_2", left: 219, top: 71, type: "task", width: 86, height: 24},
	 node_5: {name: "node_5", left: 380, top: 71, type: "fork", width: 86, height: 24}
	 },
	 lines: {
	 line_3: {type: "tb", M: "69", from: "node_1", to: "node_2", name: "", marked: false},
	 line_6: {type: "sl", from: "node_2", to: "node_5", name: "", marked: false}
	 }
	 };*/

	var This = this;

	// 节点中储存的对应于工具的信息
	var nodeInfor = {
		// id: <tool></tool>	// dom节点
	};

	editable = editable == undefined ? true : editable;
	var property = {
		width: window.screen.width,
		height: window.screen.height,
		toolBtns: ["task"],
		haveHead: true,
		headBtns: ["save"],//如果haveHead=true，则定义HEAD区的按钮，现在head被放在了侧方
		haveTool: editable,
		haveGroup: false,
		useOperStack: true
	};

	var remark = {
		cursor: "选择指针",
		direct: "转换连线",
		task: "任务结点"
	};

	xmlStr = xmlStr || '<?xml version="1.0" encoding="UTF-8"?><definitions id="Definition" targetNamespace="http://www.jboss.org/drools" typeLanguage="http://www.java.com/javaTypes" expressionLanguage="http://www.mvel.org/2.0" xmlns="http://www.omg.org/spec/BPMN/20100524/MODEL" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.omg.org/spec/BPMN/20100524/MODEL BPMN20.xsd" xmlns:g="http://www.jboss.org/drools/flow/gpd" xmlns:bpmndi="http://www.omg.org/spec/BPMN/20100524/DI" xmlns:dc="http://www.omg.org/spec/DD/20100524/DC" xmlns:di="http://www.omg.org/spec/DD/20100524/DI"	xmlns:tns="http://www.jboss.org/drools"><process processType="Private" isExecutable="true" name="新建模板"><startEvent id="_1"></startEvent><endEvent id="_3"></endEvent></process><bpmndi:BPMNDiagram><bpmndi:BPMNPlane bpmnElement="com.sample.bpmn.hello2"></bpmndi:BPMNPlane></bpmndi:BPMNDiagram></definitions>';
	var xmlDoc = new DOMParser().parseFromString(xmlStr, 'text/xml');

	var idPointer;
	var instance;
	var jsondata = {};
	var process = xmlDoc.getElementsByTagName('process')[0];
	var BPMNPlane = xmlDoc.getElementsByTagName('BPMNDiagram')[0].getElementsByTagName('BPMNPlane')[0];
	var toBeMax = 9;			// 为了计算当前的max
	(function () {		// init jsondata
		jsondata.title = title || process.getAttribute('name');
		var jsonNodes = jsondata.nodes = {};
		jsonNodes._1 = {name: "start", left: 50, top: 50, type: "start", width: 24, height: 24};
		jsonNodes._3 = {name: "end", left: 500, top: 50, type: "end", width: 24, height: 24};
		var nodes = process.getElementsByTagName('scriptTask');
		for (var i = 0; i < nodes.length; i++) {
			var toolName = nodes[i].getAttribute('name');
			var id = nodes[i].id;
			if(parseInt(id.substring(13)) > toBeMax) {
				toBeMax = parseInt(id.substring(13));
			}
			jsonNodes[id] = {
				name: toolName,
				type: 'task',
				width: 86,
				height: 24
			};

			var nodeInforDom = nodes[i].getElementsByTagName('tool');
			if(nodeInforDom.length) {
				nodeInforDom = nodeInforDom[0];
				_setNodeInformation(nodeInforDom.id, nodeInforDom.getAttribute('tool-id'), nodeInforDom.getAttribute('name'), nodeInforDom);
				var toolId = nodeInforDom.getAttribute('tool-id');
				for(var l in tools) {
					if(tools[l].id == toolId) {
						jsonNodes[id].color = tools[l].color || 'white';
						break;
					}
				}
			}
		}

		var BPMNShapes = BPMNPlane.getElementsByTagName('BPMNShape');
		for (var i = 0; i < BPMNShapes.length; i++) {
			var id = BPMNShapes[i].getAttribute('bpmnElement');
			if(!jsonNodes[id]) {		// 分支
				continue;
			}
			var Bounds = BPMNShapes[i].children[0];
			var x = Bounds.getAttribute('x');
			var y = Bounds.getAttribute('y');
			var width = Bounds.getAttribute('width');
			var height = Bounds.getAttribute('height');
			jsonNodes[id].top = parseInt(y);
			jsonNodes[id].left = parseInt(x);
		}

		var sequenceFlows = process.getElementsByTagName('sequenceFlow');
		var jsonLines = jsondata.lines = {};
		for (var i = 0; i < sequenceFlows.length; i++) {
			var sourceId = sequenceFlows[i].getAttribute('sourceRef');
			var targetId = sequenceFlows[i].getAttribute('targetRef');
			var id = sequenceFlows[i].id;
			if(parseInt(id.substring(13)) > toBeMax) {
				toBeMax = parseInt(id.substring(13));
			}
			jsonLines[id] = {
				type: 'tb',
				from: sourceId,
				to: targetId,
				name: '',	// 获取from和to两个节点中的相互关联的数据，填充进去
				marked: false
			};
		}

		// 取出所有分支合并节点，查找流程中目标是分支的连线，
		// 查找源是分支的连线，修改源为上面获取的值
		var parallelGateways = process.getElementsByTagName('parallelGateway');
		for (var i = 0; i < parallelGateways.length; i++) {
			var id = undefined;
			if (parallelGateways[i].getAttribute('gatewayDirection') == 'Diverging') {
				for (var j in jsonLines) {
					if (jsonLines[j].to == parallelGateways[i].id) {
						id = jsonLines[j].from;
						delete jsonLines[j];
						break;
					}
				}
				for (var j in jsonLines) {
					if (jsonLines[j].from == parallelGateways[i].id) {
						jsonLines[j].from = id;
					}
				}
			} else {
				for (var j in jsonLines) {
					if (jsonLines[j].from == parallelGateways[i].id) {
						id = jsonLines[j].to;
						delete jsonLines[j];
						break;
					}
				}
				for (var j in jsonLines) {
					if (jsonLines[j].to == parallelGateways[i].id) {
						jsonLines[j].to = id;
					}
				}
			}
		}

		for(var i in jsonLines) {
			var sourceId = jsonLines[i].from;
			var targetId = jsonLines[i].to;
			if(!sourceId || !targetId) {
				continue;
			}
			var M = (jsonNodes[sourceId].top + jsonNodes[sourceId].height / 2 + jsonNodes[targetId].top + jsonNodes[targetId].height / 2) / 2;
			jsonLines[i].M = M;
		}

		for(var i in jsonLines) {
			var sourceId = jsonLines[i].from;
			var targetId = jsonLines[i].to;
			var outputs = $(process).find('#'+sourceId).find('tool').find('output');
			var inputs = $(process).find('#'+targetId).find('tool').find('input');
			var div = document.createElement('div');
			for(var j=0; j<inputs.length; j++) {
				var value = $(inputs[j]).attr('value');
				if(!value) {
					continue;
				}
				var endPos = value.lastIndexOf('-');
				var outputId = value.substring(endPos+1);
				value = value.substring(0, endPos);
				if(value == sourceId) {
					for(var k=0; k<outputs.length; k++) {
						if($(outputs[k]).prop('id') == outputId) {
							$(div).append('<p>'+$(outputs[k]).attr('label')+' ---> '+$(inputs[j]).attr('label')+'</p>');
							break;
						}
					}
				}
			}
			jsonLines[i].name = div;
		}
	})();

	instance = $.createGooFlow($("#" + bindID), property);

	if (editable) {
		instance.setNodeRemarks(remark);
	}
	instance.$max = toBeMax+1;
	instance.loadData(jsondata);
	instance.onBtnSaveClick = function () {
		if (instance.$editable == false) {
			return false;
		}

		// 判断所有的节点是否都有对应的工具
		jsondata.nodes = $.extend(true, {}, instance.$nodeData);
		jsondata.lines = $.extend(true, {}, instance.$lineData);
		for (var i in jsondata.nodes) {
			if (jsondata.nodes[i].type != 'task') {
				continue;
			}
			if (!nodeInfor[i]) {
				alert('存在没有工具的任务节点');
				return false;
			}
		}

		// 判断是否所有的工具都已经被连接起来（即所有的点都既有出度又有入度）
		var stackIn = new Set();
		var stackOut = new Set();
		for (var i in jsondata.lines) {
			stackIn.add(jsondata.lines[i].to);
			stackOut.add(jsondata.lines[i].from);
		}
		if (stackIn.size != instance.$nodeCount-1 || stackOut.size != instance.$nodeCount-1) {
			alert('线路连接异常');
			return false;
		}

		if (This.onSave) {
			This.onSave();
		} else {
			alert('没有保存功能？');
		}
	};
	instance.onItemDel = function (id, type) {
		if (instance.$editable == false) {
			return false;
		}

		if (id == '_1' || id == '_3') {
			return false;
		}
		if (type == 'node') {
			// 节点中线的信息会在删除线的时候进行删除
			delete nodeInfor[id];
		} else if (type == 'line') {
			// 删除线两边对应的点的输入输出的记录
			var line = instance.$lineData[id];
			var from = nodeInfor[line.from];
			var to = nodeInfor[line.to];
			var inputs = $(to.xml).find('input');
			for(var i=0; i<inputs.length; i++) {
				if($(inputs[i]).attr('value')) {
					var fromId = $(inputs[i]).attr('value');
					var endPos = fromId.lastIndexOf('-');
					var fromIdId = fromId.substring(endPos+1);
					fromId = fromId.substring(0, endPos);
					if(fromId == line.from) {
						$(inputs[i]).removeAttr('value');
						$(inputs[i]).removeAttr('ref');
						$(nodeInfor[fromId].xml).find('#'+fromIdId).removeAttr('value');
					}
				}
			}
		}
		return true;
	};
	instance.onItemAdd = function (id, type, json) {
		if (instance.$editable == false) {
			return false;
		}

		// 下面的注释的部分现在已经不需要了，因为刚刚放进点或者线后，不需要立刻进行选择
		if (type == 'node') {			// 加点
			/*var script = xmlDoc.createElement('scriptTask');
			 script.setAttribute('id', id);
			 script.setAttribute('name', 'Script');
			 script.setAttribute('tool-name', '无工具');
			 process.appendChild(script);

			 var shape = xmlDoc.createElement('bpmndi:BPMNShape');
			 shape.setAttribute('bpmnElement', id);
			 var pos = xmlDoc.createElement('dc:Bounds');
			 pos.setAttribute('x', json.left);
			 pos.setAttribute('y', json.top);
			 pos.setAttribute('width', 86);
			 pos.setAttribute('height', 24);
			 shape.appendChild(pos);
			 BPMNPlane.appendChild(shape);*/
		} else {					// 加线
			if (json.from == '_3') {
				return false;
			}
			if (json.to == '_1') {
				return false;
			}

			// 查找已经存在的节点，
			// 如果已经存在from了，则表示其后面跟了一个分支节点
			// 如果已经存在to了，则表示其前面跟了一个聚合节点
			/*var sequenceFlow = xmlDoc.createElement('sequenceFlow');
			 sequenceFlow.id = json.from+'-'+json.to;
			 sequenceFlow.setAttribute('sourceRef', json.from);
			 sequenceFlow.setAttribute('targetRef', json.to);
			 process.appendChild(sequenceFlow);*/
		}
		return true;
	};
	instance.onItemMove = function (id, type, left, top) {
		if (instance.$editable == false) {
			return false;
		}

		return true;
	};
	instance.onItemRename = function (id, name, type) {
		if (instance.$editable == false) {
			return false;
		}

		return true;
	};
	instance.onLinePointMove = function(id, newStart, newEnd) {
		for(var i=0; i<instance.$lineData.length; i++) {				// 避免环形
			if(newStart==instance.$lineData[i].to && newEnd==instance.$lineData[i].from) {
				return false;
			}
		}
	};

	instance.onDblclickNode = function (idP, nodeName) {
		if (instance.$editable == false) {
			return false;
		}

		if(idP == '_1') {
			instance.$editable = false;
			window.open('flow/detail2.action', '输入信息', 'height=500, width=1030, top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');
			return;
		}
		if(idP == '_3') {
			instance.$editable = false;
			window.open('flow/detail3.action', '输出信息', 'height=500, width=1030, top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');
			return;
		}

		idPointer = idP;
		instance.$editable = false;

		window.open('flow/modify2.action?id=' + encodeURIComponent(idPointer) + '&name=' + encodeURIComponent(nodeName), '节点详细信息', 'height=500, width=800, top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');
	};

	instance.onDblclickLine = function(idP) {
		return false;		// 禁用

		// TODO DO NOTHING
		if (instance.$editable == false) {
			return false;
		}

		if(instance.$lineData[idP].from == '_1' || instance.$lineData[idP].to == '_3') {
			return false;
		}

		if(!nodeInfor || !nodeInfor[instance.$lineData[idP].from] || !nodeInfor[instance.$lineData[idP].to]) {
			alert('信息不完整，前面或者后面的节点信息还没有指定');
			return false;
		}

		idPointer = idP;
		instance.$editable = false;

		// TODO
		// 获取from和to的节点
		// 生成输入输出节点信息
		// 打开新的窗口
		var cWin = window.open('flow/modifyInputOutput.action?from=' + encodeURIComponent(instance.$lineData[idP].from) + '&to=' + encodeURIComponent(instance.$lineData[idP].to), '节点详细信息', 'height=600, width=1000, top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');
		// 在窗口关闭后，如果要求保存
		// 获取修改的值，添加对应值的修改
		// 更新对应的表格
		$(cWin).on('beforeunload', function() {
			instance.$editable = true;
		});
	};

	// 获取from和to的节点的数据
	instance.onLineEnd = function(idStart, idEnd) {
		if (instance.$editable == false) {
			return false;
		}

		if(idStart=='_1' || idStart=='_3') {
			return true;
		}
		if(idEnd=='_1' || idEnd=='_3') {
			return true;
		}
		if(idStart!='_1' && idStart!='_3') {
			if(!nodeInfor[idStart]) {
				return false;
			}
		}
		if(idEnd!='_1' && idEnd!='_3') {
			if(!nodeInfor[idEnd]) {
				return false;
			}
		}

		var lines = instance.$lineData;
		for(var i in lines) {
			if(lines[i].from == idEnd && lines[i].to == idStart) {
				return false;
			}
		}

		var startCount = 0;
		var endCount = 0;
		for(var i in lines) {
			if(lines[i].from == idStart) {
				startCount++;
			}
			if(lines[i].to == idEnd) {
				endCount++;
			}
		}
		var inputs = $(nodeInfor[idEnd].xml).find('input');
		var outputs = $(nodeInfor[idStart].xml).find('output');
		if(outputs.length == 0 || inputs.length == 0) {
			return true;
		}
		if(startCount >= outputs.length || endCount >= inputs.length) {
			return false;
		}

		return true;
	};

	instance.onAfterLineEnd = function(idP) {
		console.log(idP);
		if(!instance.$lineData[idP]) {
			return;
		}
		if(instance.$lineData[idP].from == '_1' || instance.$lineData[idP].from == '_3') {
			return;
		}
		if(instance.$lineData[idP].to == '_1' || instance.$lineData[idP].to == '_3') {
			return;
		}

		idPointer = idP;

		var inputs = $(nodeInfor[instance.$lineData[idP].to].xml).find('input');
		var outputs = $(nodeInfor[instance.$lineData[idP].from].xml).find('output');

		if(inputs.length==0 || outputs.length==0) {
			return;
		}

		instance.$editable = false;

		var cWin = window.open('flow/modifyInputOutput.action?from=' + encodeURIComponent(instance.$lineData[idP].from) + '&to=' + encodeURIComponent(instance.$lineData[idP].to), '节点详细信息', 'height=600, width=1000, top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');
		$(cWin).on('beforeunload', function() {
			instance.$editable = true;

			if(cWin.saved == false) {
				instance.delLine(idP);
				return;
			}
			// 首先清除和连线两端的信息有冲突的信息
			// 因为储存信息的是输入点，所以只查找输入点
			for(var k in instance.$lineData) {
				if(instance.$lineData[k].to == instance.$lineData[idP].to) {
					// 查找所有从这个点出发的连线接触到的点，清除他们上面和idP相冲突的信息
					for(var j in instance.$lineData) {
						if(j != k && instance.$lineData[j].from == instance.$lineData[k].from) {		// 找到不是k的另外一条线，而且两条线同一个from
							// 获取这条线对应的to记录的全部input
							var toes = $(nodeInfor[instance.$lineData[j].to].xml).find('input');
							for(var l=0; l<inputs.length; l++) {
								for(var x=0; x<toes.length; x++) {
									if($(inputs[l]).attr('value') == $(toes[x]).attr('value')) {
										$(toes[x]).removeAttr('value');
									}
								}
							}
						}
					}
				}
			}
			// 更新连线两端的数据的信息
			for(var k in instance.$lineData) {
				if(instance.$lineData[k].to == instance.$lineData[idP].to) {
					var div = document.createElement('div');
					var flag = false;
					var froms = $(nodeInfor[instance.$lineData[k].from].xml).find('output');
					for(var i=0; i<inputs.length; i++) {
						var value = $(inputs[i]).attr('value');
						if(!value) {
							continue;
						}
						var endPos = value.lastIndexOf('-');
						var outputId = value.substring(endPos+1);
						value = value.substring(0, endPos);
						if(instance.$lineData[k].from == value) {
							for(var j=0; j<froms.length; j++) {
								if($(froms[j]).prop('id') == outputId) {
									flag = true;
									$(div).append('<p>'+$(froms[j]).attr('label')+' ---> '+$(inputs[i]).attr('label')+'</p>');
									break;
								}
							}
						}
					}
//					if(flag) {
					instance.setName(k, div, 'line');
//					} else {
//						instance.delLine(k);
//					}
				} else if(instance.$lineData[k].from == instance.$lineData[idP].from) {		// 添加else，避免重复判断
					var div = document.createElement('div');
					var flag = false;
					var toes = $(nodeInfor[instance.$lineData[k].to].xml).find('input');
					for(var i=0; i<toes.length; i++) {
						var value = $(toes[i]).attr('value');
						if(!value) {
							continue;
						}
						var endPos = value.lastIndexOf('-');
						var outputId = value.substring(endPos+1);
						value = value.substring(0, endPos);
						if(instance.$lineData[k].from == value) {
							for(var j=0; j<outputs.length; j++) {
								if($(outputs[j]).prop('id') == outputId) {
									flag = true;
									$(div).append('<p>'+$(outputs[j]).attr('label')+' ---> '+$(toes[i]).attr('label')+'</p>');
									break;
								}
							}
						}
					}
//					if(flag) {
					instance.setName(k, div, 'line');
//					} else {
//						instance.delLine(k);
//					}
				}
			}
		});
	};

	var titleBox = document.createElement('input');
	titleBox.id = 'titleBox';
	titleBox.style.position = 'absolute';
	titleBox.style.left = '25px';
	titleBox.style.top = '3px';
	titleBox.style.width = '145px';
	titleBox.style.display = 'none';
	if (!document.getElementById(bindID).style.position) {
		document.getElementById(bindID).style.position = 'relative';
	}
	document.getElementById(bindID).appendChild(titleBox);
	document.getElementById('titleDom').addEventListener('dblclick', function () {
		if (instance.$editable == false) {
			return false;
		}

		var text = instance.$title;
		titleBox.value = text;
		titleBox.style.display = 'inline-block';
		titleBox.focus();
		titleBox.select();
	});
	titleBox.addEventListener('blur', function () {
		if (titleBox.value != "")
			instance.setTitle(titleBox.value);
		titleBox.style.display = 'none';
	});
	titleBox.addEventListener('keydown', function (e) {
		if (e && e.keyCode == 13) {
			if (titleBox.value != "")
				instance.setTitle(titleBox.value);
			titleBox.style.display = 'none';
		}
	});


	// 编辑信息的窗口的地址
	this.editWindowUrl = null;
	this.onSave = null;
	this.setName = function (value, type, addOn) {
		instance.$editable = true;
		type = type || "node";
		if (value) {
			instance.setName(idPointer, value, type, addOn);
		}
	};
	this.setToolTipInfo = function (id) {
		var div = document.getElementById(id);
		div.style.position = 'fixed';
		div.style.display = 'none';
		div.style.border = div.style.border || 'solid 1px';
		div.style.backgroundColor = div.style.backgroundColor || "white";
		div.style.zIndex = div.style.zIndex || 1;
		var title = document.getElementById('titleDom');
		title.addEventListener('mousemove', function (e) {
			div.style.left = (e.clientX + 20) + "px";
			div.style.top = (e.clientY + 20) + "px";
		});
		title.addEventListener('mouseenter', function (e) {
			div.style.display = 'block';
		});
		title.addEventListener('mouseleave', function (e) {
			div.style.display = 'none';
		});
	};
	this.getInstance = function () {
		return instance;
	};
	this.asStr = function () {
		// 根据json数据生成xml数据

		// 删除节点
		var scriptTask = process.getElementsByTagName('scriptTask');
		while (scriptTask.length > 0) {
			process.removeChild(scriptTask[0]);
		}
		var parallelGateway = process.getElementsByTagName('parallelGateway');
		while (parallelGateway.length > 0) {
			process.removeChild(parallelGateway[0]);
		}
		var sequenceFlow = process.getElementsByTagName('sequenceFlow');
		while (sequenceFlow.length > 0) {
			process.removeChild(sequenceFlow[0]);
		}
		var BPMNShape = BPMNPlane.getElementsByTagName('BPMNShape');
		while (BPMNShape.length > 0) {
			BPMNPlane.removeChild(BPMNShape[0]);
		}

		// 生成节点，使用原本代码维护的数据
		jsondata.nodes = $.extend(true, {}, instance.$nodeData);
		jsondata.lines = $.extend(true, {}, instance.$lineData);
		// 遍历整个节点，查看如果有两个节点的源相同，则将这个源记录下来
		// 如果有两个节点的目标相同，则将这个目标记录下来
		// 将其前后方生成分支标志，将其所在位置生成分支
		// 然后将分支和节点放入xml中
		var set = [];
		var fromSet = new Set();
		for (var i in jsondata.lines) {
			var from = jsondata.lines[i].from;
			if (set.includes(from)) {	// 分支
				fromSet.add(from);
			} else {
				set.push(from);
			}
		}
		set = [];
		var toSet = new Set();
		for (var i in jsondata.lines) {
			var to = jsondata.lines[i].to;
			if (set.includes(to)) {
				toSet.add(to);
			} else {
				set.push(to);
			}
		}
		fromSet.forEach(function (val) {			// 将所有的分支节点放进node中
			// 对于某个fromSet中的值，创建一个分支节点
			var max = instance.$max++;
			var id = '_jbpm-unique-' + max;
			jsondata.nodes[id] = {
				type: 'fork',		// 分支
				left: 0,
				top: 0,
				width: 86,
				height: 24
			};
			// 替换所有fromSet对应的lines的值
			for (var j in jsondata.lines) {
				if (jsondata.lines[j].from == val) {
					jsondata.lines[j].from = id;
				}
			}
			// 创建和其和值的联系
			max = instance.$max++;
			jsondata.lines['_jbpm-unique-' + max] = {
				type: 'tb',
				from: val,
				to: id,
				marked: false,
				M: 10,
				name: ""
			};
		});
		toSet.forEach(function (val) {			// 将所有的合并节点放进node中
			// 对于某个toSet中的值，创建一个合并节点
			var max = instance.$max++;
			var id = '_jbpm-unique-' + max;
			jsondata.nodes[id] = {
				type: 'join',		// 合并
				left: 0,
				top: 0,
				width: 86,
				height: 24
			};
			// 替换所有toSet对应的lines的值
			for (var j in jsondata.lines) {
				if (jsondata.lines[j].to == val) {
					jsondata.lines[j].to = id;
				}
			}
			// 创建和其和值的联系
			max = instance.$max++;
			jsondata.lines['_jbpm-unique-' + max] = {
				type: 'tb',
				to: val,
				from: id,
				marked: false,
				M: 10,
				name: ""
			};
		});

		// 根据node和line的数据生成xml
		for (var i in jsondata.nodes) {
			// 根据类型生成xml节点
			var dom = undefined;
			if (jsondata.nodes[i].type == 'task') {
				dom = xmlDoc.createElement('scriptTask');
				dom.id = i;
				dom.setAttribute("scriptFormat", "http://www.java.com/java");
				dom.setAttribute('tool-name', nodeInfor[i].toolName);
				dom.setAttribute('name', jsondata.nodes[i].name);
				dom.appendChild(nodeInfor[i].xml);
			} else if (jsondata.nodes[i].type == 'fork') {
				dom = xmlDoc.createElement('parallelGateway');
				dom.id = i;
				dom.setAttribute('name', 'Gateway');
				dom.setAttribute('gatewayDirection', 'Diverging');
			} else if (jsondata.nodes[i].type == 'join') {
				dom = xmlDoc.createElement('parallelGateway');
				dom.id = i;
				dom.setAttribute('name', 'Gateway');
				dom.setAttribute('gatewayDirection', 'Converging');
			}
			if (dom)
				process.appendChild(dom);

//			<bpmndi:BPMNShape bpmnElement="_1">
//				<dc:Bounds x="0" y="0" width="48" height="48" />
//			</bpmndi:BPMNShape>
			dom = xmlDoc.createElement('bpmndi:BPMNShape');
			dom.setAttribute('bpmnElement', i);
			var dom2 = xmlDoc.createElement('dc:Bounds');
			dom2.setAttribute('x', jsondata.nodes[i].left);
			dom2.setAttribute('y', jsondata.nodes[i].top);
			dom2.setAttribute('width', jsondata.nodes[i].width);
			dom2.setAttribute('height', jsondata.nodes[i].height);
			dom.appendChild(dom2);
			BPMNPlane.appendChild(dom);
		}

		for (var i in jsondata.lines) {
			var dom = xmlDoc.createElement('sequenceFlow');
			dom.id = i;
			dom.setAttribute('sourceRef', jsondata.lines[i].from);
			dom.setAttribute('targetRef', jsondata.lines[i].to);
			process.appendChild(dom);
		}

		var s = new XMLSerializer();
		return s.serializeToString(xmlDoc);
	};
	// 储存节点对应的工具信息
	function _setNodeInformation (id, toolId, toolName, toolDom) {
		nodeInfor[id] = {
			toolId: toolId,
			toolName: toolName,
			xml: toolDom
		};
	}
	this.setNodeInformation = _setNodeInformation;
	this.getNodeInformation = function (id) {
		return nodeInfor[id];
	};
	this.getNodeCount = function () {
		return instance.$nodeCount;
	}

	/**
	 * 函数的功能是设置在连接线上的输出作为输入的显示的列表
	 * @param {HTMLDivElement} rootElement 作为根的节点
	 * @param {Array} params		记录了从一个节点到另外一个节点的输出作为输入的显示信息
	 */
	function _setParamList(rootElement, params) {
		if(!rootElement) {
			return;
		}

		// 每个元素显示一条记录
		rootElement.innerHTML = "";			// 删除根节点下所有信息
		var table = document.createElement('table');		// 使用表格显示全部信息
		$(rootElement).append(table);
		$(table).append($('<tr><th>来自</th><th>到</th></tr>'))
		for(var i=0; i<params.length; i++) {
			var param = params[i];
			$(table).append(createElement(param));
		}

		// return

		// 根据传入的信息创建一条记录
		function createElement(param) {
			var tr = document.createElement('tr');
			var outputLabel = document.createElement('td');
			var inputLabel = document.createElement('td');
			$(outputLabel).html(param.output.label);
			$(inputLabel).html(param.input.label);
			$(tr).append(outputLabel);
			$(tr).append(inputLabel);
			return tr;
		}
	}

	this.setParamList = _setParamList;
}
