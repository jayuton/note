var Profile = function () {

	/**
	 *  更新数据
	 */
	var _form_update_init = function() {
		// submit提交
		$('#profileForm').bind('formIsValid', function(event, form) {
			var params={"nick":$("#nick",form).val(),"email":$("#email",form).val()
					,"oldPassword":$("#oldPassword",form).val(),"newPassword":$("#newPassword",form).val()};
			if(params.oldPassword== params.newPassword && params.newPassword!=""){
				note.util.showErrors([{"element":'newPassword','message':'新旧密码必须不一致！'}],$("#profileForm"),"centerdown");
				return;
			}
			params=JSON.stringify(params);
			$.callServiceAsJson(contextPath+"/user/profile/update", params, {
				"before":function(){
					$("#profileSubmitButton").attr("disabled","disabled").html('Updating....');
				},
				"done" : function(response){
					if (response.code == 0) {
						 alert("更新成功！");
					//用户入参合法性校验数错,//参数业务校验不通过,//验证码不对通过
					} else if (response.code ==1001 || response.code == 1004 || response.code == 1005){
						note.util.showErrors(response.errorsList,$("#profileForm"));
					} else {
						if(response.errorsList&& response.errorsList.length>0 ){
							alert(response.errorsList[0].message);
						} else {
							alert(response.data);
						}
					}
				},
				"always":function(){
					$("#profileSubmitButton").attr("disabled",false).html('Submit');
				}
			});
		}).ketchup({bindElement:"profileSubmitButton","targetPosition":"centerdown"});
		 
	};
    return {
        //main function to initiate the module
        init: function () {    
		   _form_update_init();
        }

    };

}();