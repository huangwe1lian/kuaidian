package cn.com.kuaidian.util;

import java.io.IOException;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * HTTP请求工具类
 */
public class OutInterface {
	private static Logger logger =  LoggerFactory.getLogger(OutInterface.class);

	/**
	 * 字符集
	 */
	public static final String UTF_8 = "UTF-8";
	public static final String GBK = "GBK";

	public static String get(String url){
		return get(url, null, UTF_8);
	}

	public static String get(String url, String referer){
		return get(url, referer , UTF_8);
	}

	public static JSONObject getJSON(String url){
		return getJSON(url, null, UTF_8);
	}

	public static JSONObject getJSON(String url, String referer){
		return getJSON(url, referer, UTF_8);
	}

	public static JSONObject getJSON(String url, String referer, final String charset){
		String result = get(url, referer, charset);
		if(result != null){
			return JSON.parseObject(result);
		}

		return null;
	}

	public static JSONArray getJSONArray(String url){
		return getJSONArray(url, null, UTF_8);
	}

	public static JSONArray getJSONArray(String url, String referer){
		return getJSONArray(url, referer, UTF_8);
	}

	public static JSONArray getJSONArray(String url, String referer, final String charset){
		String result = get(url, referer, charset);
		if(result != null){
			return JSON.parseArray(result);
		}

		return null;
	}

	public static String get(String url, String referer, final String charset) {
		try{
			HttpClient client = HTTPUtils.instance();
			GetMethod method = new GetMethod(url);
			method.addRequestHeader("referer", referer);
			client.executeMethod(method);

			return HTTPUtils.getResponseBody(method, charset);
		} catch (HttpException e) {
			e.printStackTrace();
			logger.error("执行HTTP get {} 时，发生HttpException异常！msg:{}", url , e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("执行HTTP get {} 时，发生IOException异常！msg:{}", url , e.getMessage());
		} catch (Exception e){
			e.printStackTrace();
		}

		return null;
	}
	

	public static String post(String url, Map<String, Object> params) {
		return post(url, params, null, UTF_8);
	}

	public static String post(String url, Map<String, Object> params, String referer) {
		return post(url, params, referer, UTF_8);
	}

	public static JSONObject postJSON(String url, Map<String, Object> params) {
		return postJSON(url, params, null, UTF_8);
	}

	public static JSONObject postJSON(String url, Map<String, Object> params, String referer) {
		return postJSON(url, params, referer, UTF_8);
	}

	public static JSONObject postJSON(String url, Map<String, Object> params, String referer, final String charset){
		String result = post(url, params, referer, charset);
		if(result != null){
			return JSON.parseObject(result);
		}

		return null;
	}

	public static JSONArray postJSONArray(String url, Map<String, Object> params) {
		return postJSONArray(url, params, null, UTF_8);
	}

	public static JSONArray postJSONArray(String url, Map<String, Object> params, String referer) {
		return postJSONArray(url, params, referer, UTF_8);
	}

	public static JSONArray postJSONArray(String url, Map<String, Object> params, String referer, final String charset){
		String result = post(url, params, referer, charset);
		if(result != null){
			return JSON.parseArray(result);
		}

		return null;
	}

	public static String post(String url, Map<String, Object> params, String referer, final String charset) {
		try{
			HttpClient client = HTTPUtils.instance();
			PostMethod method = new PostMethod(url);
			for(String key:params.keySet()){
				method.setParameter(key, params.get(key).toString());
			}
			method.addRequestHeader("referer", referer);
			client.executeMethod(method);

			return HTTPUtils.getResponseBody(method, charset);
		} catch (HttpException e) {
			e.printStackTrace();
			logger.error("执行HTTP post {} 时，发生HttpException异常！msg:{}", url , e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("执行HTTP post {} 时，发生IOException异常！msg:{}", url , e.getMessage());
		} catch (Exception e){
			e.printStackTrace();
		}

		return null;
	}
}
