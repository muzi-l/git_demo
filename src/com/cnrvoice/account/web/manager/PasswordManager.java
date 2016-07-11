package com.cnrvoice.account.web.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cnrvoice.unified.webservice.dto.UserInfoDto;
import com.cnrvoice.unified.webservice.rs.client.UserInfoClient;

@Service
public class PasswordManager {
	@Autowired
	private UserInfoClient userInfoClient;


	
	public UserInfoClient getUserInfoClient() {
		return userInfoClient;
	}

	public void setUserInfoClient(UserInfoClient userInfoClient) {
		this.userInfoClient = userInfoClient;
	}



	public String updatePasswords(String loginName,String originalPassword,String newPassword){
		UserInfoDto userInfoDto = new UserInfoDto();
		userInfoDto.setLoginName(loginName);
		userInfoDto.setPassword(originalPassword);
		userInfoDto.setNewPassword(newPassword);
		String ss = userInfoClient.passwordUpdate(userInfoDto);
		System.out.println(ss);
		return ss;
	}
	
	
}
