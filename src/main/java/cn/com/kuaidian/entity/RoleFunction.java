package cn.com.kuaidian.entity;

import java.util.Date;

import org.gelivable.dao.Column;
import org.gelivable.dao.Entity;
import org.gelivable.dao.Id;
import org.gelivable.dao.Label;
import org.gelivable.dao.Refer;


@Entity(tableName="sec_role_function")
@Label("角色-功能表")
public class RoleFunction {
	@Id
	@Label("角色-功能ID")
	private long id;
	
	@Label("角色ID")
	@Column(name="role_id")
	@Refer(fieldPath = "role.id", type = Role.class)
	private long roleId;
	
	@Label("功能ID")
	@Column(name="function_id")
	@Refer(fieldPath = "function.id", type = Function.class)
	private long functionId;
	
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

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public long getFunctionId() {
		return functionId;
	}

	public void setFunctionId(long functionId) {
		this.functionId = functionId;
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
