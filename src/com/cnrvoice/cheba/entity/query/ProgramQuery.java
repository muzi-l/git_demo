package com.cnrvoice.cheba.entity.query;

import com.cnrvoice.base.hibernate.query.GenericHibernateQuery;
import com.cnrvoice.cheba.entity.Program;

public class ProgramQuery extends GenericHibernateQuery<Program>
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String programNameQuery;
	private String programPeriodsQuery;
	
	public String getProgramNameQuery()
	{
		return programNameQuery;
	}
	
	public void setProgramNameQuery(String programNameQuery)
	{
		this.programNameQuery = programNameQuery;
	}
	
	public String getProgramPeriodsQuery()
	{
		return programPeriodsQuery;
	}
	
	public void setProgramPeriodsQuery(String programPeriodsQuery)
	{
		this.programPeriodsQuery = programPeriodsQuery;
	}
	
}
