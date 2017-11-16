package cn.com.kuaidian.entity;

import java.util.Date;
import org.gelivable.dao.Column;
import org.gelivable.dao.Entity;
import org.gelivable.dao.Id;
import org.gelivable.dao.Label;
import org.gelivable.dao.Refer;

@Entity(tableName="sec_user_role")
@Label("用户-角色表")
public class UserRole {
	@Id
	@Label("用户-角色表ID")
	private long id;
	
	@Label("用户ID")
	@Column(name="user_id")
	@Refer(fieldPath = "user.id", type = User.class)
	private long userId;
	
	@Label("角色ID")
	@Column(name="role_id")
	@Refer(fieldPath = "role.id", type = Role.class)
	private long roleId;
	
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

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
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
