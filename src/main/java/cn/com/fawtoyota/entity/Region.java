package cn.com.fawtoyota.entity;

import java.util.Date;

import org.gelivable.dao.Column;
import org.gelivable.dao.Entity;
import org.gelivable.dao.Id;
import org.gelivable.dao.Label;

@Entity(tableName = "ft_region")
@Label("县级市/区")
public class Region {
	@Id
	@Label("区ID")
	private long id;

	@Label("区名称")
	private String name;

	@Label("城市ID")
	@Column(name = "city_id")
	private long cityId;

	@Label("Pcauto县市级/区ID")
	@Column(name = "pc_region_id")
	private long pcRegionId;

	@Label("首字母")
	private String letter;

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

	public long getCityId() {
		return cityId;
	}

	public void setCityId(long cityId) {
		this.cityId = cityId;
	}

	public long getPcRegionId() {
		return pcRegionId;
	}

	public void setPcRegionId(long pcRegionId) {
		this.pcRegionId = pcRegionId;
	}

	public String getLetter() {
		return letter;
	}

	public void setLetter(String letter) {
		this.letter = letter;
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
