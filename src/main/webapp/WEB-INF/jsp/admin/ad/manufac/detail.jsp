<%@page contentType="text/html; charset=UTF-8" session="false"%><%@include
	file="/WEB-INF/jspf/import.jspf"%>
<style>
	.unit .pclass{
		clear:right;
		float:left;
		width:500px;
		padding-left:130px;
		height:10px;
	}
</style>
<div class="pageContent">
	<style>
		.photo-btns .swfupload {
		    background: url(/admin/swfupload/images/img.png) no-repeat;
		    background-position: 0 0;
		}
	</style>
	<form method="post" id="adForm"
		action="${ctx}/admin/ad/manufac/${method}.do<c:if test="${method=='update'}">?id=${param.id}</c:if>"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<input type="hidden" value="${empty entity ? param.type : entity.type }" name="type">
			<div class="unit"> 
				<strong>广告管理</strong>
			</div>
			<div class="divider"></div>
			<div class="unit">
				<label>广告位置: </label>
				<select class="required " name="position" >
					${geli:select_options(entity.position, 'position')}
				</select>
			</div>
			<div class="divider"></div>
			<div class="unit">
				<label>广告标题: </label>
				<input class="textInput required" value="${geli:display(entity, 'name')}${geli:display(copy, 'name')}" name="aname" id="aname"/>
				<p>&nbsp;最多输入20个汉字</p>
			</div>
			<div class="divider"></div>
			<div class="unit">
				<label>活动地址: </label>
				<input class="textInput required" value="${geli:display(entity, 'url')}" name="url" id="url"/>
				<p>&nbsp;必须包含http://或https://</p>
			</div>
			<div class="divider"></div>
			<div class="unit">
				<label>活动图: </label>
				<jsp:include page="/uploadImage"/>
				<div id="imageTest">
				    <div>
				    	<span class="photo-btns">
							<a href="javascript:;" class="updata-pic"  id="placeholderBtn_mad">上传图片</a>
						</span>
						<input class="required" id="pic" type="hidden" value="${geli:display(entity, 'pic')}" name="pic">
					    <div class="imgPlaceholder" id="showImageBlock_mad" style="padding-left:130px;">
					        <ul id="div_manufacPic">
					        	<li>
					        		<c:if test="${not empty entity && not empty entity.pic }">
					        			<img width="160" height="120" src="${geli:display(entity, 'pic')}">
					        		</c:if>
					        	</li>
					        </ul>
					    </div>    
				    </div>
				</div>
				<p class="pclass"> 
					1、建议图片尺寸：????<br>
				</p>
				<p class="pclass"> 
					2、仅支持jpg、png的图片格式<br>
				</p>
				<p class="pclass"> 
					3、图片大小要小于500k
				</p>
			</div>
			<div class="divider"></div>
			<div class="unit"> 
				<label>活动车系: </label>
				<div style="padding-left:130px;">
					<input type="hidden" id="sgIds" name="sgIds" value="" class="required textInput"/>
					<ul>
						<li style="clear:right;float: left;width: 150px">
							<input id="allSerialGroup" type="checkbox" value="0" onclick="checkAllSg()">全选
						</li>
						<c:forEach var="sg" items="${sgList }">
							<li style="clear:right;float: left;width: 150px">
								<input name="serialGroup" id="sg_${sg.id }" type="checkbox" value="${sg.id }">${sg.name }
							</li>
						</c:forEach>
					</ul>
				</div>
				
			</div>
			<div class="divider"></div>
			<div class="unit"> 
				<label>投放城市: </label>
				<div style="padding-left:130px;">
					<!--城市树-->
	                <input type="hidden" id="city" name="city" value="" class="required textInput"/>
	                <input type="checkbox" id="allCity" onclick="checkAllCity()"  value="0-0"/>全国
	                <ul class="tree treeCheck expand">
	                    <c:forEach items="${areaTree}" var="area" varStatus="index">
	                        <li style="clear:right;float: left;width: 200px">
	                            <a id="city_${area[0].id}_0"  ttitle="province" tvalue="${area[0].id}-0" >${area[0].name}</a>
	                            <c:if test="${!empty area[1]}">
	                                <ul id="city_${area[0].id}_0_expand" class="city closeTree" style="display:none;">
	                                    <c:forEach items="${area[1]}" var="city">
	                                        <li>
	                                            <a id="city_${area[0].id}_${city.id}"  ttitle="city" tvalue="${area[0].id}-${city.id}">${city.name}</a>
	                                        </li>
	                                    </c:forEach>
	                                </ul>
	                            </c:if>
	                        </li>
	                    </c:forEach>
	                </ul>
				</div>
				
			</div>
			<div class="divider"></div>
			<div class="unit">
				<label>广告起始时间: </label>
				<input
					class="date textInput"
					dateFmt="yyyy-MM-dd"
					value="<fmt:formatDate value='${entity.beginTime}' pattern='yyyy-MM-dd'></fmt:formatDate>"
					name="beginTime" id="beginTime" />
					<a class="inputDateButton" href="javascript:;">select</a>
				<p style="width: 10px;float: left">-</p>
				<input
					class="date textInput"
					dateFmt="yyyy-MM-dd"
					value="<fmt:formatDate value='${entity.endTime}' pattern='yyyy-MM-dd'></fmt:formatDate>"
					name="endTime" id="endTime"/>
				<a class="inputDateButton" href="javascript:;">select</a>
			</div>
			<c:if test="${param.type == 2 || entity.type == 2 }">
				<div class="divider"></div>
				<div class="unit">
					<label>是否需要收集线索: </label>
					<input name="isOrder" type="radio" value="0" ${(empty entity || entity.isOrder == 0) ? 'checked' : '' } onclick="isOrderChange()">否 &nbsp; &nbsp;
					<input name="isOrder" type="radio" value="1" ${entity.isOrder == 1 ? 'checked' : '' } onclick="isOrderChange()">是
				</div>
				<div id="isOrderDiv" style="display: none;">
					<div class="divider"></div>
					<div class="unit">
						<label>报名按钮名称: </label>
						<input class="textInput required" value="${geli:display(orderform, 'buttonName')}" name="buttonName" id="buttonName" maxlength="5"/>
						<p>&nbsp;最多输入5个汉字</p>
					</div>
					<div class="divider"></div>
					<div class="unit">
						<label>按钮颜色: </label>
						<input name="buttonColor" type="radio" value="蓝色" ${(empty orderform || orderform.buttonColor=='蓝色') ? 'checked' : '' }>蓝色 &nbsp; &nbsp;
						<input name="buttonColor" type="radio" value="红色" ${orderform.buttonColor == '红色' ? 'checked' : '' }>红色
					</div>
					<div class="divider"></div>
					<div class="unit">
						<label>留言内容: </label>
						<div style="padding-left:130px;">
							<ul>
								<li style="clear:right;float: left;width: 150px">
									<input name="oname" type="checkbox" value="1" ${empty entity || orderform.name == 1 ? 'checked' : '' } onclick="checkTrue(this)">姓名
								</li>
								<li style="clear:right;float: left;width: 150px">
									<input name="mobile" type="checkbox" value="1" ${empty entity || orderform.mobile == 1 ? 'checked' : '' } onclick="checkTrue(this)">电话 
								</li>
								<li style="clear:right;float: left;width: 150px">
									<input name="gender" type="checkbox" value="1" ${orderform.gender == 1 ? 'checked' : '' }>性别
								</li>
								<li style="clear:right;float: left;width: 600px">
									<input name="serialGroupId" type="checkbox" value="1" ${orderform.serialGroupId == 1 ? 'checked' : '' }>车系（是否需要选择车型<input type="radio" name="modelId" value="1" ${orderform.modelId == 1 ? 'checked' : '' }>是&nbsp; <input type="radio" name="modelId" value="0" ${orderform.modelId == 0 || empty orderform ? 'checked' : '' }>否）
								</li>
								<li style="clear:right;float: left;width: 150px">
									<input name="modelId" type="checkbox" value="1" ${orderform.modelId == 1 ? 'checked' : '' }>车型
								</li>
								<li style="clear:right;float: left;width: 150px">
									<input name="dealerId" type="checkbox" value="1" ${orderform.dealerId == 1 ? 'checked' : '' }>经销商
								</li>
								<li style="clear:right;float: left;width: 150px">
									<input name="buyTime" type="checkbox" value="1" ${orderform.buyTime == 1 ? 'checked' : '' }>购买时间
								</li>
								<li style="clear:right;float: left;width: 150px">
									<input name="payType" type="checkbox" value="1" ${orderform.payType == 1 ? 'checked' : '' }>购买方式
								</li>
							</ul>
						</div>
					</div>
				</div>
			</c:if>
		</div>
		<div class="formBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="button" onclick="btn_submit()">保存</button>
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
function checkUrl(url){
	var regex = /http(s)?:\/\/([\w-]+\.)+[\w-]+(\/[\w- .\/?%&=]*)?/; 
	var objExp = new RegExp(regex);
	
	return objExp.test(url);
}

