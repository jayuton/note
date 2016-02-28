var Login = function () {
	/**
	 * 登录页面初始化表单元素校验
	 */
	var _form_valid_init = function() {
		// submit提交
		var loginform = $('#loginform').bind('formIsValid', function(event, form) {
			var params={"account":$("#account",form).val(),"password":$("#password",form).val()};
			params=JSON.stringify(params);
			$.callServiceAsJson(contextPath+"/login/logindo", params, {
				"before":function(){
					$("#loginbutton",form).attr("disabled","disabled").html('Logining....<i class="m-icon-swapright m-icon-white"></i>');
				},
				"done" : callBack_success_login,
				"always":function(){
					$("#loginbutton",form).attr("disabled",false).html('Login<i class="m-icon-swapright m-icon-white"></i>');
				}
			});
		}).ketchup({bindElement:"loginbutton","targetPosition":"leftdown"});
		$("#loginform").data("currentForm",loginform);
		
	};
	/**
	 * 登录成功,回调函数
	 */
	var callBack_success_login=function(response){
		if (response.code == 0) {
			CommonUtils.getRootWin().location.href=contextPath+"/user/main";
		//用户入参合法性校验数错,//参数业务校验不通过,//验证码不对通过
		} else if (response.code ==1001 || response.code == 1004 || response.code == 1005){
			note.util.showErrors(response.errorsList,$("#loginform"),"leftdown");
		} else {
			if(response.errorsList&& response.errorsList.length>0 ){
				alert(response.errorsList[0].message);
			} else {
				alert(response.data);
			}
		}
		_refreshValidateCodeImg();
		//$("#validatecode").val("");
	};
	
	var _refreshValidateCodeImg=function(){
		$('#validatecodeImg').css({"width":"auto","height":"40px"}).attr('src',contextPath+'/validatecode/codeimg.jpg?' + Math.floor(Math.random()*100)).fadeIn();
	};
	
	
	/**
	 * 注册页面初始化表单元素校验
	 */
	var _form_register_init = function() {
		// submit提交
	
		var registerForm=$('#registerForm').bind('formIsValid', function(event, form) {
			var params={"account":$("#account",form).val(),"password":$("#register_password",form).val(),"nick":$("#nick",form).val(),"email":$("#email",form).val()};
			if($("#rpassword",form).val() != params.password || $.trim(params.password)==""){
				note.util.showErrors([{"element":'rpassword','message':'密码不一致！'}],$("#registerForm"),"centerdown");
				return;
			}
			params=JSON.stringify(params);
			$.callServiceAsJson(contextPath+"/login/registerdo", params, {
				"before":function(){
					$("#register-submit-btn").attr("disabled","disabled").html('registering....<i class="m-icon-swapright m-icon-white"></i>');
				},
				"done" : function(response){
					if (response.code == 0) {
						alert(response.data);
						CommonUtils.getRootWin().location.href=contextPath+"/user/main";
					//用户入参合法性校验数错,//参数业务校验不通过,//验证码不对通过
					} else if (response.code ==1001 || response.code == 1004 || response.code == 1005){
						note.util.showErrors(response.errorsList,$("#registerForm"),"leftdown");
					} else {
						if(response.errorsList&& response.errorsList.length>0 ){
							alert(response.errorsList[0].message);
						} else {
							alert(response.data);
						}
					}
				},
				"always":function(){
					$("#register-submit-btn").attr("disabled",false).html('Sign Up <i class="m-icon-swapright m-icon-white"></i>');
				}
			});
		}).ketchup({bindElement:"register-submit-btn","targetPosition":"leftdown"});
		$("#registerForm").data("currentForm",registerForm);
	};
    return {
        //main function to initiate the module
        init: function () {
	        jQuery('#forget-password').click(function () {
	            jQuery('.login-form').hide();
	            jQuery('.forget-form').show();
	            if($("#loginform").data("currentForm")){
	            	$.ketchup.removeErrorContainer($("#loginform").data("currentForm"));
	            }
	        });

	        jQuery('#back-btn').click(function () {
	            jQuery('.login-form').show();
	            jQuery('.forget-form').hide();
	            _form_valid_init();
	        });

	        jQuery('#register-btn').click(function () {
	            jQuery('.login-form').hide();
	            jQuery('.register-form').show();
	            if($("#loginform").data("currentForm")){
	            	$.ketchup.removeErrorContainer($("#loginform").data("currentForm"));
	            }
		        _form_register_init();
		        
	        });

	        jQuery('#register-back-btn').click(function () {
	            jQuery('.login-form').show();
	            jQuery('.register-form').hide();
	        	if($("#registerForm").data("currentForm")){
	        		$.ketchup.removeErrorContainer($("#registerForm").data("currentForm"));
	        	}
	        	 _form_valid_init();
	        });
	        
	      $.backstretch([
	       		    contextPath+ "/assets/img/bg/2.jpg",
	       		contextPath+"/assets/img/bg/4.jpg"
	       		        ], {
	       		          fade: 1000,
	       		          duration: 5000
	       	});
	        //回车事件
			 document.onkeydown=function(event){
				 var evt=event?event:(window.event?window.event:null);//兼容IE和FF
					if(evt.keyCode==13)
					{
						$('#loginbutton').click();
					}
			  }
	        _form_valid_init();
        }

    };

}();