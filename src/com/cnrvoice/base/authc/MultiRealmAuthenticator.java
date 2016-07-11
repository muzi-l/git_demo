/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-10-12 上午11:53:19
 * @Description:
 * 
 */
package com.cnrvoice.base.authc;

import java.util.Collection;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.AuthenticationStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MultiRealmAuthenticator extends ModularRealmAuthenticator
{
	private static final Logger log = LoggerFactory
			.getLogger(MultiRealmAuthenticator.class);
	
	protected AuthenticationInfo doMultiRealmAuthentication(
			Collection<Realm> realms, AuthenticationToken token)
	{
		
		AuthenticationStrategy strategy = getAuthenticationStrategy();
		
		AuthenticationInfo aggregate = strategy
				.beforeAllAttempts(realms, token);
		
		if (log.isTraceEnabled())
		{
			log.trace("Iterating through {} realms for PAM authentication",
					realms.size());
		}
		
		for (Realm realm : realms)
		{
			
			aggregate = strategy.beforeAttempt(realm, token, aggregate);
			
			if (realm.supports(token))
			{
				
				log.trace(
						"Attempting to authenticate token [{}] using realm [{}]",
						token, realm);
				
				AuthenticationInfo info = null;
				Throwable t = null;
				
				info = realm.getAuthenticationInfo(token);
				
				aggregate = strategy.afterAttempt(realm, token, info,
						aggregate, t);
				
			}
			else
			{
				log.debug(
						"Realm [{}] does not support token {}.  Skipping realm.",
						realm, token);
			}
		}
		
		aggregate = strategy.afterAllAttempts(token, aggregate);
		
		return aggregate;
	}
}
