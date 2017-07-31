<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@include file="/WEB-INF/jsp/inc/front.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>活动编辑</title>
 	<link href="${ctxPath }/js/sunmmernote/bootstrap.css" rel="stylesheet" />
	<script src="${ctxPath }/js/sunmmernote/jquery2.1.4.js"></script> 
	<script src="${ctxPath }/js/sunmmernote/bootstrap.js"></script>  
	<link href="${ctxPath }/js/sunmmernote/summernote.css" rel="stylesheet"/>
	<script src="${ctxPath }/js/sunmmernote/summernote.js"></script>
	<script src="${ctxPath }/js/sunmmernote/lang/summernote-zh-CN.js"></script>
	<script src="${ctxPath }/jspage/manageInfo/manage.js"></script> 
</head>
<body >

	<form id="addActive" action="" method="post">
		<input type="hidden" id="ctxPath" name="ctxPath" value="${ctxPath}" />
		<input type="hidden" id="pk_id" name="pk_id" value="${manage.pk_id}"/>
		<input type="hidden" id="context" name="context"/>
	<div style="margin-bottom: 25px;margin-top: 20px;margin-left: 320px">
		<b>标题1</b>：<input type="text" id="title" name="title"  style="width:440px;" value="${manage.manageTitle }" />
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