package cn.com.fawtoyota.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/** 
 * Cookie工具类
 * @author  作者： Lin Ruichang E-mail:
 * @date 创建时间：2016-6-3 下午3:42:20
 * @version 1.0
 */
public class CookieUtils {
	/**
     * 获得指定的cookie值
     *
     * @param request
     * @param cookieName
     * @return cookie值
     */
    public static String getCookie(HttpServletRequest request, String cookieName) {
        try {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (int i = 0, c = cookies.length; i < c; ++i) {
                    Cookie cookie = cookies[i];
                    if (cookieName.equals(cookie.getName())) {
                        return cookie.getValue();
                    }
                }
            }
        } catch (Exception e) {
            // need do nothing
        }
        return null;
    }
}
