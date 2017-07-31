<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/jsp/inc/inc.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctxPath}/jspage/staff/staff.js"></script>
<title>用户管理</title>
</head>
<body>
	<form action="" method="post" id="dataForm" name="dataForm">
		<input type="hidden" value="${ctxPath }" name="ctxPath" id="ctxPath" />
		<input type="hidden" name="pk_id" id="pk_id" />
		<input type="hidden" name="stat" id="stat" />
	</form>
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
			</tr>
		</thead>
	</table>


	<div id="tb" style="padding: 10px 5px 5px 10px; height: auto;">
		<div>
			公司名称：<input class="input_text" type="text" style="width: 80px" id="log_name" name="log_name" />&nbsp;&nbsp;&nbsp; 
			部门名称：<input class="input_text" type="text" style="width: 80px" id="user_name" name="user_name" />&nbsp;&nbsp;&nbsp;
			状态： <select class="easyui-combobox" panelHeight="auto" style="width: 100px" id="isforbid" name="isforbid">
						<option value="">请选择</option>
						<option value="1">在职</option>
						<option value="0">离职</option>
					</select>&nbsp;&nbsp;&nbsp; 
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="getdata()">&nbsp;&nbsp;搜&nbsp;&nbsp;索&nbsp;&nbsp;</a>
		</div>

		<div style="margin-bottom: 5px;margin-top: 5px;">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="false" onclick="addUserDiv()">添加员工</a>&nbsp;&nbsp; 
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="false" onclick="editUserDiv()">编辑员工</a>&nbsp;&nbsp;
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="false" onclick="deleteUser()">删除员工</a>&nbsp;&nbsp;
			<a href="#" class="easyui-linkbutton" iconCls="icon-start_and_stop" onclick="startStopDialog();">员工状态</a>&nbsp;&nbsp; 
			<a href="#" class="easyui-linkbutton" iconCls="icon-import" plain="false" onclick="uploadDialog();">导入员工</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-import" plain="false" onclick="uploadImgDialog();">批量导入员工照片</a>
		</div>
	</div>


	<!--导入用户信息  -->
	<div id="uploadUserPanel" class="easyui-dialog" title="导入用户信息" data-options="closed: true,modal:true" style="width: 580px; height: 300px; padding: 10px">
		<form id="uploadForm" name="uploadForm" action="${ctxPath}/staff/uploadStaff" method="post" enctype="multipart/form-data" target="_self">
			<table border="0" cellpadding="1" cellspacing="1" style="width: 95%; height: 80%" align="center">
				<tr>
					<td></td>
				</tr>
				<tr>
					<td>
						<table border="0" cellpadding="1" cellspacing="1"
							style="width: 100%;" align="center">
							<tr>
								<td height="80">注&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;意：</td>
								<td>导入数据前需要下载模板，对模板填充完整数据后再导入，</br>如数据有误，会返回文件，修改完后再导入。
								</td>

								<td><a href="#" class="easyui-linkbutton" plain="false" data-options="iconCls:'icon-download'"
									onclick="javascript:location.href='${ctxPath}/staff/downStaffTemp'">&nbsp;&nbsp;模板下载&nbsp;&nbsp;</a>
								</td>
							</tr>
							<tr>
								<td colspan="3"></td>
							</tr>
							<tr>
								<td>选择文件：</td>
								<td colspan="2">
									<input id="uploadFile" name="uploadFile" type="file" style="width: 360px" /> 
									<span style="color: #ff0000; position: relative; top: 2px;"> *</span>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td height="10px;"></td>
				</tr>
				<tr>
					<td><HRstyle ="FILTER: alpha(opacity=100,finishopacity 0, style=3) " width="100%" color=95B8E7 SIZE="2" /></td>
				</tr>
				<tr>
					<td height="10px;"></td>
				</tr>
				<tr>
					<td align="center">
						<a href="#" class="easyui-linkbutton" plain="false" onclick="confirmuploadStaff();">&nbsp;&nbsp;上&nbsp;&nbsp;传&nbsp;&nbsp;</a>&nbsp;&nbsp;
						<a href="#" class="easyui-linkbutton" plain="false" onclick="closeUploadUserPanel();">&nbsp;&nbsp;取&nbsp;&nbsp;消&nbsp;&nbsp;</a>
					</td>
				</tr>
			</table>
		</form>
	</div>

	<!--添加用户-->
	<div id="addDialog" class="easyui-dialog" title="增加职员"
		data-options="closed: true,modal:true" style="width: 540px; height: 260px; padding-left: 20px; padding-top: 20px; text-align: center">
		<form id="addForm" name="addForm" action="" method="post" target="_self">
			<input id="staffId" name="staffId" type="hidden" />
			<table border="0" cellpadding="1" cellspacing="5" style="text-align: left;">
				<tr>
					<td style="text-align: right" nowrap="nowrap">公司：</td>
					<td><input type="text" id="company" name="company" panelHeight="auto" class="easyui-combobox"  data-options="required:true" /></td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td style="text-align: right">部门：</td>
					<td><input type="text" id="department" name="department" panelHeight="auto" class="easyui-combobox" data-options="required:true" />
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
				</tr>
				<tr>
					<td style="text-align: right" nowrap="nowrap">姓名：</td>
					<td><input type="text" id="username" name="username" class="easyui-validatebox" data-options="required:true" /></td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td style="text-align: right" nowrap="nowrap">性别：</td>
					<td>
						<select class="easyui-combobox" panelHeight="auto" style="width: 100px" id="gender" name="gender" data-options="required:true,editable:false">
							<option value="1">男</option>
							<option value="0">女</option>
						</select>
					</td>
				</tr>
				<tr>	
					<td style="text-align: right" nowrap="nowrap">固话号码：</td>
					<td><input type="text" id="telphone" name="telphone" class="easyui-validatebox" data-options="required:true,validType:'length[3,20]'" /></td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td style="text-align: right" nowrap="nowrap">手机号：</td>
					<td>
						<input class="easyui-validatebox" id="cellphone" name="cellphone" data-options="required:true,validType:'length[11,11]'" />
					</td>
				</tr>
				<tr>
					<td style="text-align: right" nowrap="nowrap">电子邮件：</td>
					<td>
						<input class="easyui-validatebox" id="email" name="email" data-options="required:true,validType:'email'" />
					</td>
				</tr>
				<tr>
					<td colspan="5">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="5" style="padding-left: 150px;">
						<a href="#" class="easyui-linkbutton" plain="false" onclick="submitDialog('${ctxPath}');">&nbsp;&nbsp;确&nbsp;&nbsp;定&nbsp;&nbsp;</a>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="#" class="easyui-linkbutton" plain="false" onclick="cancleDialog();">&nbsp;&nbsp;取&nbsp;&nbsp;消&nbsp;&nbsp;</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	
