package com.cnrvoice.account.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cnrvoice.account.constant.AccountConst;
import com.cnrvoice.account.dao.CommonAccountDao;
import com.cnrvoice.account.entity.CommonAccount;
import com.cnrvoice.account.service.CommonAccountService;

@Service
public class CommonAccountImpl implements CommonAccountService
{
	@Autowired
	private CommonAccountDao commonAccountDao;
	
	public CommonAccountDao getCommonAccountDao()
	{
		return commonAccountDao;
	}
	
	public void setCommonAccountDao(CommonAccountDao commonAccountDao)
	{
		this.commonAccountDao = commonAccountDao;
	}
	
	public List<CommonAccount> loadAllUserState()
	{
		return commonAccountDao.loadAllStatusSet(AccountConst.USER_STATE);
	}
	
	public CommonAccount getUserStateByKey(String key)
	{
		return commonAccountDao.getStatusSetByKey(AccountConst.USER_STATE, key);
	}
	
	// 用户类型
	public List<CommonAccount> loadAllUserType()
	{
		return commonAccountDao.loadAllStatusSet(AccountConst.USER_TYPE);
	}
	
	public CommonAccount getUserTypeByKey(String key)
	{
		return commonAccountDao.getStatusSetByKey(AccountConst.USER_TYPE, key);
	}
	
}
