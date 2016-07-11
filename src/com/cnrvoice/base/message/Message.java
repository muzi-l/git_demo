/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-3-23 下午3:56:49
 * @Description:
 * 
 */
package com.cnrvoice.base.message;

import java.io.Serializable;

public class Message implements Serializable
{
	private static final long serialVersionUID = -8901762605530315598L;
	
	// 消息编码
	private String code;
	
	// 消息内容
	private String content;
	
	private MessageLevelEnum level;
	
	public Message()
	{
		
	}
	
	public String getCode()
	{
		return code;
	}
	
	public void setCode(String code)
	{
		this.code = code;
	}
	
	public String getContent()
	{
		return content;
	}
	
	public void setContent(String content)
	{
		this.content = content;
	}
	
	public MessageLevelEnum getLevel()
	{
		return level;
	}
	
	public void setLevel(MessageLevelEnum level)
	{
		this.level = level;
	}
	
}
