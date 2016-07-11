/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-6-11 上午10:48:32
 * @Description:
 * 
 */
package com.cnrvoice.cheba.service.question.http;

import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

public class ResourceInvoker
{	
	private ConfigHolder configHolder;

	public ConfigHolder getConfigHolder() {
		return configHolder;
	}

	public void setConfigHolder(ConfigHolder configHolder) {
		this.configHolder = configHolder;
	}
	
	public ResourceInvoker(ConfigHolder configHolder) {
		super();
		this.configHolder = configHolder;
	}
	
	public <T> HttpEntity<T> createJSONEntity() {
		return createJSONEntity(null);
	}
	
	public <T> HttpEntity<T> createJSONEntity(T t) {
		return new HttpEntity<T>(t, getConfigHolder().getRequestJSONHeaders());
	}
	
	public <T> T getEntity(String url,HttpMethod method,HttpEntity<?> requestEntity,Class<T> responseType, Map<String, ?> uriVariables) {
		return  getConfigHolder().getRestTemplate().exchange(getConfigHolder().getBaseUrl()+url, method, requestEntity, responseType, uriVariables).getBody();
	}
	
	public <T> T getEntity(String url,HttpMethod method,HttpEntity<?> requestEntity,Class<T> responseType) {
		return  getConfigHolder().getRestTemplate().exchange(getConfigHolder().getBaseUrl()+url, method, requestEntity, responseType).getBody();
	}
	
	public <T> T getEntityFromGet(String url,HttpEntity<?> requestEntity,Class<T> responseType, Map<String, ?> uriVariables) {
		return getEntity(url, HttpMethod.GET, requestEntity, responseType, uriVariables);
	}
	
	public <T> T getEntityFromGet(String url,HttpEntity<?> requestEntity,Class<T> responseType) {
		return getEntity(url, HttpMethod.GET, requestEntity, responseType);
	}
	
	public <T> T getEntityFromGet(String url,Class<T> responseType, Map<String, ?> uriVariables) {
		return getEntity(url, HttpMethod.GET, createJSONEntity(), responseType, uriVariables);
	}
	
	public <T> T getEntityFromGet(String url,Class<T> responseType) {
		return getEntity(url, HttpMethod.GET, createJSONEntity(), responseType);
	}
	
	public <T> T getEntityFromPost(String url,HttpEntity<?> requestEntity,Class<T> responseType, Map<String, ?> uriVariables) {
		return getEntity(url, HttpMethod.POST, requestEntity, responseType, uriVariables);
	}
	
	public <T> T getEntityFromPost(String url,HttpEntity<?> requestEntity,Class<T> responseType) {
		return getEntity(url, HttpMethod.POST, requestEntity, responseType);
	}
}
