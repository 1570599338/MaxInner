/**
 * @author liuquan
 */
$(function() {
	getdata();
	var ctxPath = $("#ctxPath").val();
	$('#company').combobox({ 
		url:ctxPath + '/department/queryCompanyList',
	    editable:false, //不可编辑状态
	    cache: false,
	    panelHeight: 'auto',//自动高度适合
	    valueField:'pk_id',  
	    textField:'company',
	    
	    onSelect: function(record){
	    	_type_name.combobox({
	    		editable:false, //不可编辑状态
	    		panelHeight: 'auto',//自动高度适合
                disabled: false,
                url: ctxPath + '/department/queryDepartment?pk_id=' + record.pk_id,
                valueField:'code',  
        	    textField:'department',
            }).combobox('clear');
			}
       }); 
	var _type_name = $('#department').combobox({
		editable:false, //不可编辑状态
		panelHeight: 'auto',//自动高度适合
        disabled: true,
        valueField:'pk_id',  
	    textField:'department',
    });
});

//获取数据及查询
function getdata(){
	var ctxPath = $("#ctxPath").val();
	var log_name = $("#log_name").val();
	var user_name = $("#user_name").val();
	//标志位防止因为在查询过程中翻页对查询结果的影响
	var isforbid = $("#isforbid").combobox('getValue');
	 // 通过路径查询项目的数据列表
	$('#dg').datagrid(
			{rownumbers:true,
				singleSelect:true,
				autoRowHeight:false,
				resizable:true,
				pagination:true,
				fit:true,
				pageSize:20,
				pageNumber:1,
				//url : ctxPath + encodeURI(encodeURI('/staff/getStaffPage?log_name=' + log_name + "&user_name=" + user_name + "&isforbid=" + isforbid)),
				url : ctxPath + encodeURI(encodeURI('/staff/getStaffPage')),
				idField : 'pk_id',
				queryParams:{
					log_name:log_name,
					user_name:user_name,
					isforbid:isforbid
				},
				columns : [ [ {
					field : 'company',
					title : '公司名称',
					width : 80,
					sortable : true
				}, {
					field : 'department',
					title : '部门名称',
					width : 100,				
				}, {
					field : 'useName',
					title : '姓名',
					width : 80,
				}, {
					field : 'state',
					title : '状态',
					width : 80,
				},{
					field : 'gender',
					title : '性别',
					width : 50,
				},{
					field : 'celphone',
					title : '手机号码',
					width : 80,
				},{
					field : 'telphone',
					title : '固话号码',
					width : 80,
				},{
					field : 'email',
					title : '电子邮件',
					width : 80
				}
				] ]
			});
	 
}

//增加操作打开DIV
function addUserDiv(){
	$("#addDialog").dialog({title: "添加用户"});
	$("#addDialog").dialog('open');
}


// 编辑操作打开DIV
function editUserDiv(){
	var ctxPath = $("#ctxPath").val();
	var menu = $("#dg").datagrid("getSelected");
	if(null == menu || menu == ''){
		sayInfo("请选择要编辑的项目。");
		return;
	}else{
		$("#staffId").val(menu.pk_id);
		$("#company").combobox('setValue',menu.companyid);
	/*	var _type_name = $('#department').combobox({
			editable:false, //不可编辑状态
			panelHeight: 'auto',//自动高度适合
	        disabled: true,
	        valueField:'pk_id',  
		    textField:'department',
	    });*/
		$('#department').combobox({
    		editable:false, //不可编辑状态
    		panelHeight: 'auto',//自动高度适合
            disabled: false,
            url: ctxPath + '/department/queryDepartment?pk_id=' + menu.companyid,
            valueField:'code',  
    	    textField:'department',
        });
		$("#department").combobox('setValue',menu.departmentCode);
		$("#username").val(menu.useName);
		if("男" == menu.gender){
			$("#gender").combobox('setValue','1');
		}else{
			$("#gender").combobox('setValue','0');
		}
		$("#telphone").val(menu.telphone);
		$("#cellphone").val(menu.celphone);
		$("#email").val(menu.email);
		
		$("#addDialog").dialog({title: "编辑员工信息"});
		$("#addDialog").dialog('open');
	}
}



// 删除选中的用户
function deleteUser() {
	var menu = $("#dg").datagrid("getSelected");
	var ctxPath = $("#ctxPath").val();
	if (null == menu || menu == '') {
		sayInfo("请选择要删除的项。");
		return;
	} else {/*
		//通过Ajax查询该用户是否已经被用到别的表里面，如果有则提示不能被删除
		var exmsg = $.ajax({url:ctxPath+"/usermanage/ifExistOtherTable?userId="+menu.pk_id,async:false,cache:false});
		var msg = exmsg.responseText;
		if(msg!=null && msg!=''){
			sayInfo(msg);
			return;
		}else{
			$.messager.confirm('删除', '确定需要删除该项吗?', function(data) {
				if (data) {	
					$("#pk_id").val(menu.pk_id);
					$("#dataForm").attr("action",$("#ctxPath").val() + '/usermanage/deleUsers');
					$("#dataForm").submit();
				}
			});
		}
	*/
		$.messager.confirm('删除', '确定需要删除该员工信息吗?', function(data) {
			if (data) {	
				$("#pk_id").val(menu.pk_id);
				$("#dataForm").attr("action",$("#ctxPath").val() + '/staff/deleStaff');
				$("#dataForm").submit();
			}
		});
		
	}
}


