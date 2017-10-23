<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@include file="/WEB-INF/jsp/inc/inc.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>图书管理</title>
 	<link href="${ctxPath }/js/sunmmernote/bootstrap.css" rel="stylesheet" />
	<script src="${ctxPath }/js/sunmmernote/jquery2.1.4.js"></script> 
	<script src="${ctxPath }/js/sunmmernote/bootstrap.js"></script>  
	<link href="${ctxPath }/js/sunmmernote/summernote.css" rel="stylesheet"/>
	<script src="${ctxPath }/js/sunmmernote/summernote.js"></script>
	<script src="${ctxPath }/js/sunmmernote/lang/summernote-zh-CN.js"></script>
	<script src="${ctxPath }/jspage/manageInfo/manage.js"></script> 
	<script type="text/javascript">
	$("#bookType").combobox('select', '${manage.type}');
	<!-- $(function(){
			$("#bookType").combobox('select', '${manage.type}');
	});

			//var bookTypex = $("#bookType").combobox('getValue');
			// 保存
			function addManageInfoDiv(){
				var pk_id = $('#pk_id').val();
				var ctxPath = $("#ctxPath").val();
				var title = $('#title').val();
				var bookType = $('#bookType').val();
				var bookTypex = $("#bookType").combobox('getValue');
				var bookTypey = $("#bookType").combobox("getValue");
				//$("#bookTypeID").combobox('select', bookType);
				$("#bookTypeID").val(bookType);
				var markupStr = $('#summernote').summernote('code');
				if(!title){
					alert("请填写活动标题！");
					return false;
				}
				if(!markupStr){
					alert("请输入活动展示！");
					return false;
				}
				if(pk_id){
					$("#context").val(markupStr);
					$("#addActive").attr("action",ctxPath + "/manage/editSaveManage");
					$("#addActive").submit();
				}else{
					$("#context").val(markupStr);
					$("#addActive").attr("action",ctxPath + "/manage/addManageInfo");
					$("#addActive").submit();
				}

			} -->
	</script> 
</head>
<body >

	<form id="addActive" action="" method="post">
		<input type="hidden" id="ctxPath" name="ctxPath" value="${ctxPath}" />
		<input type="hidden" id="pk_id" name="pk_id" value="${manage.pk_id}"/>
		<input type="hidden" id="bookTypeID" name="bookTypeID"/>
		<input type="hidden" id="context" name="context"/>
	<div style="margin-bottom: 25px;margin-top: 20px;margin-left: 320px">
		<b>标题</b>：<input type="text" id="title" name="title"  style="width:440px;" value="${manage.manageTitle }" />
	</div>
	<div style="margin-bottom: 25px;margin-top: 20px;margin-left: 320px">
		<b>类型</b>：<input id="bookType" name="bookType" class="easyui-combobox" data-options="valueField:'pk_id',textField:'name',url:'${ctxPath}/manage/queryBookTypeList'" style="width:440px;" value="${manage.type }"/>
	</div>
	</form>
	<div style="width:625px;margin-left: 250px ">
		<div  id="summernote"><p>${manage.content}.</p></div>
	</div>
 	
	<script >
	 $(document).ready(function() {
		 $('#summernote').summernote({
	    		lang: 'zh-CN',
	    		  height: 400,                 // set editor height
	    		  minHeight: 400,             // set minimum height of editor
	    		  maxHeight: 400,             // set maximum height of editor
	    		  focus: true,                  // set focus to editable area after initializing summernote
	    		  lang : 'zh-CN',
	    		  toolbar: [   
    			            ['style', ['bold','italic', 'underline','clear','fontname','fontsize','color','strikethrough']],
    			            ['Layout',['ul','ol','paragraph','height']],
    			            ['Insert',['table','picture','link','video','hr']],
    			            ['Misc',['fullscreen', 'codeview','undo','redo','help']]
    			           ]
			}); 
		 
		/* 	$('#company').combobox({ 
				url:ctxPath + '/department/queryCompanyList',
			    editable:false, //不可编辑状态
			    cache: false,
			    panelHeight: 'auto',//自动高度适合
			    valueField:'pk_id',  
			    textField:'company'
			 }); */
	    });
	</script>
	
	<div id="tb" style="padding: 10px 5px 5px 10px; margin-left: 450px">
		<div style="margin-bottom: 5px;margin-top: 5px;">
			&nbsp;&nbsp;&nbsp;
			<a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="false" onclick="addManageInfoDiv()">保存</a>&nbsp;&nbsp; 
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	 		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="#" class="easyui-linkbutton" iconCls="icon-undo" plain="false" onclick="back('${ctxPath }')">返回</a>&nbsp;&nbsp; 
		</div>
	</div>
</body>
</html>