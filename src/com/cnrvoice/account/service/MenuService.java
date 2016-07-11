/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: Guohong Mao
 * @date: 2012-4-11 上午10:18:57
 * @Description:
 * 
 */
package com.cnrvoice.account.service;

import java.util.List;
import java.util.Set;

import com.cnrvoice.account.entity.Menu;

public interface MenuService
{
	void save(Menu menu);
	
	Menu getById(String id);
	
	List<Menu> getAll();
	
	List<Menu> getMenuByUser(Set<String> permiSet);
}
