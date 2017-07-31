<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" %> 
<meta http-equiv="X-UA-Compatible" content="IE=10;IE=9;IE=8;IE=7;" />
<%@include file="/WEB-INF/jsp/inc/inc.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>内部信息系统</title>
<%-- <link rel="stylesheet" type="text/css" href="${ctxPath}/js/login/theme.css" /> --%>
<link rel="stylesheet" type="text/css" href="${ctxPath}/js/login/login.css" />
<script type="text/javascript" src="${ctxPath}/js/login/login.js"></script> 
<style type="text/css">
#versionBar {
	background-color: #212121;
	position: fixed;
	width: 100%;
	height: 35px;
	bottom: 0;
	left: 0;
	text-align: center;
	line-height: 35px;
	z-index: 11;
	-webkit-box-shadow: black 0px 10px 10px -10px inset;
	-moz-box-shadow: black 0px 10px 10px -10px inset;
	box-shadow: black 0px 10px 10px -10px inset;
}

.copyright {
	text-align: center;
	font-size: 12px;
	color: #CCC;
}

.copyright a {
	color: #A31F1A;
	text-decoration: none
}
input:-webkit-autofill { 
-webkit-box-shadow: 0 0 0px 1000px #7C7C7C inset; 
}
</style>
</head>

<body>
<div class="context"> 
    <div class="t_logo"></div>
    <div class="login_box"> 
    	<p class="title"><span class="login-img"></span><span >内部信息系统后台</span></p><!-- style="font-size: 20px;color: #bababa;" -->
        <div class="login_row"> 
            <div> 
                <form name="flogin" id="flogin" method="post" >
                <div class="f_row_name"> 
                    <input id="fname" name="uname" type="text" class="fm_user"/> 
                </div>
                <div class="f_row_pwd"> 
                   <input  id="fpwd" name="upass" type="password" class="fm_pwd"/> 
                </div>
                <div class="f_row_sub"> 
                     <input id="sub" name="sub" type="button" value="登 录" class="fm_sub" onclick="javascript:login('${ctxPath}')"<%-- onclick="javascript:location.href='${ctxPath}/user/toMain'" --%>/> 
                </div> 
                <div id="logErr" class="f_row_err"></div>
                </form>
            </div>
        </div>
    </div>
</div>
<div id="versionBar">
   <div class="copyright">
    &copy; 版权所有
    <span class="tip"><a href="#" title="MaxInsight">MaxInsight</a> (推荐使用谷歌浏览器可以获得更快的页面响应速度)</span>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <!--<span class="tip"><a href="${ctxPath}/userlogin/downloadAPK" title="Android版客户端">Android版客户端下载</a></span>-->
   </div>
  </div>




</body></html>