<%@page contentType="text/html; charset=UTF-8" session="false"%><%@include
	file="/WEB-INF/jspf/import.jspf"%><div class="pageContent">
	<form method="post" id="mediaNewsForm"
		action="${ctx}/admin/ad/dealer/recommend.do?id=${entity.id}"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<div class="unit"> 
				<strong>推荐媒体资讯</strong>
			</div>
			<div class="divider"></div>
			<div class="unit"> 
				<label>首页: </label>
				<select class="required " name="position1" id="position1" onchange="recommendChange()">
					<option value="0">无</option>
					<option value="1" ${(1 == position1.position) ? 'selected' : '' }>首页本地活动</option>
				</select>
			</div>
			<div class="divider"></div>
			<div class="unit"> 
				<label>车系页: </label>
				<select class="required " name="position2" id="position2" onchange="recommendChange()">
					<option value="0">无</option>
					<option value="2" ${(2 == position2.position) ? 'selected' : '' }>车系页本地活动</option>
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
