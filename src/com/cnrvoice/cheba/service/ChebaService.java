package com.cnrvoice.cheba.service;

import java.util.List;

import com.cnrvoice.cheba.entity.Program;
import com.cnrvoice.cheba.entity.Questionbank;
import com.cnrvoice.cheba.entity.query.ProgramQuery;
import com.cnrvoice.cheba.entity.query.QuestionbankQuery;

public interface ChebaService
{
	List<Program> query(ProgramQuery programQuery);
	
	Program addProgram(Program program);
	
	void updateProgram(Program program);
	
	void updateProgramStatus(Program program);
	
	List<Questionbank> queryQuestionbank(QuestionbankQuery questionbankQuery);
	
	Long queryCountQuestionbank(QuestionbankQuery questionbankQuery);
	
	void addQuestionbank(Questionbank questionbank);
	
	List<Program> loadAllProgram();
	
	Program getProgramByUuid(String uuid);
	
	void updateQuestionbank(Questionbank questionbank);
	
	Program getOpenedProgram();
	
	Questionbank getquestionbankByProgramUuid(String programUuid);
	
	void sortQuestionbank(String fromUuid, Integer toPosition);
	
	Integer getMaxAvailableSortPosition(String fromUuid);

	Integer getCurrentMaxSortNo(String programUuidQuery);
	
}
