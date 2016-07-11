/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-4-24 上午11:54:34
 * @Description:
 * 
 */
package com.cnrvoice.base.property;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.util.PropertyPlaceholderHelper;

public class PropertyPlaceholderConfigurer extends
		org.springframework.beans.factory.config.PropertyPlaceholderConfigurer
{
	private Properties rootProperty;
	
	private Resource rootLocation;
	
	private String[] childLocations;
	
	public Resource getRootLocation()
	{
		return rootLocation;
	}
	
	/**
	 * 设置根属性文件
	 * 
	 * @param rootLocation
	 * @throws IOException
	 */
	public void setRootLocation(Resource rootLocation) throws IOException
	{
		this.rootLocation = rootLocation;
		rootProperty = new Properties();
		InputStream in = rootLocation.getInputStream();
		try
		{
			rootProperty.load(rootLocation.getInputStream());
		}
		finally
		{
			in.close();
		}
	}
	
	public String[] getChildLocations()
	{
		return childLocations;
	}
	
	/**
	 * 设置子属性文件
	 * 
	 * @param childLocations
	 */
	public void setChildLocations(String[] childLocations)
	{
		this.childLocations = childLocations;
		Resource[] resources = new Resource[childLocations.length + 1];
		for (int i = 0; i < childLocations.length; i++)
		{
			resources[i] = getResource(childLocations[i]);
		}
		
		resources[childLocations.length] = rootLocation;
		
		setLocations(resources);
	}
	
	/**
	 * 加载资源文件
	 * 
	 * @param location
	 * @return
	 */
	private Resource getResource(String location)
	{
		location = parseStringValue(location, rootProperty);
		DefaultResourceLoader loader = new DefaultResourceLoader();
		return loader.getResource(location);
	}
	
	/**
	 * 把子属性文件的路径占位符替换掉
	 * 
	 * @param strVal
	 * @param props
	 * @return
	 */
	private String parseStringValue(String strVal, Properties props)
	{
		PropertyPlaceholderHelper helper = new PropertyPlaceholderHelper(
				placeholderPrefix, placeholderSuffix, valueSeparator,
				ignoreUnresolvablePlaceholders);
		return helper.replacePlaceholders(strVal, props);
	}
	
	/**
	 * 处理属性文件，并把加载的资源文件放到缓存中，对外开方
	 */
	@Override
	protected void processProperties(
			ConfigurableListableBeanFactory beanFactory, Properties props)
			throws BeansException
	{
		super.processProperties(beanFactory, props);
		
		// load properties to ctxPropertiesMap
		ContextPropertiesHolder.setCtxPropertiesMap(props);
	}
}
