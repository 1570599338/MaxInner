<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Strict//EN">
<html>
<head>
<title>通讯录</title>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />
<%@include file="/WEB-INF/jsp/inc/inc.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="imagetoolbar" content="no" />
<link rel="stylesheet" href="${ctxPath}/css/front/css/layout.css" type="text/css" />
<link rel="stylesheet" href="${ctxPath}/css/front/css/gallery.css" type="text/css" />
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
	
</style>
<script type="text/javascript">
$(function(){
	var ctxPath = $("#ctxPath").val();
	//$("#company").combobox('select', '${companyid}');
	$('#company').combobox({ 
		url:ctxPath + '/department/queryCompanyList',
	    editable:true, //不可编辑状态
	    cache: false,
	    panelHeight: 'auto',//自动高度适合
	    valueField:'pk_id',  
	    textField:'company',
	   /*  loadFilter:function(data){  
	       var obj={};  
	       obj.pk_id='';  
	       obj.company='-请选择-';  
	       data.splice(0,0,obj);//在数组0位置插入obj,不删除原来的元素  
	       return data;  
	     }, */
	    onSelect: function(record){
	    		_type_name.combobox({
		    		editable:true, //不可编辑状态
		    		panelHeight: 'auto',//自动高度适合
		            disabled: false,
		            url: ctxPath + '/department/queryDepartment?pk_id=' + record.pk_id,
		            valueField:'code',  
		    	    textField:'department',
		    	    /* loadFilter:function(data){  
		    		       var obj={};  
		    		       obj.code='';  
		    		       obj.department='-请选择-';  
		    		       data.splice(0,0,obj);//在数组0位置插入obj,不删除原来的元素  
		    		       return data;  
		    		     }, */
		        }).combobox('clear');
			}
	   }); 
	var _type_name = $('#department').combobox({
		editable:true, //不可编辑状态
		panelHeight: 'auto',//自动高度适合
	    disabled: false,
	    valueField:'pk_id',  
	    textField:'department',
	   /*  loadFilter:function(data){  
		       var obj={};  
		       obj.code='';  
		       obj.department='-请选择-';  
		       data.splice(0,0,obj);//在数组0位置插入obj,不删除原来的元素  
		       return data;  
		     }, */
	});
	if('${companyid}'){
		_type_name.combobox({
    		editable:true, //不可编辑状态
    		panelHeight: 'auto',//自动高度适合
            disabled: false,
            url: ctxPath + '/department/queryDepartment?pk_id=' + '${companyid}',
            valueField:'code',  
    	    textField:'department',
    	   /*  loadFilter:function(data){  
 		       var obj={};  
 		       obj.code='';  
 		       obj.department='-请选择-';  
 		       data.splice(0,0,obj);//在数组0位置插入obj,不删除原来的元素  
 		       return data;  
 		     }, */
        }).combobox('clear');
	}
	$("#company").combobox('select', '${companyid}');
	//$("#department").combobox('select', '');
	if('${companyid}'==''){
		$("#department").combobox('select', "--请选择--");
	}else{
		$("#department").combobox('select', '${departMentCode}');
	}
	
	$("#gender").combobox('select', '${gender}');
	$("#useName").val('${useName}');
	$("#telphone").val('${telphone}');
	
});

</script>

<body id="top">

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
       <li><a href="${ctxPath}/manage/seeManage">图书</a></li>
        <li><a href="${ctxPath}/meet/toHomepage">会议室</a></li>
        <li class="active last"><a href="${ctxPath}/connnect/toConnectPage">通讯录</a>
        </li>
        <li ><a href="${ctxPath}/home/toHome">首页</a></li>
      </ul>
    </div>
  </div>
</div>
<!-- ####################################################################################################### -->
<form id="upfileForm" action="" method="post">
	<input type="hidden"  id="companyx" name="companyx"  vaule="${companyid}"/>
	<input type="hidden"  id="departmentx" name="departmentx" vaule="${departMentCode}"/>
	<input type="hidden"  id="genderx" name="genderx"/>
	<input type="hidden"  id="useNamex" name="useNamex"/>
	<input type="hidden"  id="telphonex" name="telphonex"/>
	
</form>
<div class="wrapper col2">
  <div id="search_form">
    <table>
    	<tr>
    		<td>公司:</td><td> <input type="text" id="company" name="company" panelHeight="auto" class="easyui-combobox"  /> 
    			
    		</td>
    		<td>&nbsp;&nbsp;&nbsp;</td>
    		<td>部门:</td><td><input type="text" id="department" name="department" panelHeight="auto" class="easyui-combobox"  value="--请选择--"/></td>
    		<td>&nbsp;&nbsp;&nbsp;</td>
    		<td>性别:</td><td><input type="text" id="gender" name="gender" panelHeight="auto" class="easyui-combobox" data-options="valueField: 'value',textField: 'label',
    		data: [{label: '--请选择--',value: '',selected:true},{label: '男',value: '1'},{label: '女',value: '0'}]" /></td>
    		<td>&nbsp;&nbsp;&nbsp;</td>
    		<td>姓名:</td><td><input class="input_text" type="text" style="width:116px;height:24px" id="useName" name="useName" /></td>
    		<td>&nbsp;&nbsp;&nbsp;</td>
    		<td>分机号:</td><td><input class="input_text" type="text" style="width:116px;height:24px" id="telphone" name="telphone" /></td>
    		<td>&nbsp;&nbsp;&nbsp;</td>
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
	            <img src="${ctxPath}${s.path}" alt="" />
	            <p>${s.useName}</p>
	            <p>${s.telphone}</p>
	        </li>
	      </c:forEach>
	      <c:if test=""></c:if>
      </ul>
    </div>
    <!-- ####################################################################################################### -->
<!--     <div class="pagination">
      <ul>
        <li class="prev"><a href="#">&laquo; Previous</a></li>
        <li><a href="#">1</a></li>
        <li><a href="#">2</a></li>
        <li class="splitter">&hellip;</li>
        <li><a href="#">6</a></li>
        <li class="current">7</li>
        <li><a href="#">8</a></li>
        <li><a href="#">9</a></li>
        <li class="splitter">&hellip;</li>
        <li><a href="#">14</a></li>
        <li><a href="#">15</a></li>
        <li class="next"><a href="#">Next &raquo;</a></li>
      </ul>
    </div> -->
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
<script type="text/javascript" src="${ctxPath}/jspage/connection/connection.js"></script>
</body>
</html>
