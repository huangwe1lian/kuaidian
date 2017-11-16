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
	<div class="shoulib" ${method == "update" ? "style='display:none'":""}>
            <span href="###" class="hoverps"><div class="beCenter" style="padding-top: 10px">基本信息</div></span><em class="bg3"></em>
            <span href="###" ><div class="beCenter" style="padding-top: 10px">活动详细内容</div></span><em class="bg4"></em>
    </div>
	<form method="post" id="adForm"
		action="${ctx}/admin/ad/template/${method}basis.do<c:if test="${method=='update'}">?id=${param.id}</c:if>"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, ${method == "update" ? 'navTabAjaxDone':'nextToDetail'});">
		<div class="pageFormContent" layoutH="${method == 'update' ? '56':'120'}">
			<div class="unit"> 
				<strong>模板广告管理</strong>
			</div>
			<div class="divider"></div>
			<div class="unit">
				<label>广告标题: </label>
				<input class="textInput required" value="${geli:display(entity, 'name')}" name="aname" id="aname" />
				<p>&nbsp;最多输入20个汉字</p>
			</div>
			<div class="divider"></div>
			<div class="unit">
				<label>活动图: </label>
				<jsp:include page="/uploadImage"/>
				<div id="imageTest">
				    <div>
				    	<span class="photo-btns">
							<a href="javascript:;" class="updata-pic"  id="placeholderBtn_temp">上传图片</a>
						</span>
						<input class="required" id="pic" type="hidden" value="${geli:display(entity, 'pic')}" name="pic">
					    <div class="imgPlaceholder" id="showImageBlock_temp" style="padding-left:130px;">
					        <ul id="div_templatePic">
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
						<c:forEach var="sg" items="${sgList }">
							<li style="clear:right;float: left;width: 150px">
								<input name="serialGroup" id="sg_${sg.id }" type="checkbox" value="${sg.id }" onclick="serialGroupClick(${sg.id })">${sg.name }
							</li>
						</c:forEach>
					</ul>
				</div>
				
			</div>
			<div class="divider"></div>
			<div class="unit"> 
				<label>活动车型: </label>
				<input type="hidden" id="mIds" name="mIds" value="" class="required textInput"/>
				<ul id="modelul">
					
				</ul>
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
				<label>留言内容: </label>
				<div style="padding-left:130px;">
					<ul>
						<li style="clear:right;float: left;width: 150px">
							<input name="oname" type="checkbox" value="1" ${empty entity || orderform.name == 1 ? 'checked' : '' }>姓名
						</li>
						<li style="clear:right;float: left;width: 150px">
							<input name="mobile" type="checkbox" value="1" ${empty entity || orderform.mobile == 1 ? 'checked' : '' }>电话 
						</li>
						<li style="clear:right;float: left;width: 150px">
							<input name="gender" type="checkbox" value="1" ${orderform.gender == 1 ? 'checked' : '' }>性别
						</li>
						<li style="clear:right;float: left;width: 600px">
							<input name="serialGroupId" type="checkbox" value="1" ${orderform.serialGroupId == 1 ? 'checked' : '' }>车系（是否需要选择车型<input type="radio" name="modelId" value="1" ${orderform.modelId == 1 ? 'checked' : '' }>是&nbsp; <input type="radio" name="modelId" value="0" ${orderform.modelId == 0 || empty orderform ? 'checked' : '' }>否）
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
		<div class="formBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<c:if test="${method != 'update' }">
								<button type="button" onclick="btn_submit()">下一步</button>
							</c:if>
							<c:if test="${method == 'update' }">
								<button type="button" onclick="btn_submit()">保存</button>
							</c:if>
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
	var mStr = "";
	
	if(!checkCount("广告标题","aname",20)){
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
	
	$("input:checkbox[name='model']", navTab.getCurrentPanel()).each(function () {
        var mValue = $(this).val();
        if (mValue != undefined && $(this, navTab.getCurrentPanel()).attr("checked") == "checked") {
        	mStr += mValue + ",";
        }
    });
	
	if(mStr != ''){
		mStr = mStr.substring(0,mStr.length-1);
	}
	
	$("#city", navTab.getCurrentPanel()).val(cityStr);
    $("#sgIds", navTab.getCurrentPanel()).val(sgStr);
    $("#mIds", navTab.getCurrentPanel()).val(mStr);
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
if (${!empty entity}) {
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
var modelMap = "";
if (${!empty modelMap}) {
	modelMap = eval(${modelMap});  //机构的服务城市
}
function addModel(sgId){
	var name = 'sg_' + sgId;
	var models = modelMap[name];
	var i=0;
	var html = "";
	for(i=0;i<models.length;i++){
		html += '<li style="clear:right;float: left;width: 250px">'
			 + '<input name="model" id="m_'+models[i].id+'" sgId='+models[i].serialGroupId+' type="checkbox" ${method=='update'?'':'checked'} value="'+models[i].serialGroupId + '-' +models[i].id+'">'+models[i].name
			 + '</li>';
	}
	$("#modelul").append(html);
}
function removeModel(sgId){
	$("input:checkbox[sgId="+sgId+"]").each(function(){
		$(this).parent().remove();
	});
}
function serialGroupClick(sgId){
	if($("#sg_" + sgId).attr("checked") == 'checked'){
		addModel(sgId);
	} else {
		removeModel(sgId);
	}
}

var admodels = "";
if (${!empty models}) {
	admodels = eval(${models});  //机构的服务城市
}

//初始化车系
function initModel(models) {
    var init = window.setInterval(function () {
        var hasShow = $("input:checkbox[name='model']", navTab.getCurrentPanel()).length;
        if (hasShow > 0) {
            //部分车系
            for (var i = 0; i < models.length; i++) {
            	var mid = models[i].modelId;
            	$("#m_" + mid, navTab.getCurrentPanel()).attr("checked","checked");
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
            //部分车系
            for (var i = 0; i < serialGroups.length; i++) {
            	var sgId = serialGroups[i].id;
            	$("#sg_" + sgId, navTab.getCurrentPanel()).attr("checked","checked");
            	addModel(sgId);
            }
            initModel(admodels);
            window.clearInterval(init);
        }
    }, 500);
}

//========日期==========
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

<script>
    //单指令上传
    initSWFObj("placeholderBtn_temp", "showImageBlock_temp", "div_templatePic", commands["600_400Pic"], "160", "120", "150", "40", uploadCallback);

    //多指令上传
    //initMultiCommandSWFObj("placeholderBtn", "showImageBlock", "div_uploadImg", "command=603001&command=603001", "120", "90", "200", "100", uploadCallback);

    function uploadCallback() {
    	$("#pic").val($("#templatePic").val());
    }
</script>
<script>
    function nextToDetail(json){
        if(json.statusCode == 200){
            var adId = json.adId;
            navTab.closeCurrentTab("template-ad-basis");
            navTab.openTab("template-ad-detail", "/admin/ad/template/createdetail.do?adId="+adId, { title:"模板广告内容"});
        } else {
            alert(json.message);
        }
    }
</script>
