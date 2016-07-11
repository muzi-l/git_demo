package com.cnrvoice.base.authz;

import javax.servlet.http.HttpServletRequest;

public class HttpProxyIpUtil
{
	
	public static String getIpAddr(HttpServletRequest request)
	{
		String ip = request.getHeader("X-REAL-IP");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
		{
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
		{
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
		{
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
}
