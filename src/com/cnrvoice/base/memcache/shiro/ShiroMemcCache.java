/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2013-1-15 上午10:27:05
 * @Description:
 * 
 */
package com.cnrvoice.base.memcache.shiro;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.KeyIterator;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

@SuppressWarnings("deprecation")
public class ShiroMemcCache implements Cache<String, Object>
{
	// memcached client
	private MemcachedClient memcachedClient;
	
	// 有效期（单位：秒）
	private Integer expire = 1800;
	
	// 等待应答超时时间（单位：毫秒）
	private Long timeout = (long) 10000;
	
	public Object get(String key) throws CacheException
	{
		try
		{
			return memcachedClient.get(key, timeout);
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
		return null;
	}
	
	@Override
	public Object put(String key, Object value) throws CacheException
	{
		try
		{
			return memcachedClient.set(key, expire, value);
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
		return false;
	}
	
	@Override
	public Object remove(String key) throws CacheException
	{
		try
		{
			return memcachedClient.delete(key);
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
		return false;
	}
	
	@Override
	public void clear() throws CacheException
	{
		try
		{
			Collection<InetSocketAddress> list = memcachedClient
					.getAvailableServers();
			if (null != list && !list.isEmpty())
			{
				for (InetSocketAddress inetSocketAddress : list)
				{
					KeyIterator it = memcachedClient
							.getKeyIterator(inetSocketAddress);
					while (it.hasNext())
					{
						String key = it.next();
						remove(key);
					}
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
	
	@Override
	public int size()
	{
		int size = 0;
		try
		{
			Collection<InetSocketAddress> list = memcachedClient
					.getAvailableServers();
			if (null != list && !list.isEmpty())
			{
				for (InetSocketAddress inetSocketAddress : list)
				{
					KeyIterator it = memcachedClient
							.getKeyIterator(inetSocketAddress);
					while (it.hasNext())
					{
						size++;
					}
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
		return size;
	}
	
	@Override
	public Set<String> keys()
	{
		Set<String> keySet = null;
		try
		{
			Collection<InetSocketAddress> list = memcachedClient
					.getAvailableServers();
			if (null != list && !list.isEmpty())
			{
				keySet = new HashSet<String>();
				for (InetSocketAddress inetSocketAddress : list)
				{
					KeyIterator it = memcachedClient
							.getKeyIterator(inetSocketAddress);
					while (it.hasNext())
					{
						String key = it.next();
						keySet.add(key);
					}
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
		return keySet;
	}
	
	@Override
	public Collection<Object> values()
	{
		Collection<Object> values = null;
		try
		{
			Collection<InetSocketAddress> list = memcachedClient
					.getAvailableServers();
			if (null != list && !list.isEmpty())
			{
				values = new ArrayList<Object>();
				for (InetSocketAddress inetSocketAddress : list)
				{
					KeyIterator it = memcachedClient
							.getKeyIterator(inetSocketAddress);
					while (it.hasNext())
					{
						String key = it.next();
						values.add(get(key));
					}
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
		return values;
	}
	
	public MemcachedClient getMemcachedClient()
	{
		return memcachedClient;
	}
	
	public void setMemcachedClient(MemcachedClient memcachedClient)
	{
		this.memcachedClient = memcachedClient;
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
