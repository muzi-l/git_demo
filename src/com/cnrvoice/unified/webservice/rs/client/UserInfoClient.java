/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-6-11 上午10:54:50
 * @Description:
 * 
 */
package com.cnrvoice.unified.webservice.rs.client;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cnrvoice.unified.webservice.dto.UserInfoDataDto;
import com.cnrvoice.unified.webservice.dto.UserInfoDto;
import com.cnrvoice.unified.webservice.dto.UserResultDto;
import com.cnrvoice.unified.webservice.rs.client.util.BaseResouce;
import com.cnrvoice.unified.webservice.rs.client.util.ResourceInvoker;

public class UserInfoClient extends BaseResouce
{
	public UserInfoClient()
	{
		super();
	}
	public UserInfoClient(ResourceInvoker resourceInvoker) {
		super(resourceInvoker);
	}
	public List<UserInfoDto> loadAll(String subSystemKey, String type)
			throws IllegalAccessException, InvocationTargetException
	{
		Map<String, String> map = new HashMap<String, String>(2);
		map.put("subSystemKey",subSystemKey);
		map.put("type",type);
		
		UserInfoDataDto s =  getResourceInvoker().getEntityFromGet("/rest/userinfo/getuserinfoes?subSystemKey={subSystemKey}&type={type}",getResourceInvoker().createJSONEntity(),UserInfoDataDto.class,map);
		return  s.getData();
	}
	
	public UserInfoDto login(String loginName, String subSystemKey)
	{	
		Map<String, String> map = new HashMap<String, String>(2);
		map.put("loginName",loginName);
		map.put("subSystemKey",subSystemKey);
		return getResourceInvoker().getEntityFromGet("/rest/userinfo/login?loginName={loginName}&subSystemKey={subSystemKey}",getResourceInvoker().createJSONEntity(),UserInfoDto.class,map);
		
	}
	
	public UserResultDto login2(UserInfoDto userInfoDto)
	{
		return getResourceInvoker().getEntityFromPost("/rest/userinfo/login2", getResourceInvoker().createJSONEntity(userInfoDto), UserResultDto.class);
	}
	
	public String passwordUpdate(UserInfoDto userInfoDto)
	{
		return getResourceInvoker().getEntityFromPost("/rest/userinfo/passwordupdate", getResourceInvoker().createJSONEntity(userInfoDto), String.class);
	}
	
	public UserInfoDto getUserinfoById(Integer id)
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("id",id.toString());
		return getResourceInvoker().getEntityFromGet("/rest/userinfo/byid?id={id}",getResourceInvoker().createJSONEntity(),UserInfoDto.class,map);
	}
}
