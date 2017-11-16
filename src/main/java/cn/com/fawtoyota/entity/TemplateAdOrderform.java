package cn.com.fawtoyota.entity;

import java.util.Date;

import org.gelivable.dao.Column;
import org.gelivable.dao.Entity;
import org.gelivable.dao.Id;
import org.gelivable.dao.Label;
import org.gelivable.dao.Refer;
import org.gelivable.dao.Select;

@Entity(tableName = "ft_template_ad_orderform")
@Label("模板广告-报名表单定义表")
public class TemplateAdOrderform {
	@Id
	@Label("模板广告-报名表单定义表ID")
	private long id;

	@Label("模板广告ID")
	@Column(name = "template_ad_id")
	private long templateAdId;
	
	@Label("是否需要填写性别(0:否,1:是)")
	@Refer(fieldPath = "yesOrNo", type = Select.class)
	private int gender;
	
	@Label("是否需要填写姓名(0:否,1:是)")
	@Refer(fieldPath = "yesOrNo", type = Select.class)
	private int name;
	
	@Label("是否需要填写手机(0:否,1:是)")
	@Refer(fieldPath = "yesOrNo", type = Select.class)
	private int mobile;
	
	@Label("是否需要填写车系(0:否,1:是)")
	@Refer(fieldPath = "yesOrNo", type = Select.class)
	@Column(name = "serial_group_id")
	private int serialGroupId;
	
	@Label("是否需要填写车型(0:否,1:是)")
	@Refer(fieldPath = "yesOrNo", type = Select.class)
	@Column(name = "model_id")
	private int modelId;

	@Label("是否需要填写经销商(0:否,1:是)")
	@Refer(fieldPath = "yesOrNo", type = Select.class)
	@Column(name = "dealer_id")
	private int dealerId;
	
	@Label("是否需要填写购买时间(0:否,1:是)")
	@Refer(fieldPath = "yesOrNo", type = Select.class)
	@Column(name = "buy_time")
	private int buyTime;
	
	@Label("是否需要填写购买方式(0:否,1:是)")
	@Refer(fieldPath = "yesOrNo", type = Select.class)
	@Column(name = "pay_type")
	private int payType;
	
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

	public long getTemplateAdId() {
		return templateAdId;
	}

	public void setTemplateAdId(long templateAdId) {
		this.templateAdId = templateAdId;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public int getName() {
		return name;
	}

	public void setName(int name) {
		this.name = name;
	}

	public int getMobile() {
		return mobile;
	}

	public void setMobile(int mobile) {
		this.mobile = mobile;
	}

	public int getSerialGroupId() {
		return serialGroupId;
	}

	public void setSerialGroupId(int serialGroupId) {
		this.serialGroupId = serialGroupId;
	}

	public int getModelId() {
		return modelId;
	}

	public void setModelId(int modelId) {
		this.modelId = modelId;
	}

	public int getDealerId() {
		return dealerId;
	}

	public void setDealerId(int dealerId) {
		this.dealerId = dealerId;
	}

	public int getBuyTime() {
		return buyTime;
	}

	public void setBuyTime(int buyTime) {
		this.buyTime = buyTime;
	}

	public int getPayType() {
		return payType;
	}

	public void setPayType(int payType) {
		this.payType = payType;
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
