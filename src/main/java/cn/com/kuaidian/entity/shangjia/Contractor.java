package cn.com.kuaidian.entity.shangjia;

import org.gelivable.dao.Entity;
import org.gelivable.dao.Id;
import org.gelivable.dao.Label;
@Entity
public class Contractor {
	@Id
	@Label("供应商表ID")
	private long id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
