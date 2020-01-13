/*
 * title:lgt-web.js                    标题：lgt-web.js
 * content:java web frame              内容：java web前段js框架
 * copyright:@liguotao                 版权：李国涛
 * start-time:20140918                 创建时间：2014年9月18日    
 */


/*----浏览器内核说明----/
	内核也被称为"页面渲染引擎",它负责取得网页的内容(HTML,CSS等)。目前常见的浏览器内核共分为4种:
	Trident:这是微软开发的一种排版引擎,IE最先使用。此外,Maxthon、TT、The World和360浏览器也使用该内核。
	Geckos:以C++编写的开放源码的网页排版引擎,Netcape6开始采用的内核,后来的Mozilla FireFox(火狐浏览器)也采用了该内核。该代码引擎完全公开，开放程度很高。
	Presto:由Opera Software开发的浏览器排版引擎,该引擎的特点是渲染速度的优化达到了极致,也是目前公认的最快浏览器内核。
	Webkit:苹果的Safari浏览器使用的内核,谷歌浏览器Chrome也使用该浏览器内核。	
*/


/*
	IE提供了window.screenLeft和window.screenTop对象来判断窗口的位置，但未提供任何判断窗口大小的方法。
	用document.body.offsetWidth和document.body.offsetHeight属性可以获取视觉窗口的大小（显示HTML页的区域），但它们不是标准属性（FF也支持）。
  	Mozilla Firefox提供window.screenX和window.screenY属性判断窗口的位置。它还提供了window.innerWidth和window.innerHeight属性来判断视觉窗口的大小，window.outerWidth和window.outerHeight属性判断浏览器窗口自身的大小。
  	Opera和Safari提供与Mozilla相同的工具。
*/


/*----html页面基本框架----/
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />
	<title>我的网页</title>
	<base href="" target="_blank" />
	<!--[if lt IE 9]> 
		<script src="http://css3-mediaqueries-js.googlecode.com/svn/trunk/css3-mediaqueries.js"></script> 
		<script src="http://apps.bdimg.com/libs/html5shiv/3.7/html5shiv.min.js"></script>
	<![endif]--> 
	<link href="css/lgt-web.css" rel="stylesheet" media="screen">
	<script type="text/javascript" src="js/jquery.js" charset="utf-8"></script>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=5sM0GsnAAPEhOGqXdfcr4dOu"></script>
	<script type="text/javascript" src="js/lgt-web.js" charset="utf-8"></script>
</head>
<body>内容</body>
</html>*/


/*----html页面中插入flash方法----/
<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" 
codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,40,0" WIDTH="100%" HEIGHT="100%" id="myFlash">  
	<param value="myFlash.swf" name="movie">
	<param value="high" name="quality">
	<param value="always" name="allowScriptAccess">
	<param value="transparent" name="wmode">
	<param value="false" name="play" >
	<param value="false" name="loop" >  
	<EMBED src="myFlash.swf" quality="high" wmode="transparent" pluginspace="http://www.macromedia.com/go/getflashplayer" 
	       play="false" loop="false" allowScriptAccess="always" name="myFlash" type="application/x-shockwave-flash" width="100%" height="100%">
	</EMBED>  
</object>*/


//封装获取浏览器信息函数
$.getNavInfo = function(){
	var navInfo = {};
	var screenWidth = window.screen.width;                   //获取屏幕分辨率宽度
	navInfo.screenWidth = screenWidth;
	var screenHeight = window.screen.height;                 //获取屏幕分辨率高度
	navInfo.screenHeight = screenHeight;
	var availWidth = window.screen.availWidth;               //获取屏幕可用区域宽度
	navInfo.availWidth = availWidth;
	var availHeight = window.screen.availHeight;             //获取屏幕可用区域高度
	navInfo.availHeight = availHeight;
	var windowWidth = $(window).width();                     //获取窗口可视化宽度
	navInfo.windowWidth = windowWidth;
	var windowHeight = $(window).height();                   //获取窗口可视化高度
	navInfo.windowHeight = windowHeight;
	var domWidth = $(document).width();                      //获取窗口文档的宽度
	navInfo.domWidth = domWidth;
	var domHeight = $(document).height();                    //获取窗口文档的高度
	navInfo.domHeight = domHeight;
	var nav = window.navigator.userAgent;                    //获取浏览器信息        
	navInfo.navInfo = nav;
	var system = navigator.platform;                         //获取系统平台信息
	navInfo.system = system;
	return navInfo;
}


