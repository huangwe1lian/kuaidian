package cn.com.kuaidian.entity;

import java.util.Date;

import org.gelivable.dao.Column;
import org.gelivable.dao.Entity;
import org.gelivable.dao.Id;
import org.gelivable.dao.Label;

@Entity(tableName = "ft_city")
@Label("城市")
public class City {
	@Id
	@Label("城市ID")
	private long id;

	@Label("城市名称")
	private String name;

	@Label("省份Id")
	@Column(name = "province_id")
	private long provinceId;

	@Label("首字母")
	private String letter;

	@Label("中文拼音")
	private String pinyin;

	@Label("该城市的经销商数量")
	@Column(name = "dealer_total")
	private int dealerTotal;

	@Label("Pcauto省份ID")
	@Column(name = "pc_province_id")
	private long pcProvinceId;

	@Label("Pcauto城市ID")
	@Column(name = "pc_city_id")
	private long pcCityId;

	@Label("城市编号")
	@Column(name = "city_code")
	private String cityCode;

	@Label("排序")
	private int seq;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(long provinceId) {
		this.provinceId = provinceId;
	}

	public String getLetter() {
		return letter;
	}

	public void setLetter(String letter) {
		this.letter = letter;
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public int getDealerTotal() {
		return dealerTotal;
	}

	public void setDealerTotal(int dealerTotal) {
		this.dealerTotal = dealerTotal;
	}

	public long getPcProvinceId() {
		return pcProvinceId;
	}

	public void setPcProvinceId(long pcProvinceId) {
		this.pcProvinceId = pcProvinceId;
	}

	public long getPcCityId() {
		return pcCityId;
	}

	public void setPcCityId(long pcCityId) {
		this.pcCityId = pcCityId;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
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
