package cn.com.kuaidian.alipay;

public class AlipayParams { 
	private String out_trade_no; //订单号
	private String total_amount; //价格
	private String subject;      //订单名
	private String product_code; //商品编号

	private String overTime;     //超时时间
	private String notify_url;   //支付完成跳转url
	private String return_url;   //支付完成回调url
	
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public String getTotal_amount() {
		return total_amount;
	}
	public void setTotal_amount(String total_amount) {
		this.total_amount = total_amount;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getProduct_code() {
		return product_code;
	}
	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}
	public String getOverTime() {
		return overTime;
	}
	public void setOverTime(String overTime) {
		this.overTime = overTime;
	}
	public String getNotify_url() {
		return notify_url;
	}
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
	public String getReturn_url() {
		return return_url;
	}
	public void setReturn_url(String return_url) {
		this.return_url = return_url;
	}
	
	
	public AlipayParams(String out_trade_no, String total_amount,
			String subject, String product_code, String overTime) {
		super();
		this.out_trade_no = out_trade_no;
		this.total_amount = total_amount;
		this.subject = subject;
		this.product_code = product_code;
		this.overTime = overTime;
	}
	
	
	
}
