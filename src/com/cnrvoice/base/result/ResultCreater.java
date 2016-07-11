/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-4-13 下午12:14:18
 * @Description:
 * 
 */
package com.cnrvoice.base.result;

import com.cnrvoice.base.message.Message;
import com.cnrvoice.base.message.MessageCreater;

public class ResultCreater
{
	protected ResultCreater()
	{
		
	}
	
	/**
	 * 创建只包含空返回数据的结果
	 * 
	 * @param <D>
	 * 
	 * @param data
	 * @return
	 */
	public static Result<String> createResult()
	{
		Result<String> result = new Result<String>();
		result.setData("");
		result.setLevel(ResultLevelEnum.OnlyData);
		
		return result;
	}
	
	/**
	 * 创建只包含返回数据的结果
	 * 
	 * @param <D>
	 * 
	 * @param data
	 * @return
	 */
	public static <D> Result<D> createResult(D data)
	{
		Result<D> result = new Result<D>();
		result.setData(data);
		result.setLevel(ResultLevelEnum.OnlyData);
		
		return result;
	}
	
	/**
	 * 创建包含返回信息和返回数据的结果
	 * 
	 * @param <D>
	 * 
	 * @param code
	 * @param params
	 * @param data
	 * @return
	 */
	public static <D> Result<D> createResult(String code, Object[] params,
			D data)
	{
		Result<D> result = new Result<D>();
		Message msg = MessageCreater.createInfoMsg(code, params);
		result.setMessage(msg);
		
		result.setData(data);
		result.setLevel(ResultLevelEnum.DataMessage);
		
		return result;
	}
	
	/**
	 * 创建只包含返回信息的结果
	 * 
	 * @param code
	 * @param params
	 * @return
	 */
	public static Result<Object> createResult(String code, Object[] params)
	{
		Result<Object> result = new Result<Object>();
		Message msg = MessageCreater.createInfoMsg(code, params);
		result.setMessage(msg);
		result.setLevel(ResultLevelEnum.OnlyMessage);
		
		return result;
	}
	
	/**
	 * 创建只包含Sys(Exception)异常信息的结果
	 * 
	 * @param <T>
	 * 
	 * @param code
	 * @param params
	 * @param exception
	 * @return
	 */
	public static <T extends Exception> Result<Object> createExResult(T ex)
	{
		Result<Object> result = new Result<Object>();
		Message msg = MessageCreater.createExMsg(ex);
		result.setMessage(msg);
		
		result.setLevel(ResultLevelEnum.OnlyMessage);
		
		return result;
	}
}
