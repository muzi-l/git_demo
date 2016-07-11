/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: Guohong Mao
 * @date: 2012-4-11 上午10:18:57
 * @Description:
 * 
 */
package com.cnrvoice.account.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cnrvoice.account.entity.Menu;
import com.cnrvoice.account.web.manager.MenuManager;
import com.cnrvoice.base.paging.PagingArrayList;
import com.cnrvoice.base.result.JEasyResultCreater;
import com.cnrvoice.base.result.Result;

@Controller
@RequestMapping("/manager/menu/*")
public class MenuController
{
	@Autowired
	private MenuManager fenzhangMenuManager;
	
	public MenuManager getFenzhangMenuManager()
	{
		return fenzhangMenuManager;
	}
	
	public void setFenzhangMenuManager(MenuManager fenzhangMenuManager)
	{
		this.fenzhangMenuManager = fenzhangMenuManager;
	}
	
	@RequestMapping("all")
	@ResponseBody
	public Result<?> getAllMenus()
	{
		PagingArrayList<Menu> list = (PagingArrayList<Menu>) getFenzhangMenuManager()
				.getAll();
		return JEasyResultCreater.createResult(list.getList());
	}
	
}
