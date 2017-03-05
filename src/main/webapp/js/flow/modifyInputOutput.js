var register = {      // 注册信息
	svgID: 'paint-area',
	point: {
		radius: 10,
		stroke: 'grey',
		selectedStroke: 'yellow',
		width: 5,
		fill: 'red',
		position: {
			left: 4,
			right: 8
		}
	},
	line: {
		stroke: 'rgb(99,99,99)',
		strokeWidth: 4
	}
};
var tempData = {
	line: {
		id: 0,
		pointLocation: 0,
		point1: {x:0, y:0},
		point2: {x:0, y:0}
	},
	point: {			// 起始点的位置
		x: 0,
		y: 0
	},
	reset: function() {
		this.line = {
			id: 0,
			pointLocation: 0,
			point1: {x:0, y:0},
			point2: {x:0, y:0}
		};
		this.point = {			// 起始点的位置
			x: 0,
			y: 0
		};
	}
};

var saved = false;

$(document).ready(function() {
	var mouseDown = 0;		// 1表示按下
	var $maxId = 0;			// 生成ID

	var queries = window.location.search.substring(1).split('&');
	for(var i=0; i<queries.length; i++) {
		var q = queries[i];
		q = q.split('=');
		q[1] = q[1].replace(/\+/g, '%20');
		q[1] = decodeURIComponent(q[1]);
		queries[i] = q;
	}

	for(var i=0; i<queries.length; i++) {
		if(queries[i][0] == 'from') {
			fromId = queries[i][1];
		} else if(queries[i][0] == 'to') {
			toId = queries[i][1];
		}
	}

	instance = window.opener.instance;
	from = instance.getNodeInformation(fromId);
	to = instance.getNodeInformation(toId);

	$('#confirm').on('click', function() {
		save();
		saved = true;
		window.close();
	});
	$('#cancel').on('click', function() {
		window.close();
	});

	// 部署
	var inputs = $(to.xml).find('input');
	var outputs = $(from.xml).find('output');

	// 计算框的高度
	(function() {
		var max = inputs.length;
		if(outputs.length > max) {
			max = outputs.length;
		}
		max *= 82;
		if(max < 500) {
			max = 500;
		}
		$('#output').css('height', max+'px');
		$('#paint-area').css('height', max+'px');
		$('#input').css('height', max+'px');
		$('body').css('height', (max+150)+'px');
	})();

	for(var i=0; i<outputs.length; i++) {
		var item = createItem({label:$(outputs[i]).attr('label'), id:$(outputs[i]).prop('id')});
		$('#output').append(item);
		$('#'+register.svgID).append(createRelevantPoint({x:25,y:40+i*80}, {id:$(item).prop('id'), position:register.point.position.left}));
	}
	for(var i=0; i<inputs.length; i++) {
		var item = createItem({label:$(inputs[i]).attr('label'), id:0, value: $(inputs[i]).attr('value')});
		$('#input').append(item);
		var point = createRelevantPoint({x:370,y:40+i*80}, {id:$(item).prop('id'), position:register.point.position.right});
		$('#'+register.svgID).append(point);
		var outputID = $(inputs[i]).attr('value');
		if(!outputID) {
			outputID = ["0", "0"];
		} else {
			var a = outputID.substring(0, outputID.lastIndexOf('-'));
			var b = outputID.substring(outputID.lastIndexOf('-')+1);
			outputID = [a, b];
		}
		if(outputID[0] == fromId) {
			var left = undefined;
			var lefts = $('.radio');
			for(var j=0; j<lefts.length; j++) {
				if($('#'+lefts[j].dataset.id)[0].dataset.outputId == outputID[1]) {
					left = lefts[j];
					break;
				}
			}
			if(!left) {
				continue;
			}
			var liner = createLiner({left:$(left).prop('id'), right:$(point).prop('id')});
			liner.dataset.leftPointID = $(left).prop('id');
			liner.dataset.rightPointID = $(point).prop('id');
			$('#'+register.svgID).append(liner);
			left.dataset.line = $(liner).prop('id');
			point.dataset.line = $(liner).prop('id');
		}
	}

	$('#'+register.svgID).on('mouseup', radiomouseup);
	$('#'+register.svgID).on('mousemove', lineMousemove);
	$('#'+register.svgID).on('mousedown', radiomousedown);
	function radiomousedown(e) {
		// 查找点击到的点的位置
		var radios = $('.radio');
		var This = undefined;
		for(var i=0; i<radios.length; i++) {
			if(includePoint({x:$(radios[i]).attr('cx'), y:$(radios[i]).attr('cy')}, $(radios[i]).attr('r'), {x:e.offsetX, y:e.offsetY})) {
				This = radios[i];
				break;
			}
		}
		if(!This) {
			return;
		}

		mouseDown |= 1;
		tempData.line.pointLocation = This.dataset.position;
		// 查看是否存在对应的线段
		if(!This.dataset.line) {
			var liner = createLiner({left:This.id, right:This.id});
			This.dataset.line = liner.id;
			tempData.line.id = liner.id;
			tempData.line.point1.x = $(This).attr('cx');
			tempData.line.point1.y = $(This).attr('cy');
			tempData.line.point2.x = $(This).attr('cx');
			tempData.line.point2.y = $(This).attr('cy');
			if(This.dataset.position == register.point.position.left) {
				liner.dataset.leftPointID = This.id;
			} else {
				liner.dataset.rightPointID = This.id;
			}
			$('#'+register.svgID).append(liner);
		} else {
			tempData.line.id = This.dataset.line;
			tempData.line.point1.x = $('#'+This.dataset.line).attr('x1');
			tempData.line.point1.y = $('#'+This.dataset.line).attr('y1');
			tempData.line.point2.x = $('#'+This.dataset.line).attr('x2');
			tempData.line.point2.y = $('#'+This.dataset.line).attr('y2');
			$('#'+This.dataset.line).attr('x1', $(This).attr('cx'));
			$('#'+This.dataset.line).attr('y1', $(This).attr('cy'));
			$('#'+This.dataset.line).attr('x2', $(This).attr('cx'));
			$('#'+This.dataset.line).attr('y2', $(This).attr('cy'));
		}
	}
	function radiomouseup(e) {
		if((mouseDown & 1) != 1) {
			return;
		}

		// 查看是否在点内抬起鼠标
		var radios = $('.radio');
		var This = undefined;
		for(var i=0; i<radios.length; i++) {
			if(includePoint({x:$(radios[i]).attr('cx'), y:$(radios[i]).attr('cy')}, $(radios[i]).attr('r'), {x:e.offsetX, y:e.offsetY})) {
				This = radios[i];
				break;
			}
		}
		if(!This) {
			if(tempData.line.point1.x == tempData.line.point2.x) {
				var ds = $('#'+tempData.line.id)[0].dataset;
				if(ds.leftPointID) {
					delete $('#'+ds.leftPointID)[0].dataset.line;
				} else {
					delete $('#'+ds.rightPointID)[0].dataset.line;
				}
				$('#'+register.svgID).children('#'+tempData.line.id).remove();
			} else {
				$('#'+tempData.line.id).attr('x1', tempData.line.point1.x);
				$('#'+tempData.line.id).attr('y1', tempData.line.point1.y);
				$('#'+tempData.line.id).attr('x2', tempData.line.point2.x);
				$('#'+tempData.line.id).attr('y2', tempData.line.point2.y);
			}
			mouseDown &= ~1;		// 取消前面4位的状态
			return;
		}

		// 拥有两边的点
		if(This.dataset.position != tempData.line.pointLocation) {
			// 可以放点
			if(This.dataset.position == register.point.position.left) {
				if(This.dataset.line && This.dataset.line!=tempData.line.id) {		// 删除线
					setSubTitle($('#'+This.dataset.line)[0].dataset.rightPointID, 'Not Linked.');
					delete $('#'+$('#'+This.dataset.line)[0].dataset.rightPointID)[0].dataset.line;
					$('#'+register.svgID).children('#'+This.dataset.line).remove();
				}
				$('#'+tempData.line.id).attr('x1', $(This).attr('cx'));
				$('#'+tempData.line.id).attr('y1', $(This).attr('cy'));
				$('#'+tempData.line.id)[0].dataset.leftPointID = This.id;
			} else {
				if(This.dataset.line && This.dataset.line!=tempData.line.id) {		// 删除线
					setSubTitle($('#'+This.dataset.line)[0].dataset.leftPointID, 'Not Linked.');
					delete $('#'+$('#'+This.dataset.line)[0].dataset.leftPointID)[0].dataset.line;
					$('#'+register.svgID).children('#'+This.dataset.line).remove();
				}
				$('#'+tempData.line.id).attr('x2', $(This).attr('cx'));
				$('#'+tempData.line.id).attr('y2', $(This).attr('cy'));
				$('#'+tempData.line.id)[0].dataset.rightPointID = This.id;
			}
			This.dataset.line = tempData.line.id;
			setSubTitle($('#'+This.dataset.line)[0].dataset.leftPointID, 'Linked To '+$(to.xml).attr('name')+'-'
				+$('#'+$('#'+$('#'+This.dataset.line)[0].dataset.rightPointID)[0].dataset.id).find('.label-title').html());
			setSubTitle($('#'+This.dataset.line)[0].dataset.rightPointID, 'Linked From '+$(from.xml).attr('name')+'-'
				+$('#'+$('#'+$('#'+This.dataset.line)[0].dataset.leftPointID)[0].dataset.id).find('.label-title').html());
		} else {
			// 还原点
			if(tempData.line.point1.x == tempData.line.point2.x) {
				delete $('#'+$('#'+tempData.line.id)[0].dataset.leftPointID)[0].dataset.line;
				$('#'+register.svgID).children('#'+tempData.line.id).remove();
			} else {
				$('#'+tempData.line.id).attr('x1', tempData.line.point1.x);
				$('#'+tempData.line.id).attr('y1', tempData.line.point1.y);
				$('#'+tempData.line.id).attr('x2', tempData.line.point2.x);
				$('#'+tempData.line.id).attr('y2', tempData.line.point2.y);
			}
		}
		tempData.reset();
		mouseDown &= ~1;		// 取消前面4位的状态

		function setSubTitle(pointID, text) {
			$('#'+$('#'+pointID)[0].dataset.id).find('.label-infor').html(text);
		}
	}
	function lineMousemove(e) {
		if((mouseDown & 1) != 1) {
			return false;
		}
		if(tempData.line.pointLocation == register.point.position.left) {
			$('#'+tempData.line.id).attr('x2', e.offsetX);
			$('#'+tempData.line.id).attr('y2', e.offsetY);
		} else {
			$('#'+tempData.line.id).attr('x1', e.offsetX);
			$('#'+tempData.line.id).attr('y1', e.offsetY);
		}
	}

	function createItem(information) {
		var p = document.createElement('p');
		$(p).addClass('item');
		p.dataset.outputId = information.id;
		$(p).prop('id', genID());
		var label = document.createElement('label');
		$(label).html(information.label);
		$(label).addClass('label-title');
		$(p).append(label);
		$(p).append($('<br>'));
		label = document.createElement('label');
		$(label).addClass('label-infor');
		if(information.id) {
			var inst = instance.getInstance();
			var flag = false;
			for(var i in inst.$lineData) {
				if(inst.$lineData[i].from == fromId) {
					var xml = instance.getNodeInformation(inst.$lineData[i].to).xml;
					var toes = $(xml).find('input');
					for(var j=0; j<toes.length; j++) {
						var value = $(toes[j]).attr('value');
						if(!value) {
							continue;
						}
						var endPos = value.lastIndexOf('-');
						var outputId = value.substring(endPos+1);
						value = value.substring(0, endPos);
						if(value == fromId) {
							if(outputId == information.id) {
								$(label).html('Linked To '+$(xml).attr('name')+'-'+$(toes[j]).attr('label'));
								flag = true;
								break;
							}
						}
					}
				}
				if(flag) {
					break;
				}
			}
			if(!flag) {
				$(label).html('Not Linked.');
			}
		} else {
			if(information.value) {
				var endPos = information.value.lastIndexOf('-');
				var outputID = information.value.substring(endPos+1);
				var value = information.value.substring(0, endPos);
				var xml = instance.getNodeInformation(value).xml;
				var title = $(xml).attr('name');
				var subTitle = $(xml).find('#'+outputId).attr('label');
				$(label).html('Linked From '+title+'-'+subTitle);
			} else {
				$(label).html('Not Linked.');
			}
		}
		$(p).append(label);
		return p;
	}

	function createRelevantPoint(location, information) {
		var point = document.createElementNS('http://www.w3.org/2000/svg', 'circle');
		$(point).prop('id', genID());
		point.classList.add('radio');
		$(point).attr('cx', location.x);
		$(point).attr('cy', location.y);
		$(point).attr('r', register.point.radius);
		$(point).attr('stroke', register.point.stroke);
		$(point).attr('stroke-width', register.point.width);
		$(point).attr('fill', register.point.fill);
		point.dataset.position = information.position;
		point.dataset.id = information.id;          // 对应条目的id
		return point;
	}

	/**
	 * @param {Object} location 记录了左边和右边的点的id
	 */
	function createLiner(location) {
		var liner = document.createElementNS('http://www.w3.org/2000/svg', 'line');
		$(liner).prop('id', genID());
		liner.classList.add('line');
		var point1 = {x: $('#'+location.left).attr('cx'), y: $('#'+location.left).attr('cy')};
		var point2 = {x: $('#'+location.right).attr('cx'), y: $('#'+location.right).attr('cy')};
		$(liner).attr('x1', point1.x);
		$(liner).attr('y1', point1.y);
		$(liner).attr('x2', point2.x);
		$(liner).attr('y2', point2.y);
		$(liner).css('stroke', register.line.stroke);
		$(liner).css('stroke-width', register.line.strokeWidth);
		return liner;
	}

	function save() {
		var radios = $('.radio');
		var inputs = $(to.xml).find('input');
		var index = 0;
		for(var i=0; i<radios.length; i++) {
			if(radios[i].dataset.position == register.point.position.left) {
				// DO NOTHING
			} else {
				if(!(radios[i].dataset.line)) {
					continue;
				}
				var output = $('#'+$('#'+$('#'+radios[i].dataset.line)[0].dataset.leftPointID)[0].dataset.id)[0].dataset.outputId;
				$(from.xml).find('#'+output).attr('value', '0');
				// 断开和fromId的output有关的所有input
				var inst = instance.getInstance();
				for(var j in inst.$lineData) {
					if(inst.$lineData[j].from == fromId) {
						resetInput(inst.$lineData[j].to, fromId, output);
					}
				}
				// 断开和toId相关的当前的output
				resetOutput(inputs[index]);

				$(inputs[index]).attr('value', fromId+'-'+output);
				$(inputs[index]).attr('ref', 'fromFlow');
				index++;
			}
		}

		function resetInput(inputId, fromId, output) {
			var inputs = $(instance.getNodeInformation(inputId).xml).find('input');
			for(var i=0; i<inputs.length; i++) {
				if($(inputs[i]).attr('value') == fromId+'-'+output) {
					$(inputs[i]).removeAttr('value');
					$(inputs[i]).removeAttr('ref');
				}
			}
		}
		function resetOutput(input) {
			var value = $(input).attr('value');
			if(!value) {
				return;
			}
			var endPos = value.lastIndexOf('-');
			var outputId = value.substring(endPos+1);
			value = value.substring(0, endPos);
			var output = instance.getNodeInformation(value);
			$(output.xml).find('#'+outputId).removeAttr('value');
		}
	}

	function includePoint(core, radius, point) {
		core.x = parseInt(core.x);
		core.y = parseInt(core.y);
		radius = parseInt(radius);
		point.x = parseInt(point.x);
		point.y = parseInt(point.y);
		return (core.x-radius <= point.x) && (core.x+radius >= point.x) && (core.y-radius <= point.y) && (core.y+radius >= point.y);
	}
	function genID() {
		return 'generated-'+(++$maxId);
	}
	function currentID() {
		return 'generated-'+$maxId;
	}
});
