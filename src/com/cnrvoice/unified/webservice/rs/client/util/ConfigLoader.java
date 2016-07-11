/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-6-11 上午10:45:41
 * @Description:
 * 
 */
package com.cnrvoice.unified.webservice.rs.client.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class ConfigLoader
{
	private static URL filePath = Thread.currentThread()
			.getContextClassLoader().getResource("");
	
	private static String fileName = "apiconfig.properties";
	
	private static Properties props = new Properties();
	
	static
	{
		try
		{
			props.load(new FileInputStream(filePath.getPath() + File.separator
					+ fileName));
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public ConfigLoader()
	{
	}
	
	public static String getValue(String key)
	{
		return props.getProperty(key);
	}
	
	public static void updateProperties(String key, String value)
	{
		props.setProperty(key, value);
	}
}
