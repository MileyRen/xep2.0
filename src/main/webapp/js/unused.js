/**
 * 纯正为了消除下划线
 * 不会被任何地方引用
 * @auth qilin
 */

if(!window.fetch) {
	window.fetch = function(){ return new Promise(function () {}); };		// 消除掉那个下划线
}