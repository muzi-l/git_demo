/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-6-18 下午3:04:49
 * @Description:
 * 
 */
package com.cnrvoice.base.hibernate.po.generic;

import java.util.Date;

import com.cnrvoice.base.hibernate.po.GenericBaseHibernatePo;

@SuppressWarnings("serial")
public abstract class GenericBaseHbmHibernatePo<ID> extends
		GenericBaseHibernatePo<ID>
{
	public final static String[] baseFileds = new String[] { "sortNo", "descr",
			"createdTime", "createrUuid", "updatedTime", "updaterUuid",
			"version" };
	
	private String sortNo;
	
	private String descr;
	
	private Date createdTime;
	
	private String createrUuid;
	
	private Date updatedTime;
	
	private String updaterUuid;
	
	private Integer version;
	
	public String getSortNo()
	{
		return sortNo;
	}
	
	public void setSortNo(String sortNo)
	{
		this.sortNo = sortNo;
	}
	
	public String getDescr()
	{
		return descr;
	}
	
	public void setDescr(String descr)
	{
		this.descr = descr;
	}
	
	public Date getCreatedTime()
	{
		return createdTime;
	}
	
	public void setCreatedTime(Date createdTime)
	{
		this.createdTime = createdTime;
	}
	
	public String getCreaterUuid()
	{
		return createrUuid;
	}
	
	public void setCreaterUuid(String createrUuid)
	{
		this.createrUuid = createrUuid;
	}
	
	public String getUpdaterUuid()
	{
		return updaterUuid;
	}
	
	public void setUpdaterUuid(String updaterUuid)
	{
		this.updaterUuid = updaterUuid;
	}
	
	public Date getUpdatedTime()
	{
		return updatedTime;
	}
	
	public Integer getVersion()
	{
		return version;
	}
	
	public abstract ID getId();
	
	public abstract void setId(ID id);
}
