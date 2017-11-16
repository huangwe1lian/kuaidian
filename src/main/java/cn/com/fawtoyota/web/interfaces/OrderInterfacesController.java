package cn.com.fawtoyota.web.interfaces;

import java.util.Date;

import cn.com.fawtoyota.entity.Order;
import cn.com.fawtoyota.service.order.OrderService;
import cn.com.fawtoyota.util.IPUtils;
import cn.com.fawtoyota.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gelivable.dao.GeliDao;
import org.gelivable.dao.GeliOrm;
import org.gelivable.web.Env;
import org.gelivable.web.EnvUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/interfaces")
public class OrderInterfacesController {

    @Autowired
    GeliOrm geliOrm;

    @Autowired
    GeliDao geliDao;

    @RequestMapping(value="/order/create.do", method = RequestMethod.POST)
    @ResponseBody
    public String create(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        Env env = EnvUtils.getEnv();
        JSONObject result = new JSONObject();
        String orderCode= StringUtils.xssCharFilter(env.param("orderCode", ""));
    	String callback = StringUtils.xssCharFilter(env.param("callback",""));
    	String tempName = new String(env.param("name","").getBytes("ISO-8859-1"), "utf-8");
    	String name = StringUtils.xssCharFilter(tempName);
        String mobile = StringUtils.xssCharFilter(env.param("mobile", ""));
        int gender = env.paramInt("gender",-1);
        long cityId = env.paramLong("cityId",-1);
        long modelId = env.paramLong("modelId",-1);
        int type = env.paramInt("type",-1);
        int origin = env.paramInt("origin",-1);
        int payType = env.paramInt("payType",0);
        String buyTime = StringUtils.xssCharFilter(env.param("buyTime", ""));
        String refer = StringUtils.xssCharFilter(env.param("refer", ""));
        double expectPrice = env.paramDouble("expectPrice", -1.0);
        long dealerId = env.paramLong("dealerId",-1);
        long serialGroupId = env.paramLong("serialGroupId",-1);
        String ip = IPUtils.getClientRealIp(req);
        String dmemo = StringUtils.xssCharFilter(env.param("dmemo","")); 
        
        resp.setCharacterEncoding("UTF-8");
        OrderService orderService = EnvUtils.getEnv().getBean(OrderService.class);
        try{
        	String temp = orderService.orderCodeVerify(orderCode);
        	JSONObject myJsonObject = new JSONObject(temp);
        	Boolean hasOrderCode = myJsonObject.getBoolean("hasOrderCode");
        	if(!hasOrderCode){
        		result.put("result", "fail");
        		result.put("message", "报名授权码orderCode不合法");
        		result.put("orderId", 0);
        		return printResult(callback,result);
        	}
        	
        }catch(Exception e){
        	e.printStackTrace();
        	result.put("result", "fail");
    		result.put("message", "报名授权码orderCode不合法");
    		result.put("orderId", 0);
        	return printResult(callback,result);
        }
        
        //参数验证
        if(StringUtils.isBlank(name)){
        	result.put("result", "fail");
    		result.put("message", "姓名参数不合法");
    		result.put("orderId", 0);
    		return printResult(callback,result);
        }
        if(!StringUtils.isMobilePhone(mobile)){
        	result.put("result", "fail");
    		result.put("message", "手机参数不合法");
    		result.put("orderId", 0);
    		return printResult(callback,result);
    		
        }
        if( (gender!=0) && (gender != 1)){
        	result.put("result", "fail");
    		result.put("message", "性别参数不合法");
    		result.put("orderId", 0);
    		return printResult(callback,result);
    		
        }
        if(cityId <= 0){
        	result.put("result", "fail");
    		result.put("message", "城市参数不合法");
    		result.put("orderId", 0);
    		return printResult(callback,result);
        }
        if(modelId <= 0){
        	result.put("result", "fail");
    		result.put("message", "车型参数不合法");
    		result.put("orderId", 0);
    		return printResult(callback,result);
        }
        if((type<0)||(type>2)){
        	result.put("result", "fail");
    		result.put("message", "名单类型参数不合法");
    		result.put("orderId", 0);
    		return printResult(callback,result);
        }
        if((origin<0)||(origin>2)){
        	result.put("result", "fail");
    		result.put("message", "平台来源参数不合法");
    		result.put("orderId", 0);
    		return printResult(callback,result);
        }
        if((payType<0)||(payType>3)){
        	result.put("result", "fail");
    		result.put("message", "购车方式参数不合法");
    		result.put("orderId", 0);
    		return printResult(callback,result);
        }
        if(StringUtils.isBlank(buyTime)){
        	result.put("result", "fail");
    		result.put("message", "购车时间参数不合法");
    		result.put("orderId", 0);
    		return printResult(callback,result);
        }
        if(StringUtils.isBlank(refer)){
        	result.put("result", "fail");
    		result.put("message", "页面来源参数不合法");
    		result.put("orderId", 0);
    		return printResult(callback,result);
        }
        if(expectPrice < 0){
        	result.put("result", "fail");
    		result.put("message", "期望价格参数不合法");
    		result.put("orderId", 0);
    		return printResult(callback,result);
        }
        if(dealerId < 0){
        	result.put("result", "fail");
    		result.put("message", "经销商参数不合法");
    		result.put("orderId", 0);
    		return printResult(callback,result);
        }
        if(serialGroupId < 0){
        	result.put("result", "fail");
    		result.put("message", "车系参数不合法");
    		result.put("orderId", 0);
    		return printResult(callback,result);
        }
        long repeat = 1;
        try{
	        String repeat_temp = orderService.repeatVerify(orderService.getTodayStartTime(), serialGroupId, mobile, dealerId);
	    	JSONObject repeatJsonObject = new JSONObject(repeat_temp);
	    	repeat = repeatJsonObject.getLong("repeat");
        }catch(Exception e){
        	e.printStackTrace();
        }
    	
        Order order = new Order();
        try {
            //GeliLogFacade.log();
        	order.setName(name);
        	order.setMobile(mobile);
        	order.setGender(gender);
        	order.setCityId(cityId);
        	order.setModelId(modelId);
        	order.setCreateTime(new Date());
        	order.setUpdateTime(new Date());
        	order.setOrderCode(orderCode);
        	order.setType(payType);
        	order.setOrigin(origin);
        	order.setPayType(payType);
        	order.setBuyTime(buyTime);
        	order.setRefer(refer);
        	order.setExpectPrice(expectPrice);
        	order.setDealerId(dealerId);
        	order.setSerialGroupId(serialGroupId);
        	order.setDmemo(dmemo);
        	order.setIp(ip);
        	order.setRepeat(repeat);
            geliDao.create(order);
        } catch (DuplicateKeyException ex) {
            result.put("result", "fail");
    		result.put("message", "记录重复!");
    		result.put("orderId", 0);
    		return printResult(callback,result);
        }
        
        
    	result.put("status", "success");
		result.put("message", "报名成功");
		result.put("orderId", order.getId());
		return printResult(callback,result);
        
    }

    /**
     * 结果返回处理
     * @param callback
     * @param result
     * @return
     */
    private String printResult(String callback,JSONObject result){
    	if(!"".equals(callback)){
    		return callback + "(" + result.toString() + ")";
    	} else {
    		return result.toString();
    	}
    }

}

