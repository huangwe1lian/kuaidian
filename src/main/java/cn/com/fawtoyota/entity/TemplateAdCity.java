package cn.com.fawtoyota.entity;

import java.util.Date;

import org.gelivable.dao.Column;
import org.gelivable.dao.Entity;
import org.gelivable.dao.Id;
import org.gelivable.dao.Label;

@Entity(tableName = "ft_template_ad_city")
@Label("模板广告-城市关联表")
public class TemplateAdCity {
	@Id
	@Label("模板广告-城市关联表ID")
	private long id;

	@Label("模板广告ID")
	@Column(name = "template_ad_id")
	private long templateAdId;
	
	@Label("省份ID")
	@Column(name = "province_id")
	private long provinceId;
	
	@Label("城市ID")
	@Column(name = "city_id")
	private long cityId;
	
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

	public long getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(long provinceId) {
		this.provinceId = provinceId;
	}

	public long getCityId() {
		return cityId;
	}

	public void setCityId(long cityId) {
		this.cityId = cityId;
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
