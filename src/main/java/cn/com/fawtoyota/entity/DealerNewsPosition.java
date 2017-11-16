package cn.com.fawtoyota.entity;

import java.util.Date;

import org.gelivable.dao.Column;
import org.gelivable.dao.Entity;
import org.gelivable.dao.Id;
import org.gelivable.dao.Label;
import org.gelivable.dao.Refer;
import org.gelivable.dao.Select;

@Entity(tableName = "ft_dealer_news_position")
@Label("店铺活动推荐位置表")
public class DealerNewsPosition {
	@Id
	@Label("店铺活动推荐位置表ID")
	private long id;

	@Label("经销商文章ID")
	@Column(name = "dealer_news_id")
	private long dealerNewsId;
	
	@Label("页面类型（1:首页;2:车系页）")
	private int type;

	@Label("推荐位置（1:首页本地活动;2:车系页本地活动）文章正文内容")
	@Refer(fieldPath = "dealerNewsPosition", type = Select.class)
	private int position;
	
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

	public long getDealerNewsId() {
		return dealerNewsId;
	}

	public void setDealerNewsId(long dealerNewsId) {
		this.dealerNewsId = dealerNewsId;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	
	
}
