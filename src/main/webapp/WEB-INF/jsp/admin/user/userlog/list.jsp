<%@page contentType="text/html; charset=UTF-8" session="false"%><%@include
	file="/WEB-INF/jspf/import.jspf"%><form id="pagerForm" method="get">
	<input type="hidden" name="_p" value="${_p}" /> <input type="hidden"
		name="pageNum" value="${pageNum}" /> <input type="hidden"
		name="numPerPage" value="${numPerPage}" /> <input type="hidden"
		name="orderField" value="${orderField}" /> <input type="hidden"
		name="orderDirection" value="${orderDirection}" />
</form>
<div class="pageContent">
	<table class="table" style="width: 100%" layoutH="75">
		<thead>
			<tr>
				<th orderField="id"
					class="${orderField=='id' ? orderDirection : ''}">后台日志表ID</th>
				<th orderField="userId"
					class="${orderField=='userId' ? orderDirection : ''}">用户ID</th>
				<th orderField="action"
					class="${orderField=='action' ? orderDirection : ''}">操作类型</th>
				<th orderField="objId"
					class="${orderField=='objId' ? orderDirection : ''}">操作对象ID</th>
				<th orderField="objName"
					class="${orderField=='objName' ? orderDirection : ''}">操作对象实体类名</th>
				<th orderField="ip"
					class="${orderField=='ip' ? orderDirection : ''}">Ip</th>
				<th orderField="referer"
					class="${orderField=='referer' ? orderDirection : ''}">页面来源</th>
				<th orderField="createTime"
					class="${orderField=='createTime' ? orderDirection : ''}">创建时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="entity" items="${list}">
				<tr target="id" rel="${geli:oid(entity)}">
					<td>${geli:display(entity,'id')}</td>
					<td>${geli:display(entity,'userId')}</td>
					<td>${geli:display(entity,'action')}</td>
					<td>${geli:display(entity,'objId')}</td>
					<td>${geli:display(entity,'objName')}</td>
					<td>${geli:display(entity,'ip')}</td>
					<td>${geli:display(entity,'referer')}</td>
					<td>${geli:display(entity,'createTime')}</td>
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
