package cn.com.fawtoyota.entity;

import java.util.Date;

import org.gelivable.dao.Column;
import org.gelivable.dao.Entity;
import org.gelivable.dao.Id;
import org.gelivable.dao.Label;
import org.gelivable.dao.Refer;
import org.gelivable.dao.Select;

@Entity(tableName = "ft_media_news")
@Label("媒体资讯表")
public class MediaNews {
	@Id
	@Label("媒体资讯表ID")
	private long id;

	@Label("类型（1:评测,2:导购,3:新闻,4:技术,5:论坛）")
	@Refer(fieldPath = "mediaNewsKind", type = Select.class)
	private int kind;
	
	@Label("文章标题")
	private String name;
	
	@Label("文章URL")
	private String url;
	
	@Label("文章宣传/导读图")
	private String pic;
	
	@Label("来源媒体")
	private String media;
	
	@Label("是否推荐")
	@Column(name = "is_recommend")
	@Refer(fieldPath = "yesOrNo", type = Select.class)
	private int isRecommend;
	
	@Label("推荐开始时间")
	@Column(name = "begin_time")
	private Date beginTime;
	
	@Label("推荐结束时间")
	@Column(name = "end_time")
	private Date endTime;
	
	@Label("状态（0:隐藏,1:不隐藏）")
	@Refer(fieldPath = "mediaNewsStatus", type = Select.class)
	private int status;
	
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

	public int getKind() {
		return kind;
	}

	public void setKind(int kind) {
		this.kind = kind;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getMedia() {
		return media;
	}

	public void setMedia(String media) {
		this.media = media;
	}

	public int getIsRecommend() {
		return isRecommend;
	}

	public void setIsRecommend(int isRecommend) {
		this.isRecommend = isRecommend;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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
