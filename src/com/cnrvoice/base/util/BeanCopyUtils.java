/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-4-20 上午10:31:15
 * @Description:
 * 
 */
package com.cnrvoice.base.util;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.converters.ConverterFacade;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.beanutils.converters.DoubleConverter;
import org.apache.commons.beanutils.converters.IntegerConverter;

public class BeanCopyUtils extends BeanUtils
{
	
	/**
	 * 只拷贝非空属性字段
	 * 
	 * @param dest
	 * @param orig
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static void copyNotNullProperties(Object dest, Object orig)
			throws IllegalAccessException, InvocationTargetException
	{
		BeanCustomUtilsBean.getChildInstance().copyNotNullProperties(dest, orig);
	}
	
	/**
	 * 只拷贝非空属性字段，不拷贝excludeFields里的字段
	 * 
	 * @param dest
	 * @param orig
	 * @param excludeFields
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static void copyNotNullProperties(Object dest, Object orig,
			String[] excludeFields) throws IllegalAccessException,
			InvocationTargetException
	{
		BeanCustomUtilsBean.getChildInstance().copyNotNullProperties(dest, orig,
				excludeFields);
	}
	
	/**
	 * 拷贝属性字段(可以为空及null)，不拷贝excludeFields里的字段
	 * 
	 * @param dest
	 * @param orig
	 * @param excludeFields
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static void copyProperties(Object dest, Object orig,
			String[] excludeFields) throws IllegalAccessException,
			InvocationTargetException
	{
		BeanCustomUtilsBean.getChildInstance().getConvertUtils().register(new ConverterFacade(new DoubleConverter(null)),Double.class);
		BeanCustomUtilsBean.getChildInstance().getConvertUtils().register(new ConverterFacade(new IntegerConverter(null)),Integer.class);
		BeanCustomUtilsBean.getChildInstance().getConvertUtils().register(new ConverterFacade(new DateConverter(null)), Date.class);
		BeanCustomUtilsBean.getChildInstance().copyProperties(dest, orig,
				excludeFields);
		BeanCustomUtilsBean.getChildInstance().getConvertUtils().register(new ConverterFacade(new DoubleConverter(0)),Double.class);
		BeanCustomUtilsBean.getChildInstance().getConvertUtils().register(new ConverterFacade(new IntegerConverter(0)),Integer.class);
		BeanCustomUtilsBean.getChildInstance().getConvertUtils().register(new ConverterFacade(new DateConverter()), Date.class);
	}
}
