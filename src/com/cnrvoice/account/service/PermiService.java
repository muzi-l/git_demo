/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: Guohong Mao
 * @date: 2012-4-11 上午10:18:57
 * @Description:
 * 
 */
package com.cnrvoice.account.service;

import java.util.Set;

import com.cnrvoice.account.entity.Permi;

public interface PermiService
{
	Permi getPermiByUuid(String uuid);
	
	Set<String> getPermiSetByRoles(String roleUuids);
}
