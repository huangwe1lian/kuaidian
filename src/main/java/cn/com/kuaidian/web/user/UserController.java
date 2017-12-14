package cn.com.kuaidian.web.user;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gelivable.dao.GeliDao;
import org.gelivable.web.Env;
import org.gelivable.web.EnvUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
import cn.com.kuaidian.util.dwz.DwzUtils;

@Controller
@RequestMapping("/user")
public class UserController {
	
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

	@RequestMapping("/login.do")
	public String longin(){
		return "/user/login";
	}
	
	
	@RequestMapping(value="/index.do",method=RequestMethod.GET)
	public String Index(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		return "/user/index";
	}
	
	@RequestMapping(value="/usercenter.do",method=RequestMethod.GET)
	public String UserCenter(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		return "/user/usercenter";
	}
	
	@RequestMapping(value="/doLogin.do",method=RequestMethod.POST)
	public void doLongin(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Env env = EnvUtils.getEnv();
		String username = env.param("username","");
		String password = env.param("password","");
		try{
			long userId = userService.getUserId(username,password);
			//long userid = contractorService.getContractorId(username, Cryptor.encode(password, AdminSecurity.passwordKey, Cryptor.DES));
			if(userId > 0){
				UserSecurity.saveSession(userId,request,response);
				response.getWriter().write("suceess");
			} else {
				response.getWriter().write("login_fail");
			}
		}catch(Exception e){
			e.printStackTrace();
			response.getWriter().write("login_fail");
		}
	}
	
	
	@RequestMapping(value="/cuisine/list.do")
    public String cuisineList(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Env env = EnvUtils.getEnv();
		int pageNum = env.paramInt("pageNum", 1);
        int pageSize = env.paramInt("pageSize", 5);
        
		List<Cuisine> cuisines = cuisineService.getCuisineAllByPage(pageNum,pageSize);
		int total = cuisineService.getCuisineCount();
		int totalPage =total%pageSize == 0? total/pageSize : (total/pageSize)+1;
		req.setAttribute("cuisines", cuisines);
		req.setAttribute("total", total);
		req.setAttribute("totalPage", totalPage);
		
        return "/user/cuisine/list";
    }
	
	@RequestMapping(value="/order/create.do")
	@ResponseBody
    public String orderCreate(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Env env = EnvUtils.getEnv();
		long contractorId = env.paramLong("contractorId",0);
		long cuisineId = env.paramLong("cuisineId", 0);
        Order order = new Order();
        User user = UserSecurity.getCurrentUser(req);
        //Random random = new Random();
        //int randomNum = random.nextInt(100);
        //String num = System.currentTimeMillis() + "" + user.getId() + "" + randomNum;
        String num = orderService.getOrderCountToday() + "";
        
        
        order.setNumber(num);
		order.setUserId(user.getId());
		Date now = new Date(); //当前时间
		order.setCreateTime(now);
		order.setUpdateTime(now);
		order.setStatus(0);
		
		if(cuisineId > 0){
			Cuisine cuisine = cuisineService.getCuisine(cuisineId);
			order.setPrice(cuisine.getPrice());
		}
		
		if(contractorId > 0){
			Contractor contractor = contractorService.getContractor(contractorId);
			order.setContractorId(contractor.getId());
		}
		
		geliDao.create(order);
		
		OrderCuisine orderCuisine = new OrderCuisine();
		orderCuisine.setOrderId(order.getId());
		orderCuisine.setCuisineId(cuisineId);
		
		geliDao.create(orderCuisine);
        return DwzUtils.successAndForward("success", "/user/order/confirm.do?orderId="+ order.getId());
    }
	
	@RequestMapping(value="/order/confirm.do")
    public String orderConfirm(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Env env = EnvUtils.getEnv();
		long orderId = env.paramLong("orderId",0);
		Order order = orderService.getOrder(orderId);
		
		if(order != null){
			List<Cuisine> cuisines = orderService.getCuisineByOrderId(orderId);
			req.setAttribute("cuisines", cuisines);
		}
		req.setAttribute("order", order);
		
        return "/user/order/confirm";
    }
	
}
