<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/jsp/inc/inc.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctxPath}/jspage/holiday/holiday.js"></script>
<title>年假管理</title>
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
			<!-- <tr>
				<th field="type" width="50" data-options="sortable:true">类型</th>
				<th field="fileName" width="80" data-options="sortable:true">文件名</th>
				<th field="ruleTime" width="80" align="right">时间</th>
				<th field="createBy" width="50" align="right">上传者</th>
			</tr> -->
		</thead>
	</table>
	<div id="tb" style="padding: 10px 5px 5px 10px; height: auto;">
		<div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			姓名 ：<input class="input_text" type="text" style="width: 150px" id="userName" name="userName" />&nbsp;&nbsp;&nbsp; 
			&nbsp;&nbsp;&nbsp;
			<!-- 内&nbsp;&nbsp;&nbsp; 容：<input class="input_text" type="text" style="width: 150px" id="user_name" name="user_name" />&nbsp;&nbsp;&nbsp; -->
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="getdata()">&nbsp;&nbsp;搜&nbsp;&nbsp;索&nbsp;&nbsp;</a>
		</div>

		<div style="margin-bottom: 5px;margin-top: 5px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="false" onclick="updateAdDiv()">导入年假</a>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;
<%-- 			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="false" onclick="delNoticeDiv('${ctxPath }')">删除规章制度</a>&nbsp;&nbsp;
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="false" onclick="addFlageNew('${ctxPath }')">设置new标识</a>&nbsp;&nbsp; 
			&nbsp;&nbsp;&nbsp;
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="false" onclick="delFlageNew('${ctxPath }')">删除new标识</a>&nbsp;&nbsp;--%>
			&nbsp;&nbsp;&nbsp;
		</div>
	</div>
	<!--数据显示 --END -->
	<!--上传图片-->
	<div id="addDialog" class="easyui-dialog" title="导入年假" data-options="closed: true,modal:true" style="width: 600px; height: 200px; padding-left: 20px; padding-top: 20px; text-align: center">
	<form id="upfileForm" name="upfileForm" method="post"  enctype="multipart/form-data" action="<%=ctxPath%>/holiday/uploadVacation">
			<input type="hidden" name="tourId" id="tourId" />
			<input type="hidden" name="Img_message" id="Img_message" />
			<table border="0"  style="text-align: left;">
				<tr>
					<td style="text-align: right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;月份：</td>
				
					<td style="text-align: right" nowrap="nowrap">
						<select class="easyui-combobox"  panelHeight="auto" style="width: 100px" id="mouth" name="mouth" >
                                    <option value="0">请选择..</option>
                                    <option value="1">1月</option>
                                    <option value="2">2月</option>
                                    <option value="3">3月</option>
                                    <option value="4">4月</option>
                                    <option value="5">5月</option>
                                    <option value="6">6月</option>
                                    <option value="7">7月</option>
                                    <option value="8">8月</option>
                                    <option value="9">9月</option>
                                    <option value="10">10月</option>
                                    <option value="11">11月</option>
                                    <option value="12">12月</option>
                                    
                       </select>
					</td>
					<td style="text-align: right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;上传文件：</td>
					<td align='center'>
						<input type="file" id="file_path" name="file_path" value="abc" size="75" /> 
						<input type="hidden" id="filePath" name="filePath" /> 
					</td>
				</tr>
				<tr>
					<td colspan="4">&nbsp;</td>
				</tr> 
				<tr>
					<td colspan="4">&nbsp;</td>
				</tr> 
				<tr>
					<td colspan="4" style="padding-left:160px;">
						<a href="#" class="easyui-linkbutton" plain="false" onclick="onSubmitFile();">确&nbsp;&nbsp;定&nbsp;&nbsp;</a>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="#" class="easyui-linkbutton" plain="false" onclick="cancleDialogFile();">&nbsp;&nbsp;取&nbsp;&nbsp;消&nbsp;&nbsp;</a>
					</td>
				</tr>
			</table> 
		</form>
	</div>
	
	

	
	
	

</body>


</html>