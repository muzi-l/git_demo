/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-4-26 上午10:22:55
 * @Description:
 * 
 */
package com.cnrvoice.base.paging;

import java.util.List;

public class PagingListCreater
{
	/**
	 * 根据list数据和总条数totalCount创建分页PagingArrayList
	 * 
	 * @param list
	 * @param totalCount
	 * @return
	 */
	protected static <T> PagingArrayList<T> createPagingList(List<T> list,
			Integer totalCount)
	{
		PagingArrayList<T> pagingList = new PagingArrayList<T>();
		pagingList.setList(list);
		pagingList.setTotalCount(totalCount);
		
		return pagingList;
	}
}
