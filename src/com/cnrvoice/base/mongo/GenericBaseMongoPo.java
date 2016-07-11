/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-5-22 下午1:13:44
 * @Description:
 * 
 */
package com.cnrvoice.base.mongo;

import java.util.Date;

@SuppressWarnings("serial")
public class GenericBaseMongoPo extends CommonMongoPo
{
	protected String sortNo;
	
	protected String descr;
	
	protected Date createdTime;
	
	protected String createrUuid;
	
	protected Date updatedTime;
	
	protected String updaterUuid;
	
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
	
	public Date getUpdatedTime()
	{
		return updatedTime;
	}
	
	public void setUpdatedTime(Date updatedTime)
	{
		this.updatedTime = updatedTime;
	}
	
	public String getUpdaterUuid()
	{
		return updaterUuid;
	}
	
	public void setUpdaterUuid(String updaterUuid)
	{
		this.updaterUuid = updaterUuid;
	}
}
