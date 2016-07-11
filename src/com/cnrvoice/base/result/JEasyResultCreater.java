/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-4-13 下午12:14:37
 * @Description:
 * 
 */
package com.cnrvoice.base.result;

import java.io.Serializable;
import java.util.List;

import com.cnrvoice.base.paging.PagingArrayList;

public class JEasyResultCreater extends ResultCreater
{
	
	private static JEasyResultCreater instance = new JEasyResultCreater();
	
	private JEasyResultCreater()
	{
		
	}
	
	/**
	 * 创建只包含返回数据的结果，需要传入已分页的数据
	 * 
	 * @param data
	 * @return
	 */
	public static <T> Result<?> createResult(List<T> list)
	{
		Result<?> result = null;
		JEasyGridData<T, Object> gridData = instance.new JEasyGridData<T, Object>();
		
		if (list instanceof PagingArrayList)
		{
			PagingArrayList<T> data = (PagingArrayList<T>) list;
			gridData.setTotal(data.getTotalCount());
			gridData.setRows(data.getList());
			
			result = ResultCreater.createResult(gridData);
		}
		else
		{
			result = ResultCreater.createResult(list);
		}
		
		return result;
	}
	
	/**
	 * 创建只包含返回数据的结果，需要传入已分页的数据和footer
	 * 
	 * @param <F>
	 * 
	 * @param data
	 * @param footer
	 * @return
	 */
	public static <T, F> Result<?> createResult(List<T> list, List<F> footer)
	{
		Result<?> result = null;
		JEasyGridData<T, F> gridData = instance.new JEasyGridData<T, F>();
		
		if (list instanceof PagingArrayList)
		{
			PagingArrayList<T> data = (PagingArrayList<T>) list;
			gridData.setTotal(data.getTotalCount());
			gridData.setRows(data.getList());
			gridData.setFooter(footer);
			
			result = ResultCreater.createResult(gridData);
		}
		else
		{
			result = ResultCreater.createResult(list);
		}
		
		return result;
	}
	
	public class JEasyGridData<T, F> implements Serializable
	{
		private static final long serialVersionUID = 8869963421107773583L;
		
		private Integer total;
		
		private List<T> rows;
		
		private List<F> footer;
		
		public Integer getTotal()
		{
			return total;
		}
		
		public void setTotal(Integer total)
		{
			this.total = total;
		}
		
		public List<T> getRows()
		{
			return rows;
		}
		
		public void setRows(List<T> rows)
		{
			this.rows = rows;
		}
		
		public List<F> getFooter()
		{
			return footer;
		}
		
		public void setFooter(List<F> footer)
		{
			this.footer = footer;
		}
	}
}
