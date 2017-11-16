<%@page contentType="text/html; charset=UTF-8" session="false"%><%@include
	file="/WEB-INF/jspf/import.jspf"%><div class="pageContent">
	<form method="post" id="roleCityForm"
		action="${ctx}/admin/role/${method}.do<c:if test="${method=='update'}">?id=${param.id}</c:if>"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<div class="unit"> 
				<strong>角色管理</strong>
			</div>
			<div class="divider"></div>
			<div class="unit"> 
				<label>角色名称: </label>
				<input class="textInput required"
					${geli:not_oper('name') ? 'readonly="readonly"' : ''}
					value="${geli:display(entity, 'name')}" name="name" />
			</div>
			<div class="divider"></div>
			<div class="unit"> 
				<label>角色类型: </label>
				<select class="required " name="typ" >
					<option value="1" ${(1 == entity.typ) ? 'selected' : '' }>管理员</option>
					<option value="2" ${(2 == entity.typ) ? 'selected' : '' }>厂商</option>
					<option value="3" ${(3 == entity.typ) ? 'selected' : '' }>区域</option>
					<option value="4" ${(4 == entity.typ) ? 'selected' : '' }>代理商</option>
				</select>
			</div>
			<div class="divider"></div>
			<div class="unit"> 
				<label>关联城市: </label>
				<div style="padding-left:130px;">
					<!--城市树-->
	                <input type="hidden" id="city" name="city" value="" class="required textInput"/>
	                <input type="checkbox" id="allCity" onclick="checkAllCity()"  value="0-0"/>全国
	                <ul class="tree treeCheck expand">
	                    <c:forEach items="${areaTree}" var="area" varStatus="index">
	                        <li style="clear:right;float: left;width: 200px">
	                            <a id="city_${area[0].id}_0"  ttitle="province" tvalue="${area[0].id}-0" >${area[0].name}</a>
	                            <c:if test="${!empty area[1]}">
	                                <ul id="city_${area[0].id}_0_expand" class="city closeTree">
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
				<label>角色功能描述: </label>
				<textarea class="textInput textArea" name="dsc" cols="50" rows="8">${geli:display(entity, 'dsc')}</textarea>
			</div>
		</div>
		<div class="formBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="botton" onclick="btn_submit()">保存</button>
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
	
	$("#city", navTab.getCurrentPanel()).val(cityStr);
    $("#roleCityForm", navTab.getCurrentPanel()).submit();
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
if (${not empty entity}) {
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
</script>
