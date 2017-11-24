package cn.com.kuaidian.web.staff;

import org.gelivable.web.Env;
import org.gelivable.web.EnvUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.com.kuaidian.entity.staff.Staff;

@Controller
@RequestMapping("/staff")
public class StaffController {
	
	@RequestMapping("/login.do")
	public String longin(){
		return "/staff/login";
	}
	
	
	@RequestMapping(value="/doLogin.do",method=RequestMethod.POST)
	public String doLongin(){
		Env env = EnvUtils.getEnv();
		Staff staff = (Staff) env.getRequest().getAttribute("staff");
		String password = env.param("password");
		if(staff != null && password.equals(staff.getPassword())){
			return "/user/loginSuccess";
		}else{
			return "/user/loginError";
		}
	}
}
