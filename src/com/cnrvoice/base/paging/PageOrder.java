/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-4-26 上午11:29:38
 * @Description:
 * 
 */
package com.cnrvoice.base.paging;

import java.io.Serializable;

@SuppressWarnings("serial")
public class PageOrder implements Serializable
{
	// 开始索引
	protected Integer startIndex;
	
	// 分页长度
	protected Integer length;
	
	// 排序字段名
	protected String[] orderName;
	
	// 排序方式
	protected String[] orderType;
	
	public Integer getStartIndex()
	{
		return startIndex;
	}
	
	public void setStartIndex(Integer startIndex)
	{
		this.startIndex = startIndex;
	}
	
	public Integer getLength()
	{
		return length;
	}
	
	public void setLength(Integer length)
	{
		this.length = length;
	}
	
	public String[] getOrderName()
	{
		return orderName;
	}
	
	public void setOrderName(String[] orderName)
	{
		this.orderName = orderName;
	}
	
	public String[] getOrderType()
	{
		return orderType;
	}
	
	public void setOrderType(String[] orderType)
	{
		this.orderType = orderType;
	}
}
