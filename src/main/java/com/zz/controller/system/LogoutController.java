package com.zz.controller.system;

import com.zz.controller.BaseController;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 后台管理员注销controller
 * @Date 2014-12-31
 * @author 欧志辉
 * @version 1.0
 */
@Controller("logoutController")
@RequestMapping("/system/logout")
public class LogoutController extends BaseController {
	
	/**
	 * 注销
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		Subject currentUser = SecurityUtils.getSubject();
		if (currentUser != null) {  
            currentUser.logout();  
        }  
        HttpSession session = request.getSession(false);
        if( session != null ) {  
            session.invalidate();  
        }  
		return "redirect:/";
	}
}