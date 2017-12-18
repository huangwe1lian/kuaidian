<%@page import="cn.com.kuaidian.entity.Order"%>
<%@page import="cn.com.kuaidian.service.OrderService"%>
<%@page import="cn.com.kuaidian.util.StringUtils"%>
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
<%@include file="/WEB-INF/jspf/import.jspf"%>
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
	System.out.println("---kuaidian---params:"+params);
	
	//TODO 签名验证、业务处理
	try {
		boolean signVerified = true;
		//params值没有验签需要的参数会报异常
		signVerified= AlipaySignature.rsaCheckV1(params, PayConfig.ALIPAY_PUBLIC_KEY, "UTF-8", PayConfig.SIGN_TYPE);
		System.out.println("---kuaidian---signVerified:"+signVerified);
		if (signVerified) { //验签通过
			 //改变订单状态，设置为待取餐
			 String out_trade_no = StringUtils.stringValue(request.getParameter("out_trade_no"), "");
			 Env env = EnvUtils.getEnv();
			 OrderService orderService = env.getApplicationContext().getBean(OrderService.class);
			 GeliDao geliDao = env.getApplicationContext().getBean(GeliDao.class);
			 Order order = orderService.getOrderByoutTradeNo(out_trade_no);
			 if (order != null) {
				 order.setStatus(1);
				 order.setUpdateTime(new Date());
				 geliDao.update(order);
			 }
		
		}else { //验签失败
			
		}
		
	}catch(Exception e) {
		e.printStackTrace();
	}

%>
