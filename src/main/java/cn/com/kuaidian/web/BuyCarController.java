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
		List<Cuisine> cuisine = UserBuyCarUtils.getBuyCar(request);
		request.setAttribute("cuisine", cuisine);
		return "/user/buycar";
	}
	
	@RequestMapping("add.do")
	public void add(HttpServletRequest request , HttpServletResponse response) throws UnsupportedEncodingException{
		Env env = EnvUtils.getEnv();
		long id = env.paramLong("id", 0);
		Cuisine cuisine = cuisineService.getCuisine(id);
		
		JSONObject json = new JSONObject();
		json.put("id", cuisine.getId());
		
		JSONArray cuisines = UserBuyCarUtils.getBuyCarByJsonArray(request);
		if(cuisines != null){
			cuisines.add(json);
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
		
		Cuisine c = new Cuisine();
		c.setId(id);
		c.setContractorId(cuisine.getContractorId());
		c.setName(cuisine.getName());
		c.setPrice(cuisine.getPrice());
		
		List<Cuisine> cuisines = UserBuyCarUtils.getBuyCar(request);
		cuisines.remove(c);
		
		UserBuyCarUtils.saveBuyCar(request, response, cuisines.toString());
	}
	
	@RequestMapping("clean.do")
	public void clean(HttpServletRequest request , HttpServletResponse response){
		UserBuyCarUtils.saveBuyCar(request, response, "");
	}
}
