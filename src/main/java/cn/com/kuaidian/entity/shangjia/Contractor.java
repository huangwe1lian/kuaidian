package cn.com.kuaidian.entity.shangjia;

import org.gelivable.dao.Column;
import org.gelivable.dao.Entity;
import org.gelivable.dao.Id;
import org.gelivable.dao.Label;
@Entity(tableName="kd_contractor")
public class Contractor {
	
	@Id
	@Label("供应商表ID")
	@Column(name="id")
	private long id;
	
	@Label("用户名")
	@Column(name="username")
	private String username;
	
	@Label("密码")
	@Column(name="password")
	private String password;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
