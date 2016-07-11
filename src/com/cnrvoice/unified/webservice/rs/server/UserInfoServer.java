package com.cnrvoice.unified.webservice.rs.server;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cnrvoice.account.entity.UserInfo;
import com.cnrvoice.account.web.manager.UserInfoManager;
import com.cnrvoice.unified.webservice.dto.UserInfoDataDto;
import com.cnrvoice.unified.webservice.dto.UserInfoDto;
import com.cnrvoice.unified.webservice.dto.UserResultDto;
import com.google.common.collect.Lists;

@Controller
@RequestMapping("/userinfo")
public class UserInfoServer
{
	private static Logger logger = Logger.getLogger(UserInfoServer.class);
	@Autowired
	private UserInfoManager um;
	
	public UserInfoManager getUm()
	{
		return um;
	}
	
	public void setUm(UserInfoManager um)
	{
		this.um = um;
	}
	
	/**
	 * 获取符合系统,用户类型条件的认证中心数据
	 * 
	 * @param subSystemKey
	 * @param type
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@RequestMapping(value = "/getuserinfoes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public UserInfoDataDto getUserinfoes(
			@RequestParam("subSystemKey") String subSystemKey,
			@RequestParam("type") String type) throws IllegalAccessException,
			InvocationTargetException
	{
		logger.debug("获取认证中心数据" + subSystemKey);
		List<UserInfo> list = um.getUserinfoes(subSystemKey, type);
		if (list.size() > 0)
		{
			for (UserInfo userInfo : list)
			{
				userInfo.setPassword(null);
			}
			UserInfoDataDto data = new UserInfoDataDto();
			data.setData(convertList(list));
			return data;
		}
		
		return null;
	}
	
	/**
	 * 根据ID获取userinfo信息
	 * 
	 * @param id
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@RequestMapping(value = "/byid", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public UserInfoDto byId(@RequestParam("id") String id)
			throws IllegalAccessException, InvocationTargetException
	{
		UserInfo userInfo = um.queryUserInfoByUuid(Integer.parseInt(id));
		if (userInfo == null || userInfo.getStatus().equals("20"))
		{
			return new UserInfoDto();
		}
		userInfo.setPassword(null);
		return convertEntity(userInfo);
	}
	
	/**
	 * 用户登录校验
	 * 
	 * @param loginName
	 * @param subSystemKey
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public UserInfoDto login(@RequestParam("loginName") String loginName,
			@RequestParam("subSystemKey") String subSystemKey)
			throws IllegalAccessException, InvocationTargetException
	{
		logger.debug("开始登录" + loginName);
		UserInfo userinfo = um.login(loginName, subSystemKey);
		UserInfoDto userInfoDto = new UserInfoDto();
		userInfoDto.setStatus("20");
		return userinfo == null ? userInfoDto : convertEntity(userinfo);
	}
	
	/**
	 * 用户登录校验
	 * 
	 * @param loginName
	 * @param subSystemKey
	 * @param password
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@RequestMapping(value = "/login2", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public UserResultDto login2(@RequestBody UserInfoDto userInfoDto)
			throws IllegalAccessException, InvocationTargetException
	{
		logger.debug("开始登录" + userInfoDto.getLoginName());
		Map<String, Object> map = um.login2(userInfoDto.getLoginName(),
				userInfoDto.getPassword(), userInfoDto.getSubSystemKey());
		UserResultDto userResult = new UserResultDto();
		userResult.setCode((String) map.get("code"));
		if (!userResult.getCode().equals("1"))
		{
			return userResult;
		}
		userResult
				.setUserInfoDto(convertEntity((UserInfo) map.get("userInfo")));
		return userResult;
	}
	
	/**
	 * 用户修改密码
	 * 
	 * @param loginName
	 * @param password
	 * @param newPassword
	 * @return
	 */
	@RequestMapping(value = "/passwordupdate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String passwordUpdate(@RequestBody UserInfoDto userInfoDto)
	{
		return um.passwordUpdate(userInfoDto.getLoginName(),
				userInfoDto.getPassword(), userInfoDto.getNewPassword());
	}
	
	private UserInfoDto convertEntity(UserInfo userInfo)
			throws IllegalAccessException, InvocationTargetException
	{
		UserInfoDto dto = new UserInfoDto();
		BeanUtils.copyProperties(dto, userInfo);
		return dto;
	}
	
	public List<UserInfoDto> convertList(List<UserInfo> list)
			throws IllegalAccessException, InvocationTargetException
	{
		List<UserInfoDto> destinationList = Lists.newArrayList();
		for (UserInfo userInfo : list)
		{
			UserInfoDto dto = new UserInfoDto();
			BeanUtils.copyProperties(dto, userInfo);
			destinationList.add(dto);
		}
		return destinationList;
	}
	
}
