package com.cnrvoice.account.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cnrvoice.account.dao.UserInfoStatusDao;
import com.cnrvoice.account.entity.UserInfoStatus;
import com.cnrvoice.account.service.CommonUnifiedService;

@Service
public class CommonUnifiedServiceImpl implements CommonUnifiedService {
	@Autowired
	private UserInfoStatusDao userInfoStatusDao;
	
	public List<UserInfoStatus> getAllUserInfoStatus(){
		
		 return userInfoStatusDao.getAllUserInfoStatus();
	}
	
	public UserInfoStatus getUserInfoStatusValue(String key){
		
		 return userInfoStatusDao.getUserInfoStatusValue(key);
	}

	public UserInfoStatusDao getUserInfoStatusDao()
	{
		return userInfoStatusDao;
	}

	public void setUserInfoStatusDao(UserInfoStatusDao userInfoStatusDao)
	{
		this.userInfoStatusDao = userInfoStatusDao;
	}
}
