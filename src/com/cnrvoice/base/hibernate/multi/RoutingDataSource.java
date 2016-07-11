/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-6-5 下午2:51:47
 * @Description:
 * 
 */
package com.cnrvoice.base.hibernate.multi;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class RoutingDataSource extends AbstractRoutingDataSource
{
	@Override
	protected Object determineCurrentLookupKey()
	{
		return DataSourceContextHolder.getDataSourceKey();
	}
	
}
