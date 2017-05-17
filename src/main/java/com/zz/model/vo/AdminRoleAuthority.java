package com.zz.model.vo;

import java.io.Serializable;

/**
 * 管理员、角色、权限关联
 * @Date 2014-12-29
 * @author 欧志辉
 * @version 1.0
 */
public class AdminRoleAuthority implements Serializable {
	
	private static final long serialVersionUID = -1935759410716141560L;
	
	/** ID */
	private Long adminId;

	/** ID */
	private Long roleId;
	
	/** 用户名 */
	private String authority;

	public Long getAdminId() {
		return adminId;
	}

	public void setAdminId(Long adminId) {
		this.adminId = adminId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}
	
}