package cn.com.kuaidian.entity;

import org.gelivable.dao.Column;
import org.gelivable.dao.Entity;
import org.gelivable.dao.Id;
import org.gelivable.dao.Label;

@Entity(tableName="kd_user_order")
public class UserOrder {
	@Id
	@Label("用户订单表ID")
	@Column(name="id")
	private long id;
	
	@Label("用户表ID")
	@Column(name="user_id")
	private long userId;
	
	
	@Label("订单表ID")
	@Column(name="order_id")
	private long orderId;


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public long getUserId() {
		return userId;
	}


	public void setUserId(long userId) {
		this.userId = userId;
	}


	public long getOrderId() {
		return orderId;
	}


	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
}
