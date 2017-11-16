<%@page contentType="text/html; charset=UTF-8" session="false"%><%@include
	file="/WEB-INF/jspf/import.jspf"%><div class="pageContent">
	<form method="post" id="adForm"
		action="${ctx}/admin/ad/manufac/audit.do?id=${entity.id}"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<div class="unit"> 
				<label>审核状态: </label>
				<input onclick="setRequired(1)" type="radio" name="auditStatus" value="1" ${(empty entity || entity.auditStatus == 0 || entity.auditStatus == 1) ? 'checked' : '' }>审核通过
				<input onclick="setRequired(0)" type="radio" name="auditStatus" value="-1" ${entity.auditStatus == -1 ? 'checked' : '' }>审核不通过
			</div>
			<div class="divider"></div>
			<div class="unit"> 
				<label>审核不通过原因: </label>
				<textarea class="textInput textArea ${entity.auditStatus == -1 ? 'required' : '' }" id="auditMemo" name="auditMemo" cols="50" rows="7" onkeyup="checkLimit('auditMemo',100)">${geli:display(entity, 'auditMemo')}</textarea>
				<p style="padding-left:130px;">&nbsp;最多输入100个中文字符</p>
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
function setRequired(type){
	if(type == 0){
		$("#auditMemo").addClass("required");
	} else {
		$("#auditMemo").removeClass("required");
		$("#auditMemo").val("");
	}
}
function btn_submit(){
    $("#adForm").submit();
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
    var value = $.trim($("#"+id).val());
    var count = countTxt(value);
    if(count > limit){
        value = value.substring(0, count-(count-limit));
        $("#"+id).val(value);
    }
}

/**
 * 检查输入内容是否超出长度限制
 * @param id
 * @param limit
 * @returns {boolean}
 */
function checkCount(name,id,limit){
    var value = $.trim($("#"+id).val());
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
