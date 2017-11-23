package cn.com.kuaidian.service.user;

import org.gelivable.dao.GeliDao;
import org.gelivable.dao.SqlBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.com.kuaidian.entity.user.User;

@Repository
public class UserService {
	@Autowired
	private GeliDao geliDao;
	
	public User getUser(long userId){
		return geliDao.find(User.class, userId);
	}
	
	
	public User getUserByUserName(String username){
		SqlBuilder sql = new SqlBuilder();
		sql.appendSql("select * from kd_user where username = ").appendValue(username);
		return geliDao.findFirst(User.class,sql.getSql(),sql.getValues());
	}
}
