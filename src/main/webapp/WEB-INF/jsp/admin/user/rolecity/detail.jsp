<%@page contentType="text/html; charset=UTF-8" session="false"%><%@include
	file="/WEB-INF/jspf/import.jspf"%><div class="pageContent">
	<form method="post"
		action="${ctx}/admin/rolecity/${method}.do<c:if test="${method=='update'}">?id=${param.id}</c:if>"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<div class="unit"> 
				<strong>角色城市管理</strong>
			</div>
			<div class="divider"></div>
			<div class="unit"> 
				<label>角色ID: </label>
				<input type="hidden" name="role.roleId" value="${geli:display(entity, 'roleId')}">
				<input class="textInput required" readonly="readonly"
					value="${empty entity ? '' : geli:refer(entity, 'roleId').name }" name="role.display"/>
				<a class="btnLook" href="${ctx}/admin/role/select.do" lookupGroup="role">选择角色</a>
			</div>
			<div class="divider"></div>
			<div class="unit"> 
				<label>城市: </label>
				<select class="required" name="provinceId" id="provinceId" onchange="getCity()"> 
					<option value="" >请选择省份</option>
				</select>
				<select class="required" name="cityId" id="cityId">
					<option value="">请选择城市</option>
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

<script type="text/javascript">
	$(function(){
		getProvince();
	});
	
	var pid = '${entity.provinceId}';
	var cid = '${entity.cityId}';
	var isStart = 1;
	
	function getProvince(){
		$.getScript("/api/province/list?callback=setProvince");
	}
	
	function setProvince(data){
		var options = '<option value="" >请选择省份</option><option value="0" >全国</option>';
		for(var i=0;i < data.length;i++){
			options += '<option value="' + data[i].pId + '" >' + data[i].name + '</option>'
		}
		$("#provinceId").html(options);
		if(isStart){
			$("#provinceId").val(pid);
			getCity();
		}
		
	}
	
	function getCity(){
		var pid = $("#provinceId").val();
		$.getScript("/api/city/list?callback=setCity&pid=" +pid);
	}
	
	function setCity(data){
		var pid = $("#provinceId").val();
		var options = '<option value="" >请选择城市</option>';
		if(pid > 0){
			options += '<option value="0" >全省市</option>';
		}
		for(var i=0;i < data.length;i++){
			options += '<option value="' + data[i].cId + '" >' + data[i].name + '</option>'
		}
		$("#cityId").html(options);
		if(isStart > 0) {
			$("#cityId").val(cid);
			isStart = 0;
		}
	}
</script>
