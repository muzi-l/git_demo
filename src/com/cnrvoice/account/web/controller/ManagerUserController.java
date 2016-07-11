/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: JiaLiang Li
 * @date: 2012-4-11 上午10:49:49
 * @Description:
 * 
 */

package com.cnrvoice.account.web.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cnrvoice.account.entity.Menu;
import com.cnrvoice.account.entity.Role;
import com.cnrvoice.account.entity.RoleAndPermi;
import com.cnrvoice.account.entity.User;
import com.cnrvoice.account.entity.query.UserQuery;
import com.cnrvoice.account.web.manager.ManagerUserManager;
import com.cnrvoice.base.paging.JEasyPageOrder;
import com.cnrvoice.base.result.JEasyResultCreater;
import com.cnrvoice.base.result.Result;
import com.cnrvoice.unified.webservice.dto.UserInfoDto;

@Controller
public class ManagerUserController {

	@Autowired
	private ManagerUserManager managerUserManager;

	public ManagerUserManager getUserAndRoleManager() {
		return managerUserManager;
	}

	public void setUserAndRoleManager(ManagerUserManager userAndRoleManager) {
		this.managerUserManager = userAndRoleManager;
	}

	public ManagerUserManager getManagerUserManager() {
		return managerUserManager;
	}

	public void setManagerUserManager(ManagerUserManager managerUserManager) {
		this.managerUserManager = managerUserManager;
	}

	Logger loger = Logger.getLogger(ManagerUserController.class);

	// 显示添加用户页面
	@RequestMapping(value = "/manager/useradd/view")
	public String addUserView() {
		return "account/add_user";
	}

	/**
	 * 用户信息修改请求(其中要向页面传用户对象)
	 * 
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/manager/useredit/view")
	public ModelAndView updateUser(String uuid) {

		User user = managerUserManager.getByIdUser(uuid);
		if (user == null) {
			ModelAndView mv = new ModelAndView("account/show_user");

			return mv;
		}
		ModelAndView mv = new ModelAndView("account/update_user");
		mv.addObject("User", user);
		return mv;
	}

	/**
	 * 用户信息修改处理
	 * 
	 * @param user
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@RequestMapping(value = "/manager/useredit/submit")
	@ResponseBody
	public Result<?> updateUserDeal(String roleUuid, User user)
			throws IllegalAccessException, InvocationTargetException {
		boolean bool = managerUserManager.updateUsers(roleUuid, user);
		if (bool) {
			return JEasyResultCreater.createResult(new StringBuffer("修改成功"));
		}
		return JEasyResultCreater.createResult(new StringBuffer("修改失败，谢谢合作"));
	}

	/**
	 * 给用户添加角色
	 * 
	 * @param loginUuid
	 * @param roleUuid
	 * @return
	 */
	@RequestMapping(value = "/manager/useradd/submit")
	@ResponseBody
	public Result<?> addUser(String roleUuid, User user) {

		boolean boo = managerUserManager.addUsers(roleUuid, user);
		if (boo) {
			return JEasyResultCreater.createResult(new StringBuffer("添加成功"));
		}
		return JEasyResultCreater.createResult(new StringBuffer("添加失败，谢谢合作"));

	}

	/**
	 * 获得所有登录用户
	 * 
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */

	@RequestMapping(value = "/manager/loadAllUserInfo/data")
	@ResponseBody
	public Result<?> loadAllUserInfo(String type)
			throws IllegalAccessException, InvocationTargetException {

		List<UserInfoDto> user = managerUserManager.loadAllUserInfo(type);
		return JEasyResultCreater.createResult(user);

	}

	/**
	 * 查询所有用户
	 * 
	 * @return
	 */
	@RequestMapping(value = "/manager/user/data")
	@ResponseBody
	public Result<?> getUserAccountByLoginName() {
		List<User> users = managerUserManager.getUserAccountByLoginName();

		return JEasyResultCreater.createResult(users);
	}

	/**
	 * 跳转到用户列表页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/manager/user/list")
	public String list() {

		return "account/show_user";

	}

	@RequestMapping("/manager/user/permiKeys")
	@ResponseBody
	public Result<?> getPermiKeyListByRoles(String roleUuids) {
		List<RoleAndPermi> list = getManagerUserManager()
				.getRoleAndPermiByRoleUuid(roleUuids);

		return JEasyResultCreater.createResult(list);
	}

	@RequestMapping("/manager/user/menus")
	@ResponseBody
	public Result<?> getAllMenus() {
		List<Menu> list = getManagerUserManager().getAllMenus();

		return JEasyResultCreater.createResult(list);
	}

	public List<String> getPermiUuidsByRoleUuids(String roleUuids) {
		if (null != roleUuids) {

		}

		return null;
	}

	// 条件查询User
	@RequestMapping(value = "/manager/user/search")
	@ResponseBody
	public Result<?> userByWhere(UserQuery user, String role,
			JEasyPageOrder jEasyPageOrder) throws IllegalAccessException,
			InvocationTargetException {

		String[] str;
		if (role != null && role.equals("") == false) {
			str = role.split(",");
		} else {
			str = null;
		}
		user.setPageOrder(jEasyPageOrder);

		List<User> users = managerUserManager.getUserByWhere(user, str);

		return JEasyResultCreater.createResult(users);
	}

	@RequestMapping("/manager/user/permis")
	@ResponseBody
	public Result<?> getPermiKeysByUser(String userUuid) {
		List<String> list = new ArrayList<String>();
		List<Role> roles = getManagerUserManager().getUserAndRoleService()
				.getRoleKeyListByUser(userUuid);

		if (roles.get(0) != null) {
			Set<String> set = getUserAndRoleManager().getUserAndRoleService()
					.getPermiSetByRoles(roles);

			for (String string : set) {
				list.add(string);
			}
		}

		return JEasyResultCreater.createResult(list);
	}

	@RequestMapping("/manager/user/roles")
	@ResponseBody
	public Result<?> getRolesbyUser(String userUuid) {
		List<Role> roles = getManagerUserManager().getUserAndRoleService()
				.getRoleKeyListByUser(userUuid);

		return JEasyResultCreater.createResult(roles);
	}

	@RequestMapping(value = "/manager/user/query")
	@ResponseBody
	public List<User> cpByName(String term) {
		return getUserAndRoleManager().getUserByMatchName(term);
	}
	
	@RequestMapping(value = "/manager/user/all")
	@ResponseBody
	public Result<?> loadAll() {
		return JEasyResultCreater.createResult(getUserAndRoleManager().loadAll());
	}
	
}
