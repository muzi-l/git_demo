/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: Jirui Zhao
 * @date: 2012-3-8 ����3:08:27
 * @Description:
 * 
 */
package com.cnrvoice.account.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cnrvoice.account.dao.SubSystemDao;
import com.cnrvoice.account.dao.UserInfoDao;
import com.cnrvoice.account.dao.UserInfoDetailDao;
import com.cnrvoice.account.dao.UserInfoStatusDao;
import com.cnrvoice.account.entity.SubSystem;
import com.cnrvoice.account.entity.UserInfo;
import com.cnrvoice.account.entity.UserInfoDetail;
import com.cnrvoice.account.entity.UserInfoStatus;
import com.cnrvoice.account.entity.query.UserInfoQuery;
import com.cnrvoice.account.service.UserInfoService;
import com.cnrvoice.base.exception.UnCheckedException;

@Service
public class UserInfoServiceImpl implements UserInfoService
{
	@Autowired
	private UserInfoDao userInfoDao;
	@Autowired
	private UserInfoDetailDao userInfoDetailDao;
	@Autowired
	private SubSystemDao ssd;
	@Autowired
	private UserInfoStatusDao userInfoStatusDao;
	
	public UserInfo login(String loginName, String subSystemKey)
	{
		UserInfo userInfo = userInfoDao.getUserInfo(loginName, null);
		List<UserInfoDetail> userInfoDetailList = null;
		if (userInfo != null)
		{
			userInfoDetailList = userInfoDetailDao
					.queryUserInfoDetailByParameters(userInfo.getId());
			for (UserInfoDetail userInfoDetail : userInfoDetailList)
			{
				if (ssd.get(userInfoDetail.getSubsystemUuid()).getKey().equals(subSystemKey))
				{
					userInfo.setSystem(subSystemKey);
				}
			}
		}
		return userInfo;
	}
	
	public UserInfoDao getUserInfoDao()
	{
		return userInfoDao;
	}
	
	public void setUserInfoDao(UserInfoDao userInfoDao)
	{
		this.userInfoDao = userInfoDao;
	}
	
	public UserInfoDetailDao getUserInfoDetailDao()
	{
		return userInfoDetailDao;
	}
	
	public void setUserInfoDetailDao(UserInfoDetailDao userInfoDetailDao)
	{
		this.userInfoDetailDao = userInfoDetailDao;
	}
	
	@Override
	public List<UserInfo> queryUserInfo(UserInfoQuery userinfo)
			throws IllegalAccessException, InvocationTargetException
	{
		List<UserInfo> paginglist = userInfoDao.queryUserInfo(userinfo);
		for (UserInfo ui : paginglist)
		{
			String str = "";
			List<UserInfoDetail> userInfoDetaillist = queryUserInfoDetail(ui
					.getId());
			for (int i = 0; i < userInfoDetaillist.size(); i++)
			{
				UserInfoDetail ud = userInfoDetaillist.get(i);
				SubSystem ss = querySubSystemByUuid(ud.getSubsystemUuid());
				if (ss != null)
					str += ss.getName();
				if (i < userInfoDetaillist.size() - 1)
					str += ",";
			}
			ui.setSystem(str);
			UserInfoStatus userInfoStatus = userInfoStatusDao
					.getUserInfoTypeValue(ui.getType());
			if (userInfoStatus != null)
			{
				ui.setTypeValue(userInfoStatus.getValue());
			}
		}
		return paginglist;
	}
	
	@Override
	public UserInfo queryUserInfoById(Integer id)
	{
		return userInfoDao.get(id);
	}
	
	@Override
	@Transactional
	public void addUserInfo(UserInfo uf)
	{
		try
		{
			userInfoDao.save(uf);
		}
		catch (DataIntegrityViolationException e)
		{
			throw new UnCheckedException("biz.userinfo.100");
		}
		
	}
	
	@Override
	@Transactional
	public void addUserInfoDetail(UserInfoDetail ufd)
	{
		userInfoDetailDao.save(ufd);
	}
	
	@Override
	@Transactional
	public void updateUserInfo(UserInfo uf) throws IllegalAccessException,
			InvocationTargetException
	{
		try
		{
			userInfoDao.update(uf);
		}
		catch (DataIntegrityViolationException e)
		{
			throw new UnCheckedException("biz.userinfo.100");
		}
	}
	
	@Override
	public List<UserInfoDetail> queryUserInfoDetail(Integer key)
	{
		return userInfoDetailDao.queryUserInfoDetail(key);
	}
	
	@Override
	public List<SubSystem> getAllSubSystem()
	{
		return ssd.loadAll();
	}
	
	public SubSystemDao getSsd()
	{
		return ssd;
	}
	
