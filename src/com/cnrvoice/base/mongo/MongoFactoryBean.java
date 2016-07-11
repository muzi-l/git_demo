/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-3-8 ����2:58:30
 * @Description:
 * 
 */
package com.cnrvoice.base.mongo;

public class MongoFactoryBean extends
		org.springframework.data.mongodb.core.MongoFactoryBean {
	private String username;
	private String password;
	private String databaseName;

	public MongoFactoryBean() {
		super();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}
}
