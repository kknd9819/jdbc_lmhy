package com.zz.controller.system;


import com.zz.controller.BaseController;
import com.zz.model.Menu;
import com.zz.model.MenuValue;
import com.zz.model.basic.model.Message;
import com.zz.model.vo.MenuMenuValue;
import com.zz.service.system.MenuService;
import com.zz.service.system.MenuValueService;
import com.zz.util.shengyuan.Pageable;
import com.zz.util.shengyuan.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 菜单controller
 * @Date 2014-12-31
 * @author 欧志辉
 * @version 1.0
 */
@Controller("menuController")
@RequestMapping("/system/menu")
public class MenuController extends BaseController {

	@Resource(name = "menuServiceImpl")
	private MenuService menuService;
	
	@Resource(name = "menuValueServiceImpl")
	private MenuValueService menuValueService;
	
	/**
	 * 分页查询菜单列表
	 * @param pageable
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, ModelMap model) {
		
		model.addAttribute("page", menuService.findRootsForPage(pageable));
		return "/system/menu/list";
	}
	
	/**
	 * 查找菜单子类
	 * @param id
	 * @return Map<String, Object>
	 */
	@ResponseBody
	@RequestMapping(value = "/zllist", method = RequestMethod.GET)
	public Map<String, Object> zllist(Long id) {
		
		Map<String, Object> data = new HashMap<String, Object>();
		List<MenuMenuValue> menus = menuService.findChildren(id);
		data.put("message", SUCCESS_MESSAGE);
		data.put("menus", menus);
		return data;
	}

	/**
	 * 跳转到菜单添加页面
	 * @param model
	 * @return String
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		model.addAttribute("menuTree", menuService.genereateMenuTree(null));
		return "/system/menu/add";
	}

	/**
	 * 新增菜单保存
	 * @param menu
	 * @param vName
	 * @param parentId
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Menu menu, String vName, Long parentId, RedirectAttributes redirectAttributes) {
		menu.setParent(parentId);
		MenuValue menuValue = new MenuValue();
		if(!StringUtil.isEmpty(vName)){
			if (menuValueService.nameExists(menu.getMenuValue(), vName)) {
				return ERROR_VIEW;
			}
			menuValue.setCreateDate(new Date());
			menuValue.setModifyDate(new Date());
			menuValue.setvName(vName);
			Long menuValueId = menuValueService.save(menuValue);
			menu.setMenuValue(menuValueId);
		}
		if(parentId != null){
			Menu parentMenu = menuService.get(parentId);
			menu.setFullName(parentMenu.getFullName() + menu.getName());
			menu.setGrade(parentMenu.getGrade()+1);
		}else{
			menu.setFullName(menu.getName());
			menu.setGrade(0);
		}
		menuService.saveMenu(menu);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 跳转到编辑菜单页面
	 * @param id
	 * @param model
	 * @param pageable
	 * @return String
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model, Pageable pageable) {
		Menu menu = menuService.get(id);
		model.addAttribute("menuTree", menuService.genereateMenuTree(menu.getParent()));
		model.addAttribute("menu", menu);
		model.addAttribute("menuValue", menuValueService.get(menu.getMenuValue()));
		return "/system/menu/edit";
	}

	/**
	 * 更新菜单
	 * @param menu
	 * @param vName
	 * @param valueId
	 * @param parentId
	 * @param redirectAttributes
	 * @return String
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Menu menu, String vName, Long valueId, Long parentId, RedirectAttributes redirectAttributes) {
		Menu m = menuService.get(menu.getId());
		m.setParent(parentId);
		
		if (!StringUtil.isEmpty(vName)) {
			if(valueId == null){
				MenuValue pmenuValue = new MenuValue();
				if (menuValueService.nameExists(valueId, vName)) {
					return ERROR_VIEW;
				}
				pmenuValue.setCreateDate(new Date());
				pmenuValue.setModifyDate(new Date());
				pmenuValue.setvName(vName);
				Long menuValueId = menuValueService.save(pmenuValue);
				menu.setMenuValue(menuValueId);
			}else{
				MenuValue menuValue = menuValueService.get(valueId);
				if (menuValue == null) {
					return ERROR_VIEW;
				}
				if (menuValueService.nameExists(valueId, vName)) {
					return ERROR_VIEW;
				}
				menuValue.setModifyDate(new Date());
				menuValue.setvName(vName);
				menuValueService.update(menuValue);
				menu.setMenuValue(valueId);
			}
		}
		
		if (m.getParent() != null) {
			Menu parent = menuService.get(m.getParent());
			if (parent.equals(m)) {
				return ERROR_VIEW;
			}
			List<Menu> children = menuService.findChildrenMenu(m.getParent());
			if (children != null && children.contains(m)) {
				return ERROR_VIEW;
			}
		}
		menuService.updateMenu(m);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}


	/**
	 * 删除菜单
	 * @param id
	 * @param vid
	 * @return Message
	 */
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public Message delete(Long id, Long vid) {
		Menu menu = menuService.get(id);
		if (menu == null) {
			return ERROR_MESSAGE;
		}
		List<Menu> children = menuService.findChildrenMenu(id);
		if (children != null && !children.isEmpty()) {
			return Message.error("菜单包含子菜单，不可以删除");
		}
		menuService.delete(id);
		if(vid != null) {
			menuValueService.delete(vid);
		}
		return SUCCESS_MESSAGE;
	}

}