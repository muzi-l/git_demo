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

import com.cnrvoice.account.entity.SubSystem;
import com.cnrvoice.base.hibernate.dao.generic.GenericAutoSfHibernateDao;
import com.cnrvoice.base.paging.JEasyPageOrder;

@Repository
public class SubSystemDao extends GenericAutoSfHibernateDao<SubSystem, String>
{
	public List<SubSystem> querySubSystemByUuid(String uuid)
	{
		DetachedCriteria crit = createDetachedCriteria();
		if (StringUtils.isNotEmpty(uuid))
		crit.add(Restrictions.like("uuid", uuid ));
		 return queryByCriteria(crit);
	}

	public List<SubSystem> querySubSystem(JEasyPageOrder pageOrder)
	{
		DetachedCriteria crit = createDetachedCriteria();
		 try
		{
			return queryByCriteria(crit,pageOrder);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public List<SubSystem> isrepeat(String name, String key)
	{
		DetachedCriteria crit = createDetachedCriteria();
		if (StringUtils.isNotEmpty(name))
			crit.add(Restrictions.like("name", name));
		if (StringUtils.isNotEmpty(key))
			crit.add(Restrictions.like("key", key));
		return queryByCriteria(crit);
	}
	
	public SubSystem getSubSystemByKey(String key)
	{
		DetachedCriteria crit = createDetachedCriteria();
		if (StringUtils.isNotEmpty(key))
			crit.add(Restrictions.eq("key", key));
		List<SubSystem> list = queryByCriteria(crit);
		return list.size() > 0 ? list.get(0) : null;
	}
}
