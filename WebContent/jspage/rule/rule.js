
$(function(){
	getdata();
});

//获取数据及查询
function getdata(){
	var ctxPath = $("#ctxPath").val();
	var fileName  = $('#log_title').val();
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
						url : ctxPath + encodeURI(encodeURI('/rule/selectRuleList')),
						idField : 'pk_id',
						queryParams:{
							fileName:fileName
						},
						columns : [ 
						            [ 
						             	{ field : 'fileName',title : '文件名',width : 100}, 
						              	{ field : 'type', title : '类型', width : 80, sortable : true}, 		
										{field : 'ruleTime',title : '时间',width : 180},
										{field : 'createBy',title : '上传者',width : 80}
									] 
						          ]
			});
}

// 上传规章制度
function updateAdDiv(){
	// 清空数据
	restFrom();
	$("#addDialog").dialog({title: "上传规章制度"});
	$("#addDialog").dialog('open');
	
}

// 取消上传的按钮
function cancleDialogFile(){
	$("#addDialog").dialog('close');
}

// 上传规章制度的文件
function onSubmitFile(){
	var ctxPath = $("#ctxPath").val();
	// 上传问价的类型
	var type = $("#upfileType").combobox("getValue");
	// 文件及其路径
	var file = $('#file_path').val();
	if(type==0){
		sayInfo("请选择上文件的类型！");
		return false;
	}
	if($.trim(file)==''){
		sayInfo("请选择要上传的文件");
		return false;
	}
	var postfixxx = document.upfileForm.file_path.value;
	var postfixx = document.upfileForm.file_path.value.trim().match(/^(.*)(\.)(.{1,8})$/);
	var postfix = document.upfileForm.file_path.value.trim().match(/^(.*)(\.)(.{1,8})$/)[3].toLowerCase(); //获得选择的上传文件的后缀名的正则表达式
	if ((postfix == "ZIP" || postfix == "zip"|| postfix == "pdf" )) {
		$("#upfileForm").attr("action",ctxPath + "/rule/uploadFile");
		$("#upfileForm").submit();
	}else{
    	sayInfo("上传文件类型不对，请选择.pdf或.zip格式图片上传!" + postfix);
        return false;
    }
	
}


// 清空之前的数据
function restFrom(){
	$("#upfileType").combobox('setValue','0');
	$("#file_path").val("");
}


// 删除对应的规章制度
function delNoticeDiv(ctxPath){
	var menu = $("#dg").datagrid("getSelected");
	if (null == menu || menu == '') {
		sayInfo("请选择要删除的项。");
		return;
	} else {
		$("#pk_id").val(menu.pk_id);
		$("#dataForm").attr("action",ctxPath + "/rule/deleteFile");
		$("#dataForm").submit();
	}
}



/**
 * 添加new图标
 * @param ctxPath
 */
function addFlageNew(ctxPath){
	var menu = $("#dg").datagrid("getSelected");
	if (null == menu || menu == '') {
		sayInfo("请选择要删除的项。");
		return;
	} else {
		$.messager.confirm('删除', '确定需要设置该项吗?', function(data) {
			if (data) {	
				$("#pk_id").val(menu.pk_id);
				$("#dataForm").attr("action",ctxPath + "/rule/addFlageNew");
				$("#dataForm").submit();
			}});
	}
}

/**
 * 删除new图标
 * @param ctxPath
 */
function delFlageNew(ctxPath){
	var menu = $("#dg").datagrid("getSelected");
	if (null == menu || menu == '') {
		sayInfo("请选择要删除new图标的选项。");
		return;
	} else {
		/*sayInfo("请选择要删除的项。"+menu.pk_id);
		return;*/
		$.messager.confirm('删除', '确定需要删除new图标该项吗?', function(data) {
			if (data) {	
				$("#pk_id").val(menu.pk_id);
				$("#dataForm").attr("action",$("#ctxPath").val() + '/rule/delFlageNew');
				$("#dataForm").submit();
			}
		});
	}
}






