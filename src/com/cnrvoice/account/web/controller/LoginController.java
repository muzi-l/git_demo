/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-3-15 下午5:41:45
 * @Description:
 * 
 */
package com.cnrvoice.account.web.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cnrvoice.account.context.CurrentUserContext;
import com.cnrvoice.account.web.manager.LoginManager;
import com.cnrvoice.base.exception.UnCheckedException;
import com.cnrvoice.base.property.ContextPropertiesHolder;
import com.cnrvoice.base.result.JEasyResultCreater;
import com.cnrvoice.base.result.Result;

@Controller
public class LoginController {
	@Autowired
	private LoginManager loginManager;

	@RequestMapping(value = "/signin")
	@ResponseBody
	public Result<?> signin(String loginName, String password) {
		try {
			loginManager.login(loginName, password);
			Subject currentUser = SecurityUtils.getSubject();
			Session session = currentUser.getSession();
			session.setAttribute("loginName", loginName);
			session.setAttribute("systemName", (String) ContextPropertiesHolder
					.getPropertyItem("systemName"));
			session.setAttribute("systemversion", (String) ContextPropertiesHolder
					.getPropertyItem("systemversion"));
		} catch (AuthenticationException e) {
			throw new UnCheckedException(e.getMessage());
		}
		return JEasyResultCreater.createResult(new StringBuffer("success"));
	}

	@RequestMapping(value = "/loginsuccess")
	public String loginsuccess() {
		if (CurrentUserContext.getCurrentUser() != null) {
			return "login_success";
		}
		return "error";
	}

	@RequestMapping(value = "/error")
	public String error() {
		return "errorpage";
	}

	@RequestMapping(value = "/menuData")
	@ResponseBody
	public Result<?> menuData() {
		Result<?> result = JEasyResultCreater.createResult(loginManager
				.getMenuByUser());
		return result;
	}

	@RequestMapping(value = "/signout")
	@ResponseBody
	public Result<?> signout() {
		loginManager.logout();
		return JEasyResultCreater.createResult("");
	}

	public LoginManager getLoginManager() {
		return loginManager;
	}

	public void setLoginManager(LoginManager loginManager) {
		this.loginManager = loginManager;
	}
}
