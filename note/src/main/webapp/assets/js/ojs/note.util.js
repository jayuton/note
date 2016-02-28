/**
 * 笔记通用JS封装方法
 * @author jayu
 */
CommonUtils.regNamespace("note","util");
/**
 * 工具包
 */
note.util=(function(){
	
	/**
	 * 显示后台验证错误信息
	 * @param 错误数组
	 */
	var _showErrorsTip=function(errorArray,form,position){
		var elArray=[];
		if(!form) {
			form=$("body");
		}
		$.each(errorArray,function(i){
			var el=$("#"+this.element,form);
			if(!(el && el.length >0) ){
				el=$("input[name='"+this.element+"']",form);
				if(el.length==0) {
					el=$("select[name='"+this.element+"']",form);
				}
				if(el.length==0) {
					el=$("textarea[name='"+this.element+"']",form);
				}
				if(el.length>0) {
					el=el.eq(0);
				} else {
					return true;
				}
			}
			var container=$("#ketchup-"+this.element,form);
			var elOffset = el.offset(); 
			var leftTip=elOffset.left;
			var topTip= elOffset.top;
			var elType=el.attr("type");
			var name=el.attr("name");
			if(elType=="radio" || elType=="checkbox"){
				$("input[type="+elType+"][name='"+name+"']",form).each(function(n){
					if(!$(this).hasClass("ketchup-input-error")){
						$(this).addClass("ketchup-input-error");
					}
				});
			} else {
				if(!el.hasClass("ketchup-input-error")){
					el.addClass("ketchup-input-error");
				}
			}
			var sw=Math.max(document.documentElement.scrollWidth, document.documentElement.clientWidth);
			if(container.length == 0 ){
				
				var $error=null;
				if(typeof position=="undefined" || position=="centerdown"){
					$error=$('<div id="ketchup-'+this.element+'" style="display:none"><ul></ul><span class="centerdown"></span></div>');
					topTip=topTip-10;
				}else if(position=="leftdown"){
						$error=$('<div id="ketchup-'+this.element+'" style="display:none"><ul></ul><span class="leftdown"></span></div>');
						leftTip=(elOffset.left + el.outerWidth()+100)>sw?leftTip + el.outerWidth()/2-50: leftTip + el.outerWidth()- 20;
				}else if(/^.*up$/gi.test(position)){
					$error=$('<div id="ketchup-'+this.element+'" style="display:none"><span class="centerup"></span><ul></ul></div>');
					topTip=topTip+el.outerHeight()+50;
				}else{
					$error=$('<div id="ketchup-'+this.element+'" style="display:none"><span class="rightcenter"></span><ul></ul></div>');
					leftTip=leftTip+el.outerWidth()+20;
				}
				$error.addClass('ketchup-error')
				.css  ( {
								top : topTip,
								left: leftTip
				   }).appendTo(form);
				
			}
			container=$("#ketchup-"+this.element,form);
			var list = container.children('ul');

			if(list.length>0) {
				if($.inArray(this.element,elArray)<0){
					list.html("");
				}
				$('<li>'+this.message+'</li>').appendTo(list);
			}

			//需要处理不同的input select textarea
			//查找同组的值有变更就隐藏
		
			if(elType=="radio" || elType=="checkbox"){
				$("input[type="+elType+"][name='"+name+"']",form).on("mouseover",function() {
					var boj=this;
					var left=el.offset().left+(el.width()-container.width())/2;
					if(typeof position=="undefined" || position=="centerdown"){
						container
						.css({top    : el.offset().top-el.height()-32,left:left})
						.animate({
							  top    : el.offset().top-el.height()-22,
							  opacity: 1
							}, "fast").show();	
					}else if(position=="leftdown"){
						container
						.css({top    : el.offset().top-el.height()-32,left:left})
						.animate({
							  top    : el.offset().top - container.height()-5,
							  opacity: 1
							}, "fast").show();	
					}else if(/^.*up$/gi.test(position)){
						container
						.css({top    : el.offset().top+el.height()+52,left:left})
						.animate({
							  top    : el.offset().top+el.height()+22,
							  opacity: 1
							}, "fast").show();	
					}else{
						container
						.css({top    : el.offset().top-20})
						.animate({
							  top    : el.offset().top+el.height()/3,
							  opacity: 1
							}, "fast").show();	
					}
					
				 }).on("mouseout",function(){
						var boj=this;
						container
						.animate({
							  top    : el.offset().top+el.height()/2-20,
							  opacity: 0
							}, "fast", function() {
							}).hide();
					}).one("keyup",function(){
						$(boj).removeClass("ketchup-input-error");
						el.off("mouseover","**").off("mouseout","**");
						container.hide().remove();
					});
			} else {
				
				el.on("mouseover",function(){
					var left=el.offset().left+(el.width()-container.width())/2;
					if(typeof position=="undefined" || position=="centerdown"){
						container
						.css({top    : el.offset().top-el.height()-32,left:left})
						.animate({
							  top    : el.offset().top-el.height()-22,
							  opacity: 1
							}, "fast").show();
					}else if(position=="leftdown"){
						left=(el.offset().left +el.outerWidth()+ container.outerWidth())>sw?sw- container.outerWidth()-10: el.offset().left + el.outerWidth()- 20;
						container
						.css({top    : el.offset().top-el.height()-32,left:left})
						.animate({
							  top    : el.offset().top - container.height()-5,
							  opacity: 1
							}, "fast").show();	
					}else if(/^.*up$/gi.test(position)){
						container
						.css({top    : el.offset().top+el.height()+52,left:left})
						.animate({
							  top    : el.offset().top+el.height()+22,
							  opacity: 1
							}, "fast").show();	
					}else{
						container
						.css({top    : el.offset().top-20})
						.animate({
							  top    : el.offset().top+el.height()/3,
							  opacity: 1
							}, "fast").show();
					}
				}).on("mouseout",function(){
					container
					.animate({
						  top    : el.offset().top+el.height()/3-20,
						  opacity: 0
						}, "fast", function() {
							
						}).hide();
				}).one("keyup",function(){
					el.removeClass("ketchup-input-error");
					el.off("mouseover","**").off("mouseout","**");
					container.hide().remove();
				});
			}
	

			elArray.push(this.element);
			
		});
	};
	//参数支持两个,第一个源数据，第二个为替换数据
	var _defaultStr = function(){
		var len = arguments.length;
		if(len == 1)
			return typeof arguments[0] == "undefined" || arguments[0]==null?"":arguments[0];
		else if(len == 2)
			return typeof arguments[0] == "undefined" || arguments[0]==null?arguments[1]:arguments[0];
		else
			return;
	};
	var _inArray=function(id,array){
		if(array){
			for(var i in array){
				if(array[i]==id){
					return 1;
				}
			}
			return 0;
		}else{
			return 0;
		}
	};
	//要暴露出的公共方法
	return {
		showErrors:_showErrorsTip,
		defaultStr:_defaultStr,
		inArray:_inArray
	};
})();
var _pjaxContentCache={};
var _onPjaxPopstate=function(state){
	  
	  if(state.id){
		  var pageContentBody = $('.page-content .page-content-body');
		  if(_pjaxContentCache[state.id]){
			  pageContentBody.html(_pjaxContentCache[state.id]);
		  }else if(state.url){
			  window.location.href=state.url;
		  }
		  if(_pjaxContentCache.length>20){
			  _pjaxContentCache.pop();
		  }
	  }
}