/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-6-11 上午11:13:00
 * @Description:
 * 
 */
package com.cnrvoice.unified.webservice.rs.client.util;


public class BaseResouce
{
	private ResourceInvoker resourceInvoker;

	public ResourceInvoker getResourceInvoker() {
		return resourceInvoker;
	}

	public void setResourceInvoker(ResourceInvoker resourceInvoker) {
		this.resourceInvoker = resourceInvoker;
	}

	public BaseResouce(ResourceInvoker resourceInvoker) {
		super();
		this.resourceInvoker = resourceInvoker;
	}

	public BaseResouce() {
		super();
	}
	
}
