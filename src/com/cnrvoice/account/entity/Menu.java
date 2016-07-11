/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: Guohong Mao
 * @date: 2012-4-11 上午10:18:57
 * @Description:
 * 
 */
package com.cnrvoice.account.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.cnrvoice.base.hibernate.po.generic.anno.GenericAnnoUuidHibernatePo;

@Entity
@Table(name = "menu")
public class Menu extends GenericAnnoUuidHibernatePo {
  private static final long serialVersionUID = 1L;
  private String pid;
  private String text;
  private String state;
  private Boolean checked;
  @Transient
  private MenuAttribute attributes;
  @Transient
  private List<Menu> children;

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public Boolean getChecked() {
    return checked;
  }

  public void setChecked(Boolean checked) {
    this.checked = checked;
  }

  public MenuAttribute getAttributes() {
    return attributes;
  }

  public void setAttributes(MenuAttribute attributes) {
    this.attributes = attributes;
  }

  public List<Menu> getChildren() {
    return children;
  }

  public void setChildren(List<Menu> children) {
    this.children = children;
  }

  public String getPid() {
    return pid;
  }

  public void setPid(String pid) {
    this.pid = pid;
  }

}
