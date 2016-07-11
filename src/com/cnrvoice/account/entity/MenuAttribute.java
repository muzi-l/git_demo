/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: Guohong Mao
 * @date: 2012-4-11 上午10:18:57
 * @Description:
 * 
 */
package com.cnrvoice.account.entity;

import java.io.Serializable;

public class MenuAttribute implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String url;
	private String key;
	private Boolean isVirtual;
	
	public String getUrl()
	{
		return url;
	}
	
	public void setUrl(String url)
	{
		this.url = url;
	}
	
	public String getKey()
	{
		return key;
	}
	
	public void setKey(String key)
	{
		this.key = key;
	}
	
	public Boolean getIsVirtual()
	{
		return isVirtual;
	}
	
	public void setIsVirtual(Boolean isVirtual)
	{
		this.isVirtual = isVirtual;
	}
	
}
