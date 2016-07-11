/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-6-5 下午2:48:00
 * @Description:
 * 
 */
package com.cnrvoice.base.hibernate.multi;

public class DataSourceContextHolder
{
	private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();
	
	public static void setDataSourceKey(String dataSourceKey)
	{
		contextHolder.set(dataSourceKey);
	}
	
	public static String getDataSourceKey()
	{
		return contextHolder.get();
	}
	
	public static void clearDataSourceKey()
	{
		contextHolder.remove();
	}
}
