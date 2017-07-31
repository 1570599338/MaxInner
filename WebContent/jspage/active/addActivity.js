
// 保存
function addAdActivityDiv(){
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
		$("#addActive").attr("action",ctxPath + "/active/editSaveActivity");
		$("#addActive").submit();
	}else{
		$("#context").val(markupStr);
		$("#addActive").attr("action",ctxPath + "/active/addAdTour");
		$("#addActive").submit();
	}

}

// 返回
function back(){
	$('#summernote').summernote('destroy');
}



