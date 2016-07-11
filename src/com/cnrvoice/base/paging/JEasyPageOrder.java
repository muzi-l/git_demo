/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-4-26 下午12:15:49
 * @Description:
 * 
 */
package com.cnrvoice.base.paging;

import org.apache.commons.lang.StringUtils;

@SuppressWarnings("serial")
public class JEasyPageOrder extends PageOrder
{
	// --------------------------------------------------------------------------
	//
	// properties
	//
	// --------------------------------------------------------------------------
	
	// 当前页条数
	private Integer pageSize;
	
	// 当前页号
	private Integer pageNumber;
	
	// 排序列名
	private String sort;
	
	// 排序方式
	private String order;
	
	// --------------------------------------------------------------------------
	//
	// get/set
	//
	// --------------------------------------------------------------------------
	public Integer getPageSize()
	{
		return pageSize;
	}
	
	public void setPageSize(Integer pageSize)
	{
		this.pageSize = pageSize;
		setupStartIndexAndLength();
	}
	
	public Integer getPageNumber()
	{
		return pageNumber;
	}
	
	public void setPageNumber(Integer pageNumber)
	{
		this.pageNumber = pageNumber;
		setupStartIndexAndLength();
	}
	
	public String getSort()
	{
		return sort;
	}
	
	public void setSort(String sort)
	{
		this.sort = sort;
		setupOrderNameAndType();
	}
	
	public String getOrder()
	{
		return order;
	}
	
	public void setOrder(String order)
	{
		this.order = order;
		setupOrderNameAndType();
	}
	
	// --------------------------------------------------------------------------
	//
	// private methods
	//
	// --------------------------------------------------------------------------
	/**
	 * 计算开始索引和分页长度
	 */
	private void setupStartIndexAndLength()
	{
		if (pageSize != null)
		{
			setLength(JEasyPageOrderHelper.getLength(pageSize));
		}
		
		if (pageSize != null && pageNumber != null)
		{
			setStartIndex(JEasyPageOrderHelper.getStartIndex(pageNumber,
					pageSize));
		}
	}
	
	/**
	 * 转换排序字段名和排序类型
	 */
	private void setupOrderNameAndType()
	{
		if (StringUtils.isNotEmpty(sort))
		{
			setOrderName(JEasyPageOrderHelper.getOrderName(sort));
		}
		if (StringUtils.isNotEmpty(order))
		{
			setOrderType(JEasyPageOrderHelper.getOrderType(order));
		}
	}
	
	public String toString()
	{
		return "JEasyPageOrder [pageSize=" + pageSize + ",pageNumber="
				+ pageNumber + ",sort=" + sort + ",order=" + order + "]";
		
	}
}
