/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-3-8 ����3:06:37
 * @Description:
 * 
 */
package com.cnrvoice.account.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.cnrvoice.base.hibernate.po.generic.anno.GenericAnnoIdHibernatePo;

@Entity
@Table(name = "unified_userinfo")
public class UserInfo extends GenericAnnoIdHibernatePo
{
	private static final long serialVersionUID = 5175975689703083401L;
	
	private String loginName;
	
	private String password;
	
	private String userName;
	
	private String email;
	
	private String type;
	
	private String status;
	@Transient
	private String typeValue;
	@Transient
	private String system;
	
	public String getLoginName()
	{
		return loginName;
	}
	
	public void setLoginName(String loginName)
	{
		this.loginName = loginName;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public void setPassword(String password)
	{
		this.password = password;
	}
	
	public String getUserName()
	{
		return userName;
	}
	
	public void setUserName(String userName)
	{
		this.userName = userName;
	}
	
	public String getEmail()
	{
		return email;
	}
	
	public void setEmail(String email)
	{
		this.email = email;
	}
	
	public String getSystem()
	{
		return system;
	}
	
	public void setSystem(String system)
	{
		this.system = system;
	}
	
	public String getType()
	{
		return type;
	}
	
	public void setType(String type)
	{
		this.type = type;
	}

	public String getTypeValue()
	{
		return typeValue;
	}

	public void setTypeValue(String typeValue)
	{
		this.typeValue = typeValue;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}
	
}
