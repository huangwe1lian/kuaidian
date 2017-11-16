<%@page contentType="text/html; charset=UTF-8" session="false"%><%@include
	file="/WEB-INF/jspf/import.jspf"%><div class="pageContent">
	<form method="post"
		action="${ctx}/admin/user/${method}.do<c:if test="${method=='update'}">?id=${param.id}</c:if>"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<div class="unit"> 
				<strong>用户管理</strong>
			</div>
			<div class="divider"></div>
			<div class="unit"> 
				<label>姓名: </label>
				<input class="textInput required"
					${geli:not_oper('name') ? 'readonly="readonly"' : ''}
					value="${geli:display(entity, 'name')}" name="name" />
			</div>
			<div class="divider"></div>
			<div class="unit"> 
				<label>登录账号: </label>
				<input class="textInput required"
					${geli:not_oper('loginName') ? 'readonly="readonly"' : ''}
					value="${geli:display(entity, 'loginName')}" name="loginName" />
			</div>
			<div class="divider"></div>
			<div class="unit"> 
				<label>登录密码: </label>
				<input type="password" class="textInput" name="loginPwd" />
			</div>
			<div class="divider"></div>
			<div class="unit"> 
				<label>账号状态: </label>
				<select class="required required" name="status" >
					<option value="1" ${(1 == entity.status) ? 'selected' : '' }>正常</option>
					<option value="0" ${(0 == entity.status) ? 'selected' : '' }>禁用</option>
				</select>
			</div>
			<div class="divider"></div>
			<div class="unit"> 
				<label>是否删除: </label>
				<select class="required " name="deleted" >
					<option value="0" ${(0 == entity.deleted) ? 'selected' : '' }>否</option>
					<option value="1" ${(1 == entity.deleted) ? 'selected' : '' }>是</option>
				</select>
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
