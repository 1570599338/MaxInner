
$(function(){
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
						url : ctxPath + encodeURI(encodeURI('/notice/selectNoticeList')),
						idField : 'pk_id',
						queryParams:{
							title:title
						},
						columns : [ 
						            [ 
						              	{ field : 'title', title : '标题', width : 80, sortable : true}, 
										{ field : 'title_img',title : '图像',width : 10.0}, 
										{field : 'content',title : '公告内容',width : 180},
										{field : 'noticeTimex',title : '公告日期',width : 80}
									] 
						          ]
			});
}


// 打开公告
function addAdDiv(){
	// 清空页面的内容
	resetForm();
	//增加操作打开DIV
	$("#addDialog").dialog({title: "添加公告"});
	$("#addDialog").dialog('open');
	
}
// 提交公告信息
function submitDialog(ctxPath){
	// 获取当前提交的title
	var title = $('#add_title').val();
	// 获取当前提交的内容
	var message = editor.html();
	$('#add_message').val(message);
	
	if(!title){
		sayInfo("请输入<font color='blue'>公告标题</font>！");
		return;
	}
	if(!message){
		sayInfo("请输入<font color='blue'>公告内容</font>！");
		return;
	}
	var id = $('#id').val();
	sayInfo("请输入<font color='blue'>公告内容</font>！"+$('#id').val());
	//return;
	if(!id)
		$("#addForm").attr("action",ctxPath + "/notice/addAdNotice");
	else
		$("#addForm").attr("action",ctxPath + "/notice/editAdNotice");
	$("#addForm").submit();
}

//取消操作，关闭DIV
function cancleDialog(){
	resetForm();
	$("#addDialog").dialog('close');
}


// 清空增加DIV中的数据
function resetForm(){
	$("#add_title").val('');
	editor.html('');
	$("#add_message").val('');
	$("#id").val('');
}


// 删除公告
function delNoticeDiv(ctxPath){
	var menu = $("#dg").datagrid("getSelected");
	if (null == menu || menu == '') {
		sayInfo("请选择要删除的项。");
		return;
	} else {
		/*sayInfo("请选择要删除的项。"+menu.pk_id);
		return;*/
		$.messager.confirm('删除', '确定需要删除该项吗?', function(data) {
			if (data) {	
				$("#pk_id").val(menu.pk_id);
				$("#dataForm").attr("action",$("#ctxPath").val() + '/notice/delNotice');
				$("#dataForm").submit();
			}
		});
	}
}

// ***************编辑公告**************
function editNoticeDiv(){
	$("#noticeDialog").dialog({title: "编辑公告"});
	$("#noticeDialog").dialog('open');
	
	var menu = $("#dg").datagrid("getSelected");
	if (null == menu || menu == '') {
		sayInfo("请选择要编辑的项。");
		return;
	} else {
		$("#id").val(menu.pk_id);
		var title =menu.title;
		title = title.replace(/&nbsp;/g,'');
		$('#add_title').val(title);
		//$('#add_message').val(menu.content);
		editor.html('');
		editor.insertHtml(menu.content);
		$("#addDialog").dialog({title: "编辑公告"});
		$("#addDialog").dialog('open');
	}
}



/**
 * 设置new图标
 * @param ctxPath
 */
function addFlageNew(ctxPath){
	var menu = $("#dg").datagrid("getSelected");
	if (null == menu || menu == '') {
		sayInfo("请选择要设置的选项。");
		return;
	} else {
		/*sayInfo("请选择要删除的项。"+menu.pk_id);
		return;*/
		$.messager.confirm('删除', '确定需要设置该项吗?', function(data) {
			if (data) {	
				$("#pk_id").val(menu.pk_id);
				$("#dataForm").attr("action",$("#ctxPath").val() + '/notice/addFlageNew');
				$("#dataForm").submit();
			}
		});
	}
}


/**
 * 设置new图标
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
				$("#dataForm").attr("action",$("#ctxPath").val() + '/notice/delFlageNew');
				$("#dataForm").submit();
			}
		});
	}
}

























