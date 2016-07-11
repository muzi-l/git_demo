/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2013-11-4 下午1:36:56
 * @Description:
 * 
 */
package com.cnrvoice.base.hibernate.dao;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.cnrvoice.base.dao.DaoOperator;
import com.cnrvoice.base.exception.UnCheckedException;
import com.cnrvoice.base.hibernate.po.GenericBaseHibernatePo;
import com.cnrvoice.base.util.BeanCopyUtils;
import com.cnrvoice.base.util.BeanCustomUtils;

public class GenericBaseHibernateDao<T extends GenericBaseHibernatePo<ID>, ID extends Serializable>
		extends HibernateDao<T, ID>
{
	// --------------------------------------------------------------------------
	//
	// properties
	//
	// --------------------------------------------------------------------------
	
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
	public GenericBaseHibernateDao()
	{
		super();
	}
	
	// --------------------------------------------------------------------------
	//
	// public methods
	//
	// --------------------------------------------------------------------------
	
	/**
	 * 存储实体到数据库
	 * 
	 * @param entity
	 */
	public void save(T entity)
	{
		entity.setId(null);
		entity.setCreatedTime(new Date());
		entity.setCreaterUuid(daoOperator.getOperatorUuid());
		entity.setUpdaterUuid(daoOperator.getOperatorUuid());
		
		getHibernateTemplate().save(entity);
		
		flush();
	}
	
	/**
	 * 存储实体到数据库
	 * 
	 * @param entities
	 */
	public void save(Collection<T> entities)
	{
		for (T entity : entities)
		{
			entity.setId(null);
			entity.setCreatedTime(new Date());
			entity.setCreaterUuid(daoOperator.getOperatorUuid());
			entity.setUpdaterUuid(daoOperator.getOperatorUuid());
			
			getHibernateTemplate().save(entity);
		}
		
		flush();
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
		if (StringUtils.isNotBlank(entity.getId().toString()))
		{
			T po = get(entity.getId());
			
			if (StringUtils.isNotBlank(po.getId().toString()))
			{
				BeanCopyUtils.copyNotNullProperties(po, entity,
						GenericBaseHibernatePo.baseFileds);
				
				po.setUpdaterUuid(daoOperator.getOperatorUuid());
				getHibernateTemplate().update(po);
				
				flush();
			}
			else
			{
				throw new UnCheckedException("biz.update.entityIsNull",
						"The entity that is query by uuid is null!");
			}
		}
		else
		{
			throw new UnCheckedException("biz.update.uuidIsEmpty",
					"The entity's uuid is empty!");
		}
	}
	
	/**
	 * 更新实体，可以更新null及空值
	 * 
	 * @param entity
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public void updateWithEmptyValue(T entity) throws IllegalAccessException,
			InvocationTargetException
	{
		if (StringUtils.isNotBlank(entity.getId().toString()))
		{
			T po = get(entity.getId());
			
			if (StringUtils.isNotBlank(po.getId().toString()))
			{
				BeanCustomUtils.copyProperties(po, entity,
						GenericBaseHibernatePo.baseFileds);
				
				po.setUpdaterUuid(daoOperator.getOperatorUuid());
				getHibernateTemplate().update(po);
				
				flush();
			}
			else
			{
				throw new UnCheckedException("biz.update.entityIsNull",
						"The entity that is query by uuid is null!");
			}
		}
		else
		{
			throw new UnCheckedException("biz.update.uuidIsEmpty",
					"The entity's uuid is empty!");
		}
	}
	
	/**
	 * 根据主键删除指定实体
	 * 
	 * @param uuid
	 */
	public void delete(ID uuid)
	{
		this.delete(this.load(uuid));
		
		flush();
	}
	
	/**
	 * 删除指定的实体
	 * 
	 * @param entity
	 */
	public void delete(T entity)
	{
		getHibernateTemplate().delete(entity);
		
		flush();
	}
	
	/**
	 * 删除集合中的全部实体
	 * 
	 * @param entities
	 */
	public void deleteAll(Collection<T> entities)
	{
		getHibernateTemplate().deleteAll(entities);
		
		flush();
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
}
