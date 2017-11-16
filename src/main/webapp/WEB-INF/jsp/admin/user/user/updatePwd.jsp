<%@page contentType="text/html; charset=UTF-8" session="false"%><%@include
	file="/WEB-INF/jspf/import.jspf"%><div class="pageContent">
	<form method="post" id="updatePwdForm"
		action="${ctx}/admin/updatePwd.do"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<div class="unit"> 
				<strong>修改密码</strong>
			</div>
			<div class="divider"></div>
			<div class="unit"> 
				<label>原密码: </label>
				<input class="textInput" name="oldPwd" id="oldPwd" type="password"/>
			</div>
			<div class="divider"></div>
			<div class="unit"> 
				<label>新密码: </label>
				<input class="textInput" name="newPwd" id="newPwd" type="password"/>
			</div>
			<div class="divider"></div>
			<div class="unit"> 
				<label>确认密码: </label>
				<input class="textInput" name="checkPwd" id="checkPwd" type="password"/>
			</div>
		</div>
		<div class="formBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="button" onclick="btn_submit()">保存</button>
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
</div>
<script type="text/javascript">
	function btn_submit(){
		var oldPwd = $("#oldPwd").val();
		var newPwd = $("#newPwd").val();
		var checkPwd = $("#checkPwd").val();
		if(oldPwd == ""){
			alertMsg.error("请输入原密码！");
			return false;
		} else if(newPwd == ""){
			alertMsg.error("请输入新密码！");
			return false;
		} else if (checkPwd == ""){
			alertMsg.error("请输入确认密码！");
			return false;
		} else if (newPwd != checkPwd){
			alertMsg.error("新密码与确认密码不一致，请确认！");
			return false;
		} else {
			$("#updatePwdForm", navTab.getCurrentPanel()).submit();
		}
		
	}
</script>
