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
		<link rel="stylesheet" type="text/css" href="/user/css/swiper-3.4.2.min.css" />
		<!-- 自定义样式区域 -->
		<link rel="stylesheet" type="text/css" href="/user/css/qr.css" />
	</head>

	<body>

		<div class="g-doc">

			<div class="area2 area">
				<div class="a2title">
					<div class="inner">
						<div href="#" class="a2titleLeft">最近订单</div>
					</div>
				</div>
				<div class="a2foodHistoryList">
					<c:forEach items="${orders}" var="item">
						<div class="a2foodHistoryItem">
							<div class="inner">
								<div class="picbox">
									<img src="img/fooditem.jpg" />
								</div>
								<div class="foodHisInfo">
									<p class="foodHisInfoi1">${item.name}<span> x2 </span>...</p>
									<p class="foodHisInfoi2">${item.update_time}</p>
								</div>
								<!--待取-->
								<div class="foodHisState">
								 	<c:choose>
										<c:when test="${item.status == 0}">
											<p>待支付</p>
										</c:when>
										<c:when test="${item.status == 1}">
											<p>待取餐</p>
										</c:when>
										<c:when test="${item.status == 2}">
											<p class="fontGray">订单完成</p>
										</c:when>
									</c:choose>
									<div class="foodHisState-btnbox">
										<c:choose>
											<c:when test="${item.status == -1}">
												<a href="#" class="Hisbtn gray" >已取消</a>
											</c:when>
											<c:when test="${item.status == 0}">
												<a href="/user/order/canncel.do?orderId=${item.id}" class="Hisbtn gray" >取消订单</a>
												<a href="/alipay/action.do?orderId=${item.id}" class="Hisbtn red">去支付</a>
											</c:when>
											<c:when test="${item.status == 1}">
												<a href="/user/qr.do?out_trade_no=${item.out_trade_no}" class="Hisbtn green">去取餐</a>
											</c:when>
											<c:when test="${item.status == 2}">
												<div class="foodHisState-btnbox">
													<a href="javascript:alert('去评价页');" class="Hisbtn red" >去评价</a>
												</div>
											</c:when>
										</c:choose>
									</div>
								</div>
								<!--待取-->
							</div>
						</div>
					</c:forEach>
				</div>
			</div>

		</div>

		<!--页面脚本区S-->
		<!--所有页面用到的js脚本都必须放到此位置，包括外链js和内嵌js-->
		<script src="/user/js/jquery.js" type="text/javascript" charset="utf-8"></script>
		<script src="/user/js/popup.js" type="text/javascript" charset="utf-8"></script>
		<script src="/user/js/com.js" type="text/javascript" charset="utf-8"></script>
		<script src="/user/js/swiper-3.4.2.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="/user/js/qrcode.min.js" type="text/javascript" charset="utf-8"></script>
		<script type="text/javascript">
			$(function() {

			})
		</script>

		<!--页面脚本区E-->

	</body>

</html>