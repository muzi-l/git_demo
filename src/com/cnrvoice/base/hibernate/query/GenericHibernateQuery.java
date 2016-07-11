/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-4-25 上午11:12:59
 * @Description:
 * 
 */
package com.cnrvoice.base.hibernate.query;

import com.cnrvoice.base.hibernate.po.GenericBaseHibernatePo;
import com.cnrvoice.base.paging.PageOrder;

@SuppressWarnings({ "serial", "rawtypes" })
public class GenericHibernateQuery<T extends GenericBaseHibernatePo> extends
		HibernateQuery<T>
{
	// 分页排序vo
	private PageOrder pageOrder;
	
	public PageOrder getPageOrder()
	{
		return pageOrder;
	}
	
	public void setPageOrder(PageOrder pageOrder)
	{
		this.pageOrder = pageOrder;
	}
}
