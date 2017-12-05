package cn.com.kuaidian.resource.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gelivable.web.Env;
import org.gelivable.web.EnvUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.com.kuaidian.entity.shangjia.Contractor;
import cn.com.kuaidian.entity.user.User;
import cn.com.kuaidian.resource.auth.UserSecurity;
import cn.com.kuaidian.service.user.UserService;

/**
 * 个人中心权限控制
 */
public class UserSecurityInterceptor implements HandlerInterceptor {
	public static final boolean HAS_RIGHT = true;
    public static  final boolean HAS_NOT_RIGHT = false;
    
    public static final Map<String,Long> functionMap = new HashMap<String,Long>();
	
    static {
    	functionMap.put("user", 31l);
    	functionMap.put("role", 22l);
    	functionMap.put("function", 21l);
    }
    
	@Autowired
	private UserService userService;
	
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	Env env = EnvUtils.getEnv();
        //AdminAuthFacade authFacade = env.getBean(AdminAuthFacade.class);
        String uri = request.getRequestURI();

        String userPrefix = env.getServletContext().getContextPath() + "/user";
        
        if (uri.startsWith(userPrefix)) {
 			User user = UserSecurity.getCurrentUser(request);
 			if (user == null) {
 				response.sendRedirect("/user/login.do");
 				return HAS_NOT_RIGHT;
 			}
 		}
        
        /**
         * 增加对权限的判断
         * 按功能增加对应的权限管理
         * 根据功能名称获取权限
         */
        boolean hasRight = HAS_RIGHT;
        /*try {
        	for(String key : functionMap.keySet()){
        		if (matchActions(key, request, "")) {
                    hasRight = authFacade.hasRight(geliDao.find(Function.class, functionMap.get(key))) ? HAS_RIGHT : HAS_NOT_RIGHT;
                    if(!hasRight){
                    	sendAuthFail(response, false);
                    }
                    break;
                }
        	}
        	
		} catch (Exception e) {
			e.printStackTrace();
		}*/
        
 		
    	return hasRight;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
