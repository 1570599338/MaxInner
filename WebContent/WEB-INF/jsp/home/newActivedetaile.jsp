<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%-- <%@include file="/WEB-INF/jsp/inc/front.jsp"%> --%>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<html>
<head>
<title>活动展示</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@include file="/WEB-INF/jsp/inc/inc.jsp"%>
<link href="${ctxPath }/js/sunmmernote/bootstrap.css" rel="stylesheet" />
<script src="${ctxPath }/js/sunmmernote/jquery2.1.4.js"></script> 
<script src="${ctxPath }/js/sunmmernote/bootstrap.js"></script>  
<link href="${ctxPath }/js/sunmmernote/summernote.css" rel="stylesheet"/>
<script src="${ctxPath }/js/sunmmernote/summernote.js"></script>
<script src="${ctxPath }/js/sunmmernote/lang/summernote-zh-CN.js"></script>
<script src="${ctxPath }/jspage/active/addActivity.js"></script> 
	<link rel="stylesheet" href="${ctxPath}/css/front/css/layout.css" type="text/css" />
<link rel="stylesheet" href="${ctxPath}/css/front/css/detail-activity.css" type="text/css"/>
<link rel="stylesheet" href="${ctxPath}/css/front/css/detail-p.css" type="text/css"/>
</head>
<body id="top">
<div class="wrapper col1">
  <div id="header" class="clear">
    <div class="fl_left">
      <h1><a href="http://www.maxinsight.cn"><img src="${ctxPath}/img/front/images/logo.png"/></a></h1>
    </div>
    <div class="fl_right">
      
      <ul id="topnav" class="clear">
     <%--  <li><a href="${ctxPath}/manage/seeManage">经营管理</a></li>
        <li class="active last"><a href="${ctxPath}/meet/toHomepage">会议室</a></li>
         --%><li><a href="#">快速查找</a>
          <ul>
            <li><a href="${ctxPath}/home/toHome#hpage_info">最新公告</a></li>
            <li><a href="${ctxPath}/home/toHome#rules">规章制度</a></li>
            <li><a href="${ctxPath}/home/toHome#download">下载专区</a></li>            
            <li><a href="${ctxPath}/home/toHome#foucs">关注我们</a></li>
          </ul>
        </li>
        <li>
        	<a href="#">内部链接</a>
             <ul>
             	<li><a href="http://192.168.1.237:8088/logon.aspx" target="_blank">财务平台</a></li>
                <li><a href="http://maxinsight.21tb.com" target="_blank">培训平台</a></li>
                <li><a href="http://192.168.1.246/?#" target="_blank">渠道管理平台</a></li> 
             </ul>       	
        </li>   
        <li><a href="${ctxPath}/manage/seeManage">图书</a></li> 
         <li ><a href="${ctxPath}/meet/toHomepage">会议室</a></li>    
        <li><a href="${ctxPath}/connnect/toConnectPage">通讯录</a></li>
        <li class="active last"><a href="${ctxPath}/home/toHome">首页</a></li>
      </ul>
    </div>
  </div>
</div>
<!-- ##############################################header结束######################################################### -->
<div class="wrapper col2">	
	<div id="detail">    	  
    	 <div class="detail_content clear">
    	 
         <p style="padding:10px 0 7px 10px;border-bottom:1px dashed #ccc;"><a href="${ctxPath}/home/toHome">首页</a>&nbsp;&gt;&nbsp;<a href="${ctxPath}/home/toHome">活动展示</a>&nbsp;&gt;&nbsp;</p>
            <div class="ctxx">
            	<div class="title">${article.title}</div>
                <div class="time">${article.time}</div>
                <div id="summernote"><p>${article.content}</p></div>
				  <script>
				    $(document).ready(function() {
				        $('#summernote').summernote();
				       // $('#summernote').summernote('code', ${article.content});
				        $('#summernote').summernote('destroy');
				    });
				  </script>
            </div>
             <div class="fy">
            	<!-- <div class="fy_bottom"><span>上一篇：</span><a href="#">未定</a></div>
                <div class="fy_bottom"><span>下一篇：</span><a href="#">IT制度</a></div> -->
            </div> 
      	</div>
      <div class="classify">
            <div class="cf_title">
                <h3>相关分类</h3>
            </div>
            <div class="cf_detail">
                <ul class="xl xl2 cl">
                    <li><a href="${ctxPath}/home/noticePage">最新公告</a></li>
                    <li><a href="${ctxPath}/home/toHome#download">工具下载</a></li>
                    <li><a href="${ctxPath}/home/toHome">活动展示</a></li>
                    <!-- <li><a href="templet/list.html">模板下载</a></li> -->
                    <li><a href="${ctxPath}/connnect/toConnectPage">通讯录</a></li>
                    <li><a href="${ctxPath}/home/toHome#rules">规章制度</a></li>
                </ul>
            </div>
      </div>
    </div>    
</div>
<!-- ###########################################################链接结束############################################ -->
<div class="wrapper">
  <div id="copyright" class="clear">
    <p class="fl_left">Copyright &copy; 2016 - All Rights Reserved - <a href="#">MaxInsight</a></p>
   
  </div>
</div>
<script type="text/javascript" >
//缩放图片到合适大小
//ResizeImages();
</script>

</body>
</html>