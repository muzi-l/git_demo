/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-3-30 下午12:32:11
 * @Description:
 * 
 */
package com.cnrvoice.base.log;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class LoggerHandler
{
	private static Map<String, Logger> loggerMapping = new HashMap<String, Logger>();
	
	/**
	 * 记录debug日志
	 * 
	 * @param clazz
	 * @param message
	 */
	@SuppressWarnings("rawtypes")
	public static void debug(Class clazz, String message)
	{
		getLogger(clazz).debug(message);
	}
	
	/**
	 * 记录debug日志
	 * 
	 * @param name
	 * @param message
	 */
	public static void debug(String name, String message)
	{
		getLogger(name).debug(message);
	}
	
	/**
	 * 记录info日志
	 * 
	 * @param clazz
	 * @param message
	 */
	@SuppressWarnings("rawtypes")
	public static void info(Class clazz, String message)
	{
		getLogger(clazz).info(message);
	}
	
	/**
	 * 记录info日志
	 * 
	 * @param name
	 * @param message
	 */
	public static void info(String name, String message)
	{
		getLogger(name).info(message);
	}
	
	/**
	 * 记录warn日志
	 * 
	 * @param clazz
	 * @param message
	 */
	@SuppressWarnings("rawtypes")
	public static void warn(Class clazz, String message)
	{
		getLogger(clazz).warn(message);
	}
	
	/**
	 * 记录warn日志
	 * 
	 * @param name
	 * @param message
	 */
	public static void warn(String name, String message)
	{
		getLogger(name).warn(message);
	}
	
	/**
	 * 记录error日志
	 * 
	 * @param clazz
	 * @param message
	 * @param t
	 */
	@SuppressWarnings("rawtypes")
	public static void error(Class clazz, String message, Throwable t)
	{
		getLogger(clazz).error(message, t);
	}
	
	/**
	 * 记录error日志
	 * 
	 * @param name
	 * @param message
	 * @param t
	 */
	public static void error(String name, String message, Throwable t)
	{
		getLogger(name).error(message, t);
	}
	
	/**
	 * 根据clazz获取logger
	 * 
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private static Logger getLogger(Class clazz)
	{
		Logger logger = loggerMapping.get(clazz.getName());
		
		if (logger == null)
		{
			Logger temp = Logger.getLogger(clazz);
			loggerMapping.put(clazz.getName(), temp);
			logger = temp;
		}
		
		return logger;
	}
	
	/**
	 * 根据名称获取logger
	 * 
	 * @param name
	 * @return
	 */
	private static Logger getLogger(String name)
	{
		Logger logger = loggerMapping.get(name);
		
		if (logger == null)
		{
			Logger temp = Logger.getLogger(name);
			loggerMapping.put(name, temp);
			logger = temp;
		}
		
		return logger;
	}
}
