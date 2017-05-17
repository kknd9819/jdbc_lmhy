package com.zz.controller.system;


import com.zz.controller.BaseController;
import com.zz.model.Admin;
import com.zz.service.system.AdminService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;

/**
 * 个人资料controller
 * @Date 2014-12-31
 * @author 欧志辉
 * @version 1.0
 */
@Controller("profileController")
@RequestMapping("/system/profile")
public class ProfileController extends BaseController {

	@Resource(name = "adminServiceImpl")
	private AdminService adminService;

	/**
	 * 验证当前密码
	 * @param currentPassword
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/checkCurrentPassword", method = RequestMethod.GET)
	public boolean checkCurrentPassword(String currentPassword) {
		if (StringUtils.isEmpty(currentPassword)) {
			return false;
		}
		Admin admin = adminService.getCurrent();
		if (StringUtils.equals(DigestUtils.md5Hex(currentPassword), admin.getPassword())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 跳转到修改页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(ModelMap model) {
		model.addAttribute("admin", adminService.getCurrent());
		return "/system/profile/edit";
	}

	/**
	 * 更新
	 * @param currentPassword
	 * @param password
	 * @param email
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(String currentPassword, String password, String email, RedirectAttributes redirectAttributes) {
		Admin pAdmin = adminService.getCurrent();
		if (StringUtils.isNotEmpty(currentPassword) && StringUtils.isNotEmpty(password)) {
			if (!StringUtils.equals(DigestUtils.md5Hex(currentPassword), pAdmin.getPassword())) {
				return ERROR_VIEW;
			}
			pAdmin.setPassword(DigestUtils.md5Hex(password));
		}
		pAdmin.setEmail(email);
		adminService.update(pAdmin);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:edit.jhtml";
	}

}