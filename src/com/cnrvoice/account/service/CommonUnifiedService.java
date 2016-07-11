package com.cnrvoice.account.service;

import java.util.List;

import com.cnrvoice.account.entity.UserInfoStatus;


public interface CommonUnifiedService {

	//销售渠道分类
	public List<UserInfoStatus> getAllUserInfoStatus();
	
	public UserInfoStatus getUserInfoStatusValue(String key);
}
