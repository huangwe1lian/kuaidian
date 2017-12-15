package cn.com.kuaidian.service;

import java.util.Date;
import java.util.List;

import org.gelivable.dao.GeliDao;
import org.gelivable.dao.SqlBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.kuaidian.entity.Cuisine;
import cn.com.kuaidian.entity.Order;
import cn.com.kuaidian.util.DateUtils;

@Service
public class OrderService {
	@Autowired
	private GeliDao geliDao;
	
	
	public Order getOrder(long orderId){
		return geliDao.find(Order.class, orderId);
	}
	
	//统计某时段内订单数量
	public int getOrderCountToday(){
		Date todaty = DateUtils.getBeginLastDay(0);
		return getOrderCount(DateUtils.getStartOfDay(todaty) ,DateUtils.getEndOfDay(todaty));
	}
	
	//统计某时段内订单数量
	public int getOrderCount(Date startTime,Date endTime){
		SqlBuilder sql = new SqlBuilder();
		sql.appendSql("select count(*) from kd_order o, kd_user u where 1=1  ");
		sql.appendSql("and u.id = o.user_id and o.create_time >=").appendValue(startTime);
		sql.appendSql("and o.create_time <= ").appendValue(endTime);		
		return geliDao.count(sql.getSql(), sql.getValues());
	}
	
	
	public List<Cuisine> getCuisineByOrderId(long orderId){
		SqlBuilder sql = new SqlBuilder();
		sql.appendSql("select * from kd_cuisine c,kd_order o,kd_order_cuisine oc ");
		sql.appendSql("where oc.cuisine_id = c.id and oc.order_id = o.id ");
		sql.appendSql("and o.id= ").appendValue(orderId);
		return geliDao.list(Cuisine.class, sql.getSql(), sql.getValues());
	}

	public Order getOrderByoutTradeNo(String outTradeNo) {
		SqlBuilder sql = new SqlBuilder();
		sql.appendSql("select * from kd_order where out_trade_no = ").appendValue(outTradeNo);
		return geliDao.findFirst(Order.class, sql.getSql(), sql.getValues());
	}
	
	public List<Order> getOrdersByUserPage(long userId,int pageNo,int pageSize) {
		SqlBuilder sql = new SqlBuilder();
		sql.appendSql("select * from kd_order where user_id = ").appendValue(userId);
		sql.appendSql(" order by create_time desc");
		return geliDao.page(Order.class, sql.getSql(),pageNo,pageSize,sql.getValues());
	}
	
}
