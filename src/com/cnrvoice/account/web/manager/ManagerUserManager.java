package com.cnrvoice.account.web.manager;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cnrvoice.account.entity.Menu;
import com.cnrvoice.account.entity.RoleAndPermi;
import com.cnrvoice.account.entity.User;
import com.cnrvoice.account.entity.UserAndRole;
import com.cnrvoice.account.entity.query.UserQuery;
import com.cnrvoice.account.service.ManagerUserService;
import com.cnrvoice.account.service.MenuService;
import com.cnrvoice.account.service.PermiService;
import com.cnrvoice.account.service.RoleAndPermiService;
import com.cnrvoice.unified.webservice.dto.UserInfoDto;

@Service
public class ManagerUserManager {
	@Autowired
	private ManagerUserService userAndRoleService;
	@Autowired
	private RoleAndPermiService roleAndPermiService;
	@Autowired
	private PermiService permiService;
	@Autowired
	private MenuService fenzhangMenuService;

	public ManagerUserService getUserAndRoleService() {
		return userAndRoleService;
	}

	public void setUserAndRoleService(ManagerUserService userAndRoleService) {
		this.userAndRoleService = userAndRoleService;
	}

	public RoleAndPermiService getRoleAndPermiService() {
		return roleAndPermiService;
	}

	public void setRoleAndPermiService(RoleAndPermiService roleAndPermiService) {
		this.roleAndPermiService = roleAndPermiService;
	}

	public PermiService getPermiService() {
		return permiService;
	}

	public void setPermiService(PermiService permiService) {
		this.permiService = permiService;
	}

	public MenuService getFenzhangMenuService() {
		return fenzhangMenuService;
	}

	public void setFenzhangMenuService(MenuService fenzhangMenuService) {
		this.fenzhangMenuService = fenzhangMenuService;
	}

	public List<User> loadAll() {
		return userAndRoleService.loadAll();
	}

	public void addUser(User user) {

		userAndRoleService.addUser(user);
	}

	public User getByIdUser(String uuid) {
		// User user = userAndRoleService.getByIdUser(uuid);

		return userAndRoleService.getByIdUser(uuid);
	}

	public void updateUser(User user) throws IllegalAccessException,
			InvocationTargetException {

		userAndRoleService.updateUser(user);
	}

	public void addUserAndRole(UserAndRole userAndRole) {
		userAndRoleService.addUserAndRole(userAndRole);
	}

	public User findByUuid(String LoginName) {
		return userAndRoleService.findByUuid(LoginName);
	}

	public List<UserInfoDto> loadAllUserInfo(String type)
			throws IllegalAccessException, InvocationTargetException {

		return userAndRoleService.loadAllUserInfo(type);

	}

	// public void deleteUserAndRole(String uuid){
	// userAndRoleService.deleteUserAndRole(uuid);
	// }
	public void deleteUserAndRole(UserAndRole userAndRole) {
		userAndRoleService.deleteUserAndRole(userAndRole);
	}

	public List<User> getUserAccountByLoginName() {
		return userAndRoleService.getUserAccountByLoginName();
	}

	public boolean getUserByName(String LoginName) {
		return userAndRoleService.getUserByName(LoginName);
	}

	public List<UserAndRole> finIfUserAndRole(String uuid) {
		return userAndRoleService.finIfUserAndRole(uuid);
	}

	public List<RoleAndPermi> getRoleAndPermiByRoleUuid(String uuid) {
		return getRoleAndPermiService().getRoleAndPermiByRoleUuid(uuid);
	}

	public List<Menu> getAllMenus() {
		return getFenzhangMenuService().getAll();
	}

	public List<String> getPermiKeyListByRoles(String roleUuids) {
		Set<String> set = getPermiService().getPermiSetByRoles(roleUuids);
		List<String> list = new ArrayList<String>();
		for (String string : set) {
			list.add(string);
		}
		return list;
	}

	public List<User> getUserByWhere(UserQuery user, String[] roleWhere)
			throws IllegalAccessException, InvocationTargetException {

		return userAndRoleService.getUserByWhere(user, roleWhere);
	}

	public boolean addUsers(String roleUuid, User user) {

		return userAndRoleService.addUsers(roleUuid, user);

	}

	public boolean updateUsers(String roleUuid, User user)
			throws IllegalAccessException, InvocationTargetException {
		return userAndRoleService.updateUsers(roleUuid, user);
	}

	public List<User> getUserByMatchName(String name) {

		return userAndRoleService.getUserByMatchName(name);
	}
}
