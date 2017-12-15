package cn.com.kuaidian.alipay;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;

import javax.servlet.http.HttpServletRequest;

import cn.com.kuaidian.util.IPUtils;

public class AlipayUtil { 
	
	public static String buildRequest(AlipayParams reqParams, PayType payType, HttpServletRequest request) throws PayException, UnsupportedEncodingException{
		if (null == reqParams) {
			throw new PayException(1000, "参数错误：传入params为空");
		}
		
		String url = request.getRequestURL().toString();
	    String method = request.getMethod();
	    String ip = IPUtils.getClientRealIp(request);
	    reqParams.setNotify_url("http://67cge8.natappfree.cc/notify.jsp");
	    reqParams.setReturn_url("http://localhost:8090/user/qr.do");
	
	    //TODO 参数过滤、日志记录
	    
	    Map paramMap = new HashMap();
	    paramMap.put("out_trade_no", reqParams.getOut_trade_no());
	    paramMap.put("total_amount", reqParams.getTotal_amount());
	    paramMap.put("subject", reqParams.getSubject());
	    paramMap.put("product_code", reqParams.getProduct_code());
	    
	    paramMap.put("notify_url", reqParams.getNotify_url());
	    paramMap.put("return_url", reqParams.getReturn_url());
	    paramMap.put("overTime", reqParams.getOverTime());
	 

	    String returnHtml = AlipaySubmit.buildRequest(paramMap, payType);
	    return returnHtml;
		   
	}
		   
	
}
