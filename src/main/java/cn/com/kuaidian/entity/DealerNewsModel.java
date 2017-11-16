package cn.com.kuaidian.entity;

import java.util.Date;

import org.gelivable.dao.Column;
import org.gelivable.dao.Entity;
import org.gelivable.dao.Id;
import org.gelivable.dao.Label;

@Entity(tableName = "ft_dealer_news_model")
@Label("行情关联车型表")
public class DealerNewsModel {
	@Id
	@Label("行情关联车型表ID")
	private long id;

	@Label("行情文章ID")
	@Column(name = "dealer_news_id")
	private long dealerNewsId;

	@Label("车型ID")
	@Column(name = "model_id")
	private long modelId;

	@Label("优惠幅度")
	private float discount;

	@Label("促销价")
	@Column(name = "discount_price")
	private float discountPrice;

	@Label("优惠类型(1:金额,2:折扣)")
	@Column(name = "discount_type")
	private int discountType;

	@Label("现车类型（1:有现车;2:预订）")
	private int type;

	@Label("官方价（单位:万）")
	private float price;

	@Label("优惠折扣")
	private float decline;

	@Label("是否含旧车优惠(1:包含,0:不包含)")
	@Column(name = "is_replace")
	private int isReplace;

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

	public long getModelId() {
		return modelId;
	}

	public void setModelId(long modelId) {
		this.modelId = modelId;
	}

	public float getDiscount() {
		return discount;
	}

	public void setDiscount(float discount) {
		this.discount = discount;
	}

	public float getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(float discountPrice) {
		this.discountPrice = discountPrice;
	}

	public int getDiscountType() {
		return discountType;
	}

	public void setDiscountType(int discountType) {
		this.discountType = discountType;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public float getDecline() {
		return decline;
	}

	public void setDecline(float decline) {
		this.decline = decline;
	}

	public int getIsReplace() {
		return isReplace;
	}

	public void setIsReplace(int isReplace) {
		this.isReplace = isReplace;
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
