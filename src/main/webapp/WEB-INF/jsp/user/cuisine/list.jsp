<%@page contentType="text/html" pageEncoding="utf-8" session="false"%>
<%@include file="/WEB-INF/jspf/import.jspf"%>
<!DOCTYPE html>
<html>
<head>
<title>选菜页</title>
</head>
<body>
<c:forEach var="item" items="${cuisines}">
	<input type="hidden" value="${item.id}"/>
	<p>${item.name}</p>
	<p>${item.price}元</p>
	<input type="checkbox" name="cuisine">
	<br/>
</c:forEach>
<c:forEach begin="1" end="${totalPage}" varStatus="vs">
	<a href="/user/cuisine/list.do?pageNum=${vs.count}">${vs.count}</a>
</c:forEach>
<br/>
<input type="button"  onclick="pay();" value="去结算"/>
</body>
<script type="text/javascript" src="/admin/layui.js"></script>
<script>
	function pay(){
		$.ajax({
			url:'',
			data:'',
			type:'post',
			dataType:'json',
			success:function(data){
				
			}
		});
	}
</script>
</html>