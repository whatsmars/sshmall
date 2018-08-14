package com.$2012.dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.$2012.utils.GenericsUtils;
import com.$2012.vo.QueryResult;

/*
 * 本项目考虑到只有一个数据源（数据库），故将DAO和Service融合，即Service
 * 又考虑到各模块有很多相似的DAO操作，故将DAO抽离出来成为父类，Service extends DAO，此时事务加在DAO层即可
 * 将DAO组合进Service适合于多数据源（如数据库，XML，文件），此时事务Tx加在Service层
 *   Service Tx
 *     dbDAO
 *     xmlDAO
 *     fileDAO
 */
@SuppressWarnings("unchecked")
@Transactional
public abstract class DaoSupport<T> implements Dao<T> {
	protected Class<T> entityClass = GenericsUtils.getSuperClassGenricType(this.getClass());

	protected HibernateTemplate hibernateTemplate;

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	@Resource
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public void clear() {
		this.hibernateTemplate.clear();
	}
	
	public void delete(T entity) {
		this.hibernateTemplate.delete(entity);
	}

	/*
	 * Object...表示可变参数，相当于new Object[]{}（不懂就google），至于
	 * 为什么用Serializable，可参考hibernate里的session.get(XX.Class, Serializable)
	 */
	public void delete(Serializable... entityIds) {
		for (Serializable id : entityIds) {
			T t = this.find(id);
			if (t == null) throw new RuntimeException(
					this.entityClass.getName() + "表中没有对应的id-" + id);
			this.hibernateTemplate.delete(t);//deleteAll(Collection)
		}
	}

	public void save(T entity) {
		this.hibernateTemplate.save(entity);
	}

	public void update(T entity) {
		this.hibernateTemplate.update(entity);
	}

	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public T find(Serializable entityId) {
		if (entityId == null)
			throw new RuntimeException(this.entityClass.getName()
					+ ":传入的实体id不能为空");
		return (T) this.hibernateTemplate.get(this.entityClass, entityId);
	}

	public long getCount() {
		return (Long) this.hibernateTemplate.iterate("select count(o) from "+ getEntityName(this.entityClass)+ " o").next();
	}
	
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public QueryResult<T> getScrollData() {
		return this.getScrollData(-1, -1);
	}
	
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public QueryResult<T> getScrollData(int firstIndex, int maxResult) {
		return this.getScrollData(firstIndex, maxResult, null, null, null);
	}
	
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public QueryResult<T> getScrollData(int firstIndex, int maxResult,
			LinkedHashMap<String, String> orderby) {
		return this.getScrollData(firstIndex, maxResult, null, null, orderby);
	}
	
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public QueryResult<T> getScrollData(int firstIndex, int maxResult,
			String whereql, List<Object> queryParams) {
		return this.getScrollData(firstIndex, maxResult, whereql, queryParams, null);
	}
	
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public QueryResult<T> getScrollData(final int firstIndex, final int maxResult,
			final String whereql, final List<Object> queryParams,
			final LinkedHashMap<String, String> orderby) {
		final QueryResult qr = new QueryResult<T>();
		final Class<T> entityClazz = this.entityClass;
		final String entityName = getEntityName(entityClazz);
		this.hibernateTemplate.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery("select o from "+
						entityName+ " o "+(whereql==null || 
						"".equals(whereql.trim())? "" : "where "+ 
						whereql)+ buildOrderby(orderby));
				setQueryParams(query, queryParams);
				
				if(firstIndex!=-1 && maxResult!=-1) 
					query.setFirstResult(firstIndex).setMaxResults(maxResult);
				List<T> resultList = query.list();
				qr.setResultList(resultList);
				
				query = session.createQuery("select count(o) from "+ entityName+ " o "+(whereql==null || 
						"".equals(whereql.trim())? "": "where "+ whereql));
				setQueryParams(query, queryParams);
				qr.setTotalRecords((Long) query.uniqueResult());
				return resultList;
			}
		});
		return qr;
	}

	/*
	 * 通配符设参
	 */
	protected static void setQueryParams(Query query, List<Object> queryParams) {
		if (queryParams != null && queryParams.size() > 0) {
			for (int i = 0; i < queryParams.size(); i++) {
				query.setParameter(i, queryParams.get(i));
			}
		}
	}

	/*
	 * 构建排序语句
	 * Map<排序字段, asc/desc>
	 * Return String    order by xx asc,yy desc
	 */
	protected static String buildOrderby(LinkedHashMap<String, String> orderby) {
		StringBuilder orderbyql = new StringBuilder("");
		if (orderby != null && orderby.size() > 0) {
			orderbyql.append(" order by ");
			for (String key : orderby.keySet()) {
				orderbyql.append("o.").append(key).append(" ").append(
						orderby.get(key)).append(",");
			}
			orderbyql.deleteCharAt(orderbyql.length() - 1);
		}
		return orderbyql.toString();
	}

	/*
	 * 获取实体的名称
	 */
	protected static <E> String getEntityName(Class<E> clazz) {
		String entityName = clazz.getSimpleName();
		/*Entity entity = clazz.getAnnotation(Entity.class);
		if (entity.name() != null && !"".equals(entity.name())) {
			entityName = entity.name();
		}*/
		return entityName;
	}
	
}
