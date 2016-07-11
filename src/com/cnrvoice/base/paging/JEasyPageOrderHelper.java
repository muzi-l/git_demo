/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-4-26 下午2:11:32
 * @Description:
 * 
 */
package com.cnrvoice.base.paging;

import org.apache.commons.lang.StringUtils;

public class JEasyPageOrderHelper
{
	private static final String COMMA = ",";
	
	private JEasyPageOrderHelper()
	{
		
	}
	
	/**
	 * 获取分页长度
	 * 
	 * @param pageSize
	 * @return
	 */
	static Integer getLength(Integer pageSize)
	{
		return pageSize;
	}
	
	/**
	 * 获取开始索引
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	static Integer getStartIndex(Integer pageNumber, Integer pageSize)
	{
		return (pageNumber - 1) * pageSize;
	}
	
	/**
	 * 获取排序字段名
	 * 
	 * @param sort
	 * @return
	 */
	static String[] getOrderName(String sort)
	{
		return StringUtils.split(sort, COMMA);
	}
	
	/**
	 * 
	 * 获取排序方式
	 * 
	 * @param order
	 * @return
	 */
	static String[] getOrderType(String order)
	{
		return StringUtils.split(order, COMMA);
	}
}
