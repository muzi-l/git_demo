/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-3-8 ����2:58:30
 * @Description:
 * 
 */
package com.cnrvoice.base.mongo;

import org.springframework.dao.support.DaoSupport;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.core.MongoTemplate;

public class MongoDaoSupport extends DaoSupport {
	private MongoTemplate mongoTemplate;

	private MongoFactoryBean mongoFactory;

	public final void setMongoFactory(MongoFactoryBean mongoFactory)
			throws Exception {
		if (this.mongoTemplate == null || mongoFactory != this.mongoFactory) {
			this.mongoFactory = mongoFactory;
			this.mongoTemplate = createMongoTemplate(mongoFactory);
		}
	}

	protected MongoTemplate createMongoTemplate(MongoFactoryBean mongoFactory)
			throws Exception {
		return new MongoTemplate(mongoFactory.getObject(),
				mongoFactory.getDatabaseName(), new UserCredentials(
						mongoFactory.getUsername(), mongoFactory.getPassword()));
	}

	public final void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	public final MongoTemplate getMongoTemplate() {
		return this.mongoTemplate;
	}

	@Override
	protected final void checkDaoConfig() {
		if (this.mongoTemplate == null) {
			throw new IllegalArgumentException(
					"'mongoFactory' or 'mongoTemplate' is required");
		}
	}
}
