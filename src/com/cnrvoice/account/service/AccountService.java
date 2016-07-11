/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-3-8 ����2:58:30
 * @Description:
 * 
 */
package com.cnrvoice.account.service;

import com.cnrvoice.account.entity.User;

public interface AccountService
{
	public User getUserAccountByLoginName(String loginName);
}
