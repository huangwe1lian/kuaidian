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
		<link rel="stylesheet" type="text/css" href="/user/css/diancan.css" />
	</head>

	<body>

		<div class="g-doc">
			<div class="area area1">
				<input class="searchinput" placeholder="输入菜品名称" type="text" name="" id="" value="" />
			</div>
			<div class="area area2">
				<div class="Listleft">
					<div class="nav act">推荐</div>
					<div class="nav">主菜</div>
					<div class="nav">副菜</div>
					<div class="nav">素菜</div>
					<div class="nav">小吃</div>
					<div class="nav">水果</div>
					<div class="nav">明日推荐</div>
					<div class="nav">后日推荐</div>
				</div>
				<div class="Listright">
					<!--tap1-->
					<div class="tapbox">
					<c:forEach var="item" items="${cuisines}" varStatus="vs">
						<div data-id="${vs.count}" class="fooditem">
							<div class="foodinner">
								<a href="/user/cuisine/cuisineDetail.do?cuisineId=${item.id}">
									<img src="${item.pic}" />
								</a>
								<div class="foodinfobox">
									<input class="foodid" type="hidden" value="${item.id}">
									<p class="foodname">${item.name}</p>
									<p class="foodinfo">${item.desc}</p>
									<p class="foodprice">¥ <span>${item.price}</span></p>
								</div>
								<div class="toCar">
									<input class="foodid2" type="hidden" value="${item.id}">
									<span class="toCarReduce"></span>
									<span class="toCarNum">0</span>
									<span class="toCarAdd"></span>
								</div>
							</div>

						</div>
					</c:forEach>
					</div>
					<!--tap1-->
					<!--tap2-->
					<div class="tapbox" style="display:none;">
						<div data-id="9" class="fooditem">
							<div class="foodinner">
								<img src="/user/img/fooditem.jpg" />
								<div class="foodinfobox">
									<p class="foodname">1土豆蘑菇饭</p>
									<p class="foodinfo">土豆+蘑菇+姜丝</p>
									<p class="foodprice">¥ <span>8</span></p>
								</div>
								<div class="toCar">

									<span class="toCarReduce"></span>
									<span class="toCarNum">0</span>
									<span class="toCarAdd"></span>
								</div>
							</div>

						</div>
						<div data-id="10" class="fooditem">
							<div class="foodinner">
								<img src="/user/img/fooditem.jpg" />
								<div class="foodinfobox">
									<p class="foodname">1土豆蘑菇饭</p>
									<p class="foodinfo">土豆+蘑菇+姜丝</p>
									<p class="foodprice">¥ <span>8</span></p>
								</div>
								<div class="toCar">
									<span class="toCarReduce"></span>
									<span class="toCarNum">0</span>
									<span class="toCarAdd"></span>
								</div>
							</div>

						</div>

					</div>
					<!--tap2-->
					<!--tap3-->
					<div class="tapbox" style="display:none;">
						<div data-id="11" class="fooditem">
							<div class="foodinner">
								<img src="/user/img/fooditem.jpg" />
								<div class="foodinfobox">
									<p class="foodname">2土豆蘑菇饭</p>
									<p class="foodinfo">土豆+蘑菇+姜丝</p>
									<p class="foodprice">¥ <span>8</span></p>
								</div>
								<div class="toCar">

									<span class="toCarReduce"></span>
									<span class="toCarNum">0</span>
									<span class="toCarAdd"></span>
								</div>
							</div>

						</div>
						<div data-id="12" class="fooditem">
							<div class="foodinner">
								<img src="/user/img/fooditem.jpg" />
								<div class="foodinfobox">
									<p class="foodname">2土豆蘑菇饭</p>
									<p class="foodinfo">土豆+蘑菇+姜丝</p>
									<p class="foodprice">¥ <span>8</span></p>
								</div>
								<div class="toCar">
									<span class="toCarReduce"></span>
									<span class="toCarNum">0</span>
									<span class="toCarAdd"></span>
								</div>
							</div>

						</div>

					</div>
					<!--tap3-->
				</div>
			</div>

			<div class="buycar act">
				<div class="buyCarnum">0</div>
				<div class="buyCarPrice">¥ <span>0</span></div>
				<a href="/user/buycar/list.do" class="buyCarSum">结算</a>
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
				if(!com.getCookie('_kd_user_buyCar_')){
					var arr=[]
				}else{
					var arr=JSON.parse(com.getCookie('_kd_user_buyCar_')).Carlist
				}
		
				init()
				function init() {
					for(var j in arr) {
						$('.fooditem').map(function(index, item) {
							if($(item).attr('data-id') == arr[j].foodid) {
								$(item).find('.toCarNum').html(arr[j].num)
								$(item).addClass('act')
							}
						})

					}
				}

				var pxa = parseFloat(window.getComputedStyle(document.documentElement)["fontSize"])
				$('.area2').css('height', $(window).height() - (2.3 * pxa) + 'px')
				$('.g-doc').css('height', $(window).height())

				$('.buycarbtn').click(function() {

					if(arr.length) {
						$('.buyCarpopup').toggle()
					} else {
						com.tips('请选择菜品')
						$('.buyCarpopup').hide()
					}

				})
				$('.fooditem .toCarAdd').click(function() {

					var num = $(this).siblings('.toCarNum').html()
					num++
					$(this).siblings('.toCarNum').html(num)
					$(this).parents('.fooditem').addClass('act')
					refreshCar()

				})

				$('.fooditem .toCarReduce').click(function() {
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

				$('.nav').click(function() {
					$(this).addClass('act').siblings().removeClass('act')
					$('.tapbox').eq($(this).index()).show().siblings().hide()

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

					for(var i = 0; i < $('.fooditem').length; i++) {
						if($('.fooditem').eq(i).attr('data-id') == nowid) {
							var num = $('.fooditem').eq(i).find('.toCarNum').html()
							num--
							if(num == 0) {
								$('.fooditem').eq(i).removeClass('act')
							}
							$('.fooditem').eq(i).find('.toCarNum').html(num)
						}
					}
					for(var i in arr) {
						if(parseInt(arr[i].foodid) == nowid) {
							arr[i].num--
								if(arr[i].num == 0) {

									arr.splice(i, 1)

								}
						}
					}
					refreshCar()
					

				})

				$('.buyCarItembox').on('click', '.toCarAdd', function() {
					var nowid = $(this).parents('.buyCarListItem').attr('data-id')

					for(var i = 0; i < $('.fooditem').length; i++) {
						if($('.fooditem').eq(i).attr('data-id') == nowid) {
							var num = $('.fooditem').eq(i).find('.toCarNum').html()
							num++
							$('.fooditem').eq(i).find('.toCarNum').html(num)
						}
					}
					for(var i in arr) {
						if(parseInt(arr[i].foodid) == nowid) {
							arr[i].num++
						}
					}
					refreshCar()

				})

				refreshCar()

				//更新购物车函数
				function refreshCar() {
					arr = []
					var totalfoodNum = 0; //总共多少份
					var totalfoddPrice = 0 //总共多少钱
					for(var i = 0; i < $('.tapbox').length; i++) {
						var nowTap = $('.tapbox').eq(i)
						var nowFoodiTem = nowTap.find('.fooditem')
						for(var j = 0; j < nowFoodiTem.length; j++) {

							if(nowFoodiTem.eq(j).find('.toCarNum').html() > 0) {
								arr.push({
									foodid: nowFoodiTem.eq(j).attr('data-id'),
									foodname: nowFoodiTem.eq(j).find('.foodname').html(),
									price: nowFoodiTem.eq(j).find('.foodprice span').html(),
									num: parseInt(nowFoodiTem.eq(j).find('.toCarNum').html())
								})
								totalfoodNum += parseInt(nowFoodiTem.eq(j).find('.toCarNum').html())
								totalfoddPrice += (nowFoodiTem.eq(j).find('.toCarNum').html() * nowFoodiTem.eq(j).find('.foodprice span').html())
							}

						}
						if(arr.length == 0) {
							$('.buyCarpopup').hide()
						}
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
					
					

					
					com.setCookie('_kd_user_buyCar_', JSON.stringify({Carlist:arr}))
				}

				$('.toCar').click(function() {
					
				})

			})
		</script>

		<!--页面脚本区E-->

	</body>

</html>