package cn.com.fawtoyota.entity;

import java.util.Date;

import org.gelivable.dao.Column;
import org.gelivable.dao.Entity;
import org.gelivable.dao.Id;
import org.gelivable.dao.Label;

@Entity(tableName = "ft_dealer_news_ext")
@Label("行情额外信息表")
public class DealerNewsExt {

	@Id
	@Label("行情额外信息表ID")
	private long id;

	@Label("经销商ID")
	@Column(name = "dealer_id")
	private long dealerId;

	@Label("行情文章ID")
	@Column(name = "dealer_news_id")
	private long dealerNewsId;

	@Label("贷款公司名称")
	@Column(name = "loan_company")
	private String loanCompany;

	@Label("贷款最低首付（百分比）")
	@Column(name = "loan_min_pay")
	private int loanMinPay;

	@Label("贷款利率（百分比）")
	@Column(name = "loan_interest")
	private int loanInterest;

	@Label("最高期限（单位:月）")
	@Column(name = "loan_max_month")
	private int loanMaxMonth;

	@Label("贷款说明")
	@Column(name = "loan_desc")
	private String loanDesc;

	@Label("置换补贴降价数额")
	@Column(name = "replace_discount")
	private float replaceDiscount;

	@Label("置换补贴说明")
	@Column(name = "replace_desc")
	private String replaceDesc;

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

	public long getDealerNewsId() {
		return dealerNewsId;
	}

	public void setDealerNewsId(long dealerNewsId) {
		this.dealerNewsId = dealerNewsId;
	}

	public String getLoanCompany() {
		return loanCompany;
	}

	public void setLoanCompany(String loanCompany) {
		this.loanCompany = loanCompany;
	}

	public int getLoanMinPay() {
		return loanMinPay;
	}

	public void setLoanMinPay(int loanMinPay) {
		this.loanMinPay = loanMinPay;
	}

	public int getLoanInterest() {
		return loanInterest;
	}

	public void setLoanInterest(int loanInterest) {
		this.loanInterest = loanInterest;
	}

	public int getLoanMaxMonth() {
		return loanMaxMonth;
	}

	public void setLoanMaxMonth(int loanMaxMonth) {
		this.loanMaxMonth = loanMaxMonth;
	}

	public String getLoanDesc() {
		return loanDesc;
	}

	public void setLoanDesc(String loanDesc) {
		this.loanDesc = loanDesc;
	}

	public float getReplaceDiscount() {
		return replaceDiscount;
	}

	public void setReplaceDiscount(float replaceDiscount) {
		this.replaceDiscount = replaceDiscount;
	}

	public String getReplaceDesc() {
		return replaceDesc;
	}

	public void setReplaceDesc(String replaceDesc) {
		this.replaceDesc = replaceDesc;
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
