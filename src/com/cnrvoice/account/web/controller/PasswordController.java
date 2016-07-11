package com.cnrvoice.account.web.controller;

import java.lang.reflect.InvocationTargetException;

import org.apache.shiro.crypto.hash.Sha512Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cnrvoice.account.web.manager.PasswordManager;
import com.cnrvoice.base.property.ContextPropertiesHolder;
import com.cnrvoice.base.result.JEasyResultCreater;
import com.cnrvoice.base.result.Result;
@Controller
public class PasswordController {
	
	@Autowired
	private PasswordManager passwordManager;
	
	public PasswordManager getPasswordManager() {
		return passwordManager;
	}
	public void setPasswordManager(PasswordManager passwordManager) {
		this.passwordManager = passwordManager;
	}
	// 显示修改用户密码页面
	@RequestMapping(value = "/manager/password/list")
	public String updatePasswordView() {
		return "account/update_password";
	}
	/**
	 * 修改密码处理
	 * 
	 * @param user
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@RequestMapping(value = "/manager/updatepassword/submit")
	@ResponseBody
	public Result<?> updatePassword(String loginName,String originalPassword,String newPassword)throws IllegalAccessException, InvocationTargetException {
		String hashedPassword = new Sha512Hash(originalPassword, ContextPropertiesHolder.getPropertyItem("salt"), 1024).toBase64();
		originalPassword = hashedPassword;
		String hashedNewPassword = new Sha512Hash(newPassword, ContextPropertiesHolder.getPropertyItem("salt"), 1024).toBase64();
		newPassword = hashedNewPassword;
		String isbool = passwordManager.updatePasswords(loginName, originalPassword, newPassword);	
		return JEasyResultCreater.createResult(new StringBuffer(isbool));
	}
	
	
}
