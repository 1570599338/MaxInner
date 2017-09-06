<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%-- <%@include file="/WEB-INF/jsp/inc/front.jsp"%> --%>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<html>
<head>
<title>活动展示</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@include file="/WEB-INF/jsp/inc/inc.jsp"%>
<link rel="stylesheet" href="${ctxPath}/css/front/css/meet.css" type="text/css" />
<link rel="stylesheet" href="${ctxPath}/css/front/css/detail.css" type="text/css"/>
<link rel="stylesheet" href="${ctxPath}/css/front/css/detail-p.css" type="text/css"/>
 <script type="text/javascript" src="${ctxPath }/jspage/home/meet_base.js"></script>
<style type="text/css">
	thead{
		font-family:Arial, Helvetica, sans-serif;
		font-size:16px;
		text-align:center;
		 font-weight:900;
		  color:#FFF;
		
		}
		.comment_odd{
			background-color:#BEBEBE;
		}
		.comment_even{
			background-color:#BEBEBE;
		}
		.mini{
			background-color:#2e8764;
		}
		.updown{
			width:206px;
			height:51px;
		}
		.up{
			width:206px;
			height:25px;
			background-color:#2e8764;
			}
		.down{
			width:206px;
			height:26px;
			
		}
		
		.tablecolor-bottom{
			border-collapse:separate;
			padding:10px;
			border-bottom :30px solid  #E0E0E0;
		}
		.tablecolor-up{
			border-collapse:separate;
			padding:20px;
			border-top :10px solid  #A6A6A7;
		}
</style>

</head>
<body id="top">
<input type="hidden" name="ctxPath" id="ctxPath" value="${ctxPath}" />
<div class="wrapper col1">
  <div id="header" class="clear">
    <div class="fl_left">
      <h1><a href="http://www.maxinsight.cn"><img src="${ctxPath}/img/front/images/logo.png"/></a></h1>
      
    </div>
    <div class="fl_right">
      
      <ul id="topnav" class="clear">
      <li><a href="${ctxPath}/manage/seeManage">经营管理</a></li>
        <li class="active last"><a href="${ctxPath}/meet/toHomepage">会议室</a></li>
        <li><a href="#">快速查找</a>
          <ul>
            <li><a href="${ctxPath}/home/toHome#hpage_info">最新公告</a></li>
            <li><a href="${ctxPath}/home/toHome#rules">规章制度</a></li>
            <li><a href="${ctxPath}/home/toHome#download">下载专区</a></li>            
            <li><a href="${ctxPath}/home/toHome#foucs">关注我们</a></li>
          </ul>
        </li>
        <li>
        	<a href="#">内部链接</a>
             <ul>
             	<li><a href="http://192.168.1.237:8088/logon.aspx" target="_blank">财务平台</a></li>
                <li><a href="http://maxinsight.21tb.com" target="_blank">培训平台</a></li>
                <li><a href="http://192.168.1.246/?#" target="_blank">渠道管理平台</a></li> 
             </ul>       	
        </li>        
        <li><a href="${ctxPath}/connnect/toConnectPage">通讯录</a></li>
        <li><a href="${ctxPath}/home/toHome">首页</a></li>
      </ul>
    </div>
  </div>
