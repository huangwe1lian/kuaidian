package cn.com.kuaidian.web.user;

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
@RequestMapping("/user/buycar")
public class BuyCarController {
	@Autowired
	private CuisineService cuisineService;
	
	
	@RequestMapping("/list.do")
	public String list(HttpServletRequest request , HttpServletResponse response) throws UnsupportedEncodingException{
		Env env = EnvUtils.getEnv();
		JSONArray cuisine = UserBuyCarUtils.getBuyCar(request);
		int totalMoney = 0;
		if(cuisine !=null && cuisine.size() >0){
			String ids = "";
			for(int i=0 ;i < cuisine.size() ;i++){
				JSONObject json = cuisine.getJSONObject(i);
				String id = json.getString("foodid");
				double price = json.getDouble("price");
				double count = json.getDouble("num");
				totalMoney += (price * count);
				ids += id;
				if(i < cuisine.size()-1){
					ids+=",";
				}
			}
			request.setAttribute("ids", ids);
		}
		
		request.setAttribute("cuisine", cuisine);
		request.setAttribute("totalMoney", totalMoney);
		return "/user/xiadan";
	}
	
	@RequestMapping("/clean.do")
	public void clean(HttpServletRequest request , HttpServletResponse response){
		UserBuyCarUtils.saveBuyCar(request, response, "");
	}
}
