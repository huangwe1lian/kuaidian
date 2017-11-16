<%@page contentType="text/html; charset=UTF-8" session="false"%><%@include
	file="/WEB-INF/jspf/import.jspf"%><form id="pagerForm" method="get">
	<input type="hidden" name="_p" value="${_p}" /> 
	<input type="hidden" name="pageNum" value="${pageNum}" /> 
	<input type="hidden" name="numPerPage" value="${numPerPage}" /> 
	<input type="hidden" name="orderField" value="${orderField}" /> 
	<input type="hidden" name="orderDirection" value="${orderDirection}" />
	<input type="hidden" name="id" value="${param.id}" />
	<input type="hidden" name="name" value="${param.name}" />
	<input type="hidden" name="dealerShortName" value="${param.dealerShortName}" />
	<input type="hidden" name="smallType" value="${param.smallType}" />
	<input type="hidden" name="deleted" value="${param.deleted}" />
	<input type="hidden" name="hidden" value="${param.hidden}" />
	<input type="hidden" name="isRecommend" value="${param.isRecommend}" />
	<input type="hidden" name="activityLevel" value="${param.activityLevel}" />
	<input type="hidden" name="status" value="${param.status}" />
	<input type="hidden" name="sgId" value="${param.sgId}" />
	<input type="hidden" name="createTime1" value="${param.createTime1}" />
	<input type="hidden" name="createTime2" value="${param.createTime2}" />
</form>
<div class="pageHeader">
    <form method="post" action="${ctx}/admin/ad/dealer/list.do"  class="pageForm required-validate" onsubmit=" return navTabSearch(this)">
	    <input type="hidden" name="pageNum" value="${pageNum}" />
		<input type="hidden" name="numPerPage" value="${numPerPage }" />
		<input type="hidden" name="orderField" value="${orderField}" />
		<input type="hidden" name="orderDirection" value="${orderDirection}" />
        <div class="pageFormContent">
            	<p>
                    <label>活动ID:</label>
                    <input class="textInput digits valid" name="id" value="${param.id}" type="text">
                </p>
                <p>
                    <label>活动标题:</label>
                    <input class="textInput" name="name" value="${param.name}" type="text">
                </p>
                <p>
                    <label>经销商简称:</label>
                    <input class="textInput" name="dealerShortName" value="${param.dealerShortName}" type="text">
                </p>
                <p>
                	<label>活动类型:</label>
                    <select class="required " name="smallType" >
						<option value="" >全部</option>
						${geli:select_options(param.smallType, 'dealerNewsSmallType4Type2')}					
					</select>
                </p>
                <p>
                	<label>是否删除:</label>
                    <select class="required " name="deleted" >
						<option value="" >全部</option>
						${geli:select_options(param.deleted, 'yesOrNo')}					
					</select>
                </p>
                <p>
                	<label>是否隐藏:</label>
                    <select class="required " name="hidden" >
						<option value="" >全部</option>
						${geli:select_options(param.hidden, 'yesOrNo')}					
					</select>
                </p>
                <p>
                	<label>是否推荐:</label>
                    <select class="required " name="isRecommend" >
						<option value="" >全部</option>
						${geli:select_options(param.isRecommend, 'yesOrNo')}					
					</select>
                </p>
                <p>
                	<label>活动级别:</label>
                    <select class="required " name="activityLevel" >
						<option value="" >全部</option>
						${geli:select_options(param.activityLevel, 'dealerNewsActivityLevel')}					
					</select>
                </p>
                <p>
                	<label>广告状态:</label>
                    <select class="required " name="status" >
						<option value="" >全部</option>
						${geli:select_options(param.status, 'dealerNewsStatus')}						
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
	<table class="table" style="width: 100%" layoutH="225">
		<thead>
			<tr>
				<th orderField="id"
					class="${orderField=='id' ? orderDirection : ''}">活动ID</th>
				<th orderField="name"
					class="${orderField=='name' ? orderDirection : ''}">活动标题</th>
				<th orderField="dealerId"
					class="${orderField=='dealerId' ? orderDirection : ''}">经销商</th>
				<th orderField="city" >活动城市</th>
				<th orderField="smallType"
					class="${orderField=='smallType' ? orderDirection : ''}">小类型</th>
				<th orderField="status"
					class="${orderField=='status' ? orderDirection : ''}">状态</th>
				<th orderField="beginTime"
					class="${orderField=='beginTime' ? orderDirection : ''}">活动开始时间</th>
				<th orderField="endTime"
					class="${orderField=='endTime' ? orderDirection : ''}">活动结束时间</th>
				<th orderField="deleted"
					class="${orderField=='deleted' ? orderDirection : ''}">是否删除</th>
				<th orderField="hidden"
					class="${orderField=='hidden' ? orderDirection : ''}">是否隐藏</th>
				<th orderField="isRecommend"
					class="${orderField=='isRecommend' ? orderDirection : ''}">是否推荐</th>
				<th orderField="recommendPosition"
					class="${orderField=='recommendPosition' ? orderDirection : ''}">推荐位置</th>
				<td>操作</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="entity" items="${list}">
				<tr target="id" rel="${geli:oid(entity)}">
					<c:set var="dealer" value="${geli:refer(entity,'dealerId') }"></c:set>
					<td>${geli:display(entity,'id')}</td>
					<td>${geli:display(entity,'name')}</td>
					<td>${dealer.shortName}</td>
					<td>${empty dealer ? '' : geli:refer(dealer,'provinceId').name} ${empty dealer ? '' : geli:refer(dealer,'cityId').name}</td>
					<td>${geli:display(entity,'smallType')}</td>
					<td>${geli:display(entity,'status')}</td>
					<td>${geli:display(entity,'beginTime')}</td>
					<td>${geli:display(entity,'endTime')}</td>
					<td>${geli:display(entity,'deleted')}</td>
					<td>${geli:display(entity,'hidden')}</td>
					<td>${geli:display(entity,'isRecommend')}</td>
					<td>${geli:display(entity,'recommendPosition')}</td>
					<td>
						<c:if test="${entity.hidden == 1 }">
						<a href="${ctx}/admin/ad/dealer/shifthidden.do?id=${entity.id}&hidden=0" target="ajaxTodo">显示</a>
						</c:if>
						<c:if test="${entity.hidden == 0 }">
						<a href="${ctx}/admin/ad/dealer/shifthidden.do?id=${entity.id}&hidden=1" target="ajaxTodo">隐藏</a>
						</c:if>
						<a href="${ctx}/admin/ad/dealer/recommend.do?id=${entity.id}" target="dialog" title="推荐设置">推荐设置</a>
						<a href="${ctx}/admin/ad/dealer/showdetail.do?id=${entity.id}" target="dialog" title="详情">详情</a>
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
