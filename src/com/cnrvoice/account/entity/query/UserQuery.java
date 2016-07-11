package com.cnrvoice.account.entity.query;

import com.cnrvoice.account.entity.User;
import com.cnrvoice.base.hibernate.query.GenericHibernateQuery;

public class UserQuery extends GenericHibernateQuery<User>
{
	private static final long serialVersionUID = 577472666290473232L;
	
	private String loginName;
	
	private String roleName;
	
	private String userUuids;
	
	public String getLoginName()
	{
		return loginName;
	}
	
	public void setLoginName(String loginName)
	{
		this.loginName = loginName;
	}
	
	public String getRoleName()
	{
		return roleName;
	}
	
	public void setRoleName(String roleName)
	{
		this.roleName = roleName;
	}
	
	public String getUserUuids()
	{
		return userUuids;
	}
	
	public void setUserUuids(String userUuids)
	{
		this.userUuids = userUuids;
	}
	
}
