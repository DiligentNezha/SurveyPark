package io.vicp.goradical.surveypark.service.impl;

import io.vicp.goradical.surveypark.dao.BaseDao;
import io.vicp.goradical.surveypark.dao.impl.BaseDaoImpl;
import io.vicp.goradical.surveypark.service.BaseService;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * 抽象的BaseService,专门用于继承
 * @param <T>
 */
public abstract class BaseServiceImpl<T> implements BaseService<T> {

	//注入dao
	@Autowired
	private BaseDao<T> dao;

	private Class<T> clazz;

	public BaseServiceImpl() {
		//得到泛型化的超类
		ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
		clazz = (Class<T>) type.getActualTypeArguments()[0];
	}

	@Override
	public void saveEntity(T t) {
		dao.saveEntity(t);
	}

	@Override
	public void saveOrUpdateEntity(T t) {
		dao.saveOrUpdateEntity(t);
	}

	@Override
	public void updateEntity(T t) {
		dao.updateEntity(t);
	}

	@Override
	public void deleteEntity(T t) {
		dao.deleteEntity(t);
	}

	@Override
	public void batchEntityByHQL(String hql, Object... objects) {
		dao.batchEntityByHQL(hql, objects);
	}

	@Override
	public T loadEntity(Integer id) {
		return dao.loadEntity(id);
	}

	@Override
	public T getEntity(Integer id) {
		return dao.getEntity(id);
	}

	@Override
	public List<T> findEntityByHQL(String hql, Object... objects) {
		return dao.findEntityByHQL(hql, objects);
	}

	@Override
	public List<T> findAllEntities() {
		String hql = "from " + clazz.getSimpleName();
		return findEntityByHQL(hql);
	}

	/**
	 * 单值检索,确保查询结果有且只有一条记录
	 * @param hql
	 * @param objects
	 * @return
	 */
	@Override
	public Object uniqueResult(String hql, Object... objects) {
		return dao.uniqueResult(hql, objects);
	}

	/**
	 * 仅作为SQL语句方便书写
	 * @return
	 */
	protected Session getCurrentSession() {
		return ((BaseDaoImpl) dao).getSessionFactory().getCurrentSession();
	}
}
