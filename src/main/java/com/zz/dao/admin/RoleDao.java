package com.zz.dao.admin;



import com.zz.model.Role;
import com.zz.model.basic.dao.base.BaseDao;

import java.util.List;

/**
 * 角色持久层接口
 * @Date 2014-12-29
 * @author 欧志辉
 * @version 1.0
 */
public interface RoleDao extends BaseDao<Role, Long> {
	
	/**
	 * 批量删除
	 * @param roles
	 */
	public void batchDelete(List<Role> roles);
	
	/**
	 * 根据管理员ID查询管理员的角色
	 * @param adminId
	 * @return List<Role>
	 */
	public List<Role> findRoleByAdminId(Long adminId);
}