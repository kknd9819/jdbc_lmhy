package com.zz.model.basic.dao.base.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.zz.model.basic.dao.annotation.AnnotationHelper;
import com.zz.model.basic.dao.base.BaseDao;
import com.zz.model.basic.dao.dialect.Dialect;
import com.zz.model.basic.dao.jdbc.SpringJDBCTemplate;
import com.zz.model.basic.model.Order;
import com.zz.model.basic.model.Page;
import com.zz.util.shengyuan.CamelCaseUtils;
import com.zz.util.shengyuan.ReflectionUtil;
import com.zz.util.shengyuan.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;



@Repository("baseDao")
public class BaseDaoImpl<T, PK extends Serializable> implements BaseDao<T, PK> {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource
	private SpringJDBCTemplate springJDBCTemplate;

	@Resource
	private Dialect dialect;

	private Class<T> entityClass;

	// 表名
	private String entityTable;

	// ID属性名
	private String entityId;

	// ID字段名
	private String entityColumnId;

	/** insert字段字符串 */
	private String entityInsertFields;

	/** insert value参数 */
	private String insertValueParams;

	/** 更新 value参数 */
	private String updateValueParams;

	/** select字段字符串 */
	private String entitySelectFields;

	/** 实体fields */
	private List<String> entityFields;

	public BaseDaoImpl() {
		this.entityClass = ReflectionUtil.getSuperClassGenricType(getClass());
		this.entityTable = AnnotationHelper.getEntityTable(entityClass);
		Field[] fields = entityClass.getDeclaredFields();
		this.entityId = AnnotationHelper.getEntityId(fields);
		this.entityColumnId = CamelCaseUtils.toUnderlineName(entityId);
		this.entityInsertFields = AnnotationHelper.getEntityInsertFields(fields);
		this.insertValueParams = AnnotationHelper.getInsertValueParams(fields);
		this.updateValueParams = AnnotationHelper.getUpdateValueParams(fields);
		this.entitySelectFields = getEntitySelectFilelds(entityId, entityInsertFields);
		this.entityFields = AnnotationHelper.getEntityFields(fields);
	}

	private String getEntitySelectFilelds(String id, String insertFields) {
		StringBuilder selectBuilder = new StringBuilder(200);
		selectBuilder.append(CamelCaseUtils.toUnderlineName(id));
		if (!StringUtil.isEmpty(insertFields)) {
			selectBuilder.append(",");
			selectBuilder.append(insertFields);
		}
		return selectBuilder.toString();
	}

	@Override
	public T get(PK id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(entityId, id);
		List<T> listT = springJDBCTemplate.query(getGetSql(), paramMap, entityClass);
		if (listT != null && listT.size() > 0) {
			return listT.get(0);
		}
		return null;
	}

	/**
	 * 获取get SQL语句
	 * 
	 * @return
	 */
	private String getGetSql() {
		StringBuilder selectSqlBuilder = new StringBuilder(300);
		selectSqlBuilder.append(getCommonSelectSql());
		selectSqlBuilder.append(" WHERE ");
		selectSqlBuilder.append(entityColumnId);
		selectSqlBuilder.append(" = ");
		selectSqlBuilder.append(":");
		selectSqlBuilder.append(entityId);
		return selectSqlBuilder.toString();
	}

	/**
	 * 获取select sql语句
	 * 
	 * @return
	 */
	private String getCommonSelectSql(Order... sorts) {
		StringBuilder selectSqlBuilder = new StringBuilder(300);
		selectSqlBuilder.append(" SELECT ");
		selectSqlBuilder.append(entitySelectFields);
		selectSqlBuilder.append(" FROM ");
		selectSqlBuilder.append(entityTable);

		return getOrder(selectSqlBuilder.toString(), sorts);

	}

	private String getOrder(String sql, Order... sorts) {
		if (sorts != null && sorts.length > 0) {
			boolean isOrder = checkOrderProperty(sorts);
			if (isOrder) {
				return getOrderSql(sql, sorts);
			}
		}
		return sql;
	}

	/**
	 * 
	 * @param sorts
	 * @return
	 */
	private boolean checkOrderProperty(Order[] sorts) {
		boolean isOrder = true;
		if (sorts != null && sorts.length > 0) {
			for (Order order : sorts) {
				if (!entitySelectFields.contains(order.getProperty())) {
					isOrder = false;
					break;
				}
			}
		}
		return isOrder;
	}

