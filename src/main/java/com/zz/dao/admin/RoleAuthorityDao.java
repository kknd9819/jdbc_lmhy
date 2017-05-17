package com.zz.dao.admin;



import com.zz.model.RoleAuthority;

import java.util.List;

/**
 * 角色权限关联关系持久层接口
 * @Date 2015-01-02
 * @author 欧志辉
 * @version 1.0
 */
public interface RoleAuthorityDao {
	
	/**
	 * 保存角色权限关联关系
	 * @param roleAuthoritys
	 */
	public void batchSaveRoleAuthority(List<RoleAuthority> roleAuthoritys);
	
	/**
	 * 通过角色ID删除角色权限关联关系
	 * @param roleId
	 */
	public void deleteByRoleId(Long roleId);
}