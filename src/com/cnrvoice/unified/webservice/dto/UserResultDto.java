package com.cnrvoice.unified.webservice.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserResultDto implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -9008568772997319047L;

    private String code;

    private UserInfoDto userInfoDto;

    public String getCode() {
	return code;
    }

    public void setCode(String code) {
	this.code = code;
    }

    public UserInfoDto getUserInfoDto() {
	return userInfoDto;
    }

    public void setUserInfoDto(UserInfoDto userInfoDto) {
	this.userInfoDto = userInfoDto;
    }

}
