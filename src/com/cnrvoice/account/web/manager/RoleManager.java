/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: Jirui Zhao
 * @date: 2012-4-11 上午10:49:49
 * @Description:
 * 
 */
package com.cnrvoice.account.web.manager;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cnrvoice.account.entity.Menu;
import com.cnrvoice.account.entity.Permi;
import com.cnrvoice.account.entity.Role;
import com.cnrvoice.account.entity.RoleAndPermi;
import com.cnrvoice.account.entity.query.RoleQuery;
import com.cnrvoice.account.service.MenuService;
import com.cnrvoice.account.service.PermiService;
import com.cnrvoice.account.service.RoleService;

@Service
public class RoleManager
{
	@Autowired
	RoleService roleService;
	@Autowired
	private MenuService fenzhangMenuService;
	@Autowired
	private PermiService permiService;
	
	public RoleService getRoleService()
	{
		return roleService;
	}
	
	public void setRoleService(RoleService roleService)
	{
		this.roleService = roleService;
	}
	
	public MenuService getFenzhangMenuService()
	{
		return fenzhangMenuService;
	}
	
	public void setFenzhangMenuService(MenuService fenzhangMenuService)
	{
		this.fenzhangMenuService = fenzhangMenuService;
	}
	
	public PermiService getPermiService()
	{
		return permiService;
	}
	
	public void setPermiService(PermiService permiService)
	{
		this.permiService = permiService;
	}
	
	public List<Role> checkRolesByparameters(RoleQuery roleQuery)
			throws IllegalAccessException, InvocationTargetException
	{
		return roleService.checkRolesByparameters(roleQuery);
	}
	
	public List<Role> getAllRoles()
	{
		return roleService.getAllRoles();
	}
	
	public void add(Role role)
	{
		roleService.addRole(role);
	}
	
	public void addRoleAndPermi(RoleAndPermi rolepermi)
	{
		roleService.addRoleAndPermi(rolepermi);
	}
	
	public void update(Role role) throws IllegalAccessException,
			InvocationTargetException
	{
		roleService.updateRole(role);
	}
	
	public Role getRoleByUuid(String uuid)
	{
		return roleService.getRoleByUuid(uuid);
	}
	
	public List<RoleAndPermi> queryRoleAndPermi(String parameter)
	{
		return roleService.queryRoleAndPermi(parameter);
	}
	
	public Permi queryPermi(String parameter, String value)
	{
		return roleService.queryPermi(parameter, value);
	}
	
	public List<Permi> getAllPermis()
	{
		return roleService.getAllPermis();
	}
	
	public void deleteRoleAndPermi(List<RoleAndPermi> roleAndPermis)
	{
		roleService.deleteRoleAndPermi(roleAndPermis);
	}
	
	public List<Menu> getAllMenus()
	{
		return getFenzhangMenuService().getAll();
	}
	
	public List<String> getPermiKeyListByRoles(String roleUuids)
	{
		Set<String> set = getPermiService().getPermiSetByRoles(roleUuids);
		List<String> list = new ArrayList<String>();
		for (String string : set)
		{
			list.add(string);
		}
		return list;
	}
	
	public boolean isrepeat(String name)
	{
		return roleService.isrepeat(name);
	}
}
