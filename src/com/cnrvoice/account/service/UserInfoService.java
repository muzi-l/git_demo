/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-3-8 ����3:07:56
 * @Description:
 * 
 */
package com.cnrvoice.account.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import com.cnrvoice.account.entity.SubSystem;
import com.cnrvoice.account.entity.UserInfo;
import com.cnrvoice.account.entity.UserInfoDetail;
import com.cnrvoice.account.entity.UserInfoStatus;
import com.cnrvoice.account.entity.query.UserInfoQuery;

public interface UserInfoService
{
	public UserInfo login(String loginName, String subSystemKey);
	public List<UserInfo>  queryUserInfo(UserInfoQuery userinfo) throws IllegalAccessException, InvocationTargetException;
	public UserInfo  queryUserInfoById(Integer id);
	public List<UserInfoDetail> queryUserInfoDetail(Integer key);
	public List<UserInfo> getALL();
	public List<SubSystem> getAllSubSystem();
	public void  addUserInfo(UserInfo uf);
	public void  addUserInfoDetail(UserInfoDetail ufd);
	public void  updateUserInfo(UserInfo uf) throws IllegalAccessException, InvocationTargetException;
	public boolean isrepeat(String loginName);
	public void  deleteUserInfoDetail(UserInfoDetail ufd);
	public SubSystem  querySubSystemByUuid(String uuid);
	public List<UserInfo>  queryUserinfo(UserInfo userinfo);
	public List<UserInfo> getUserinfoes(String subSystemUuid,String type);
	public List<UserInfoStatus> getAllUserInfoType();
	public String passwordUpdate(String loginName, String password,
			String newPassword);
	public void resetpassword(UserInfo userInfo);
	public Map<String,Object> login2(String loginName, String password,
			String subSystemKey);
}
