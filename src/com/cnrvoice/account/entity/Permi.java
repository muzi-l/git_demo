package com.cnrvoice.account.entity;

import com.cnrvoice.base.hibernate.po.generic.hbm.GenericHbmUuidHibernatePo;

public class Permi extends GenericHbmUuidHibernatePo
{
	private static final long serialVersionUID = -8421234405283276257L;
	private String data;
	private String value;
	
	private String key;
	
	public String getData()
	{
		return this.data;
	}
	
	public void setData(String data)
	{
		this.data = data;
	}
	
	public String getValue()
	{
		return this.value;
	}
	
	public void setValue(String value)
	{
		this.value = value;
	}
	
	public String getKey()
	{
		return key;
	}
	
	public void setKey(String key)
	{
		this.key = key;
	}
	
}
