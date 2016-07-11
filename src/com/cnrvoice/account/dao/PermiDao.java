/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-3-8 ����2:58:30
 * @Description:
 * 
 */
package com.cnrvoice.account.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.cnrvoice.account.entity.Permi;
import com.cnrvoice.base.hibernate.dao.generic.GenericAutoSfHibernateDao;

@Repository
public class PermiDao extends GenericAutoSfHibernateDao<Permi, String>
{
	public List<Permi> queryPermi(String parameter,String value) {
		DetachedCriteria crit = createDetachedCriteria();
		if (StringUtils.isNotEmpty(value))
		{
			crit.add(Restrictions.eq(parameter,value));
		}
		return queryByCriteria(crit);
	}
}