//验证客户端所属环境（PC或mobile）函数      
$.isPc = function isPC(){
	var isPc = true;
	var userAgentInfo = $.getNavInfo().navInfo;
    var Agents = ["Android", "iPhone","SymbianOS", "Windows Phone"];
    for (var i = 0; i < Agents.length; i++) {
    	if(userAgentInfo.indexOf(Agents[i]) > 0) {
    		isPc = false;
            break;
        }
    }
    return isPc;
}


//封装获得flash对象函数
$.getFlashObject = function(movieName){
	//movieName:flash元素id值
	if (window.document[movieName]){  
		return window.document[movieName]; 
	}  
	if (navigator.appName.indexOf("Microsoft Internet")==-1){  
		if (document.embeds && document.embeds[movieName]){ 
       		return document.embeds[movieName];  
		}else{  
     		return document.getElementById(movieName);  
   		}  
	}
}


//封装多精度小数四舍五入函数
$.clearFloatPie = function(num,index,flag){            
	//num:float小数 , index:保留位数 , flag:是否四舍五入
	if(flag){                  //取true时进行四舍五入
		if(num.toString().indexOf(".") != -1){
			if(index == 0){
				var i = num.toString().substring(num.toString().indexOf(".")+1,num.toString().indexOf(".")+2);
				if(i < 5){
					return parseInt(num.toString().substring(0,num.toString().indexOf(".")));
				}else{
					return parseInt(num.toString().substring(0,num.toString().indexOf(".")))+1;
				}
			}else{
				var i = num.toString().substring(num.toString().indexOf(".")+(index+1),num.toString().indexOf(".")+(index+2));
				if(i < 5){
					return parseFloat(num.toString().substring(0,num.toString().indexOf(".")+(index+1)));
				}else{
					return parseFloat(num.toString().substring(0,num.toString().indexOf(".")+index) + (parseInt(num.toString().substring(num.toString().indexOf(".")+index,num.toString().indexOf(".")+(index+1)))+1));
				}
			}
		}else{
			return parseInt(num+1);
		}
	}else{
		if(num.toString().indexOf(".") != -1){
			if(index == 0){
				return parseInt(num.toString().substring(0,num.toString().indexOf(".")));
			}else{
				return parseFloat(num.toString().substring(0,num.toString().indexOf(".")+(index+1)));
			}
		}else{
			return parseInt(num);
		}
	}
}


//验证是否为IE8
$.isIE8 = function(){
	if(navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion .split(";")[1].replace(/[ ]/g,"")=="MSIE8.0"){ 
		return true;
	}else{
		return false;
	}
}


//验证是否为IE
$.isIE = function(){
	if(navigator.appName == "Microsoft Internet Explorer"){ 
		return true;
	}else{
		return false;
	}
}


//验证是否为Chrome
$.isCHROME = function(){
	if(navigator.appName == "Netscape"){ 
		return true;
	}else{
		return false;
	}
}


//判断元素在数组中是否存在
Array.prototype.contains = function(needle) {
	for (i in this) {
		if (this[i] == needle) return true;
	}
	return false;
}


//字符串自动去两侧空格
$.trimString = function(str){
	return str.replace(/(^\s*)|(\s*$)/g, "");
}


//通用对象判空验证
$.isNull = function(data){
	var flag = false;
	if(data == undefined || data == null){
		flag = true;
	}
	return flag;
}


//通用字符串判空验证
$.isEmpty = function(data){
	var flag = false;
	flag = $.isNull(data);
	if(!flag && (Object.prototype.toString.call(data) === "[object String]" && data.replace(/\s/g,'') == "")){
		flag = true;
	}
	return flag;
}


//获取地址栏传参
$.getUrlAddressParam = function(key, wd){
	var reg = new RegExp("(^|&)"+ key +"=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if(!$.isEmpty(wd) && wd == "parent"){
		var pSrc = $.parentFun({ "iframeSelected" : '.right-iframe', "pElemSelected" : '.right-iframe' }).attr("src");
		if(pSrc.indexOf("?") != -1){
			r = pSrc.substr(pSrc.indexOf("?")+1).match(reg);
		}
	}
	if(r!=null)return  unescape(r[2]); return null;
}


