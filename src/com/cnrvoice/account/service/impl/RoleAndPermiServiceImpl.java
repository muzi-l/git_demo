/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: Guohong Mao
 * @date: 2012-4-11 上午10:18:57
 * @Description:
 * 
 */
package com.cnrvoice.account.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cnrvoice.account.dao.RoleAndPermiDao;
import com.cnrvoice.account.entity.RoleAndPermi;
import com.cnrvoice.account.service.RoleAndPermiService;

@Service
public class RoleAndPermiServiceImpl implements RoleAndPermiService
{
	@Autowired
	private RoleAndPermiDao roleAndPermiDao;
	
	public RoleAndPermiDao getRoleAndPermiDao()
	{
		return roleAndPermiDao;
	}
	
	public void setRoleAndPermiDao(RoleAndPermiDao roleAndPermiDao)
	{
		this.roleAndPermiDao = roleAndPermiDao;
	}
	
	@Override
	public List<RoleAndPermi> getRoleAndPermiByRoleUuid(String uuids)
	{
		String[] roleUuids = null;
		roleUuids = uuids.split(",");
		return getRoleAndPermiDao().getRoleAndPermiByRoleUuid(roleUuids);
	}
	
}
