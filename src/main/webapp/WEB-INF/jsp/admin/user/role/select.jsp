<%@page contentType="text/html; charset=UTF-8" session="false"%><%@include
	file="/WEB-INF/jspf/import.jspf"%>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="delete"
				href="javascript:$.bringBack({id:'',display:''});"><span>清空</span></a></li>
		</ul>
	</div>
	<table class="table" targetType="dialog" style="width: 100%" layoutH="75">
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
				<th>查找带回</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="entity" items="${list}">
				<tr target="roleId" rel="${geli:oid(entity)}">
					<td>${geli:display(entity,'id')}</td>
					<td>${geli:display(entity,'name')}</td>
					<td>${entity.typ == 1 ? '管理员' : entity.typ==2 ? '厂商' : entity.typ==3 ? '区域' : '代理商'}</td>
					<td>${geli:display(entity,'createTime')}</td>
					<td>${geli:display(entity,'updateTime')}</td>
					<td><a class="btnSelect"
						href="javascript:$.bringBack({roleId:'${geli:display(entity, 'id')}',display:'${geli:display(entity, 'name')}'})"
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
