/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-5-18 上午11:14:30
 * @Description:
 * 
 */
package com.cnrvoice.base.hibernate.po.generic.anno;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.cnrvoice.base.hibernate.po.generic.GenericBaseAnnoHibernatePo;


@MappedSuperclass
@SuppressWarnings("serial")
public class GenericAnnoIdHibernatePo extends GenericBaseAnnoHibernatePo<Integer>
{
	@Id
	@Column(nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	public Integer getId()
	{
		return id;
	}
	
	public void setId(Integer id)
	{
		this.id = id;
	}
}
