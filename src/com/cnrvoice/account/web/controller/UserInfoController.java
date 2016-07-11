/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: Jirui Zhao
 * @date: 2012-3-8 ����3:08:27
 * @Description:
 * 
 */
package com.cnrvoice.account.web.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cnrvoice.account.entity.UserInfo;
import com.cnrvoice.account.entity.UserInfoDetail;
import com.cnrvoice.account.entity.query.UserInfoQuery;
import com.cnrvoice.account.web.manager.UserInfoManager;
import com.cnrvoice.base.paging.JEasyPageOrder;
import com.cnrvoice.base.property.ContextPropertiesHolder;
import com.cnrvoice.base.result.JEasyResultCreater;
import com.cnrvoice.base.result.Result;

@Controller
@RequestMapping(value = "/manager/*")
public class UserInfoController
{
	@Autowired
	private UserInfoManager um;
	private static Logger logger = Logger.getLogger(UserInfoController.class);
	
	public UserInfoManager getUm()
	{
		return um;
	}
	
	public void setUm(UserInfoManager um)
	{
		this.um = um;
	}
	
	/**
	 * 查询按钮提交
	 * 
	 * @param userinfo
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@RequestMapping("userinfo/search")
	@ResponseBody
	public Result<?> queryUserInfo(UserInfoQuery userinfo,
			JEasyPageOrder pageOrder) throws IllegalAccessException,
			InvocationTargetException
	{
		logger.debug("执行查询");
		userinfo.setPageOrder(pageOrder);
		List<UserInfo> list = um.queryUserInfo(userinfo);
		
		return JEasyResultCreater.createResult(list);
	}
	
	@RequestMapping("userinfo/pagination")
	@ResponseBody
	public Result<?> pagination(UserInfoQuery userinfo, JEasyPageOrder pageOrder)
			throws IllegalAccessException, InvocationTargetException
	{
		userinfo.setPageOrder(pageOrder);
		List<UserInfo> list = um.queryUserInfo(userinfo);
		
		return JEasyResultCreater.createResult(list);
	}
	
	/**
	 * 跳转主页面
	 * 
	 * @return String
	 */
	@RequestMapping("userinfo/list")
	public String list()
	{
		return "manager/userinfo_check";
		
	}
	
	/**
	 * 跳转添加页面
	 * 
	 * @return String
	 */
	@RequestMapping("userinfoadd/view")
	public String userInfoAdd()
	{
		return "manager/userinfo_add";
	}
	
	/**
	 * 添加提交
	 * 
	 * @param userInfo
	 * @param userInfoDetail
	 * @return Result<StringBuffer>
	 */
	@RequestMapping("userinfoadd/submit")
	@ResponseBody
	public Result<?> userinfoadd(UserInfo userInfo, String subsystemUuid)
	{
		Result<StringBuffer> result = null;
		if (!um.isrepeat(userInfo.getLoginName().toLowerCase()))
		{
			userInfo.setLoginName(userInfo.getLoginName().toLowerCase());
			userInfo.setUserName(userInfo.getUserName().toLowerCase());
			
			// 采用SHA-512算法对密码进行加密
			String hashedPassword = new Sha512Hash(userInfo.getPassword(),
					ContextPropertiesHolder.getPropertyItem("salt"), 1024)
					.toBase64();
			userInfo.setPassword(hashedPassword);
			
			um.addUserInfo(userInfo);
			for (String str : subsystemUuid.split(","))
			{
				UserInfoDetail ud = new UserInfoDetail();
				ud.setSubsystemUuid(str);
				ud.setUserinfoId(userInfo.getId());
				um.addUserInfoDetail(ud);
			}
			result = JEasyResultCreater.createResult(new StringBuffer("添加成功"));
		}
		return result;
	}
	
