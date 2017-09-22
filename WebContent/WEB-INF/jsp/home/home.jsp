<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/inc/front.jsp"%>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<html>
<head>
<title>内部信息网</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="imagetoolbar" content="no" />
<META NAME="Author" CONTENT="oscar999">
<%@include file="/WEB-INF/jsp/inc/easyUI.jsp"%>
<link rel="stylesheet" href="${ctxPath}/css/front/css/layout.css" type="text/css" />
<script type="text/javascript" src="${ctxPath}/js/common.js"></script>
<script type="text/javascript" src="${ctxPath}/jspage/home/home.js"></script>
<script type="text/javascript" src="${ctxPath}/js/front/lunbo/koala.min.1.5.js"></script>
<%-- <script type="text/javascript" src="${ctxPath}/js/front/lunbo/terminator2.2.min.js"></script> --%>


<style type="text/css">
	p.bottomMore{
				display:block;
				width:100%;
				font-weight:bold;
				text-align:center;
				line-height:normal;
			}
			#footer .pic p{
	text-align:center;
	}
	.shadow img{display:none;}
</style>
<script type="text/javascript">
var flag=false;
function DrawImage(ImgD,w,h){
	var default_W = 929;
	var default_H = 523;
	var image=new Image();
	image.src=ImgD.src;
	if(image.width>0 && image.height>0){
		flag=true;
		if(image.width/image.height>= 1.3){
			/* if(image.width>w){
			  ImgD.width=w;
			  ImgD.height=(image.height*w)/image.width;
			}else{
			  ImgD.width=image.width;
			  ImgD.height=image.height;
			} */
			if(image.width>=default_W && image.height>=default_H){
				ImgD.width=default_W;
				ImgD.height=default_H;
			}else{
					ImgD.height=default_H;
					ImgD.width=(ImgD.height*default_W)/image.width;
				}
		}else{
			if(image.height>default_H){
			  ImgD.height=default_H;
			  ImgD.width=(image.width*h)/image.height;
			}else{
			  ImgD.width=image.width*(default_H/image.height);
			  ImgD.height=default_H;
			}
		}
	}
}
</script>
</head>
<body id="top">
<div class="wrapper col1">
  <div id="header" class="clear">
    <div class="fl_left">
      <h1><a href="http://www.maxinsight.cn"><img src="${ctxPath}/img/front/images/logo.png"/></a></h1>
      
    </div>
    <div class="fl_right">
      
      <ul id="topnav" class="clear">
       <%-- <li><a href="${ctxPath}/manage/seeManage">经营管理</a></li>
      	<li><a href="${ctxPath}/meet/toHomepage">会议室</a></li> --%>
        
        <li><a href="#">快速查找</a>
          <ul>
            <li><a href="#hpage_info">最新公告</a></li>
            <li><a href="#rules">规章制度</a></li>
            <li><a href="#download">下载专区</a></li>            
            <li><a href="#foucs">关注我们</a></li>
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
         <li class="active last"><a href="${ctxPath}/meet/toHomepage">会议室</a></li>      
        <li><a href="${ctxPath}/connnect/toConnectPage">通讯录</a></li>
        <li class="active last"><a href="${ctxPath}/home/toHome">首页</a></li>
      </ul>
    </div>
  </div>
</div>
<!-- ##############################################header结束######################################################### -->

<form id="activeForm" action="" method="post" target="_blank" >
	<input type="hidden" id="activeID" name="activeID"/>
	<input type="hidden" id="ctxPath" name="ctxPath" value="${ctxPath}"/>
</form>
<div class="wrapper col2">
	<div id="container" class="clear">
	 
    <div id="fsD1" class="focus">  
        <div id="D1pic1" class="fPic">  
        	<c:forEach items="${active }" var="s" varStatus="ss">
        		<div class="fcon" style="display: none;">
	                <a target="_blank" href="javascript:void(0);" onclick="getActive(${s.pk_id})" ><img src="${ctxPath}/${s.path}"  onload="DrawImage(this,930,523);" style="opacity: 1; "></a>
	                <span class="shadow"><a target="_blank" onclick="getActive(${s.pk_id})" href="javascript:void(0);">${s.title}</a></span>
           		</div>
        	</c:forEach>
        </div>
        <div class="fbg">  
        <div class="D1fBt" id="D1fBt">  
        <c:forEach items="${active }" var="s" varStatus="ss">
        <a href="javascript:void(0);" hidefocus="true" target="_self" class=""><i>${ss.count}</i></a>
        </c:forEach>
              
        </div>  
        </div>  
        <span class="prev"></span>   
        <span class="next"></span>    
    </div>  
    <script type="text/javascript">
        Qfast.add('widgets', { path: "${ctxPath}/js/front/lunbo/terminator2.2.min.js", type: "js", requires: ['fx'] });  
        Qfast(false, 'widgets', function () {
            K.tabs({
                id: 'fsD1',   //焦点图包裹id  
                conId: "D1pic1",  //** 大图域包裹id  
                tabId:"D1fBt",  
                tabTn:"a",
                conCn: '.fcon', //** 大图域配置class       
                auto: 1,   //自动播放 1或0
                effect: 'fade',   //效果配置
                eType: 'click', //** 鼠标事件
                pageBt:true,//是否有按钮切换页码
                bns: ['.prev', '.next'],//** 前后按钮配置class                          
                interval: 2500  //** 停顿时间  
            }) 
        })  
        

    </script>      
    
     </div>	