</div>
<!-- ##############################################header结束######################################################### -->
<div class="wrapper col2">	
	<div id="detail">    	  
    	 <div class="detail_content clear">
         <p><a href="${ctxPath}/home/toHome">首页</a>&nbsp;&gt;&nbsp;<a href="${ctxPath}/home/toHome">会议室</a>&nbsp;&gt;&nbsp;</p>
           <!-- **********************Start 数据**************************** --> 
   
		   	<div id="tb" style="padding: 10px 5px 5px 50px; height: auto;">
				<div>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					日期 ：<input class="easyui-datebox" style="width: 150px" id="searchData" name="searchData">&nbsp;&nbsp;&nbsp; 
					&nbsp;&nbsp;&nbsp;
					<!-- 内&nbsp;&nbsp;&nbsp; 容：<input class="input_text" type="text" style="width: 180px" id="user_name" name="user_name" />&nbsp;&nbsp;&nbsp; -->
					<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="searchData()">&nbsp;查&nbsp;询&nbsp;</a>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</div>
			</div>         
		    <div class="container">
			  <div class="content">
				  <div class="meet_table" align="center"   width="550px"  >
					  <div  style="padding-bottom:5px">
					  	<table width="640px" border="0" >
					  		<tr class="comment_odd" height="25">
					  			<td><div align="left" style="font-weight:900;" ><a href="javascript:void(0);"  onclick="previousDay()" ><font color="#000">&nbsp;&nbsp;&lt;&lt; 前一天&nbsp;&nbsp;</font></a> </div></td>
					  			<td><div align="center" ><span id="dateTime"></span>&nbsp;&nbsp;<span id="weekday">111</span></div></td>
					  			<td><div align="right"  style="font-weight:900;" > <a href="javascript:void(0);"   onclick="nextDay()" > <font color="#000">&nbsp;&nbsp;后一天 &gt;&gt;&nbsp;&nbsp; </font></a> </div></td>
					  		</tr>
					  		<tr></tr>
					  	</table>
					   </div>
						   <div>
							    <table class="content"   border="1" bordercolor="#cccccc" style="border-collapse:collapse;">
								       <thead>
								       	  <tr bgcolor="#0f1719" rowspan=2>
								       	  	<td  colspan="2">名称</td>
								       	  	<td colspan="3">上海会议室</td>
								       	  </tr> 
									      <tr bgcolor="#0f1719">
									        <td  colspan="2" height="35" width="29">时间</td>
									        <td width="203">斯图加特</td>
									        <td width="203">伯明翰</td>
									        <td width="203">底特律</td>
									       </tr>
								       </thead>
								       
								       <tbody id="tbody" name="tbody">  </tbody>
							      </table>
						</div>
				
			    </div>
			   
			  </div>
			</div>
            
            
            <!-- **********************END 数据**************************** -->            
            
            <div class="fy">
            	<!-- <div class="fy_bottom"><span>上一篇：</span><a href="#">未定</a></div>
                <div class="fy_bottom"><span>下一篇：</span><a href="#">IT制度</a></div> -->
            </div>
      	</div>
     <%--  <div class="classify">
            <div class="cf_title">
                <h3>相关分类</h3>
            </div>
            <div class="cf_detail">
                <ul class="xl xl2 cl">
                    <li><a href="${ctxPath}/home/noticePage">最新公告</a></li>
                    <li><a href="${ctxPath}/home/toHome#download">工具下载</a></li>
                    <li><a href="${ctxPath}/home/toHome">活动展示</a></li>
                    <!-- <li><a href="templet/list.html">模板下载</a></li> -->
                    <li><a href="${ctxPath}/connnect/toConnectPage">通讯录</a></li>
                    <li><a href="${ctxPath}/home/toHome#rules">规章制度</a></li>
                    
                </ul>
            </div>
      </div> --%>
    </div>    
</div>
<!-- ###########################################################链接结束############################################ -->
<div class="wrapper">
  <div id="copyright" class="clear">
    <p class="fl_left">Copyright &copy; 2016 - All Rights Reserved - <a href="#">MaxInsight</a></p>
   
  </div>
</div>
<script type="text/javascript" >

//缩放图片到合适大小
function ResizeImages()
{
   var myimg,oldwidth,oldheight;
  /*  var maxwidth=550;
   var maxheight=880 */
   var maxwidth=640;
   var maxheight=1200;
   var imgs = document.getElementById('MaxActionarticle').getElementsByTagName('img');   //如果你定义的id不是article，请修改此处

   for(i=0;i<imgs.length;i++){
     myimg = imgs[i];
	// 
     if(myimg.width > myimg.height){
         if(myimg.width > maxwidth){
			  oldwidth = myimg.width;
			  myimg.height = myimg.height * (maxwidth/oldwidth);
			  myimg.width = maxwidth;
         }
     }else{
         if(myimg.height > maxheight){
			  oldheight = myimg.height;
			  myimg.width = myimg.width * (maxheight/oldheight);
			  myimg.height = maxheight;
         }
     }
   }
}
//缩放图片到合适大小
//ResizeImages();
</script>

</body>
</html>