//阻止事件冒泡
$.stopEventPopple = function(e){
	if(!$.isNull(e)){
		if($.isIE()){
			e.cancelBubble = true;//停止冒泡
			e.returnValue = false;//阻止事件的默认行为
		}else{
			e.preventDefault();//取消事件的默认行为  
			e.stopPropagation();//停止冒泡
		}
	}
}


//获取系统当前日期元素数组
$.getCurrentDate = function(){
	var date = [];
	var dateTime = new Date();
	var days= ["日","一","二","三","四","五","六"];
	
	var aa = dateTime.getFullYear();            //年
	var bb = dateTime.getMonth() + 1;           //月
	var cc = dateTime.getDate();                //日
	var dd = dateTime.getHours();               //时
	var ee = dateTime.getMinutes();             //分
	var ff = dateTime.getDay();                 //星期
	var gg = dateTime.getSeconds();             //秒
	date.push(aa,bb,cc,dd,ee,gg,days[ff]);
	
	return date;
}


//获取n天前或n天后的日期
$.getDateStrByN = function(n) {  
    var dd = new Date();  
    dd.setDate(dd.getDate() + n);
    var y = dd.getFullYear();  
    var m = (dd.getMonth() + 1) < 10 ? ('0'+(dd.getMonth() + 1)) : (dd.getMonth() + 1);  
    var d = dd.getDate() <10 ? ('0'+ dd.getDate()) :dd.getDate();  
    return y + "-" + m + "-" + d;  
}


//封装比较日期时间大小函数
$.dateCompare = function(startDate,endDate){
	//startDate:开始日期时间 , endDate:结束日期时间  (yyyy-MM-dd hh:ss:mm或yyyy-MM-dd)
	if(startDate.indexOf(":") == -1){
		startDate += " 00:00:00";
	}
	if(endDate.indexOf(":") == -1){
		endDate += " 00:00:00";
	}
	var arr1 = startDate.substring(0 , startDate.indexOf(" ")).split("-");
	var arr2 = startDate.substr(startDate.indexOf(" ")+1).split(":");
	var starttime = new Date(arr1[0] , arr1[1]-1 , arr1[2] , arr2[0] , arr2[1] , arr2[2]);    
	var starttimes = starttime.getTime();
	  
	var arrs1 = endDate.substring(0 , endDate.indexOf(" ")).split("-");
	var arrs2 = endDate.substr(endDate.indexOf(" ")+1).split(":");
	var lktime = new Date(arrs1[0] , arrs1[1]-1 , arrs1[2] , arrs2[0] , arrs2[1] , arrs2[2]);    
	var lktimes = lktime.getTime();

	if(lktimes > starttimes){   
		return ">";                      //结束日期大于开始日期
	}else if(starttimes == lktimes){
		return "==";   
	}else{
		return "<"; 
	}
}


//将日期Date转换为指定格式字符串
Date.prototype.format = function(format){
	var date = {
		"M+" : this.getMonth() + 1,
		"d+" : this.getDate(),
		"h+" : this.getHours(),
		"m+" : this.getMinutes(),
		"s+" : this.getSeconds(),
		"q+" : Math.floor((this.getMonth() + 3) / 3),
		"S+" : this.getMilliseconds()
	};
	if(/(y+)/i.test(format)){
		format = format.replace(RegExp.$1, (this.getFullYear() + '').substr(4 - RegExp.$1.length));
	}
	for(var k in date){
		if(new RegExp("(" + k + ")").test(format)){
			format = format.replace(RegExp.$1, RegExp.$1.length == 1? date[k] : ("00" + date[k]).substr(("" + date[k]).length));
		}
	}
	return format;
}


