package com.zz.dao.admin.impl;


import com.zz.dao.admin.AdminDao;
import com.zz.model.Admin;
import com.zz.model.basic.dao.base.impl.BaseDaoImpl;
import com.zz.model.vo.AdminRoleAuthority;
import com.zz.util.shengyuan.StringUtil;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 后台管理员持久层实现
 * @Date 2014-12-29
 * @author 欧志辉
 * @version 1.0
 */
@Repository("adminDaoImpl")
public class AdminDaoImpl extends BaseDaoImpl<Admin, Long> implements AdminDao {
	
	@Override
	public List<Admin> findByUsername(String username) {
		if (StringUtil.isEmpty(username)) {
			return null;
		}
		String sql = "select * from xx_admin where lower(username) = lower(:username)";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("username", username);
		return super.query(sql, paramMap);
	}

	@Override
	public List<Admin> findByRoleCode(List<String> roleCodes) {
		StringBuilder sqlSb = new StringBuilder("select a.* from xx_admin a ");
		sqlSb.append("left join xx_admin_role ra on a.id = ra.admins left join xx_role r "); 
		sqlSb.append("on r.id = ra.roles where 1 = 1");
		Map<String,Object> paramMap = new HashMap<String,Object>();
		if (roleCodes != null && roleCodes.size() > 0) {
			sqlSb.append(" and r.code in(:roleCodes)");
			paramMap.put("roleCodes", roleCodes);
		}
		return super.query(sqlSb.toString(), paramMap);
	}

	@Override
	public List<AdminRoleAuthority> findAuthoritys(Long adminId) {
		if (adminId == null) {
			return null;
		}
		StringBuilder sqlSB = new StringBuilder("select ra.authorities as authority from xx_admin a ");
		sqlSB.append("left join xx_admin_role ar on a.id = ar.admins ");
		sqlSB.append("right join xx_role_authority ra on ar.roles = ra.role where a.id = :adminId");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("adminId", adminId);
		return super.query(sqlSB.toString(), paramMap, AdminRoleAuthority.class);
	}

	@Override
	public void batchDelete(List<Admin> admins) {
		String sql = "delete from xx_admin where id = :id";
		super.batch(sql, admins);
	}

}