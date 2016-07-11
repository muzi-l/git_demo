/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-3-8 下午2:58:30
 * @Description:
 * 
 */
package com.cnrvoice.base.hibernate.dao.generic;

import java.io.Serializable;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;

import com.cnrvoice.base.hibernate.dao.GenericBaseHibernateDao;
import com.cnrvoice.base.hibernate.po.GenericBaseHibernatePo;

public class GenericAutoSfHibernateDao<T extends GenericBaseHibernatePo<ID>, ID extends Serializable>
		extends GenericBaseHibernateDao<T, ID>
{
	
	// --------------------------------------------------------------------------
	//
	// constructor
	//
	// --------------------------------------------------------------------------
	/**
	 * 构造方法，根据实例类自动获取实体类类型
	 */
	public GenericAutoSfHibernateDao()
	{
		super();
	}
	
	@Resource
	public void setSessionFactoryAutowired(SessionFactory sessionFactory)
	{
		super.setSessionFactory(sessionFactory);
	}
}
