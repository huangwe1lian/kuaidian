package cn.com.kuaidian.web.shangjia;

import org.gelivable.web.Env;
import org.gelivable.web.EnvUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.com.kuaidian.entity.shangjia.Contractor;

@Controller
@RequestMapping("/shangjia")
public class ContractorController {
	
	@RequestMapping("/login.do")
	public String longin(){
		return "/shangjia/login";
	}
	
	@RequestMapping(value="/doLogin.do",method=RequestMethod.POST)
	public String doLongin(){
		Env env = EnvUtils.getEnv();
		Contractor contractor = (Contractor) env.getRequest().getAttribute("contractor");
		String password = env.param("password");
		if(contractor != null && password.equals(contractor.getPassword())){
			return "/shangjia/loginSuccess";
		}else{
			return "/shangjia/loginError";
		}
	}
}
