<%@page contentType="text/html" pageEncoding="UTF-8" session="false"%>
<%@include file="/WEB-INF/jspf/import.jspf"%>
<!DOCTYPE html>
<html>
    <head>
		<meta charset="utf-8">
	  	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	 	 <title>快点</title>
	 	<meta name="renderer" content="webkit">
	  	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	  	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	  	<meta name="apple-mobile-web-app-status-bar-style" content="black"> 
	  	<meta name="apple-mobile-web-app-capable" content="yes">
	  	<meta name="format-detection" content="telephone=no">
	  	<link rel="stylesheet" href="/admin/css/layui.css"  media="all">
    </head>
    <body>
        <c:forEach var="item" items="${cuisines}">
        ${item.name}
        </c:forEach>
    </body>
    <script src="/admin/layui.js" charset="utf-8"></script>
</html>
