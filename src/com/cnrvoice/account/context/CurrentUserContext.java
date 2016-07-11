/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-4-9 下午7:09:27
 * @Description:
 * 
 */
package com.cnrvoice.account.context;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.cnrvoice.account.constant.AccountConst;
import com.cnrvoice.account.entity.Menu;
import com.cnrvoice.account.entity.User;
import com.cnrvoice.unified.webservice.dto.UserInfoDto;

public class CurrentUserContext
{
	public static void clearAllAttribute()
	{
		Subject currentUser = SecurityUtils.getSubject();
		
		if (currentUser != null)
		{
			Session session = currentUser.getSession();
			if (session != null)
			{
				session.removeAttribute(AccountConst.CURRENT_USER_INFO);
				session.removeAttribute(AccountConst.CURRENT_USER);
				session.removeAttribute(AccountConst.CURRENT_AUTH_INFO);
				session.removeAttribute(AccountConst.CURRENT_AUTH_MENU);
			}
		}
	}
	
	public static void setCurrentUserInfo(UserInfoDto userInfo)
	{
		Subject currentUser = SecurityUtils.getSubject();
		
		if (currentUser != null)
		{
			Session session = currentUser.getSession();
			if (session != null)
			{
				session.setAttribute(AccountConst.CURRENT_USER_INFO, userInfo);
			}
		}
	}
	
	public static UserInfoDto getCurrentUserInfo()
	{
		Subject currentUser = SecurityUtils.getSubject();
		UserInfoDto userInfo = null;
		
		if (currentUser != null)
		{
			Session session = currentUser.getSession();
			if (session != null)
			{
				
				userInfo = (UserInfoDto) session
						.getAttribute(AccountConst.CURRENT_USER_INFO);
			}
		}
		
		return userInfo;
	}
	
	public static void setCurrentUser(User user)
	{
		Subject currentUser = SecurityUtils.getSubject();
		
		if (currentUser != null)
		{
			Session session = currentUser.getSession();
			if (session != null)
			{
				session.setAttribute(AccountConst.CURRENT_USER, user);
			}
		}
	}
	
	public static User getCurrentUser()
	{
		Subject currentUser = SecurityUtils.getSubject();
		User user = null;
		
		if (currentUser != null)
		{
			Session session = currentUser.getSession();
			if (session != null)
			{
				
				user = (User) session.getAttribute(AccountConst.CURRENT_USER);
			}
		}
		
		return user;
	}
	
	public static void setCurrentAuthInfo(SimpleAuthorizationInfo authInfo)
	{
		Subject currentUser = SecurityUtils.getSubject();
		
		if (currentUser != null)
		{
			Session session = currentUser.getSession();
			if (session != null)
			{
				session.setAttribute(AccountConst.CURRENT_AUTH_INFO, authInfo);
			}
		}
	}
	
	public static SimpleAuthorizationInfo getCurrentAuthInfo()
	{
		Subject currentUser = SecurityUtils.getSubject();
		SimpleAuthorizationInfo authInfo = null;
		
		if (currentUser != null)
		{
			Session session = currentUser.getSession();
			if (session != null)
			{
				authInfo = (SimpleAuthorizationInfo) session
						.getAttribute(AccountConst.CURRENT_AUTH_INFO);
			}
		}
		return authInfo;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Menu> getCurrentAuthMenu()
	{
		Subject currentUser = SecurityUtils.getSubject();
		List<Menu> fenzhangMenus = null;
		
		if (currentUser != null)
		{
			Session session = currentUser.getSession();
			if (session != null)
			{
				fenzhangMenus = (List<Menu>) session
						.getAttribute(AccountConst.CURRENT_AUTH_MENU);
			}
		}
		return fenzhangMenus;
	}
	
	public static void setCurrentAuthMenu(List<Menu> fenzhangMenus)
	{
		Subject currentUser = SecurityUtils.getSubject();
		
		if (currentUser != null)
		{
			Session session = currentUser.getSession();
			if (session != null)
			{
				session.setAttribute(AccountConst.CURRENT_AUTH_MENU,
						fenzhangMenus);
			}
		}
	}
	
	public static <D> void setSessionAttr(String key, D date)
	{
		Subject currentUser = SecurityUtils.getSubject();
		
		if (currentUser != null)
		{
			Session session = currentUser.getSession();
			if (session != null)
			{
				session.setAttribute(key + getCurrentUser().getUuid(), date);
			}
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> T getSessionAttr(Class T, String key)
	{
		Subject currentUser = SecurityUtils.getSubject();
		T entity = null;
		if (currentUser != null)
		{
			Session session = currentUser.getSession();
			if (session != null)
			{
				entity = (T) session.getAttribute(key
						+ getCurrentUser().getUuid());
			}
		}
		return entity;
	}
	
	public static void clearSessionAttr(String key)
	{
		Subject currentUser = SecurityUtils.getSubject();
		
		if (currentUser != null)
		{
			Session session = currentUser.getSession();
			if (session != null)
			{
				session.removeAttribute(key + getCurrentUser().getUuid());
			}
		}
	}
}
