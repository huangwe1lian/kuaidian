package cn.com.kuaidian.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
	
	
	public List<Map<String, Object>> getCuisineByOrderId(long orderId){
		SqlBuilder sql = new SqlBuilder();
		sql.appendSql("select c.*,oc.num  from kd_cuisine c,kd_order o,kd_order_cuisine oc ");
		sql.appendSql("where oc.cuisine_id = c.id and oc.order_id = o.id ");
		sql.appendSql("and o.id= ").appendValue(orderId);
		return geliDao.getJdbcTemplate().queryForList(sql.getSql(), sql.getValues());
	}

	public Order getOrderByoutTradeNo(String outTradeNo) {
		SqlBuilder sql = new SqlBuilder();
		sql.appendSql("select * from kd_order where out_trade_no = ").appendValue(outTradeNo);
		return geliDao.findFirst(Order.class, sql.getSql(), sql.getValues());
	}
	
	public List<Map<String, Object>> getOrdersByUserPage(long userId,int pageNo,int pageSize) {
		SqlBuilder sql = new SqlBuilder();
		sql.appendSql("select o.*,c.name,c.pic,oc.num from kd_order o,kd_order_cuisine oc,kd_cuisine c where o.id = oc.order_id and c.id = oc.cuisine_id  and user_id = ").appendValue(userId);
		sql.appendSql(" order by create_time desc ");
		sql.appendSql("limit ").appendValue((pageNo-1)*pageSize);
		sql.appendSql(",").appendValue(pageSize);
		return geliDao.getJdbcTemplate().queryForList(sql.getSql(), sql.getValues());
	}
	
	public int getOrderCountByUserId(long userId){
		SqlBuilder sql = new SqlBuilder();
		sql.appendSql(" select count(*) from kd_order o,kd_user u where u.id= o.user_id ");
		sql.appendSql(" and u.id = ").appendValue(userId);
		int count = geliDao.count(sql.getSql(), sql.getValues());
		return count;
	}
	
}
