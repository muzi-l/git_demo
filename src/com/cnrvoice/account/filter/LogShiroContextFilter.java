/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-4-1 下午6:06:21
 * @Description:
 * 
 */
package com.cnrvoice.account.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.MDC;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.cnrvoice.account.constant.AccountConst;
import com.cnrvoice.unified.webservice.dto.UserInfoDto;

public class LogShiroContextFilter implements Filter
{
	private final static String DEFAULT_USERID = "anonymous";
	
	@Override
	public void destroy()
	{
		
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException
	{
		Subject currentUser = SecurityUtils.getSubject();
		UserInfoDto userInfo = getCurrentUserInfo();
		
		if (currentUser != null && userInfo != null)
		{
			MDC.put("userId", userInfo.getId());
			MDC.put("userName", userInfo.getLoginName());
		}
		else
		{
			MDC.put("userId", "");
			MDC.put("userName", DEFAULT_USERID);
		}
		
		chain.doFilter(request, response);
	}
	
	private UserInfoDto getCurrentUserInfo()
	{
		Subject currentUser = SecurityUtils.getSubject();
		
		if (currentUser != null)
		{
			Session session = currentUser.getSession();
			
			if (session != null)
			{
				UserInfoDto userInfo = (UserInfoDto) session
						.getAttribute(AccountConst.CURRENT_USER_INFO);
				
				return userInfo;
			}
		}
		
		return null;
	}
	
	@Override
	public void init(FilterConfig arg0) throws ServletException
	{
		
	}
	
}
