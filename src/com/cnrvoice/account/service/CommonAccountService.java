package com.cnrvoice.account.service;

import java.util.List;

import com.cnrvoice.account.entity.CommonAccount;

public interface CommonAccountService {
	// 获取用户状态
	public List<CommonAccount> loadAllUserState();

	// 根据Key值获取用户状态
	public CommonAccount getUserStateByKey(String key);

	public List<CommonAccount> loadAllUserType();

	public CommonAccount getUserTypeByKey(String key);

}
