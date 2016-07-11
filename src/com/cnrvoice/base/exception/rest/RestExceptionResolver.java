/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-10-9 下午12:15:03
 * @Description:
 * 
 */
package com.cnrvoice.base.exception.rest;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.servlet.ModelAndView;

import com.cnrvoice.base.exception.UnCheckedException;
import com.cnrvoice.base.exception.ValidationException;
import com.cnrvoice.base.hibernate.multi.SessionFactoryContextHolder;
import com.cnrvoice.base.log.ExLogger;
import com.cnrvoice.base.result.rest.RestResult;
import com.cnrvoice.base.result.rest.RestResultCreater;

public class RestExceptionResolver extends
		org.springframework.web.servlet.handler.SimpleMappingExceptionResolver
{
	@Override
	protected ModelAndView doResolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
	{
		RestResult<Object> result;
		
		if (ex instanceof ValidationException)
		{
			result = RestResultCreater
					.createValidResult((ValidationException) ex);
		}
		else if (ex instanceof AuthenticationException)
		{
			result = RestResultCreater
					.createValidResult((AuthenticationException) ex);
		}
		else if (ex instanceof HttpRequestMethodNotSupportedException)
		{
			result = RestResultCreater
					.createExResult(new UnCheckedException("error.405"));
			response.setStatus(405);
		}
		else if (ex instanceof HttpMessageNotReadableException)
		{
			result = RestResultCreater
					.createExResult(new UnCheckedException("error.400"));
			response.setStatus(400);
		}
		else if (ex instanceof UncategorizedSQLException)
		{
			result = RestResultCreater
					.createExResult(new UnCheckedException("error.500"));
			response.setStatus(500);
		}
		else
		{
			result = RestResultCreater.createExResult(ex);
		}
		
		// 清除Dao中的Context
		SessionFactoryContextHolder.clearSessionFactoryKey();
		SessionFactoryContextHolder.clearSessionFactoryKey();
		
		// 异常日志记录
		ExLogger.log(result.getCode(), result.getContent(), ex);
		
		String isAjax = "";
		
		if (request.getHeader("accept").indexOf("application/json") > -1)
		{
			isAjax = "true";
		}
		
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
