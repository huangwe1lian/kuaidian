<%@page contentType="text/html" pageEncoding="utf-8" session="false"%>
<%@include file="/WEB-INF/jspf/import.jspf"%>
<!DOCTYPE html>
<html>
<head>
<title>选菜页</title>
</head>
<body>
<form action="/user/order/create.do" method="post">
	<c:forEach var="item" items="${cuisines}">
		<%-- <input type="hidden" value="${item.id}"/> --%>
		<p>${item.name}</p>
		<p>${item.price}元</p>
		<input type="checkbox" id="${item.id}" contractor-data="${item.contractorId}" name="cuisine">
		<br/>
	</c:forEach>
	<c:forEach begin="1" end="${totalPage}" varStatus="vs">
		<a href="/user/cuisine/list.do?pageNum=${vs.count}">${vs.count}</a>
	</c:forEach>
	<br/>
	<input type="button"  onclick="pay();" value="去结算"/>
</form>
<!-- <a type="button"  href="/user/order/create.do">去结算</a> -->
</body>
<script src="http://js.3conline.com/min/temp/v1/lib-jquery1.10.2.js"></script>
<script>
	function pay(){
		var choose = $("input[name='cuisine']:checked");
		var cuisineId = choose.attr("id");
		var contractorId = choose.attr("contractor-data");
		$.ajax({
			url:'/user/order/create.do',
			data:{cuisineId:cuisineId,contractorId:contractorId},
			type:'post',
			dataType:'json',
			success:function(data){
				if(data.statusCode == 200){
					location.href = data.forwardUrl;
				}
			}
		}); 
	}
</script>
</html>