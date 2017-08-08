<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" %> 
<meta http-equiv="X-UA-Compatible" content="IE=10;IE=9;IE=8;IE=7;" />
<%@include file="/WEB-INF/jsp/inc/inc.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>卓思数据咨询股份有限公司</title>

<style type="text/css">
body {
	font: 12px/20px "微软雅黑", "宋体", Arial, sans-serif, Verdana, Tahoma;
	padding: 0;
	margin: 0;
}
.layout-split-proxy-h{
	position:absolute;
	width:2px;
	background:#888;
	font-size:1px;
	cursor:e-resize;
	display:none;
	z-index:5;
}

.layout-split-north{
	border-bottom:5px solid #efefef;
}
.layout-split-south{
	border-top:5px solid #efefef;
}
.layout-split-east{
	border-left:0px solid #efefef;
}
.layout-split-west{
	border-right:0px solid #efefef;
}
a:link {
 text-decoration: none;
}
a:visited {
 text-decoration: none;
}
a:hover {
 text-decoration: underline;
}
a:active {
 text-decoration: none;
}
.cs-north {
	height:98px;
}
.cs-north-bg {
	width: 100%;
	height: 100%;
	background: url(${ctxPath}/js/easyui/themes/metro/images/header.png) repeat-x;
}
.cs-north-logo {
	float:left;
	width: 30%;
	height: 40px;
	margin: 15px 0px 0px 5px;
	white-space:nowrap;
	display: inline-block;
	color:#ffffff;font-size:22px;font-weight:bold;text-decoration:none
}
.cs-west {
	width:200px;padding:0px;
}
.cs-navi-tab {
	padding: 5px;
}
.cs-tab-menu {
	width:120px;
}
.cs-home-remark {
	padding: 10px;
}
.wrapper {
    float: right;
    height: 30px;
    margin-left: 10px;
}
.ui-skin-nav {
    float: right;
	padding: 0;
	margin-right: 10px;
	list-style: none outside none;
	height: 30px;
}

.ui-skin-nav .li-skinitem {
    float: left;
    font-size: 12px;
    line-height: 30px;
	margin-left: 10px;
    text-align: center;
}
.ui-skin-nav .li-skinitem span {
	cursor: pointer;
	width:10px;
	height:10px;
	display:inline-block;
}
.ui-skin-nav .li-skinitem span.cs-skin-on{
	border: 1px solid #FFFFFF;
}

