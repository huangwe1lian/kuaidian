<%@page contentType="text/html" pageEncoding="UTF-8" session="false"%>
<%@include file="/WEB-INF/jspf/import.jspf"%>
<!DOCTYPE html>
<html>

	<head>
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<meta content="telephone=no" name="format-detection" />
		<meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1.0,user-scalable=no" />
		<meta content="always" name="referrer">

		<!-- seo 信息 -->
		<title>快点_太平洋电脑网</title>
		<meta name="keywords" content="快点专题，太平洋电脑专题" />
		<meta name="description" content="快点_太平洋电脑网" />

		<!-- qq传链接抓取信息 -->
		<meta itemprop="image" content="http://www1.pconline.com.cn/zt/share/share_logo.jpg">
		<meta itemprop="name" content="快点_太平洋电脑网">

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
					i.body.style.fontSize = "16px"
				} else {
					i.addEventListener("DOMContentLoaded", function(e) {
						i.body.style.fontSize = "16px"
					}, false)
				}
			})(750, 750);
		</script>

		<!-- 公用重置样式 -->
		<link rel="stylesheet" type="text/css" href="/user/css/reset.css" />
		<!-- 自定义样式区域 -->
		<link rel="stylesheet" type="text/css" href="/user/css/usercenter.css" />
		<link rel="stylesheet" type="text/css" href="/user/css/bottomNav.css"/>
	</head>

	<body>

		<div class="g-doc">
			<div class="area area1">
				<div class="a1Top">
					<span class="icon-mail"></span>
					<span class="toptitle">我</span>
					<span class="icon-function"></span>
				</div>
				<div class="a1Center">
					<div class="name1">猫咪点点</div>
					<div class="name2">liminmin</div>
					<div class="header">
						<img src="./img/headerpic.jpg"/>
					</div>
				</div>
				<div class="a1Bottom">
					<div class="a1BottomInner">
						<div class="a1BottomLeft">
						<p class="num">3</p>
						<p class="numinfo">正在排队</p>
						</div>
						<a class="gotoEdit" href="#">编辑个人资料</a>
					</div>
					
				</div>
			</div>
			<div class="area area2">
				<a href="#">
					<div class="listinner">
						<div class="listLeft">
							<i class="icon icon-dd"></i>
							<span>我的订单</span>
						</div>
						<div class="listRight">
							<span>20</span>
						</div>
					</div>
				</a>
				<a href="#">
					<div class="listinner">
						<div class="listLeft">
							<i class="icon icon-msg"></i>
							<span>我的订单</span>
						</div>
						<div class="listRight">
							<span>20</span>
						</div>
					</div>
				</a>
				<a href="#">
					<div class="listinner">
						<div class="listLeft">
							<i class="icon icon-fav"></i>
							<span>我的订单</span>
						</div>
						<div class="listRight dz">
							<span>20</span>
							<p>最新于02月11日</p>
						</div>
					</div>
				</a>
				<a href="#">
					<div class="listinner">
						<div class="listLeft">
							<i class="icon icon-tp"></i>
							<span>我的订单</span>
						</div>
						<div class="listRight">
							<span>20</span>
						</div>
					</div>
				</a>
			</div>
			
			<div class="bottomNav">
				<a href="/user/index.do" class="bottomNavBtn sideBtn act">
					<img src="img/icon-kuaidian.png"/>
					<p>快点</p>
				</a>
				<a href="/user/cuisine/list.do" class="bottomNavBtn bottomNavBtnCenter">
					
				</a>
				<a href="#" class="bottomNavBtn sideBtn">
					<img src="img/icon-kuaidian.png"/>
					<p>个人中心</p>
				</a>
			</div>
			
		</div>

		<!--页面脚本区S-->
		<!--所有页面用到的js脚本都必须放到此位置，包括外链js和内嵌js-->
		<script src="/user/js/jquery.js" type="text/javascript" charset="utf-8"></script>
		<script src="/user/js/popup.js" type="text/javascript" charset="utf-8"></script>
		<script src="/user/js/com.js" type="text/javascript" charset="utf-8"></script>
		<script type="text/javascript">
			$(function() {
			$('.g-doc').css('height',$(window).height())
				

			})
		</script>

		<!--页面脚本区E-->

	</body>

</html>