var articleEditor = null;
$(function() {
	$('#articleTags').tagsInput({
		width : 'auto',
		'minChars' : 2, // 每个标签的小最字符
		'maxChars' : 10,
		'onAddTag' : function() {
			return false;
		},
	});
	$('#atUser').tagsInput({
		width : 'auto',
		'minChars' : 2, // 每个标签的小最字符
		'maxChars' : 10,
		defaultText:"add user",
		autocomplete_url : contextPath + "/user/at/list",
		autocomplete_result : function(data) {
			user.article.put_atUser(data.name, data.id);
			return data.name;
		},
		autocomplete : {
			parse : function(response) {
				return $.map(response.data, function(row) {
					return {
						data : row
					};
				});
			},
			formatItem : function(row, i, max) {
				if (row && row.name) {
					return row.name;
				}
				return false;
			},
			formatMatch : function(row, i, max) {
				if (row && row.name) {
					return row.name;
				}
				return "";
			}
		}
	});
	
	editArticle();
	user.article.init_edit();
});

function editArticle() {
	articleEditor = editormd("article-editormd", {
		width : "95%",
		height : 540,
		path : contextPath+'/assets/js/plugins/editormd/lib/',
		theme : "dark",
		editorTheme : "pastel-on-dark",
		markdown : "",
		codeFold : true,
		saveHTMLToTextarea : true, // 保存 HTML 到 Textarea
		searchReplace : true,
		watch : true, // 打开 实时预览
		emoji : false,
		taskList : true,
		tocm : true, // Using [TOCM]
		tex : true, // 开启科学公式TeX语言支持，默认关闭
		flowChart : true, // 开启流程图支持，默认关闭
		sequenceDiagram : true, // 开启时序/序列图支持，默认关闭,
		toolbarAutoFixed : false,
		imageUpload : true,
		imageFormats : [ "jpg", "jpeg", "gif", "png", "bmp", "webp" ],
		imageUploadURL : contextPath+"/user/images/upload",
		onload : function() {
			if ($.browser.mobile) {
				this.unwatch();
			}
			user.article.initAutoUploadeFiles(this,this.editor.get(0));
		},
		onfullscreen : function() {
			this.editor.css("border-radius", 0).css("z-index", 9999);
		},
		onfullscreenExit : function() {
			this.editor.css({
				zIndex : 999,
				border : "none",
				"border-radius" : "5px"
			});

			this.resize();
		}
	});
}

var gtreetable= null;
$(function() {
	gtreetable= jQuery('#gtreetable').gtreetable({
		  'buttonsDirectContainer':'.page-content-body',
		  'source': function (id) {
		      return {
		        type: 'GET',
		        url: contextPath+'/user/category/nodeChildren',
		        data: { 'id': id },        
		        dataType: 'json',
		        error: function(XMLHttpRequest) {
		          alert(XMLHttpRequest.status+': '+XMLHttpRequest.responseText);
		        }
		      }
		    },
		    'onSave':function (oNode) {
		        return {
		          type: 'POST',
		          url: !oNode.isSaved() ? contextPath+'/user/category/nodeCreate' : contextPath+'/user/category/nodeUpdate?id=' + oNode.getId(),
		          data: {
		            parent: oNode.getParent(),
		            name: oNode.getName(),
		            position: oNode.getInsertPosition(),
		            related: oNode.getRelatedNodeId()
		          },
		          dataType: 'json',
		          error: function(XMLHttpRequest) {
		            alert(XMLHttpRequest.status+': '+XMLHttpRequest.responseText);
		          }
		        };
		      },
		      'onDelete':function (oNode) {
		        return {
		          type: 'POST',
		          url: contextPath+"/user/category/"+'nodeDelete?id=' + oNode.getId(),
		          dataType: 'json',
		          error: function(XMLHttpRequest) {
		            alert(XMLHttpRequest.status+': '+XMLHttpRequest.responseText);
		          }
		        };
		      },
		    'draggable': false,
		    'manyroots':true,
		    'onSelect' : function(that) {
				var parents = that.getParents();
				var names = [];
				names.push(that.name);
				for ( var i in parents) {
					names.push(parents[i].name);
				}
				names.reverse();
				$("#categoryName").text(names.join("->")).attr("categoryId",that.id);
				user.article.loadArticleTitleList(that.id);

			},
		    'language':'zh-CN',
		    'types': {'folder':'fa fa-folder-open','file':'fa fa-file'}
		});
	
});
function shareDialog(that){
    bootbox.prompt({title:"复制链接地址分享给朋友(记得@授权)",value:basePath+"/guest/article/view/"+$(that).parents("li:first").attr("id"),callback:function(result){	
    }});
}