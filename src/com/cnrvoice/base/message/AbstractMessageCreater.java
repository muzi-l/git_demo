/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-4-13 下午2:12:52
 * @Description:
 * 
 */
package com.cnrvoice.base.message;

import org.springframework.context.NoSuchMessageException;

class AbstractMessageCreater
{
	
	protected AbstractMessageCreater()
	{
		
	}
	
	/**
	 * 创建包含信息的结果
	 * 
	 * @param code
	 * @param params
	 * @param level
	 * @return
	 */
	protected static Message createMessage(String code, Object[] params,
			MessageLevelEnum level)
	{
		String content;
		try
		{
			content = MessageSourceBundler.getMessageContent(code, params);
		}
		catch (NoSuchMessageException e)
		{
			content = "获取信息出错，编号(" + code + " )无对应的信息！";
		}
		
		Message msg = new Message();
		msg.setCode(code);
		msg.setContent(content);
		msg.setLevel(level);
		
		return msg;
	}
	
}