	public void setSsd(SubSystemDao ssd)
	{
		this.ssd = ssd;
	}
	
	@Override
	@Transactional
	public void deleteUserInfoDetail(UserInfoDetail ufd)
	{
		userInfoDetailDao.delete(ufd);
		
	}
	
	@Override
	public SubSystem querySubSystemByUuid(String uuid)
	{
		List<SubSystem> list = ssd.querySubSystemByUuid(uuid);
		SubSystem ss = list != null && list.size() != 0 ? list.get(0) : null;
		return ss;
	}
	
	@Override
	public boolean isrepeat(String loginName)
	{
		
		List<UserInfo> list = userInfoDao.isrepeat(loginName);
		Boolean bl = list != null && list.size() > 0 ? true : false;
		if (bl == true)
		{
			throw new UnCheckedException("biz.userinfo.100");
			
		}
		return bl;
	}
	
	@Override
	public List<UserInfo> queryUserinfo(UserInfo userinfo)
	{
		// TODO Auto-generated method stub
		return userInfoDao.queryUserInfo(userinfo);
	}
	
	@Override
	public List<UserInfo> getALL()
	{
		return userInfoDao.loadAll();
	}
	
	@Override
	public List<UserInfo> getUserinfoes(String subSystemKey, String type)
	{
		SubSystem subSystem = new SubSystem();
		subSystem.setKey(subSystemKey);
		List<SubSystem> s = ssd.queryByExample(subSystem);
		String uuid = "";
		if(!s.isEmpty()){
			uuid = s.get(0).getUuid();
		}	
		List<UserInfoDetail> userInfoDetailList = userInfoDetailDao
				.queryUserInfoDetailAssubsystemUuid(uuid);
		List<UserInfo> userInfoList = new ArrayList<UserInfo>();
		String types[] = type.split(",");
		if (userInfoDetailList != null && userInfoDetailList.size() > 0)
		{
			for (UserInfoDetail ud : userInfoDetailList)
			{
				UserInfo userInfo = userInfoDao.get(ud.getUserinfoId());
				if (userInfo != null)
				{
					for (int i = 0; i < types.length; i++)
					{
						if (userInfo.getType().equals(types[i])
								&& userInfo.getStatus().equals("10"))
						{
							userInfoList.add(userInfo);
						}
					}
				}
			}
		}
		return userInfoList;
	}
	
	public UserInfoStatusDao getUserInfoStatusDao()
	{
		return userInfoStatusDao;
	}
	
	public void setUserInfoStatusDao(UserInfoStatusDao userInfoStatusDao)
	{
		this.userInfoStatusDao = userInfoStatusDao;
	}
	
	@Override
	public List<UserInfoStatus> getAllUserInfoType()
	{
		return userInfoStatusDao.getAllUserInfoType();
	}
	
	@Override
	@Transactional
	public String passwordUpdate(String loginName, String password,
			String newPassword)
	{
		String str = "";
		UserInfo userInfo = userInfoDao.getUserInfo(loginName, password);
		if (userInfo != null)
		{
			UserInfo updatePasswordUserinfo =new UserInfo();
			try {
				BeanUtils.copyProperties(updatePasswordUserinfo, userInfo);
				updatePasswordUserinfo.setPassword(newPassword);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			resetpassword(updatePasswordUserinfo);
			UserInfo updateuserInfo = userInfoDao.get(userInfo.getId());
			str = updateuserInfo.getPassword().equals(newPassword) ? "success"
					: "false";
		}
		else
		{
			str = "passworderror";
		}
		return str;
	}

	@Override
	public void resetpassword(UserInfo userInfo)
	{
		try
		{
			userInfoDao.update(userInfo);
		}
		catch (IllegalAccessException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public Map<String,Object> login2(String loginName, String password,
			String subSystemKey)
	{
		Map<String, Object> map = new HashMap<String,Object>();
		UserInfo userInfo = userInfoDao.getUserInfo(loginName, null);
		//用户不存在
		if(userInfo == null){
			map.put("code", "10");
			return map;
		}
		//用户密码不正确
		if(!userInfo.getPassword().equals(password)){
			map.put("code", "20");
			return map;
		}
		SubSystem subSystem = ssd.getSubSystemByKey(subSystemKey);
		//子系统不存在
		if(subSystem == null){
			map.put("code", "30");
			return map;
		}
		UserInfoDetail userInfoDetail = userInfoDetailDao.queryUserInfoDetail(userInfo.getId(),subSystem.getUuid());
		//用户不能登录该子系统
		if(userInfoDetail == null){
			map.put("code", "40");
			return map;
		}
		//用户可以登录，返回用户信息
		map.put("code", "1");
		map.put("userInfo", userInfo);
		return map;
	}
	
}
