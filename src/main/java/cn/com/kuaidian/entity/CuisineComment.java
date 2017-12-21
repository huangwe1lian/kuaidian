package cn.com.kuaidian.entity;

import java.util.Date;

import org.gelivable.dao.Column;
import org.gelivable.dao.Entity;
import org.gelivable.dao.Id;
import org.gelivable.dao.Label;
import org.gelivable.dao.Refer;

import cn.com.kuaidian.entity.user.User;

@Entity(tableName="kd_cuisine_comment")
public class CuisineComment {
	@Id
	@Label("评论ID")
	@Column(name = "id")
	private long id;
	
	@Label("订单ID")
	@Column(name="order_id")
	private long orderId;
	
	@Label("菜式ID")
	@Column(name="cuisine_id")
	@Refer(fieldPath = "cuisine.id", type = Cuisine.class)
	private long cuisineId;
	
	@Label("用户ID")
	@Column(name="user_id")
	@Refer(fieldPath = "user.id", type = User.class)
	private long userId;
	
	@Label("评分")
	@Column(name="score")
	private double score;
	
	@Label("评价内容")
	@Column(name="text")
	private String text;
	
	@Label("创建时间")
	@Column(name="create_time")
	private Date createTime;
	
	@Label("创建时间")
	@Column(name="update_time")
	private Date updateTime;

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
	
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
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
