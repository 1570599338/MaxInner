<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Strict//EN">
<html>
<head>
	<title>图书</title>
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />
	<%@include file="/WEB-INF/jsp/inc/inc.jsp"%>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="imagetoolbar" content="no" />
	<link rel="stylesheet" href="${ctxPath}/css/front/css/layout.css" type="text/css" />
	<link rel="stylesheet" href="${ctxPath}/css/front/css/book.css" type="text/css" />
</head>
<style type="text/css">
	#copyright_rule{
		position:relative;
		margin:0 auto 0;
		display:block;
		width:960px;
		padding:10px 0;
		border-top:1px solid #333333;
	}
	
tr{
	font-family:"微软雅黑",Georgia, "Times New Roman", Times, serif;
}

	.rTop{
		width:100px; height:25px;
		/* background:#A6A6A7; */
		text-align:center; font-size:small;
		line-height:25px; border:1px solid #999;
		position:fixed; right:0; bottom:0px;
		border-bottom-color:#333; 
		border-right-color:#333; margin:50px;
		cursor:pointer; display:none
	}	
</style>
<script type="text/javascript">
$(function(){
	var ctxPath = $("#ctxPath").val();
	$('#bookType').combobox({ 
		url:ctxPath + '/manage/queryBookTypeList',
	    editable:true, //不可编辑状态
	    cache: false,
	    panelHeight: 'auto',//自动高度适合
	    valueField:'pk_id',  
	    textField:'name'
	});
	$("#bookType").combobox('select', '${bookType}');
	if('${companyid}'==''){
		$("#department").combobox('select', "--请选择--");
	}else{
		$("#department").combobox('select', '${departMentCode}');
	}
	
	$("#bookType").combobox('select', '${bookTypex}');
	/* $("#useName").val('${useName}');
	$("#telphone").val('${telphone}'); */
	
});


<!--拖动滚动条或滚动鼠标轮-->
window.onscroll=function(){
	if(document.body.scrollTop||document.documentElement.scrollTop>0){
	document.getElementById('rTop').style.display="block"
	}else{
		document.getElementById('rTop').style.display="none";
	}
}
/* 点击“回到顶部”按钮 */
function toTop(){
window.scrollTo('0','0');
document.getElementById('rTop').style.display="none"
}
</script>

<body id="top">
<div class="rTop" id="rTop" onClick="toTop()">返回顶部</div>
<input type="hidden" id="ctxPath" name="ctxPath" value="${ctxPath}" />
<div class="wrapper col1">
   <div id="header" class="clear">
    <div class="fl_left">
      <h1><a href="index.html"><img src="${ctxPath}/img/front/images/logo.png"/></a></h1>     
    </div>
    <div class="fl_right">
      
      <ul id="topnav" class="clear"> 
     <%--  <li><a href="${ctxPath}/manage/seeManage">经营管理</a></li> --%>
      
       <li><a href="#">快速查找</a>
          <ul>
            <li><a href="index.html/#hpage_info">最新公告</a></li>
            <li><a href="#rules">规章制度</a></li>
            <li><a href="#download">下载专区</a></li>            
            <li><a href="#foucs">关注我们</a></li>
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
       <li  class="active last"><a href="${ctxPath}/manage/seeManage">图书</a></li>
        <li><a href="${ctxPath}/meet/toHomepage">会议室</a></li>
        <li><a href="${ctxPath}/connnect/toConnectPage">通讯录</a>
        </li>
        <li ><a href="${ctxPath}/home/toHome">首页</a></li>
      </ul>
    </div>
  </div>
</div>

<!-- ####################################################################################################### -->
<form id="upfileForm" action="" method="post" >
	<input type="hidden"  id="bookx" name="bookx"  vaule="${bookx}"/>
	<input type="hidden"  id="bookTypex" name="bookTypex" vaule="${bookTypex}"/>
	<input type="hidden"  id="pk_id" name="pk_id"/>
	
</form>
<div class="wrapper col2">
  <div id="search_form">
    <table>
    	<tr>
    		<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;图&nbsp;&nbsp;&nbsp;书:&nbsp;&nbsp;&nbsp;</td>
    		<td><input class="input_text" type="text" style="width:116px;height:24px" id="book" name="book" value="${bookx }"/></td>
    		<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
    		<td>类型:</td><td> <input type="text" id="bookType" name="bookType" panelHeight="auto" class="easyui-combobox" value='${bookTypex}' /> 
    		<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
    		<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
    		<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
    		<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
    		<td><a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="getdata()">&nbsp;&nbsp;搜&nbsp;&nbsp;索&nbsp;&nbsp;</a></td>
    	</tr>
    </table>
  </div>
</div>  	
<!-- ####################################################################################################### -->


<div class="wrapper col3">
  <div id="container" class="clear">
    <!-- ####################################################################################################### -->
    <div id="gallery" class="clear">
      <ul>
	      <c:forEach items="${list}" var="s" varStatus="ss">
	        <li>
	            <img src="${ctxPath}${s.path}" alt=""  onclick="seeBookinfo(${s.pk_id})"/>
	            <p onclick="seeBookinfo(${s.pk_id})">${s.manageTitle}</p>
	        </li>
	      </c:forEach>
	      <c:if test=""></c:if>
      </ul>
    </div>
    <!-- ####################################################################################################### -->
  </div>
</div>


<!-- ####################################################################################################### -->

<!-- ####################################################################################################### -->
<div class="footer">
  <div id="copyright" class="clear">
    <p class="fl_left">Copyright &copy; 2016 - All Rights Reserved - <a href="http://www.maxinsight.cn/">MaxInsight</a></p>   
  </div>
</div>
<script type="text/javascript" src="${ctxPath}/jspage/book/book.js"></script>
</body>
</html>
