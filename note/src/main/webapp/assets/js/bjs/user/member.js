CommonUtils.regNamespace("admin", "member");
admin.member = (function() {

	/**
	 * 删除会员资料
	 */
	var _del = function(userId) {
			$.callServiceAsJson(contextPath+"/user/member/del/"+userId,  {
				"before":function(){
					 App.startPageLoading();
				},
				"done" : function(response){
					if(response.code==0){
						$("#tr_"+userId).remove();
					}else{
						alert(response.errorList || response.data);
					}
				},
				"always":function(){
					 App.stopPageLoading();
				}
			});
	};
	/**
	 * 重置会员资料
	 */
	var _reset = function(userId) {
			$.callServiceAsJson(contextPath+"/user/member/reset/"+userId,  {
				"before":function(){
					 App.startPageLoading();
				},
				"done" : function(response){
					if(response.code==0){
						alert("重置成功!");
					}else{
						alert(response.errorList || response.data);
					}
				},
				"always":function(){
					 App.stopPageLoading();
				}
			});
	};
	
	return {
		del : _del,
		reset:_reset,
	};
})();
$(function(){
	var $member_table=$("#member_table");
	 $member_table.find("a.delete").click(function(e){
		 e.preventDefault();
		 admin.member.del($(this).attr("data-id"));
	});
	 $member_table.find("a.reset").click(function(e){
		 e.preventDefault();
		 admin.member.reset($(this).attr("data-id"));
	});
})