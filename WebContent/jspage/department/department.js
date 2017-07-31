
// 添加公司信息
function addCompany(){
	// 清空数据
	$("#model_Id").val("");
	$("#company").val("");
	$("#addComanyDialog").dialog({title: "添加公司信息"});
	$("#addComanyDialog").dialog('open');
}

/**
 * 编辑公司的信息
 */
function editCompany(){
	var p = $('#dataList').accordion('getSelected'); 
	if (p){
		var companyId = p.panel('options').id;
		$("#companyId").val(companyId);
		var title = p.panel('options').title;
		$("#company").val(title);
		$("#addComanyDialog").dialog({title: "编辑公司信息"});
		$("#addComanyDialog").dialog('open');
	}else{
		sayInfo("请选择公司的panel！");
	}
}

/**
 * 删除公司信息
 */
function delCompany(){
	var ctxPath = $("#ctxPath").val();
	var p = $('#dataList').accordion('getSelected'); 
	if (p){
		var pk_id = p.panel('options').id;
		$.ajax({
			url : ctxPath+'/department/queryDepartment',
			data : {"pk_id":pk_id},
			type : 'post',
			dataType : 'json',
			success : function(data) {
				if(data.length>0){
					sayInfo("对不起，该公司下面还有部门，请清空对应的对应部门在删除该公司！");
					return;
				}else{
					var pk_id = p.panel('options').id;
					$("#pk_id").val(pk_id);
					$("#deleteForm").attr("action",ctxPath + "/department/delComany");
					$("#deleteForm").submit();
				}
			},
			error : function() {
				sayInfo("出错了。");
			}
		}); 
	}else{
		sayInfo("请选择文件模板！");
	}
}


//提交公司信息
function submitCompanyDialog(){
	var company = $("#company").val();
	var companyId = $("#companyId").val();
	var url ="";
	if(companyId){
		// 修改文件的模板
		url = $("#ctxPath").val() + '/department/editCompany';
	}else{
		// 添加文件的模板
		url = $("#ctxPath").val() + '/department/addCompany';
	}
	if(!company){
		sayInfo("请填写公司信息！");
		return false;
	}
	$("#addCompanyForm").attr("action",url);
	$("#addCompanyForm").submit();
}

// 关闭弹窗
function cancleCompanyDialog(){
	$("#addComanyDialog").dialog('close');
}

//添加部门
function addDepartment(){
	var ctxPath = $("#ctxPath").val();
	// 清空编辑标志位
	$("#flage").val("");
	//restFrom();
	$('#companyName').combobox({  
	    url:ctxPath + '/department/queryCompanyList',
	    panelHeight: 'auto',//自动高度适合
	    editable:false,
	    valueField:'pk_id',  
	    textField:'company',
	});
	$("#addDepartmentDialog").dialog({title: "添加部门"});
	$("#addDepartmentDialog").dialog('open');
}



//添加文件的上传类型
function submitTypeDialog(ctxPath){
	// 标志位 判断是执行添加操作还是编辑操作
	var flage = $("#flage").val();
	var url = "";
	if(""==flage){
		url =ctxPath + '/department/addDepartment';
	}else{
		url = ctxPath + '/department/editDepartment';
	}
		var companyName = $("#companyName").combobox('getValue');
		var departMent = $("#departMent").val();
		
		if(!companyName){
			sayInfo("请选择公司名称！");
			return false;
		}
		if(!departMent){
			sayInfo("请填写部门名称！");
			return false;
		}
		$("#addDepartmentForm").attr("action",url);
		$("#addDepartmentForm").submit();
}


//取消按钮
function cancleDepartmentDialog(){
	$("#addDepartmentDialog").dialog('close');
}


/**
 * 编辑部门信息
 * @param ctxPath
 * @param company
 * @param pk_id
 * @param department
 * @param code
 */
function editDepartment(ctxPath,company,pk_id,department,code){
	$("#flage").val("editTypeId");
	$("#departMentId").val(pk_id);
	$("#companyName").combobox('setValue',company);
	$("#departMent").val(department);
	// 控制部门代码不可编辑
	$('#departCode').attr("disabled","false");
	$("#departCode").val(code);
	$("#addDepartmentDialog").dialog({title: "编辑部门信息"});
	$("#addDepartmentDialog").dialog('open');
}

/**
 * 删除部门信息
 * @param ctxPath
 * @param pk_id
 */
function deleDepartment(ctxPath,pk_id){
	$.messager.confirm("提示","删除操作不可恢复，您确定要删除该部门吗？",function(data){
		if(data){
			$("#departMentId").val(pk_id);
			$("#addDepartmentForm").attr("action",ctxPath + "/department/delDepartment");
			$("#addDepartmentForm").submit();
		}
	});
}










