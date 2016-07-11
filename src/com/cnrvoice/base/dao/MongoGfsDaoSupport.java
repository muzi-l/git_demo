/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-3-8 ����2:58:30
 * @Description:
 * 
 */
package com.cnrvoice.base.dao;

import com.cnrvoice.base.mongo.MongoFactoryBean;
import com.mongodb.DB;

public class MongoGfsDaoSupport
{
	private MongoFactoryBean mongoFactory;
	
	private DB database;
	
	public final void setMongoFactory(MongoFactoryBean mongoFactory)
			throws Exception
	{
		if (this.database == null || mongoFactory != this.mongoFactory)
		{
			this.mongoFactory = mongoFactory;
			this.database = mongoFactory.getObject().getDB(
					mongoFactory.getDatabaseName());
		}
	}
	
	public DB getDatabase()
	{
		return database;
	}
}
