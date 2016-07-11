/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: Guohong Mao
 * @date: 2012-4-11 上午10:18:57
 * @Description:
 * 
 */
package com.cnrvoice.account.web.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cnrvoice.account.entity.Menu;
import com.cnrvoice.account.entity.Permi;
import com.cnrvoice.account.entity.Role;
import com.cnrvoice.account.entity.RoleAndPermi;
import com.cnrvoice.account.entity.UserAndRole;
import com.cnrvoice.account.service.MenuService;
import com.cnrvoice.account.service.ManagerUserService;
import com.cnrvoice.account.service.PermiService;
import com.cnrvoice.account.service.RoleAndPermiService;
import com.cnrvoice.account.service.RoleService;

@Service
public class MenuManager
{
	@Autowired
	private MenuService fenzhangMenuService;
	@Autowired
	private ManagerUserService userAndRoleService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private RoleAndPermiService roleAndPermiService;
	@Autowired
	private PermiService permiService;
	
	public MenuService getFenzhangMenuService()
	{
		return fenzhangMenuService;
	}
	
	public void setFenzhangMenuService(MenuService fenzhangMenuService)
	{
		this.fenzhangMenuService = fenzhangMenuService;
	}
	
	public ManagerUserService getUserAndRoleService()
	{
		return userAndRoleService;
	}
	
	public void setUserAndRoleService(ManagerUserService userAndRoleService)
	{
		this.userAndRoleService = userAndRoleService;
	}
	
	public RoleService getRoleService()
	{
		return roleService;
	}
	
	public void setRoleService(RoleService roleService)
	{
		this.roleService = roleService;
	}
	
	public RoleAndPermiService getRoleAndPermiService()
	{
		return roleAndPermiService;
	}
	
	public void setRoleAndPermiService(RoleAndPermiService roleAndPermiService)
	{
		this.roleAndPermiService = roleAndPermiService;
	}
	
	public PermiService getPermiService()
	{
		return permiService;
	}
	
	public void setPermiService(PermiService permiService)
	{
		this.permiService = permiService;
	}
	
	public void save(Menu menu)
	{
		getFenzhangMenuService().save(menu);
	}
	
	public Menu getById(String id)
	{
		return getFenzhangMenuService().getById(id);
	}
	
	public List<UserAndRole> getUserAndRoleByUserUuid(String uuid)
	{
		return getUserAndRoleService().getUserAndRoleByUserUuid(uuid);
	}
	
	public Role getRoleByUuid(String uuid)
	{
		return getRoleService().getRoleByUuid(uuid);
	}
	
	public List<RoleAndPermi> getRoleAndPermiByRoleUuid(String uuid)
	{
		return getRoleAndPermiService().getRoleAndPermiByRoleUuid(uuid);
	}
	
	public Permi getPermiByUuid(String uuid)
	{
		return getPermiService().getPermiByUuid(uuid);
	}
	
	public List<Menu> getAll()
	{
		return getFenzhangMenuService().getAll();
	}
	
}
