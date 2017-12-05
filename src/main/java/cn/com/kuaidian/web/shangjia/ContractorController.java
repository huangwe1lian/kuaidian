package cn.com.kuaidian.web.shangjia;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gelivable.web.Env;
import org.gelivable.web.EnvUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.com.kuaidian.resource.auth.ContractorSecurity;
import cn.com.kuaidian.service.shangjia.ContractorService;

@Controller
@RequestMapping("/shangjia")
public class ContractorController {
	
	@Autowired
	private ContractorService contractorService;
	
	@RequestMapping("/login.do")
	public String longin(){
		return "/shangjia/login";
	}
	
	
	@RequestMapping(value="/index.do",method=RequestMethod.GET)
	public String Index() throws IOException{
		return "/shangjia/index";
	}
	
	@RequestMapping(value="/doLogin.do",method=RequestMethod.POST)
	public void doLongin(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Env env = EnvUtils.getEnv();
		String username = env.param("username","");
		String password = env.param("password","");
		try{
			long contractorId = contractorService.getContractorId(username,password);
			//long userid = contractorService.getContractorId(username, Cryptor.encode(password, AdminSecurity.passwordKey, Cryptor.DES));
			if(contractorId > 0){
				ContractorSecurity.saveSession(contractorId,request,response);
		      	response.sendRedirect("/shangjia/index.do");
			} else {
				response.sendRedirect("/shangjia/login.do");
			}
		}catch(Exception e){
			e.printStackTrace();
			//response.sendRedirect("msg.jsp?code=login_fail");
		}
	}
}
