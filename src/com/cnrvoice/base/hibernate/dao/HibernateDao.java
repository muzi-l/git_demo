/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-6-8 下午1:12:02
 * @Description:
 * 
 */
package com.cnrvoice.base.hibernate.dao;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.cnrvoice.base.hibernate.multi.DataSourceContextHolder;
import com.cnrvoice.base.hibernate.po.HibernatePo;
import com.cnrvoice.base.paging.HibernatePageOrder;
import com.cnrvoice.base.paging.PageOrder;
import com.cnrvoice.base.paging.PageOrderConvert;
import com.cnrvoice.base.paging.PagingArrayList;
import com.cnrvoice.base.paging.PagingListCreater;

public class HibernateDao<T extends HibernatePo, ID extends Serializable>
		extends HibernateDaoSupport
{
	// --------------------------------------------------------------------------
	//
	// properties
	//
	// --------------------------------------------------------------------------
	// 实体类类型(由构造方法自动赋值)
	protected Class<T> entityClass;
	
	protected boolean enableMultiDS = false;
	
	// --------------------------------------------------------------------------
	//
	// constructor
	//
	// --------------------------------------------------------------------------
	/**
	 * 构造方法，根据实例类自动获取实体类类型
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public HibernateDao()
	{
		this.entityClass = null;
		Class c = getClass();
		Type t = c.getGenericSuperclass();
		if (t instanceof ParameterizedType)
		{
			Type[] p = ((ParameterizedType) t).getActualTypeArguments();
			this.entityClass = (Class<T>) p[0];
		}
	}
	
	// --------------------------------------------------------------------------
	//
	// public methods
	//
	// --------------------------------------------------------------------------
	
	// -------------------- 基本检索、增加、修改、删除操作 ----------------
	/**
	 * 
	 * @param id
	 * @return
	 */
	public T get(ID id)
	{
		this.setupContext();
		
		T value = (T) getHibernateTemplate().get(entityClass, id);
		
		this.removeContext();
		
		return value;
	}
	
	/**
	 * 根据主键获取实体。如果没有相应的实体，抛出异常。
	 * 
	 * @param id
	 * @return
	 */
	public T load(ID id)
	{
		this.setupContext();
		
		T value = (T) getHibernateTemplate().load(entityClass, id);
		
		this.removeContext();
		
		return value;
	}
	
	/**
	 * 获取全部实体。
	 * 
	 * @return
	 */
	public List<T> loadAll()
	{
		this.setupContext();
		
		List<T> list = (List<T>) getHibernateTemplate().loadAll(entityClass);
		
		this.removeContext();
		
		return list;
	}
	
	/**
	 * 存储实体到数据库
	 * 
	 * @param entity
	 */
	public void save(T entity)
	{
		this.setupContext();
		
		getHibernateTemplate().save(entity);
		
		this.removeContext();
	}
	
	/**
	 * 存储实体到数据库
	 * 
	 * @param entities
	 */
	public void save(Collection<T> entities)
	{
		this.setupContext();
		
		for (T entity : entities)
		{
			getHibernateTemplate().save(entity);
		}
		
		this.removeContext();
	}
	
	/**
	 * 更新实体
	 * 
	 * @param entity
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public void update(T entity) throws IllegalAccessException,
			InvocationTargetException
	{
		this.setupContext();
		
		getHibernateTemplate().update(entity);
		
		this.removeContext();
	}
	
	/**
	 * 根据主键删除指定实体
	 * 
	 * @param id
	 */
	public void delete(ID id)
	{
		this.setupContext();
		
		this.delete(this.load(id));
		
		this.removeContext();
	}
	
	/**
	 * 删除指定的实体
	 * 
	 * @param entity
	 */
	public void delete(T entity)
	{
		this.setupContext();
		
		getHibernateTemplate().delete(entity);
		
		this.removeContext();
	}
	
	/**
	 * 删除集合中的全部实体
	 * 
	 * @param entities
	 */
	public void deleteAll(Collection<T> entities)
	{
		this.setupContext();
		
		getHibernateTemplate().deleteAll(entities);
		
		this.removeContext();
	}
	
	// -----------------------QBC(Query by Criteria)----------------------
	
	/**
	 * 根据实体类查询数据，所有属性默认操作equal
	 * 
	 * @param entity
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> queryByExample(T entity)
	{
		this.setupContext();
		
		List<T> list = getHibernateTemplate().findByExample(entity);
		
		this.removeContext();
		
		return list;
	}
	
	/**
	 * 检索满足标准的数据
	 * 
	 * @param criteria
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> queryByCriteria(DetachedCriteria criteria)
	{
		this.setupContext();
		
		List<T> list = getHibernateTemplate().findByCriteria(criteria);
		
		this.removeContext();
		
		return list;
	}
	
	/**
	 * 检索满足标准的数据，返回指定范围的记录
	 * 
	 * @param detachedCriteria
	 * @param pageOrder
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("unchecked")
	public PagingArrayList<T> queryByCriteria(
			DetachedCriteria detachedCriteria, PageOrder pageOrder)
			throws IllegalAccessException, InvocationTargetException
	{
		this.setupContext();
		
		HibernatePageOrder hibernatePageOrder = PageOrderConvert
				.toHibernatePageOrder(pageOrder);
		
		List<T> list = new ArrayList<T>();
		Integer totalCount = 0;
		
		List<Order> orderList = hibernatePageOrder.getOrderLisrt();
		
		if (orderList != null)
		{
			for (Order order : orderList)
			{
				detachedCriteria.addOrder(order);
			}
		}
		
		list = getHibernateTemplate().findByCriteria(detachedCriteria,
				hibernatePageOrder.getFirstResult(),
				hibernatePageOrder.getMaxResults());
		
		totalCount = this.getRowCount(detachedCriteria);
		
		this.removeContext();
		
		return PagingCreater.createPagingArrayList(list, totalCount);
	}
	
	// -------------------- HQL ----------------------------------------------
	
	/**
	 * 使用HSQL语句检索数据
	 * 
	 * @param queryString
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> query(String queryString)
	{
		this.setupContext();
		
		List<T> list = getHibernateTemplate().find(queryString);
		
		this.removeContext();
		
		return list;
	}
	
	/**
	 * 使用带参数的HSQL语句检索数据
	 * 
	 * @param queryString
	 * @param values
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> query(String queryString, Object[] values)
	{
		this.setupContext();
		
		List<T> list = getHibernateTemplate().find(queryString, values);
		
		this.removeContext();
		
		return list;
	}
	
	/**
	 * 检索满足HQL的数据，返回指定范围的记录 仅一对一的内连接查询，分页才有效
	 * 
	 * @param selectString
	 * @param queryString
	 * @param orderString
	 * @param pageOrder
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	public List<T> query(final String hql, PageOrder pageOrder)
			throws IllegalAccessException, InvocationTargetException
	{
		this.setupContext();
		
		final HibernatePageOrder hibernatePageOrder = PageOrderConvert
				.toHibernatePageOrder(pageOrder);
		
		List<T> list = query(hql, hibernatePageOrder.getFirstResult(),
				hibernatePageOrder.getMaxResults());
		
		this.removeContext();
		
		return list;
	}
	
	// --------------------------------------------------------------------------
	//
	// protected methods
	//
	// --------------------------------------------------------------------------
	/**
	 * 创建与会话无关的检索标准
	 * 
	 * @return
	 */
	protected DetachedCriteria createDetachedCriteria()
	{
		return DetachedCriteria.forClass(this.entityClass);
	}
	
	/**
	 * 创建与会话无关的检索标准
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	protected DetachedCriteria createDetachedCriteria(Class clazz)
	{
		return DetachedCriteria.forClass(clazz);
	}
	
	/**
	 * 创建与会话绑定的检索标准
	 * 
	 * @return
	 */
	protected Criteria createCriteria()
	{
		return this.createDetachedCriteria().getExecutableCriteria(
				this.getSession());
	}
	
	// --------------------------------------------------------------------------
	//
	// private methods
	//
	// --------------------------------------------------------------------------
	/**
	 * 使用指定的检索标准检索数据，返回指定统计值(max,min,avg,sum)
	 * 
	 * @param criteria
	 * @param propertyName
	 * @param StatName
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	protected Object getStaticColumn(DetachedCriteria criteria,
			String propertyName, String StatName)
	{
		this.setupContext();
		
		if (StatName.toLowerCase().equals("max"))
			criteria.setProjection(Projections.max(propertyName));
		else if (StatName.toLowerCase().equals("min"))
			criteria.setProjection(Projections.min(propertyName));
		else if (StatName.toLowerCase().equals("avg"))
			criteria.setProjection(Projections.avg(propertyName));
		else if (StatName.toLowerCase().equals("sum"))
			criteria.setProjection(Projections.sum(propertyName));
		else
			return null;
		List list = this.queryByCriteria(criteria, 0, 1);
		
		this.removeContext();
		
		return list.get(0);
	}
	
	/**
	 * 使用指定的检索标准获取满足标准的记录数
	 * 
	 * @param criteria
	 * @return
	 */
	protected Integer getRowCount(DetachedCriteria criteria)
	{
		this.setupContext();
		
		criteria.setProjection(Projections.rowCount());
		List<?> list = getHibernateTemplate().findByCriteria(criteria, 0, 1);
		
		Integer totalCount = 0;
		
		if (CollectionUtils.isNotEmpty(list))
		{
			totalCount = Integer.parseInt(list.get(0).toString());
		}
		
		this.removeContext();
		
		return totalCount;
	}
	
	/**
	 * 检索满足HQL的数据，返回指定范围的记录
	 * 
	 * @param hql
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected List<T> query(final String hql, final int firstResult,
			final int maxResults)
	{
		return getHibernateTemplate().executeFind(new HibernateCallback<List>()
		{
			public List doInHibernate(Session session)
					throws HibernateException, SQLException
			{
				setupContext();
				
				Query query = session.createQuery(hql);
				query.setFirstResult(firstResult);
				query.setMaxResults(maxResults);
				List list = query.list();
				
				removeContext();
				
				return list;
			}
		});
	}
	
	/**
	 * 检索满足标准的数据，返回指定范围的记录
	 * 
	 * @param criteria
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected List<T> queryByCriteria(DetachedCriteria criteria,
			int firstResult, int maxResults)
	{
		this.setupContext();
		
		List<T> list = getHibernateTemplate().findByCriteria(criteria,
				firstResult, maxResults);
		
		this.removeContext();
		
		return list;
	}
	
	/**
	 * flush()后只是将Hibernate缓存中的数据提交到数据库,如果这时数据库处在一个事物当中,则数据库将这些SQL语句缓存起来。
	 * 当Hibernate进行commit()时,会告诉数据库,你可以真正提交了, 这时数据才会永久保存下来,也就是被持久化了.
	 */
	protected void flush()
	{
		this.setupContext();
		
		getHibernateTemplate().flush();
		
		this.removeContext();
	}
	
	/**
		 * 
		 */
	protected void setupContext()
	{
		
	}
	
	protected void setupDataSourceKey(String dataSourceKey)
	{
		enableMultiDS = true;
		
		DataSourceContextHolder.setDataSourceKey(dataSourceKey);
	}
	
	/**
		 * 
		 */
	private void removeContext()
	{
		if (enableMultiDS)
		{
			DataSourceContextHolder.clearDataSourceKey();
		}
	}
	
	// --------------------------------------------------------------------------
	//
	// inner class
	//
	// --------------------------------------------------------------------------
	protected static class PagingCreater extends PagingListCreater
	{
		/**
		 * 根据list数据和总条数totalCount创建分页PagingArrayList
		 * 
		 * @param list
		 * @param totalCount
		 * @return
		 */
		protected static <T> PagingArrayList<T> createPagingArrayList(
				List<T> list, Integer totalCount)
		{
			return createPagingList(list, totalCount);
		}
	}
}
