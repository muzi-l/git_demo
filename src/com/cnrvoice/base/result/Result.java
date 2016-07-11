/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-3-26 上午10:07:11
 * @Description:
 * 
 */
package com.cnrvoice.base.result;

import java.io.Serializable;

import com.cnrvoice.base.message.Message;

public class Result<D> implements Serializable
{
	private static final long serialVersionUID = -6085511743644254694L;
	
	private ResultLevelEnum level = ResultLevelEnum.OnlyData;
	
	private Message message;
	
	private D data;
	
	protected Result()
	{
		
	}
	
	public ResultLevelEnum getLevel()
	{
		return level;
	}
	
	public void setLevel(ResultLevelEnum level)
	{
		this.level = level;
	}
	
	public Message getMessage()
	{
		return message;
	}
	
	public void setMessage(Message message)
	{
		this.message = message;
	}
	
	public D getData()
	{
		return data;
	}
	
	public void setData(D data)
	{
		this.data = data;
	}
	
}
