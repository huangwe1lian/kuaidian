package cn.com.kuaidian.resource.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.com.kuaidian.util.PropertiesUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Properties;

/**
 * 读取系统配置拦截器
 */
public class SystemConfigInterceptor implements HandlerInterceptor {

    @Autowired
    private PropertiesUtils propertiesUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //将root.properties文件中的属性加载到Model中
        if(modelAndView != null){
            Properties properties = propertiesUtils.getPropertiesByName("root.properties");
            for (Object key : properties.keySet()) {
                String keyStr = key.toString();
                String value = properties.get(keyStr).toString();
                modelAndView.addObject(keyStr, value);
            }
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

}
