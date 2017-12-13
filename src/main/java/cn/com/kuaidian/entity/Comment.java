package cn.com.kuaidian.entity;

import org.gelivable.dao.Column;
import org.gelivable.dao.Entity;
import org.gelivable.dao.Id;
import org.gelivable.dao.Label;

@Entity(tableName ="kd_comment")
public class Comment {
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
}
