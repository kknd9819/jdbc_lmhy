package com.zz.model.basic.dao.jdbc;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface SpringJDBCTemplate {
	/**
	 * 保存并返回主键（仅支持数据库自动增长） 如果sql为空或null或者model对象为null，返回受影响数为0
	 * 
	 * @param sql
	 * @param obj
	 *            javabean 对象
	 * @return 返回主键
	 */
	public Serializable save(String sql, Object model);

	/**
	 * 更新
	 * 
	 * @param sql
	 * @param model
	 * @return
	 */
	public int update(String sql, Object model);

	/**
	 * 更新
	 * 
	 * @param sql
	 * @param paramMap
	 * @return
	 */
	public int update(String sql, Map<String, Object> paramMap);

	/**
	 * 批量添加 如果sql为空或null或者listObject对象为null，返回受影响数为int[]{0}
	 * 
	 * @param sql
	 * @param listObject
	 *            javaBean集合
	 * @return 返回受影响数集合
	 */
	public <T> int[] batchOperate(String sql, List<T> listObject);

	/**
	 * 批量操作
	 * 
	 * @param <T>
	 * @param sql
	 *            sql语句
	 * @param paramMaps
	 *            参数MAP数组
	 * @return 返回受影响数
	 */
	public <T> int[] batchOperate(String sql, Map<String, ?>[] paramMaps);

	/**
	 * 排序查询
	 * 
	 * @param sql
	 * @param persistentClass
	 * @param orders
	 * @return
	 */
	public <T> List<T> query(String sql, Class<T> persistentClass);

	/**
	 * 带参数排序查询
	 * 
	 * @param sql
	 * @param paramMap
	 * @param persistentClass
	 * @param orders
	 * @return
	 */
	public <T> List<T> query(String sql, Map<String, Object> paramMap, Class<T> persistentClass);

	/**
	 * 参数排序分页
	 * 
	 * @param sql
	 * @param offset
	 * @param limit
	 * @param orders
	 * @param persistentClass
	 * @return
	 */
	public <T> List<T> queryForPage(String sql, int offset, int limit, Class<T> persistentClass);

	/**
	 * 参数排序分页k
	 * 
	 * @param sql
	 * @param offset
	 * @param limit
	 * @param paramMap
	 * @param orders
	 * @param persistentClass
	 * @return
	 */
	public <T> List<T> queryForPage(String sql, int offset, int limit, Map<String, Object> paramMap, Class<T> persistentClass);

	/**
	 * Map数据集合查询
	 * 
	 * @param sql
	 * @return
	 */
	public List<Map<String, Object>> queryForList(String sql);

	/**
	 * 待参数的Map数据集合查询
	 * @param sql
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryForList(String sql, Map<String, Object> paramMap);

	/**
	 * 参见{@see queryForObject(String sql,Map<String, Object> paramMap, Class<T> persistentClass)}
	 * 
	 * @param sql
	 * @param persistentClass
	 * @return
	 */
	public <T> T queryForObject(String sql, Class<T> persistentClass);

	/**
	 * 查询对象
	 * 
	 * @param sql
	 *            sql语句
	 * @param paramMap
	 *            sql参数
	 * @param persistentClass
	 *            返回的对象
	 * @return 如果查询的记录大于两条，会报错，返回指定的persistentClass对象
	 */
	public <T> T queryForObject(String sql, Map<String, Object> paramMap, Class<T> persistentClass);

	/**
	 * 查询总行数
	 * 
	 * @param sql
	 * @return
	 */
	public Integer queryForCount(String sql);

	/**
	 * 带参数查询总行数
	 * 
	 * @param sql
	 * @param paramMap
	 * @return
	 */
	public Integer queryForCount(String sql, Map<String, Object> paramMap);
	
	/**
	 * 带参数查询
	 * 
	 * @param sql
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> queryForMap(String sql, Map<String, Object> paramMap);

	/**
	 * 执行不带IN参数的存储过程
	 * 
	 * @param procedureName
	 *            存储过程名称
	 * @param out
	 *            OUT参数返回，可不输入
	 * @return 返回执行结果
	 */
	public Map<String, Object> callProcedure(String procedureName, String... out);

	/**
	 * 执行带IN参数的存储过程
	 * 
	 * @param procedureName
	 *            存储过程名称
	 * @Param paramMap IN参数
	 * @param out
	 *            OUT参数返回，可不输入
	 * @return 返回执行结果
	 */
	public Map<String, Object> callProcedure(String procedureName, Map<String, Object> paramMap, String... out);

}
