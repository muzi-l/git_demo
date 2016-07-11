package com.cnrvoice.base.memcache.spring;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.rubyeye.xmemcached.MemcachedClient;

import org.springframework.cache.Cache;
import org.springframework.cache.support.AbstractCacheManager;

public class XmemcCacheManager extends AbstractCacheManager
{
	// memcached client
	private MemcachedClient memcachedClient;
	
	// XMemcached cache
	private Map<String, XmemcCache> xcaches;
	
	protected Collection<? extends Cache> loadCaches()
	{
		Collection<Cache> caches = new LinkedHashSet<Cache>(this.xcaches.size());
		Set<Entry<String, XmemcCache>> set = xcaches.entrySet();
		for (Entry<String, XmemcCache> entry : set)
		{
			XmemcCache xmemcCache = entry.getValue();
			xmemcCache.setMemcachedClient(this.memcachedClient);
			xmemcCache.setName(entry.getKey());
			caches.add(xmemcCache);
		}
		return caches;
	}
	
	public MemcachedClient getMemcachedClient()
	{
		return memcachedClient;
	}
	
	public void setMemcachedClient(MemcachedClient memcachedClient)
	{
		this.memcachedClient = memcachedClient;
	}
	
	public Map<String, XmemcCache> getXcaches()
	{
		return xcaches;
	}
	
	public void setXcaches(Map<String, XmemcCache> xcaches)
	{
		this.xcaches = xcaches;
	}
	
}
