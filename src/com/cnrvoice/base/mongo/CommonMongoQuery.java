/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-5-22 下午1:15:06
 * @Description:
 * 
 */
package com.cnrvoice.base.mongo;

import java.io.Serializable;

import com.cnrvoice.base.paging.PageOrder;

@SuppressWarnings("serial")
public class CommonMongoQuery<T extends CommonMongoPo> implements Serializable
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
