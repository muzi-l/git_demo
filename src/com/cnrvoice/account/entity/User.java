package com.cnrvoice.account.entity;

import java.util.List;

import com.cnrvoice.base.hibernate.po.generic.hbm.GenericHbmUuidHibernatePo;

public class User extends GenericHbmUuidHibernatePo {
	private static final long serialVersionUID = -6353952666579424791L;
	private String loginName;
	private String status;
	private String type;
	private Integer cpId;

	private List<Role> roles;
	private String roleName;

	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getCpId() {
		return this.cpId;
	}

	public void setCpId(Integer cpId) {
		this.cpId = cpId;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
