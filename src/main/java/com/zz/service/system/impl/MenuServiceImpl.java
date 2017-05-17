package com.zz.service.system.impl;


import com.zz.dao.admin.MenuDao;
import com.zz.model.Menu;
import com.zz.model.basic.model.Page;
import com.zz.model.basic.service.impl.BaseServiceImpl;
import com.zz.model.vo.MenuMenuValue;
import com.zz.service.system.MenuService;
import com.zz.util.shengyuan.JsonUtils;
import com.zz.util.shengyuan.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.*;

/**
 * 菜单服务层接口实现
 * @Date 2014-12-30
 * @author 欧志辉
 * @version 1.0
 */
@Service("menuServiceImpl")
public class MenuServiceImpl extends BaseServiceImpl<Menu, Long> implements MenuService {
	
	@Resource(name = "menuDaoImpl")
	private MenuDao menuDao;

	@Resource(name = "menuDaoImpl")
	public void setMenuDao(MenuDao menuDao) {
		super.setDao(menuDao);
	}

	@Override
	public List<Menu> findRoots() {
		return menuDao.findRoots();
	}

	@Override
	public Page<MenuMenuValue> findRootsForPage(Pageable pageable) {
		int pageSize = pageable.getPageSize();
		int pageNo = pageable.getPageNumber();
		return menuDao.findRootsForPage(pageNo, pageSize);
	}

	@Override
	public List<MenuMenuValue> findChildren(Long parentId) {
		
		return menuDao.findChildren(parentId);
	}

	@Override
	public List<Menu> findChildrenMenu(Long parentId) {
		return menuDao.findChildrenMenu(parentId);
	}
	
	@Override
	public Long saveMenu(Menu menu) {
		Assert.notNull(menu);
		setValue(menu);
		menu.setCreateDate(new Date());
		menu.setModifyDate(new Date());
		return menuDao.save(menu);
	}

	@Override
	public int updateMenu(Menu menu) {
		Assert.notNull(menu);
		setValue(menu);
		List<Menu> children = menuDao.findChildrenMenu(menu.getId());
		for (Menu child : children) {
			setValue(child);
			menuDao.update(child);
		}
		Menu source = menuDao.get(menu.getId());
		menu.setCreateDate(source.getCreateDate());
		menu.setModifyDate(new Date());
		return menuDao.update(menu);
	}
	
	/**
	 * 设置值
	 * 
	 * @param menu 角色菜单
	 */
	private void setValue(Menu menu) {
		if (menu == null) {
			return;
		}
		Long parentId = menu.getParent();
		if (parentId != null) {
			Menu parent = menuDao.get(parentId);
			menu.setFullName(parent.getFullName() + menu.getName());
			menu.setTreePath(parent.getTreePath() + parentId + Menu.TREE_PATH_SEPARATOR);
		} else {
			menu.setTreePath(Menu.TREE_PATH_SEPARATOR);
		}
		menu.setGrade(menu.getTreePaths().size());
	}

	@Override
	public List<Menu> findMenuByMenuValueId(Long menuValueId) {
		
		return menuDao.findMenuByMenuValueId(menuValueId);
	}

	@Override
	public String generateTree(Long roleId) {
		List<MenuMenuValue> menuMenuValues = menuDao.getAllMenuMenuValue();
		List<Menu> menus = menuDao.findMenuByRoleId(roleId);
		Set<Long> hasMenus = new HashSet<Long>();
		if (menus != null) {
			for (Menu menu : menus) {
				hasMenus.add(menu.getId());
			}
		}
		List<Map<String, Object>> jsonList = new ArrayList<Map<String, Object>>();
		if (menuMenuValues != null) {
			for (MenuMenuValue menuMenuValue : menuMenuValues) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", menuMenuValue.getId());
				map.put("name", menuMenuValue.getName());
				map.put("pId", menuMenuValue.getParent());
				map.put("authority", menuMenuValue.getvName());
				if(hasMenus.contains(menuMenuValue.getId())){
					map.put("checked", true);
				}
				jsonList.add(map);
			}
		}
		return JsonUtils.toJson(jsonList);
	}

	@Override
	public String genereateMenuTree(Long parentId) {
		List<Menu> menus = menuDao.getAll();
		List<Map<String, Object>> jsonList = new ArrayList<Map<String, Object>>();
		if (menus != null) {
			for (Menu menu : menus) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", menu.getId());
				map.put("name", menu.getName());
				map.put("pId", menu.getParent());
				if (parentId != null) {
					if(menu.getId().longValue() == parentId.longValue()) {
						map.put("checked", true);
					}
				}
				jsonList.add(map);
			}
		}
		return JsonUtils.toJson(jsonList);
	}
}