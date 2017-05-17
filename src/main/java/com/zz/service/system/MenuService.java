package com.zz.service.system;



import com.zz.model.Menu;
import com.zz.model.basic.model.Page;
import com.zz.model.basic.service.BaseService;
import com.zz.model.vo.MenuMenuValue;
import com.zz.util.shengyuan.Pageable;

import java.util.List;

/**
 * 菜单服务层接口
 * @Date 2014-12-30
 * @author 欧志辉
 * @version 1.0
 */
public interface MenuService extends BaseService<Menu, Long> {
	/**
	 * 查找顶级菜单
	 * @return 顶级菜单
	 */
	public List<Menu> findRoots();
	
	/**
	 * 查询一级菜单列表
	 * @param pageable
	 * @return 分页对象
	 */
	public Page<MenuMenuValue> findRootsForPage(Pageable pageable);
	
	/**
	 * 查找下级菜单菜单权限值关联关系
	 * @param parentId 上级菜单ID
	 * @return List<MenuMenuValue> 下级菜单菜单权限值关联关系
	 */
	public List<MenuMenuValue> findChildren(Long parentId);
	
	/**
	 * 查找下级菜单
	 * @param parentId 上级菜单ID
	 * @return List<Menu> 下级菜单
	 */
	public List<Menu> findChildrenMenu(Long parentId);
	
	/**
	 * 保存菜单
	 * @param menu
	 * @return
	 */
	public Long saveMenu(Menu menu);
	
	/**
	 * 更新菜单
	 * @param menu
	 * @return
	 */
	public int updateMenu(Menu menu);
	
	/**
	 * 根据菜单权限值ID查找菜单
	 * @param menuValueId 菜单权限值ID
	 * @return 下级菜单
	 */
	public List<Menu> findMenuByMenuValueId(Long menuValueId);
	
	/**
	 * 生成菜单树
	 * @param roleId
	 * @return String
	 */
	public String generateTree(Long roleId);
	
	/**
	 * 生成所有菜单的树形数据结构
	 * @param parentId 父菜单ID
	 * @return String
	 */
	public String genereateMenuTree(Long parentId);
}