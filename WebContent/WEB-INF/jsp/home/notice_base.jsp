<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%-- <%@include file="/WEB-INF/jsp/inc/front.jsp"%> --%>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<html>
<head>
<title>最新公告</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@include file="/WEB-INF/jsp/inc/inc.jsp"%>
<link rel="stylesheet" href="${ctxPath}/css/front/css/layout.css" type="text/css" />
<link rel="stylesheet" href="${ctxPath}/css/front/css/detail.css" type="text/css"/>
<script type="text/javascript" src="${ctxPath}/jspage/home/notice_base.js"></script>
<style type="text/css">
	#copyright_rule{
	position:relative;
	margin:0 auto 0;
	display:block;
	width:960px;
	padding:10px 0;
	border-top:1px solid #333333;
	}
</style>
</head>

<body id="top">
<div class="wrapper col1">
  <div id="header" class="clear">
    <div class="fl_left">
      <h1><a href="http://www.maxinsight.cn"><img src="${ctxPath}/img/front/images/logo.png"/></a></h1>
      
    </div>
    <div class="fl_right">
      
      <ul id="topnav" class="clear">
      <%-- <li><a href="${ctxPath}/manage/seeManage">经营管理</a></li>
        <li class="active last"><a href="${ctxPath}/meet/toHomepage">会议室</a></li>
        --%> <li><a href="#">快速查找</a>
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
         <li class="active last"><a href="${ctxPath}/meet/toHomepage">会议室</a></li>      
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
            <p><a href="${ctxPath}/home/toHome">首页</a>&nbsp;&gt;&nbsp;<a href="${ctxPath}/home/toHome#hpage_info">最新公告</a>&nbsp;&gt;&nbsp;</p>
            <!--  -->
            <form action="" method="post" id="dataForm" name="dataForm">
				<input type="hidden" value="${ctxPath }" name="ctxPath" id="ctxPath" />
				<input type="hidden" name="fileName" id="fileName" />
				<input type="hidden" name="filetype" id="filetype" value="${filetype}"/>
			</form>
			<div id="tb" style="padding: 10px 5px 5px 10px; height: auto;">
				<div>&nbsp;&nbsp;&nbsp;
					标 &nbsp;&nbsp;&nbsp; 题：<input class="input_text" type="text" style="width: 150px" id="fileNamex" name="fileNamex" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="getdata()">&nbsp;&nbsp;搜&nbsp;&nbsp;索&nbsp;&nbsp;</a><%-- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="false" onclick="editNoticeDiv('${ctxPath}')">查看</a>&nbsp;&nbsp;  --%>
				</div>
			</div>
			<!--数据显示 --Start -->
			<table id="dg" style="width: 700px; height: 10px" nowrap="true" fitColumns="true" data-options="
						rownumbers:true,
						singleSelect:true,
						autoRowHeight:false,
						resizable:true,
						pagination:true,
						fit:true,
						toolbar:'#tb',
						pageSize:10">
				<thead>
				</thead>
			</table>
			
		<!--数据显示 --END -->
            
            <!--  -->
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
    <p class="fl_left">Copyright &copy; 2016 - All Rights Reserved - <a href="http://www.maxinsight.cn/">MaxInsight</a></p>
   
  </div>
</div>


	<!--最新的公告-->
	<!--最新的公告-->
	<div id="noticeDialog" class="easyui-dialog" title="增加公告" data-options="closed: true,modal:true" style="width: 760px; height: 600px; padding-left: 20px; padding-top: 20px; text-align: center">
		<form id="noticeForm" name="noticeForm" action="" method="post" target="_self">
			<input type="hidden" name="pk_id" id="id" />
			<input type="hidden" name="notice_message" id="notice_message" />
			<table border="0"  style="text-align: left;">
				<tr>
					<td align='center' ><span id="notice_title"></span></td>
				</tr>
				<tr>
					<td><textarea name="message" id="message"  style="width:630px;height:30px;visibility:hidden;"></textarea></td>
				</tr>
				<tr>
					<td >&nbsp;</td>
				</tr> 
				<tr>
					<td ><span id="notice_content"></span></td>
				</tr> 
				
				 <tr>
					<td >&nbsp;</td>
				</tr>
				<tr>
					<td >&nbsp;</td>
				</tr> 
				<tr>
					<td style="padding-left:330px;">
						<a href="#" class="easyui-linkbutton" plain="false" onclick="noticeClose();">关&nbsp;&nbsp;闭&nbsp;&nbsp;</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>