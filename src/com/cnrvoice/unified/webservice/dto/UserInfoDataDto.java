package com.cnrvoice.unified.webservice.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class UserInfoDataDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<UserInfoDto> data;
	public List<UserInfoDto> getData() {
		return data;
	}
	public void setData(List<UserInfoDto> data) {
		this.data = data;
	}
}
