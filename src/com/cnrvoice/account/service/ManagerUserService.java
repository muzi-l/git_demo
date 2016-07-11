/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: Guohong Mao
 * @date: 2012-4-11 上午10:18:57
 * @Description:
 * 
 */
package com.cnrvoice.account.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Set;

import com.cnrvoice.account.entity.Role;
import com.cnrvoice.account.entity.User;
import com.cnrvoice.account.entity.UserAndRole;
import com.cnrvoice.account.entity.query.UserQuery;
import com.cnrvoice.unified.webservice.dto.UserInfoDto;

public interface ManagerUserService {
	public List<User> loadAll();

	public void addUser(User user);

	public User getByIdUser(String uuid);

	public void updateUser(User user) throws IllegalAccessException,
			InvocationTargetException;

	public void addUserAndRole(UserAndRole userAndRole);

	public User findByUuid(String LoginName);

	public List<UserInfoDto> loadAllUserInfo(String type) throws IllegalAccessException,
			InvocationTargetException;

	List<UserAndRole> getUserAndRoleByUserUuid(String uuid);

	// public void deleteUserAndRole(String uuid);
	// public List<User> getUserAccountByLoginName();
	List<User> getUserAccountByLoginName();

	public boolean getUserByName(String LoginName);
	public User getUserByLoginName(String LoginName);

	public List<UserAndRole> finIfUserAndRole(String uuid);

	public void deleteUserAndRole(UserAndRole userAndRole);

	public List<User> getUserByWhere(UserQuery user1, String[] roleWhere)
			throws IllegalAccessException, InvocationTargetException;

	public List<Role> getRoleKeyListByUser(String userUuid);

	Set<String> getPermiSetByRoles(List<Role> roles);

	// 用户添加角色事务
	public boolean addUsers(String roleUuid, User user);

	// 用户修改角色事务
	public boolean updateUsers(String roleUuid, User user)
			throws IllegalAccessException, InvocationTargetException;

	public List<User> getUserByMatchName(String name);
}
