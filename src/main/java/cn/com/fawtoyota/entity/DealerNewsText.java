package cn.com.fawtoyota.entity;

import org.gelivable.dao.Column;
import org.gelivable.dao.Entity;
import org.gelivable.dao.Id;
import org.gelivable.dao.Label;

@Entity(tableName = "ft_dealer_news_text")
@Label("文章内容表")
public class DealerNewsText {
	@Id
	@Label("文章内容表ID")
	private long id;

	@Label("经销商文章ID")
	@Column(name = "dealer_news_id")
	private long dealerNewsId;

	@Label("文章正文内容")
	private String content;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getDealerNewsId() {
		return dealerNewsId;
	}

	public void setDealerNewsId(long dealerNewsId) {
		this.dealerNewsId = dealerNewsId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
