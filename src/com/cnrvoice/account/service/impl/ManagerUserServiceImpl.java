package com.cnrvoice.account.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cnrvoice.account.constant.AccountConst;
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
import com.cnrvoice.account.entity.query.UserQuery;
import com.cnrvoice.account.service.ManagerUserService;
import com.cnrvoice.base.exception.UnCheckedException;
import com.cnrvoice.base.property.ContextPropertiesHolder;
import com.cnrvoice.unified.webservice.dto.UserInfoDto;
import com.cnrvoice.unified.webservice.rs.client.UserInfoClient;

@Service
public class ManagerUserServiceImpl implements ManagerUserService
{
	
	@Autowired
	private UserDao userDao;
	@Autowired
	private UserAndRoleDao userAndRoleDao;
	@Autowired
	private RoleAndPermiDao roleAndPermiDao;
	@Autowired
	private PermiDao permiDao;
	@Autowired
	private UserInfoClient userInfoClient;
	
	public RoleDao getRoleDao()
	{
		return roleDao;
	}
	
	public void setRoleDao(RoleDao roleDao)
	{
		this.roleDao = roleDao;
	}
	
	@Autowired
	private RoleDao roleDao;
	
	public UserAndRoleDao getUserAndRoleDao()
	{
		return userAndRoleDao;
	}
	
	public void setUserAndRoleDao(UserAndRoleDao userAndRoleDao)
	{
		this.userAndRoleDao = userAndRoleDao;
	}
	
	public UserDao getUserDao()
	{
		return userDao;
	}
	
