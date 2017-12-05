package cn.com.kuaidian.service;

import java.util.List;

import org.gelivable.dao.GeliDao;
import org.gelivable.dao.SqlBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.kuaidian.entity.Cuisine;

@Service
public class CuisineService {
	@Autowired
	private GeliDao geliDao;
	
	public Cuisine getCuisine(long cuisineId){
		return geliDao.find(Cuisine.class, cuisineId);
	}
	
	
	public List<Cuisine> getCuisineByContractorId(long contractorId){
		SqlBuilder sql = new SqlBuilder();
		sql.appendSql("select * from kd_cuisine c, kd_contractor c1 where 1=1 ");
		sql.appendSql("and c1.id = c.contractor_id and c1.id = ").appendValue(contractorId);
		return geliDao.list(Cuisine.class, sql.getSql(), sql.getValues());
	}
	
	public List<Cuisine> getCuisineAllByPage(int pageNum,int pageSize){
		SqlBuilder sql = new SqlBuilder();
		sql.appendSql("select * from kd_cuisine c, kd_contractor c1 where 1=1 ");
		sql.appendSql("and c1.id = c.contractor_id");
		return geliDao.page(Cuisine.class, sql.getSql(),pageNum , pageSize, sql.getValues());
	}
}
