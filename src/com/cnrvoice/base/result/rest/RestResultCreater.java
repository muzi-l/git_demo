/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-10-9 下午12:12:44
 * @Description:
 * 
 */
package com.cnrvoice.base.result.rest;

import org.apache.shiro.authc.AuthenticationException;

import com.cnrvoice.base.exception.ValidationException;
import com.cnrvoice.base.message.Message;
import com.cnrvoice.base.message.MessageCreater;

public class RestResultCreater
{
	protected RestResultCreater()
	{
		
	}
	
	/**
	 * 创建只包含空返回数据的结果
	 * 
	 * @return
	 */
	public static RestResult<String> createResult()
	{
		RestResult<String> result = new RestResult<String>();
		result.setData("");
		result.setCode(RestResultCodeConst.SUCCESS);
		
		return result;
	}
	
	/**
	 * 创建只包含返回数据的结果
	 * 
	 * @param data
	 * @return
	 */
	public static <D> RestResult<D> createResult(D data)
	{
		RestResult<D> result = new RestResult<D>();
		result.setData(data);
		result.setCode(RestResultCodeConst.SUCCESS);
		
		return result;
	}
	
	/**
	 * 创建包含返回信息和返回数据的结果
	 * 
	 * @param code
	 * @param params
	 * @param data
	 * @return
	 */
	public static <D> RestResult<D> createResult(String code, Object[] params,
			D data)
	{
		RestResult<D> result = new RestResult<D>();
		Message msg = MessageCreater.createInfoMsg(code, params);
		result.setContent(msg.getContent());
		
		result.setData(data);
		result.setCode(RestResultCodeConst.SUCCESS);
		
		return result;
	}
	
	/**
	 * 创建包含返回验证失败信息的结果
	 * 
	 * @param validEx
	 * @return
	 */
	public static RestResult<Object> createValidResult(
			ValidationException validEx)
	{
		RestResult<Object> result = new RestResult<Object>();
		Message msg = MessageCreater.createValidMsg(validEx);
		result.setContent(msg.getContent());
		result.setCode(RestResultCodeConst.VALID_FAIL);
		
		return result;
	}
	
	/**
	 * 创建包含返回验证失败信息的结果
	 * 
	 * @param validEx
	 * @return
	 */
	public static RestResult<Object> createValidResult(
			AuthenticationException authcEx)
	{
		RestResult<Object> result = new RestResult<Object>();
		Message msg = MessageCreater.createValidMsg(authcEx);
		result.setContent(msg.getContent());
		result.setCode(RestResultCodeConst.VALID_FAIL);
		
		return result;
	}
	
	/**
	 * 创建只包含返回异常信息的结果
	 * 
	 * @param ex
	 * @return
	 */
	public static <T extends Exception> RestResult<Object> createExResult(T ex)
	{
		RestResult<Object> result = new RestResult<Object>();
		Message msg = MessageCreater.createExMsg(ex);
		result.setContent(msg.getContent());
		
		result.setCode(msg.getCode());
		
		return result;
	}
}
