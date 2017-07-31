// 活动展示
function getActive(activeId){
	var ctxPath = $('#ctxPath').val();
	/*$('#activeID').val(activeId);
	$("#activeForm").attr("action",ctxPath + '/home/activeDetaile');
	$("#activeForm").submit();*/
	$('#activeID').val(activeId);
	$("#activeForm").attr("action",ctxPath + '/active/seeActivity');
	$("#activeForm").submit();
}


// 获取系统通知
function getNotice(ctxPath,pk_id){
	$('#notice_title').html("");
	$('#notice_content').html("");
	$.ajax({
		url : ctxPath+'/notice/getOneNotice',
		data : {"pk_id":pk_id},
		type : 'post',
		dataType : 'json',
		success : function(data) {
			if(data){
				$("#id").val(pk_id);
				var title = "<font size='40px'><b>" + data.title +"</b></font><br/>"+data.noticeTime+"<br/><br/><hr width=80% size=1 color=#a6a6a7 style='border:1 dashed #5151A2'/>";
				$('#notice_title').html(title);
				$('#notice_content').html(data.content);
				$("#noticeDialog").dialog({title: "显示公告信息"});
				$("#noticeDialog").dialog('open');
			}else{
				sayInfo("对不起，暂时未找到对应的通知");
				return;
			}
		},
		error : function() {
			sayInfo("出错了。");
		}
	}); 
}

// 关闭弹窗
function noticeClose(){
	$("#noticeDialog").dialog('close');
}

// 下载规章制度
function downFile(ctxPath,fileID){
	$("#fileName").val(fileName);
	$("#fileForm").attr("action",ctxPath + '/home/down');
	$("#fileForm").submit();
}

// 在浏览器上直接查看pdf格式数据
function viewPdf(ctxPath,fileID){
	$("#fileName").val(fileID);
	$("#fileForm").attr("action",ctxPath + '/home/viewRuleFile');
	$("#fileForm").submit();
}

// 显示隐藏的规章制度的数据
function getlistRule(ctxPath,type){
	$("#fileType").val(type);
	$("#fileForm").attr("action",ctxPath + '/home/toRuleBase');
	$("#fileForm").submit();
	
}

// 显示数据的样式
function getlistRuleX(ctxPath,type){
	
}

// 下载专区
function downPage(ctxPath,type,title){
	$("#typeId").val(type);
	$("#title").val(title);
	$("#type").val("2");
	$("#downfileForm").attr("action",ctxPath + '/home/todownFilePage');
	$("#downfileForm").submit();
}


function downPageHead(ctxPath,modelId,modeltitle){
	$("#modelId").val(modelId);
	$("#modelTitle").val(modeltitle);
	$("#type").val("1");
	$("#downfileForm").attr("action",ctxPath + '/home/todownFilePage');
	$("#downfileForm").submit();
}


//会议室提示
function meet(){
	sayInfo("<font color='00AAC2'>IT部正在加班加点的在做该模块，稍后敬请期待。</font>");
}


