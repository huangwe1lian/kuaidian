package cn.com.kuaidian.service;

import java.util.List;

import org.gelivable.dao.GeliDao;
import org.gelivable.dao.SqlBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.kuaidian.entity.OrderComment;

@Service
public class OrderCommentService {
	@Autowired
	private GeliDao geliDao;
	
	public OrderComment getCommentById(long commentId){
		return geliDao.find(OrderComment.class, commentId);
	}
	
	/**
	 * 按条件列出评论信息
	 */
	public List<OrderComment> listComment(long userId, long orderId, int pageNo, int pageSize){
		SqlBuilder sql = new SqlBuilder();
		sql.appendSql("select * kd_comment where 1=1 ");
		if (userId != 0) {
			sql.appendSql(" and user_id = ? ").appendValue(userId);
		}
		if (orderId != 0) {
			sql.appendSql(" and order_id = ? ").appendValue(orderId);
		}
		sql.appendSql(" order by create_time desc");
		if(pageNo == 0 || pageSize == 0) { //不分页
			return geliDao.list(OrderComment.class, sql.getSql(), sql.getValues());
		}
		
		return geliDao.page(OrderComment.class, sql.getSql(), pageNo, pageSize, sql.getValues());
	}
	
	
}
