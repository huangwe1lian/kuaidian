<%@page contentType="text/html" pageEncoding="utf-8" session="false"%>
<%@include file="/WEB-INF/jspf/import.jspf"%>
<!DOCTYPE html>
<html>
<head>
  <jsp:include page="/admin/header.jsp"></jsp:include>
</head>
<body>
<c:forEach var="item" items="${cuisines}">
	<p>${item.name}</p><p>${item.price}元</p><br/>
</c:forEach>

<jsp:include page="/admin/footer1.jsp"></jsp:include>
</body>
</html>

