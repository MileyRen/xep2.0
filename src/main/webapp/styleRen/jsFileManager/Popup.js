!
function(a, b) {
    function g() {}
    var h, c = '<div class="alert_model_main"><div class="model_content"  id="model_content"></div></div><div class="alert_model_btn" id="alert_model_btn"></div>',
    d = ['<button id="check_ok">Yes</button>', 
    	'<button id="check_ok">yes</button><button id="check_cancel">cancel</button>'],
    e = '.wrap_model_bg{width: 100%;height: 100%;background: #222;position: fixed; left: 0px; top: 0;z-index: 999; opacity: 0.3;filter:alpha(opacity=30);}.alert_model_con{animation:scaleAlt 0.3s ease-out;-webkit-animation:scaleAlt 0.3s ease-out;width: 300px;height:auto;border-radius:5px;overflow:hidden; background: #fff; position: fixed; left: 50%;top: 35%;margin-left: -160px; z-index: 1000;text-align: center;padding: 0px;box-shadow: 0 0 19px #636363;}.alert_model_btn{width: 100%;height: 40px; position: absolute; left: 0px; bottom:0px;}.alert_model_btn.db button{width: 50%; float: left;}.alert_model_btn.db button#check_cancel{border-left: 1px solid #fff}.alert_model_btn button{display: block; width: 100%;height: 40px; background: #444;border:1px solid #444;color: #fff;font: 13px/1.5 "Microsoft Yahei","微软雅黑",Arial,Tahoma,sans-serif;letter-spacing: 3px; outline: none}.alert_model_btn button:hover{background: #222}.alert_model_main{display:table;margin-bottom:40px;width:100%; overflow:hidden; line-height: 20px;font: 12px/1.4 "Microsoft Yahei","微软雅黑",Arial,Tahoma,sans-serif;}.model_content{padding:11px 10px 12px 10px;line-height: 20px;+padding:4px 0px;+margin:12px 10px; color:#333;height:70px;+height:auto; display: table-cell;vertical-align: middle; }.alert_model_title{height:40px;overflow:hidden; padding: 0px 4px 0 12px; line-height: 40px; text-align: left;border-bottom: 1px solid #ccc;color:#666}#alert_close{padding: 0 11px; height:40px; line-height: 40px; float: right;text-align: center;display: block;cursor: pointer;}#alert_close img{position: relative; top: 11px;display: block; float: left;}#alert_close:hover{ opacity: 0.7;filter:alpha(opacity=70);}	@media screen and (max-width:540px){.alert_model_con{width: 80%!important;left:10%!important;margin-left: 0px!important;}}@keyframes scaleAlt {from {transform:scale(0.88);opacity: 0}to {transform:scale(1);opacity: 1}}@-webkit-keyframes scaleAlt {from {-webkit-transform:scale(0.88);opacity: 0}to {-webkit-transform:scale(1);opacity: 1}}',
    f = b.createElement("style");
    f.type = "text/css",
    f.styleSheet ? f.styleSheet.cssText = e: f.appendChild(b.createTextNode(e)),
    b.getElementsByTagName("head")[0].appendChild(f),
    g.prototype = {
        dom: function() {
            b.body.insertAdjacentHTML("beforeend", '<div class="wrap_model_bg" id="wrap_model_bg"></div>'),
            b.body.insertAdjacentHTML("beforeend", '<div class="alert_model_con" id="alert_model_con">' + c + "</div>")
        },
        alert: function() {
            this.cont(arguments)
        },
        confirm: function() {
            this.cont(arguments, 2)
        },
        stopPro: function(a) {
            a.stopPropagation && a.stopPropagation(),
            a.cancelBubble = !0
        },
        cont: function() {
            var c, e;
            b.getElementById("wrap_model_bg") && this.del(),
            this.dom(),
            c = b.getElementById("alert_model_btn"),
            arguments[1] ? (c.className += " db", c.innerHTML = d[1]) : c.innerHTML = d[0],
            e = b.getElementById("model_content"),
            e.innerHTML = "string" == typeof arguments[0][0] ? arguments[0][0].replace(/</gi, "&lt;").replace(/>/gi, "&gt;") : arguments[0][0],
            "string" == typeof arguments[0][1] ? (b.getElementById("alert_model_con").insertAdjacentHTML("afterbegin", '<div class="alert_model_title" id="alert_model_title"> <span id="alert_close"><img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABMAAAATAQMAAABInqSPAAAABlBMVEUAAADGx8bG+XUxAAAAAXRSTlMAQObYZgAAADJJREFUCNdjQAYsLAwMfHwMDPLyDAz8+xgY2P8wMDD/YGBg/ACiQXyQOEgepA6sHhkAABYSB0w+EA48AAAAAElFTkSuQmCC" alt=""></span>' + arguments[0][1] + "</div>"), this.bind(c, arguments[0][2], 1)) : this.bind(c, arguments[0][1])
        },
        bind: function(a, c) {
            var h, e = this,
            f = b.getElementById("wrap_model_bg"),
            g = a.getElementsByTagName("button");
            for (h = 0; h < g.length; h++) !
            function(a) {
                g[a].onclick = function(b) {
                    e.del(),
                    0 == a ? c && c(!0) : c && c(!1),
                    e.stopPro(b || event)
                }
            } (h);
            f.onclick = function(a) {
                e.del(),
                e.stopPro(a || event)
            },
            b.onkeyup = function(a) {
                var b = a || event,
                d = b.keyCode;
                27 === d ? (e.del(), c && c(!1)) : 13 === d && (e.del(), c && c(!0))
            };
            try {
                b.getElementById("alert_close").onclick = function(a) {
                    e.del(),
                    e.stopPro(a || event)
                }
            } catch(i) {
                return ! 1
            }
        },
        del: function() {
            b.body.removeChild(b.getElementById("wrap_model_bg")),
            b.body.removeChild(b.getElementById("alert_model_con"))
        }
    },
    h = new g,
    window.alert = function(a, b, c) {
        h.alert.call(h, a, b, c)
    },
    window.confirm = function(a, b, c) {
        h.confirm.call(h, a, b, c)
    }
} (window, document);