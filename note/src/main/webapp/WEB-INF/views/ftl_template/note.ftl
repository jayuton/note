<#ftl strip_whitespace=true>
<#--
 * 门户层通用的模板都定义在此宏里面，供所有模板ftl文件使用。
 * 该模块会自动往所有模块文件里 import 进去。
 * ${contextPath} 为 应用上下文根性目录:如 /xxx
-->
<#global contextPath>${request.contextPath}</#global>
<#global basePath>${Request['basePath']}</#global>
<#-- 往表单插入令牌 -->
<#macro csrf_token><input type="hidden" name="_token" id="_token" value="${Request['_token']}"/></#macro>
<#--
* 令牌JS要放在JS头最顶处.
*-->
<#macro csrf_token_js>
<script type="text/javascript">
	<#-- 设置全局contextPath	-->
	var contextPath = "${request.getContextPath()}";
	var basePath = "${basePath}";
	function getToken(){
		return "${Request['_token']}";
	}
</script>
</#macro>