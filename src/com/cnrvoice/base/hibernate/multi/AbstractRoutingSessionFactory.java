/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-6-5 下午7:00:17
 * @Description:
 * 
 */
package com.cnrvoice.base.hibernate.multi;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.InitializingBean;

@SuppressWarnings({ "serial", "unchecked" })
public abstract class AbstractRoutingSessionFactory extends
		AbstractSessionFactory implements InitializingBean
{
	private Map<Object, Object> targetSessionFactories;
	
	private Object defaultTargetSessionFactory;
	
	private Map<Object, SessionFactory> resolvedSessionFactories;
	
	private SessionFactory resolvedDefaultSessionFactory;
	
	@SuppressWarnings("rawtypes")
	public void afterPropertiesSet()
	{
		if (this.targetSessionFactories == null)
		{
			throw new IllegalArgumentException(
					"Property 'targetSessionFactories' is required");
		}
		
		this.resolvedSessionFactories = new HashMap<Object, SessionFactory>(
				this.targetSessionFactories.size());
		
		for (Map.Entry entry : this.targetSessionFactories.entrySet())
		{
			Object lookupKey = resolveSpecifiedLookupKey(entry.getKey());
			SessionFactory sessionFactory = resolveSpecifiedSessionFactory(entry
					.getValue());
			this.resolvedSessionFactories.put(lookupKey, sessionFactory);
		}
		if (this.defaultTargetSessionFactory != null)
		{
			this.resolvedDefaultSessionFactory = resolveSpecifiedSessionFactory(this.defaultTargetSessionFactory);
		}
	}
	
	/**
	 * 
	 * @param lookupKey
	 * @return
	 */
	protected Object resolveSpecifiedLookupKey(Object lookupKey)
	{
		return lookupKey;
	}
	
	/**
	 * 
	 * @param sessionFactory
	 * @return
	 * @throws IllegalArgumentException
	 */
	protected SessionFactory resolveSpecifiedSessionFactory(
			Object sessionFactory) throws IllegalArgumentException
	{
		if (sessionFactory instanceof SessionFactory)
		{
			return (SessionFactory) sessionFactory;
		}
		else if (sessionFactory instanceof String)
		{
			return (SessionFactory) getWebApplicationContext().getBean(
					sessionFactory.toString());
		}
		else
		{
			throw new IllegalArgumentException(
					"Illegal sessionFactory value - only [org.hibernate.SessionFactory] and String supported: "
							+ sessionFactory);
		}
	}
	
	/**
	 * 
	 */
	public SessionFactory determineTargetSessionFactory()
	{
		Object lookupKey = determineCurrentLookupKey();
		
		SessionFactory sessionFactory = this.resolvedSessionFactories
				.get(lookupKey);
		if (sessionFactory == null && lookupKey == null)
		{
			sessionFactory = this.resolvedDefaultSessionFactory;
		}
		if (sessionFactory == null)
		{
			throw new IllegalStateException(
					"Cannot determine target SessionFactory for lookup key ["
							+ lookupKey + "]");
		}
		return sessionFactory;
	}
	
	/**
	 * 
	 * @return
	 */
	protected abstract String determineCurrentLookupKey();
	
	public Map<Object, Object> getTargetSessionFactories()
	{
		return targetSessionFactories;
	}
	
	public void setTargetSessionFactories(
			Map<Object, Object> targetSessionFactories)
	{
		this.targetSessionFactories = targetSessionFactories;
	}
	
	public Object getDefaultTargetSessionFactory()
	{
		return defaultTargetSessionFactory;
	}
	
	public void setDefaultTargetSessionFactory(
			Object defaultTargetSessionFactory)
	{
		this.defaultTargetSessionFactory = defaultTargetSessionFactory;
	}
	
	public Map<Object, SessionFactory> getResolvedSessionFactories()
	{
		return resolvedSessionFactories;
	}
	
	public void setResolvedSessionFactories(
			Map<Object, SessionFactory> resolvedSessionFactories)
	{
		this.resolvedSessionFactories = resolvedSessionFactories;
	}
	
	public SessionFactory getResolvedDefaultSessionFactory()
	{
		return resolvedDefaultSessionFactory;
	}
	
	public void setResolvedDefaultSessionFactory(
			SessionFactory resolvedDefaultSessionFactory)
	{
		this.resolvedDefaultSessionFactory = resolvedDefaultSessionFactory;
	}
}
