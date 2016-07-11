/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-6-18 下午6:01:27
 * @Description:
 * 
 */
package com.cnrvoice.base.hibernate.po.generic.hbm;

import com.cnrvoice.base.hibernate.po.generic.GenericBaseHbmHibernatePo;


@SuppressWarnings("serial")
public class GenericHbmUuidHibernatePo extends
		GenericBaseHbmHibernatePo<String>
{
	private String uuid;
	
	public String getUuid()
	{
		return uuid;
	}
	
	public void setUuid(String uuid)
	{
		this.uuid = uuid;
	}
	
	@Override
	public String getId()
	{
		return uuid;
	}
	
	@Override
	public void setId(String id)
	{
		this.uuid = id;
	}
}
