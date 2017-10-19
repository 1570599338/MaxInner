function seeBookinfo(id){
	var ctxPath = $("#ctxPath").val();
	$("#pk_id").val(id);
	$("#upfileForm").attr("action",ctxPath + "/manage/seeManageOne");
	$("#upfileForm").submit();
}

//条件查询
function getdata(){
	var ctxPath = $("#ctxPath").val();
	var bookType = $("#bookType").combobox("getValue");
	var book = $("#book").val();
	var telphone = $("#telphone").val();
	$("#bookTypex").val(bookType);
	$("#bookx").val(book);
	
	$("#upfileForm").attr("action",ctxPath + "/manage/seeManage");
	$("#upfileForm").submit();
	
}