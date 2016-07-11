package com.cnrvoice.cheba.dao;

import org.springframework.stereotype.Repository;

import com.cnrvoice.base.hibernate.dao.generic.GenericAutoSfHibernateDao;
import com.cnrvoice.cheba.entity.AnswerHistory;

@Repository
public class AnswerHistoryDao extends
		GenericAutoSfHibernateDao<AnswerHistory, String>
{
	
}
