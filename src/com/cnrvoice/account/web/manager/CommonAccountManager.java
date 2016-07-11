package com.cnrvoice.account.web.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cnrvoice.account.entity.CommonAccount;
import com.cnrvoice.account.service.CommonAccountService;

@Service
public class CommonAccountManager {
	@Autowired
	private CommonAccountService commonAccountService;

	public CommonAccountService getCommonAccountService() {
		return commonAccountService;
	}

	public void setCommonAccountService(
			CommonAccountService commonAccountService) {
		this.commonAccountService = commonAccountService;
	}

	public List<CommonAccount> loadAllUserState() {
		return commonAccountService.loadAllUserState();
	}

	public CommonAccount getUserStateByKey(String key) {
		return commonAccountService.getUserStateByKey(key);
	}

	public List<CommonAccount> loadAllUserType() {
		return commonAccountService.loadAllUserType();
	}

	public CommonAccount getUserTypeByKey(String key) {
		return commonAccountService.getUserTypeByKey(key);
	}

}
