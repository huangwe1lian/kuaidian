package cn.com.kuaidian.entity;

import java.util.Date;

import org.gelivable.dao.Column;
import org.gelivable.dao.Entity;
import org.gelivable.dao.Id;
import org.gelivable.dao.Label;

@Entity(tableName = "ft_dealer_news_add_model")
@Label("行情加装车型表")
public class DealerNewsAddModel {
	@Id
	@Label("行情加装车型表ID")
	private long id;

	@Label("经销商ID")
	@Column(name = "dealer_id")
	private long dealerId;

	@Label("行情文章ID")
	@Column(name = "dealer_news_id")
	private long dealerNewsId;

	@Label("经销商加装车型ID")
	@Column(name = "dealer_add_model_id")
	private long dealerAddModelId;

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

	public long getDealerNewsId() {
		return dealerNewsId;
	}

	public void setDealerNewsId(long dealerNewsId) {
		this.dealerNewsId = dealerNewsId;
	}

	public long getDealerAddModelId() {
		return dealerAddModelId;
	}

	public void setDealerAddModelId(long dealerAddModelId) {
		this.dealerAddModelId = dealerAddModelId;
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
