<%@page contentType="text/html; charset=UTF-8" session="false"%><%@include
	file="/WEB-INF/jspf/import.jspf"%><form id="pagerForm"
	action="${ctx}/admin/user/select.do" method="get">
	<input type="hidden" name="_p" value="${_p}" /> <input type="hidden"
		name="pageNum" value="${pageNum}" /> <input type="hidden"
		name="numPerPage" value="${numPerPage}" /> <input type="hidden"
		name="orderField" value="${orderField}" /> <input type="hidden"
		name="orderDirection" value="${orderDirection}" />
</form>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="delete"
				href="javascript:$.bringBack({id:'',display:''});"><span>清空</span></a></li>
		</ul>
	</div>
	<table class="table" targetType="dialog" style="width: 100%"
		layoutH="75">
		<thead>
			<tr>
				<th orderField="id"
					class="${orderField=='id' ? orderDirection : ''}">用户ID</th>
				<th orderField="name"
					class="${orderField=='name' ? orderDirection : ''}">姓名</th>
				<th orderField="loginName"
					class="${orderField=='loginName' ? orderDirection : ''}">登录账号</th>
				<th orderField="status"
					class="${orderField=='status' ? orderDirection : ''}">账号状态</th>
				<th orderField="deleted"
					class="${orderField=='deleted' ? orderDirection : ''}">是否删除</th>
				<th>查找带回</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="entity" items="${list}">
				<tr target="id" rel="${geli:oid(entity)}">
					<td>${geli:display(entity,'id')}</td>
					<td>${geli:display(entity,'name')}</td>
					<td>${geli:display(entity,'loginName')}</td>
					<td>${entity.status == 0 ? '禁用' : '正常' }</td>
					<td>${entity.deleted == 0 ? '否' : '是' }</td>
					<td><a class="btnSelect"
						href="javascript:$.bringBack({userId:'${entity.id}',display:'${geli:display(entity, 'name')}'})"
						title="查找带回">选择</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
<div class="panelBar">
	<div class="pages">
		<select class="combox" name="numPerPage"
			onchange="dialogPageBreak({numPerPage:this.value})"><option
				${numPerPage == 20 ? "selected " : ""} value="20">20</option>
			<option ${numPerPage == 50 ? "selected " : ""} value="50">50</option>
			<option ${numPerPage == 100 ? "selected " : ""} value="100">100</option>
			<option ${numPerPage == 200 ? "selected " : ""} value="200">200</option></select><span>共
			${total} 条</span>
	</div>
	<div class="pagination" targetType="dialog" totalCount="${total}"
		numPerPage="${numPerPage}" pageNumShown="10" currentPage="${pageNum}"></div>
</div>
