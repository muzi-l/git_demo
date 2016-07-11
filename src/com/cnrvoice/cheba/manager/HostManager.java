package com.cnrvoice.cheba.manager;

import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cnrvoice.base.exception.UnCheckedException;
import com.cnrvoice.base.util.BeanCopyUtils;
import com.cnrvoice.cheba.entity.AnswerHistory;
import com.cnrvoice.cheba.entity.Program;
import com.cnrvoice.cheba.entity.Questionbank;
import com.cnrvoice.cheba.entity.vo.ProgramVo;
import com.cnrvoice.cheba.entity.vo.QuestionVo;
import com.cnrvoice.cheba.service.HostService;
import com.cnrvoice.cheba.service.question.QuestionPush;
import com.cnrvoice.euc.webservice.dto.AnswerCaseDto;
import com.cnrvoice.euc.webservice.dto.AnswerDto;
import com.cnrvoice.euc.webservice.dto.QuestionDto;
import com.cnrvoice.euc.webservice.dto.RewardAnswererDto;
import com.cnrvoice.euc.webservice.dto.RewardAudienceDto;
import com.cnrvoice.euc.webservice.rs.client.ChebaClient;

@Service
public class HostManager
{
	@Autowired
	private HostService hostService;
	
//	@Autowired
//	private EucUserInfoClient eucUserInfoClient;
	
	@Autowired
	private ChebaClient chebaClient;
	
	public ChebaClient getChebaClient()
	{
		return chebaClient;
	}

	public void setChebaClient(ChebaClient chebaClient)
	{
		this.chebaClient = chebaClient;
	}

//	public EucUserInfoClient getEucUserInfoClient()
//	{
//		return eucUserInfoClient;
//	}
//	
//	public void setEucUserInfoClient(EucUserInfoClient eucUserInfoClient)
//	{
//		this.eucUserInfoClient = eucUserInfoClient;
//	}
	
	public HostService getHostService()
	{
		return hostService;
	}
	
	public void setHostService(HostService hostService)
	{
		this.hostService = hostService;
	}
	
	public ProgramVo getCurrProgram() throws IllegalAccessException,
			InvocationTargetException
	{
		Program program = hostService.getCurrProgram();
		ProgramVo programVo = null;
		if (program != null)
		{
			programVo = new ProgramVo();
			BeanCopyUtils.copyProperties(programVo, program);
		}
		return programVo;
	}
	
	public Questionbank getNextQuestion(String programUuid)
			throws IllegalAccessException, InvocationTargetException
	{
		return hostService.getNextQuestion(programUuid);
	}
	
	public void answerQuestion(String programUuid, String userUuid,
			String questionUuid, String answer, boolean correct)
			throws IllegalAccessException, InvocationTargetException
	{
		try{
//			Questionbank question = hostService.getQuestion(questionUuid);
			AnswerDto answerDto = new AnswerDto();
			answerDto.setProgramUuid(programUuid);
			answerDto.setUserUuid(userUuid);
			answerDto.setQuestionUuid(questionUuid);
			answerDto.setAnswer(answer);
//			answerDto.setCorrectAnswer(question.getCorrectAnswer());
//			answerDto.setIsCorrect(correct);
//			answerDto.setAward(question.getQuestionBonus());
			// 记录答题
			boolean isSucc = chebaClient.recordAnswerHistory(answerDto);
			if(!isSucc){
				throw new RuntimeException("添加答题记录失败！");
			}
			// 该题失效
			hostService.invalidQuestion(questionUuid);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new UnCheckedException("biz.question.answer.101");
		}
	}

	public void pushQuestion(String programUuid, String questionUuid,
			String userUuid)
	{
		try
		{
			Program program = hostService.getProgram(programUuid);
			Questionbank questionbank = hostService.getQuestion(questionUuid);
			QuestionDto questionDto = new QuestionDto();
			questionDto.setProgramUuid(programUuid);
			questionDto.setQuestionUuid(questionUuid);
			questionDto.setAnswererUuid(userUuid);
			Calendar time = Calendar.getInstance();
			time.setTime(new Date());
			questionDto.setStartTime(time.getTime());
			time.add(Calendar.SECOND, program.getAnswerTime());
			questionDto.setEndTime(time.getTime());
			// 临时保存题目
			if(!chebaClient.saveQuestion(questionDto)){
				throw new UnCheckedException("");
			}
			QuestionVo questionVo = new QuestionVo();
			BeanCopyUtils.copyProperties(questionVo, questionbank);
			questionVo.setCurrAnswererUuid(userUuid);
			if (program.getAnswer_1_Uuid().equals(userUuid))
			{
				questionVo.setCurrAnswererName(program.getAnswer_1_Name());
			}
			else if (program.getAnswer_2_Uuid().equals(userUuid))
			{
				questionVo.setCurrAnswererName(program.getAnswer_2_Name());
			}
			else if (program.getAnswer_3_Uuid().equals(userUuid))
			{
				questionVo.setCurrAnswererName(program.getAnswer_3_Name());
			}
			questionVo.setAnswerTime(program.getAnswerTime());
			questionVo.setCompereName(program.getCompereName());
			// 推送题目
			QuestionPush.push(questionVo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new UnCheckedException("biz.question.push.101");
		}
	}

	public void reward(String programUuid, String userUuid,
			boolean isAllCorrect, String questionUuid, Double questionBonus, Integer awardNum)
	{
		try
		{
			if (isAllCorrect)
			{
				RewardAnswererDto rewardAnswererDto = new RewardAnswererDto();
				rewardAnswererDto.setAnswererUuid(userUuid);
				rewardAnswererDto.setAward(questionBonus);
				chebaClient.rewardAnswerer(rewardAnswererDto);
			}else{
				RewardAudienceDto rewardAudienceDto = new RewardAudienceDto();
				rewardAudienceDto.setProgramUuid(programUuid);
				rewardAudienceDto.setAnswererUuid(userUuid);
				rewardAudienceDto.setAward(questionBonus);
				rewardAudienceDto.setMaxRewardUserNumber(awardNum);
				chebaClient.rewardAudience(rewardAudienceDto);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new UnCheckedException("biz.reward.101");
		}
	}

	public AnswerCaseDto getAnswerCase(String programUuid, String questionUuid)
	{
		try
		{
			AnswerCaseDto answerCaseDto = new AnswerCaseDto();
			answerCaseDto.setProgramUuid(programUuid);
			answerCaseDto.setQuestionUuid(questionUuid);
			answerCaseDto.setAnswer_A("answer_A");
			answerCaseDto.setAnswer_B("answer_B");
			answerCaseDto.setAnswer_C("answer_C");
			answerCaseDto.setAnswer_D("answer_D");
			return chebaClient.getAnswerCase(answerCaseDto);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new UnCheckedException("biz.answer.case.101");
		}
	}

	public void recordAnswerCase(AnswerHistory answerHistory)
	{
		try
		{
			hostService.recordAnswerCase(answerHistory);
			QuestionDto questionDto = new QuestionDto();
			questionDto.setProgramUuid(answerHistory.getProgramUuid());
			questionDto.setQuestionUuid(answerHistory.getQuestionUuid());
			questionDto.setCorrectAnswer(answerHistory.getCorrectAnswer());
			if (!chebaClient.updateQuestion(questionDto))
			{
				throw new UnCheckedException("");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new UnCheckedException("biz.question.answer.102");
		}
	}
}
