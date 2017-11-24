package cn.com.kuaidian.resource.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gelivable.web.Env;
import org.gelivable.web.EnvUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.com.kuaidian.entity.user.User;
import cn.com.kuaidian.service.user.UserService;

/**
 * 个人中心权限控制
 * Created by ilinfei on 16/5/30.
 */
public class UserSecurityInterceptor implements HandlerInterceptor {
	
	@Autowired
	private UserService userService;
	
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	Env env = EnvUtils.getEnv();
		String username = env.param("username");
		User user = userService.getUserByUserName(username);
		request.setAttribute("user", user);
		return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
