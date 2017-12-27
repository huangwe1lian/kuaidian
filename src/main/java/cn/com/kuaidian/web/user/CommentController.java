package cn.com.kuaidian.web.user;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gelivable.dao.GeliDao;
import org.gelivable.dao.GeliOrm;
import org.gelivable.web.Env;
import org.gelivable.web.EnvUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.kuaidian.entity.CuisineComment;
import cn.com.kuaidian.entity.OrderComment;
import cn.com.kuaidian.entity.user.User;
import cn.com.kuaidian.resource.auth.UserSecurity;
import cn.com.kuaidian.service.OrderCommentService;
import cn.com.kuaidian.util.dwz.DwzUtils;


@Controller
@RequestMapping("/user/comment")
public class CommentController {
	@Autowired
	private GeliDao geliDao;
	
	@Autowired
	private GeliOrm geliOrm;
	
	@Autowired
	private OrderCommentService commentService;
	
	@RequestMapping(value="/list.do")
    public String list(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Env env = EnvUtils.getEnv();
		req.getAttribute("");
		long userId = env.paramLong("userId",0L);
		
		List<OrderComment> comments = commentService.listComment(userId, 0, 0, 0);
		
		req.setAttribute("comments", comments);
		
		//----
		
        return "/comment/list";
    }
	
	//创建评论
	@RequestMapping(value="/create.do")
    public String createComment(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Env env = EnvUtils.getEnv();
		User user = UserSecurity.getCurrentUser(req);
		req.setCharacterEncoding("utf-8");
		long userId = user.getId();
		long orderId = env.paramLong("orderId", 0);
		long cuisineId = env.paramLong("cuisineId1",0);
		long cuisineId2 = env.paramLong("cuisineId2",0);
		Double avgScore = env.paramDouble("avgScore", 0);
		String text = env.param("text1", "");
		String text2 = env.param("text2", "");
		int score = env.paramInt("score1", 5);
		int score2 = env.paramInt("score2", 5);
		Date now = new Date();
		
		CuisineComment c1 = new CuisineComment();
		c1.setOrderId(orderId);
		c1.setText(text);
		c1.setCuisineId(cuisineId);
		c1.setScore(score);
		c1.setCreateTime(now);
		c1.setUpdateTime(now);
		geliDao.create(c1);	
		
		if(cuisineId2 > 0){
			CuisineComment c2 = new CuisineComment();
			c2.setOrderId(orderId);
			c2.setText(text2);
			c2.setCuisineId(cuisineId2);
			c2.setScore(score2);
			c2.setCreateTime(now);
			c2.setUpdateTime(now);
			geliDao.create(c2);	
		}
		
        return "redirect:/user/mypl.do";
    }
}
