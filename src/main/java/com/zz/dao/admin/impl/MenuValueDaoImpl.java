package com.zz.dao.admin.impl;


import com.zz.dao.admin.MenuValueDao;
import com.zz.model.MenuValue;
import com.zz.model.basic.dao.base.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 菜单值持久层接口实现
 * @Date 2014-12-30
 * @author 欧志辉
 * @version 1.0
 */
@Repository("menuValueDaoImpl")
public class MenuValueDaoImpl extends BaseDaoImpl<MenuValue, Long> implements MenuValueDao {

	@Override
	public List<MenuValue> nameExists(Long id, String vName) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("vName", vName);
		StringBuilder sqlSB = new StringBuilder("select * from xx_menu_value ");
		sqlSB.append("where lower(v_name) = lower(:vName)");
		if (id != null) {
			sqlSB.append(" and id <> :id");
			paramMap.put("id", id);
		}
		return super.query(sqlSB.toString(), paramMap);
	}

	@Override
	public void batchDelete(List<MenuValue> menuValues) {
		String sql = "delete from xx_menu_value where id = :id";
		super.batch(sql, menuValues);
	}

}