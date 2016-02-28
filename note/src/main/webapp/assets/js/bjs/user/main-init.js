$(function() {
	$(".md-content").each(function(){
		editormd.markdownToHTML($(this).attr("id"), {
	              htmlDecode      : "style,script,iframe",  // you can filter tags decode
	              taskList        : true,
	              previewTheme  : "",//dark
	              codeFold : true,
	              tex             : true,  // 默认不解析
	              flowChart       : true,  // 默认不解析
	              sequenceDiagram : true,  // 默认不解析
	       });
	});
});