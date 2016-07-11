/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: Jirui Zhao
 * @date: 2012-3-8 ����3:08:27
 * @Description:
 * 
 */
package com.cnrvoice.account.web.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cnrvoice.account.entity.SubSystem;
import com.cnrvoice.account.web.manager.SubSystemManager;
import com.cnrvoice.base.paging.JEasyPageOrder;
import com.cnrvoice.base.result.JEasyResultCreater;
import com.cnrvoice.base.result.Result;

@Controller
@RequestMapping(value = "/manager/*")
public class SubSystemController {
	@Autowired
	private SubSystemManager subSystemManager;

	public SubSystemManager getSubSystemManager() {
		return subSystemManager;
	}

	public void setSubSystemManager(SubSystemManager subSystemManager) {
		this.subSystemManager = subSystemManager;
	}

	/**
	 * 获取所有可登录系统数据
	 * 
	 * @param pageOrder
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@RequestMapping("subsystem/data")
	@ResponseBody
	public Result<?> getData(JEasyPageOrder pageOrder)
			throws IllegalAccessException, InvocationTargetException {
		List<SubSystem> list = subSystemManager.querySubSystem(pageOrder);

		return JEasyResultCreater.createResult(list);
	}

	/**
	 * 添加可登录系统
	 * 
	 * @param subSystem
	 * @return
	 */
	@RequestMapping("subsystem/add")
	@ResponseBody
	public Result<?> subSystemAdd(SubSystem subSystem) {
		if (subSystemManager.isrepeat(subSystem))
			;
		{
			subSystemManager.subSystemAdd(subSystem);
			return JEasyResultCreater.createResult(new StringBuffer("添加成功"));
		}
	}

	/**
	 * 编辑可登录系统
	 * 
	 * @param subSystem
	 * @return
	 */
	@RequestMapping("subsystem/update")
	@ResponseBody
	public Result<?> subSystemUpdate(SubSystem subSystem) {
		if (subSystemManager.isrepeat(subSystem))
			;
		{
			subSystemManager.subSystemUpdate(subSystem);
			return JEasyResultCreater.createResult(new StringBuffer("编辑成功"));
		}
	}

	/**
	 * 跳转主页面
	 * 
	 * @return String
	 */
	@RequestMapping("subsystem/list")
	public String list() {
		return "manager/subsystem_check";

	}

}
