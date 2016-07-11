/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-4-11 上午10:18:57
 * @Description:
 * 
 */
package com.cnrvoice.account.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.cnrvoice.account.entity.RoleAndPermi;
import com.cnrvoice.base.hibernate.dao.generic.GenericAutoSfHibernateDao;

@Repository
public class RoleAndPermiDao extends GenericAutoSfHibernateDao<RoleAndPermi, String>
{
	public List<RoleAndPermi> getRoleAndPermiByRoleUuid(String uuid)
	{
		DetachedCriteria crit = createDetachedCriteria();
		if (StringUtils.isNotEmpty(uuid))
		{
			crit.add(Restrictions.eq("roleUuid",uuid));
		}
		return queryByCriteria(crit);
	}

	public List<RoleAndPermi> getRoleAndPermiByRoleUuid(String[] roleUuids)
	{
		DetachedCriteria crit = createDetachedCriteria();
		if (roleUuids.length != 0 && roleUuids != null)
		{
			crit.add(Restrictions.in("roleUuid",roleUuids));
		}
		return queryByCriteria(crit);
	}
}
