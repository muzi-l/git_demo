/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-4-24 下午1:03:41
 * @Description:
 * 
 */
package com.cnrvoice.base.property;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ContextPropertiesHolder
{
	private static Map<String, Object> ctxPropertiesMap;
	
	static Map<String, Object> getCtxPropertiesMap()
	{
		return ctxPropertiesMap;
	}
	
	/**
	 * 设置当前属性文件
	 * 
	 * @param props
	 */
	static void setCtxPropertiesMap(Properties props)
	{
		ctxPropertiesMap = new HashMap<String, Object>();
		for (Object key : props.keySet())
		{
			String keyStr = key.toString();
			String value = props.getProperty(keyStr);
			ctxPropertiesMap.put(keyStr, value);
		}
	}
	
	/**
	 * 获取当前属性文件条目
	 * 
	 * @param key
	 * @return
	 */
	public static Object getPropertyItem(String key)
	{
		return ctxPropertiesMap.get(key);
	}
}
