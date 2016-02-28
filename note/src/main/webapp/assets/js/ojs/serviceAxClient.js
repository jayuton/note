(function($){function uniqueId(){return(new Date).getTime()}function onPjaxPopstate(e){var t=window.history.state;t&&typeof t.id!="undefined"&&t.id&&$.isFunction(_onPjaxPopstate)&&_onPjaxPopstate(t)}var default_options={dataType:"json",type:"POST",cache:!1,contentType:"application/json;charset=UTF-8",timeout:1e4},defalut_param_options={before:null,done:null,fail:null,always:null,trim:!0,title:"",pjax:!1,timeout:13e4};try{var token=getToken()}catch(e){}token||(token="");var headers={"ec-token":token,pjax:window.history&&window.history.pushState&&window.history.replaceState&&!navigator.userAgent.match(/((iPod|iPhone|iPad).+\bOS\s+[1-4]\D|WebApps\/.+CFNetwork)/)};headers.pjax&&($(window).on("popstate.pjax",onPjaxPopstate),window.history.pushState(null,"",window.location.href));var _callService=function(servName,params,datatype,param_options){if(!servName){alert("请求路径必须填写！");return}var paramOptions=$.extend({},defalut_param_options,param_options||{}),asyncs=!0;$.isFunction(paramOptions.done)||(asyncs=!1);var isForm=!1,isJsonObjet=!1,tempparams=params,trimLeft=/^\s+/,trimRight=/\s+$/,trimCLeft=/(^　*)/g,trimCRight=/(　*$)/g;try{params&&typeof params=="object"&&(paramOptions.trim?params=JSON.stringify(params,function(e,t){return typeof t=="string"&&(t=t.replace(trimLeft,"").replace(trimRight,"").replace(trimCLeft,"").replace(trimCRight,"")),t}):params=JSON.stringify(params)),params&&/^\{(.+:.+,*){1,}\}$/.test(params)&&(isJsonObjet=!0)}catch(e){isJsonObjet=!1}params&&(paramOptions.type==="GET"?isForm=!0:/^(.+=.+&?){1,}$/.test(params)&&!isJsonObjet&&(isForm=!0)),isForm&&isJsonObjet&&tempparams&&(paramOptions.trim?params=JSON.parse(JSON.stringify(tempparams,function(e,t){return typeof t=="string"&&(t=t.replace(trimLeft,"").replace(trimRight,"").replace(trimCLeft,"").replace(trimCRight,"")),t})):params=tempparams,isForm=!1);var response={code:"",errorsList:"",data:"",isjson:!0,hasAlert:!1},reqparm={};params?reqparm={url:servName,data:params,dataType:datatype,type:paramOptions.type,timeout:paramOptions.timeout,async:asyncs}:reqparm={url:servName,dataType:datatype,type:paramOptions.type,timeout:paramOptions.timeout,async:asyncs},isForm&&(reqparm.contentType="application/x-www-form-urlencoded;charset=UTF-8");var req=$.extend({},default_options,reqparm,{beforeSend:function(e){$.each(headers,function(t,n){e.setRequestHeader(t,n)}),$.isFunction(paramOptions.before)&&paramOptions.before.apply(this,[e])}}),pjaxState={id:uniqueId(),url:reqparm.url,title:req.title,params:reqparm};headers.pjax&&reqparm.type=="GET"&&reqparm.dataType=="html"&&paramOptions.pjax&&window.history.pushState(pjaxState,pjaxState.title,reqparm.url),$.ajax(req).done(function(data,status,xhr){if(!data||typeof data.data=="undefined"&&typeof data.code=="undefined"){try{if(typeof data=="string"&&/^\{(.+:.+,*){1,}\}$/.test(data)){var tempdata=eval("("+data+")");if(tempdata&&typeof tempdata.data!="undefined"&&typeof tempdata.code!="undefined")if(tempdata.code==1101||tempdata.code==1100){typeof sso!="undefined"&&typeof sso.user!="undefined"&&typeof sso.user.popupLogin!="undefined"?sso.user.popupLogin():(alert("用户未登录或会话过期，请重新登录！"),response.hasAlert=!0,CommonUtils.getRootWin().location.href=tempdata.data);return}}}catch(e){}response.code=xhr.getResponseHeader("respCode"),response.errorsList=decodeURIComponent(xhr.getResponseHeader("respMsg"));if(typeof response.code=="undefined"||!/^[-]?\d+$/.test(response.code))response.code=0;if(response.code==1101||response.code==1100){typeof sso!="undefined"&&typeof sso.user!="undefined"&&typeof sso.user.popupLogin!="undefined"?sso.user.popupLogin():(alert("用户未登录或会话过期，请重新登录！"),response.hasAlert=!0,CommonUtils.getRootWin().location.href=response.errorsList);return}if(response.code==1103||response.code==1104){alert("无权访问页面！"),response.hasAlert=!0;return}response.data=data,response.isjson=!1}else{response.code=data.code,response.data=data.data,response.errorsList=data.errorsList,response.isjson=!0;if(!(!response.code||response.code!=1101&&response.code!=1100)){typeof sso!="undefined"&&typeof sso.user!="undefined"&&typeof sso.user.popupLogin!="undefined"?sso.user.popupLogin():(alert("用户未登录或会话过期，请重新登录！"),response.hasAlert=!0,CommonUtils.getRootWin().location.href=data.data);return}if(response.code&&(response.code==1103||response.code==1104)){alert("无权访问页面！"),response.hasAlert=!0;return}}asyncs&&paramOptions.done.apply(this,[response]),headers.pjax&&reqparm.type=="GET"&&reqparm.dataType=="html"&&paramOptions.pjax&&(window.history.replaceState(pjaxState,"",reqparm.url),$.isFunction(paramOptions.pjaxdone)&&paramOptions.pjaxdone.apply(this,[pjaxState]))}).fail(function(e,t){var n=e.getResponseHeader("respCode"),r=decodeURIComponent(e.getResponseHeader("respMsg"));response.code=n,response.data=r;if(!!n&&typeof n!="undefined"){if(n==1101||n==1100){typeof sso!="undefined"&&typeof sso.user!="undefined"&&typeof sso.user.popupLogin!="undefined"?sso.user.popupLogin():(alert("用户未登录或会话过期，请重新登录！"),response.hasAlert=!0,CommonUtils.getRootWin().location.href=r);return}if(n==1103||n==1104){alert("无权访问页面！"),response.hasAlert=!0;return}}if(e.status==0)return;e.status==404?(response.code=1,response.errorsList=[{code:e.status,msg:"请求路径不存在."}]):e.status==500?(response.code=1,response.errorsList=[{code:e.status,msg:e.responseText}]):e.status==403?(response.code=1,response.errorsList=[{code:e.status,msg:"请刷新页面重新操作或退出重登录!"}]):e.status==400?(response.code=1,response.errorsList=[{code:e.status,msg:"请求数据格式错误!"}]):e.status==12029?(response.code=1,response.errorsList=[{code:e.status,msg:"网络问题,请检查网络是否连接正常!"}]):(response.code=1,response.errorsList=[{code:e.status,msg:"网络问题,请稍后重试!"}]),asyncs&&($.isFunction(paramOptions.fail)?paramOptions.fail.apply(this,[response]):(alert("失败:"+response.errorsList[0].msg+"("+response.errorsList[0].code+")"),response.hasAlert=!0))}).always(function(){$.isFunction(paramOptions.always)&&asyncs&&paramOptions.always.apply(this)});if(!asyncs)return window.history.pushState(null,"",req.url),response};$.extend($,{callServiceAsJson:function(e,t,n){return t&&t.done&&!n&&(n=t,t=null),_callService(e,t,"json",n)},callServiceAsJsonGet:function(e,t,n){return t&&t.done&&!n&&(n=t,t=null),_callService(e,t,"json",$.extend(n||{},{type:"GET"}))},callServiceAsXml:function(e,t,n){return t&&t.done&&!n&&(n=t,t=null),_callService(e,t,"xml",n)},callServiceAsHtml:function(e,t,n){return t&&t.done&&!n&&(n=t,t=null),_callService(e,t,"html",n)},callServiceAsHtmlGet:function(e,t,n){return t&&t.done&&!n&&(n=t,t=null),_callService(e,t,"html",$.extend(n||{},{type:"GET"}))},callServiceAsText:function(e,t,n){return t&&t.done&&!n&&(n=t,t=null),_callService(e,t,"text",n)},callServiceAsTextGet:function(e,t,n){return t&&t.done&&!n&&(n=t,t=null),_callService(e,t,"text",$.extend(n||{},{type:"GET"}))}})})(jQuery,this);