/**
 * @namespace {Object} converter
 */
var converter = {
	/**
	 * @param {string|JSON} json
	 * @param {HTMLDivElement} [container]
	 * @return {HTMLDivElement}
	 */
	json2HTML:function(json, container) {
		if(typeof json == 'string') {
			json = eval(json);
		}
		container = container || document.createElement('div');
		container.classList.add('st_tree');

		container.appendChild(convertArr(json));

		return container;

		function convertArr(jsonArr) {
			var ul = document.createElement('ul');
			for(var i=0; i<jsonArr.length; i++) {
				var json = jsonArr[i];
				var li = document.createElement('li');
				var a = document.createElement('a');
				a.setAttribute('href', 'javascript:;');
				a.setAttribute('ref', json.id);
				a.innerHTML = json.text;
				li.appendChild(a);
				ul.appendChild(li);
				if(json.children) {
					ul.appendChild(convertArr(json.children));
				}
			}
			return ul;
		}
	}
};
