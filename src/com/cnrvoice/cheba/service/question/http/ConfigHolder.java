/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-6-11 上午10:46:49
 * @Description:
 * 
 */
package com.cnrvoice.cheba.service.question.http;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import com.cnrvoice.base.property.ContextPropertiesHolder;

public class ConfigHolder
{
	private RestTemplate restTemplate;
	
	private static String baseUrl = "http://192.168.0.246/topic/publish?id=50";
	
	private static HttpHeaders requestJSONHeaders = new HttpHeaders();
	
	static{
		String url = (String) ContextPropertiesHolder
				.getPropertyItem("question.push.url");
		if(StringUtils.isNotBlank(url)){
			baseUrl = url;
		}
	}
	
	public ConfigHolder(RestTemplate restTemplate) {
		super();
		this.restTemplate = restTemplate;
	}

	public RestTemplate getRestTemplate() {
		if(restTemplate == null){
			this.restTemplate = new RestTemplate();
		}
		return restTemplate;
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public HttpHeaders getRequestJSONHeaders() {
		return requestJSONHeaders;
	}
}
