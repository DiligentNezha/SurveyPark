package io.vicp.goradical.surveypark.dao;

import java.util.List;

/**
 * BaseDao 接口
 * @param <T>
 */
public interface BaseDao<T> {
	//写操作
	void saveEntity(T t);
	void saveOrUpdateEntity(T t);
	void updateEntity(T t);
	void deleteEntity(T t);
	void batchEntityByHQL(String hql, Object... objects);

	//执行原生SQL语句
	void executeSQL(String sql, Object... objects);

	//读操作
	T loadEntity(Integer id);
	T getEntity(Integer id);
	List<T> findEntityByHQL(String hql, Object... objects);

	//单值检索，确保查询结果有且只有一条记录
	Object uniqueResult(String hql, Object... objects);

	/**
	 * 执行原生查询
	 */
	List executeSQLQuery(String sql, Object... objects);
}