function btn_submit(){
	var cityStr = "";
	var sgStr = "";
	var flag = 0;
	
	if(!checkCount("广告标题","aname",20)){
		return false;
	}
	
	if ($("#beginTime",navTab.getCurrentPanel()).val() == "" || $("#endTime",navTab.getCurrentPanel()).val() == "") {
        alertMsg.error("请输入广告起始时间！");
        flag = 1;
    } else {
        if (!compareDate(new Date().format("yyyy-MM-dd HH:mm:ss"),$("#endTime",navTab.getCurrentPanel()).val(),0)){
            alertMsg.error("广告结束时间需大于当期时间！");
            flag = 1;
        } else if (!compareDate($("#beginTime",navTab.getCurrentPanel()).val(),$("#endTime",navTab.getCurrentPanel()).val(),0)){
        	alertMsg.error("广告结束时间需大于开始时间！");
            flag = 1;
        } else if(!checkUrl($("#url").val())){
        	alertMsg.error("请输入正确的链接地址！");
            flag = 1;
        }
    }
	if(flag){
		return false;
	}
	
	if ($("#allCity", navTab.getCurrentPanel()).attr("checked") == "checked") {
        //用户选择了全国
        cityStr = "0-0";
    } else {
        $("div.checked,div.indeterminate", navTab.getCurrentPanel()).each(function () {
            var cityValue = $(this).children("input[title='province'],input[title='city']").val();
            if (cityValue != undefined) {
                cityStr += cityValue + ",";
            }
        });
        
        if(cityStr != ''){
    		cityStr = cityStr.substring(0,cityStr.length-1);
    	}
    }
	
	$("input:checkbox[name='serialGroup']", navTab.getCurrentPanel()).each(function () {
        var sgValue = $(this).val();
        if (sgValue != undefined && $(this, navTab.getCurrentPanel()).attr("checked") == "checked") {
        	sgStr += sgValue + ",";
        }
    });
	
	if(sgStr != ''){
		sgStr = sgStr.substring(0,sgStr.length-1);
	}
	
	$("#city", navTab.getCurrentPanel()).val(cityStr);
    $("#sgIds", navTab.getCurrentPanel()).val(sgStr);
    $("#adForm", navTab.getCurrentPanel()).submit();
}


