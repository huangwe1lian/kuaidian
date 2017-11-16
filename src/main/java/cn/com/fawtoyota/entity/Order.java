package cn.com.fawtoyota.entity;

import java.util.Date;

import org.gelivable.dao.Column;
import org.gelivable.dao.Entity;
import org.gelivable.dao.Id;
import org.gelivable.dao.Label;
import org.gelivable.dao.Refer;

@Entity(tableName = "ft_order")
@Label("名单线索量表")
public class Order {
	@Id
	@Label("名单线索量ID")
	private long id;
	
	@Label("姓名")
	private String name;
	
	@Label("手机号")
	private String mobile;
	
	@Label("性别（0:女士,1:先生）")
	private int gender;
	
	@Label("经销商ID")
	@Column(name = "dealer_id")
	@Refer(fieldPath = "dealer.id", type = Dealer.class)
	private long dealerId;
	
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
	
	@Label("名单类型（0:未知,1:我要询价,2:置换购车）")
	private int type;
	
	@Label("平台来源（0:未知,1:PC,2:wap）")
	private int origin;
	
	@Label("购车方式（0:全款购车,1:全款置换,2:贷款购车,3:贷款置换）")
	@Column(name = "pay_type")
	private int payType;
	
	@Label("购车时间")
	@Column(name = "buy_time")
	private String buyTime;
	
	@Label("网友IP地址")
	private String ip;
	
	@Label("页面来源")
	private String refer;
	
	@Label("名单报名授权代码")
	@Column(name = "order_code")
	private String orderCode;
	
	@Label("名单状态(0:未处理,1:已处理)")
	private int status;
	
	@Label("商家备注")
	private String dmemo;
	
	@Label("名单处理时间")
	@Column(name = "deal_time")
	private Date dealTime;
	
	@Label("创建时间")
	@Column(name = "create_time")
	private Date createTime;

	@Label("更新时间")
	@Column(name = "update_time")
	private Date updateTime;
	
	@Label("期望价格（单位:万）")
	@Column(name = "expect_price")
	private double expectPrice;
	
	@Label("重复次数")
	@Column(name="repeat")
	private long repeat;

	public long getRepeat() {
		return repeat;
	}

	public void setRepeat(long repeat) {
		this.repeat = repeat;
	}

	public double getExpectPrice() {
		return expectPrice;
	}

	public void setExpectPrice(double expectPrice) {
		this.expectPrice = expectPrice;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public long getDealerId() {
		return dealerId;
	}

	public void setDealerId(long dealerId) {
		this.dealerId = dealerId;
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getOrigin() {
		return origin;
	}

	public void setOrigin(int origin) {
		this.origin = origin;
	}

	public int getPayType() {
		return payType;
	}

	public void setPayType(int payType) {
		this.payType = payType;
	}

	public String getBuyTime() {
		return buyTime;
	}

	public void setBuyTime(String buyTime) {
		this.buyTime = buyTime;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getRefer() {
		return refer;
	}

	public void setRefer(String refer) {
		this.refer = refer;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getDmemo() {
		return dmemo;
	}

	public void setDmemo(String dmemo) {
		this.dmemo = dmemo;
	}

	public Date getDealTime() {
		return dealTime;
	}

	public void setDealTime(Date dealTime) {
		this.dealTime = dealTime;
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
