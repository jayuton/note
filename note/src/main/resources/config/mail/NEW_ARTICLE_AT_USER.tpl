 <!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<title>友记分享</title>
<style>
.inbox{
    font-size: 13px;
}
.inbox h4{
    font-size: 16px;
}
.inbox .inbox-nav {
    margin: 0;
    padding: 0;
    list-style: none;
}
.inbox ul li{
	list-style: none;
}
.inbox ul.inbox-nav {
    margin-bottom: 0;
}
.inbox .inbox-nav {
    margin: 0;
    padding: 0;
    list-style: none;
}
.margin-bottom-10 {
    margin-bottom: 10px !important;
}
.inbox ul.inbox-nav li {
    padding: 0;
}
.inbox .inbox-nav li {
    position: relative;
}
.inbox .inbox-nav li a {
    color: #4d82a3;
    display: block;
    font-size: 15px;
    border-left: none;
    text-align: left !important;
    padding: 8px 14px;
    margin-bottom: 1px;
    background: #f4f9fd;
    text-overflow: ellipsis;
    white-space: nowrap;
    overflow: hidden;
    min-width: 200px;
    max-width:400px;
}

a {
    color: #428bca;
    text-shadow: none !important;
    text-decoration: underline;
}
.inbox ul.inbox-nav li span.left {
    float: left;
    text-overflow: ellipsis;
    width: 70%;
    white-space: nowrap;
    overflow: hidden;
    text-align: left;
    font-size: 15px;
}

.inbox ul.inbox-nav li span {
    color: #828f97;
    font-size: 12px;
    margin-right: 10px;
}
.inbox ul.inbox-nav li span.right {
    float: right;
    text-overflow: ellipsis;
    width: 25%;
    white-space: nowrap;
    overflow: hidden;
    text-align: right;
    font-size: 14px;
    margin-right: 0;
    padding-right: 0;
}
</style>
</head>
<body>
<div class="inbox">
<h4 class="title">朋友最新分享笔记</h4>
 <#if articleList?? && (articleList?size>0) > 
<ul class="inbox-nav">
 <#list articleList as article>
<li class="title">
	<a href="${siteUrl}/guest/article/view/${article.id}">
		<span class="left">【作者:${article.authorName}】${article.title}</span>
		<span  class="right">${article.writeTime?string("YYYY-MM-dd")}</span>
	</a>
</li>
</#list> 
</ul>
</#if>
</div>
</body>
</html>
