/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2013-1-9 下午4:19:34
 * @Description:
 * 
 */
package com.cnrvoice.base.message;

public class MailSmsMessageProcessor
{
	/**
	 * 创建信息
	 * 
	 * @param code
	 * @param params
	 * @return
	 */
	protected static Message createMessage(String code, Object[] params)
	{
		String content;
		content = MessageSourceBundler.getMessageContent(code, params);
		
		Message msg = new Message();
		msg.setCode(code);
		msg.setContent(content);
		
		return msg;
	}
}
