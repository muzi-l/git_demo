/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-4-20 上午10:32:27
 * @Description:
 * 
 */
package com.cnrvoice.base.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ContextClassLoaderLocal;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

class BeanCustomUtilsBean extends BeanUtilsBean
{
	private Log log = LogFactory.getLog(BeanUtils.class);
	
	private static final ContextClassLoaderLocal BEANS_BY_CLASSLOADER = new ContextClassLoaderLocal()
	{
		// Creates the default instance used when the context classloader is
		// unavailable
		protected Object initialValue()
		{
			return new BeanCustomUtilsBean();
		}
	};
	
	/**
	 * Gets the instance which provides the functionality for {@link BeanUtils}.
	 * This is a pseudo-singleton - an single instance is provided per (thread)
	 * context classloader. This mechanism provides isolation for web apps
	 * deployed in the same container.
	 * 
	 * @return The (pseudo-singleton) BeanUtils bean instance
	 */
	public static BeanCustomUtilsBean getChildInstance()
	{
		return (BeanCustomUtilsBean) BEANS_BY_CLASSLOADER.get();
	}
	
	/**
	 * 只拷贝非空属性字段
	 * 
	 * @param dest
	 * @param orig
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings("rawtypes")
	public void copyNotNullProperties(Object dest, Object orig)
			throws IllegalAccessException, InvocationTargetException
	{
		
		// Validate existence of the specified beans
		if (dest == null)
		{
			throw new IllegalArgumentException("No destination bean specified");
		}
		if (orig == null)
		{
			throw new IllegalArgumentException("No origin bean specified");
		}
		if (log.isDebugEnabled())
		{
			log.debug("BeanUtils.copyProperties(" + dest + ", " + orig + ")");
		}
		
		// Copy the properties, converting as necessary
		if (orig instanceof DynaBean)
		{
			DynaProperty[] origDescriptors = ((DynaBean) orig).getDynaClass()
					.getDynaProperties();
			for (int i = 0; i < origDescriptors.length; i++)
			{
				String name = origDescriptors[i].getName();
				// Need to check isReadable() for WrapDynaBean
				// (see Jira issue# BEANUTILS-61)
				if (getPropertyUtils().isReadable(orig, name)
						&& getPropertyUtils().isWriteable(dest, name))
				{
					Object value = ((DynaBean) orig).get(name);
					
					// 当value不为空时，
					// 才执行copyProperty
					if (value != null && !"".equals(value))
					{
						copyProperty(dest, name, value);
					}
				}
			}
		}
		else if (orig instanceof Map)
		{
			Iterator entries = ((Map) orig).entrySet().iterator();
			while (entries.hasNext())
			{
				Map.Entry entry = (Map.Entry) entries.next();
				String name = (String) entry.getKey();
				if (getPropertyUtils().isWriteable(dest, name))
				{
					Object value = entry.getValue();
					
					// 当value不为空时，
					// 才执行copyProperty
					if (value != null && !"".equals(value))
					{
						copyProperty(dest, name, value);
					}
				}
			}
		}
		else
		/* if (orig is a standard JavaBean) */{
			PropertyDescriptor[] origDescriptors = getPropertyUtils()
					.getPropertyDescriptors(orig);
			for (int i = 0; i < origDescriptors.length; i++)
			{
				String name = origDescriptors[i].getName();
				if ("class".equals(name))
				{
					continue; // No point in trying to set an object's class
				}
				if (getPropertyUtils().isReadable(orig, name)
						&& getPropertyUtils().isWriteable(dest, name))
				{
					try
					{
						Object value = getPropertyUtils().getSimpleProperty(
								orig, name);
						
						// 当value不为空时，
						// 才执行copyProperty
						if (value != null && !"".equals(value))
						{
							copyProperty(dest, name, value);
						}
					}
					catch (NoSuchMethodException e)
					{
						// Should not happen
					}
				}
			}
		}
	}
	
	/**
	 * 只拷贝非空属性字段，不拷贝excludeFields里的字段
	 * 
	 * @param dest
	 * @param orig
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings("rawtypes")
	public void copyNotNullProperties(Object dest, Object orig,
			String[] excludeFields) throws IllegalAccessException,
			InvocationTargetException
	{
		
		// Validate existence of the specified beans
		if (dest == null)
		{
			throw new IllegalArgumentException("No destination bean specified");
		}
		if (orig == null)
		{
			throw new IllegalArgumentException("No origin bean specified");
		}
		if (log.isDebugEnabled())
		{
			log.debug("BeanUtils.copyProperties(" + dest + ", " + orig + ")");
		}
		
		// Copy the properties, converting as necessary
		if (orig instanceof DynaBean)
		{
			DynaProperty[] origDescriptors = ((DynaBean) orig).getDynaClass()
					.getDynaProperties();
			for (int i = 0; i < origDescriptors.length; i++)
			{
				String name = origDescriptors[i].getName();
				// Need to check isReadable() for WrapDynaBean
				// (see Jira issue# BEANUTILS-61)
				if (getPropertyUtils().isReadable(orig, name)
						&& getPropertyUtils().isWriteable(dest, name))
				{
					Object value = ((DynaBean) orig).get(name);
					
					// 当excludeFields为空
					// 或者excludeFields不为空但excludeFields不包含该字段时，
					// 才执行copyProperty
					if (ArrayUtils.isEmpty(excludeFields)
							|| (ArrayUtils.isNotEmpty(excludeFields) && !ArrayUtils
									.contains(excludeFields, name)))
					{
						// 当value不为空时，
						// 才执行copyProperty
						if (value != null && !"".equals(value))
						{
							copyProperty(dest, name, value);
						}
					}
					
				}
			}
		}
		else if (orig instanceof Map)
		{
			Iterator entries = ((Map) orig).entrySet().iterator();
			while (entries.hasNext())
			{
				Map.Entry entry = (Map.Entry) entries.next();
				String name = (String) entry.getKey();
				if (getPropertyUtils().isWriteable(dest, name))
				{
					Object value = entry.getValue();
					
					// 当excludeFields为空
					// 或者excludeFields不为空但excludeFields不包含该字段时，
					// 才执行copyProperty
					if (ArrayUtils.isEmpty(excludeFields)
							|| (ArrayUtils.isNotEmpty(excludeFields) && !ArrayUtils
									.contains(excludeFields, name)))
					{
						// 当value不为空时，
						// 才执行copyProperty
						if (value != null && !"".equals(value))
						{
							copyProperty(dest, name, value);
						}
					}
					
				}
			}
		}
		else
		/* if (orig is a standard JavaBean) */{
			PropertyDescriptor[] origDescriptors = getPropertyUtils()
					.getPropertyDescriptors(orig);
			for (int i = 0; i < origDescriptors.length; i++)
			{
				String name = origDescriptors[i].getName();
				if ("class".equals(name))
				{
					continue; // No point in trying to set an object's class
				}
				if (getPropertyUtils().isReadable(orig, name)
						&& getPropertyUtils().isWriteable(dest, name))
				{
					try
					{
						Object value = getPropertyUtils().getSimpleProperty(
								orig, name);
						
						// 当excludeFields为空
						// 或者excludeFields不为空但excludeFields不包含该字段时，
						// 才执行copyProperty
						if (ArrayUtils.isEmpty(excludeFields)
								|| (ArrayUtils.isNotEmpty(excludeFields) && !ArrayUtils
										.contains(excludeFields, name)))
						{
							// 当value不为空时，
							// 才执行copyProperty
							if (value != null && !"".equals(value))
							{
								copyProperty(dest, name, value);
							}
						}
						
					}
					catch (NoSuchMethodException e)
					{
						// Should not happen
					}
				}
			}
		}
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
	@SuppressWarnings("rawtypes")
	public void copyProperties(Object dest, Object orig, String[] excludeFields)
			throws IllegalAccessException, InvocationTargetException
	{
		
		// Validate existence of the specified beans
		if (dest == null)
		{
			throw new IllegalArgumentException("No destination bean specified");
		}
		if (orig == null)
		{
			throw new IllegalArgumentException("No origin bean specified");
		}
		if (log.isDebugEnabled())
		{
			log.debug("BeanUtils.copyProperties(" + dest + ", " + orig + ")");
		}
		
		// Copy the properties, converting as necessary
		if (orig instanceof DynaBean)
		{
			DynaProperty[] origDescriptors = ((DynaBean) orig).getDynaClass()
					.getDynaProperties();
			for (int i = 0; i < origDescriptors.length; i++)
			{
				String name = origDescriptors[i].getName();
				// Need to check isReadable() for WrapDynaBean
				// (see Jira issue# BEANUTILS-61)
				if (getPropertyUtils().isReadable(orig, name)
						&& getPropertyUtils().isWriteable(dest, name))
				{
					Object value = ((DynaBean) orig).get(name);
					
					// 当excludeFields为空
					// 或者excludeFields不为空但excludeFields不包含该字段时，
					// 才执行copyProperty
					if (ArrayUtils.isEmpty(excludeFields)
							|| (ArrayUtils.isNotEmpty(excludeFields) && !ArrayUtils
									.contains(excludeFields, name)))
					{
						copyProperty(dest, name, value);
					}
				}
			}
		}
		else if (orig instanceof Map)
		{
			Iterator entries = ((Map) orig).entrySet().iterator();
			while (entries.hasNext())
			{
				Map.Entry entry = (Map.Entry) entries.next();
				String name = (String) entry.getKey();
				if (getPropertyUtils().isWriteable(dest, name))
				{
					Object value = entry.getValue();
					
					// 当excludeFields为空
					// 或者excludeFields不为空但excludeFields不包含该字段时，
					// 才执行copyProperty
					if (ArrayUtils.isEmpty(excludeFields)
							|| (ArrayUtils.isNotEmpty(excludeFields) && !ArrayUtils
									.contains(excludeFields, name)))
					{
						copyProperty(dest, name, value);
					}
				}
			}
		}
		else
		/* if (orig is a standard JavaBean) */{
			PropertyDescriptor[] origDescriptors = getPropertyUtils()
					.getPropertyDescriptors(orig);
			for (int i = 0; i < origDescriptors.length; i++)
			{
				String name = origDescriptors[i].getName();
				if ("class".equals(name))
				{
					continue; // No point in trying to set an object's class
				}
				if (getPropertyUtils().isReadable(orig, name)
						&& getPropertyUtils().isWriteable(dest, name))
				{
					try
					{
						Object value = getPropertyUtils().getSimpleProperty(
								orig, name);
						
						// 当excludeFields为空
						// 或者excludeFields不为空但excludeFields不包含该字段时，
						// 才执行copyProperty
						if (ArrayUtils.isEmpty(excludeFields)
								|| (ArrayUtils.isNotEmpty(excludeFields) && !ArrayUtils
										.contains(excludeFields, name)))
						{
							copyProperty(dest, name, value);
						}
					}
					catch (NoSuchMethodException e)
					{
						// Should not happen
					}
				}
			}
		}
	}
	
	/**
	 * 只描述非空属性字段
	 * 
	 * @param bean
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	public Map describeNotNull(Object bean) throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException
	{
		
		if (bean == null)
		{
			// return (Collections.EMPTY_MAP);
			return (new java.util.HashMap());
		}
		
		if (log.isDebugEnabled())
		{
			log.debug("Describing bean: " + bean.getClass().getName());
		}
		
		Map description = new HashMap();
		if (bean instanceof DynaBean)
		{
			DynaProperty[] descriptors = ((DynaBean) bean).getDynaClass()
					.getDynaProperties();
			for (int i = 0; i < descriptors.length; i++)
			{
				String name = descriptors[i].getName();
				
				// 当属性可读时才执行description.put
				if (getPropertyUtils().isReadable(bean, name))
				{
					try
					{
						Object value = getProperty(bean, name);
						
						// 当value不为空时，
						// 才执行copyProperty
						if (value != null && !"".equals(value))
						{
							description.put(name, value.toString());
						}
					}
					catch (NoSuchMethodException e)
					{
						// Should not happen
					}
				}
			}
		}
		else
		{
			PropertyDescriptor[] descriptors = getPropertyUtils()
					.getPropertyDescriptors(bean);
			Class clazz = bean.getClass();
			for (int i = 0; i < descriptors.length; i++)
			{
				String name = descriptors[i].getName();
				if ("class".equals(name))
				{
					continue; // No point in trying to set an object's class
				}
				// 当属性可读时才执行description.put
				if (getPropertyUtils().isReadable(bean, name))
				{
					try
					{
						Object value = getProperty(bean, name);
						
						// 当value不为空时，
						// 才执行copyProperty
						if (value != null && !"".equals(value))
						{
							description.put(name, value.toString());
						}
					}
					catch (NoSuchMethodException e)
					{
						// Should not happen
					}
				}
			}
		}
		return (description);
		
	}
	
	/**
	 * 只描述非空属性字段，不描述excludeFields里的字段
	 * 
	 * @param bean
	 * @param excludeFields
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	public Map describeNotNull(Object bean, String[] excludeFields)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException
	{
		if (bean == null)
		{
			// return (Collections.EMPTY_MAP);
			return (new java.util.HashMap());
		}
		
		if (log.isDebugEnabled())
		{
			log.debug("Describing bean: " + bean.getClass().getName());
		}
		
		Map description = new HashMap();
		if (bean instanceof DynaBean)
		{
			DynaProperty[] descriptors = ((DynaBean) bean).getDynaClass()
					.getDynaProperties();
			for (int i = 0; i < descriptors.length; i++)
			{
				String name = descriptors[i].getName();
				
				// 当属性可读时才执行description.put
				if (getPropertyUtils().isReadable(bean, name))
				{
					// 当excludeFields为空
					// 或者excludeFields不为空但excludeFields不包含该字段时，
					// 才执行description.put
					if (ArrayUtils.isEmpty(excludeFields)
							|| (ArrayUtils.isNotEmpty(excludeFields) && !ArrayUtils
									.contains(excludeFields, name)))
					{
						try
						{
							Object value = getProperty(bean, name);
							
							// 当value不为空时，
							// 才执行copyProperty
							if (value != null && !"".equals(value))
							{
								description.put(name, value.toString());
							}
						}
						catch (NoSuchMethodException e)
						{
							// Should not happen
						}
					}
				}
			}
		}
		else
		{
			PropertyDescriptor[] descriptors = getPropertyUtils()
					.getPropertyDescriptors(bean);
			Class clazz = bean.getClass();
			for (int i = 0; i < descriptors.length; i++)
			{
				String name = descriptors[i].getName();
				if ("class".equals(name))
				{
					continue; // No point in trying to set an object's class
				}
				// 当属性可读时才执行description.put
				if (getPropertyUtils().isReadable(bean, name))
				{
					// 当excludeFields为空
					// 或者excludeFields不为空但excludeFields不包含该字段时，
					// 才执行description.put
					if (ArrayUtils.isEmpty(excludeFields)
							|| (ArrayUtils.isNotEmpty(excludeFields) && !ArrayUtils
									.contains(excludeFields, name)))
					{
						try
						{
							Object value = getProperty(bean, name);
							
							// 当value不为空时，
							// 才执行copyProperty
							if (value != null && !"".equals(value))
							{
								description.put(name, value.toString());
							}
						}
						catch (NoSuchMethodException e)
						{
							// Should not happen
						}
					}
				}
			}
		}
		return (description);
	}
	
	/**
	 * 只描述非空属性字段，不描述excludeFields里的字段，并将字段名转换成自定义名称
	 * 
	 * @param bean
	 * @param excludeFields
	 * @param fieldNameMap
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	@SuppressWarnings({ "rawtypes", "unused" })
	public Map describeNotNull(Object bean, String[] excludeFields,
			Map fieldNameMap) throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException
	{
		if (bean == null)
		{
			// return (Collections.EMPTY_MAP);
			return (new java.util.HashMap());
		}
		
		if (log.isDebugEnabled())
		{
			log.debug("Describing bean: " + bean.getClass().getName());
		}
		
		Map<String, String> description = new HashMap<String, String>();
		if (bean instanceof DynaBean)
		{
			DynaProperty[] descriptors = ((DynaBean) bean).getDynaClass()
					.getDynaProperties();
			for (int i = 0; i < descriptors.length; i++)
			{
				String name = descriptors[i].getName();
				// 当属性可读时才执行description.put
				if (getPropertyUtils().isReadable(bean, name))
				{
					// 当excludeFields为空
					// 或者excludeFields不为空但excludeFields不包含该字段时，
					// 才执行description.put
					if (ArrayUtils.isEmpty(excludeFields)
							|| (ArrayUtils.isNotEmpty(excludeFields) && !ArrayUtils
									.contains(excludeFields, name)))
					{
						try
						{
							Object value = getProperty(bean, name);
							
							// 当value不为空时，
							// 才执行copyProperty
							if (value != null && !"".equals(value))
							{
								if (fieldNameMap.get(name) != null
										&& !"".equals(fieldNameMap.get(name)
												.toString()))
									description.put(fieldNameMap.get(name)
											.toString(), value.toString());
								else
									description.put(name, value.toString());
							}
						}
						catch (NoSuchMethodException e)
						{
							// Should not happen
						}
					}
				}
			}
		}
		else
		{
			PropertyDescriptor[] descriptors = getPropertyUtils()
					.getPropertyDescriptors(bean);
			Class clazz = bean.getClass();
			for (int i = 0; i < descriptors.length; i++)
			{
				String name = descriptors[i].getName();
				if ("class".equals(name))
				{
					continue; // No point in trying to set an object's class
				}
				// 当属性可读时才执行description.put
				if (getPropertyUtils().isReadable(bean, name))
				{
					// 当excludeFields为空
					// 或者excludeFields不为空但excludeFields不包含该字段时，
					// 才执行description.put
					if (ArrayUtils.isEmpty(excludeFields)
							|| (ArrayUtils.isNotEmpty(excludeFields) && !ArrayUtils
									.contains(excludeFields, name)))
					{
						// 当属性可读时才执行description.put
						if (getPropertyUtils().isReadable(bean, name))
						{
							try
							{
								Object value = getProperty(bean, name);
								
								// 当value不为空时，
								// 才执行copyProperty
								if (value != null && !"".equals(value))
								{
									if (fieldNameMap.get(name) != null
											&& !"".equals(fieldNameMap
													.get(name).toString()))
										description.put(fieldNameMap.get(name)
												.toString(), value.toString());
									else
										description.put(name, value.toString());
								}
							}
							catch (NoSuchMethodException e)
							{
								// Should not happen
							}
						}
					}
				}
			}
		}
		return (description);
	}
	
	/**
	 * 描述属性字段，不描述excludeFields里的字段
	 * 
	 * @param bean
	 * @param excludeFields
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	public Map describe(Object bean, String[] excludeFields)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException
	{
		if (bean == null)
		{
			// return (Collections.EMPTY_MAP);
			return (new java.util.HashMap());
		}
		
		if (log.isDebugEnabled())
		{
			log.debug("Describing bean: " + bean.getClass().getName());
		}
		
		Map description = new HashMap();
		if (bean instanceof DynaBean)
		{
			DynaProperty[] descriptors = ((DynaBean) bean).getDynaClass()
					.getDynaProperties();
			for (int i = 0; i < descriptors.length; i++)
			{
				String name = descriptors[i].getName();
				
				// 当属性可读时才执行description.put
				if (getPropertyUtils().isReadable(bean, name))
				{
					// 当excludeFields为空
					// 或者excludeFields不为空但excludeFields不包含该字段时，
					// 才执行description.put
					if (ArrayUtils.isEmpty(excludeFields)
							|| (ArrayUtils.isNotEmpty(excludeFields) && !ArrayUtils
									.contains(excludeFields, name)))
					{
						try
						{
							Object value = getProperty(bean, name);
							
							description.put(name, value.toString());
						}
						catch (NoSuchMethodException e)
						{
							// Should not happen
						}
					}
				}
			}
		}
		else
		{
			PropertyDescriptor[] descriptors = getPropertyUtils()
					.getPropertyDescriptors(bean);
			Class clazz = bean.getClass();
			for (int i = 0; i < descriptors.length; i++)
			{
				String name = descriptors[i].getName();
				if ("class".equals(name))
				{
					continue; // No point in trying to set an object's class
				}
				// 当属性可读时才执行description.put
				if (getPropertyUtils().isReadable(bean, name))
				{
					// 当excludeFields为空
					// 或者excludeFields不为空但excludeFields不包含该字段时，
					// 才执行description.put
					if (ArrayUtils.isEmpty(excludeFields)
							|| (ArrayUtils.isNotEmpty(excludeFields) && !ArrayUtils
									.contains(excludeFields, name)))
					{
						try
						{
							Object value = getProperty(bean, name);
							
							description.put(name, value);
						}
						catch (NoSuchMethodException e)
						{
							// Should not happen
						}
					}
				}
			}
		}
		return (description);
	}
}
