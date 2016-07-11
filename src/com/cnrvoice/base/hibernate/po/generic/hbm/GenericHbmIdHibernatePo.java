/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-6-18 下午6:03:34
 * @Description:
 * 
 */
package com.cnrvoice.base.hibernate.po.generic.hbm;

import com.cnrvoice.base.hibernate.po.generic.GenericBaseHbmHibernatePo;


@SuppressWarnings("serial")
public class GenericHbmIdHibernatePo extends GenericBaseHbmHibernatePo<Integer>
{
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
