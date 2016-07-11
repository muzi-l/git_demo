/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-3-30 下午1:30:11
 * @Description:
 * 
 */
package com.cnrvoice.base.log;

public class CommonLogger
{
	@SuppressWarnings("rawtypes")
	public static void debug(Class clazz, String message)
	{
		LoggerHandler.debug(clazz, message);
	}
	
	@SuppressWarnings("rawtypes")
	public static void info(Class clazz, String message)
	{
		LoggerHandler.info(clazz, message);
	}
	
	@SuppressWarnings("rawtypes")
	public static void warn(Class clazz, String message)
	{
		LoggerHandler.warn(clazz, message);
	}
	
	@SuppressWarnings("rawtypes")
	public static void error(Class clazz, String message, Throwable t)
	{
		LoggerHandler.error(clazz, message, t);
	}
}
