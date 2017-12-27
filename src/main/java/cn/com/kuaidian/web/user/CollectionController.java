package cn.com.kuaidian.web.user;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gelivable.dao.GeliDao;
import org.gelivable.web.Env;
import org.gelivable.web.EnvUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.kuaidian.entity.user.Collection;
import cn.com.kuaidian.entity.user.User;
import cn.com.kuaidian.resource.auth.UserSecurity;
import cn.com.kuaidian.service.CollectionService;

@Controller
@RequestMapping("/user/collection")
public class CollectionController {
	@Autowired
	private GeliDao geliDao;
	
	@Autowired
	private CollectionService collectionService;
	
	@RequestMapping("/add.do")
	public void add(HttpServletRequest request ,HttpServletResponse response){
		Env env = EnvUtils.getEnv();
		User user = UserSecurity.getCurrentUser(request);
		long cuisineId = env.paramLong("cuisineId", 0);
		long userId = user.getId();
		Date now = new Date();
		Collection  c = collectionService.getCollection(userId, cuisineId);
		if(c == null){
			c = new Collection();
			c.setCuisineId(cuisineId);
			c.setUserId(userId);
			c.setCreateTime(now);
			c.setUpdateTime(now);
			c.setStatus(1);
			geliDao.create(c);
		}else{
			c.setStatus(c.getStatus() == 0?1:0);
			c.setUpdateTime(now);
			geliDao.update(c);
		}
	}
	
	
	@RequestMapping("/list.do")
	public String list(HttpServletRequest request ,HttpServletResponse response){
		Env env = EnvUtils.getEnv();
		User user = UserSecurity.getCurrentUser(request);
		return "/user/collection/list";
	}
}
