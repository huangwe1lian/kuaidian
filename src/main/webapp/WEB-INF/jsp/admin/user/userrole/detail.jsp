<%@page contentType="text/html; charset=UTF-8" session="false"%><%@include
	file="/WEB-INF/jspf/import.jspf"%><div class="pageContent">
	<form method="post"
		action="${ctx}/admin/userrole/${method}.do<c:if test="${method=='update'}">?id=${param.id}</c:if>"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<div class="unit"> 
				<strong>访问控制管理</strong>
			</div>
			<div class="divider"></div>
			<div class="unit"> 
				<label>用户ID: </label>
				<input type="hidden" name="user.userId" value="${geli:display(entity, 'userId')}">
				<input class="textInput required" readonly="readonly"
					value="${empty entity ? '' : geli:refer(entity, 'userId').name }" name="user.display" id="user" lookupGroup="user"/>
				<a class="btnLook" href="${ctx}/admin/user/select.do" lookupGroup="user">选择用户</a>
			</div>
			<div class="divider"></div>
			<div class="unit"> 
				<label>角色ID: </label>
				<input type="hidden" name="role.roleId" value="${geli:display(entity, 'roleId')}">
				<input class="textInput required" readonly="readonly"
					value="${empty entity ? '' : geli:refer(entity, 'roleId').name }" name="role.display" id="role" lookupGroup="role"/>
				<a class="btnLook" href="${ctx}/admin/role/select.do" lookupGroup="role">选择角色</a>
			</div>
		</div>
		<div class="formBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">保存</button>
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
