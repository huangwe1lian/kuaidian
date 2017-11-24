package cn.com.kuaidian.resource.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gelivable.web.Env;
import org.gelivable.web.EnvUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.com.kuaidian.entity.staff.Staff;
import cn.com.kuaidian.service.staff.StaffService;

/**
 * 个人中心权限控制
 * Created by ilinfei on 16/5/30.
 */
public class StaffSecurityInterceptor implements HandlerInterceptor {
	
	@Autowired
	private StaffService staffService;
	
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	Env env = EnvUtils.getEnv();
		String username = env.param("username","");
		Staff staff = staffService.getStaffByStaffName(username);
		request.setAttribute("staff", staff);
		return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
