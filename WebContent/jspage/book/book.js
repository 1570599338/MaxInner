function seeBookinfo(id){
	var ctxPath = $("#ctxPath").val();
	$("#pk_id").val(id);
	$("#upfileForm").attr("action",ctxPath + "/manage/seeManageOne");
	$("#upfileForm").submit();
}