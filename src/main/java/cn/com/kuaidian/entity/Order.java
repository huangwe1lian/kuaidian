package cn.com.kuaidian.entity;

import org.gelivable.dao.Column;
import org.gelivable.dao.Entity;
import org.gelivable.dao.Id;
import org.gelivable.dao.Label;

@Entity(tableName="kd_order")
public class Order {
	@Id
	@Label("订单表ID")
	@Column(name="id")
	private long id;
	
	@Label("订单号")
	@Column(name = "number")
	private String number;
	
	@Label("商家ID")
	@Column(name = "contractor_id")
	private long contractorId;
	

	@Label("用户ID")
	@Column(name = "user_id")
	private long userId;
	
	@Label("总金额")
	@Column(name = "price")
	private double price;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public long getContractorId() {
		return contractorId;
	}

	public void setContractorId(long contractorId) {
		this.contractorId = contractorId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
}
