package com.cnrvoice.cheba.service;

import java.lang.reflect.InvocationTargetException;

import com.cnrvoice.cheba.entity.AnswerHistory;
import com.cnrvoice.cheba.entity.Program;
import com.cnrvoice.cheba.entity.Questionbank;

public interface HostService
{
	
	Program getCurrProgram();
	
	void invalidQuestion(String questionUuid)
			throws IllegalAccessException, InvocationTargetException;

	Questionbank getNextQuestion(String programUuid)
			throws IllegalAccessException, InvocationTargetException;

	Questionbank getQuestion(String questionUuid);

	Program getProgram(String programUuid);

	void recordAnswerCase(AnswerHistory answerHistory);
	
}
