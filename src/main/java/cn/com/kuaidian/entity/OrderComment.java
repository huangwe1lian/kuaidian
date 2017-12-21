package cn.com.kuaidian.entity;

import java.util.Date;

import org.gelivable.dao.Column;
import org.gelivable.dao.Entity;
import org.gelivable.dao.Id;
import org.gelivable.dao.Label;

@Entity(tableName ="kd_order_comment")
public class OrderComment {
	@Id
	@Label("评论ID")
	@Column(name = "id")
	private long id;
	
	@Label("订单ID")
	@Column(name="order_id")
	private long orderId;
	
	@Label("用户ID")
	@Column(name="user_id")
	private long userId;
	
	@Label("平均分")
	@Column(name="avgScore")
	private double avgScore;
	
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

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public double getAvgScore() {
		return avgScore;
	}

	public void setAvgScore(double avgScore) {
		this.avgScore = avgScore;
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
