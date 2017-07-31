
// 保存
function addManageInfoDiv(){
	var pk_id = $('#pk_id').val();
	var ctxPath = $("#ctxPath").val();
	var title = $('#title').val();
	var markupStr = $('#summernote').summernote('code');
	if(!title){
		alert("请填写活动标题！");
		return false;
	}
	if(!markupStr){
		alert("请输入活动展示！");
		return false;
	}
	if(pk_id){
		$("#context").val(markupStr);
		$("#addActive").attr("action",ctxPath + "/manage/editSaveManage");
		$("#addActive").submit();
	}else{
		$("#context").val(markupStr);
		$("#addActive").attr("action",ctxPath + "/manage/addManageInfo");
		$("#addActive").submit();
	}

}

// 返回
function back(){
	$('#summernote').summernote('destroy');
}



