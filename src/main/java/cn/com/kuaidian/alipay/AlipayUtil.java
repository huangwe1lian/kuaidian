package cn.com.kuaidian.alipay;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;

import javax.servlet.http.HttpServletRequest;

import cn.com.kuaidian.util.IPUtils;

public class AlipayUtil { 
	
	public static String buildRequest(AlipayParams reqParams, PayType type, HttpServletRequest request) throws PayException, UnsupportedEncodingException{
		if (null == reqParams) {
			throw new PayException(1000, "参数错误：传入params为空");
		}
		try {
			String str;
			switch (type.ordinal()){
				case 0:
		    		return buildRequest_pc(reqParams, request);
		    	case 1:
		    		return buildRequest_wap(reqParams, request);
		    	case 2:
		    		return buildRequest_refund(reqParams, request);
			}
		    return "";
			
		}finally {
			//
		}
		   
	}
	
	//pc发起支付
	private static String buildRequest_pc(AlipayParams reqParams, HttpServletRequest request){
		return "";
	}
	
	//wap发起支付
	private static String buildRequest_wap(AlipayParams reqParams, HttpServletRequest request) throws PayException, UnsupportedEncodingException{
		String url = request.getRequestURL().toString();
	    String method = request.getMethod();
	    String ip = IPUtils.getClientRealIp(request);
	    reqParams.setNotify_url("");
	    reqParams.setReturn_url("");
	
	    //TODO 参数过滤
	    
	    Map paramMap = new HashMap();
	    paramMap.put("payment_type", "wap");
	    paramMap.put("out_trade_no", reqParams.getOut_trade_no());
	    paramMap.put("total_amount", reqParams.getTotal_amount());
	    paramMap.put("subject", reqParams.getSubject());
	    paramMap.put("product_code", reqParams.getProduct_code());
	    
	    paramMap.put("notify_url", reqParams.getNotify_url());
	    paramMap.put("return_url", reqParams.getReturn_url());
	    paramMap.put("overTime", reqParams.getOverTime());
	 

	    String returnHtml = AlipaySubmit.buildRequest(paramMap);
	    return returnHtml;
	}
	
	//退款
	private static String buildRequest_refund(AlipayParams reqParams, HttpServletRequest request){
		return "";
	}
		   
	
}
