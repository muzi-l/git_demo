package com.cnrvoice.cheba.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cnrvoice.cheba.entity.Program;
import com.cnrvoice.cheba.entity.Questionbank;
import com.cnrvoice.cheba.entity.query.ProgramQuery;
import com.cnrvoice.cheba.entity.query.QuestionbankQuery;
import com.cnrvoice.cheba.service.ChebaService;
import com.cnrvoice.euc.webservice.dto.UserDto;
import com.cnrvoice.euc.webservice.dto.UserResultDto;
import com.cnrvoice.euc.webservice.rs.client.EucUserInfoClient;

@Service
public class ChebaManager
{
	@Autowired
	private ChebaService chebaService;
	@Autowired
	private EucUserInfoClient eucUserInfoClient;
	
	public ChebaService getChebaService()
	{
		return chebaService;
	}
	
	public void setChebaService(ChebaService chebaService)
	{
		this.chebaService = chebaService;
	}
	
	public EucUserInfoClient getEucUserInfoClient()
	{
		return eucUserInfoClient;
	}
	
	public void setEucUserInfoClient(EucUserInfoClient eucUserInfoClient)
	{
		this.eucUserInfoClient = eucUserInfoClient;
	}
	
	public List<Program> query(ProgramQuery programQuery)
	{
		return chebaService.query(programQuery);
	}
	
	public Program addProgram(Program program)
	{
		return chebaService.addProgram(program);
	}
	
	public void updateProgram(Program program)
	{
		chebaService.updateProgram(program);
	}
	
	public void updateProgramStatus(Program program)
	{
		chebaService.updateProgramStatus(program);
	}
	
	public Program getProgramByUuid(String uuid)
	{
		return chebaService.getProgramByUuid(uuid);
	}
	
	public Program getOpenedProgram()
	{
		return chebaService.getOpenedProgram();
	}
	
	public List<UserDto> getUser()
	{
		UserResultDto result = eucUserInfoClient
				.queryUserByCondition(new UserDto());
		
		return result.getData().getRows();
	}
	
	public List<UserDto> queryUser(UserDto userDto)
	{
		UserResultDto result = eucUserInfoClient.queryUserByCondition(userDto);
		
		return result.getData().getRows();
	}
	
	public List<Questionbank> queryQuestionbank(
			QuestionbankQuery questionbankQuery)
	{
		List<Questionbank> questionbanks = chebaService
				.queryQuestionbank(questionbankQuery);
		for (Questionbank questionbank : questionbanks)
		{
			questionbank.setProgramNameStr(chebaService.getProgramByUuid(
					questionbank.getProgramUuid()).getProgramPeriods());
		}
		return questionbanks;
	}
	
	public Long queryCountQuestionbank(QuestionbankQuery questionbankQuery)
	{
		return chebaService.queryCountQuestionbank(questionbankQuery);
	}
	
	public void addQuestionbank(Questionbank questionbank)
	{
		chebaService.addQuestionbank(questionbank);
	}
	
	public List<Program> loadAllProgram()
	{
		return chebaService.loadAllProgram();
	}
	
	public void updateQuestionbank(Questionbank questionbank)
	{
		chebaService.updateQuestionbank(questionbank);
	}
	
	public void sortQuestionbank(String fromUuid, Integer toPosition)
	{
		chebaService.sortQuestionbank(fromUuid, toPosition);
	}
	
	public Integer getMaxAvailableSortPosition(String fromUuid)
	{
		return chebaService.getMaxAvailableSortPosition(fromUuid);
	}
	
	public Integer getCurrentMaxSortNo(String programUuidQuery)
	{
		return chebaService.getCurrentMaxSortNo(programUuidQuery);
	}
}
