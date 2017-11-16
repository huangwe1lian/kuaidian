<%@page contentType="text/html; charset=UTF-8" session="false"%>
<%@include file="/WEB-INF/jspf/import.jspf"%>
<form id="pagerForm" action="${ctx}/admin/rolecity/list.do"  method="get">
	<input type="hidden" name="_p" value="${_p}" /> 
	<input type="hidden" name="pageNum" value="${pageNum}" /> 
	<input type="hidden" name="numPerPage" value="${numPerPage}" /> 
	<input type="hidden" name="orderField" value="${orderField}" /> 
	<input type="hidden" name="orderDirection" value="${orderDirection}" />
	<input type="hidden" name="roleId" value="${param.roleId}" />
	<input type="hidden" name="provinceId" value="${param.provinceId}" />
	<input type="hidden" name="cityId" value="${param.cityId}" />
</form>
<div class="pageHeader">
    <form method="post" action="${ctx}/admin/rolecity/list.do"  class="pageForm required-validate" onsubmit=" return navTabSearch(this)">
    <input type="hidden" name="pageNum" value="${pageNum}" />
	<input type="hidden" name="numPerPage" value="${numPerPage }" />
	<input type="hidden" name="orderField" value="${orderField}" />
	<input type="hidden" name="orderDirection" value="${orderDirection}" />
        <div class="pageFormContent">
            	<p>
                    <label>角色ID:</label>
                    <input class="textInput digits valid" name="roleId" value="${param.roleId}" type="text">
                </p>
                <p>
					<label>城市: </label>
					<select name="provinceId" id="oprovinceId" onchange="getCity()"> 
						<option value="" >请选择省份</option>
					</select>
					<select name="cityId" id="ocityId">
						<option value="">请选择城市</option>
					</select>
				</p>
            <div class="subBar">
                <ul>
                    <li><div class="buttonActive"><div class="buttonContent"><button type="submit" >检索</button></div></div></li>
                </ul>
            </div>
        </div>
    </form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="${ctx}/admin/rolecity/create.do"
				target="navTab" title="添加角色-功能表"><span>添加</span></a></li>
			<li><a class="delete"
				href="${ctx}/admin/rolecity/delete.do?id={id}" target="ajaxTodo"
				title="确定要删除吗?"><span>删除</span></a></li>
			<li><a class="edit"
				href="${ctx}/admin/rolecity/update.do?id={id}" target="navTab"
				title="修改角色-功能表"><span>修改</span></a></li>
			<li class="line">line</li>
		</ul>
	</div>
	<table class="table" style="width: 100%" layoutH="138">
		<thead>
			<tr>
				<th orderField="id"
					class="${orderField=='id' ? orderDirection : ''}">角色-城市ID</th>
				<th orderField="roleId"
					class="${orderField=='roleId' ? orderDirection : ''}">角色</th>
				<th orderField="provinceId"
					class="${orderField=='provinceId' ? orderDirection : ''}">省份</th>
				<th orderField="cityId"
					class="${orderField=='cityId' ? orderDirection : ''}">城市</th>
				<th orderField="createTime"
					class="${orderField=='createTime' ? orderDirection : ''}">创建时间</th>
				<th orderField="updateTime"
					class="${orderField=='updateTime' ? orderDirection : ''}">更新时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="entity" items="${list}">
				<tr target="id" rel="${geli:oid(entity)}">
					<td>${geli:display(entity,'id')}</td>
					<td>${geli:refer(entity, 'roleId').name }</td>
					<td>${geli:refer(entity, 'provinceId').name }${entity.provinceId == 0 ? '全国' : '' }</td>
					<td>${geli:refer(entity, 'cityId').name }</td>
					<td>${geli:display(entity,'createTime')}</td>
					<td>${geli:display(entity,'updateTime')}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
<div class="panelBar">
	<div class="pages">
		<select class="combox" name="numPerPage"
			onchange="navTabPageBreak({numPerPage:this.value})"><option
				${numPerPage == 20 ? "selected " : ""} value="20">20</option>
			<option ${numPerPage == 50 ? "selected " : ""} value="50">50</option>
			<option ${numPerPage == 100 ? "selected " : ""} value="100">100</option>
			<option ${numPerPage == 200 ? "selected " : ""} value="200">200</option></select><span>共
			${total} 条</span>
	</div>
	<div class="pagination" targetType="navTab" totalCount="${total}"
		numPerPage="${numPerPage}" pageNumShown="10" currentPage="${pageNum}"></div>
</div>

<script type="text/javascript">
	$(function(){
		getProvince();
	});
	
	var pid = '${param.provinceId}';
	var cid = '${param.cityId}';
	var isStart = 1;
	
	function getProvince(){
		$.getScript("/api/province/list?callback=setProvince");
	}
	
	function setProvince(data){
		var options = '<option value="" >请选择省份</option><option value="0" >全国</option>';
		for(var i=0;i < data.length;i++){
			options += '<option value="' + data[i].pId + '" >' + data[i].name + '</option>'
		}
		$("#oprovinceId").html(options);
		if(isStart){
			$("#oprovinceId").val(pid);
			getCity();
		}
		
	}
	
	function getCity(){
		var pid = $("#oprovinceId").val();
		$.getScript("/api/city/list?callback=setCity&pid=" +pid);
	}
	
	function setCity(data){
		var pid = $("#oprovinceId").val();
		var options = '<option value="" >请选择城市</option>';
		if(pid >= 0){
			options += '<option value="0" >全省市</option>';
		}
		for(var i=0;i < data.length;i++){
			options += '<option value="' + data[i].cId + '" >' + data[i].name + '</option>'
		}
		$("#ocityId").html(options);
		if(isStart > 0) {
			$("#ocityId").val(cid);
			isStart = 0;
		}
	}
</script>
