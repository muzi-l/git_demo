package com.cnrvoice.cheba.dao;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.cnrvoice.base.hibernate.dao.generic.GenericAutoSfHibernateDao;
import com.cnrvoice.base.paging.JEasyPageOrder;
import com.cnrvoice.cheba.entity.Questionbank;
import com.cnrvoice.cheba.entity.query.QuestionbankQuery;

@Repository
public class QuestionbankDao extends
		GenericAutoSfHibernateDao<Questionbank, String>
{
	
	@SuppressWarnings("unchecked")
	public List<Questionbank> queryQuestionbank(
			QuestionbankQuery questionbankQuery)
	{
		List<Questionbank> list = null;
		Session session = null;
		try
		{
			session = getSession();
			session.beginTransaction();
			String sql = "select c.sort_no as sortNo,c.uuid as uuid,c.program_uuid as programUuid,c.question_title as questionTitle,c.difficulty_type as difficultyType,c.question_content as questionContent,c.answer_a as answer_A,c.answer_b as answer_B,c.answer_c as answer_C,c.answer_d as answer_D,c.correct_answer as correctAnswer from cheba_question_bank c where c.program_uuid=:programUuid";
			
			// 排序
			sql += " order by (c.sort_no+1) desc";
			
			Query query = session.createSQLQuery(sql);
			query.setParameter("programUuid",
					questionbankQuery.getProgramUuidQuery());
			
			// 分页
			JEasyPageOrder jEasyPageOrder = (JEasyPageOrder) (questionbankQuery
					.getPageOrder());
			query.setFirstResult(jEasyPageOrder.getPageSize()
					* (jEasyPageOrder.getPageNumber() - 1));
			query.setMaxResults(jEasyPageOrder.getPageSize());
			
			// 返回的结果封装到Questionbank中
			query.setResultTransformer(Transformers
					.aliasToBean(Questionbank.class));
			list = (List<Questionbank>) query.list();
			session.getTransaction().commit();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			// 回滚
			session.getTransaction().rollback();
		}
		finally
		{
			session.close();
		}
		return list;
	}
	
	public Long queryCountQuestionbank(QuestionbankQuery questionbankQuery)
	{
		Session session = null;
		Long total = null;
		try
		{
			session = getSession();
			session.beginTransaction();
			String sql = "select count(*) from cheba_question_bank c where c.program_uuid=:programUuid";
			
			// 排序
			sql += " order by (c.sort_no+1) desc";
			
			Query query = session.createSQLQuery(sql);
			query.setParameter("programUuid",
					questionbankQuery.getProgramUuidQuery());
			
			BigInteger count = (BigInteger) query.uniqueResult();
			total = count.longValue();
			session.getTransaction().commit();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			// 回滚
			session.getTransaction().rollback();
		}
		finally
		{
			session.close();
		}
		return total;
	}
	
	@SuppressWarnings("unchecked")
	public Questionbank getquestionbankByProgramUuid(String programUuid)
	{
		Session session = getSession();
		String sql = "select * from cheba_question_bank where program_uuid=:programUuid and is_deleted = 0 order by (sort_no+1) desc";
		Query query = session.createSQLQuery(sql).addEntity(Questionbank.class);
		query.setParameter("programUuid", programUuid);
		List<Questionbank> questionbanks = (List<Questionbank>) query.list();
		return questionbanks.size() == 0 ? null : questionbanks.get(0);
	}
	
	public List<Questionbank> getSortQueue(String programUuid)
	{
		String hql = "from Questionbank where programUuid='" + programUuid
				+ "' and sort_no is not null";
		
		return this.query(hql);
	}
	
	public void batchSaveOrUpdate(List<Questionbank> list)
	{
		for (Questionbank questionbank : list)
		{
			if (StringUtils.isBlank(questionbank.getUuid()))
			{
				questionbank.setId(null);
				questionbank.setCreatedTime(new Date());
				questionbank.setCreaterUuid(daoOperator.getOperatorUuid());
				questionbank.setUpdaterUuid(daoOperator.getOperatorUuid());
			}
		}
		
		getHibernateTemplate().saveOrUpdateAll(list);
		
		flush();
	}
	
	public Integer getCurrentMaxSortNo(String programUuidQuery)
	{
		List<Questionbank> questionbanks = getSortQueue(programUuidQuery);
		return questionbanks == null ? 0 : questionbanks.size();
	}
}
