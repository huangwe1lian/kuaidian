package cn.com.kuaidian.entity;

import java.util.Date;

import org.gelivable.dao.Column;
import org.gelivable.dao.Entity;
import org.gelivable.dao.Id;
import org.gelivable.dao.Label;

@Entity(tableName="ft_user_log")
@Label("后台日志表")
public class UserLog {
	@Id
	@Label("后台日志表ID")
	private long id;
	
	@Label("用户ID")
	@Column(name="user_id")
	private long userId;
	
	@Label("操作类型")
	private String action;
	
	@Label("操作对象ID")
	@Column(name="obj_id")
	private long objId;
	
	@Label("操作对象实体类名")
	@Column(name="obj_name")
	private String objName;
	
	@Label("Ip")
	private String ip;
	
	@Label("页面来源")
	private String referer;
	
	@Label("创建时间")
	@Column(name="create_time")
	private Date createTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public long getObjId() {
		return objId;
	}

	public void setObjId(long objId) {
		this.objId = objId;
	}

	public String getObjName() {
		return objName;
	}

	public void setObjName(String objName) {
		this.objName = objName;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	
}
