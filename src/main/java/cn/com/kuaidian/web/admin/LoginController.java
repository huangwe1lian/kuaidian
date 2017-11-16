package cn.com.kuaidian.web.admin;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gelivable.dao.GeliUtils;
import org.gelivable.web.Env;
import org.gelivable.web.EnvUtils;
import org.gelivable.webmvc.JSONBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.com.kuaidian.entity.User;
import cn.com.kuaidian.resource.auth.AdminSecurity;
import cn.com.kuaidian.resource.log.LogFacade;
import cn.com.kuaidian.service.admin.UserService;
import cn.com.kuaidian.util.Cryptor;

/** 
 * 
 * @author  作者： Lin Ruichang E-mail:
 * @date 创建时间：2016-5-31 上午10:46:59
 * @version 1.0
 */
@Controller
@RequestMapping("/admin")
public class LoginController {
	
	@Autowired
	UserService userService;
	
	@RequestMapping("/loginCheck.do")
	public void loginCheck(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//int gtResult = GeetestUtils.verify(request);
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		try{
			long userid = userService.findUserId(username, Cryptor.encode(password, AdminSecurity.passwordKey, Cryptor.DES));
			if(userid > 0){
				AdminSecurity.saveSession(userid,request,response);
		      	response.sendRedirect("/admin/index.jsp");
			} else {
				response.sendRedirect("/admin/login.jsp");
			}
		}catch(Exception e){
			e.printStackTrace();
			response.sendRedirect("msg.jsp?code=login_fail");
		}
	}
	
	@RequestMapping("/logout.do")
	public String logout(HttpServletRequest request, HttpServletResponse response) throws IOException{
		AdminSecurity.clearSession(request, response);
		response.sendRedirect("/admin/login.jsp");
		return null;
		
	}
	
	@RequestMapping(value="/updatePwd.do", method = RequestMethod.GET)
    public String showUpdatePwd(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        return "admin/user/user/updatePwd";
    }
	
	@RequestMapping(value="/updatePwd.do", method = RequestMethod.POST)
	public String doUpdatePwd(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Env env = EnvUtils.getEnv();
		User user = AdminSecurity.getCurrentUser(req);
		String newPwd = env.param("newPwd");
		if(!"".equals(newPwd)){
			user.setLoginPwd(Cryptor.encode(newPwd, AdminSecurity.passwordKey, Cryptor.DES));
			LogFacade.log(user);
			GeliUtils.getDao().update(user);
		}
		
		AdminSecurity.clearSession(req, resp);
		resp.setCharacterEncoding("UTF-8");
        resp.getWriter().println(new JSONBuilder()
				        .put("statusCode", 200)
				        .put("message", "操作成功")
				        .put("callbackType", "redirect")
				        .put("redirectUrl", "/admin/login.jsp")
				        .toString());
		return null;
	}
}
