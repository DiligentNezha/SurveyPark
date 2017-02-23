package io.vicp.goradical.surveypark.dao.impl;

import io.vicp.goradical.surveypark.dao.BaseDao;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * 抽象的dao实现，专门用于继承
 * @param <T>
 */
public class BaseDaoImpl<T> implements BaseDao<T> {

	@Autowired
	private SessionFactory sessionFactory;

	private Class<T> clazz;

	public BaseDaoImpl() {
		//得到泛型化的超类
		ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
		clazz = (Class<T>) type.getActualTypeArguments()[0];
	}

	@Override
	public void saveEntity(T t) {
		sessionFactory.getCurrentSession().save(t);
	}

	@Override
	public void saveOrUpdateEntity(T t) {
		sessionFactory.getCurrentSession().saveOrUpdate(t);
	}

	@Override
	public void updateEntity(T t) {
		sessionFactory.getCurrentSession().update(t);
	}

	@Override
	public void deleteEntity(T t) {
		sessionFactory.getCurrentSession().delete(t);
	}

	@Override
	public void batchEntityByHQL(String hql, Object... objects) {
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		for (int i = 0; i < objects.length; i++) {
			query.setParameter(i, objects[i]);
		}
		query.executeUpdate();
	}

	@Override
	public void executeSQL(String sql, Object... objects) {
		SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
		for (int i = 0; i < objects.length; i++) {
			sqlQuery.setParameter(i, objects[i]);
		}
		sqlQuery.executeUpdate();
	}

	@Override
	public T loadEntity(Integer id) {
		return (T) sessionFactory.getCurrentSession().load(clazz, id);
	}

	@Override
	public T getEntity(Integer id) {
		return (T) sessionFactory.getCurrentSession().get(clazz, id);
	}

	@Override
	public List<T> findEntityByHQL(String hql, Object... objects) {
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		for (int i = 0; i < objects.length; i++) {
			query.setParameter(i, objects[i]);
		}
		return query.list();
	}

	@Override
	public Object uniqueResult(String hql, Object... objects) {
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		for (int i = 0; i < objects.length; i++) {
			query.setParameter(i, objects[i]);
		}
		return query.uniqueResult();
	}

	/**
	 * 执行原生查询
	 *
	 * @param sql
	 * @param objects
	 */
	@Override
	public List executeSQLQuery(Class clazz, String sql, Object... objects) {
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		//添加实体类
		if (clazz != null) {
			query.addEntity(clazz);
		}
		for (int i = 0; i < objects.length; i++) {
			query.setParameter(i, objects[i]);
		}
		return query.list();
	}

	/**
	 * 无实际作用，仅作为方便SQL语句书写
	 * @return
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}
