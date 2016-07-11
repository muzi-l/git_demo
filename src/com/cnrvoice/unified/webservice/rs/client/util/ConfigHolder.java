/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-6-11 上午10:46:49
 * @Description:
 * 
 */
package com.cnrvoice.unified.webservice.rs.client.util;

import java.util.Collections;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

public class ConfigHolder
{
	private RestTemplate restTemplate;
	
	private String baseUrl;
	
	private HttpHeaders requestJSONHeaders;
	
	public ConfigHolder(String baseUrl) {
		super();
		this.restTemplate = new RestTemplate();
		Assert.notNull(baseUrl, "'baseUrl' must not be null");
		this.baseUrl = baseUrl;
		this.requestJSONHeaders = new HttpHeaders();
		this.requestJSONHeaders.setAccept(Collections.singletonList(new MediaType("application","json")));
		this.requestJSONHeaders.setContentType(new MediaType("application","json"));
	}
	
	public ConfigHolder(RestTemplate restTemplate, String baseUrl) {
		super();
		this.restTemplate = restTemplate;
		Assert.notNull(baseUrl, "'baseUrl' must not be null");
		this.baseUrl = baseUrl;
		this.requestJSONHeaders = new HttpHeaders();
		this.requestJSONHeaders.setAccept(Collections.singletonList(new MediaType("application","json")));
		this.requestJSONHeaders.setContentType(new MediaType("application","json"));
	}

	public ConfigHolder(RestTemplate restTemplate, String baseUrl, HttpHeaders requestJSONHeaders) {
		super();
		this.restTemplate = restTemplate;
		Assert.notNull(baseUrl, "'baseUrl' must not be null");
		this.baseUrl = baseUrl;
		this.requestJSONHeaders = requestJSONHeaders;
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

	public void setRequestJSONHeaders(HttpHeaders requestJSONHeaders) {
		this.requestJSONHeaders = requestJSONHeaders;
	}

	public HttpHeaders getRequestJSONHeaders() {
		return requestJSONHeaders;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}
}
