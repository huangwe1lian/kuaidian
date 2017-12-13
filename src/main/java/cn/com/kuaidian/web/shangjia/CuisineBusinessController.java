package cn.com.kuaidian.web.shangjia;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gelivable.web.Env;
import org.gelivable.web.EnvUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.com.kuaidian.entity.Cuisine;
import cn.com.kuaidian.service.CuisineService;

@Controller
@RequestMapping("/shangjia/cuisinebusiness")
public class CuisineBusinessController {

	@Autowired
	private CuisineService cuisineService;

	@RequestMapping(value = "/list.do", method = RequestMethod.GET)
	public String list(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Env env = EnvUtils.getEnv();
		int pageNo = env.paramInt("pageNo");
		int pageSize = env.paramInt("pageSize");
		List<Cuisine> cuisineList = cuisineService.getCuisineAllByPage(1, 20);
		System.out.println(cuisineList.size());
		request.setAttribute("list", cuisineList);
		return "/shangjia/cuisine/list";
	}
}
