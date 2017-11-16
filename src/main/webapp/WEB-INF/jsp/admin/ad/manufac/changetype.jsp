<%@page contentType="text/html; charset=UTF-8" session="false"%><%@include
	file="/WEB-INF/jspf/import.jspf"%><div class="pageContent">
	<style>
		ul .liclass{
			clear:right;
			float:left;
			margin:10px;
			width:250px;
			height:100px;
			border: thin;
			border-style: dotted;
		}
		ul .divclass{
			padding:20px 28px;
			text-align: center;
		}
		.divclass p{
			width:200px;
		}
	</style>
	<form class="pageForm required-validate">
		<div class="pageFormContent" layoutH="56">
			<div class="unit"> 
				<strong>请选择广告类型</strong>
			</div>
			<div class="unit"> 
				<ul>
					<li class="liclass">
						<a onclick="buttonclick(1)" href="javascript:;" title="发布展示型广告">
							<div class="divclass">
								<p>展示型</p>
								<p>广告无需填写URL</p>
							</div>
						</a>
					</li>
					<li class="liclass">
						<a onclick="buttonclick(2)" href="javascript:;"  title="发布专题型广告">
							<div class="divclass">
								<p>专题型</p>
								<p>广告需要填写URL</p>
							</div>
						</a>
					</li>
				</ul>
			</div>
		</div>
	</form>
</div>
<script type="text/javascript">
function buttonclick(type){
	if(type == 1){
		navTab.openTab("ad-manufac-create", "/admin/ad/manufac/create.do?type="+type, { title:"发布展示型广告"});
	} else {
		navTab.openTab("ad-manufac-create", "/admin/ad/manufac/create.do?type="+type, { title:"发布专题型广告"});
	}
    
    closeUploadDialog();
}
//上传图片弹窗关闭
function closeUploadDialog(){
	$.pdialog.closeCurrent();
}
</script>