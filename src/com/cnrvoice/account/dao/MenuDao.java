/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: Guohong Mao
 * @date: 2012-4-11 上午10:18:57
 * @Description:
 * 
 */
package com.cnrvoice.account.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cnrvoice.account.entity.Menu;
import com.cnrvoice.account.entity.MenuAttribute;
import com.cnrvoice.base.hibernate.dao.generic.GenericAutoSfHibernateDao;

@Repository
public class MenuDao extends GenericAutoSfHibernateDao<Menu, String> {
  public Menu getRoot() {

    /* return findOne(new Query(Criteria.where("attributes.key").is("ROOT"))); */
    
    Menu menu = this.get("ROOT");

    Menu men2 = new Menu();
    men2.setPid(menu.getUuid());
    List<Menu> children = this.queryByExample(men2);
    menu.setChildren(children);
    children = convet(children);
    return menu;
  }

  private List<Menu> convet(List<Menu> menus) {
    for (Menu menu : menus) {
      Menu men2 = new Menu();
      men2.setPid(menu.getUuid());
      List<Menu> children = this.queryByExample(men2);
      // setAttributes
      MenuAttribute menuAttribute = new MenuAttribute();
      menuAttribute.setIsVirtual(Boolean.FALSE);
      menuAttribute.setUrl(menu.getSortNo());
      menuAttribute.setKey(menu.getDescr());
      menu.setAttributes(menuAttribute);
      if (null != children && children.size() > 0) {

        menu.setChildren(children);
        convet(children);
      }
    }
    return menus;
  }
}
