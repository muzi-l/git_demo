/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-6-6 上午9:42:57
 * @Description:
 * 
 */
package com.cnrvoice.base.hibernate.multi;

import java.io.Serializable;
import java.sql.Connection;
import java.util.Map;
import java.util.Set;

import javax.naming.NamingException;
import javax.naming.Reference;

import org.hibernate.Cache;
import org.hibernate.HibernateException;
import org.hibernate.Interceptor;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.TypeHelper;
import org.hibernate.classic.Session;
import org.hibernate.engine.FilterDefinition;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.metadata.CollectionMetadata;
import org.hibernate.stat.Statistics;
import org.springframework.web.context.support.WebApplicationObjectSupport;

@SuppressWarnings("serial")
public abstract class AbstractSessionFactory extends
		WebApplicationObjectSupport implements SessionFactory
{
	public FilterDefinition getFilterDefinition(String filterName)
			throws HibernateException
	{
		return determineTargetSessionFactory().getFilterDefinition(filterName);
	}
	
	public Session openSession(Connection connection)
	{
		return determineTargetSessionFactory().openSession(connection);
	}
	
	public Session openSession(Interceptor interceptor)
			throws HibernateException
	{
		return determineTargetSessionFactory().openSession(interceptor);
	}
	
	public Session openSession(Connection connection, Interceptor interceptor)
	{
		return determineTargetSessionFactory().openSession(connection,
				interceptor);
	}
	
	public Session openSession() throws HibernateException
	{
		return determineTargetSessionFactory().openSession();
	}
	
	public Session getCurrentSession() throws HibernateException
	{
		return determineTargetSessionFactory().getCurrentSession();
	}
	
	@SuppressWarnings("rawtypes")
	public ClassMetadata getClassMetadata(Class persistentClass)
			throws HibernateException
	{
		return determineTargetSessionFactory()
				.getClassMetadata(persistentClass);
	}
	
	public ClassMetadata getClassMetadata(String entityName)
			throws HibernateException
	{
		return determineTargetSessionFactory().getClassMetadata(entityName);
	}
	
	public CollectionMetadata getCollectionMetadata(String roleName)
			throws HibernateException
	{
		return determineTargetSessionFactory().getCollectionMetadata(roleName);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map getAllClassMetadata() throws HibernateException
	{
		return determineTargetSessionFactory().getAllClassMetadata();
	}
	
	@SuppressWarnings("rawtypes")
	public Map getAllCollectionMetadata() throws HibernateException
	{
		return determineTargetSessionFactory().getAllCollectionMetadata();
	}
	
	public Statistics getStatistics()
	{
		return determineTargetSessionFactory().getStatistics();
	}
	
	public void close() throws HibernateException
	{
		determineTargetSessionFactory().close();
	}
	
	public boolean isClosed()
	{
		return determineTargetSessionFactory().isClosed();
	}
	
	@SuppressWarnings({ "rawtypes", "deprecation" })
	public void evict(Class persistentClass) throws HibernateException
	{
		determineTargetSessionFactory().evict(persistentClass);
	}
	
	@SuppressWarnings({ "rawtypes", "deprecation" })
	public void evict(Class persistentClass, Serializable id)
			throws HibernateException
	{
		determineTargetSessionFactory().evict(persistentClass, id);
	}
	
	@SuppressWarnings("deprecation")
	public void evictEntity(String entityName) throws HibernateException
	{
		determineTargetSessionFactory().evictEntity(entityName);
	}
	
	@SuppressWarnings("deprecation")
	public void evictEntity(String entityName, Serializable id)
			throws HibernateException
	{
		determineTargetSessionFactory().evictEntity(entityName, id);
	}
	
	@SuppressWarnings("deprecation")
	public void evictCollection(String roleName) throws HibernateException
	{
		determineTargetSessionFactory().evictCollection(roleName);
	}
	
	@SuppressWarnings("deprecation")
	public void evictCollection(String roleName, Serializable id)
			throws HibernateException
	{
		determineTargetSessionFactory().evictCollection(roleName, id);
	}
	
	@SuppressWarnings("deprecation")
	public void evictQueries() throws HibernateException
	{
		determineTargetSessionFactory().evictQueries();
	}
	
	@SuppressWarnings("deprecation")
	public void evictQueries(String cacheRegion) throws HibernateException
	{
		determineTargetSessionFactory().evictQueries(cacheRegion);
	}
	
	public StatelessSession openStatelessSession()
	{
		return determineTargetSessionFactory().openStatelessSession();
	}
	
	public StatelessSession openStatelessSession(Connection connection)
	{
		return determineTargetSessionFactory().openStatelessSession(connection);
	}
	
	@SuppressWarnings("rawtypes")
	public Set getDefinedFilterNames()
	{
		return determineTargetSessionFactory().getDefinedFilterNames();
	}
	
	public Reference getReference() throws NamingException
	{
		return determineTargetSessionFactory().getReference();
	}
	
	public Cache getCache()
	{
		return determineTargetSessionFactory().getCache();
	}
	
	public boolean containsFetchProfileDefinition(String name)
	{
		return determineTargetSessionFactory().containsFetchProfileDefinition(
				name);
	}
	
	public TypeHelper getTypeHelper()
	{
		return determineTargetSessionFactory().getTypeHelper();
	}
	
	public abstract SessionFactory determineTargetSessionFactory();
}
//