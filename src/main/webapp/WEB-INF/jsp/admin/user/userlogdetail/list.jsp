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
					class="${orderField=='id' ? orderDirection : ''}">后台日志内容表ID</th>
				<th orderField="userLogId"
					class="${orderField=='userLogId' ? orderDirection : ''}">后台日志表ID</th>
				<th orderField="befContent"
					class="${orderField=='befContent' ? orderDirection : ''}">操作前内容</th>
				<th orderField="aftContent"
					class="${orderField=='aftContent' ? orderDirection : ''}">操作后内容</th>
				<th orderField="createTime"
					class="${orderField=='createTime' ? orderDirection : ''}">创建时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="entity" items="${list}">
				<tr target="id" rel="${geli:oid(entity)}">
					<td>${geli:display(entity,'id')}</td>
					<td>${geli:display(entity,'userLogId')}</td>
					<td>${geli:display(entity,'befContent')}</td>
					<td>${geli:display(entity,'aftContent')}</td>
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
