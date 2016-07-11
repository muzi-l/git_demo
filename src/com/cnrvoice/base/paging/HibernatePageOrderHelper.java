/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-4-26 下午3:57:06
 * @Description:
 * 
 */
package com.cnrvoice.base.paging;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Order;

public class HibernatePageOrderHelper
{
	private static final String ASC = "asc";
	
	@SuppressWarnings("unused")
	private static final String DESC = "desc";
	
	private static HibernatePageOrderHelper instance = new HibernatePageOrderHelper();
	
	private HibernatePageOrderHelper()
	{
		
	}
	
	/**
	 * 根据排序名称和排序类型获取排序对应列表
	 * 
	 * @param orderName
	 * @param orderType
	 * @return
	 */
	static List<Order> getOrderList(String[] orderName, String[] orderType)
	{
		List<Order> orderList = new ArrayList<Order>();
		int typeLength = orderType.length;
		for (int i = 0; i < orderName.length; i++)
		{
			boolean isAsc = true;
			if (i <= typeLength && !ASC.equalsIgnoreCase(orderType[i]))
			{
				isAsc = false;
			}
			
			orderList.add(instance.new HibernateOrder(orderName[i], isAsc));
		}
		return orderList;
	}
	
	@SuppressWarnings("serial")
	private class HibernateOrder extends Order
	{
		HibernateOrder(String propertyName, boolean ascending)
		{
			super(propertyName, ascending);
		}
	}
}
