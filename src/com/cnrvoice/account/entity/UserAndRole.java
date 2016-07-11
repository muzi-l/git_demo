package com.cnrvoice.account.entity;

import com.cnrvoice.base.hibernate.po.generic.hbm.GenericHbmUuidHibernatePo;

public class UserAndRole extends GenericHbmUuidHibernatePo {
	private static final long serialVersionUID = -6604188641934355149L;
	private String userUuid;
	private String roleUuid;

	public UserAndRole() {
	}

	public String getUserUuid() {
		return this.userUuid;
	}

	public void setUserUuid(String userUuid) {
		this.userUuid = userUuid;
	}

	public String getRoleUuid() {
		return this.roleUuid;
	}

	public void setRoleUuid(String roleUuid) {
		this.roleUuid = roleUuid;
	}

}
