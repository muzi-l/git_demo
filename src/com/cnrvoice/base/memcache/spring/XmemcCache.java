package com.cnrvoice.base.memcache.spring;

import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.KeyIterator;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

@SuppressWarnings("deprecation")
public class XmemcCache implements Cache
{
	// memcached client
	private MemcachedClient memcachedClient;
	
	// cache名称
	private String name;
	
	// 有效期（单位：秒）
	private Integer expire = 3600;
	
	// 等待应答超时时间（单位：毫秒）
	private Long timeout = (long) 10000;
	
	@Override
	public Object getNativeCache()
	{
		return this.memcachedClient;
	}
	
	/**
	 * 通过key获取
	 */
	public ValueWrapper get(Object key)
	{
		SimpleValueWrapper simpleValueWrapper = null;
		try
		{
			simpleValueWrapper = new SimpleValueWrapper(memcachedClient.get(
					objectToString(key), this.timeout));
		}
		catch (TimeoutException e)
		{
			e.printStackTrace();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		catch (MemcachedException e)
		{
			e.printStackTrace();
		}
		return simpleValueWrapper;
	}
	
	/**
	 * 保存键值对
	 */
	@Override
	public void put(Object key, Object value)
	{
		try
		{
			memcachedClient.set(objectToString(key), this.expire, value);
		}
		catch (TimeoutException e)
		{
			e.printStackTrace();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		catch (MemcachedException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 通过key删除
	 */
	@Override
	public void evict(Object key)
	{
		try
		{
			memcachedClient.delete(objectToString(key));
		}
		catch (TimeoutException e)
		{
			e.printStackTrace();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		catch (MemcachedException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 清除所有数据
	 */
	@Override
	public void clear()
	{
		try
		{
			Collection<InetSocketAddress> list = memcachedClient
					.getAvailableServers();
			for (InetSocketAddress inetSocketAddress : list)
			{
				KeyIterator it = memcachedClient
						.getKeyIterator(inetSocketAddress);
				while (it.hasNext())
				{
					String key = it.next();
					evict(key);
				}
			}
		}
		catch (MemcachedException e)
		{
			e.printStackTrace();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		catch (TimeoutException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Object to String
	 * 
	 * @param object
	 * @return
	 */
	private static String objectToString(Object object)
	{
		if (object == null)
		{
			return null;
		}
		else if (object instanceof String)
		{
			return (String) object;
		}
		else
		{
			return object.toString();
		}
	}
	
	public MemcachedClient getMemcachedClient()
	{
		return memcachedClient;
	}
	
	public void setMemcachedClient(MemcachedClient memcachedClient)
	{
		this.memcachedClient = memcachedClient;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public Integer getExpire()
	{
		return expire;
	}
	
	public void setExpire(Integer expire)
	{
		this.expire = expire;
	}
	
	public Long getTimeout()
	{
		return timeout;
	}
	
	public void setTimeout(Long timeout)
	{
		this.timeout = timeout;
	}
	
}
