/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-10-12 下午12:13:50
 * @Description:
 * 
 */
package com.cnrvoice.base.authz;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.HttpMethodPermissionFilter;
import org.apache.shiro.web.util.WebUtils;
import org.codehaus.jackson.map.ObjectMapper;

import com.cnrvoice.base.exception.UnCheckedException;
import com.cnrvoice.base.result.rest.RestResult;
import com.cnrvoice.base.result.rest.RestResultCreater;

public class RestMultiMethodFilter extends HttpMethodPermissionFilter
{
	protected RestMultiMethodDao restmmDao;
	
	private final ObjectMapper objectMapper = new ObjectMapper();
	
	public RestMultiMethodDao getRestmmDao()
	{
		return restmmDao;
	}
	
	public void setRestmmDao(RestMultiMethodDao restmmDao)
	{
		this.restmmDao = restmmDao;
	}
	
	@Override
	public boolean isAccessAllowed(ServletRequest request,
			ServletResponse response, Object mappedValue) throws IOException
	{
		String[] perms = (String[]) mappedValue;
		String action = getHttpMethodAction(request);
		String[] resolvedPerms = buildPermissions(perms, action);
		
		Subject subject = getSubject(request, response);
		
		String urlUuid = null;
		boolean isPermitted = true;
		if (resolvedPerms != null && resolvedPerms.length > 0)
		{
			if (resolvedPerms.length == 1)
			{
				if (!subject.isPermitted(resolvedPerms[0]))
				{
					isPermitted = false;
				}
				else
				{
					urlUuid = perms[0];
				}
			}
			else
			{
				boolean isAllowed[] = subject.isPermitted(resolvedPerms);
				isPermitted = false;
				for (int i = 0; i < isAllowed.length; i++)
				{
					boolean b = isAllowed[i];
					if (b)
					{
						isPermitted = b;
						urlUuid = perms[i];
					}
				}
			}
		}
		boolean isAllow = true;
		if (isPermitted)
		{
			if (restmmDao != null)
			{
				Session session = subject.getSession();
				String appKey = (String) session.getAttribute("appKey");
				Set<String> authorizedIpList = restmmDao.getAuthorizedIpList(
						appKey, urlUuid);
				String ipAddr = HttpProxyIpUtil
						.getIpAddr((HttpServletRequest) request);
				if (authorizedIpList != null)
				{
					isAllow = false;
					for (String ip : authorizedIpList)
					{
						if (ipAddr.equals(ip))
						{
							return true;
						}
					}
				}
			}
		}
		return (isPermitted && isAllow);
	}
	
	@Override
	protected boolean onAccessDenied(ServletRequest request,
			ServletResponse response) throws IOException
	{
		HttpServletResponse rep = WebUtils.toHttp(response);
		rep.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		RestResult<Object> result = RestResultCreater.createExResult(new UnCheckedException("error.401.201"));
		objectMapper.writeValue(response.getWriter(), result);
		return false;
	}
	
}
