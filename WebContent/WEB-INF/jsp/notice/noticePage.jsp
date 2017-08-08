<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/jsp/inc/inc.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<style>
	/* form {
		margin: 0;
	} */
	textarea {
		display: block;
	}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script type="text/javascript" src="${ctxPath}/jspage/notice/notice.js"></script>
<script>
	var editor;
	KindEditor.ready(function(K) {
		editor = K.create('textarea[name="message"]', {
			resizeType : 1,
			allowPreviewEmoticons : true,
			allowImageUpload : false,
			items : [
				'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
				'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
				'insertunorderedlist', '|', 'emoticons', 'image', 'link']
		});
	});
</script>
<title>用户管理</title>
</head>
<body>
	<form action="" method="post" id="dataForm" name="dataForm">
		<input type="hidden" value="${ctxPath }" name="ctxPath" id="ctxPath" />
		<input type="hidden" name="pk_id" id="pk_id" />
	</form>
	<!--数据显示 --Start -->
	<table id="dg" style="width: 700px; height: 300px" nowrap="true" fitColumns="true" data-options="
				rownumbers:true,
				singleSelect:true,
				autoRowHeight:false,
				resizable:true,
				pagination:true,
				fit:true,
				toolbar:'#tb',
				pageSize:20">
		<thead>
			<tr>
				<th field="title" width="50" data-options="sortable:true">标题</th>
				<th field="title_img" width="80" data-options="sortable:true">图像</th>
				<th field="content" width="80" align="right">内容</th>
				<th field="noticeTime" width="50" align="right">添加时间</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding: 10px 5px 5px 10px; height: auto;">
		<div>&nbsp;&nbsp;&nbsp;
			标 &nbsp;&nbsp;&nbsp; 题：<input class="input_text" type="text" style="width: 150px" id="log_title" name="log_title" />&nbsp;&nbsp;&nbsp; 
			&nbsp;&nbsp;&nbsp;
			<!-- 内&nbsp;&nbsp;&nbsp; 容：<input class="input_text" type="text" style="width: 150px" id="user_name" name="user_name" />&nbsp;&nbsp;&nbsp; -->
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="getdata()">&nbsp;&nbsp;搜&nbsp;&nbsp;索&nbsp;&nbsp;</a>
		</div>

		<div style="margin-bottom: 5px;margin-top: 5px;">
			&nbsp;&nbsp;&nbsp;
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="false" onclick="addAdDiv()">添加公告</a>&nbsp;&nbsp; 
			&nbsp;&nbsp;&nbsp;
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="false" onclick="editNoticeDiv()">编辑公告</a>&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="false" onclick="delNoticeDiv('${ctxPath }')">删除公告</a>&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="false" onclick="addFlageNew('${ctxPath }')">设置new标识</a>&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="false" onclick="delFlageNew('${ctxPath }')">删除new标识</a>&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;
		</div>
	</div>
	<!--数据显示 --END -->
	<!--添加用户-->
	<div id="addDialog" class="easyui-dialog" title="增加公告" data-options="closed: true,modal:true" style="width: 800px; height: 550px; padding-left: 20px; padding-top: 20px; text-align: center">
		<form id="addForm" name="addForm" action="" method="post" target="_self">
			<input type="hidden" name="pk_id" id="id" />
			<input type="hidden" name="add_message" id="add_message" />
			<table border="0"  style="text-align: left;">
				<tr>
					<td style="text-align: right" nowrap="nowrap">标题：</td>
					<td align='center'>
						<input class="easyui-validatebox" type="text" style="width: 400px; " id="add_title" name="add_title" data-options="required:true" />
					</td>
				</tr>
				<tr>
					<td colspan="2">&nbsp;</td>
				</tr> 
				<tr>
					<td colspan="2">&nbsp;</td>
				</tr> 
				<tr>
					<td style="text-align: right" nowrap="nowrap">内容：</td>
					<td><textarea name="message" id="message"  style="width:600px;height:280px;visibility:hidden;"></textarea></td>
				
				</tr>
				 <tr>
					<td colspan="2">&nbsp;</td>
				</tr>
				<tr style="">
					<td >上传附件：</td>
					<td >
						<input type="file" id="file_path" name="file_path" value="abc" size="75" /> 
						 <input type="hidden" id="filePath" name="filePath" />
					</td>					
					
				</tr> 
				
				<tr>
					<td colspan="2">&nbsp;</td>
				</tr>
				
				<tr>
					<td colspan="2" style="padding-left:230px;">
						<a href="#" class="easyui-linkbutton" plain="false" onclick="submitDialog('${ctxPath}');">确&nbsp;&nbsp;定&nbsp;&nbsp;</a>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="#" class="easyui-linkbutton" plain="false" onclick="cancleDialog();">&nbsp;&nbsp;取&nbsp;&nbsp;消&nbsp;&nbsp;</a>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
				</tr>
			</table>
		</form>
	</div>
	

	
	
	

</body>
<script type="text/javascript" src="${ctxPath}/jspage/notice/notice.js"></script>

</html>