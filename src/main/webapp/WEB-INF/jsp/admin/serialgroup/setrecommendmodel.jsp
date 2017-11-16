<%@page contentType="text/html; charset=UTF-8" session="false"%>
<%@include	file="/WEB-INF/jspf/import.jspf"%>
<div class="pageContent">
	<div class="pageFormContent" layoutH="56">
		<div class="unit"> 
			<strong>推荐车型</strong>
		</div>
		<div class="divider"></div>
		<div class="unit">
			<select id="hotModel" name="hotModelId">
				<option value="-1">请选择车型</option>
				<c:forEach items="${models}" var="model">
					<option	value="${model.id}">${model.name }(指导价：${model.price }万)</option>
				</c:forEach>
			</select>
		</div>
	</div>
	<div class="formBar">
		<ul>
			<li>
				<div class="button">
					<div class="buttonContent">
						<button type="button" class="close" onclick="save(${sgid});">保存</button>
					</div>
				</div>
			</li>
		</ul>
	</div>
</div>
<script type="text/javascript">
	function save(sgid){
		var hotModelId = $('#hotModel option:selected').val();
		window.location.href = "${ctx}/admin/serialgroup/saveRecommendModel.do?sgid="+sgid+"&modelId="+hotModelId;
	}
</script>
