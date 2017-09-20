$(function(){
	var dateTime;
	getDate(dateTime);
	$(".mini").css("background","#bbffaa");
	
});

// 添加预定会议室
function addBookMeet(){
	emptyDiv();
	// 获取当前页面的时间，并写入添加日期
	var dateTime = $("#dateTime").text();
	$('#bookDate').datebox('setValue', dateTime);
	$("#addDialog").dialog({title: "预定会议室"});
	$("#addDialog").dialog('open');
}

// 取消按钮
function cancleBookMeet(){
	$("#addDialog").dialog('close');
}

// 确认提交按钮
function onSubmit(){
	//emptyDiv();
	var ctxPath = $("#ctxPath").val();
	// 选择会议室
	var meet = $("#meet").combo('getValue');
	if(!meet ||meet<=0){
		sayInfo("请选择会议室。");
		return;
	}
//	$('#dd').datebox('setValue', '6/1/2012');	// set datebox value
//	var v = $('#dd').datebox('getValue');	// get datebox value
	// 预定日期
	var bookDate = $('#bookDate').datebox('getValue');
	if(!bookDate){
		sayInfo("请选择会议预定日期。");
		return;
	}
	// 开始时间
	var startTime = $("#startTime").combo('getValue');
	if(!startTime ||startTime<=0){
		sayInfo("请选择会议预定开始时间。");
		return;
	}	
	
	// 结束时间
	var endTime = $("#endTime").combo('getValue');
	if(!endTime ||endTime<=0){
		sayInfo("请选择会议预定结束时间。");
		return;
	}
	var booker =$('#booker').val();
	if(!booker){
		sayInfo("请填写预订人。");
		return;
	}
	if(parseInt(startTime)>parseInt(endTime)){
		sayInfo("开始时间不能在结束时间之后。");
		return;
	}
	
	// 获取预订的记录值
	var bookMeetId = $("#bookMeetId").val();
	if(bookMeetId){// 判断已经有记录值了
		checkAndupdate(bookMeetId,meet,bookDate,startTime,endTime)
	}else {// 没有预定记录值的话就是添加造作
		checkAndAdd(meet,bookDate,startTime,endTime);
	}
}

/**
 * 验证并修改已经预定的会议室
 * @param pk_id
 * @param meet
 * @param bookDate
 * @param startTime
 * @param endTime
 */
function checkAndupdate(bookMeetId,meet,bookDate,startTime,endTime){
	var ctxPath = $("#ctxPath").val();
	$.ajax({
		url : ctxPath + '/meet/checkMeetUpdate',
		data :{
			"bookMeetId":bookMeetId,
			"meet":meet,
			"bookDate":bookDate,
			"startTime":startTime,
			"endTime":endTime,
		},
		type : 'post',
		dataType : 'json',
		//contentType : 'application/json;charset=utf-8',
		success : function(data) {// data:{meet:[{}],info:[{}]}
		if(data.mess>0){
			sayInfo("该会议室在"+startTime +"时 ~~ "+endTime+"时的时间段内已经有人预订了！");
			return;
			}else{
				$("#bookMeet").attr("action",ctxPath + "/meet/editbookMeet");
				$("#bookMeet").submit();
			}
		},
		error : function() {
			sayInfo("出错了。");
		}
	});
}


/**
 * 验证并提交预订
 * @param meet
 * @param bookDate
 * @param startTime
 * @param endTime
 */
function checkAndAdd(meet,bookDate,startTime,endTime){
	var ctxPath = $("#ctxPath").val();
	$.ajax({
		url : ctxPath + '/meet/checkMeet',
		data :{"meet":meet,
			"bookDate":bookDate,
			"startTime":startTime,
			"endTime":endTime,
		},
		type : 'post',
		dataType : 'json',
		//contentType : 'application/json;charset=utf-8',
		success : function(data) {// data:{meet:[{}],info:[{}]}
		if(data.mess>0){
			sayInfo("该会议室在"+startTime +"时 ~~ "+endTime+"时的时间段内已经有人预订了！");
			return;
			}else{
				$("#bookMeet").attr("action",ctxPath + "/meet/bookMeet");
				$("#bookMeet").submit();
			}
		},
		error : function() {
			sayInfo("出错了。");
		}
	});
}

