package com.zz.controller.system;


import com.zz.controller.BaseController;
import com.zz.model.Menu;
import com.zz.model.MenuValue;
import com.zz.model.basic.model.Message;
import com.zz.service.system.MenuService;
import com.zz.service.system.MenuValueService;
import com.zz.util.shengyuan.StringUtil;
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
 * 菜单权限值controller
 * @Date 2014-01-03
 * @author 欧志辉
 * @version 1.0
 */
@Controller("menuValueController")
@RequestMapping("/system/menuValue")
public class MenuValueController extends BaseController {

	@Resource(name = "menuValueServiceImpl")
	private MenuValueService menuValueService;

	@Resource(name = "menuServiceImpl")
	private MenuService menuService;
	
	/**
	 * 检查菜单值是否唯一
	 * @param id 菜单权限主键
	 * @param vName 菜单权限值
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/checkName", method = RequestMethod.GET)
	public boolean checkName(Long id, String vName) {
		if (StringUtil.isEmpty(vName)) {
			return false;
		}
		return !menuValueService.nameExists(id, vName);
	}

	/**
	 * 跳转到添加页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		return "/admin/menu_value/add";
	}

	/**
	 * 新增菜单权限值
	 * @param menuValue
	 * @param redirectAttributes
	 * @return String
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(MenuValue menuValue, RedirectAttributes redirectAttributes) {
		if (menuValueService.nameExists(menuValue.getId(), menuValue.getvName())) {
			return ERROR_VIEW;
		}
		menuValueService.save(menuValue);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 跳转到编辑页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		
		model.addAttribute("menuvalue", menuValueService.get(id));
		return "/admin/menu_value/edit";
	}

	/**
	 * 编辑保存
	 * @param menuValue
	 * @param redirectAttributes
	 * @return String
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(MenuValue menuValue,RedirectAttributes redirectAttributes) {
		MenuValue pmenuValue = menuValueService.get(menuValue.getId());
		if (pmenuValue == null) {
			return ERROR_VIEW;
		}
		if (!menuValueService.nameExists(menuValue.getId(), menuValue.getvName())) {
			return ERROR_VIEW;
		}
		menuValueService.update(menuValue);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 分页查找菜单权限值列表
	 * @param pageNo
	 * @param model
	 * @return String
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(int pageNo, int pageSize, ModelMap model) {
		model.addAttribute("page", menuValueService.findPage(pageNo, pageSize));
		return "/admin/menu_value/list";
	}

	/**
	 * 删除菜单权限值
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public Message delete(Long[] ids) {
		List<MenuValue> menuValues = new ArrayList<MenuValue>();
		if (ids != null) {
			for (Long id : ids) {
				MenuValue menuValue = menuValueService.get(id);
				List<Menu> menus = menuService.findMenuByMenuValueId(id);
				if (menuValue != null && menus != null && menus.size()>0) {
					return Message.error("该菜单权限值不能被删除",menuValue.getvName());
				}
				menuValues.add(menuValue);
			}
			long totalCount = menuValueService.getAll().size();
			if (ids.length >= totalCount) {
				return Message.error("请至少保留一个菜单权限值");
			}
			menuValueService.batchDelete(menuValues);
		}
		return SUCCESS_MESSAGE;
	}

}