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

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.cnrvoice.account.entity.UserAndRole;
import com.cnrvoice.base.hibernate.dao.generic.GenericAutoSfHibernateDao;

@Repository
public class UserAndRoleDao extends GenericAutoSfHibernateDao<UserAndRole, String> {
	public List<UserAndRole> getUserAndRoleByUserUuid(String uuid) {
		return query("from UserAndRole where userUuid='" + uuid + "'");
	}

	public List<UserAndRole> byUserAndRoleWhere(String[] uuid,
			String[] roleWhere) {

		DetachedCriteria crit1 = this.createDetachedCriteria();
		crit1.add(Restrictions.in("userUuid", uuid));
		crit1.add(Restrictions.in("roleUuid", roleWhere));

		List<UserAndRole> userAndRoles = this.queryByCriteria(crit1);

		return userAndRoles;
	}

	public List<UserAndRole> byRoleWhere(String[] roleWhere) {

		DetachedCriteria crit1 = this.createDetachedCriteria();

		crit1.add(Restrictions.in("roleUuid", roleWhere));

		List<UserAndRole> userAndRoles = this.queryByCriteria(crit1);

		return userAndRoles;
	}

}
