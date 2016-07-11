package com.cnrvoice.account.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cnrvoice.account.entity.CommonAccount;
import com.cnrvoice.base.hibernate.dao.generic.GenericAutoSfHibernateDao;

@Repository
public class CommonAccountDao extends
		GenericAutoSfHibernateDao<CommonAccount, String> {
	public List<CommonAccount> loadAllStatusSet(String type) {
		String hsql = " from CommonAccount where type='" + type + "'";
		List<CommonAccount> CommonAccount = super.query(hsql);
		CommonAccount.size();

		return CommonAccount;

	}

	public CommonAccount getStatusSetByKey(String type, String key) {
		String hsql = "from CommonAccount where type='" + type + "' and key ='"
				+ key + "'";

		CommonAccount commonAccount = null;
		List<CommonAccount> fz = super.query(hsql);
		for (int i = 0; i < fz.size(); i++) {
			commonAccount = fz.get(i);

		}
		return commonAccount;
	}

}
