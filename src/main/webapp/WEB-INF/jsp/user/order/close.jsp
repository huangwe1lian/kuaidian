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
		<link rel="stylesheet" type="text/css" href="/user/css/state.css" />
	</head>

	<body>

		<div class="g-doc">
			<div class="area area1">
				<!--订单完成-->
				<div class="state-success state" style="display:none;">
					<p class="state-title">订单完成</p>
					<p class="state-info">感谢你使用快点点餐，期待再次为你服务</p>
					<div class="btnbox">
						<a class="btn btnfn1" href="/user/cuisine/list.do">继续点餐</a>
						<a class="btn btnfn2" href="#">请评价</a>
					</div>
				</div>
				<!--订单完成-->

				<!--等待取餐-->
				<div class="state-wait state" style="display:none;">
					<p class="state-title">等待取餐</p>
					<p class="state-info">前方还有2人，请到窗口扫码取餐</p>
					<div class="btnbox">
						<a class="btn btnfn1" href="/user/cuisine/list.do">继续点餐</a>
						<a class="btn btnfn2" href="#">去取餐</a>
					</div>
				</div>
				<!--等待取餐-->

				<!--等待支付-->
				<div class="state-topay state" style="display:none;">
					<p class="state-title">等待支付</p>
					<p class="state-info" data-time="60">请在支付剩余时间内完成支付 <span></span></p>
					<div class="btnbox">
						<a class="btn btnfn1" href="#">取消订单 </a>
						<a class="btn btnfn2" href="#">去支付</a>
					</div>
				</div>
				<!--等待支付-->
				
				<!--等待支付-->
				<div class="state-fail state" -style="display:none;">
					<p class="state-title">订单关闭</p>
					<p class="state-info" data-time="60">订单已关闭，期待再次为你服务 <span></span></p>
					<div class="btnbox">
						<a class="btn btnfn1" href="/user/cuisine/list.do">继续点餐 </a>
					</div>
				</div>
				<!--等待支付-->
			</div>
			
			<div class="area area2">
				<div class="titleBox">
					<div class="inner">
						<div class="areaTitle">菜品信息</div>
					</div>
				</div>
				<div>
					<div class="infoitem">
						<div class="inner">
							<span class="infoitemfn1">土豆蘑菇饭</span>
							<span class="infoitemfn2">x2</span>
							<span class="infoitemfn3">￥24</span>
						</div>
					</div>
					<div class="infoitem">
						<div class="inner">
							<span class="infoitemfn1">土豆蘑菇饭</span>
							<span class="infoitemfn2">x2</span>
							<span class="infoitemfn3">￥24</span>
						</div>
					</div>
					<div class="infoitem">
						<div class="inner">
							<span class="infoitemfn4"><span>小计：</span> ￥24</span>
						</div>
					</div>

				</div>
			</div>
			
			<div class="area area3">
				<div class="titleBox">
					<div class="inner">
						<div class="areaTitle">订单信息</div>
					</div>
				</div>
				<div>
					<div class="infoitem">
						<div class="inner">
							<span class="inkey">订单号码</span>
							<span class="invalue">1259302-121</span>
						</div>
					</div>
					<div class="infoitem">
						<div class="inner">
							<span class="inkey">订单时间</span>
							<span class="invalue">2017-05-25  05:23</span>
						</div>
					</div>
					<div class="infoitem">
						<div class="inner">
							<span class="inkey">预约时间</span>
							<span class="invalue">12:40 ~ 12:50</span>
						</div>
					</div>
					<div class="infoitem">
						<div class="inner">
							<span class="inkey">口味偏好</span>
							<span class="invalue">不辣</span>
						</div>
					</div>

				</div>
			</div>

		</div>

		<!--页面脚本区S-->
		<!--所有页面用到的js脚本都必须放到此位置，包括外链js和内嵌js-->
		<script src="/user/js/jquery.js" type="text/javascript" charset="utf-8"></script>
		<script src="/user/js/popup.js" type="text/javascript" charset="utf-8"></script>
		<script src="/user/js/com.js" type="text/javascript" charset="utf-8"></script>
		<script type="text/javascript">
			//true进行倒计时
			if(true) {
				var lesstime = $('.state-topay .state-info').attr('data-time');
				$('.state-topay .state-info span').html(fixzero(toTime(lesstime).min) + ':' + fixzero(toTime(lesstime).sec))
				setInterval(function() {
					lesstime--;
					if(lesstime >= 0) {
						//继续倒计时

						$('.state-topay .state-info span').html(fixzero(toTime(lesstime).min) + ':' + fixzero(toTime(lesstime).sec))
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
					arg = arg.toString()
					if(arg.length == 1) {
						return '0' + arg
					} else {
						return arg
					}
				}
			}
		</script>
		<!--页面脚本区E-->

	</body>

</html>