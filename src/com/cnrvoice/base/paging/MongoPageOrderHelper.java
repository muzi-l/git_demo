/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-5-21 下午3:31:12
 * @Description:
 * 
 */
package com.cnrvoice.base.paging;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.query.Order;

public class MongoPageOrderHelper
{
	private static final String ASC = "asc";
	
	private static MongoPageOrderHelper instance = new MongoPageOrderHelper();
	
	private MongoPageOrderHelper()
	{
		
	}
	
	/**
	 * 根据排序名称和排序类型获取排序对应列表
	 * 
	 * @param orderName
	 * @param orderType
	 * @return
	 */
	static List<KeyOrder> getKeyOrders(String[] orderName, String[] orderType)
	{
		List<KeyOrder> list = new ArrayList<KeyOrder>();
		
		int typeLength = orderType.length;
		
		for (int i = 0; i < orderName.length; i++)
		{
			KeyOrder item = instance.new KeyOrder();
			
			if (i <= typeLength && !ASC.equalsIgnoreCase(orderType[i]))
			{
				item.setOrder(Order.DESCENDING);
			}
			else
			{
				item.setOrder(Order.ASCENDING);
			}
			
			item.setKey(orderName[i]);
			list.add(item);
		}
		
		return list;
	}
	
	public class KeyOrder
	{
		private String key;
		
		private Order order;
		
		public String getKey()
		{
			return key;
		}
		
		public void setKey(String key)
		{
			this.key = key;
		}
		
		public Order getOrder()
		{
			return order;
		}
		
		public void setOrder(Order order)
		{
			this.order = order;
		}
		
	}
}
