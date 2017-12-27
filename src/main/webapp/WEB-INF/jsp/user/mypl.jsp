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
    <link rel="stylesheet" type="text/css" href="//js.3conline.com/pcauto/2016/autopocket/4sadmin/reset.css">
    <!-- 自定义样式区域 -->
    <link rel="stylesheet" type="text/css" href="/user/css/mypl.css" />
  </head>

  <body>

    <div class="g-doc">
      <div class="area area1">
        <div class="titleBox">
          <div class="inner">
            <div class="areaTitle">我的评论</div>
          </div>
        </div>
        <div>
       	<c:if test="${not empty cuisineComments}">
	       	<c:forEach items="${cuisineComments}" var="item">
		          <div class="plitem">
		            <div class="inner">
		              <a href="/user/cuisine/cuisineDetail.do?cuisineId=${geli:refer(item,'cuisineId').id}" class="pltop">
		                <img src="${geli:refer(item,'cuisineId').pic}" />
		                <div class="foodinfobox">
		                  <p class="foodname">${geli:refer(item,'cuisineId').name}</p>
		                  <p class="foodinfo">${geli:refer(item,'cuisineId').desc}</p>
		                  <p class="data"><fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd" /></p>
		                </div>
		                <div class="cpxx"><i></i><i></i><i></i></div>
		              </a>
		              <div class="pf">
		                <span>我的评分: <i class="mark1">${item.score}分</i></span>
		                <div class="startbox" data-value="${item.score}">
		                <c:forEach begin="1" end="${item.score}">
		                	<i class="icon-star act"></i>
		                </c:forEach>
		                <c:forEach begin="${item.score}" end="4">
		                	<i class="icon-star"></i>
		                </c:forEach>
		                </div>
		                <span class="fpvalue">
		                  <span style="display:none;">1星</span>
		                  <span style="display:none;">2星</span>
		                  <span style="display:none;">3星</span>
		                  <span style="display:none;">4星</span>
		
		                </span>
		              </div>
		
		            </div>
		          </div>
		      </c:forEach>
          </c:if>

          </div>

        </div>
        <!-- <div class="submitbtn">发表评价</div> -->

      </div>

    </div>

    <!--页面脚本区S-->
    <!--所有页面用到的js脚本都必须放到此位置，包括外链js和内嵌js-->
    <script src="/user/js/jquery.js" type="text/javascript" charset="utf-8"></script>
    <script src="/user/js/popup.js" type="text/javascript" charset="utf-8"></script>
    <script src="/user/js/com.js" type="text/javascript" charset="utf-8"></script>
    

    <!--页面脚本区E-->

  </body>

</html>