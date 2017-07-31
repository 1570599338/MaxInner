// 条件查询
function getdata(){
	var ctxPath = $("#ctxPath").val();
	var company = $("#company").combobox("getValue");
	var department = $("#department").combobox("getValue");
	var gender = $("#gender").combobox("getValue");
	var useName = $("#useName").val();
	var telphone = $("#telphone").val();
	$("#companyx").val(company);
	$("#departmentx").val(department);
	$("#genderx").val(gender);
	$("#useNamex").val(useName);
	$("#telphonex").val(telphone);
	
	$("#upfileForm").attr("action",ctxPath + "/connnect/toConnectPage");
	$("#upfileForm").submit();
	
}

// 会议室提示
function meet(){
	sayInfo("<font color='00AAC2'>IT部正在加班加点的在做该模块，稍后敬请期待。</font>");
}










