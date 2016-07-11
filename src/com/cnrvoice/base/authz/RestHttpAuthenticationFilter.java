package com.cnrvoice.base.authz;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.codehaus.jackson.map.ObjectMapper;

import com.cnrvoice.base.exception.UnCheckedException;
import com.cnrvoice.base.result.rest.RestResult;
import com.cnrvoice.base.result.rest.RestResultCreater;

public class RestHttpAuthenticationFilter extends FormAuthenticationFilter
{
	private final ObjectMapper objectMapper = new ObjectMapper();
	
	@Override
	protected boolean onAccessDenied(ServletRequest request,
			ServletResponse response) throws IOException
	{
		HttpServletResponse rep = WebUtils.toHttp(response);
		rep.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		RestResult<Object> result = RestResultCreater.createExResult(new UnCheckedException("error.401.101"));
		objectMapper.writeValue(response.getWriter(), result);
		return false;
	}
}
