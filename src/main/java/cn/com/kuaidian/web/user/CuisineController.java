package cn.com.kuaidian.web.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gelivable.dao.GeliOrm;
import org.gelivable.web.Env;
import org.gelivable.web.EnvUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.kuaidian.entity.Cuisine;
import cn.com.kuaidian.service.CuisineService;

@Controller
@RequestMapping("/user")
public class CuisineController {
	
	@Autowired
	private GeliOrm geliOrm;
	
	@Autowired
	private CuisineService cuisineService;
	
	@RequestMapping(value="/cuisine/list.do")
    public String cuisineList(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Env env = EnvUtils.getEnv();
		int pageNum = env.paramInt("pageNum", 1);
        int pageSize = env.paramInt("pageSize", 5);
        
		List<Cuisine> cuisines = cuisineService.getCuisineAllByPage(pageNum,pageSize);
		int total = cuisineService.getCuisineCount();
		int totalPage =total%pageSize == 0? total/pageSize : (total/pageSize)+1;
		req.setAttribute("cuisines", cuisines);
		req.setAttribute("total", total);
		req.setAttribute("totalPage", totalPage);
		
        return "/user/cuisine/diancan";
    }
}
