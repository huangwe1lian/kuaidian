package cn.com.kuaidian.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gelivable.dao.GeliOrm;
import org.gelivable.web.Env;
import org.gelivable.web.EnvUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.kuaidian.alipay.AlipayParams;
import cn.com.kuaidian.alipay.AlipayUtil;
import cn.com.kuaidian.alipay.PayType;
import cn.com.kuaidian.entity.Order;
import cn.com.kuaidian.service.OrderService;

@Controller
@RequestMapping("/alipay")
public class AlipayController {
	
	@Autowired
	private GeliOrm geliOrm;
	
	@Autowired
	private OrderService orderService;
	
	@RequestMapping(value="/aciton.do")
    public void list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");  //暂时用get请求
		Env env = EnvUtils.getEnv();
		long orderId = env.paramLong("orderId", 0);
		
		Order order = orderService.getOrder(orderId);
		String out_trade_no = order.getOutTradeNo();
		String product_code = order.getNumber();
		String subject = "测试订单名称"; 
		String total_amount = order.getPrice()+"";
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
    }
}
