package com.cnrvoice.account.web.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cnrvoice.account.entity.UserInfoStatus;
import com.cnrvoice.account.service.CommonUnifiedService;

@Service
public class CommonUnifiedManager {

	@Autowired
	private CommonUnifiedService commonUnifiedService;

	public CommonUnifiedService getCommonUnifiedService() {
		return commonUnifiedService;
	}

	public void setCommonUnifiedService(CommonUnifiedService commonUnifiedService) {
		this.commonUnifiedService = commonUnifiedService;
	}
	public List<UserInfoStatus> getAllUserInfoStatus(){
		return commonUnifiedService.getAllUserInfoStatus();
	}
	
	public UserInfoStatus getUserInfoStatusValue(String key){	
		return commonUnifiedService.getUserInfoStatusValue(key);
	}
	
}
