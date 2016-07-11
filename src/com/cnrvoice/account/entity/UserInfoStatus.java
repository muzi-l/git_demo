package com.cnrvoice.account.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cnrvoice.base.hibernate.po.generic.anno.GenericAnnoUuidHibernatePo;

@Entity
@Table(name = "unified_statusset")
public class UserInfoStatus extends GenericAnnoUuidHibernatePo
{

		private static final long serialVersionUID = 6657293852014529312L;
		
		private String type;
		@Column(name = "data")
		private String key;
		
		private String value;
		
		
		public String getType()
		{
			return type;
		}
		
		public void setType(String type)
		{
			this.type = type;
		}
		
		public String getKey()
		{
			return key;
		}
		
		public void setKey(String key)
		{
			this.key = key;
		}
		
		public String getValue()
		{
			return value;
		}
		
		public void setValue(String value)
		{
			this.value = value;
		}
		
	
}
