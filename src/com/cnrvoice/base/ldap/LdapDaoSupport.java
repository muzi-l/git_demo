/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-3-8 ����2:58:30
 * @Description:
 * 
 */
package com.cnrvoice.base.ldap;

import org.springframework.dao.support.DaoSupport;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.LdapTemplate;

public class LdapDaoSupport extends DaoSupport {
	private LdapTemplate ldapTemplate;

	public final void setContextSource(ContextSource contextSource)
			throws Exception {
		if (this.ldapTemplate == null
				|| contextSource != this.ldapTemplate.getContextSource()) {
			this.ldapTemplate = createLdapTemplate(contextSource);
		}
	}

	protected LdapTemplate createLdapTemplate(ContextSource contextSource) {
		return new LdapTemplate(contextSource);
	}

	public final ContextSource getContextSource() {
		return (this.ldapTemplate != null ? this.ldapTemplate
				.getContextSource() : null);
	}

	public final void setLdapTemplate(LdapTemplate ldapTemplate) {
		this.ldapTemplate = ldapTemplate;
	}

	public final LdapTemplate getLdapTemplate() {
		return this.ldapTemplate;
	}

	@Override
	protected void checkDaoConfig() throws IllegalArgumentException {
		if (this.ldapTemplate == null) {
			throw new IllegalArgumentException(
					"'contextSource' or 'ldapTemplate' is required");
		}
	}
}
