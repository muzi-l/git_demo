package com.cnrvoice.cheba.service.impl;

import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cnrvoice.cheba.constant.ChebaConstant;
import com.cnrvoice.cheba.dao.AnswerHistoryDao;
import com.cnrvoice.cheba.dao.ProgramDao;
import com.cnrvoice.cheba.dao.QuestionbankDao;
import com.cnrvoice.cheba.entity.AnswerHistory;
import com.cnrvoice.cheba.entity.Program;
import com.cnrvoice.cheba.entity.Questionbank;
import com.cnrvoice.cheba.service.HostService;

@Service
public class HostServiceImpl implements HostService
{
	
	@Autowired
	private ProgramDao programDao;
	
	@Autowired
	private QuestionbankDao questionbankDao;
	
	@Autowired
	private AnswerHistoryDao answerHistoryDao;

	public AnswerHistoryDao getAnswerHistoryDao()
	{
		return answerHistoryDao;
	}

	public void setAnswerHistoryDao(AnswerHistoryDao answerHistoryDao)
	{
		this.answerHistoryDao = answerHistoryDao;
	}

	public QuestionbankDao getQuestionbankDao()
	{
		return questionbankDao;
	}
	
	public void setQuestionbankDao(QuestionbankDao questionbankDao)
	{
		this.questionbankDao = questionbankDao;
	}
	
	public ProgramDao getProgramDao()
	{
		return programDao;
	}
	
	public void setProgramDao(ProgramDao programDao)
	{
		this.programDao = programDao;
	}
	
	@Override
	public Program getCurrProgram()
	{
		return programDao.getByStatus(ChebaConstant.PROGRAM_STATUS_OPEN);
	}
	
	@Override
	public Questionbank getNextQuestion(String programUuid)
			throws IllegalAccessException, InvocationTargetException
	{
		return questionbankDao.getquestionbankByProgramUuid(programUuid);
	}
	
	@Override
	public void invalidQuestion(String questionUuid)
			throws IllegalAccessException, InvocationTargetException
	{
		
		Questionbank question = new Questionbank();
		question.setUuid(questionUuid);
		question.setIsDeleted(true);
		questionbankDao.update(question);
	}

	@Override
	public Questionbank getQuestion(String questionUuid)
	{
		return questionbankDao.get(questionUuid);
	}

	@Override
	public Program getProgram(String programUuid)
	{
		return programDao.get(programUuid);
	}

	@Override
	public void recordAnswerCase(AnswerHistory answerHistory)
	{
		answerHistoryDao.save(answerHistory);
	}
	
}
