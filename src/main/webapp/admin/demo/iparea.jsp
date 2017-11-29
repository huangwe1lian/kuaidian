<%@page contentType="text/html" pageEncoding="UTF-8" session="false"
%><%@include file="/WEB-INF/jspf/import.jspf"
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>登陆 - 应用账系统</title>
        <style>
        </style>
        <script type="text/javascript" src="http://js.3conline.com/js/jquery-1.4.2.min.js"></script>
        <script src="${ctx }/admin/demo/locate.js"></script>
        <script src="${ctx }/admin/demo/locate_auto.js"></script>
        
    </head>
    <body>
        <script type="text/javascript">
	        LocateToyota.init({
	        	device: 'pc', 	//pc端需要添加设备类型
	        	callback:function(data){
	        	    alert(data);
	        	}
	        });
        </script>
    </body>
</html>
