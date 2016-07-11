/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: Jirui Zhao
 * @date: 2012-4-11 上午10:49:49
 * @Description:
 * 
 */
package com.cnrvoice.account.web.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cnrvoice.account.entity.Menu;
import com.cnrvoice.account.entity.Permi;
import com.cnrvoice.account.entity.Role;
import com.cnrvoice.account.entity.RoleAndPermi;
import com.cnrvoice.account.entity.query.RoleQuery;
import com.cnrvoice.account.web.manager.RoleManager;
import com.cnrvoice.base.paging.JEasyPageOrder;
import com.cnrvoice.base.result.JEasyResultCreater;
import com.cnrvoice.base.result.Result;

/**
 * 
 * @author 赵继瑞
 * 
 */
@Controller
@RequestMapping(value = "/manager/*")
public class RoleController {
	@Autowired
	private RoleManager roleManager;
	// private Role role = null;
	private static Logger logger = Logger.getLogger(RoleController.class);

	public RoleManager getRoleManager() {
		return roleManager;
	}

	public void setRoleManager(RoleManager roleManager) {
		this.roleManager = roleManager;
	}

	/**
	 * 获取符合条件的Role
	 * 
	 * @param checkname
	 * @return Result
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@RequestMapping(value = "/role/search")
	@ResponseBody
	public Result<?> rolecheck(RoleQuery roleQuery, JEasyPageOrder pageOrder)
			throws IllegalAccessException, InvocationTargetException {
		logger.debug("查询开始" + roleQuery.getName());
		roleQuery.setPageOrder(pageOrder);
		List<Role> roles = roleManager.checkRolesByparameters(roleQuery);
		return JEasyResultCreater.createResult(roles);
	}

	/**
	 * 转入添加角色页面
	 * 
	 * @return String
	 */
	@RequestMapping(value = "/roleadd/view")
	public String skipToAddView() {
		return "account/role_add";
	}

	/**
	 * 添加角色及角色对应权限
	 * 
	 * @param role
	 * @param ss
	 * @return Result<?>
	 */
	@RequestMapping(value = "/roleadd/submit")
	@ResponseBody
	public Result<?> roleadd(Role role, String ss) {
		Result<?> result = null;
		if (!roleManager.isrepeat(role.getName())) {
			roleManager.add(role);
			// 当权限不为空时为角色添加对应权限
			if (ss != null && ss.trim() != "") {
				String[] str = ss.split(",");
				for (int i = 0; i < str.length; i++) {
					Permi permi = roleManager.queryPermi("key", str[i]);
					if (permi != null) {
						logger.debug("添加权限为" + permi.getKey()
								+ permi.getValue());
						RoleAndPermi rolepermi = new RoleAndPermi();
						rolepermi.setRoleUuid(role.getUuid());
						rolepermi.setPermiUuid(permi.getUuid());
						roleManager.addRoleAndPermi(rolepermi);
					}
				}
			}
			result = JEasyResultCreater
					.createResult(new StringBuffer("添加角色成功"));
		}
		return result;
	}

	/**
	 * 转入更新页面
	 * 
	 * @param key
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/roleedit/view")
	public ModelAndView skipToUpdateView(String key) {
		Role role = roleManager.getRoleByUuid(key);
		ModelAndView mdv = null;
		if (role != null) {
			mdv = new ModelAndView("account/role_update", "role", role);
		} else {
			mdv = new ModelAndView("account/role_check");
		}
		return mdv;
	}

	/**
	 * 更新角色
	 * 
	 * @param name
	 * @param fdsa
	 * @return Result<?>
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@RequestMapping(value = "/roleedit/submit")
	@ResponseBody
	public Result<?> roleupdate(Role role, String fdsa)
			throws IllegalAccessException, InvocationTargetException {
		roleManager.update(role);
		// 先删除之前权限,再加入新的权限
		deleteRolePermi(role.getUuid());
		if (fdsa != null && fdsa.trim() != "") {
			String[] str = fdsa.split(",");
			for (int i = 0; i < str.length; i++) {
				Permi permi = roleManager.queryPermi("key", str[i]);
				if (permi != null) {
					logger.debug("更新新的权限为" + permi.getKey() + permi.getValue());
					RoleAndPermi rolepermi = new RoleAndPermi();
					rolepermi.setRoleUuid(role.getUuid());
					rolepermi.setPermiUuid(permi.getUuid());
					roleManager.addRoleAndPermi(rolepermi);
				}
			}
		}
		role = null;
		return JEasyResultCreater.createResult(new StringBuffer("更新成功"));
	}

	/**
	 * 删除角色对应权限
	 * 
	 * @param str
	 * @return
	 */
	private void deleteRolePermi(String str) {
		List<RoleAndPermi> list = roleManager.queryRoleAndPermi(str);
		if (list != null && list.size() != 0) {
			roleManager.deleteRoleAndPermi(list);
			logger.debug("删除权限成功");
		} else {
			logger.debug("角色无权限,不必删除");
		}
	}

	/**
	 * 返回主页面
	 * 
	 * @return string
	 */
	@RequestMapping(value = "/role/list")
	public String list() {
		return "account/role_check";
	}

	/**
	 * 获取当前角色的权限
	 * 
	 * @param roleUuid
	 * @return Result<?>
	 */
	@RequestMapping("role/permiKeys")
	@ResponseBody
	public Result<?> getPermiKeyListByRoles(String roleUuid) {
		List<String> list = getRoleManager().getPermiKeyListByRoles(roleUuid);

		return JEasyResultCreater.createResult(list);
	}

	/**
	 * 获取所有权限
	 * 
	 * @return Result<?>
	 */
	@RequestMapping("role/menus")
	@ResponseBody
	public Result<?> getAllMenus() {
		List<Menu> list = getRoleManager().getAllMenus();
		return JEasyResultCreater.createResult(list);
	}

	/**
	 * 获取查询输入框autocomplete的值
	 * 
	 * @param name
	 * @return List<Role>
	 */
	@RequestMapping("role/query")
	@ResponseBody
	public List<Role> getRoles(String name) {
		return getRoleManager().getRoleService().getRoles(name);
	}

	/**
	 * 获取所有角色基础值
	 * 
	 * @return
	 */
	@RequestMapping(value = "get/roleAll")
	@ResponseBody
	public Result<?> getAllRoles() {
		return JEasyResultCreater.createResult(getRoleManager()
				.getRoleService().getAllRoles());

	}
}