// 获取页面大数据
function getDate(dateTime){
	var ctxPath = $("#ctxPath").val();
	$.ajax({
		url : ctxPath + '/meet/getMeetInfo',
		data :{"dateTimex":dateTime,},
		type : 'post',
		dataType : 'json',
		//contentType : 'application/json;charset=utf-8',
		success : function(data) {// data:{meet:[{}],info:[{}]}
			var meet =null;
			var meetInfo =null;
			if(data){
				meet = data.meet;
				meetInfo = data.meetinfo;
			}
			var tbody="";
			for(var i=9;i<18;i++){
				tbody = tbody + "<tr class='"+(i%2==1?"comment_odd":"comment_even")+"'>";// <tr class="xx">
					tbody = tbody + "<td height='40'> <center> <font color='#0081c2' > " +(i<10? "0"+i:i)+":00 </font> <br/><font color='#76c307' >|</font> <font color='#0F1719' >"+(i+1)+ ":00 </font> </center> </td>";								//<td> xx </td>
					for(var m=0;m<meet.length;m++){//**********************************
						if(meetInfo&&meetInfo.length>0){
							var flage =0;
							var count =0;
							var countx =0;
							for(var j=0;j<meetInfo.length;j++){
								
								//已经有预定记录了
								if(meetInfo[j].meetId==meet[m].pk_id){
										// 如果匹配到了判断时间节点 -- 只包含开始节点不包含结束节点-
									if(meetInfo[j].startTime-0.5<=i && meetInfo[j].endTime>i){
										// 结束半小时的节点之后的节点的判定
										if(meetInfo[j].endTime-i==0.5){
											var endflag = 0;
											// 判断该会议室的吓一条记录是不是也是半小时的
											for(var n=j+1;n<meetInfo.length;n++){
												if(meetInfo[n].meetId==meet[m].pk_id){
													if(i+1-meetInfo[n].startTime==0.5){
														tbody = tbody + "<td  onclick=updateBookMeet('"+meetInfo[j].pk_id+"') > " ;
														tbody = tbody + "<div class='updown'><div class='up'>"+	"预定人：<font color='#FFFFFF'>"+meetInfo[j].booker+"</font> "+"</div><div class='up'>"+	"预定人：<font color='#FFFFFF'>"+meetInfo[j+1].booker+"</font> "+"</div></div>";
														tbody = tbody +	"</td>";
														endflag=1;
														//m++;
														break;
													}
												}
											}
											if(endflag==0){
												tbody = tbody + "<td  onclick=updateBookMeet('"+meetInfo[j].pk_id+"') > " ;
												tbody = tbody + "<div class='updown'><div class='up'>"+	"预定人：<font color='#FFFFFF'>"+meetInfo[j].booker+"</font> "+"</div><div class='down'></div></div>";
												tbody = tbody +	"</td>";
												break;	
											}else{
												break;
											}
										}
										// 开始半小时的节点
										if(meetInfo[j].startTime-i==0.5){
											tbody = tbody + "<td onclick=updateBookMeet('"+meetInfo[j].pk_id+"') > " ;
											tbody = tbody + "<div class='updown'><div class='down'></div><div class='up'>"+	"预定人：<font color='#FFFFFF'>"+meetInfo[j].booker+"</font> "+"</div></div>";
											tbody = tbody +	"</td>";
											break;
										}
										// 完整1个时间的节点
										tbody = tbody + "<td class='mini' onclick=updateBookMeet('"+meetInfo[j].pk_id+"') > 预定人：<font color='#FFFFFF'>"+meetInfo[j].booker+"</font> <br/> 设&nbsp;&nbsp;&nbsp;备：";
										if(meetInfo[j].assist==0){
											tbody = tbody +"无"+"</td>";
										}else{
											tbody = tbody +meetInfo[j].assist+"</td>";
										}
									}else{
										count++;
										if(count==meetInfo.length)
											tbody = tbody + "<td></td>";
									}
								}else{
									countx++;
								}
							}
							if(countx==meetInfo.length-count&&countx!=0)
								tbody = tbody + "<td></td>";
							
						}else{// 没有预定记录的情况下
							tbody = tbody + "<td></td>";
						}
					}//***************************************************************
				tbody = tbody + "</tr>";
			}
			$("#dateTime").html(data.dateTime);
			$("#weekday").html(data.weekday);
			// 清空之前的数据
			$("#tbody").empty();
			// 插入到前台
			$("#tbody").append(tbody);
		}
	});
}


