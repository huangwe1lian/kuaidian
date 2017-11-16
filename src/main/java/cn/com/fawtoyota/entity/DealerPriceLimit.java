package cn.com.fawtoyota.entity;

import java.util.Date;

import org.gelivable.dao.Column;
import org.gelivable.dao.Entity;
import org.gelivable.dao.Id;
import org.gelivable.dao.Label;
import org.gelivable.dao.Refer;

@Entity(tableName = "ft_dealer_price_limit")
@Label("经销商报价管控表")
public class DealerPriceLimit {
	@Id
	@Label("经销商报价管控表ID")
	private long id;

	@Label("省份ID")
	@Column(name = "province_id")
	@Refer(fieldPath = "province.id", type = Province.class)
	private long provinceId;
	
	@Label("城市ID")
	@Column(name = "city_id")
	@Refer(fieldPath = "city.id", type = City.class)
	private long cityId;
	
	@Label("车系ID")
	@Column(name = "serial_group_id")
	@Refer(fieldPath = "serialGroup.id", type = SerialGroup.class)
	private long serialGroupId;
	
	@Label("车型ID")
	@Column(name = "model_id")
	@Refer(fieldPath = "model.id", type = Model.class)
	private long modelId;
	
	@Label("优惠价格（单位:元）")
	@Column(name = "discount_price")
	private int discountPrice;
	
	@Label("最低报价（单位:万）")
	@Column(name = "min_price")
	private float minPrice;
	
	@Label("开始时间")
	@Column(name = "begin_time")
	private Date beginTime;
	
	@Label("结束时间")
	@Column(name = "end_time")
	private Date endTime;
	
	@Label("状态（0：待生效，1：生效中，2：已失效）")
	private int status;
	
	@Label("备注说明")
	private int memo;
	
	@Label("创建人ID")
	@Column(name = "create_user_id")
	@Refer(fieldPath = "user.id", type = User.class)
	private long createUserId;

	@Label("更新人ID")
	@Column(name = "update_user_id")
	@Refer(fieldPath = "user.id", type = User.class)
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

	public long getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(long provinceId) {
		this.provinceId = provinceId;
	}

	public long getCityId() {
		return cityId;
	}

	public void setCityId(long cityId) {
		this.cityId = cityId;
	}

	public long getSerialGroupId() {
		return serialGroupId;
	}

	public void setSerialGroupId(long serialGroupId) {
		this.serialGroupId = serialGroupId;
	}

	public long getModelId() {
		return modelId;
	}

	public void setModelId(long modelId) {
		this.modelId = modelId;
	}

	public int getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(int discountPrice) {
		this.discountPrice = discountPrice;
	}

	public float getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(float minPrice) {
		this.minPrice = minPrice;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getMemo() {
		return memo;
	}

	public void setMemo(int memo) {
		this.memo = memo;
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
