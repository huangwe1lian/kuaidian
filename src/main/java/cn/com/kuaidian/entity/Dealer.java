package cn.com.kuaidian.entity;

import java.util.Date;

import org.gelivable.dao.Column;
import org.gelivable.dao.Entity;
import org.gelivable.dao.Id;
import org.gelivable.dao.Label;
import org.gelivable.dao.Refer;

@Entity(tableName = "ft_dealer")
@Label("经销商")
public class Dealer {
	@Id
	@Label("经销商ID")
	private long id;
	
	@Label("经销商代码,一汽丰田的经销商ID")
	@Column(name = "dealer_code")
	private String dealerCode;
	
	@Label("经销商名称")
	private String name;

	@Label("经销商简称")
	@Column(name = "short_name")
	private String shortName;

	@Label("登陆账号")
	@Column(name = "login_name")
	private String loginName;

	@Label("登陆密码")
	@Column(name = "login_pwd")
	private String loginPwd;

	@Label("省份ID")
	@Column(name = "province_id")
	@Refer(fieldPath = "province.id", type = Province.class)
	private long provinceId;

	@Label("城市ID")
	@Column(name = "city_id")
	@Refer(fieldPath = "city.id", type = City.class)
	private long cityId;

	@Label("县级市/区ID")
	@Column(name = "region_id")
	private long regionId;

	@Label("联系电话")
	private String phone;

	@Label("邮箱")
	private String email;

	@Label("地址")
	private String address;

	@Label("代理服务")
	@Column(name = "agent_service")
	private String agentService;

	@Label("百度地图:经度坐标值")
	@Column(name = "map_lon")
	private String mapLon;

	@Label("百度地图:纬度坐标值")
	@Column(name = "map_lat")
	private String mapLat;

	@Label("排序值")
	private int seq;
	
	@Label("是否隐藏（0:否,1:是）")
	private int hidden;

	@Label("是否删除,0:否,1:是")
	private int deleted;

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

	public String getDealerCode() {
		return dealerCode;
	}

	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginPwd() {
		return loginPwd;
	}

	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
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

	public long getRegionId() {
		return regionId;
	}

	public void setRegionId(long regionId) {
		this.regionId = regionId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAgentService() {
		return agentService;
	}

	public void setAgentService(String agentService) {
		this.agentService = agentService;
	}

	public String getMapLon() {
		return mapLon;
	}

	public void setMapLon(String mapLon) {
		this.mapLon = mapLon;
	}

	public String getMapLat() {
		return mapLat;
	}

	public void setMapLat(String mapLat) {
		this.mapLat = mapLat;
	}
	
	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public int getHidden() {
		return hidden;
	}

	public void setHidden(int hidden) {
		this.hidden = hidden;
	}

	public int getDeleted() {
		return deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
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