//封装生成随机数组函数
$.radom = function(x,y,z){
	//x:返回随机数组长度 , y:最大值 , z:最小值
	var array = new Array();
	return getArray(x,y,z);            
	
	function getArray(count,maxs,mins){
		while(array.length < count){
	 		var temp = getRandom(maxs,mins);
	 	    if(!serch(array,temp)){
	 	    	array.push(temp);
	 		}
		}
		return array;
	}
	 
	function getRandom(maxs,mins){  //随机生成maxs到mins之间的数
    	return Math.round(Math.random()*(maxs-mins))+mins;
	}
  	
	function serch(array,num){   //array是否重复的数
   		for(var i=0;i<array.length;i++){
     		if(array[i] == num){
				return true;
    		}      
   		}
    	return false;
	}	
}


//封装浏览器cookie函数
$.cookie = function(name, value, options) {    
	//name:cookie标识名称 , value:字符串取值 , options:保存的期限
	if (typeof value != 'undefined') {
		options = options || {};
		if (value === null) {
			value = '';
			options = $.extend({}, options);
			options.expires = -1;
		}
		var expires = '';
		if (options.expires && (typeof options.expires == 'number' || options.expires.toUTCString)) {
			var date;
			if (typeof options.expires == 'number') {
				date = new Date();
				date.setTime(date.getTime() + (options.expires * 24 * 60 * 60 * 1000));
			} else {
				date = options.expires;
			}
			expires = '; expires=' + date.toUTCString();
		}
		var path = options.path ? '; path=' + (options.path) : '';
		var domain = options.domain ? '; domain=' + (options.domain) : '';
		var secure = options.secure ? '; secure' : '';
		document.cookie = [name, '=', encodeURIComponent(value), expires, path, domain, secure].join('');
	} else {
		var cookieValue = null;
		if (document.cookie && document.cookie != '') {
			var cookies = document.cookie.split(';');
			for (var i = 0; i < cookies.length; i++) {
				var cookie = jQuery.trim(cookies[i]);
				if (cookie.substring(0, name.length + 1) == (name + '=')) {
					cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
					break;
				}
			}
		}
		return cookieValue;
	}
};


//缓存封装
var onlineCache={
	type: 'session',
	setType: function(){
		if(this.type == 'session'){
			return window.sessionStorage;
		}
		if(this.type == 'local'){
			return window.localStorage;
		}
	},
	set: function($key, $value){
		this.setType().setItem($key, $value);
	},
	get: function($key){
		return this.setType().getItem($key)
	}
}


//封装map形式数据部分方法
function addMap(){  
    this.elements = new Array();  
    //获取MAP元素个数  
    this.size = function() {  
        return this.elements.length;  
    };  
    //判断MAP是否为空  
    this.isEmpty = function() {  
        return (this.elements.length < 1);  
    };  
    //删除MAP所有元素  
    this.clear = function() {  
        this.elements = new Array();  
    };  
    //向MAP中增加元素（key, value)   
    this.put = function(_key, _value) {  
        this.elements.push( {  
            key : _key,  
            value : _value  
        });  
    };  
    //删除指定KEY的元素，成功返回True，失败返回False  
    this.remove = function(_key) {  
        var bln = false;  
        try {  
            for (i = 0; i < this.elements.length; i++) {  
                if (this.elements[i].key == _key) {  
                    this.elements.splice(i, 1);  
                    return true;  
                }  
            }  
        } catch (e) {  
            bln = false;  
        }  
        return bln;  
    };  
    //获取指定KEY的元素值VALUE，失败返回NULL  
    this.get = function(_key) {  
        try {  
            for (i = 0; i < this.elements.length; i++) {  
                if (this.elements[i].key == _key) {  
                    return this.elements[i].value;  
                }  
            }  
        } catch (e) {  
            return null;  
        }  
    };  
    //获取指定索引的元素（使用element.key，element.value获取KEY和VALUE），失败返回NULL  
    this.element = function(_index) {  
        if (_index < 0 || _index >= this.elements.length) {  
            return null;  
        }  
        return this.elements[_index];  
    };  
    //判断MAP中是否含有指定KEY的元素  
    this.containsKey = function(_key) {  
        var bln = false;  
        try {  
            for (i = 0; i < this.elements.length; i++) {  
                if (this.elements[i].key == _key) {  
                    bln = true;  
                }  
            }  
        } catch (e) {  
            bln = false;  
        }  
        return bln;  
    };  
    //判断MAP中是否含有指定VALUE的元素  
    this.containsValue = function(_value) {  
        var bln = false;  
        try {  
            for (i = 0; i < this.elements.length; i++) {  
                if (this.elements[i].value == _value) {  
                    bln = true;  
                }  
            }  
        } catch (e) {  
            bln = false;  
        }  
        return bln;  
    };  
    //获取MAP中所有VALUE的数组（ARRAY）  
    this.values = function() {  
        var arr = new Array();  
        for (i = 0; i < this.elements.length; i++) {  
            arr.push(this.elements[i].value);  
        }  
        return arr;  
    };  
    //获取MAP中所有KEY的数组（ARRAY）  
    this.keys = function() {  
        var arr = new Array();  
        for (i = 0; i < this.elements.length; i++) {  
            arr.push(this.elements[i].key);  
        }  
        return arr;  
    };
}



