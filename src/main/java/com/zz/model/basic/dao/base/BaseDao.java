package com.zz.model.basic.dao.base;

import com.zz.model.basic.model.Order;
import com.zz.model.basic.model.Page;

import java.io.Serializable;
import java.util.List;
import java.util.Map;



public interface BaseDao<T, PK extends Serializable> {
	/**
	 * 按id获取对象.
	 * 
	 * @param id
	 * @return
	 */
	public T get(PK id);

	/**
	 * 按id删除对象.
	 * 
	 * @param id
	 * @return 返回受影响数
	 */
	public int delete(PK id);

	/**
	 * 批量根据id删除多个对象
	 * 
	 * @param ids
	 *            id数组
	 * @return 返回影响行数
	 */
	public int[] batchDelete(PK[] ids);

	/**
	 * 保存新增的对象.
	 * 
	 * @param entity
	 * @return
	 */
	public PK save(T entity);

	/**
	 * 更新对象.（注意：如果T的属性a对应数据库a字段是有值的，但如果在update时传值时T的a属性无值，则更新到数据时a字段也会没值）
	 * 
	 * @param entity
	 * @return 返回受影响数
	 */
	public int update(T entity);

	/**
	 * 获取全部对象.
	 * 
	 * @param sorts
	 *            排序方式
	 * @return
	 */
	public List<T> getAll(Order... sorts);

	/**
	 * 数量统计
	 * 
	 * @param paramMap
	 *            查询条件(key:字段名或属性名,value:查询值)
	 * @return
	 */
	public Integer count(Map<String, Object> paramMap);

	/**
	 * 执行默认所有字段的查询.MAP的查询条件仅按AND方式匹配，value支持‘%’模糊查询.
	 * 
	 * @param paramMap
	 *            查询条件(key:字段名或属性名,value:查询值)
	 * @param sorts
	 * @return
	 */
	public List<T> query(Map<String, Object> paramMap, Order... sorts);

	/**
	 * 分页查询
	 * 
	 * @param pageSize
	 *            每页数量
	 * @param currentPage
	 *            当前页
	 * @param paramMap
	 *            参数
	 * @param sorts
	 *            排序
	 * @return 返回分页
	 */
	public Page<T> queryForPage(int pageSize, int currentPage, Map<String, Object> paramMap, Order... sorts);

}
