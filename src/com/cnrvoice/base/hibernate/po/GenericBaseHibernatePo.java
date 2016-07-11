/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-6-18 下午3:01:25
 * @Description:
 * 
 */
package com.cnrvoice.base.hibernate.po;

import java.util.Date;


@SuppressWarnings("serial")
public abstract class GenericBaseHibernatePo<ID> extends HibernatePo
{
	public final static String[] baseFileds = new String[] { "sortNo", "descr",
		"createdTime", "createrUuid", "updatedTime", "updaterUuid",
		"version" };
	
	public abstract String getSortNo();
	
	public abstract void setSortNo(String sortNo);
	
	public abstract String getDescr();
	
	public abstract void setDescr(String descr);
	
	public abstract Date getCreatedTime();
	
	public abstract void setCreatedTime(Date createdTime);
	
	public abstract String getCreaterUuid();
	
	public abstract void setCreaterUuid(String createrUuid);
	
	public abstract String getUpdaterUuid();
	
	public abstract void setUpdaterUuid(String updaterUuid);
	
	public abstract Date getUpdatedTime();
	
	public abstract Integer getVersion();
	
	public abstract ID getId();
	
	public abstract void setId(ID id);
}
