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
		<link rel="stylesheet" type="text/css" href="/user/css/item.css" />
	</head>

	<body>

		<div class="g-doc">
			<div class="area area1">
				<div class="a1picbox">
					<img src="/user/img/fooditem.jpg" />
				</div>
				<div class="a1infobox" data-id="${cuisine.id}">
					<div class="a1infoline">
						<div class="foodname">${cuisine.name}</div>
						<div class="foodstar">
							<c:forEach begin="1" end="${cuisine.score}">
								<i class="icon-star act"></i>
							</c:forEach>
							<c:forEach begin="${cuisine.score}" end="4">
								<i class="icon-star"></i>
							</c:forEach>
						</div>
					</div>
					<div class="a1infoline">
						<div class="foodmakeby">${cuisine.desc}</div>
						<div class="foodsalenum">
							月售81份
						</div>
					</div>
					<div class="a1infoline">
						<div class="foodprice">¥<span>${cuisine.price}</span></div>
						<div class="foodctr">
							<div class="toCar">
								<span class="toCarReduce"></span>
								<span class="toCarNum">0</span>
								<span class="toCarAdd"></span>
							</div>
						</div>
					</div>
				</div>
			</div>
			
			<c:if test="${!empty comments}"> 
				<div class="area area2">
					<div class="titleBox">
						<div class="inner">
							<div class="areaTitle">菜品评价</div>
						</div>
					</div>
					<div>
						<c:forEach items="${comments}" var="item"> 
							<div class="plitem">
								<div class="inner">
									<div class="headbox">
										<img src="/user/img/headerpic.jpg" />
									</div>
									<div class="infobox">
										<p class="plname">${geli:refer(item,'userId').name} <span><fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd" /></span></p>
										<div class="plstar">打分
											<div class="foodstar">
												<c:forEach begin="1" end="${item.score}">
													<i class="icon-star act"></i>
												</c:forEach>
												<c:forEach begin="${item.score}" end="4">
													<i class="icon-star"></i>
												</c:forEach>
											</div>
										</div>
										<p class="plmain">${item.text}</p>
									</div>
								</div>
							</div>
						</c:forEach>
					</div>
				</div>
			</c:if>
			<div class="buycar act">
				<div class="buyCarnum">0</div>
				<div class="buyCarPrice">¥ <span>0</span></div>
				<a href="/user/buycar/list.do" class="buyCarSum">去结算</a>
				<div class="buycarbtn"></div>
			</div>
			<div class="buyCarpopup" style="display:none;">
				<div class="mask"></div>
				<div class="buyCarList">
					<div class="buyCarListTitle">已选菜式</div>
					<div class="buyCarItembox">

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
			$(function() {
				if(!com.getCookie('arr')) {
					var arr = []
				} else {
					var arr = JSON.parse(com.getCookie('arr')).Carlist
				}
				init()

				function init() {
					for(var j in arr) {
						if($('.a1infobox').attr('data-id') == arr[j].foodid) {
							$('.a1infobox').find('.toCarNum').html(arr[j].num)
						}
					}
				}

				$('.buycarbtn').click(function() {

					if(arr.length) {
						$('.buyCarpopup').toggle()
					} else {
						com.tips('请选择菜品')
						$('.buyCarpopup').hide()
					}

				})
				$('.area1 .toCarAdd').click(function() {

					var num = $(this).siblings('.toCarNum').html()
					num++
					$(this).siblings('.toCarNum').html(num)
					$(this).parents('.fooditem').addClass('act')
					refreshCar()

				})

				$('.area1 .toCarReduce').click(function() {
					var num = $(this).siblings('.toCarNum').html()
					if(num > 0) {
						num--
						if(num == 0) {
							$(this).parents('.fooditem').removeClass('act')
						}
					}
					$(this).siblings('.toCarNum').html(num)
					refreshCar()
				})

				$('.mask').click(function() {
					$('.buyCarpopup').hide()
				})
				$('.buyCarSum').click(function() {
					//结算按钮
					if($('.buycar').hasClass('act')) {
						com.tips('正在结算')
					} else {
						com.tips('请选择菜品')
					}

				})

				$('.buyCarItembox').on('click', '.toCarReduce', function() {
					var nowid = $(this).parents('.buyCarListItem').attr('data-id')
					var haveitem = false
					var num = $(this).parents('.buyCarListItem').find('.toCarNum').html()
					num--
					for(var j in arr) {
						if(nowid == arr[j].foodid) {
							haveitem = true
							arr[j].num--
								if(arr[j].num > 0) {
									$(this).parents('.buyCarListItem').find('.toCarNum').html(arr[j].num)
									if(nowid==$('.a1infobox').attr('data-id')){
								$('.a1infobox .toCarNum').html(arr[j].num)
							}
								} else {
									arr.splice(j, 1)
									if(nowid==$('.a1infobox').attr('data-id')){
								$('.a1infobox .toCarNum').html(0)
							}
								}
						}
					}
					refreshCar()

				})

				$('.buyCarItembox').on('click', '.toCarAdd', function() {
					var nowid = $(this).parents('.buyCarListItem').attr('data-id')
					var haveitem = false
					for(var j in arr) {
						if(nowid == arr[j].foodid) {
							haveitem = true
							arr[j].num = parseInt(arr[j].num) + 1
							$(this).parents('.buyCarListItem').find('.toCarNum').html(arr[j].num)
							if(nowid==$('.a1infobox').attr('data-id')){
								$('.a1infobox .toCarNum').html(arr[j].num)
							}
						}
					}
					refreshCar()

				})

				refreshCar()

				//更新购物车函数
				function refreshCar() {

					var totalfoodNum = 0; //总共多少份
					var totalfoddPrice = 0 //总共多少钱
					var haveitem = false
					for(var j in arr) {

						if($('.a1infobox').attr('data-id') == arr[j].foodid) {
							haveitem = true
							arr[j].num = $('.area1 .toCarNum').html()
							if(arr[j].num == 0) {
								arr.splice(j, 1)
							}
						}

					}

					if(!haveitem && $('.area1 .toCarNum').html() > 0) {
						arr.push({
							foodid: $('.a1infobox').attr('data-id'),
							foodname: $('.a1infobox .foodname').html(),
							price: $('.a1infobox .foodprice span').html(),
							num: parseInt($('.area1 .toCarNum').html())
						})
					}

					for(var j in arr) {
						totalfoodNum += parseInt(arr[j].num)
						totalfoddPrice += (parseInt(arr[j].num) * parseFloat(arr[j].price))
					}

					if(arr.length == 0) {
						$('.buyCarpopup').hide()
					}

					if(totalfoodNum == 0) {
						$('.buycar').removeClass('act')
					} else {
						$('.buycar').addClass('act')
					}
					console.log(arr)
					$('.buyCarPrice span').html(totalfoddPrice)
					$('.buyCarnum').html(totalfoodNum)
					refreshList()

				}
				//更新购买的菜单
				function refreshList() {
					var str = ''
					for(var i in arr) {

						str += '<div data-id="' + arr[i].foodid + '" class="buyCarListItem">'
						str += '<span class="buyCarItemName">' + arr[i].foodname + '</span>'
						str += '<span class="buyCarItemPrice">¥ <i>' + arr[i].price * arr[i].num + '</i></span>'
						str += '<div class="toCar">'
						str += '<span class="toCarReduce"></span>'
						str += '<span class="toCarNum">' + arr[i].num + '</span>'
						str += '<span class="toCarAdd"></span>'
						str += '</div>'
						str += '</div>'

					}
					$('.buyCarItembox').html(str)

					com.setCookie('arr', JSON.stringify({
						Carlist: arr
					}))
				}

				$('.toCar').click(function() {

				})

			})
		</script>

		<!--页面脚本区E-->

	</body>

</html>