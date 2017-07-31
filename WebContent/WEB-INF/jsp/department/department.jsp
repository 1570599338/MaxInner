<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/jsp/inc/inc.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>公司与部门</title>
</head>
<body>
	<form id="deleteForm" name="deleteForm" action="" method="post" target="_self">
	    <input id="tacheTitle" name="tacheTitle" type="hidden" value="${tacheName_one }" />
	    <input id="tache_oldName" name="tache_oldName" type="hidden" value="${tacheName }" />
	    <input id="projectId_del" name="projectId_del" type="hidden" value="${projectId }" />
	    <input id="pk_id" name="pk_id" type="hidden"  />
	</form>
	<h2>公司与部门</h2>
	
	<div style="background:#f0f0f0;width: 1200px;height: 35px;">&nbsp;&nbsp;
		 <a href="#" class="easyui-linkbutton"  data-options="iconCls:'icon-add'"  style="margin-top: 4px;" onclick="addCompany()">添加公司</a>
		 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		 <a href="#" class="easyui-linkbutton"  data-options="iconCls:'icon-edit' "  style="margin-top: 4px;" onclick="editCompany('${ctxPath}')">编辑公司</a>
		 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		 <a href="#" class="easyui-linkbutton"  data-options="iconCls:'icon-remove' "  style="margin-top: 4px;" onclick="delCompany('${ctxPath}')">删除公司</a>
		 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		 <a href="#" class="easyui-linkbutton"  data-options="iconCls:'icon-add'"  style="margin-top: 4px;" onclick="addDepartment()">添加部门</a>
		  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	</div>
	<br />
	
	<div id="dataList" class="easyui-accordion">
	   <c:forEach items="${list}" var="company" >
	        <div title="${company.company }" id="${company.PK_ID}"  class="tacheDIV" style="overflow:auto;padding:10px;" iconCls="icon-flow">
	        	<table class="gridtable" id="${company.PK_ID}">
	        	   <TR>
					  <th width="5%" nowrap="nowrap">编号</th>
					  <th width="12%" nowrap="nowrap">部门名称</th>
					  <th width="12%" nowrap="nowrap">部门编号</th>
					  <th width="8%" nowrap="nowrap">公司ID</th>
					  <th width="18%" nowrap="nowrap">创建时间</th>
					  <th width="5%" nowrap="nowrap">操作</th>

				   </TR>
				   <c:forEach items="${company.typeList }" var="depart" varStatus="dep">
					   <TR>
					      <td>${dep.count}</td>
					      <td>${depart.department}</td>
					      <td>${depart.code}</td>
					      <td>${depart.companyId}</td>
					      <td>${depart.createAt}</td>
					      <td>
					      	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit'" plain="true" onclick="editDepartment('${ctxPath}','${company.company }','${depart.PK_ID }','${depart.department}','${depart.code}')"></a>
					      	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'" plain="true" onclick="deleDepartment('${ctxPath}','${depart.PK_ID }')"></a>
					      </td>
					   </TR>
					</c:forEach>
				 </table>
			</div>
	   </c:forEach>
	</div>
	 
	<!--数据显示 --END -->
	<!--文件模板类型-->
<div id="addComanyDialog" class="easyui-dialog" title="公司名称" data-options="closed: true,modal:true"  style="width:350px;height:200px;padding-left:20px; padding-top:20px;text-align:center">
	<form id="addCompanyForm" name="addCompanyForm" action="" method="post" target="_self">
		<input id="ctxPath" name="ctxPath" type="hidden" value="${ctxPath }" />
		<input id="companyId" name="companyId" type="hidden" value="" />
		<table  border="0" cellpadding="5" cellspacing="2"  style="text-align: left;margin-left: 20px; "  >
			 <tr>
				 <td style="text-align: right">公司名称：</td>
				 <td><input  id="company" name="company"  class="easyui-validatebox" data-options="required:true"  />
				 </td>
			 </tr>		 		 
			 <tr><td  colspan="2" >
				   <a href="#" class="easyui-linkbutton"  plain="false" onclick="submitCompanyDialog('${ctxPath}');">&nbsp;&nbsp;确&nbsp;&nbsp;定&nbsp;&nbsp;</a>
				   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			       <a href="#" class="easyui-linkbutton"  plain="false" onclick="cancleCompanyDialog();">&nbsp;&nbsp;取&nbsp;&nbsp;消&nbsp;&nbsp;</a>
			 </td></tr>
	    </table>
    </form>
</div>

<div id="addDepartmentDialog" class="easyui-dialog" title="增加所属模板类型" data-options="closed: true,modal:true"  style="width:680px;height:180px;padding-left:20px; padding-top:20px;text-align:center">
	<form id="addDepartmentForm" name="addTargetForm" action="" method="post" target="_self">
		<input id="companyId" name="companyId" type="hidden"  />
		<input id="departMentId" name="departMentId" type="hidden"  />
		<input id="flage" name="flage" type="hidden"  />
		 <table  border="0" cellpadding="3" cellspacing="1"  style="text-align: left; width:530px;height:80px;"  >
		 	 <tr>
				 <td style="text-align: right" nowrap="nowrap">公司名称：</td>
				 <td> 
					 <input  id="companyName" name="companyName"  class="easyui-combobox"  panelHeight="auto" data-options="required:true"  />
				</td>
				<td style="text-align: right" nowrap="nowrap">部门名称：</td>
				<td><input type="text" id="departMent" name="departMent" class="easyui-validatebox" data-options="required:true,validType:'length[1,18]'" /></td>
				<td style="text-align: right" nowrap="nowrap">部门代码：</td>
				<td>
					<input type="text" id="departCode" name="departCode" class="easyui-validatebox" data-options="required:true,validType:'length[1,18]'" />
				</td>
			 </tr>
			 <tr><td  colspan="5" style="padding-left: 130px;">
				   <a href="#" class="easyui-linkbutton"  plain="false" onclick="submitTypeDialog('${ctxPath}');">&nbsp;&nbsp;确&nbsp;&nbsp;定&nbsp;&nbsp;</a>
				   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			       <a href="#" class="easyui-linkbutton"  plain="false" onclick="cancleDepartmentDialog();">&nbsp;&nbsp;取&nbsp;&nbsp;消&nbsp;&nbsp;</a>
			 </td></tr>
	    </table>
    </form>
</div>
<script type="text/javascript" src="${ctxPath}/jspage/department/department.js"></script>
</body>
</html>