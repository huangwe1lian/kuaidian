package cn.com.kuaidian.web;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gelivable.web.Env;
import org.gelivable.web.EnvUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.com.kuaidian.entity.Cuisine;
import cn.com.kuaidian.entity.Order;
import cn.com.kuaidian.service.CuisineService;
import cn.com.kuaidian.service.OrderService;
import cn.com.kuaidian.util.UserBuyCarUtils;

@Controller
@RequestMapping("/buycar")
public class BuyCarController {
	@Autowired
	private CuisineService cuisineService;
	
	@RequestMapping("list.do")
	public String list(HttpServletRequest request , HttpServletResponse response) throws UnsupportedEncodingException{
		Env env = EnvUtils.getEnv();
		JSONArray cuisine = UserBuyCarUtils.getBuyCar(request);
		int totalMoney = 0;
		if(cuisine !=null && cuisine.size() >0){
			for(int i=0 ;i < cuisine.size() ;i++){
				JSONObject json = cuisine.getJSONObject(i);
				double price = json.getDouble("price");
				double count = json.getDouble("count");
				totalMoney += (price * count);
			}
		}
		
		request.setAttribute("cuisine", cuisine);
		request.setAttribute("totalMoney", totalMoney);
		return "/user/xiadan";
	}
	
	@RequestMapping("add.do")
	public void add(HttpServletRequest request , HttpServletResponse response) throws UnsupportedEncodingException{
		Env env = EnvUtils.getEnv();
		long id = env.paramLong("id", 0);
		Cuisine cuisine = cuisineService.getCuisine(id);
		long cuisineId = cuisine.getId();
		JSONObject json = new JSONObject();
		json.put("id", cuisine.getId());
		json.put("name",cuisine.getName());
		json.put("price", cuisine.getPrice());
		json.put("count", 1);
		
		JSONArray cuisines = UserBuyCarUtils.getBuyCarByJsonArray(request);
		if(cuisines != null && cuisines.size() >0){
			boolean ifhas = false;
			for (int i = 0; i < cuisines.size(); i++) {
				JSONObject obj = cuisines.getJSONObject(i);
				if(obj.getLong("id") == cuisineId){
					obj.put("count", obj.getIntValue("count") + 1);
					ifhas = true;
				}
			}
			if(!ifhas){
				cuisines.add(json);
			}
		}else{
			cuisines = new JSONArray();
			cuisines.add(json);
		}
		
		String cuisineText = JSONObject.toJSONString(cuisines);
		UserBuyCarUtils.saveBuyCar(request, response, cuisineText);

	}
	
	@RequestMapping("deleted.do")
	public void deleted(HttpServletRequest request , HttpServletResponse response) throws UnsupportedEncodingException{
		Env env = EnvUtils.getEnv();
		long id = env.paramLong("id", 0);
		Cuisine cuisine = cuisineService.getCuisine(id);
		long cuisineId = cuisine.getId();
		
		JSONArray cuisines = UserBuyCarUtils.getBuyCarByJsonArray(request);
		if(cuisines != null && cuisines.size() >0){
			for (int i = 0; i < cuisines.size(); i++) {
				JSONObject obj = cuisines.getJSONObject(i);
				if(obj.getLong("id") == cuisineId){
					cuisines.remove(obj);
					break;
				}
			}
		}
		
		UserBuyCarUtils.saveBuyCar(request, response, cuisines.toString());
	}
	
	@RequestMapping("clean.do")
	public void clean(HttpServletRequest request , HttpServletResponse response){
		UserBuyCarUtils.saveBuyCar(request, response, "");
	}
}
