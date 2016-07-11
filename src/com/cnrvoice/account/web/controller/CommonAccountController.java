package com.cnrvoice.account.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cnrvoice.account.entity.CommonAccount;
import com.cnrvoice.account.web.manager.CommonAccountManager;
import com.cnrvoice.base.result.JEasyResultCreater;
import com.cnrvoice.base.result.Result;

@Controller
public class CommonAccountController {
	@Autowired
	private CommonAccountManager commonAccountManager;

	public CommonAccountManager getCommonAccountManager() {
		return commonAccountManager;
	}

	public void setCommonAccountManager(
			CommonAccountManager commonAccountManager) {
		this.commonAccountManager = commonAccountManager;
	}

	/**
	 * 用户状态基础值
	 * 
	 * @return
	 */
	@RequestMapping(value = "/common/userState/all")
	@ResponseBody
	public Result<?> getAllUserState() {
		return JEasyResultCreater.createResult(commonAccountManager
				.loadAllUserState());
	}

	/**
	 * 根据Key查单个用户基础值
	 * 
	 * @param key
	 * @return
	 */
	@RequestMapping(value = "/common/userState/bykey")
	@ResponseBody
	public CommonAccount UserStateByKey(String key) {
		return commonAccountManager.getUserStateByKey(key);
	}

	/**
	 * 获取用户类型基础值
	 * 
	 * @return
	 */
	@RequestMapping(value = "/common/userType/all")
	@ResponseBody
	public Result<?> loadAllUserType() {

		return JEasyResultCreater.createResult(commonAccountManager
				.loadAllUserType());

	}

	/**
	 * 根据Key获取单个用户类型基础值
	 * 
	 * @param key
	 * @return
	 */
	@RequestMapping(value = "/common/userType/bykey")
	@ResponseBody
	public CommonAccount getUserTypeByKey(String key) {

		return commonAccountManager.getUserTypeByKey(key);
	}

}