	public void setUserDao(UserDao userDao)
	{
		this.userDao = userDao;
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
	
	
	public UserInfoClient getUserInfoClient() {
		return userInfoClient;
	}

	public void setUserInfoClient(UserInfoClient userInfoClient) {
		this.userInfoClient = userInfoClient;
	}

	Logger loger = Logger.getLogger(ManagerUserServiceImpl.class);
	
	@Override
	public List<User> loadAll()
	{
		return userDao.loadAll();
	}
	
	@Override
	public List<UserInfoDto> loadAllUserInfo(String type) throws IllegalAccessException,
			InvocationTargetException
	{
		String systemName = (String) ContextPropertiesHolder
				.getPropertyItem(AccountConst.SYSTEM_PROJECT_NAME);
		
		List<UserInfoDto> userInfos = userInfoClient.loadAll(systemName,type);
		
		List<UserInfoDto> uf = new ArrayList<UserInfoDto>();
		
		List<User> users = userDao.query("from User");
		for (UserInfoDto userInfo : userInfos)
		{
			for (User user : users)
			{
				
				if (userInfo.getLoginName().equals(user.getLoginName()))
				{
					uf.add(userInfo);
				}
				
			}
			
		}
		for (UserInfoDto userInfo : uf)
		{
			userInfos.remove(userInfo);
			loger.debug("成功移除");
		}
		
		return userInfos;
	}
	
	@Override
	public void updateUser(User user) throws IllegalAccessException,
			InvocationTargetException
	{
		
		userDao.update(user);
		
	}
	
	// 根据ID获取对象
	@Override
	public User getByIdUser(String uuid)
	{
		return userDao.get(uuid);
	}
	
	@Override
	public void addUserAndRole(UserAndRole userAndRole)
	{
		
		userAndRoleDao.save(userAndRole);
		
	}
	
	@Override
	public User findByUuid(String LoginName)
	{
		User user = new User();
		user.setLoginName(LoginName);
		
		return userDao.queryByExample(user).get(0);
		
	}
	
	@Override
	public List<UserAndRole> getUserAndRoleByUserUuid(String uuid)
	{
		
		return getUserAndRoleDao().getUserAndRoleByUserUuid(uuid);
	}
	
	// 查询所有用户及角色
	public List<User> getUserAccountByLoginName()
	{
		List<User> users = userDao.loadAll();
		for (User user : users)
		{
			List<UserAndRole> userAndRoles = userAndRoleDao
					.query("from UserAndRole where userUuid='" + user.getUuid()
							+ "'");
			// and 4isDeleted!='1'
			List<Role> roles = new ArrayList<Role>();
			for (UserAndRole userAndRole : userAndRoles)
			{
				roles.add(roleDao.get(userAndRole.getRoleUuid()));
			}
			
			StringBuilder sb = new StringBuilder();
			String roleNameString = "";
			if (!roles.isEmpty())
			{
				int i = 1;
				for (Role role : roles)
				{
					if (null != role)
					{
						sb.append(role.getName());
						if (i < roles.size())
							sb.append(",");
					}
				}
				if (sb.toString().endsWith(","))
				{
					roleNameString = sb.substring(0, sb.length() - 1);
					i++;
				}
				else
				{
					roleNameString = sb.toString();
				}
				
				user.setRoleName(roleNameString);
				
			}
			
		}
		
		return users;
	}
	
	public void deleteUserAndRole(UserAndRole userAndRole)
	{
		
		userAndRoleDao.delete(userAndRole);
		loger.debug("执行删除角色成功");
	}
	
	/**
	 * 查询LoginName是否已存在
	 * 
	 * @param LoginName
	 * @return
	 */
	public boolean getUserByName(String LoginName)
	{
		
		User user = new User();
		user.setLoginName(LoginName);
		List<User> users = userDao.queryByExample(user);
		if (users.isEmpty())
		{
			return false;
		}
		return true;
	}
	
	/**
	 * 判断角色是否已拥有权限
	 * 
	 * @param uuid
	 * @return
	 */
	public List<UserAndRole> finIfUserAndRole(String uuid)
	{
		String hsql = "from UserAndRole where userUuid='" + uuid + "'";
		List<UserAndRole> userAndRole = userAndRoleDao.query(hsql);
		return userAndRole;
		
	}
	
	@Override
	public void addUser(User user)
	{
		boolean userIf = getUserByName(user.getLoginName());
		if (userIf)
		{
			throw new UnCheckedException("biz.user.110", "");
		}
		else
		{
			try
			{
				userDao.save(user);
			}
			catch (DataIntegrityViolationException e)
			{
				throw new UnCheckedException("biz.user.110", "");
			}
		}
		
	}
	
	public List<User> getUserByWhere(UserQuery user1, String[] roleWhere)
			throws IllegalAccessException, InvocationTargetException
	{
		
		List<User> userListWhere = new ArrayList<User>();
		
		if (roleWhere == null && user1.getLoginName() != null
				&& user1.getLoginName() != "")
		{
			List<User> loginNameUserList = userDao.byUserWhere(user1);
			
			for (User user : loginNameUserList)
			{
				userListWhere.add(user);
			}
			
		}
		else if (user1.getLoginName() == "" && roleWhere != null)
		{
			List<UserAndRole> roleList = userAndRoleDao.byRoleWhere(roleWhere);
			Set<String> userUuidSet = new HashSet<String>();
			for (UserAndRole userAndRole : roleList)
			{
				userUuidSet.add(userAndRole.getUserUuid());
			}
			for (String uuid : userUuidSet)
			{
				
				userListWhere.add(userDao.get(uuid));
			}
			
		}
		else if (user1.getLoginName() != "" && user1.getLoginName() != null
				&& roleWhere != null)
		{
			
			List<User> loginNameUserList = userDao.byUserWhere(user1);
			if (loginNameUserList.size() != 0)
			{
				String[] userUuid = new String[loginNameUserList.size()];
				for (int i = 0; i < loginNameUserList.size(); i++)
				{
					userUuid[i] = loginNameUserList.get(i).getUuid();
				}
				
				List<UserAndRole> userAndRoleList = userAndRoleDao
						.byUserAndRoleWhere(userUuid, roleWhere);
				
				Set<String> userUuidSet = new HashSet<String>();
				for (UserAndRole userAndRole : userAndRoleList)
				{
					userUuidSet.add(userAndRole.getUserUuid());
				}
				for (String uuid : userUuidSet)
				{
					
					userListWhere.add(userDao.get(uuid));
				}
			}
			
		}
		else
		{
			
			List<User> loginNameUserList = userDao.byUserWhere(user1);
			
			for (User user : loginNameUserList)
			{
				userListWhere.add(user);
			}
		}
		
		String[] userUuids = new String[userListWhere.size()];
		for (int i = 0; i < userListWhere.size(); i++)
		{
			userUuids[i] = userListWhere.get(i).getUuid();
			
		}
		List<User> userList = new ArrayList<User>();
		if (userUuids.length != 0)
		{
			userList = userDao.byUserUuidWhere(user1, userUuids);
		}
		
		for (User user : userList)
		{
			UserAndRole userAndRoles1 = new UserAndRole();
			userAndRoles1.setUserUuid(user.getUuid());
			
			List<UserAndRole> userAndRoles = userAndRoleDao
					.queryByExample(userAndRoles1);
			// List<UserAndRole> userAndRoles =
			// userAndRoleDao.query("from UserAndRole where userUuid='" +
			// user.getUuid() + "' and isDeleted='0'");
			List<Role> roles = new ArrayList<Role>();
			for (UserAndRole userAndRole : userAndRoles)
			{
				Role role = roleDao.get(userAndRole.getRoleUuid());
				roles.add(role);
			}
			
			StringBuilder sb = new StringBuilder();
			String roleNameString = "";
			if (!roles.isEmpty())
			{
				int i = 1;
				for (Role role : roles)
				{
					if (null != role)
					{
						sb.append(role.getName());
						if (i < roles.size())
							sb.append(",");
					}
				}
				if (sb.toString().endsWith(","))
				{
					roleNameString = sb.substring(0, sb.length() - 1);
					i++;
				}
				else
				{
					roleNameString = sb.toString();
				}
				
				user.setRoleName(roleNameString);
				
			}
			
		}
		
		return userList;
	}
	
	public List<Role> getRoleKeyListByUser(String userUuid)
	{
		UserAndRole userAndRoles1 = new UserAndRole();
		userAndRoles1.setUserUuid(userUuid);
		
		List<UserAndRole> userAndRoles = userAndRoleDao
				.queryByExample(userAndRoles1);
		List<Role> roles = new ArrayList<Role>();
		for (UserAndRole userAndRole : userAndRoles)
		{
			roles.add(roleDao.get(userAndRole.getRoleUuid()));
		}
		
		return roles;
		
	}
	
	public Set<String> getPermiSetByRoles(List<Role> roles)
	{
		Set<String> permiSet = new HashSet<String>();
		for (Role role : roles)
		{
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
	
	@Transactional
	public boolean addUsers(String roleUuid, User user)
	{
		boolean bll = getUserByName(user.getLoginName());
		if (bll == true)
		{
			loger.debug("用户已存在！");
			return false;
		}
		else
		{
			User user1 = new User();
			
			user1.setCpId(user.getCpId());
			user1.setLoginName(user.getLoginName());
			user1.setStatus(user.getStatus());
			user1.setType(user.getType());
			addUser(user);
		}
		User users = findByUuid(user.getLoginName());
		
		loger.debug(users.getUuid() + roleUuid);
		if (roleUuid != null && roleUuid.trim() != "")
		{
			String[] str = roleUuid.split(",");
			for (int i = 0; i < str.length; i++)
			{
				loger.debug(str[i]);
				
				UserAndRole userAndRole = new UserAndRole();
				userAndRole.setUserUuid(users.getUuid());
				userAndRole.setRoleUuid(str[i]);
				addUserAndRole(userAndRole);
			}
		}
		
		return true;
	}
	
	@Transactional
	public boolean updateUsers(String roleUuid, User user)
			throws IllegalAccessException, InvocationTargetException
	{
		
		User users = new User();
		users.setLoginName(user.getLoginName());
		users.setCpId(user.getCpId());
		users.setUuid(user.getUuid());
		users.setStatus(user.getStatus());
		users.setType(user.getType());
		updateUser(users);
		
		// 删除用户已拥有所有角色
		List<UserAndRole> userAndRoles = finIfUserAndRole(user.getUuid());
		if (userAndRoles.size() != 0)
		{
			for (UserAndRole userAndRole : userAndRoles)
			{
				deleteUserAndRole(userAndRole);
				loger.debug("删除成功！");
			}
			
		}
		else
		{
			loger.debug("该用户暂时无对应角色！");
		}
		
		if (roleUuid != null && roleUuid.trim() != "")
		{
			
			loger.debug("用户添加角色");
			String[] str = roleUuid.split(",");
			for (int i = 0; i < str.length; i++)
			{
				UserAndRole userAndRole = new UserAndRole();
				userAndRole.setUserUuid(user.getUuid());
				userAndRole.setRoleUuid(str[i]);
				addUserAndRole(userAndRole);
			}
		}
		return true;
	}
	
	@Override
	public List<User> getUserByMatchName(String name)
	{
		return userDao.getUserByMatchName(name);
	}

	@Override
	public User getUserByLoginName(String LoginName) {
		User user = new User();
		user.setLoginName(LoginName);
		List<User> users = userDao.queryByExample(user);
		if(users.isEmpty()){
			return null;
		}else{
			return users.get(0);
		}
	}
}
