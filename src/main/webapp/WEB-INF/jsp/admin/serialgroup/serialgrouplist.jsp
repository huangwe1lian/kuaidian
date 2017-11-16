<%@page contentType="text/html; charset=UTF-8" session="false"%>
<%@include	file="/WEB-INF/jspf/import.jspf"%>
<form id="pagerForm" method="get">
	<input type="hidden" name="_p" value="${_p}" /> <input type="hidden"
		name="pageNum" value="${pageNum}" /> <input type="hidden"
		name="numPerPage" value="${numPerPage}" /> <input type="hidden"
		name="orderField" value="${orderField}" /> <input type="hidden"
		name="orderDirection" value="${orderDirection}" />
</form>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="edit"
				href="${ctx}/admin/serialgroup/update.do?id={id}" target="navTab"
				title="修改车系"><span>修改</span></a></li>
			<li class="line">line</li>
		</ul>
	</div>
	<table class="table" style="width: 100%" layoutH="75">
		<thead>
			<tr>
				<th orderField="id"
					class="${orderField=='id' ? orderDirection : ''}">车系ID</th>
				<th orderField="name"
					class="${orderField=='name' ? orderDirection : ''}">车系名</th>
				<th orderField="nav_seq"
					class="${orderField=='nav_seq' ? orderDirection : ''}">导航排序值</th>
				<th orderField="hot_seq"
					class="${orderField=='hot_seq' ? orderDirection : ''}">首页热门车系排序值</th>
				<th orderField="status"
					class="${orderField=='status' ? orderDirection : ''}">在售状态</th>
				<th orderField="deleted"
					class="${orderField=='deleted' ? orderDirection : ''}">是否删除</th>
				<th orderField="updateUserId"
					class="${orderField=='updateUserId' ? orderDirection : ''}">更新人</th>
				<th orderField="updateTime"
					class="${orderField=='updateTime' ? orderDirection : ''}">更新时间</th>
				<th orderField=""
					class="">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="entity" items="${list}">
				<tr target="id" rel="${geli:oid(entity)}">
					<td>${geli:display(entity,'id')}</td>
					<td>${geli:display(entity,'name')}</td>
					<td><input id ="nav_seq" value="${geli:display(entity,'navSeq')}"/></td>
					<td><input id="hot_seq" value="${geli:display(entity,'hotSeq')}"/></td>
					<td>
						<c:if test="${entity.status == 0 }">未上市</c:if>
						<c:if test="${entity.status == 1 }">在售</c:if>
						<c:if test="${entity.status == 2 }">停售</c:if>
					</td>
					<td>
						<c:if test="${entity.deleted == 0 }">否</c:if>
						<c:if test="${entity.deleted == 1 }">是</c:if>
					</td>
					<td>${geli:refer(entity,'updateUserId').name}</td>
					<td>${geli:display(entity,'updateTime')}</td>
					<td>
						<li>
							<!--注：当前文案跟实际状态刚好相反  -->
							<c:if test="${entity.hidden == 0 }"><a href="${ctx}/admin/serialgroup/updatefromlist.do?id=${entity.id}&from=hidden&hidden=1">隐藏</a></c:if>
							<c:if test="${entity.hidden == 1 }"><a href="${ctx}/admin/serialgroup/updatefromlist.do?id=${entity.id}&from=hidden&hidden=0">显示</a></c:if>
						</li>
						<li><a href="">车系图片</a></li>
						<li><a href="">车型底价管理</a></li>
						<li><a href="" target="navTab">查看车系页</a></li>
						<li><a style="cursor:pointer" id="save_seq" onclick="save(${entity.id});">保存</a></li>
						<li><a href="${ctx}/admin/serialgroup/setRecommendModel.do?sgid=${entity.id}" target="dialog" title="设置推荐车型">设置推荐车型</li>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<script type="text/javascript">
		function save(id){
			var nav_seq = $("#nav_seq").val();
			var hot_seq = $("#hot_seq").val();
			window.location.href = "${ctx}/admin/serialgroup/updatefromlist.do?id="+id+"&from=seq&nav_seq="+nav_seq+"&hot_seq="+hot_seq ;
		}
	</script>
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
