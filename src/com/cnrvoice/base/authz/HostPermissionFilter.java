package com.cnrvoice.base.authz;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.codehaus.jackson.map.ObjectMapper;

import com.cnrvoice.base.exception.UnCheckedException;
import com.cnrvoice.base.result.rest.RestResult;
import com.cnrvoice.base.result.rest.RestResultCreater;

public class HostPermissionFilter extends AuthorizationFilter
{
	
	protected HostPermissionDao hostpDao;
	
	private final ObjectMapper objectMapper = new ObjectMapper();
	
	public HostPermissionDao getHostpDao()
	{
		return hostpDao;
	}
	
	public void setHostpDao(HostPermissionDao hostpDao)
	{
		this.hostpDao = hostpDao;
	}
	
	protected boolean isAccessAllowed(ServletRequest request,
			ServletResponse response, Object mappedValue) throws Exception
	{
		boolean isAllow = true;
		String[] perms = (String[]) mappedValue;
		String urlUuid = null;
		if(perms != null && perms.length > 0){
			urlUuid = perms[0];
		}
		
		String appKey = request.getParameter("appKey");
		String ipAddr = HttpProxyIpUtil.getIpAddr((HttpServletRequest) request);
		String method = WebUtils.toHttp(request).getMethod();
		if (hostpDao != null)
		{
			isAllow = hostpDao.isAuthIp(urlUuid, method, appKey, ipAddr);
		}
		return isAllow;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request,
			ServletResponse response) throws IOException
	{
		
		HttpServletResponse rep = WebUtils.toHttp(response);
		rep.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		RestResult<Object> result = RestResultCreater.createExResult(new UnCheckedException("error.401.301"));
		objectMapper.writeValue(response.getWriter(), result);
		return false;
	}
	
}
