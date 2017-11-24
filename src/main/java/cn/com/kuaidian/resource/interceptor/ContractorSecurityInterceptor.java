package cn.com.kuaidian.resource.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gelivable.web.Env;
import org.gelivable.web.EnvUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.com.kuaidian.entity.shangjia.Contractor;
import cn.com.kuaidian.service.shangjia.ContractorService;

/**
 * 个人中心权限控制
 */
public class ContractorSecurityInterceptor implements HandlerInterceptor {
	
	@Autowired
	private ContractorService contractorService;
	
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	Env env = EnvUtils.getEnv();
		String username = env.param("username","");
		Contractor contractor = contractorService.getContractorByContractorName(username);
		request.setAttribute("contractor", contractor);
		return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
