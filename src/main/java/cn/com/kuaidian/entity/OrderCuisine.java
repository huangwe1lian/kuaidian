package cn.com.kuaidian.entity;

import org.gelivable.dao.Column;
import org.gelivable.dao.Entity;
import org.gelivable.dao.Id;
import org.gelivable.dao.Label;

@Entity(tableName="kd_order_cuisine")
public class OrderCuisine {
	@Id
	@Label("订单菜式中间表")
	@Column(name="id")
	private long id;
	
	@Label("订单菜式中间表")
	@Column(name = "order_id")
	private long orderId;
	
	@Label("订单菜式中间表")
	@Column(name = "cuisine_id")
	private long cuisineId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public long getCuisineId() {
		return cuisineId;
	}

	public void setCuisineId(long cuisineId) {
		this.cuisineId = cuisineId;
	}

}
