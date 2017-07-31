$(function(){
	/*$('#summernote').summernote({
		lang: 'zh-CN',
		  height: 300,                 // set editor height
		  minHeight: null,             // set minimum height of editor
		  maxHeight: null,             // set maximum height of editor
		  focus: true                  // set focus to editable area after initializing summernote
		});  */
	getdata();
});

//获取数据及查询
function getdata(){
	var ctxPath = $("#ctxPath").val();
	var title  = $('#log_title').val();
	 // 通过路径查询项目的数据列表
	$('#dg').datagrid({
						rownumbers:true,
						singleSelect:true,
						autoRowHeight:false,
						resizable:true,
						pagination:true,
						fit:true,
						pageSize:20,
						pageNumber:1,
						//url : ctxPath + encodeURI(encodeURI('/notice/selectNoticeList?title='+title)),
						url : ctxPath + encodeURI(encodeURI('/manage/selectManageList')),
						idField : 'pk_id',
						queryParams:{
							title:title
						},
						columns : [ 
						            [ 
						              	{ field : 'manageTitle', title : '标题', width : 80, sortable : true}, 
										{field : 'createBy',title : '创建者',width : 80},
										{field : 'time',title : '日期',width : 80}
										
									] 
						          ]
			});
}


// 天机文章
function addAdActivityDiv(){
	var ctxPath = $("#ctxPath").val();
	$("#dataForm").attr("action",ctxPath + "/manage/manage");
	$("#dataForm").submit();
	
}

 // 编辑活动展示
function editActivityDiv(){
	var ctxPath = $("#ctxPath").val();
	var menu = $("#dg").datagrid("getSelected");
	if(null == menu || menu == ''){
		sayInfo("请选择要编辑的信息。");
		return;
	}else{
		$("#pk_id").val(menu.pk_id);
		$("#dataForm").attr("action",ctxPath + "/manage/editManage");
		$("#dataForm").submit();
	}
	
}

//上传封面
function upLoadPhoto(xx){
	var menu = $("#dg").datagrid("getSelected");
	if (null == menu || menu == '') {
		sayInfo("请选择要上传活动封面照事件。");
		return;
	}
	//增加操作打开DIV
	$("#addimg").dialog({title: "上传封面"});
	$("#addimg").dialog('open');
	
}


function deleteActivity(){
	var ctxPath = $("#ctxPath").val();
	var menu = $("#dg").datagrid("getSelected");
	if(null == menu || menu == ''){
		sayInfo("请选择要编辑的活动。");
		return;
	}else{
		$.messager.confirm("提示","删除操作不可恢复，您确定要删除该活动展示吗？",function(data){
			if(data){
			$("#pk_id").val(menu.pk_id);
			$("#dataForm").attr("action",ctxPath + "/manage/deleteManage");
			$("#dataForm").submit();
			}
		});
	}
}



//取消操作，关闭DIV
function cancleDialogImg(){
	$("#addimg").dialog('close');
}



