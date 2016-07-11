/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2013-10-29 下午2:15:56
 * @Description:
 * 
 */
package com.cnrvoice.base.hibernate.dao.generic;

import java.io.Serializable;

import com.cnrvoice.base.hibernate.dao.GenericBaseHibernateDao;
import com.cnrvoice.base.hibernate.po.GenericBaseHibernatePo;

public class GenericManSfHibernateDao<T extends GenericBaseHibernatePo<ID>, ID extends Serializable>
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
	public GenericManSfHibernateDao()
	{
		super();
	}
}
