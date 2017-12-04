package cn.com.kuaidian.resource.auth;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.gelivable.dao.GeliDao;
import org.gelivable.dao.GeliUtils;
import org.gelivable.web.EnvUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.com.kuaidian.entity.Function;
import cn.com.kuaidian.entity.user.User;
import cn.com.kuaidian.service.user.UserService;


@Component
public class ContractorAuthFacade {
    String application = "kuaidian";
    String authServer;
    String authUri;
    final Map<Long, Boolean> adminMap = new ConcurrentHashMap<Long, Boolean>();
    AtomicLong updateAt = new AtomicLong();
    
    @Autowired
    UserService userService;

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public void setAuthServer(String authServer) {
        this.authServer = authServer;
    }

    public void setAuthUri(String authUri) {
        this.authUri = authUri;
    }

    public String getAuthUri() {
        return authUri;
    }

    public void clearCache() {
        adminMap.clear();
    }

    public boolean isAdmin() {
        return isAdmin(getUserId());
    }
    
    public boolean isAdmin(Long userId) {
        if (adminMap.isEmpty()) {
            refreshAdminMap();
        }

        if (System.currentTimeMillis() - updateAt.get() > 300000) {
            new Thread(){
                @Override
                public void run() {
                    updateAt.set(System.currentTimeMillis());
                    refreshAdminMap();
                }
            }.start();
        }
        
        synchronized(adminMap) {
            return adminMap.containsKey(userId);
        }
    }

    private void refreshAdminMap() {
        List<Map<String, Object>> adminList = userService.listAdmin();

        if (adminList == null) {
            return;
        }

        synchronized(adminMap) {
            adminMap.clear();
            for (Map<String, Object> admin : adminList) {
            	try {
            		adminMap.put(Long.valueOf(String.valueOf(admin.get("userid"))), Boolean.TRUE);
				} catch (Exception e) {
					e.printStackTrace();
				}
            }
        }
    }

    public boolean hasRight(Function function) {
        return hasRight(function, getUserId());
    }

    public boolean hasRight(Function function, long userId) {
        if (isAdmin(userId)) {
            return true;
        }

        String sql = "select count(*) from sec_user_role u, sec_role_function r "
                + " where u.role_id = r.role_id"
                + " and r.function_id = ?"
                + " and u.user_id = ?";

        GeliDao dao = GeliUtils.getDao();
        int count = dao.count(sql, function.getId(), userId);
        return count > 0;
    }

    public boolean isApplicationUser(long userId) {
        if (isAdmin(userId)) {
            return true;
        }

        return GeliUtils.getDao().count(
                "select count(*) from sec_user_role where user_id = ?", userId) > 0;
    }

    private long getUserId() {
        User user = AdminSecurity.getCurrentUser(EnvUtils.getEnv().getRequest());
        if (user == null) {
            return 0;
        }
        return user.getId();
    }

    private static String trimAndEncode(String uriVal) {
        if (uriVal == null) {
            return "";
        }
        try {
            return URLEncoder.encode(uriVal.trim(), "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex);
        }
    }
 
}

