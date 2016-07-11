/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-10-16 下午3:18:43
 * @Description:
 * 
 */
package com.cnrvoice.base.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public class TypeUtil
{
	/**
	 * 判断一个类是否为基本数据类型。
	 * 
	 * @param clazz
	 *            要判断的类。
	 * @return true 表示为基本数据类型。
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isBaseDataType(Class clazz) throws Exception
	{
		return (clazz.equals(String.class) || clazz.equals(Integer.class)
				|| clazz.equals(Byte.class) || clazz.equals(Long.class)
				|| clazz.equals(Double.class) || clazz.equals(Float.class)
				|| clazz.equals(Character.class) || clazz.equals(Short.class)
				|| clazz.equals(BigDecimal.class)
				|| clazz.equals(BigInteger.class)
				|| clazz.equals(Boolean.class) || clazz.equals(Date.class) || clazz
					.isPrimitive());
	}
}
