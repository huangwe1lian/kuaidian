package cn.com.kuaidian.entity;

import java.util.Date;

import org.gelivable.dao.Column;
import org.gelivable.dao.Entity;
import org.gelivable.dao.Id;
import org.gelivable.dao.Label;

@Entity(tableName = "ft_media_news_serial_group")
@Label("媒体资讯车系关联表")
public class MediaNewsSerialGroup {
	@Id
	@Label("媒体资讯车系关联表ID")
	private long id;

	@Label("媒体资讯ID")
	@Column(name = "media_news_id")
	private long mediaNewsId;
	
	@Label("车系ID")
	@Column(name = "serial_group_id")
	private long serialGroupId;
	
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

	public long getMediaNewsId() {
		return mediaNewsId;
	}

	public void setMediaNewsId(long mediaNewsId) {
		this.mediaNewsId = mediaNewsId;
	}

	public long getSerialGroupId() {
		return serialGroupId;
	}

	public void setSerialGroupId(long serialGroupId) {
		this.serialGroupId = serialGroupId;
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
