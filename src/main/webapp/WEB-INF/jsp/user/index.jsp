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
		<link rel="stylesheet" type="text/css" href="/user/css/swiper-3.4.2.min.css" />
		<!-- 自定义样式区域 -->
		<link rel="stylesheet" type="text/css" href="/user/css/index.css" />
		<link rel="stylesheet" type="text/css" href="/user/css/bottomNav.css" />
	</head>

	<body>

		<div class="g-doc">
			<div class="area area1">

				<div class="swiper-container bannerSwiper">
					<div class="swiper-wrapper">
						<div class="swiper-slide">
							<img src="/user/img/banner.jpg" />
						</div>
						<div class="swiper-slide">
							<img src="/user/img/banner.jpg" />
						</div>
						<div class="swiper-slide">
							<img src="/user/img/banner.jpg" />
						</div>
					</div>
					<div class="swiper-pagination"></div>
				</div>

				<div class="a1SortBox">
					<a href="#">
						<img src="/user/img/icon-index1.png" alt="主食" />
						<p>主食</p>
					</a>
					<a href="#">
						<img src="/user/img/icon-index2.png" alt="主食" />
						<p>主食</p>
					</a>
					<a href="#">
						<img src="/user/img/icon-index3.png" alt="面食" />
						<p>面食</p>
					</a>
					<a href="#">
						<img src="/user/img/icon-index4.png" alt="粥汤" />
						<p>粥汤</p>
					</a>
				</div>

			</div>
			<c:if test="${!empty today}">
				<div class="area area2">
	
					<div class="titleBox">
						<div class="inner">
							<div class="areaTitle">今日推荐<span>Today's selection</span></div>
						</div>
					</div>
					<div class="hor-scroll">
						<div class="inner ">
								<c:forEach items="${today}" var="item">
									<div class="fooditem">
										<div class="fooditem-pic">
											<a href="/user/cuisine/cuisineDetail.do?cuisineId=${item.id}">
												<img src="/user/img/fooditem.jpg" />
											</a>
											<div class="price">￥${item.price}</div>
										</div>
										<p class="foodname">${item.name}</p>
									</div>
								</c:forEach>
						</div>
					</div>
	
				</div>
			</c:if>
			<div class="area ad">
				<a href="#" target="_blank">
					<img src="/user/img/adbanner.jpg" />
				</a>
			</div>
			<c:if test="${!empty tromorrow}">
				<div class="area area3">
	
					<div class="titleBox">
						<div class="inner">
							<div class="areaTitle">猜你喜欢<span>Assume you like</span></div>
						</div>
					</div>
					<div class="hor-scroll">
							<div class="inner ">
								<c:forEach items="${tromorrow}" var="item">
										<div class="fooditem">
											<div class="fooditem-pic">
												<a href="/user/cuisine/cuisineDetail.do?cuisineId=${item.id}">
													<img src="/user/img/fooditem.jpg" />
												</a>
												<div class="price">￥${item.price}</div>
											</div>
											<p class="foodname">${item.name}</p>
										</div>
								</c:forEach>
							</div>
					</div>
	
				</div>
			</c:if>
			<div class="area area4">

				<div class="titleBox">
					<div class="inner">
						<div class="areaTitle">心水菜品<span>每周菜品由你来决定</span></div>
					</div>
				</div>
				<div class="week-selectbox">
					<div class="inner">
						<c:forEach items="${favorite}" var="item">
							<div class="week-item">
								<img src="/user/img/fooditem.jpg" />
								<div class="rbox">
									<div class="foodname">${item.name}</div>
									<div class="foodinfo">${item.desc}</div>
									<div class="foodfavnum"><span>155</span>人喜欢</div>
								</div>
								<a href="javascript:addrfavrite(${item.id});" class="favbtn"></a>
							</div>
						</c:forEach>
					</div>
				</div>

			</div>
			<!--有act变部车-->
			<a class="iconplus act" href="javascript:toBuyCar();"></a>
			<div class="bottomNav">
				<a href="#" class="bottomNavBtn sideBtn act">
					<img src="/user/img/icon-b1.png" />
					<p>快点</p>
				</a>
				<a href="/user/cuisine/list.do" class="bottomNavBtn sideBtn bottomNavBtnCenter">
					<img src="/user/img/icon-b2a.png" />
					<p>订餐</p>
				</a>
				<a href="/user/usercenter.do" class="bottomNavBtn sideBtn">
					<img src="/user/img/icon-b3a.png" />
					<p>我</p>
				</a>
			</div>
		</div>

		<!--页面脚本区S-->
		<!--所有页面用到的js脚本都必须放到此位置，包括外链js和内嵌js-->
		<script src="/user/js/jquery.js" type="text/javascript" charset="utf-8"></script>
		<script src="/user/js/popup.js" type="text/javascript" charset="utf-8"></script>
		<script src="/user/js/com.js" type="text/javascript" charset="utf-8"></script>
		<script src="/user/js/swiper-3.4.2.min.js" type="text/javascript" charset="utf-8"></script>
		<script type="text/javascript">
			$(function() {
				new Swiper('.bannerSwiper', {
					pagination: '.swiper-pagination',
					effect: 'flip'
				})

				$('.favbtn').click(function() {
					$(this).toggleClass('act')
				})
			})
			function toBuyCar(){
				if(!com.getCookie('_kd_user_buyCar_')){
					location.href='/buycar/list.do';
				}else{
					com.tips('请选择菜品');
				}
			}
			
			function addrfavrite(cuisineId){
				$.getScript('/user/collection/add.do?cuisineId='+cuisineId);
			}
		</script>

		<!--页面脚本区E-->

	</body>

</html>