package cn.com.kuaidian.web;

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

import cn.com.kuaidian.entity.Comment;
import cn.com.kuaidian.service.CommentService;
import cn.com.kuaidian.util.dwz.DwzUtils;


@Controller
@RequestMapping("/comment")
public class CommentController {
	@Autowired
	private GeliDao geliDao;
	
	@Autowired
	private GeliOrm geliOrm;
	
	@Autowired
	private CommentService commentService;
	
	@RequestMapping(value="/list.do")
    public String list(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Env env = EnvUtils.getEnv();
		req.getAttribute("");
		long userId = env.paramLong("userId",0L);
		
		List<Comment> comments = commentService.listComment(userId, 0, 0, 0);
		
		req.setAttribute("comments", comments);
		
		//----
		
        return "/comment/list";
    }
	
	//创建评论
	@RequestMapping(value="/create.do")
	@ResponseBody
    public String createComment(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Env env = EnvUtils.getEnv();
		long userId = env.paramLong("userId", 0);
		long orderId = env.paramLong("orderId", 0);
		Double avgScore = env.paramDouble("avgScore", 0);
		String content = env.param("content", ""); //评论内容
		Date now = new Date();
		
		Comment comment = new Comment();
		comment.setUserId(userId);
		comment.setOrderId(orderId);
		comment.setAvgScore(avgScore);
		comment.setText(content);
		comment.setCreateAt(now);
		
		geliDao.create(comment);	
		
		
        return DwzUtils.successAndForward("success", "");
    }
}
