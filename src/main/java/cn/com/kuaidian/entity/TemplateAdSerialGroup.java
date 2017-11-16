package cn.com.kuaidian.entity;

import java.util.Date;

import org.gelivable.dao.Column;
import org.gelivable.dao.Entity;
import org.gelivable.dao.Id;
import org.gelivable.dao.Label;

@Entity(tableName = "ft_template_ad_serial_group")
@Label("模板广告-车系关联表")
public class TemplateAdSerialGroup {
	@Id
	@Label("模板广告-车系关联表ID")
	private long id;

	@Label("模板广告ID")
	@Column(name = "template_ad_id")
	private long templateAdId;
	
	@Label("车系ID")
	@Column(name = "serial_group_id")
	private long serialGroupId;
	
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

	public long getSerialGroupId() {
		return serialGroupId;
	}

	public void setSerialGroupId(long serialGroupId) {
		this.serialGroupId = serialGroupId;
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
