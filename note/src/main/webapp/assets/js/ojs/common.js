function getCookie(e){var t=document.cookie.split(";");for(var n=0;n<t.length;n++){var r=t[n].split("=");if($.trim(r[0])==e)return unescape(r[1])}}function addToFavorite(e,t){typeof e!="undefined"&&(e=window.location),typeof t!="undefined"&&(t=document.title);try{window.external.addFavorite(e,t)}catch(n){try{window.sidebar.addPanel(t,e,"")}catch(n){alert("加入收藏失败，请使用Ctrl+D进行添加")}}}function setHome(e,t){try{e.style.behavior="url(#default#homepage)",e.setHomePage(t)}catch(n){if(window.netscape){try{netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect")}catch(n){alert("此操作被浏览器拒绝！\n请在浏览器地址栏输入“about:config”并回车\n然后将 [signed.applets.codebase_principal_support]的值设置为'true',双击即可。")}var r=Components.classes["@mozilla.org/preferences-service;1"].getService(Components.interfaces.nsIPrefBranch);r.setCharPref("browser.startup.homepage",t)}}}CommonUtils={regNamespace:function(){var e=arguments,t=null,n;t=window;for(var r=0;r<e.length;r+=1){n=e[r].split(".");for(var i=0;i<n.length;i+=1){t[n[i]]=t[n[i]]||{},t=t[n[i]];if(r==1)break}}return t},serializeJson:function(e){if(!e)return"";try{var t="",n=[];for(t in e)n.push(t+"="+e[t])}catch(r){return""}return n.join("&")},getRootWin:function(){var e=window;while(e!=e.parent)e=e.parent;return e},autoTopWinOnload:function(){window!=window.top&&(this.getRootWin().top.location.href=window.location.href)},mathDiv:function(arg1,arg2){var t1=0,t2=0,r1,r2;try{t1=arg1.toString().split(".")[1].length}catch(e){}try{t2=arg2.toString().split(".")[1].length}catch(e){}with(Math)return r1=Number(arg1.toString().replace(".","")),r2=Number(arg2.toString().replace(".","")),r1/r2*pow(10,t2-t1)},mathMul:function(e,t){var n=0,r=e.toString(),i=t.toString();try{n+=r.split(".")[1].length}catch(s){}try{n+=i.split(".")[1].length}catch(s){}return Number(r.replace(".",""))*Number(i.replace(".",""))/Math.pow(10,n)},mathAdd:function(e,t){var n,r,i;try{n=e.toString().split(".")[1].length}catch(s){n=0}try{r=t.toString().split(".")[1].length}catch(s){r=0}return i=Math.pow(10,Math.max(n,r)),(e*i+t*i)/i},mathSub:function(e,t){var n,r,i,s;try{n=e.toString().split(".")[1].length}catch(o){n=0}try{r=t.toString().split(".")[1].length}catch(o){r=0}return i=Math.pow(10,Math.max(n,r)),s=n>=r?n:r,((e*i-t*i)/i).toFixed(s)},replaceAll:function(e,t,n){return e.replace(new RegExp(t,"gm"),n)},ToCbc:function(e){var t=[];console.log(e);for(var n=0;n<e.length;n++)e.charCodeAt(n)>65248&&e.charCodeAt(n)<65375?t.push(String.fromCharCode(e.charCodeAt(n)-65248)):e.charCodeAt(n)==12288?t.push(String.fromCharCode(32)):t.push(String.fromCharCode(e.charCodeAt(n)));return t.join("")},getScrollTop:function(){var e=0;return window.pageYOffset?e=window.pageYOffset:document.documentElement&&document.documentElement.scrollTop?e=document.documentElement.scrollTop:document.body&&(e=document.body.scrollTop),e},getScrollLeft:function(){var e=0;return window.pageXOffset?scrollLeft=window.pageXOffset:document.documentElement&&document.documentElement.scrollLeft?e=document.documentElement.scrollLeft:document.body&&(e=document.body.scrollLeft),e},getClientWidth:function(){var e=0;return document.body.clientWidth&&document.documentElement.clientWidth?e=document.body.clientWidth<document.documentElement.clientWidth?document.body.clientWidth:document.documentElement.clientWidth:e=document.body.clientWidth>document.documentElement.clientWidth?document.body.clientWidth:document.documentElement.clientWidth,e},getClientHeight:function(){var e=0;return document.body.clientHeight&&document.documentElement.clientHeight?e=document.body.clientHeight<document.documentElement.clientHeight?document.body.clientHeight:document.documentElement.clientHeight:e=document.body.clientHeight>document.documentElement.clientHeight?document.body.clientHeight:document.documentElement.clientHeight,e},getScrollHeight:function(){return Math.max(document.body.scrollHeight,document.documentElement.scrollHeight)},removeCssPx:function(e){if(typeof e=="undefined"||!e)return 0;typeof e!="object"&&(e=e.toString());var t=e.toLowerCase().indexOf("px");return t>0?parseInt(e.substring(0,t)):parseInt(e)},copyClip:function(e){if(window.clipboardData)window.clipboardData.setData("Text",e);else if(window.netscape){netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");var t=Components.classes["@mozilla.org/widget/clipboard;1"].createInstance(Components.interfaces.nsIClipboard),n=Components.classes["@mozilla.org/widget/transferable;1"].createInstance(Components.interfaces.nsITransferable);n.addDataFlavor("text/unicode");var r=new Object,i=new Object,r=Components.classes["@mozilla.org/supports-string;1"].createInstance(Components.interfaces.nsISupportsString),s=e;r.data=s,n.setTransferData("text/unicode",r,s.length*2);var o=Components.interfaces.nsIClipboard;t.setData(n,null,o.kGlobalClipboard)}}},String.prototype.format=function(e){var t=this;if(arguments.length>0)if(arguments.length==1&&typeof e=="object"){var n=[];e.constructor!=Array?n=[e]:n=e;var r=[];for(var i in n){var s=t;for(var o in n[i])if(n[i][o]!=undefined){var u=new RegExp("({"+o+"})","g");s=s.replace(u,n[i][o])}r.push(s)}r.length>0&&(t=r.join(""))}else for(var i=0;i<arguments.length;i++)if(arguments[i]!=undefined){var u=new RegExp("({["+i+"]})","g");t=t.replace(u,arguments[i])}return t};