//*------------------------------封装开源插件区域------------------------*//


/* Pause Resume jQuery(控制jquery动画的暂停) */
(function(){var e=jQuery,f="jQuery.pause",d=1,b=e.fn.animate,a={};function c(){return new Date().getTime()}e.fn.animate=function(k,h,j,i){var g=e.speed(h,j,i);g.complete=g.old;return this.each(function(){if(!this[f]){this[f]=d++}var l=e.extend({},g);b.apply(e(this),[k,e.extend({},l)]);a[this[f]]={run:true,prop:k,opt:l,start:c(),done:0}})};e.fn.pause=function(){return this.each(function(){if(!this[f]){this[f]=d++}var g=a[this[f]];if(g&&g.run){g.done+=c()-g.start;if(g.done>g.opt.duration){delete a[this[f]]}else{e(this).stop();g.run=false}}})};e.fn.resume=function(){return this.each(function(){if(!this[f]){this[f]=d++}var g=a[this[f]];if(g&&!g.run){g.opt.duration-=g.done;g.done=0;g.run=true;g.start=c();b.apply(e(this),[g.prop,e.extend({},g.opt)])}})}})();


/* jquery.jscollbar(自定义滚动条) */
(function(f){function p(a,b,c,d){var e=this.opt,m=this.$con[0],g=this.$node.clone().appendTo("body").width(b).height(c).find(".jscrollbar"),n=e.showXBar&&m.scrollWidth>b,m=e.showYBar&&m.scrollHeight>c,k=0,k="",h="outer"===e.position?d:0;n&&m?k="xy":n?(k=g.css({height:c-h,zIndex:-1})[0].scrollHeight,k="x"+(e.showXBar&&k>c-h?"y":"")):m&&(k=g.css({width:b-h,zIndex:-1})[0].scrollWidth,k=(e.showXBar&&k>b-h?"x":"")+"y");this.scrollWidth=g[0].scrollWidth;this.scrollHeight=g[0].scrollHeight;g.parent().remove();
	this.bars=k;e=this.delta=(this.bars.length-1)*d;g=this.thumbSize={x:q.call(this,"x"),y:q.call(this,"y")};this.maxPos={x:b-g.x-e,y:c-g.y-e};this.maxSPos={x:this.scrollWidth-b+e,y:this.scrollHeight-c+e};b=this.bars.split("");n=0;for(m=b.length;n<m;n++)c=l[g=b[n]],e=l["xy".replace(g,"")].s,f("<div></div>").addClass(g).data("thumbType",g).css(e,d).append(f(r)[c.s](this.thumbSize[g]).data("type",g)).insertAfter(this.$con.css(e,"-="+("outer"===this.opt.position?d:0)));"inner"===this.opt.position&&a.find(".x,.y").css("background",
    "transparent")}function q(a){a=l[a];return Math.max(20,Math.pow(this.$con[a.s]()-this.delta,2)/this.$con[0][a.ss])}function s(){var a=this;this.$node.on("mousedown",".thumb",function(b){t.call(a,b,b.target);return!1}).on("click",".x,.y",function(b){var c=0,c=b.target,d=f.data(c,"thumbType"),e=l[d];c===this&&(c=b["page"+d.toUpperCase()]-f(c).offset()[e.p],a.scrollTo(d,c/a.maxPos[d]*a.maxSPos[d],a.opt.speed))});f(document).unbind("mouseup.jsb").bind("mouseup.jsb",function(){f(this).unbind("mousemove.jsb")});
    u.call(this);"inner"===this.opt.position&&v.call(this)}function v(){var a=this.$node;a.hover(function(){a.find(".x,.y").stop().animate({opacity:1},200)},function(){a.find(".x,.y").stop().animate({opacity:0},500)}).mouseout()}function u(){var a=this.node,b=this,c=null;this.opt.mouseEvent&&(c=function(a){a=a||window.event;b.scrollBy(2===b.bars.length?"y":b.bars,-(a.wheelDelta?a.wheelDelta/120:-a.detail/3)*b.opt.mouseSpeed);f.event.fix(a).preventDefault()},a.addEventListener?(a.addEventListener("mousewheel",
    c),a.addEventListener("DOMMouseScroll",c)):a.onmousewheel=c)}function t(a,b){var c={X:a.pageX,Y:a.pageY},d=this,e={X:parseInt(f(b).css("left"))||0,Y:parseInt(f(b).css("top"))||0};f(document).bind("mousemove.jsb",function(a){var g=f.data(b,"type"),h=g.toUpperCase(),k=l[g].p;a=Math.min(Math.max(0,e[h]+(a["page"+h]-c[h])),d.maxPos[g]);f(b).css(k,a);d.scroll(g);return!1})}function h(a,b){this.node=a[0];this.$node=a;this.width=a.width();this.height=a.height();this.opt=b;this.plugID="jsb_"+Math.floor(9E3*
    Math.random()+1E3);var c=this.width,d=this.height,e=b.width;this.$node.css("overflow","hidden").addClass("jscrollbar").wrapInner(w.attr("id",this.plugID).css({width:c,height:d}));this.$con=f("#"+this.plugID);p.call(this,this.$node,c,d,e);s.call(this)}var w=f('<div class="jscrollbar" style="overflow: hidden"></div>'),r='<div class="thumb"></div>',l={x:{s:"width",p:"left",sp:"scrollLeft",ss:"scrollWidth"},y:{s:"height",p:"top",sp:"scrollTop",ss:"scrollHeight"}};h.fn=h.prototype;h.fn.update=function(a){var b=
    this.$node,c=this.width=b.width(),d=this.height=b.height(),e=this.opt.width,f=this.getScrollPos("x"),g=this.getScrollPos("y");this.$node.find(".x,.y").remove();this.$con.css({width:c,height:d});p.call(this,b,c,d,e);switch(a){case "relative":f&&this.scrollTo("x",f);g&&this.scrollTo("y",g);break;case "bottom":this.scrollTo("y",this.maxSPos.y);break;case "right":this.scrollTo("x",this.maxSPos.x);break;default:this.scroll()}this.scroll()};h.fn.getThumbLocation=function(a){return parseFloat(this.$node.find("."+
    a+" .thumb").css(l[a].p))||0};h.fn.getScrollPos=function(a){return this.$con[0][l[a].sp]};h.fn.scroll=function(a){void 0===a&&(a="x",this.scroll("y"));this.$con[0][l[a].sp]=this.getThumbLocation(a)/this.maxPos[a]*this.maxSPos[a]};h.fn.scrollBy=function(a,b){this.scrollTo(a,this.$con[0][l[a].sp]+b)};h.fn.scrollTo=function(a,b,c){b=Math.max(Math.min(this.maxSPos[a],b),0);var d={};d[l[a].sp]=b;this.$con.animate(d,c||0);this.$node.trigger("scroll",[a,b]);c=c||0;var d=l[a],e={};e[d.p]=this.maxPos[a]*(b||
    this.$con[0][d.sp])/this.maxSPos[a];this.$node.find("."+a+" .thumb").animate(e,c)};f.fn.jscrollbar=function(a){if("string"===typeof a){var b=this.data("jsb_data");return b?b[a].apply(b,[].slice.call(arguments,1)):this}a=f.extend({},{width:8,position:"inner",showXBar:!0,showYBar:!0,mouseEvent:!0,mouseSpeed:30,speed:250},a);return this.each(function(){f.data(this,"jsb_data")||f.data(this,"jsb_data",new h(f(this),a))})};f.fn.jscrollbar.version="2.0.0"})
(jQuery);
