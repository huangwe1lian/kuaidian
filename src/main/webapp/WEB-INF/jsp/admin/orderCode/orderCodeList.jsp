<%@page contentType="text/html; charset=UTF-8" session="false"%>
<%@include file="/WEB-INF/jspf/import.jspf"%>
<!-- 搜索 -->
<div class="pageHeader">
<form id="pagerForm" method="post" action="${ctx}/admin/ordercode/list.do" class="pageForm required-validate" onsubmit=" return navTabSearch(this)">
	<input type="hidden" name="_p" value="${_p}" /> 
	<input type="hidden" name="pageNum" value="${pageNum}" /> 
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
	<input type="hidden" name="orderField" value="${orderField}" /> 
	<input type="hidden" name="orderDirection" value="${orderDirection}" />
	<div class="pageFormContent">
            <p>
                <label>授权代码:</label>
                <input class="textInput" name="order_code" value="${order_code}" type="text">
            </p>
            <p>
                <label>状态:</label>
                <select class="required " name="status" >
                	<option value="1" >正常</option>
					<option value="0" >禁用</option>
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
			<li><a class="add" href="${ctx}/admin/ordercode/create.do"
				target="navTab" title="添加名单报名授权表"><span>添加</span></a></li>
			<li><a class="delete"
				href="${ctx}/admin/ordercode/delete.do?id={id}" target="ajaxTodo"
				title="确定要删除吗?"><span>删除</span></a></li>
			<li><a class="edit"
				href="${ctx}/admin/ordercode/update.do?id={id}" target="navTab"
				title="修改名单报名授权表"><span>修改</span></a></li>
			<li class="line">line</li>
		</ul>
	</div>
	<table class="table" style="width: 100%" layoutH="140">
		<thead>
			<tr>
				<th orderField="id"
					class="${orderField=='id' ? orderDirection : ''}">名单报名授权ID</th>
				<th orderField="orderCode"
					class="${orderField=='orderCode' ? orderDirection : ''}">授权代码</th>
				<th orderField="description"
					class="${orderField=='description' ? orderDirection : ''}">授权代码说明</th>
				<th orderField="url"
					class="${orderField=='url' ? orderDirection : ''}">相关专题、活动链接</th>
				<th orderField="status"
					class="${orderField=='status' ? orderDirection : ''}">状态</th>
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
					<td>${geli:display(entity,'orderCode')}</td>
					<td>${geli:display(entity,'description')}</td>
					<td>${geli:display(entity,'url')}</td>
					<td>
						<c:if test="${entity.status == 0 }">禁用</c:if>
						<c:if test="${entity.status == 1 }">正常</c:if>
					</td>
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
