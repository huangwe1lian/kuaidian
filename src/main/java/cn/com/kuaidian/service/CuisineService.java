package cn.com.kuaidian.service;

import org.gelivable.dao.GeliDao;
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
	
}
