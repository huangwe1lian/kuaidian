package cn.com.kuaidian.resource.interceptor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.gelivable.dao.GeliDao;
import org.gelivable.web.Env;
import org.gelivable.web.EnvUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.com.kuaidian.entity.Function;
import cn.com.kuaidian.entity.User;
import cn.com.kuaidian.resource.auth.AdminSecurity;
import cn.com.kuaidian.resource.auth.AdminAuthFacade;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 厂商,区域后台权限控制
 * Created by ilinfei on 16/5/30.
 */
public class AdminSecurityInterceptor implements HandlerInterceptor {
	public static final boolean HAS_RIGHT = true;
    public static  final boolean HAS_NOT_RIGHT = false;
    
    public static final Map<String,Long> functionMap = new HashMap<String,Long>();
    
    static {
    	functionMap.put("user", 31l);
    	functionMap.put("role", 22l);
    	functionMap.put("function", 21l);
    }
    
    @Autowired
    GeliDao geliDao;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	Env env = EnvUtils.getEnv();
        AdminAuthFacade authFacade = env.getBean(AdminAuthFacade.class);
        String uri = request.getRequestURI();

        String adminPrefix = env.getServletContext().getContextPath() + "/admin";
        
        if (uri.startsWith(adminPrefix)) {
 			User user = AdminSecurity.getCurrentUser(request);
 			if (user == null) {
 				response.sendRedirect("/admin/login.jsp");
 				return HAS_NOT_RIGHT;
 			}
 		}
        
        /**
         * 增加对权限的判断
         * 按功能增加对应的权限管理
         * 根据功能名称获取权限
         */
        boolean hasRight = HAS_RIGHT;
        try {
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
		}
        
 		
    	return hasRight;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
    
    
    private void sendAuthFail(HttpServletResponse resp, boolean json) throws IOException {
        if (json) {
            resp.setCharacterEncoding("UTF-8");
            resp.setContentType("text/json");
            resp.getWriter().println("{\"statusCode\":300, \"message\":\"没有权限！\"}");
        } else {
            resp.setCharacterEncoding("UTF-8");
            resp.setContentType("text/html");
            resp.getWriter().print("<div class=\"pageContent\">"
                    + "<div style='padding-top:200px;text-align:center;"
                    + "font-size:24px;color:red;'>"
                    + "没有权限!</div></div>");
        }
    }
    
    boolean matchActions(String entityName, HttpServletRequest req, String ... actions) {
        String uri = req.getRequestURI();
        Env env = EnvUtils.getEnv();
        String uriPrefix = env.getServletContext().getContextPath() + "/admin/" + entityName.toLowerCase() + '/';
        for (String action : actions) {
            if (uri.startsWith(uriPrefix + action)) {
                return true;
            }
        }
        return false;
    }
    
    
}