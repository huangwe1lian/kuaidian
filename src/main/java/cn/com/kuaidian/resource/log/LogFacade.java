package cn.com.kuaidian.resource.log;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.gelivable.dao.GeliUtils;
import org.gelivable.web.Env;
import org.gelivable.web.EnvUtils;

import cn.com.kuaidian.entity.UserLog;
import cn.com.kuaidian.entity.UserLogDetail;
import cn.com.kuaidian.resource.auth.AdminSecurity;

/** 
 * 日志类
 * @author  作者： Lin Ruichang E-mail:
 * @date 创建时间：2016-6-1 下午4:00:12
 * @version 1.0
 */
public class LogFacade {
	public static final String LOG_ID = "__LOG_ID__";

    public static void log(Object entity) throws IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        long userId = AdminSecurity.getCurrentUser(EnvUtils.getEnv().getRequest()).getId();
        log(userId,entity);
    }
    
    public static void log(long userId,Object entity) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, SecurityException, InvocationTargetException, NoSuchMethodException {
    	Class clazz = Class.forName(entity.getClass().getName());
    	long id = (Long) GeliUtils.getOrm().getFieldGetter(clazz, "id").get(entity);
    	
        UserLog log = new UserLog();
        Env env = EnvUtils.getEnv();
        HttpServletRequest req = env.getRequest();
        String uri = req.getRequestURI();
        String action = uri.substring(uri.lastIndexOf("/") + 1);
        log.setIp(req.getRemoteAddr());
        log.setAction(action);
        log.setCreateTime(new Date());
        log.setObjId(id);
        log.setObjName(clazz.getSimpleName());
        log.setReferer(req.getHeader("referer"));
        log.setUserId(userId);
        long logId = GeliUtils.getDao().create(log);
        env.setCache(LOG_ID, logId);
        logDetail(entity);
    }

    public static void logDetail(Object entity) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, SecurityException, InvocationTargetException, NoSuchMethodException {
        Env env = EnvUtils.getEnv();
        Long logId = (Long)env.getCache(LOG_ID);
        if (logId == null) {
            return;
        }
        
        Class clazz = Class.forName(entity.getClass().getName());
    	long id = (Long) GeliUtils.getOrm().getFieldGetter(clazz, "id").get(entity);
    	
    	Object old = null;
    	if(id > 0){
    		old = GeliUtils.getDao().findDb(entity.getClass(), id, GeliUtils.getOrm().getObjectId(entity));
    	}
        
        UserLogDetail logDetail = new UserLogDetail();
        logDetail.setUserLogId(logId);
        if(id > 0){
        	logDetail.setBefContent(GeliUtils.getDao().object2String(old));
        }
        logDetail.setAftContent(GeliUtils.getDao().object2String(entity));
        logDetail.setCreateTime(new Date());
        GeliUtils.getDao().create(logDetail);
    }
}