	/**
	 * 获取排序SQL
	 * 
	 * @param sql
	 *            原生SQL
	 * @param orders
	 *            排序字段
	 * @param orders
	 *            排序方向
	 * @return
	 */
	public String getOrderSql(String sql, Order... orders) {
		if (orders == null || orders.length == 0) {
			return sql;
		}
		StringBuilder orderSql = new StringBuilder(1000);
		orderSql.append(" SELECT * FROM ( ");
		orderSql.append(sql);
		orderSql.append(" ) t ORDER BY ");
		int index = 0;
		for (Order order : orders) {
			if (index > 0) {
				orderSql.append(", ");
			}
			orderSql.append("t.");
			orderSql.append(order.getProperty());
			orderSql.append(" ");
			orderSql.append(order.getDirection().name());
			index++;
		}
		return orderSql.toString();
	}

	/**
	 * 获取select sql语句
	 * 
	 * @return
	 */
	private String getCommonSelectCountSql() {
		StringBuilder selectSqlBuilder = new StringBuilder(300);
		selectSqlBuilder.append(" SELECT ");
		selectSqlBuilder.append("COUNT(1)");
		selectSqlBuilder.append(" FROM ");
		selectSqlBuilder.append(entityTable);
		return selectSqlBuilder.toString();
	}

