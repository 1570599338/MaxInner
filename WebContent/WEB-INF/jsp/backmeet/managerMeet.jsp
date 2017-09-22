<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/WEB-INF/jsp/inc/inc.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>会议系统的管理后台</title>

 <script type="text/javascript" src="${ctxPath }/jspage/backmeet/backmeet.js"></script>
<%--<link href="${ctxPath }/js/smartWizard/smart_wizard.css" rel="stylesheet" type="text/css">
 --%><style type="text/css">

/* .td{border:solid #add9c0; border-width:0px 1px 1px 0px;}
.table{border:solid #add9c0; border-width:1px 0px 0px 1px;}
 */
	thead{
		font-family:Arial, Helvetica, sans-serif;
		font-size:16px;
		text-align:center;
		 font-weight:900;
		  color:#FFF;
		
		}
		.comment_odd{
			background-color:#f2f2f2;
		}
		.mini{
		/* 、、background-color:#00AAc2; */
		background-color:#73C003;
		}
		
		
		.updown{
				width:127px;
				height:44px;
			}
		.up{
				width:144px;
				height:22px;
				background-color:#73C003;
			}
		.down{
			width:127px;
			height:22px;
		}
    </style>
</head>
<body >
	<div id="tb" style="padding: 10px 5px 5px 10px; height: auto;text-align:center">
		<!-- <div style="padding-left:40px"> -->
		<div style="margin-bottom: 5px;margin-top: 5px;">
			日期 ：<input class="easyui-datebox" style="width: 150px" id="searchData" name="searchData">&nbsp;&nbsp;&nbsp; 
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<!-- 内&nbsp;&nbsp;&nbsp; 容：<input class="input_text" type="text" style="width: 180px" id="user_name" name="user_name" />&nbsp;&nbsp;&nbsp; -->
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="searchData()">&nbsp;搜&nbsp;索&nbsp;</a>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			
		<!-- </div> -->

		<!-- <div style="margin-bottom: 5px;margin-top: 5px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; -->
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="false" onclick="addBookMeet()">预定</a>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;
			<%-- <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="false" onclick="updateAdDiv()">修改</a>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp; 
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="false" onclick="delNoticeDiv('${ctxPath }')">取消</a>&nbsp;&nbsp;
			&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; --%>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;
			<a href="#" class="easyui-linkbutton" iconCls="icon-download" plain="false" onclick="downNoticeDiv('${ctxPath }')">下载</a>&nbsp;&nbsp;
		</div>
	</div>
	<div class="container">
	  <div class="content">
		  <div class="meet_table" align="center"   width="580px"  >
			  <div  style="padding-bottom:5px">
			  	<table width="770px" border="0" >
			  		<tr class="comment_odd" height="25">
			  			<td><div align="left" style="color:#0081c2;font-weight:900;" onclick="previousDay()">&nbsp;&nbsp; &lt;&lt; 前一天 </div></td>
			  			<td><div align="center" ><span id="dateTime"></span>&nbsp;&nbsp;<span id="weekday"></span></div></td>
			  			<td><div align="right"  style="color:#0081c2;font-weight:900;" onclick="nextDay()"> 后一天 &gt;&gt;&nbsp;&nbsp;</div></td>
			  		</tr>
			  		<tr></tr>
			  	</table>
			   </div>
			   <div>
				    <table class="content"   border="1" bordercolor="#cccccc" style="border-collapse:collapse;">
					       <thead>
					       	  <tr bgcolor="#0081c2">
					       	  	<td>名称</td>
					       	  	<td colspan="3">一层</td>
					       	  	<td colspan="2">二层</td>
					       	  </tr>
						      <tr bgcolor="#0081c2">
						        <td height="40" width="29">时间</td>
						        <td width="142">火星</td>
						        <td width="142">天王星</td>
						        <td width="142">海王星</td>
						        <td width="142">金星</td>
						        <td width="142">水星</td>
						       </tr>
					       </thead>
					       
					       <tbody id="tbody" name="tbody">  </tbody>
				      </table>
				</div>
		
	    </div>
	   
	  </div>
	</div>
	
 <div id="addDialog" class="easyui-dialog" title="预定会议室" data-options="closed: true,modal:true" style="width: 580px; height: 410px; padding-left: 40px; padding-top: 20px; text-align: center">
	<form id="bookMeet" name=""bookMeet"" method="post"  action="">
			<input type="hidden" name="ctxPath" id="ctxPath" value="${ctxPath }" />
			<input type="hidden" name="bookMeetId" id="bookMeetId" />
			<table border="0"  style="text-align: center;" style="width: 580px; height: 380px; padding-left: 40px; padding-top: 20px; text-align: center" >
				<tr>
					<td style="text-align: right" nowrap="nowrap">会议室</td>
				
					<td style="text-align: right" nowrap="nowrap">
						<select class="easyui-combobox"  panelHeight="auto" style="width: 180px" id="meet" name="meet" >
                                    <option value="0">请选择..</option>
                                    <option value="1">火星</option>
                                    <option value="2">天王星</option>
                                    <option value="3">海王星</option>
                                    <option value="5">金星</option>
                                    <option value="6">水星</option>
                                    
                       </select>
					</td>
					
					<td>日期</td>
					<td><input class="easyui-datebox" style="width: 180px" name="bookDate" id="bookDate" /></td>
				</tr>
				<tr>
					<td colspan="4">&nbsp;</td>
				</tr> 
				 <tr>
					<td>开始时间</td>
					<td>
						<select class="easyui-combobox"  panelHeight="auto" style="width: 180px" id="startTime" name="startTime" >
                                    <option value="0">请选择..</option>
                                    <option value="9">09:00</option>
                                    <option value="9.5">09:30</option>
                                    <option value="10">10:00</option>
                                    <option value="10.5">10:30</option>
                                    <option value="11">11:00</option>
                                    <option value="11.5">11:30</option>
                                    <option value="12">12:00</option>
                                    <option value="12.5">12:30</option>
                                    <option value="13">13:00</option>
                                    <option value="13.5">13:30</option>
                                    <option value="14">14:00</option>
                                    <option value="14.5">14:30</option>
                                    <option value="15">15:00</option>
                                    <option value="15.5">15:30</option>
                                    <option value="16">16:00</option>
                                    <option value="16.5">16:30</option>
                                    <option value="17">17:00</option>
                                    <option value="17.5">17:30</option>
                                    <option value="18">18:00</option>
                       </select>
					</td>
					<td>结束时间</td>
					<td>
						<select class="easyui-combobox"  panelHeight="auto" style="width: 180px" id="endTime" name="endTime" >
                                    <option value="0">请选择..</option>
                                    <option value="9">09:00</option>
                                    <option value="9.5">09:30</option>
                                    <option value="10">10:00</option>
                                    <option value="10.5">10:30</option>
                                    <option value="11">11:00</option>
                                    <option value="11.5">11:30</option>
                                    <option value="12">12:00</option>
                                    <option value="12.5">12:30</option>
                                    <option value="13">13:00</option>
                                    <option value="13.5">13:30</option>
                                    <option value="14">14:00</option>
                                    <option value="14.5">14:30</option>
                                    <option value="15">15:00</option>
                                    <option value="15.5">15:30</option>
                                    <option value="16">16:00</option>
                                    <option value="16.5">16:30</option>
                                    <option value="17">17:00</option>
                                    <option value="17.5">17:30</option>
                                    <option value="18">18:00</option>
                       </select>
					</td>
				</tr>
				<tr>
					<td colspan="4">&nbsp;</td>
				</tr> 
				<tr>
					<td>辅助设备</td>
					<td>
						<!-- <select class="easyui-combobox"  panelHeight="auto" style="width: 180px" id="bookassist" name="bookassist" >
                                    <option value="0">请选择..</option>
                                    <option value="1">三方</option>
                                    <option value="2">电话</option>
                                    <option value="3">投影</option>
                       </select> -->
                       <input type="checkbox" name="bookassist" id='sanfang' value="1"/>三方
                       <input type="checkbox" name="bookassist" id='dianhua' value="2"/>电话
                       <input type="checkbox" name="bookassist" id='touying' value="3"/>投影
					</td>
					<td>预订人</td>
					<td><input class="easyui-validatebox" style="width: 180px" data-options="required:true,validType:'length[3,10]'" name="booker" id="booker"></td>
				</tr>
				<tr>
					<td colspan="4">&nbsp;</td>
				</tr>  
				<tr>
					<td colspan="4">备注</td>
				</tr> 
				<tr>
					<td colspan="4"><textarea name="remark" id="remark" style="height:60px;width: 350px"></textarea></td>
				</tr> 
				
				<tr>
					<td colspan="4">&nbsp;</td>
				</tr> 
				<tr>
					<td colspan="4">&nbsp;</td>
				</tr> 
				<tr>
					<td colspan="4">&nbsp;</td>
				</tr> 
				
				<tr>
					<td colspan="4" >
						<a href="#" class="easyui-linkbutton" plain="false" onclick="onSubmit();">确&nbsp;&nbsp;定&nbsp;&nbsp;</a>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="#" class="easyui-linkbutton" plain="false" onclick="cancleBookMeet();">&nbsp;&nbsp;取&nbsp;&nbsp;消&nbsp;&nbsp;</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	
	<!-- 修改用户的信息 -->
	<div id="updateDialog" class="easyui-dialog" title="预定会议室" data-options="closed: true,modal:true" style="width: 650px; height: 425px; padding-left: 20px;padding-right: 20px; padding-top: 20px; text-align: center">
		<div id="tbupdate" style="padding: 10px 5px 5px 10px; height: auto;">
		<div>
			日期 ：<input class="easyui-datebox" style="width: 150px" id="updatesearchData" name="updatesearchData">&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;
				会&nbsp;议&nbsp;室 ：<select class="easyui-combobox"  panelHeight="auto" style="width: 180px" id="updatemeet" name="updatemeet" >
                                    <option value="0">请选择..</option>
                                    <option value="1">火星</option>
                                    <option value="2">天王星</option>
                                    <option value="3">海王星</option>
                                    <option value="5">金星</option>
                                    <option value="6">水星</option>
                       </select>&nbsp;&nbsp;&nbsp; 
			<!-- 内&nbsp;&nbsp;&nbsp; 容：<input class="input_text" type="text" style="width: 150px" id="user_name" name="user_name" />&nbsp;&nbsp;&nbsp; -->
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="updategetdata()">&nbsp;&nbsp;搜&nbsp;&nbsp;索&nbsp;&nbsp;</a>
		</div>
		</div>
		<table id="dgupdate" style="width: 700px; height: 10px" nowrap="true" fitColumns="true" data-options="
						rownumbers:true,
						singleSelect:true,
						autoRowHeight:false,
						resizable:true,
						pagination:true,
						fit:true,
						toolbar:'#tbupdate',
						pageSize:10">
				<thead>
				</thead>
			</table>
	</div>
	
	<!-- 修改用户的信息 -->
	<form id="deletForm" action="">
		<input id="pk_id" name="pk_id" type="hidden" />
		<input id="deletdate" name="deletdate" type="hidden" />
		<input id="deletemeet" name="deletemeet"  type="hidden" >
	</form>
	<div id="deleteDialog" class="easyui-dialog" title="预定会议室" data-options="closed: true,modal:true" style="width: 650px; height: 425px; padding-left: 20px;padding-right: 20px; padding-top: 20px; text-align: center">
		<div id="tbdelete" style="padding: 10px 5px 5px 10px; height: auto;">
			<div>
				日期 ：<input class="easyui-datebox" style="width: 150px" id="deletesearchData" name="deletesearchData">&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;
				会&nbsp;议&nbsp;室 ：<select class="easyui-combobox"  panelHeight="auto" style="width: 180px" id="deltemeet" name="deltemeet" >
                                    <option value="0">请选择..</option>
                                    <option value="1">火星</option>
                                    <option value="2">天王星</option>
                                    <option value="3">海王星</option>
                                    <option value="5">金星</option>
                                    <option value="6">水星</option>
                       </select>&nbsp;&nbsp;&nbsp; 
				&nbsp;&nbsp;&nbsp;
				<!-- 内&nbsp;&nbsp;&nbsp; 容：<input class="input_text" type="text" style="width: 150px" id="user_name" name="user_name" />&nbsp;&nbsp;&nbsp; -->
				<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="deletegetdata()">&nbsp;&nbsp;搜&nbsp;&nbsp;索&nbsp;&nbsp;</a>
			</div>
		</div>
		<table id="dgdelete" style="width: 600px; height: 10px" nowrap="true" fitColumns="true" data-options="
						rownumbers:true,
						singleSelect:true,
						autoRowHeight:false,
						resizable:true,
						pagination:true,
						fit:true,
						toolbar:'#tbdelete',
						pageSize:10">
				<thead>
				</thead>
			</table>
	</div>
	
	<!-- 导出数据 -->
	<div id="downDialog" class="easyui-dialog" title="到处数据" data-options="closed: true,modal:true" style="width: 580px; height: 320px; padding-left: 40px; padding-top: 20px; text-align: center">
	<form id="downBookMeet" name=""bookMeet"" method="post"  action="">
			<table border="0"  style="text-align: center;" style="width: 580px; height: 380px; padding-left: 40px; padding-top: 20px; text-align: center" >
				<tr>
					<td style="text-align: right" nowrap="nowrap">会议室</td>
				
					<td style="text-align: right" nowrap="nowrap">
						<select class="easyui-combobox"  panelHeight="auto" style="width: 180px" id="downbookMeetId" name="downbookMeetId" >
                                    <option value="0">请选择..</option>
                                    <option value="1">火星</option>
                                    <option value="2">天王星</option>
                                    <option value="3">海王星</option>
                                    <option value="5">金星</option>
                                    <option value="6">水星</option>
                                    
                       </select>
					</td>
					
					<td>日期</td>
					<td><input class="easyui-datebox" style="width: 180px" name="downBookDate" id="downBookDate" /></td>
				</tr>
				<tr>
					<td colspan="4">&nbsp;</td>
				</tr> 
				 <tr>
					<td>开始时间</td>
					<td>
						<select class="easyui-combobox"  panelHeight="auto" style="width: 180px" id="downstartTime" name="downstartTime" >
                                    <option value="">请选择..</option>
                                    <option value="9">09:00</option>
                                    <option value="9.5">09:30</option>
                                    <option value="10">10:00</option>
                                    <option value="10.5">10:30</option>
                                    <option value="11">11:00</option>
                                    <option value="11.5">11:30</option>
                                    <option value="12">12:00</option>
                                    <option value="12.5">12:30</option>
                                    <option value="13">13:00</option>
                                    <option value="13.5">13:30</option>
                                    <option value="14">14:00</option>
                                    <option value="14.5">14:30</option>
                                    <option value="15">15:00</option>
                                    <option value="15.5">15:30</option>
                                    <option value="16">16:00</option>
                                    <option value="16.5">16:30</option>
                                    <option value="17">17:00</option>
                                    <option value="17.5">17:30</option>
                                    <option value="18">18:00</option>
                       </select>
					</td>
					<td>结束时间</td>
					<td>
						<select class="easyui-combobox"  panelHeight="auto" style="width: 180px" id="downendTime" name="downendTime" >
                                    <option value="">请选择..</option>
                                     <option value="9">09:00</option>
                                    <option value="9.5">09:30</option>
                                    <option value="10">10:00</option>
                                    <option value="10.5">10:30</option>
                                    <option value="11">11:00</option>
                                    <option value="11.5">11:30</option>
                                    <option value="12">12:00</option>
                                    <option value="12.5">12:30</option>
                                    <option value="13">13:00</option>
                                    <option value="13.5">13:30</option>
                                    <option value="14">14:00</option>
                                    <option value="14.5">14:30</option>
                                    <option value="15">15:00</option>
                                    <option value="15.5">15:30</option>
                                    <option value="16">16:00</option>
                                    <option value="16.5">16:30</option>
                                    <option value="17">17:00</option>
                                    <option value="17.5">17:30</option>
                                    <option value="18">18:00</option>
                       </select>
					</td>
				</tr>
				<tr>
					<td colspan="4">&nbsp;</td>
				</tr> 
				<tr>
					<td>辅助设备</td>
					<td>
						<select class="easyui-combobox"  panelHeight="auto" style="width: 180px" id="downbookassist" name="downbookassist" >
                                    <option value="0">请选择..</option>
                                    <option value="1">三方</option>
                                    <option value="2">电话</option>
                                    <option value="3">投影</option>
                       </select> 
                      <!--  <input type="checkbox" name="bookassist" id='sanfang' value="1"/>三方
                       <input type="checkbox" name="bookassist" id='dianhua' value="2"/>电话
                       <input type="checkbox" name="bookassist" id='touying' value="3"/>投影 -->
					</td>
					<td>预订人</td>
					<td><input class="easyui-validatebox" style="width: 180px"  name="downuserName" id="downuserName"></td>
				</tr>
				<tr>
					<td colspan="4">&nbsp;</td>
				</tr>  
				
				<tr>
					<td colspan="4">&nbsp;</td>
				</tr> 
				
				
				<tr>
					<td colspan="4" >
						<a href="#" class="easyui-linkbutton" plain="false" onclick="downonSubmit();">下&nbsp;&nbsp;载&nbsp;&nbsp;</a>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="#" class="easyui-linkbutton" plain="false" onclick="downcancleBookMeet();">&nbsp;&nbsp;取&nbsp;&nbsp;消&nbsp;&nbsp;</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>