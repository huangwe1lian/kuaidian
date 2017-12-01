<%@page contentType="text/html" pageEncoding="UTF-8" session="false"
%>
<%@include file="/WEB-INF/jspf/import.jspf"%>
<!DOCTYPE html>
<html>
    <head>
        <!-- <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>登陆 - 应用账系统</title>
        <script src="/admin/js/jquery-1.7.2.min.js"></script>
		<script src="http://static.geetest.com/static/tools/gt.js"></script> -->
		<meta charset="utf-8">
	  	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	 	 <title>快点</title>
	 	<meta name="renderer" content="webkit">
	  	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	  	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	  	<meta name="apple-mobile-web-app-status-bar-style" content="black"> 
	  	<meta name="apple-mobile-web-app-capable" content="yes">
	  	<meta name="format-detection" content="telephone=no">
	  	<link rel="stylesheet" href="/admin/css/layui.css"  media="all">
    </head>
    <body onload='document.getElementById("return").value = location.href.replace(/http:\/\/(.*)\/(.*)/, "http://$1/login.jsp");'>
        <form id="login" action="/shangjia/doLogin.do" method="post"> 
            <div>
            <!-- <img src="themes/default/images/ftlogo.jpg"/> -->
            <span style="font-size: 22px; padding: 10px; color: #666;">快点点餐系统</span>
            </div>
            <fieldset id="inputs"> 
                <input id="username" name="username" type="text" placeholder="员工帐号" autofocus required> 
                <input id="password" name="password" type="password" placeholder="密码" required> 
            	<!-- <div id="embed-captcha"></div>
			    <p id="wait" class="show">正在加载验证码......</p>
			    <p id="notice" class="hide">请先拖动验证码到相应位置</p> -->
            </fieldset> 
            <fieldset id="actions"> 
                <input type="hidden" id="return" name="return" value="">
                <input type="submit" id="submit" value="登 录"> 
            </fieldset> 
        </form>
    </body>
    <script src="/admin/layui.js" charset="utf-8"></script>
</html>
