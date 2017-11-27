package cn.com.kuaidian.entity;

import org.gelivable.dao.Column;
import org.gelivable.dao.Entity;
import org.gelivable.dao.Id;
import org.gelivable.dao.Label;

@Entity(tableName="kd_cuisine")
public class Cuisine {
	@Id
	@Label("菜式表ID")
	@Column(name="id")
	private long id;
	
	@Label("菜式名称")
	@Column(name="name")
	private String name;

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
	
}