</div>


<!-- ##############################################活动展示结束######################################################### -->
<div class="wrapper col3">
  <div id="container" class="clear">
    <!-- ####################################################################################################### -->
    
    <div id="hpage_info" class="clear">    
        
        <div id="comments">
            <h2>最新公告</h2>
            <ul class="commentlist">
            <c:forEach items="${notice }" var="s" varStatus="ss">
              <li class="${ss.index%2 eq '0'? 'comment_odd':'comment_even' }">

                <div class="author">
                	<span class="name">${s.flage_new}<a href="javascript:void(0);" style='font-size:15px;' onclick="getNotice('${ctxPath}','${s.pk_id}')">&nbsp;&nbsp;${s.title}</a></span>
                </div>
                <div class="submitdate"><a href="javascript:void(0);">&nbsp;&nbsp;&nbsp;${s.noticeTime}</a></div>
                <p><div class="noticeContent" style="width:860px;height:22px; overflow:hidden; text-overflow:ellipsis; white-space:nowrap;">&nbsp;&nbsp;&nbsp;&nbsp;${s.content}</div></p>
                 <p class="readmore"><a href="javascript:void(0);" onclick="getNotice('${ctxPath}','${s.pk_id}')">Continue Reading &raquo;</a></p> 
              </li>

            </c:forEach>
	            <li class="comment_even" id="comment_even_last">
	            	<p class="bottomMore"><a href="${ctxPath}/home/noticePage" target="_blank">VIEW　ALL &raquo;</a></p>
	            </li>

            </ul>
          </div>
         </div>      
     
    </div>
    <!-- ####################################################################################################### -->
  </div>
</div>
<!-- #####################################################公告结束################################################## -->
<div class="wrapper col4">
  <div id="container" class="clear">
  <div id="rules" class="clear">
  <!-- ####################################################################################################### -->  		
  	<form id="fileForm" action="" method="post" target="_blank" >
  		<input type="hidden" name="fileName" id="fileName" value="">
  		<input type="hidden" name="fileType" id="fileType" value="">
  	</form>	
  	
  		 <div class="rules_title">规章制度</div>
         <div id="column" >
          <div class="subnav">
          <h2><a href="javascript:getlistRule('${ctxPath}','1');"   target="_blank">基本制度<span>..More</span></a></h2>
            <%-- <h2><div class="title_left">基本制度</div><div class="title_right"><a href="javascript:getlistRule('${ctxPath}','1');"  target="_blank" >&gt;&gt;More</a></div></h2> --%>
            <ul>
	             <c:forEach items="${rule}" var="s" varStatus="ss">
	        		<c:if test="${s.type eq '1' }"><li><a href="javascript:void(0);" onclick="viewPdf('${ctxPath}','${s.pk_id}')">
		        		<div style="width:280px; overflow:hidden; text-overflow:ellipsis; white-space:nowrap;">
		        			${s.fileName}
		        		</div>
	        		</a></li></c:if>
	      		 </c:forEach> 
              </ul>
          </div>
         </div>
         <div id="column" >
          <div class="subnav">
           <h2><a href="javascript:getlistRule('${ctxPath}','2');"   target="_blank">IT制度<span>..More</span></a></h2>
           <%--  <h2><div class="title_left">IT制度</div><div class="title_right"><a href="javascript:getlistRule('${ctxPath}','2');" target="_blank" >&gt;&gt;More</a></div></h2> --%>
            <ul>
            	 <c:forEach items="${rule}" var="s" varStatus="ss">
	        		<c:if test="${s.type eq '2' }"><li><a href="javascript:void(0);" onclick="viewPdf('${ctxPath}','${s.pk_id}')" target="_blank">
	        		<div style="width:280px; overflow:hidden; text-overflow:ellipsis; white-space:nowrap;">
	        		${s.fileName}
	        		</div>
	        		</a></li></c:if>
	      		 </c:forEach> 
            </ul>
          </div>
         </div>
         <div id="column" >
          <div class="subnav">
          <h2><a href="javascript:getlistRule('${ctxPath}','3');"   target="_blank">财务制度<span>..More</span></a></h2>
            <%-- <h2><div class="title_left">财务制度</div><div class="title_right"><a href="javascript:getlistRule('${ctxPath}','3');" target="_blank" >&gt;&gt;More</a></div></h2> --%>
            <ul>
              <c:forEach items="${rule}" var="s" varStatus="ss">
	        		<c:if test="${s.type eq '3' }"><li><a href="javascript:void(0);" onclick="viewPdf('${ctxPath}','${s.pk_id}')">
	        		<div style="width:280px; overflow:hidden; text-overflow:ellipsis; white-space:nowrap;">
	        		${s.fileName}
	        		</div>
	        		</a></li></c:if>
	      		 </c:forEach> 
 
            </ul>
          </div>
         </div>
         
  <!-- ####################################################################################################### -->
  </div>
  </div>
