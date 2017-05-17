package com.zz.model.basic.dao.dialect;

import org.springframework.stereotype.Component;


/**
 * MySql分页
 * @author longhuang
 *
 */

@Component("mySQLDialect")
public class MySQLDialect extends Dialect{
	@Override
	public String getLimitString(String sql,int offset,int limit) {
        StringBuilder sqlBuilder = new StringBuilder(sql.length()+20);
        sqlBuilder.append(sql);
    	sqlBuilder.append(" limit ");
    	sqlBuilder.append(offset);
    	sqlBuilder.append(",");
    	sqlBuilder.append(limit);
        return sqlBuilder.toString();
	}   
  
}
