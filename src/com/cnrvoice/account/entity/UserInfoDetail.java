/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: Jirui Zhao
 * @date: 2012-3-8 ����3:06:37
 * @Description:
 * 
 */
package com.cnrvoice.account.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.cnrvoice.base.hibernate.po.generic.anno.GenericAnnoUuidHibernatePo;

@Entity
@Table(name = "unified_userinfo_detail")
public class UserInfoDetail extends GenericAnnoUuidHibernatePo
{
	private static final long serialVersionUID = 5175975689703083401L;
	
	private Integer userinfoId;
	
	private String subsystemUuid;


	public String getSubsystemUuid()
	{
		return subsystemUuid;
	}

	public void setSubsystemUuid(String subsystemUuid)
	{
		this.subsystemUuid = subsystemUuid;
	}

	public Integer getUserinfoId()
	{
		return userinfoId;
	}

	public void setUserinfoId(Integer userinfoId)
	{
		this.userinfoId = userinfoId;
	}

}
