package cn.com.kuaidian.alipay;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayRequest;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePayRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;

public class AlipaySubmit { 
	public static String buildRequest(Map<String, String> paramMap, PayType payType) throws PayException, UnsupportedEncodingException {
		String str;
		switch (payType.ordinal()){
			case 0:
	    		return buildRequest_pc(paramMap);  //pc
	    	case 1: 
	    		return buildRequest_wap(paramMap); //wap
	    	case 2:
	    		return buildRequest_refund(paramMap); //退款
		}
		return "";
	}
	
	
	public static String buildRequest_wap(Map<String, String> paramMap) throws UnsupportedEncodingException {

		//SDK 公共请求类，包含公共请求参数，以及封装了签名与验签
		AlipayClient client = new DefaultAlipayClient(PayConfig.URL, PayConfig.APPID, PayConfig.RSA_PRIVATE_KEY, PayConfig.FORMAT, PayConfig.CHARSET, PayConfig.ALIPAY_PUBLIC_KEY, PayConfig.SIGN_TYPE); 
		//创建API对应的request  
		AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();
		//在公共参数中设置异步通地址方法
	    alipayRequest.setNotifyUrl("http://67cge8.natappfree.cc/notify.jsp");  //外网链接,先写死
	    //在公共参数中设置同步通地址方法
	    alipayRequest.setReturnUrl("https://www.alipay.com/");  //外网链接,先写死
	    
	    //设置请求参数，建议测试时只是用必传参数
	    String bz="{" + 
		        "    \"out_trade_no\":\""+paramMap.get("out_trade_no")+"\"," + 
		        "    \"total_amount\":\""+paramMap.get("total_amount")+"\"," + 
		        "    \"subject\":\""+paramMap.get("subject")+"\"," + 
		        "    \"product_code\":\"QUICK_WAP_PAY\"" + 
		        "  }";
	    
	    bz = new String(bz.getBytes("ISO-8859-1"), "UTF-8");
	    alipayRequest.setBizContent(bz);//填充业务参数 
	    String form = "";
	  		try {
	  			form = client.pageExecute(alipayRequest).getBody();
	  		} catch (AlipayApiException e) {
	  			// TODO Auto-generated catch block
	  			e.printStackTrace();
	  		}
		return form;
	  }
	
	public static String buildRequest_pc(Map<String, String> paramMap) throws UnsupportedEncodingException {
		return null;
		
	}
	
	public static String buildRequest_refund(Map<String, String> paramMap) throws UnsupportedEncodingException {
		return null;
		
	}
}
		
