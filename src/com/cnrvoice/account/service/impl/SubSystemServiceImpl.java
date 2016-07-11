/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: Jirui Zhao
 * @date: 2012-3-8 ����3:08:27
 * @Description:
 * 
 */
package com.cnrvoice.account.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cnrvoice.account.dao.SubSystemDao;
import com.cnrvoice.account.entity.SubSystem;
import com.cnrvoice.account.service.SubSystemService;
import com.cnrvoice.base.exception.UnCheckedException;
import com.cnrvoice.base.paging.JEasyPageOrder;

@Service
public class SubSystemServiceImpl implements SubSystemService
{
	@Autowired
	private SubSystemDao subSystemDao;
	
	public SubSystemDao getSubSystemDao()
	{
		return subSystemDao;
	}
	
	public void setSubSystemDao(SubSystemDao subSystemDao)
	{
		this.subSystemDao = subSystemDao;
	}
	
	@Override
	public List<SubSystem> querySubSystem(JEasyPageOrder pageOrder)
	{
		return subSystemDao.querySubSystem(pageOrder);
	}
	
	@Override
	@Transactional
	public void subSystemAdd(SubSystem subSystem)
	{
			subSystemDao.save(subSystem);
		
	}
	
	@Override
	@Transactional
	public void subSystemUpdate(SubSystem subSystem)
	{
			try
			{
				subSystemDao.update(subSystem);
			}
			catch (IllegalAccessException e)
			{
				e.printStackTrace();
			}
			catch (InvocationTargetException e)
			{
				e.printStackTrace();
			}
	}
	
	@Override
	public boolean isrepeat(SubSystem subSystem)
	{
		List<SubSystem> list = subSystemDao.isrepeat(subSystem.getName(), null);
		if (list != null)
		{
			for (SubSystem st : list)
			{
				if (st.getUuid().equals(subSystem.getUuid()))
				{
					list.remove(st);
					 break; 
				}
			}
		}
		Boolean bl = list != null && list.size() > 0 ? true : false;
		if (bl == true)
		{
			throw new UnCheckedException("biz.subsystem.100");
		}
		List<SubSystem> list2 = subSystemDao.isrepeat(null, subSystem.getKey());
		if (list2 != null)
		{
			for (SubSystem st : list2)
			{
				if (st.getUuid().equals(subSystem.getUuid()))
				{
					list2.remove(st);
					 break; 
				}
			}
		}
		Boolean b2 = list2 != null && list2.size() > 0 ? true : false;
		if (b2 == true)
		{
			throw new UnCheckedException("biz.subsystem.101");
		}
		return bl && b2;
	}
}
