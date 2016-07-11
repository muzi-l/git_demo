package com.cnrvoice.cheba.entity.query;

import com.cnrvoice.base.hibernate.query.GenericHibernateQuery;
import com.cnrvoice.cheba.entity.Questionbank;

public class QuestionbankQuery extends GenericHibernateQuery<Questionbank>
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String programUuidQuery;
	
	private String questionTitleQuery;
	
	public String getProgramUuidQuery()
	{
		return programUuidQuery;
	}
	
	public void setProgramUuidQuery(String programUuidQuery)
	{
		this.programUuidQuery = programUuidQuery;
	}
	
	public String getQuestionTitleQuery()
	{
		return questionTitleQuery;
	}
	
	public void setQuestionTitleQuery(String questionTitleQuery)
	{
		this.questionTitleQuery = questionTitleQuery;
	}
	
}
