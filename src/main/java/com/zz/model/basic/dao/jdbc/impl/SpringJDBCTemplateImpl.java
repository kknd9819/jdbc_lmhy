package com.zz.model.basic.dao.jdbc.impl;

import java.io.Serializable;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.zz.model.basic.dao.dialect.Dialect;
import com.zz.model.basic.dao.jdbc.SpringJDBCTemplate;
import com.zz.util.shengyuan.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;


@Component("springJDBCTemplate")
public class SpringJDBCTemplateImpl implements SpringJDBCTemplate {
	private static final HashMap<String, Object> DEFAULT_PARAM_MAP = new HashMap<String, Object>();

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Resource
	private SimpleJdbcCall simpleJdbcCall;

	@Resource
	private Dialect mySQLDialect;

	@Override
	public Serializable save(String sql, Object model) {
		logger.debug("保存开始，保存SQL:{}", sql);
		if (StringUtil.isEmpty(sql)) {
			logger.debug("saveReturnPk方法传入的sql参数为空或null");
			return 0L;
		}

		if (model == null) {
			logger.debug("saveReturnPk方法传入的model参数为 null");
			return 0L;
		}

		KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(model);
		namedParameterJdbcTemplate.update(sql, paramSource, generatedKeyHolder);
		return (Serializable) generatedKeyHolder.getKey();
	}

	@Override
	public int update(String sql, Object model) {
		logger.debug("更新操作开始，SQL:{}", sql);
		if (StringUtil.isEmpty(sql)) {
			logger.debug("updateSql方法传入的sql参数为空或null");
			return 0;
		}

		if (model == null) {
			logger.debug("updateSql方法传入的model参数为 null");
			return 0;
		}
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(model);
		return namedParameterJdbcTemplate.update(sql, paramSource);
	}

	@Override
	public int update(String sql, Map<String, Object> paramMap) {
		logger.debug("更新操作开始，SQL:{}", sql);
		if (StringUtil.isEmpty(sql)) {
			logger.debug("updateSql方法传入的sql参数为空或null");
			return 0;
		}
		if (paramMap == null || paramMap.isEmpty()) {
			logger.debug("updateSql方法传入的paramMap参数为空或null");
			return 0;
		}
		return namedParameterJdbcTemplate.update(sql, paramMap);
	}

	@Override
	public <T> int[] batchOperate(String sql, List<T> listObject) {
		logger.debug("批量操作开始，SQL:{}", sql);

		if (StringUtil.isEmpty(sql)) {
			logger.debug("batchOperate方法传入的sql参数为空或null");
			return new int[] { 0 };
		}

		if (listObject == null || listObject.size() <= 0) {
			logger.debug("batchOperate方法传入的listObject参数为 null");
			return new int[] { 0 };
		}
		SqlParameterSource[] batchArgs = new BeanPropertySqlParameterSource[listObject.size()];
		for (int i = 0; i < listObject.size(); i++) {
			batchArgs[i] = new BeanPropertySqlParameterSource(listObject.get(i));
		}
		return namedParameterJdbcTemplate.batchUpdate(sql, batchArgs);
	}

	@Override
	public <T> int[] batchOperate(String sql, Map<String, ?>[] paramMaps) {
		logger.debug("批量操作开始，SQL:{}", sql);

		if (StringUtil.isEmpty(sql)) {
			logger.debug("batchOperate方法传入的sql参数为空或null");
			return new int[] { 0 };
		}

		if (paramMaps == null || paramMaps.length <= 0) {
			logger.debug("batchOperate方法传入的paramMaps参数为 null");
			return new int[] { 0 };
		}
		return namedParameterJdbcTemplate.batchUpdate(sql, paramMaps);
	}

	@Override
	public <T> List<T> query(String sql, Class<T> persistentClass) {
		return query(sql, DEFAULT_PARAM_MAP, persistentClass);
	}

