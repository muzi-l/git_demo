package com.cnrvoice.account.entity;

import com.cnrvoice.base.hibernate.po.generic.hbm.GenericHbmUuidHibernatePo;


public class CommonAccount extends GenericHbmUuidHibernatePo
{
	private static final long serialVersionUID = 4976455416456657963L;
	private String type;
	private String data;
	private String value;
	private String key;
	
	public String getType()
	{
		return this.type;
	}
	
	public void setType(String type)
	{
		this.type = type;
	}
	
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
