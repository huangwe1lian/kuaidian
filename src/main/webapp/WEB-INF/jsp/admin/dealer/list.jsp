<%@page contentType="text/html; charset=UTF-8" session="false"%>
<%@include file="/WEB-INF/jspf/import.jspf"%>
<form id="pagerForm" method="get">
	<input type="hidden" name="pageNum" value="${pageNum}" /> 
	<input type="hidden" name="numPerPage" value="${numPerPage}" /> 
	<input type="hidden" name="orderField" value="${orderField}" /> 
	<input type="hidden" name="orderDirection" value="${orderDirection}" />
	<input type="hidden" name="id" value="${param.id}" /> 
	<input type="hidden" name="provinceId" value="${param.provinceId}" /> 
	<input type="hidden" name="cityId" value="${param.cityId}" /> 
	<input type="hidden" name="hidden" value="${param.hidden}" /> 
	<input type="hidden" name="shortName" value="${param.shortName}" />
	<input type="hidden" name="name" value="${param.name}" />
</form>
<div class="pageHeader">
    <form method="post" action="${ctx}/admin/dealer/list.do"  class="pageForm required-validate" onsubmit=" return navTabSearch(this)">
	    <input type="hidden" name="pageNum" value="${pageNum}" />
		<input type="hidden" name="numPerPage" value="${numPerPage}" />
        <div class="pageFormContent">
            	<p>
                    <label>经销商ID:</label>
                    <input class="textInput digits valid" name="id" value="${param.id}" type="text">
                </p>
                <p>
                    <label>经销商全称:</label>
                    <input class="textInput" name="name" value="${param.name}" type="text">
                </p>
                <p>
                    <label>经销商简称:</label>
                    <input class="textInput" name="shortName" value="${param.shortName}" type="text">
                </p>
                <p>
                    <label>城市: </label>
					<select name="provinceId" id="d_provinceId" onchange="getCity()"> 
						<option value="" >请选择省份</option>
					</select>
					<select name="cityId" id="d_cityId">
						<option value="">请选择城市</option>
					</select>
                </p>
                <p>
                	<label>显示状态:</label>
                    <select class="required " name="hidden" >
						<option value="" >全部</option>
						<option value="0" ${(0 == param.hidden && not empty param.hidden) ? 'selected' : '' }>显示</option>
						<option value="1" ${(1 == param.hidden) ? 'selected' : '' }>隐藏</option>
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
		</ul>
	</div>
	<table class="table" style="width: 100%" layoutH="170">
		<thead>
			<tr>
				<th orderField="id"
					class="${orderField=='id' ? orderDirection : ''}">ID</th>
				<th orderField="name"
					class="${orderField=='name' ? orderDirection : ''}">经销商全称</th>	
				<th orderField="shortName"
					class="${orderField=='shortName' ? orderDirection : ''}">经销商简称</th>
				<th orderField="cityId"
					class="${orderField=='cityId' ? orderDirection : ''}">城市</th>
				<th orderField="seq" style="width: 150px;"
					class="${orderField=='seq' ? orderDirection : ''}">排序值</th>
				<th orderField="hidden"
					class="${orderField=='hidden' ? orderDirection : ''}">显示状态</th>
				<th orderField="createTime" 
					class="${orderField=='createTime' ? orderDirection : ''}">创建时间</th>
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
					<td>${geli:display(entity,'shortName')}</td>
					<td>${geli:refer(entity,'cityId').name}</td>
					<td>
						<input type="text" value="${geli:display(entity,'seq')}" id="seq_${geli:oid(entity)}" size="10" maxlength="9">
						<a href="javascript:;" onclick="updateDealerSeq(this,${geli:display(entity,'id')})">保存</a>
					</td>
					<td ${entity.hidden==0?'':'style=color:gray'}>${entity.hidden==0?'显示':'隐藏'}</td>
					<td>${geli:display(entity,'createTime')}</td>
					<td>${geli:display(entity,'updateTime')}</td>
					<td>
						<a href="${ctx}/admin/dealer/update.do?id=${geli:oid(entity)}&hidden=${entity.hidden}" target="ajaxToDo" >${entity.hidden==0?'隐藏':'显示'}</a>&nbsp;&nbsp;  
						<a href="javascript:;">查看店铺首页</a>&nbsp;&nbsp;
						<a href="${ctx}/admin/dealer/detail.do?id=${geli:oid(entity)}" target="dialog" width="700" height="550">详情</a>  
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
	$("#d_provinceId").html(options);
	if(isStart){
		$("#d_provinceId").val(pid);
		getCity();
	}
	
}

function getCity(){
	var pid = $("#d_provinceId").val();
	$.getScript("/api/city/list?callback=setCity&pid=" +pid);
}

function setCity(data){
	var pid = $("#d_provinceId").val();
	var options = '<option value="" >请选择城市</option>';
	if(pid >= 0){
		options += '<option value="0" >全省市</option>';
	}
	for(var i=0;i < data.length;i++){
		options += '<option value="' + data[i].cId + '" >' + data[i].name + '</option>'
	}
	$("#d_cityId").html(options);
	if(isStart > 0) {
		$("#d_cityId").val(cid);
		isStart = 0;
	}
}

function updateDealerSeq(obj,id){
	var seq = $("#seq_"+id).val();
	var regex = /[^\d*{9}$]/g;
	if(regex.test(seq)){
		alertMsg.warn("请输入大于或者等于0的整数！");
		return;
	}
	 $.ajax({
		url:"${ctx}/admin/dealer/update.do",
		data:{id:id,seq:seq},
		method:'post',
		dataType:'json',
		success:function(result){
			if(result.statusCode==200){
				alertMsg.correct(result.message);
				navTab.reload("${ctx}/admin/dealer/list.do");
			}else{
				alertMsg.error(result.message);
			}
		}
	}); 
}
</script>