//========城市树==========
function  checkAllCity() {
    if ($("#allCity", navTab.getCurrentPanel()).attr("checked") == "checked") {
        $("input:checkbox[title='province'],input:checkbox[title='city']", navTab.getCurrentPanel()).each(function () {
        	$(this).parent().attr("class", "ckbox checked");
        });
    } else {
        $("input:checkbox[title='province'],input:checkbox[title='city']", navTab.getCurrentPanel()).each(function () {
            $(this).parent().attr("class", "ckbox unchecked");
        });
    }
}

var citys = "";
var isAllCity = '${isAllCity}';
if (${!empty citys}) {
   citys = eval(${citys});  //机构的服务城市
}
initCity(citys, isAllCity);
//初始化城市
function initCity(citys, isAllCity) {
    var init = window.setInterval(function () {
        var hasShow = $("input:checkbox[title='province']", navTab.getCurrentPanel()).length;
        if (hasShow > 0) {
            $(".closeTree", navTab.getCurrentPanel()).css("display","none");
            $("input:checkbox[title='province']", navTab.getCurrentPanel()).each(function () {
            	if($(this).parent().parent().parent().find(".city").length > 0){
            		$(this).parent().prev("div").attr("class", "expandable");
            	}
                
            });
            if (isAllCity == 'true') {
                //全部城市
                $("#allCity", navTab.getCurrentPanel()).attr("checked", "checked");
                checkAllCity();
            } else {
                //部分城市
                for (var i = 0; i < citys.length; i++) {
                    var pId = citys[i].provinceId;
                    var cId = citys[i].cityId;
                    $("#city_" + pId + "_0").prev("div").attr("class", "ckbox checked");
                    $("#city_" + pId + "_" + cId).prev("div").attr("class", "ckbox checked");
                }
            }
            window.clearInterval(init);
        }
    }, 500);
}

