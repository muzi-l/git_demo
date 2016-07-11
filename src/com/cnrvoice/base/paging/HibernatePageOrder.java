/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-4-26 下午3:31:16
 * @Description:
 * 
 */
package com.cnrvoice.base.paging;

import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.hibernate.criterion.Order;

@SuppressWarnings("serial")
public class HibernatePageOrder extends PageOrder
{
	// 排序对应列表
	private List<Order> orderList;
	
	HibernatePageOrder()
	{
		super();
	}
	
	public Integer getFirstResult()
	{
		return startIndex;
	}
	
	public Integer getMaxResults()
	{
		return length;
	}
	
	public List<Order> getOrderLisrt()
	{
		return orderList;
	}
	
	/**
	 * 转换成排序对应列表
	 */
	public void setupOrderList()
	{
		if (ArrayUtils.isNotEmpty(orderName)
				&& ArrayUtils.isNotEmpty(orderType))
		{
			orderList = HibernatePageOrderHelper.getOrderList(orderName,
					orderType);
		}
	}
}