//会议室系统的修改
function updateBookMeet(pk_id){
	var ctxPath = $("#ctxPath").val();
	$('#sanfang').attr("checked",false);
	$('#dianhua').attr("checked",false);
	$('#touying').attr("checked",false);
	
	$.messager.confirm("提示","确定要修改已经预定的会议室?",function(data){
  		if(data){
  			$.ajax({
  				url : ctxPath + '/meet/getOnebookMeetInfo',
  				data :{"meetid":pk_id},
  				type : 'post',
  				dataType : 'json',
  				//contentType : 'application/json;charset=utf-8',
  				success : function(data) {// data:{meet:[{}],info:[{}]}
  					if(data){
  						
  						$("#addDialog").dialog({title: "修改会议室"});
  						$("#addDialog").dialog('open');
  						// 获取预订的会议室的ID
  						$('#bookMeetId').val(data.pk_id);
  						// 会议室
  						$('#meet').combobox('setValue', data.meetid);
  						// 预定日期
  						$('#bookDate').datebox('setValue', data.bootime);
  						// 开始时间startTime
  						$('#startTime').combobox('setValue', data.startTime);
  						// 结束时间 endTime
  						$('#endTime').combobox('setValue', data.endTime);
  						// 辅助设
  						if(data.bookassist!=0){
  							var tem = (data.bookassist).split(",");
  							//for (var i=0 ; i< tem.length ; i++){
  							    if(tem[0]==1)
  							    	$('#sanfang').attr("checked",'checked');
  							  if(tem[1]==2)
							    	$('#dianhua').attr("checked",'checked');
  							  if(tem[2]==3)
							    	$('#touying').attr("checked",'checked');
  							//}
  						}
  						
  						// $('#bookassist').combobox('setValue', data.bookassist);
  						// 预订人
  						$('#booker').val(data.booker);
  						// 备注
  						$('#remark').val(data.remark);
  						
  					}
  				}
  			});
  		}
  	});
	
}


// 后一天
function nextDay(){
	$('#searchData').datebox('setValue', '');
	var dateTime = $("#dateTime").text();
	dateTime = addDate(dateTime,1);
	//$("#dateTime").html(dateTime);
	// 获取数据
	getDate(dateTime);
	
}

// 前一天
function previousDay(){
	$('#searchData').datebox('setValue', '');
	var dateTime = $("#dateTime").text();
	dateTime = addDate(dateTime,-1);
	//$("#dateTime").html(dateTime);
	// 获取数据
	getDate(dateTime);
}

/**
 * 获取后一天的日期
 * @param dd  时间
 * @param dadd 天数
 * @returns {Date}
 */
 function addDate(date,days){
//	 var d=new Date(date);
	 var d = new Date(Date.parse(date.replace(/-/g,"/")));
	 d.setDate(d.getDate()+days);
	 var month=d.getMonth()+1;
	 var day = d.getDate();
	 if(month<10){
		 month = "0"+month;
		 }
	 if(day<10){
		 day = "0"+day;
		 }
	 var val = d.getFullYear()+"-"+month+"-"+day;
	 return val;
 } 

// 搜索功能
 function searchData(){
	 var dateTime = $('#searchData').datebox('getValue');
	 getDate(dateTime);
 }




function emptyDiv(){
	$('#bookMeetId').val("");
	// 会议室
	$('#meet').combobox('setValue', '0');
	// 预定日期
	$('#bookDate').datebox('setValue', "");
	// 开始时间startTime
	$('#startTime').combobox('setValue', '0');
	// 结束时间 endTime
	$('#endTime').combobox('setValue', '0');
	// 辅助设
	//$('#bookassist').combobox('setValue', '0');
	$('#sanfang').attr("checked",false);
	$('#dianhua').attr("checked",false);
	$('#touying').attr("checked",false);
	// 预订人
	$('#booker').val('');
	// 备注
	$('#remark').val('');
}

