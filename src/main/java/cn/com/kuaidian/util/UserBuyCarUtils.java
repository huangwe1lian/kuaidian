package cn.com.kuaidian.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gelivable.web.EncodeUtils;
import org.gelivable.web.Env;
import org.gelivable.web.EnvUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.com.kuaidian.entity.Cuisine;
import cn.com.kuaidian.service.CuisineService;

public class UserBuyCarUtils {
	public final static String tookenCookieName = "_kd_user_buyCar";
	
	
	public static void saveBuyCar(HttpServletRequest request, HttpServletResponse response,String cuisineText) {
        try { 	 
        	 Cookie c = new Cookie(tookenCookieName, EncodeUtils.encodeURIComponent(cuisineText));
        	 response.addCookie(c);
        } catch (Exception e) {
            e.printStackTrace();
        }  
        
    }
	
	public static JSONArray getBuyCarByJsonArray(HttpServletRequest request) throws UnsupportedEncodingException{
		JSONArray cuisineArray = null;
		Env env = EnvUtils.getEnv();
		String cuisineText = CookieUtils.getCookie(request, tookenCookieName);
		if(!StringUtils.isBlank(cuisineText))
		{
		  cuisineArray = JSONObject.parseArray(URLDecoder.decode(cuisineText, "utf-8"));
		}
		return cuisineArray;
	}
	
	public static List<Cuisine> getBuyCar(HttpServletRequest request) throws UnsupportedEncodingException{
		List<Cuisine> list = new ArrayList<Cuisine>();
		Env env = EnvUtils.getEnv();
		CuisineService cuisineService = env.getBean(CuisineService.class);
		String cuisineText = CookieUtils.getCookie(request, tookenCookieName);
		if(!StringUtils.isBlank(cuisineText))
		{
		  JSONArray cuisineArray = JSONObject.parseArray(URLDecoder.decode(cuisineText, "utf-8"));
		  if(cuisineArray != null && cuisineArray.size() > 0){
			  for (int i = 0 ; i < cuisineArray.size(); i++) {
				JSONObject obj = cuisineArray.getJSONObject(i);
				long id = obj.getLongValue("id");
				Cuisine c = cuisineService.getCuisine(id);
				list.add(c);
			}
		  }
		}
		return list;
	}
}
