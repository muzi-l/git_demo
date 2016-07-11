package com.cnrvoice.account.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cnrvoice.account.entity.UserInfoStatus;
import com.cnrvoice.account.web.manager.CommonUnifiedManager;
import com.cnrvoice.base.result.JEasyResultCreater;
import com.cnrvoice.base.result.Result;

@Controller
public class CommonUnifiedController {


	@Autowired
	private CommonUnifiedManager commonUnifiedManager;
	
	public CommonUnifiedManager getCommonUnifiedManager() {
		return commonUnifiedManager;
	}

	public void setCommonUnifiedManager(CommonUnifiedManager commonUnifiedManager) {
		this.commonUnifiedManager = commonUnifiedManager;
	}
	/**
	 * 获取所有用户认证类型
	 * @return
	 */

	@RequestMapping(value = "/common/unifiedstatus/all")
	@ResponseBody
	public Result<?> getAllUserInfoStatus(){
		return JEasyResultCreater.createResult(commonUnifiedManager.getAllUserInfoStatus());
	}

	/**
	 * 根据Key获取认证用户
	 * @param key
	 * @return
	 */
	@RequestMapping(value = "/common/unifiedstatus/bykey")
	@ResponseBody
	public UserInfoStatus getUserInfoStatusValue(String key){	
		return commonUnifiedManager.getUserInfoStatusValue(key);
	}
	
}
