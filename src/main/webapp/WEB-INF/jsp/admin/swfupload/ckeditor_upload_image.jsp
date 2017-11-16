<%@ page contentType="text/html;charset=UTF-8" language="java" session="false"%>
<%@include file="/WEB-INF/jspf/import.jspf"%>
	<style>
		.photo-btns .swfupload {
		    background: url(/admin/swfupload/images/img.png) no-repeat;
		    background-position: 0 0;
		}
	</style>
<jsp:include page="/uploadImage"/>
<form class="pageForm required-validate">
	<div class="pageFormContent" layoutH="56">
		<div id="imageTest">
		    <div>
		    	<span class="photo-btns">
					<a href="javascript:;" class="updata-pic"  id="placeholderBtn">上传图片</a>
				</span>
			    <div class="imgPlaceholder" id="showImageBlock">
			        <ul id="div_image">
			        	
			        </ul>
			    </div>    
		    </div>
		</div>
	</div>
	<div class="formBar">
		<ul>
			<li>
				<div class="buttonActive">
					<div class="buttonContent">
						<button type="button" onclick="btn_insert()">插入</button>
					</div>
				</div>
			</li>
			<li>
				<div class="button">
					<div class="buttonContent">
						<button type="button" class="close">取消</button>
					</div>
				</div>
			</li>
		</ul>
	</div>
</form>
<<script type="text/javascript">
	function btn_insert(){
		var pic = $("#image").val();
		if(pic != ""){
			var html ='<img src="' + pic + '"/>';
			window.insertImg(html,'${id}');
		}
		window.closeUploadDialog();
	}
</script>
<script>
    //单指令上传
    initSWFObj("placeholderBtn", "showImageBlock", "div_image", commands["600_400Pic"], "160", "120", "150", "40", uploadCallback);

    //多指令上传
    //initMultiCommandSWFObj("placeholderBtn", "showImageBlock", "div_pic", commands["600_400Pic"], "160", "120", "150", "40", uploadCallback);

    function uploadCallback() {
        var imgUrl = $("#uploadImg").val();
        console.log(imgUrl)
    }
</script>
