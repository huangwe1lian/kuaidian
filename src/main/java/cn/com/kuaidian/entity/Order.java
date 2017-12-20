package cn.com.kuaidian.entity;

import java.util.Date;

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
	
	@Label("取餐号")
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
	
	@Label("订单号")
	@Column(name = "out_trade_no")
	private String outTradeNo;
	
	@Label("订单创建时间")
	@Column(name = "create_time")
	private Date createTime;
	
	@Label("订单更新时间")
	@Column(name = "update_time")
	private Date updateTime;
	
	@Label("订单状态:-1:取消,0:待付款,1:已付款,2:已完成,3:已评价")
	@Column(name = "status")
	private int status;
	
	@Label("预约时间")
	@Column(name="appoint_time_start")
	private Date appointTimeStart; 
	
	@Label("预约时间")
	@Column(name="appoint_time_end")
	private Date appointTimeEnd; 
	
	@Label("口味，0：无，1甜，2酸，3苦，4辣，5咸")
	@Column(name="taste")
	private int taste;
	
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public Date getAppointTimeStart() {
		return appointTimeStart;
	}

	public void setAppointTimeStart(Date appointTimeStart) {
		this.appointTimeStart = appointTimeStart;
	}

	public Date getAppointTimeEnd() {
		return appointTimeEnd;
	}

	public void setAppointTimeEnd(Date appointTimeEnd) {
		this.appointTimeEnd = appointTimeEnd;
	}

	public int getTaste() {
		return taste;
	}

	public void setTaste(int taste) {
		this.taste = taste;
	}
}
