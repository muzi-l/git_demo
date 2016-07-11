/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-3-30 上午11:56:00
 * @Description:
 * 
 */
package com.cnrvoice.base.log;

public class BizLogger
{
	private static String LOGGER_NAME = "BizLogger";
	
	/**
	 * 记录业务日志
	 * 
	 * @param message
	 */
	public static void log(String message)
	{
		LoggerHandler.info(LOGGER_NAME, message);
	}
	
}
