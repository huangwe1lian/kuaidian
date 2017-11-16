<%@ page contentType="text/html;charset=UTF-8" language="java" session="false"%>
<%@include file="/WEB-INF/jspf/import.jspf"%>
<html>
<head>
    <title>图片上传Demo</title>
</head>
<body>
<script src="http://js.3conline.com/min/temp/v1/lib-jquery-1.5.min.js"></script>
<jsp:include page="/uploadImage"/>
<div id="imageTest">
    <p>上传图片：Tips:点击上传图片文字下方触发上传</p>
    <div id="placeholderBtn"></div>
    <div id="showImageBlock">
        <ul id="div_uploadImg"></ul>
    </div>
</div>
<script>
    //单指令上传
    initSWFObj("placeholderBtn", "showImageBlock", "div_uploadImg", commands["600_400Pic"], "120", "90", "200", "100", uploadCallback);

    //多指令上传
    //initMultiCommandSWFObj("placeholderBtn", "showImageBlock", "div_uploadImg", "command=603001&command=603001", "120", "90", "200", "100", uploadCallback);

    function uploadCallback() {
        var imgUrl = $("#uploadImg").val();
        console.log(imgUrl)
    }
</script>
</body>
</html>
