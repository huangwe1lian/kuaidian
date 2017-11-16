package cn.com.kuaidian.entity;

import java.util.Date;

import org.gelivable.dao.Column;
import org.gelivable.dao.Entity;
import org.gelivable.dao.Id;
import org.gelivable.dao.Label;
import org.gelivable.dao.Refer;


@Entity(tableName="sec_role_city")
@Label("角色-城市")
public class RoleCity {
	@Id
	@Label("角色-城市ID")
	private long id;
	
	@Label("角色ID")
	@Column(name="role_id")
	@Refer(fieldPath = "role.id", type = Role.class)
	private long roleId;

	@Label("省份ID")
	@Column(name="province_id")
	@Refer(fieldPath = "province.id", type = Province.class)
	private long provinceId;

	@Label("城市ID")
	@Column(name="city_id")
	@Refer(fieldPath = "city.id", type = City.class)
	private long cityId;

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

	public long getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(long provinceId) {
		this.provinceId = provinceId;
	}

	public long getCityId() {
		return cityId;
	}

	public void setCityId(long cityId) {
		this.cityId = cityId;
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
