/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-3-29 下午6:06:46
 * @Description:
 * 
 */
package com.cnrvoice.base.log;

import java.lang.reflect.InvocationTargetException;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

import com.cnrvoice.base.message.Message;
import com.cnrvoice.base.message.MessageCreater;
import com.cnrvoice.base.util.BeanCustomUtils;
import com.cnrvoice.base.util.TypeUtil;

public class OpsLogAdvice
{
	private static String LOGGER_NAME = "OpsLogger";
	
	/**
	 * 方法执行前调用
	 * 
	 * @param method
	 * @param args
	 * @param target
	 * @throws Throwable
	 */
	public void before(JoinPoint point) throws Throwable
	{
		StringBuffer buffer = new StringBuffer();
		buffer.append("BeforeAspect--invoke method and args{");
		
		String clazzName = point.getTarget().getClass().getSimpleName();
		String methodName = point.getSignature().getName();
		
		buffer.append(clazzName + "." + methodName + "(");
		
		argsDescribe(point.getArgs(), buffer);
		
		buffer.append(")");
		
		buffer.append("}");
		
		LoggerHandler.info(LOGGER_NAME, buffer.toString());
	}
	
	/**
	 * 方法执行的前后调用
	 * 
	 * @param point
	 * @throws Throwable
	 */
	public void around(ProceedingJoinPoint point) throws Throwable
	{
		StringBuffer buffer = new StringBuffer();
		buffer.append("AroundAspect--invoke method and args{");
		
		String clazzName = point.getTarget().getClass().getSimpleName();
		String methodName = point.getSignature().getName();
		
		buffer.append(clazzName + "." + methodName + "(");
		
		argsDescribe(point.getArgs(), buffer);
		
		buffer.append(")");
		
		buffer.append("}");
		
		LoggerHandler.info(LOGGER_NAME, buffer.toString());
	}
	
	/**
	 * 方法执行后调用
	 * 
	 * @param method
	 * @param args
	 * @param target
	 * @throws Throwable
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	void after(JoinPoint point) throws IllegalAccessException,
			InvocationTargetException, Throwable
	{
		StringBuffer buffer = new StringBuffer();
		buffer.append("AfterAspect--invoke method and args{");
		
		String clazzName = point.getTarget().getClass().getSimpleName();
		String methodName = point.getSignature().getName();
		
		buffer.append(clazzName + "." + methodName + "(");
		
		argsDescribe(point.getArgs(), buffer);
		
		buffer.append(")");
		
		buffer.append("}");
		
		LoggerHandler.info(LOGGER_NAME, buffer.toString());
	}
	
	/**
	 * 对异常的处理
	 * 
	 * @param point
	 * @param ex
	 * @throws Throwable
	 */
	public void afterThrowing(JoinPoint point, Exception ex) throws Throwable
	{
		Message msg = MessageCreater.createExMsg(ex);
		
		// 异常日志记录
		ExLogger.log(msg.getCode(), msg.getContent(), ex);
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("ThrowingAspect--invoke method and args{");
		
		String clazzName = point.getTarget().getClass().getSimpleName();
		String methodName = point.getSignature().getName();
		
		buffer.append(clazzName + "." + methodName + "(");
		
		argsDescribe(point.getArgs(), buffer);
		
		buffer.append(")");
		
		buffer.append("}--exception{(");
		
		buffer.append(ex.getClass().getName() + ")");
		
		buffer.append(ex.getMessage() + "}");
		
		LoggerHandler.info(LOGGER_NAME, buffer.toString());
	}
	
	private void argsDescribe(Object[] args, StringBuffer buffer)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException, Exception
	{
		
		for (Object arg : args)
		{
			if (arg == null || "".equals(arg))
			{
				buffer.append(arg + ";");
			}
			else if (TypeUtil.isBaseDataType(arg.getClass()))
			{
				buffer.append(arg.toString() + ";");
			}
			else
			{
				buffer.append(BeanCustomUtils.describe(arg, null).toString()
						+ ";");
			}
		}
	}
	
}
