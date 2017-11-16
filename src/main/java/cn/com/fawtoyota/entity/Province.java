package cn.com.fawtoyota.entity;

import java.util.Date;

import org.gelivable.dao.Column;
import org.gelivable.dao.Entity;
import org.gelivable.dao.Id;
import org.gelivable.dao.Label;

@Entity(tableName = "ft_province")
@Label("省份")
public class Province {
	@Id
	@Label("省份ID")
	private long id;

	@Label("省份名称")
	private String name;

	@Label("首字母")
	private String letter;

	@Label("大区ID")
	@Column(name = "area_id")
	private long areaId;
	
	@Label("省会城市ID")
	@Column(name = "capital_city_id")
	private long capitalCityId;

	@Label("Pcauto省份ID")
	@Column(name = "pc_province_id")
	private long pcProvinceId;

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

	public String getLetter() {
		return letter;
	}

	public void setLetter(String letter) {
		this.letter = letter;
	}
	
	public long getAreaId() {
		return areaId;
	}

	public void setAreaId(long areaId) {
		this.areaId = areaId;
	}

	public long getCapitalCityId() {
		return capitalCityId;
	}

	public void setCapitalCityId(long capitalCityId) {
		this.capitalCityId = capitalCityId;
	}

	public long getPcProvinceId() {
		return pcProvinceId;
	}

	public void setPcProvinceId(long pcProvinceId) {
		this.pcProvinceId = pcProvinceId;
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
