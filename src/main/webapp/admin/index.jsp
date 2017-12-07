<%@page contentType="text/html" pageEncoding="utf-8" session="false"
%><%@include file="/WEB-INF/jspf/import.jspf"
%><%
/* User user = AdminSecurity.getCurrentUser(request);
AdminAuthFacade authFacade = EnvUtils.getEnv().getBean(AdminAuthFacade.class);
if (user == null) {
    response.sendRedirect("login.jsp");
    return;
}  */

/* pageContext.setAttribute("isAdmin", authFacade.isAdmin());
pageContext.setAttribute("_USER_", user); */

%>
<!DOCTYPE html>
<html>
<head>
  <jsp:include page="/admin/header.jsp"></jsp:include>
</head>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
  <jsp:include page="/admin/header1.jsp"></jsp:include>
  <div class="layui-body">
    <!-- 内容主体区域 -->
   <!--  <div style="padding: 15px;">
      内容主体区域
      
      <br><br>
      <blockquote class="layui-elem-quote">
        layui 之所以赢得如此多人的青睐，更多是在于它“前后台系统通吃”的能力。既可编织出绚丽的前台页面，又可满足繁杂的后台功能需求。
        <br>layui 后台布局， 致力于让每一位开发者都能轻松搭建自己的后台模板。
      </blockquote>
      
      <a href="/doc/element/layout.html#admin" target="_blank" class="layui-btn layui-btn-big">获取该布局代码</a>
      
      <br><br><br><br>
      下面是充数内容，为的是出现滚动条<br>充数内容<br>充数内容<br>充数内容<br>充数内容<br>充数内容<br>充数内容<br>充数内容<br>充数内容<br>充数内容<br>充数内容<br>充数内容<br>充数内容<br>充数内容<br>充数内容<br>充数内容<br>充数内容<br>充数内容<br>充数内容<br>充数内容<br>充数内容<br>充数内容<br>充数内容<br>充数内容<br>充数内容<br>充数内容<br>充数内容<br>充数内容<br>充数内容<br>充数内容<br>充数内容<br>充数内容<br>充数内容<br>充数内容<br>充数内容<br>充数内容<br>充数内容<br>充数内容<br>充数内容<br>充数内容<br>充数内容<br>充数内容<br>充数内容<br>充数内容<br>充数内容<br>充数内容<br>充数内容<br>充数内容<br>充数内容<br>充数内容<br>充数内容<br>
    </div> -->
  </div>
  <jsp:include page="/admin/footer.jsp"></jsp:include>
</div>
<jsp:include page="/admin/footer1.jsp"></jsp:include>
</body>
</html>

