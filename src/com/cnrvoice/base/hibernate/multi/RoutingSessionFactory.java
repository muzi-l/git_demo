/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-6-5 下午7:36:42
 * @Description:
 * 
 */
package com.cnrvoice.base.hibernate.multi;

@SuppressWarnings({ "unchecked", "serial" })
public class RoutingSessionFactory extends AbstractRoutingSessionFactory
{
	@Override
	protected String determineCurrentLookupKey()
	{
		return SessionFactoryContextHolder.getSessionFactoryKey();
	}
	
}
