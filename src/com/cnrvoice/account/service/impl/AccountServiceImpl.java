/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-3-8 ����2:58:30
 * @Description:
 * 
 */
package com.cnrvoice.account.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cnrvoice.account.dao.PermiDao;
import com.cnrvoice.account.dao.RoleAndPermiDao;
import com.cnrvoice.account.dao.RoleDao;
import com.cnrvoice.account.dao.UserAndRoleDao;
import com.cnrvoice.account.dao.UserDao;
import com.cnrvoice.account.entity.Permi;
import com.cnrvoice.account.entity.Role;
import com.cnrvoice.account.entity.RoleAndPermi;
import com.cnrvoice.account.entity.User;
import com.cnrvoice.account.entity.UserAndRole;
import com.cnrvoice.account.service.AccountService;

@Service
@Transactional
public class AccountServiceImpl implements AccountService
{
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private UserAndRoleDao userAndRoleDao;
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private RoleAndPermiDao roleAndPermiDao;
	
	@Autowired
	private PermiDao permiDao;
	
	public User getUserAccountByLoginName(String loginName)
	{
		User user = userDao.query(
				"from User where loginName='" + loginName + "'").get(0);
		List<UserAndRole> userAndRoles = userAndRoleDao
				.query("from UserAndRole where userUuid='" + user.getUuid()
						+ "'");
		List<Role> roles = new ArrayList<Role>();
		for (UserAndRole userAndRole : userAndRoles)
		{
			roles.add(roleDao.get(userAndRole.getRoleUuid()));
		}
		
		for (Role role : roles)
		{
			List<RoleAndPermi> roleAndPermis = roleAndPermiDao
					.query("from RoleAndPermi where roleUuid='"
							+ role.getUuid() + "'");
			List<Permi> permis = new ArrayList<Permi>();
			for (RoleAndPermi roleAndPermi : roleAndPermis)
			{
				permis.add(permiDao.get(roleAndPermi.getPermiUuid()));
			}
			role.setPermis(permis);
		}
		user.setRoles(roles);
		return user;
	}
	
	public UserDao getUserDao()
	{
		return userDao;
	}
	
	public void setUserDao(UserDao userDao)
	{
		this.userDao = userDao;
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
	
}
