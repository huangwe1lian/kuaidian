<%@page contentType="text/html; charset=UTF-8" session="false"%><%@include
	file="/WEB-INF/jspf/import.jspf"%><div class="pageContent">
	<form class="pageForm required-validate">
		<div class="pageFormContent" layoutH="56">
			<div class="unit"> 
				<strong>审核不通过原因</strong>
			</div>
			<div class="unit"> 
				${entity.auditMemo }
			</div>
		</div>
		<div class="formBar">
			<ul>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="close">确定</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>
</div>
