<%@page import="com.alipay.api.internal.util.AlipaySignature"%>
<%@page import="cn.com.kuaidian.alipay.PayConfig"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.HashMap"%>
<%@page import="cn.com.kuaidian.alipay.PayType"%>
<%@page import="cn.com.kuaidian.alipay.AlipayUtil"%>
<%@page import="cn.com.kuaidian.alipay.AlipayParams"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	/*
	* 支付完回调逻辑
	*/
	
	//获取支付宝POST过来反馈信息
	Map<String,String> params = new HashMap<String,String>();
	Map requestParams = request.getParameterMap();
	for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
		String name = (String) iter.next();
		String[] values = (String[]) requestParams.get(name);
		String valueStr = "";
		for (int i = 0; i < values.length; i++) {
			valueStr = (i == values.length - 1) ? valueStr + values[i]
					: valueStr + values[i] + ",";
		}
		
		params.put(name, valueStr);
	}
	System.out.println("---kuaidian---"+params);
	
	//TODO 签名验证、业务处理
	boolean signVerified = false;
	signVerified= AlipaySignature.rsaCheckV1(params, PayConfig.ALIPAY_PUBLIC_KEY, "UTF-8", PayConfig.SIGN_TYPE);
	if (signVerified) { //验签通过
		
	}else { //验签失败
		
	}
	
%>
