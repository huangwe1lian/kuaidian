picnumindex = 1;

var SWFUploadUtils = {
		_$ : function(id){
			return document.getElementById(id);
		},
		insertPlaceholder : function(container,times){
			var that = this;
			if(typeof times === 'number'){
				 for(var i = times; i > 0; i--){
					var placeholder = document.createElement("li");
					placeholder.className = "tobeupload";
					that._$(container).appendChild(placeholder);
				 }

			}
		},
		replacePlaceholder : function(container,index,elem){
			if(!isNaN(index)){
				var childs = this._$(container).getElementsByTagName("li");
				for(var i=0; i<childs.length; i++){
					if(childs[i].className === 'tobeupload'){
						var placeholder = childs[i];
						this._$(container).replaceChild(elem,placeholder);
						break;
					}
				}
			}
		},
		cancelPlaceholder : function(container){
			var childs = this._$(container).getElementsByTagName("li");
			for(var i = childs.length-1; i>=0; i--){
				if(childs[i].className === 'tobeupload'){
					childs[i].parentNode.removeChild(childs[i]);
				}
			}
		}
	};

function preLoad() {
    if (!this.support.loading) {
        alert("You need the Flash Player 9.028 or above to use SWFUpload.");
        return false;
    }
	return true;
}
function loadFailed() {
    alert("Something went wrong while loading SWFUpload. If this were a real application we'd clean up and then give you an alternative");
}

function fileQueueError(file, errorCode, message) {
	try {
		var imageName = "error.gif";
		var errorName = "";
		if (errorCode === SWFUpload.errorCode_QUEUE_LIMIT_EXCEEDED) {
			errorName = "You have attempted to queue too many files.";
		}
		if (errorName !== "") {
			alert(errorName);
			return;
		}
		switch (errorCode) {
		case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:
			break;
		case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT:
			alert("部分图片超过10M，请调整大小后再上传");
			break;
		case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:
		case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE:
			alert("不支持此格式文件");
			break;
		default:
			alert("系统错误，请联系管理员");
			break;
		}
	} catch (ex) {
		this.debug(ex);
	}

}

/**
 *	选择图片的对话框消失以后操作
 **/
function fileDialogComplete(numFilesSelected, numFilesQueued, totalFilesQueued) {
    var maxNum = 1; //最多支持1张
	if(numFilesQueued>maxNum){
        alert("每次上传图片数量不能超过"+maxNum+"张");
        swfu.cancelQueue();
        return;
    }

	if(numFilesQueued > 0){
		//删除第一张图片.当第二张上传时在第一张的位置上更新第二张
		$("#" + this.settings.custom_settings.upload_target + " li::first-child").remove();
	}

	/** 插入占位符 */
	SWFUploadUtils.insertPlaceholder(this.settings.custom_settings.upload_target,numFilesQueued);
	try {
		if (numFilesQueued > 0) {
			//document.getElementById("step_2").className = "";
			//document.getElementById("uploadImg").className = "";
			document.getElementById(this.settings.custom_settings.showImageBlock).className = "";
            document.getElementById(this.settings.custom_settings.showImageBlock).style.display = "";
			//document.getElementById("uploadBtn").className += " againUpload";
			//document.getElementById("uploadArea").className = "uploadArea";
			//document.getElementById("insertAllBtn").style.display = "none";
			//document.getElementById("cancelUploadBtn").style.display = "inline-block";
		   this.startResizedUpload(this.getFile(0).ID, this.customSettings.resizedImgWidth,
				this.customSettings.resizedImgHeight, SWFUpload.RESIZE_ENCODING.JPEG, this.customSettings.resizedImgQuality,
				false,this.customSettings.resizedImgCondition);
		}
	} catch (ex) {
		this.debug(ex);
	}
}

/**
 *	更新进度条
 */
