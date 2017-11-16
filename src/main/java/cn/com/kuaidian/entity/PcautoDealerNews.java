package cn.com.kuaidian.entity;

import java.util.Date;

import org.gelivable.dao.Column;
import org.gelivable.dao.Entity;
import org.gelivable.dao.Id;
import org.gelivable.dao.Label;

@Entity(tableName = "ft_pcauto_dealer_news")
@Label("经销商文章对照表")
public class PcautoDealerNews {
	@Id
	@Label("经销商文章对照表")
	private long id;
	
	@Label("经销商文章表ID")
	@Column(name = "dealer_news_id")
	private long dealerNewsId;
	
	@Label("Pcauto经销商文章表ID")
	@Column(name = "pc_dealer_news_id")
	private long pcDealerNewsId;
	
	@Label("文章类型：1:商家行情、2:店铺活动、3:品牌新闻")
	private int type;
	
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

	public long getPcDealerNewsId() {
		return pcDealerNewsId;
	}

	public void setPcDealerNewsId(long pcDealerNewsId) {
		this.pcDealerNewsId = pcDealerNewsId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
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
