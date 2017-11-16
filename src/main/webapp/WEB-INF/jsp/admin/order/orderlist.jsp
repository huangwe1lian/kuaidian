<%@page contentType="text/html; charset=UTF-8" session="false"%>
<%@include file="/WEB-INF/jspf/import.jspf"%>
<!-- 搜索 -->
<div class="pageHeader">

	<form id="pagerForm" method="post" action="${ctx}/admin/order/list.do"  class="pageForm required-validate" onsubmit=" return navTabSearch(this)">
		<input type="hidden" name="_p" value="${_p}" /> 
		<input type="hidden" name="pageNum" value="${pageNum}" /> 
		<input type="hidden" name="numPerPage" value="${numPerPage}" /> 
		<input type="hidden" name="orderField" value="${orderField}" />
		<input type="hidden" name="orderDirection" value="${orderDirection}" />
 
        <div class="pageFormContent">
                <p>
                    <label>姓名:</label>
                    <input class="textInput" name="name" value="${name}" type="text">
                </p>
                <p>
                    <label>手机:</label>
                    <input class="textInput" name="mobile" value="${mobile}" type="text">
                </p>
                <p>
                    <label>来源:</label>
                    <input class="textInput" name="refer" value="${refer}" type="text">
                </p>
                <p>
                    <label>平台:</label>
                    <select class="required " name="origin" >
						<option value="-1" >全部</option>
						<option value="0" ${(0 == origin) ? 'selected' : '' }>未知</option>
						<option value="1" ${(1 == origin) ? 'selected' : '' }>PC</option>
						<option value="2" ${(2 == origin) ? 'selected' : '' }>wap</option>
					</select>
                </p>
                <p>
                	<label>重复线索：</label>
                	<select class="required" name="isRepeat">
                		<option value="-1">请选择</option>
                		<option value="1">是</option>
                		<option value="0">否</option>
                	</select>
                </p>
                <p>
					<label>城市: </label>
					<select name="provinceId" id="oprovinceId" onchange="getCity()"> 
						<option value="" >请选择省份</option>
					</select>
					<select name="cityId" id="ocityId">
						<option value="">请选择城市</option>
					</select>
				</p>
				<p>
					<label>意向车系：</label>
					<select name="serialGroupId" class="required">
						<option value="-1">请选择</option>
						<c:forEach var="sg" items ="${sgs}">
							<option value="${sg.id }">${sg.name}</option>
						</c:forEach>
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
			<li class="">
                <a class="icon" href="javascript:exportExcel()">
                    <span>导出</span>
                </a>
            </li>
            <li class="line">line</li>
		</ul>
	</div>
	<table class="table" style="width: 100%" layoutH="200">
		<thead>
			<tr>
				<th orderField="id"
					class="${orderField=='id' ? orderDirection : ''}">名单线索量ID</th>
				<th orderField="name"
					class="${orderField=='name' ? orderDirection : ''}">姓名</th>
				<th orderField="mobile"
					class="${orderField=='mobile' ? orderDirection : ''}">手机号</th>
				<th orderField="dealerId"
					class="${orderField=='dealerId' ? orderDirection : ''}">经销商</th>
				<th orderField="cityId"
					class="${orderField=='cityId' ? orderDirection : ''}">城市</th>
				<th orderField="serialGroupId"
					class="${orderField=='serialGroupId' ? orderDirection : ''}">车系</th>
				<th orderField="origin"
					class="${orderField=='origin' ? orderDirection : ''}">平台来源</th>
				<th orderField="refer"
					class="${orderField=='refer' ? orderDirection : ''}">页面来源</th>
				<th orderField="createTime"
					class="${orderField=='createTime' ? orderDirection : ''}">创建时间</th>
				<th orderField=""
					class="">重复线索</th>
				<th>操作</th>	
			</tr>
		</thead>
		<tbody>
			<c:forEach var="entity" items="${list}">
				<tr target="id" rel="${geli:oid(entity)}">
					<td>${geli:display(entity,'id')}</td>
					<td>${geli:display(entity,'name')}</td>
					<td>${geli:display(entity,'mobile')}</td>
					<td>${geli:refer(entity,'dealerId').name}</td>
					<td>${geli:refer(entity,'cityId').name}</td>
					<td>${geli:refer(entity,'serialGroupId').name}</td>
					<td>
						<c:choose>
							<c:when test="${entity.origin == 0 }" >未知</c:when>
							<c:when test="${entity.origin == 1 }" >PC</c:when>
							<c:when test="${entity.origin == 2 }" >wap</c:when>
						</c:choose>
					</td>
					
					<td>${geli:display(entity,'refer')}</td>
					<td>${geli:display(entity,'createTime')}</td>
					<td>
						<c:if test="${entity.repeat >1 }">是</c:if>
						<c:if test="${entity.repeat ==1 }">否</c:if>
					</td>
					<td><a href="${ctx}/admin/order/showDetail.do?id=${entity.id}" target="dialog" title="详情" width="600" height="600">详情</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<script type="text/javascript">
        function exportExcel() {
            var $name = '${param.name}';
            var $mobile = '${param.mobile}';
            var $refer = '${param.refer}';
            var $origin = '${param.origin}';
            var isEmpty = ${fn:length(list)>0?"false":"true"};
            if(isEmpty){
                alertMsg.warn('没有线索量数据！')
            } else {
                alertMsg.confirm("确认是否导出对应的线索量数据？", {
                    okCall: function(){
                        window.location.href = '${ctx}/admin/order/createExcel.do?name=' 
                        		+ $name + '&mobile=' + $mobile + '&refer='
                        		+ $refer + '&origin=' + $origin;
                    }
                });


            }
        }
        
        //城市
    	$(function(){
    		getProvince();
    	});
    	
    	var pid = '${param.provinceId}';
    	var cid = '${param.cityId}';
    	var isStart = 1;
    	
    	function getProvince(){
    		$.getScript("/api/province/list?callback=setProvince");
    	}
    	
    	function setProvince(data){
    		var options = '<option value="" >请选择省份</option><option value="0" >全国</option>';
    		for(var i=0;i < data.length;i++){
    			options += '<option value="' + data[i].pId + '" >' + data[i].name + '</option>'
    		}
    		$("#oprovinceId").html(options);
    		if(isStart){
    			$("#oprovinceId").val(pid);
    			getCity();
    		}
    		
    	}
    	
    	function getCity(){
    		var pid = $("#oprovinceId").val();
    		$.getScript("/api/city/list?callback=setCity&pid=" +pid);
    	}
    	
    	function setCity(data){
    		var pid = $("#oprovinceId").val();
    		var options = '<option value="" >请选择城市</option>';
    		if(pid >= 0){
    			options += '<option value="0" >全省市</option>';
    		}
    		for(var i=0;i < data.length;i++){
    			options += '<option value="' + data[i].cId + '" >' + data[i].name + '</option>'
    		}
    		$("#ocityId").html(options);
    		if(isStart > 0) {
    			$("#ocityId").val(cid);
    			isStart = 0;
    		}
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
