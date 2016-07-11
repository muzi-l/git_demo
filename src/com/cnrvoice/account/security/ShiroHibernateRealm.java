/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-3-9 ����5:08:36
 * @Description:
 * 
 */
package com.cnrvoice.account.security;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.cnrvoice.account.constant.AccountConst;
import com.cnrvoice.account.context.CurrentUserContext;
import com.cnrvoice.account.entity.Permi;
import com.cnrvoice.account.entity.Role;
import com.cnrvoice.account.entity.User;
import com.cnrvoice.account.service.AccountService;
import com.cnrvoice.account.service.ManagerUserService;
import com.cnrvoice.base.property.ContextPropertiesHolder;
import com.cnrvoice.base.util.BeanCopyUtils;
import com.cnrvoice.unified.webservice.dto.UserInfoDto;
import com.cnrvoice.unified.webservice.rs.client.UserInfoClient;

public class ShiroHibernateRealm extends AuthorizingRealm
{
	@Autowired
	private ManagerUserService managerUserService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private UserInfoClient userInfoClient;
	
	/**
	 * 认证信息
	 */
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException
	{
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		String userName = token.getUsername();
		if (userName != null && !"".equals(userName))
		{
			UserInfoDto userInfo = login(token.getUsername());
			if (userInfo != null
					&& StringUtils.isNotBlank(userInfo.getLoginName()))
			{
				return new SimpleAuthenticationInfo(userInfo.getLoginName(),
						userInfo.getPassword(),
						ByteSource.Util.bytes(ContextPropertiesHolder
								.getPropertyItem("salt")), getName());
			}
			else
			{
				throw new AuthenticationException("biz.login.114");
			}
		}
		return null;
	}
	
	private UserInfoDto login(String loginName)
	{
		UserInfoDto UserInfoDto = getUserInfoClient().login(
				loginName,
				ContextPropertiesHolder.getPropertyItem("system.project.name")
						.toString());
		UserInfoDto userInfo = new UserInfoDto();
		try
		{
			BeanCopyUtils.copyNotNullProperties(userInfo, UserInfoDto);
			if(userInfo.getUserName()!=null){
				Subject currentUser = SecurityUtils.getSubject();
				Session session = currentUser.getSession();
				session.setAttribute("userName", userInfo.getUserName());
			}
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		return userInfo;
	}
	
	private void fetchAuthInfo(String username)
	{
		User user = accountService.getUserAccountByLoginName(username);
		
		if (user != null && user.getRoles() != null)
		{
			SimpleAuthorizationInfo authInfo = new SimpleAuthorizationInfo();
			
			for (Role role : user.getRoles())
			{
				Set<String> permis = new HashSet<String>();
				for (Permi permi : role.getPermis())
				{
					permis.add(permi.getKey());
				}
				authInfo.addStringPermissions(permis);
			}
			
			if (CollectionUtils.isEmpty(user.getRoles()))
			{
				throw new AuthenticationException("account.login.no_role");
			}
			
			if (CollectionUtils.isEmpty(authInfo.getStringPermissions()))
			{
				throw new AuthenticationException("account.login.no_permi");
			}
			
			CurrentUserContext.setCurrentAuthInfo(authInfo);
			
			CurrentUserContext.setCurrentUser(user);
		}
	}
	
	/**
	 * 授权信息
	 */
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals)
	{
		SimpleAuthorizationInfo authInfo = CurrentUserContext
				.getCurrentAuthInfo();
		
		return authInfo;
	}
	
	public AccountService getAccountService()
	{
		return accountService;
	}
	
	public void setAccountService(AccountService accountService)
	{
		this.accountService = accountService;
	}
	
	public ManagerUserService getManagerUserService()
	{
		return managerUserService;
	}
	
	public void setManagerUserService(ManagerUserService managerUserService)
	{
		this.managerUserService = managerUserService;
	}
	
	
	public UserInfoClient getUserInfoClient() {
		return userInfoClient;
	}

	public void setUserInfoClient(UserInfoClient userInfoClient) {
		this.userInfoClient = userInfoClient;
	}

	@Override
	protected void assertCredentialsMatch(AuthenticationToken token,
			AuthenticationInfo info) throws AuthenticationException
	{
		// 比对
		try
		{
			super.assertCredentialsMatch(token, info);
		}
		catch (Exception e)
		{
			// 密码错误
			// throw new UnCheckedException("biz.login.111");
			throw new AuthenticationException("biz.login.111");
		}
		UsernamePasswordToken t = (UsernamePasswordToken) token;
		// 用户名
		String userName = t.getUsername();
		
		if (userName != null && !"".equals(userName))
		{
			UserInfoDto userInfo = login(userName);
			if (userInfo != null)
			{
				if (!ContextPropertiesHolder
						.getPropertyItem("system.project.name").toString()
						.equals(userInfo.getSystem()))
				{
					// logger.info(userName + "非本系统用户");
					throw new AuthenticationException("biz.login.113");
					// throw new UnCheckedException();
				}
				else
				{
					if (AccountConst.USER_UNACTIVATED.equals(userInfo
							.getStatus()))
					{
						// logger.info(userName + "该账号在认证中心中未被激活");
						// throw new UnCheckedException("biz.login.110");
						throw new AuthenticationException("biz.login.110");
					}
					else
					{
						User user = managerUserService
								.getUserByLoginName(userInfo.getLoginName());
						if (null == user)
						{
							// logger.info(userName + "该用户未被管理员授权");
							throw new AuthenticationException("biz.login.115");
						}
						else
						{
							if (AccountConst.USER_UNACTIVATED.equals(user
									.getStatus()))
							{
								// logger.info(userName + "该用户未被激活");
								// throw new
								// UnCheckedException("biz.login.112");
								throw new AuthenticationException(
										"biz.login.112");
							}
							else
							{
								CurrentUserContext.setCurrentUserInfo(userInfo);
								fetchAuthInfo(userName);
							}
						}
					}
				}
			}
		}
	}
}
