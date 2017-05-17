package com.zz.dao.admin.impl;


import com.zz.dao.admin.AdminRoleDao;
import com.zz.model.AdminRole;
import com.zz.model.basic.dao.jdbc.SpringJDBCTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 后台管理员角色关联关系持久层实现
 * @Date 2014-12-29
 * @author 欧志辉
 * @version 1.0
 */
@Repository("adminRoleDaoImpl")
public class AdminRoleDaoImpl implements AdminRoleDao {

	@Resource
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Resource
	private SpringJDBCTemplate springJDBCTemplate;
	
	@Override
	public void batchSaveAdminRole(List<AdminRole> adminRoles) {
		
		String sql = "insert into xx_admin_role (admins, roles) values(:adminId,:roleId)";
		springJDBCTemplate.batchOperate(sql, adminRoles);
	}

	@Override
	public void deleteByAdminId(Long adminId) {
		String sql = "delete from xx_admin_role where admins = :adminId";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("adminId", adminId);
		namedParameterJdbcTemplate.update(sql, paramMap);
	}

	@Override
	public void deleteByRoleId(Long roleId) {
		String sql = "delete from xx_admin_role where roles = :roleId";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("roleId", roleId);
		namedParameterJdbcTemplate.update(sql, paramMap);
	}

	@Override
	public List<AdminRole> getRoleById(Long id) {
		String sql = "select admins as adminId,roles as roleId from xx_admin_role where admins=:admin";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("admin", id);
		List<AdminRole> list = springJDBCTemplate.query(sql, paramMap,AdminRole.class);
		if (list != null && list.size() > 0) {
			return list;
		}
		return null;
	}

}