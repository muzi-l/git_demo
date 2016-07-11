/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-4-25 下午3:40:56
 * @Description:
 * 
 */
package com.cnrvoice.cheba.daoperator;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.cnrvoice.account.context.CurrentUserContext;
import com.cnrvoice.account.context.DaoOperatorHolder;
import com.cnrvoice.base.dao.DaoOperator;

@Service
public class ChebaDaoOperator implements DaoOperator
{
	public String getOperatorUuid()
	{
		String key = DaoOperatorHolder.getOperatorKey();
		if (StringUtils.isNotBlank(key)){
			return key;
		}
		return CurrentUserContext.getCurrentUser().getUuid();
	}
}
