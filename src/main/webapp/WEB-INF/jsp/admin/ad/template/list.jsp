<%@page contentType="text/html; charset=UTF-8" session="false"%><%@include
	file="/WEB-INF/jspf/import.jspf"%><form id="pagerForm" method="get">
	<input type="hidden" name="_p" value="${_p}" /> 
	<input type="hidden" name="pageNum" value="${pageNum}" /> 
	<input type="hidden" name="numPerPage" value="${numPerPage}" /> 
	<input type="hidden" name="orderField" value="${orderField}" /> 
	<input type="hidden" name="orderDirection" value="${orderDirection}" />
	<input type="hidden" name="id" value="${param.id}" />
	<input type="hidden" name="codeId" value="${param.codeId}" />
	<input type="hidden" name="name" value="${param.name}" />
	<input type="hidden" name="auditStatus" value="${param.auditStatus}" />
	<input type="hidden" name="status" value="${param.status}" />
	<input type="hidden" name="sgId" value="${param.sgId}" />
	<input type="hidden" name="status" value="${param.status}" />
	<input type="hidden" name="createTime1" value="${param.createTime1}" />
	<input type="hidden" name="createTime2" value="${param.createTime2}" />
</form>
<div class="pageHeader">
    <form method="post" action="${ctx}/admin/ad/template/list.do"  class="pageForm required-validate" onsubmit=" return navTabSearch(this)">
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
                <label>线索模块ID:</label>
                <input class="textInput digits valid" name="codeId" value="${param.codeId}" type="text">
            </p>
            <p>
                <label>广告标题:</label>
                <input class="textInput" name="name" value="${param.name}" type="text">
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
			<li><a class="add" href="${ctx}/admin/ad/template/createbasis.do" rel="template-ad-basis"
				target="navTab" title="发布模板广告"><span>发布模板广告</span></a></li>
			<li><a class="delete" href="${ctx}/admin/ad/template/delete.do?id={id}"
				target="ajaxTodo" title="确定要删除吗?"><span>删除</span></a></li>
			<li class="line">line</li>
		</ul>
	</div>
	<table class="table" style="width: 100%" layoutH="200">
		<thead>
			<tr>
				<th orderField="id"
					class="${orderField=='id' ? orderDirection : ''}">模板广告ID</th>
				<th orderField="name"
					class="${orderField=='name' ? orderDirection : ''}">模板广告标题</th>
				<th orderField="seq"
					class="${orderField=='seq' ? orderDirection : ''}">排序值</th>
				<th orderField="status"
					class="${orderField=='status' ? orderDirection : ''}">广告状态</th>
				<th orderField="createUserId"
					class="${orderField=='createUserId' ? orderDirection : ''}">创建人</th>
				<th orderField="createTime"
					class="${orderField=='createTime' ? orderDirection : ''}">创建时间</th>
				<th orderField="updateUserId"
					class="${orderField=='updateUserId' ? orderDirection : ''}">更新人</th>
				<th orderField="updateTime"
					class="${orderField=='updateTime' ? orderDirection : ''}">更新时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="entity" items="${list}">
				<tr target="id" rel="${geli:oid(entity)}">
					<td>${geli:display(entity,'id')}</td>
					<td>${geli:display(entity,'name')}</td>
					<td><input id="seq_${entity.id}" style="width:50px;" type="text" value="${geli:display(entity,'seq')}" maxlength="4"></td>
					<td>${geli:display(entity,'status')}</td>
					<td>${geli:refer(entity,'createUserId').name}</td>
					<td>${geli:display(entity,'createTime')}</td>
					<td>${geli:refer(entity,'updateUserId').name}</td>
					<td>${geli:display(entity,'updateTime')}</td>
					<td>
						<a href="${ctx}/admin/ad/template/updatebasis.do?id=${entity.id}" target="navTab" title="修改基本信息">修改基本信息</a>
						<a href="${ctx}/admin/ad/template/updatedetail.do?adId=${entity.id}" target="navTab" title="修改内容">修改内容</a>
						<a href="${ctx}/admin/ad/template/shiftStatus.do?id=${entity.id}&status=2" target="ajaxTodo">上架</a>
						<a href="${ctx}/admin/ad/template/shiftStatus.do?id=${entity.id}&status=3" target="ajaxTodo">下架</a>
						<a href="${ctx}/admin/ad/template/showdetail.do?id=${entity.id}" target="dialog" title="预览">预览</a>
						<a href="${ctx}/admin/ad/manufac/copycreate.do?id=${entity.id}&type=2" target="navTab" title="发布广告">发布广告</a>
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
	$.post("${ctx}/admin/ad/template/updateSeq.do",{"id":id,"seq":seq},DWZ.ajaxDone);
}
</script>
