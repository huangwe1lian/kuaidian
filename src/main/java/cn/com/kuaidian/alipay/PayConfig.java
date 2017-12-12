package cn.com.kuaidian.alipay;

public class PayConfig { 
	//先写死,可改为读取配置文件
	
	/**********************沙箱环境******************************/
	public static final String APPID="2016082700323804";
	//私钥 pkcs8格式的
	public static final String RSA_PRIVATE_KEY="MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDjIH412dZUjZCAH/FQdP7bSUafy4vKalSoRgl/fR9ZZ8zo+5WbPJDioLsbugLXex0a/VnlznucabWuwxwPAiGrkrCxa5+iay6RWclD7AOUtWYHiXCPMdne5Yrc0bjOQNqSbPhwKB6mUtyo9PVaPXhWuUXLhKiou75alWSfxKeV/3fM4OMvzGNo9dIpcOeGOGYAMo6m3aI3X1OBtwThbKxWHKPJNiGG/LSag3Qruw7s6NpjpUnCJ/qRUgFiKvQubD4ChBkMpB1zhlWPXqzBY4RqGVTPLfj6utT2GHrGie8uWE90UPPzSoaFn3zy+jE9AFdEn0W9lWoS5UzAaxDOLDpTAgMBAAECggEBAMY75ew6ZMcHh8dMGeKPOqFKI0n07d2NULZN6pxLKMFvx3jRnJIRGqOyhZx9jV1lJtIXrg7VWS37VYdi42p2Ac0uCunPdVTdF5flNbUCmV2Btbt+Sa+xYucYkUqKATKDiEGkunGpkpa4W79bjYllfVK0AibPciVTN6D4IwsPKPXQjkfzSp+sP9yxMjYCzqJ/NE38afXoafdSRWPATL38FZTVt9BJCsxHVek5/k+CuLoMtQasXTZjdhRuVvzYL81VSQXg0GkDMKlcLSeExrRC7ApF1H8b0njVa31QoXPXAbMOrTTOGnCVqswzT0ZWT1Bd+t2Le4dCmr0J3/SZ6qGA6gECgYEA9qgOXVuRK6UhkLDZhyZ+HIgfvQaR6jTE2BCXBFAIgCcjqXGnvBCSc4g21MFNHSqQehXOK3EWWyen2pnQ8Q0BGvSEDAZBa+A+/UA/zi26JrUWYhG6IhUXpdG6oVUbpPjhXG0FDeP2apmW7rE2uzBX2Sj1jrTHmTZW1uMNq6pmtsMCgYEA67sMupdVW2LLhkEk2pXx5XHXIizaOFVmS12fP4T2qtrIOkLt31D0TixdfRKiCdWDDxXDxEroUgpVh+e4Q42q6VwnDJ932LBdqFnIFoi76UjNiKp5j6A2rZcZwVzXYt5jHNUMYqoS5P1xVsnwDEEEGeBXwR5wJKUcZtVxl5KR1TECgYBgJytSYXu6E+l9yjNCaFMJNFeLDKCdOGzz/aOhXsGxk5BnYZMN0TgGKbeWQLSbBhjEPvPcI32fk1nv8b5t+a/QnjlLprSiJQzpFL7K1TCYHkfXeymgV7CcR6IFenQIX60aprUtsQxCihU4f4Tie/oDAeCX/QlCRNiddG3D2e2omQKBgQDMpWtshbq9hmxIFj/nX+tLlOP+sE+WPlV7MkDxBP6b3ymaosYdbAtb3lofHdD8lpabvvpga+N6baxJOxqV0uRAiRcBd2O1A4+beq4hGf2AjgqZDd5QmPc/BoF+g5odJBGJU0/YS+vxTjFZ9h/mk3768+9bIZCbz5dzkvN1cfIW0QKBgGUZG1sqqqbeOzJuOxeiKKCE0VYnT1ueTtjbPgLiKAH20xb0O7WI/qgCUtF+1Xe89RMvRUmOCSwVkRVklhaQDo8Hi/yre2tP+dOofNCqmorjbzK0xWuaPUMEkt+2mkaS2XptqwWynjoF41a+GHgwZVG9cInjpjXOaMvpqGvx/dyM";
	public static final String URL="https://openapi.alipaydev.com/gateway.do";
	public static final String ALIPAY_PUBLIC_KEY="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApYhdn5hbNTCB6xoBGt24t8ULxO7VtfRy0p4myyklXhZVBjoai7eVufbYygVl4bakxoiuLv6BcbFoGAlPYn+hMvuYaJtzq2aEWv2loHJ6466ktqd3rIDBKPyVtbNCDixSd/OStiMiHTldX+D/Kd1Q/8dioDWAONhkwLBKNuzCSctnysIxdlVZ/1QjvX02kbxIqzR5Uoo1X2ni2FhT+tTz+AaA49zGdd6U9KBEPpvxN4oDJ7U1vOFCVJ8wILwiHICJ1B9BzaskQtbDQ/zkwhYA2Qa5hJcroW0XDjwqf5vapkPaXIRvI2Ij12bpVWej9DCeUR5lyPCygXe+I/lwbhu2qwIDAQAB";	
	/**********************沙箱环境******************************/
	public static final String CHARSET="UTF-8";  //编码
	public static final String FORMAT="json";  // 返回格式
	public static final String SIGN_TYPE="RSA2";  //签名方式
	
	
}