// 提交增加的数据
function submitDialog(ctxPath){
	if($("#staffId").val()==null||""==$("#staffId").val()){
		if($("#addForm").form('validate')){
			/*var exameMsg=$.ajax({url:$('#ctxPath').val()+"/usermanage/checkAddUser?userId="+$("#userId").val()+"&user_name="+$("#add_user_name").val()+"&log_name="+$("#add_log_name").val()+"&email="+$("#add_email").val(),async:false,cache:false});
			var msg=exameMsg.responseText;
			if(msg!=null && msg!=''){
				sayInfo(msg);
				return;
			}*/
			$("#addForm").attr("action",ctxPath + "/staff/addStaff");
			$("#addForm").submit();
		}
	}else{
		if($("#addForm").form('validate')){
			/*var exameMsg=$.ajax({url:$('#ctxPath').val()+"/usermanage/checkAddUser?userId="+$("#userId").val()+"&user_name="+$("#add_user_name").val()+"&log_name="+$("#add_log_name").val()+"&email="+$("#add_email").val(),async:false,cache:false});
			var msg=exameMsg.responseText;
			if(msg!=null && msg!=''){
				sayInfo(msg);
				return;
			}*/
		$("#addForm").attr("action",ctxPath + "/staff/editStaff");
		$("#addForm").submit();
	}
 }
}


//是否队选中的用户进行解禁
function startStopDialog(){
	var menu = $("#dg").datagrid("getSelected");
	var ctxPath = $("#ctxPath").val();
	if (null == menu || menu == '') {
		sayInfo("请选项。");
		return;
	} else {
		if("<font color=blue>在职</font>"==menu.state){
			$.messager.confirm('员工离职', '请确定该员工离职？', function(data) {
				if (data) {
					$("#pk_id").val(menu.pk_id);
					$("#stat").val(menu.stat);
					$("#dataForm").attr("action",$("#ctxPath").val() + '/staff/forbidStaff');
					$("#dataForm").submit();
				}
			});
		}else{
			$.messager.confirm('员工在职', '请确定该员工在职?', function(data) {
				if (data) {
					$("#pk_id").val(menu.pk_id);
					$("#stat").val(menu.stat);
					$("#dataForm").attr('action',$("#ctxPath").val() + '/staff/forbidStaff');
					$("#dataForm").submit();
				}
			});
		}
	}
}


// 取消操作，关闭DIV
function cancleDialog(){
	resetForm();
	$("#addDialog").dialog('close');
}


// 清空增加DIV中的数据
function resetForm(){
	$("#userId").val('');
	$("#add_log_name").val('');
	$("#add_user_name").val('');
	$("#add_gender").val('');
	$("#add_company").val('');
	$("#add_postalcode").val('');
	$("#add_tel").val('');
	$("#add_cellphone").val('');
	$("#add_email").val('');
	$("#add_fax").val('');
}


//关闭上传
function closeUploadPanelStaff(){
	$("#uploadUserPanel").dialog("close");
}


function myformatter(date){
	var y = date.getFullYear();
	var m = date.getMonth()+1;
	var d = date.getDate();
	return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
}


function myparser(s){
	if (!s) return new Date();
	var ss = s.split('-');
	var y = parseInt(ss[0],10);
	var m = parseInt(ss[1],10);
	var d = parseInt(ss[2],10);
	if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
		return new Date(y,m-1,d);
	} else {
		return new Date();
	}
}


//上传用户信息
function uploadDialog() {
	var file = $("#uploadFile");
	file.replaceWith(file.clone());
	$("#uploadUserPanel").dialog('open');
}


// 上传用户数据
function confirmuploadStaff() {
	if ($.trim($('#uploadFile').val()) == '') {
		sayInfo('还没有选择上传文件！');
		return;
	} else {
		var filePath = $.trim($('#uploadFile').val());
		var postfix = filePath.match(/^(.*)(\.)(.{1,8})$/)[3].toLowerCase(); // 获得选择的上传文件的后缀名的正则表达式
		if (postfix != "xls") {
			sayInfo("文件类型不对，请选择.xls的文件!");
			return;
		}
	}
	$("#uploadUserPanel").dialog('close');
	var win = $.messager.progress({
		title : '上传用户信息',
		msg : '数据上传中...'
	});
	setTimeout(function() {
		$.messager.progress('close');
	}, 5000);
	document.uploadForm.submit();
}

// 关闭
function closeUploadUserPanel() {
	var file = $("#uploadFile");
	file.replaceWith(file.clone());
	$("#uploadUserPanel").dialog('close');
}

//关闭
function closeUploadPanelImg() {
	var file = $("#uploadImg");
	file.replaceWith(file.clone());
	$("#uploadStaffImgPanel").dialog('close');
}


//*************************************批量导入照片
//批量导入照片
function uploadImgDialog() {
	var file = $("#uploadImg");
	file.replaceWith(file.clone());
	$("#uploadStaffImgPanel").dialog('open');
}

//上传照片
function confirmuploadImg() {
	if ($.trim($('#uploadImg').val()) == '') {
		sayInfo('还没有选择上传文件！');
		return;
	} else {
		var filePath = $.trim($('#uploadImg').val());
		var postfix = filePath.match(/^(.*)(\.)(.{1,8})$/)[3].toLowerCase(); // 获得选择的上传文件的后缀名的正则表达式
		if (postfix != "zip") {
			sayInfo("文件类型不对，请选择.zip的文件!");
			return;
		}
	}
	$("#uploadStaffImgPanel").dialog('close');
	var win = $.messager.progress({
		title : '上传员工照片',
		msg : '数据上传中...'
	});
	setTimeout(function() {
		$.messager.progress('close');
	}, 5000);
	document.uploadImgForm.submit();
}


