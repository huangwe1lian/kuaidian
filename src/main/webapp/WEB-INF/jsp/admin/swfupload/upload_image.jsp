<%@ page contentType="text/html;charset=UTF-8" language="java" session="false"%>
<%@include file="/WEB-INF/jspf/import.jspf"%>
<%-- 上传图片公共模块 --%>
<!-- 图片上传 begin-->
<script type="text/javascript">
    var upload_url = '${upc.url}';
    var upc_referer ='${upc.referer}';
    //压缩指令
    var commands = {
        '600_400Pic':603001
    };
</script>
<script type="text/javascript" src="/admin/swfupload/js/swfupload.js"></script>
<script type="text/javascript" src="/admin/swfupload/js/handlers.js"></script>
<script type="text/javascript" src="/admin/swfupload/js/swfupload.queue.js"></script>
<script type="text/javascript" src="/admin/swfupload/js/initupload.js"></script>
<!-- 图片上传 end-->