/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2013-1-15 上午10:27:35
 * @Description:
 * 
 */
package com.cnrvoice.base.memcache.shiro;

import java.util.Map;

import net.rubyeye.xmemcached.MemcachedClient;

import org.apache.shiro.cache.AbstractCacheManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.beans.factory.DisposableBean;

public class ShiroMemcCacheManager extends AbstractCacheManager implements
		DisposableBean
{
	// memcached client
	private MemcachedClient memcachedClient;
	private Map<String, ShiroMemcCache> shiroMemcCaches;
	
	@SuppressWarnings("rawtypes")
	@Override
	protected Cache createCache(String name) throws CacheException
	{
		ShiroMemcCache cache = shiroMemcCaches.get(name);
		cache.setMemcachedClient(memcachedClient);
		return cache;
	}
	
	@Override
	public void destroy() throws Exception
	{
		super.destroy();
		memcachedClient.shutdown();
	}
	
	public MemcachedClient getMemcachedClient()
	{
		return memcachedClient;
	}
	
	public void setMemcachedClient(MemcachedClient memcachedClient)
	{
		this.memcachedClient = memcachedClient;
	}
	
	public Map<String, ShiroMemcCache> getShiroMemcCaches()
	{
		return shiroMemcCaches;
	}
	
	public void setShiroMemcCaches(Map<String, ShiroMemcCache> shiroMemcCaches)
	{
		this.shiroMemcCaches = shiroMemcCaches;
	}
	
}