	@Override
	public <T> List<T> query(String sql, Map<String, Object> paramMap, Class<T> persistentClass) {
		logger.debug("普通查询操作开始，SQL:{}", sql);
		if (StringUtil.isEmpty(sql)) {
			logger.debug("带参数query方法传入的sql参数为空或null");
			return null;
		}

		if (paramMap == null) {
			logger.debug("带参数query方法传入的paramMap参数为null");
			return null;
		}

		if (persistentClass == null) {
			logger.debug("不带参数query方法传入的persistentClass参数为空或null");
			return null;
		}

		return namedParameterJdbcTemplate.query(sql, paramMap, new BeanPropertyRowMapper<T>(persistentClass));
	}

	@Override
	public <T> List<T> queryForPage(String sql, int offset, int limit, Class<T> persistentClass) {
		return queryForPage(sql, offset, limit, DEFAULT_PARAM_MAP, persistentClass);
	}

	@Override
	public <T> List<T> queryForPage(String sql, int offset, int limit, Map<String, Object> paramMap, Class<T> persistentClass) {
		logger.debug("分页查询操作开始，SQL:{}", sql);
		if (StringUtil.isEmpty(sql)) {
			logger.debug("带参数queryForPage方法传入的sql参数为空或null");
			return null;
		}

		if (paramMap == null) {
			logger.debug("带参数queryForPage方法传入的paramMap参数为null");
			return null;
		}

		if (offset < 0) {
			logger.debug("带参数queryForPage方法传入的offset参数小于0");
			return null;
		}

		if (persistentClass == null) {
			logger.debug("不带参数query方法传入的persistentClass参数为空或null");
			return null;
		}

		sql = mySQLDialect.getLimitString(sql, offset, limit);
		logger.debug("数据库分页后的SQL:{}", sql);

		return namedParameterJdbcTemplate.query(sql, paramMap, new BeanPropertyRowMapper<T>(persistentClass));
	}

	@Override
	public List<Map<String, Object>> queryForList(String sql) {
		return namedParameterJdbcTemplate.queryForList(sql, DEFAULT_PARAM_MAP);
	}

	@Override
	public List<Map<String, Object>> queryForList(String sql, Map<String, Object> paramMap) {
		return namedParameterJdbcTemplate.queryForList(sql, paramMap);
	}

	@Override
	public <T> T queryForObject(String sql, Class<T> persistentClass) {
		return queryForObject(sql, DEFAULT_PARAM_MAP, persistentClass);
	}

	@Override
	public <T> T queryForObject(String sql, Map<String, Object> paramMap, Class<T> persistentClass) {
		return namedParameterJdbcTemplate.queryForObject(sql, paramMap, persistentClass);
	}

	@Override
	public Integer queryForCount(String sql) {
		return queryForCount(sql, DEFAULT_PARAM_MAP);
	}

	@Override
	public Integer queryForCount(String sql, Map<String, Object> paramMap) {
		logger.debug("总行数查询操作开始，SQL:{}", sql);
		if (StringUtil.isEmpty(sql)) {
			logger.debug("queryForCount传入的sql参数为空或null");
			return null;
		}
		if (paramMap == null) {
			logger.debug("queryForCount传入的paramMap参数为null");
			return null;
		}
		return queryForObject(sql, paramMap, Integer.class);
	}

	@Override
	public Map<String, Object> queryForMap(String sql,Map<String, Object> paramMap) {
		return namedParameterJdbcTemplate.queryForMap(sql, paramMap);
	}

	public Map<String, Object> callProcedure(String procedureName, String... out) {
		return callProcedure(procedureName, DEFAULT_PARAM_MAP, out);
	}

	public Map<String, Object> callProcedure(String procedureName, Map<String, Object> paramMap, String... out) {
		logger.debug("存储过程{}调用开始", procedureName);
		if (StringUtil.isEmpty(procedureName)) {
			logger.debug("callProcedure传入的procedureName参数为空或null");
			return null;
		}

		if (paramMap == null) {
			paramMap = DEFAULT_PARAM_MAP;
		}

		simpleJdbcCall.withProcedureName(procedureName);
		if (out != null && out.length > 0) {
			for (int i = 0; i < out.length; i++) {
				simpleJdbcCall.declareParameters(new SqlOutParameter(out[i], Types.OTHER));
			}
		}
		return simpleJdbcCall.execute(paramMap);
	}

}
