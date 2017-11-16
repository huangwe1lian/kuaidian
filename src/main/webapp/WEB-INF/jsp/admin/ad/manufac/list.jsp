<%@page contentType="text/html; charset=UTF-8" session="false"%><%@include
	file="/WEB-INF/jspf/import.jspf"%><form id="pagerForm" method="get">
	<input type="hidden" name="_p" value="${_p}" /> 
	<input type="hidden" name="pageNum" value="${pageNum}" /> 
	<input type="hidden" name="numPerPage" value="${numPerPage}" /> 
	<input type="hidden" name="orderField" value="${orderField}" /> 
	<input type="hidden" name="orderDirection" value="${orderDirection}" />
	<input type="hidden" name="id" value="${param.id}" />
	<input type="hidden" name="name" value="${param.name}" />
	<input type="hidden" name="type" value="${param.type}" />
	<input type="hidden" name="position" value="${param.position}" />
	<input type="hidden" name="auditStatus" value="${param.auditStatus}" />
	<input type="hidden" name="status" value="${param.status}" />
	<input type="hidden" name="createTime1" value="${param.createTime1}" />
	<input type="hidden" name="createTime2" value="${param.createTime2}" />
</form>
<div class="pageHeader">
    <form method="post" action="${ctx}/admin/ad/manufac/list.do"  class="pageForm required-validate" onsubmit=" return navTabSearch(this)">
	    <input type="hidden" name="pageNum" value="${pageNum}" />
		<input type="hidden" name="numPerPage" value="${numPerPage }" />
		<input type="hidden" name="orderField" value="${orderField}" />
		<input type="hidden" name="orderDirection" value="${orderDirection}" />
        <div class="pageFormContent">
            	<p>
                    <label>广告ID:</label>
                    <input class="textInput digits valid" name="id" value="${param.id}" type="text">
                </p>
                <p>
                    <label>广告标题:</label>
                    <input class="textInput" name="name" value="${param.name}" type="text">
                </p>
                <p>
                	<label>广告类型:</label>
                    <select class="required " name="type" >
						<option value="" >全部</option>
						${geli:select_options(param.type, 'adType')}							
					</select>
                </p>
                <p>
                	<label>广告位置:</label>
                    <select class="required " name="position" >
						<option value="" >全部</option>
						${geli:select_options(param.position, 'position')}					
					</select>
                </p>
                <p>
                	<label>审核状态:</label>
                    <select class="required " name="auditStatus" >
						<option value="" >全部</option>
						${geli:select_options(param.auditStatus, 'auditStatus')}					
					</select>
                </p>
                <p>
                	<label>广告状态:</label>
                    <select class="required " name="status" >
						<option value="" >全部</option>
						${geli:select_options(param.status, 'adStatus')}						
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
			<li><a class="add" href="${ctx}/admin/ad/manufac/changetype.do" rel="nav-changetype"
				target="dialog" title="发布广告"><span>发布广告</span></a></li>
			<li><a class="delete" href="${ctx}/admin/ad/manufac/delete.do?id={id}"
				target="ajaxTodo" title="确定要删除吗?"><span>删除</span></a></li>
			<li class="line">line</li>
		</ul>
	</div>
	<table class="table" style="width: 100%" layoutH="200">
		<thead>
			<tr>
				<th orderField="id"
					class="${orderField=='id' ? orderDirection : ''}">广告表ID</th>
				<th orderField="name"
					class="${orderField=='name' ? orderDirection : ''}">广告标题</th>
				<th orderField="type"
					class="${orderField=='type' ? orderDirection : ''}">广告类型</th>
				<th orderField="position"
					class="${orderField=='position' ? orderDirection : ''}">广告位置</th>
				<th orderField="seq"
					class="${orderField=='seq' ? orderDirection : ''}">排序值</th>
				<th orderField="auditStatus"
					class="${orderField=='auditStatus' ? orderDirection : ''}">审核状态</th>
				<th orderField="status"
					class="${orderField=='status' ? orderDirection : ''}">广告状态</th>
				<th orderField="beginTime"
					class="${orderField=='beginTime' ? orderDirection : ''}">广告开始时间</th>
				<th orderField="endTime"
					class="${orderField=='endTime' ? orderDirection : ''}">广告结束时间</th>
				<th orderField="createUserId"
					class="${orderField=='createUserId' ? orderDirection : ''}">创建人</th>
				<th orderField="updateUserId"
					class="${orderField=='updateUserId' ? orderDirection : ''}">更新人</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="entity" items="${list}">
				<tr target="id" rel="${geli:oid(entity)}">
					<td>${geli:display(entity,'id')}</td>
					<td>${geli:display(entity,'name')}</td>
					<td>${geli:display(entity,'type')}</td>
					<td>${geli:display(entity,'position')}</td>
					<td><input id="seq_${entity.id}" style="width:50px;" type="text" value="${geli:display(entity,'seq')}" maxlength="4"></td>
					<td>${geli:display(entity,'auditStatus')}</td>
					<td>${geli:display(entity,'status')}</td>
					<td><fmt:formatDate value='${entity.beginTime}' pattern='yyyy-MM-dd'></fmt:formatDate></td>
					<td><fmt:formatDate value='${entity.endTime}' pattern='yyyy-MM-dd'></fmt:formatDate></td>
					<td>${geli:refer(entity,'createUserId').name}</td>
					<td>${geli:refer(entity,'updateUserId').name}</td>
					<td>
						<c:if test="${topTyp == 1 || topTyp == 2 }">
						<a href="${ctx}/admin/ad/manufac/audit.do?id=${entity.id}" target="dialog" title="审核">审核</a>
						</c:if>
						<a href="${ctx}/admin/ad/manufac/update.do?id=${entity.id}" target="navTab" title="修改">修改</a>
						<c:if test="${(entity.status == 0 || entity.status == 1 || entity.status == 3 || entity.status == 4) && entity.auditStatus == 1  }">
						<a href="${ctx}/admin/ad/manufac/shiftStatus.do?id=${entity.id}&status=2" target="ajaxTodo">上架</a>
						</c:if>
						<c:if test="${(entity.status == 1 || entity.status == 2) && entity.auditStatus == 1  }">
						<a href="${ctx}/admin/ad/manufac/shiftStatus.do?id=${entity.id}&status=3" target="ajaxTodo">下架</a>
						</c:if>
						<c:if test="${entity.auditStatus == -1 }">
						<a href="${ctx}/admin/ad/manufac/reason.do?id=${entity.id}" target="dialog" title="审核不通过原因">不通过原因</a>
						</c:if>
						<a href="${ctx}/admin/ad/manufac/showdetail.do?id=${entity.id}" target="dialog" title="详情">详情</a>
						<a href="javascript:saveSeq(${entity.id});" title="保存">保存</a>
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
<script type="text/javascript">
function saveSeq(id){
	var seq = $("#seq_" + id).val();
	$.post("${ctx}/admin/ad/manufac/updateSeq.do",{"id":id,"seq":seq},DWZ.ajaxDone);
}
</script>
