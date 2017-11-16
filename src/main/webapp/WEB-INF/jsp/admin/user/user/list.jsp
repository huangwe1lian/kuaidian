<%@page contentType="text/html; charset=UTF-8" session="false"%><%@include
	file="/WEB-INF/jspf/import.jspf"%>
<form id="pagerForm" action="${ctx}/admin/user/list.do" method="get">
	<input type="hidden" name="_p" value="${_p}" /> 
	<input type="hidden" name="pageNum" value="${pageNum}" /> 
	<input type="hidden" name="numPerPage" value="${numPerPage}" /> 
	<input type="hidden" name="orderField" value="${orderField}" /> 
	<input type="hidden" name="orderDirection" value="${orderDirection}" />
	<input type="hidden" name="id" value="${param.id}" />
	<input type="hidden" name="name" value="${param.name}" />
	<input type="hidden" name="loginName" value="${param.loginName}" />
	<input type="hidden" name="status" value="${param.status}" />
	<input type="hidden" name="deleted" value="${param.deleted }" />
</form>
<div class="pageHeader">
    <form rel="pagerForm" method="post" action="${ctx}/admin/user/list.do"  class="pageForm required-validate" onsubmit=" return navTabSearch(this)">
    <input type="hidden" name="pageNum" value="${pageNum}" />
	<input type="hidden" name="numPerPage" value="${numPerPage }" />
	<input type="hidden" name="orderField" value="${orderField}" />
	<input type="hidden" name="orderDirection" value="${orderDirection}" />
        <div class="pageFormContent">
            	<p>
                    <label>用户ID:</label>
                    <input class="textInput digits valid" name="id" value="${param.id}" type="text">
                </p>
                    <label>姓名:</label>
                    <input class="textInput" name="name" value="${param.name}" type="text">
                </p>
            	<p>
                    <label>登录名</label>
                    <input class="textInput" name="loginName" value="${param.loginName}" type="text">
                </p>
                <p>
                	<label>账号状态:</label>
                    <select class="required " name="status" >
						<option value="" >全部</option>
						<option value="0" ${(0 == param.status && not empty param.status) ? 'selected' : '' }>禁用</option>
						<option value="1" ${(1 == param.status) ? 'selected' : '' }>正常</option>
					</select>
                </p>
                <p>
                	<label>是否删除:</label>
                    <select class="required " name="deleted" >
						<option value="" >全部</option>
						<option value="0" ${(0 == param.deleted && not empty param.deleted) ? 'selected' : '' }>否</option>
						<option value="1" ${(1 == param.deleted) ? 'selected' : '' }>是</option>
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
			<li><a class="add" href="${ctx}/admin/user/create.do"
				target="navTab" title="添加用户"><span>添加</span></a></li>
			<li><a class="delete" href="${ctx}/admin/user/delete.do?id={id}"
				target="ajaxTodo" title="确定要删除吗?"><span>删除</span></a></li>
			<li><a class="edit" href="${ctx}/admin/user/update.do?id={id}"
				target="navTab" title="修改用户"><span>修改</span></a></li>
			<li class="line">line</li>
		</ul>
	</div>
	<table class="table" style="width: 100%" layoutH="170">
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
				<th orderField="lastLoginTime"
					class="${orderField=='lastLoginTime' ? orderDirection : ''}">最后登录时间</th>
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
					<td>${geli:display(entity,'name')}</td>
					<td>${geli:display(entity,'loginName')}</td>
					<td>${entity.status == 0 ? '禁用' : '正常' }</td>
					<td>${entity.deleted == 0 ? '否' : '是' }</td>
					<td>${geli:display(entity,'lastLoginTime')}</td>
					<td>${geli:display(entity,'createTime')}</td>
					<td>${geli:display(entity,'updateTime')}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
<div class="panelBar">
	<div class="pages">
		<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
			<option ${numPerPage == 20 ? "selected " : ""} value="20">20</option>
			<option ${numPerPage == 50 ? "selected " : ""} value="50">50</option>
			<option ${numPerPage == 100 ? "selected " : ""} value="100">100</option>
			<option ${numPerPage == 200 ? "selected " : ""} value="200">200</option>
		</select>
		<span>共 ${total} 条</span>
	</div>
	<div class="pagination" targetType="navTab" totalCount="${total}"
		numPerPage="${numPerPage}" pageNumShown="10" currentPage="${pageNum}"></div>
</div>
