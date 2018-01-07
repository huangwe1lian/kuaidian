package cn.com.kuaidian.service;

import java.util.List;

import org.gelivable.dao.GeliDao;
import org.gelivable.dao.SqlBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.kuaidian.entity.CuisineComment;

@Service
public class CuisineCommentService {
	@Autowired
	private GeliDao geliDao;
	
	public List<CuisineComment> getCuisineCommentByPage(long cuisineId,int pageNo ,int pageSize){
		SqlBuilder sql = new SqlBuilder();
		sql.appendSql("select cc.*,u.name from kd_cuisine c, kd_cuisine_comment cc,kd_user u where 1=1 ");
		sql.appendSql(" and cc.cuisine_id = c.id and u.id = cc.user_id ");
		sql.appendSql(" and cc.cuisine_id = ").appendValue(cuisineId);
		sql.appendSql(" order by cc.create_time desc ");
		return geliDao.page(CuisineComment.class, sql.getSql(), pageNo, pageSize, sql.getValues());
	}
	
	
	public List<CuisineComment> getCuisineCommentByUserId(long userId){
		SqlBuilder sql = new SqlBuilder();
		sql.appendSql("select cc.* from kd_user u,kd_cuisine_comment cc where 1=1 and u.id = cc.user_id and u.id = ").appendValue(userId);
		sql.appendSql(" order by cc.create_time desc");
		return geliDao.list(CuisineComment.class, sql.getSql(), sql.getValues());
	}
	
	
	public int geCommentCountByUserId(long userId){
		SqlBuilder sql = new SqlBuilder();
		sql.appendSql("select count(*) from kd_cuisine_comment o,kd_user u where u.id= o.user_id ");
		sql.appendSql("and u.id = ").appendValue(userId);
		int count = geliDao.count(sql.getSql(), sql.getValues());
		return count;
	}
}
