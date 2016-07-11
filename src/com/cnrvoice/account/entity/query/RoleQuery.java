package com.cnrvoice.account.entity.query;

import com.cnrvoice.account.entity.Role;
import com.cnrvoice.base.hibernate.query.GenericHibernateQuery;

public class RoleQuery extends GenericHibernateQuery<Role>
{
	private static final long serialVersionUID = 577472666290473232L;
	
	private String name;
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
}
