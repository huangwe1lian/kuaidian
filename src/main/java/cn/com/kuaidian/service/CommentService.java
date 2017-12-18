package cn.com.kuaidian.service;

import java.util.List;

import org.gelivable.dao.GeliDao;
import org.gelivable.dao.SqlBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.kuaidian.entity.Comment;

@Service
public class CommentService {
	@Autowired
	private GeliDao geliDao;
	
	public Comment getCommentById(long commentId){
		return geliDao.find(Comment.class, commentId);
	}
	
	/**
	 * 按条件列出评论信息
	 */
	public List<Comment> listComment(long userId, long orderId, int pageNo, int pageSize){
		SqlBuilder sql = new SqlBuilder();
		sql.appendSql("select * kd_comment where 1=1 ");
		if (userId != 0) {
			sql.appendSql(" and user_id = ? ").appendValue(userId);
		}
		if (orderId != 0) {
			sql.appendSql(" and order_id = ? ").appendValue(orderId);
		}
		sql.appendSql(" order by create_at desc");
		if(pageNo == 0 || pageSize == 0) { //不分页
			return geliDao.list(Comment.class, sql.getSql(), sql.getValues());
		}
		
		return geliDao.page(Comment.class, sql.getSql(), pageNo, pageSize, sql.getValues());
	}
	
	
}
