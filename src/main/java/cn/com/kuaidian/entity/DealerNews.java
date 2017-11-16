package cn.com.kuaidian.entity;

import java.util.Date;

import org.gelivable.dao.Column;
import org.gelivable.dao.Entity;
import org.gelivable.dao.Id;
import org.gelivable.dao.Label;
import org.gelivable.dao.Refer;
import org.gelivable.dao.Select;

@Entity(tableName = "ft_dealer_news")
@Label("经销商文章表")
public class DealerNews {
	@Id
	@Label("经销商文章表ID")
	private long id;

	@Label("经销商ID")
	@Column(name = "dealer_id")
	@Refer(fieldPath="dealer.id", type=Dealer.class)
	private long dealerId;

	@Label("文章标题")
	private String name;

	@Label("文章短标题,仅行情")
	@Column(name = "short_name")
	private String shortName;

	@Label("文章类型：1:商家行情、2:店铺活动、3:品牌新闻")
	@Refer(fieldPath = "dealerNewsType", type = Select.class)
	private int type;

	@Label("小类型[商家行情(1:裸车,2:加装车,3:含置换优惠,4:含礼包,5:备注说明,6:贷款政策),店铺活动(1:促销活动,2:试驾会,3:自驾游,4:车友聚会,5:车展,6:其它活动")
	@Column(name = "small_type")
	@Refer(fieldPath = "dealerNewsSmallType4Type2", type = Select.class)
	private String smallType; //该字段的类型下拉只适用于活动

	@Label("状态,-2:草稿;-1:审核不通过;0:待审;1:通过;2:已结束(商家行情)")
	@Refer(fieldPath = "dealerNewsStatus", type = Select.class)
	private int status;

	@Label("文章导读图,店铺活动")
	private String pic;

	@Label("行情购买礼包,仅行情")
	@Column(name = "gift_package")
	private String giftPackage;

	@Label("行情审核备注,,仅行情")
	@Column(name = "audit_memo")
	private String auditMemo;

	@Label("行情首次审核通过时间,仅行情")
	@Column(name = "first_pass_time")
	private Date firstPassTime;

	@Label("行情促销说明")
	@Column(name = "news_desc")
	private String newsDesc;

	@Label("促销/活动开始时间")
	@Column(name = "begin_time")
	private Date beginTime;

	@Label("促销/活动结束时间")
	@Column(name = "end_time")
	private Date endTime;

	@Label("是否删除（0:否,1:是）")
	@Refer(fieldPath = "yesOrNo", type = Select.class)
	private int deleted;
	
	@Label("是否隐藏（0:否,1:是）")
	@Refer(fieldPath = "yesOrNo", type = Select.class)
	private int hidden;
	
	@Label("活动级别（1:厂商活动,2:自主活动）")
	@Column(name = "activity_level")
	@Refer(fieldPath = "dealerNewsActivityLevel", type = Select.class)
	private int activityLevel;
	
	@Label("是否推荐（0:否,1:是）")
	@Refer(fieldPath = "yesOrNo", type = Select.class)
	@Column(name = "is_recommend")
	private int isRecommend;

	@Label("推荐人ID")
	@Column(name = "recommend_user_id")
	private long recommendUserId;

	@Label("推荐时间")
	@Column(name = "recommend_time")
	private Date recommendTime;
	
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

	public long getDealerId() {
		return dealerId;
	}

	public void setDealerId(long dealerId) {
		this.dealerId = dealerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getSmallType() {
		return smallType;
	}

	public void setSmallType(String smallType) {
		this.smallType = smallType;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getGiftPackage() {
		return giftPackage;
	}

	public void setGiftPackage(String giftPackage) {
		this.giftPackage = giftPackage;
	}

	public String getAuditMemo() {
		return auditMemo;
	}

	public void setAuditMemo(String auditMemo) {
		this.auditMemo = auditMemo;
	}

	public Date getFirstPassTime() {
		return firstPassTime;
	}

	public void setFirstPassTime(Date firstPassTime) {
		this.firstPassTime = firstPassTime;
	}

	public String getNewsDesc() {
		return newsDesc;
	}

	public void setNewsDesc(String newsDesc) {
		this.newsDesc = newsDesc;
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

	public int getHidden() {
		return hidden;
	}

	public void setHidden(int hidden) {
		this.hidden = hidden;
	}

	public int getActivityLevel() {
		return activityLevel;
	}

	public void setActivityLevel(int activityLevel) {
		this.activityLevel = activityLevel;
	}

	public int getIsRecommend() {
		return isRecommend;
	}

	public void setIsRecommend(int isRecommend) {
		this.isRecommend = isRecommend;
	}

	public long getRecommendUserId() {
		return recommendUserId;
	}

	public void setRecommendUserId(long recommendUserId) {
		this.recommendUserId = recommendUserId;
	}

	public Date getRecommendTime() {
		return recommendTime;
	}

	public void setRecommendTime(Date recommendTime) {
		this.recommendTime = recommendTime;
	}
	
	
	
}
