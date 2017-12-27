package cn.com.kuaidian.service;

import java.util.List;

import org.gelivable.dao.GeliDao;
import org.gelivable.dao.SqlBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.kuaidian.entity.user.Collection;

@Service
public class CollectionService {
	@Autowired
	private GeliDao geliDao;
	
	public int getCount(long userId,long cuisineId){
		SqlBuilder sql = new SqlBuilder();
		sql.appendSql("select * from kd_collection where user_id = ").appendValue(userId);
		sql.appendSql(" and cuisine_id =").appendValue(cuisineId);
		return geliDao.count(sql.getSql(), sql.getValues());
	}
	
	
	public Collection getCollection(long userId,long cuisineId){
		SqlBuilder sql = new SqlBuilder();
		sql.appendSql("select * from kd_collection where user_id = ").appendValue(userId);
		sql.appendSql(" and cuisine_id =").appendValue(cuisineId);
		List<Collection>list = geliDao.list(Collection.class, sql.getSql() ,sql.getValues());
		if(list !=null && list.size() ==1){
			return list.get(0);
		}else{
			return null;
		}
		
	}
	
	
	public List<Collection> getCollection(long userId){
		SqlBuilder sql = new SqlBuilder();
		sql.appendSql("select * from kd_collection c where c.user_id = ").appendValue(userId);
		sql.appendSql(" and c.status = 1 ");
		sql.appendSql(" order by c.update_time desc");
		List<Collection>list = geliDao.list(Collection.class, sql.getSql() ,sql.getValues());
		return list;
	}
}