	@Override
	public int delete(PK id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(entityId, id);
		return springJDBCTemplate.update(getDeleteSql(), paramMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public int[] batchDelete(PK[] ids) {
		Map<String, ?>[] paramMaps = new HashMap[ids.length];
		for (int i = 0; i < ids.length; i++) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put(entityId, ids[i]);
			paramMaps[i] = paramMap;
		}
		return springJDBCTemplate.batchOperate(getDeleteSql(), paramMaps);
	}

	/**
	 * 带参数删除
	 * 
	 * @param sql
	 * @param paramMap
	 * @return
	 */
	public int delete(String sql, Map<String, Object> paramMap) {
		return springJDBCTemplate.update(sql, paramMap);
	}

	/**
	 * 获取删除Sql
	 * 
	 * @return
	 */
	private String getDeleteSql() {
		StringBuilder deleteSqlBuilder = new StringBuilder(200);
		deleteSqlBuilder.append(" DELETE FROM ");
		deleteSqlBuilder.append(entityTable);
		deleteSqlBuilder.append(" WHERE ");
		deleteSqlBuilder.append(entityColumnId);
		deleteSqlBuilder.append(" = :");
		deleteSqlBuilder.append(entityId);
		return deleteSqlBuilder.toString();
	}

	@SuppressWarnings("unchecked")
	@Override
	public PK save(T entity) {
		return (PK) springJDBCTemplate.save(getInsertSql(), entity);
	}

	/**
	 * 保存
	 * 
	 * @param sql
	 *            保存语句
	 * @param paramMap
	 *            参数
	 * @return 返回受影响行数
	 */
	public int save(String sql, Map<String, Object> paramMap) {
		return springJDBCTemplate.update(sql, paramMap);
	}

	/**
	 * 获取insert sql语句
	 * 
	 * @return
	 */
	private String getInsertSql() {
		StringBuilder inserSqlBuilder = new StringBuilder(300);
		inserSqlBuilder.append(" INSERT INTO ");
		inserSqlBuilder.append(entityTable);
		inserSqlBuilder.append(" (");
		inserSqlBuilder.append(entityInsertFields);
		inserSqlBuilder.append(") VALUES (");
		inserSqlBuilder.append(insertValueParams);
		inserSqlBuilder.append(")");
		return inserSqlBuilder.toString();
	}

	@Override
	public int update(T entity) {
		return springJDBCTemplate.update(getUpdateSql(), entity);
	}

	/**
	 * 更新
	 * 
	 * @param sql
	 * @param paramMap
	 * @return 返回受影响行数
	 */
	public int update(String sql, Map<String, Object> paramMap) {
		return springJDBCTemplate.update(sql, paramMap);
	}

	/**
	 * 获取更新SQL
	 * 
	 * @return
	 */
	private String getUpdateSql() {
		StringBuilder updateSqlBuilder = new StringBuilder(200);
		updateSqlBuilder.append(" UPDATE ");
		updateSqlBuilder.append(entityTable);
		updateSqlBuilder.append(" SET ");
		updateSqlBuilder.append(updateValueParams);
		updateSqlBuilder.append(" WHERE ");
		updateSqlBuilder.append(entityColumnId);
		updateSqlBuilder.append(" = :");
		updateSqlBuilder.append(entityId);
		return updateSqlBuilder.toString();
	}

	@Override
	public List<T> getAll(Order... sorts) {
		return springJDBCTemplate.query(getCommonSelectSql(sorts), entityClass);
	}

	@Override
	public Integer count(Map<String, Object> paramMap) {
		if (paramMap == null) {
			paramMap = new HashMap<String, Object>();
		}

		if (!checkParamMapKeys(paramMap)) {
			logger.debug("paramMap中的参数key不在该实体字段");
			return null;
		}

		int totalCount = queryForCount(getCountSql(paramMap), paramMap);
		return totalCount;
	}

	@Override
	public List<T> query(Map<String, Object> paramMap, Order... sorts) {

		if (paramMap == null) {
			paramMap = new HashMap<String, Object>();
		}

		if (!checkParamMapKeys(paramMap)) {
			logger.debug("paramMap中的参数key不在该实体字段");
			return null;
		}

		return springJDBCTemplate.query(getQuerySql(paramMap, sorts), paramMap, entityClass);

	}

	private String getCountSql(Map<String, Object> paramMap) {
		StringBuilder queryBuilder = new StringBuilder(200);
		queryBuilder.append(getCommonSelectCountSql());
		queryBuilder.append(getWhere(paramMap));
		return queryBuilder.toString();
	}

	private String getQuerySql(Map<String, Object> paramMap, Order... sorts) {
		StringBuilder queryBuilder = new StringBuilder(200);
		queryBuilder.append(getCommonSelectSql());
		queryBuilder.append(getWhere(paramMap));
		return getOrder(queryBuilder.toString(), sorts);

	}

	private String getWhere(Map<String, Object> paramMap) {
		if (paramMap == null || paramMap.isEmpty()) {
			return "";
		}
		StringBuilder whereBuilder = new StringBuilder();
		whereBuilder.append(" WHERE ");
		int index = 0;
		for (String key : paramMap.keySet()) {
			if (index > 0) {
				whereBuilder.append(" AND ");
			}
			String key_column = CamelCaseUtils.toUnderlineName(key);
			whereBuilder.append(key_column);
			if (paramMap.get(key).toString().indexOf("%") > -1) {
				whereBuilder.append(" LIKE :");
			} else {
				whereBuilder.append(" = :");
			}
			whereBuilder.append(key);
			index++;
		}
		return whereBuilder.toString();
	}

	/**
	 * 检查参数key是否包括在实体字段里 只有全部在实体字段里，才返回true,否则返回false;
	 * 
	 * @param paramMap
	 * @return
	 */
	private boolean checkParamMapKeys(Map<String, Object> paramMap) {
		if (paramMap == null) {
			return false;
		}

		for (String key : paramMap.keySet()) {
			if (!entityFields.contains(key)) {
				return false;
			}
		}

		return true;

	}

	/**
	 * 批量执行SQL脚本，使用泛型对象集合数据为参数
	 * 
	 * @param sql
	 * @param listT
	 * @return 受影响行数集合
	 */
	public int[] batch(String sql, List<T> listT) {
		return springJDBCTemplate.batchOperate(sql, listT);
	}

	/**
	 * 批量执行SQL脚本，使用Map数组数据为参数
	 * 
	 * @param sql
	 * @param paramMaps
	 * @return 受影响行数集合
	 */
	public int[] batch(String sql, Map<String, ?>[] paramMaps) {
		return springJDBCTemplate.batchOperate(sql, paramMaps);
	}

	/**
	 * 不带参数查询
	 * 
	 * @param sql
	 * @return
	 */
	public List<T> query(String sql) {
		return springJDBCTemplate.query(sql, entityClass);
	}

	/**
	 * 查询，返回泛型对象集合
	 * 
	 * @param sql
	 * @param paramMap
	 * @return
	 */
	public List<T> query(String sql, Map<String, Object> paramMap) {
		return springJDBCTemplate.query(sql, paramMap, entityClass);
	}

	/**
	 * 不带参数查询
	 * 
	 * @param sql
	 * @param persistentClass
	 * @return
	 */
	public <X> List<X> query(String sql, Class<X> persistentClass) {
		return springJDBCTemplate.query(sql, persistentClass);
	}

	/**
	 * 通过SQL脚本进行查询，根据传递的类Class返回对象集合
	 * 
	 * @param sql
	 * @param paramMap
	 * @param persistentClass
	 * @return
	 */
	public <X> List<X> query(String sql, Map<String, Object> paramMap, Class<X> persistentClass) {
		return springJDBCTemplate.query(sql, paramMap, persistentClass);
	}

	public List<Map<String, Object>> queryForList(String sql) {
		return springJDBCTemplate.queryForList(sql);
	}

	public List<Map<String, Object>> queryForList(String sql, Map<String, Object> paramMap) {
		return springJDBCTemplate.queryForList(sql, paramMap);
	}

	public <X> X queryForObject(String sql, Class<X> persistentClass) {
		return springJDBCTemplate.queryForObject(sql, persistentClass);
	}

	/**
	 * 执行SQL，返回指定的对象
	 * 
	 * @param sql
	 *            sql
	 * @param paramMap
	 *            sql参数
	 * @param persistentClass
	 *            返回的对象
	 * @return 返回指定persistentClass，如果SQL返回大于1条记录，则会报错
	 */
	public <X> X queryForObject(String sql, Map<String, Object> paramMap, Class<X> persistentClass) {
		return springJDBCTemplate.queryForObject(sql, paramMap, persistentClass);
	}
	
	public Map<String,Object> queryForMap(String sql,Map<String,Object> paramMap){
		return springJDBCTemplate.queryForMap(sql, paramMap);
	}

	public Integer queryForCount(String sql) {
		return springJDBCTemplate.queryForCount(sql);
	}

	/**
	 * 执行SQL统计脚本，返回查询的唯一结果
	 * 
	 * @param sql
	 * @param paramMap
	 * @return
	 */
	public Integer queryForCount(String sql, Map<String, Object> paramMap) {
		return springJDBCTemplate.queryForCount(sql, paramMap);
	}

	public Page<T> queryForPage(int pageSize, int currentPage, Map<String, Object> paramMap, Order... sorts) {
		if (paramMap == null) {
			paramMap = new HashMap<String, Object>();
		}

		if (!checkParamMapKeys(paramMap)) {
			logger.debug("paramMap中的参数key不在该实体字段");
			return null;
		}

		int totalCount = queryForCount(getCountSql(paramMap), paramMap);

		// 共多少页
		int pageCount = pageSize == 0 ? 1 : (int) Math.ceil((double) totalCount / (double) pageSize);

		// 如果当前页大于最大页
		if (currentPage > pageCount) {
			currentPage = pageCount;
		}
		// 获取开始页
		int offset = ((currentPage - 1) < 0) ? 0 : (currentPage - 1) * pageSize;

		List<T> listT = springJDBCTemplate.queryForPage(getQuerySql(paramMap, sorts), offset, pageSize, paramMap, entityClass);
		return new Page<T>(listT, totalCount, currentPage, pageSize);

	}

	public Page<T> queryForPage(String sqlQuery, String sqlCount, int pageSize, int currentPage, Map<String, Object> paramMap) {
		return queryForPage(sqlQuery, sqlCount, pageSize, currentPage, paramMap, entityClass);
	}

	/**
	 * 分页查询
	 * 
	 * @param sqlQuery
	 *            内容查询语句
	 * @param sqlCount
	 *            数据大小查询语句
	 * @param pageSize
	 *            每页多少行
	 * @param currentPage
	 *            当前第几页
	 * @param paramMap
	 *            查询参数
	 * @param persistentClass
	 *            返回的对象
	 * @return
	 */
	public <X> Page<X> queryForPage(String sqlQuery, String sqlCount, int pageSize, int currentPage, Map<String, Object> paramMap, Class<X> persistentClass) {
		if (paramMap == null) {
			paramMap = new HashMap<String, Object>();
		}

		int totalCount = queryForCount(sqlCount, paramMap);

		// 共多少页
		int pageCount = pageSize == 0 ? 1 : (int) Math.ceil((double) totalCount / (double) pageSize);

		// 如果当前页大于最大页
		if (currentPage > pageCount) {
			currentPage = pageCount;
		}
		// 获取开始页
		int offset = ((currentPage - 1) < 0) ? 0 : (currentPage - 1) * pageSize;
		List<X> listX = springJDBCTemplate.queryForPage(sqlQuery, offset, pageSize, paramMap, persistentClass);
		return new Page<X>(listX, totalCount, currentPage, pageSize);
	}

	/**
	 * 执行不带IN参数的存储过程
	 * 
	 * @param procedureName
	 *            存储过程名称
	 * @param out
	 *            OUT参数返回，可不输入
	 * @return 返回执行结果
	 */
	public Map<String, Object> callProcedure(String procedureName, String... out) {
		return springJDBCTemplate.callProcedure(procedureName, out);
	}

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
	public Map<String, Object> callProcedure(String procedureName, Map<String, Object> paramMap, String... out) {
		return springJDBCTemplate.callProcedure(procedureName, paramMap, out);
	}
}