.ui-skin-nav .li-skinitem span.gray{background-color:gray;}
.ui-skin-nav .li-skinitem span.default{background-color:red;}
.ui-skin-nav .li-skinitem span.bootstrap{background-color:#D7EBF9;}
.ui-skin-nav .li-skinitem span.black{background-color:black;}
.ui-skin-nav .li-skinitem span.metro{background-color:#FFE57E;}
</style>
<script type="text/javascript">
function addTab(title, url){
	if ($('#tabs').tabs('exists', title)){
		$('#tabs').tabs('select', title);//选中并刷新
		var currTab = $('#tabs').tabs('getSelected');
		var url = $(currTab.panel('options').content).attr('src');
		if(url != undefined && currTab.panel('options').title != 'Home') {
			$('#tabs').tabs('update',{
				tab:currTab,
				options:{
					content:createFrame(url)
				}
			})
		}
	} else {
		var content = createFrame(url);
		$('#tabs').tabs('add',{
			title:title,
			content:content,
			closable:true
		});
	}
	tabClose();
}
function createFrame(url) {
	var s = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
	return s;
}
		
function tabClose() {
	/*双击关闭TAB选项卡*/
	$(".tabs-inner").dblclick(function(){
		var subtitle = $(this).children(".tabs-closable").text();
		$('#tabs').tabs('close',subtitle);
	})
	/*为选项卡绑定右键*/
	$(".tabs-inner").bind('contextmenu',function(e){
		$('#mm').menu('show', {
			left: e.pageX,
			top: e.pageY
		});

		var subtitle =$(this).children(".tabs-closable").text();

		$('#mm').data("currtab",subtitle);
		$('#tabs').tabs('select',subtitle);
		return false;
	});
}		
//绑定右键菜单事件
function tabCloseEven() {
	//刷新
	$('#mm-tabupdate').click(function(){
		var currTab = $('#tabs').tabs('getSelected');
		var url = $(currTab.panel('options').content).attr('src');
		if(url != undefined && currTab.panel('options').title != 'Home') {
			$('#tabs').tabs('update',{
				tab:currTab,
				options:{
					content:createFrame(url)
				}
			})
		}
	})
	//关闭当前
	$('#mm-tabclose').click(function(){
		var currtab_title = $('#mm').data("currtab");
		$('#tabs').tabs('close',currtab_title);
	})
	//全部关闭
	$('#mm-tabcloseall').click(function(){
		$('.tabs-inner span').each(function(i,n){
			var t = $(n).text();
			if(t != 'Home') {
				$('#tabs').tabs('close',t);
			}
		});
	});
	//关闭除当前之外的TAB
	$('#mm-tabcloseother').click(function(){
		var prevall = $('.tabs-selected').prevAll();
		var nextall = $('.tabs-selected').nextAll();		
		if(prevall.length>0){
			prevall.each(function(i,n){
				var t=$('a:eq(0) span',$(n)).text();
				if(t != 'Home') {
					$('#tabs').tabs('close',t);
				}
			});
		}
		if(nextall.length>0) {
			nextall.each(function(i,n){
				var t=$('a:eq(0) span',$(n)).text();
				if(t != 'Home') {
					$('#tabs').tabs('close',t);
				}
			});
		}
		return false;
	});
	//关闭当前右侧的TAB
	$('#mm-tabcloseright').click(function(){
		var nextall = $('.tabs-selected').nextAll();
		if(nextall.length==0){
			//msgShow('系统提示','后边没有啦~~','error');
			alert('后边没有啦~~');
			return false;
		}
		nextall.each(function(i,n){
			var t=$('a:eq(0) span',$(n)).text();
			$('#tabs').tabs('close',t);
		});
		return false;
	});
	//关闭当前左侧的TAB
	$('#mm-tabcloseleft').click(function(){
		var prevall = $('.tabs-selected').prevAll();
		if(prevall.length==0){
			alert('到头了，前边没有啦~~');
			return false;
		}
		prevall.each(function(i,n){
			var t=$('a:eq(0) span',$(n)).text();
			$('#tabs').tabs('close',t);
		});
		return false;
	});

	//退出
	$("#mm-exit").click(function(){
		$('#mm').menu('hide');
	})
}

$(function() {
	tabCloseEven();

	$('.cs-navi-tab').click(function() {
		var $this = $(this);
		var href = $this.attr('src');
		var title = $this.text();
		addTab(title, href);
	});

	var themes = {
		'gray' : 'themes/gray/easyui.css',
		'black' : 'themes/black/easyui.css',
		'bootstrap' : 'themes/bootstrap/easyui.css',
		'default' : 'themes/default/easyui.css',
		'metro' : 'themes/metro/easyui.css'
	};

	var skins = $('.li-skinitem span').click(function() {
		var $this = $(this);
		if($this.hasClass('cs-skin-on')) return;
		skins.removeClass('cs-skin-on');
		$this.addClass('cs-skin-on');
		var skin = $this.attr('rel');
		$('#swicth-style').attr('href', themes[skin]);
		setCookie('cs-skin', skin);
		skin == 'dark-hive' ? $('.cs-north-logo').css('color', '#FFFFFF') : $('.cs-north-logo').css('color', '#000000');
	});

	if(getCookie('cs-skin')) {
		var skin = getCookie('cs-skin');
		$('#swicth-style').attr('href', themes[skin]);
		$this = $('.li-skinitem span[rel='+skin+']');
		$this.addClass('cs-skin-on');
		skin == 'dark-hive' ? $('.cs-north-logo').css('color', '#FFFFFF') : $('.cs-north-logo').css('color', '#000000');
	}
});


function setCookie(name,value) {//两个参数，一个是cookie的名子，一个是值
    var Days = 30; //此 cookie 将被保存 30 天
    var exp = new Date();    //new Date("December 31, 9998");
    exp.setTime(exp.getTime() + Days*24*60*60*1000);
    document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();
}

function getCookie(name) {//取cookies函数        
    var arr = document.cookie.match(new RegExp("(^| )"+name+"=([^;]*)(;|$)"));
     if(arr != null) return unescape(arr[2]); return null;
}


function exit(){
	$.messager.confirm("提示","确定退出当前系统？",function(data){
		if(data){
			location.href='${ctxPath}/user/toLogin';
		}
	});
}
</script>
</head>
<body class="easyui-layout">
	<div region="north" border="true" class="cs-north">
		<div class="cs-north-bg">
			<div class="cs-north-logo">
			<a href="http://www.maxinsight.cn" style=" text-decoration:none; color:#0f1719">
				 <img style="width:164px ;height:50px;position:absolute;padding:5px;left:108px;top:25px; " src="${ctxPath}/img/front/images/logo.png"/>
			</a><!-- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;卓思内部信息系统 -->
		</div>
			<div style="position:absolute;padding:5px;right:10px;top:60px;font-weight:bold;text-decoration:none;">
				<a href="#" class="easyui-linkbutton" data-options="plain:true"><font style="color:white;"><!-- 首页 --></font></a>
				<a href="#" class="easyui-menubutton" data-options="menu:'#mm1',iconCls:'icon-edit'"><font style="color:white;"><!-- 工具 --></font></a>
				<a href="#" class="easyui-menubutton" data-options="menu:'#mm2',iconCls:'icon-help'"><font style="color:white;"><!-- 向导 --></font></a>
				<a href="#" class="easyui-menubutton" data-options="menu:'#mm3'"><font style="color:white;">登录用户信息</font></a>
			</div>
			<div id="mm1" style="width:150px;">
				<div data-options="iconCls:'icon-undo'">Undo</div>
				<div data-options="iconCls:'icon-redo'">Redo</div>
				<div class="menu-sep"></div>
				<div>Cut</div>
				<div>Copy</div>
				<div>Paste</div>
				<div class="menu-sep"></div>
				<div>
					<span>Toolbar</span>
					<div>
						<div>Address</div>
						<div>Link</div>
						<div>Navigation Toolbar</div>
						<div>Bookmark Toolbar</div>
						<div class="menu-sep"></div>
						<div>New Toolbar...</div>
					</div>
				</div>
				<div data-options="iconCls:'icon-remove'">Delete</div>
				<div>Select All</div>
			</div>
			<div id="mm2" style="width:100px;">
				<div>Help</div>
				<div>Update</div>
				<div>About</div>
			</div>
			<div id="mm3" class="menu-content" style="background:#f0f0f0;padding:10px;text-align:left">
				<!-- <img src="http://www.jeasyui.com/images/logo1.png" style="width:150px;height:50px"> -->
				<p style="font-size:14px;color:#444;">
					用户名：admin<br/>
					角&nbsp;&nbsp;&nbsp;&nbsp;色：超级管理员<br/>
					部&nbsp;&nbsp;&nbsp;&nbsp;门：IT总部<br/>
				</p>
				<div onclick="exit()">退出登录</div>
			</div>

		</div>
	</div>
	<div region="west" border="true" split="true" title="菜单导航" class="cs-west" iconCls="icon-nav">
		<div class="easyui-accordion" fit="true" border="false">
				<%-- <div title="用户角色" iconCls="icon-user">
					 <a href="javascript:void(0);" src="${ctxPath}/user/toDatagrid" class="cs-navi-tab" iconCls="icon-user">会员用户</a></p>
					 <a href="javascript:void(0);" src="${ctxPath}/user/toDatagrid" class="cs-navi-tab" iconCls="icon-nav">角色权限</a></p>
					 <a href="javascript:void(0);" src="${ctxPath}/user/toDatagrid" class="cs-navi-tab" iconCls="icon-nav">操作功能</a></p>
				</div> --%>
				<div title="首页" iconCls="icon-project">
				   <a href="javascript:void(0);" src="${ctxPath}/active/toPage" class="cs-navi-tab">活动展示</a></p>
					<a href="javascript:void(0);" src="${ctxPath}/notice/toPage" class="cs-navi-tab">公告发布</a></p>
					 <a href="javascript:void(0);" src="${ctxPath}/tour/toPage" class="cs-navi-tab">活动展示</a></p> 
					<a href="javascript:void(0);" src="${ctxPath}/rule/toPage" class="cs-navi-tab">规章制度</a></p>
				</div>
				<div title="下载专区" iconCls="icon-datasave">
					<a href="javascript:void(0);" src="${ctxPath}/upload/toPage" class="cs-navi-tab">模板类型</a></p>
					<a href="javascript:void(0);" src="${ctxPath}/upload/uploadFile" class="cs-navi-tab">上传文件</a></p>
				</div>
			 	<div title="通讯录" iconCls="icon-questionnaire">
					<a href="javascript:void(0);" src="${ctxPath}/department/toDepartmentPage" class="cs-navi-tab">分公司与部门</a></p>
					<a href="javascript:void(0);" src="${ctxPath}/staff/toStaffPage" class="cs-navi-tab">员工信息</a></p>
				</div>
				
				<div title="会议室预定" iconCls="icon-audit">
					<a href="javascript:void(0);" src="${ctxPath}/meet/topage" class="cs-navi-tab">会议室预定</a></p>
				</div>
				 <div title="经营管理" iconCls="icon-statistic">
					<a href="javascript:void(0);" src="${ctxPath}/manage/topage" class="cs-navi-tab">经营管理信息</a></p>
				</div> 
		</div>
	</div>
	<div id="mainPanle" region="center" border="true" border="false">
		 <div id="tabs" class="easyui-tabs"  fit="true" border="false" >
                <div title="首页" iconCls="icon-home">
				<div class="cs-home-remark">
					<h1>UI Demo</h1> <br/> <br/>
					作者：刘劝 <br/> <br/>
					版本：v1.0 <br/> <br/>
					
					欢迎进入卓思内部信息系统后台。<br/> <br/>
					
					
				</div>
				</div>
        </div>
	</div>
	
	<!--
	<div data-options="region:'east',split:true,collapsed:true,title:'切换项目'" style="width:100px;padding:10px;">项目列表</div>
	-->

	<div region="south" border="false" id="south"><div class="cs-north-bg"><center><font color="white">卓思数据咨询股份有限公司</font></center></div></div>
	
	<div id="mm" class="easyui-menu cs-tab-menu">
		<div id="mm-tabupdate">刷新</div>
		<div class="menu-sep"></div>
		<div id="mm-tabclose">关闭</div>
		<div id="mm-tabcloseother">关闭其他</div>
		<div id="mm-tabcloseall">关闭全部</div>
	</div>
</body>
</html>