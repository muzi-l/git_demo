package com.cnrvoice.cheba.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.cnrvoice.base.hibernate.dao.generic.GenericAutoSfHibernateDao;
import com.cnrvoice.base.hibernate.util.HibernateLikeExprUtil;
import com.cnrvoice.cheba.entity.Program;
import com.cnrvoice.cheba.entity.query.ProgramQuery;

@Repository
public class ProgramDao extends GenericAutoSfHibernateDao<Program, String>
{
	
	public List<Program> query(ProgramQuery programQuery)
	{
		DetachedCriteria crit = this.createDetachedCriteria();
		
		if (StringUtils.isNotBlank(programQuery.getProgramNameQuery()))
		{
			crit.add(HibernateLikeExprUtil.createAnyWhereMatchLikeExpr(
					"programName", programQuery.getProgramNameQuery()));
		}
		if (StringUtils.isNotBlank(programQuery.getProgramPeriodsQuery()))
		{
			crit.add(HibernateLikeExprUtil.createAnyWhereMatchLikeExpr(
					"programPeriods", programQuery.getProgramPeriodsQuery()));
		}
		List<Program> programs = null;
		try
		{
			programs = this.queryByCriteria(crit, programQuery.getPageOrder());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return programs;
	}
	
	public Program getByStatus(String status)
	{
		DetachedCriteria crit = this.createDetachedCriteria();
		crit.add(Restrictions.eq("status", status));
		List<Program> programs = queryByCriteria(crit);
		Program program = null;
		if (programs.size() == 1)
		{
			program = programs.get(0);
		}
		return program;
	}
	
}
