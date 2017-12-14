<%@page import="cn.com.kuaidian.util.DateUtils"%>
<%@page import="cn.com.kuaidian.alipay.PayType"%>
<%@page import="cn.com.kuaidian.alipay.AlipayUtil"%>
<%@page import="cn.com.kuaidian.alipay.AlipayParams"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jspf/import.jspf"%>
<%

	/*
	*发起支付宝支付
	*/
	

	/*
	*发起支付宝支付
	*/
	
	//TODO 参数验证、乱码解决
	request.setCharacterEncoding("UTF-8");  //暂时用get请求
	/* String out_trade_no = request.getParameter("out_trade_no");
	String total_amount = request.getParameter("total_amount");
	String subject = request.getParameter("subject");
	String product_code = request.getParameter("product_code"); */
	String out_trade_no = System.currentTimeMillis()+"";
	String total_amount = "0.01";
	String subject = "测试订单名称";
	String product_code = "110";
	String over_time = "60"; 
	
	AlipayParams params = new AlipayParams(out_trade_no, total_amount, 
			 subject, product_code, over_time);
	
	
	/****** 业务处理开始   *****/
	
	
	
	/****** 业务处理结束   *****/
	
	//发起wap支付
	String form = AlipayUtil.buildRequest(params, PayType.wap, request);
	
	
	response.setContentType("text/html;charset=UTF-8"); 
    response.getWriter().write(form);//直接将完整的表单html输出到页面 
    response.getWriter().flush(); 
	
	
%>
