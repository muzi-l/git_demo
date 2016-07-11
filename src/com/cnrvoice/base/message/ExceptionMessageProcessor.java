/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-4-28 下午5:08:40
 * @Description:
 * 
 */
package com.cnrvoice.base.message;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.context.NoSuchMessageException;

import com.cnrvoice.base.exception.UnCheckedException;
import com.cnrvoice.base.exception.ValidationException;

class ExceptionMessageProcessor
{
	private ExceptionMessageProcessor()
	{
		
	}
	
	/**
	 * 创建包含信息的验证结果，可以通过异常的code获取信息
	 * 
	 * @param validEx
	 * @return
	 */
	protected static Message createValidMsg(ValidationException validEx)
	{
		Message message = new Message();
		message.setLevel(MessageLevelEnum.Valid);
		
		for (int index = 0; index < validEx.getCodeList().size(); index++)
		{
			String code = validEx.getCodeList().get(index);
			Object[] params = validEx.getParamList().get(index);
			
			String content = validEx.getContentList().get(index);
			try
			{
				content = MessageSourceBundler.getMessageContent(code, params);
			}
			catch (NoSuchMessageException e)
			{
				if (StringUtils.isBlank(content))
				{
					content = "获取信息出错，编号(" + code + " )无对应的信息！";
				}
			}
			
			if (index == 0)
			{
				message.setCode(code);
				message.setContent(content);
			}
			else
			{
				message.setCode(message.getCode() + ";" + code);
				message.setContent(message.getContent() + ";" + content);
			}
			
		}
		
		return message;
	}
	
	/**
	 * 创建包含信息的验证结果，可以通过异常的code获取信息
	 * 
	 * @param authcEx
	 * @return
	 */
	protected static Message createValidMsg(AuthenticationException authcEx)
	{
		String code = "";
		String content = "";
		MessageLevelEnum level;
		// 自定义异常
		code = authcEx.getMessage();
		level = MessageLevelEnum.Valid;
		
		try
		{
			content = MessageSourceBundler.getMessageContent(code);
		}
		catch (NoSuchMessageException e)
		{
			if (StringUtils.isBlank(content))
			{
				content = "获取信息出错，编号(" + code + " )无对应的信息！";
			}
		}
		
		Message msg = new Message();
		msg.setCode(code);
		msg.setContent(content);
		msg.setLevel(level);
		
		return msg;
	}
	
	/**
	 * 创建包含信息的异常结果，可以通过异常的code获取信息，也可以直接通过异常的content获取信息
	 * 
	 * @param params
	 * @param ex
	 * @return
	 */
	protected static <T extends Exception> Message createExMsg(T ex)
	{
		String code = "";
		String content = "";
		MessageLevelEnum level;
		Object[] params = null;
		// 自定义异常
		if (ex instanceof UnCheckedException)
		{
			code = ((UnCheckedException) ex).getCode();
			content = ((UnCheckedException) ex).getContent();
			level = MessageLevelEnum.LogicEx;
			params = ((UnCheckedException) ex).getParams();
		}// 系统异常
		else
		{
			code = ex.getClass().getName();
			level = MessageLevelEnum.SysEx;
		}
		
		try
		{
			content = MessageSourceBundler.getMessageContent(code, params);
		}
		catch (NoSuchMessageException e)
		{
			if (StringUtils.isBlank(content))
			{
				content = "获取信息出错，编号(" + code + " )无对应的信息！";
			}
		}
		
		Message msg = new Message();
		msg.setCode(code);
		msg.setContent(content);
		msg.setLevel(level);
		
		return msg;
	}
}
