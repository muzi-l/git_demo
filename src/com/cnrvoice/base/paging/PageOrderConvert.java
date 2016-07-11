/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-5-21 下午3:03:27
 * @Description:
 * 
 */
package com.cnrvoice.base.paging;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

public class PageOrderConvert
{
	private PageOrderConvert()
	{
		
	}
	
	/**
	 * 转换成HibernatePageOrder
	 * 
	 * @param pageOrder
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static HibernatePageOrder toHibernatePageOrder(PageOrder pageOrder)
			throws IllegalAccessException, InvocationTargetException
	{
		HibernatePageOrder hibernatePageOrder = new HibernatePageOrder();
		
		BeanUtils.copyProperties(hibernatePageOrder, pageOrder);
		
		hibernatePageOrder.setupOrderList();
		
		return hibernatePageOrder;
	}
	
	/**
	 * 转换成MongoPageOrder
	 * 
	 * @param pageOrder
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static MongoPageOrder toMongoPageOrder(PageOrder pageOrder)
			throws IllegalAccessException, InvocationTargetException
	{
		MongoPageOrder mongoPageOrder = new MongoPageOrder();
		
		BeanUtils.copyProperties(mongoPageOrder, pageOrder);
		
		mongoPageOrder.setupKeyOrders();
		
		return mongoPageOrder;
	}
}
