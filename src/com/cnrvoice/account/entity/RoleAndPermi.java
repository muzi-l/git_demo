package com.cnrvoice.account.entity;

import com.cnrvoice.base.hibernate.po.generic.hbm.GenericHbmUuidHibernatePo;

public class RoleAndPermi extends GenericHbmUuidHibernatePo
{
	private static final long serialVersionUID = 5993122764336731446L;
	private String roleUuid;
	private String permiUuid;
	
	public String getRoleUuid()
	{
		return this.roleUuid;
	}
	
	public void setRoleUuid(String roleUuid)
	{
		this.roleUuid = roleUuid;
	}
	
	public String getPermiUuid()
	{
		return this.permiUuid;
	}
	
	public void setPermiUuid(String permiUuid)
	{
		this.permiUuid = permiUuid;
	}
}
