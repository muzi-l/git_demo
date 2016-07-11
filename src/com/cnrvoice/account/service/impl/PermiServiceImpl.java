/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: Guohong Mao
 * @date: 2012-4-11 上午10:18:57
 * @Description:
 * 
 */
package com.cnrvoice.account.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cnrvoice.account.dao.PermiDao;
import com.cnrvoice.account.dao.RoleAndPermiDao;
import com.cnrvoice.account.dao.RoleDao;
import com.cnrvoice.account.entity.Permi;
import com.cnrvoice.account.entity.Role;
import com.cnrvoice.account.entity.RoleAndPermi;
import com.cnrvoice.account.service.PermiService;

@Service
public class PermiServiceImpl implements PermiService
{
	@Autowired
	private PermiDao permiDao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private RoleAndPermiDao roleAndPermiDao;
	
	public PermiDao getPermiDao()
	{
		return permiDao;
	}
	
	public void setPermiDao(PermiDao permiDao)
	{
		this.permiDao = permiDao;
	}
	
	public RoleDao getRoleDao()
	{
		return roleDao;
	}
	
	public void setRoleDao(RoleDao roleDao)
	{
		this.roleDao = roleDao;
	}
	
	public RoleAndPermiDao getRoleAndPermiDao()
	{
		return roleAndPermiDao;
	}
	
	public void setRoleAndPermiDao(RoleAndPermiDao roleAndPermiDao)
	{
		this.roleAndPermiDao = roleAndPermiDao;
	}
	
	@Override
	public Permi getPermiByUuid(String uuid)
	{
		return getPermiDao().get(uuid);
	}
	
	@Override
	public Set<String> getPermiSetByRoles(String roleUuids)
	{
		Set<String> permiSet = new HashSet<String>();
		String[] ids = roleUuids.split(",");
		for (String uuid : ids)
		{
			Role role = getRoleDao().get(uuid);
			List<RoleAndPermi> roleAndPermis = getRoleAndPermiDao()
					.getRoleAndPermiByRoleUuid(role.getUuid());
			for (RoleAndPermi roleAndPermi : roleAndPermis)
			{
				Permi permi = getPermiDao().get(roleAndPermi.getPermiUuid());
				permiSet.add(permi.getKey());
			}
		}
		return permiSet;
	}
	
}
