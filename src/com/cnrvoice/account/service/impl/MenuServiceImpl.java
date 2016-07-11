/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: Guohong Mao
 * @date: 2012-4-11 上午10:18:57
 * @Description:
 * 
 */
package com.cnrvoice.account.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cnrvoice.account.dao.MenuDao;
import com.cnrvoice.account.dao.PermiDao;
import com.cnrvoice.account.dao.RoleAndPermiDao;
import com.cnrvoice.account.dao.RoleDao;
import com.cnrvoice.account.dao.UserAndRoleDao;
import com.cnrvoice.account.entity.Menu;
import com.cnrvoice.account.service.MenuService;

@Service
public class MenuServiceImpl implements MenuService
{
	@Autowired
	private MenuDao menuDao;
	
	@Autowired
	private UserAndRoleDao userAndRoleDao;
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private PermiDao permiDao;
	
	@Autowired
	private RoleAndPermiDao roleAndPermiDao;
	
	public MenuDao getMenuDao()
	{
		return menuDao;
	}
	
	public void setMenuDao(MenuDao menuDao)
	{
		this.menuDao = menuDao;
	}
	
	public UserAndRoleDao getUserAndRoleDao()
	{
		return userAndRoleDao;
	}
	
	public void setUserAndRoleDao(UserAndRoleDao userAndRoleDao)
	{
		this.userAndRoleDao = userAndRoleDao;
	}
	
	public RoleDao getRoleDao()
	{
		return roleDao;
	}
	
	public void setRoleDao(RoleDao roleDao)
	{
		this.roleDao = roleDao;
	}
	
	public PermiDao getPermiDao()
	{
		return permiDao;
	}
	
	public void setPermiDao(PermiDao permiDao)
	{
		this.permiDao = permiDao;
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
	public void save(Menu menu)
	{
//		getMenuDao().insert(menu);
	}
	
	@Override
	public Menu getById(String id)
	{
		return getMenuDao().load(id);
	}
	
	@Override
	public List<Menu> getAll()
	{
		List<Menu> list = new ArrayList<Menu>();
		list.add(getMenuDao().getRoot());
		return list;
	}
	
	@Override
	public List<Menu> getMenuByUser(Set<String> permiSet)
	{
		// 1
		// Set<String> permiSet = new HashSet<String>();
		// User user =
		// getFenzhangMenuManager().getUserAndRoleService().getByIdUser(userId);
		// UserAndRole
		/*
		 * List<UserAndRole> userAndRoles =
		 * getUserAndRoleDao().getUserAndRoleByUserUuid(user.getUuid()); for
		 * (UserAndRole userAndRole : userAndRoles) { Role role =
		 * getRoleDao().get(userAndRole.getRoleUuid()); List<RoleAndPermi>
		 * roleAndPermis =
		 * getRoleAndPermiDao().getRoleAndPermiByRoleUuid(role.getUuid()); for
		 * (RoleAndPermi roleAndPermi : roleAndPermis) { Permi permi =
		 * getPermiDao().get(roleAndPermi.getPermiUuid());
		 * permiSet.add(permi.getKey()); } }
		 */
		// 2
		List<Menu> list = new ArrayList<Menu>();
		list = getAll();
		
		// 3
		List<Menu> lst = new ArrayList<Menu>();
		List<Menu> removed = currentUsersMenu(list, permiSet, lst);
		// 移除掉没有权限的菜单
		return remove(list, removed);
	}
	
	private List<Menu> currentUsersMenu(List<Menu> menus, Set<String> permiSet,
			List<Menu> list)
	{
		if (null != menus)
		{
			for (Menu menu : menus)
			{
				Set<String> set = new HashSet<String>();
				Set<String> subMenuPermiSet = getSubMenuPermiSet(menu, set);
				// 是否有当前菜单的子菜单权限
				boolean isHasSubMenuPermi = false;
				if (!subMenuPermiSet.isEmpty())
				{
					for (String key : subMenuPermiSet)
					{
						if (permiSet.contains(key))
						{
							isHasSubMenuPermi = true;
							break;
						}
					}
				}
				// 有子菜单的权限，当前菜单不能被删除
				if (isHasSubMenuPermi)
				{
					currentUsersMenu(menu.getChildren(), permiSet, list);
				}
				else
				{ // 无权限的子菜单
					//
					if (!permiSet.contains(menu.getAttributes().getKey()))
					{
						// menus.remove(menu);
						list.add(menu);
					}
				}
			}
		}
		return list;
	}
	
	private Set<String> getSubMenuPermiSet(Menu menu, Set<String> set)
	{
		if (null != menu.getChildren())
		{
			for (Menu m : menu.getChildren())
			{
				set.add(m.getAttributes().getKey());
				getSubMenuPermiSet(m, set);
			}
		}
		return set;
	}
	
	private List<Menu> remove(List<Menu> all, List<Menu> removed)
	{
		for (Menu fenzhangMenu : removed)
		{
			removeFenzhangMenu(all, fenzhangMenu);
		}
		return all;
	}
	
	private List<Menu> removeFenzhangMenu(List<Menu> menus, Menu removed)
	{
		if (null != menus)
		{
			for (int i = 0; i < menus.size(); i++)
			{
				if (menus.get(i).getId().equals(removed.getId()))
				{
					menus.remove(menus.get(i));
					break;
				}
				else
				{
					removeFenzhangMenu(menus.get(i).getChildren(), removed);
				}
			}
		}
		return menus;
	}
}
