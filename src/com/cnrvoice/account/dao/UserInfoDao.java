/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-3-8 ����3:07:16
 * @Description:
 * 
 */
package com.cnrvoice.account.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.cnrvoice.account.entity.UserInfo;
import com.cnrvoice.account.entity.query.UserInfoQuery;
import com.cnrvoice.base.hibernate.dao.generic.GenericAutoSfHibernateDao;
import com.cnrvoice.base.hibernate.util.HibernateLikeExprUtil;

@Repository
public class UserInfoDao extends GenericAutoSfHibernateDao<UserInfo, Integer>
{
	public List<UserInfo> queryUserInfo(UserInfoQuery userinfo)
			throws IllegalAccessException, InvocationTargetException
	{
		
		DetachedCriteria crit = createDetachedCriteria();
		String userName = userinfo.getUserName();
		String loginName = userinfo.getLoginName();
		if (StringUtils.isNotEmpty(userName))
		{
			crit.add(HibernateLikeExprUtil.createAnyWhereMatchLikeExpr("userName", userName));
		}
		if (StringUtils.isNotEmpty(loginName))
		{
			crit.add(HibernateLikeExprUtil.createAnyWhereMatchLikeExpr("loginName", loginName));
		}
		return queryByCriteria(crit, userinfo.getPageOrder());
	}
	
	public List<UserInfo> isrepeat(String loginName)
	{
		DetachedCriteria crit = createDetachedCriteria();
		if (StringUtils.isNotEmpty(loginName))
			crit.add(Restrictions.like("loginName", loginName));
		return queryByCriteria(crit);
	}
	
	public List<UserInfo> queryUserInfo(UserInfo userinfo)
	{
		DetachedCriteria crit = createDetachedCriteria();
		String userName = userinfo.getUserName();
		String loginName = userinfo.getLoginName();
		if (StringUtils.isNotEmpty(userName))
		{
			crit.add(HibernateLikeExprUtil.createAnyWhereMatchLikeExpr("userName", userName));
		}
		if (StringUtils.isNotEmpty(loginName))
		{
			crit.add(HibernateLikeExprUtil.createAnyWhereMatchLikeExpr("loginName", loginName));
		}
		return queryByCriteria(crit);
	}
	
	public UserInfo getUserInfo(String loginName,String password)
	{
		DetachedCriteria crit = createDetachedCriteria();
		if (StringUtils.isNotEmpty(loginName))
		{
			crit.add(Restrictions.eq("loginName", loginName));
		}
		if (StringUtils.isNotEmpty(password))
		{
			crit.add(Restrictions.eq("password", password));
		}
		List<UserInfo> list = queryByCriteria(crit);
		return list.size() > 0 ? list.get(0) : null;
	}

}
