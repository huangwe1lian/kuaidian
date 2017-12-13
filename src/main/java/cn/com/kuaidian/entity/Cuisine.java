package cn.com.kuaidian.entity;

import java.util.Date;

import org.gelivable.dao.Column;
import org.gelivable.dao.Entity;
import org.gelivable.dao.Id;
import org.gelivable.dao.Label;

@Entity(tableName="kd_cuisine")
public class Cuisine {
	@Id
	@Label("菜式表ID")
	@Column(name="id")
	private long id;
	
	@Label("菜式名称")
	@Column(name="name")
	private String name;
	
	@Label("商家ID")
	@Column(name="contractor_id")
	private long contractorId;
	
	@Label("价格")
	@Column(name="price")
	private double price;
	
	@Label("菜式创建时间")
	@Column(name = "create_time")
	private Date createTime;
	
	@Label("菜式更新时间")
	@Column(name = "update_time")
	private Date updateTime;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getContractorId() {
		return contractorId;
	}

	public void setContractorId(long contractorId) {
		this.contractorId = contractorId;
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
	
}