/**
 * 会议室修改
 */
function updateAdDiv(){
	$("#updateDialog").dialog({title: "预定会议室"});
	$("#updateDialog").dialog('open');
	var dateTime = $("#dateTime").text();
	$('#updatesearchData').datebox('setValue', dateTime);
	updategetdata();
}


//获取数据及查询
function updategetdata(){
	var ctxPath = $("#ctxPath").val();
	var deletesearchData  = $('#updatesearchData').datebox('getValue');
	var meetid  = $('#updatemeet').combo('getValue');
	 // 通过路径查询项目的数据列表
	$('#dgupdate').datagrid({
						rownumbers:true,
						singleSelect:true,
						autoRowHeight:false,
						resizable:true,
						pagination:true,
						fit:true,
						pageSize:10,
						pageList: [10,15,20],
						pageNumber:1,
						//url : ctxPath + encodeURI(encodeURI('/notice/selectNoticeList?title='+title)),
						url : ctxPath + encodeURI(encodeURI('/meet/getCanelMeetlist')),
						idField : 'pk_id',
						queryParams:{
							'deletesearchData':deletesearchData,
							'meetid':meetid
						},
						columns : [ 
						            [ 
						              	{ field : 'name', title : '会议室', width : 80, sortable : true}, 
										{ field : 'booker',title : '预定人',width : 40}, 
										{ field : 'startTime',title : '开始时间',width : 40}, 
										{ field : 'endTime',title : '结束时间',width : 40}, 
										{field : 'assist',title : '设备',width : 80},
										{field : 'bootime',title : '预定时间',width : 80}
									] 
						          ],
						          onClickRow: function(node){
						        	  var sel=$('#dgupdate').datagrid('getSelected');
							  	    	$.messager.confirm("提示","确定要修改<font color=red>"+sel.booker+"于"+sel.bootime+"预订的"+sel.name+"</font> 会议室?",function(data){
							  	    		if(data){
							  	    			$("#updateDialog").dialog('close');
							  	    			updateMeet();
							  	    		}
							  	    	});
						        	  
						  		}
			});
}


/**
 * 数据的反馈到页面
 */
function updateMeet() {
	var menu = $('#dgupdate').datagrid('getSelected');
	$('#sanfang').attr("checked",false);
	$('#dianhua').attr("checked",false);
	$('#touying').attr("checked",false);
	if (menu) {
		//var menu = $("#dg").datagrid("getSelected");
		if (null == menu || menu == '') {
			sayInfo("请选择要查看的文件选项。");
			return;
		} else {
		//	$("#updateDialog").dialog('close');
			
			$("#addDialog").dialog({title: "修改会议室"});
			$("#addDialog").dialog('open');
			// 将会议室的ID隐藏到form表单中
			$('#bookMeetId').val(menu.pk_id);
			// 会议室
			$('#meet').combobox('setValue', menu.meetid);
			// 预定日期
			$('#bookDate').datebox('setValue', menu.bootime);
			// 开始时间startTime
			$('#startTime').combobox('setValue', menu.startTime);
			// 结束时间 endTime
			$('#endTime').combobox('setValue', menu.endTime);
			// 辅助设
			//$('#bookassist').combobox('setValue', menu.bookassist);
			if(menu.bookassist!=0){
					var tem = (menu.bookassist).split(",");
					//for (var i=0 ; i< tem.length ; i++){
					    if(tem[0]==1)
					    	$('#sanfang').attr("checked",'checked');
					  if(tem[1]==2)
				    	$('#dianhua').attr("checked",'checked');
					  if(tem[2]==3)
				    	$('#touying').attr("checked",'checked');
					//}
				}
			// 预订人
			$('#booker').val(menu.booker);
			// 备注
			$('#remark').val(menu.remark);
			
			
			/*$("#pk_id").val(menu.pk_id);
			$("#deletForm").attr("action",ctxPath + '/meet/deleteMeet');
			$("#deletForm").submit();*/
		}
	} else {
		sayInfo('请先选择目标项目！');
	}
}



