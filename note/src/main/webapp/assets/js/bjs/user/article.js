/**
 *文章管理员操作类
 * @author jayu
 */
CommonUtils.regNamespace("user", "article");
/**
 * 工具包
 */
user.article = (function() {
	var at_user=[];
	var _put_atUser=function(name,id){
		at_user[name]=id;
	}
	var _concat_atUser=function(arr){
		if(arr)at_user.concat(arr);
	}
	var _get_atUser=function(name,id){
		if(typeof at_user[name]!=undefined){
			return at_user[name];
		};
		return null;
	}
	
	
	/**
	 * 添加新笔记
	 */
	var _new_note = function() {
			var article={};
			article.title="无标题笔记";
			article.tags="";
			article.articeAtUserList=[];
			var tagslist = "";		
			var selectedNodes$ = $("#gtreetable").find("tr.node-selected");
			if(!selectedNodes$.length){
				alert("笔记类目未选中!");
				return;
			}
			article.cateId=selectedNodes$.attr("data-id");
			$.callServiceAsJson(contextPath+"/user/article/save", article, {
				"before":function(){
					App.startPageLoading();
				},
				"done" : function(response){
					if(response.code==0){
						var data=response.data;
						var newArticle$ = $('<li class="title" id="'+data.id+'">'+
						'<a class="btn readAjax" data-id="'+data.id+'"  href="javascript:;">'+
						'<span class="left">'+data.title+'</span>'+
						'<span  class="right">'+DateUtil.getSDByLong(data.writeTime,"yyyy-MM-dd")+'</span>'+
						'</a><b> </b><span class="btng">'+
						'<i class="fa fa-share-square-o" title="分享" onclick="shareDialog(this)"></i><i class="fa fa-trash" title="删除" onclick="user.article.delArtilce(this)"></i></span></li>');
						$("#page_article_title_list").prepend(newArticle$);
						initTitleEvent(newArticle$);
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
	 * 编辑文章
	 */
	var _init_edit = function() {
		// submit提交
		$('#articleAddForm').bind('formIsValid', function(event, form) {

			var article={};
			article.id=$("#articleId").val();
			article.content=encodeURIComponent(articleEditor.getMarkdown());
			article.title=$("#articleTitle").val();
			article.tags=$("#articleTags").val();
			article.articeAtUserList=[];
			var tagslist = $("#atUser").val().split(",");
			for(var i in tagslist){
				var id=_get_atUser(tagslist[i]);
				if(id){
					article.articeAtUserList.push({"bizId":id});
				}
			}
			$.callServiceAsJson(contextPath+"/user/article/update", article, {
				"before":function(){
					//$("#articleSubmit").attr("disabled","disabled");
					App.startPageLoading();
				},
				"done" : function(response){
					if(response.code==0){
						alert("编辑成功!");
						$("#page_article_title_list").find("#"+article.id).find("a.readAjax > span.left").text(article.title);
					}else{
						alert(response.errorList || response.data);
					}
				},
				"always":function(){
					//$("#articleSubmit").attr("disabled","false");
					App.stopPageLoading();
				}
			});
			
		}).ketchup({bindElement:"articleSubmit","targetPosition":"centerdown"});
	};

	
	/**
	 * 删除文章
	 */
	var _del = function(articleId,func) {
			if(!confirm("确认删除笔记！")){
				return;
			}
			$.callServiceAsJson(contextPath+"/user/article/del/"+articleId, {
				"before":function(){
					//$("#articleSubmit").attr("disabled","disabled");
					App.startPageLoading();
				},
				"done" : function(response){
					if(response.code==0){
						if($.isFunction(func)){
							func(response.data);
						}
					}else{
						alert(response.errorList || response.data);
					}
				},
				"always":function(){
					//$("#articleSubmit").attr("disabled","false");
					App.stopPageLoading();
				}
			});
	};
	
	var _delArtilce = function(that) {
		_del($(that).parents("li:first").attr("id"),function(){
			$(that).parents("li:first").remove();
		});
	};
	
	var _loadArticleTitleList=function(categoryId){
		 $.callServiceAsHtmlGet(contextPath+"/user/artpage/title/cate/"+categoryId, {
				"before":function(){
					App.startPageLoading();
				},
				"done" : function(response){
					if(response.code==0){
						var data=response.data;
						var patl$=$("#page_article_title_list");
						 patl$.html(data);
						initTitleEvent(patl$);
					}
				},
				"always":function(){
					App.stopPageLoading();
				}
			});
	}
	var initTitleEvent=function(ele$){
		$('.readAjax',ele$).on('click', function(){
				 var that = this;
				  $.callServiceAsJsonGet(contextPath+"/user/article/loadEdit/"+$(that).attr("data-id") , {
						"before":function(){
							App.startPageLoading();
						},
						"done" : function(response){
							if(response.code==0){
								var data=response.data;
							    $(that).closest(".title").addClass("active").siblings().removeClass("active");
								articleEditor.setMarkdown(data.content);
								$("#articleTitle").val(data.title);
								$("#articleTags").importTags(data.tags);
								$("#articleId").val(data.id);
								var atUserTags=[];
								if(data.articeAtUserList){
									for(var i in data.articeAtUserList){
										_put_atUser(data.articeAtUserList[i].account,data.articeAtUserList[i].bizId);
										atUserTags.push(data.articeAtUserList[i].account);
									}
								}
								$("#atUser").importTags(atUserTags.join(","));
							}else{
								
							}
						},
						"always":function(){
							App.stopPageLoading();
						}
					});
		        });
	}
	var _refreshArticle = function(){
		var selectedNodes$ = $("#gtreetable").find("tr.node-selected");
		if(!selectedNodes$.length){
			alert("类目未选中!");
			return;
		}
		var cateId=selectedNodes$.attr("data-id");
		_loadArticleTitleList(cateId);
	}
	
	//拖动上传图片
	var autoUploadeFiles=function(that,files){
		if(!files.length){
			return;
		}
		if (!files[0].type.match(/image*/)) {
		    return;  
		}
		var fd=new FormData();
		fd.append('editormd-image-file',files[0]);
		$.ajax({
		    url:contextPath+"/user/images/upload",
		    type:"POST",
		    data:fd,
		    /**   
             * 必须false才会避开jQuery对 formdata 的默认处理   
             * XMLHttpRequest会对 formdata 进行正确的处理   
             */  
            processData : false,  
            /**   
             *必须false才会自动加上正确的Content-Type   
             */  
            contentType : false,
		    success:function(data){
		    	if(data.success ==1){
		            if (data.url === ""){
                        return false;
                    }
		            var cm          = that.cm;
		            var editor      = that.editor;
		            var cursor      = cm.getCursor();
                    cm.replaceSelection("![](" + data.url  + ")");
		    	}else{
		    		alert(data.message);
		    	}
		    }
		});
	}
	//拖动上传图片
	var _initAutoUploadeFiles=function(that,ele){
		try{
			ele.addEventListener("dragenter", function(e){  
			    e.stopPropagation();  
			    e.preventDefault();  
			}, false);  
			ele.addEventListener("dragover", function(e){  
			    e.stopPropagation();  
			    e.preventDefault();  
			}, false);  
			ele.addEventListener("drop", function(e){  
				    e.stopPropagation();  
				    e.preventDefault();				       
				    autoUploadeFiles(that,e.dataTransfer.files);
				}, false);
		}catch(e){}
	}
	return {
		newNote : _new_note,
		init_edit:_init_edit,
		del:_del,
		delArtilce:_delArtilce,
		initAutoUploadeFiles:_initAutoUploadeFiles,
		loadArticleTitleList:_loadArticleTitleList,
		refreshArticle:_refreshArticle,
		put_atUser:_put_atUser,
		get_atUser:_get_atUser,
		concat_atUser:_concat_atUser
	};
})();
