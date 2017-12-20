package cn.com.kuaidian.web.user;

import java.io.IOException;
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

import cn.com.kuaidian.entity.Cuisine;
import cn.com.kuaidian.resource.auth.UserSecurity;
import cn.com.kuaidian.service.CuisineService;
import cn.com.kuaidian.service.OrderService;
import cn.com.kuaidian.service.shangjia.ContractorService;
import cn.com.kuaidian.service.user.UserService;

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
		List<Cuisine> today = cuisineService.getCuisineAllByPage(1, 5); //今日推荐,先出假数据
		List<Cuisine> tromorrow = cuisineService.getCuisineAllByPage(2, 5); //明天日推荐,先出假数据
		
		req.setAttribute("today", today);
		req.setAttribute("tromorrow", tromorrow);
		
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
}
