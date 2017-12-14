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
		<link rel="stylesheet" type="text/css" href="/user/css/swiper-3.4.2.min.css"/>
		<!-- 自定义样式区域 -->
		<link rel="stylesheet" type="text/css" href="/user/css/index.css" />
		<link rel="stylesheet" type="text/css" href="/user/css/bottomNav.css"/>
	</head>

	<body>

		<div class="g-doc">
			<div class="area area1">
				
					<div class="swiper-container bannerSwiper">
						<div class="swiper-wrapper">
							<div class="swiper-slide">
								<img src="/user/img/banner.jpg"/>
							</div>
							<div class="swiper-slide">
								<img src="/user/img/banner.jpg"/>
							</div>
							<div class="swiper-slide">
								<img src="/user/img/banner.jpg"/>
							</div>
						</div>
						 <div class="swiper-pagination"></div>
					</div>
					
					<div class="a1SortBox">
						<a href="#">
							<img src="img/icon-rice.png" alt="" />
							<p>主食</p>
						</a>
						<a href="#">
							<img src="img/icon-rice.png" alt="" />
							<p>面食</p>
						</a>
						<a href="#">
							<img src="img/icon-rice.png" alt="" />
							<p>粥汤</p>
						</a>
					</div>
				
			</div>
			
			<div class="area area2">
				
					<div class="titleBox">
						<div class="inner">
							<div class="areaTitle">今日推荐<span>Today's selection</span></div>
						</div>
					</div>
					<div class="hor-scroll">
						<div class="inner ">
							<div class="fooditem">
								<div class="fooditem-pic">
									<img src="img/fooditem.jpg"/>
									<div class="price">￥18</div>
								</div>
								<p class="foodname">土豆蘑菇饭</p>
							</div>
							<div class="fooditem">
								<div class="fooditem-pic">
									<img src="img/fooditem.jpg"/>
									<div class="price">￥18</div>
								</div>
								<p class="foodname">土豆蘑菇饭</p>
							</div>
							<div class="fooditem">
								<div class="fooditem-pic">
									<img src="img/fooditem.jpg"/>
									<div class="price">￥18</div>
								</div>
								<p class="foodname">土豆蘑菇饭</p>
							</div>
							<div class="fooditem">
								<div class="fooditem-pic">
									<img src="img/fooditem.jpg"/>
									<div class="price">￥18</div>
								</div>
								<p class="foodname">土豆蘑菇饭</p>
							</div>
							<div class="fooditem">
								<div class="fooditem-pic">
									<img src="img/fooditem.jpg"/>
									<div class="price">￥18</div>
								</div>
								<p class="foodname">土豆蘑菇饭</p>
							</div>
							<div class="fooditem">
								<div class="fooditem-pic">
									<img src="img/fooditem.jpg"/>
									<div class="price">￥18</div>
								</div>
								<p class="foodname">土豆蘑菇饭</p>
							</div>
							<div class="fooditem">
								<div class="fooditem-pic">
									<img src="img/fooditem.jpg"/>
									<div class="price">￥18</div>
								</div>
								<p class="foodname">土豆蘑菇饭</p>
							</div>
						</div>
					</div>
					
				
			</div>
			
			<div class="area area3">
				
					<div class="titleBox">
						<div class="inner">
							<div class="areaTitle">明日推荐<span>Tomorrow's selection</span></div>
						</div>
					</div>
					<div class="hor-scroll">
						<div class="inner ">
							<div class="fooditem">
								<div class="fooditem-pic">
									<img src="img/fooditem.jpg"/>
									<div class="price">￥18</div>
								</div>
								<p class="foodname">土豆蘑菇饭</p>
							</div>
							<div class="fooditem">
								<div class="fooditem-pic">
									<img src="img/fooditem.jpg"/>
									<div class="price">￥18</div>
								</div>
								<p class="foodname">土豆蘑菇饭</p>
							</div>
							<div class="fooditem">
								<div class="fooditem-pic">
									<img src="img/fooditem.jpg"/>
									<div class="price">￥18</div>
								</div>
								<p class="foodname">土豆蘑菇饭</p>
							</div>
							<div class="fooditem">
								<div class="fooditem-pic">
									<img src="img/fooditem.jpg"/>
									<div class="price">￥18</div>
								</div>
								<p class="foodname">土豆蘑菇饭</p>
							</div>
							<div class="fooditem">
								<div class="fooditem-pic">
									<img src="img/fooditem.jpg"/>
									<div class="price">￥18</div>
								</div>
								<p class="foodname">土豆蘑菇饭</p>
							</div>
							<div class="fooditem">
								<div class="fooditem-pic">
									<img src="img/fooditem.jpg"/>
									<div class="price">￥18</div>
								</div>
								<p class="foodname">土豆蘑菇饭</p>
							</div>
							<div class="fooditem">
								<div class="fooditem-pic">
									<img src="img/fooditem.jpg"/>
									<div class="price">￥18</div>
								</div>
								<p class="foodname">土豆蘑菇饭</p>
							</div>
						</div>
					</div>
					
				
			</div>
			<div class="area area4">
				
					<div class="titleBox">
						<div class="inner">
							<div class="areaTitle">周五推荐<span>Day selection</span></div>
						</div>
					</div>
					<div class="hor-scroll">
						<div class="inner ">
							<div class="fooditem">
								<div class="fooditem-pic">
									<img src="img/fooditem.jpg"/>
									<div class="price">￥18</div>
								</div>
								<p class="foodname">土豆蘑菇饭</p>
							</div>
							<div class="fooditem">
								<div class="fooditem-pic">
									<img src="img/fooditem.jpg"/>
									<div class="price">￥18</div>
								</div>
								<p class="foodname">土豆蘑菇饭</p>
							</div>
							<div class="fooditem">
								<div class="fooditem-pic">
									<img src="img/fooditem.jpg"/>
									<div class="price">￥18</div>
								</div>
								<p class="foodname">土豆蘑菇饭</p>
							</div>
							<div class="fooditem">
								<div class="fooditem-pic">
									<img src="img/fooditem.jpg"/>
									<div class="price">￥18</div>
								</div>
								<p class="foodname">土豆蘑菇饭</p>
							</div>
							<div class="fooditem">
								<div class="fooditem-pic">
									<img src="img/fooditem.jpg"/>
									<div class="price">￥18</div>
								</div>
								<p class="foodname">土豆蘑菇饭</p>
							</div>
							<div class="fooditem">
								<div class="fooditem-pic">
									<img src="img/fooditem.jpg"/>
									<div class="price">￥18</div>
								</div>
								<p class="foodname">土豆蘑菇饭</p>
							</div>
							<div class="fooditem">
								<div class="fooditem-pic">
									<img src="img/fooditem.jpg"/>
									<div class="price">￥18</div>
								</div>
								<p class="foodname">土豆蘑菇饭</p>
							</div>
						</div>
					</div>
					
				
			</div>
			<div class="area area5">
				
					<div class="titleBox">
						<div class="inner">
							<div class="areaTitle">下周菜品<span>每周菜品由你来决定</span></div>
						</div>
					</div>
					<div class="compare">
						<div class="inner">
							<div class="compareItem">
								<div class="compareFood LeftFood">
									<div class="compareItem-pic">
										<img src="img/fooditem.jpg"/>
									</div>
									<div class="supportBtn">支持</div>
								</div>
								<div class="compareFood RigthFood">
									<div class="compareItem-pic">
										<img src="img/fooditem.jpg"/>
									</div>
									<div class="supportBtn">支持</div>
								</div>
								<div class="compareCenter">
									<div style="width:36%;" class="colorBar"></div>
									<div class="compareCover"></div>
								</div>
								
							</div>
							<div class="compareItem">
								<div class="compareFood LeftFood">
									<div class="compareItem-pic">
										<img src="img/fooditem.jpg"/>
									</div>
									<div class="supportBtn">支持</div>
								</div>
								<div class="compareFood RigthFood">
									<div class="compareItem-pic">
										<img src="img/fooditem.jpg"/>
									</div>
									<div class="supportBtn">支持</div>
								</div>
								<div class="compareCenter">
									<div style="width:36%;" class="colorBar"></div>
									<div class="compareCover"></div>
								</div>
								
							</div>
						</div>
					</div>
					
				
			</div>
			
			<div class="bottomNav">
				<a href="#" class="bottomNavBtn sideBtn act">
					<img src="img/icon-kuaidian.png"/>
					<p>快点</p>
				</a>
				<a href="/user/cuisine/list.do" class="bottomNavBtn bottomNavBtnCenter">
					
				</a>
				<a href="/user/usercenter.do" class="bottomNavBtn sideBtn">
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
		<script src="/user/js/swiper-3.4.2.min.js" type="text/javascript" charset="utf-8"></script>
		<script type="text/javascript">
			$(function() {
			new Swiper('.bannerSwiper',{
				pagination : '.swiper-pagination',
				effect : 'flip'
			})
				

			})
		</script>

		<!--页面脚本区E-->

	</body>

</html>