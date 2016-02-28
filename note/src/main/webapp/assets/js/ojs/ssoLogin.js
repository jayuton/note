CommonUtils.regNamespace("sso", "user");
sso.user=(function(){
	var _loadingTime=new Date().getTime();
	var _getLoadingTime=function(){
		return _loadingTime;
	}
	var _refreshValidateCodeImg=function(){
		$('#validatecodeImg').css({"width":"auto","height":"40px"}).attr('src',
				contextPath+'/validatecode/codeimg?' + Math.floor(Math.random()*100)).fadeIn();
	};
	var _popupLogin=function(){
				ec.form.dialog.createDialog({id:"-popup-login",url:contextPath+"/staff/login/popupLogin",urlMethod:"POST",close:true,
				"initCallBack":function(){
					//显示图片验证码
					if(!$("#validatecode").attr("src") || $("#validatecode").attr("src")==""|| $("#validatecode").attr("src")==null ){
						$("#validatecode").on("click.firstgencode focus.firstgencode",function(){
							_refreshValidateCodeImg();	
							$(this).off(".firstgencode");
						});
					}
				},
				"submitCallBack":function(dialogForm,dialog){
					var params={"staffCode":$("#popup_loginform #staffCode").val(),"password":$("#popup_loginform  #password").val()};
					$.callServiceAsJson(contextPath+"/staff/login/logindopop?validatecode="+$("#validatecode").val(), params, {
						"before":function(xhr){
							xhr.setRequestHeader("_al_ec_token",$("#_al_ec_token").val());
						},
						"done" : function(resp){
							if(resp.code==0){
								dialogForm.close(dialog);
								//window.location.reload();
							}else{
								_refreshValidateCodeImg();
								if(resp.errorsList&& resp.errorsList.length>0 ){
									dialogForm.showError(resp.errorsList[0].message);
								} else {
									dialogForm.showError(resp.data);
								}
								
							}
						},"fail":function(resp){
							dialogForm.showError(resp.errorsList[0].msg+'('+resp.errorsList[0].code+')');	
						}
					});
				}
			});
	}
	// 要暴露出的公共方法
	return {
		popupLogin:_popupLogin,
		getLoadingTime:_getLoadingTime,
		refreshValidateCodeImg:_refreshValidateCodeImg
	};
})();
jQuery(function(){
	jQuery("a").filter(function(i){
		var href=$(this).attr("href");
		var valid=typeof href !="undefined" &&  !!href && href.indexOf("javascript")<0 && href.indexOf("#")!=0 ;
		var clstag=$(this).attr("clstag");
		var isValidate=typeof clstag !="undefined" && !!clstag &&  clstag.indexOf("ssologin")>=0;
		return valid && !isValidate;
	}).bind("click",function(event){
		var nowTime=new Date().getTime();
		if((nowTime-sso.user.getLoadingTime())/1000<=6){//超过10分钟判断一次
			return;
		}
		var response=$.callServiceAsJson(contextPath+"/staff/login/isLogin");
		if (response.code !=0){
			//阻止原元素默认事件发生,如 a标签href跳转事件
			//弹出登录窗口
			event.preventDefault();
			sso.user.popupLogin();
			//如果提供了事件对象，则这是一个非IE浏览器
			if (event.stopPropagation){
				//因此它支持W3C的 stopPropagation()方法
				event.stopPropagation();
			} else{
				//否则，我们需要使用IE的方式来取消事件冒泡
				event.cancelBubble = true;
			}
		}
	});
});