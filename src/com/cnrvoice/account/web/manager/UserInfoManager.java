/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: Jirui Zhao
 * @date: 2012-3-8 ����3:08:27
 * @Description:
 * 
 */
package com.cnrvoice.account.web.manager;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cnrvoice.account.entity.SubSystem;
import com.cnrvoice.account.entity.UserInfo;
import com.cnrvoice.account.entity.UserInfoDetail;
import com.cnrvoice.account.entity.UserInfoStatus;
import com.cnrvoice.account.entity.query.UserInfoQuery;
import com.cnrvoice.account.service.UserInfoService;

@Service
public class UserInfoManager
{
	@Autowired
	public UserInfoService userinfosv;
	
	public UserInfoService getUserinfo()
	{
		return userinfosv;
	}
	
	public void setUserinfo(UserInfoService userinfosv)
	{
		this.userinfosv = userinfosv;
	}
	
	public UserInfo login(String loginName, String subSystemKey)
	{
		return userinfosv.login(loginName, subSystemKey);
	}
	
	public List<UserInfo> queryUserInfo(UserInfoQuery userinfo)
			throws IllegalAccessException, InvocationTargetException
	{
		List<UserInfo> list = userinfosv.queryUserInfo(userinfo);
		return list;
	}
	
	public boolean isrepeat(String loginName)
	{
		return userinfosv.isrepeat(loginName);
	}
	
	public UserInfo queryUserInfoByUuid(Integer id)
	{
		return userinfosv.queryUserInfoById(id);
	}
	
	public void addUserInfo(UserInfo uf)
	{
		userinfosv.addUserInfo(uf);
		
	}
	
	public void addUserInfoDetail(UserInfoDetail ufd)
	{
		userinfosv.addUserInfoDetail(ufd);
	}
	
	public void updateUserInfo(UserInfo uf) throws IllegalAccessException,
			InvocationTargetException
	{
		userinfosv.updateUserInfo(uf);
		
	}
	
	public List<UserInfoDetail> queryUserInfoDetail(Integer key)
	{
		return userinfosv.queryUserInfoDetail(key);
	}
	
	public List<SubSystem> getAllSubSystem()
	{
		return userinfosv.getAllSubSystem();
	}
	
	public void deleteUserInfoDetail(UserInfoDetail ufd)
	{
		userinfosv.deleteUserInfoDetail(ufd);
	}
	
	public SubSystem querySubSystemByUuid(String uuid)
	{
		return userinfosv.querySubSystemByUuid(uuid);
	}
	
	public List<UserInfo> getALL()
	{
		// TODO Auto-generated method stub
		return userinfosv.getALL();
	}
	
	public List<UserInfo> getUserinfoes(String subSystemKey,String type)
	{
		return userinfosv.getUserinfoes(subSystemKey,type);
	}
	public List<UserInfoStatus> getAllUserInfoType()
	{
		return userinfosv.getAllUserInfoType();
	}

	public String passwordUpdate(String loginName, String password,
			String newPassword)
	{
		return userinfosv.passwordUpdate(loginName, password,newPassword);
	}

	public void resetpassword(UserInfo userInfo)
	{
		userinfosv.resetpassword(userInfo);
		
	}

	public Map<String,Object> login2(String loginName,String password, String subSystemKey)
	{
		return userinfosv.login2(loginName,password, subSystemKey);
	}
}
