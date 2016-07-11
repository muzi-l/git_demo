/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-3-8 ����3:06:37
 * @Description:
 * 
 */
package com.cnrvoice.account.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cnrvoice.base.hibernate.po.generic.anno.GenericAnnoUuidHibernatePo;

@Entity
@Table(name = "unified_subsystem")
public class SubSystem extends GenericAnnoUuidHibernatePo
{
	private static final long serialVersionUID = 5175975689703083401L;
	
	private String name;
	
	@Column(name = "data")
	private String key;
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
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
