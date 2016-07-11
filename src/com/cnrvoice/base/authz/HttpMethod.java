package com.cnrvoice.base.authz;

public enum HttpMethod
{
	
	DELETE("delete"), GET("read"), HEAD("read"), MKCOL("create"), OPTIONS(
			"read"), POST("create"), PUT("update"), TRACE("read");
	
	private String method;
	
	private HttpMethod(String method)
	{
		this.method = method;
	}
	
	private String getMethod()
	{
		return method;
	}
	
	public static String getOperation(String method)
	{
		HttpMethod httpMethod = null;
		for (HttpMethod m : HttpMethod.values())
		{
			if (m.name().equals(method))
			{
				httpMethod = m;
			}
		}
		return httpMethod.getMethod();
	}
	
}
