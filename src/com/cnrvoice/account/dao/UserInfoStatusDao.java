package com.cnrvoice.account.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.cnrvoice.account.constant.UnifedConst;
import com.cnrvoice.account.entity.UserInfoStatus;
import com.cnrvoice.base.hibernate.dao.generic.GenericAutoSfHibernateDao;

@Repository
public class UserInfoStatusDao extends GenericAutoSfHibernateDao<UserInfoStatus, String>
{
	public List<UserInfoStatus> getAllUserInfoType()
	{
		DetachedCriteria crit = createDetachedCriteria();
		crit.add(Restrictions.eq("type", UnifedConst.USERINFO_TYPE ));
		 return queryByCriteria(crit);
	}
	public UserInfoStatus getUserInfoTypeValue(String key){
		DetachedCriteria crit = createDetachedCriteria();
		crit.add(Restrictions.eq("key", key));
		crit.add(Restrictions.eq("type", UnifedConst.USERINFO_TYPE ));
		 List<UserInfoStatus> list=queryByCriteria(crit);
		 return list!=null&&list.size()>0?list.get(0):null;
	}
	public List<UserInfoStatus> getAllUserInfoStatus()
	{
		DetachedCriteria crit = createDetachedCriteria();
		crit.add(Restrictions.eq("type", UnifedConst.USERINFO_STATUS));
		 return queryByCriteria(crit);
	}
	public UserInfoStatus getUserInfoStatusValue(String key){
		DetachedCriteria crit = createDetachedCriteria();
		crit.add(Restrictions.eq("type", UnifedConst.USERINFO_STATUS));
		crit.add(Restrictions.eq("key", key));
		 List<UserInfoStatus> list=queryByCriteria(crit);
		 return list!=null&&list.size()>0?list.get(0):null;
	}
}
