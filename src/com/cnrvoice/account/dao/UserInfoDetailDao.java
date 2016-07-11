/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-3-8 ����3:07:16
 * @Description:
 * 
 */
package com.cnrvoice.account.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.cnrvoice.account.entity.UserInfoDetail;
import com.cnrvoice.base.hibernate.dao.generic.GenericAutoSfHibernateDao;

@Repository
public class UserInfoDetailDao extends GenericAutoSfHibernateDao<UserInfoDetail, String>
{
    public List<UserInfoDetail> queryUserInfoDetail(Integer key)
	{
		DetachedCriteria  dcr=createDetachedCriteria();
		if (key!=null&&key!=0)
		{
		dcr.add(Restrictions.eq("userinfoId",  key));
		}
		return queryByCriteria(dcr);
		
//		List<UserInfoDetail> list = null;
//        Session session = getSession();
//        String sql = "select subsystem_uuid as subsystemUuid from unified_userinfo_detail where userinfo_id=:userinfoId";
//        Query query = session.createSQLQuery(sql);
//        query.setParameter("userinfoId", key);
//        query.setResultTransformer(Transformers.aliasToBean(UserInfoDetail.class));
//        list = (List<UserInfoDetail>)query.list();
//        return list;
	}
	public List<UserInfoDetail> queryUserInfoDetailBysubsystemUuid(String subsystemUuid)
	{
		DetachedCriteria  dcr=createDetachedCriteria();
		if (StringUtils.isNotEmpty(subsystemUuid))
		{
		dcr.add(Restrictions.eq("subsystemUuid",  subsystemUuid ));
		}
		return queryByCriteria(dcr);
	}
	public List<UserInfoDetail> queryUserInfoDetailAssubsystemUuid(String subsystemUuid)
	{
		DetachedCriteria  dcr=createDetachedCriteria();
		if (StringUtils.isNotEmpty(subsystemUuid))
		{
		dcr.add(Restrictions.like("subsystemUuid","%"+subsystemUuid+"%" ));
		}
		return queryByCriteria(dcr);
	}
	public List<UserInfoDetail> queryUserInfoDetailByParameters( Integer uuid)
	{
		DetachedCriteria  dcr=createDetachedCriteria();
		if (uuid!=null)
		{
			dcr.add(Restrictions.eq("userinfoId",uuid));
		}
		return queryByCriteria(dcr);
	}
	
	public UserInfoDetail queryUserInfoDetail(Integer uuid,String subsystemUuid)
	{
		DetachedCriteria  dcr=createDetachedCriteria();
		if (uuid!=null)
		{
			dcr.add(Restrictions.eq("userinfoId",uuid));
		}
		if (StringUtils.isNotBlank(subsystemUuid))
		{
			dcr.add(Restrictions.eq("subsystemUuid",subsystemUuid));
		}
		List<UserInfoDetail> list = queryByCriteria(dcr);
		return list.size() > 0 ? list.get(0) : null;
	}
}
