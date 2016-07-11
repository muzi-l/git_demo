/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-3-8 ����2:58:30
 * @Description:
 * 
 */
package com.cnrvoice.base.ldap;

import java.io.Serializable;
import java.util.List;

import javax.naming.Name;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.support.AbstractContextMapper;


public abstract class GenericLdapDao<T extends GenericLdapPo, ID extends Serializable>
		extends LdapDaoSupport
{
	protected class GenericContextMapper extends AbstractContextMapper
	{
		protected T doMapFromContext(DirContextOperations ctx)
		{
			return context2Object(ctx);
		}
	}
	
	protected abstract void object2Context(T entity, DirContextOperations ctx);
	
	protected abstract T context2Object(DirContextOperations ctx);
	
	protected abstract Name buildDn(T entity);
	
	protected abstract Name buildDn(String loginName);
	
	public void create(T entity)
	{
		DirContextOperations ctx = new DirContextAdapter(buildDn(entity));
		object2Context(entity, ctx);
		getLdapTemplate().bind(ctx);
	}
	
	/*
	 * @see PersonDao#update(Person)
	 */
	public void update(T entity)
	{
		DirContextOperations ctx = getLdapTemplate().lookupContext(
				buildDn(entity));
		object2Context(entity, ctx);
		getLdapTemplate().modifyAttributes(ctx);
	}
	
	/*
	 * @see PersonDao#delete(Person)
	 */
	public void delete(T entity)
	{
		getLdapTemplate().unbind(buildDn(entity));
	}
	
	@SuppressWarnings("unchecked")
	public List<T> findAll()
	{
		return getLdapTemplate().search("", "(objectclass=person)",
				new GenericContextMapper());
	}
	
	@SuppressWarnings("unchecked")
	public T findByPrimaryKey(Name dn)
	{
		return (T) getLdapTemplate().lookup(dn, new GenericContextMapper());
	}
	
	@SuppressWarnings("unchecked")
	public T find(T entity)
	{
		return (T) getLdapTemplate().lookup(buildDn(entity),
				new GenericContextMapper());
	}
	
	@SuppressWarnings("unchecked")
	public T findByLoginName(String loginName)
	{
		return (T) getLdapTemplate().lookup(buildDn(loginName),
				new GenericContextMapper());
	}
	
	@Autowired
	public void setContextSourceAutowired(ContextSource contextSource)
			throws Exception
	{
		super.setContextSource(contextSource);
	}
}
