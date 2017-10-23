<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/inc/inc.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>
	textarea {
		display: block;
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

</script>

	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>图书管理</title>

</head>
<body>
	<form action="" method="post" id="dataForm" name="dataForm" >
		<input type="hidden" value="${ctxPath }" name="ctxPath" id="ctxPath" />
		<input type="hidden" name="pk_id" id="pk_id" />
		<input type="hidden" name="bookTypeID" id="bookTypeID" />
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
			&nbsp;&nbsp;书&nbsp;&nbsp;名：<input class="input_text" type="text" style="width: 150px" id="log_title" name="log_title" />&nbsp;&nbsp;&nbsp; 
			&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;类&nbsp;&nbsp;型：<input type="text" id="bookType" name="bookType" panelHeight="auto" class="easyui-combobox" value='${bookTypex}' />&nbsp;&nbsp;&nbsp; 
			&nbsp;&nbsp;&nbsp;
			<!-- 内&nbsp;&nbsp;&nbsp; 容：<input class="input_text" type="text" style="width: 150px" id="user_name" name="user_name" />&nbsp;&nbsp;&nbsp; -->
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="getdata()">&nbsp;&nbsp;搜&nbsp;&nbsp;索&nbsp;&nbsp;</a>
		</div>

		<div style="margin-bottom: 5px;margin-top: 5px;">
			&nbsp;&nbsp;&nbsp;
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="false" onclick="addAdActivityDiv()">添加图书</a>&nbsp;&nbsp; 
			&nbsp;&nbsp;&nbsp;
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="false" onclick="editActivityDiv()">编辑图书</a>&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="false" onclick="deleteActivity()">删除图书</a>&nbsp;&nbsp;
 			&nbsp;&nbsp;&nbsp;
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="false" onclick="upLoadPhoto('${ctxPath }')">上传图书封面</a>&nbsp;&nbsp;
	 		&nbsp;&nbsp;&nbsp;
	 	<%--	<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="false" onclick="addFlageNew('${ctxPath }')">设置new标识</a>&nbsp;&nbsp;
	 		&nbsp;&nbsp;&nbsp;
	 		<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="false" onclick="delFlageNew('${ctxPath }')">删除new标识</a>&nbsp;&nbsp;
	 		&nbsp;&nbsp;&nbsp; --%>
		</div>
	</div>
	<!--数据显示 --END -->
	
	<!--上传图片-->
	<div id="addimg" class="easyui-dialog" title="上传图片封面" data-options="closed: true,modal:true" style="width: 600px; height: 150px; padding-left: 20px; padding-top: 20px; text-align: center">
	<form name="fileInfo" method="post"  enctype="multipart/form-data" action="<%=ctxPath%>/manage/uploadBooksImg">
			<input type="hidden" name="activitypkId" id="activitypkId" />
			<table border="0"  style="text-align: left;">
				<tr>
					<td style="text-align: right" nowrap="nowrap">上传图片：</td>
					<td align='center'>
						<input type="file" id="file_path" name="file_path" value="abc" size="100" /> 
						<input type="hidden" id="filePath" name="filePath" /> 
					</td>
					<td >&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="#" class="easyui-linkbutton" plain="false" onclick="onSubmitImg();">&nbsp;&nbsp;确&nbsp;&nbsp;定&nbsp;&nbsp;</a>
					&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="#" class="easyui-linkbutton" plain="false" onclick="cancleDialogImg();">&nbsp;&nbsp;取&nbsp;&nbsp;消&nbsp;&nbsp;</a>
						&nbsp;&nbsp;&nbsp;&nbsp;
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

<script type="text/javascript" src="${ctxPath}/jspage/manageInfo/manageInfo.js?new date()"></script>

</html>