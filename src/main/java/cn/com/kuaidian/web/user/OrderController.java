package cn.com.kuaidian.web.user;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gelivable.dao.GeliDao;
import org.gelivable.web.Env;
import org.gelivable.web.EnvUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.com.kuaidian.entity.Cuisine;
import cn.com.kuaidian.entity.Order;
import cn.com.kuaidian.entity.OrderCuisine;
import cn.com.kuaidian.entity.shangjia.Contractor;
import cn.com.kuaidian.entity.user.User;
import cn.com.kuaidian.resource.auth.UserSecurity;
import cn.com.kuaidian.service.CuisineService;
import cn.com.kuaidian.service.OrderService;
import cn.com.kuaidian.service.shangjia.ContractorService;
import cn.com.kuaidian.service.user.UserService;
import cn.com.kuaidian.util.CookieUtils;
import cn.com.kuaidian.util.StringUtils;
import cn.com.kuaidian.util.UserBuyCarUtils;
import cn.com.kuaidian.util.dwz.Constants.AppointTimeEnd;
import cn.com.kuaidian.util.dwz.Constants.AppointTimeStart;
import cn.com.kuaidian.util.dwz.DwzUtils;

@Controller
@RequestMapping("/user")
public class OrderController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CuisineService cuisineService;
	
	@Autowired
	private ContractorService contractorService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private GeliDao geliDao;
	
	@RequestMapping(value="/order/create.do")
    public String orderCreate(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Env env = EnvUtils.getEnv();
		long contractorId = env.paramLong("contractorId",0);
		//String cuisineId = env.param("cuisineId", "");
		int appointTimne = env.paramInt("appointTimne",0);
		int taste = env.paramInt("taste",0);
		
        Order order = new Order();
        User user = UserSecurity.getCurrentUser(req);
        String outTradeNo = System.currentTimeMillis()+"";
        String num = (orderService.getOrderCountToday() +1) + "";
    	while(num.length() < 3){
    		num = "0" + num ;
    	}
        order.setOutTradeNo(outTradeNo);
        order.setNumber(num);
		order.setUserId(user.getId());
		Date now = new Date(); //当前时间
		order.setCreateTime(now);
		order.setUpdateTime(now);
		order.setStatus(0);
		order.setTaste(taste);
		if(appointTimne == 1){
			order.setAppointTimeStart(AppointTimeStart.FIRST);
			order.setAppointTimeEnd(AppointTimeEnd.FIRST);
		}else if(appointTimne == 2){
			order.setAppointTimeStart(AppointTimeStart.SECOND);
			order.setAppointTimeEnd(AppointTimeEnd.SECOND);
		}else if(appointTimne == 3){
			order.setAppointTimeStart(AppointTimeStart.THREE);
			order.setAppointTimeEnd(AppointTimeEnd.THREE);
		}else if(appointTimne == 4){
			order.setAppointTimeStart(AppointTimeStart.FOUR);
			order.setAppointTimeEnd(AppointTimeEnd.FOUR);
		}
		geliDao.create(order);
		
		double totalMoney = 0;
		JSONArray cuisine = UserBuyCarUtils.getBuyCarByJsonArray(req);
		if(cuisine !=null && cuisine.size() >0){
			for(int i=0 ;i < cuisine.size() ;i++){
				JSONObject json = cuisine.getJSONObject(i);
				long id = json.getLong("foodid");
				double price = json.getDouble("price");
				double count = json.getDouble("num");
				
				OrderCuisine orderCuisine = new OrderCuisine();
				orderCuisine.setOrderId(order.getId());
				orderCuisine.setCuisineId(id);
				orderCuisine.setNum(1);
				geliDao.create(orderCuisine);
				
				totalMoney += (price * count);
			}
		}
		
		if(contractorId > 0){
			Contractor contractor = contractorService.getContractor(contractorId);
			order.setContractorId(contractor.getId());
		}
		order.setPrice(totalMoney);
		geliDao.update(order);
		
		UserBuyCarUtils.saveBuyCar(req, resp, "");
		return "redirect:/user/order/confirm.do?orderId="+ order.getId();
    }
	
	@RequestMapping(value="/order/confirm.do")
    public String orderConfirm(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Env env = EnvUtils.getEnv();
		long orderId = env.paramLong("orderId",0);
		Order order = orderService.getOrder(orderId);
		
		if(order != null){
			List<Map<String,Object>> cuisines = orderService.getCuisineByOrderId(orderId);
			req.setAttribute("cuisines", cuisines);
		}
		req.setAttribute("order", order);
		
        return "/user/order/confirm";
    }
	
	
	@RequestMapping(value="/order/canncel.do")
    public String orderCanncel(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Env env = EnvUtils.getEnv();
		long orderId = env.paramLong("orderId", 0);
		Order order =  orderService.getOrder(orderId);
		User user = UserSecurity.getCurrentUser(req);
		if(order.getUserId() == user.getId()){
			order.setStatus(-1);
			order.setUpdateTime(new Date());
			geliDao.update(order);
		}
        return "redirect:/user/qr.do?out_trade_no=" + order.getOutTradeNo();
    }
	
	
	@RequestMapping(value="/totallist.do")
    public String totallist(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Env env = EnvUtils.getEnv();
		int pageNo = env.paramInt("pageNo",1);
		int pageSize = env.paramInt("pageSize",5);
		User user = UserSecurity.getCurrentUser(req);
		List<Map<String, Object>> orders = orderService.getOrdersByUserPage(user.getId(), pageNo, pageSize);
		req.setAttribute("orders", orders);
        return "/user/totallist";
    }
	
	
	@RequestMapping(value="/qr.do")
    public String qr(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Env env = EnvUtils.getEnv();
		String outTradeNo =  env.param("out_trade_no", "");
		int isPay =  env.paramInt("isPay", 0);
		Order order = orderService.getOrderByoutTradeNo(outTradeNo);
		if(isPay==1 && order.getStatus() == 0){
			order.setStatus(1); //模拟成功支付,将状态设置为已支付
			geliDao.update(order);//模拟成功支付
		}
		User user = UserSecurity.getCurrentUser(req);
		long userId = user.getId();
		List<Map<String, Object>> orders = orderService.getOrdersByUserPage(userId, 1, 5);
		List<Map<String,Object>>  cuisine = orderService.getCuisineByOrderId(order.getId());
		req.setAttribute("cuisine", cuisine);
		req.setAttribute("orders", orders);
		req.setAttribute("order", order);
        return "/user/qr";
    }
	
	@RequestMapping("/order/pl.do")
	public String pl(HttpServletRequest request,HttpServletResponse response){
		Env env = EnvUtils.getEnv();
		long orderId = env.paramLong("orderId");
		List<Map<String,Object>> cuisines = orderService.getCuisineByOrderId(orderId);
		request.setAttribute("cuisines", cuisines);
		request.setAttribute("orderId", orderId);
		return "/user/order/pl";
	}
	

	@RequestMapping("/order/close.do")
	public String close(HttpServletRequest request,HttpServletResponse response){
		Env env = EnvUtils.getEnv();
		long orderId = env.paramLong("orderId");
		return "/user/order/close";
	}
	
	@ResponseBody
	@RequestMapping(value="/order/update.do")
    public String orderUpdate(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Env env = EnvUtils.getEnv();
		long orderId = env.paramLong("orderId");
		Order order = orderService.getOrder(orderId);
		order.setStatus(2);
		order.setUpdateTime(new Date());
		geliDao.update(order);
		return DwzUtils.success("ok");
	}
	
	@ResponseBody
	@RequestMapping(value="/order/state.do")
    public String orderState(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Env env = EnvUtils.getEnv();
		long orderId = env.paramLong("orderId");
		Order order = orderService.getOrder(orderId);
		Map<String, Object> map = new HashMap<String, Object>();
		if(order!=null){
			int state = order.getStatus();
			map.put("state",state);
		}
		return DwzUtils.success("ok", map);
	}
}
