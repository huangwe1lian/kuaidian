var swfu;
var resizedImgCondition = "512KB";

/**
 * 初始化单个指令上传Flash插件
 * @param command 上传压缩指令
 */
var initSWFObj = function (placeholderId,imageId,targetId,command,imageWidth,imageHeight,btnWidth,btnHeight,uploadCallback) {
    buildSWFObj(placeholderId,imageId,targetId,imageWidth,imageHeight,btnWidth,btnHeight,uploadCallback,false,command)
};

/**
 * 初始化多个指令上传Flash插件
 * @param commandIdStr 如: command=2030&command=2031
 */
var initMultiCommandSWFObj = function (placeholderId,imageId,targetId,commandIdStr,imageWidth,imageHeight,btnWidth,btnHeight,uploadCallback) {
    buildSWFObj(placeholderId,imageId,targetId,imageWidth,imageHeight,btnWidth,btnHeight,uploadCallback,true,commandIdStr)
};

/**
 * 构建flash上传对象
 * @param placeholderId  上传按钮flash放置的div ID
 * @param imageId        页面上传图片外部整块的div ID,主要是在handler.js里控制这块的样式显示
 * @param targetId       上传图片的div位置(通常定义为:div_uploadImage，不强制要求这样的格式)，同时也返回上传路径时，把targetId的前面的div_去掉做为表单参数名返回。
 *                       如target=div_uploadImage,则返回上传路径时的表单<input type = 'hidden' name='uploadImage' id='uploadImage' >
 * @param imageWidth     图片显示宽度
 * @param imageHeight    图片显示高度
 * @param btnWidth       上传按钮flash宽度
 * @param btnHeight      上传按钮flash高度
 * @param uploadCallback 上传成功之后的回调函数的，可选
 * @param isMultiCommand 是否多个指令
 * @param commandIdStr   上传压缩指令ID串
 */
function buildSWFObj(placeholderId,imageId,targetId,imageWidth,imageHeight,btnWidth,btnHeight,uploadCallback,isMultiCommand,commandIdStr) {
    swfu = new SWFUpload({
        upload_url: buildUploadUrl(isMultiCommand, commandIdStr),
        post_params : {
            "application" : "fawtoyota",
            "fileType" : "Picture",
            "referer" : upc_referer,
            "common_session_id" : getUploadCookie("common_session_id")
        },
        // File Upload Settings
        file_size_limit:"2MB",
        file_types:"*.jpg;*.gif;*.jpeg;*.png;",
        file_types_description:"All Files",
        file_upload_limit:"0",
        use_query_string:true,
        // Event Handler Settings - these functions as defined in Handlers.js
        //  The handlers are not part of SWFUpload but are part of my website and control how
        //  my website reacts to the SWFUpload events.
        swfupload_preload_handler:preLoad,
        swfupload_load_failed_handler:loadFailed,

        file_queue_error_handler:fileQueueError,
        file_dialog_complete_handler:fileDialogComplete,

        upload_progress_handler:uploadProgress,
        upload_error_handler:uploadError,
        upload_success_handler:uploadSuccess,
        upload_complete_handler:uploadComplete,

        // Button Settings
        button_placeholder_id:placeholderId,        //"spanButtonPlaceholder"
        button_width:btnWidth,
        button_height:btnHeight,
        button_window_mode:SWFUpload.WINDOW_MODE.TRANSPARENT,
        button_cursor:SWFUpload.CURSOR.HAND,

        // Flash Settings
        flash_url:"/admin/swfupload/flash/swfupload.swf",
        flash9_url:"/admin/swfupload/flash/swfupload_fp9.swf",
        custom_settings:{
            upload_target:targetId,           //"uploadImg"
            showImageBlock:imageId,
            imageWidth:imageWidth,
            imageHeight:imageHeight,
            callback:uploadCallback,           //自定义回调函数
            resizedImgWidth:700,
            resizedImgHeight:0,
            resizedImgQuality:100,
            resizedImgCondition:resizedImgCondition
        },
        // Debug Settings
        debug:false
    });
}

function buildUploadUrl(isMultiCommand, commandIdStr) {
    if(isMultiCommand) {
        return upload_url + '?' + commandIdStr;
    } else {
        return upload_url + '?command=' + commandIdStr;
    }
}

function getUploadCookie(name, nounescape) {
    var cookie_start = document.cookie.indexOf(name);
    var cookie_end = document.cookie.indexOf(";", cookie_start);
    if (cookie_start == -1) {
        return '';
    } else {
        var v = document.cookie.substring(cookie_start + name.length + 1, (cookie_end > cookie_start ? cookie_end : document.cookie.length));
        return !nounescape ? unescape(v) : v;
    }
}
