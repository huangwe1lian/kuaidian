package cn.com.kuaidian.web.user;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gelivable.dao.GeliDao;
import org.gelivable.web.Env;
import org.gelivable.web.EnvUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.kuaidian.entity.Cuisine;
import cn.com.kuaidian.entity.Order;
import cn.com.kuaidian.entity.shangjia.Contractor;
import cn.com.kuaidian.entity.user.User;
import cn.com.kuaidian.resource.auth.UserSecurity;
import cn.com.kuaidian.service.CuisineService;
import cn.com.kuaidian.service.shangjia.ContractorService;
import cn.com.kuaidian.service.user.UserService;
import cn.com.kuaidian.util.DateUtils;
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
	private GeliDao geliDao;

	@RequestMapping("/login.do")
	public String longin(){
		return "/user/login";
	}
	
	
	@RequestMapping(value="/index.do",method=RequestMethod.GET)
	public String Index(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		return "/user/index";
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
		      	response.sendRedirect("/user/index.do");
			} else {
				response.sendRedirect("/user/login.do");
			}
		}catch(Exception e){
			e.printStackTrace();
			//response.sendRedirect("msg.jsp?code=login_fail");
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
        Random random = new Random();
        int randomNum = random.nextInt(100);
        String num = System.currentTimeMillis() + "" + user.getId() + "" + randomNum;
        order.setNumber(num);
		order.setUserId(user.getId());
		
		if(cuisineId > 0){
			Cuisine cuisine = cuisineService.getCuisine(cuisineId);
			order.setPrice(cuisine.getPrice());
		}
		
		if(contractorId > 0){
			Contractor contractor = contractorService.getContractor(contractorId);
			order.setContractorId(contractor.getId());
		}
		
		geliDao.create(order);
        return DwzUtils.successAndForward("success", "/user/order/confirm.do");
    }
	
	@RequestMapping(value="/order/confirm.do")
    public String orderConfirm(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Env env = EnvUtils.getEnv();
		/*long contractorId = env.paramLong("contractorId",0);
		long cuisineId = env.paramLong("cuisineId", 0);
        Order order = new Order();
        User user = UserSecurity.getCurrentUser(req);
        Random random = new Random();
        int randomNum = random.nextInt(100);
        String num = System.currentTimeMillis() + "" + user.getId() + "" + randomNum;
        order.setNumber(num);
		order.setUserId(user.getId());
		
		if(cuisineId > 0){
			Cuisine cuisine = cuisineService.getCuisine(cuisineId);
			order.setPrice(cuisine.getPrice());
		}
		
		if(contractorId > 0){
			Contractor contractor = contractorService.getContractor(contractorId);
			order.setContractorId(contractor.getId());
		}*/
		
		//geliDao.create(order);
        return "/user/order/confirm";
    }
	
}
