package cn.com.kuaidian.resource.auth;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.gelivable.dao.GeliUtils;
import org.gelivable.web.EnvUtils;

import cn.com.kuaidian.entity.shangjia.Contractor;
import cn.com.kuaidian.entity.shangjia.ContractorSession;
import cn.com.kuaidian.entity.user.Session;
import cn.com.kuaidian.service.shangjia.ContractorSessionService;
import cn.com.kuaidian.util.CookieUtils;
import cn.com.kuaidian.util.IPUtils;
import cn.com.kuaidian.util.StringUtils;

/** 
 * 后台登陆安全相关
 * @author  作者： Lin Ruichang E-mail:
 * @date 创建时间：2016-6-3 下午2:47:53
 * @version 1.0
 */
public class ContractorSecurity {
	
	public final static String tookenCookieName = "_kd_contractor_";
	
	public final static String passwordKey = "2f44aaabd753444a";
	
	private static long expiryTime = 1000 * 60 * 60 * 2;	//2小时session过期
	
    public static void saveSession(long contractorId, HttpServletRequest request, HttpServletResponse response) {
        try { 	 
        	 Date date = new Date();       
        	 Timestamp currentTime = new Timestamp(date.getTime());
        	        	 
        	 ContractorSession contractorSession = new ContractorSession();       	
        	 contractorSession.setSessionId(UUID.randomUUID().toString());
        	 contractorSession.setContractorId(contractorId);
        	 contractorSession.setIp(IPUtils.getClientRealIp(request));
        	 contractorSession.setCreateTime(currentTime);
        	 GeliUtils.getDao().create(contractorSession);
        	 Cookie c = new Cookie(tookenCookieName, contractorSession.getSessionId());
        	 response.addCookie(c);
        	 HttpSession httpsession = request.getSession();
        	 httpsession.setAttribute("contractorId", contractorId);
        	
        } catch (Exception e) {
            e.printStackTrace();
        }  
        
    }
	
	/**
     * 判断之前是否登陆
     *
     * @param request
     * @return
     */
    public static ContractorSession recognize(HttpServletRequest request) {
        String sessionId = CookieUtils.getCookie(request, tookenCookieName);
        if (sessionId == null) {
            return null;
        }
        ContractorSession session = EnvUtils.getEnv().getBean(ContractorSessionService.class).findBySessionId(sessionId);
        if (session == null) {
            return null;
        }

        Date createTime = session.getCreateTime();
        if (createTime == null) {
            return null;
        }
        if ((System.currentTimeMillis() - createTime.getTime()) > expiryTime) {
            return null;
        }
        return session;
    }
    
    /**
     * 清除session
     * @param request
     * @param response
     */
    public static void clearSession(HttpServletRequest request, HttpServletResponse response) {
        String sessionId = CookieUtils.getCookie(request, tookenCookieName);

        if (sessionId != null) {
            try {
            	EnvUtils.getEnv().getBean(ContractorSessionService.class).removeSession(sessionId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // remove cookie
        String domain = StringUtils.getDomainSuffix(request.getServerName());
        Cookie c = new Cookie(tookenCookieName, sessionId);
        if (domain.indexOf('.') != -1) {
            c.setDomain(domain);
        }
        c.setPath("/");
        c.setMaxAge(0);
        response.addCookie(c);

    }
    
    /**
     * 获取当前登陆用户信息
     *
     * @param request
     * @return
     */
    public static Contractor getCurrentContractor(HttpServletRequest request) {
    	ContractorSession session = recognize(request);
        if (session == null) {
            return null;
        }
        long contractorId = session.getContractorId();
        Contractor contractor = null;
        try {
        	contractor = GeliUtils.getDao().find(Contractor.class, contractorId);
		} catch (Exception e) {
			contractor = null;
		}
        return contractor;
    }
    
}