	/**
	 * 跳转更新页面
	 * 
	 * @param uuid
	 * @return ModelAndView
	 */
	@RequestMapping("userinfoedit/view")
	public ModelAndView userInfoUpdEdit(Integer id)
	{
		ModelAndView mav = new ModelAndView();
		UserInfo ui = um.queryUserInfoByUuid(id);
		if (ui != null && !id.equals(10000))
		{
			List<UserInfoDetail> udt = um.queryUserInfoDetail(ui.getId());
			mav.setViewName("manager/userinfo_update");
			mav.addObject("userinfo", ui);
			String subsystemUuid = "";
			for (int i = 0; i < udt.size(); i++)
			{
				UserInfoDetail uu = udt.get(i);
				subsystemUuid += uu.getSubsystemUuid();
				if (i < udt.size() - 1)
				{
					subsystemUuid += ",";
				}
			}
			mav.addObject("subsystemUuid", subsystemUuid);
			
		}
		else
		{
			mav.setViewName("manager/userinfo_check");
		}
		return mav;
	}
	
	/**
	 * 获取已拥有的可登录系统
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("userinfoedit/have")
	@ResponseBody
	public Result<?> userInfoHave(Integer id)
	{
		UserInfo ui = um.queryUserInfoByUuid(id);
		List<UserInfoDetail> udt = um.queryUserInfoDetail(ui.getId());
		String subsystemUuid = "";
		for (int i = 0; i < udt.size(); i++)
		{
			UserInfoDetail uu = udt.get(i);
			subsystemUuid += uu.getSubsystemUuid();
			if (i < udt.size() - 1)
			{
				subsystemUuid += ",";
			}
		}
		return JEasyResultCreater.createResult(subsystemUuid);
	}
	
	/**
	 * 更新提交
	 * 
	 * @param userinfo
	 * @param userInfoDetail
	 * @return Result<StringBuffer>
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@RequestMapping("userinfoedit/submit")
	@ResponseBody
	public Result<?> userinfoedit(UserInfo userinfo, String subsystemUuid)
			throws IllegalAccessException, InvocationTargetException
	{
		
		// 采用SHA-512算法对密码进行加密
		// String hashedPassword = new Sha512Hash(userinfo.getPassword(),
		// ContextPropertiesHolder.getPropertyItem("salt"), 1024).toBase64();
		// ui.setPassword(hashedPassword);
		um.updateUserInfo(userinfo);
		// 先删除旧的详情表,再添加新的详情
		List<UserInfoDetail> udt = um.queryUserInfoDetail(userinfo.getId());
		for (UserInfoDetail aa : udt)
		{
			um.deleteUserInfoDetail(aa);
		}
		;
		for (String str : subsystemUuid.split(","))
		{
			UserInfoDetail uf = new UserInfoDetail();
			uf.setUserinfoId(userinfo.getId());
			uf.setSubsystemUuid(str);
			um.addUserInfoDetail(uf);
		}
		
		return JEasyResultCreater.createResult(new StringBuffer("更新成功"));
	}
	
	/**
	 * 获取全部subSystem
	 * 
	 * @return
	 */
	@RequestMapping("subsystem/getall")
	@ResponseBody
	public Result<?> getAllSubSystem()
	{
		return JEasyResultCreater.createResult(um.getAllSubSystem());
	}
	
	/**
	 * 获取所有的userinfotype
	 * 
	 * @return
	 */
	@RequestMapping("userinfotype/getall")
	@ResponseBody
	public Result<?> getAllUserInfoType()
	{
		return JEasyResultCreater.createResult(um.getAllUserInfoType());
	}
	
	/**
	 * 获取查询输入框autocomplete的值
	 * 
	 * @param name
	 * @return List<UserInfo>
	 */
	@RequestMapping("userinfo/query")
	@ResponseBody
	public List<UserInfo> query(UserInfo userinfo)
	{
		return um.getUserinfo().queryUserinfo(userinfo);
	}
	
	@RequestMapping("userinfo/resetpassword")
	@ResponseBody
	public Result<?> resetpassword(UserInfo userInfo)
	{
		String newPassWord = "abcd1234";
		String hashedPassword = new Sha512Hash(newPassWord,
				ContextPropertiesHolder.getPropertyItem("salt"), 1024)
				.toBase64();
		userInfo.setPassword(hashedPassword);
		um.resetpassword(userInfo);
		return JEasyResultCreater.createResult(new StringBuffer("重置成功,新密码为："
				+ newPassWord));
	}
}
