/**
 * 弹出层窗口
 * 
 * @author jayu
 */
CommonUtils.regNamespace("note.form","dialog");
/**
 * 依赖 ketchup jquery.simaple 两个JS
 * 对 simple dialog二次封装
 */
note.form.dialog = (function(){
	var defaultOptions={id:"","submitCallBack":null,"initCallBack":null,position:["15%"],modal:true,drag:false,width:null,height:null,close:true,
			beforeClose:null,afterClose:null,url:null,urlMethod:"GET",appendTo:'body',reNew:true,
			overlayLeft:null,overlayTop:null};//heihgt:250,submitCallBack:function(dialogForm,dialog)
	var _createDialog=function(options){
		defaultOptions=$.extend({},defaultOptions,options||{});
//		if(!defaultOptions.reNew){
//			var modal=$("#ec-dialog-form-container"+defaultOptions.id).data("modal");
//			if(modal){
//				dialogForm.open(modal.d);
//				return;
//			}
//		}
		if(!!defaultOptions.url){
			if(defaultOptions.urlMethod=="GET"){
				if($("#ec-dialog-form-container"+defaultOptions.id).length==0){
					var response=$.callServiceAsHtmlGet(defaultOptions.url);
					if(response.code==0){
						$(defaultOptions.appendTo).append(response.data);
						$("#ec-dialog-form-container"+defaultOptions.id).data("url",defaultOptions.url);
					}else{
						return;
					}
				}else{
					if($("#ec-dialog-form-container"+defaultOptions.id).data("url") !=defaultOptions.url){
						$.modal.close();
						$("#ec-dialog-form-container"+defaultOptions.id).remove();
						var response=$.callServiceAsHtmlGet(defaultOptions.url);
						if(response.code==0){
							$(defaultOptions.appendTo).append(response.data);
						}else{
							return;
						}
					}
				}
			}else if(defaultOptions.urlMethod=="POST"){
				$.modal.close();
				$("#ec-dialog-form-container"+defaultOptions.id).remove();
				var response=$.callServiceAsHtml(defaultOptions.url);
				if(response.code==0){
					$(defaultOptions.appendTo).append(response.data);
				}else{
					return;
				}
			}
		}
		if(!(defaultOptions && defaultOptions.submitCallBack && $.isFunction(defaultOptions.submitCallBack))){
			alert("submitCallBack is must have");
			return;
		}
		// create a modal dialog with the data
		if(!defaultOptions.width){
			$("#ec-dialog-form-container"+defaultOptions.id).css({width:450});
		}else {
			$("#ec-dialog-form-container"+defaultOptions.id).css({width:defaultOptions.width});
		}
		var modal=null;
		if(defaultOptions.close){
			modal=$("#ec-dialog-form-container"+defaultOptions.id).modal({
				closeHTML: '<a href="javascript:void(0)" title="Close" class="modal-close simplemodal-close"><div class="modal-close "></div></a>',
				position: defaultOptions.position,
				autoResize:false,
				escClose:true,
				modal:true,
				persist:!defaultOptions.reNew,
				overlayId: 'ec-dialog-overlay'+defaultOptions.id,
				containerId: 'ec-dialog-container'+defaultOptions.id,
				onOpen: dialogForm.open,
				onShow: dialogForm.show,
				onClose: dialogForm.close
			});
		} else {
			modal=$("#ec-dialog-form-container"+defaultOptions.id).modal({
				closeHTML: '',
				position: defaultOptions.position,
				autoResize:false,
				escClose:false,
				persist:!defaultOptions.reNew,
				modal:defaultOptions.modal,
				overlayId: 'ec-dialog-overlay'+defaultOptions.id,
				containerId: 'ec-dialog-container'+defaultOptions.id,
				onOpen: dialogForm.open,
				onShow: dialogForm.show,
				onClose: dialogForm.close
			});
			if(defaultOptions.overlayLeft)
				$("#ec-dialog-overlay"+defaultOptions.id).css({"left":defaultOptions.overlayLeft});
			if(defaultOptions.overlayTop)
				$("#ec-dialog-overlay"+defaultOptions.id).css({"top":defaultOptions.overlayTop});
		}
		$("#ec-dialog-form-container"+defaultOptions.id).data("modal",modal);
	};
	var dialogForm={
			open:function (dialog) {
                var dialogContainerDiv$=$(dialog.container);
				if(defaultOptions.initCallBack && $.isFunction(defaultOptions.initCallBack)){
					var flag=defaultOptions.initCallBack.apply(this,[dialogForm,dialog,dialog.container]);
					if(typeof flag!="undefined" && flag==false){
						$.modal.close();
						return;
					}
				}
				// add padding to the buttons in firefox/mozilla
				if ($.browser.mozilla) {
                    dialogContainerDiv$.find('.ec-dialog-form-button').css({
						'padding-bottom': '2px'
					});
				}
				// input field font size
				if ($.browser.safari) {
                    dialogContainerDiv$.find('.ec-dialog-form-input').css({
						'font-size': '.9em'
					});
				}
				var title$=dialogContainerDiv$.find('.ec-dialog-form-title');
				var title=title$.html();
				if(!!title$.data("firsttitle")){
					title =title$.data("firsttitle");
				} else {
					title$.data("firsttitle",title);	
				}
				
				if($.ketchup)
				$.ketchup.removeErrorContainer(dialogContainerDiv$.find("#ec-dialog-form-container"+defaultOptions.id));
				title$.html('加载中...');
                dialogContainerDiv$.find('.ec-dialog-form-loading').show();
                dialogContainerDiv$.find('.ec-dialog-form-bottom-button').hide();
                dialogContainerDiv$.find('.ec-dialog-form-form').hide();
				// dynamically determine height
                dialogContainerDiv$=$("#ec-dialog-container"+defaultOptions.id);
				var h =dialogContainerDiv$.height();
				h += 22;
				if(defaultOptions.height){
					h=defaultOptions.height;
				}
				if(h<45){
					h=$("#ec-dialog-form-container"+defaultOptions.id+' .ec-dialog-form-bottom-button').height()+h+title$.height();
                    dialogContainerDiv$.css({"height":h});
				}
                dialogContainerDiv$.data("height",h);
				if(defaultOptions.drag){
					try{
                        dialogContainerDiv$.draggable({handle: "div.ec-dialog-form-top", opacity: 0.6});
                        dialogContainerDiv$.find("div.ec-dialog-form-top").css({"cursor":"move"});
					}catch(e){
						//jquery.ui.core,jquery.ui.widget,jquery.ui.mouse.query.ui.draggable three js file
					}
				}
				dialog.overlay.fadeIn(100, function () {
					dialog.container.fadeIn(100, function () {
                        $(dialog.container).find("#ec-dialog-form-container"+defaultOptions.id+' .ec-dialog-form-content').css({height:"80"});
						dialog.data.fadeIn(200, function () {
								title$.html(title);

                                    $(dialog.container).find("#ec-dialog-form-container"+defaultOptions.id+' .ec-dialog-form-loading').hide();
                                    $(dialog.container).find("#ec-dialog-form-container"+defaultOptions.id+' .ec-dialog-form-form').fadeIn(200, function () {
									var h=0;
									if(defaultOptions.height){
										h= defaultOptions.height;
									} else {
										h=$(dialog.container).find("#ec-dialog-form-container"+defaultOptions.id+' .ec-dialog-form-form').height();
										h += 22;
										if(h<25){
											h=$(dialog.container).find("#ec-dialog-form-container"+defaultOptions.id+' .ec-dialog-form-form').children().first().height();
										}
										h += 22;
									}
									if($(dialog.container).height()<h){
										var containerHeight=$(dialog.container).find("#ec-dialog-form-container"+defaultOptions.id+' .ec-dialog-form-bottom-button').height()+h+title$.height();
                                        $(dialog.container).css({"height":containerHeight});
									}
                                    $(dialog.container).data("height",h);
                                    $(dialog.container).find("#ec-dialog-form-container"+defaultOptions.id+' .ec-dialog-form-content').animate({
										height: h
									});
									// fix png's for IE 6
                                    $(dialog.container).find("#ec-dialog-form-container"+defaultOptions.id+' .ec-dialog-form-bottom-button').show();
									if ($.browser.msie && $.browser.version < 7) {
                                        $(dialog.container).find("#ec-dialog-form-container"+defaultOptions.id+' .ec-dialog-form-button').each(function () {
											if ($(this).css('backgroundImage').match(/^url[("']+(.*\.png)[)"']+$/i)) {
												var src = RegExp.$1;
												$(this).css({
													backgroundImage: 'none',
													filter: 'progid:DXImageTransform.Microsoft.AlphaImageLoader(src="' +  src + '", sizingMethod="crop")'
												});
											}
										});
									}
								});

						});
					});
				});
			},
			show:function (dialog) {
				if($.ketchup){
					var ketchupForm=$(dialog.container).find("#ec-dialog-form-container"+defaultOptions.id).bind('formIsValid', function(event, form) {
                        $(dialog.container).find("#ec-dialog-form-container"+defaultOptions.id+' .ec-dialog-form-bottom-button').hide();
                        $(dialog.container).find("#ec-dialog-form-container"+defaultOptions.id+' .ec-dialog-form-message').hide().empty();
                        $(dialog.container).find("#ec-dialog-form-container"+defaultOptions.id+' .ec-dialog-form-title').html('提交中...');

                        $(dialog.container).find("#ec-dialog-form-container"+defaultOptions.id+' .ec-dialog-form-form').fadeOut(200);
                        $(dialog.container).find("#ec-dialog-form-container"+defaultOptions.id+' .ec-dialog-form-content').animate({
							height: '80'
						}, function () {
                            $(dialog.container).find("#ec-dialog-form-container"+defaultOptions.id+' .ec-dialog-form-loading').fadeIn(200, function () {
								defaultOptions.submitCallBack.apply(this,[dialogForm,dialog,dialog.container]);
							});
						});
						
					}).ketchup({bindElement: $(dialog.container).find("#ec-dialog-form-container"+defaultOptions.id+" #dialogFormSubmit"),"appendTo":function(contianerror,el){
					  	contianerror.appendTo($(el).parent());
					  },"containerRelative":{top:0,left:0},targetPosition:"centerup"});
                    $(dialog.container).find("#ec-dialog-form-container"+defaultOptions.id).data("ketchupForm",ketchupForm);
				} else {
                    $(dialog.container).find("#ec-dialog-form-container"+defaultOptions.id+" #dialogFormSubmit").click(function(){
                        $(dialog.container).find("#ec-dialog-form-container"+defaultOptions.id+' .ec-dialog-form-message').hide().empty();
                        $(dialog.container).find("#ec-dialog-form-container"+defaultOptions.id+' .ec-dialog-form-title').html('提交中...');
                        $(dialog.container).find("#ec-dialog-form-container"+defaultOptions.id+' .ec-dialog-form-bottom-button').hide();
                        $(dialog.container).find("#ec-dialog-form-container"+defaultOptions.id+' .ec-dialog-form-form').fadeOut(200);
                        $(dialog.container).find("#ec-dialog-form-container"+defaultOptions.id+' .ec-dialog-form-content').animate({
							height: '80'
						}, function () {
                            $(dialog.container).find("#ec-dialog-form-container"+defaultOptions.id+' .ec-dialog-form-loading').fadeIn(200, function () {
								defaultOptions.submitCallBack.apply(this,[dialogForm,dialog,dialog.container]);
							});
						});
					});
				}
			},
			close:function (dialog) {
				var modal=this;
//				if(!defaultOptions.reNew){
//					if(modal){
//						dialog.overlay.hide();
//						dialog.container.hide();
//						dialog.data.hide();
//						return;
//					}
//				}
				if($.isFunction(defaultOptions.beforeClose)){	
					if(!defaultOptions.beforeClose.apply(this)){
						modal.bindEvents();
						modal.occb = false;
						return;
					};
				}
				if($.ketchup){
					$.ketchup.removeErrorContainer($("#ec-dialog-form-container"+defaultOptions.id).data("ketchupForm"));
					$("#ec-dialog-form-container"+defaultOptions.id).unbind('formIsValid');
				}
				$("#ec-dialog-form-container"+defaultOptions.id+' .ec-dialog-form-message').hide().empty();
				$("#ec-dialog-form-container"+defaultOptions.id+' .ec-dialog-form-title').html('关闭...');
				$("#ec-dialog-form-container"+defaultOptions.id+' .ec-dialog-form-loading').show();
				$("#ec-dialog-form-container"+defaultOptions.id+' .ec-dialog-form-bottom-button').hide();
				$("#ec-dialog-form-container"+defaultOptions.id+' .ec-dialog-form-form').fadeOut(200);
				$("#ec-dialog-form-container"+defaultOptions.id+' .ec-dialog-form-content').animate({
					height: 40
				}, function () {
					$("#ec-dialog-form-container"+defaultOptions.id+' .ec-dialog-form-loading').hide();
					dialog.data.fadeOut(100, function () {
						dialog.container.fadeOut(100, function () {
							dialog.overlay.fadeOut(100, function () {
								$.modal.close();
								if($.isFunction(defaultOptions.afterClose)){
									defaultOptions.afterClose.apply(this);
								}
							});
						});
					});
				});
			},
			showError: function (message) {
				$("#ec-dialog-form-container"+defaultOptions.id+' .ec-dialog-form-loading').hide();
				var message$=$("#ec-dialog-form-container"+defaultOptions.id+' .ec-dialog-form-message')
				.html($('<div class="ec-dialog-form-error"></div>').append(message)).show();
				$("#ec-dialog-form-container"+defaultOptions.id+' .ec-dialog-form-content').animate({
					height: $("#ec-dialog-container"+defaultOptions.id).data("height")+message$.height()
				}, function () {
					var title$=$("#ec-dialog-form-container"+defaultOptions.id+' .ec-dialog-form-title');
					title$.html(title$.data("firsttitle"));
					$("#ec-dialog-form-container"+defaultOptions.id+' .ec-dialog-form-form').fadeIn(200, function () {
						$("#ec-dialog-form-container"+defaultOptions.id+' .ec-dialog-form-bottom-button').show();
						// fix png's for IE 6
						if ($.browser.msie && $.browser.version < 7) {
							$("#ec-dialog-form-container"+defaultOptions.id+' .ec-dialog-form-button').each(function () {
								if ($(this).css('backgroundImage').match(/^url[("']+(.*\.png)[)"']+$/i)) {
									var src = RegExp.$1;
									$(this).css({
										backgroundImage: 'none',
										filter: 'progid:DXImageTransform.Microsoft.AlphaImageLoader(src="' +  src + '", sizingMethod="crop")'
									});
								}
							});
						}
					});
				});
				
			}
	};
	// 要暴露出的公共方法
	return {
		createDialog:_createDialog
	};
})();