function uploadProgress(file, bytesLoaded) {
	try {
		var percent = Math.ceil((bytesLoaded / file.size) * 100)<=100? Math.ceil((bytesLoaded / file.size) * 100):100;
		if (file.uploadtype === SWFUpload.UPLOAD_TYPE.RESIZED) {
			percent = 30;
		}
		var progress = new FileProgress(file,  this.customSettings.upload_target);
		progress.setProgress(percent);
	} catch (ex) {
		this.debug(ex);
	}
}

/**
 * 更新一张图片成功
 */
function uploadSuccess(file, serverData) {
	try {
		var progress = new FileProgress(file,  this.customSettings.upload_target);
		var uploadTarget = this.customSettings.upload_target;
		var data = eval('(' + serverData + ')');
		switch (data.retCode) {
			case 0:
                if($("#uploadKeepSource").val() =='true'){
                    data.files.pop();
                }
				progress.setComplete(file,data,this.settings.custom_settings.upload_target,this.settings.custom_settings.imageWidth,this.settings.custom_settings.imageHeight);
				//imgList.put(file.id, data.files);
				picnumindex++;
				break;
			case 5:
			case 14:
				$("#" + uploadTarget).html("");
				alert('部分图片超过10M，请调整大小后再上传');
				progress.setCancelled();
				break;
			case 6:
				$("#" + uploadTarget).html("");
				alert('上传失败，请检查图片是否正常');
				progress.setCancelled();
				break;
			case 12:
				$("#" + uploadTarget).html("");
				alert('未登录或超时,请先登录');
				progress.setCancelled();
				swfu.cancelQueue();
				break;
			case 21:
				$("#" + uploadTarget).html("");
				alert('不支持此格式文件');
				progress.setCancelled();
				break;
			default :
				$("#" + uploadTarget).html("");
				alert('系统出错<br />错误码：' + data.retCode);
				progress.setCancelled();
				break;
		}

	} catch (ex) {
		this.debug(ex);
	}
}

/**
 * 整个上传成功
 */
function uploadComplete(file) {
	try {
		/*  I want the next upload to continue automatically so I'll call startUpload here */
		if (this.getStats().files_queued > 0) {
		   var progress = new FileProgress({id:"tmp_fpID"},  this.customSettings.upload_target);
		   progress.setProgress(0);
		   this.startResizedUpload(this.getFile(0).ID, this.customSettings.resizedImgWidth,
				this.customSettings.resizedImgHeight, SWFUpload.RESIZE_ENCODING.JPEG, this.customSettings.resizedImgQuality,
				false,this.customSettings.resizedImgCondition);
		} else {
			//document.getElementById("cancelUploadBtn").style.display = "none";
			//document.getElementById("insertAllBtn").style.display = "inline-block";
			picnumindex = 1;
		}
        if (this.settings.custom_settings.callback != undefined && this.settings.custom_settings.callback.constructor == Function) {
            this.settings.custom_settings.callback(this.settings.custom_settings.upload_target);
        }
	} catch (ex) {
		this.debug(ex);
	}
}

function uploadError(file, errorCode, message) {
	var imageName =  "error.gif";
	var progress;
	try {
		switch (errorCode) {
		case SWFUpload.UPLOAD_ERROR.FILE_CANCELLED:
			try {
				progress = new FileProgress(file,  this.customSettings.upload_target);
				progress.setCancelled();

			}
			catch (ex1) {
				this.debug(ex1);
			}
			break;
		case SWFUpload.UPLOAD_ERROR.UPLOAD_STOPPED:
			try {
				progress = new FileProgress(file,  this.customSettings.upload_target);
				progress.setCancelled();
			}
			catch (ex2) {
				this.debug(ex2);
			}
			break;
		case SWFUpload.UPLOAD_ERROR.UPLOAD_LIMIT_EXCEEDED:
			alert("部分图片超过10M，请调整大小后再上传");
			break;
		case SWFUpload.UPLOAD_ERROR.RESIZE:
			alert("可能是你的图片尺寸过大，请调整后再上传");
			break;
		default:
			alert("系统错误，请联系管理员");
			break;
		}
	} catch (ex3) {
		this.debug(ex3);
	}
}


FileProgress.prototype.setStatus = function (status) {
   // this.fileProgressElement.childNodes[2].innerHTML = status;
};

