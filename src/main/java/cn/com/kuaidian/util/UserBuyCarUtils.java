package cn.com.kuaidian.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gelivable.web.EncodeUtils;
import org.gelivable.web.Env;
import org.gelivable.web.EnvUtils;

import cn.com.kuaidian.service.CuisineService;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class UserBuyCarUtils {
	public final static String tookenCookieName = "_kd_user_buyCar_";
	
	
	public static void saveBuyCar(HttpServletRequest request, HttpServletResponse response,String cuisineText) {
        try { 	 
        	 Cookie c = new Cookie(tookenCookieName, EncodeUtils.encodeURIComponent(cuisineText));
        	 c.setPath("/");
        	 response.addCookie(c);
        } catch (Exception e) {
            e.printStackTrace();
        }  
        
    }
	
	public static JSONArray getBuyCarByJsonArray(HttpServletRequest request) throws UnsupportedEncodingException{
		JSONArray cuisineArray = null;
		String cuisineText = CookieUtils.getCookie(request, tookenCookieName);
		if(!StringUtils.isBlank(cuisineText))
		{
			JSONObject buycar = JSONObject.parseObject(unescape(cuisineText));
			cuisineArray = buycar.getJSONArray("Carlist");
		}
		return cuisineArray;
	}
	
	public static JSONArray getBuyCar(HttpServletRequest request) throws UnsupportedEncodingException{
		JSONArray list = new JSONArray();
		String cuisineText = CookieUtils.getCookie(request, tookenCookieName);
		System.out.println(cuisineText);
		if(!StringUtils.isBlank(cuisineText))
		{
		  //System.out.println(unescape(cuisineText));
		  JSONObject buycar = JSONObject.parseObject(unescape(cuisineText));
		  JSONArray cuisineArray = buycar.getJSONArray("Carlist");
		  if(cuisineArray != null && cuisineArray.size() > 0){
			  for (int i = 0 ; i < cuisineArray.size(); i++) {
				JSONObject obj = cuisineArray.getJSONObject(i);
				if(obj != null){
					list.add(obj);
				}
			}
		  }
		}
		return list;
	}
	
	
	public static String unescape(String src) {
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length());
		int lastPos = 0, pos = 0;
		char ch;
		while (lastPos < src.length()) {
			pos = src.indexOf("%", lastPos);
			if (pos == lastPos) {
				if (src.charAt(pos + 1) == 'u') {
					ch = (char) Integer.parseInt(
							src.substring(pos + 2, pos + 6), 16);
					tmp.append(ch);
					lastPos = pos + 6;
				} else {
					ch = (char) Integer.parseInt(
							src.substring(pos + 1, pos + 3), 16);
					tmp.append(ch);
					lastPos = pos + 3;
				}
			} else {
				if (pos == -1) {
					tmp.append(src.substring(lastPos));
					lastPos = src.length();
				} else {
					tmp.append(src.substring(lastPos, pos));
					lastPos = pos;
				}
			}
		}
		return tmp.toString();
	}
	
	public String escape(String src) {
		int i;
		char j;
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length() * 6);
		for (i = 0; i < src.length(); i++) {
			j = src.charAt(i);
			if (Character.isDigit(j) || Character.isLowerCase(j)
					|| Character.isUpperCase(j))
				tmp.append(j);
			else if (j < 256) {
				tmp.append("%");
				if (j < 16)
					tmp.append("0");
				tmp.append(Integer.toString(j, 16));
			} else {
				tmp.append("%u");
				tmp.append(Integer.toString(j, 16));
			}
		}
		return tmp.toString();
	}
		  
}
