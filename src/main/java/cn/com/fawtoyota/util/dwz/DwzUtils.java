package cn.com.fawtoyota.util.dwz;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

/**
 * 后台DWZ框架返回工具类
 * 
 * @author zhoutianhuai
 *
 */
public class DwzUtils {
	private final static Logger LOG = LoggerFactory.getLogger(DwzUtils.class);
	
	/**
	 * 返回信息
	 * 参数注释见ResponseEntity
	 * @return
	 */
	public static String resEntity(int statusCode, String message, String navTabId,
			String callbackType, String forwardUrl, String confirmMsg){
		return JSON.toJSONString(new ResponseEntity(statusCode, message, navTabId, callbackType, forwardUrl, confirmMsg));
	}
	/**
	 * 返回信息
	 * 参数注释见ResponseEntity
	 * @return
	 */
	public static String resEntity(int statusCode, String message, String navTabId,
			String callbackType, String forwardUrl, String confirmMsg,Map<String,Object> attr){
		return JSON.toJSONString(new ResponseEntity(statusCode, message, navTabId, callbackType, forwardUrl, confirmMsg, attr));
	}
	
	
	/**
	 * 返回 成功信息 - 无后续操作
	 * 参数注释见ResponseEntity
	 * @return
	 */
	public static String success(String message){
		return resEntity(ResponseEntity.STATUS_CODE_SUCCESS, message, null,null, null, null);
	}
	/**
	 * 返回 成功信息 - 无后续操作
	 * 参数注释见ResponseEntity
	 * @return
	 */
	public static String success(String message, Map<String,Object> attr){
		return resEntity(ResponseEntity.STATUS_CODE_SUCCESS, message, null,null, null, null,attr);
	}
	/**
	 * 返回 成功信息 - 关闭当前tab,切换到navTabId
	 * 参数注释见ResponseEntity
	 * @return
	 */
	public static String successAndClose(String navTabId, String message){
		return resEntity(ResponseEntity.STATUS_CODE_SUCCESS, message, navTabId,ResponseEntity.TYPE_CLOSE_CURRENT, null, null);
	}
	/**
	 * 返回 成功信息 - 当前tab跳转至forwardUrl
	 * 参数注释见ResponseEntity
	 * @return
	 */
	public static String successAndForward(String message, String forwardUrl){
		return resEntity(ResponseEntity.STATUS_CODE_SUCCESS, message, null,ResponseEntity.TYPE_FORWARD, forwardUrl, null);
	}
	/**
	 * 返回 成功信息 - 确认框,当前tab跳转至forwardUrl
	 * 参数注释见ResponseEntity
	 * @return
	 */
	public static String successAndForwardConfirm(String navTabId, String message, String forwardUrl, String confirmMsg){
		return resEntity(ResponseEntity.STATUS_CODE_SUCCESS, message, navTabId,ResponseEntity.TYPE_FORWARD_CONFIRM, forwardUrl, confirmMsg);
	}
	
	
	
	/**
	 * 返回 错误信息
	 * 参数注释见ResponseEntity
	 * @return
	 */
	public static String error(String message){
		return resEntity(ResponseEntity.STATUS_CODE_ERROR , message, null,null, null, null);
	}
	public static String error(int statusCode , String message){
		return resEntity(statusCode , message, null,null, null, null);
	}
	
}
