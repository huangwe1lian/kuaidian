package cn.com.fawtoyota.entity;

import java.util.Date;

import org.gelivable.dao.Column;
import org.gelivable.dao.Entity;
import org.gelivable.dao.Id;
import org.gelivable.dao.Label;

@Entity(tableName = "ft_dealer_add_model")
@Label("经销商加装车型表")
public class DealerAddModel {
	@Id
	@Label("经销商加装车型表ID")
	private long id;

	@Label("经销商ID")
	@Column(name = "dealer_id")
	private long dealerId;

	@Label("车系ID")
	@Column(name = "serial_group_id")
	private long serialGroupId;

	@Label("车型ID")
	@Column(name = "model_id")
	private long modelId;

	@Label("官方价（单位:万）")
	private float price;

	@Label("加装车售价（单位:万）")
	@Column(name = "add_price")
	private float addPrice;

	@Label("加装车配置,Json字符串方式存储")
	private String config;

	@Label("创建时间")
	@Column(name = "create_time")
	private Date createTime;

	@Label("更新时间")
	@Column(name = "update_time")
	private Date updateTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getDealerId() {
		return dealerId;
	}

	public void setDealerId(long dealerId) {
		this.dealerId = dealerId;
	}

	public long getSerialGroupId() {
		return serialGroupId;
	}

	public void setSerialGroupId(long serialGroupId) {
		this.serialGroupId = serialGroupId;
	}

	public long getModelId() {
		return modelId;
	}

	public void setModelId(long modelId) {
		this.modelId = modelId;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public float getAddPrice() {
		return addPrice;
	}

	public void setAddPrice(float addPrice) {
		this.addPrice = addPrice;
	}

	public String getConfig() {
		return config;
	}

	public void setConfig(String config) {
		this.config = config;
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
