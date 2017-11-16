package cn.com.fawtoyota.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpHeaders;
import org.apache.http.entity.ContentType;
import org.apache.http.params.CoreConnectionPNames;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * demo:<pre>
 *  HttpClient client = HTTPUtils.instance();
 *  GetMethod method = new GetMethod(url);
 *  client.executeMethod(method);
 *  return HTTPUtils.getResponseBody(method, charset)
 *  </pre>
 */
public class HTTPUtils {
	private static Log log = LogFactory.getLog(HTTPUtils.class);

	private static final String PROXY_URL = "192.168.11.254";

	private static final int PROXY_PORT = 8080;

	private static final int REQ_TIME_OUT = 20000;

	/**
	 * 获取HttpClient实例
	 * @return HttpClient
	 */
	public static HttpClient instance(){
		HttpClient client = new HttpClient();
		PropertiesUtils prop = SpringUtils.getBean(PropertiesUtils.class);
		int timeout = StringUtils.intValue(prop.getHttpPropertiesVal("http.timeout"), REQ_TIME_OUT);

		setHttpClientProxy(client);
		client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, timeout);
		client.getParams().setContentCharset("UTF-8");
		return client;
	}

	/**
	 *  设置代理服务器地址和端口
	 */
	public static void setHttpClientProxy(HttpClient client){
		PropertiesUtils prop = SpringUtils.getBean(PropertiesUtils.class);
		try { 
			boolean isUseProxy = StringUtils.booleanValue(prop.getHttpPropertiesVal("isUseProxy"), false);
			if(isUseProxy){
				String proxyHost = StringUtils.stringValue(prop.getHttpPropertiesVal("http.proxyHost"),PROXY_URL);
				int proxyPort = StringUtils.intValue(prop.getHttpPropertiesVal("http.proxyPort"), PROXY_PORT);
				client.getHostConfiguration().setProxy(proxyHost, proxyPort);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("请求读取env.properties时错误，请查看是否有出现错误！");
		}
	}

	/**
	 * 返回请求数据
	 * @param method
	 * @return
	 * @throws URIException 
	 */
	public static String getResponseBody(HttpMethod method,String charset) throws URIException{
		String result = null;
		StringBuilder sb = new StringBuilder();
		if (method.getStatusCode() == HttpStatus.SC_OK) {
			BufferedReader br;
			try {
				br = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(),charset));
				String str;
				while((str = br.readLine()) != null){  
					sb .append(str );  
				}
			} catch (IOException e) {
				e.printStackTrace();
				log.error("请求返回错误.");
			} 
			result = sb.toString();
		}else{
			log.error("请求失败."+method.getURI()+" status:"+method.getStatusCode());
		}
		return result;
	}

	/**
	 * POST发送JSON
	 * @param method
	 * @param jsonStr
	 * @return
	 */
	public static JSONObject doPostJSON(PostMethod method , String jsonStr){
		JSONObject result = new JSONObject();
		try {
			RequestEntity re = new StringRequestEntity(jsonStr,"application/x-www-form-urlencoded","UTF-8");
			method.setRequestEntity(re);
			HTTPUtils.instance().executeMethod(method);
			result = JSON.parseObject(getResponseBody(method,"UTF-8"));
		} catch (Exception e) {
			log.error("post json数据出错.", e);
		}
		return result;
	}

	/**
	 * POST发送JSON
	 * @param url
	 * @param jsonStr
	 * @return
	 */
	public static JSONObject doPostJSON(String url,String jsonStr){
		JSONObject result = new JSONObject();
		try {
			PostMethod method = new PostMethod(url); 
			method.addRequestHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString());
			RequestEntity re = new StringRequestEntity(jsonStr,"text/json","UTF-8");
			method.setRequestEntity(re);
			HTTPUtils.instance().executeMethod(method);
			result = JSON.parseObject(getResponseBody(method,"UTF-8"));
		} catch (Exception e) {
			log.error("post json数据出错.", e);
		}
		return result;
	}

	public static String readPost(HttpServletRequest request,String charset) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String line;
		StringBuilder sb = new StringBuilder();
		while((line = br.readLine())!=null){
			sb.append(line);
		}
		// 将资料解码
		String reqBody = sb.toString();
		return  URLDecoder.decode(reqBody, charset);
	}
}
