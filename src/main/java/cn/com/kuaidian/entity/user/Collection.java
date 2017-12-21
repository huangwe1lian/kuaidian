package cn.com.kuaidian.entity.user;

import java.util.Date;

import org.gelivable.dao.Column;
import org.gelivable.dao.Entity;
import org.gelivable.dao.Id;
import org.gelivable.dao.Label;

@Entity(tableName="kd_collection")
public class Collection {
	@Id
	@Label("收藏ID")
	@Column(name="id")
	private long id;
	
	@Label("用户ID")
	@Column(name="user_id")
	private long userId;
	
	@Label("菜式ID")
	@Column(name="cuisine_id")
	private long cuisineId;
	
	@Label("收藏状态，0未收藏，1已收藏")
	@Column(name="status")
	private int status;
	
	@Label("创建时间")
	@Column(name="create_time")
	private Date createTime;
	
	@Label("更新时间")
	@Column(name="update_time")
	private Date updateTime;

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

	public long getCuisineId() {
		return cuisineId;
	}

	public void setCuisineId(long cuisineId) {
		this.cuisineId = cuisineId;
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
}
