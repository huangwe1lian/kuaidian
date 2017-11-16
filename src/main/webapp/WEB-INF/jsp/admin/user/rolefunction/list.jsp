<%@page contentType="text/html; charset=UTF-8" session="false"%>
<%@include file="/WEB-INF/jspf/import.jspf"%>
<form id="pagerForm" action="${ctx}/admin/rolefunction/list.do" method="get">
	<input type="hidden" name="_p" value="${_p}" /> 
	<input type="hidden" name="pageNum" value="${pageNum}" /> 
	<input type="hidden" name="numPerPage" value="${numPerPage}" /> 
	<input type="hidden" name="orderField" value="${orderField}" /> 
	<input type="hidden" name="orderDirection" value="${orderDirection}" />
	<input type="hidden" name="roleId" value="${param.roleId}" />
	<input type="hidden" name="functionId" value="${param.functionId}" />
</form>
<div class="pageHeader">
    <form method="post" action="${ctx}/admin/rolefunction/list.do"  class="pageForm required-validate" onsubmit=" return navTabSearch(this)">
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
                    <label>功能ID:</label>
                    <input class="textInput digits valid" name="functionId" value="${param.functionId}" type="text">
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
			<li><a class="add" href="${ctx}/admin/rolefunction/create.do"
				target="navTab" title="添加角色-功能表"><span>添加</span></a></li>
			<li><a class="delete"
				href="${ctx}/admin/rolefunction/delete.do?id={id}" target="ajaxTodo"
				title="确定要删除吗?"><span>删除</span></a></li>
			<li><a class="edit"
				href="${ctx}/admin/rolefunction/update.do?id={id}" target="navTab"
				title="修改角色-功能表"><span>修改</span></a></li>
			<li class="line">line</li>
		</ul>
	</div>
	<table class="table" style="width: 100%" layoutH="138">
		<thead>
			<tr>
				<th orderField="id"
					class="${orderField=='id' ? orderDirection : ''}">角色-功能ID</th>
				<th orderField="roleId"
					class="${orderField=='roleId' ? orderDirection : ''}">角色</th>
				<th orderField="functionId"
					class="${orderField=='functionId' ? orderDirection : ''}">功能</th>
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
					<td>${geli:refer(entity, 'functionId').name }</td>
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
