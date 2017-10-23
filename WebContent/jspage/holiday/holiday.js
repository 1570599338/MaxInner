/**
 * @author liuquan
 */
$(function() {
	getdata();
});

//获取数据及查询
function getdata(){
	var ctxPath = $("#ctxPath").val();
	// 员工姓名
	var username = $("#username").val();
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
				url : ctxPath + encodeURI(encodeURI('/holiday/getVacationlist')),
				idField : 'pk_id',
				queryParams:{
					username:username
				},
				columns : [ [ {
					field : 'userName',
					title : '员工姓名',
					width : 80,
					sortable : true
				}, {
					field : 'baseDay',
					title : '累计年假天数',
					width : 100,
					sortable : true
				}, {
					field : 'spendDay',
					title : '本月请假天数',
					width : 80,
					sortable : true
				}, {
					field : 'restDay',
					title : '剩年假天数',
					width : 80,
					sortable : true
				},{
					field : 'month',
					title : '截止月份',
					width : 50,
					sortable : true
				},{
					field : 'createBy',
					title : '执行',
					width : 50,
					sortable : true
				},{
					field : 'createAt',
					title : '日期',
					width : 80,
				}
				] ]
			});
	 
}

// 上传规章制度
function updateAdDiv(){
	// 清空数据
	restFrom();
	$("#addDialog").dialog({title: "导入年假"});
	$("#addDialog").dialog('open');
	
}

// 上传规章制度的文件
function onSubmitFile(){
	var ctxPath = $("#ctxPath").val();
	// 上传问价的类型
	var type = $("#mouth").combobox("getValue");
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
	if (postfix == "xls") {
		$("#upfileForm").attr("action",ctxPath + "/holiday/uploadVacation");
		$("#upfileForm").submit();
	}else{
    	sayInfo("上传文件类型不对，请选择.xls格式图片上传!" + postfix);
        return false;
    }
	
}
//清空之前的数据
function restFrom(){
	$("#upfileType").combobox('setValue','0');
	$("#file_path").val("");
}











