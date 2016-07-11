/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-3-23 下午2:53:05
 * @Description:
 * 
 */
package com.cnrvoice.base.message;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;

public class MessageSourceBundler
{
	public static final String DEFAULT_LANGUAGE = "zh";
	
	public static final String DEFAULT_COUNTRY = "CN";
	
	private static Locale locale = new Locale(DEFAULT_LANGUAGE, DEFAULT_COUNTRY);
	
	private static ResourceBundleMessageSource staticMessageSource;
	
	/**
	 * 根据code获取消息内容
	 * 
	 * @param code
	 * @return
	 */
	public static String getMessageContent(String code)
	{
		String message = staticMessageSource.getMessage(code, null, locale);
		return message;
	}
	
	/**
	 * 根据code和locale获取消息内容
	 * 
	 * @param code
	 * @param locale
	 * @return
	 */
	public static String getMessageContent(String code, Locale locale)
	{
		String message = staticMessageSource.getMessage(code, null, locale);
		return message;
	}
	
	/**
	 * 根据code和参数params获取消息内容
	 * 
	 * @param code
	 * @param params
	 * @return
	 */
	public static String getMessageContent(String code, Object[] params)
	{
		String message = staticMessageSource.getMessage(code, params, locale);
		return message;
	}
	
	/**
	 * 根据code、参数params和locale获取消息内容
	 * 
	 * @param code
	 * @param params
	 * @param locale
	 * @return
	 */
	public static String getMessageContent(String code, Object[] params,
			Locale locale)
	{
		String message = staticMessageSource.getMessage(code, params, locale);
		return message;
	}
	
	/**
	 * 初始化静态实例
	 * 
	 * @param messageSource
	 */
	@Autowired
	public void initStaticField(ResourceBundleMessageSource messageSource)
	{
		if (staticMessageSource == null)
		{
			staticMessageSource = messageSource;
		}
	}
	
}
