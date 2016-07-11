/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: Jirui Zhao
 * @date: 2012-4-11 上午10:49:49
 * @Description:
 * 
 */
package com.cnrvoice.account.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import com.cnrvoice.account.entity.Permi;
import com.cnrvoice.account.entity.Role;
import com.cnrvoice.account.entity.RoleAndPermi;
import com.cnrvoice.account.entity.query.RoleQuery;

public interface RoleService
{
	public List<Role> checkRolesByparameters(RoleQuery roleQuery)
			throws IllegalAccessException, InvocationTargetException;
	
	public List<Role> getAllRoles();
	
	public List<RoleAndPermi> queryRoleAndPermi(String parameter);
	
	public Permi queryPermi(String parameter, String value);
	
	public List<Permi> getAllPermis();
	
	public void addRole(Role role);
	
	public void addRoleAndPermi(RoleAndPermi rolepermi);
	
	public void updateRole(Role role) throws IllegalAccessException,
			InvocationTargetException;
	
	public void deleteRoleAndPermi(List<RoleAndPermi> roleAndPermis);
	
	public Role getRoleByUuid(String uuid);
	
	public boolean isrepeat(String name);
	
	public List<Role> getRoles(String name);
}
