package com.cnrvoice.unified.webservice.dto;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserInfoDto implements Serializable
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private String loginName;
	
	private String password;
	
	private String newPassword;
	
	private String userName;
	
	private String email;
	
	private String type;
	
	private String status;
	
	private Date createdTime;
	
	private String system;
	
	private String subSystemKey;
	
	
	
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
	
	public Date getCreatedTime()
	{
		return createdTime;
	}
	
	public void setCreatedTime(Date createdTime)
	{
		this.createdTime = createdTime;
	}
	
	
	public String getType()
	{
		return type;
	}
	
	public void setType(String type)
	{
		this.type = type;
	}
	
	public Integer getId()
	{
		return id;
	}
	
	public void setId(Integer id)
	{
		this.id = id;
	}
	
	public String getSystem()
	{
		return system;
	}
	
	public void setSystem(String system)
	{
		this.system = system;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getSubSystemKey() {
		return subSystemKey;
	}

	public void setSubSystemKey(String subSystemKey) {
		this.subSystemKey = subSystemKey;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
}
