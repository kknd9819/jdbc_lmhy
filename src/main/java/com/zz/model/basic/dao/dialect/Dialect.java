package com.zz.model.basic.dao.dialect;


/**
 * 分页方言
 * 
 * @author longhuang
 *
 */
public abstract class Dialect {
	/**
	 * 数据库方言
	 */
	public static enum DialectType {
		MYSQL, ORACLE;
		public static DialectType fromString(String value) {
			return DialectType.valueOf(value.toUpperCase());
		}
	}

	

	/**
	 * 获取分页SQL
	 * 
	 * @param sql
	 *            　原始SQL
	 * @param offset
	 *            　分页开始处
	 * @param limit
	 *            　偏移量
	 * @return　返回分页SQL
	 */
	public abstract String getLimitString(String sql, int offset, int limit);

}
