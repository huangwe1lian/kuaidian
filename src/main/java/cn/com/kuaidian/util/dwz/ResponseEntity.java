package cn.com.kuaidian.util.dwz;

import java.util.Map;

import com.alibaba.fastjson.JSON;

/**
 * 系统响应实体
 * 兼容前后台
 * @author Fenhy
 * 2016-3-11下午2:50:51
 */
public class ResponseEntity {
	
	public static final int STATUS_CODE_SUCCESS = Constants.StatusCode.SUCCESS;
	public static final int STATUS_CODE_ERROR = Constants.StatusCode.ERROR;
	public static final int STATUS_CODE_TIMEOUT = Constants.StatusCode.TIMEOUT;
	
	public static final String MESSAGE_SUCCESS = "操作成功.";
	public static final String MESSAGE_TIMEOUT = "登录超时,请重新登录.";
	
	public static final String TYPE_CLOSE_CURRENT = "closeCurrent";
	public static final String TYPE_FORWARD = "forward";
	public static final String TYPE_FORWARD_CONFIRM = "forwardConfirm";
	
	
	private int statusCode ;
	private String message ;
	private String navTabId ;
	private String callbackType ;
	private String forwardUrl ;
	private String confirmMsg ;
	private Map<String,Object> attr;//自定义返回内容
	
	public ResponseEntity(){}

	/**
	 * 系统响应实体
	 * @param statusCode 响应状态
	 * @param message 响应消息内容
	 * @param navTabId 对应面板id
	 * @param callbackType 回调类型
	 * @param forwardUrl 跳转路径
	 * @param confirmMsg 跳转确定提示语
	 */
	public ResponseEntity(int statusCode, String message, String navTabId,
			String callbackType, String forwardUrl, String confirmMsg) {
		this.statusCode = statusCode;
		this.message = message;
		this.navTabId = navTabId;
		this.callbackType = callbackType;
		this.forwardUrl = forwardUrl;
		this.confirmMsg = confirmMsg;
	}
	
	/**
	 * 系统响应实体
	 * @param statusCode 响应状态
	 * @param message 响应消息内容
	 * @param navTabId 对应面板id
	 * @param callbackType 回调类型
	 * @param forwardUrl 跳转路径
	 * @param confirmMsg 跳转确定提示语
	 */
	public ResponseEntity(int statusCode, String message, String navTabId,
			String callbackType, String forwardUrl, String confirmMsg, Map<String,Object> attr) {
		this.statusCode = statusCode;
		this.message = message;
		this.navTabId = navTabId;
		this.callbackType = callbackType;
		this.forwardUrl = forwardUrl;
		this.confirmMsg = confirmMsg;
		this.attr = attr;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getNavTabId() {
		return navTabId;
	}

	public void setNavTabId(String navTabId) {
		this.navTabId = navTabId;
	}

	public String getCallbackType() {
		return callbackType;
	}

	public void setCallbackType(String callbackType) {
		this.callbackType = callbackType;
	}

	public String getForwardUrl() {
		return forwardUrl;
	}

	public void setForwardUrl(String forwardUrl) {
		this.forwardUrl = forwardUrl;
	}

	public String getConfirmMsg() {
		return confirmMsg;
	}

	public void setConfirmMsg(String confirmMsg) {
		this.confirmMsg = confirmMsg;
	}

	public Map<String,Object> getAttr() {
		return attr;
	}

	public void setAttr(Map<String,Object> attr) {
		this.attr = attr;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}
