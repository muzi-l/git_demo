/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-4-13 下午12:16:27
 * @Description:
 * 
 */
package com.cnrvoice.base.message;

import org.apache.shiro.authc.AuthenticationException;

import com.cnrvoice.base.exception.ValidationException;

public class MessageCreater extends AbstractMessageCreater
{
	private MessageCreater()
	{
		
	}
	
	/**
	 * 创建包含成功信息的结果
	 * 
	 * @param code
	 * @param params
	 * @return
	 */
	public static Message createInfoMsg(String code, Object[] params)
	{
		return createMessage(code, params, MessageLevelEnum.Info);
	}
	
	/**
	 * 创建包含验证错误信息的结果
	 * 
	 * @param codeList
	 * @param paramList
	 * @return
	 */
	public static Message createValidMsg(ValidationException validEx)
	{
		return ExceptionMessageProcessor.createValidMsg(validEx);
	}
	
	/**
	 * 创建包含验证错误信息的结果
	 * 
	 * @param codeList
	 * @param paramList
	 * @return
	 */
	public static Message createValidMsg(AuthenticationException authcEx)
	{
		return ExceptionMessageProcessor.createValidMsg(authcEx);
	}
	
	/**
	 * 创建包含异常错误信息的结果
	 * 
	 * @param <T>
	 * 
	 * @param code
	 * @param params
	 * @param exception
	 * @return
	 */
	public static <T extends Exception> Message createExMsg(T ex)
	{
		return ExceptionMessageProcessor.createExMsg(ex);
	}
	
	/**
	 * 创建Email和SMS需要的Message
	 * 
	 * @param code
	 * @param params
	 * @return
	 */
	public static Message createMailSmsMessage(String code, Object[] params)
	{
		return MailSmsMessageProcessor.createMessage(code, params);
	}
}