/* ******************************************
 *	FileProgress Object
 *	Control object for displaying file info
 * ****************************************** */
/**
 * 进度条初始化和显示图片效果
 *
 */
function FileProgress(file, targetID) {
	this.fileProgressID = file.id;
	if (document.getElementById("tmp_fpID")) {
		document.getElementById("tmp_fpID").id = this.fileProgressID;
	}
	this.fileProgressWrapper = document.getElementById(this.fileProgressID);
	if (!this.fileProgressWrapper) {

		this.fileProgressWrapper = document.createElement("li");
		this.fileProgressWrapper.className = "loading";
		this.fileProgressWrapper.id = this.fileProgressID;

		this.fileProgressElement = document.createElement("i");
		this.fileProgressElement.className = "load";

		var progressPercentText = document.createElement("i");
		progressPercentText.className = "progress";
		progressPercentText.appendChild(document.createTextNode("已上传0%"));

		var progressBar = document.createElement("i");
		progressBar.className = "bar";
		progressBar.appendChild(document.createElement("ins"));

		this.fileProgressElement.appendChild(progressPercentText);
		this.fileProgressElement.appendChild(progressBar);
		this.fileProgressWrapper.appendChild(this.fileProgressElement);

		/** 替换占位符 */
		SWFUploadUtils.replacePlaceholder(targetID, file.index, this.fileProgressWrapper);

	} else {
		this.fileProgressElement = this.fileProgressWrapper.firstChild;
		this.fileProgressElement.childNodes[1].firstChild.nodeValue = "";
	}
	this.height = this.fileProgressWrapper.offsetHeight;
}

FileProgress.prototype.setProgress = function (percentage) {
	this.fileProgressElement.childNodes[0].innerHTML = "已上传" + percentage + "%";
	this.fileProgressElement.childNodes[1].childNodes[0].style.width = percentage + "%";
};

FileProgress.prototype.setComplete = function (file,imgobjs,targetId,wSize,hSize) {
	var nodeParent = this.fileProgressWrapper;
	nodeParent.className = "";
    //onload=\"DrawImage(this,"+wSize+","+hSize+")\"  <img>里onload 自动按比例缩小 暂时不用--huangqinghe
    var imgText = "<img height=\'"+hSize+"\' width=\'"+wSize+"\' src='"+ imgobjs.files[0].url + "'>"
    			+"<input type='hidden' name=\'"+targetId.substring(4)+"\' id=\'"+targetId.substring(4)+"\' value='"+imgobjs.files[0].url+"'/>";
	nodeParent.innerHTML = imgText;
    /*nodeParent.onmouseover = function() {
        this.className += " mHover";
    }
    nodeParent.onmouseout = function() {
        var oClass = "";
        if (this.className.indexOf("insed") > -1) {
            oClass = "insed";
        }
        this.className = oClass;
    }*/
	//在图片上点击插入
	nodeParent.onclick = function(e){
		//alert("在图片上点击插入事件");
	}
};

FileProgress.prototype.setError = function () {

};

FileProgress.prototype.setCancelled = function () {
	var nodeParent = this.fileProgressWrapper;
	nodeParent.parentNode.removeChild(nodeParent);
};

//按比例缩小并居中显示
//upImg：图像对象
//width：最大宽度
//height：最大高度
function DrawImage(upImg, width, height) {
	var flag = false, width, height;
	var image = new Image();
	image.src = upImg.src;
	if (image.width > 0 && image.height > 0) {
		flag = true;
		if (image.width / image.height >= width / height) {
			if (image.width > width) {
				upImg.width = width;
				upImg.height = (image.height * width) / image.width;
				upImg.style.marginTop = (Math.max(0,height - upImg.height)) / 2 + "px";
			} else {
				upImg.width = image.width;
				upImg.height = image.height;
			}
		} else {
			if (image.height > height) {
				upImg.height = height;
				upImg.width = (image.width * height) / image.height;
			} else {
				upImg.width = image.width;
				upImg.height = image.height;
			}
		}
	}
}
