package cn.com.kuaidian.web.common;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.kuaidian.util.geetest.GeetestLib;
import cn.com.kuaidian.util.geetest.GeetestUtils;


/** 
 * geetest验证码
 * @author  作者： Lin Ruichang E-mail:
 * @date 创建时间：2016-5-30 下午5:42:06
 * @version 1.0
 */
@Controller
public class GeetestController {
	@RequestMapping(value="/geetest.do")
	public void captcha(HttpServletRequest request, HttpServletResponse response) throws IOException{
		GeetestLib gtSdk = new GeetestLib(GeetestUtils.getCaptcha_id(), GeetestUtils.getPrivate_key());

        String resStr = "{}";
        int gtServerStatus = gtSdk.preProcess();
        //将服务器状态设置到session中
        request.getSession().setAttribute(gtSdk.gtServerStatusSessionKey, gtServerStatus);
        resStr = gtSdk.getResponseStr();
        PrintWriter out = response.getWriter();
        out.println(resStr);
	}
}
