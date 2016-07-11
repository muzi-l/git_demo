/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: Guohong Mao
 * @date: 2012-4-11 上午10:18:57
 * @Description:
 * 
 */
package com.cnrvoice.account.service;

import java.util.List;

import com.cnrvoice.account.entity.RoleAndPermi;

public interface RoleAndPermiService
{
	List<RoleAndPermi> getRoleAndPermiByRoleUuid(String uuid);
}
