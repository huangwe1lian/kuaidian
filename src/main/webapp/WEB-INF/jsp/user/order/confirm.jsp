<%@page contentType="text/html" pageEncoding="utf-8" session="false"%>
<%@include file="/WEB-INF/jspf/import.jspf"%>
<!DOCTYPE html>
<html>
<head>
<title>订单确认页</title>
</head>
<body>
订单信息如下：
订单号：${order.number}
总价：${order.price}
有以下菜式：
<c:forEach items="${cuisines}" var="item" varStatus="vs">
${vs.count}.${item.name},价格：${item.price}
</c:forEach>

<a href="/action/alipay.jsp">去付款</a>
</body>
</html>