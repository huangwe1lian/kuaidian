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
		<link rel="stylesheet" type="text/css" href="css/reset.css" />
		<link rel="stylesheet" type="text/css" href="css/swiper-3.4.2.min.css" />
		<!-- 自定义样式区域 -->
		<link rel="stylesheet" type="text/css" href="css/qr.css" />
	</head>

	<body>

		<div class="g-doc">
			<div class="area area1">
				<div class="swiper-container a1swiper">
					<div class="swiper-wrapper">
						<div class="swiper-slide">
							<div class="qrbox">
								<div class="qrtitle">当前订单</div>
								<div class="qrinfo">
									<div class="qrinfoLeft">
										<div class="qrinfoLi1">土豆蘑菇饭<span> x2</span> ...</div>
										<div class="qrinfoLi2">2017-10-20 12:30</div>
									</div>
									<div class="qrinfoRight colorGreen">
										待取餐
									</div>
								</div>
								<div class="qrCenter">
									<div class="qrinner qr1">
									</div>
									<p class="qrnum">订单号：153686-153</p>
									
									<p class="qrnum fontred">取餐成功，祝您用餐愉快</p>
								</div>
								<div class="qrpaidui">前方还有 <span>2</span>人，请到窗口扫码取餐</div>
							</div>
						</div>
						<div class="swiper-slide">
							<div class="qrbox">
								<div class="qrtitle">当前订单</div>
								<div class="qrinfo">
									<div class="qrinfoLeft">
										<div class="qrinfoLi1">土豆蘑菇饭<span> x2</span> ...</div>
										<div class="qrinfoLi2">2017-10-20 12:30</div>
									</div>
									<div class="qrinfoRight colorGreen">
										待取餐
									</div>
								</div>
								<div class="qrCenter">
									<div class="qrinner qr1">
									</div>
									<p class="qrnum">订单号：153686-153</p>
								</div>
								<div class="qrpaidui">前方还有 <span>2</span>人，请到窗口扫码取餐</div>
							</div>
						</div>
						<div class="swiper-slide">
							<div class="qrbox">
								<div class="qrtitle">当前订单</div>
								<div class="qrinfo">
									<div class="qrinfoLeft">
										<div class="qrinfoLi1">土豆蘑菇饭<span> x2</span> ...</div>
										<div class="qrinfoLi2">2017-10-20 12:30</div>
									</div>
									<div class="qrinfoRight colorGreen">
										已完成订单
									</div>
								</div>
								<div class="qrCenter">
									<div class="qrinner tick">
									</div>
									<p class="qrnum fontred">取餐成功，祝您用餐愉快</p>
								</div>
								<div class="qrpaidui">前方还有 <span>2</span>人，请到窗口扫码取餐</div>
							</div>
						</div>
					</div>
				</div>
				<div class="tipsinfo">取餐时段：2017-10-20 12:30至13:30 <span class="icon-question"></span></div>
				<div class="swiper-pagination1 dz"></div>
			</div>
			
			<div class="area2 area">
				<div class="a2title">
					<div class="inner">
						<div href="#" class="a2titleLeft">最近订单</div>
						<a href="#" class="a2titleRigth">全部订单 ></a>
					</div>
				</div>
				<div class="a2foodHistoryList">
						<div class="a2foodHistoryItem">
							<div class="inner">
								<div class="picbox">
									<img src="img/fooditem.jpg"/>
								</div>
								<div class="foodHisInfo">
									<p class="foodHisInfoi1">土豆蘑菇饭<span> x2 </span>...</p>
									<p class="foodHisInfoi2">2017-10-20 12:30</p>
								</div>
								<!--待支付-->
								<div class="foodHisState">
									<p>待支付</p>
									<div class="foodHisState-btnbox">
										<a href="#" class="Hisbtn gray" >取消订单</a>
										<a href="#" class="Hisbtn red">去支付</a>
									</div>
								</div>
								<!--待支付-->
							</div>
						</div>
						<div class="a2foodHistoryItem">
							<div class="inner">
								<div class="picbox">
									<img src="img/fooditem.jpg"/>
								</div>
								<div class="foodHisInfo">
									<p class="foodHisInfoi1">土豆蘑菇饭<span> x2 </span>...</p>
									<p class="foodHisInfoi2">2017-10-20 12:30</p>
								</div>
								<!--完成-->
								<div class="foodHisState">
									<p class="fontGray">订单完成</p>
									<div class="foodHisState-btnbox">
										<a href="#" class="Hisbtn red" >去评价</a>
									</div>
								</div>
								<!--完成-->
							</div>
						</div>
						<div class="a2foodHistoryItem">
							<div class="inner">
								<div class="picbox">
									<img src="img/fooditem.jpg"/>
								</div>
								<div class="foodHisInfo">
									<p class="foodHisInfoi1">土豆蘑菇饭<span> x2 </span>...</p>
									<p class="foodHisInfoi2">2017-10-20 12:30</p>
								</div>
								<!--完成-->
								<div class="foodHisState">
									<p class="fontGray">订单完成</p>
									<div class="foodHisState-btnbox">
										<a href="#" class="Hisbtn red" >去评价</a>
									</div>
								</div>
								<!--完成-->
							</div>
						</div>
						<div class="a2foodHistoryItem">
							<div class="inner">
								<div class="picbox">
									<img src="img/fooditem.jpg"/>
								</div>
								<div class="foodHisInfo">
									<p class="foodHisInfoi1">土豆蘑菇饭</p>
									<p class="foodHisInfoi2">2017-10-20 12:30</p>
								</div>
								<!--完成-->
								<div class="foodHisState">
									<p class="fontGray">订单完成</p>
									
								</div>
								<!--完成-->
							</div>
						</div>
					</div>
			</div>
			
		</div>

		<!--页面脚本区S-->
		<!--所有页面用到的js脚本都必须放到此位置，包括外链js和内嵌js-->
		<script src="js/jquery.js" type="text/javascript" charset="utf-8"></script>
		<script src="js/popup.js" type="text/javascript" charset="utf-8"></script>
		<script src="js/com.js" type="text/javascript" charset="utf-8"></script>
		<script src="js/swiper-3.4.2.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="js/qrcode.min.js" type="text/javascript" charset="utf-8"></script>
		<script type="text/javascript">
			$(function() {
				new Swiper('.a1swiper', {
					pagination: '.swiper-pagination1',
					paginationBulletRender: function(swiper, index, className) {
						return '<span class="bull ' + className + '">' + (index + 1) + '</span>';
					}
				})
				//生成二维码
				for(var i=0;i<$('.qr1').length;i++){
					new QRCode($('.qr1').get(i), 'http://www.baidu.com');
				}
				

			})
		</script>

		<!--页面脚本区E-->

	</body>

</html>