/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2013-1-15 上午10:28:30
 * @Description:
 * 
 */
package com.cnrvoice.base.memcache;

import net.rubyeye.xmemcached.utils.XMemcachedClientFactoryBean;

import org.springframework.beans.factory.DisposableBean;

public class XmemcClientFactoryBean extends XMemcachedClientFactoryBean
		implements DisposableBean
{
	public void destroy() throws Exception
	{
		super.shutdown();
	}
	
	public void setFailureMode(boolean failureMode)
	{
		super.setFailureMode(false);
	}
}
