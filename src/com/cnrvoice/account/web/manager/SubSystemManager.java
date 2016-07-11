/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: Jirui Zhao
 * @date: 2012-3-8 ����3:08:27
 * @Description:
 * 
 */
package com.cnrvoice.account.web.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cnrvoice.account.entity.SubSystem;
import com.cnrvoice.account.service.SubSystemService;
import com.cnrvoice.base.paging.JEasyPageOrder;

@Service
public class SubSystemManager
{
	@Autowired
	public SubSystemService subSystemService;
	public List<SubSystem> querySubSystem(JEasyPageOrder pageOrder)
	{
		return subSystemService.querySubSystem(pageOrder);
	}
	public SubSystemService getSubSystemService()
	{
		return subSystemService;
	}
	public void setSubSystemService(SubSystemService subSystemService)
	{
		this.subSystemService = subSystemService;
	}
	public void subSystemAdd(SubSystem subSystem)
	{
		subSystemService. subSystemAdd(subSystem);
		
	}
	public void subSystemUpdate(SubSystem subSystem)
	{
		subSystemService. subSystemUpdate(subSystem);
		
	}
	public boolean isrepeat(SubSystem subSystem)
	{
		return subSystemService.isrepeat(subSystem);
	}
}
