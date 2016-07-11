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
import org.springframework.stereotype.Repository;

import com.cnrvoice.account.entity.Role;
import com.cnrvoice.account.entity.query.RoleQuery;
import com.cnrvoice.base.hibernate.dao.generic.GenericAutoSfHibernateDao;
import com.cnrvoice.base.hibernate.util.HibernateLikeExprUtil;

@Repository
public class RoleDao extends GenericAutoSfHibernateDao<Role, String>
{
	public List<Role> checkRolesByparameters(RoleQuery roleQuery)
			throws IllegalAccessException, InvocationTargetException
	{
		DetachedCriteria ctr = createDetachedCriteria();
		if (StringUtils.isNotBlank(roleQuery.getName()))
		{
			ctr.add(HibernateLikeExprUtil.createAnyWhereMatchLikeExpr("name", roleQuery.getName()));
		}
		return queryByCriteria(ctr, roleQuery.getPageOrder());
	}
	
	public List<Role> getAllRoles()
	{
		return loadAll();
	}
	
	public List<Role> isrepeat(String name)
	{
		DetachedCriteria ctr = createDetachedCriteria();
		if (StringUtils.isNotEmpty(name))
			ctr.add(HibernateLikeExprUtil.createAnyWhereMatchLikeExpr("name", name));
		return queryByCriteria(ctr);
	}
}
