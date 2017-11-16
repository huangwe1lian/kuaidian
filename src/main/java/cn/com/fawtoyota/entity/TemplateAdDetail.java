package cn.com.fawtoyota.entity;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.gelivable.dao.Column;
import org.gelivable.dao.Entity;
import org.gelivable.dao.Id;
import org.gelivable.dao.Label;
import org.gelivable.dao.Refer;
import org.gelivable.dao.Select;
import org.hibernate.validator.constraints.NotEmpty;

@Entity(tableName = "ft_template_ad_detail")
@Label("模板广告内容表")
public class TemplateAdDetail {
	@Id
	@Label("模板广告详细内容表ID")
	private long id;
	
	@Label("模板广告ID")
	@Column(name = "template_ad_id")
	@NotNull(message="关联模板广告ID不能为空")
	private long templateAdId;
	
	@Label("右侧栏目名称")
	@Column(name = "right_channel_name")
	private String rightChannelName;
	
	@Label("左侧栏目一名称")
	@Column(name = "left_channel_name1")
	private String leftChannelName1;
	
	@Label("左侧栏目一内容")
	@Column(name = "left_channel_txt1")
	private String leftChannelTxt1;
	
	@Label("左侧栏目二名称")
	@Column(name = "left_channel_name2")
	private String leftChannelName2;
	
	@Label("左侧栏目二内容")
	@Column(name = "left_channel_txt2")
	private String leftChannelTxt2;
	
	@Label("左侧栏目三名称")
	@Column(name = "left_channel_name3")
	private String leftChannelName3;
	
	@Label("左侧栏目三内容")
	@Column(name = "left_channel_txt3")
	private String leftChannelTxt3;
	
	@Label("左侧栏目四名称")
	@Column(name = "left_channel_name4")
	private String leftChannelName4;
	
	@Label("左侧栏目四内容")
	@Column(name = "left_channel_txt4")
	private String leftChannelTxt4;
	
	@Label("左侧栏目五名称")
	@Column(name = "left_channel_name5")
	private String leftChannelName5;
	
	@Label("左侧栏目五内容")
	@Column(name = "left_channel_txt5")
	private String leftChannelTxt5;
	
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

	public long getTemplateAdId() {
		return templateAdId;
	}

	public void setTemplateAdId(long templateAdId) {
		this.templateAdId = templateAdId;
	}

	public String getRightChannelName() {
		return rightChannelName;
	}

	public void setRightChannelName(String rightChannelName) {
		this.rightChannelName = rightChannelName;
	}

	public String getLeftChannelName1() {
		return leftChannelName1;
	}

	public void setLeftChannelName1(String leftChannelName1) {
		this.leftChannelName1 = leftChannelName1;
	}

	public String getLeftChannelTxt1() {
		return leftChannelTxt1;
	}

	public void setLeftChannelTxt1(String leftChannelTxt1) {
		this.leftChannelTxt1 = leftChannelTxt1;
	}

	public String getLeftChannelName2() {
		return leftChannelName2;
	}

	public void setLeftChannelName2(String leftChannelName2) {
		this.leftChannelName2 = leftChannelName2;
	}

	public String getLeftChannelTxt2() {
		return leftChannelTxt2;
	}

	public void setLeftChannelTxt2(String leftChannelTxt2) {
		this.leftChannelTxt2 = leftChannelTxt2;
	}

	public String getLeftChannelName3() {
		return leftChannelName3;
	}

	public void setLeftChannelName3(String leftChannelName3) {
		this.leftChannelName3 = leftChannelName3;
	}

	public String getLeftChannelTxt3() {
		return leftChannelTxt3;
	}

	public void setLeftChannelTxt3(String leftChannelTxt3) {
		this.leftChannelTxt3 = leftChannelTxt3;
	}

	public String getLeftChannelName4() {
		return leftChannelName4;
	}

	public void setLeftChannelName4(String leftChannelName4) {
		this.leftChannelName4 = leftChannelName4;
	}

	public String getLeftChannelTxt4() {
		return leftChannelTxt4;
	}

	public void setLeftChannelTxt4(String leftChannelTxt4) {
		this.leftChannelTxt4 = leftChannelTxt4;
	}

	public String getLeftChannelName5() {
		return leftChannelName5;
	}

	public void setLeftChannelName5(String leftChannelName5) {
		this.leftChannelName5 = leftChannelName5;
	}

	public String getLeftChannelTxt5() {
		return leftChannelTxt5;
	}

	public void setLeftChannelTxt5(String leftChannelTxt5) {
		this.leftChannelTxt5 = leftChannelTxt5;
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
