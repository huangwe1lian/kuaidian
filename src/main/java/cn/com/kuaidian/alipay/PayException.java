package cn.com.kuaidian.alipay;

public class PayException extends Exception { 
	private String msg;
	private int code;
	 
	public PayException(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	 
}
