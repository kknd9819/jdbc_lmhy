package com.zz.model.basic.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.zz.model.basic.dao.base.BaseDao;
import com.zz.model.basic.model.Order;
import com.zz.model.basic.model.Page;
import com.zz.model.basic.service.BaseService;
import org.springframework.transaction.annotation.Transactional;



@Transactional
public class BaseServiceImpl<T, PK extends Serializable> implements BaseService<T, PK> {
	@Resource
	protected BaseDao<T, PK> baseDao;

	public void setDao(BaseDao<T, PK> baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public T get(PK id) {
		return baseDao.get(id);
	}

	@Override
	public int delete(PK id) {
		return baseDao.delete(id);
	}

	@Override
	public int[] batchDelete(PK[] ids) {
		return baseDao.batchDelete(ids);
	}

	@Override
	public PK save(T entity) {
		return baseDao.save(entity);
	}

	@Override
	public int update(T entity) {
		return baseDao.update(entity);
	}

	@Override
	public List<T> getAll(Order... sorts) {
		return baseDao.getAll(sorts);
	}

	@Override
	public Integer count(Map<String, Object> paramMap) {
		return baseDao.count(paramMap);
	}

	@Override
	public List<T> query(Map<String, Object> paramMap, Order... sorts) {
		return baseDao.query(paramMap, sorts);
	}

	@Override
	public Page<T> queryForPage(int pageSize, int currentPage, Map<String, Object> paramMap, Order... sorts) {
		return baseDao.queryForPage(pageSize, currentPage, paramMap, sorts);
	}

}
