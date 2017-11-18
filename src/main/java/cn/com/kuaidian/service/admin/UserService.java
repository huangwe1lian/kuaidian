package cn.com.kuaidian.service.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.gelivable.dao.GeliDao;
import org.gelivable.dao.GeliOrm;
import org.gelivable.dao.SqlBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.kuaidian.entity.Role;
import cn.com.kuaidian.entity.User;
import cn.com.kuaidian.entity.UserRole;

/** 
 * 
 * @author  作者： Lin Ruichang E-mail:
 * @date 创建时间：2016-6-3 下午3:43:34
 * @version 1.0
 */
@Service
public class UserService {
	@Autowired
	GeliDao geliDao;
	
	@Autowired
	GeliOrm geliOrm;
	
	/**
	 * 獲取用戶信息
	 * @param username
	 * @param password
	 * @return
	 */
	public User find(String username, String password){
		String sql = "select * from ft_user where login_name = ? and login_pwd = ?";
		List<User> list = geliDao.getJdbcTemplate().queryForList(sql, new Object[]{username,password},User.class);
		if(list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public long findUserId(String username, String password){
		try {
			SqlBuilder user_sql = new SqlBuilder();
			user_sql.appendSql("select id from ft_user where login_name = ? and login_pwd = ? ");
			long userid = geliDao.getJdbcTemplate().queryForObject(user_sql.getSql() ,new Object[]{username,password},Long.class);
			return userid;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return -1l;
	}
	
	/**
	 * 获取Admin的集合
	 * @return
	 */
	public List<Map<String,Object>> listAdmin(){
		SqlBuilder sql = new SqlBuilder();
		sql.appendSql(" select u.id userId from sec_user_role ur,sec_role r,ft_user u ")
		   .appendSql(" where r.id=ur.role_id and u.id=ur.user_id and r.typ=1 ")
		   .appendSql(" and u.status=1 and u.deleted=0 ");
		return geliDao.getJdbcTemplate().queryForList(sql.getSql());
	}
	
	
	/**
	 * 根据用户id判断该用户是否厂商
	 * @param userId
	 * @return
	 */
	public boolean isManufacturer(long userId){
		if (userId<=0) {
			return false;
		}
    	SqlBuilder sql = new SqlBuilder();
    	sql.appendSql("select count(*) from sec_user_role ur left join sec_role r on r.id=ur.role_id ")
    	.appendSql(" where 1=1 and r.typ = 2 and ur.user_id = ").appendValue(userId);
    	return geliDao.getJdbcTemplate().queryForInt(sql.getSql(),sql.getValues())>0;
	 }
	
	
	
	/**
	 * 根据用户ID获取角色集合
	 * @param userId
	 * @return
	 */
	public List<Role> findRoleByUserId(long userId){
		SqlBuilder sql = new SqlBuilder();
		sql.appendSql(" select r.* from sec_role r,sec_user_role ur where ur.role_id=r.id and ur.user_id=")
		   .appendValue(userId);
		return geliDao.list(Role.class, sql.getSql(), sql.getValues());
	}
}
