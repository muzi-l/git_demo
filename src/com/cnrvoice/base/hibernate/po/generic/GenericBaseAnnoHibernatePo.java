/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-5-18 下午1:56:04
 * @Description:
 * 
 */
package com.cnrvoice.base.hibernate.po.generic;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import com.cnrvoice.base.hibernate.po.GenericBaseHibernatePo;



@SuppressWarnings("serial")
@MappedSuperclass
public abstract class GenericBaseAnnoHibernatePo<ID> extends
		GenericBaseHibernatePo<ID>
{
	public final static String[] baseFileds = new String[] { "sortNo", "descr",
			"createdTime", "createrUuid", "updatedTime", "updaterUuid",
			"version" };
	
	private String sortNo;
	
	private String descr;
	
	@Column(nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdTime;
	
	@Column(nullable = false, updatable = false)
	private String createrUuid;
	
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@Generated(GenerationTime.ALWAYS)
	private Date updatedTime;
	
	@Column(nullable = false)
	private String updaterUuid;
	
	@Version
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