<!--批量导入照片 -->	
	<div id="uploadStaffImgPanel" class="easyui-dialog" title="导入用户信息" data-options="closed: true,modal:true" style="width: 580px; height: 300px; padding: 10px">
		<form id="uploadImgForm" name="uploadImgForm" action="${ctxPath}/staff/toUpdateHeadImg" method="post" enctype="multipart/form-data" target="_self">
			<table border="0" cellpadding="1" cellspacing="1" style="width: 95%; height: 80%" align="center">
				<tr>
					<td></td>
				</tr>
				<tr>
					<td>
						<table border="0" cellpadding="1" cellspacing="1"
							style="width: 100%;" align="center">
							<tr>
								<td height="80">注&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;意：</td>
								<td>批量导入照片时应该时把所有照片进行整体打个.ZIP的压缩包。</br>照片格式应该是.JPG、.png、.jpeg。</br> 照片的命名规则：员工的姓名。例如：张三.JPG。</td>
								<%-- <td><a href="#" class="easyui-linkbutton" plain="false" data-options="iconCls:'icon-download'"
									onclick="javascript:location.href='${ctxPath}/staff/downStaffTemp'">&nbsp;&nbsp;模板下载&nbsp;&nbsp;</a>
								</td> --%>
							</tr>
							<tr>
								<td colspan="3"></td>
							</tr>
							<tr>
								<td>选择文件：</td>
								<td colspan="2">
									<input id="uploadImg" name="uploadImg" type="file" style="width: 360px" /> 
									<span style="color: #ff0000; position: relative; top: 2px;"> *</span>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td height="10px;"></td>
				</tr>
				<tr>
					<td><HRstyle ="FILTER: alpha(opacity=100,finishopacity 0, style=3) " width="100%" color=95B8E7 SIZE="2" /></td>
				</tr>
				<tr>
					<td height="10px;"></td>
				</tr>
				<tr>
					<td align="center">
						<a href="#" class="easyui-linkbutton" plain="false" onclick="confirmuploadImg();">&nbsp;&nbsp;上&nbsp;&nbsp;传&nbsp;&nbsp;</a>&nbsp;&nbsp;
						<a href="#" class="easyui-linkbutton" plain="false" onclick="closeUploadPanelImg();">&nbsp;&nbsp;取&nbsp;&nbsp;消&nbsp;&nbsp;</a>
					</td>
				</tr>
			</table>
		</form>
	</div>	

</body>
</html>