</div>
<!-- ########################################################规章制度结束############################################### -->
<div class="wrapper col5" style="padding:5px 0 10px 0;">
  <div id="container" class="clear">
    <!-- ####################################################################################################### -->
   <form id="downfileForm" action="" method="post" target="_blank" >
   		<input type="hidden" name="modelId" id="modelId" value="">
   		<input type="hidden" name="modelTitle" id="modelTitle" value="">
  		<input type="hidden" name="typeId" id="typeId" value="">
  		<input type="hidden" name="title" id="title" value="">
  		<input type="hidden" name="type" id="type" value="">
  	</form>	
  	   <form id="downfileFormOther" action="file://192.168.1.240/Shared_Folders/Tools" method="post"  target="_blank" >
  	</form>	
    <div id="download" class="download">
    	<p >下载专区</p>
    	<c:forEach items="${uploadList }" var="tache" >
	    	<div class="download_detail">
	        	<p><a href="javascript:onclick=downPageHead('${ctxPath}','${tache.pk_id}','${tache.title}')" >${tache.title}<span>..More</span></a></p>
	            <ul>
	             <c:forEach items="${tache.typeList }" var="list" varStatus="ss">
	            	<li><a href="javascript:onclick=downPage('${ctxPath}','${list.pk_id}','${list.title}')">${list.title }</a></li>
	             </c:forEach>
	            </ul>
	        </div> 
        </c:forEach>
         	
    </div>
  </div>
</div>
    
<!-- ##############################################下载专区结束######################################################### -->

<div class="wrapper">
  <div id="footer" class="clear">
  	<div id="foucs">
  		<h2>关注我们</h2>
        <div class="pic">
        	<div class="pic_left">
            <img src="${ctxPath}/img/front/images/icon_wx.jpg" alt="" width="120" height="120"/>
			<!--代码修改-->
            <p class="">卓思公众号</p>
			<!--代码修改-->
            </div>
        	<div class="pic_right"><img src="${ctxPath}/img/front/images/qrcode.bmp" alt="" width="120" height="120"/>
			<!--代码修改-->
            <p class="">卓思欢乐汇</p>
			<!--代码修改-->
            </div>            
        </div>  
    </div>      
  </div>   
</div>


<!-- ###########################################################链接结束############################################ -->
<div class="wrapper">
  <div id="copyright" class="clear">
    <p class="fl_left">Copyright &copy; 2016 - All Rights Reserved - <a href="http://www.maxinsight.cn/">MaxInsight</a></p>
  </div>
</div>
	<!--最新的公告-->
<div id="noticeDialog" class="easyui-dialog" title="增加公告" data-options="closed: true,modal:true" style="width: 760px; height: 600px; padding-left: 20px; padding-top: 20px; text-align: center">
	<form id="noticeForm" name="noticeForm" action="" method="post" target="_self">
			<input type="hidden" name="pk_id" id="id" />
			<input type="hidden" name="notice_message" id="notice_message" />
			<table border="0"  style="text-align: left;">
				<tr>
					<td align='center' ><span id="notice_title"></span></td>
				</tr>
				<tr>
					<td><textarea name="message" id="message"  style="width:630px;height:30px;visibility:hidden;"></textarea></td>
				</tr>
				<tr>
					<td >&nbsp;</td>
				</tr> 
				<tr>
					<td ><span id="notice_content"></span></td>
				</tr> 
				
				 <tr>
					<td >&nbsp;</td>
				</tr>
				<tr>
					<td >&nbsp;</td>
				</tr> 
				<tr>
					<td style="padding-left:330px;">
						<a href="javascript:void(0);" class="easyui-linkbutton" plain="false" onclick="noticeClose();">关&nbsp;&nbsp;闭&nbsp;&nbsp;</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>