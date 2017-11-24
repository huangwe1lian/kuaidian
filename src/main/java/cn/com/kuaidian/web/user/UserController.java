package cn.com.kuaidian.web.user;

import org.gelivable.dao.GeliDao;
import org.gelivable.web.Env;
import org.gelivable.web.EnvUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.com.kuaidian.entity.user.User;
import cn.com.kuaidian.service.user.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private GeliDao geliDao;

	@RequestMapping("/login.do")
	public String longin(){
		return "/user/login";
	}
	
	
	@RequestMapping(value="/doLogin.do",method=RequestMethod.POST)
	public String doLongin(){
		Env env = EnvUtils.getEnv();
		User user = (User) env.getRequest().getAttribute("user");
		String username = env.param("username");
		String password = env.param("password");
		if(user != null && password.equals(user.getPassword())){
			return "/user/loginSuccess";
		}else{
			return "/user/loginError";
		}
	}
}
