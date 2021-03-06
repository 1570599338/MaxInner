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

<script type="text/javascript" src="${ctxPath}/jspage/tour/tour.js"></script>
<script>
	var editor;
	var editorImg;
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
		editorImg = K.create('textarea[name="messageImg"]', {
			resizeType : 1,
			allowPreviewEmoticons : true,
			allowImageUpload : false,
			items : [
				'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
				'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
				'insertunorderedlist', '|', 'emoticons']
		});
	});
</script>
<title>活动展示后台</title>
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
				<th field="content" width="80" align="right">摘要</th>
				<th field="noticeTime" width="50" align="right">添加时间</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding: 10px 5px 5px 10px; height: auto;">
		<div>&nbsp;&nbsp;&nbsp;
			活&nbsp;动&nbsp;标&nbsp;题：<input class="input_text" type="text" style="width: 150px" id="log_title" name="log_title" />&nbsp;&nbsp;&nbsp; 
			&nbsp;&nbsp;&nbsp;
			<!-- 内&nbsp;&nbsp;&nbsp; 容：<input class="input_text" type="text" style="width: 150px" id="user_name" name="user_name" />&nbsp;&nbsp;&nbsp; -->
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="getdata()">&nbsp;&nbsp;搜&nbsp;&nbsp;索&nbsp;&nbsp;</a>
		</div>

		<div style="margin-bottom: 5px;margin-top: 5px;">
			&nbsp;&nbsp;&nbsp;
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="false" onclick="addAdDiv()">添加摘要</a>&nbsp;&nbsp; 
			&nbsp;&nbsp;&nbsp;
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="false" onclick="editNoticeDiv()">编辑摘要</a>&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="false" onclick="delNoticeDiv('${ctxPath }')">删除摘要</a>&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="false" onclick="upLoadPhoto('${ctxPath }')">上传活动照片</a>&nbsp;&nbsp;
	 		&nbsp;&nbsp;&nbsp;
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="false" onclick="seePhoto('${ctxPath }')">查看图片</a>&nbsp;&nbsp; 
		</div>
	</div>
	<!--数据显示 --END -->
	<!--添加摘要-->
	<div id="addDialog" class="easyui-dialog" title="增加摘要" data-options="closed: true,modal:true" style="width: 760px; height: 600px; padding-left: 20px; padding-top: 20px; text-align: center">
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
					<td style="text-align: right" nowrap="nowrap">摘要：</td>
					<td><textarea name="message" id="message"  style="width:630px;height:300px;visibility:hidden;"></textarea></td>
				
				</tr>
				 <tr>
					<td colspan="2">&nbsp;</td>
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
	
	
	<!--上传图片-->
	<div id="addimg" class="easyui-dialog" title="增加摘要" data-options="closed: true,modal:true" style="width: 760px; height: 600px; padding-left: 20px; padding-top: 20px; text-align: center">
	<form name="fileInfo" method="post"  enctype="multipart/form-data" action="<%=ctxPath%>/tour/toupreport">
			<input type="hidden" name="tourId" id="tourId" />
			<input type="hidden" name="Img_message" id="Img_message" />
			<table border="0"  style="text-align: left;">
				<tr>
					<td style="text-align: right" nowrap="nowrap">上传图片：</td>
					<td align='center'>
						<input type="file" id="file_path" name="file_path" value="abc" size="100" /> 
						<input type="hidden" id="filePath" name="filePath" /> 
					</td>
				</tr>
				<tr>
					<td colspan="2">&nbsp;</td>
				</tr> 
				<tr>
					<td colspan="2">&nbsp;</td>
				</tr> 
				<tr>
					<td style="text-align: right" nowrap="nowrap">描述：</td>
					<td><textarea name="messageImg" id="messageImg"  style="width:630px;height:300px;visibility:hidden;"></textarea></td>
				
				</tr>
				 <tr>
					<td colspan="2">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="2">&nbsp;</td>
				</tr> 
				<tr>
					<td colspan="2" style="padding-left:230px;">
						<a href="#" class="easyui-linkbutton" plain="false" onclick="onSubmitImg();">确&nbsp;&nbsp;定&nbsp;&nbsp;</a>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="#" class="easyui-linkbutton" plain="false" onclick="cancleDialogImg();">&nbsp;&nbsp;取&nbsp;&nbsp;消&nbsp;&nbsp;</a>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
				</tr>
			</table>
		</form>
	</div>
	
	<!--查看图片-->
	<div id="seeImg" class="easyui-dialog" title="增加摘要" data-options="closed: true,modal:true" style="width: 700px; height: 600px; padding-left: 20px; padding-top: 20px; text-align: center">
		<table>
			<tbody id="tbody" name="tbody"></tbody>
		</table>
	</div>

</body>



	<!--编辑照片和文件-->
	<div id="editimg" class="easyui-dialog" title="增加摘要" data-options="closed: true,modal:true" style="width: 760px; height: 600px; padding-left: 20px; padding-top: 20px; text-align: center">
	<form name="fileInfo" method="post"  enctype="multipart/form-data" action="<%=ctxPath%>/tour/toupreport">
			<input type="hidden" name="tourId" id="tourId" />
			<input type="hidden" name="Img_message" id="Img_message" />
			<table border="0"  style="text-align: left;">
				<tr id="uploadImgBlock" >
					<td style="text-align: right" nowrap="nowrap">上传图片：</td>
					<td align='center'>
						<input type="file" id="file_path" name="file_path" value="abc" size="100" /> 
						<input type="hidden" id="filePath" name="filePath" /> 
					</td>
				</tr>
				<tr>
					<td colspan="2"><input type='submit' name='submit' id='editHiden' value='删除' class='subimt' /></td>
				</tr> 
				<tr>
					<td colspan="2">&nbsp;</td>
				</tr> 
				<tr>
					<td style="text-align: right" nowrap="nowrap">描述：</td>
					<td><textarea name="messageImg" id="messageImg"  style="width:630px;height:300px;visibility:hidden;"></textarea></td>
				
				</tr>
				 <tr>
					<td colspan="2">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="2">&nbsp;</td>
				</tr> 
				<tr>
					<td colspan="2" style="padding-left:230px;">
						<a href="#" class="easyui-linkbutton" plain="false" onclick="onSubmitImg();">确&nbsp;&nbsp;定&nbsp;&nbsp;</a>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="#" class="easyui-linkbutton" plain="false" onclick="cancleDialogImg();">&nbsp;&nbsp;取&nbsp;&nbsp;消&nbsp;&nbsp;</a>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
				</tr>
			</table>
		</form>
	</div>


<script type="text/javascript" src="${ctxPath}/jspage/tour/tour.js"></script>

</html>