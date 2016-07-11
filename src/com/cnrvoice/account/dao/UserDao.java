/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-3-8 ����2:58:30
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

import com.cnrvoice.account.entity.User;
import com.cnrvoice.account.entity.query.UserQuery;
import com.cnrvoice.base.hibernate.dao.generic.GenericAutoSfHibernateDao;
import com.cnrvoice.base.hibernate.util.HibernateLikeExprUtil;
import com.cnrvoice.base.paging.PagingArrayList;

@Repository
public class UserDao extends GenericAutoSfHibernateDao<User, String>
{
	
	public List<User> byUserWhere(UserQuery user1)
	{
		
		DetachedCriteria crit = this.createDetachedCriteria();
		
		String loginName = user1.getLoginName();
		if (StringUtils.isNotEmpty(loginName) && loginName != null
				&& !"".equals(loginName))
		crit.add(HibernateLikeExprUtil.createAnyWhereMatchLikeExpr("loginName", loginName));
		
		List<User> users = this.queryByCriteria(crit);
		
		return users;
	}
	
	public PagingArrayList<User> byUserUuidWhere(UserQuery user1,
			String[] userUuids) throws IllegalAccessException,
			InvocationTargetException
	{
		
		DetachedCriteria crit = this.createDetachedCriteria();
		
		if (userUuids != null)
		{
			crit.add(Restrictions.in("uuid", userUuids));
		}
		
		PagingArrayList<User> users = this.queryByCriteria(crit,
				user1.getPageOrder());
		
		return users;
	}
	
	public List<User> getUserByMatchName(String name)
	{
		DetachedCriteria crit = this.createDetachedCriteria();
		if (!StringUtils.isNotEmpty(name) && "".equals(name))
			return null;
		crit.add(HibernateLikeExprUtil.createAnyWhereMatchLikeExpr("loginName", name));
		return queryByCriteria(crit);
	}
}
