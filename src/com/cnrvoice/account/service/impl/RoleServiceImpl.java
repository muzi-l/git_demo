/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: Jirui Zhao
 * @date: 2012-4-11 上午10:49:49
 * @Description:
 * 
 */
package com.cnrvoice.account.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cnrvoice.account.dao.PermiDao;
import com.cnrvoice.account.dao.RoleAndPermiDao;
import com.cnrvoice.account.dao.RoleDao;
import com.cnrvoice.account.entity.Permi;
import com.cnrvoice.account.entity.Role;
import com.cnrvoice.account.entity.RoleAndPermi;
import com.cnrvoice.account.entity.query.RoleQuery;
import com.cnrvoice.account.service.RoleService;
import com.cnrvoice.base.exception.UnCheckedException;

@Service
public class RoleServiceImpl implements RoleService
{
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private RoleAndPermiDao roleAndPermiDao;
	
	@Autowired
	private PermiDao permiDao;
	
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
	
	public PermiDao getPermiDao()
	{
		return permiDao;
	}
	
	public void setPermiDao(PermiDao permiDao)
	{
		this.permiDao = permiDao;
	}
	
	@Override
	@Transactional
	public List<Role> checkRolesByparameters(RoleQuery roleQuery)
			throws IllegalAccessException, InvocationTargetException
	{
		return roleDao.checkRolesByparameters(roleQuery);
		
	}
	
	@Override
	@Transactional
	public void addRole(Role role)
	{
		try
		{
			roleDao.save(role);
		}
		catch (DataIntegrityViolationException e)
		{
			e.printStackTrace();
			// throw new UnCheckedException("biz.role.100");
		}
	}
	
	@Override
	@Transactional
	public void updateRole(Role role) throws IllegalAccessException,
			InvocationTargetException
	{
		
		try
		{
			roleDao.update(role);
		}
		catch (DataIntegrityViolationException e)
		{
			throw new UnCheckedException("biz.role.100");
		}
		
	}
	
	@Override
	@Transactional
	public List<Role> getAllRoles()
	{
		return roleDao.getAllRoles();
	}
	
	@Override
	@Transactional
	public void addRoleAndPermi(RoleAndPermi rolepermi)
	{
		roleAndPermiDao.save(rolepermi);
	}
	
	@Override
	@Transactional
	public List<RoleAndPermi> queryRoleAndPermi(String parameter)
	{
		List<RoleAndPermi> rolepermis = roleAndPermiDao
				.getRoleAndPermiByRoleUuid(parameter);
		return rolepermis;
	}
	
	@Override
	@Transactional
	public void deleteRoleAndPermi(List<RoleAndPermi> roleAndPermis)
	{
		roleAndPermiDao.deleteAll(roleAndPermis);
	}
	
	@Override
	@Transactional
	public Permi queryPermi(String parameter, String value)
	{
		List<Permi> permis = permiDao.queryPermi(parameter, value);
		Permi permi = (permis != null) && permis.size() != 0 ? permis.get(0)
				: null;
		return permi;
	}
	
	@Override
	@Transactional
	public List<Permi> getAllPermis()
	{
		List<Permi> permis = permiDao.loadAll();
		return permis;
	}
	
	@Override
	@Transactional
	public Role getRoleByUuid(String uuid)
	{
		return roleDao.get(uuid);
	}
	
	@Override
	@Transactional
	public boolean isrepeat(String name)
	{
		
		Role role = new Role();
		role.setName(name);
		List<Role> list = roleDao.queryByExample(role);
		Boolean bl = list.size() > 0 ? true : false;
		if (bl == true)
		{
			throw new UnCheckedException("biz.role.100");
		}
		return bl;
	}
	
	@Override
	public List<Role> getRoles(String name)
	{
		
		List<Role> ss = roleDao.isrepeat(name);
		return ss;
	}
}
