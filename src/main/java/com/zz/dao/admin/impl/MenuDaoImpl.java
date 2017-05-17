package com.zz.dao.admin.impl;


import com.zz.dao.admin.MenuDao;
import com.zz.model.Menu;
import com.zz.model.basic.dao.base.impl.BaseDaoImpl;
import com.zz.model.basic.model.Page;
import com.zz.model.vo.MenuMenuValue;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 菜单持久层实现
 * @Date 2014-12-29
 * @author 欧志辉
 * @version 1.0
 */
@Repository("menuDaoImpl")
public class MenuDaoImpl extends BaseDaoImpl<Menu, Long> implements MenuDao {

	@Override
	public List<Menu> findRoots() {
		String sql = "select * from xx_menu where parent is null order by orders";
		return super.query(sql);
	}

	@Override
	public Page<MenuMenuValue> findRootsForPage(int pageNo, int pageSize) {
		
		StringBuilder sqlSB = new StringBuilder("select m.*,mv.v_name from xx_menu m ");
		sqlSB.append("left join xx_menu_value mv on m.menu_value = mv.id where m.parent is null ");
		String sqlCount = "select count(*) from (" + sqlSB.toString() + ") t___";
		sqlSB.append("order by orders");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		return super.queryForPage(sqlSB.toString(), sqlCount, pageSize, pageNo, paramMap, MenuMenuValue.class);
	}
	
	@Override
	public List<MenuMenuValue> findChildren(Long parentId) {
		if (parentId == null) {
			return null;
		}
		StringBuilder sqlSB = new StringBuilder("select m.*,mv.v_name from xx_menu m ");
		sqlSB.append("left join xx_menu_value mv on m.menu_value = mv.id where m.parent = :parentId ");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("parentId", parentId);
		return super.query(sqlSB.toString(), paramMap, MenuMenuValue.class);
	}

	@Override
	public List<Menu> findChildrenMenu(Long parentId) {
		if (parentId == null) {
			return null;
		}
		String sql ="select * from xx_menu where parent = :parentId";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("parentId", parentId);
		return super.query(sql, paramMap);
	}
	
	@Override
	public List<Menu> findMenuByMenuValueId(Long menuValueId) {
		if (menuValueId == null) {
			return null;
		}
		String sql = "select * from xx_menu where menu_value = :menuValueId";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("menuValueId", menuValueId);
		return super.query(sql, paramMap);
	}

	@Override
	public List<Menu> findMenuByRoleId(Long roleId) {
		if (roleId == null) {
			return null;
		}
		StringBuilder sqlSB = new StringBuilder("select m.* from xx_menu m ");
		sqlSB.append("left join xx_menu_value mv on m.menu_value = mv.id ");
		sqlSB.append("left join xx_role_authority ra on mv.v_name = ra.authorities where ra.role = :roleId");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("roleId", roleId);
		return super.query(sqlSB.toString(), paramMap);
	}

	@Override
	public List<MenuMenuValue> getAllMenuMenuValue() {
		StringBuilder sqlSB = new StringBuilder("select m.id,m.name,m.parent,mv.v_name ");
		sqlSB.append("from xx_menu m left join xx_menu_value mv on m.menu_value = mv.id ");
		return super.query(sqlSB.toString(), MenuMenuValue.class);
	}
	
}