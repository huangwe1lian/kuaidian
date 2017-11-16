package cn.com.fawtoyota.entity;

import java.util.Date;

import org.gelivable.dao.Column;
import org.gelivable.dao.Entity;
import org.gelivable.dao.Id;
import org.gelivable.dao.Label;

@Entity(tableName = "ft_order_code")
@Label("名单报名授权表")
public class OrderCode {
	@Id
	@Label("名单报名授权ID")
	private long id;
	
	@Label("授权代码")
	@Column(name = "order_code")
	private String orderCode;
	
	@Label("授权代码说明")
	private String description;
	
	@Label("相关专题、活动链接")
	private String url;
	
	@Label("状态（0:禁用,1:正常）")
	private int status;
	
	@Label("创建人ID")
	@Column(name = "create_user_id")
	private long createUserId;

	@Label("更新人ID")
	@Column(name = "update_user_id")
	private long updateUserId;
	
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

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public long getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(long createUserId) {
		this.createUserId = createUserId;
	}

	public long getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(long updateUserId) {
		this.updateUserId = updateUserId;
	}

	
	
}
