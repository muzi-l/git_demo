/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-5-18 上午11:14:17
 * @Description:
 * 
 */
package com.cnrvoice.base.hibernate.po.generic.anno;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

import com.cnrvoice.base.hibernate.po.generic.GenericBaseAnnoHibernatePo;


@MappedSuperclass
@SuppressWarnings("serial")
public class GenericAnnoUuidHibernatePo extends GenericBaseAnnoHibernatePo<String>
{
	@Id
	@Column(nullable = false, updatable = false)
	@GeneratedValue(generator = "genericGenerator")
	@GenericGenerator(name = "genericGenerator", strategy = "uuid")
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
