<%@page contentType="text/html; charset=UTF-8" session="false"%>
<%@include file="/WEB-INF/jspf/import.jspf"%>

<div class="pageContent">
	<div class="shoulib" ${method == "update" ? "style='display:none'":""}>
            <span href="###"><div class="beCenter" style="padding-top: 10px">基本信息</div></span><em class="bg4"></em>
            <span href="###" class="hoverps" ><div class="beCenter" style="padding-top: 10px">活动详细内容</div></span><em class="bg3"></em>
    </div>
	<form method="post" id="addetailForm"
		action="${ctx}/admin/ad/template/${method}detail.do<c:if test="${method=='update'}">?id=${entity.id}</c:if>"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent" layoutH="${method == 'update' ? '56':'130'}">
			<input type="hidden" name="templateAdId" value="${adId }">
			<div class="unit"> 
				<strong>模板广告管理</strong>
			</div>
			<div class="divider"></div>
			<div class="unit">
				<label>右侧栏目名称: </label>
				<input class="textInput required" value="${geli:display(entity, 'rightChannelName')}" name="rightChannelName"  id="rightChannelName"/>
				<p>&nbsp;最多输入10个汉字</p>
			</div>
			<div class="divider"></div>
			<div class="unit">
				<label>左侧栏目一名称: </label>
				<input class="textInput" ${geli:not_oper('leftChannelName1') ? 'readonly="readonly"' : ''} value="${geli:display(entity, 'leftChannelName1')}" name="leftChannelName1" id="leftChannelName1"/>
				<p>&nbsp;最多输入10个汉字</p>
			</div>
			<div class="divider"></div>
			<div class="unit">
				<label>左侧栏目一内容: </label>
				<div style="clear:right;float:left;width:800px;height:470px;">
					<textarea name="leftChannelTxt1" id="leftChannelTxt1" rows="15" style="width:800px;height="400px;" >${geli:display(entity, 'leftChannelTxt1')}</textarea>
				</div>
			</div>
			<div class="divider"></div>
			<div class="unit">
				<label>左侧栏目二名称: </label>
				<input class="textInput" ${geli:not_oper('leftChannelName2') ? 'readonly="readonly"' : ''} value="${geli:display(entity, 'leftChannelName2')}" name="leftChannelName2" id="leftChannelName2"/>
				<p>&nbsp;最多输入10个汉字</p>
			</div>
			<div class="divider"></div>
			<div class="unit">
				<label>左侧栏目二内容: </label>
				<div style="clear:right;float:left;width:800px;height:470px;">
					<textarea name="leftChannelTxt2" id="leftChannelTxt2" rows="15" style="width:800px;height="400px;" >${geli:display(entity, 'leftChannelTxt2')}</textarea>
				</div>
			</div>
			<div class="divider"></div>
			<div class="unit">
				<label>左侧栏目三名称: </label>
				<input class="textInput" ${geli:not_oper('leftChannelName3') ? 'readonly="readonly"' : ''} value="${geli:display(entity, 'leftChannelName3')}" name="leftChannelName3" id="leftChannelName3"/>
				<p>&nbsp;最多输入10个汉字</p>
			</div>
			<div class="divider"></div>
			<div class="unit">
				<label>左侧栏目三内容: </label>
				<div style="clear:right;float:left;width:800px;height:470px;">
					<textarea name="leftChannelTxt3" id="leftChannelTxt3" rows="15" style="width:800px;height="400px;" >${geli:display(entity, 'leftChannelTxt3')}</textarea>
				</div>
			</div>
			<div class="divider"></div>
			<div class="unit">
				<label>左侧栏目四名称: </label>
				<input class="textInput" ${geli:not_oper('leftChannelName4') ? 'readonly="readonly"' : ''} value="${geli:display(entity, 'leftChannelName4')}" name="leftChannelName4"  id="leftChannelName4"/>
				<p>&nbsp;最多输入10个汉字</p>
			</div>
			<div class="divider"></div>
			<div class="unit">
				<label>左侧栏目四内容: </label>
				<div style="clear:right;float:left;width:800px;height:470px;">
					<textarea name="leftChannelTxt4" id="leftChannelTxt4" rows="15" style="width:800px;height="400px;" >${geli:display(entity, 'leftChannelTxt4')}</textarea>
				</div>
			</div>
			<div class="divider"></div>
			<div class="unit">
				<label>左侧栏目五名称: </label>
				<input class="textInput" ${geli:not_oper('leftChannelName5') ? 'readonly="readonly"' : ''} value="${geli:display(entity, 'leftChannelName5')}" name="leftChannelName5"  id="leftChannelName5"/>
				<p>&nbsp;最多输入10个汉字</p>
			</div>
			<div class="divider"></div>
			<div class="unit">
				<label>左侧栏目五内容: </label>
				<div style="clear:right;float:left;width:800px;height:470px;">
					<textarea name="leftChannelTxt5" id="leftChannelTxt5" rows="15" style="width:800px;height="400px;" >${geli:display(entity, 'leftChannelTxt5')}</textarea>
				</div>
			</div>
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
<script>
	$(function(){
		if( CKEDITOR.instances['leftChannelTxt1']){ 
		    CKEDITOR.remove(CKEDITOR.instances['leftChannelTxt1']); 
		}
		if( CKEDITOR.instances['leftChannelTxt2']){ 
		    CKEDITOR.remove(CKEDITOR.instances['leftChannelTxt2']); 
		}
		if( CKEDITOR.instances['leftChannelTxt3']){ 
		    CKEDITOR.remove(CKEDITOR.instances['leftChannelTxt3']); 
		}
		if( CKEDITOR.instances['leftChannelTxt4']){ 
		    CKEDITOR.remove(CKEDITOR.instances['leftChannelTxt4']); 
		}
		if( CKEDITOR.instances['leftChannelTxt5']){ 
		    CKEDITOR.remove(CKEDITOR.instances['leftChannelTxt5']); 
		}
		if( CKEDITOR.instances['leftChannelTxt1']){ 
		    CKEDITOR.remove(CKEDITOR.instances['leftChannelTxt1']); 
		}
	   	var ck_editor1 = CKEDITOR.replace( 'leftChannelTxt1', {
		   	customConfig : CKEDITOR.basePath + 'ckeditor.config.js'
	   	});
	   	var ck_editor2 = CKEDITOR.replace( 'leftChannelTxt2', {
		   	customConfig : CKEDITOR.basePath + 'ckeditor.config.js'
	   	});
	   	var ck_editor3 = CKEDITOR.replace( 'leftChannelTxt3', {
		   	customConfig : CKEDITOR.basePath + 'ckeditor.config.js'
	   	});
	   	var ck_editor4 = CKEDITOR.replace( 'leftChannelTxt4', {
		   	customConfig : CKEDITOR.basePath + 'ckeditor.config.js'
	   	});
	   	var ck_editor5 = CKEDITOR.replace( 'leftChannelTxt5', {
		   	customConfig : CKEDITOR.basePath + 'ckeditor.config.js'
	   	});
	});
	//删除左右两端的空格
	function trim(str){ 
	　　 	return str.replace(/(^\s*)|(\s*$)/g, "");
	}
	
	
	//上传图片弹窗(编辑器js调用)
	function showUploadDialog(editor){
		$.pdialog.open("/uploadImage/ckeditor.do?id="+editor.name,"ckeditor-uploadImg","上传图片",{close:true});
	}
	
	//上传图片弹窗关闭
	function closeUploadDialog(){
		$.pdialog.close("ckeditor-uploadImg");
	}
	
	//编辑器插入内容
	function insertImg(shtml,id){
		CKEDITOR.instances[id].insertHtml(shtml);
	}
	
	function CKupdate() {
        for (instance in CKEDITOR.instances)
            CKEDITOR.instances[instance].updateElement();
    }
</script>
<script type="text/javascript">
function btn_submit(){
	if(!checkCount("右侧栏目名称","rightChannelName",10)){
		return false;
	}
	if(!checkCount("左侧栏目一名称","leftChannelName1",10)){
		return false;
	}
	if(!checkCount("左侧栏目二名称","leftChannelName2",10)){
		return false;
	}
	if(!checkCount("左侧栏目三名称","leftChannelName3",10)){
		return false;
	}
	if(!checkCount("左侧栏目四名称","leftChannelName4",10)){
		return false;
	}
	if(!checkCount("左侧栏目五名称","leftChannelName5",10)){
		return false;
	}
	
	CKupdate(); //在提交表单前需要做以上处理
    $("#addetailForm", navTab.getCurrentPanel()).submit();
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