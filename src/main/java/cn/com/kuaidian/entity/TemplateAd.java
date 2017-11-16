package cn.com.kuaidian.entity;

import java.util.Date;

import org.gelivable.dao.Column;
import org.gelivable.dao.Entity;
import org.gelivable.dao.Id;
import org.gelivable.dao.Label;
import org.gelivable.dao.Refer;
import org.gelivable.dao.Select;

@Entity(tableName = "ft_template_ad")
@Label("模板广告表")
public class TemplateAd {
	@Id
	@Label("模板广告表ID")
	private long id;
	
	@Label("广告标题")
	private String name;
	
	@Label("广告图")
	private String pic;
	
	@Label("排序值(数字越大，排序越靠前)")
	private int seq;
	
	@Label("审核状态（0:待审,1:审核通过,-1:审核不通过）")
	@Column(name = "audit_status")
	@Refer(fieldPath = "auditStatus", type = Select.class)
	private int auditStatus;
	
	@Label("审核备注")
	@Column(name = "audit_memo")
	private String auditMemo;
	
	@Label("审核时间")
	@Column(name = "audit_time")
	private Date auditTime;
	
	@Label("审核人ID")
	@Column(name = "audit_user_id")
	@Refer(fieldPath = "user.id", type = User.class)
	private long auditUserId;
	
	@Label("广告状态（0:审核中,1:待上架,2:上架,3:下架,4:结束）")
	@Refer(fieldPath = "adStatus", type = Select.class)
	private int status;
	
	@Label("广告开始时间")
	@Column(name = "begin_time")
	private Date beginTime;
	
	@Label("广告结束时间")
	@Column(name = "end_time")
	private Date endTime;
	
	@Label("名单报名授权code")
	@Column(name = "order_code")
	private String orderCode;
	
	@Label("是否需要收集线索(0:否，1:是)")
	@Refer(fieldPath = "yesOrNo", type = Select.class)
	@Column(name = "is_order")
	private int isOrder;
	
	@Label("创建人ID")
	@Column(name = "create_user_id")
	@Refer(fieldPath = "user.id", type = User.class)
	private long createUserId;

	@Label("更新人ID")
	@Column(name = "update_user_id")
	@Refer(fieldPath = "user.id", type = User.class)
	private long updateUserId;

	@Label("创建时间")
	@Column(name = "create_time")
	private Date createTime;

	@Label("更新时间")
	@Column(name = "update_time")
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

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public int getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(int auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getAuditMemo() {
		return auditMemo;
	}

	public void setAuditMemo(String auditMemo) {
		this.auditMemo = auditMemo;
	}

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public long getAuditUserId() {
		return auditUserId;
	}

	public void setAuditUserId(long auditUserId) {
		this.auditUserId = auditUserId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public int getIsOrder() {
		return isOrder;
	}

	public void setIsOrder(int isOrder) {
		this.isOrder = isOrder;
	}

	public long getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(long createUserId) {
		this.createUserId = createUserId;
	}

	public long getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(long updateUserId) {
		this.updateUserId = updateUserId;
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
