/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-3-8 ����3:07:56
 * @Description:
 * 
 */
package com.cnrvoice.account.service;

import java.util.List;

import com.cnrvoice.account.entity.SubSystem;
import com.cnrvoice.base.paging.JEasyPageOrder;

public interface SubSystemService
{

	List<SubSystem> querySubSystem(JEasyPageOrder pageOrder);

	void subSystemAdd(SubSystem subSystem);

	void subSystemUpdate(SubSystem subSystem);

	boolean isrepeat(SubSystem subSystem);
	
}
