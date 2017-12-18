<%@page contentType="text/html" pageEncoding="utf-8" session="false"%>
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
		<link rel="stylesheet" type="text/css" href="/user/css/sum.css" />
	</head>

	<body>

		<div class="g-doc">
			<div class="area area1">
				<div class="daojishi">
					<!--data-time赋值剩余秒数进行倒数-->
					<h1 data-time="900"></h1>
					<h2>请在支付剩余时间内完成支付</h2>
				</div>
				<div class="titleBox">
					<div class="inner">
						<div class="areaTitle">订单信息</div>
					</div>
				</div>
				<div>
					<div class="infoitem">
						<div class="inner">
							<span class="infoitemName">订单号码</span>
							<span class="infoitemValue">${order.outTradeNo}</span>
						</div>
					</div>
					<div class="infoitem">
						<div class="inner">
							<span class="infoitemName">创建时间</span>
							<span class="infoitemValue"><fmt:formatDate value="${order.createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></span>
						</div>
					</div>
					<div class="infoitem">
						<div class="inner">
							<span class="infoitemName">支付金额</span>
							<span class="infoitemValue fontred">￥${order.price}</span>
						</div>
					</div>
				</div>

			</div>

			<div class="area area2">
				<div class="titleBox">
					<div class="inner">
						<div class="areaTitle">支付方式</div>
					</div>
				</div>
				<div>
					<div class="infoitem act">
						<div class="inner">
							<span class="infoitemName icon-pay icon-zfb"></span>
							<span class="infoitemValue">支付宝</span>
							<span class="infotick"></span>
						</div>
					</div>
					<div class="infoitem">
						<div class="inner">
							<span class="infoitemName icon-pay icon-wx"></span>
							<span class="infoitemValue">微信</span>
							<span class="infotick"></span>
						</div>
					</div>

				</div>
			</div>
			<a href="/alipay/action.do?orderId=${order.id}" class="submitbtn">确认支付￥${order.price}</a>
		</div>

		<!--页面脚本区S-->
		<!--所有页面用到的js脚本都必须放到此位置，包括外链js和内嵌js-->
		<script src="/user/js/jquery.js" type="text/javascript" charset="utf-8"></script>
		<script src="/user/js/popup.js" type="text/javascript" charset="utf-8"></script>
		<script src="/user/js/com.js" type="text/javascript" charset="utf-8"></script>
		<script type="text/javascript">
			$(function() {
				$('.g-doc').css('height', $(window).height())

				var lesstime = $('h1').attr('data-time');
				$('h1').html(fixzero(toTime(lesstime).min) + ':' + fixzero(toTime(lesstime).sec))
				setInterval(function() {
					lesstime--;
					if(lesstime >= 0) {
						//继续倒计时

						$('h1').html(fixzero(toTime(lesstime).min) + ':' + fixzero(toTime(lesstime).sec))
					} else {
						//倒计时停止
					}

				}, 1000)

				function toTime(arg) {
					var min = parseInt(arg / 60);
					var sec = arg % 60
					return {
						min: min,
						sec: sec
					}
				}

				function fixzero(arg) {
					arg = arg.toString();
					if(arg.length == 1) {
						return '0' + arg
					} else {
						return arg
					}
				}

				var zfType = 0;
				//0支付宝 1微信
				$('.infoitem').click(function() {
					$(this).addClass('act').siblings().removeClass('act');
					zfType = $(this).index();
				})
				$('.submitbtn').click(function() {
					if(zfType == 0) {
						//支付宝
					} else {
						//微信
						com.tips('暂不支持微信支付');
					}
				})

			})
		</script>

		<!--页面脚本区E-->

	</body>

</html>