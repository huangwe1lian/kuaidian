<%@page contentType="text/html" pageEncoding="UTF-8" session="false"%>
<%@include file="/WEB-INF/jspf/import.jspf"%>
<% 
	String code = EnvUtils.getEnv().param("code");
	if ("login_fail".equals(code)) {
	    pageContext.setAttribute("txt", "帐号或密码错误");
	} else if ("unauthorized".equals(code)) {
	    pageContext.setAttribute("txt", "您未开通本系统权限。");
	} else {
	    pageContext.setAttribute("txt", code);
	}
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<meta content="telephone=no" name="format-detection" />
		<meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1.0,user-scalable=no" />
		<meta content="always" name="referrer">

		<!-- seo 信息 -->
		<title>快点_点餐系统</title>
		<meta name="keywords" content="快点专题" />
		<meta name="description" content="快点_点餐系统" />

		<!-- qq传链接抓取信息 -->
		<meta itemprop="image" content="http://www1.pconline.com.cn/zt/share/share_logo.jpg">
		<meta itemprop="name" content="快点_点餐系统">

		<!-- 专题设计和制作 -->
		<meta name="Author" content="guanyelin_gz designer_gz">

		<!-- 页面适配js -->
		<script type="text/javascript">
			(function(e, t) {
				var i = document,
					n = window;
				var l = i.documentElement;
				var a, r;
				var d, s = document.createElement("style");
				var o;

				function m() {
					var i = l.getBoundingClientRect().width;
					if(!t) {
						t = 540
					}
					if(i > t) {
						i = t
					}
					var n = i * 100 / e;
					s.innerHTML = "html{font-size:" + n + "px;}"
				}
				a = i.querySelector('meta[name="viewport"]');
				r = "width=device-width,initial-scale=1,maximum-scale=1.0,user-scalable=no";
				if(a) {
					a.setAttribute("content", r)
				} else {
					a = i.createElement("meta");
					a.setAttribute("name", "viewport");
					a.setAttribute("content", r);
					if(l.firstElementChild) {
						l.firstElementChild.appendChild(a)
					} else {
						var c = i.createElement("div");
						c.appendChild(a);
						i.write(c.innerHTML);
						c = null
					}
				}
				m();
				if(l.firstElementChild) {
					l.firstElementChild.appendChild(s)
				} else {
					var c = i.createElement("div");
					c.appendChild(s);
					i.write(c.innerHTML);
					c = null
				}
				n.addEventListener("resize", function() {
					clearTimeout(o);
					o = setTimeout(m, 300)
				}, false);
				n.addEventListener("pageshow", function(e) {
					if(e.persisted) {
						clearTimeout(o);
						o = setTimeout(m, 300)
					}
				}, false);
				if(i.readyState === "complete") {
					i.body.style.fontSize = "16px";
				} else {
					i.addEventListener("DOMContentLoaded", function(e) {
						i.body.style.fontSize = "16px";
					}, false)
				}
			})(750, 750);
		</script>

		<!-- 公用重置样式 -->
		<link rel="stylesheet" type="text/css" href="/user/css/reset.css" />
		<!-- 自定义样式区域 -->
		<link rel="stylesheet" type="text/css" href="/user/css/login.css" />
	</head>

	<body>

		<div class="g-doc">
			<form id="login" action="/user/doLogin.do" method="post"> 
				<div class="loginInput AccountInput">
					<span>账号:</span>
					<input id="username" name="username" type="text" placeholder="请输入用户名" > 
				</div>
				<div class="loginInput PasswordInput">
					<span>密码:</span>
					<input id="password" name="password" type="password" placeholder="请输入密码" >
				</div>
				<div class="loginBtn">登录</div>
			</form>
		</div>

		<!--页面脚本区S-->
		<!--所有页面用到的js脚本都必须放到此位置，包括外链js和内嵌js-->
		<script src="/user/js/jquery.js" type="text/javascript" charset="utf-8"></script>
		<script src="/user/js/popup.js" type="text/javascript" charset="utf-8"></script>
		<script src="/user/js/com.js" type="text/javascript" charset="utf-8"></script>
		<script type="text/javascript">
			$(function() {
				$('.g-doc').css('height', $(window).height())
				
				$('.loginBtn').click(function(){
					//if($("#username").length>1 && $("#password").length>1){
						$.ajax({
							url:'/user/doLogin.do',
							data:$("#login").serialize(),
							type:'post',
							dataType:'text',
							success:function(text){
								if(text=="suceess"){
									location.href = "/user/index.do";
								}else if(text= "login_fail"){
									com.tips("帐号或密码错误");
								}else{
									com.tips("您未开通本系统权限。");
								}
							}
						});
						//$("#login").submit();
					//}
				});
			});
		</script>

		<!--页面脚本区E-->

	</body>

</html>
