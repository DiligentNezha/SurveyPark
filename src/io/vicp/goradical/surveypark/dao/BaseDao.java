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
	//读操作
	T loadEntity(Integer id);
	T getEntity(Integer id);
	List<T> findEntityByHQL(String hql, Object... objects);

}
