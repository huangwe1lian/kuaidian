package cn.com.kuaidian.service.user;

import java.util.List;
import java.util.Map;

import org.gelivable.dao.GeliDao;
import org.gelivable.dao.SqlBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.kuaidian.entity.user.User;

@Service
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
	
	public long getUserId(String username, String password){
		try {
			SqlBuilder _sql = new SqlBuilder();
			_sql.appendSql("select id from kd_user where username = ? and password = ? ");
			long contractorId = geliDao.getJdbcTemplate().queryForObject(_sql.getSql() ,new Object[]{username,password},Long.class);
			return contractorId;
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return -1l;
	}


	public List<Map<String, Object>> listAdmin() {
		SqlBuilder sql = new SqlBuilder();
		sql.appendSql(" select u.id userId from kd_user_role ur,sec_role r,ft_user u ")
		   .appendSql(" where r.id=ur.role_id and u.id=ur.user_id and r.typ=1 ")
		   .appendSql(" and u.status=1 and u.deleted=0 ");
		return geliDao.getJdbcTemplate().queryForList(sql.getSql());
	}
}
