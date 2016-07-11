/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-3-15 下午5:56:39
 * @Description:
 * 
 */
package com.cnrvoice.account.web.manager;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cnrvoice.account.context.CurrentUserContext;
import com.cnrvoice.account.entity.Menu;
import com.cnrvoice.account.service.MenuService;

@Service
public class LoginManager
{
	@Autowired
	private MenuService menuService;
	
	public void login(String loginName, String password)
	{
		UsernamePasswordToken token = new UsernamePasswordToken(loginName,
				password);
		Subject currentUser = SecurityUtils.getSubject();
		currentUser.login(token);
	}
	
	public void logout()
	{
		Subject currentUser = SecurityUtils.getSubject();
		
		currentUser.logout();
		
		CurrentUserContext.clearAllAttribute();
	}
	
	public List<Menu> getAllFzMenu()
	{
		return menuService.getAll();
	}
	
	public List<Menu> getMenuByUser()
	{
//		List<Menu> fenzhangMenus = CurrentUserContext.getCurrentAuthMenu();
//		if (fenzhangMenus == null)
//		{
			
			List<Menu> fenzhangMenus = menuService.getMenuByUser(CurrentUserContext
					.getCurrentAuthInfo().getStringPermissions());
			CurrentUserContext.setCurrentAuthMenu(fenzhangMenus);
//		}
		return fenzhangMenus;
	}
	
	public MenuService getMenuService()
	{
		return menuService;
	}
	
	public void setMenuService(MenuService menuService)
	{
		this.menuService = menuService;
	}
}