// 取消预订会议室
function delNoticeDiv(){
	$("#deleteDialog").dialog({title: "取消预订会议室"});
	$("#deleteDialog").dialog('open');
	// 获取日期
	var dateTime = $("#dateTime").text();
	$('#deletesearchData').datebox('setValue', dateTime);
	deletegetdata();
}


/**
 * 获取预订会议室的相关信息
 */
function deletegetdata(){
	var ctxPath = $("#ctxPath").val();
	var deletesearchData  = $('#deletesearchData').datebox('getValue');
	var meetid  = $('#deltemeet').combo('getValue');
	 // 通过路径查询项目的数据列表
	$('#dgdelete').datagrid({
						rownumbers:true,
						singleSelect:true,
						autoRowHeight:false,
						resizable:true,
						pagination:true,
						fit:true,
						pageSize:10,
						pageList: [10,15,20],
						pageNumber:1,
						//url : ctxPath + encodeURI(encodeURI('/notice/selectNoticeList?title='+title)),
						url : ctxPath + encodeURI(encodeURI('/meet/getCanelMeetlist')),
						idField : 'pk_id',
						queryParams:{
							'deletesearchData':deletesearchData,
							'meetid':meetid
						},
						columns : [ 
						            [ 
						              	{ field : 'name', title : '会议室', width : 80, sortable : true}, 
										{ field : 'booker',title : '预定人',width : 40}, 
										{ field : 'startTime',title : '开始时间',width : 40}, 
										{ field : 'endTime',title : '结束时间',width : 40}, 
										{field : 'assist',title : '设备',width : 80},
										{field : 'bootime',title : '预定时间',width : 80}
									] 
						          ],
						          onClickRow: function(node){
						        	  var sel=$('#dgdelete').datagrid('getSelected');
							  	    	$.messager.confirm("提示","确定要取消<font color=red>"+sel.booker+"于"+sel.bootime+"预订的"+sel.name+"</font> 会议室?",function(data){
							  	    		if(data){
							  	    			CanelMeet();
							  	    		}
							  	    	});
						        	  
						  		}
			});
}

function CanelMeet() {
	var node = $('#dgdelete').datagrid('getSelected');
	if (node) {
		var ctxPath = $("#ctxPath").val();
		//var menu = $("#dg").datagrid("getSelected");
		var menu = node;
		if (null == menu || menu == '') {
			sayInfo("请选择要查看的文件选项。");
			return;
		} else {
			$("#pk_id").val(menu.pk_id);
			$("#deletForm").attr("action",ctxPath + '/meet/deleteMeet');
			$("#deletForm").submit();
		}
	} else {
		sayInfo('请先选择目标项目！');
	}
}

// 打开预定会议室系统的到处数据的界面
function downNoticeDiv(ctxPath){
	$("#downDialog").dialog({title: "导出会议室预定数据"});
	$("#downDialog").dialog('open');
	/*$("#downBookMeet").attr("action",ctxPath + '/meet/downloadDataExcel');
	$("#downBookMeet").submit();*/
}

// 取消下载查询窗口
function downcancleBookMeet(){
	$("#downDialog").dialog('close');
}


// 确定提交查询书
function downonSubmit(){
	var ctxPath = $("#ctxPath").val();
	var bookDate = $('#downBookDate').datebox('getValue');
	// 开始时间
	var startTime = $("#downstartTime").combo('getValue');
/*	if(!startTime ||startTime<=0){
		sayInfo("请选择会议预定开始时间。");
		return;
	}	*/
	
	// 结束时间
	var endTime = $("#downendTime").combo('getValue');
	/*if(!endTime ||endTime<=0){
		sayInfo("请选择会议预定结束时间。");
		return;
	}*/
	if(parseInt(startTime)>parseInt(endTime)){
		sayInfo("开始时间不能在结束时间之后。");
		return;
	}
	
	if(!parseInt(startTime)>0 && parseInt(endTime)>0 && parseInt(startTime)>parseInt(endTime)){
		sayInfo("开始时间不能在结束时间之后。");
		return;
	}
	
	$("#downBookMeet").attr("action",ctxPath + '/meet/downloadDataExcel');
	$("#downBookMeet").submit();
}




