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
		<link rel="stylesheet" type="text/css" href="/user/css/pl.css" />
	</head>

	<body>

		<div class="g-doc">
			<div class="area area1">
				<div class="titleBox">
					<div class="inner">
						<div class="areaTitle">订单信息</div>
					</div>
				</div>
				<div>
				<form id="form" action="/user/comment/create.do?orderId=${orderId}" method="post">
					<c:if test="${not empty cuisines}">
						<c:forEach items="${cuisines}" var="item" varStatus="vs">
							<div class="plitem">
								<input type="hidden" value="${item.id}" name="cuisineId${vs.count}">
								<div class="inner">
									<a href="#" class="pltop">
										<img src="${item.pic}" />
										<div class="foodinfobox">
											<p class="foodname">${item.name}</p>
											<p class="foodinfo">${item.desc}</p>
										</div>
										<div class="cpxx" onclick="link(${item.id});">菜品详情 ></div>
									</a>
									<div class="pf">
										<span>评分:</span>
										<div class="startbox" data-value="5">
											<i class="icon-star act"></i>
											<i class="icon-star act"></i>
											<i class="icon-star act"></i>
											<i class="icon-star act"></i>
											<i class="icon-star act"></i>
										</div>
										<span class="fpvalue">
											<span style="display:none;">1星</span>
											<span style="display:none;">2星</span>
											<span style="display:none;">3星</span>
											<span style="display:none;">4星</span>
											<span>哎哟~不错哦</span>
										</span>
									</div>
									<div class="txtbox">
										<textarea name="text${vs.count}" placeholder="请填写您的评价~最多100字" class="txt" ></textarea>
									</div>
								</div>
							</div>
						</c:forEach>
					</c:if>
					</div>
					<div class="submitbtn" onclick="doSubmit();">发表评价</div>
				</form>
			</div>

		</div>

		<!--页面脚本区S-->
		<!--所有页面用到的js脚本都必须放到此位置，包括外链js和内嵌js-->
		<script src="/user/js/jquery.js" type="text/javascript" charset="utf-8"></script>
		<script src="/user/js/popup.js" type="text/javascript" charset="utf-8"></script>
		<script src="/user/js/com.js" type="text/javascript" charset="utf-8"></script>
		<script type="text/javascript">
			$('.icon-star').click(function(){
				$(this).parents('.pf').find('.fpvalue span').eq($(this).index()).show().siblings().hide()
				$(this).parent().attr('data-value',$(this).index());
				var score=$(this).index()
				var oi=$(this).parent().find('i')
				oi.map(function(index,item){
					if(index<=score){
						oi.eq(index).addClass('act')
					}else{
						oi.eq(index).removeClass('act')
					}
				})
			})
			function link(cuisineId){
				location.href="/user/cuisine/cuisineDetail.do?cuisineId="+cuisineId;
			}
			
			function doSubmit(){
				$("#form").submit();
			}
		</script>

		<!--页面脚本区E-->

	</body>

</html>