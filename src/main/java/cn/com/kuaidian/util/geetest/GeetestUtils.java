package cn.com.kuaidian.util.geetest;

import javax.servlet.http.HttpServletRequest;


/** 
 * 极验校验工具类
 * @date 创建时间：2016-5-31 上午9:47:54
 * @version 1.0
 */
public class GeetestUtils {
	
	// 填入自己的captcha_id和private_key
	private static final String captcha_id = "b46d1900d0a894591916ea94ea91bd2c";
	private static final String private_key = "36fc3fe98530eea08dfc6ce76e3d24c4";

	public static final String getCaptcha_id() {
		return captcha_id;
	}

	public static final String getPrivate_key() {
		return private_key;
	}

	public static int verify(HttpServletRequest request){
		GeetestLib gtSdk = new GeetestLib(getCaptcha_id(), getPrivate_key());
		
		String challenge = request.getParameter(GeetestLib.fn_geetest_challenge);
		String validate = request.getParameter(GeetestLib.fn_geetest_validate);
		String seccode = request.getParameter(GeetestLib.fn_geetest_seccode);
		
		//从session中获取gt-server状态
		int gt_server_status_code = (Integer) request.getSession().getAttribute(gtSdk.gtServerStatusSessionKey);
		
		int gtResult = 0;

		if (gt_server_status_code == 1) {
			//gt-server正常，向gt-server进行二次验证
			gtResult = gtSdk.enhencedValidateRequest(challenge, validate, seccode);
		} else {
			// gt-server非正常情况下，进行failback模式验证
			System.out.println("failback:use your own server captcha validate");
			gtResult = gtSdk.failbackValidateRequest(challenge, validate, seccode);
		}
		
		return gtResult;
	}
}
