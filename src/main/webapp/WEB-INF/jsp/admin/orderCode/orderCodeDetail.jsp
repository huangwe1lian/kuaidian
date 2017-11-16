<%@page contentType="text/html; charset=UTF-8" session="false"%>
<%@include file="/WEB-INF/jspf/import.jspf"%>
<div class="pageContent">
	<form method="post"
		action="${ctx}/admin/ordercode/${method}.do<c:if test="${method=='update'}">?id=${param.id}</c:if>"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<div class="unit">
				<label>授权代码说明: </label>
				<input style="width:500px" class="textInput" ${geli:not_oper('description') ? 'readonly="readonly"' : ''}
					value="${geli:display(entity, 'description')}" name="description" />
			</div>
			<div class="divider"></div>
			<div class="unit">
				<label>相关专题、活动链接: </label>
				<input style="width:500px" class="textInput" ${geli:not_oper('url') ? 'readonly="readonly"' : ''}
					value="${geli:display(entity, 'url')}" name="url" />
			</div>
			<div class="divider"></div>
			<div class="unit">
				<label>状态: </label>
				<select class="required " name="status" >
					<option value="1" ${(1 == status) ? 'selected' : '' }>正常</option>
					<option value="0" ${(0 == status) ? 'selected' : '' }>禁用</option>
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
