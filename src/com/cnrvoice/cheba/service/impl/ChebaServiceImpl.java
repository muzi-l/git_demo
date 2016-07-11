package com.cnrvoice.cheba.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cnrvoice.base.exception.UnCheckedException;
import com.cnrvoice.base.property.ContextPropertiesHolder;
import com.cnrvoice.cheba.constant.ChebaConstant;
import com.cnrvoice.cheba.dao.ProgramDao;
import com.cnrvoice.cheba.dao.QuestionbankDao;
import com.cnrvoice.cheba.entity.Program;
import com.cnrvoice.cheba.entity.Questionbank;
import com.cnrvoice.cheba.entity.query.ProgramQuery;
import com.cnrvoice.cheba.entity.query.QuestionbankQuery;
import com.cnrvoice.cheba.service.ChebaService;
import com.cnrvoice.cheba.service.biz.SortBiz;

@Service
public class ChebaServiceImpl implements ChebaService
{
	private static Integer maxSortNo = null;
	static
	{
		String maxSortNoStr = (String) ContextPropertiesHolder
				.getPropertyItem("max.sort.no");
		maxSortNo = Integer.valueOf(maxSortNoStr);
	}
	@Autowired
	private ProgramDao programDao;
	@Autowired
	private QuestionbankDao questionbankDao;
	
	public ProgramDao getProgramDao()
	{
		return programDao;
	}
	
	public void setProgramDao(ProgramDao programDao)
	{
		this.programDao = programDao;
	}
	
	public QuestionbankDao getQuestionbankDao()
	{
		return questionbankDao;
	}
	
	public void setQuestionbankDao(QuestionbankDao questionbankDao)
	{
		this.questionbankDao = questionbankDao;
	}
	
	@Override
	public List<Program> query(ProgramQuery programQuery)
	{
		List<Program> result = programDao.query(programQuery);
		for (Program program : result)
		{
			Questionbank questionbankExample = new Questionbank();
			questionbankExample.setProgramUuid(program.getUuid());
			
			program.setQuestionbankCount(String.valueOf(questionbankDao
					.queryByExample(questionbankExample).size()));
		}
		
		return result;
	}
	
	@Override
	public Program addProgram(Program program)
	{
		if (program.getStatus().equals(ChebaConstant.PROGRAM_STATUS_OPEN))
		{
			Program programExample = new Program();
			programExample.setStatus(program.getStatus());
			List<Program> programs = programDao.queryByExample(programExample);
			if (programs.size() != 0)
			{
				throw new UnCheckedException("biz.program.101", "");
			}
		}
		programDao.save(program);
		return program;
	}
	
	@Override
	public void updateProgram(Program program)
	{
		try
		{
			programDao.update(program);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new UnCheckedException("biz.program.100", "");
		}
	}
	
	@Override
	public void updateProgramStatus(Program program)
	{
		if (program.getStatus().equals(ChebaConstant.PROGRAM_STATUS_OPEN))
		{
			Program programExample = new Program();
			programExample.setStatus(program.getStatus());
			List<Program> programs = programDao.queryByExample(programExample);
			if (programs.size() != 0)
			{
				throw new UnCheckedException("biz.program.101", "");
			}
			
		}
		else if (program.getStatus()
				.equals(ChebaConstant.PROGRAM_STATUS_CLOSED))
		{
			// 结束节目
		}
		try
		{
			programDao.update(program);
		}
		catch (Exception e)
		{
			throw new UnCheckedException("biz.program.100", "");
		}
	}
	
	@Override
	public Program getProgramByUuid(String uuid)
	{
		Program result = programDao.get(uuid);
		Questionbank questionbankExample = new Questionbank();
		questionbankExample.setProgramUuid(result.getUuid());
		result.setQuestionbankCount(String.valueOf(questionbankDao
				.queryByExample(questionbankExample).size()));
		return result;
	}
	
	@Override
	public Program getOpenedProgram()
	{
		Program program = new Program();
		program.setStatus(ChebaConstant.PROGRAM_STATUS_OPEN);
		return programDao.queryByExample(program).size() == 0 ? null
				: programDao.queryByExample(program).get(0);
	}
	
	@Override
	public List<Questionbank> queryQuestionbank(
			QuestionbankQuery questionbankQuery)
	{
		return questionbankDao.queryQuestionbank(questionbankQuery);
	}
	
	@Override
	public Long queryCountQuestionbank(QuestionbankQuery questionbankQuery)
	{
		return questionbankDao.queryCountQuestionbank(questionbankQuery);
	}
	
	@Override
	public void addQuestionbank(Questionbank questionbank)
	{
		questionbank.setIsDeleted(Boolean.FALSE);
		
		firstSaveQuestionbank(questionbank);
	}
	
	@Override
	public List<Program> loadAllProgram()
	{
		return programDao.loadAll();
	}
	
	@Override
	public void updateQuestionbank(Questionbank questionbank)
	{
		try
		{
			questionbankDao.update(questionbank);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public Questionbank getquestionbankByProgramUuid(String programUuid)
	{
		return questionbankDao.getquestionbankByProgramUuid(programUuid);
	}
	
	// -----------------排序相关-------------------
	public void firstSaveQuestionbank(Questionbank questionbank)
	{
		Integer currentMaxSortNo = questionbankDao
				.getCurrentMaxSortNo(questionbank.getProgramUuid());
		SortBiz<Questionbank> sortBiz = new SortBiz<Questionbank>();
		List<Questionbank> list = questionbankDao.getSortQueue(questionbank
				.getProgramUuid());
		sortBiz.save2(list, questionbank, currentMaxSortNo, maxSortNo);
		questionbankDao.batchSaveOrUpdate(list);
	}
	
	@Override
	public void sortQuestionbank(String fromUuid, Integer toPosition)
	{
		// 将要被移动的题目
		Questionbank questionbank = questionbankDao.get(fromUuid);
		SortBiz<Questionbank> sortBiz = new SortBiz<Questionbank>();
		List<Questionbank> list = questionbankDao.getSortQueue(questionbank
				.getProgramUuid());
		sortBiz.sort(list, questionbank, toPosition, maxSortNo);
		questionbankDao.batchSaveOrUpdate(list);
	}
	
	@Override
	public Integer getMaxAvailableSortPosition(String fromUuid)
	{
		Questionbank questionbank = questionbankDao.get(fromUuid);
		Integer currentMaxSortNo = questionbankDao
				.getCurrentMaxSortNo(questionbank.getProgramUuid());
		Integer upper = null;
		if (StringUtils.isNotBlank(questionbank.getSortNo()))
		{ // 已经在队列内
			if (currentMaxSortNo == maxSortNo)
			{ // 队列已经满了
				upper = maxSortNo;
			}
			else
			{
				upper = currentMaxSortNo;
			}
		}
		else
		{ // 不在队列内
			if (currentMaxSortNo == maxSortNo)
			{ // 队列已经满了
				upper = maxSortNo;
			}
			else
			{
				upper = currentMaxSortNo + 1;
			}
		}
		return upper;
	}
	
	@Override
	public Integer getCurrentMaxSortNo(String programUuidQuery)
	{
		return questionbankDao.getCurrentMaxSortNo(programUuidQuery);
	}
	
	// ---------------排序end---------------------
}
