/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-5-22 下午1:16:30
 * @Description:
 * 
 */
package com.cnrvoice.base.mongo;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Sort;
import org.springframework.data.mongodb.core.query.Update;

import com.cnrvoice.base.dao.DaoOperator;
import com.cnrvoice.base.paging.MongoPageOrder;
import com.cnrvoice.base.paging.MongoPageOrderHelper.KeyOrder;
import com.cnrvoice.base.paging.PageOrder;
import com.cnrvoice.base.paging.PageOrderConvert;
import com.cnrvoice.base.paging.PagingArrayList;
import com.cnrvoice.base.paging.PagingListCreater;

public class CommonMongoDao<T extends CommonMongoPo, ID extends Serializable>
		extends MongoDaoSupport
{
	// --------------------------------------------------------------------------
	//
	// properties
	//
	// --------------------------------------------------------------------------
	
	// 实体类类型(由构造方法自动赋值)
	protected Class<T> entityClass;
	
	@Autowired
	protected DaoOperator daoOperator;
	
	// --------------------------------------------------------------------------
	//
	// constructor
	//
	// --------------------------------------------------------------------------
	/**
	 * 构造方法，根据实例类自动获取实体类类型
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public CommonMongoDao()
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
	
	/**
	 * 构造方法，根据实例类自动获取实体类类型
	 * 
	 * @param entityClass
	 */
	public CommonMongoDao(Class<T> entityClass)
	{
		this.entityClass = entityClass;
	}
	
	// --------------------------------------------------------------------------
	//
	// public methods
	//
	// --------------------------------------------------------------------------
	
	// -------------------- 基本检索、增加、修改、删除操作 ----------------
	
	/**
	 * 根据主键获取实体。如果没有相应的实体，抛出异常。
	 * 
	 * @param id
	 * @return
	 */
	public T load(ID id)
	{
		return (T) getMongoTemplate().findById(id, entityClass);
	}
	
	/**
	 * 获取全部实体
	 * 
	 * @return
	 */
	public List<T> loadAll()
	{
		return (List<T>) getMongoTemplate().findAll(entityClass);
	}
	
	/**
	 * 存储实体到数据库
	 * 
	 * @param entity
	 */
	public void insert(T entity)
	{
		getMongoTemplate().insert(entity);
	}
	
	/**
	 * 存储实体集合到数据库
	 * 
	 * @param entities
	 */
	public void insert(Collection<T> entities)
	{
		getMongoTemplate().insertAll(entities);
	}
	
	/**
	 * 更新实体
	 * 
	 * @param entity
	 */
	public void update(T entity)
	{
		getMongoTemplate().save(entity);
	}
	
	/**
	 * 根据主键删除指定实体
	 * 
	 * @param id
	 */
	public void delete(ID id)
	{
		getMongoTemplate().remove(load(id));
	}
	
	/**
	 * 删除指定的实体
	 * 
	 * @param entity
	 */
	public void delete(T entity)
	{
		getMongoTemplate().remove(entity);
	}
	
	/**
	 * 删除集合中的全部实体
	 * 
	 * @param entities
	 */
	public void deleteAll(Collection<T> entities)
	{
		for (T entity : entities)
		{
			getMongoTemplate().remove(entity);
		}
	}
	
	// -----------------------QBC(Query by Criteria)----------------------
	
	/**
	 * 根据主键获取实体。如果没有相应的实体，抛出异常
	 * 
	 * @param query
	 * @return
	 */
	public T findOne(Query query)
	{
		return getMongoTemplate().findOne(query, entityClass);
	}
	
	/**
	 * 检索满足标准的数据
	 * 
	 * @param criteria
	 * @return
	 */
	public List<T> queryByCriteria(Criteria criteria)
	{
		Query query = new Query(criteria);
		
		List<T> list = getMongoTemplate().find(query, entityClass);
		
		return list;
	}
	
	/**
	 * 检索满足标准的数据，返回指定范围的记录
	 * 
	 * @param criteria
	 * @param pageOrder
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public PagingArrayList<T> queryByCriteria(Criteria criteria,
			PageOrder pageOrder) throws IllegalAccessException,
			InvocationTargetException
	{
		MongoPageOrder mongoPageOrder = PageOrderConvert
				.toMongoPageOrder(pageOrder);
		
		List<T> list = new ArrayList<T>();
		
		Integer totalCount = getRowCount(criteria);
		
		Query query = new Query(criteria);
		
		List<KeyOrder> keyOrders = mongoPageOrder.getKeyOrders();
		if (keyOrders != null)
		{
			Sort sort = query.sort();
			for (KeyOrder keyOrder : keyOrders)
			{
				sort = sort.on(keyOrder.getKey(), keyOrder.getOrder());
			}
		}
		
		query.skip(mongoPageOrder.getSkip());
		query.limit(mongoPageOrder.getLimit());
		
		list = getMongoTemplate().find(query, entityClass);
		
		return PagingCreater.createPagingArrayList(list, totalCount);
	}
	
	/**
	 * 检索满足标准的数据，并修改记录
	 * 
	 * @param query
	 * @param update
	 * @return
	 */
	public T findAndModify(Query query, Update update)
	{
		return getMongoTemplate().findAndModify(query, update, entityClass);
	}
	
	/**
	 * 
	 */
	public void dropCollection()
	{
		if (getMongoTemplate().collectionExists(entityClass))
			getMongoTemplate().dropCollection(entityClass);
	}
	
	// --------------------------------------------------------------------------
	//
	// private methods
	//
	// --------------------------------------------------------------------------
	/**
	 * 使用指定的检索标准获取满足标准的记录数
	 * 
	 * @param criteria
	 * @return
	 */
	protected Integer getRowCount(Criteria criteria)
	{
		Query query = new Query(criteria);
		
		Long totalCount = getMongoTemplate().count(query, entityClass);
		
		return Integer.parseInt(totalCount.toString());
	}
	
	// --------------------------------------------------------------------------
	//
	// get/set
	//
	// --------------------------------------------------------------------------
	public DaoOperator getDaoOperator()
	{
		return daoOperator;
	}
	
	public void setDaoOperator(DaoOperator daoOperator)
	{
		this.daoOperator = daoOperator;
	}
	
	@Autowired
	public void setMongoFactoryAutowired(MongoFactoryBean mongoFactory)
			throws Exception
	{
		super.setMongoFactory(mongoFactory);
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
