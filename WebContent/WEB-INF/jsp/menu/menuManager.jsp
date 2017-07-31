<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/inc/inc.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>新华信复核管理软件平台</title>
<script type="text/javascript" src="${ctxPath }/jspage/menu/menuManager.js" charset="UTF-8"></script>
<%-- <base href="${basePath}"> --%>
	<script type="text/javascript">
		function sayInfo(mess){
			$.messager.confirm('提示',mess,'info');
		}
	</script>
	
</head>
<body>
	<div id="tb" style="padding:5px;height:auto">
		<div style="margin-bottom:5px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="openAddDialog();"></a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editMenuInfo()"></a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="javascript:deleteMenuInfo()"></a>
		</div>
	</div>

	<table id="test" style="width:500px;height:400px" data-options="
				rownumbers:true,
				singleSelect:true,
				autoRowHeight:false,
				resizable:true,
				fit:true,
				toolbar:'#tb'"> 
	</table>


<!-- 添加菜单的弹窗 -->
 <div id="addMenu" class="easyui-dialog" title="添加菜单" 
	data-options="closed: true,modal:true" 
	style="width:500px;height:auto;padding:10px">
	<form id="add" name="addForm" action="${ctxPath}/menuManage/subAddMenu" method="post">
	   <input type="hidden" id="ctxPath" name="ctxPath" value="${ctxPath }"/> 
		<table border="0" cellpadding="1" cellspacing="10" align="center" style="text-align: left">
			<tr>
				<td colspan="2">菜单名称：
					<td>
					<input class="input_text" type="text" id="menuName_add" name="menuName_add" maxlength="10" style="width: 150px;"/>
					<span style="color:#ff0000;position:relative;top:2px;" > *</span>
					</td>
				</td>
			</tr>
			<tr>
				<td colspan="2">菜单图标：
					<td>
					<input class="input_text" type="text" id="menuImge_add" name="menuImge_add" style="width: 150px;"/>
					</td>
					<!-- <span style="color:#ff0000;position:relative;top:2px;" > *</span> -->
				</td>
			</tr>
			<tr>
				<td colspan="2">菜单编号：
					<td>
					<input class="input_text" type="text" id="menuNum_add" name="menuNum_add" style="width: 150px;"/>
					<span style="color:#ff0000;position:relative;top:2px;" > *</span>
					</td>
				</td>
			</tr>
			<tr >
				<td colspan="2">父菜单编号：
					<td>
					<input class="input_text" type="text" id="parentMenuNum_add" name="parentMenuNum_add" value="" style="width:150px;"/>
		    		<span style="color:#ff0000;position:relative;top:2px;" > *若为父菜单则不填</span>
		    		</td>
				</td>
			</tr>
			<tr >
				<td colspan="2">菜单路径：
				<td>
				<input class="input_text" type="text" id="menuURL_add" name="menuURL_add" style="width: 180px;"/>
				</td>
				<!-- <span style="color:#ff0000;position:relative;top:2px;" > *</span> -->
				</td>
			</tr>
			<tr >
				<td colspan="2">菜单功能描述：
					<td>
					<textarea name="describe_add" style="height:60px;"></textarea>
					</td>
				</td>
			</tr>
			<tr style="text-align：center;">
					<a href="#" class="easyui-linkbutton"  plain="false" onclick="addSub();">&nbsp;&nbsp;确&nbsp;&nbsp;定&nbsp;&nbsp;</a>&nbsp;&nbsp;
					<a href="#" class="easyui-linkbutton"  plain="false" onclick="cancleAddDialog();">&nbsp;&nbsp;取&nbsp;&nbsp;消&nbsp;&nbsp;</a>
			</tr>
		</table>
	</form>		
</div>

<!-- 编辑菜单的弹窗 -->
<div id="editMenu" class="easyui-dialog" title="编辑菜单" 
	data-options="closed: true,modal:true" 
	style="width:500px;height:auto;padding:10px">
	<form name="editForm" action="${ctxPath}/menuManage/subEditMenu" method="post">
		 <input type="hidden" id="ctxPath" name="ctxPath" value="${ctxPath }"/> 
		<!-- <input type="hidden" name="menuNum" id="menuNum" value=""/> -->
		<table border="0" cellpadding="1" cellspacing="10" align="center">
			<tr>
				<td colspan="2">菜单名称：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<td>
					<input class="input_text" type="text" id="menuName" name="menuName" maxlength="10" style="width: 150px;"/>
					<span style="color:#ff0000;position:relative;top:2px;" > *</span>
					</td>
				</td>
			</tr>
			<tr>
				<td colspan="2">菜单图标：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<td>
					<input class="input_text" type="text" id="menuImge" name="menuImge" style="width: 150px;"/>
					</td>
					<!-- <span style="color:#ff0000;position:relative;top:2px;" > *</span> -->
				</td>
			</tr>
			<tr>
				<td colspan="2">菜单编号：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<td>
					<input class="input_text" type="text" id="menuNum" name="menuNum" style="width: 150px;"/>
					<span style="color:#ff0000;position:relative;top:2px;" > *</span>
					</td>
				</td>
			</tr>
			<tr >
				<td colspan="2">父菜单编号：&nbsp;&nbsp;&nbsp;&nbsp;
					<td>
					<input class="input_text" type="text" id="parentMenuNum" name="parentMenuNum" value="" style="width:150px;"/>
		    		<span style="color:#ff0000;position:relative;top:2px;" > *若为父菜单则不填</span>
		    		</td>
				</td>
			</tr>
			<tr >
				<td colspan="2">菜单路径：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<td>
				<input class="input_text" type="text" id="menuURL" name="menuURL" style="width: 180px;"/>
				</td>
				<!-- <span style="color:#ff0000;position:relative;top:2px;" > *</span> -->
				</td>
			</tr>
			<tr >
				<td colspan="2">菜单功能描述：
					<td>
					<textarea id="describe" name="describe" style="height:60px;"></textarea>
					</td>
				</td>
			</tr>
			<tr align="center">
					<a href="#" class="easyui-linkbutton"  plain="false" onclick="editSub();">&nbsp;&nbsp;确&nbsp;&nbsp;定&nbsp;&nbsp;</a>&nbsp;&nbsp;
					<a href="#" class="easyui-linkbutton"  plain="false" onclick="cancleDialog();">&nbsp;&nbsp;取&nbsp;&nbsp;消&nbsp;&nbsp;</a>
					<!-- <input class="button_G" type="button" value="取消" onclick="cancleDialog()"/>&nbsp;&nbsp;
		            <input class="button_M"  type="button" value="提交" onclick="editSub()"/>
				 -->
			</tr>
		</table>
	</form>
</div>
</body>
</html>
	