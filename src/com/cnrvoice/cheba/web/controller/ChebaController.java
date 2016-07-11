package com.cnrvoice.cheba.web.controller;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cnrvoice.base.exception.UnCheckedException;
import com.cnrvoice.base.paging.JEasyPageOrder;
import com.cnrvoice.base.result.JEasyResultCreater;
import com.cnrvoice.base.result.Result;
import com.cnrvoice.base.result.ResultCreater;
import com.cnrvoice.cheba.entity.Program;
import com.cnrvoice.cheba.entity.Questionbank;
import com.cnrvoice.cheba.entity.query.ProgramQuery;
import com.cnrvoice.cheba.entity.query.QuestionbankQuery;
import com.cnrvoice.cheba.entity.vo.JEasyGridDataVo;
import com.cnrvoice.cheba.manager.ChebaManager;
import com.cnrvoice.cheba.service.biz.SortBiz;
import com.cnrvoice.euc.webservice.dto.UserDto;

@Controller
@RequestMapping("/manager/program")
public class ChebaController
{
	@Autowired
	private ChebaManager chebaManager;
	
	public ChebaManager getChebaManager()
	{
		return chebaManager;
	}
	
	public void setChebaManager(ChebaManager chebaManager)
	{
		this.chebaManager = chebaManager;
	}
	
	@RequestMapping(value = "/list")
	public ModelAndView programView()
	{
		return new ModelAndView("/manager/cheba_program_list");
	}
	
	@RequestMapping(value = "/query")
	@ResponseBody
	public Result<?> query(ProgramQuery programQuery,
			JEasyPageOrder jEasyPageOrder)
	{
		programQuery.setPageOrder(jEasyPageOrder);
		return JEasyResultCreater
				.createResult(chebaManager.query(programQuery));
	}
	
	@RequestMapping(value = "/add")
	@ResponseBody
	public Result<?> addProgram(Program program)
	{
		
		return ResultCreater.createResult(chebaManager.addProgram(program));
	}
	
	@RequestMapping(value = "/update")
	@ResponseBody
	public Result<?> updateProgram(Program program)
	{
		chebaManager.updateProgram(program);
		return ResultCreater.createResult();
	}
	
	@RequestMapping(value = "/status/update")
	@ResponseBody
	public Result<?> updateProgramStatus(Program program)
	{
		chebaManager.updateProgramStatus(program);
		return ResultCreater.createResult();
	}
	
	@RequestMapping(value = "/getProgramByUuid")
	@ResponseBody
	public Result<?> getProgramByUuid(String uuid)
	{
		return ResultCreater.createResult(chebaManager.getProgramByUuid(uuid));
	}
	
	@RequestMapping(value = "/getUser")
	@ResponseBody
	public Result<?> getUser(String uuid)
	{
		return ResultCreater.createResult(chebaManager.getUser());
	}
	
	@RequestMapping(value = "/queryUser")
	@ResponseBody
	public Result<?> queryUser(UserDto userDto)
	{
		return ResultCreater.createResult(chebaManager.queryUser(userDto));
	}
	
	@RequestMapping(value = "/questionbank/list")
	public ModelAndView questionbankView()
	{
		return new ModelAndView("/manager/cheba_questionbank_list");
	}
	
	@RequestMapping(value = "/questionbank/query")
	@ResponseBody
	public Result<?> queryQuestionbank(QuestionbankQuery questionbankQuery,
			JEasyPageOrder jEasyPageOrder)
	{
		questionbankQuery.setPageOrder(jEasyPageOrder);
		List<Questionbank> questionbanks = chebaManager
				.queryQuestionbank(questionbankQuery);
		
		if (null != questionbanks && !questionbanks.isEmpty())
		{
			Integer maxSortNo = chebaManager
					.getCurrentMaxSortNo(questionbankQuery
							.getProgramUuidQuery());
			
			for (Questionbank questionbank : questionbanks)
			{
				if (StringUtils.isNotBlank(questionbank.getSortNo()))
				{
					questionbank.setIndex(SortBiz.getIndexBySortNo(
							Integer.valueOf(questionbank.getSortNo()),
							maxSortNo));
				}
			}
		}
		
		Long total = chebaManager.queryCountQuestionbank(questionbankQuery);
		JEasyGridDataVo jEasyGridDataVo = new JEasyGridDataVo();
		jEasyGridDataVo.setTotal(total);
		jEasyGridDataVo.setRows(questionbanks);
		return ResultCreater.createResult(jEasyGridDataVo);
	}
	
	@RequestMapping(value = "/questionbank/loadAllProgram")
	@ResponseBody
	public Result<?> loadAllProgram()
	{
		return ResultCreater.createResult(chebaManager.loadAllProgram());
	}
	
	@RequestMapping(value = "/questionbank/add")
	@ResponseBody
	public Result<?> addQuestionbank(Questionbank questionbank)
	{
		chebaManager.addQuestionbank(questionbank);
		return ResultCreater.createResult();
	}
	
	@RequestMapping(value = "/questionbank/update")
	@ResponseBody
	public Result<?> updateQuestionbank(Questionbank questionbank)
	{
		chebaManager.updateQuestionbank(questionbank);
		return ResultCreater.createResult();
	}
	
	/**
	 * 题目排序
	 * 
	 * @param fromUuid
	 * @param toPosition
	 * @return
	 */
	@RequestMapping(value = "/questionbank/sort")
	@ResponseBody
	public Result<?> sortQuestionbank(
			@RequestParam(value = "fromUuid", required = true) String fromUuid,
			@RequestParam(value = "toPosition", required = true) Integer toPosition)
	{
		Result<?> result = null;
		try
		{
			chebaManager.sortQuestionbank(fromUuid, toPosition);
			result = ResultCreater.createResult();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			result = ResultCreater.createExResult(e);
		}
		return result;
	}
	
	/**
	 * 题目排序时获取当前最大可排序号
	 * 
	 * @param fromUuid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/questionbank/stick/maxAvailableSortNo")
	@ResponseBody
	public Result<?> getInfoMaxAvailableSortNo(
			@RequestParam(value = "fromUuid", required = true) String fromUuid)
			throws Exception
	{
		Result<?> result = null;
		try
		{
			result = ResultCreater.createResult(chebaManager
					.getMaxAvailableSortPosition(fromUuid));
		}
		catch (Exception e)
		{
			if (e instanceof UnCheckedException)
			{
				throw e;
			}
			e.printStackTrace();
			result = ResultCreater.createExResult(e);
		}
		return result;
	}
}
