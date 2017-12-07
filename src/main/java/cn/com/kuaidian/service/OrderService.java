package cn.com.kuaidian.service;

import org.gelivable.dao.GeliDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
	@Autowired
	private GeliDao geliDao;
	
}
