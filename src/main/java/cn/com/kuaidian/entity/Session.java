package cn.com.kuaidian.entity;

import java.util.Date;

import org.gelivable.dao.Column;
import org.gelivable.dao.Entity;
import org.gelivable.dao.Id;
import org.gelivable.dao.Label;


@Entity(tableName="sec_session")
@Label("登录session记录表")
public class Session {
	@Id
	@Label("登录session记录表ID")
	private long id;
	
	@Label("用户ID")
	@Column(name="user_id")
	private long userId;
	
	@Label("sessionId")
	@Column(name="session_id")
	private String sessionId;
	
	@Label("ip")
	private String ip;
	
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

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	
}
