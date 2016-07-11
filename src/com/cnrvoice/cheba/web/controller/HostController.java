package com.cnrvoice.cheba.web.controller;

import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cnrvoice.base.property.ContextPropertiesHolder;
import com.cnrvoice.base.result.Result;
import com.cnrvoice.base.result.ResultCreater;
import com.cnrvoice.cheba.entity.AnswerHistory;
import com.cnrvoice.cheba.entity.Questionbank;
import com.cnrvoice.cheba.entity.vo.ProgramVo;
import com.cnrvoice.cheba.manager.HostManager;
import com.cnrvoice.euc.webservice.dto.AnswerCaseDto;

@Controller
@RequestMapping("/host")
public class HostController
{
	private static String chatUrl;
	
	static{
		chatUrl = (String) ContextPropertiesHolder
				.getPropertyItem("chat.url");
	}
	
	@Autowired
	private HostManager hostManager;
	
	public HostManager getHostManager()
	{
		return hostManager;
	}
	
	public void setHostManager(HostManager hostManager)
	{
		this.hostManager = hostManager;
	}
	
	@RequestMapping(value = "/manage")
	public ModelAndView getHostManage()
	{
		return new ModelAndView("/host/manage", "chatUrl", chatUrl);
	}
	
	@RequestMapping(value = "/currProgram")
	@ResponseBody
	public Result<?> getCurrProgram() throws IllegalAccessException,
			InvocationTargetException
	{
		ProgramVo programVo = hostManager.getCurrProgram();
		return ResultCreater.createResult(programVo);
	}
	
	@RequestMapping(value = "/nextq")
	@ResponseBody
	public Result<?> nextQuestion(
			@RequestParam("programUuid") String programUuid)
			throws IllegalAccessException, InvocationTargetException
	{
		Questionbank questionbank = hostManager.getNextQuestion(programUuid);
		return ResultCreater.createResult(questionbank);
	}
	
	@RequestMapping(value = "/pushq")
	@ResponseBody
	public Result<?> pushQuestion(
			@RequestParam("programUuid") String programUuid, 
			@RequestParam("questionUuid") String questionUuid,
			@RequestParam("userUuid") String userUuid )
	{
		hostManager.pushQuestion(programUuid, questionUuid, userUuid);
		return ResultCreater.createResult(true);
	}
	
	@RequestMapping(value = "/select")
	@ResponseBody
	public Result<?> answerQuestion(
			@RequestParam("programUuid") String programUuid, 
			@RequestParam("userUuid") String userUuid, 
			@RequestParam("questionUuid") String questionUuid,
			@RequestParam("answer") String answer,
			@RequestParam("correct") boolean correct)
			throws IllegalAccessException, InvocationTargetException
	{
		hostManager.answerQuestion(programUuid, userUuid, questionUuid, answer, correct);
		return ResultCreater.createResult(true);
	}
	
	@RequestMapping(value = "/reward")
	@ResponseBody
	public Result<?> reward(
			@RequestParam("programUuid") String programUuid, 
			@RequestParam("userUuid") String userUuid, 
			@RequestParam("questionUuid") String questionUuid,
			@RequestParam("questionBonus") Double questionBonus,
			@RequestParam("awardNum") Integer awardNum,
			@RequestParam("isAllCorrect") boolean isAllCorrect)
	{
		hostManager.reward(programUuid, userUuid, isAllCorrect, questionUuid, questionBonus, awardNum);
		return ResultCreater.createResult(true);
	}
	
	@RequestMapping(value = "/case/answer")
	@ResponseBody
	public Result<?> getAnswerCase(
			@RequestParam("programUuid") String programUuid,
			@RequestParam("questionUuid") String questionUuid)
	{
		AnswerCaseDto answerCaseDto = hostManager.getAnswerCase(programUuid, questionUuid);
		return ResultCreater.createResult(answerCaseDto);
	}
	
	@RequestMapping(value = "/case/answer/record")
	@ResponseBody
	public Result<?> recodeAnswerCase(AnswerHistory AnswerHistory)
	{
		hostManager.recordAnswerCase(AnswerHistory);
		return ResultCreater.createResult(true);
	}
}
