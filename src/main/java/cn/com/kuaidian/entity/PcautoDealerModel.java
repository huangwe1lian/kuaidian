package cn.com.kuaidian.entity;

import java.util.Date;

import org.gelivable.dao.Column;
import org.gelivable.dao.Entity;
import org.gelivable.dao.Id;
import org.gelivable.dao.Label;

@Entity(tableName = "ft_pcauto_dealer_model")
@Label("经销商车型报价对照表ID")
public class PcautoDealerModel {
	@Id
	@Label("经销商车型报价对照表ID")
	private long id;

	@Label("经销商报价表ID")
	@Column(name = "dealer_model_id")
	private long dealerModelId;

	@Label("Pcauto经销商报价表ID")
	@Column(name = "pc_dealer_model_id")
	private long pcDealerModelId;

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

	public long getDealerModelId() {
		return dealerModelId;
	}

	public void setDealerModelId(long dealerModelId) {
		this.dealerModelId = dealerModelId;
	}

	public long getPcDealerModelId() {
		return pcDealerModelId;
	}

	public void setPcDealerModelId(long pcDealerModelId) {
		this.pcDealerModelId = pcDealerModelId;
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
