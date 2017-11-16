package cn.com.fawtoyota.entity;

import java.util.Date;

import org.gelivable.dao.Column;
import org.gelivable.dao.Entity;
import org.gelivable.dao.Id;
import org.gelivable.dao.Label;


@Entity(tableName="ft_user_log_detail")
@Label("后台日志内容表")
public class UserLogDetail {
	@Id
	@Label("后台日志内容表ID")
	private long id;
	
	@Label("后台日志表ID")
	@Column(name="user_log_id")
	private long userLogId;
	
	@Label("操作前内容")
	@Column(name="bef_content")
	private String befContent;
	
	@Label("操作后内容")
	@Column(name="aft_content")
	private String aftContent;
	
	@Label("创建时间")
	@Column(name="create_time")
	private Date createTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserLogId() {
		return userLogId;
	}

	public void setUserLogId(long userLogId) {
		this.userLogId = userLogId;
	}

	public String getBefContent() {
		return befContent;
	}

	public void setBefContent(String befContent) {
		this.befContent = befContent;
	}

	public String getAftContent() {
		return aftContent;
	}

	public void setAftContent(String aftContent) {
		this.aftContent = aftContent;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
}
