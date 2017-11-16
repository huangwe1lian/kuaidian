<%@page contentType="text/html; charset=UTF-8" session="false"%>
<%@include file="/WEB-INF/jspf/import.jspf"%>
<form id="pagerForm" method="get">
	<input type="hidden" name="_p" value="${_p}" /> 
	<input type="hidden" name="pageNum" value="${pageNum}" /> 
	<input type="hidden" name="numPerPage" value="${numPerPage}" /> 
	<input type="hidden" name="orderField" value="${orderField}" /> 
	<input type="hidden" name="orderDirection" value="${orderDirection}" />
	<input type="hidden" name="id" value="${param.id}" /> 
	<input type="hidden" name="name" value="${param.name}" />
	<input type="hidden" name="media" value="${param.media}" /> 
	<input type="hidden" name="status" value="${param.status}" />
	<input type="hidden" name="isRecommend" value="${param.isRecommend}" />
	<input type="hidden" name="kind" value="${param.kind}" />
	<input type="hidden" name="createTime1" value="${param.createTime1}" />
	<input type="hidden" name="createTime2" value="${param.createTime2}" />
	
</form>
<div class="pageHeader">
    <form method="post" action="${ctx}/admin/medianews/list.do"  class="pageForm required-validate" onsubmit=" return navTabSearch(this)">
	    <input type="hidden" name="pageNum" value="${pageNum}" />
		<input type="hidden" name="numPerPage" value="${numPerPage }" />
		<input type="hidden" name="orderField" value="${orderField}" />
		<input type="hidden" name="orderDirection" value="${orderDirection}" />
        <div class="pageFormContent">
            	<p>
                    <label>ID:</label>
                    <input class="textInput digits valid" name="id" value="${param.id}" type="text">
                </p>
                <p>
                    <label>文章标题:</label>
                    <input class="textInput" name="name" value="${param.name}" type="text">
                </p>
                <p>
                    <label>来源媒体:</label>
                    <input class="textInput" name="media" value="${param.media}" type="text">
                </p>
                <p>
                	<label>状态:</label>
                    <select class="required " name="status" >
						<option value="" >全部</option>
						<option value="0" ${(0 == param.status && not empty param.status) ? 'selected' : '' }>隐藏</option>
						<option value="1" ${(1 == param.status) ? 'selected' : '' }>不隐藏</option>
					</select>
                </p>
                <p>
                	<label>是否推荐:</label>
                    <select class="required " name="isRecommend" >
						<option value="" >全部</option>
						<option value="0" ${(0 == param.isRecommend && not empty param.status) ? 'selected' : '' }>否</option>
						<option value="1" ${(1 == param.isRecommend) ? 'selected' : '' }>是</option>
					</select>
                </p>
                <p>
                	<label>类型:</label>
                    <select class="required " name="kind" >
						<option value="" >全部</option>
						<c:forEach var="kind" items="${mediaNewsKind }">
							<option value="${kind.key }" ${(kind.key == param.kind && not empty param.kind) ? 'selected' : '' }>${kind.value }</option>
						</c:forEach>
					</select>
                </p>
                <p>
                	<label>关联车系:</label>
                    <select class="required " name="sgId" >
						<option value="" >全部</option>
						<c:forEach var="sg" items="${sgList }">
							<option value="${sg.id }" ${(sg.id == param.sgId && not empty param.sgId) ? 'selected' : '' }>${sg.name }</option>
						</c:forEach>
					</select>
                </p>
                <p>
                	<label>创建时间:</label>
	                    <input
						class="date textInput"
						dateFmt="yyyy-MM-dd 00:00:00"
						value="${empty param.createTime1 ? lastYear : param.createTime1}"
						name="createTime1" id="createTime1" />
						<a class="inputDateButton" href="javascript:;">select</a>
					<p style="width: 10px;float: left">-</p>
					<input
						class="date textInput"
						dateFmt="yyyy-MM-dd 23:59:59"
						value="${param.createTime2}"
						name="createTime2" id="createTime2"/>
					<a class="inputDateButton" href="javascript:;">select</a>
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
			<li><a class="add" href="${ctx}/admin/medianews/create.do"
				target="navTab" title="发布媒体资讯"><span>发布媒体资讯</span></a></li>
			<li><a class="delete"
				href="${ctx}/admin/medianews/delete.do?id={id}" target="ajaxTodo"
				title="确定要删除吗?"><span>删除</span></a></li>
			<li class="line">line</li>
		</ul>
	</div>
	<table class="table" style="width: 100%" layoutH="220">
		<thead>
			<tr>
				<th orderField="id"
					class="${orderField=='id' ? orderDirection : ''}">ID</th>
				<th orderField="kind"
					class="${orderField=='kind' ? orderDirection : ''}">类型</th>
				<th orderField="name"
					class="${orderField=='name' ? orderDirection : ''}">文章标题</th>
				<th orderField="media"
					class="${orderField=='media' ? orderDirection : ''}">来源媒体</th>
				<th orderField="isRecommend"
					class="${orderField=='isRecommend' ? orderDirection : ''}">是否推荐</th>
				<th orderField="endTime"
					class="${orderField=='endTime' ? orderDirection : ''}">推荐结束时间</th>
				<th orderField="status"
					class="${orderField=='status' ? orderDirection : ''}">状态</th>
				<th orderField="createUserId"
					class="${orderField=='createUserId' ? orderDirection : ''}">创建人</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="entity" items="${list}">
				<tr target="id" rel="${geli:oid(entity)}">
					<td>${geli:display(entity,'id')}</td>
					<td>${geli:display(entity,'kind')}</td>
					<td><a target="_brank" href="${geli:display(entity,'url')}">${geli:display(entity,'name')}</a></td>
					<td>${geli:display(entity,'media')}</td>
					<td>${geli:display(entity,'isRecommend')}</td>
					<td>${geli:display(entity,'endTime')}</td>
					<td>${geli:display(entity,'status')}</td>
					<td>${geli:refer(entity,'createUserId').name}</td>
					<td>
						<a href="${ctx}/admin/medianews/update.do?id=${entity.id}" target="navTab" title="修改">修改</a>
						<a href="${ctx}/admin/medianews/recommend.do?id=${entity.id}" target="dialog" title="推荐">推荐</a>
						<c:choose>
							<c:when test="${entity.status == 1 }">
								<a href="${ctx}/admin/medianews/shiftStatus.do?id=${entity.id}&status=0" target="ajaxTodo">隐藏</a>
							</c:when>
							<c:otherwise>
								<a href="${ctx}/admin/medianews/shiftStatus.do?id=${entity.id}&status=1" target="ajaxTodo">不隐藏</a>
							</c:otherwise>
						</c:choose>
						<a href="${ctx}/admin/medianews/showDetail.do?id=${entity.id}" target="dialog" title="详情">详情</a>
					</td>
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