//========车系==========
function checkAllSg(){
	if($("#allSerialGroup", navTab.getCurrentPanel()).attr("checked") == "checked"){
		$("input:checkbox[name='serialGroup']", navTab.getCurrentPanel()).each(function () {
        	$(this).attr("checked","checked");
        });
	} else {
		$("input:checkbox[name='serialGroup']", navTab.getCurrentPanel()).each(function () {
        	$(this).attr("checked",false);
        });
	}
}

var serialGroups = "";
var isAllSg = '${isAllSg}';
if (${!empty serialGroups}) {
	serialGroups = eval(${serialGroups});  //机构的服务城市
}
initSg(serialGroups, isAllSg);

//初始化车系
function initSg(serialGroups, isAllSg) {
    var init = window.setInterval(function () {
        var hasShow = $("input:checkbox[name='serialGroup']", navTab.getCurrentPanel()).length;
        if (hasShow > 0) {
            $(".city .closeTree", navTab.getCurrentPanel()).attr("style", "display:none");
            if (isAllSg == 'true') {
                //全部车系
                $("#allSerialGroup", navTab.getCurrentPanel()).attr("checked", "checked");
                checkAllSg();
            } else {
                //部分车系
                for (var i = 0; i < serialGroups.length; i++) {
                	var sgId = serialGroups[i].id;
                	$("#sg_" + sgId, navTab.getCurrentPanel()).attr("checked","checked");
                }
            }
            window.clearInterval(init);
        }
    }, 500);
}

/**
 * 比较两个日期的大小(endDate是否大于startDate)
 * @param startDate (格式:2010-10-10 11:00:00)
 * @param endDate (格式:2010-10-10 11:00:00)
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

function isOrderChange(){
	var isOrder = $('input[name="isOrder"]:checked').val();
	if(isOrder == 0){
		$("#isOrderDiv").css("display","none");
		$("#buttonName").removeClass("required");
	} else {
		$("#buttonName").addClass("required");
		$("#isOrderDiv").css("display","block");
	}
}
isOrderChange();


function checkTrue($this){
	if($($this).attr('checked') != 'checked'){
		$($this).attr('checked',true);
	}
}
</script>

<script>
    //单指令上传
    initSWFObj("placeholderBtn_mad", "showImageBlock_mad", "div_manufacPic", commands["600_400Pic"], "160", "120", "150", "40", uploadCallback);

    //多指令上传
    //initMultiCommandSWFObj("placeholderBtn", "showImageBlock", "div_uploadImg", "command=603001&command=603001", "120", "90", "200", "100", uploadCallback);

    function uploadCallback() {
        $("#pic").val($("#manufacPic").val());
    }
</script>
<script type="text/javascript">
/**
 * 限定输入内容的长度
 * @param id
 * @param limit
 * @returns
 */
function checkLimit(id,limit){
    var value = $.trim($("#"+id,navTab.getCurrentPanel()).val());
    var count = countTxt(value);
    if(count > limit){
        value = value.substring(0, count-(count-limit));
        $("#"+id,navTab.getCurrentPanel()).val(value);
    }
}

/**
 * 检查输入内容是否超出长度限制
 * @param id
 * @param limit
 * @returns {boolean}
 */
function checkCount(name,id,limit){
    var value = $.trim($("#"+id,navTab.getCurrentPanel()).val());
    var count = countTxt(value);
    if(count > limit){
    	alertMsg.error(name + "字数超出限制");
    	$("#"+id,navTab.getCurrentPanel()).focus();
        return false;
    } else {
    	return true;
    }
}
 
 /**
  * 计算字符的个数
  * @param val
  * @returns {number}
  */
 function countTxt(val){
     var len = val.length;
     var count = 0;
     for(i=0;i<len;i++){
         if (isChinese(val.charAt(i))){
             count++;
         }else{
             count += 0.5;
         }
     }
     return count;
 }

 /**
  * 判断是否为中文字符
  * @param str
  * @returns {boolean}
  */
 function isChinese(str){
     var lst = /[^\u4E00-\u9FA5]/g;
     return !lst.test(str);
 }
</script>