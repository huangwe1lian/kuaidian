<%@page contentType="text/html; charset=UTF-8" session="false"%><%@include
	file="/WEB-INF/jspf/import.jspf"%><div class="pageContent">
	<form method="post" id="mediaNewsForm"
		action="${ctx}/admin/medianews/recommend.do?id=${entity.id}"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<div class="unit"> 
				<strong>推荐媒体资讯</strong>
			</div>
			<div class="divider"></div>
			<div class="unit"> 
				<label>是否推荐: </label>
				<select class="required " name="isRecommend" id="isRecommend" onchange="recommendChange()">
					<option value="0" ${(0 == entity.isRecommend) ? 'selected' : '' }>否</option>
					<option value="1" ${(1 == entity.isRecommend) ? 'selected' : '' }>是</option>
				</select>
			</div>
			<div class="divider"></div>
			<div class="unit" id="recommendTime"> 
				<label>推荐起始时间: </label><input
					class="date textInput"
					dateFmt="yyyy-MM-dd"
					value="<fmt:formatDate value='${entity.beginTime}' pattern='yyyy-MM-dd'></fmt:formatDate>"
					name="beginTime" 
					id="beginTime"/>
				<a class="inputDateButton" href="javascript:;" id="beginTimeSelect">select</a>
				<p style="width: 10px;float: left">-</p>
				<input
					class="date textInput beginEndValidate"
					dateFmt="yyyy-MM-dd"
					value="<fmt:formatDate value='${entity.endTime}' pattern='yyyy-MM-dd'></fmt:formatDate>"
					name="endTime" 
					id="endTime"/>
				<a class="inputDateButton" href="javascript:;" id="endTimeSelect">select</a>
			</div>
		</div>
		<div class="formBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="button"  onclick="btn_submit()">保存</button>
						</div>
					</div>
				</li>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="close">取消</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>
</div>
<script type="text/javascript">
function btn_submit(){
	var cityStr = "";
	var sgStr = "";
	var flag = 0;
	if ($("#isRecommend").val() == 1 && ($("#beginTime").val() == "" || $("#endTime").val() == "")) {
        alertMsg.error("请输入推荐起始时间！");
        flag = 1;
    } else {
        if (!compareDate(new Date().format("yyyy-MM-dd"),$("#endTime").val(),0)){
            alertMsg.error("产品有效期结束时间需大于当期时间！");
            flag = 1;
        } else if (!compareDate($("#beginTime").val(),$("#endTime").val(),0)){
        	alertMsg.error("产品有效期结束时间需大于开始时间！");
            flag = 1;
        } 
    }
	if(flag){
		return false;
	}
	
    $("#mediaNewsForm").submit();
}



/**
 * 比较两个日期的大小(endDate是否大于startDate)
 * @param startDate (格式:2010-10-10)
 * @param endDate (格式:2010-10-10)
 * @param type(0:不作相等比较;1:作相等比较)
 * @return true or false
 */
function compareDate(startDate,endDate,type) {
    var startMonth = startDate.substring(5,startDate.lastIndexOf ("-"));   
    var startDay = startDate.substring(startDate.length,startDate.lastIndexOf ("-")+1);   
    var startYear = startDate.substring(0,startDate.indexOf ("-"));   
    var endMonth = endDate.substring(5,endDate.lastIndexOf ("-"));   
    var endDay = endDate.substring(endDate.length,endDate.lastIndexOf ("-")+1);   
    var endYear = endDate.substring(0,endDate.indexOf ("-"));   
    var result = false;
    if(type==0){
        if(Date.parse(startMonth+"/"+startDay+"/"+startYear) > Date.parse(endMonth+"/"+endDay+"/"+endYear)){
            result = false;
        }else{
            result = true;
        }
    }else{
        if(Date.parse(startMonth+"/"+startDay+"/"+startYear)>=Date.parse(endMonth+"/"+endDay+"/"+endYear)){
            result = false;
        }else{
            result = true;
        }
    }
    return result;   
}

//日期格式化函数
Date.prototype.format = function(format)
{ 
    var o = { 
        "M+" : this.getMonth()+1, //month 
        "d+" : this.getDate(), //day 
        "h+" : this.getHours(), //hour 
        "m+" : this.getMinutes(), //minute 
        "s+" : this.getSeconds(), //second 
        "q+" : Math.floor((this.getMonth()+3)/3), //quarter 
        "S" : this.getMilliseconds() //millisecond 
    } 
    if(/(y+)/.test(format)) format=format.replace(RegExp.$1, 
    (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
    for(var k in o)if(new RegExp("("+ k +")").test(format)) 
        format = format.replace(RegExp.$1, 
    RegExp.$1.length==1 ? o[k] : 
        ("00"+ o[k]).substr((""+ o[k]).length)); 
    return format; 
} 

function recommendChange(){
	var isRecommend = $("#isRecommend").val();
	if(isRecommend == 0){
		$("#recommendTime").css("display","none");
		$("#beginTime").val("");
		$("#endTime").val("");
	} else {
		$("#recommendTime").css("display","block");
	}
}
recommendChange();
</script>
