<%@page contentType="text/html; charset=UTF-8" session="false"%>
<%@include file="/WEB-INF/jspf/import.jspf"%>
<form id="pagerForm" action="${ctx}/admin/userrole/list.do"  method="get">
	<input type="hidden" name="_p" value="${_p}" /> 
	<input type="hidden" name="pageNum" value="${pageNum}" /> 
	<input type="hidden" name="numPerPage" value="${numPerPage}" /> 
	<input type="hidden" name="orderField" value="${orderField}" /> 
	<input type="hidden" name="orderDirection" value="${orderDirection}" />
	<input type="hidden" name="roleId" value="${param.roleId}" />
	<input type="hidden" name="userId" value="${param.userId}" />
</form>
<div class="pageHeader">
    <form method="post" action="${ctx}/admin/userrole/list.do"  class="pageForm required-validate" onsubmit=" return navTabSearch(this)">
    <input type="hidden" name="pageNum" value="${pageNum}" />
	<input type="hidden" name="numPerPage" value="${numPerPage }" />
	<input type="hidden" name="orderField" value="${orderField}" />
	<input type="hidden" name="orderDirection" value="${orderDirection}" />
        <div class="pageFormContent">
                <p>
                    <label>用户ID:</label>
                    <input class="textInput digits valid" name="userId" value="${param.functionId}" type="text">
                </p>
            	<p>
                    <label>角色ID:</label>
                    <input class="textInput digits valid" name="roleId" value="${param.roleId}" type="text">
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
			<li><a class="add" href="${ctx}/admin/userrole/create.do"
				target="navTab" title="添加用户-角色表"><span>添加</span></a></li>
			<li><a class="delete"
				href="${ctx}/admin/userrole/delete.do?id={id}" target="ajaxTodo"
				title="确定要删除吗?"><span>删除</span></a></li>
			<li><a class="edit"
				href="${ctx}/admin/userrole/update.do?id={id}" target="navTab"
				title="修改用户-角色表"><span>修改</span></a></li>
			<li class="line">line</li>
		</ul>
	</div>
	<table class="table" style="width: 100%" layoutH="138">
		<thead>
			<tr>
				<th orderField="id"
					class="${orderField=='id' ? orderDirection : ''}">用户-角色ID</th>
				<th orderField="userId"
					class="${orderField=='userId' ? orderDirection : ''}">用户</th>
				<th orderField="roleId"
					class="${orderField=='roleId' ? orderDirection : ''}">角色</th>
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
					<td>${geli:refer(entity, 'userId').name }</td>
					<td>${geli:refer(entity, 'roleId').name }</td>
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
