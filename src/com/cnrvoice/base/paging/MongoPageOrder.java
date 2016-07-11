/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-5-21 下午2:29:41
 * @Description:
 * 
 */
package com.cnrvoice.base.paging;

import java.util.List;

import org.apache.commons.lang.ArrayUtils;

import com.cnrvoice.base.paging.MongoPageOrderHelper.KeyOrder;

@SuppressWarnings("serial")
public class MongoPageOrder extends PageOrder
{
	private List<KeyOrder> keyOrders;
	
	MongoPageOrder()
	{
		super();
	}
	
	public Integer getSkip()
	{
		return startIndex;
	}
	
	public Integer getLimit()
	{
		return length;
	}
	
	public List<KeyOrder> getKeyOrders()
	{
		return keyOrders;
	}
	
	/**
	 * 转换成排序对应列表
	 */
	public void setupKeyOrders()
	{
		if (ArrayUtils.isNotEmpty(orderName)
				&& ArrayUtils.isNotEmpty(orderType))
		{
			keyOrders = MongoPageOrderHelper.getKeyOrders(orderName, orderType);
		}
	}
	
}
