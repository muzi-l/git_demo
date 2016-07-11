/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-10-9 下午12:12:57
 * @Description:
 * 
 */
package com.cnrvoice.base.result.rest;

import java.io.Serializable;

public class RestResult<D> implements Serializable
{
	private static final long serialVersionUID = 1632133659479515435L;
	
	private String code;
	
	private String content;
	
	private D data;
	
	protected RestResult()
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
	
	public D getData()
	{
		return data;
	}
	
	public void setData(D data)
	{
		this.data = data;
	}
}
