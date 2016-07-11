/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-3-8 下午2:58:30
 * @Description:
 * 
 */
package com.cnrvoice.base.mongo;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public class GenericMongoDao<T extends GenericBaseMongoPo, ID extends Serializable>
		extends CommonMongoDao<T, ID>
{
	// -------------------- 基本检索、增加、修改、删除操作 ----------------
	
	/**
	 * 存储实体到数据库
	 * 
	 * @param entity
	 */
	public void insert(T entity)
	{
		entity.setCreatedTime(new Date());
		entity.setCreaterUuid(daoOperator.getOperatorUuid());
		entity.setUpdatedTime(new Date());
		entity.setUpdaterUuid(daoOperator.getOperatorUuid());
		
		getMongoTemplate().insert(entity);
	}
	
	/**
	 * 存储实体集合到数据库
	 * 
	 * @param entities
	 */
	public void insert(Collection<T> entities)
	{
		for (T entity : entities)
		{
			entity.setCreatedTime(new Date());
			entity.setCreaterUuid(daoOperator.getOperatorUuid());
			entity.setUpdatedTime(new Date());
			entity.setUpdaterUuid(daoOperator.getOperatorUuid());
		}
		
		getMongoTemplate().insertAll(entities);
	}
	
	/**
	 * 更新实体
	 * 
	 * @param entity
	 */
	public void update(T entity)
	{
		entity.setUpdatedTime(new Date());
		entity.setUpdaterUuid(daoOperator.getOperatorUuid());
		
		getMongoTemplate().save(entity);
	}
	
	// -----------------------QBC(Query by Criteria)----------------------
	
	/**
	 * 检索满足标准的数据，并修改记录
	 * 
	 * @param query
	 * @param update
	 * @return
	 */
	public T findAndModify(Query query, Update update)
	{
		update.set("updatedTime", new Date());
		update.set("updaterUuid", daoOperator.getOperatorUuid());
		return getMongoTemplate().findAndModify(query, update, entityClass);
	}
}
