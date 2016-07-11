/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-3-21 下午5:15:31
 * @Description:
 * 
 */
package com.cnrvoice.base.exception;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.web.servlet.ModelAndView;

import com.cnrvoice.base.hibernate.multi.SessionFactoryContextHolder;
import com.cnrvoice.base.log.ExLogger;
import com.cnrvoice.base.result.ResultCreater;
import com.cnrvoice.base.result.Result;

public class SimpleMappingExceptionResolver extends
		org.springframework.web.servlet.handler.SimpleMappingExceptionResolver
{
	@Override
	protected ModelAndView doResolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
	{
		Result<Object> result = ResultCreater.createExResult(ex);
		
		// 清除Dao中的Context
		SessionFactoryContextHolder.clearSessionFactoryKey();
		SessionFactoryContextHolder.clearSessionFactoryKey();
		
		// 异常日志记录
		ExLogger.log(result.getMessage().getCode(), result.getMessage()
				.getContent(), ex);
		
		String isAjax = request.getHeader("x-requested-with");
		// if (!(request.getHeader("accept").indexOf("application/json") > -1 ||
		// request
		// .getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1))
		// {
		//
		// }
		
		// 同步请求，JSP格式返回
		if (StringUtils.isEmpty(isAjax))
		{
			return super.doResolveException(request, response, handler, ex);
		}// 异步请求，JSON格式返回
		else
		{
			ObjectMapper objectMapper = new ObjectMapper();
			
			try
			{
				objectMapper.writeValue(response.getWriter(), result);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			
			return new ModelAndView();
		}
	}
}
