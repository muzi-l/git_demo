/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-6-5 下午7:38:17
 * @Description:
 * 
 */
package com.cnrvoice.base.hibernate.multi;

public class SessionFactoryContextHolder
{
	private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();
	
	public static void setSessionFactoryKey(String sessionFactoryKey)
	{
		contextHolder.set(sessionFactoryKey);
	}
	
	public static String getSessionFactoryKey()
	{
		return contextHolder.get();
	}
	
	public static void clearSessionFactoryKey()
	{
		contextHolder.remove();
	}
}
