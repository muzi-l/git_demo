package com.cnrvoice.account.entity.query;

import com.cnrvoice.account.entity.UserInfo;
import com.cnrvoice.base.hibernate.query.GenericHibernateQuery;

public class UserInfoQuery extends GenericHibernateQuery<UserInfo>
{
	
    private static final long serialVersionUID = 51759756897030801L;
	
	private String loginName;
	
	private String userName;

	public String getLoginName()
	{
		return loginName;
	}

	public void setLoginName(String loginName)
	{
		this.loginName = loginName;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}
	
}
