package com.cnrvoice.cheba.service.question.http;


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
