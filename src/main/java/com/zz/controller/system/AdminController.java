package com.zz.controller.system;


import com.zz.controller.BaseController;
import com.zz.model.Admin;
import com.zz.model.basic.model.Message;
import com.zz.model.basic.model.Page;
import com.zz.service.system.AdminService;
import com.zz.service.system.RoleService;
import com.zz.util.shengyuan.Pageable;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 后台管理员controller
 * @Date 2014-12-31
 * @author 欧志辉
 * @version 1.0
 */
@Controller("adminController")
@RequestMapping("/system/admin")
public class AdminController extends BaseController {
	
	@Resource(name = "adminServiceImpl")
	private AdminService adminService;
	
	@Resource(name = "roleServiceImpl")
	private RoleService roleService;

	/**
	 * 跳转管理员列表
	 * @param pageable
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, ModelMap model) {
		Page<Admin> page = adminService.findPage(pageable);
		model.addAttribute("page", page);
		model.addAttribute("pageable", pageable);
		return "/system/admin/list";
	}
	
	/**
	 * 判断用户名是否存在
	 * @param username
	 * @return boolean
	 */
	@ResponseBody
	@RequestMapping(value = "/checkUsername", method = RequestMethod.GET)
	public boolean checkUsername(String username) {
		if (StringUtils.isEmpty(username)) {
			return false;
		}
		if (adminService.usernameExists(username)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 跳转到新增管理员页面
	 * @param model
	 * @return String
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		model.addAttribute("roles", roleService.getAll());
		return "/system/admin/add";
	}

	/**
	 * 新增管理员
	 * @param admin
	 * @param roleIds
	 * @param redirectAttributes
	 * @return String
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Admin admin, Long[] roleIds, RedirectAttributes redirectAttributes) {
		if (adminService.usernameExists(admin.getUsername())) {
			return ERROR_VIEW;
		}
		admin.setPassword(DigestUtils.md5Hex(admin.getPassword()));
		admin.setIsLocked(false);
		admin.setLoginFailureCount(0);
		admin.setLockedDate(null);
		admin.setLoginDate(null);
		admin.setLoginIp(null);
		adminService.saveAdmin(admin, roleIds);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 跳转到编写管理员页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		model.addAttribute("roles", roleService.getAll());
		model.addAttribute("hasRoleIds", roleService.getRoleIdsByAdminId(id));
		model.addAttribute("admin", adminService.get(id));
		return "/system/admin/edit";
	}

	/**
	 * 修改管理员
	 * @param propertyAdmin
	 * @param roleIds
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Admin propertyAdmin, Long[] roleIds, RedirectAttributes redirectAttributes) {
		
		Admin admin = adminService.get(propertyAdmin.getId());
		if (admin == null) {
			return ERROR_VIEW;
		}
		if (StringUtils.isNotEmpty(propertyAdmin.getPassword())) {
			admin.setPassword(DigestUtils.md5Hex(propertyAdmin.getPassword()));
		}
		admin.setName(propertyAdmin.getName());
		admin.setIsEnabled(propertyAdmin.getIsEnabled());
		if(null!=propertyAdmin.getIsLocked())
			admin.setIsLocked(propertyAdmin.getIsLocked());
		admin.setEmail(propertyAdmin.getEmail());
		adminService.updateAdmin(admin, roleIds);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 根据ID删除管理员列表
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public Message delete(Long[] ids) {
		if (ids.length >= adminService.getAll().size()) {
			return Message.error("请至少保留一个管理员！");
		}
		List<Admin> admins = new ArrayList<Admin>();
		for (Long id : ids) {
			admins.add(new Admin(id));
		}
		adminService.batchDelete(admins);
		return SUCCESS_MESSAGE;
	}

}