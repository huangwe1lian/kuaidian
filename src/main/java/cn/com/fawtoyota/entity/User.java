package cn.com.fawtoyota.entity;

import java.util.Date;

import org.gelivable.dao.Column;
import org.gelivable.dao.Entity;
import org.gelivable.dao.Id;
import org.gelivable.dao.Label;


@Entity(tableName="ft_user")
@Label("厂商/区域用户表")
public class User {
	@Id
	@Label("厂商/区域用户表ID")
	private long id;
	
	@Label("姓名")
	private String name;
	
	@Label("登录账号")
	@Column(name="login_name")
	private String loginName;
	
	@Label("登录密码")
	@Column(name="login_pwd")
	private String loginPwd;
	
	@Label("账号状态（0:禁用,1:正常）")
	private int status;
	
	@Label("是否删除（0:否,1:是）")
	private int deleted;
	
	@Label("最后登录时间")
	@Column(name="last_login_time")
	private Date lastLoginTime;
	
	@Label("创建时间")
	@Column(name="create_time")
	private Date createTime;
	
	@Label("更新时间")
	@Column(name="update_time")
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getDeleted() {
		return deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
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
