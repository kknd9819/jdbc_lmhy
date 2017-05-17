package com.zz.dao.admin;


import com.zz.model.AdminRole;

import java.util.List;

/**
 * 后台管理员角色关联关系持久层接口
 * @Date 2015-01-02
 * @author 欧志辉
 * @version 1.0
 */
public interface AdminRoleDao {
	
	/**
	 * 保存后台管理员角色关联关系
	 * @param adminRoles
	 */
	public void batchSaveAdminRole(List<AdminRole> adminRoles);
	
	/**
	 * 通过后台管理员ID删除后台管理员角色关联关系
	 * @param adminId
	 */
	public void deleteByAdminId(Long adminId);
	
	/**
	 * 通过角色ID删除后台管理员角色关联关系
	 * @param roleId
	 */
	public void deleteByRoleId(Long roleId);

	/**
	 * 根据管理员ID 查询角色
	 * @param id
	 * @return
	 */
	public List<AdminRole> getRoleById(Long id);
}