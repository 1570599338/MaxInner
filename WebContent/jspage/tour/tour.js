
$(function(){
	getdata();
	
	$('#editHiden').click(function(){
		$('#uploadImgBlock').hide();
	});
	
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
						url : ctxPath + encodeURI(encodeURI('/tour/selectTourList')),
						idField : 'pk_id',
						queryParams:{
							title:title
						},
						columns : [ 
						            [ 
						              	{ field : 'title', title : '活动标题', width : 80, sortable : true}, 
										//{ field : 'title_img',title : '图像',width : 10.0}, 
										{field : 'content',title : '摘要内容',width : 180},
										{field : 'tourTime',title : '日期',width : 80},
										{field : 'count',title : '图片数量',width : 80},
									] 
						          ]
			});
}


// 打开公告
function addAdDiv(){
	// 清空页面的内容
	resetForm();
	//增加操作打开DIV
	$("#addDialog").dialog({title: "添加摘要"});
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
		sayInfo("请输入<font color='blue'>摘要标题</font>！");
		return;
	}
	if(!message){
		sayInfo("请输入<font color='blue'>摘要内容</font>！");
		return;
	}
	var id = $('#id').val();
	//sayInfo("请输入<font color='blue'>摘要内容</font>！"+$('#id').val());
	//return;
	if(!id)
		$("#addForm").attr("action",ctxPath + "/tour/addAdTour");
	else
		$("#addForm").attr("action",ctxPath + "/tour/editAdTour");
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
				$("#dataForm").attr("action",$("#ctxPath").val() + '/tour/delTour');
				$("#dataForm").submit();
			}
		});
	}
}

// ***************编辑公告**************
function editNoticeDiv(){
	var menu = $("#dg").datagrid("getSelected");
	if (null == menu || menu == '') {
		sayInfo("请选择要编辑的项。");
		return;
	} else {
		$("#id").val(menu.pk_id);
		$('#add_title').val(menu.title);
		//$('#add_message').val(menu.content);
		editor.html('');
		editor.insertHtml(menu.content);
		$("#addDialog").dialog({title: "编辑摘要"});
		$("#addDialog").dialog('open');
	}
}

//打开上传图片
function upLoadPhoto(xx){
	var menu = $("#dg").datagrid("getSelected");
	if (null == menu || menu == '') {
		sayInfo("请选择要上传图片的活动事件。");
		return;
	}
	//增加操作打开DIV
	$("#addimg").dialog({title: "添加图片"});
	$("#addimg").dialog('open');
	
}

// 上传图片
function onSubmitImg(){
	var menu = $("#dg").datagrid("getSelected");
	if (null == menu || menu == '') {
		sayInfo("请选择要上传图片的活动事件。");
		return;
	} else {
		$("#tourId").val(menu.pk_id);
		 if(document.fileInfo.file_path.value.trim()==''){
		    	sayInfo("请选择要上传的照片！");
				return;
		   } else {
		        var postfix = document.fileInfo.file_path.value.trim().match(/^(.*)(\.)(.{1,8})$/)[3].toLowerCase(); //获得选择的上传文件的后缀名的正则表达式  
		        if (!(postfix != "png" || postfix != "jpg" || postfix != "jpeg")) {
		            window.alert("图片类型不对，请选择.png、.jpg、.jpeg格式图片上传!" + postfix);
		            return false;
		        }
		   } 
		   // 获取当前提交的内容
			var message = editorImg.html();
			$('#Img_message').val(message);
			if(!message){
				sayInfo("请输入<font color='blue'>照片描述</font>！");
				return;
			}
		   document.fileInfo.submit();
	}
}

String.prototype.trim = function()
{
    return this.replace(/(^\s*)|(\s*$)/g, "");
}


//取消操作，关闭DIV
function cancleDialogImg(){
	$("#addimg").dialog('close');
}

// 查看图片
function seePhoto(ctxPath){
	var menu = $("#dg").datagrid("getSelected");
	if (null == menu || menu == '') {
		sayInfo("请选择要上传图片的活动事件。");
		return;
	}else{
		$.ajax({
			url : ctxPath + '/tour/seePhoto?activeID=' + menu.pk_id,
			type : 'post',
			dataType : 'json',
			success : function(data) {
				if(data.length>0){
					var  tbody = "";
						var count = 3;
						for(i=0;i<data.length;i++){
							/*if((i>3&&i%4==0)||i==0){
								tbody = tbody + "<tr>";
								 count = 1;
							}*/
//*******************************************************************************
							if((i+count)%count==0){
								tbody = tbody + "<tr>";
								
							}
						
							tbody = tbody + "<td>";
						//***************************************************	
								var vartable = "<table> <tr>";
								vartable = vartable + "<td>";
									vartable = vartable + "<a javascript:void(0); onclick=editPhoto('"+data[i].pk_id+"')>";
										vartable = vartable + "<img src='"+ctxPath+"/"+data[i].imgPath+"'  height='90' width='160'/>";
									vartable = vartable + "</a>";
								vartable = vartable + "</td>";
								
									vartable = vartable + "<td>";
									vartable = vartable + "<input type='submit' name='submit' value='编辑' class='subimt' onclick=editPhoto('"+data[i].pk_id+"') style='cursor:pointer' />";
									vartable = vartable + "<br/><br/><br/>";
									vartable = vartable + "<input type='submit' name='submit' value='删除' class='subimt' onclick=deltePhoto('"+data[i].pk_id+"') style='cursor:pointer' />";
									vartable = vartable + "</td>";
								vartable = vartable + "</tr></table>";
						//***************************************************		
								
								tbody = tbody + vartable;
								tbody = tbody + "</td>";
								if((i+count)%count==(count-1)){
									
									tbody = tbody + "</tr>";
								}
							
//*******************************************************************************								
							/*if(count==1){
								tbody = tbody + "</tr>";
							}
							count ++;*/
						}
					//tbody = tbody + "</table>";
						$("#tbody").empty();
					// 查如到前台
					$("#tbody").append(tbody);
					//增加操作打开DIV
					$("#seeImg").dialog({title: "查看图片"});
					$("#seeImg").dialog('open');
					
				}else{
					sayInfo('该活动没有上传图片！');
				}
			}
		});
	}
	
}


// 编辑照片
function editPhoto( pkid){
	sayInfo("*******照片********"+pkid);
	return;
}


//编辑照片
function editPhoto( pkid){
	sayInfo("*******照片********"+pkid);
	return;
}

// 删除照片
function deltePhoto( pkid){
	//$("#tbody").empty();
	$('#uploadImgBlock').hide();
	$("#editimg").dialog({title: "查看图片"});
	$("#editimg").dialog('open');
	sayInfo("*******删除照片********"+pkid);
	return;
}


// editHiden
/*

function editHiden(){
	$('#uploadImgBlock').hide();
}

*/

























