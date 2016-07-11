/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-3-30 上午11:55:40
 * @Description:
 * 
 */
package com.cnrvoice.base.log;

public class ExLogger
{
	private static String LOGGER_NAME = "ExLogger";
	
	/**
	 * 记录异常日志
	 * 
	 * @param code
	 * @param content
	 * @param t
	 */
	public static void log(String code, String content, Throwable t)
	{
		String message = "{code:" + code + ",content:" + content + "}";
		
		LoggerHandler.error(LOGGER_NAME, message, t);
	}
	
}
