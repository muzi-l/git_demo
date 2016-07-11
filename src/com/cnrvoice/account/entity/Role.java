package com.cnrvoice.account.entity;

import java.util.List;

import com.cnrvoice.base.hibernate.po.generic.hbm.GenericHbmUuidHibernatePo;

public class Role extends GenericHbmUuidHibernatePo
{
	private static final long serialVersionUID = 1257279677474854494L;
	private String name;
	
	private List<Permi> permis;
	
	public String getName()
	{
		return this.name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public List<Permi> getPermis()
	{
		return permis;
	}
	
	public void setPermis(List<Permi> permis)
	{
		this.permis = permis;
	}
	
}
