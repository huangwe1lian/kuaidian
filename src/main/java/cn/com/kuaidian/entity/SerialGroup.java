package cn.com.kuaidian.entity;

import java.util.Date;

import org.gelivable.dao.Column;
import org.gelivable.dao.Entity;
import org.gelivable.dao.Id;
import org.gelivable.dao.Label;
import org.gelivable.dao.Refer;

@Entity(tableName="ft_serial_group")
@Label("车系")
public class SerialGroup {
	@Id
	@Label("车系ID")
	private long id;
	
	@Label("车系名称")
	private String name;
	
	@Label("车系英文名称")
	private String ename;
	
	@Label("车系封面图")
	private String logo;
	
	@Label("在售状态 0:未上市,1:在售,2:停售")
	private int status;
	
	@Label("是否删除,0:否,1:是")
	private int deleted;
	
	@Label("创建时间")
	@Column(name = "create_time")
	private Date createTime;
	
	@Label("更新时间")
	@Column(name = "update_time")
	private Date updateTime;
	
	@Label("热门推荐车系图1")
	private String logo1;
	
	@Label("热门推荐车系图2")
	private String logo2;
	
	@Label("导航排序值")
	@Column(name = "nav_seq")
	private long navSeq;
	
	@Label("首页热门车系排序值")
	@Column(name = "hot_seq")
	private long hotSeq;
	
	@Label("是否隐藏（0:否,1:是）")
	private int hidden;
	
	@Label("更新人ID")
	@Column(name = "update_user_id")
	@Refer(fieldPath = "user.id", type = User.class)
	private long updateUserId;
	
	@Label("推荐车型ID")
	@Column(name = "hot_model_id")
	private long hotModelId;

	public long getHotModelId() {
		return hotModelId;
	}

	public void setHotModelId(long hotModelId) {
		this.hotModelId = hotModelId;
	}

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

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
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

	public String getLogo1() {
		return logo1;
	}

	public void setLogo1(String logo1) {
		this.logo1 = logo1;
	}

	public String getLogo2() {
		return logo2;
	}

	public void setLogo2(String logo2) {
		this.logo2 = logo2;
	}

	public long getNavSeq() {
		return navSeq;
	}

	public void setNavSeq(long navSeq) {
		this.navSeq = navSeq;
	}

	public long getHotSeq() {
		return hotSeq;
	}

	public void setHotSeq(long hotSeq) {
		this.hotSeq = hotSeq;
	}

	public int getHidden() {
		return hidden;
	}

	public void setHidden(int hidden) {
		this.hidden = hidden;
	}

	public long getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(long updateUserId) {
		this.updateUserId = updateUserId;
	}

}
