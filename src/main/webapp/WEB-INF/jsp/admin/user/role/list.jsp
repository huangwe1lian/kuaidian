<%@page contentType="text/html; charset=UTF-8" session="false"%><%@include
	file="/WEB-INF/jspf/import.jspf"%>
<form id="pagerForm" action="${ctx}/admin/role/list.do"  method="get">
	<input type="hidden" name="_p" value="${_p}" /> 
	<input type="hidden" name="pageNum" value="${pageNum}" /> 
	<input type="hidden" name="numPerPage" value="${numPerPage}" /> 
	<input type="hidden" name="orderField" value="${orderField}" /> 
	<input type="hidden" name="orderDirection" value="${orderDirection}" />
	<input type="hidden" name="id" value="${param.id}" />
	<input type="hidden" name="name" value="${param.name}" />	
	<input type="hidden" name="typ" value="${param.typ}" />	
</form>
<div class="pageHeader">
    <form rel="pagerForm" method="post" action="${ctx}/admin/role/list.do"  class="pageForm required-validate" onsubmit=" return navTabSearch(this)">
    <input type="hidden" name="pageNum" value="${pageNum}" />
	<input type="hidden" name="numPerPage" value="${numPerPage }" />
	<input type="hidden" name="orderField" value="${orderField}" />
	<input type="hidden" name="orderDirection" value="${orderDirection}" />
        <div class="pageFormContent">
            	<p>
                    <label>角色ID:</label>
                    <input class="textInput digits valid" name="id" value="${param.id}" type="text">
                </p>
                <p>
                    <label>角色名称:</label>
                    <input class="textInput" name="name" value="${param.name}" type="text">
                </p>
                <p>
                	<label>角色类型:</label>
                    <select class="required " name="typ" >
						<option value="" >全部</option>
						<option value="1" ${(1 == param.typ && not empty param.typ) ? 'selected' : '' }>管理员</option>
						<option value="2" ${(2 == param.typ) ? 'selected' : '' }>厂商</option>
						<option value="3" ${(3 == param.typ) ? 'selected' : '' }>区域</option>
						<option value="4" ${(4 == param.typ) ? 'selected' : '' }>代理商</option>
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
			<li><a class="add" href="${ctx}/admin/role/create.do"
				target="navTab" title="添加角色表"><span>添加</span></a></li>
			<li><a class="delete" href="${ctx}/admin/role/delete.do?id={id}"
				target="ajaxTodo" title="确定要删除吗?"><span>删除</span></a></li>
			<li><a class="edit" href="${ctx}/admin/role/update.do?id={id}"
				target="navTab" title="修改角色表"><span>修改</span></a></li>
			<li class="line">line</li>
		</ul>
	</div>
	<table class="table" style="width: 100%" layoutH="138">
		<thead>
			<tr>
				<th orderField="id"
					class="${orderField=='id' ? orderDirection : ''}">角色ID</th>
				<th orderField="name"
					class="${orderField=='name' ? orderDirection : ''}">角色名称</th>
				<th orderField="typ"
					class="${orderField=='typ' ? orderDirection : ''}">角色类型</th>
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
					<td>${entity.typ == 1 ? '管理员' : entity.typ==2 ? '厂商' : entity.typ==3 ? '区域' : '代理商'}</td>
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
